package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BestAvailableRuleDiffblueTest {
  /**
   * Test new {@link BestAvailableRule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link BestAvailableRule}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BestAvailableRule.<init>()"})
  public void testNewBestAvailableRule() {
    // Arrange and Act
    BestAvailableRule actualBestAvailableRule = new BestAvailableRule();

    // Assert
    assertNull(actualBestAvailableRule.getLoadBalancer());
    assertNull(actualBestAvailableRule.roundRobinRule.getLoadBalancer());
  }
}
