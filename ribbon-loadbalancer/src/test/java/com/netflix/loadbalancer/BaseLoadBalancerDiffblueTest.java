package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
        new BaseLoadBalancer("Lb Name", rule, new LoadBalancerStats());

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
        new BaseLoadBalancer("Lb Name", rule, new LoadBalancerStats());

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
}
