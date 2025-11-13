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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer2() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(
            "com.netflix.loadbalancer.IPing$MockitoMock$1553284035",
            rule,
            new LoadBalancerStats(),
            mock(IPing.class));

    // Assert
    assertEquals(
        "com.netflix.loadbalancer.IPing$MockitoMock$1553284035", actualBaseLoadBalancer.getName());
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
  public void testNewBaseLoadBalancer3() {
    // Arrange
    WeightedResponseTimeRule rule = new WeightedResponseTimeRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(
            "com.netflix.loadbalancer.IPing$MockitoMock$1553284035",
            rule,
            new LoadBalancerStats(),
            mock(IPing.class));

    // Assert
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$1553284035", rule.name);
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

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(lb);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link IPingStrategy} {@link IPingStrategy#pingServers(IPing, Server[])} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule, IPingStrategy)"})
  public void testNewBaseLoadBalancer_givenNull_whenIPingStrategyPingServersReturnNull() {
    // Arrange
    IPing ping = mock(IPing.class);

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any())).thenReturn(null);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, null, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} LoadBalancer is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_givenNull_whenResponseTimeWeightedRuleLoadBalancerIsNull() {
    // Arrange
    IPing ping = mock(IPing.class);

    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    rule.setLoadBalancer(null);

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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}.
   *
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule, IPingStrategy)"})
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero2() {
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
  public void testNewBaseLoadBalancer_thenRuleReturnResponseTimeWeightedRule() {
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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing,
   * IPingStrategy)}.
   *
   * <ul>
   *   <li>Then {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} {@link
   *       WeightedResponseTimeRule#name} is {@code String}.
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
  public void testNewBaseLoadBalancer_thenWeightedResponseTimeRuleNameIsJavaLangString() {
    // Arrange
    WeightedResponseTimeRule rule = new WeightedResponseTimeRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenThrow(new IllegalArgumentException());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("java.lang.String", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    assertEquals("java.lang.String", rule.name);
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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>When {@code config}.
   *   <li>Then return Name is {@code config}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenConfig_thenReturnNameIsConfig() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("config", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof ResponseTimeWeightedRule);
    assertEquals("config", actualBaseLoadBalancer.getName());
    assertEquals("config", rule.name);
    assertEquals("config", ((ResponseTimeWeightedRule) rule2).name);
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>When {@link DummyPing} (default constructor).
   *   <li>Then Ping return {@link DummyPing}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenDummyPing_thenPingReturnDummyPing() {
    // Arrange
    LoadBalancerStats stats = new LoadBalancerStats();
    DummyPing ping = new DummyPing();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(" ", null, stats, ping);

    // Assert
    IPing ping2 = actualBaseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertNull(((DummyPing) ping2).getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, ping2);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenNull_thenReturnName() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("Name", null, new LoadBalancerStats(), ping);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then Rule return {@link AvailabilityFilteringRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnAvailabilityFilteringRule() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(null, new AvailabilityFilteringRule());

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertSame(
        actualBaseLoadBalancer,
        ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
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
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule, IPingStrategy)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule2() {
    // Arrange
    IPing ping = mock(IPing.class);

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[] {true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, null, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule, IPingStrategy)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule3() {
    // Arrange
    IPing ping = mock(IPing.class);

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenThrow(new IllegalArgumentException());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, null, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
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
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule4() {
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
   *   <li>When {@link PingConstant} (default constructor).
   *   <li>Then Ping return {@link PingConstant}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenPingConstant_thenPingReturnPingConstant() {
    // Arrange
    LoadBalancerStats stats = new LoadBalancerStats();
    PingConstant ping = new PingConstant();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(" ", null, stats, ping);

    // Assert
    IPing ping2 = actualBaseLoadBalancer.getPing();
    assertTrue(ping2 instanceof PingConstant);
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(((PingConstant) ping2).getConstant());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, ping2);
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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule, IPingStrategy)"})
  public void testNewBaseLoadBalancer_whenResponseTimeWeightedRule2() {
    // Arrange
    IPing ping = mock(IPing.class);
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenThrow(new IllegalArgumentException());

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    assertEquals("default", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}.
   *
   * <ul>
   *   <li>When {@link RoundRobinRule#RoundRobinRule()}.
   *   <li>Then return Rule is {@link RoundRobinRule#RoundRobinRule()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule, IPingStrategy)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule, IPingStrategy)"})
  public void testNewBaseLoadBalancer_whenRoundRobinRule_thenReturnRuleIsRoundRobinRule() {
    // Arrange
    IPing ping = mock(IPing.class);
    RoundRobinRule rule = new RoundRobinRule();

    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(
            new boolean[] {
              true, false, true, false, true, false, true, false, true, false, true, false
            });

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof RoundRobinRule);
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
    assertSame(rule, rule2);
    assertSame(ping, actualBaseLoadBalancer.getPing());
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
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());
    dynamicServerListLoadBalancer.addServerStatusChangeListener(listener);

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    dynamicServerListLoadBalancer.setPing(ping);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.new PingTask().run();

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Test PingTask {@link PingTask#run()}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code true}.
   *   <li>Then calls {@link IPing#isAlive(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link PingTask#run()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PingTask.run()"})
  public void testPingTaskRun_givenIPingIsAliveReturnTrue_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(ping);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.new PingTask().run();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Test PingTask {@link PingTask#run()}.
   *
   * <ul>
   *   <li>Given {@link ServerStatusChangeListener} {@link
   *       ServerStatusChangeListener#serverStatusChanged(Collection)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link PingTask#run()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PingTask.run()"})
  public void testPingTaskRun_givenServerStatusChangeListenerServerStatusChangedDoesNothing() {
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

    // Act
    dynamicServerListLoadBalancer.new PingTask().run();

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
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
   * Test {@link BaseLoadBalancer#setupPingTask()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setupPingTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setupPingTask()"})
  public void testSetupPingTask_givenBaseLoadBalancer() {
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
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>When {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_givenBaseLoadBalancer_whenName() {
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
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code true}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Name is {@code ,}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_givenIPingIsAliveReturnTrue_thenBaseLoadBalancerNameIsComma() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName(",");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals(",", baseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertEquals(",", loadBalancerStats.getName());
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
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_givenIPingIsAliveThrowRuntimeException() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName("Name");

    // Assert
    verify(ping).isAlive(isA(Server.class));
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
   * <p>Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setMaxTotalPingTime(int)"})
  public void testSetMaxTotalPingTime() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    assertEquals(3, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Test {@link BaseLoadBalancer#setMaxTotalPingTime(int)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setMaxTotalPingTime(int)"})
  public void testSetMaxTotalPingTime_givenBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    assertEquals(3, baseLoadBalancer.getMaxTotalPingTime());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setMaxTotalPingTime(int)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setMaxTotalPingTime(int)"})
  public void testSetMaxTotalPingTime_thenBaseLoadBalancerReachableServersSizeIsOne() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
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
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#isPingInProgress()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseLoadBalancer.isPingInProgress()"})
  public void testIsPingInProgress_givenBaseLoadBalancer_thenReturnFalse() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act and Assert
    assertFalse(baseLoadBalancer.isPingInProgress());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
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
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    baseLoadBalancer.setRule(rule);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    List<Server> serverList = ((BaseLoadBalancer) loadBalancer).allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.allServerList;
    assertEquals(serverList2, loadBalancer.getAllServers());
    assertSame(serverList2, serverList);
    assertSame(baseLoadBalancer.lbTimer, ((BaseLoadBalancer) loadBalancer).lbTimer);
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
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

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
  public void testSetServers4() {
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
   *   <li>Then {@link BaseLoadBalancer} AllServers first Scheme is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#setServers(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.setServers(String)"})
  public void testSetServers_thenBaseLoadBalancerAllServersFirstSchemeIsNull() {
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
    Server getResult = allServers.get(0);
    assertNull(getResult.getScheme());
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = baseLoadBalancer.upServerList;
    assertEquals(1, serverList2.size());
    assertFalse(getResult.isReadyToServe());
    assertTrue(getResult.isAlive());
    assertEquals(Server.UNKNOWN_ZONE, getResult.getZone());
    assertSame(getResult, reachableServers.get(0));
    assertSame(getResult, serverList.get(0));
    assertSame(getResult, serverList2.get(0));
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
   * Test {@link BaseLoadBalancer#forceQuickPing()}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.forceQuickPing()"})
  public void testForceQuickPing_givenIPingIsAliveThrowIllegalArgumentException() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException());

    BaseLoadBalancer baseLoadBalancer =
        new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setPing(ping);

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert
    verify(ping2).isAlive(isA(Server.class));
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Test {@link BaseLoadBalancer#forceQuickPing()}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} throw {@link
   *       RuntimeException#RuntimeException()}.
   *   <li>Then calls {@link IPing#isAlive(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.forceQuickPing()"})
  public void testForceQuickPing_givenIPingIsAliveThrowRuntimeException_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

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
  public void testInit2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setMaxTotalPingTime(3);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
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

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setMaxTotalPingTime(3);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections = new PrimeConnections(" ", 3, 25L, " ", 10.0f);
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(10, endStats.total);
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
  public void testInit4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener3)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener3);
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setMaxTotalPingTime(3);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
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
   * <ul>
   *   <li>Given {@link ServerListChangeListener} {@link
   *       ServerListChangeListener#serverListChanged(List, List)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_givenServerListChangeListenerServerListChangedDoesNothing() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.setMaxTotalPingTime(3);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    baseLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    baseLoadBalancer.init();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
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
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} AllServers
   *       first ReadyToServe.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenDynamicServerListLoadBalancerAllServersFirstReadyToServe() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener4 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener4)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener4);
    dynamicServerListLoadBalancer.addServers(new ArrayList<>());
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.setMaxTotalPingTime(3);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setEnablePrimingConnections(false);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert that nothing has changed
    verify(listener4).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isReadyToServe());
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}
   *       PrimeConnections EndStats {@link PrimeConnections.PrimeConnectionEndStats#failure} is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenDynamicServerListLoadBalancerPrimeConnectionsEndStatsFailureIsZero() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServerStatusChangeListener(
        mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.setMaxTotalPingTime(3);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    dynamicServerListLoadBalancer.setPrimeConnections(primeConnections);

    // Act
    dynamicServerListLoadBalancer.init();

    // Assert
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
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
   * Test {@link BaseLoadBalancer#primeCompleted(Server, Throwable)}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#primeCompleted(Server, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.primeCompleted(Server, Throwable)"})
  public void testPrimeCompleted_givenIPingIsAliveThrowRuntimeException() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServer(new Server("42"));
    Server s = new Server("42");

    // Act
    baseLoadBalancer.primeCompleted(s, new Throwable());

    // Assert that nothing has changed
    verify(ping).isAlive(isA(Server.class));
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#primeCompleted(Server, Throwable)}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#primeCompleted(Server, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.primeCompleted(Server, Throwable)"})
  public void testPrimeCompleted_thenBaseLoadBalancerReachableServersEmpty() {
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
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.shutdown()"})
  public void testShutdown_givenBaseLoadBalancer_thenDoesNotThrow() {
    // Arrange, Act and Assert
    new BaseLoadBalancer().shutdown();
  }
}
