package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ZoneAvoidanceRuleDiffblueTest {
  /**
   * Test new {@link ZoneAvoidanceRule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ZoneAvoidanceRule}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAvoidanceRule.<init>()"})
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

  /**
   * Test {@link ZoneAvoidanceRule#createSnapshot(LoadBalancerStats)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#createSnapshot(LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ZoneAvoidanceRule.createSnapshot(LoadBalancerStats)"})
  public void testCreateSnapshot_givenHashMapFooIsArrayList_thenReturnSizeIsTwo() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("foo", new ArrayList<>());
    map.put("\"TestKey\"", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Map<String, ZoneSnapshot> actualCreateSnapshotResult =
        ZoneAvoidanceRule.createSnapshot(lbStats);

    // Assert
    assertEquals(2, actualCreateSnapshotResult.size());
    ZoneSnapshot getResult = actualCreateSnapshotResult.get("foo");
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getLoadPerServer(), 0.0);
    assertTrue(actualCreateSnapshotResult.containsKey("\"TestKey\""));
  }

  /**
   * Test {@link ZoneAvoidanceRule#createSnapshot(LoadBalancerStats)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code "TestKey"} is {@link ArrayList#ArrayList()}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#createSnapshot(LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ZoneAvoidanceRule.createSnapshot(LoadBalancerStats)"})
  public void testCreateSnapshot_givenHashMapTestKeyIsArrayList_thenReturnSizeIsOne() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("\"TestKey\"", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Map<String, ZoneSnapshot> actualCreateSnapshotResult =
        ZoneAvoidanceRule.createSnapshot(lbStats);

    // Assert
    assertEquals(1, actualCreateSnapshotResult.size());
    ZoneSnapshot getResult = actualCreateSnapshotResult.get("\"TestKey\"");
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getLoadPerServer(), 0.0);
  }

  /**
   * Test {@link ZoneAvoidanceRule#createSnapshot(LoadBalancerStats)}.
   *
   * <ul>
   *   <li>When {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#createSnapshot(LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ZoneAvoidanceRule.createSnapshot(LoadBalancerStats)"})
  public void testCreateSnapshot_whenLoadBalancerStats_thenReturnEmpty() {
    // Arrange and Act
    Map<String, ZoneSnapshot> actualCreateSnapshotResult =
        ZoneAvoidanceRule.createSnapshot(new LoadBalancerStats());

    // Assert
    assertTrue(actualCreateSnapshotResult.isEmpty());
  }

  /**
   * Test {@link ZoneAvoidanceRule#randomChooseZone(Map, Set)}.
   *
   * <ul>
   *   <li>Given {@code "TestObjectForHashSet"}.
   *   <li>Then return {@code "TestObjectForHashSet"}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#randomChooseZone(Map, Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ZoneAvoidanceRule.randomChooseZone(Map, Set)"})
  public void testRandomChooseZone_givenTestObjectForHashSet_thenReturnTestObjectForHashSet() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();

    HashSet<String> chooseFrom = new HashSet<>();
    chooseFrom.add("\"TestObjectForHashSet\"");

    // Act and Assert
    assertEquals(
        "\"TestObjectForHashSet\"", ZoneAvoidanceRule.randomChooseZone(snapshot, chooseFrom));
  }

  /**
   * Test {@link ZoneAvoidanceRule#randomChooseZone(Map, Set)}.
   *
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#randomChooseZone(Map, Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ZoneAvoidanceRule.randomChooseZone(Map, Set)"})
  public void testRandomChooseZone_whenHashSet_thenReturnNull() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();

    // Act and Assert
    assertNull(ZoneAvoidanceRule.randomChooseZone(snapshot, new HashSet<>()));
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)} with {@code
   * lbStats}, {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double,
   * double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(LoadBalancerStats, double, double)"})
  public void testGetAvailableZonesWithLbStatsTriggeringLoadTriggeringBlackoutPercentage() {
    // Arrange and Act
    Set<String> actualAvailableZones =
        ZoneAvoidanceRule.getAvailableZones(new LoadBalancerStats(), 10.0d, 10.0d);

    // Assert
    assertNull(actualAvailableZones);
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)} with {@code
   * lbStats}, {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double,
   * double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(LoadBalancerStats, double, double)"})
  public void testGetAvailableZonesWithLbStatsTriggeringLoadTriggeringBlackoutPercentage2() {
    // Arrange and Act
    Set<String> actualAvailableZones =
        ZoneAvoidanceRule.getAvailableZones((LoadBalancerStats) null, 10.0d, 10.0d);

    // Assert
    assertNull(actualAvailableZones);
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)} with {@code
   * lbStats}, {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double,
   * double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(LoadBalancerStats, double, double)"})
  public void testGetAvailableZonesWithLbStatsTriggeringLoadTriggeringBlackoutPercentage3() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("\"TestKey\"", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(lbStats, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
    assertTrue(actualAvailableZones.contains("\"TestKey\""));
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)} with {@code
   * lbStats}, {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double,
   * double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(LoadBalancerStats, double, double)"})
  public void testGetAvailableZonesWithLbStatsTriggeringLoadTriggeringBlackoutPercentage4() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("foo", new ArrayList<>());
    map.put("\"TestKey\"", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(lbStats, 10.0d, 10.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double, double)} with {@code
   * lbStats}, {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(LoadBalancerStats, double,
   * double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(LoadBalancerStats, double, double)"})
  public void testGetAvailableZonesWithLbStatsTriggeringLoadTriggeringBlackoutPercentage5() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.put("foo", new ArrayList<>());
    map.put("\"TestKey\"", new ArrayList<>());

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(map);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(lbStats, 0.0d, 10.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)} with {@code snapshot},
   * {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(Map, double, double)"})
  public void testGetAvailableZonesWithSnapshotTriggeringLoadTriggeringBlackoutPercentage() {
    // Arrange and Act
    Set<String> actualAvailableZones =
        ZoneAvoidanceRule.getAvailableZones(new HashMap<>(), 10.0d, 10.0d);

    // Assert
    assertNull(actualAvailableZones);
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)} with {@code snapshot},
   * {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(Map, double, double)"})
  public void testGetAvailableZonesWithSnapshotTriggeringLoadTriggeringBlackoutPercentage2() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    ZoneSnapshot zoneSnapshot = new ZoneSnapshot(3, 3, 3, 10.0d);
    snapshot.put("\"TestKey\"", zoneSnapshot);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
    assertTrue(actualAvailableZones.contains("\"TestKey\""));
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)} with {@code snapshot},
   * {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(Map, double, double)"})
  public void testGetAvailableZonesWithSnapshotTriggeringLoadTriggeringBlackoutPercentage3() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    ZoneSnapshot zoneSnapshot = new ZoneSnapshot(3, 3, 3, 10.0d);
    snapshot.put("foo", zoneSnapshot);
    ZoneSnapshot zoneSnapshot2 = new ZoneSnapshot(3, 3, 3, 10.0d);
    snapshot.put("\"TestKey\"", zoneSnapshot2);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)} with {@code snapshot},
   * {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(Map, double, double)"})
  public void testGetAvailableZonesWithSnapshotTriggeringLoadTriggeringBlackoutPercentage4() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    ZoneSnapshot zoneSnapshot = new ZoneSnapshot(0, 3, 3, 10.0d);
    snapshot.put("foo", zoneSnapshot);
    ZoneSnapshot zoneSnapshot2 = new ZoneSnapshot(3, 3, 3, 10.0d);
    snapshot.put("\"TestKey\"", zoneSnapshot2);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)} with {@code snapshot},
   * {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(Map, double, double)"})
  public void testGetAvailableZonesWithSnapshotTriggeringLoadTriggeringBlackoutPercentage5() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    ZoneSnapshot zoneSnapshot = new ZoneSnapshot(3, 3, 3, 1.0d);
    snapshot.put("foo", zoneSnapshot);
    ZoneSnapshot zoneSnapshot2 = new ZoneSnapshot(3, 3, 3, 10.0d);
    snapshot.put("\"TestKey\"", zoneSnapshot2);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertEquals(1, actualAvailableZones.size());
    assertTrue(actualAvailableZones.contains("foo"));
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)} with {@code snapshot},
   * {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(Map, double, double)"})
  public void testGetAvailableZonesWithSnapshotTriggeringLoadTriggeringBlackoutPercentage6() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    ZoneSnapshot zoneSnapshot = new ZoneSnapshot(3, 3, 3, -0.5d);
    snapshot.put("foo", zoneSnapshot);
    ZoneSnapshot zoneSnapshot2 = new ZoneSnapshot(3, 3, 3, 10.0d);
    snapshot.put("\"TestKey\"", zoneSnapshot2);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)} with {@code snapshot},
   * {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(Map, double, double)"})
  public void testGetAvailableZonesWithSnapshotTriggeringLoadTriggeringBlackoutPercentage7() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    ZoneSnapshot zoneSnapshot = new ZoneSnapshot(0, 3, 3, 10.0d);
    snapshot.put("foo", zoneSnapshot);
    ZoneSnapshot zoneSnapshot2 = new ZoneSnapshot(0, 3, 3, 10.0d);
    snapshot.put("\"TestKey\"", zoneSnapshot2);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)} with {@code snapshot},
   * {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(Map, double, double)"})
  public void testGetAvailableZonesWithSnapshotTriggeringLoadTriggeringBlackoutPercentage8() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    ZoneSnapshot zoneSnapshot = new ZoneSnapshot(3, 3, 3, 1.0d);
    snapshot.put("foo", zoneSnapshot);
    ZoneSnapshot zoneSnapshot2 = new ZoneSnapshot(3, 3, 3, 1.0d);
    snapshot.put("\"TestKey\"", zoneSnapshot2);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 10.0d);

    // Assert
    assertEquals(2, actualAvailableZones.size());
    assertTrue(actualAvailableZones.contains("\"TestKey\""));
    assertTrue(actualAvailableZones.contains("foo"));
  }

  /**
   * Test {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)} with {@code snapshot},
   * {@code triggeringLoad}, {@code triggeringBlackoutPercentage}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getAvailableZones(Map, double, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ZoneAvoidanceRule.getAvailableZones(Map, double, double)"})
  public void testGetAvailableZonesWithSnapshotTriggeringLoadTriggeringBlackoutPercentage9() {
    // Arrange
    HashMap<String, ZoneSnapshot> snapshot = new HashMap<>();
    ZoneSnapshot zoneSnapshot = new ZoneSnapshot(3, 3, 3, 10.0d);
    snapshot.put("foo", zoneSnapshot);
    ZoneSnapshot zoneSnapshot2 = new ZoneSnapshot(3, 3, 3, 10.0d);
    snapshot.put("\"TestKey\"", zoneSnapshot2);

    // Act
    Set<String> actualAvailableZones = ZoneAvoidanceRule.getAvailableZones(snapshot, 10.0d, 1.0d);

    // Assert
    assertTrue(actualAvailableZones.isEmpty());
  }

  /**
   * Test {@link ZoneAvoidanceRule#getPredicate()}.
   *
   * <p>Method under test: {@link ZoneAvoidanceRule#getPredicate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractServerPredicate ZoneAvoidanceRule.getPredicate()"})
  public void testGetPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualPredicate = new ZoneAvoidanceRule().getPredicate();

    // Assert
    assertTrue(actualPredicate instanceof CompositePredicate);
    assertNull(((CompositePredicate) actualPredicate).rule);
  }
}
