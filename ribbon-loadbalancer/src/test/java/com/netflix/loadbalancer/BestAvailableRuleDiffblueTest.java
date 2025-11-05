package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class BestAvailableRuleDiffblueTest {
  /**
   * Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  public void testChoose() {
    // Arrange, Act and Assert
    assertNull((new BestAvailableRule()).choose("Key"));
  }

  /**
   * Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  public void testChoose2() {
    // Arrange
    BestAvailableRule bestAvailableRule = new BestAvailableRule();
    bestAvailableRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(bestAvailableRule.choose("Key"));
  }

  /**
   * Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  public void testChoose3() {
    // Arrange
    BestAvailableRule bestAvailableRule = new BestAvailableRule();
    IPing ping = mock(IPing.class);
    bestAvailableRule.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act and Assert
    assertNull(bestAvailableRule.choose("Key"));
  }

  /**
   * Method under test: {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer() {
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
   * Method under test: {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer2() {
    // Arrange
    BestAvailableRule bestAvailableRule = new BestAvailableRule();

    // Act
    bestAvailableRule.setLoadBalancer(null);

    // Assert
    assertNull(bestAvailableRule.getLoadBalancer());
    assertNull(bestAvailableRule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Method under test: {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer3() {
    // Arrange
    BestAvailableRule bestAvailableRule = new BestAvailableRule();
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    bestAvailableRule.setLoadBalancer(lb);

    // Assert
    assertSame(lb, bestAvailableRule.getLoadBalancer());
    assertSame(lb, bestAvailableRule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link BestAvailableRule}
   */
  @Test
  public void testNewBestAvailableRule() {
    // Arrange and Act
    BestAvailableRule actualBestAvailableRule = new BestAvailableRule();

    // Assert
    assertNull(actualBestAvailableRule.getLoadBalancer());
    assertNull(actualBestAvailableRule.roundRobinRule.getLoadBalancer());
  }
}
