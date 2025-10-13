package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BestAvailableRuleDiffblueTest {
  /**
   * Test {@link BestAvailableRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link BestAvailableRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"com.netflix.loadbalancer.Server BestAvailableRule.choose(Object)"})
  public void testChoose_givenBestAvailableRuleLoadBalancerIsBaseLoadBalancer_thenReturnNull() {
    // Arrange
    BestAvailableRule bestAvailableRule = new BestAvailableRule();
    bestAvailableRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(bestAvailableRule.choose("Key"));
  }

  /**
   * Test {@link BestAvailableRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link BestAvailableRule} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"com.netflix.loadbalancer.Server BestAvailableRule.choose(Object)"})
  public void testChoose_givenBestAvailableRule_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new BestAvailableRule().choose("Key"));
  }

  /**
   * Test {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Then {@link BestAvailableRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BestAvailableRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_thenBestAvailableRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    BestAvailableRule bestAvailableRule = new BestAvailableRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    bestAvailableRule.setLoadBalancer(lb);

    // Assert
    assertSame(lb, bestAvailableRule.getLoadBalancer());
    assertSame(lb, bestAvailableRule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link BestAvailableRule} (default constructor) LoadBalancer is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BestAvailableRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_whenNull_thenBestAvailableRuleLoadBalancerIsNull() {
    // Arrange
    BestAvailableRule bestAvailableRule = new BestAvailableRule();

    // Act
    bestAvailableRule.setLoadBalancer(null);

    // Assert that nothing has changed
    assertNull(bestAvailableRule.getLoadBalancer());
    assertNull(bestAvailableRule.roundRobinRule.getLoadBalancer());
  }

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
