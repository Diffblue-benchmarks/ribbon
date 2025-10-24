package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RandomRuleDiffblueTest {
  /**
   * Test new {@link RandomRule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link RandomRule}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RandomRule.<init>()"})
  public void testNewRandomRule() {
    // Arrange, Act and Assert
    assertNull(new RandomRule().getLoadBalancer());
  }
}
