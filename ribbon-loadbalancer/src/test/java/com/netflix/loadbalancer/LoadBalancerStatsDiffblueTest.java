package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.UnboxedIntProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LoadBalancerStatsDiffblueTest {
  /**
   * Test {@link LoadBalancerStats#LoadBalancerStats()}.
   *
   * <p>Method under test: {@link LoadBalancerStats#LoadBalancerStats()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerStats.<init>()"})
  public void testNewLoadBalancerStats() {
    // Arrange and Act
    LoadBalancerStats actualLoadBalancerStats = new LoadBalancerStats();

    // Assert
    assertNull(actualLoadBalancerStats.getName());
    assertEquals(0, actualLoadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, actualLoadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, actualLoadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, actualLoadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, actualLoadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(actualLoadBalancerStats.getServerStats().isEmpty());
    assertTrue(actualLoadBalancerStats.getZoneStats().isEmpty());
    assertTrue(actualLoadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(actualLoadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link LoadBalancerStats#LoadBalancerStats(String)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then return {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#LoadBalancerStats(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerStats.<init>(String)"})
  public void testNewLoadBalancerStats_whenName_thenReturnName() {
    // Arrange and Act
    LoadBalancerStats actualLoadBalancerStats = new LoadBalancerStats("Name");

    // Assert
    assertEquals("Name", actualLoadBalancerStats.getName());
    assertEquals(0, actualLoadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, actualLoadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, actualLoadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, actualLoadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, actualLoadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(actualLoadBalancerStats.getServerStats().isEmpty());
    assertTrue(actualLoadBalancerStats.getZoneStats().isEmpty());
    assertTrue(actualLoadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(actualLoadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link LoadBalancerStats#setName(String)}
   *   <li>{@link LoadBalancerStats#toString()}
   *   <li>{@link LoadBalancerStats#getActiveRequestsCountTimeout()}
   *   <li>{@link LoadBalancerStats#getCircuitTripMaxTimeoutSeconds()}
   *   <li>{@link LoadBalancerStats#getCircuitTrippedTimeoutFactor()}
   *   <li>{@link LoadBalancerStats#getConnectionFailureCountThreshold()}
   *   <li>{@link LoadBalancerStats#getName()}
   *   <li>{@link LoadBalancerStats#getZoneStats()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UnboxedIntProperty LoadBalancerStats.getActiveRequestsCountTimeout()",
    "UnboxedIntProperty LoadBalancerStats.getCircuitTripMaxTimeoutSeconds()",
    "UnboxedIntProperty LoadBalancerStats.getCircuitTrippedTimeoutFactor()",
    "UnboxedIntProperty LoadBalancerStats.getConnectionFailureCountThreshold()",
    "String LoadBalancerStats.getName()",
    "java.util.Map LoadBalancerStats.getZoneStats()",
    "void LoadBalancerStats.setName(String)",
    "String LoadBalancerStats.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    // Act
    loadBalancerStats.setName("Name");
    String actualToStringResult = loadBalancerStats.toString();
    UnboxedIntProperty actualActiveRequestsCountTimeout =
        loadBalancerStats.getActiveRequestsCountTimeout();
    UnboxedIntProperty actualCircuitTripMaxTimeoutSeconds =
        loadBalancerStats.getCircuitTripMaxTimeoutSeconds();
    UnboxedIntProperty actualCircuitTrippedTimeoutFactor =
        loadBalancerStats.getCircuitTrippedTimeoutFactor();
    UnboxedIntProperty actualConnectionFailureCountThreshold =
        loadBalancerStats.getConnectionFailureCountThreshold();
    String actualName = loadBalancerStats.getName();

    // Assert
    assertEquals("Name", actualName);
    assertEquals("Zone stats: {},Server stats: []", actualToStringResult);
    assertEquals(10, actualCircuitTrippedTimeoutFactor.get());
    assertEquals(3, actualConnectionFailureCountThreshold.get());
    assertEquals(30, actualCircuitTripMaxTimeoutSeconds.get());
    assertEquals(600, actualActiveRequestsCountTimeout.get());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
  }

  /**
   * Test {@link LoadBalancerStats#getActiveRequestsCount(String)}.
   *
   * <p>Method under test: {@link LoadBalancerStats#getActiveRequestsCount(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getActiveRequestsCount(String)"})
  public void testGetActiveRequestsCount() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("Name");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getActiveRequestsCount(null));
  }

  /**
   * Test {@link LoadBalancerStats#getActiveRequestsCount(String)}.
   *
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>When {@code Zone}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getActiveRequestsCount(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getActiveRequestsCount(String)"})
  public void testGetActiveRequestsCount_givenLoadBalancerStats_whenZone() {
    // Arrange, Act and Assert
    assertEquals(0, new LoadBalancerStats().getActiveRequestsCount("Zone"));
  }

  /**
   * Test {@link LoadBalancerStats#getActiveRequestsPerServer(String)}.
   *
   * <p>Method under test: {@link LoadBalancerStats#getActiveRequestsPerServer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double LoadBalancerStats.getActiveRequestsPerServer(String)"})
  public void testGetActiveRequestsPerServer() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("Name");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0.0d, loadBalancerStats.getActiveRequestsPerServer(null), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getActiveRequestsPerServer(String)}.
   *
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>When {@code Zone}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getActiveRequestsPerServer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double LoadBalancerStats.getActiveRequestsPerServer(String)"})
  public void testGetActiveRequestsPerServer_givenLoadBalancerStats_whenZone() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new LoadBalancerStats().getActiveRequestsPerServer("Zone"), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getCircuitBreakerTrippedCount()"})
  public void testGetCircuitBreakerTrippedCount_givenHashMapFooIsArrayList_thenReturnZero() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("foo", new ArrayList<>());

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
  }

  /**
   * Test {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}.
   *
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getCircuitBreakerTrippedCount()"})
  public void testGetCircuitBreakerTrippedCount_givenLoadBalancerStats_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, new LoadBalancerStats().getCircuitBreakerTrippedCount());
  }

  /**
   * Test {@link LoadBalancerStats#getAvailableZones()}.
   *
   * <p>Method under test: {@link LoadBalancerStats#getAvailableZones()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Set LoadBalancerStats.getAvailableZones()"})
  public void testGetAvailableZones() {
    // Arrange, Act and Assert
    assertTrue(new LoadBalancerStats().getAvailableZones().isEmpty());
  }
}
