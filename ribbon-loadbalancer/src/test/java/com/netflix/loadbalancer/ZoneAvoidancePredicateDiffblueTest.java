package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
