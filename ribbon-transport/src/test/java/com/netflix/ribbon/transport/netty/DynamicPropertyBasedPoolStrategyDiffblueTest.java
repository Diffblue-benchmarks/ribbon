package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DynamicPropertyBasedPoolStrategyDiffblueTest {
  /**
   * Test {@link DynamicPropertyBasedPoolStrategy#DynamicPropertyBasedPoolStrategy(int, String)}.
   *
   * <ul>
   *   <li>Then return AvailablePermits is five.
   * </ul>
   *
   * <p>Method under test: {@link
   * DynamicPropertyBasedPoolStrategy#DynamicPropertyBasedPoolStrategy(int, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicPropertyBasedPoolStrategy.<init>(int, String)"})
  public void testNewDynamicPropertyBasedPoolStrategy_thenReturnAvailablePermitsIsFive() {
    // Arrange and Act
    DynamicPropertyBasedPoolStrategy actualDynamicPropertyBasedPoolStrategy =
        new DynamicPropertyBasedPoolStrategy(5, "\"maxConnectionsLimit\"");

    // Assert
    assertEquals(5, actualDynamicPropertyBasedPoolStrategy.getAvailablePermits());
    assertEquals(5, actualDynamicPropertyBasedPoolStrategy.getMaxConnections());
  }

  /**
   * Test {@link DynamicPropertyBasedPoolStrategy#DynamicPropertyBasedPoolStrategy(int, String)}.
   *
   * <ul>
   *   <li>Then return AvailablePermits is three.
   * </ul>
   *
   * <p>Method under test: {@link
   * DynamicPropertyBasedPoolStrategy#DynamicPropertyBasedPoolStrategy(int, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicPropertyBasedPoolStrategy.<init>(int, String)"})
  public void testNewDynamicPropertyBasedPoolStrategy_thenReturnAvailablePermitsIsThree() {
    // Arrange and Act
    DynamicPropertyBasedPoolStrategy actualDynamicPropertyBasedPoolStrategy =
        new DynamicPropertyBasedPoolStrategy(3, "\"maxConnectionsLimit\"");

    // Assert
    assertEquals(3, actualDynamicPropertyBasedPoolStrategy.getAvailablePermits());
    assertEquals(3, actualDynamicPropertyBasedPoolStrategy.getMaxConnections());
  }
}
