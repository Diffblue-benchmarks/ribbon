package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
