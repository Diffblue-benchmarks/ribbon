package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.UnboxedIntProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
   *   <li>Then return Name is {@code "TestLoadBalancerStats"}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#LoadBalancerStats(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerStats.<init>(String)"})
  public void testNewLoadBalancerStats_thenReturnNameIsTestLoadBalancerStats() {
    // Arrange and Act
    LoadBalancerStats actualLoadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");

    // Assert
    assertEquals("\"TestLoadBalancerStats\"", actualLoadBalancerStats.getName());
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
    "Map LoadBalancerStats.getZoneStats()",
    "void LoadBalancerStats.setName(String)",
    "String LoadBalancerStats.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    // Act
    loadBalancerStats.setName("\"LoadBalancerStats_US_East_Region\"");
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
    assertEquals("Zone stats: {},Server stats: []", actualToStringResult);
    assertEquals("\"LoadBalancerStats_US_East_Region\"", actualName);
    assertEquals(10, actualCircuitTrippedTimeoutFactor.get());
    assertEquals(3, actualConnectionFailureCountThreshold.get());
    assertEquals(30, actualCircuitTripMaxTimeoutSeconds.get());
    assertEquals(600, actualActiveRequestsCountTimeout.get());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
  }

  /**
   * Test {@link LoadBalancerStats#getServerStats()}.
   *
   * <p>Method under test: {@link LoadBalancerStats#getServerStats()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map LoadBalancerStats.getServerStats()"})
  public void testGetServerStats() {
    // Arrange, Act and Assert
    assertTrue(new LoadBalancerStats().getServerStats().isEmpty());
  }

  /**
   * Test {@link LoadBalancerStats#getServerStats(Server)} with {@code Server}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getServerStats(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServerStats LoadBalancerStats.getServerStats(Server)"})
  public void testGetServerStatsWithServer_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new LoadBalancerStats().getServerStats(null));
  }

  /**
   * Test {@link LoadBalancerStats#incrementZoneCounter(Server)}.
   *
   * <p>Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerStats.incrementZoneCounter(Server)"})
  public void testIncrementZoneCounter() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    // Act
    loadBalancerStats.incrementZoneCounter(new Server("42"));

    // Assert
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("unknown");
    assertEquals("null:unknown", getResult.monitorId);
    assertEquals("unknown", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link LoadBalancerStats#incrementZoneCounter(Server)}.
   *
   * <p>Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerStats.incrementZoneCounter(Server)"})
  public void testIncrementZoneCounter2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.incrementZoneCounter(new Server("42"));

    // Act
    loadBalancerStats.incrementZoneCounter(new Server("42"));

    // Assert that nothing has changed
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("unknown");
    assertEquals("null:unknown", getResult.monitorId);
    assertEquals("unknown", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link LoadBalancerStats#incrementZoneCounter(Server)}.
   *
   * <p>Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerStats.incrementZoneCounter(Server)"})
  public void testIncrementZoneCounter3() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    Server server = new Server("42");
    server.setZone("java.lang.Integer");

    // Act
    loadBalancerStats.incrementZoneCounter(server);

    // Assert
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("java.lang.integer");
    assertEquals("java.lang.integer", getResult.getZone());
    assertEquals("null:java.lang.integer", getResult.monitorId);
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link LoadBalancerStats#incrementZoneCounter(Server)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then {@link LoadBalancerStats#LoadBalancerStats()} ZoneStats Empty.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerStats.incrementZoneCounter(Server)"})
  public void testIncrementZoneCounter_givenNull_thenLoadBalancerStatsZoneStatsEmpty() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    Server server = new Server("42");
    server.setZone(null);

    // Act
    loadBalancerStats.incrementZoneCounter(server);

    // Assert that nothing has changed
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
  }

  /**
   * Test {@link LoadBalancerStats#updateZoneServerMapping(Map)}.
   *
   * <ul>
   *   <li>Given {@code "TestKey"}.
   *   <li>Then {@link LoadBalancerStats#LoadBalancerStats()} ZoneStats size is one.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#updateZoneServerMapping(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerStats.updateZoneServerMapping(Map)"})
  public void testUpdateZoneServerMapping_givenTestKey_thenLoadBalancerStatsZoneStatsSizeIsOne() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    HashMap<String, List<Server>> map = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    map.put("\"TestKey\"", serverList);

    // Act
    loadBalancerStats.updateZoneServerMapping(map);

    // Assert
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("\"testkey\"");
    assertEquals("\"testkey\"", getResult.getZone());
    assertEquals("null:\"testkey\"", getResult.monitorId);
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
    assertEquals(1, map.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(map.containsKey("\"TestKey\""));
    assertTrue(availableZones.contains("\"TestKey\""));
    assertSame(serverList, stringListMap.get("\"TestKey\""));
  }

  /**
   * Test {@link LoadBalancerStats#updateZoneServerMapping(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then {@link HashMap#HashMap()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#updateZoneServerMapping(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerStats.updateZoneServerMapping(Map)"})
  public void testUpdateZoneServerMapping_whenHashMap_thenHashMapEmpty() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    HashMap<String, List<Server>> map = new HashMap<>();

    // Act
    loadBalancerStats.updateZoneServerMapping(map);

    // Assert that nothing has changed
    assertTrue(map.isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link LoadBalancerStats#getInstanceCount(String)}.
   *
   * <p>Method under test: {@link LoadBalancerStats#getInstanceCount(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getInstanceCount(String)"})
  public void testGetInstanceCount() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getInstanceCount(null));
  }

  /**
   * Test {@link LoadBalancerStats#getInstanceCount(String)}.
   *
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>When {@code "us-east-1"}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getInstanceCount(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getInstanceCount(String)"})
  public void testGetInstanceCount_givenLoadBalancerStats_whenUsEast1() {
    // Arrange, Act and Assert
    assertEquals(0, new LoadBalancerStats().getInstanceCount("\"us-east-1\""));
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
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getActiveRequestsCount(null));
  }

  /**
   * Test {@link LoadBalancerStats#getActiveRequestsCount(String)}.
   *
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>When {@code "us-east-1"}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getActiveRequestsCount(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getActiveRequestsCount(String)"})
  public void testGetActiveRequestsCount_givenLoadBalancerStats_whenUsEast1() {
    // Arrange, Act and Assert
    assertEquals(0, new LoadBalancerStats().getActiveRequestsCount("\"us-east-1\""));
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
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0.0d, loadBalancerStats.getActiveRequestsPerServer(null), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getActiveRequestsPerServer(String)}.
   *
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>When {@code "us-east-1a"}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getActiveRequestsPerServer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double LoadBalancerStats.getActiveRequestsPerServer(String)"})
  public void testGetActiveRequestsPerServer_givenLoadBalancerStats_whenUsEast1a() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new LoadBalancerStats().getActiveRequestsPerServer("\"us-east-1a\""), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getZoneSnapshot(List)} with {@code servers}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return ActiveRequestsCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getZoneSnapshot(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ZoneSnapshot LoadBalancerStats.getZoneSnapshot(List)"})
  public void testGetZoneSnapshotWithServers_whenArrayList_thenReturnActiveRequestsCountIsZero() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    // Act
    ZoneSnapshot actualZoneSnapshot = loadBalancerStats.getZoneSnapshot(new ArrayList<>());

    // Assert
    assertEquals(0, actualZoneSnapshot.getActiveRequestsCount());
    assertEquals(0, actualZoneSnapshot.getCircuitTrippedCount());
    assertEquals(0, actualZoneSnapshot.getInstanceCount());
    assertEquals(0.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getZoneSnapshot(List)} with {@code servers}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return ActiveRequestsCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getZoneSnapshot(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ZoneSnapshot LoadBalancerStats.getZoneSnapshot(List)"})
  public void testGetZoneSnapshotWithServers_whenNull_thenReturnActiveRequestsCountIsZero() {
    // Arrange and Act
    ZoneSnapshot actualZoneSnapshot =
        new LoadBalancerStats().getZoneSnapshot((List<? extends Server>) null);

    // Assert
    assertEquals(0, actualZoneSnapshot.getActiveRequestsCount());
    assertEquals(0, actualZoneSnapshot.getCircuitTrippedCount());
    assertEquals(0, actualZoneSnapshot.getInstanceCount());
    assertEquals(0.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getZoneSnapshot(String)} with {@code zone}.
   *
   * <p>Method under test: {@link LoadBalancerStats#getZoneSnapshot(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ZoneSnapshot LoadBalancerStats.getZoneSnapshot(String)"})
  public void testGetZoneSnapshotWithZone() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act
    ZoneSnapshot actualZoneSnapshot = loadBalancerStats.getZoneSnapshot((String) null);

    // Assert
    assertEquals(0, actualZoneSnapshot.getActiveRequestsCount());
    assertEquals(0, actualZoneSnapshot.getCircuitTrippedCount());
    assertEquals(0, actualZoneSnapshot.getInstanceCount());
    assertEquals(0.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getZoneSnapshot(String)} with {@code zone}.
   *
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>When {@code "us-west-2"}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getZoneSnapshot(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ZoneSnapshot LoadBalancerStats.getZoneSnapshot(String)"})
  public void testGetZoneSnapshotWithZone_givenLoadBalancerStats_whenUsWest2() {
    // Arrange and Act
    ZoneSnapshot actualZoneSnapshot = new LoadBalancerStats().getZoneSnapshot("\"us-west-2\"");

    // Assert
    assertEquals(0, actualZoneSnapshot.getActiveRequestsCount());
    assertEquals(0, actualZoneSnapshot.getCircuitTrippedCount());
    assertEquals(0, actualZoneSnapshot.getInstanceCount());
    assertEquals(0.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getCircuitBreakerTrippedCount(String)} with {@code String}.
   *
   * <p>Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getCircuitBreakerTrippedCount(String)"})
  public void testGetCircuitBreakerTrippedCountWithString() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount(null));
  }

  /**
   * Test {@link LoadBalancerStats#getCircuitBreakerTrippedCount(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>When {@code "us-east-1"}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getCircuitBreakerTrippedCount(String)"})
  public void testGetCircuitBreakerTrippedCountWithString_givenLoadBalancerStats_whenUsEast1() {
    // Arrange, Act and Assert
    assertEquals(0, new LoadBalancerStats().getCircuitBreakerTrippedCount("\"us-east-1\""));
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
    map.put("\"TestKey\"", new ArrayList<>());

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
  }

  /**
   * Test {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code "TestKey"} is {@link ArrayList#ArrayList()}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getCircuitBreakerTrippedCount()"})
  public void testGetCircuitBreakerTrippedCount_givenHashMapTestKeyIsArrayList_thenReturnZero() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("\"TestKey\"", new ArrayList<>());

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
   * Test {@link LoadBalancerStats#getMeasuredZoneHits(String)}.
   *
   * <p>Method under test: {@link LoadBalancerStats#getMeasuredZoneHits(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long LoadBalancerStats.getMeasuredZoneHits(String)"})
  public void testGetMeasuredZoneHits() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0L, loadBalancerStats.getMeasuredZoneHits(null));
  }

  /**
   * Test {@link LoadBalancerStats#getMeasuredZoneHits(String)}.
   *
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>When {@code "us-east-1a"}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getMeasuredZoneHits(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long LoadBalancerStats.getMeasuredZoneHits(String)"})
  public void testGetMeasuredZoneHits_givenLoadBalancerStats_whenUsEast1a() {
    // Arrange, Act and Assert
    assertEquals(0L, new LoadBalancerStats().getMeasuredZoneHits("\"us-east-1a\""));
  }

  /**
   * Test {@link LoadBalancerStats#getCongestionRatePercentage(String)}.
   *
   * <p>Method under test: {@link LoadBalancerStats#getCongestionRatePercentage(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getCongestionRatePercentage(String)"})
  public void testGetCongestionRatePercentage() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats("\"TestLoadBalancerStats\"");
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCongestionRatePercentage(null));
  }

  /**
   * Test {@link LoadBalancerStats#getCongestionRatePercentage(String)}.
   *
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>When {@code "us-east-1"}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getCongestionRatePercentage(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancerStats.getCongestionRatePercentage(String)"})
  public void testGetCongestionRatePercentage_givenLoadBalancerStats_whenUsEast1() {
    // Arrange, Act and Assert
    assertEquals(0, new LoadBalancerStats().getCongestionRatePercentage("\"us-east-1\""));
  }

  /**
   * Test {@link LoadBalancerStats#getAvailableZones()}.
   *
   * <p>Method under test: {@link LoadBalancerStats#getAvailableZones()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set LoadBalancerStats.getAvailableZones()"})
  public void testGetAvailableZones() {
    // Arrange, Act and Assert
    assertTrue(new LoadBalancerStats().getAvailableZones().isEmpty());
  }

  /**
   * Test {@link LoadBalancerStats#getSingleServerStat(Server)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerStats#getSingleServerStat(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServerStats LoadBalancerStats.getSingleServerStat(Server)"})
  public void testGetSingleServerStat_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new LoadBalancerStats().getSingleServerStat(null));
  }
}
