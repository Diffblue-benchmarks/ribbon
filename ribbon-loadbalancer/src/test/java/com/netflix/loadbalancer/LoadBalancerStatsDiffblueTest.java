package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
   * <p>
   * Method under test: {@link LoadBalancerStats#LoadBalancerStats()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#LoadBalancerStats(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * Test {@link LoadBalancerStats#LoadBalancerStats(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Name is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#LoadBalancerStats(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerStats.<init>(String)"})
  public void testNewLoadBalancerStats_whenNull_thenReturnNameIsNull() {
    // Arrange and Act
    LoadBalancerStats actualLoadBalancerStats = new LoadBalancerStats(null);

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
   * <ul>
   *   <li>When space.</li>
   *   <li>Then return Name is space.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#LoadBalancerStats(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerStats.<init>(String)"})
  public void testNewLoadBalancerStats_whenSpace_thenReturnNameIsSpace() {
    // Arrange and Act
    LoadBalancerStats actualLoadBalancerStats = new LoadBalancerStats(" ");

    // Assert
    assertEquals(" ", actualLoadBalancerStats.getName());
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
   * Test {@link LoadBalancerStats#getServerStats()}.
   * <p>
   * Method under test: {@link LoadBalancerStats#getServerStats()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map LoadBalancerStats.getServerStats()"})
  public void testGetServerStats() {
    // Arrange, Act and Assert
    assertTrue((new LoadBalancerStats()).getServerStats().isEmpty());
  }

  /**
   * Test {@link LoadBalancerStats#getServerStats(Server)} with {@code Server}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getServerStats(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ServerStats LoadBalancerStats.getServerStats(Server)"})
  public void testGetServerStatsWithServer_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new LoadBalancerStats()).getServerStats(null));
  }

  /**
   * Test {@link LoadBalancerStats#incrementZoneCounter(Server)}.
   * <p>
   * Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerStats.incrementZoneCounter(Server)"})
  public void testIncrementZoneCounter() {
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
   * <ul>
   *   <li>Then {@link LoadBalancerStats#LoadBalancerStats(String)} with name is space ZoneStats size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerStats.incrementZoneCounter(Server)"})
  public void testIncrementZoneCounter_thenLoadBalancerStatsWithNameIsSpaceZoneStatsSizeIsOne() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats(" ");

    // Act
    loadBalancerStats.incrementZoneCounter(new Server("42"));

    // Assert
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("unknown");
    assertEquals(" :unknown", getResult.monitorId);
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
   * <ul>
   *   <li>Then {@link LoadBalancerStats#LoadBalancerStats()} ZoneStats size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerStats.incrementZoneCounter(Server)"})
  public void testIncrementZoneCounter_thenLoadBalancerStatsZoneStatsSizeIsOne() {
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
   * Test {@link LoadBalancerStats#updateZoneServerMapping(Map)}.
   * <ul>
   *   <li>Given {@code :}.</li>
   *   <li>Then {@link LoadBalancerStats#LoadBalancerStats()} ZoneStats size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#updateZoneServerMapping(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerStats.updateZoneServerMapping(Map)"})
  public void testUpdateZoneServerMapping_givenColon_thenLoadBalancerStatsZoneStatsSizeIsTwo() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    HashMap<String, List<Server>> map = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    map.put(":", serverList);
    map.put("foo", new ArrayList<>());

    // Act
    loadBalancerStats.updateZoneServerMapping(map);

    // Assert
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(2, zoneStats.size());
    ZoneStats getResult = zoneStats.get(":");
    assertEquals(":", getResult.getZone());
    assertEquals("null::", getResult.monitorId);
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(2, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(2, availableZones.size());
    assertTrue(zoneStats.containsKey("foo"));
    assertTrue(stringListMap.containsKey("foo"));
    assertTrue(availableZones.contains(":"));
    assertSame(serverList, stringListMap.get(":"));
  }

  /**
   * Test {@link LoadBalancerStats#updateZoneServerMapping(Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then {@link LoadBalancerStats#LoadBalancerStats()} ZoneStats size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#updateZoneServerMapping(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerStats.updateZoneServerMapping(Map)"})
  public void testUpdateZoneServerMapping_givenFoo_thenLoadBalancerStatsZoneStatsSizeIsOne() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("foo", new ArrayList<>());

    // Act
    loadBalancerStats.updateZoneServerMapping(map);

    // Assert
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    assertEquals(1, loadBalancerStats.getAvailableZones().size());
    assertTrue(zoneStats.containsKey("foo"));
    assertTrue(stringListMap.containsKey("foo"));
  }

  /**
   * Test {@link LoadBalancerStats#updateZoneServerMapping(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then {@link LoadBalancerStats#LoadBalancerStats()} ZoneStats Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#updateZoneServerMapping(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerStats.updateZoneServerMapping(Map)"})
  public void testUpdateZoneServerMapping_whenHashMap_thenLoadBalancerStatsZoneStatsEmpty() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    // Act
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Assert that nothing has changed
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link LoadBalancerStats#getInstanceCount(String)}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()} updateZoneServerMapping {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getInstanceCount(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerStats.getInstanceCount(String)"})
  public void testGetInstanceCount_givenLoadBalancerStatsUpdateZoneServerMappingHashMap() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getInstanceCount(null));
  }

  /**
   * Test {@link LoadBalancerStats#getInstanceCount(String)}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   *   <li>When {@code Zone}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getInstanceCount(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerStats.getInstanceCount(String)"})
  public void testGetInstanceCount_givenLoadBalancerStats_whenZone() {
    // Arrange, Act and Assert
    assertEquals(0, (new LoadBalancerStats()).getInstanceCount("Zone"));
  }

  /**
   * Test {@link LoadBalancerStats#getActiveRequestsCount(String)}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()} updateZoneServerMapping {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getActiveRequestsCount(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerStats.getActiveRequestsCount(String)"})
  public void testGetActiveRequestsCount_givenLoadBalancerStatsUpdateZoneServerMappingHashMap() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getActiveRequestsCount(null));
  }

  /**
   * Test {@link LoadBalancerStats#getActiveRequestsCount(String)}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   *   <li>When {@code Zone}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getActiveRequestsCount(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerStats.getActiveRequestsCount(String)"})
  public void testGetActiveRequestsCount_givenLoadBalancerStats_whenZone() {
    // Arrange, Act and Assert
    assertEquals(0, (new LoadBalancerStats()).getActiveRequestsCount("Zone"));
  }

  /**
   * Test {@link LoadBalancerStats#getActiveRequestsPerServer(String)}.
   * <p>
   * Method under test: {@link LoadBalancerStats#getActiveRequestsPerServer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"double LoadBalancerStats.getActiveRequestsPerServer(String)"})
  public void testGetActiveRequestsPerServer() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0.0d, loadBalancerStats.getActiveRequestsPerServer(null), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getActiveRequestsPerServer(String)}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   *   <li>When {@code Zone}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getActiveRequestsPerServer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"double LoadBalancerStats.getActiveRequestsPerServer(String)"})
  public void testGetActiveRequestsPerServer_givenLoadBalancerStats_whenZone() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new LoadBalancerStats()).getActiveRequestsPerServer("Zone"), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getZoneSnapshot(List)} with {@code servers}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return ActiveRequestsCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getZoneSnapshot(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return ActiveRequestsCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getZoneSnapshot(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ZoneSnapshot LoadBalancerStats.getZoneSnapshot(List)"})
  public void testGetZoneSnapshotWithServers_whenNull_thenReturnActiveRequestsCountIsZero() {
    // Arrange and Act
    ZoneSnapshot actualZoneSnapshot = (new LoadBalancerStats()).getZoneSnapshot((List<? extends Server>) null);

    // Assert
    assertEquals(0, actualZoneSnapshot.getActiveRequestsCount());
    assertEquals(0, actualZoneSnapshot.getCircuitTrippedCount());
    assertEquals(0, actualZoneSnapshot.getInstanceCount());
    assertEquals(0.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getZoneSnapshot(String)} with {@code zone}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()} updateZoneServerMapping {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getZoneSnapshot(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ZoneSnapshot LoadBalancerStats.getZoneSnapshot(String)"})
  public void testGetZoneSnapshotWithZone_givenLoadBalancerStatsUpdateZoneServerMappingHashMap() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
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
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   *   <li>When {@code Zone}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getZoneSnapshot(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ZoneSnapshot LoadBalancerStats.getZoneSnapshot(String)"})
  public void testGetZoneSnapshotWithZone_givenLoadBalancerStats_whenZone() {
    // Arrange and Act
    ZoneSnapshot actualZoneSnapshot = (new LoadBalancerStats()).getZoneSnapshot("Zone");

    // Assert
    assertEquals(0, actualZoneSnapshot.getActiveRequestsCount());
    assertEquals(0, actualZoneSnapshot.getCircuitTrippedCount());
    assertEquals(0, actualZoneSnapshot.getInstanceCount());
    assertEquals(0.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
  }

  /**
   * Test {@link LoadBalancerStats#getCircuitBreakerTrippedCount(String)} with {@code String}.
   * <p>
   * Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerStats.getCircuitBreakerTrippedCount(String)"})
  public void testGetCircuitBreakerTrippedCountWithString() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount(null));
  }

  /**
   * Test {@link LoadBalancerStats#getCircuitBreakerTrippedCount(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   *   <li>When {@code Zone}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerStats.getCircuitBreakerTrippedCount(String)"})
  public void testGetCircuitBreakerTrippedCountWithString_givenLoadBalancerStats_whenZone() {
    // Arrange, Act and Assert
    assertEquals(0, (new LoadBalancerStats()).getCircuitBreakerTrippedCount("Zone"));
  }

  /**
   * Test {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerStats.getCircuitBreakerTrippedCount()"})
  public void testGetCircuitBreakerTrippedCount_givenLoadBalancerStats_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new LoadBalancerStats()).getCircuitBreakerTrippedCount());
  }

  /**
   * Test {@link LoadBalancerStats#getMeasuredZoneHits(String)}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()} updateZoneServerMapping {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getMeasuredZoneHits(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long LoadBalancerStats.getMeasuredZoneHits(String)"})
  public void testGetMeasuredZoneHits_givenLoadBalancerStatsUpdateZoneServerMappingHashMap() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0L, loadBalancerStats.getMeasuredZoneHits(null));
  }

  /**
   * Test {@link LoadBalancerStats#getMeasuredZoneHits(String)}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   *   <li>When {@code Zone}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getMeasuredZoneHits(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long LoadBalancerStats.getMeasuredZoneHits(String)"})
  public void testGetMeasuredZoneHits_givenLoadBalancerStats_whenZone() {
    // Arrange, Act and Assert
    assertEquals(0L, (new LoadBalancerStats()).getMeasuredZoneHits("Zone"));
  }

  /**
   * Test {@link LoadBalancerStats#getCongestionRatePercentage(String)}.
   * <p>
   * Method under test: {@link LoadBalancerStats#getCongestionRatePercentage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerStats.getCongestionRatePercentage(String)"})
  public void testGetCongestionRatePercentage() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCongestionRatePercentage(null));
  }

  /**
   * Test {@link LoadBalancerStats#getCongestionRatePercentage(String)}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   *   <li>When {@code Zone}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getCongestionRatePercentage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerStats.getCongestionRatePercentage(String)"})
  public void testGetCongestionRatePercentage_givenLoadBalancerStats_whenZone() {
    // Arrange, Act and Assert
    assertEquals(0, (new LoadBalancerStats()).getCongestionRatePercentage("Zone"));
  }

  /**
   * Test {@link LoadBalancerStats#getAvailableZones()}.
   * <p>
   * Method under test: {@link LoadBalancerStats#getAvailableZones()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set LoadBalancerStats.getAvailableZones()"})
  public void testGetAvailableZones() {
    // Arrange, Act and Assert
    assertTrue((new LoadBalancerStats()).getAvailableZones().isEmpty());
  }

  /**
   * Test {@link LoadBalancerStats#getSingleServerStat(Server)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerStats#getSingleServerStat(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ServerStats LoadBalancerStats.getSingleServerStat(Server)"})
  public void testGetSingleServerStat_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new LoadBalancerStats()).getSingleServerStat(null));
  }
}
