package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ZoneAwareLoadBalancerDiffblueTest {
  /**
   * Test {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.<init>()"})
  public void testNewZoneAwareLoadBalancer() {
    // Arrange and Act
    ZoneAwareLoadBalancer<Server> actualZoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Assert
    assertTrue(actualZoneAwareLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualZoneAwareLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualZoneAwareLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualZoneAwareLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default", actualZoneAwareLoadBalancer.getName());
    assertNull(actualZoneAwareLoadBalancer.getPrimeConnections());
    assertNull(actualZoneAwareLoadBalancer.getClientConfig());
    assertNull(actualZoneAwareLoadBalancer.getPing());
    assertNull(actualZoneAwareLoadBalancer.getServerListImpl());
    assertNull(actualZoneAwareLoadBalancer.getFilter());
    assertNull(actualZoneAwareLoadBalancer.getServerListUpdater());
    assertNull(actualZoneAwareLoadBalancer.lbTimer);
    assertEquals(10, actualZoneAwareLoadBalancer.getPingInterval());
    assertEquals(5, actualZoneAwareLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualZoneAwareLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualZoneAwareLoadBalancer.isPingInProgress());
    assertFalse(actualZoneAwareLoadBalancer.isSecure);
    assertFalse(actualZoneAwareLoadBalancer.useTunnel);
    assertTrue(actualZoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualZoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualZoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(actualZoneAwareLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Act
    zoneAwareLoadBalancer.setServerListForZones(new HashMap<>());

    // Assert that nothing has changed
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
    assertTrue(stringListMap.containsKey("Setting server list for zones: {}"));
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones3() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
    assertTrue(stringListMap.containsKey("Setting server list for zones: {}"));
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones4() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.putIfAbsent(":", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(2, zoneStats.size());
    ZoneStats getResult = zoneStats.get(":");
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones5() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("Id"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.putIfAbsent(":", serverList);
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(2, zoneStats.size());
    assertEquals(1, zoneStats.get(":").getInstanceCount());
    assertEquals(2, zoneServersMap.size());
    List<Server> getResult = zoneServersMap.get(":");
    assertEquals(1, getResult.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(2, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(2, availableZones.size());
    assertTrue(getResult.get(0).isAlive());
    assertTrue(zoneServersMap.containsKey("Setting server list for zones: {}"));
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
    assertTrue(stringListMap.containsKey(":"));
    assertTrue(stringListMap.containsKey("Setting server list for zones: {}"));
    assertTrue(availableZones.contains(":"));
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones6() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new AvailabilityFilteringRule());

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
    assertTrue(stringListMap.containsKey("Setting server list for zones: {}"));
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones7() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setEnablePrimingConnections(true);

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
    assertTrue(stringListMap.containsKey("Setting server list for zones: {}"));
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones8() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("Id"));
    serverList.add(new Server("Id"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.putIfAbsent(":", serverList);
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(2, zoneStats.size());
    assertEquals(2, zoneStats.get(":").getInstanceCount());
    assertEquals(2, zoneServersMap.size());
    List<Server> getResult = zoneServersMap.get(":");
    assertEquals(2, getResult.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(2, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(2, availableZones.size());
    assertTrue(getResult.get(0).isAlive());
    assertTrue(zoneServersMap.containsKey("Setting server list for zones: {}"));
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
    assertTrue(stringListMap.containsKey(":"));
    assertTrue(stringListMap.containsKey("Setting server list for zones: {}"));
    assertTrue(availableZones.contains(":"));
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones9() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("Id"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.putIfAbsent(":", serverList);
    ArrayList<Server> serverList2 = new ArrayList<>();
    zoneServersMap.put("_Counter", serverList2);
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(3, zoneStats.size());
    ZoneStats getResult = zoneStats.get("_counter");
    assertEquals("_counter", getResult.getZone());
    assertEquals("default:_counter", getResult.monitorId);
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(3, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(3, availableZones.size());
    assertTrue(zoneStats.containsKey(":"));
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
    assertTrue(stringListMap.containsKey(":"));
    assertTrue(stringListMap.containsKey("Setting server list for zones: {}"));
    assertTrue(availableZones.contains("_Counter"));
    assertSame(serverList2, stringListMap.get("_Counter"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenArrayListAddNull() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(null);

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
    assertTrue(stringListMap.containsKey("Setting server list for zones: {}"));
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code 42}.</li>
   *   <li>Then {@link HashMap#HashMap()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenArrayListAddServerWithIdIs42_thenHashMapSizeIsOne() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    assertEquals(1, zoneServersMap.size());
    List<Server> getResult = zoneServersMap.get("Setting server list for zones: {}");
    assertEquals(1, getResult.size());
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(getResult.get(0).isAlive());
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
    assertTrue(stringListMap.containsKey("Setting server list for zones: {}"));
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} containsKey {@code :}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_thenHashMapContainsKeyColon() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("/"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.putIfAbsent(":", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(2, zoneStats.size());
    ZoneStats getResult = zoneStats.get(":");
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
    assertEquals(2, zoneServersMap.size());
    List<Server> getResult2 = zoneServersMap.get("Setting server list for zones: {}");
    assertEquals(1, getResult2.size());
    assertTrue(getResult2.get(0).isAlive());
    assertTrue(zoneServersMap.containsKey(":"));
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()} {@code :} is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_whenHashMapColonIsArrayList() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put(":", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(2, zoneStats.size());
    ZoneStats getResult = zoneStats.get(":");
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()} IfAbsent space is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_whenHashMapIfAbsentSpaceIsArrayList() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("/"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList2 = new ArrayList<>();
    zoneServersMap.putIfAbsent(" ", serverList2);
    zoneServersMap.putIfAbsent(":", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(3, zoneStats.size());
    ZoneStats getResult = zoneStats.get(" ");
    assertEquals(" ", getResult.getZone());
    assertEquals("default: ", getResult.monitorId);
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(3, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(3, availableZones.size());
    assertTrue(zoneStats.containsKey(":"));
    assertTrue(zoneStats.containsKey("setting server list for zones: {}"));
    assertTrue(stringListMap.containsKey(":"));
    assertTrue(stringListMap.containsKey("Setting server list for zones: {}"));
    assertTrue(availableZones.contains(" "));
    assertTrue(availableZones.contains(":"));
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
    assertSame(serverList2, stringListMap.get(" "));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()} space is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_whenHashMapSpaceIsArrayList() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    zoneServersMap.put(" ", serverList);

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get(" ");
    assertEquals(" ", getResult.getZone());
    assertEquals("default: ", getResult.monitorId);
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(availableZones.contains(" "));
    assertSame(serverList, stringListMap.get(" "));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#chooseServer(Object)} with {@code Object}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server ZoneAwareLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setEnablePrimingConnections(true);
    zoneAwareLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertNull(zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#chooseServer(Object)} with {@code Object}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server ZoneAwareLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListImpl(new ConfigurationBasedServerList());

    // Act and Assert
    assertNull(zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#chooseServer(Object)} with {@code Object}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server ZoneAwareLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject3() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new AvailabilityFilteringRule());

    // Act and Assert
    assertNull(zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#chooseServer(Object)} with {@code Object}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Ping is {@link IPing}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server ZoneAwareLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject_givenZoneAwareLoadBalancerPingIsIPing_thenReturnNull() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));
    zoneAwareLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertNull(zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#chooseServer(Object)} with {@code Object}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server ZoneAwareLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject_givenZoneAwareLoadBalancer_thenReturnNull() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Act and Assert
    assertNull(zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#chooseServer(Object)} with {@code Object}.
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server ZoneAwareLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject_thenReturnServerWithIdIs42() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    Server newServer = new Server("42");
    zoneAwareLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServer(new Server("com.netflix.loadbalancer.Server"));
    zoneAwareLoadBalancer.setRule(null);
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    assertTrue(actualLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default_zone", actualLoadBalancer.getName());
    assertNull(actualLoadBalancer.getPrimeConnections());
    assertNull(actualLoadBalancer.getClientConfig());
    assertNull(actualLoadBalancer.getPing());
    assertNull(actualLoadBalancer.lbTimer);
    assertEquals(10, actualLoadBalancer.getPingInterval());
    assertEquals(5, actualLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualLoadBalancer.isPingInProgress());
    assertTrue(actualLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualLoadBalancer.allServerList.isEmpty());
    assertTrue(actualLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} addServers array of {@link Object} with two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_givenZoneAwareLoadBalancerAddServersArrayOfObjectWithTwo() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServers(new Object[]{2});

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    assertTrue(actualLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default_zone", actualLoadBalancer.getName());
    assertNull(actualLoadBalancer.getPrimeConnections());
    assertNull(actualLoadBalancer.getClientConfig());
    assertNull(actualLoadBalancer.getPing());
    assertNull(actualLoadBalancer.lbTimer);
    assertEquals(10, actualLoadBalancer.getPingInterval());
    assertEquals(5, actualLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualLoadBalancer.isPingInProgress());
    assertTrue(actualLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualLoadBalancer.allServerList.isEmpty());
    assertTrue(actualLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Rule is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_givenZoneAwareLoadBalancerRuleIsNull() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(null);
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    assertTrue(actualLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default_zone", actualLoadBalancer.getName());
    assertNull(actualLoadBalancer.getPrimeConnections());
    assertNull(actualLoadBalancer.getClientConfig());
    assertNull(actualLoadBalancer.getPing());
    assertNull(actualLoadBalancer.lbTimer);
    assertEquals(10, actualLoadBalancer.getPingInterval());
    assertEquals(5, actualLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualLoadBalancer.isPingInProgress());
    assertTrue(actualLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualLoadBalancer.allServerList.isEmpty());
    assertTrue(actualLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}.</li>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_givenZoneAwareLoadBalancer_thenRuleReturnRoundRobinRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    assertTrue(actualLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default_zone", actualLoadBalancer.getName());
    assertNull(actualLoadBalancer.getPrimeConnections());
    assertNull(actualLoadBalancer.getClientConfig());
    assertNull(actualLoadBalancer.getPing());
    assertNull(actualLoadBalancer.lbTimer);
    assertEquals(10, actualLoadBalancer.getPingInterval());
    assertEquals(5, actualLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualLoadBalancer.isPingInProgress());
    assertTrue(actualLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualLoadBalancer.allServerList.isEmpty());
    assertTrue(actualLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   * <ul>
   *   <li>Then Rule return {@link AvailabilityFilteringRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_thenRuleReturnAvailabilityFilteringRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new AvailabilityFilteringRule());
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act and Assert
    IRule rule = zoneAwareLoadBalancer.getLoadBalancer("Zone").getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   * <ul>
   *   <li>Then Rule return {@link BestAvailableRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_thenRuleReturnBestAvailableRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new BestAvailableRule());
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    assertTrue(actualLoadBalancer.getRule() instanceof BestAvailableRule);
    assertTrue(actualLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default_zone", actualLoadBalancer.getName());
    assertNull(actualLoadBalancer.getPrimeConnections());
    assertNull(actualLoadBalancer.getClientConfig());
    assertNull(actualLoadBalancer.getPing());
    assertNull(actualLoadBalancer.lbTimer);
    assertEquals(10, actualLoadBalancer.getPingInterval());
    assertEquals(5, actualLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualLoadBalancer.isPingInProgress());
    assertTrue(actualLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualLoadBalancer.allServerList.isEmpty());
    assertTrue(actualLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   * <ul>
   *   <li>Then Rule return {@link RandomRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_thenRuleReturnRandomRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new RandomRule());
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    assertTrue(actualLoadBalancer.getRule() instanceof RandomRule);
    assertTrue(actualLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default_zone", actualLoadBalancer.getName());
    assertNull(actualLoadBalancer.getPrimeConnections());
    assertNull(actualLoadBalancer.getClientConfig());
    assertNull(actualLoadBalancer.getPing());
    assertNull(actualLoadBalancer.lbTimer);
    assertEquals(10, actualLoadBalancer.getPingInterval());
    assertEquals(5, actualLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualLoadBalancer.isPingInProgress());
    assertTrue(actualLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualLoadBalancer.allServerList.isEmpty());
    assertTrue(actualLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   * <ul>
   *   <li>Then Rule return {@link ZoneAvoidanceRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_thenRuleReturnZoneAvoidanceRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new ZoneAvoidanceRule());
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act and Assert
    IRule rule = zoneAwareLoadBalancer.getLoadBalancer("Zone").getRule();
    AbstractServerPredicate predicate = ((ZoneAvoidanceRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    assertTrue(rule instanceof ZoneAvoidanceRule);
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   * <ul>
   *   <li>When {@code Zone}.</li>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_whenZone_thenRuleReturnRoundRobinRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    assertTrue(actualLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default_zone", actualLoadBalancer.getName());
    assertNull(actualLoadBalancer.getPrimeConnections());
    assertNull(actualLoadBalancer.getClientConfig());
    assertNull(actualLoadBalancer.getPing());
    assertNull(actualLoadBalancer.lbTimer);
    assertEquals(10, actualLoadBalancer.getPingInterval());
    assertEquals(5, actualLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualLoadBalancer.isPingInProgress());
    assertTrue(actualLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualLoadBalancer.allServerList.isEmpty());
    assertTrue(actualLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
    assertSame(zoneAwareLoadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} addServers {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_givenZoneAwareLoadBalancerAddServersArrayList() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServers(new ArrayList<>());
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    assertEquals("default", rule.name);
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(loadBalancer.getReachableServers().isEmpty());
    assertTrue(((ZoneAwareLoadBalancer<Server>) loadBalancer).allServerList.isEmpty());
    assertTrue(((ZoneAwareLoadBalancer<Server>) loadBalancer).upServerList.isEmpty());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Filter is {@link ServerListFilter}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_givenZoneAwareLoadBalancerFilterIsServerListFilter() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setFilter(mock(ServerListFilter.class));
    zoneAwareLoadBalancer.addServers(new ArrayList<>());
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    assertEquals("default", rule.name);
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(loadBalancer.getReachableServers().isEmpty());
    assertTrue(((ZoneAwareLoadBalancer<Server>) loadBalancer).allServerList.isEmpty());
    assertTrue(((ZoneAwareLoadBalancer<Server>) loadBalancer).upServerList.isEmpty());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}.</li>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link ResponseTimeWeightedRule#name} is {@code default}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_givenZoneAwareLoadBalancer_thenResponseTimeWeightedRuleNameIsDefault() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    assertEquals("default", rule.name);
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(loadBalancer.getReachableServers().isEmpty());
    assertTrue(((ZoneAwareLoadBalancer<Server>) loadBalancer).allServerList.isEmpty());
    assertTrue(((ZoneAwareLoadBalancer<Server>) loadBalancer).upServerList.isEmpty());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) LoadBalancer {@link ZoneAwareLoadBalancer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenAvailabilityFilteringRuleLoadBalancerZoneAwareLoadBalancer() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
    assertSame(loadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) LoadBalancer {@link ZoneAwareLoadBalancer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenAvailabilityFilteringRuleLoadBalancerZoneAwareLoadBalancer2() {
    // Arrange
    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("foo", new ArrayList<>());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
    assertSame(loadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <ul>
   *   <li>Then {@link BestAvailableRule} (default constructor) LoadBalancer AllServers size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenBestAvailableRuleLoadBalancerAllServersSizeIsOne() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    Server newServer = new Server("/");
    zoneAwareLoadBalancer.addServer(newServer);
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = loadBalancer.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = ((ZoneAwareLoadBalancer<Server>) loadBalancer).allServerList;
    assertEquals(1, serverList.size());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
    assertSame(newServer, serverList.get(0));
    assertSame(serverList, ((ZoneAwareLoadBalancer<Server>) loadBalancer).upServerList);
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <ul>
   *   <li>Then {@link BestAvailableRule} (default constructor) LoadBalancer LoadBalancerStats ZoneStats size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenBestAvailableRuleLoadBalancerLoadBalancerStatsZoneStatsSizeIsOne() {
    // Arrange
    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("foo", new ArrayList<>());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    LoadBalancerStats loadBalancerStats = ((ZoneAwareLoadBalancer<Server>) loadBalancer).getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(stringListMap.get("foo").isEmpty());
    assertTrue(zoneStats.containsKey("foo"));
    assertTrue(availableZones.contains("foo"));
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
    assertSame(loadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Rule {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_whenNull_thenZoneAwareLoadBalancerRuleRoundRobinRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(new HashMap<>());

    // Act
    zoneAwareLoadBalancer.setRule(null);

    // Assert that nothing has changed
    assertTrue(zoneAwareLoadBalancer.getRule() instanceof RoundRobinRule);
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Rule {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_whenNull_thenZoneAwareLoadBalancerRuleRoundRobinRule2() {
    // Arrange
    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("foo", new ArrayList<>());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Act
    zoneAwareLoadBalancer.setRule(null);

    // Assert that nothing has changed
    assertTrue(zoneAwareLoadBalancer.getRule() instanceof RoundRobinRule);
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   * <ul>
   *   <li>When {@link RandomRule} (default constructor).</li>
   *   <li>Then {@link RandomRule} (default constructor) LoadBalancer {@link ZoneAwareLoadBalancer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_whenRandomRule_thenRandomRuleLoadBalancerZoneAwareLoadBalancer() {
    // Arrange
    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("foo", new ArrayList<>());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);
    RandomRule rule = new RandomRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    LoadBalancerStats loadBalancerStats = ((ZoneAwareLoadBalancer<Server>) loadBalancer).getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(stringListMap.get("foo").isEmpty());
    assertTrue(zoneStats.containsKey("foo"));
    assertTrue(availableZones.contains("foo"));
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
  }
}
