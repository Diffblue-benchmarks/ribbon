package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AvailabilityPredicateDiffblueTest {
  /**
   * Test {@link AvailabilityPredicate#AvailabilityPredicate(IRule)}.
   * <p>
   * Method under test: {@link AvailabilityPredicate#AvailabilityPredicate(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Given {@link AvailabilityPredicate#AvailabilityPredicate(IRule)} with rule is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AvailabilityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AvailabilityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_givenAvailabilityPredicateWithRuleIsNull() {
    // Arrange
    AvailabilityPredicate availabilityPredicate = new AvailabilityPredicate(null);

    // Act and Assert
    assertTrue(availabilityPredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Test {@link AvailabilityPredicate#apply(PredicateKey)} with {@code PredicateKey}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AvailabilityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AvailabilityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_thenReturnTrue() {
    // Arrange
    AvailabilityPredicate availabilityPredicate = new AvailabilityPredicate(new AvailabilityFilteringRule());

    // Act and Assert
    assertTrue(availabilityPredicate.apply(new PredicateKey(new Server("42"))));
  }
}
