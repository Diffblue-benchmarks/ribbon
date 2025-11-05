package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import org.junit.Test;

public class ZoneAvoidanceRuleDiffblueTest {
  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    zoneAvoidanceRule.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(6L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig2() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "[{}] get global property '{}' with default '{}'");

    // Act
    zoneAvoidanceRule.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(6L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig3() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    zoneAvoidanceRule.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(6L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#createSnapshot(LoadBalancerStats)}
   */
  @Test
  public void testCreateSnapshot() {
    // Arrange and Act
    Map<String, ZoneSnapshot> actualCreateSnapshotResult = ZoneAvoidanceRule.createSnapshot(new LoadBalancerStats());

    // Assert
    assertTrue(actualCreateSnapshotResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#createSnapshot(LoadBalancerStats)}
   */
  @Test
  public void testCreateSnapshot2() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("foo", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Map<String, ZoneSnapshot> actualCreateSnapshotResult = ZoneAvoidanceRule.createSnapshot(lbStats);

    // Assert
    assertEquals(1, actualCreateSnapshotResult.size());
    ZoneSnapshot getResult = actualCreateSnapshotResult.get("foo");
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getLoadPerServer(), 0.0);
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#createSnapshot(LoadBalancerStats)}
   */
  @Test
  public void testCreateSnapshot3() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));
    map.put("foo", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Map<String, ZoneSnapshot> actualCreateSnapshotResult = ZoneAvoidanceRule.createSnapshot(lbStats);

    // Assert
    assertEquals(1, actualCreateSnapshotResult.size());
    ZoneSnapshot getResult = actualCreateSnapshotResult.get("foo");
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getLoadPerServer(), 0.0);
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)}
   */
  @Test
  public void testGetAvailableZones() {
    // Arrange and Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(new LoadBalancerStats(), 10.0d, 10.0d);

    // Assert
    assertNull(actualAvailableZones);
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)}
   */
  @Test
  public void testGetAvailableZones2() {
    // Arrange and Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones((LoadBalancerStats) null, 10.0d, 10.0d);

    // Assert
    assertNull(actualAvailableZones);
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)}
   */
  @Test
  public void testGetAvailableZones3() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server("42"));

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(lbStats, 1.0E-6d, 10.0d);

    // Assert
    assertNull(actualAvailableZones);
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)}
   */
  @Test
  public void testGetAvailableZones4() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("foo", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(lbStats, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
    assertTrue(actualAvailableZones.contains("foo"));
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)}
   */
  @Test
  public void testGetAvailableZones5() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("42", new ArrayList<>());
    map.put("foo", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(lbStats, 10.0d, 10.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)}
   */
  @Test
  public void testGetAvailableZones6() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));
    map.put("foo", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(lbStats, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
    assertTrue(actualAvailableZones.contains("foo"));
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)}
   */
  @Test
  public void testGetAvailableZones7() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("LoadBalancer:  PingTask executing [{}] servers configured", new ArrayList<>());
    map.put("foo", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(lbStats, 10.0d, 10.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)}
   */
  @Test
  public void testGetAvailableZones8() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("42", new ArrayList<>());
    map.put("foo", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(lbStats, 0.0d, 10.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  public void testGetAvailableZones9() {
    // Arrange and Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(new HashMap<>(), 10.0d, 10.0d);

    // Assert
    assertNull(actualAvailableZones);
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  public void testGetAvailableZones10() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    snapshot.put("foo", new ZoneSnapshot(3, 3, 3, 10.0d));

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
    assertTrue(actualAvailableZones.contains("foo"));
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  public void testGetAvailableZones11() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    snapshot.put("42", new ZoneSnapshot(3, 3, 3, 10.0d));
    snapshot.put("foo", new ZoneSnapshot(3, 3, 3, 10.0d));

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  public void testGetAvailableZones12() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    snapshot.computeIfPresent("foo", mock(BiFunction.class));
    snapshot.put("foo", new ZoneSnapshot(3, 3, 3, 10.0d));

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
    assertTrue(actualAvailableZones.contains("foo"));
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  public void testGetAvailableZones13() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    snapshot.putAll(new HashMap<>());
    snapshot.put("42", new ZoneSnapshot(3, 3, 3, 10.0d));
    snapshot.put("foo", new ZoneSnapshot(3, 3, 3, 10.0d));

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  public void testGetAvailableZones14() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    snapshot.put("42", new ZoneSnapshot(0, 3, 3, 10.0d));
    snapshot.put("foo", new ZoneSnapshot(3, 3, 3, 10.0d));

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  public void testGetAvailableZones15() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    snapshot.put("42", new ZoneSnapshot(3, 3, 3, 1.0d));
    snapshot.put("foo", new ZoneSnapshot(3, 3, 3, 10.0d));

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
    assertTrue(actualAvailableZones.contains("42"));
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  public void testGetAvailableZones16() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    snapshot.put("42", new ZoneSnapshot(3, 3, 3, -0.5d));
    snapshot.put("foo", new ZoneSnapshot(3, 3, 3, 10.0d));

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  public void testGetAvailableZones17() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    snapshot.put("42", new ZoneSnapshot(3, 3, 3, 10.0d));
    snapshot.put("foo", new ZoneSnapshot(3, 3, 3, 10.0d));

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 1.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Method under test: {@link ZoneAvoidanceRule#getPredicate()}
   */
  @Test
  public void testGetPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualPredicate = (new ZoneAvoidanceRule()).getPredicate();

    // Assert
    assertTrue(actualPredicate instanceof CompositePredicate);
    assertNull(((CompositePredicate) actualPredicate).rule);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ZoneAvoidanceRule}
   */
  @Test
  public void testNewZoneAvoidanceRule() {
    // Arrange and Act
    ZoneAvoidanceRule actualZoneAvoidanceRule = new ZoneAvoidanceRule();

    // Assert
    AbstractServerPredicate predicate = actualZoneAvoidanceRule.getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    assertNull(actualZoneAvoidanceRule.getLoadBalancer());
    assertNull(actualZoneAvoidanceRule.roundRobinRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
  }
}
