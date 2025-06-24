package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DynamicPropertyBasedPoolStrategyDiffblueTest {
  /**
   * Test {@link DynamicPropertyBasedPoolStrategy#DynamicPropertyBasedPoolStrategy(int, String)}.
   * <ul>
   *   <li>Then return AvailablePermits is five.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicPropertyBasedPoolStrategy#DynamicPropertyBasedPoolStrategy(int, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicPropertyBasedPoolStrategy.<init>(int, String)"})
  public void testNewDynamicPropertyBasedPoolStrategy_thenReturnAvailablePermitsIsFive() {
    // Arrange and Act
    DynamicPropertyBasedPoolStrategy actualDynamicPropertyBasedPoolStrategy = new DynamicPropertyBasedPoolStrategy(5,
        "Property Name");

    // Assert
    assertEquals(5, actualDynamicPropertyBasedPoolStrategy.getAvailablePermits());
    assertEquals(5, actualDynamicPropertyBasedPoolStrategy.getMaxConnections());
  }

  /**
   * Test {@link DynamicPropertyBasedPoolStrategy#DynamicPropertyBasedPoolStrategy(int, String)}.
   * <ul>
   *   <li>Then return AvailablePermits is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicPropertyBasedPoolStrategy#DynamicPropertyBasedPoolStrategy(int, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicPropertyBasedPoolStrategy.<init>(int, String)"})
  public void testNewDynamicPropertyBasedPoolStrategy_thenReturnAvailablePermitsIsThree() {
    // Arrange and Act
    DynamicPropertyBasedPoolStrategy actualDynamicPropertyBasedPoolStrategy = new DynamicPropertyBasedPoolStrategy(3,
        "Property Name");

    // Assert
    assertEquals(3, actualDynamicPropertyBasedPoolStrategy.getAvailablePermits());
    assertEquals(3, actualDynamicPropertyBasedPoolStrategy.getMaxConnections());
  }
}
