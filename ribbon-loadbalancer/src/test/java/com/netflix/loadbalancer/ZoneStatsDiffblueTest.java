package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ZoneStatsDiffblueTest {
  /**
   * Test {@link ZoneStats#ZoneStats(String, String, LoadBalancerStats)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return {@link ZoneStats#monitorId} is {@code Name:Zone}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneStats#ZoneStats(String, String, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneStats.<init>(String, String, LoadBalancerStats)"})
  public void testNewZoneStats_whenName_thenReturnMonitorIdIsNameZone() {
    // Arrange and Act
    ZoneStats<Server> actualZoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Assert
    assertEquals("Name:Zone", actualZoneStats.monitorId);
    assertEquals("Zone", actualZoneStats.getZone());
    assertEquals(0, actualZoneStats.getActiveRequestsCount());
    assertEquals(0, actualZoneStats.getCircuitBreakerTrippedCount());
    assertEquals(0, actualZoneStats.getInstanceCount());
    assertEquals(0.0d, actualZoneStats.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, actualZoneStats.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, actualZoneStats.getMeasuredZoneHits());
  }

  /**
   * Test {@link ZoneStats#ZoneStats(String, String, LoadBalancerStats)}.
   * <ul>
   *   <li>When space.</li>
   *   <li>Then return {@link ZoneStats#monitorId} is {@code :Zone}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneStats#ZoneStats(String, String, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneStats.<init>(String, String, LoadBalancerStats)"})
  public void testNewZoneStats_whenSpace_thenReturnMonitorIdIsZone() {
    // Arrange and Act
    ZoneStats<Server> actualZoneStats = new ZoneStats<>(" ", "Zone", new LoadBalancerStats());

    // Assert
    assertEquals(" :Zone", actualZoneStats.monitorId);
    assertEquals("Zone", actualZoneStats.getZone());
    assertEquals(0, actualZoneStats.getActiveRequestsCount());
    assertEquals(0, actualZoneStats.getCircuitBreakerTrippedCount());
    assertEquals(0, actualZoneStats.getInstanceCount());
    assertEquals(0.0d, actualZoneStats.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, actualZoneStats.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, actualZoneStats.getMeasuredZoneHits());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ZoneStats#toString()}
   *   <li>{@link ZoneStats#getZone()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ZoneStats.getZone()", "String ZoneStats.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act
    String actualToStringResult = zoneStats.toString();

    // Assert
    assertEquals("Zone", zoneStats.getZone());
    assertEquals(
        "[Zone:Zone;\tInstance count:0;\tActive connections count: 0;\tCircuit breaker tripped count: 0;\tActive"
            + " connections per server: 0.0;]\n",
        actualToStringResult);
  }

  /**
   * Test {@link ZoneStats#getActiveRequestsCount()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneStats#getActiveRequestsCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ZoneStats.getActiveRequestsCount()"})
  public void testGetActiveRequestsCount_thenReturnZero() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0, zoneStats.getActiveRequestsCount());
  }

  /**
   * Test {@link ZoneStats#getInstanceCount()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneStats#getInstanceCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ZoneStats.getInstanceCount()"})
  public void testGetInstanceCount_thenReturnZero() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0, zoneStats.getInstanceCount());
  }

  /**
   * Test {@link ZoneStats#getCircuitBreakerTrippedCount()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ZoneStats.getCircuitBreakerTrippedCount()"})
  public void testGetCircuitBreakerTrippedCount_thenReturnZero() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0, zoneStats.getCircuitBreakerTrippedCount());
  }

  /**
   * Test {@link ZoneStats#getActiveRequestsPerServer()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneStats#getActiveRequestsPerServer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"double ZoneStats.getActiveRequestsPerServer()"})
  public void testGetActiveRequestsPerServer_thenReturnZero() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0.0d, zoneStats.getActiveRequestsPerServer(), 0.0);
  }

  /**
   * Test {@link ZoneStats#getMeasuredZoneHits()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneStats#getMeasuredZoneHits()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long ZoneStats.getMeasuredZoneHits()"})
  public void testGetMeasuredZoneHits_thenReturnZero() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0L, zoneStats.getMeasuredZoneHits());
  }

  /**
   * Test {@link ZoneStats#getCircuitBreakerTrippedPercentage()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneStats#getCircuitBreakerTrippedPercentage()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"double ZoneStats.getCircuitBreakerTrippedPercentage()"})
  public void testGetCircuitBreakerTrippedPercentage_thenReturnZero() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0.0d, zoneStats.getCircuitBreakerTrippedPercentage(), 0.0);
  }
}
