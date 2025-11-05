package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ZoneSnapshotDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ZoneSnapshot#ZoneSnapshot()}
   *   <li>{@link ZoneSnapshot#toString()}
   *   <li>{@link ZoneSnapshot#getActiveRequestsCount()}
   *   <li>{@link ZoneSnapshot#getCircuitTrippedCount()}
   *   <li>{@link ZoneSnapshot#getInstanceCount()}
   *   <li>{@link ZoneSnapshot#getLoadPerServer()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ZoneSnapshot actualZoneSnapshot = new ZoneSnapshot();
    String actualToStringResult = actualZoneSnapshot.toString();
    int actualActiveRequestsCount = actualZoneSnapshot.getActiveRequestsCount();
    int actualCircuitTrippedCount = actualZoneSnapshot.getCircuitTrippedCount();
    int actualInstanceCount = actualZoneSnapshot.getInstanceCount();

    // Assert
    assertEquals("ZoneSnapshot [instanceCount=0, loadPerServer=0.0, circuitTrippedCount=0, activeRequestsCount=0]",
        actualToStringResult);
    assertEquals(0, actualActiveRequestsCount);
    assertEquals(0, actualCircuitTrippedCount);
    assertEquals(0, actualInstanceCount);
    assertEquals(0.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ZoneSnapshot#ZoneSnapshot(int, int, int, double)}
   *   <li>{@link ZoneSnapshot#toString()}
   *   <li>{@link ZoneSnapshot#getActiveRequestsCount()}
   *   <li>{@link ZoneSnapshot#getCircuitTrippedCount()}
   *   <li>{@link ZoneSnapshot#getInstanceCount()}
   *   <li>{@link ZoneSnapshot#getLoadPerServer()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters2() {
    // Arrange and Act
    ZoneSnapshot actualZoneSnapshot = new ZoneSnapshot(3, 3, 3, 10.0d);
    String actualToStringResult = actualZoneSnapshot.toString();
    int actualActiveRequestsCount = actualZoneSnapshot.getActiveRequestsCount();
    int actualCircuitTrippedCount = actualZoneSnapshot.getCircuitTrippedCount();
    int actualInstanceCount = actualZoneSnapshot.getInstanceCount();

    // Assert
    assertEquals("ZoneSnapshot [instanceCount=3, loadPerServer=10.0, circuitTrippedCount=3, activeRequestsCount=3]",
        actualToStringResult);
    assertEquals(10.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
    assertEquals(3, actualActiveRequestsCount);
    assertEquals(3, actualCircuitTrippedCount);
    assertEquals(3, actualInstanceCount);
  }
}
