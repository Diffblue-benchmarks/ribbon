package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AvailabilityPredicateDiffblueTest {
  /**
   * Test {@link AvailabilityPredicate#AvailabilityPredicate(IRule)}.
   *
   * <p>Method under test: {@link AvailabilityPredicate#AvailabilityPredicate(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AvailabilityPredicate.<init>(IRule)"})
  public void testNewAvailabilityPredicate() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(rule);

    // Assert
    IRule iRule = actualAvailabilityPredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualAvailabilityPredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Test {@link AvailabilityPredicate#AvailabilityPredicate(IRule, IClientConfig)}.
   *
   * <p>Method under test: {@link AvailabilityPredicate#AvailabilityPredicate(IRule, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AvailabilityPredicate.<init>(IRule, IClientConfig)"})
  public void testNewAvailabilityPredicate2() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "[{}] get global property '{}' with default '{}'");

    // Act
    AvailabilityPredicate actualAvailabilityPredicate =
        new AvailabilityPredicate(rule, clientConfig);

    // Assert
    IRule iRule = actualAvailabilityPredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualAvailabilityPredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Test {@link AvailabilityPredicate#AvailabilityPredicate(LoadBalancerStats, IClientConfig)}.
   *
   * <p>Method under test: {@link AvailabilityPredicate#AvailabilityPredicate(LoadBalancerStats,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AvailabilityPredicate.<init>(LoadBalancerStats, IClientConfig)"})
  public void testNewAvailabilityPredicate3() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "[{}] get global property '{}' with default '{}'");

    // Act
    AvailabilityPredicate actualAvailabilityPredicate =
        new AvailabilityPredicate(lbStats, clientConfig);

    // Assert
    assertNull(actualAvailabilityPredicate.rule);
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(lbStats, actualAvailabilityPredicate.getLBStats());
  }

  /**
   * Test {@link AvailabilityPredicate#AvailabilityPredicate(IRule, IClientConfig)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then EmptyConfig RefreshCount is three.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityPredicate#AvailabilityPredicate(IRule, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AvailabilityPredicate.<init>(IRule, IClientConfig)"})
  public void testNewAvailabilityPredicate_whenEmptyConfig_thenEmptyConfigRefreshCountIsThree() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    AvailabilityPredicate actualAvailabilityPredicate =
        new AvailabilityPredicate(rule, clientConfig);

    // Assert
    IRule iRule = actualAvailabilityPredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualAvailabilityPredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Test {@link AvailabilityPredicate#AvailabilityPredicate(LoadBalancerStats, IClientConfig)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then EmptyConfig RefreshCount is three.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityPredicate#AvailabilityPredicate(LoadBalancerStats,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AvailabilityPredicate.<init>(LoadBalancerStats, IClientConfig)"})
  public void testNewAvailabilityPredicate_whenEmptyConfig_thenEmptyConfigRefreshCountIsThree2() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    AvailabilityPredicate actualAvailabilityPredicate =
        new AvailabilityPredicate(lbStats, clientConfig);

    // Assert
    assertNull(actualAvailabilityPredicate.rule);
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(lbStats, actualAvailabilityPredicate.getLBStats());
  }

  /**
   * Test {@link AvailabilityPredicate#AvailabilityPredicate(IRule, IClientConfig)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityPredicate#AvailabilityPredicate(IRule, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AvailabilityPredicate.<init>(IRule, IClientConfig)"})
  public void testNewAvailabilityPredicate_whenNull() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(rule, null);

    // Assert
    IRule iRule = actualAvailabilityPredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualAvailabilityPredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Test {@link AvailabilityPredicate#AvailabilityPredicate(LoadBalancerStats, IClientConfig)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityPredicate#AvailabilityPredicate(LoadBalancerStats,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AvailabilityPredicate.<init>(LoadBalancerStats, IClientConfig)"})
  public void testNewAvailabilityPredicate_whenNull2() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(lbStats, null);

    // Assert
    assertNull(actualAvailabilityPredicate.rule);
    assertSame(lbStats, actualAvailabilityPredicate.getLBStats());
  }

  /**
   * Test {@link AvailabilityPredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <p>Method under test: {@link AvailabilityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AvailabilityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    rule.setLoadBalancer(new NoOpLoadBalancer());
    AvailabilityPredicate availabilityPredicate = new AvailabilityPredicate(rule);

    // Act
    boolean actualApplyResult = availabilityPredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertTrue(actualApplyResult);
  }

  /**
   * Test {@link AvailabilityPredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <ul>
   *   <li>Given {@link AvailabilityPredicate#AvailabilityPredicate(IRule)} with rule is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AvailabilityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_givenAvailabilityPredicateWithRuleIsNull() {
    // Arrange
    AvailabilityPredicate availabilityPredicate = new AvailabilityPredicate(null);

    // Act
    boolean actualApplyResult = availabilityPredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertTrue(actualApplyResult);
  }

  /**
   * Test {@link AvailabilityPredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AvailabilityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_thenReturnTrue() {
    // Arrange
    AvailabilityPredicate availabilityPredicate =
        new AvailabilityPredicate(new AvailabilityFilteringRule());

    // Act
    boolean actualApplyResult = availabilityPredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertTrue(actualApplyResult);
  }
}
