package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DynamicPropertyBasedPoolStrategyDiffblueTest {
  /**
   * Method under test:
   * {@link DynamicPropertyBasedPoolStrategy#DynamicPropertyBasedPoolStrategy(int, String)}
   */
  @Test
  public void testNewDynamicPropertyBasedPoolStrategy() {
    // Arrange and Act
    DynamicPropertyBasedPoolStrategy actualDynamicPropertyBasedPoolStrategy = new DynamicPropertyBasedPoolStrategy(3,
        "Property Name");

    // Assert
    assertEquals(3, actualDynamicPropertyBasedPoolStrategy.getAvailablePermits());
    assertEquals(3, actualDynamicPropertyBasedPoolStrategy.getMaxConnections());
  }

  /**
   * Method under test:
   * {@link DynamicPropertyBasedPoolStrategy#DynamicPropertyBasedPoolStrategy(int, String)}
   */
  @Test
  public void testNewDynamicPropertyBasedPoolStrategy2() {
    // Arrange and Act
    DynamicPropertyBasedPoolStrategy actualDynamicPropertyBasedPoolStrategy = new DynamicPropertyBasedPoolStrategy(5,
        "Property Name");

    // Assert
    assertEquals(5, actualDynamicPropertyBasedPoolStrategy.getAvailablePermits());
    assertEquals(5, actualDynamicPropertyBasedPoolStrategy.getMaxConnections());
  }
}
