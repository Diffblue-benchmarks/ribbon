package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractLoadBalancerDiffblueTest {
  /**
   * Test {@link AbstractLoadBalancer#chooseServer()}.
   *
   * <p>Method under test: {@link AbstractLoadBalancer#chooseServer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"com.netflix.loadbalancer.Server AbstractLoadBalancer.chooseServer()"})
  public void testChooseServer() {
    // Arrange, Act and Assert
    assertNull(new BaseLoadBalancer().chooseServer());
  }
}
