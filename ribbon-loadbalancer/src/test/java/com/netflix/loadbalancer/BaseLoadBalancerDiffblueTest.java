package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.PrimeConnections;
import com.netflix.client.config.IClientConfig;
import com.netflix.util.concurrent.ShutdownEnabledTimer;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BaseLoadBalancerDiffblueTest {
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
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("\"TestLoadBalancer\"", rule, new LoadBalancerStats());

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
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero2() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(
            "\"TestLoadBalancer\"", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   *
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link
   *       ResponseTimeWeightedRule#name} is {@code "TestLoadBalancer"}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_thenResponseTimeWeightedRuleNameIsTestLoadBalancer() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("\"TestLoadBalancer\"", rule, new LoadBalancerStats());

    // Assert
    assertEquals("\"TestLoadBalancer\"", rule.name);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link
   *       ResponseTimeWeightedRule#name} is {@code "TestLoadBalancer"}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenResponseTimeWeightedRuleNameIsTestLoadBalancer2() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("\"TestLoadBalancer\"", rule, new LoadBalancerStats(), null);

    // Assert
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof ResponseTimeWeightedRule);
    assertEquals("\"TestLoadBalancer\"", rule.name);
    assertEquals("\"TestLoadBalancer\"", ((ResponseTimeWeightedRule) rule2).name);
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   *
   * <ul>
   *   <li>Then return LoadBalancerStats ZoneStats size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_thenReturnLoadBalancerStatsZoneStatsSizeIsOne() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server("https://"));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("\"TestLoadBalancer\"", rule, lbStats);

    // Assert
    Map<String, ZoneStats> zoneStats = actualBaseLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("unknown");
    assertEquals("null:unknown", getResult.monitorId);
    assertEquals("unknown", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>Then return Name is {@code Weight adjusting job started}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenReturnNameIsWeightAdjustingJobStarted() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(
            "Weight adjusting job started", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof ResponseTimeWeightedRule);
    assertEquals("Weight adjusting job started", actualBaseLoadBalancer.getName());
    assertEquals("Weight adjusting job started", rule.name);
    assertEquals("Weight adjusting job started", ((ResponseTimeWeightedRule) rule2).name);
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>Then return Rule is {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule,
   * LoadBalancerStats, IPing)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenReturnRuleIsResponseTimeWeightedRule() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(
            "\"TestLoadBalancer\"", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    assertSame(rule, actualBaseLoadBalancer.getRule());
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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   *
   * <ul>
   *   <li>When {@link AvailabilityFilteringRule} (default constructor).
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
  public void testNewBaseLoadBalancer_whenAvailabilityFilteringRule_thenReturnPingIsNull() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("\"TestLoadBalancer\"", rule, new LoadBalancerStats(), null);

    // Assert
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
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
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("\"TestLoadBalancer\"", rule, new LoadBalancerStats());

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
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule2() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer(
            "\"TestLoadBalancer\"", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    assertSame(rule, actualBaseLoadBalancer.getRule());
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
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("\"TestLoadBalancer\"", null, new LoadBalancerStats());

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertEquals("\"TestLoadBalancer\"", actualBaseLoadBalancer.getName());
    assertTrue(actualBaseLoadBalancer.getLoadBalancerStats().getZoneStats().isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
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
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer =
        new BaseLoadBalancer("\"TestLoadBalancer\"", null, new LoadBalancerStats(), ping);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, actualBaseLoadBalancer.getPing());
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
        new PrimeConnections(
            "\"LoadBalancerClient\"", 3, 1L, "\"http://localhost:8080/primeConnections\"");
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
}
