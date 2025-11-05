package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class DummyPingDiffblueTest {
  /**
   * Method under test: {@link DummyPing#isAlive(Server)}
   */
  @Test
  public void testIsAlive() {
    // Arrange
    DummyPing dummyPing = new DummyPing();

    // Act and Assert
    assertTrue(dummyPing.isAlive(new Server("42")));
  }

  /**
   * Method under test: {@link DummyPing#isAlive(Server)}
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
   * Method under test: default or parameterless constructor of {@link DummyPing}
   */
  @Test
  public void testNewDummyPing() {
    // Arrange, Act and Assert
    assertNull((new DummyPing()).getLoadBalancer());
  }
}
