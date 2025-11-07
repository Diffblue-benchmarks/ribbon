package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.PrimeConnections;
import com.netflix.loadbalancer.ServerListUpdater.UpdateAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class DynamicServerListLoadBalancerDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.<init>()"})
  public void testNewDynamicServerListLoadBalancer() {
    // Arrange and Act
    DynamicServerListLoadBalancer<Server> actualDynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    // Assert
    assertTrue(actualDynamicServerListLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualDynamicServerListLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualDynamicServerListLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualDynamicServerListLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default", actualDynamicServerListLoadBalancer.getName());
    assertNull(actualDynamicServerListLoadBalancer.getPrimeConnections());
    assertNull(actualDynamicServerListLoadBalancer.getClientConfig());
    assertNull(actualDynamicServerListLoadBalancer.getPing());
    assertNull(actualDynamicServerListLoadBalancer.getServerListImpl());
    assertNull(actualDynamicServerListLoadBalancer.getFilter());
    assertNull(actualDynamicServerListLoadBalancer.getServerListUpdater());
    assertNull(actualDynamicServerListLoadBalancer.lbTimer);
    assertEquals(10, actualDynamicServerListLoadBalancer.getPingInterval());
    assertEquals(5, actualDynamicServerListLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualDynamicServerListLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualDynamicServerListLoadBalancer.isPingInProgress());
    assertFalse(actualDynamicServerListLoadBalancer.isSecure);
    assertFalse(actualDynamicServerListLoadBalancer.useTunnel);
    assertTrue(actualDynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualDynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualDynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(actualDynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, lsrv);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenDynamicServerListLoadBalancer() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} Ping is {@link IPing}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenDynamicServerListLoadBalancerPingIsIPing() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.setServersList(new ArrayList<>());

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_thenArrayListIsDynamicServerListLoadBalancerUpServerList() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, lsrv);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_thenArrayListIsDynamicServerListLoadBalancerUpServerList2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, lsrv);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_thenArrayListIsDynamicServerListLoadBalancerUpServerList3() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, lsrv);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_thenArrayListIsDynamicServerListLoadBalancerUpServerList4() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, lsrv);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(new HashMap<>());

    // Assert that nothing has changed
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones2() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
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
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
    assertSame(serverList, stringListMap.get("Setting server list for zones: {}"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones3() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    zoneServersMap.put("ZoneStats_", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("zonestats_");
    assertEquals("default:zonestats_", getResult.monitorId);
    assertEquals("zonestats_", getResult.getZone());
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
    assertTrue(availableZones.contains("ZoneStats_"));
    assertSame(serverList, stringListMap.get("ZoneStats_"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones4() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServers(new Object[]{-1});

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    zoneServersMap.put("ZoneStats_", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("zonestats_");
    assertEquals("default:zonestats_", getResult.monitorId);
    assertEquals("zonestats_", getResult.getZone());
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
    assertTrue(availableZones.contains("ZoneStats_"));
    assertSame(serverList, stringListMap.get("ZoneStats_"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones5() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServers(new Object[]{0});
    dynamicServerListLoadBalancer.addServers(new Object[]{-1});

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    zoneServersMap.put("ZoneStats_", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("zonestats_");
    assertEquals("default:zonestats_", getResult.monitorId);
    assertEquals("zonestats_", getResult.getZone());
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
    assertTrue(availableZones.contains("ZoneStats_"));
    assertSame(serverList, stringListMap.get("ZoneStats_"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones6() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServers(new Object[]{-1});

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    zoneServersMap.put("42", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("42");
    assertEquals("42", getResult.getZone());
    assertEquals("default:42", getResult.monitorId);
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
    assertTrue(availableZones.contains("42"));
    assertSame(serverList, stringListMap.get("42"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones7() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPrimeConnections(
        new PrimeConnections("Setting server list for zones: {}", 3, 1L, "Setting server list for zones: {}"));
    dynamicServerListLoadBalancer.addServers(new Object[]{-1});

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    ArrayList<Server> serverList = new ArrayList<>();
    zoneServersMap.put("ZoneStats_", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("zonestats_");
    assertEquals("default:zonestats_", getResult.monitorId);
    assertEquals("zonestats_", getResult.getZone());
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
    assertTrue(availableZones.contains("ZoneStats_"));
    assertSame(serverList, stringListMap.get("ZoneStats_"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenArrayListAddServerWithIdIs42() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
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
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
    assertSame(serverList, stringListMap.get("Setting server list for zones: {}"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenArrayListAddServerWithIdIs422() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
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
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
    assertSame(serverList, stringListMap.get("Setting server list for zones: {}"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code _Counter}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenArrayListAddServerWithIdIsCounter() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("_Counter"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, ZoneStats> zoneStats = loadBalancerStats.getZoneStats();
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
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
    assertSame(serverList, stringListMap.get("Setting server list for zones: {}"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DynamicServerListLoadBalancer#setFilter(ServerListFilter)}
   *   <li>{@link DynamicServerListLoadBalancer#setServerListImpl(ServerList)}
   *   <li>{@link DynamicServerListLoadBalancer#setServerListUpdater(ServerListUpdater)}
   *   <li>{@link DynamicServerListLoadBalancer#forceQuickPing()}
   *   <li>{@link DynamicServerListLoadBalancer#getFilter()}
   *   <li>{@link DynamicServerListLoadBalancer#getServerListImpl()}
   *   <li>{@link DynamicServerListLoadBalancer#getServerListUpdater()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.forceQuickPing()",
      "ServerListFilter DynamicServerListLoadBalancer.getFilter()",
      "ServerList DynamicServerListLoadBalancer.getServerListImpl()",
      "ServerListUpdater DynamicServerListLoadBalancer.getServerListUpdater()",
      "void DynamicServerListLoadBalancer.setFilter(ServerListFilter)",
      "void DynamicServerListLoadBalancer.setServerListImpl(ServerList)",
      "void DynamicServerListLoadBalancer.setServerListUpdater(ServerListUpdater)",
      "String DynamicServerListLoadBalancer.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    ServerListFilter<Server> filter = mock(ServerListFilter.class);

    // Act
    dynamicServerListLoadBalancer.setFilter(filter);
    ConfigurationBasedServerList niwsServerList = new ConfigurationBasedServerList();
    dynamicServerListLoadBalancer.setServerListImpl(niwsServerList);
    PollingServerListUpdater serverListUpdater = new PollingServerListUpdater();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    dynamicServerListLoadBalancer.forceQuickPing();
    ServerListFilter<Server> actualFilter = dynamicServerListLoadBalancer.getFilter();
    ServerList<Server> actualServerListImpl = dynamicServerListLoadBalancer.getServerListImpl();

    // Assert
    assertTrue(actualServerListImpl instanceof ConfigurationBasedServerList);
    assertSame(niwsServerList, actualServerListImpl);
    assertSame(serverListUpdater, dynamicServerListLoadBalancer.getServerListUpdater());
    assertSame(filter, actualFilter);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature()"})
  public void testEnableAndInitLearnNewServersFeature() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature();

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature()"})
  public void testEnableAndInitLearnNewServersFeature2() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature();

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature()"})
  public void testEnableAndInitLearnNewServersFeature3() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature();

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature()"})
  public void testEnableAndInitLearnNewServersFeature4() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    zoneAwareLoadBalancer.setPing(mock(IPing.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    zoneAwareLoadBalancer.enableAndInitLearnNewServersFeature();

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature()"})
  public void testEnableAndInitLearnNewServersFeature_thenThrowRuntimeException() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doThrow(new RuntimeException("foo42foo")).when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature();
    verify(serverListUpdater).start(isA(UpdateAction.class));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature()"})
  public void testEnableAndInitLearnNewServersFeature_thenThrowRuntimeException2() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doThrow(new RuntimeException("foo42foo")).when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature();
    verify(serverListUpdater).start(isA(UpdateAction.class));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.stopServerListRefreshing()"})
  public void testStopServerListRefreshing() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doThrow(new RuntimeException("foo")).when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.stopServerListRefreshing();
    verify(serverListUpdater).stop();
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.stopServerListRefreshing()"})
  public void testStopServerListRefreshing2() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doThrow(new RuntimeException("foo")).when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.addServers(new Object[]{","});
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.stopServerListRefreshing();
    verify(serverListUpdater).stop();
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}.
   * <ul>
   *   <li>Given {@link PollingServerListUpdater} {@link PollingServerListUpdater#stop()} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.stopServerListRefreshing()"})
  public void testStopServerListRefreshing_givenPollingServerListUpdaterStopDoesNothing() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.stopServerListRefreshing();

    // Assert
    verify(serverListUpdater).stop();
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.stopServerListRefreshing()"})
  public void testStopServerListRefreshing_thenThrowRuntimeException() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doThrow(new RuntimeException("foo")).when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.stopServerListRefreshing();
    verify(serverListUpdater).stop();
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers3() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers4() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers5() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers6() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers7() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("localhost", 8080));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers_givenDynamicServerListLoadBalancer() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert that nothing has changed
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} addServer {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers_givenDynamicServerListLoadBalancerAddServerNull() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    dynamicServerListLoadBalancer.addServer(null);

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert that nothing has changed
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("Setting server list for zones: {}")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("/"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList3() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_givenDynamicServerListLoadBalancer() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} Ping is {@link IPing}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_givenDynamicServerListLoadBalancerPingIsIPing() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(new ArrayList<>());

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code /} Alive is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_givenServerWithIdIsSlashAliveIsTrue() {
    // Arrange
    Server newServer = new Server("/");
    newServer.setAlive(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(newServer);
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_thenArrayListIsDynamicServerListLoadBalancerUpServerList() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_thenArrayListIsDynamicServerListLoadBalancerUpServerList2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_thenArrayListIsDynamicServerListLoadBalancerUpServerList3() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_thenArrayListIsDynamicServerListLoadBalancerUpServerList4() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]: clearing server list (SET op)")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getLastUpdate()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#getLastUpdate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DynamicServerListLoadBalancer.getLastUpdate()"})
  public void testGetLastUpdate() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    when(serverListUpdater.getLastUpdate()).thenReturn("");

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    String actualLastUpdate = dynamicServerListLoadBalancer.getLastUpdate();

    // Assert
    verify(serverListUpdater).getLastUpdate();
    assertEquals("", actualLastUpdate);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getLastUpdate()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#getLastUpdate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DynamicServerListLoadBalancer.getLastUpdate()"})
  public void testGetLastUpdate2() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    when(serverListUpdater.getLastUpdate()).thenReturn("");

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    String actualLastUpdate = zoneAwareLoadBalancer.getLastUpdate();

    // Assert
    verify(serverListUpdater).getLastUpdate();
    assertEquals("", actualLastUpdate);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getLastUpdate()}.
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} addServer {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#getLastUpdate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DynamicServerListLoadBalancer.getLastUpdate()"})
  public void testGetLastUpdate_givenDynamicServerListLoadBalancerAddServerServerWithIdIs42() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    when(serverListUpdater.getLastUpdate()).thenReturn("2020-03-01");

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setMaxTotalPingTime(1);
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    String actualLastUpdate = dynamicServerListLoadBalancer.getLastUpdate();

    // Assert
    verify(serverListUpdater).getLastUpdate();
    assertEquals("2020-03-01", actualLastUpdate);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getLastUpdate()}.
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} MaxTotalPingTime is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#getLastUpdate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DynamicServerListLoadBalancer.getLastUpdate()"})
  public void testGetLastUpdate_givenDynamicServerListLoadBalancerMaxTotalPingTimeIsOne() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    when(serverListUpdater.getLastUpdate()).thenReturn("2020-03-01");

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setMaxTotalPingTime(1);
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    String actualLastUpdate = dynamicServerListLoadBalancer.getLastUpdate();

    // Assert
    verify(serverListUpdater).getLastUpdate();
    assertEquals("2020-03-01", actualLastUpdate);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getLastUpdate()}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} UpServerList is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#getLastUpdate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DynamicServerListLoadBalancer.getLastUpdate()"})
  public void testGetLastUpdate_givenZoneAwareLoadBalancerUpServerListIsArrayList() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    when(serverListUpdater.getLastUpdate()).thenReturn("");

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setUpServerList(new ArrayList<>());
    zoneAwareLoadBalancer.addServers(new Object[]{"New Servers"});
    zoneAwareLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    String actualLastUpdate = zoneAwareLoadBalancer.getLastUpdate();

    // Assert
    verify(serverListUpdater).getLastUpdate();
    assertEquals("", actualLastUpdate);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getLastUpdate()}.
   * <ul>
   *   <li>Then return {@code 2020-03-01}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#getLastUpdate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DynamicServerListLoadBalancer.getLastUpdate()"})
  public void testGetLastUpdate_thenReturn20200301() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    when(serverListUpdater.getLastUpdate()).thenReturn("2020-03-01");

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    String actualLastUpdate = dynamicServerListLoadBalancer.getLastUpdate();

    // Assert
    verify(serverListUpdater).getLastUpdate();
    assertEquals("2020-03-01", actualLastUpdate);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getDurationSinceLastUpdateMs()}.
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#getDurationSinceLastUpdateMs()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long DynamicServerListLoadBalancer.getDurationSinceLastUpdateMs()"})
  public void testGetDurationSinceLastUpdateMs() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    when(serverListUpdater.getDurationSinceLastUpdateMs()).thenReturn(1L);

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    long actualDurationSinceLastUpdateMs = dynamicServerListLoadBalancer.getDurationSinceLastUpdateMs();

    // Assert
    verify(serverListUpdater).getDurationSinceLastUpdateMs();
    assertEquals(1L, actualDurationSinceLastUpdateMs);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getDurationSinceLastUpdateMs()}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#getDurationSinceLastUpdateMs()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long DynamicServerListLoadBalancer.getDurationSinceLastUpdateMs()"})
  public void testGetDurationSinceLastUpdateMs_thenReturnOne() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    when(serverListUpdater.getDurationSinceLastUpdateMs()).thenReturn(1L);

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    long actualDurationSinceLastUpdateMs = dynamicServerListLoadBalancer.getDurationSinceLastUpdateMs();

    // Assert
    verify(serverListUpdater).getDurationSinceLastUpdateMs();
    assertEquals(1L, actualDurationSinceLastUpdateMs);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getNumberMissedCycles()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#getNumberMissedCycles()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int DynamicServerListLoadBalancer.getNumberMissedCycles()"})
  public void testGetNumberMissedCycles_thenReturnZero() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(new PollingServerListUpdater());

    // Act and Assert
    assertEquals(0, dynamicServerListLoadBalancer.getNumberMissedCycles());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getCoreThreads()}.
   * <ul>
   *   <li>Then return two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerListLoadBalancer#getCoreThreads()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int DynamicServerListLoadBalancer.getCoreThreads()"})
  public void testGetCoreThreads_thenReturnTwo() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(new PollingServerListUpdater());

    // Act and Assert
    assertEquals(2, dynamicServerListLoadBalancer.getCoreThreads());
  }
}
