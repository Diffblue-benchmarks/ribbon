package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ZoneAwareLoadBalancerDiffblueTest {
  /**
   * Test {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("setting server list for zones: {}");
    assertEquals("default:setting server list for zones: {}", getResult.monitorId);
    assertEquals("setting server list for zones: {}", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones2() {
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
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones3() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    zoneServersMap.put("foo", serverList);

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("foo");
    assertEquals("default:foo", getResult.monitorId);
    assertEquals("foo", getResult.getZone());
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
    assertTrue(availableZones.contains("foo"));
    assertSame(serverList, stringListMap.get("foo"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones4() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put(" ", new ArrayList<>());
    zoneServersMap.put("foo", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(2, zoneStats.size());
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(2, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(2, availableZones.size());
    assertTrue(zoneStats.containsKey(" "));
    assertTrue(zoneStats.containsKey("foo"));
    assertTrue(stringListMap.containsKey(" "));
    assertTrue(stringListMap.containsKey("foo"));
    assertTrue(availableZones.contains("foo"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones5() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new AvailabilityFilteringRule());

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("setting server list for zones: {}");
    assertEquals("default:setting server list for zones: {}", getResult.monitorId);
    assertEquals("setting server list for zones: {}", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones6() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    zoneServersMap.replace("Setting server list for zones: {}", serverList, new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert that nothing has changed
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones7() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.computeIfPresent("Setting server list for zones: {}", mock(BiFunction.class));

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert that nothing has changed
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("setting server list for zones: {}");
    assertEquals("default:setting server list for zones: {}", getResult.monitorId);
    assertEquals("setting server list for zones: {}", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <ul>
   *   <li>Given {@link BiFunction}.
   *   <li>When {@link HashMap#HashMap()} replaceAll {@link BiFunction}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenBiFunction_whenHashMapReplaceAllBiFunction() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.replaceAll(mock(BiFunction.class));

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert that nothing has changed
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} addServer {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenZoneAwareLoadBalancerAddServerNull() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServer(null);
    zoneAwareLoadBalancer.setPing(mock(IPing.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    zoneServersMap.put("foo", serverList);

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("foo");
    assertEquals("default:foo", getResult.monitorId);
    assertEquals("foo", getResult.getZone());
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
    assertTrue(availableZones.contains("foo"));
    assertSame(serverList, stringListMap.get("foo"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} {@code Setting server list for zones: {}} size is one.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_thenHashMapSettingServerListForZonesSizeIsOne() {
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
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(getResult.get(0).isAlive());
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
    assertSame(serverList, stringListMap.get("Setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} {@code Setting server list for zones: {}} size is two.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_thenHashMapSettingServerListForZonesSizeIsTwo() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    assertEquals(1, zoneServersMap.size());
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    List<Server> getResult = zoneServersMap.get("Setting server list for zones: {}");
    assertEquals(2, getResult.size());
    assertTrue(getResult.get(0).isAlive());
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
    assertSame(serverList, stringListMap.get("Setting server list for zones: {}"));
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_whenHashMap() {
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
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_whenHashMap2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));

    // Act
    zoneAwareLoadBalancer.setServerListForZones(new HashMap<>());

    // Assert that nothing has changed
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    Server newServer = new Server(" ", "localhost", 8080);
    zoneAwareLoadBalancer.addServer(newServer);
    zoneAwareLoadBalancer.setPing(ping);
    zoneAwareLoadBalancer.setRule(null);

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    verify(ping).isAlive(isA(Server.class));
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
   *
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} addServer {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_givenZoneAwareLoadBalancerAddServerNull() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    zoneAwareLoadBalancer.addServer(null);
    zoneAwareLoadBalancer.setPing(mock(IPing.class));
    zoneAwareLoadBalancer.setRule(null);

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
   *
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}.
   *   <li>Then Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Then Rule return {@link AvailabilityFilteringRule}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_thenRuleReturnAvailabilityFilteringRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new AvailabilityFilteringRule());

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
   *
   * <ul>
   *   <li>Then Rule return {@link BestAvailableRule}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_thenRuleReturnBestAvailableRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new BestAvailableRule());

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    IRule rule = actualLoadBalancer.getRule();
    assertTrue(rule instanceof BestAvailableRule);
    assertEquals("default_zone", actualLoadBalancer.getName());
    assertSame(actualLoadBalancer, ((BestAvailableRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   *
   * <ul>
   *   <li>Then Rule return {@link RandomRule}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_thenRuleReturnRandomRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new RandomRule());

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
   *
   * <ul>
   *   <li>Then Rule return {@link RandomRule}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_thenRuleReturnRandomRule2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));
    zoneAwareLoadBalancer.setRule(new RandomRule());

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
   *
   * <ul>
   *   <li>Then Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_thenRuleReturnRoundRobinRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));
    zoneAwareLoadBalancer.setRule(null);

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
   *
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) LoadBalancer {@link
   *       ZoneAwareLoadBalancer}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Then {@link BestAvailableRule} (default constructor) LoadBalancer {@link
   *       ZoneAwareLoadBalancer}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenBestAvailableRuleLoadBalancerZoneAwareLoadBalancer() {
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
   *
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} LoadBalancer AllServers
   *       Empty.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenResponseTimeWeightedRuleLoadBalancerAllServersEmpty() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(loadBalancer.getReachableServers().isEmpty());
    assertTrue(((ZoneAwareLoadBalancer<Server>) loadBalancer).allServerList.isEmpty());
    assertTrue(((ZoneAwareLoadBalancer<Server>) loadBalancer).upServerList.isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#setRule(IRule)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Rule {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_whenNull_thenZoneAwareLoadBalancerRuleRoundRobinRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Act
    zoneAwareLoadBalancer.setRule(null);

    // Assert that nothing has changed
    assertTrue(zoneAwareLoadBalancer.getRule() instanceof RoundRobinRule);
  }
}
