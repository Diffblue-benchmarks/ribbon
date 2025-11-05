package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class AbstractLoadBalancerDiffblueTest {
  /**
   * Method under test: {@link AbstractLoadBalancer#chooseServer()}
   */
  @Test
  public void testChooseServer() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).chooseServer());
  }

  /**
   * Method under test: {@link AbstractLoadBalancer#chooseServer()}
   */
  @Test
  public void testChooseServer2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertNull((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).chooseServer());
  }
}
