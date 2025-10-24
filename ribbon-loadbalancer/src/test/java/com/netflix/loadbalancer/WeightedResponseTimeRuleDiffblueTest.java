package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class WeightedResponseTimeRuleDiffblueTest {
  /**
   * Test {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.<init>()"})
  public void testNewWeightedResponseTimeRule() {
    // Arrange and Act
    WeightedResponseTimeRule actualWeightedResponseTimeRule = new WeightedResponseTimeRule();

    // Assert
    assertEquals("unknown", actualWeightedResponseTimeRule.name);
    assertNull(actualWeightedResponseTimeRule.getLoadBalancer());
    assertNull(actualWeightedResponseTimeRule.serverWeightTimer);
    assertFalse(actualWeightedResponseTimeRule.serverWeightAssignmentInProgress.get());
    assertTrue(actualWeightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }
}
