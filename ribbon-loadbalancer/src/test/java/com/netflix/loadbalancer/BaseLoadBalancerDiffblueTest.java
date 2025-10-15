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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.IClientConfigAware;
import com.netflix.client.IClientConfigAware.Factory;
import com.netflix.client.PrimeConnections;
import com.netflix.client.PrimeConnections.PrimeConnectionEndStats;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.loadbalancer.AbstractLoadBalancer.ServerGroup;
import com.netflix.loadbalancer.BaseLoadBalancer.PingTask;
import com.netflix.loadbalancer.BaseLoadBalancer.Pinger;
import com.netflix.loadbalancer.SimpleRoundRobinWithRetryLBTest.PingFake;
import com.netflix.util.concurrent.ShutdownEnabledTimer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class BaseLoadBalancerDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>()"})
  public void testNewBaseLoadBalancer() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer();

    // Assert
    assertTrue(actualBaseLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualBaseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig)"})
  public void testNewBaseLoadBalancer2() {
    // Arrange
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "[{}] get global property '{}' with default '{}'", " ");

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IClientConfig clientConfig = actualBaseLoadBalancer.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertEquals("[{}] get global property '{}' with default '{}'", clientConfig.getClientName());
    assertEquals(
        "[{}] get global property '{}' with default '{}'", actualBaseLoadBalancer.getName());
    assertEquals(
        "[{}] get global property '{}' with default '{}'",
        actualBaseLoadBalancer.getLoadBalancerStats().getName());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(
        actualBaseLoadBalancer,
        ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, clientConfig);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig, IRule, IPing)"})
  public void testNewBaseLoadBalancer3() {
    // Arrange
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "LoadBalancer [{}]: maxTotalPingTime set to {}", " ");

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(config, new AvailabilityFilteringRule(), mock(IPing.class));

    // Assert
    IClientConfig clientConfig = actualBaseLoadBalancer.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals("LoadBalancer [{}]: maxTotalPingTime set to {}", clientConfig.getClientName());
    assertEquals("LoadBalancer [{}]: maxTotalPingTime set to {}", actualBaseLoadBalancer.getName());
    assertEquals(
        "LoadBalancer [{}]: maxTotalPingTime set to {}",
        actualBaseLoadBalancer.getLoadBalancerStats().getName());
    assertSame(
        actualBaseLoadBalancer,
        ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, clientConfig);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer4() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServers(new Object[] {""});

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer5() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb2 = new BaseLoadBalancer(config);
    lb2.addServer(new Server(Server.UNKNOWN_ZONE));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer6() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(listener2);
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server(Server.UNKNOWN_ZONE));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setWeights(new ArrayList<>());
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer7() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(listener2);
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server(Server.UNKNOWN_ZONE));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.initWithNiwsConfig(
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build());
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer8() {
    // Arrange
    IPing ping = mock(IPing.class);
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.addServer(new Server("42"));
    lb.chooseServer("Key");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(listener2);
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server(Server.UNKNOWN_ZONE));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer9() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(
            "com.netflix.loadbalancer.IPing$MockitoMock$210192632",
            rule,
            new LoadBalancerStats(),
            mock(IPing.class));

    // Assert
    assertEquals(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenBaseLoadBalancer() {
    // Arrange
    IPing ping = mock(IPing.class);

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.initialize(new BaseLoadBalancer());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenBaseLoadBalancer2() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(new BaseLoadBalancer());
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_givenBaseLoadBalancer3() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.initialize(new BaseLoadBalancer());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    assertEquals("Name", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenBaseLoadBalancerAddServerServerWithIdIs42() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("42"));
    lb.chooseServer("Key");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(listener2);
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server(Server.UNKNOWN_ZONE));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenBaseLoadBalancerAddServerServerWithIdIs422() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServer(new Server("42"));
    lb2.addServerListChangeListener(listener2);
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server(Server.UNKNOWN_ZONE));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with id is {@link Server#UNKNOWN_ZONE}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenBaseLoadBalancerAddServerServerWithIdIsUnknown_zone() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServer(new Server(Server.UNKNOWN_ZONE));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} chooseServer {@code Key}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenBaseLoadBalancerChooseServerKey() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} PingInterval is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenBaseLoadBalancerPingIntervalIsFortyTwo() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPingInterval(42);
    lb.chooseServer("Key");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(listener2);
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server(Server.UNKNOWN_ZONE));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Rule is {@link
   *       AvailabilityFilteringRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenBaseLoadBalancerRuleIsAvailabilityFilteringRule() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setRule(new AvailabilityFilteringRule());
    lb.chooseServer("Key");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(listener2);
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server(Server.UNKNOWN_ZONE));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing,
   * IPingStrategy)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"
  })
  public void testNewBaseLoadBalancer_givenHashMap() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.updateZoneServerMapping(new HashMap<>());
    IPing ping = mock(IPing.class);

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenThrow(new RuntimeException());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}.
   *
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException()}.
   *   <li>When {@link AvailabilityFilteringRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule, IPingStrategy)"})
  public void testNewBaseLoadBalancer_givenRuntimeException_whenAvailabilityFilteringRule() {
    // Arrange
    IPing ping = mock(IPing.class);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenThrow(new RuntimeException());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}.
   *
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig, IRule, IPing)"})
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, mock(IPing.class));

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero2() {
    // Arrange
    IPing ping = mock(IPing.class);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   *
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero3() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Lb Name", rule, new LoadBalancerStats());

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero4() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Then calls {@link ServerListChangeListener#serverListChanged(List, List)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_thenCallsServerListChanged() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server(Server.UNKNOWN_ZONE));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb2);
    rule.initialize(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}.
   *
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link
   *       ResponseTimeWeightedRule#name} is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig, IRule, IPing)"})
  public void testNewBaseLoadBalancer_thenResponseTimeWeightedRuleNameIsEmptyString() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, mock(IPing.class));

    // Assert
    assertEquals("", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   *
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link
   *       ResponseTimeWeightedRule#name} is {@code Lb Name}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_thenResponseTimeWeightedRuleNameIsLbName() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Lb Name", rule, new LoadBalancerStats());

    // Assert
    assertEquals("Lb Name", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing,
   * IPingStrategy)}.
   *
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link
   *       ResponseTimeWeightedRule#name} is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"
  })
  public void testNewBaseLoadBalancer_thenResponseTimeWeightedRuleNameIsName() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenThrow(new IllegalArgumentException());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    assertEquals("Name", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}.
   *
   * <ul>
   *   <li>Then return ClientConfig ClientName is {@code Dr Jane Doe}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig)"})
  public void testNewBaseLoadBalancer_thenReturnClientConfigClientNameIsDrJaneDoe() {
    // Arrange
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IClientConfig clientConfig = actualBaseLoadBalancer.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertEquals("Dr Jane Doe", clientConfig.getClientName());
    assertEquals("Dr Jane Doe", actualBaseLoadBalancer.getName());
    assertEquals("Dr Jane Doe", actualBaseLoadBalancer.getLoadBalancerStats().getName());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(
        actualBaseLoadBalancer,
        ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, clientConfig);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}.
   *
   * <ul>
   *   <li>Then return ClientConfig ClientName is {@code Dr Jane Doe}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig, IRule, IPing)"})
  public void testNewBaseLoadBalancer_thenReturnClientConfigClientNameIsDrJaneDoe2() {
    // Arrange
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(config, new AvailabilityFilteringRule(), mock(IPing.class));

    // Assert
    IClientConfig clientConfig = actualBaseLoadBalancer.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals("Dr Jane Doe", clientConfig.getClientName());
    assertEquals("Dr Jane Doe", actualBaseLoadBalancer.getName());
    assertEquals("Dr Jane Doe", actualBaseLoadBalancer.getLoadBalancerStats().getName());
    assertSame(
        actualBaseLoadBalancer,
        ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, clientConfig);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}.
   *
   * <ul>
   *   <li>Then return Rule is {@link ClientConfigEnabledRoundRobinRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig, IRule, IPing)"})
  public void testNewBaseLoadBalancer_thenReturnRuleIsClientConfigEnabledRoundRobinRule() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    ClientConfigEnabledRoundRobinRule rule = new ClientConfigEnabledRoundRobinRule();
    PingFake ping = mock(PingFake.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>Then Rule return {@link AvailabilityFilteringRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenRuleReturnAvailabilityFilteringRule() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), null);

    // Assert
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof AvailabilityFilteringRule);
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertSame(
        actualBaseLoadBalancer,
        ((AvailabilityFilteringRule) rule2).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig)"})
  public void testNewBaseLoadBalancer_thenThrowRuntimeException() {
    // Arrange
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " ");

    // Act and Assert
    thrown.expect(RuntimeException.class);
    new BaseLoadBalancer(config);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig, IRule, IPing)"})
  public void testNewBaseLoadBalancer_thenThrowRuntimeException2() {
    // Arrange
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " ");

    // Act and Assert
    thrown.expect(RuntimeException.class);
    new BaseLoadBalancer(config, new AvailabilityFilteringRule(), mock(IPing.class));
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@link AvailabilityFilteringRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule, IPingStrategy)"})
  public void testNewBaseLoadBalancer_whenAvailabilityFilteringRule() {
    // Arrange
    IPing ping = mock(IPing.class);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}.
   *
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig, IRule, IPing)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, mock(IPing.class));

    // Assert
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule2() {
    // Arrange
    IPing ping = mock(IPing.class);
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule, IPingStrategy)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule3() {
    // Arrange
    IPing ping = mock(IPing.class);
    BestAvailableRule rule = new BestAvailableRule();

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   *
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule4() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Lb Name", rule, new LoadBalancerStats());

    // Assert
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule5() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}.
   *
   * <ul>
   *   <li>When {@link DummyPing} (default constructor).
   *   <li>Then Ping return {@link DummyPing}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig, IRule, IPing)"})
  public void testNewBaseLoadBalancer_whenDummyPing_thenPingReturnDummyPing() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DummyPing ping = new DummyPing();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config, rule, ping);

    // Assert
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof AvailabilityFilteringRule);
    IPing ping2 = actualBaseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
    assertSame(
        actualBaseLoadBalancer,
        ((AvailabilityFilteringRule) rule2).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
    assertSame(ping, ping2);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then return Name is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IClientConfig)"})
  public void testNewBaseLoadBalancer_whenEmptyConfig_thenReturnNameIsEmptyString() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(config);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    IPing ping = actualBaseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertEquals("", actualBaseLoadBalancer.getName());
    assertEquals("", actualBaseLoadBalancer.getLoadBalancerStats().getName());
    assertSame(actualBaseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(
        actualBaseLoadBalancer,
        ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(config, actualBaseLoadBalancer.getClientConfig());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@link IPingStrategy}.
   *   <li>Then return Ping is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule, IPingStrategy)"})
  public void testNewBaseLoadBalancer_whenIPingStrategy_thenReturnPingIsNull() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(null, null, mock(IPingStrategy.class));

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, null);

    // Assert
    assertTrue(actualBaseLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(actualBaseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualBaseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule2() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", null, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualBaseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule3() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", null, new LoadBalancerStats(), ping);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing,
   * IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"
  })
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule4() {
    // Arrange
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, true});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", null, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    assertTrue(actualBaseLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(actualBaseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualBaseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>When {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenResponseTimeWeightedRule() {
    // Arrange
    IPing ping = mock(IPing.class);
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>When {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenResponseTimeWeightedRule2() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    assertEquals("Name", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>When {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   *   <li>Then return Ping is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenResponseTimeWeightedRule_thenReturnPingIsNull() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), null);

    // Assert
    assertEquals("Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)} with {@code
   * clientConfig}, {@code rule}, {@code ping}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing)"})
  public void testInitWithConfigWithClientConfigRulePing() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, mock(IPing.class));

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertEquals(0, rule.getAvailableServersCount());
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(((BaseLoadBalancer) loadBalancer).allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)} with {@code
   * clientConfig}, {@code rule}, {@code ping}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing)"})
  public void testInitWithConfigWithClientConfigRulePing2() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);
    baseLoadBalancer.addServer(new Server("42"));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> serverList = ((BaseLoadBalancer) loadBalancer).allServerList;
    assertEquals(1, serverList.size());
    Server getResult = allServers.get(0);
    assertFalse(getResult.isAlive());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(getResult, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)} with {@code
   * clientConfig}, {@code rule}, {@code ping}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing)"})
  public void testInitWithConfigWithClientConfigRulePing3() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> serverList = ((BaseLoadBalancer) loadBalancer).allServerList;
    assertEquals(1, serverList.size());
    Server getResult = allServers.get(0);
    assertFalse(getResult.isAlive());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(getResult, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)} with {@code
   * clientConfig}, {@code rule}, {@code ping}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing)"})
  public void testInitWithConfigWithClientConfigRulePing4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);
    baseLoadBalancer.addServers(new Object[] {"New Servers"});
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping2);

    // Assert
    verify(ping2, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("New Servers", getResult.getHost());
    assertEquals("New Servers:80", getResult.getHostPort());
    assertEquals("New Servers:80", getResult.getId());
    List<Server> serverList = ((BaseLoadBalancer) loadBalancer).allServerList;
    assertEquals(2, serverList.size());
    assertSame(newServer, allServers.get(1));
    assertSame(newServer, serverList.get(1));
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   * with {@code clientConfig}, {@code rule}, {@code ping}, {@code stats}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)"
  })
  public void testInitWithConfigWithClientConfigRulePingStats() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping, new LoadBalancerStats());

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(loadBalancer.getReachableServers().isEmpty());
    assertTrue(((BaseLoadBalancer) loadBalancer).allServerList.isEmpty());
    assertTrue(((BaseLoadBalancer) loadBalancer).upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   * with {@code clientConfig}, {@code rule}, {@code ping}, {@code stats}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)"
  })
  public void testInitWithConfigWithClientConfigRulePingStats2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping, new LoadBalancerStats());

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertEquals(0, rule.getAvailableServersCount());
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(((BaseLoadBalancer) loadBalancer).allServerList.isEmpty());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
    assertSame(clientConfig, ((BaseLoadBalancer) loadBalancer).getClientConfig());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   * with {@code clientConfig}, {@code rule}, {@code ping}, {@code stats}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)"
  })
  public void testInitWithConfigWithClientConfigRulePingStats3() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(mock(IPing.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping, new LoadBalancerStats());

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertEquals(0, rule.getAvailableServersCount());
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(loadBalancer.getReachableServers().isEmpty());
    assertTrue(((BaseLoadBalancer) loadBalancer).allServerList.isEmpty());
    assertTrue(((BaseLoadBalancer) loadBalancer).upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   * with {@code clientConfig}, {@code rule}, {@code ping}, {@code stats}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)"
  })
  public void testInitWithConfigWithClientConfigRulePingStats4() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping, new LoadBalancerStats());

    // Assert
    assertEquals("", baseLoadBalancer.getName());
    assertSame(baseLoadBalancer, rule.getLoadBalancer());
    assertSame(baseLoadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   * with {@code clientConfig}, {@code rule}, {@code ping}, {@code stats}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)"
  })
  public void testInitWithConfigWithClientConfigRulePingStats5() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping, new LoadBalancerStats());

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   * with {@code clientConfig}, {@code rule}, {@code ping}, {@code stats}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)"
  })
  public void testInitWithConfigWithClientConfigRulePingStats6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping2, null);

    // Assert
    verify(ping2).isAlive(isA(Server.class));
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertNull(baseLoadBalancer.getLoadBalancerStats());
    assertNull(((BaseLoadBalancer) loadBalancer).getLoadBalancerStats());
    assertEquals(1, rule.getAvailableServersCount());
    List<Server> expectedReachableServers = baseLoadBalancer.allServerList;
    assertEquals(expectedReachableServers, baseLoadBalancer.getReachableServers());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   * with {@code clientConfig}, {@code rule}, {@code ping}, {@code stats}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)"
  })
  public void testInitWithConfigWithClientConfigRulePingStats7() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    WeightedResponseTimeRule rule = new WeightedResponseTimeRule();

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping2, new LoadBalancerStats());

    // Assert
    verify(ping2).isAlive(isA(Server.class));
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertEquals(1, loadBalancer.getReachableServers().size());
    List<Server> serverList = ((BaseLoadBalancer) loadBalancer).allServerList;
    assertEquals(1, serverList.size());
    assertEquals(serverList, ((BaseLoadBalancer) loadBalancer).upServerList);
    assertSame(rule, baseLoadBalancer.getRule());
    assertSame(rule, ((BaseLoadBalancer) loadBalancer).getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)}
   * with {@code clientConfig}, {@code rule}, {@code ping}, {@code stats}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing, LoadBalancerStats)"
  })
  public void testInitWithConfigWithClientConfigRulePingStats8() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());
    baseLoadBalancer.addServerStatusChangeListener(listener2);

    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener3).serverStatusChanged(Mockito.<Collection<Server>>any());
    baseLoadBalancer.addServerStatusChangeListener(listener3);

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    rule.setLoadBalancer(new BaseLoadBalancer());

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping2, new LoadBalancerStats());

    // Assert that nothing has changed
    verify(ping2).isAlive(isA(Server.class));
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener3).serverStatusChanged(isA(Collection.class));
    assertSame(baseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)} with {@code
   * clientConfig}, {@code rule}, {@code ping}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithConfig(IClientConfig, IRule, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithConfig(IClientConfig, IRule, IPing)"})
  public void testInitWithConfigWithClientConfigRulePing_givenIPingIsAliveReturnTrue() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    // Act
    baseLoadBalancer.initWithConfig(clientConfig, rule, ping2);

    // Assert
    verify(ping2).isAlive(isA(Server.class));
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener, atLeast(1)).serverStatusChanged(isA(Collection.class));
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> serverList = ((BaseLoadBalancer) loadBalancer).allServerList;
    assertEquals(1, serverList.size());
    Server getResult = allServers.get(0);
    assertFalse(getResult.isAlive());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(getResult, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)} with {@code clientConfig}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = baseLoadBalancer.getPing();
    assertTrue(ping instanceof DummyPing);
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(
        baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)} with {@code clientConfig}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping2 = baseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
    assertSame(
        baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)} with {@code clientConfig}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
    assertEquals("Dr Jane Doe", baseLoadBalancer.getLoadBalancerStats().getName());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals("Dr Jane Doe", baseLoadBalancer.getName());
    IPing ping2 = baseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertSame(baseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertSame(
        baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)} with {@code clientConfig}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig4() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setEnablePrimingConnections(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping2 = baseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
    assertSame(
        baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)} with {@code clientConfig}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    DefaultClientConfigImpl clientConfig = new DefaultClientConfigImpl();

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    assertTrue(baseLoadBalancer.getRule() instanceof AvailabilityFilteringRule);
    IPing ping2 = baseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertEquals("", baseLoadBalancer.getName());
    assertEquals("", baseLoadBalancer.getLoadBalancerStats().getName());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
    assertSame(baseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig, Factory)} with {@code
   * clientConfig}, {@code factory}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig,
   * IClientConfigAware.Factory)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.initWithNiwsConfig(IClientConfig, IClientConfigAware.Factory)"
  })
  public void testInitWithNiwsConfigWithClientConfigFactory_thenThrowRuntimeException()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    Factory factory = mock(Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new RuntimeException());

    // Act and Assert
    thrown.expect(RuntimeException.class);
    baseLoadBalancer.initWithNiwsConfig(clientConfig, factory);
    verify(factory)
        .create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
  }

  /**
   * Test {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)} with {@code clientConfig}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig_givenBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals("", baseLoadBalancer.getName());
    assertEquals("", baseLoadBalancer.getLoadBalancerStats().getName());
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)} with {@code clientConfig}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig_givenIPingIsAliveThrowRuntimeException() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping2 = baseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
    assertSame(
        baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)} with {@code clientConfig}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Name is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig_thenBaseLoadBalancerNameIsEmptyString() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(mock(IPing.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    baseLoadBalancer.initWithNiwsConfig(clientConfig);

    // Assert
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals("", baseLoadBalancer.getName());
    assertEquals("", baseLoadBalancer.getLoadBalancerStats().getName());
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertSame(clientConfig, baseLoadBalancer.getClientConfig());
  }

  /**
   * Test {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)} with {@code clientConfig}.
   *
   * <ul>
   *   <li>Then calls {@link ServerListChangeListener#serverListChanged(List, List)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig_thenCallsServerListChanged() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping2 = baseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(2, baseLoadBalancer.getMaxTotalPingTime());
    assertEquals(30, baseLoadBalancer.getPingInterval());
    assertSame(baseLoadBalancer, ((DummyPing) ping2).getLoadBalancer());
    assertSame(
        baseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
  }

  /**
   * Test PingTask {@link PingTask#run()}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} throw {@link
   *       RuntimeException#RuntimeException()}.
   *   <li>Then calls {@link IPing#isAlive(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link PingTask#run()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PingTask.run()"})
  public void testPingTaskRun_givenIPingIsAliveThrowRuntimeException_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.new PingTask().run();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   *
   * <p>Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger() throws Exception {
    // Arrange
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, false});

    // Act
    new BaseLoadBalancer().new Pinger(pingerStrategy).runPinger();

    // Assert
    verify(pingerStrategy).pingServers(isNull(), isA(Server[].class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   *
   * <p>Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger2() throws Exception {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));

    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, false});

    // Act
    baseLoadBalancer.new Pinger(pingerStrategy).runPinger();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger_givenBaseLoadBalancerAddServerServerWithIdIs42()
      throws Exception {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, false});

    // Act
    baseLoadBalancer.new Pinger(pingerStrategy).runPinger();

    // Assert
    verify(pingerStrategy).pingServers(isNull(), isA(Server[].class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} addServer
   *       {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger_givenDynamicServerListLoadBalancerAddServerServerWithIdIs42()
      throws Exception {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerStatusChangeListener(listener);
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, false});

    // Act
    dynamicServerListLoadBalancer.new Pinger(pingerStrategy).runPinger();

    // Assert
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    verify(listener).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} Filter is
   *       {@link ServerListFilter}.
   * </ul>
   *
   * <p>Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger_givenDynamicServerListLoadBalancerFilterIsServerListFilter()
      throws Exception {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setFilter(mock(ServerListFilter.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(listener);
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, false});

    // Act
    dynamicServerListLoadBalancer.new Pinger(pingerStrategy).runPinger();

    // Assert
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    verify(listener).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test {@link BaseLoadBalancer#removeServerListChangeListener(ServerListChangeListener)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with {@code Id}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BaseLoadBalancer#removeServerListChangeListener(ServerListChangeListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.removeServerListChangeListener(ServerListChangeListener)"
  })
  public void testRemoveServerListChangeListener_givenBaseLoadBalancerAddServerServerWithId() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("Id"));
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.setPingInterval(42);

    // Act
    baseLoadBalancer.removeServerListChangeListener(mock(ServerListChangeListener.class));

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Test {@link BaseLoadBalancer#removeServerListChangeListener(ServerListChangeListener)}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code true}.
   *   <li>Then calls {@link IPing#isAlive(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BaseLoadBalancer#removeServerListChangeListener(ServerListChangeListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseLoadBalancer.removeServerListChangeListener(ServerListChangeListener)"
  })
  public void testRemoveServerListChangeListener_givenIPingIsAliveReturnTrue_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.setPingInterval(42);

    // Act
    baseLoadBalancer.removeServerListChangeListener(mock(ServerListChangeListener.class));

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "IClientConfig BaseLoadBalancer.getClientConfig()",
    "LoadBalancerStats BaseLoadBalancer.getLoadBalancerStats()",
    "int BaseLoadBalancer.getMaxTotalPingTime()",
    "String BaseLoadBalancer.getName()",
    "IPing BaseLoadBalancer.getPing()",
    "int BaseLoadBalancer.getPingInterval()",
    "PrimeConnections BaseLoadBalancer.getPrimeConnections()",
    "IRule BaseLoadBalancer.getRule()",
    "boolean BaseLoadBalancer.isEnablePrimingConnections()",
    "void BaseLoadBalancer.setEnablePrimingConnections(boolean)",
    "void BaseLoadBalancer.setLoadBalancerStats(LoadBalancerStats)",
    "void BaseLoadBalancer.setPrimeConnections(PrimeConnections)",
    "String BaseLoadBalancer.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setEnablePrimingConnections(true);
    LoadBalancerStats lbStats = new LoadBalancerStats();
    baseLoadBalancer.setLoadBalancerStats(lbStats);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);
    String actualToStringResult = baseLoadBalancer.toString();
    IClientConfig actualClientConfig = baseLoadBalancer.getClientConfig();
    LoadBalancerStats actualLoadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    int actualMaxTotalPingTime = baseLoadBalancer.getMaxTotalPingTime();
    String actualName = baseLoadBalancer.getName();
    IPing actualPing = baseLoadBalancer.getPing();
    int actualPingInterval = baseLoadBalancer.getPingInterval();
    PrimeConnections actualPrimeConnections = baseLoadBalancer.getPrimeConnections();
    IRule actualRule = baseLoadBalancer.getRule();

    // Assert
    assertTrue(actualRule instanceof RoundRobinRule);
    assertEquals("default", actualName);
    assertEquals(
        "{NFLoadBalancer:name=default,current list of Servers=[],Load balancer stats=Zone stats: {},Server"
            + " stats: []}",
        actualToStringResult);
    assertNull(actualPrimeConnections.getEndStats());
    assertNull(actualClientConfig);
    assertNull(actualPing);
    assertEquals(10, actualPingInterval);
    assertEquals(5, actualMaxTotalPingTime);
    assertTrue(baseLoadBalancer.isEnablePrimingConnections());
    assertSame(primeConnections, actualPrimeConnections);
    assertSame(lbStats, actualLoadBalancerStats);
  }

  /**
   * Test {@link BaseLoadBalancer#setupPingTask()}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setupPingTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setupPingTask()"})
  public void testSetupPingTask() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.setupPingTask();

    // Assert that nothing has changed
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#setupPingTask()}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setupPingTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setupPingTask()"})
  public void testSetupPingTask2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.setupPingTask();

    // Assert that nothing has changed
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_givenBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setName("Name");

    // Assert that nothing has changed
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} Alive is {@code true}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Name is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_givenServerWithIdIs42AliveIsTrue_thenBaseLoadBalancerNameIsName() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setAlive(true);

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(newServer);
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName("Name");

    // Assert
    assertEquals("Name", baseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertEquals("Name", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Name is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_thenBaseLoadBalancerNameIsName() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName("Name");

    // Assert
    assertEquals("Name", baseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertEquals("Name", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   *
   * <ul>
   *   <li>When {@code class}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Name is {@code class}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_whenClass_thenBaseLoadBalancerNameIsClass() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName("class");

    // Assert
    assertEquals("class", baseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertEquals("class", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Name is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_whenNull_thenBaseLoadBalancerNameIsNull() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName(null);

    // Assert
    assertNull(baseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertNull(loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#lockAllServerList(boolean)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#allServerLock}
   *       ReadLockCount is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#lockAllServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockAllServerList(boolean)"})
  public void testLockAllServerList_thenBaseLoadBalancerAllServerLockReadLockCountIsOne() {
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
   * Test {@link BaseLoadBalancer#lockAllServerList(boolean)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@link WriteLock}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#lockAllServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockAllServerList(boolean)"})
  public void testLockAllServerList_whenTrue_thenReturnWriteLock() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    Lock actualLockAllServerListResult = baseLoadBalancer.lockAllServerList(true);

    // Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLockAllServerListResult instanceof WriteLock);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Test {@link BaseLoadBalancer#lockUpServerList(boolean)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerLock}
   *       ReadLockCount is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockUpServerList(boolean)"})
  public void testLockUpServerList_thenBaseLoadBalancerUpServerLockReadLockCountIsOne() {
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
   * Test {@link BaseLoadBalancer#lockUpServerList(boolean)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@link WriteLock}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockUpServerList(boolean)"})
  public void testLockUpServerList_whenTrue_thenReturnWriteLock() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    Lock actualLockUpServerListResult = baseLoadBalancer.lockUpServerList(true);

    // Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.upServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLockUpServerListResult instanceof WriteLock);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Test {@link BaseLoadBalancer#setPingInterval(int)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPingInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPingInterval(int)"})
  public void testSetPingInterval() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);

    // Act
    baseLoadBalancer.setPingInterval(42);

    // Assert
    assertEquals(42, baseLoadBalancer.getPingInterval());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setPingInterval(int)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPingInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPingInterval(int)"})
  public void testSetPingInterval2() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    baseLoadBalancer.setPingInterval(42);

    // Assert
    assertEquals(42, baseLoadBalancer.getPingInterval());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setPingInterval(int)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPingInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPingInterval(int)"})
  public void testSetPingInterval3() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.setPingInterval(42);

    // Assert
    assertEquals(42, baseLoadBalancer.getPingInterval());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setPingInterval(int)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPingInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPingInterval(int)"})
  public void testSetPingInterval4() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));

    // Act
    baseLoadBalancer.setPingInterval(42);

    // Assert
    assertEquals(42, baseLoadBalancer.getPingInterval());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setPingInterval(int)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPingInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPingInterval(int)"})
  public void testSetPingInterval5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.setPingInterval(42);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(42, baseLoadBalancer.getPingInterval());
    assertTrue(allServers.get(0).isAlive());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#setPingInterval(int)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPingInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPingInterval(int)"})
  public void testSetPingInterval6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.setPingInterval(42);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(42, baseLoadBalancer.getPingInterval());
    assertFalse(allServers.get(0).isAlive());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setPingInterval(int)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} PingInterval is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPingInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPingInterval(int)"})
  public void testSetPingInterval_thenBaseLoadBalancerPingIntervalIsFortyTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setPingInterval(42);

    // Assert
    assertEquals(42, baseLoadBalancer.getPingInterval());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setPingInterval(int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} PingInterval is ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPingInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPingInterval(int)"})
  public void testSetPingInterval_whenZero_thenBaseLoadBalancerPingIntervalIsTen() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setPingInterval(0);

    // Assert that nothing has changed
    assertEquals(10, baseLoadBalancer.getPingInterval());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setMaxTotalPingTime(int)}.
   *
   * <ul>
   *   <li>When three.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} MaxTotalPingTime is three.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setMaxTotalPingTime(int)"})
  public void testSetMaxTotalPingTime_whenThree_thenBaseLoadBalancerMaxTotalPingTimeIsThree() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    assertEquals(3, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Test {@link BaseLoadBalancer#setMaxTotalPingTime(int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} MaxTotalPingTime is five.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setMaxTotalPingTime(int)"})
  public void testSetMaxTotalPingTime_whenZero_thenBaseLoadBalancerMaxTotalPingTimeIsFive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setMaxTotalPingTime(0);

    // Assert that nothing has changed
    assertEquals(5, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Test {@link BaseLoadBalancer#isPingInProgress()}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseLoadBalancer.isPingInProgress()"})
  public void testIsPingInProgress() {
    // Arrange, Act and Assert
    assertFalse(new BaseLoadBalancer().isPingInProgress());
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    IPing ping2 = mock(IPing.class);

    // Act
    baseLoadBalancer.setPing(ping2);

    // Assert
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(ping2, baseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);

    // Act
    baseLoadBalancer.setPing(ping2);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping2).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@link IPing}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Ping is {@link IPing}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_givenBaseLoadBalancer_whenIPing_thenBaseLoadBalancerPingIsIPing() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    IPing ping = mock(IPing.class);

    // Act
    baseLoadBalancer.setPing(ping);

    // Assert
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(ping, baseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   *
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException()}.
   *   <li>Then not {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_givenRuntimeException_thenNotBaseLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    // Act
    baseLoadBalancer.setPing(ping);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   *
   * <ul>
   *   <li>Given {@link ServerStatusChangeListener} {@link
   *       ServerStatusChangeListener#serverStatusChanged(Collection)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_givenServerStatusChangeListenerServerStatusChangedDoesNothing() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);

    // Act
    baseLoadBalancer.setPing(ping2);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping2).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_thenBaseLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    // Act
    baseLoadBalancer.setPing(ping);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#setRule(IRule)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setRule(IRule)"})
  public void testSetRule() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    Server newServer = new Server(null);
    baseLoadBalancer.addServer(newServer);
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    baseLoadBalancer.setRule(rule);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> serverList = ((BaseLoadBalancer) loadBalancer).allServerList;
    assertEquals(1, serverList.size());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#setRule(IRule)}.
   *
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) LoadBalancer {@link
   *       BaseLoadBalancer}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenAvailabilityFilteringRuleLoadBalancerBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    baseLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, baseLoadBalancer.getRule());
    assertSame(rule, ((BaseLoadBalancer) loadBalancer).getRule());
    assertSame(loadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#setRule(IRule)}.
   *
   * <ul>
   *   <li>Then {@link BestAvailableRule} (default constructor) LoadBalancer {@link
   *       BaseLoadBalancer}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenBestAvailableRuleLoadBalancerBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    baseLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertSame(baseLoadBalancer, rule.roundRobinRule.getLoadBalancer());
    assertSame(rule, baseLoadBalancer.getRule());
    assertSame(rule, ((BaseLoadBalancer) loadBalancer).getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#setRule(IRule)}.
   *
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} LoadBalancer AllServers
   *       first Alive.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenResponseTimeWeightedRuleLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    baseLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(serverList, loadBalancer.getReachableServers());
    assertSame(serverList, ((BaseLoadBalancer) loadBalancer).allServerList);
    assertSame(serverList, ((BaseLoadBalancer) loadBalancer).upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#setRule(IRule)}.
   *
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} LoadBalancer Ping is
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenResponseTimeWeightedRuleLoadBalancerPingIsNull() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    baseLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertNull(((BaseLoadBalancer) loadBalancer).getPing());
    assertNull(((BaseLoadBalancer) loadBalancer).lbTimer);
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(((BaseLoadBalancer) loadBalancer).allServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setRule(IRule)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Rule {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setRule(IRule)"})
  public void testSetRule_whenNull_thenBaseLoadBalancerRuleRoundRobinRule() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setRule(null);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getRule() instanceof RoundRobinRule);
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   *
   * <ul>
   *   <li>When {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_whenFalse() {
    // Arrange, Act and Assert
    assertEquals(0, new BaseLoadBalancer().getServerCount(false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   *
   * <ul>
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_whenTrue() {
    // Arrange, Act and Assert
    assertEquals(0, new BaseLoadBalancer().getServerCount(true));
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);

    // Act
    baseLoadBalancer.addServer(new Server("42"));

    // Assert
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(1, serverList.size());
    assertTrue(newServer.isAlive());
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(1, serverList.size());
    assertTrue(newServer.isAlive());
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(1, serverList.size());
    assertTrue(newServer.isAlive());
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(1, serverList.size());
    assertTrue(newServer.isAlive());
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer7() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(2, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(2, serverList.size());
    assertSame(newServer, reachableServers.get(1));
    assertSame(newServer, serverList.get(1));
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer8() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);

    // Act
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> expectedAllServers = dynamicServerListLoadBalancer.upServerList;
    assertEquals(expectedAllServers, dynamicServerListLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer9() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(3, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(3, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(3, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(3, serverList2.size());
    assertSame(newServer, allServers.get(2));
    assertSame(newServer, reachableServers.get(2));
    assertSame(newServer, serverList.get(2));
    assertSame(newServer, serverList2.get(2));
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer10() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());
    IPing ping2 = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    assertSame(newServer, allServers.get(1));
    assertSame(newServer, serverList.get(1));
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code null}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer_givenBaseLoadBalancer_whenNull_thenBaseLoadBalancerAllServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServer(null);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerList}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer_thenBaseLoadBalancerAllServersIsBaseLoadBalancerUpServerList() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServer(new Server("42"));

    // Assert
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   *
   * <ul>
   *   <li>Then not {@link Server#Server(String)} with id is {@code 42} ReadyToServe.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer_thenNotServerWithIdIs42ReadyToServe() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");

    // Act
    baseLoadBalancer.addServer(newServer);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(1, serverList.size());
    assertFalse(newServer.isReadyToServe());
    assertTrue(newServer.isAlive());
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(serverList, newServers);
    assertEquals(serverList, baseLoadBalancer.getAllServers());
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(1, baseLoadBalancer.getAllServers().size());
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals(1, baseLoadBalancer.getAllServers().size());
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals(1, baseLoadBalancer.getAllServers().size());
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    assertEquals(1, baseLoadBalancer.getAllServers().size());
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    assertEquals(1, baseLoadBalancer.getAllServers().size());
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList7() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    assertEquals(2, baseLoadBalancer.getAllServers().size());
    assertEquals(2, baseLoadBalancer.getReachableServers().size());
    assertEquals(2, baseLoadBalancer.allServerList.size());
    assertEquals(2, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList8() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(null);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList9() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    ArrayList<Server> newServers = new ArrayList<>();
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(3, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(3, serverList.size());
    assertSame(server, reachableServers.get(2));
    assertSame(server, serverList.get(2));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList10() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    assertEquals(2, baseLoadBalancer.getAllServers().size());
    assertEquals(2, baseLoadBalancer.getReachableServers().size());
    assertEquals(2, baseLoadBalancer.allServerList.size());
    assertEquals(2, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_givenIPingIsAliveThrowRuntimeException() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert that nothing has changed
    verify(ping).isAlive(isA(Server.class));
    assertEquals(1, newServers.size());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link BaseLoadBalancer#BaseLoadBalancer()} {@link
   *       BaseLoadBalancer#upServerList}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_thenArrayListIsBaseLoadBalancerUpServerList() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(serverList, newServers);
    assertEquals(serverList, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link
   *       DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link
   *       BaseLoadBalancer#upServerList}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_thenArrayListIsDynamicServerListLoadBalancerUpServerList() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.addServers(newServers);

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> serverList = dynamicServerListLoadBalancer.upServerList;
    assertEquals(serverList, newServers);
    assertEquals(serverList, dynamicServerListLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers size is two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_thenBaseLoadBalancerAllServersSizeIsTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    assertEquals(2, baseLoadBalancer.getAllServers().size());
    assertEquals(2, baseLoadBalancer.getReachableServers().size());
    assertEquals(2, baseLoadBalancer.allServerList.size());
    assertEquals(2, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <ul>
   *   <li>Then not {@link ArrayList#ArrayList()} first ReadyToServe.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_thenNotArrayListFirstReadyToServe() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    assertEquals(1, newServers.size());
    assertEquals(1, baseLoadBalancer.getAllServers().size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(1, serverList.size());
    Server getResult = newServers.get(0);
    assertFalse(getResult.isReadyToServe());
    assertSame(getResult, reachableServers.get(0));
    assertSame(getResult, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_whenArrayList_thenBaseLoadBalancerAllServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServers(new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_whenNull_thenBaseLoadBalancerAllServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServers((List<Server>) null);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);

    // Act
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("New Servers", getResult.getHost());
    assertEquals("New Servers:80", getResult.getHostPort());
    assertEquals("New Servers:80", getResult.getId());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertSame(getResult, reachableServers.get(0));
    assertSame(getResult, serverList.get(0));
    assertSame(getResult, serverList2.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("New Servers", getResult.getHost());
    assertEquals("New Servers:80", getResult.getHostPort());
    assertEquals("New Servers:80", getResult.getId());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    assertFalse(getResult.isAlive());
    assertSame(getResult, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);

    // Act
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("New Servers", getResult.getHost());
    assertEquals("New Servers:80", getResult.getHostPort());
    assertEquals("New Servers:80", getResult.getId());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertSame(getResult, reachableServers.get(0));
    assertSame(getResult, serverList.get(0));
    assertSame(getResult, serverList2.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    // Act
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("New Servers", getResult.getHost());
    assertEquals("New Servers:80", getResult.getHostPort());
    assertEquals("New Servers:80", getResult.getId());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertSame(getResult, reachableServers.get(0));
    assertSame(getResult, serverList.get(0));
    assertSame(getResult, serverList2.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    // Act
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    assertEquals(2, baseLoadBalancer.getAllServers().size());
    assertEquals(2, baseLoadBalancer.getReachableServers().size());
    assertEquals(2, baseLoadBalancer.allServerList.size());
    assertEquals(2, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject7() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);

    // Act
    dynamicServerListLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals(1, dynamicServerListLoadBalancer.getReachableServers().size());
    assertEquals(1, dynamicServerListLoadBalancer.allServerList.size());
    assertEquals(1, dynamicServerListLoadBalancer.upServerList.size());
    List<Server> expectedAllServers = dynamicServerListLoadBalancer.upServerList;
    assertEquals(expectedAllServers, dynamicServerListLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject8() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    // Act
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("New Servers", getResult.getHost());
    assertEquals("New Servers:80", getResult.getHostPort());
    assertEquals("New Servers:80", getResult.getId());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertFalse(getResult.isReadyToServe());
    assertSame(getResult, reachableServers.get(0));
    assertSame(getResult, serverList.get(0));
    assertSame(getResult, serverList2.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject9() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    // Act
    baseLoadBalancer.addServers(new Object[] {"http://"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("", getResult.getHost());
    assertEquals(":80", getResult.getHostPort());
    assertEquals(":80", getResult.getId());
    assertEquals("http", getResult.getScheme());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject10() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    // Act
    baseLoadBalancer.addServers(new Object[] {"https://"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals(":443", getResult.getHostPort());
    assertEquals(":443", getResult.getId());
    assertEquals("https", getResult.getScheme());
    assertEquals(443, getResult.getPort());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject11() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    // Act
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(3, allServers.size());
    Server getResult = allServers.get(2);
    assertEquals("New Servers", getResult.getHost());
    assertEquals("New Servers:80", getResult.getHostPort());
    assertEquals("New Servers:80", getResult.getId());
    assertNull(getResult.getScheme());
    assertEquals(80, getResult.getPort());
    assertTrue(getResult.isAlive());
    assertTrue(getResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, getResult.getZone());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} ReadyToServe is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject_givenServerWithIdIs42ReadyToServeIsTrue() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);
    baseLoadBalancer.setPing(ping2);

    Server newServer = new Server("42");
    newServer.setReadyToServe(true);
    baseLoadBalancer.addServer(newServer);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());
    baseLoadBalancer.addServerStatusChangeListener(listener2);

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener3);

    // Act
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    verify(ping2, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    assertEquals(2, baseLoadBalancer.getAllServers().size());
    assertEquals(2, baseLoadBalancer.getReachableServers().size());
    assertEquals(2, baseLoadBalancer.allServerList.size());
    assertEquals(2, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject_thenBaseLoadBalancerReachableServersSizeIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <ul>
   *   <li>When array of {@link Object} with {@code :}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject_whenArrayOfObjectWithColon() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[] {":"});

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <ul>
   *   <li>When array of {@link Object} with {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject_whenArrayOfObjectWithNull() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[] {null});

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <ul>
   *   <li>When array of {@link Object} with {@code /}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject_whenArrayOfObjectWithSlash() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);

    // Act
    baseLoadBalancer.addServers(new Object[] {"/"});

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("", getResult.getHost());
    assertEquals(":80", getResult.getHostPort());
    assertEquals(":80", getResult.getId());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(1, serverList.size());
    assertSame(getResult, reachableServers.get(0));
    assertSame(getResult, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <ul>
   *   <li>When array of {@link Object} with two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject_whenArrayOfObjectWithTwo() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[] {2});

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <ul>
   *   <li>When empty array of {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject_whenEmptyArrayOfObject() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    baseLoadBalancer.addServers(new Object[] {});

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject_whenNull() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
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
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    assertTrue(lsrv.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList2() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    baseLoadBalancer.setServersList(new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertEquals(baseLoadBalancer.allServerList, lsrv);
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertEquals(baseLoadBalancer.allServerList, lsrv);
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertEquals(baseLoadBalancer.allServerList, lsrv);
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add("42");

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    Server getResult = reachableServers.get(0);
    assertEquals("42", getResult.getHost());
    assertEquals("42:80", getResult.getHostPort());
    assertEquals("42:80", getResult.getId());
    assertNull(getResult.getScheme());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(1, serverList.size());
    assertEquals(80, getResult.getPort());
    assertFalse(allServers.get(0).isAlive());
    assertTrue(getResult.isAlive());
    assertTrue(getResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, getResult.getZone());
    assertSame(newServer, serverList.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList7() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add("42");
    lsrv.add("42");

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertTrue(allServers.get(1).isAlive());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList8() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add("42");
    lsrv.add("42");

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertTrue(allServers.get(1).isAlive());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList9() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add("42");
    lsrv.add("42");

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertTrue(allServers.get(1).isAlive());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList10() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add("42");
    lsrv.add("42");

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(2, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(2, serverList.size());
    assertSame(newServer, reachableServers.get(1));
    assertSame(newServer, serverList.get(1));
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList11() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());
    IPing ping2 = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add("42");
    lsrv.add("42");

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertEquals(2, baseLoadBalancer.allServerList.size());
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList12() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());
    IPing ping2 = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add("http://");
    lsrv.add("42");

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("", getResult.getHost());
    assertEquals(":80", getResult.getHostPort());
    assertEquals(":80", getResult.getId());
    assertEquals("http", getResult.getScheme());
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} EnablePrimingConnections is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenBaseLoadBalancerEnablePrimingConnectionsIsTrue() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setEnablePrimingConnections(true);
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    assertTrue(lsrv.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenBaseLoadBalancer_whenArrayList_thenArrayListEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    assertTrue(lsrv.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenDynamicServerListLoadBalancer_thenArrayListEmpty() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    assertTrue(lsrv.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Given {@code Lsrv}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenLsrv_thenBaseLoadBalancerReachableServersSizeIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add("Lsrv");

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenNull_thenBaseLoadBalancerAllServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add(null);

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Given two.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenTwo_thenThrowIllegalArgumentException() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    IPing ping2 = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add(2);
    lsrv.add("42");

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    baseLoadBalancer.setServersList(lsrv);
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers3() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers4() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("");

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers5() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers6() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(null);
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("");

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers7() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("LoadBalancer [{}]: clearing server list (SET op)");

    // Assert that nothing has changed
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("42", getResult.getHost());
    assertEquals("42:80", getResult.getHostPort());
    assertEquals("42:80", getResult.getId());
    assertEquals(1, baseLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers8() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("com.netflix.loadbalancer.IPing$MockitoMock$210192632");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632", getResult.getHost());
    assertEquals(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getHostPort());
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getId());
    assertEquals(1, baseLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers9() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("Srv String");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("Srv String", getResult.getHost());
    assertEquals("Srv String:80", getResult.getHostPort());
    assertEquals("Srv String:80", getResult.getId());
    assertEquals(1, baseLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers10() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(",", 3, 1L, ",");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    IRule rule = baseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers11() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("com.netflix.loadbalancer.IPing$MockitoMock$210192632");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632", getResult.getHost());
    assertEquals(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getHostPort());
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getId());
    assertEquals(1, baseLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers12() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("com.netflix.loadbalancer.IPing$MockitoMock$210192632");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632", getResult.getHost());
    assertEquals(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getHostPort());
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getId());
    assertEquals(1, baseLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers13() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("com.netflix.loadbalancer.IPing$MockitoMock$210192632");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632", getResult.getHost());
    assertEquals(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getHostPort());
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getId());
    assertEquals(1, baseLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers14() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("http://");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("", getResult.getHost());
    assertEquals(":80", getResult.getHostPort());
    assertEquals(":80", getResult.getId());
    assertEquals("http", getResult.getScheme());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers15() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("https://");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals(":443", getResult.getHostPort());
    assertEquals(":443", getResult.getId());
    assertEquals("https", getResult.getScheme());
    assertEquals(443, getResult.getPort());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers16() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("com.netflix.loadbalancer.IPing$MockitoMock$210192632");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isReadyToServe());
    List<Server> expectedServerList = baseLoadBalancer.allServerList;
    assertEquals(expectedServerList, baseLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers17() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(null);
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("com.netflix.loadbalancer.IPing$MockitoMock$210192632");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertNull(getResult.getScheme());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertEquals(80, getResult.getPort());
    assertTrue(getResult.isAlive());
    assertEquals(Server.UNKNOWN_ZONE, getResult.getZone());
    assertSame(getResult, reachableServers.get(0));
    assertSame(getResult, serverList.get(0));
    assertSame(getResult, serverList2.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers18() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(",", 3, 1L, ",");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("com.netflix.loadbalancer.IPing$MockitoMock$210192632");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632", getResult.getHost());
    assertEquals(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getHostPort());
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getId());
    assertEquals(1, baseLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers19() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServers(new Object[] {"New Servers"});
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(false);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setPrimeConnections(null);

    // Act
    dynamicServerListLoadBalancer.setServers(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632");

    // Assert that nothing has changed
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> expectedAllServers = dynamicServerListLoadBalancer.upServerList;
    assertEquals(expectedAllServers, dynamicServerListLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers20() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServers(new Object[] {"New Servers"});
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(false);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setPrimeConnections(null);

    // Act
    dynamicServerListLoadBalancer.setServers(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertEquals(1, dynamicServerListLoadBalancer.allServerList.size());
    List<Server> expectedAllServers = dynamicServerListLoadBalancer.allServerList;
    assertEquals(expectedAllServers, dynamicServerListLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_givenBaseLoadBalancer_thenBaseLoadBalancerAllServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setServers("");

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} AllServers
   *       Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_thenDynamicServerListLoadBalancerAllServersEmpty() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(false);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setPrimeConnections(null);

    // Act
    dynamicServerListLoadBalancer.setServers("");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} AllServers
   *       size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_thenDynamicServerListLoadBalancerAllServersSizeIsOne() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(false);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setPrimeConnections(null);

    // Act
    dynamicServerListLoadBalancer.setServers(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632", getResult.getHost());
    assertEquals(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getHostPort());
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getId());
    assertEquals(1, dynamicServerListLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>When {@code IPing$MockitoMock$210192632:80}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenComNetflixLoadbalancerIPingMockitoMock21019263280() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("com.netflix.loadbalancer.IPing$MockitoMock$210192632:80");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632", getResult.getHost());
    assertEquals(
        "com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getHostPort());
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$210192632:80", getResult.getId());
    assertEquals(1, baseLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenNull_thenBaseLoadBalancerAllServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setServers(null);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>When {@code /}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenSlash() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("/");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("", getResult.getHost());
    assertEquals(":80", getResult.getHostPort());
    assertEquals(":80", getResult.getId());
    assertEquals(1, baseLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>When {@code /}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenSlash2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener3).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener3);
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("/");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener3, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    verify(listener2, atLeast(1)).serverStatusChanged(Mockito.<Collection<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("", getResult.getHost());
    assertEquals(":80", getResult.getHostPort());
    assertEquals(":80", getResult.getId());
    assertEquals(1, baseLoadBalancer.allServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>When {@code Srv String}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenSrvString_thenBaseLoadBalancerAllServersSizeIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setServers("Srv String");

    // Assert
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    Server getResult = allServers.get(0);
    assertTrue(getResult.isReadyToServe());
    assertSame(getResult, reachableServers.get(0));
    assertSame(getResult, serverList.get(0));
    assertSame(getResult, serverList2.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_givenBaseLoadBalancerAddServerServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.getServerByIndex(1, false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with id is {@code 42}.
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_givenBaseLoadBalancerAddServerServerWithIdIs42_whenTrue() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.getServerByIndex(1, true));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_thenBaseLoadBalancerReachableServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(1, true));
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_thenBaseLoadBalancerReachableServersEmpty2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act and Assert
    assertNull(baseLoadBalancer.getServerByIndex(1, false));
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   *
   * <ul>
   *   <li>When {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_whenFalse() {
    // Arrange, Act and Assert
    assertTrue(new BaseLoadBalancer().getServerList(false).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   *
   * <ul>
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_whenTrue() {
    // Arrange, Act and Assert
    assertTrue(new BaseLoadBalancer().getServerList(true).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancerAddServerServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancerAddServerServerWithIdIs422() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancer_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new BaseLoadBalancer().getServerList(ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code ALL}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancer_whenAll_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new BaseLoadBalancer().getServerList(ServerGroup.ALL).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code STATUS_UP}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancer_whenStatusUp() {
    // Arrange, Act and Assert
    assertTrue(new BaseLoadBalancer().getServerList(ServerGroup.STATUS_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>Then return {@link BaseLoadBalancer#BaseLoadBalancer()} {@link
   *       BaseLoadBalancer#allServerList}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_thenReturnBaseLoadBalancerAllServerList() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(baseLoadBalancer.allServerList, actualServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#getReachableServers()}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getReachableServers()"})
  public void testGetReachableServers() {
    // Arrange, Act and Assert
    assertTrue(new BaseLoadBalancer().getReachableServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getAllServers()}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getAllServers()"})
  public void testGetAllServers() {
    // Arrange, Act and Assert
    assertTrue(new BaseLoadBalancer().getAllServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#chooseServer(Object)} with {@code Object}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act and Assert
    assertNull(new BaseLoadBalancer(config).chooseServer("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#chooseServer(Object)} with {@code Object}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RandomRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    Server actualChooseServerResult = baseLoadBalancer.chooseServer("Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertNull(actualChooseServerResult);
  }

  /**
   * Test {@link BaseLoadBalancer#chooseServer(Object)} with {@code Object}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject3() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(null);

    // Act and Assert
    assertNull(baseLoadBalancer.chooseServer(null));
  }

  /**
   * Test {@link BaseLoadBalancer#chooseServer(Object)} with {@code Object}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject_givenBaseLoadBalancerAddServerServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.chooseServer("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#chooseServer(Object)} with {@code Object}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject_givenBaseLoadBalancer_whenKey_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new BaseLoadBalancer().chooseServer("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#chooseServer(Object)} with {@code Object}.
   *
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject_givenZoneAwareLoadBalancer_whenKey_thenReturnNull() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Act and Assert
    assertNull(zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#chooseServer(Object)} with {@code Object}.
   *
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject_thenReturnServerWithIdIs42() {
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
   * Test {@link BaseLoadBalancer#choose(Object)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());

    // Act and Assert
    assertNull(baseLoadBalancer.choose("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RandomRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    String actualChooseResult = baseLoadBalancer.choose("Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals("42:80", actualChooseResult);
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RandomRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    String actualChooseResult = baseLoadBalancer.choose("Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertNull(actualChooseResult);
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)}
   *       with id is {@code 42}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code 42:80}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose_givenBaseLoadBalancerAddServerServerWithIdIs42_whenKey_thenReturn4280() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertEquals("42:80", baseLoadBalancer.choose("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose_givenBaseLoadBalancer_whenKey_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new BaseLoadBalancer().choose("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code true}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code 42:80}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose_givenIPingIsAliveReturnTrue_whenKey_thenReturn4280() {
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
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[] {"New Servers"});

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert that nothing has changed
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId2() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   *
   * <ul>
   *   <li>Then calls {@link ServerStatusChangeListener#serverStatusChanged(Collection)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_thenCallsServerStatusChanged() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then not {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_when42_thenNotBaseLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   *
   * <ul>
   *   <li>When {@code 42:80}.
   *   <li>Then calls {@link ServerStatusChangeListener#serverStatusChanged(Collection)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_when4280_thenCallsServerStatusChanged() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42:80");

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   *
   * <ul>
   *   <li>When {@code http://}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_whenHttp_thenBaseLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("http://");

    // Assert that nothing has changed
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   *
   * <ul>
   *   <li>When {@code https://}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_whenHttps_thenBaseLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("https://");

    // Assert that nothing has changed
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
    assertFalse(server.isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer2() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
    assertFalse(server.isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer_givenBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    assertFalse(server.isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer_givenBaseLoadBalancer_whenServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server server = new Server("42");

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert that nothing has changed
    assertFalse(server.isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#forceQuickPing()}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code true}.
   *   <li>Then calls {@link IPing#isAlive(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.forceQuickPing()"})
  public void testForceQuickPing_givenIPingIsAliveReturnTrue_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPing(ping);

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(config);
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    baseLoadBalancer.setPrimeConnections(primeConnections);
    baseLoadBalancer.setEnablePrimingConnections(true);

    // Act
    baseLoadBalancer.init();

    // Assert
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(1, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit2() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setZone(" ");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("localhost", 8080));
    dynamicServerListLoadBalancer.addServer(newServer);
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit3() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setZone(" ");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("localhost", 8080));
    dynamicServerListLoadBalancer.addServer(newServer);
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener3, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit4() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setZone(" ");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListImpl(new ConfigurationBasedServerList());
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("localhost", 8080));
    dynamicServerListLoadBalancer.addServer(newServer);
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener3, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}
   *       LoadBalancerStats is {@link LoadBalancerStats#LoadBalancerStats()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_givenDynamicServerListLoadBalancerLoadBalancerStatsIsLoadBalancerStats() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setSchemea("Scheme");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setLoadBalancerStats(new LoadBalancerStats());
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(newServer);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}
   *       updateAllServerList {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_givenDynamicServerListLoadBalancerUpdateAllServerListArrayList() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setSchemea("Scheme");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.updateAllServerList(new ArrayList<>());
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(newServer);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} Schemea is {@code Scheme}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_givenServerWithIdIs42SchemeaIsScheme() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setSchemea("Scheme");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(newServer);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} Zone is space.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_givenServerWithIdIs42ZoneIsSpace() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setZone(" ");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServer(newServer);
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenBaseLoadBalancerAllServersSizeIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    baseLoadBalancer.setPrimeConnections(primeConnections);
    baseLoadBalancer.setEnablePrimingConnections(true);

    // Act
    baseLoadBalancer.init();

    // Assert
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(1, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers size is three.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenBaseLoadBalancerAllServersSizeIsThree() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    baseLoadBalancer.setPrimeConnections(primeConnections);
    baseLoadBalancer.setEnablePrimingConnections(true);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(25L, endStats.totalTime);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(3, allServers.size());
    assertEquals(3, endStats.total);
    assertFalse(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers size is two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenBaseLoadBalancerAllServersSizeIsTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    baseLoadBalancer.setPrimeConnections(primeConnections);
    baseLoadBalancer.setEnablePrimingConnections(true);

    // Act
    baseLoadBalancer.init();

    // Assert
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers size is two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenBaseLoadBalancerAllServersSizeIsTwo2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    baseLoadBalancer.setPrimeConnections(primeConnections);
    baseLoadBalancer.setEnablePrimingConnections(true);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertEquals(25L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}
   *       PrimeConnections EndStats {@link PrimeConnections.PrimeConnectionEndStats#total} is
   *       thirty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenDynamicServerListLoadBalancerPrimeConnectionsEndStatsTotalIsThirty() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setZone(" ");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setRule(new AvailabilityFilteringRule());
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("localhost", 8080));
    dynamicServerListLoadBalancer.addServer(newServer);
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ", 10.0f);
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener3, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(25L, endStats.totalTime);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(3, allServers.size());
    assertEquals(30, endStats.total);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
    assertFalse(allServers.get(2).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}
   *       PrimeConnections EndStats {@link PrimeConnections.PrimeConnectionEndStats#total} is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenDynamicServerListLoadBalancerPrimeConnectionsEndStatsTotalIsThree() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setZone(" ");

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setRule(new AvailabilityFilteringRule());
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("localhost", 8080));
    dynamicServerListLoadBalancer.addServer(newServer);
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener3, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(25L, endStats.totalTime);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(3, allServers.size());
    assertEquals(3, endStats.total);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
    assertFalse(allServers.get(2).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code true}.
   *   <li>Then calls {@link IPing#isAlive(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.shutdown()"})
  public void testShutdown_givenIPingIsAliveReturnTrue_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    IPing ping2 = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.shutdown();

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Test {@link BaseLoadBalancer#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link ServerStatusChangeListener} {@link
   *       ServerStatusChangeListener#serverStatusChanged(Collection)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.shutdown()"})
  public void testShutdown_givenServerStatusChangeListenerServerStatusChangedDoesNothing() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());
    IPing ping2 = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.shutdown();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
  }
}
