package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AvailabilityFilteringRuleDiffblueTest {
  /**
   * Test new {@link AvailabilityFilteringRule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link AvailabilityFilteringRule}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AvailabilityFilteringRule.<init>()"})
  public void testNewAvailabilityFilteringRule() {
    // Arrange and Act
    AvailabilityFilteringRule actualAvailabilityFilteringRule = new AvailabilityFilteringRule();

    // Assert
    AbstractServerPredicate predicate = actualAvailabilityFilteringRule.getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    assertNull(actualAvailabilityFilteringRule.getLoadBalancer());
    assertNull(actualAvailabilityFilteringRule.roundRobinRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
  }

  /**
   * Test {@link AvailabilityFilteringRule#getPredicate()}.
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#getPredicate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractServerPredicate AvailabilityFilteringRule.getPredicate()"})
  public void testGetPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualPredicate = new AvailabilityFilteringRule().getPredicate();

    // Assert
    assertTrue(actualPredicate instanceof CompositePredicate);
    assertNull(((CompositePredicate) actualPredicate).rule);
  }
}
