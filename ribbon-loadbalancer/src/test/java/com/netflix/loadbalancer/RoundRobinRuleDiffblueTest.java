package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RoundRobinRuleDiffblueTest {
  /**
   * Test {@link RoundRobinRule#RoundRobinRule()}.
   *
   * <p>Method under test: {@link RoundRobinRule#RoundRobinRule()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RoundRobinRule.<init>()"})
  public void testNewRoundRobinRule() {
    // Arrange, Act and Assert
    assertNull(new RoundRobinRule().getLoadBalancer());
  }
}
