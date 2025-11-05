package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class AbstractLoadBalancerPingDiffblueTest {
  /**
   * Method under test: {@link AbstractLoadBalancerPing#isAlive(Server)}
   */
  @Test
  public void testIsAlive() {
    // Arrange
    DummyPing dummyPing = new DummyPing();

    // Act and Assert
    assertTrue(dummyPing.isAlive(new Server("42")));
  }

  /**
   * Method under test: {@link AbstractLoadBalancerPing#isAlive(Server)}
   */
  @Test
  public void testIsAlive2() {
    // Arrange
    DummyPing dummyPing = new DummyPing();
    IPing ping = mock(IPing.class);
    dummyPing.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act and Assert
    assertTrue(dummyPing.isAlive(new Server("42")));
  }

  /**
   * Method under test:
   * {@link AbstractLoadBalancerPing#setLoadBalancer(AbstractLoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer() {
    // Arrange
    DummyPing dummyPing = new DummyPing();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    dummyPing.setLoadBalancer(lb);

    // Assert
    assertSame(lb, dummyPing.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link AbstractLoadBalancerPing#setLoadBalancer(AbstractLoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer2() {
    // Arrange
    DummyPing dummyPing = new DummyPing();
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    dummyPing.setLoadBalancer(lb);

    // Assert
    assertSame(lb, dummyPing.getLoadBalancer());
  }

  /**
   * Method under test: {@link AbstractLoadBalancerPing#getLoadBalancer()}
   */
  @Test
  public void testGetLoadBalancer() {
    // Arrange, Act and Assert
    assertNull((new DummyPing()).getLoadBalancer());
  }

  /**
   * Method under test: {@link AbstractLoadBalancerPing#getLoadBalancer()}
   */
  @Test
  public void testGetLoadBalancer2() {
    // Arrange
    DummyPing dummyPing = new DummyPing();
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    dummyPing.setLoadBalancer(lb);

    // Act and Assert
    assertSame(lb, dummyPing.getLoadBalancer());
  }
}
