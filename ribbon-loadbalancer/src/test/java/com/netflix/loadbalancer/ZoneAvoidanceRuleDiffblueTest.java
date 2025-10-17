package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ZoneAvoidanceRuleDiffblueTest {
  /**
   * Test new {@link ZoneAvoidanceRule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ZoneAvoidanceRule}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAvoidanceRule.<init>()"})
  public void testNewZoneAvoidanceRule() {
    // Arrange and Act
    ZoneAvoidanceRule actualZoneAvoidanceRule = new ZoneAvoidanceRule();

    // Assert
    AbstractServerPredicate predicate = actualZoneAvoidanceRule.getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    assertNull(actualZoneAvoidanceRule.getLoadBalancer());
    assertNull(actualZoneAvoidanceRule.roundRobinRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
  }

  /**
   * Test {@link ZoneAvoidanceRule#getPredicate()}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getPredicate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractServerPredicate ZoneAvoidanceRule.getPredicate()"})
  public void testGetPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualPredicate = new ZoneAvoidanceRule().getPredicate();

    // Assert
    assertTrue(actualPredicate instanceof CompositePredicate);
    assertNull(((CompositePredicate) actualPredicate).rule);
  }
}
