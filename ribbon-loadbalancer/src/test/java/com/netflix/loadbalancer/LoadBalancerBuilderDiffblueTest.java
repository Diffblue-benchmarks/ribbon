package com.netflix.loadbalancer;

import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class LoadBalancerBuilderDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancer()}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.netflix.loadbalancer.ZoneAwareLoadBalancer LoadBalancerBuilder.buildDynamicServerListLoadBalancer()"
  })
  public void testBuildDynamicServerListLoadBalancer_thenThrowIllegalArgumentException() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildDynamicServerListLoadBalancer();
  }

  /**
   * Test {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}.
   *
   * <p>Method under test: {@link
   * LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.netflix.loadbalancer.ZoneAwareLoadBalancer LoadBalancerBuilder.buildDynamicServerListLoadBalancerWithUpdater()"
  })
  public void testBuildDynamicServerListLoadBalancerWithUpdater() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildDynamicServerListLoadBalancerWithUpdater();
  }

  /**
   * Test {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.netflix.loadbalancer.ILoadBalancer LoadBalancerBuilder.buildLoadBalancerFromConfigWithReflection()"
  })
  public void testBuildLoadBalancerFromConfigWithReflection_thenThrowIllegalArgumentException() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildLoadBalancerFromConfigWithReflection();
  }
}
