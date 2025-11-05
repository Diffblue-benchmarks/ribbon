package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.UnboxedIntProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import org.junit.Test;

public class LoadBalancerStatsDiffblueTest {
  /**
   * Method under test:
   * {@link LoadBalancerStats#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    loadBalancerStats.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals("", loadBalancerStats.getName());
    assertEquals(7L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link LoadBalancerStats#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    loadBalancerStats.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals("", loadBalancerStats.getName());
    assertEquals(7L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test: {@link LoadBalancerStats#getServerStats()}
   */
  @Test
  public void testGetServerStats() {
    // Arrange, Act and Assert
    assertTrue((new LoadBalancerStats()).getServerStats().isEmpty());
    assertNull((new LoadBalancerStats()).getServerStats(null));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getServerStats()}
   */
  @Test
  public void testGetServerStats2() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
  }

  /**
   * Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
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
   * Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
  public void testIncrementZoneCounter2() {
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
   * Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
  public void testIncrementZoneCounter3() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.incrementZoneCounter(new Server("42"));

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
   * Method under test: {@link LoadBalancerStats#incrementZoneCounter(Server)}
   */
  @Test
  public void testIncrementZoneCounter4() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats(" ");

    // Act
    loadBalancerStats.incrementZoneCounter(new Server("42 "));

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
   * Method under test: {@link LoadBalancerStats#updateZoneServerMapping(Map)}
   */
  @Test
  public void testUpdateZoneServerMapping() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    HashMap<String, List<Server>> map = new HashMap<>();

    // Act
    loadBalancerStats.updateZoneServerMapping(map);

    // Assert
    assertTrue(map.isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Method under test: {@link LoadBalancerStats#updateZoneServerMapping(Map)}
   */
  @Test
  public void testUpdateZoneServerMapping2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    HashMap<String, List<Server>> map = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    map.put("foo", serverList);

    // Act
    loadBalancerStats.updateZoneServerMapping(map);

    // Assert
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("foo");
    assertEquals("foo", getResult.getZone());
    assertEquals("null:foo", getResult.monitorId);
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
    assertTrue(map.containsKey("foo"));
    assertTrue(availableZones.contains("foo"));
    assertSame(serverList, stringListMap.get("foo"));
  }

  /**
   * Method under test: {@link LoadBalancerStats#updateZoneServerMapping(Map)}
   */
  @Test
  public void testUpdateZoneServerMapping3() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    HashMap<String, List<Server>> map = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    map.put(":", serverList);
    ArrayList<Server> serverList2 = new ArrayList<>();
    map.put("foo", serverList2);

    // Act
    loadBalancerStats.updateZoneServerMapping(map);

    // Assert
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(2, zoneStats.size());
    ZoneStats getResult = zoneStats.get(":");
    assertEquals(":", getResult.getZone());
    ZoneStats getResult2 = zoneStats.get("foo");
    assertEquals("foo", getResult2.getZone());
    assertEquals("null::", getResult.monitorId);
    assertEquals("null:foo", getResult2.monitorId);
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult2.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult2.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0, getResult2.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult2.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0.0d, getResult2.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
    assertEquals(0L, getResult2.getMeasuredZoneHits());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(2, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(2, availableZones.size());
    assertTrue(availableZones.contains(":"));
    assertTrue(availableZones.contains("foo"));
    assertEquals(loadBalancerStats.upServerListZoneMap, map);
    assertSame(serverList, stringListMap.get(":"));
    assertSame(serverList2, stringListMap.get("foo"));
  }

  /**
   * Method under test: {@link LoadBalancerStats#updateZoneServerMapping(Map)}
   */
  @Test
  public void testUpdateZoneServerMapping4() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent(" ", mock(BiFunction.class));
    ArrayList<Server> serverList = new ArrayList<>();
    map.put("foo", serverList);

    // Act
    loadBalancerStats.updateZoneServerMapping(map);

    // Assert
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("foo");
    assertEquals("foo", getResult.getZone());
    assertEquals("null:foo", getResult.monitorId);
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
    assertTrue(map.containsKey("foo"));
    assertTrue(availableZones.contains("foo"));
    assertSame(serverList, stringListMap.get("foo"));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getInstanceCount(String)}
   */
  @Test
  public void testGetInstanceCount() {
    // Arrange, Act and Assert
    assertEquals(0, (new LoadBalancerStats()).getInstanceCount("Zone"));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getInstanceCount(String)}
   */
  @Test
  public void testGetInstanceCount2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getInstanceCount(null));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getInstanceCount(String)}
   */
  @Test
  public void testGetInstanceCount3() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertEquals(0, loadBalancerStats.getInstanceCount(null));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getActiveRequestsCount(String)}
   */
  @Test
  public void testGetActiveRequestsCount() {
    // Arrange, Act and Assert
    assertEquals(0, (new LoadBalancerStats()).getActiveRequestsCount("Zone"));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getActiveRequestsCount(String)}
   */
  @Test
  public void testGetActiveRequestsCount2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getActiveRequestsCount(null));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getActiveRequestsCount(String)}
   */
  @Test
  public void testGetActiveRequestsCount3() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertEquals(0, loadBalancerStats.getActiveRequestsCount(null));
  }

  /**
   * Method under test:
   * {@link LoadBalancerStats#getActiveRequestsPerServer(String)}
   */
  @Test
  public void testGetActiveRequestsPerServer() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new LoadBalancerStats()).getActiveRequestsPerServer("Zone"), 0.0);
  }

  /**
   * Method under test:
   * {@link LoadBalancerStats#getActiveRequestsPerServer(String)}
   */
  @Test
  public void testGetActiveRequestsPerServer2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0.0d, loadBalancerStats.getActiveRequestsPerServer(null), 0.0);
  }

  /**
   * Method under test:
   * {@link LoadBalancerStats#getActiveRequestsPerServer(String)}
   */
  @Test
  public void testGetActiveRequestsPerServer3() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertEquals(0.0d, loadBalancerStats.getActiveRequestsPerServer(null), 0.0);
  }

  /**
   * Method under test: {@link LoadBalancerStats#getZoneSnapshot(String)}
   */
  @Test
  public void testGetZoneSnapshot() {
    // Arrange and Act
    ZoneSnapshot actualZoneSnapshot = (new LoadBalancerStats()).getZoneSnapshot("Zone");

    // Assert
    assertEquals(0, actualZoneSnapshot.getActiveRequestsCount());
    assertEquals(0, actualZoneSnapshot.getCircuitTrippedCount());
    assertEquals(0, actualZoneSnapshot.getInstanceCount());
    assertEquals(0.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
  }

  /**
   * Method under test: {@link LoadBalancerStats#getZoneSnapshot(String)}
   */
  @Test
  public void testGetZoneSnapshot2() {
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
   * Method under test: {@link LoadBalancerStats#getZoneSnapshot(String)}
   */
  @Test
  public void testGetZoneSnapshot3() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act
    ZoneSnapshot actualZoneSnapshot = loadBalancerStats.getZoneSnapshot((String) null);

    // Assert
    assertEquals(0, actualZoneSnapshot.getActiveRequestsCount());
    assertEquals(0, actualZoneSnapshot.getCircuitTrippedCount());
    assertEquals(0, actualZoneSnapshot.getInstanceCount());
    assertEquals(0.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
  }

  /**
   * Method under test: {@link LoadBalancerStats#getZoneSnapshot(List)}
   */
  @Test
  public void testGetZoneSnapshot4() {
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
   * Method under test: {@link LoadBalancerStats#getZoneSnapshot(List)}
   */
  @Test
  public void testGetZoneSnapshot5() {
    // Arrange and Act
    ZoneSnapshot actualZoneSnapshot = (new LoadBalancerStats()).getZoneSnapshot((List<? extends Server>) null);

    // Assert
    assertEquals(0, actualZoneSnapshot.getActiveRequestsCount());
    assertEquals(0, actualZoneSnapshot.getCircuitTrippedCount());
    assertEquals(0, actualZoneSnapshot.getInstanceCount());
    assertEquals(0.0d, actualZoneSnapshot.getLoadPerServer(), 0.0);
  }

  /**
   * Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  public void testGetCircuitBreakerTrippedCount() {
    // Arrange, Act and Assert
    assertEquals(0, (new LoadBalancerStats()).getCircuitBreakerTrippedCount());
    assertEquals(0, (new LoadBalancerStats()).getCircuitBreakerTrippedCount("Zone"));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  public void testGetCircuitBreakerTrippedCount2() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("foo", new ArrayList<>());

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
  }

  /**
   * Method under test: {@link LoadBalancerStats#getCircuitBreakerTrippedCount()}
   */
  @Test
  public void testGetCircuitBreakerTrippedCount3() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));
    map.put("foo", new ArrayList<>());

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
  }

  /**
   * Method under test:
   * {@link LoadBalancerStats#getCircuitBreakerTrippedCount(String)}
   */
  @Test
  public void testGetCircuitBreakerTrippedCount4() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount(null));
  }

  /**
   * Method under test:
   * {@link LoadBalancerStats#getCircuitBreakerTrippedCount(String)}
   */
  @Test
  public void testGetCircuitBreakerTrippedCount5() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount(null));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getMeasuredZoneHits(String)}
   */
  @Test
  public void testGetMeasuredZoneHits() {
    // Arrange, Act and Assert
    assertEquals(0L, (new LoadBalancerStats()).getMeasuredZoneHits("Zone"));
    assertEquals(0L,
        (new LoadBalancerStats()).getMeasuredZoneHits("niws.loadbalancer.%s.circuitTripTimeoutFactorSeconds"));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getMeasuredZoneHits(String)}
   */
  @Test
  public void testGetMeasuredZoneHits2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0L, loadBalancerStats.getMeasuredZoneHits(null));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getMeasuredZoneHits(String)}
   */
  @Test
  public void testGetMeasuredZoneHits3() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertEquals(0L, loadBalancerStats.getMeasuredZoneHits(null));
  }

  /**
   * Method under test:
   * {@link LoadBalancerStats#getCongestionRatePercentage(String)}
   */
  @Test
  public void testGetCongestionRatePercentage() {
    // Arrange, Act and Assert
    assertEquals(0, (new LoadBalancerStats()).getCongestionRatePercentage("Zone"));
  }

  /**
   * Method under test:
   * {@link LoadBalancerStats#getCongestionRatePercentage(String)}
   */
  @Test
  public void testGetCongestionRatePercentage2() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(new HashMap<>());

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCongestionRatePercentage(null));
  }

  /**
   * Method under test:
   * {@link LoadBalancerStats#getCongestionRatePercentage(String)}
   */
  @Test
  public void testGetCongestionRatePercentage3() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertEquals(0, loadBalancerStats.getCongestionRatePercentage(null));
  }

  /**
   * Method under test: {@link LoadBalancerStats#getAvailableZones()}
   */
  @Test
  public void testGetAvailableZones() {
    // Arrange, Act and Assert
    assertTrue((new LoadBalancerStats()).getAvailableZones().isEmpty());
  }

  /**
   * Method under test: {@link LoadBalancerStats#getAvailableZones()}
   */
  @Test
  public void testGetAvailableZones2() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();
    loadBalancerStats.updateZoneServerMapping(map);

    // Act and Assert
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Method under test: {@link LoadBalancerStats#getSingleServerStat(Server)}
   */
  @Test
  public void testGetSingleServerStat() {
    // Arrange, Act and Assert
    assertNull((new LoadBalancerStats()).getSingleServerStat(null));
  }

  /**
   * Methods under test:
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
  public void testGettersAndSetters() {
    // Arrange
    LoadBalancerStats loadBalancerStats = new LoadBalancerStats();

    // Act
    loadBalancerStats.setName("Name");
    String actualToStringResult = loadBalancerStats.toString();
    UnboxedIntProperty actualActiveRequestsCountTimeout = loadBalancerStats.getActiveRequestsCountTimeout();
    UnboxedIntProperty actualCircuitTripMaxTimeoutSeconds = loadBalancerStats.getCircuitTripMaxTimeoutSeconds();
    UnboxedIntProperty actualCircuitTrippedTimeoutFactor = loadBalancerStats.getCircuitTrippedTimeoutFactor();
    UnboxedIntProperty actualConnectionFailureCountThreshold = loadBalancerStats.getConnectionFailureCountThreshold();
    String actualName = loadBalancerStats.getName();
    Map<String, ZoneStats> actualZoneStats = loadBalancerStats.getZoneStats();

    // Assert that nothing has changed
    assertEquals("Name", actualName);
    assertEquals("Zone stats: {},Server stats: []", actualToStringResult);
    assertEquals(10, actualCircuitTrippedTimeoutFactor.get());
    assertEquals(3, actualConnectionFailureCountThreshold.get());
    assertEquals(30, actualCircuitTripMaxTimeoutSeconds.get());
    assertEquals(600, actualActiveRequestsCountTimeout.get());
    assertTrue(actualZoneStats.isEmpty());
  }

  /**
   * Method under test: {@link LoadBalancerStats#LoadBalancerStats()}
   */
  @Test
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
   * Method under test: {@link LoadBalancerStats#LoadBalancerStats(String)}
   */
  @Test
  public void testNewLoadBalancerStats2() {
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
   * Method under test: {@link LoadBalancerStats#LoadBalancerStats(String)}
   */
  @Test
  public void testNewLoadBalancerStats3() {
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
   * Method under test: {@link LoadBalancerStats#LoadBalancerStats(String)}
   */
  @Test
  public void testNewLoadBalancerStats4() {
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
}
