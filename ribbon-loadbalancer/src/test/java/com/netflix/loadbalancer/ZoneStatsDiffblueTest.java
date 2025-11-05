package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ZoneStatsDiffblueTest {
  /**
   * Method under test: {@link ZoneStats#getActiveRequestsCount()}
   */
  @Test
  public void testGetActiveRequestsCount() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0, zoneStats.getActiveRequestsCount());
  }

  /**
   * Method under test: {@link ZoneStats#getInstanceCount()}
   */
  @Test
  public void testGetInstanceCount() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0, zoneStats.getInstanceCount());
  }

  /**
   * Method under test: {@link ZoneStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  public void testGetCircuitBreakerTrippedCount() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0, zoneStats.getCircuitBreakerTrippedCount());
  }

  /**
   * Method under test: {@link ZoneStats#getActiveRequestsPerServer()}
   */
  @Test
  public void testGetActiveRequestsPerServer() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0.0d, zoneStats.getActiveRequestsPerServer(), 0.0);
  }

  /**
   * Method under test: {@link ZoneStats#getMeasuredZoneHits()}
   */
  @Test
  public void testGetMeasuredZoneHits() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0L, zoneStats.getMeasuredZoneHits());
  }

  /**
   * Method under test: {@link ZoneStats#getCircuitBreakerTrippedPercentage()}
   */
  @Test
  public void testGetCircuitBreakerTrippedPercentage() {
    // Arrange
    ZoneStats<Server> zoneStats = new ZoneStats<>("Name", "Zone", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0.0d, zoneStats.getCircuitBreakerTrippedPercentage(), 0.0);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ZoneStats#toString()}
   *   <li>{@link ZoneStats#getZone()}
   * </ul>
   */
  @Test
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
   * Method under test:
   * {@link ZoneStats#ZoneStats(String, String, LoadBalancerStats)}
   */
  @Test
  public void testNewZoneStats() {
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
   * Method under test:
   * {@link ZoneStats#ZoneStats(String, String, LoadBalancerStats)}
   */
  @Test
  public void testNewZoneStats2() {
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
}
