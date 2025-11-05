package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.IClientConfigAware;
import com.netflix.client.PrimeConnections;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.util.concurrent.ShutdownEnabledTimer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class BaseLoadBalancerDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BaseLoadBalancer#setEnablePrimingConnections(boolean)}
   *   <li>{@link BaseLoadBalancer#setLoadBalancerStats(LoadBalancerStats)}
   *   <li>{@link BaseLoadBalancer#setPrimeConnections(PrimeConnections)}
   *   <li>{@link BaseLoadBalancer#toString()}
   *   <li>{@link BaseLoadBalancer#getClientConfig()}
   *   <li>{@link BaseLoadBalancer#getLoadBalancerStats()}
   *   <li>{@link BaseLoadBalancer#getMaxTotalPingTime()}
   *   <li>{@link BaseLoadBalancer#getName()}
   *   <li>{@link BaseLoadBalancer#getPing()}
   *   <li>{@link BaseLoadBalancer#getPingInterval()}
   *   <li>{@link BaseLoadBalancer#getPrimeConnections()}
   *   <li>{@link BaseLoadBalancer#getRule()}
   *   <li>{@link BaseLoadBalancer#isEnablePrimingConnections()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setEnablePrimingConnections(true);
    LoadBalancerStats lbStats = new LoadBalancerStats();
    baseLoadBalancer.setLoadBalancerStats(lbStats);
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    baseLoadBalancer.setPrimeConnections(primeConnections);
    String actualToStringResult = baseLoadBalancer.toString();
    baseLoadBalancer.getClientConfig();
    LoadBalancerStats actualLoadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    int actualMaxTotalPingTime = baseLoadBalancer.getMaxTotalPingTime();
    String actualName = baseLoadBalancer.getName();
    baseLoadBalancer.getPing();
    int actualPingInterval = baseLoadBalancer.getPingInterval();
    PrimeConnections actualPrimeConnections = baseLoadBalancer.getPrimeConnections();
    IRule actualRule = baseLoadBalancer.getRule();

    // Assert that nothing has changed
    assertTrue(actualRule instanceof RoundRobinRule);
    assertEquals("default", actualName);
    assertEquals("{NFLoadBalancer:name=default,current list of Servers=[],Load balancer stats=Zone stats: {},Server"
        + " stats: []}", actualToStringResult);
    assertEquals(10, actualPingInterval);
    assertEquals(5, actualMaxTotalPingTime);
    assertTrue(baseLoadBalancer.isEnablePrimingConnections());
    assertSame(primeConnections, actualPrimeConnections);
    assertSame(lbStats, actualLoadBalancerStats);
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testInitWithConfig() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping);

    // Assert
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(baseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("", baseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, baseLoadBalancer.getRule());
    assertSame(baseLoadBalancer, rule.getLoadBalancer());
    assertSame(baseLoadBalancer, rule.roundRobinRule.getLoadBalancer());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
    assertSame(ping, baseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testInitWithConfig2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    Server newServer2 = new Server("42");
    baseLoadBalancer.addServer(newServer2);
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(baseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("", baseLoadBalancer.getName());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.allServerList;
    assertEquals(serverList2, baseLoadBalancer.getAllServers());
    assertEquals(serverList2, baseLoadBalancer.getReachableServers());
    assertEquals(serverList2, baseLoadBalancer.upServerList);
    assertSame(rule, baseLoadBalancer.getRule());
    assertSame(baseLoadBalancer, rule.getLoadBalancer());
    assertSame(baseLoadBalancer, rule.roundRobinRule.getLoadBalancer());
    assertSame(newServer, serverList.get(0));
    assertSame(newServer2, serverList.get(1));
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
    assertSame(ping, baseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   */
  @Test
  public void testInitWithConfig3() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    LoadBalancerStats stats = new LoadBalancerStats();

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping, stats);

    // Assert
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(baseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("", baseLoadBalancer.getName());
    assertNull(baseLoadBalancer.getPrimeConnections());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, baseLoadBalancer.getRule());
    assertSame(baseLoadBalancer, rule.getLoadBalancer());
    assertSame(baseLoadBalancer, rule.roundRobinRule.getLoadBalancer());
    assertSame(stats, baseLoadBalancer.getLoadBalancerStats());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
    assertSame(ping, baseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   */
  @Test
  public void testInitWithConfig4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(mock(IPing.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    LoadBalancerStats stats = new LoadBalancerStats();

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping, stats);

    // Assert
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(baseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("", baseLoadBalancer.getName());
    assertNull(baseLoadBalancer.getPrimeConnections());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, baseLoadBalancer.getRule());
    assertSame(baseLoadBalancer, rule.getLoadBalancer());
    assertSame(baseLoadBalancer, rule.roundRobinRule.getLoadBalancer());
    assertSame(stats, baseLoadBalancer.getLoadBalancerStats());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
    assertSame(ping, baseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   */
  @Test
  public void testInitWithConfig5() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    LoadBalancerStats stats = new LoadBalancerStats();

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping, stats);

    // Assert
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(baseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals(0, rule.getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, baseLoadBalancer.getRule());
    assertSame(baseLoadBalancer, rule.getLoadBalancer());
    assertSame(baseLoadBalancer, rule.roundRobinRule.getLoadBalancer());
    assertSame(stats, baseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, baseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   */
  @Test
  public void testInitWithConfig6() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener3);
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    baseLoadBalancer.setPing(ping);
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    BestAvailableRule rule = new BestAvailableRule();
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);
    LoadBalancerStats stats = new LoadBalancerStats();

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping2, stats);

    // Assert
    List<Server> serverList = baseLoadBalancer.allServerList;
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(baseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("", baseLoadBalancer.getName());
    assertNull(baseLoadBalancer.getPrimeConnections());
    List<Server> serverList2 = baseLoadBalancer.allServerList;
    assertEquals(1, serverList2.size());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertEquals(serverList, baseLoadBalancer.getAllServers());
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
    assertSame(baseLoadBalancer, rule.getLoadBalancer());
    assertSame(baseLoadBalancer, rule.roundRobinRule.getLoadBalancer());
    assertSame(rule, baseLoadBalancer.getRule());
    assertSame(stats, baseLoadBalancer.getLoadBalancerStats());
    assertSame(newServer, serverList2.get(0));
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
    assertSame(ping2, baseLoadBalancer.getPing());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = baseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertEquals("", baseLoadBalancer.getName());
    assertEquals("", baseLoadBalancer.getLoadBalancerStats().getName());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(10L, clientConfig.getRefreshCount());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(mock(IPing.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = baseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertEquals("", baseLoadBalancer.getName());
    assertEquals("", baseLoadBalancer.getLoadBalancerStats().getName());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(10L, clientConfig.getRefreshCount());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig3() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = baseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertEquals("", baseLoadBalancer.getName());
    assertEquals("", baseLoadBalancer.getLoadBalancerStats().getName());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(10L, clientConfig.getRefreshCount());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = baseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertEquals("", baseLoadBalancer.getName());
    assertEquals("", baseLoadBalancer.getLoadBalancerStats().getName());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(10L, clientConfig.getRefreshCount());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig5() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = baseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertEquals("", baseLoadBalancer.getName());
    assertEquals("", baseLoadBalancer.getLoadBalancerStats().getName());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(10L, clientConfig.getRefreshCount());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    IPing ping2 = baseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertEquals(10L, clientConfig.getRefreshCount());
    assertSame(baseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig7() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "Name Space");

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping2 = baseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertEquals("Dr Jane Doe", baseLoadBalancer.getName());
    assertEquals("Dr Jane Doe", baseLoadBalancer.getLoadBalancerStats().getName());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(10L, clientConfig.getRefreshCount());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
    assertSame(baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig8() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException(" ")).when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping2 = baseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertEquals("", baseLoadBalancer.getName());
    assertEquals("", baseLoadBalancer.getLoadBalancerStats().getName());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(10L, clientConfig.getRefreshCount());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
    assertSame(baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig, IClientConfigAware.Factory)}
   */
  @Test
  public void testInitWithNiwsConfig9() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.get(Mockito.<IClientConfigKey<Object>>any(), Mockito.<Object>any())).thenReturn("Get");
    when(clientConfig.get(Mockito.<IClientConfigKey<Integer>>any(), Mockito.<Integer>any()))
        .thenThrow(new IllegalArgumentException("Error initializing load balancer"));
    when(clientConfig.getClientName()).thenThrow(new IllegalArgumentException("Error initializing load balancer"));
    when(clientConfig.getOrDefault(Mockito.<IClientConfigKey<String>>any())).thenReturn("Or Default");
    IClientConfigAware.Factory factory = mock(IClientConfigAware.Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any())).thenReturn(null);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    baseLoadBalancer.initWithNiwsConfig(clientConfig, factory);
    verify(factory, atLeast(1)).create(eq("Or Default"), isA(IClientConfig.class));
    verify(clientConfig).get(Mockito.<IClientConfigKey<Object>>any(), Mockito.<Object>any());
    verify(clientConfig).getClientName();
    verify(clientConfig, atLeast(1)).getOrDefault(Mockito.<IClientConfigKey<String>>any());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#addServerListChangeListener(ServerListChangeListener)}
   */
  @Test
  public void testAddServerListChangeListener() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    baseLoadBalancer.setPing(ping);

    Server newServer = new Server("42");
    newServer.setZone("Zone");
    baseLoadBalancer.addServer(newServer);

    // Act
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer()}
   */
  @Test
  public void testNewBaseLoadBalancer39() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer();

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer40() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, config.getRefreshCount());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer41() {
    // Arrange
    DefaultClientConfigImpl config = new DefaultClientConfigImpl();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, config.getRefreshCount());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer42() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Dr Jane Doe", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("Dr Jane Doe", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, config.getRefreshCount());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer43() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, config.getRefreshCount());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer44() {
    // Arrange, Act and Assert
    thrown.expect(RuntimeException.class);
    new BaseLoadBalancer(DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " "));
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer45() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer46() {
    // Arrange
    DefaultClientConfigImpl config = new DefaultClientConfigImpl();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer47() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Dr Jane Doe", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("Dr Jane Doe", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer48() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DummyPing ping = new DummyPing();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    IPing ping2 = actualBaseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
    assertSame(ping, ping2);
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer49() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " ");

    // Act and Assert
    thrown.expect(RuntimeException.class);

    new BaseLoadBalancer(config, new AvailabilityFilteringRule(), mock(IPing.class));

  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer50() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals("", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer51() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("LoadBalancer [{}]: maxTotalPingTime set to {}", " ");
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("LoadBalancer [{}]: maxTotalPingTime set to {}", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("LoadBalancer [{}]: maxTotalPingTime set to {}", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer52() {
    // Arrange
    IPing ping = mock(IPing.class);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer53() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(null, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer54() {
    // Arrange
    IPing ping = mock(IPing.class);
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer55() {
    // Arrange
    IPing ping = mock(IPing.class);
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertEquals("default", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer56() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(null, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertEquals("default", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer57() {
    // Arrange
    DummyPing ping = new DummyPing();
    ClientConfigEnabledRoundRobinRule rule = new ClientConfigEnabledRoundRobinRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    IPing ping2 = actualBaseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(((DummyPing) ping2).getLoadBalancer());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, ping2);
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer58() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer59() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(" ", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals(" ", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer60() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", null, lbStats);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer61() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer62() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertEquals("Lb Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer63() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server(Server.UNKNOWN_ZONE));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertEquals("Lb Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer64() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();
    rule.setLoadBalancer(new BaseLoadBalancer());
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer65() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server(Server.UNKNOWN_ZONE));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("LoadBalancer [{}]: Error pinging", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("LoadBalancer [{}]: Error pinging", actualBaseLoadBalancer.getName());
    assertEquals("LoadBalancer [{}]: Error pinging", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer66() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server(Server.UNKNOWN_ZONE));
    lbStats.incrementZoneCounter(new Server(Server.UNKNOWN_ZONE));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertEquals("Lb Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer67() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server("localhost", 8080));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertEquals("Lb Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer68() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();
    IPing ping = mock(IPing.class);
    rule.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer69() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[]{-1});

    BestAvailableRule rule = new BestAvailableRule();
    rule.setLoadBalancer(lb);
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer70() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[]{"NFLoadBalancer-PingTimer-"});

    BestAvailableRule rule = new BestAvailableRule();
    rule.setLoadBalancer(lb);
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer71() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server("localhost", 5));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertEquals("Lb Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer72() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException("foo"));

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServer(new Server("42"));

    BestAvailableRule rule = new BestAvailableRule();
    rule.setLoadBalancer(lb);
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer73() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException("foo"));

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServer(new Server(Server.UNKNOWN_ZONE));

    BestAvailableRule rule = new BestAvailableRule();
    rule.setLoadBalancer(lb);
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer74() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer75() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(" ", rule, stats, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals(" ", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer76() {
    // Arrange
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", null, stats, ping);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer77() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.incrementZoneCounter(new Server("com.netflix.loadbalancer.Server"));
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer78() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats stats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, null);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer79() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.incrementZoneCounter(new Server("Id"));
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(" ", rule, stats, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals(" ", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer80() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertEquals("Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer81() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, null, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getLoadBalancerStats());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer82() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.incrementZoneCounter(new Server("com.netflix.loadbalancer.Server"));
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("com.netflix.loadbalancer.DummyPing", rule, stats,
        ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("com.netflix.loadbalancer.DummyPing", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  public void testNewBaseLoadBalancer83() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.incrementZoneCounter(new Server("42"));
    stats.incrementZoneCounter(new Server("42"));
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any())).thenThrow(new RuntimeException(" "));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test: {@link BaseLoadBalancer.PingTask#run()}
   */
  @Test
  public void testPingTaskRun() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(false);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    (baseLoadBalancer.new PingTask()).run();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer.PingTask#run()}
   */
  @Test
  public void testPingTaskRun2() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    (baseLoadBalancer.new PingTask()).run();

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer.Pinger#runPinger()}
   */
  @Test
  public void testPingerRunPinger() throws Exception {
    // Arrange
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    ((new BaseLoadBalancer()).new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(pingerStrategy).pingServers(isNull(), isA(Server[].class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer.Pinger#runPinger()}
   */
  @Test
  public void testPingerRunPinger2() throws Exception {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    (baseLoadBalancer.new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(pingerStrategy).pingServers(isNull(), isA(Server[].class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer.Pinger#runPinger()}
   */
  @Test
  public void testPingerRunPinger3() throws Exception {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(false);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    (baseLoadBalancer.new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer.Pinger#runPinger()}
   */
  @Test
  public void testPingerRunPinger4() throws Exception {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    (baseLoadBalancer.new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer.Pinger#runPinger()}
   */
  @Test
  public void testPingerRunPinger5() throws Exception {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    (baseLoadBalancer.new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    verify(listener, atLeast(1)).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#removeServerListChangeListener(ServerListChangeListener)}
   */
  @Test
  public void testRemoveServerListChangeListener() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.removeServerListChangeListener(mock(ServerListChangeListener.class));

    // Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#removeServerListChangeListener(ServerListChangeListener)}
   */
  @Test
  public void testRemoveServerListChangeListener2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Act
    baseLoadBalancer.removeServerListChangeListener(mock(ServerListChangeListener.class));

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#removeServerListChangeListener(ServerListChangeListener)}
   */
  @Test
  public void testRemoveServerListChangeListener3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(false);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new Object[]{"New Servers"});
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Act
    baseLoadBalancer.removeServerListChangeListener(mock(ServerListChangeListener.class));

    // Assert
    verify(ping2).isAlive(isA(Server.class));
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#lockAllServerList(boolean)}
   */
  @Test
  public void testLockAllServerList() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act and Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.lockAllServerList(true) instanceof ReentrantReadWriteLock.WriteLock);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#lockAllServerList(boolean)}
   */
  @Test
  public void testLockAllServerList2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.lockAllServerList(false);

    // Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertEquals(1, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#lockAllServerList(boolean)}
   */
  @Test
  public void testLockAllServerList3() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act and Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.lockAllServerList(true) instanceof ReentrantReadWriteLock.WriteLock);
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#lockAllServerList(boolean)}
   */
  @Test
  public void testLockAllServerList4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPingInterval(42);

    // Act
    baseLoadBalancer.lockAllServerList(false);

    // Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertEquals(1, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  public void testLockUpServerList() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act and Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.upServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.lockUpServerList(true) instanceof ReentrantReadWriteLock.WriteLock);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  public void testLockUpServerList2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.lockUpServerList(false);

    // Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.upServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertEquals(1, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  public void testLockUpServerList3() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act and Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.upServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.lockUpServerList(true) instanceof ReentrantReadWriteLock.WriteLock);
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  public void testLockUpServerList4() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new ResponseTimeWeightedRule());

    // Act and Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.upServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.lockUpServerList(true) instanceof ReentrantReadWriteLock.WriteLock);
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  public void testLockUpServerList5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    Lock actualLockUpServerListResult = baseLoadBalancer.lockUpServerList(true);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ReadWriteLock readWriteLock = baseLoadBalancer.upServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLockUpServerListResult instanceof ReentrantReadWriteLock.WriteLock);
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  public void testSetMaxTotalPingTime() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    assertEquals(3, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  public void testSetMaxTotalPingTime2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setMaxTotalPingTime(0);

    // Assert that nothing has changed
    assertEquals(5, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  public void testSetMaxTotalPingTime3() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    assertEquals(3, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  public void testSetMaxTotalPingTime4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{42});

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    assertEquals(3, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  public void testSetMaxTotalPingTime5() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("com.netflix.loadbalancer.Server"));

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    assertEquals(3, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  public void testSetMaxTotalPingTime6() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{5});

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    assertEquals(3, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  public void testSetMaxTotalPingTime7() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new Object[]{-1});

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    assertEquals(3, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  public void testSetMaxTotalPingTime8() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{42});

    // Act
    baseLoadBalancer.setMaxTotalPingTime(2);

    // Assert
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  public void testIsPingInProgress() {
    // Arrange, Act and Assert
    assertFalse((new BaseLoadBalancer()).isPingInProgress());
    assertFalse((new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig())).isPingInProgress());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  public void testIsPingInProgress2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(false);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.isPingInProgress();

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  public void testIsPingInProgress3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RandomRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    boolean actualIsPingInProgressResult = baseLoadBalancer.isPingInProgress();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertFalse(actualIsPingInProgressResult);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  public void testIsPingInProgress4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new ResponseTimeWeightedRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    boolean actualIsPingInProgressResult = baseLoadBalancer.isPingInProgress();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertFalse(actualIsPingInProgressResult);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  public void testIsPingInProgress5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, null);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    boolean actualIsPingInProgressResult = baseLoadBalancer.isPingInProgress();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertFalse(actualIsPingInProgressResult);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  public void testIsPingInProgress6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RetryRule(new AvailabilityFilteringRule(), 4L));
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    boolean actualIsPingInProgressResult = baseLoadBalancer.isPingInProgress();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertFalse(actualIsPingInProgressResult);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  public void testIsPingInProgress7() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, null);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    boolean actualIsPingInProgressResult = baseLoadBalancer.isPingInProgress();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertFalse(actualIsPingInProgressResult);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  public void testIsPingInProgress8() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any()))
        .thenThrow(new IllegalArgumentException("LoadBalancer:  PingTask executing [{}] servers configured"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPing(ping2);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.isPingInProgress();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping2, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  public void testGetServerCount() {
    // Arrange, Act and Assert
    assertEquals(0, (new BaseLoadBalancer()).getServerCount(true));
    assertEquals(0, (new BaseLoadBalancer()).getServerCount(false));
    assertEquals(0, (new BaseLoadBalancer(mock(IPing.class), null)).getServerCount(true));
    assertEquals(0, (new BaseLoadBalancer()).getServerCount(true));
    assertEquals(0, (new BaseLoadBalancer()).getServerCount(false));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  public void testGetServerCount2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertEquals(0, (new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getServerCount(true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  public void testGetServerCount3() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{-1});

    // Act and Assert
    assertEquals(0, baseLoadBalancer.getServerCount(false));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  public void testGetServerCount4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(mock(IPing.class), null);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));

    // Act and Assert
    assertEquals(0, baseLoadBalancer.getServerCount(true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  public void testGetServerCount5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, null);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    int actualServerCount = baseLoadBalancer.getServerCount(true);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(0, actualServerCount);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  public void testGetServerCount6() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertEquals(0, (new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getServerCount(true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  public void testGetServerCount7() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));

    // Act and Assert
    assertEquals(0, baseLoadBalancer.getServerCount(true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  public void testGetServerCount8() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(new LoadBalancerStats());

    // Act and Assert
    assertEquals(0, baseLoadBalancer.getServerCount(true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertTrue(newServer.isAlive());
    assertTrue(newServer.isReadyToServe());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
    assertSame(newServer, serverList2.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertTrue(newServer.isAlive());
    assertTrue(newServer.isReadyToServe());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
    assertSame(newServer, serverList2.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener3).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertTrue(newServer.isAlive());
    assertTrue(newServer.isReadyToServe());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
    assertSame(newServer, serverList2.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer4() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServer(null);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any()))
        .thenThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.setPing(ping2);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer2 = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer2);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping2, atLeast(1)).isAlive(isA(Server.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertEquals(newServer, newServer2);
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
    assertSame(newServer, serverList.get(0));
    assertSame(newServer2, serverList.get(1));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any()))
        .thenThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    Server newServer2 = new Server("42");
    baseLoadBalancer.addServer(newServer2);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.setPing(ping2);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer3 = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer3);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(ping2, atLeast(1)).isAlive(isA(Server.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(3, serverList.size());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertEquals(newServer, newServer3);
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
    assertSame(newServer, serverList.get(0));
    assertSame(newServer2, serverList.get(1));
    assertSame(newServer3, serverList.get(2));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer7() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any()))
        .thenThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setPrimeConnections(new PrimeConnections("LoadBalancer [{}]: clearing server list (SET op)", 3, 1L,
        "LoadBalancer [{}]: clearing server list (SET op)"));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.setPing(ping2);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer2 = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer2);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping2, atLeast(1)).isAlive(isA(Server.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertEquals(newServer, newServer2);
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
    assertSame(newServer, serverList.get(0));
    assertSame(newServer2, serverList.get(1));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer8() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any()))
        .thenThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setPrimeConnections(new PrimeConnections("LoadBalancer [{}]: clearing server list (SET op)", 3, 1L,
        "LoadBalancer [{}]: clearing server list (SET op)"));
    Server newServer = new Server(". No nodes/servers to prime connections");
    baseLoadBalancer.addServer(newServer);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.setPing(ping2);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer2 = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer2);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping2, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    assertFalse(newServer2.isAlive());
    assertFalse(newServer2.isReadyToServe());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
    assertSame(newServer, serverList.get(0));
    assertSame(newServer2, serverList.get(1));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer9() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertTrue(newServer.isAlive());
    assertTrue(newServer.isReadyToServe());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
    assertSame(newServer, serverList2.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer10() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertTrue(newServer.isAlive());
    assertTrue(newServer.isReadyToServe());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
    assertSame(newServer, serverList2.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer11() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertTrue(newServer.isAlive());
    assertTrue(newServer.isReadyToServe());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
    assertSame(newServer, serverList2.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer12() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServer(null);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer13() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  forceQuickPing invoking")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    dynamicServerListLoadBalancer.addServer(newServer);

    // Assert
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> serverList = dynamicServerListLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    assertFalse(newServer.isAlive());
    assertFalse(newServer.isReadyToServe());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer14() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  forceQuickPing invoking")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any()))
        .thenThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    Server newServer2 = new Server("42");
    baseLoadBalancer.addServer(newServer2);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.setPing(ping2);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer3 = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer3);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(ping2, atLeast(1)).isAlive(isA(Server.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(3, serverList.size());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertEquals(newServer, newServer3);
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
    assertSame(newServer, serverList.get(0));
    assertSame(newServer2, serverList.get(1));
    assertSame(newServer3, serverList.get(2));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer15() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException((String) null)).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any()))
        .thenThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)"));

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.setPing(ping);
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    dynamicServerListLoadBalancer.addServer(newServer);

    // Assert
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> serverList = dynamicServerListLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    assertFalse(newServer.isAlive());
    assertFalse(newServer.isReadyToServe());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer16() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  forceQuickPing invoking")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server(null);

    // Act
    dynamicServerListLoadBalancer.addServer(newServer);

    // Assert
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> serverList = dynamicServerListLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    assertFalse(newServer.isReadyToServe());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  public void testAddServer17() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  forceQuickPing invoking")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any()))
        .thenThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)"));

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.setPing(ping);
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("/");

    // Act
    dynamicServerListLoadBalancer.addServer(newServer);

    // Assert
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> serverList = dynamicServerListLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    assertFalse(newServer.isReadyToServe());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    ArrayList<Server> newServers = new ArrayList<>();

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert that nothing has changed
    assertTrue(newServers.isEmpty());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers2() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    ArrayList<Server> newServers = new ArrayList<>();

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert that nothing has changed
    assertTrue(newServers.isEmpty());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers3() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    List<Server> serverList3 = baseLoadBalancer.upServerList;
    assertEquals(serverList3, newServers);
    assertEquals(serverList3, baseLoadBalancer.getAllServers());
    assertEquals(serverList3, baseLoadBalancer.getReachableServers());
    assertSame(server, serverList.get(0));
    assertSame(server, serverList2.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);
    Server server2 = new Server("42");
    newServers.add(server2);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(2, serverList2.size());
    List<Server> serverList3 = baseLoadBalancer.upServerList;
    assertEquals(serverList3, newServers);
    assertEquals(serverList3, baseLoadBalancer.getAllServers());
    assertEquals(serverList3, baseLoadBalancer.getReachableServers());
    assertSame(server, serverList.get(0));
    assertSame(server2, serverList.get(1));
    assertSame(server, serverList2.get(0));
    assertSame(server2, serverList2.get(1));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.allServerList;
    assertEquals(serverList2, newServers);
    assertEquals(serverList2, baseLoadBalancer.getAllServers());
    assertEquals(serverList2, baseLoadBalancer.getReachableServers());
    assertEquals(serverList2, baseLoadBalancer.upServerList);
    assertSame(server, serverList.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(false);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    List<Server> serverList2 = baseLoadBalancer.allServerList;
    assertEquals(serverList2, newServers);
    assertEquals(serverList2, baseLoadBalancer.getAllServers());
    assertSame(server, serverList.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers7() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    List<Server> serverList3 = baseLoadBalancer.upServerList;
    assertEquals(serverList3, newServers);
    assertEquals(serverList3, baseLoadBalancer.getAllServers());
    assertEquals(serverList3, baseLoadBalancer.getReachableServers());
    assertSame(server, serverList.get(0));
    assertSame(server, serverList2.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers8() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    List<Server> serverList3 = baseLoadBalancer.upServerList;
    assertEquals(serverList3, newServers);
    assertEquals(serverList3, baseLoadBalancer.getAllServers());
    assertEquals(serverList3, baseLoadBalancer.getReachableServers());
    assertSame(server, serverList.get(0));
    assertSame(server, serverList2.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers9() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(null);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    assertEquals(1, newServers.size());
    assertNull(newServers.get(0));
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers10() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(null);

    // Act
    dynamicServerListLoadBalancer.addServers(newServers);

    // Assert
    assertEquals(1, newServers.size());
    assertNull(newServers.get(0));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers11() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers12() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers13() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers14() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(false);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers15() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[]{2});

    // Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers16() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[]{":"});

    // Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers17() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[]{null});

    // Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers18() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers((Object[]) null);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers19() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[]{});

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers20() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException(Server.UNKNOWN_ZONE)).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);

    // Act
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals(1, dynamicServerListLoadBalancer.allServerList.size());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    List<Server> expectedAllServers = dynamicServerListLoadBalancer.allServerList;
    assertEquals(expectedAllServers, dynamicServerListLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers21() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException(Server.UNKNOWN_ZONE)).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException(Server.UNKNOWN_ZONE));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    Server newServer2 = new Server("42");
    baseLoadBalancer.addServer(newServer2);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.setPing(ping2);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(ping2, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(3, serverList.size());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
    assertSame(newServer, serverList.get(0));
    assertSame(newServer2, serverList.get(1));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers22() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException(Server.UNKNOWN_ZONE)).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException(Server.UNKNOWN_ZONE));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setPrimeConnections(new PrimeConnections(Server.UNKNOWN_ZONE, 3, 1L, Server.UNKNOWN_ZONE));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.setPing(ping2);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping2, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers23() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    ArrayList<Server> newServers = new ArrayList<>();

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert that nothing has changed
    assertTrue(newServers.isEmpty());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers24() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    ArrayList<Server> newServers = new ArrayList<>();

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert that nothing has changed
    assertTrue(newServers.isEmpty());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers25() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    List<Server> serverList3 = baseLoadBalancer.upServerList;
    assertEquals(serverList3, newServers);
    assertEquals(serverList3, baseLoadBalancer.getAllServers());
    assertEquals(serverList3, baseLoadBalancer.getReachableServers());
    assertSame(server, serverList.get(0));
    assertSame(server, serverList2.get(0));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers26() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);
    Server server2 = new Server("42");
    newServers.add(server2);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(2, serverList2.size());
    List<Server> serverList3 = baseLoadBalancer.upServerList;
    assertEquals(serverList3, newServers);
    assertEquals(serverList3, baseLoadBalancer.getAllServers());
    assertEquals(serverList3, baseLoadBalancer.getReachableServers());
    assertSame(server, serverList.get(0));
    assertSame(server2, serverList.get(1));
    assertSame(server, serverList2.get(0));
    assertSame(server2, serverList2.get(1));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers27() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    Server newServer = new Server("/");
    baseLoadBalancer.addServer(newServer);
    Server server = new Server("42");

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> serverList = baseLoadBalancer.allServerList;
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    assertEquals(1, newServers.size());
    List<Server> serverList2 = baseLoadBalancer.allServerList;
    assertEquals(2, serverList2.size());
    assertEquals(serverList, baseLoadBalancer.getAllServers());
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
    assertSame(newServer, serverList2.get(0));
    assertSame(server, newServers.get(0));
    assertSame(server, serverList2.get(1));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers28() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    Server newServer = new Server("/");
    baseLoadBalancer.addServer(newServer);
    Server server = new Server("42");

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(server);
    Server server2 = new Server("42");
    newServers.add(server2);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    assertEquals(2, newServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(3, serverList.size());
    Server getResult = newServers.get(1);
    assertFalse(getResult.isAlive());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
    assertSame(newServer, serverList.get(0));
    assertSame(server, newServers.get(0));
    assertSame(server2, getResult);
    assertSame(server, serverList.get(1));
    assertSame(server2, serverList.get(2));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers29() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RandomRule());
    baseLoadBalancer.addServer(new Server("/"));

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    assertEquals(1, newServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.allServerList;
    assertEquals(serverList2, baseLoadBalancer.getAllServers());
    assertEquals(serverList2, baseLoadBalancer.getReachableServers());
    assertEquals(serverList2, baseLoadBalancer.upServerList);
    assertSame(server, newServers.get(0));
    assertSame(server, serverList.get(1));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  public void testAddServers30() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    baseLoadBalancer.addServer(new Server("/"));

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    assertEquals(1, newServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(2, serverList2.size());
    List<Server> serverList3 = baseLoadBalancer.upServerList;
    assertEquals(serverList3, baseLoadBalancer.getAllServers());
    assertEquals(serverList3, baseLoadBalancer.getReachableServers());
    assertSame(server, newServers.get(0));
    assertSame(server, serverList.get(1));
    assertSame(server, serverList2.get(1));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers31() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers32() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers33() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    verify(ping).isAlive(isA(Server.class));
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers34() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[]{2});

    // Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers35() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[]{":"});

    // Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers36() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[]{null});

    // Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers37() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers((Object[]) null);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers38() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[]{});

    // Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers39() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException(Server.UNKNOWN_ZONE)).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);

    // Act
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals(3, dynamicServerListLoadBalancer.allServerList.size());
    assertEquals(3, dynamicServerListLoadBalancer.upServerList.size());
    List<Server> serverList = dynamicServerListLoadBalancer.upServerList;
    assertEquals(serverList, dynamicServerListLoadBalancer.getAllServers());
    assertEquals(serverList, dynamicServerListLoadBalancer.getReachableServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  public void testAddServers40() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.chooseServer("Key");
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException(Server.UNKNOWN_ZONE));
    baseLoadBalancer.setPing(ping2);
    baseLoadBalancer.addServers(new Object[]{"New Servers"});
    baseLoadBalancer.addServers(new Object[]{Integer.MIN_VALUE});
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException(Server.UNKNOWN_ZONE)).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing().when(listener2).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener2);

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    verify(ping2, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    assertEquals(2, baseLoadBalancer.allServerList.size());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).getServerByIndex(1, true));
    assertNull((new BaseLoadBalancer()).getServerByIndex(1, false));
    assertNull((new BaseLoadBalancer()).getServerByIndex(1, true));
    assertNull((new BaseLoadBalancer()).getServerByIndex(1, false));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertNull((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getServerByIndex(1, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex3() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(mock(IPing.class));

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(1, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.getServerByIndex(1, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex5() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.getServerByIndex(1, false));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex6() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.setPing(mock(IPing.class));

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(1, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex7() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setMaxTotalPingTime(3);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.setPing(mock(IPing.class));

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(1, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex8() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertNull((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getServerByIndex(1, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex9() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{2});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(1, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex10() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act
    Server actualServerByIndex = baseLoadBalancer.getServerByIndex(1, true);

    // Assert
    assertEquals("New Servers", actualServerByIndex.getHost());
    assertEquals("New Servers:80", actualServerByIndex.getHostPort());
    assertEquals("New Servers:80", actualServerByIndex.getId());
    assertNull(actualServerByIndex.getScheme());
    assertEquals(80, actualServerByIndex.getPort());
    assertTrue(actualServerByIndex.isAlive());
    assertTrue(actualServerByIndex.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualServerByIndex.getZone());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex11() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("Id"));
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act
    Server actualServerByIndex = baseLoadBalancer.getServerByIndex(1, true);

    // Assert
    assertEquals("New Servers", actualServerByIndex.getHost());
    assertEquals("New Servers:80", actualServerByIndex.getHostPort());
    assertEquals("New Servers:80", actualServerByIndex.getId());
    assertNull(actualServerByIndex.getScheme());
    assertEquals(80, actualServerByIndex.getPort());
    assertTrue(actualServerByIndex.isAlive());
    assertTrue(actualServerByIndex.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualServerByIndex.getZone());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex12() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act
    Server actualServerByIndex = baseLoadBalancer.getServerByIndex(1, false);

    // Assert
    assertEquals("New Servers", actualServerByIndex.getHost());
    assertEquals("New Servers:80", actualServerByIndex.getHostPort());
    assertEquals("New Servers:80", actualServerByIndex.getId());
    assertNull(actualServerByIndex.getScheme());
    assertEquals(80, actualServerByIndex.getPort());
    assertTrue(actualServerByIndex.isAlive());
    assertTrue(actualServerByIndex.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualServerByIndex.getZone());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex13() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(10, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex14() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(10, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex15() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server(""));
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(10, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex16() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server(Server.UNKNOWN_ZONE));
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(10, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex17() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{2});
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(10, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex18() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{1});
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(10, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex19() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{","});
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(10, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex20() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{5});
    baseLoadBalancer.addServers(new Object[]{"New Servers", "New Servers"});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(10, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex21() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{Short.SIZE, "New Servers"});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(10, true));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  public void testGetServerByIndex22() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{Integer.MIN_VALUE, "New Servers"});

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(10, true));
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.ALL);

    // Assert
    assertTrue(actualServerList.isEmpty());
    assertSame(baseLoadBalancer.allServerList, actualServerList);
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.STATUS_UP);

    // Assert
    assertTrue(actualServerList.isEmpty());
    assertSame(baseLoadBalancer.upServerList, actualServerList);
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList3() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(AbstractLoadBalancer.ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList4() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.ALL);

    // Assert
    assertTrue(actualServerList.isEmpty());
    assertSame(baseLoadBalancer.allServerList, actualServerList);
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList5() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(new LoadBalancerStats());

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.ALL);

    // Assert
    assertTrue(actualServerList.isEmpty());
    assertSame(baseLoadBalancer.allServerList, actualServerList);
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList6() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("http"));

    // Act and Assert
    assertSame(baseLoadBalancer.upServerList,
        baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.STATUS_UP));
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList7() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("http"));

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList8() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("http"));

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList9() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("http://"));
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("http"));

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList10() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList11() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(false).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList12() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertTrue((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList13() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setPingInterval(42);

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList14() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.setPingInterval(42);

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList15() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.setPingInterval(42);

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList16() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RoundRobinRule());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.setPingInterval(42);

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList17() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(null, new AvailabilityFilteringRule());
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.setPingInterval(42);

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList18() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(mock(IPing.class), null);
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.setPingInterval(42);

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList19() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new BestAvailableRule());
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.setPingInterval(42);

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList20() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RandomRule());
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.setPingInterval(42);

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList21() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(mock(IPing.class), null);
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.addServers(new ArrayList<>());
    baseLoadBalancer.setPingInterval(42);

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(true).isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList22() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.ALL);

    // Assert
    assertTrue(actualServerList.isEmpty());
    assertSame(baseLoadBalancer.allServerList, actualServerList);
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList23() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.STATUS_UP);

    // Assert
    assertTrue(actualServerList.isEmpty());
    assertSame(baseLoadBalancer.upServerList, actualServerList);
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList24() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(AbstractLoadBalancer.ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList25() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.ALL);

    // Assert
    assertTrue(actualServerList.isEmpty());
    assertSame(baseLoadBalancer.allServerList, actualServerList);
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList26() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList27() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList28() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(AbstractLoadBalancer.ServerGroup.STATUS_UP);

    // Assert
    assertTrue(actualServerList.isEmpty());
    assertSame(baseLoadBalancer.upServerList, actualServerList);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList29() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList30() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(false).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  public void testGetServerList31() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertTrue((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getServerList(true).isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#cancelPingTask()}
   */
  @Test
  public void testCancelPingTask() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(false);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server(""));

    // Act
    baseLoadBalancer.cancelPingTask();

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#cancelPingTask()}
   */
  @Test
  public void testCancelPingTask2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RetryRule(new AvailabilityFilteringRule()));
    baseLoadBalancer.addServer(new Server(""));

    // Act
    baseLoadBalancer.cancelPingTask();

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  public void testGetReachableServers() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getReachableServers().isEmpty());
    assertTrue((new BaseLoadBalancer()).getReachableServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  public void testGetReachableServers2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertTrue((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getReachableServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  public void testGetReachableServers3() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertTrue((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getReachableServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  public void testGetReachableServers4() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setPing(mock(IPing.class));

    // Act and Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  public void testGetReachableServers5() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setLoadBalancerStats(new LoadBalancerStats());
    baseLoadBalancer.setPing(mock(IPing.class));

    // Act and Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  public void testGetReachableServers6() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new BestAvailableRule());
    baseLoadBalancer.setLoadBalancerStats(new LoadBalancerStats());
    baseLoadBalancer.setPing(mock(IPing.class));

    // Act and Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getAllServers().isEmpty());
    assertTrue((new BaseLoadBalancer()).getAllServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertTrue((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getAllServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RoundRobinRule(new BaseLoadBalancer()));
    baseLoadBalancer.addServers(new Object[]{"com.netflix.loadbalancer.BaseLoadBalancer"});

    // Act
    List<Server> actualAllServers = baseLoadBalancer.getAllServers();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(baseLoadBalancer.allServerList, actualAllServers);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new WeightedResponseTimeRule());
    baseLoadBalancer.addServers(new Object[]{"com.netflix.loadbalancer.BaseLoadBalancer"});

    // Act
    List<Server> actualAllServers = baseLoadBalancer.getAllServers();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(baseLoadBalancer.allServerList, actualAllServers);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setMaxTotalPingTime(3);
    baseLoadBalancer.addServers(new Object[]{"com.netflix.loadbalancer.BaseLoadBalancer"});

    // Act
    List<Server> actualAllServers = baseLoadBalancer.getAllServers();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(baseLoadBalancer.allServerList, actualAllServers);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.chooseServer("Key");
    baseLoadBalancer.addServers(new Object[]{"com.netflix.loadbalancer.BaseLoadBalancer"});

    // Act
    List<Server> actualAllServers = baseLoadBalancer.getAllServers();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(baseLoadBalancer.allServerList, actualAllServers);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers7() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServers(new Object[]{"com.netflix.loadbalancer.BaseLoadBalancer"});

    // Act
    List<Server> actualAllServers = baseLoadBalancer.getAllServers();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(baseLoadBalancer.allServerList, actualAllServers);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers8() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, null);
    baseLoadBalancer.addServers(new Object[]{"com.netflix.loadbalancer.BaseLoadBalancer"});

    // Act
    List<Server> actualAllServers = baseLoadBalancer.getAllServers();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(baseLoadBalancer.allServerList, actualAllServers);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers9() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertTrue((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getAllServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers10() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act and Assert
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers11() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act and Assert
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers12() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act and Assert
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  public void testGetAllServers13() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServers(new ArrayList<>());
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act and Assert
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).chooseServer("Key"));
    assertNull((new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig())).chooseServer("Key"));
    assertNull((new BaseLoadBalancer()).chooseServer("Key"));
    assertNull((new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig())).chooseServer("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertNull((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).chooseServer("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer3() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Act and Assert
    assertNull(zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.chooseServer("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    AvailabilityFilteringRule rule = mock(AvailabilityFilteringRule.class);
    when(rule.getLoadBalancer()).thenReturn(new BaseLoadBalancer());
    Server server = new Server("42");
    when(rule.choose(Mockito.<Object>any())).thenReturn(server);
    doNothing().when(rule).setLoadBalancer(Mockito.<ILoadBalancer>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, rule);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    Server actualChooseServerResult = baseLoadBalancer.chooseServer("Key");

    // Assert
    verify(rule).getLoadBalancer();
    verify(rule).choose(isA(Object.class));
    verify(rule).setLoadBalancer(isA(ILoadBalancer.class));
    verify(ping).isAlive(isA(Server.class));
    assertSame(server, actualChooseServerResult);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer6() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertNull((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).chooseServer("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer7() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Act and Assert
    assertNull(zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer8() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.chooseServer("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer9() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RandomRule());
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act
    Server actualChooseServerResult = baseLoadBalancer.chooseServer("Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertSame(newServer, actualChooseServerResult);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer10() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new ResponseTimeWeightedRule());
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act
    Server actualChooseServerResult = baseLoadBalancer.chooseServer("Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertSame(newServer, actualChooseServerResult);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  public void testChoose() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).choose("Key"));
    assertNull((new BaseLoadBalancer()).choose("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  public void testChoose2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());

    // Act and Assert
    assertNull(baseLoadBalancer.choose("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  public void testChoose3() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertNull((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).choose("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  public void testChoose4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertEquals("42:80", baseLoadBalancer.choose("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  public void testChoose5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new ResponseTimeWeightedRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    String actualChooseResult = baseLoadBalancer.choose("Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals("42:80", actualChooseResult);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  public void testChoose6() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());

    // Act and Assert
    assertNull(baseLoadBalancer.choose("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  public void testChoose7() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act and Assert
    assertNull(baseLoadBalancer.choose("Key"));
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  public void testChoose8() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertEquals("42:80", baseLoadBalancer.choose("Key"));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  public void testChoose9() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new ResponseTimeWeightedRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    String actualChooseResult = baseLoadBalancer.choose("Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals("42:80", actualChooseResult);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  public void testMarkServerDown() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server server = new Server("42");

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert that nothing has changed
    assertFalse(server.isAlive());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  public void testMarkServerDown2() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    Server server = new Server("42");

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert that nothing has changed
    assertFalse(server.isAlive());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  public void testMarkServerDown3() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("Id"));
    Server server = new Server("42");

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert that nothing has changed
    assertFalse(server.isAlive());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  public void testMarkServerDown4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    Server server = new Server("42");

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert that nothing has changed
    verify(ping).isAlive(isA(Server.class));
    assertFalse(server.isAlive());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  public void testMarkServerDown5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertFalse(server.isAlive());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  public void testMarkServerDown6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException("foo"));
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    assertFalse(server.isAlive());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  public void testMarkServerDown7() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException("foo"));
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  markServerDown called on [{}]")).when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    assertFalse(server.isAlive());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  public void testMarkServerDown8() {
    // Arrange
    new IllegalArgumentException("foo");
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  markServerDown called on [{}]")).when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServers(new ArrayList<>());
    zoneAwareLoadBalancer.addServerListChangeListener(listener3);
    zoneAwareLoadBalancer.addServerStatusChangeListener(listener2);
    zoneAwareLoadBalancer.addServerStatusChangeListener(listener);
    zoneAwareLoadBalancer.addServer(new Server("42"));

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    zoneAwareLoadBalancer.markServerDown(server);

    // Assert
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    assertFalse(server.isAlive());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown9() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown10() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown11() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown12() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown13() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown14() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown15() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener4 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener4).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener4);
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener4).serverStatusChanged(isA(Collection.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown16() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener4 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener4).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerListChangeListener listener5 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener5)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerListChangeListener(listener5);
    baseLoadBalancer.addServerStatusChangeListener(listener4);
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener5).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener4).serverStatusChanged(isA(Collection.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown17() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener4 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener4).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerListChangeListener listener5 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener5)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    baseLoadBalancer.addServerListChangeListener(listener5);
    baseLoadBalancer.addServerStatusChangeListener(listener4);
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener5).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener4).serverStatusChanged(isA(Collection.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown18() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener4 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener4).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerListChangeListener listener5 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener5)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener6 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("Exception while pinging Server: '{}'")).when(listener6)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener6);
    baseLoadBalancer.addServerListChangeListener(listener5);
    baseLoadBalancer.addServerStatusChangeListener(listener4);
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener5).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener6).serverStatusChanged(isA(Collection.class));
    verify(listener4).serverStatusChanged(isA(Collection.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown19() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener4 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener4)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener4);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("http://");

    // Assert
    verify(listener4).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown20() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener4 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener4)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener4);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("https://");

    // Assert
    verify(listener4).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown21() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener4 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener4)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener4);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("/");

    // Assert
    verify(listener4).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown22() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener4 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener4).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerListChangeListener listener5 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener5)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener6 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("Exception while pinging Server: '{}'")).when(listener6)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener6);
    baseLoadBalancer.addServerListChangeListener(listener5);
    baseLoadBalancer.addServerStatusChangeListener(listener4);
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42:80");

    // Assert
    verify(listener5).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener6).serverStatusChanged(isA(Collection.class));
    verify(listener4).serverStatusChanged(isA(Collection.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  public void testMarkServerDown23() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener4 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener4)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener4);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown((String) null);

    // Assert that nothing has changed
    verify(listener4).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  public void testForceQuickPing() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  public void testForceQuickPing2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  public void testForceQuickPing3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  public void testInit() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RandomRule());
    baseLoadBalancer.addServers(new Object[]{"NFLoadBalancer-PingTimer-"});

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  public void testInit2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException(" ")).when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RoundRobinRule());
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServers(new Object[]{"NFLoadBalancer-PingTimer-"});

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  public void testInit3() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.init();

    // Assert
    assertNull(baseLoadBalancer.getPrimeConnections());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  public void testInit4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setEnablePrimingConnections(true);

    // Act
    baseLoadBalancer.init();

    // Assert
    assertNull(baseLoadBalancer.getPrimeConnections());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  public void testInit5() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");

    baseLoadBalancer.setPrimeConnections(primeConnections);
    baseLoadBalancer.setEnablePrimingConnections(true);

    // Act
    baseLoadBalancer.init();

    // Assert
    PrimeConnections primeConnections2 = baseLoadBalancer.getPrimeConnections();
    assertNull(primeConnections2.getEndStats());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertSame(primeConnections, primeConnections2);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  public void testInit6() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");

    baseLoadBalancer.setPrimeConnections(primeConnections);
    baseLoadBalancer.setEnablePrimingConnections(true);

    // Act
    baseLoadBalancer.init();

    // Assert
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
    assertSame(primeConnections, baseLoadBalancer.getPrimeConnections());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BaseLoadBalancer#setEnablePrimingConnections(boolean)}
   *   <li>{@link BaseLoadBalancer#setLoadBalancerStats(LoadBalancerStats)}
   *   <li>{@link BaseLoadBalancer#setPrimeConnections(PrimeConnections)}
   *   <li>{@link BaseLoadBalancer#toString()}
   *   <li>{@link BaseLoadBalancer#getClientConfig()}
   *   <li>{@link BaseLoadBalancer#getLoadBalancerStats()}
   *   <li>{@link BaseLoadBalancer#getMaxTotalPingTime()}
   *   <li>{@link BaseLoadBalancer#getName()}
   *   <li>{@link BaseLoadBalancer#getPing()}
   *   <li>{@link BaseLoadBalancer#getPingInterval()}
   *   <li>{@link BaseLoadBalancer#getPrimeConnections()}
   *   <li>{@link BaseLoadBalancer#getRule()}
   *   <li>{@link BaseLoadBalancer#isEnablePrimingConnections()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setEnablePrimingConnections(true);
    LoadBalancerStats lbStats = new LoadBalancerStats();
    baseLoadBalancer.setLoadBalancerStats(lbStats);
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    baseLoadBalancer.setPrimeConnections(primeConnections);
    String actualToStringResult = baseLoadBalancer.toString();
    baseLoadBalancer.getClientConfig();
    LoadBalancerStats actualLoadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    int actualMaxTotalPingTime = baseLoadBalancer.getMaxTotalPingTime();
    String actualName = baseLoadBalancer.getName();
    baseLoadBalancer.getPing();
    int actualPingInterval = baseLoadBalancer.getPingInterval();
    PrimeConnections actualPrimeConnections = baseLoadBalancer.getPrimeConnections();
    IRule actualRule = baseLoadBalancer.getRule();

    // Assert that nothing has changed
    assertTrue(actualRule instanceof RoundRobinRule);
    assertEquals("default", actualName);
    assertEquals("{NFLoadBalancer:name=default,current list of Servers=[],Load balancer stats=Zone stats: {},Server"
        + " stats: []}", actualToStringResult);
    assertEquals(10, actualPingInterval);
    assertEquals(5, actualMaxTotalPingTime);
    assertTrue(baseLoadBalancer.isEnablePrimingConnections());
    assertSame(primeConnections, actualPrimeConnections);
    assertSame(lbStats, actualLoadBalancerStats);
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer()}
   */
  @Test
  public void testNewBaseLoadBalancer() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer();

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, config.getRefreshCount());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer3() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Dr Jane Doe", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("Dr Jane Doe", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, config.getRefreshCount());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer4() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, config.getRefreshCount());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer5() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Mr John Smith", " ");

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Mr John Smith", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("Mr John Smith", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, config.getRefreshCount());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer6() {
    // Arrange, Act and Assert
    thrown.expect(RuntimeException.class);
    new BaseLoadBalancer(DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " "));
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer7() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "key cannot be null");

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Dr Jane Doe", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("Dr Jane Doe", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, config.getRefreshCount());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  public void testNewBaseLoadBalancer8() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "key cannot be null");

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, config.getRefreshCount());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer9() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer10() {
    // Arrange
    DefaultClientConfigImpl config = new DefaultClientConfigImpl();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer11() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Dr Jane Doe", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("Dr Jane Doe", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer12() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DummyPing ping = new DummyPing();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    IPing ping2 = actualBaseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
    assertSame(ping, ping2);
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer13() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " ");

    // Act and Assert
    thrown.expect(RuntimeException.class);

    new BaseLoadBalancer(config, new AvailabilityFilteringRule(), mock(IPing.class));

  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer14() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals("", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer15() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("LoadBalancer [{}]: maxTotalPingTime set to {}", " ");
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("LoadBalancer [{}]: maxTotalPingTime set to {}", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("LoadBalancer [{}]: maxTotalPingTime set to {}", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBaseLoadBalancer.getPingInterval());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(7L, config.getRefreshCount());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer16() {
    // Arrange
    IPing ping = mock(IPing.class);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer17() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(null, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer18() {
    // Arrange
    IPing ping = mock(IPing.class);
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer19() {
    // Arrange
    IPing ping = mock(IPing.class);
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertEquals("default", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer20() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    rule.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(null, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer21() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(null, rule);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertEquals("default", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  public void testNewBaseLoadBalancer22() {
    // Arrange
    DummyPing ping = new DummyPing();
    ClientConfigEnabledRoundRobinRule rule = new ClientConfigEnabledRoundRobinRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    IPing ping2 = actualBaseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(((DummyPing) ping2).getLoadBalancer());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, ping2);
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer23() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer24() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(" ", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals(" ", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer25() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", null, lbStats);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer26() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer27() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertEquals("Lb Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer28() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(",", null, lbStats);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals(",", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer29() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("default", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  public void testNewBaseLoadBalancer30() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server("Id"));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer31() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer32() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(
        "com.netflix.loadbalancer.IPing$MockitoMock$1072947407", rule, stats, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$1072947407", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer33() {
    // Arrange
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", null, stats, ping);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer34() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.incrementZoneCounter(new Server("Id"));
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer35() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats stats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, null);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer36() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertEquals("Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer37() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("name", rule, stats, ping);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Method under test:
   * {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  public void testNewBaseLoadBalancer38() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    LoadBalancerStats stats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, null);

    // Assert
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBaseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBaseLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertEquals("Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertFalse(actualBaseLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }
}
