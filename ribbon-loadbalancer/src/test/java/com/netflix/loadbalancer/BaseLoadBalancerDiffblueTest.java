package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
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
import com.netflix.client.PrimeConnections;
import com.netflix.client.PrimeConnections.PrimeConnectionEndStats;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.loadbalancer.AbstractLoadBalancer.ServerGroup;
import com.netflix.loadbalancer.BaseLoadBalancer.PingTask;
import com.netflix.loadbalancer.BaseLoadBalancer.Pinger;
import com.netflix.util.concurrent.ShutdownEnabledTimer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BaseLoadBalancerDiffblueTest {
  @InjectMocks private BaseLoadBalancer baseLoadBalancer;

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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer2() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("42"));

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
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
  public void testNewBaseLoadBalancer5() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(
            "com.netflix.loadbalancer.IPing$MockitoMock$1570122152",
            rule,
            new LoadBalancerStats(),
            mock(IPing.class));

    // Assert
    assertEquals(
        "com.netflix.loadbalancer.IPing$MockitoMock$1570122152", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenDynamicServerListLoadBalancer() {
    // Arrange
    IPing ping = mock(IPing.class);

    Server newServer = new Server("42");
    newServer.setSchemea(" ");

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb = new BaseLoadBalancer();
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    lb.setPrimeConnections(primeConnections);
    lb.addServerListChangeListener(listener2);
    lb.addServerListChangeListener(listener);
    lb.setPing(ping2);
    lb.addServer(newServer);

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.initialize(new DynamicServerListLoadBalancer<>());
    rule.setLoadBalancer(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(ping2).isAlive(isA(Server.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} Schemea is space.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenServerWithIdIs42SchemeaIsSpace() {
    // Arrange
    IPing ping = mock(IPing.class);

    Server newServer = new Server("42");
    newServer.setSchemea(" ");

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerListChangeListener(listener);
    lb.setPing(ping2);
    lb.addServer(newServer);

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(ping2).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42/} Schemea is space.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenServerWithIdIs42SchemeaIsSpace2() {
    // Arrange
    IPing ping = mock(IPing.class);

    Server newServer = new Server("42/");
    newServer.setSchemea(" ");

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerListChangeListener(listener2);
    lb.addServerListChangeListener(listener);
    lb.setPing(ping2);
    lb.addServer(newServer);

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    verify(ping2).isAlive(isA(Server.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
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
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero() {
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
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero4() {
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
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero5() {
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
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link
   *       ResponseTimeWeightedRule#name} is {@code default}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_thenResponseTimeWeightedRuleNameIsDefault() {
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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link
   *       ResponseTimeWeightedRule#name} is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenResponseTimeWeightedRuleNameIsName() {
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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing,
   * IPingStrategy)}.
   *
   * <ul>
   *   <li>Then return Name is {@code BaseLoadBalancer}.
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
  public void testNewBaseLoadBalancer_thenReturnNameIsComNetflixLoadbalancerBaseLoadBalancer() {
    // Arrange
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenThrow(new IllegalArgumentException());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(
            "com.netflix.loadbalancer.BaseLoadBalancer", null, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertEquals("com.netflix.loadbalancer.BaseLoadBalancer", actualBaseLoadBalancer.getName());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Then Rule return {@link ResponseTimeWeightedRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_thenRuleReturnResponseTimeWeightedRule() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(null, rule);

    // Assert
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof ResponseTimeWeightedRule);
    assertEquals("default", rule.name);
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   *
   * <ul>
   *   <li>Then Rule return {@link ResponseTimeWeightedRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_thenRuleReturnResponseTimeWeightedRule2() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("NFLoadBalancer-serverWeightTimer-", rule, new LoadBalancerStats());

    // Assert
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof ResponseTimeWeightedRule);
    assertEquals("NFLoadBalancer-serverWeightTimer-", actualBaseLoadBalancer.getName());
    assertEquals("NFLoadBalancer-serverWeightTimer-", rule.name);
    assertEquals("NFLoadBalancer-serverWeightTimer-", ((ResponseTimeWeightedRule) rule2).name);
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>When {@link AvailabilityFilteringRule} (default constructor).
   *   <li>Then return Ping is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenAvailabilityFilteringRule_thenReturnPingIsNull() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(null, rule);

    // Assert
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing,
   * IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
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
  public void testNewBaseLoadBalancer_whenBestAvailableRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPing(ping);
    lb.addServer(new Server("42"));

    BestAvailableRule rule = new BestAvailableRule();
    rule.setLoadBalancer(lb);
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping2 = mock(IPing.class);

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenThrow(new RuntimeException());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", rule, stats, ping2, pingStrategy);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
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
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule() {
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
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule2() {
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
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule3() {
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
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule4() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing,
   * IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code Name}.
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
  public void testNewBaseLoadBalancer_whenNull_thenReturnName() {
    // Arrange
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", null, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(stats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>When {@code null}.
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
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnAvailabilityFilteringRule() {
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
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
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
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule3() {
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
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule4() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", null, new LoadBalancerStats(), ping);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing,
   * IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
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
  public void testNewBaseLoadBalancer_whenResponseTimeWeightedRule2() {
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
   * Test PingTask {@link PingTask#run()}.
   *
   * <p>Method under test: {@link PingTask#run()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PingTask.run()"})
  public void testPingTaskRun() {
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
   * Test PingTask {@link PingTask#run()}.
   *
   * <p>Method under test: {@link PingTask#run()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PingTask.run()"})
  public void testPingTaskRun2() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.new PingTask().run();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
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
   * <p>Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger4() throws Exception {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerStatusChangeListener(listener);
    dynamicServerListLoadBalancer.setPing(ping);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, false});

    // Act
    dynamicServerListLoadBalancer.new Pinger(pingerStrategy).runPinger();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    verify(listener).serverStatusChanged(isA(Collection.class));
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
   * Test {@link BaseLoadBalancer#setupPingTask()}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setupPingTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setupPingTask()"})
  public void testSetupPingTask_thenBaseLoadBalancerReachableServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setupPingTask();

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName("Name");

    // Assert
    verify(ping).isAlive(isA(Server.class));
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
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Name is {@code LoadBalancer [{}]: Server
   *       [{}] status changed to {}}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_thenBaseLoadBalancerNameIsLoadBalancerServerStatusChangedTo() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName("LoadBalancer [{}]:  Server [{}] status changed to {}");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    assertEquals(
        "LoadBalancer [{}]:  Server [{}] status changed to {}", baseLoadBalancer.getName());
    assertEquals(
        "LoadBalancer [{}]:  Server [{}] status changed to {}",
        baseLoadBalancer.getLoadBalancerStats().getName());
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
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
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName("Name");

    // Assert
    assertEquals("Name", baseLoadBalancer.getName());
    assertEquals("Name", baseLoadBalancer.getLoadBalancerStats().getName());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Name is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_whenName_thenBaseLoadBalancerNameIsName() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName("Name");

    // Assert
    assertEquals("Name", baseLoadBalancer.getName());
    assertEquals("Name", baseLoadBalancer.getLoadBalancerStats().getName());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
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
    assertNull(baseLoadBalancer.getLoadBalancerStats().getName());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
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
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerLock}
   *       ReadLockCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockUpServerList(boolean)"})
  public void testLockUpServerList_thenBaseLoadBalancerUpServerLockReadLockCountIsZero() {
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
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} MaxTotalPingTime is five.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setMaxTotalPingTime(int)"})
  public void testSetMaxTotalPingTime_thenBaseLoadBalancerMaxTotalPingTimeIsFive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setMaxTotalPingTime(0);

    // Assert that nothing has changed
    assertEquals(5, baseLoadBalancer.getMaxTotalPingTime());
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
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_givenIPingIsAliveThrowIllegalArgumentException() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);

    // Act
    baseLoadBalancer.setPing(ping2);

    // Assert
    List<Server> serverList = baseLoadBalancer.allServerList;
    verify(ping).isAlive(isA(Server.class));
    verify(ping2).isAlive(isA(Server.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
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
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer} {@link BaseLoadBalancer#lbTimer} {@link
   *       ShutdownEnabledTimer}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_thenBaseLoadBalancerLbTimerShutdownEnabledTimer() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    baseLoadBalancer.setPing(ping);

    // Assert
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertSame(ping, baseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   *
   * <ul>
   *   <li>When {@link DummyPing} (default constructor).
   *   <li>Then {@link BaseLoadBalancer} {@link BaseLoadBalancer#lbTimer} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_whenDummyPing_thenBaseLoadBalancerLbTimerIsNull() {
    // Arrange
    DummyPing ping = new DummyPing();

    // Act
    baseLoadBalancer.setPing(ping);

    // Assert
    assertNull(baseLoadBalancer.lbTimer);
    assertSame(ping, baseLoadBalancer.getPing());
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
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    baseLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = loadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = ((BaseLoadBalancer) loadBalancer).allServerList;
    assertEquals(1, serverList.size());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
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
  public void testSetRule2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server newServer = new Server(null);
    baseLoadBalancer.addServer(newServer);
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    baseLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = loadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = ((BaseLoadBalancer) loadBalancer).allServerList;
    assertEquals(1, serverList.size());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
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
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link
   *       ResponseTimeWeightedRule#name} is {@code default}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenResponseTimeWeightedRuleNameIsDefault() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    baseLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertEquals("default", rule.name);
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(loadBalancer.getReachableServers().isEmpty());
    assertTrue(((BaseLoadBalancer) loadBalancer).allServerList.isEmpty());
    assertTrue(((BaseLoadBalancer) loadBalancer).upServerList.isEmpty());
    assertSame(rule, baseLoadBalancer.getRule());
    assertSame(rule, ((BaseLoadBalancer) loadBalancer).getRule());
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
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Ping is {@link IPing}.
   *   <li>When {@code false}.
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_givenBaseLoadBalancerPingIsIPing_whenFalse_thenReturnOne() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    int actualServerCount = baseLoadBalancer.getServerCount(false);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(1, actualServerCount);
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code false}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_givenBaseLoadBalancer_whenFalse_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, new BaseLoadBalancer().getServerCount(false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code true}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_givenBaseLoadBalancer_whenTrue_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, new BaseLoadBalancer().getServerCount(true));
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
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_givenBaseLoadBalancer_whenArrayList_thenArrayListEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    ArrayList<Server> newServers = new ArrayList<>();

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert that nothing has changed
    assertTrue(newServers.isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
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
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(2, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(2, serverList2.size());
    assertSame(server, allServers.get(1));
    assertSame(server, reachableServers.get(1));
    assertSame(server, serverList.get(1));
    assertSame(server, serverList2.get(1));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_whenNull_thenBaseLoadBalancerReachableServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServers((List<Server>) null);

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
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
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenNull_whenArrayListAddNull_thenArrayListSizeIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Object> lsrv = new ArrayList<>();
    lsrv.add(null);

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert that nothing has changed
    assertEquals(1, lsrv.size());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link BaseLoadBalancer#BaseLoadBalancer()} {@link
   *       BaseLoadBalancer#upServerList}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServersList(List)"})
  public void testSetServersList_thenArrayListIsBaseLoadBalancerUpServerList() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    baseLoadBalancer.setServersList(lsrv);

    // Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertEquals(baseLoadBalancer.upServerList, lsrv);
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
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

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
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

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
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
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
  public void testSetServers4() {
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
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("foo,bar");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
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
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("foo,bar");

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertEquals(2, baseLoadBalancer.allServerList.size());
    List<Server> expectedAllServers = baseLoadBalancer.allServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
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
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    primeConnections.primeConnections(new ArrayList<>());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("42");

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(newServer, allServers.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer} PrimeConnections is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_givenBaseLoadBalancerPrimeConnectionsIsNull() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPrimeConnections(null);

    // Act
    baseLoadBalancer.setServers("foo,bar");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(2, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(2, serverList2.size());
    Server getResult = allServers.get(1);
    assertSame(getResult, reachableServers.get(1));
    assertSame(getResult, serverList.get(1));
    assertSame(getResult, serverList2.get(1));
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
   *   <li>Then {@link BaseLoadBalancer} AllServers first Host is {@code . No nodes}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_thenBaseLoadBalancerAllServersFirstHostIsNoNodes() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers(". No nodes/servers to prime connections");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals(". No nodes", getResult.getHost());
    assertEquals(". No nodes:80", getResult.getHostPort());
    assertEquals(". No nodes:80", getResult.getId());
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer} AllServers first is {@link Server#Server(String)} with id
   *       is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_thenBaseLoadBalancerAllServersFirstIsServerWithIdIsNull() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    Server newServer = new Server(null);
    baseLoadBalancer.addServer(newServer);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("Srv String");

    // Assert that nothing has changed
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertSame(newServer, allServers.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_thenBaseLoadBalancerReachableServersSizeIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setServers("Srv String");

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer(IClientConfig)} with config is EmptyConfig
   *       {@link BaseLoadBalancer#allServerList} size is two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_thenBaseLoadBalancerWithConfigIsEmptyConfigAllServerListSizeIsTwo() {
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
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("foo,bar");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertEquals(2, baseLoadBalancer.allServerList.size());
    assertEquals(2, baseLoadBalancer.upServerList.size());
    List<Server> expectedReachableServers = baseLoadBalancer.upServerList;
    assertEquals(expectedReachableServers, baseLoadBalancer.getReachableServers());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link
   *       BaseLoadBalancer#allServerList} size is two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_thenDynamicServerListLoadBalancerAllServerListSizeIsTwo() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
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
    dynamicServerListLoadBalancer.setEnablePrimingConnections(false);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    dynamicServerListLoadBalancer.setServers("foo,bar");

    // Assert
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertEquals(2, dynamicServerListLoadBalancer.allServerList.size());
    assertEquals(2, dynamicServerListLoadBalancer.upServerList.size());
    List<Server> expectedReachableServers = dynamicServerListLoadBalancer.upServerList;
    assertEquals(expectedReachableServers, dynamicServerListLoadBalancer.getReachableServers());
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

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.setEnablePrimingConnections(false);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    dynamicServerListLoadBalancer.setServers("");

    // Assert
    verify(listener2, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
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
   *   <li>Then not {@link BaseLoadBalancer} AllServers first ReadyToServe.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_thenNotBaseLoadBalancerAllServersFirstReadyToServe() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(null);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("Srv String");

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
    assertFalse(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link BaseLoadBalancer} AllServers first is {@link Server#Server(String)} with id
   *       is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_when42_thenBaseLoadBalancerAllServersFirstIsServerWithIdIs42() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("42");

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(newServer, allServers.get(0));
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then {@link BaseLoadBalancer} AllServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenEmptyString_thenBaseLoadBalancerAllServersEmpty() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>When {@code foo,bar}.
   *   <li>Then {@link BaseLoadBalancer} AllServers size is two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenFooBar_thenBaseLoadBalancerAllServersSizeIsTwo() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("foo,bar");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(2, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(2, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(2, serverList2.size());
    Server getResult = allServers.get(1);
    assertSame(getResult, reachableServers.get(1));
    assertSame(getResult, serverList.get(1));
    assertSame(getResult, serverList2.get(1));
  }

  /**
   * Test {@link BaseLoadBalancer#setServers(String)}.
   *
   * <ul>
   *   <li>When {@code http://}.
   *   <li>Then {@link BaseLoadBalancer} AllServers first Host is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenHttp_thenBaseLoadBalancerAllServersFirstHostIsEmptyString() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("http://");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
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
   * <ul>
   *   <li>When {@code https://}.
   *   <li>Then {@link BaseLoadBalancer} AllServers first HostPort is {@code :443}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenHttps_thenBaseLoadBalancerAllServersFirstHostPortIs443() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("https://");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
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
   * <ul>
   *   <li>When {@code LoadBalancer [{}]: clearing server list (SET op)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenLoadBalancerClearingServerListSetOp() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(false);
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("LoadBalancer [{}]: clearing server list (SET op)");

    // Assert that nothing has changed
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertSame(newServer, allServers.get(0));
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
   *   <li>When {@code Srv String}.
   *   <li>Then {@link BaseLoadBalancer} AllServers first Host is {@code Srv String}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_whenSrvString_thenBaseLoadBalancerAllServersFirstHostIsSrvString() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServer(new Server("42"));
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.setServers("Srv String");

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    Server getResult = allServers.get(0);
    assertEquals("Srv String", getResult.getHost());
    assertEquals("Srv String:80", getResult.getHostPort());
    assertEquals("Srv String:80", getResult.getId());
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code false}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_givenBaseLoadBalancer_whenFalse_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new BaseLoadBalancer().getServerByIndex(1, false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_givenBaseLoadBalancer_whenTrue_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new BaseLoadBalancer().getServerByIndex(1, true));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   *
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_thenReturnServerWithIdIs42() {
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
   *   <li>When {@code false}.
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_whenFalse_thenReturnServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.getServerByIndex(1, false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    List<Server> actualServerList = dynamicServerListLoadBalancer.getServerList(true);

    // Assert
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Ping is {@link IPing}.
   *   <li>When {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_givenBaseLoadBalancerPingIsIPing_whenFalse() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(mock(IPing.class));

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(false);

    // Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_givenBaseLoadBalancer_whenFalse() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(false);

    // Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_givenBaseLoadBalancer_whenTrue() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(true);

    // Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_thenBaseLoadBalancerReachableServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP);

    // Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_thenBaseLoadBalancerReachableServersSizeIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP);

    // Assert that nothing has changed
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerList}
   *       size is two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_thenBaseLoadBalancerUpServerListSizeIsTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP);

    // Assert that nothing has changed
    assertEquals(2, baseLoadBalancer.upServerList.size());
    List<Server> expectedReachableServers = baseLoadBalancer.upServerList;
    assertEquals(expectedReachableServers, baseLoadBalancer.getReachableServers());
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
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>When {@code ALL}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_whenAll() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(ServerGroup.ALL);

    // Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <ul>
   *   <li>When {@code STATUS_UP}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_whenStatusUp() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    List<Server> actualServerList = baseLoadBalancer.getServerList(ServerGroup.STATUS_UP);

    // Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualServerList.isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getReachableServers()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getReachableServers()"})
  public void testGetReachableServers_givenBaseLoadBalancer_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new BaseLoadBalancer().getReachableServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getReachableServers()}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} throw {@link
   *       RuntimeException#RuntimeException()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseLoadBalancer.getReachableServers()"})
  public void testGetReachableServers_givenIPingIsAliveThrowRuntimeException_thenReturnEmpty() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServers(new Object[] {"New Servers", "New Servers", "New Servers"});

    // Act
    List<Server> actualReachableServers = baseLoadBalancer.getReachableServers();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    assertTrue(actualReachableServers.isEmpty());
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
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_thenBaseLoadBalancerAllServersFirstAlive() {
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
   * <ul>
   *   <li>Then not {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_thenNotBaseLoadBalancerAllServersFirstAlive() {
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
   * <p>Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.forceQuickPing()"})
  public void testForceQuickPing() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    baseLoadBalancer.setPing(mock(IPing.class));

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#forceQuickPing()}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.forceQuickPing()"})
  public void testForceQuickPing_thenBaseLoadBalancerReachableServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
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
    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.init();

    // Assert
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(1, endStats.total);
    assertEquals(1L, endStats.totalTime);
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
  public void testInit3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(1L, endStats.totalTime);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
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
  public void testInit4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.setMaxTotalPingTime(3);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(1L, endStats.totalTime);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
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
  public void testInit5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(null);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(1, endStats.total);
    assertEquals(1L, endStats.totalTime);
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
  public void testInit6() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(null);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(1, endStats.total);
    assertEquals(1L, endStats.totalTime);
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
  public void testInit7() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServers(new Object[] {"New Servers"});
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(null);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(1L, endStats.totalTime);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} AllServers
   *       size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenDynamicServerListLoadBalancerAllServersSizeIsOne() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
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
    dynamicServerListLoadBalancer.addServer(null);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(1, endStats.total);
    assertEquals(1L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} AllServers
   *       size is two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenDynamicServerListLoadBalancerAllServersSizeIsTwo() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPingInterval(42);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(null);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(1L, endStats.totalTime);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertFalse(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} AllServers
   *       size is two.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenDynamicServerListLoadBalancerAllServersSizeIsTwo2() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    primeConnections.primeConnections(new ArrayList<>());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPingInterval(42);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(null);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(1L, endStats.totalTime);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertFalse(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then not {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} AllServers
   *       second ReadyToServe.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenNotDynamicServerListLoadBalancerAllServersSecondReadyToServe() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    primeConnections.primeConnections(new ArrayList<>());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    Server newServer = new Server("42");
    newServer.setZone(" ");
    newServer.setReadyToServe(true);

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPingInterval(42);
    dynamicServerListLoadBalancer.addServer(newServer);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(null);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    PrimeConnectionEndStats endStats =
        dynamicServerListLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(1L, endStats.totalTime);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertFalse(allServers.get(0).isReadyToServe());
    assertFalse(allServers.get(1).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} PrimeConnections EndStats
   *       {@link PrimeConnections.PrimeConnectionEndStats#failure} is zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenZoneAwareLoadBalancerPrimeConnectionsEndStatsFailureIsZero() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    primeConnections.primeConnections(new ArrayList<>());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServers(new ArrayList<>());
    zoneAwareLoadBalancer.addServerListChangeListener(listener3);
    zoneAwareLoadBalancer.setPingInterval(42);
    zoneAwareLoadBalancer.addServer(new Server("42"));
    zoneAwareLoadBalancer.addServerListChangeListener(listener2);
    zoneAwareLoadBalancer.addServerListChangeListener(listener);
    zoneAwareLoadBalancer.addServer(null);
    zoneAwareLoadBalancer.addServer(new Server("42"));
    zoneAwareLoadBalancer.setEnablePrimingConnections(true);
    zoneAwareLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    zoneAwareLoadBalancer.init();

    // Assert
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    PrimeConnectionEndStats endStats = zoneAwareLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(1L, endStats.totalTime);
    List<Server> allServers = zoneAwareLoadBalancer.getAllServers();
    assertEquals(2, allServers.size());
    assertEquals(2, endStats.total);
    assertFalse(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#primeCompleted(Server, Throwable)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#primeCompleted(Server, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.primeCompleted(Server, Throwable)"})
  public void testPrimeCompleted() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    Server s = new Server("42");

    // Act
    baseLoadBalancer.primeCompleted(s, new Throwable());

    // Assert that nothing has changed
    verify(ping).isAlive(isA(Server.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#primeCompleted(Server, Throwable)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#primeCompleted(Server, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.primeCompleted(Server, Throwable)"})
  public void testPrimeCompleted_givenBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server s = new Server("42");

    // Act
    baseLoadBalancer.primeCompleted(s, new Throwable());

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
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

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setPingInterval(42);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.shutdown();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
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

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.setPingInterval(42);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.shutdown();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
  }
}
