package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ResponseTimeWeightedRuleDiffblueTest {
  /**
   * Test {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.<init>()"})
  public void testNewResponseTimeWeightedRule() {
    // Arrange and Act
    ResponseTimeWeightedRule actualResponseTimeWeightedRule = new ResponseTimeWeightedRule();

    // Assert
    assertEquals("unknown", actualResponseTimeWeightedRule.name);
    assertNull(actualResponseTimeWeightedRule.getLoadBalancer());
    assertNull(actualResponseTimeWeightedRule.serverWeightTimer);
    assertFalse(actualResponseTimeWeightedRule.serverWeightAssignmentInProgress.get());
  }
}
