package com.netflix.loadbalancer;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class NoOpPingDiffblueTest {
  /**
   * Method under test: {@link NoOpPing#isAlive(Server)}
   */
  @Test
  public void testIsAlive() {
    // Arrange
    NoOpPing noOpPing = new NoOpPing();

    // Act and Assert
    assertTrue(noOpPing.isAlive(new Server("42")));
  }
}
