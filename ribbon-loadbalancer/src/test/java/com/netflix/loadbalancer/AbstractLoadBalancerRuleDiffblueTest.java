package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class AbstractLoadBalancerRuleDiffblueTest {
  /**
   * Method under test:
   * {@link AbstractLoadBalancerRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer() {
    // Arrange
    RandomRule randomRule = new RandomRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    randomRule.setLoadBalancer(lb);

    // Assert
    assertSame(lb, randomRule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link AbstractLoadBalancerRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer2() {
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
   * Method under test:
   * {@link AbstractLoadBalancerRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer3() {
    // Arrange
    RandomRule randomRule = new RandomRule();
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    randomRule.setLoadBalancer(lb);

    // Assert
    assertSame(lb, randomRule.getLoadBalancer());
  }

  /**
   * Method under test: {@link AbstractLoadBalancerRule#getLoadBalancer()}
   */
  @Test
  public void testGetLoadBalancer() {
    // Arrange, Act and Assert
    assertNull((new AvailabilityFilteringRule()).getLoadBalancer());
  }

  /**
   * Method under test: {@link AbstractLoadBalancerRule#getLoadBalancer()}
   */
  @Test
  public void testGetLoadBalancer2() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    availabilityFilteringRule.setLoadBalancer(lb);

    // Act and Assert
    assertSame(lb, availabilityFilteringRule.getLoadBalancer());
  }
}
