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
import com.netflix.client.config.IClientConfig.Builder;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ZoneAvoidancePredicateDiffblueTest {
  /**
   * Test {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule)}.
   *
   * <p>Method under test: {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAvoidancePredicate.<init>(IRule)"})
  public void testNewZoneAvoidancePredicate() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(rule);

    // Assert
    IRule iRule = actualZoneAvoidancePredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualZoneAvoidancePredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Test {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule, IClientConfig)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then EmptyConfig RefreshCount is three.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAvoidancePredicate.<init>(IRule, IClientConfig)"})
  public void testNewZoneAvoidancePredicate_whenEmptyConfig_thenEmptyConfigRefreshCountIsThree() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate =
        new ZoneAvoidancePredicate(rule, clientConfig);

    // Assert
    IRule iRule = actualZoneAvoidancePredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualZoneAvoidancePredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Test {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(LoadBalancerStats, IClientConfig)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then EmptyConfig RefreshCount is three.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(LoadBalancerStats,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAvoidancePredicate.<init>(LoadBalancerStats, IClientConfig)"})
  public void testNewZoneAvoidancePredicate_whenEmptyConfig_thenEmptyConfigRefreshCountIsThree2() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate =
        new ZoneAvoidancePredicate(lbStats, clientConfig);

    // Assert
    assertNull(actualZoneAvoidancePredicate.rule);
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(lbStats, actualZoneAvoidancePredicate.getLBStats());
  }

  /**
   * Test {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule, IClientConfig)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAvoidancePredicate.<init>(IRule, IClientConfig)"})
  public void testNewZoneAvoidancePredicate_whenNull() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(rule, null);

    // Assert
    IRule iRule = actualZoneAvoidancePredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualZoneAvoidancePredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Test {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(LoadBalancerStats, IClientConfig)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(LoadBalancerStats,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAvoidancePredicate.<init>(LoadBalancerStats, IClientConfig)"})
  public void testNewZoneAvoidancePredicate_whenNull2() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(lbStats, null);

    // Assert
    assertNull(actualZoneAvoidancePredicate.rule);
    assertSame(lbStats, actualZoneAvoidancePredicate.getLBStats());
  }

  /**
   * Test {@link ZoneAvoidancePredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <p>Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ZoneAvoidancePredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey() {
    // Arrange
    ZoneAvoidancePredicate zoneAvoidancePredicate =
        new ZoneAvoidancePredicate(new AvailabilityFilteringRule());

    // Act
    boolean actualApplyResult = zoneAvoidancePredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertTrue(actualApplyResult);
  }

  /**
   * Test {@link ZoneAvoidancePredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <p>Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ZoneAvoidancePredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey2() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(rule, clientConfig);

    // Act
    boolean actualApplyResult = zoneAvoidancePredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertTrue(actualApplyResult);
  }

  /**
   * Test {@link ZoneAvoidancePredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <p>Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ZoneAvoidancePredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey3() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ZoneAvoidancePredicate zoneAvoidancePredicate =
        new ZoneAvoidancePredicate(lbStats, clientConfig);

    // Act
    boolean actualApplyResult = zoneAvoidancePredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertTrue(actualApplyResult);
  }

  /**
   * Test {@link ZoneAvoidancePredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <p>Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ZoneAvoidancePredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey4() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    rule.setLoadBalancer(new BaseLoadBalancer());
    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(rule);

    // Act
    boolean actualApplyResult = zoneAvoidancePredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertTrue(actualApplyResult);
  }

  /**
   * Test {@link ZoneAvoidancePredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <ul>
   *   <li>Given {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule)} with rule is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ZoneAvoidancePredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_givenZoneAvoidancePredicateWithRuleIsNull() {
    // Arrange
    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(null);

    // Act
    boolean actualApplyResult = zoneAvoidancePredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertTrue(actualApplyResult);
  }
}
