package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractLoadBalancerRuleDiffblueTest {
  /**
   * Test {@link AbstractLoadBalancerRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given {@link RandomRule} (default constructor).
   *   <li>Then {@link RandomRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractLoadBalancerRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractLoadBalancerRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_givenRandomRule_thenRandomRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    RandomRule randomRule = new RandomRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    randomRule.setLoadBalancer(lb);

    // Assert
    assertSame(lb, randomRule.getLoadBalancer());
  }

  /**
   * Test {@link AbstractLoadBalancerRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link AbstractLoadBalancerRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractLoadBalancerRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    availabilityFilteringRule.setLoadBalancer(lb);

    // Assert
    assertEquals(0, availabilityFilteringRule.getAvailableServersCount());
    assertSame(lb, availabilityFilteringRule.getLoadBalancer());
    assertSame(lb, availabilityFilteringRule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link AbstractLoadBalancerRule#getLoadBalancer()}.
   *
   * <p>Method under test: {@link AbstractLoadBalancerRule#getLoadBalancer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ILoadBalancer AbstractLoadBalancerRule.getLoadBalancer()"})
  public void testGetLoadBalancer() {
    // Arrange, Act and Assert
    assertNull(new AvailabilityFilteringRule().getLoadBalancer());
  }
}
