package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractLoadBalancerDiffblueTest {
  /**
   * Test {@link AbstractLoadBalancer#chooseServer()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractLoadBalancer#chooseServer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"com.netflix.loadbalancer.Server AbstractLoadBalancer.chooseServer()"})
  public void testChooseServer_givenBaseLoadBalancer_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).chooseServer());
  }
}
