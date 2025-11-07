package com.netflix.loadbalancer;

import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class LoadBalancerBuilderDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancer()}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "com.netflix.loadbalancer.ZoneAwareLoadBalancer LoadBalancerBuilder.buildDynamicServerListLoadBalancer()"})
  public void testBuildDynamicServerListLoadBalancer_thenThrowIllegalArgumentException() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildDynamicServerListLoadBalancer();
  }

  /**
   * Test {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}.
   * <p>
   * Method under test: {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "com.netflix.loadbalancer.ZoneAwareLoadBalancer LoadBalancerBuilder.buildDynamicServerListLoadBalancerWithUpdater()"})
  public void testBuildDynamicServerListLoadBalancerWithUpdater() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildDynamicServerListLoadBalancerWithUpdater();
  }

  /**
   * Test {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}.
   * <ul>
   *   <li>Given newBuilder.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "com.netflix.loadbalancer.ILoadBalancer LoadBalancerBuilder.buildLoadBalancerFromConfigWithReflection()"})
  public void testBuildLoadBalancerFromConfigWithReflection_givenNewBuilder() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildLoadBalancerFromConfigWithReflection();
  }
}
