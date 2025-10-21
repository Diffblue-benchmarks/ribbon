package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ZoneStatsDiffblueTest {
  /**
   * Test {@link ZoneStats#ZoneStats(String, String, LoadBalancerStats)}.
   *
   * <ul>
   *   <li>When space.
   *   <li>Then return {@link ZoneStats#monitorId} is {@code :"us-east-1a"}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneStats#ZoneStats(String, String, LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneStats.<init>(String, String, LoadBalancerStats)"})
  public void testNewZoneStats_whenSpace_thenReturnMonitorIdIsUsEast1a() {
    // Arrange and Act
    ZoneStats<Server> actualZoneStats =
        new ZoneStats<>(" ", "\"us-east-1a\"", new LoadBalancerStats());

    // Assert
    assertEquals(" :\"us-east-1a\"", actualZoneStats.monitorId);
    assertEquals("\"us-east-1a\"", actualZoneStats.getZone());
    assertEquals(0, actualZoneStats.getActiveRequestsCount());
    assertEquals(0, actualZoneStats.getCircuitBreakerTrippedCount());
    assertEquals(0, actualZoneStats.getInstanceCount());
    assertEquals(0.0d, actualZoneStats.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, actualZoneStats.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, actualZoneStats.getMeasuredZoneHits());
  }

  /**
   * Test {@link ZoneStats#ZoneStats(String, String, LoadBalancerStats)}.
   *
   * <ul>
   *   <li>When {@code "US-East-1a"}.
   *   <li>Then return {@link ZoneStats#monitorId} is {@code "US-East-1a":"us-east-1a"}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneStats#ZoneStats(String, String, LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneStats.<init>(String, String, LoadBalancerStats)"})
  public void testNewZoneStats_whenUsEast1a_thenReturnMonitorIdIsUsEast1aUsEast1a() {
    // Arrange and Act
    ZoneStats<Server> actualZoneStats =
        new ZoneStats<>("\"US-East-1a\"", "\"us-east-1a\"", new LoadBalancerStats());

    // Assert
    assertEquals("\"US-East-1a\":\"us-east-1a\"", actualZoneStats.monitorId);
    assertEquals("\"us-east-1a\"", actualZoneStats.getZone());
    assertEquals(0, actualZoneStats.getActiveRequestsCount());
    assertEquals(0, actualZoneStats.getCircuitBreakerTrippedCount());
    assertEquals(0, actualZoneStats.getInstanceCount());
    assertEquals(0.0d, actualZoneStats.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, actualZoneStats.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, actualZoneStats.getMeasuredZoneHits());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ZoneStats#toString()}
   *   <li>{@link ZoneStats#getZone()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ZoneStats.getZone()", "String ZoneStats.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    ZoneStats<Server> zoneStats =
        new ZoneStats<>("\"US-East-1a\"", "\"us-east-1a\"", new LoadBalancerStats());

    // Act
    String actualToStringResult = zoneStats.toString();

    // Assert
    assertEquals(
        "[Zone:\"us-east-1a\";\tInstance count:0;\tActive connections count: 0;\tCircuit breaker tripped count: 0;"
            + "\tActive connections per server: 0.0;]\n",
        actualToStringResult);
    assertEquals("\"us-east-1a\"", zoneStats.getZone());
  }

  /**
   * Test {@link ZoneStats#getActiveRequestsCount()}.
   *
   * <p>Method under test: {@link ZoneStats#getActiveRequestsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ZoneStats.getActiveRequestsCount()"})
  public void testGetActiveRequestsCount() {
    // Arrange
    ZoneStats<Server> zoneStats =
        new ZoneStats<>("\"US-East-1a\"", "\"us-east-1a\"", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0, zoneStats.getActiveRequestsCount());
  }

  /**
   * Test {@link ZoneStats#getActiveRequestsCount()}.
   *
   * <p>Method under test: {@link ZoneStats#getActiveRequestsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ZoneStats.getActiveRequestsCount()"})
  public void testGetActiveRequestsCount2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());
    ZoneStats<Server> zoneStats = new ZoneStats<>("\"US-East-1a\"", null, loadBalancerStats);

    // Act and Assert
    assertEquals(0, zoneStats.getActiveRequestsCount());
  }

  /**
   * Test {@link ZoneStats#getInstanceCount()}.
   *
   * <p>Method under test: {@link ZoneStats#getInstanceCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ZoneStats.getInstanceCount()"})
  public void testGetInstanceCount() {
    // Arrange
    ZoneStats<Server> zoneStats =
        new ZoneStats<>("\"US-East-1a\"", "\"us-east-1a\"", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0, zoneStats.getInstanceCount());
  }

  /**
   * Test {@link ZoneStats#getInstanceCount()}.
   *
   * <p>Method under test: {@link ZoneStats#getInstanceCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ZoneStats.getInstanceCount()"})
  public void testGetInstanceCount2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());
    ZoneStats<Server> zoneStats = new ZoneStats<>("\"US-East-1a\"", null, loadBalancerStats);

    // Act and Assert
    assertEquals(0, zoneStats.getInstanceCount());
  }

  /**
   * Test {@link ZoneStats#getCircuitBreakerTrippedCount()}.
   *
   * <p>Method under test: {@link ZoneStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ZoneStats.getCircuitBreakerTrippedCount()"})
  public void testGetCircuitBreakerTrippedCount() {
    // Arrange
    ZoneStats<Server> zoneStats =
        new ZoneStats<>("\"US-East-1a\"", "\"us-east-1a\"", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0, zoneStats.getCircuitBreakerTrippedCount());
  }

  /**
   * Test {@link ZoneStats#getCircuitBreakerTrippedCount()}.
   *
   * <p>Method under test: {@link ZoneStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ZoneStats.getCircuitBreakerTrippedCount()"})
  public void testGetCircuitBreakerTrippedCount2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());
    ZoneStats<Server> zoneStats = new ZoneStats<>("\"US-East-1a\"", null, loadBalancerStats);

    // Act and Assert
    assertEquals(0, zoneStats.getCircuitBreakerTrippedCount());
  }

  /**
   * Test {@link ZoneStats#getActiveRequestsPerServer()}.
   *
   * <p>Method under test: {@link ZoneStats#getActiveRequestsPerServer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ZoneStats.getActiveRequestsPerServer()"})
  public void testGetActiveRequestsPerServer() {
    // Arrange
    ZoneStats<Server> zoneStats =
        new ZoneStats<>("\"US-East-1a\"", "\"us-east-1a\"", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0.0d, zoneStats.getActiveRequestsPerServer(), 0.0);
  }

  /**
   * Test {@link ZoneStats#getActiveRequestsPerServer()}.
   *
   * <p>Method under test: {@link ZoneStats#getActiveRequestsPerServer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ZoneStats.getActiveRequestsPerServer()"})
  public void testGetActiveRequestsPerServer2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());
    ZoneStats<Server> zoneStats = new ZoneStats<>("\"US-East-1a\"", null, loadBalancerStats);

    // Act and Assert
    assertEquals(0.0d, zoneStats.getActiveRequestsPerServer(), 0.0);
  }

  /**
   * Test {@link ZoneStats#getMeasuredZoneHits()}.
   *
   * <p>Method under test: {@link ZoneStats#getMeasuredZoneHits()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ZoneStats.getMeasuredZoneHits()"})
  public void testGetMeasuredZoneHits() {
    // Arrange
    ZoneStats<Server> zoneStats =
        new ZoneStats<>("\"US-East-1a\"", "\"us-east-1a\"", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0L, zoneStats.getMeasuredZoneHits());
  }

  /**
   * Test {@link ZoneStats#getMeasuredZoneHits()}.
   *
   * <p>Method under test: {@link ZoneStats#getMeasuredZoneHits()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ZoneStats.getMeasuredZoneHits()"})
  public void testGetMeasuredZoneHits2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());
    ZoneStats<Server> zoneStats = new ZoneStats<>("\"US-East-1a\"", null, loadBalancerStats);

    // Act and Assert
    assertEquals(0L, zoneStats.getMeasuredZoneHits());
  }

  /**
   * Test {@link ZoneStats#getCircuitBreakerTrippedPercentage()}.
   *
   * <p>Method under test: {@link ZoneStats#getCircuitBreakerTrippedPercentage()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ZoneStats.getCircuitBreakerTrippedPercentage()"})
  public void testGetCircuitBreakerTrippedPercentage() {
    // Arrange
    ZoneStats<Server> zoneStats =
        new ZoneStats<>("\"US-East-1a\"", "\"us-east-1a\"", new LoadBalancerStats());

    // Act and Assert
    assertEquals(0.0d, zoneStats.getCircuitBreakerTrippedPercentage(), 0.0);
  }

  /**
   * Test {@link ZoneStats#getCircuitBreakerTrippedPercentage()}.
   *
   * <p>Method under test: {@link ZoneStats#getCircuitBreakerTrippedPercentage()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ZoneStats.getCircuitBreakerTrippedPercentage()"})
  public void testGetCircuitBreakerTrippedPercentage2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());
    ZoneStats<Server> zoneStats = new ZoneStats<>("\"US-East-1a\"", null, loadBalancerStats);

    // Act and Assert
    assertEquals(0.0d, zoneStats.getCircuitBreakerTrippedPercentage(), 0.0);
  }
}
