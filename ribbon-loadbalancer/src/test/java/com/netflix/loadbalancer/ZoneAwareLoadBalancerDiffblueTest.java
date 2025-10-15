package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.DynamicServerListLoadBalancerTest.MyServerList;
import java.util.ArrayList;
import java.util.Collection;
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

public class ZoneAwareLoadBalancerDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

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
   * Test {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer(IClientConfig, IRule, IPing,
   * ServerList, ServerListFilter)}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer(IClientConfig, IRule,
   * IPing, ServerList, ServerListFilter)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ZoneAwareLoadBalancer.<init>(IClientConfig, IRule, IPing, ServerList, ServerListFilter)"
  })
  public void testNewZoneAwareLoadBalancer2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    new ZoneAwareLoadBalancer<>(
        clientConfig, rule, mock(IPing.class), null, mock(ServerListFilter.class));

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    assertNull(((ZoneAwareLoadBalancer<Server>) loadBalancer).getServerListImpl());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
    assertSame(loadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer(IClientConfig, IRule, IPing,
   * ServerList, ServerListFilter)}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer(IClientConfig, IRule,
   * IPing, ServerList, ServerListFilter)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ZoneAwareLoadBalancer.<init>(IClientConfig, IRule, IPing, ServerList, ServerListFilter)"
  })
  public void testNewZoneAwareLoadBalancer3() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    MyServerList serverList = new MyServerList();

    // Act
    new ZoneAwareLoadBalancer<>(clientConfig, rule, ping, serverList, mock(ServerListFilter.class));

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
    assertSame(serverList, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getServerListImpl());
    assertSame(loadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer(IClientConfig, IRule, IPing,
   * ServerList, ServerListFilter)}.
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer(IClientConfig, IRule,
   * IPing, ServerList, ServerListFilter)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ZoneAwareLoadBalancer.<init>(IClientConfig, IRule, IPing, ServerList, ServerListFilter)"
  })
  public void testNewZoneAwareLoadBalancer4() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    BestAvailableRule rule = new BestAvailableRule();
    IPing ping = mock(IPing.class);
    MyServerList serverList = new MyServerList();

    // Act
    new ZoneAwareLoadBalancer<>(clientConfig, rule, ping, serverList, mock(ServerListFilter.class));

    // Assert
    ILoadBalancer loadBalancer = rule.getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    assertSame(rule, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getRule());
    assertSame(serverList, ((ZoneAwareLoadBalancer<Server>) loadBalancer).getServerListImpl());
    assertSame(loadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer(IClientConfig, IRule, IPing,
   * ServerList, ServerListFilter, ServerListUpdater)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer(IClientConfig, IRule,
   * IPing, ServerList, ServerListFilter, ServerListUpdater)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ZoneAwareLoadBalancer.<init>(IClientConfig, IRule, IPing, ServerList, ServerListFilter, ServerListUpdater)"
  })
  public void testNewZoneAwareLoadBalancer_givenNull_thenThrowRuntimeException() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setClientName(null);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    ConfigurationBasedServerList serverList = new ConfigurationBasedServerList();
    ServerListFilter<Server> filter = mock(ServerListFilter.class);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    new ZoneAwareLoadBalancer<>(
        clientConfig, rule, ping, serverList, filter, new PollingServerListUpdater());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#initWithNiwsConfig(IClientConfig)} with {@code clientConfig}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig_givenNull() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener4 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener4).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener5 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener5).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener6 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener6)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerStatusChangeListener(listener6);
    zoneAwareLoadBalancer.addServerStatusChangeListener(listener5);
    zoneAwareLoadBalancer.addServerStatusChangeListener(listener4);
    zoneAwareLoadBalancer.addServerListChangeListener(listener3);
    zoneAwareLoadBalancer.addServerStatusChangeListener(listener2);
    zoneAwareLoadBalancer.addServerStatusChangeListener(listener);
    zoneAwareLoadBalancer.setPing(ping);
    zoneAwareLoadBalancer.addServer(new Server("42"));

    DefaultClientConfigImpl clientConfig = new DefaultClientConfigImpl();
    clientConfig.setClientName(null);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    zoneAwareLoadBalancer.initWithNiwsConfig(clientConfig);
    verify(ping).isAlive(isA(Server.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener6).serverStatusChanged(isA(Collection.class));
    verify(listener5).serverStatusChanged(isA(Collection.class));
    verify(listener4).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
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
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>When {@link HashMap#HashMap()} All is {@link HashMap#HashMap()}.
   *   <li>Then {@link HashMap#HashMap()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenHashMap_whenHashMapAllIsHashMap_thenHashMapEmpty() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.putAll(new HashMap<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert that nothing has changed
    assertTrue(zoneServersMap.isEmpty());
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
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
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
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} addServer {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenZoneAwareLoadBalancerAddServerNull2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
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
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} addServer {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenZoneAwareLoadBalancerAddServerNull3() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
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
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} addServers {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenZoneAwareLoadBalancerAddServersArrayList() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServers(new ArrayList<>());
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
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
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} chooseServer {@code Key}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenZoneAwareLoadBalancerChooseServerKey() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.chooseServer("Key");
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
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
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Filter is {@link
   *       ServerListFilter}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenZoneAwareLoadBalancerFilterIsServerListFilter() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setFilter(mock(ServerListFilter.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
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
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} UpServerList is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenZoneAwareLoadBalancerUpServerListIsArrayList() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setUpServerList(new ArrayList<>());
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    zoneAwareLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
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
   *   <li>Then {@link HashMap#HashMap()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_whenHashMap_thenHashMapEmpty() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert that nothing has changed
    assertTrue(zoneServersMap.isEmpty());
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
   *   <li>Then {@link HashMap#HashMap()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_whenHashMap_thenHashMapEmpty2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));
    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert that nothing has changed
    assertTrue(zoneServersMap.isEmpty());
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Rule is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_givenZoneAwareLoadBalancerRuleIsNull() {
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
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Rule is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_givenZoneAwareLoadBalancerRuleIsNull2() {
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
   * Test {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Rule is {@link
   *       RoundRobinRule#RoundRobinRule()}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_givenZoneAwareLoadBalancerRuleIsRoundRobinRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));
    zoneAwareLoadBalancer.setRule(new RoundRobinRule());

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
  public void testGetLoadBalancer_thenRuleReturnBestAvailableRule2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));
    zoneAwareLoadBalancer.setRule(new BestAvailableRule());

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
   *
   * <ul>
   *   <li>Then Rule return {@link ClientConfigEnabledRoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer ZoneAwareLoadBalancer.getLoadBalancer(String)"})
  public void testGetLoadBalancer_thenRuleReturnClientConfigEnabledRoundRobinRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setPing(mock(IPing.class));
    zoneAwareLoadBalancer.setRule(new ClientConfigEnabledRoundRobinRule());

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    assertTrue(actualLoadBalancer.getRule() instanceof ClientConfigEnabledRoundRobinRule);
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
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link
   *       ResponseTimeWeightedRule#name} is {@code default}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAwareLoadBalancer.setRule(IRule)"})
  public void testSetRule_thenResponseTimeWeightedRuleNameIsDefault() {
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
