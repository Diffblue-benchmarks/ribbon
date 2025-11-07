package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.PrimeConnections;
import com.netflix.client.PrimeConnections.PrimeConnectionEndStats;
import com.netflix.loadbalancer.AbstractLoadBalancer.ServerGroup;
import com.netflix.loadbalancer.BaseLoadBalancer.PingTask;
import com.netflix.loadbalancer.BaseLoadBalancer.Pinger;
import com.netflix.loadbalancer.RandomLBTest.PingFake;
import com.netflix.util.concurrent.ShutdownEnabledTimer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class BaseLoadBalancerDiffblueTest {
  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>()"})
  public void testNewBaseLoadBalancer() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer();

    // Assert
    assertTrue(actualBaseLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualBaseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer2() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(
        "com.netflix.loadbalancer.IPing$MockitoMock$1813567985", rule, new LoadBalancerStats(), mock(IPing.class));

    // Assert
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$1813567985", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>()"})
  public void testNewBaseLoadBalancer3() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer();

    // Assert
    assertTrue(actualBaseLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualBaseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer4() {
    // Arrange
    LoadBalancerStats stats = new LoadBalancerStats();
    stats.incrementZoneCounter(new Server("com.netflix.loadbalancer.Server"));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", null, stats, mock(IPing.class));

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Map<String, ZoneStats> zoneStats = actualBaseLoadBalancer.getLoadBalancerStats().getZoneStats();
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
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer5() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(
        "com.netflix.loadbalancer.IPing$MockitoMock$780772852", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertEquals("com.netflix.loadbalancer.IPing$MockitoMock$780772852", actualBaseLoadBalancer.getName());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_givenHashMap() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.updateZoneServerMapping(new HashMap<>());
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Shutdown hook installed for: {}", rule, stats, ping,
        pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertEquals("Shutdown hook installed for: {}", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException(String)} with space.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_givenRuntimeExceptionWithSpace() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any())).thenThrow(new RuntimeException(" "));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>Given {@link Server#Server(String)} with {@code Id}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_givenServerWithId() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.incrementZoneCounter(new Server("Id"));

    // Act and Assert
    assertSame(rule, (new BaseLoadBalancer("Name", rule, stats, mock(IPing.class))).getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, new LoadBalancerStats());

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero2() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, new LoadBalancerStats(),
        mock(IPing.class));

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero3() {
    // Arrange
    IPing ping = mock(IPing.class);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero4() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, new LoadBalancerStats(),
        mock(IPing.class));

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>Then {@link AvailabilityFilteringRule} (default constructor) AvailableServersCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_thenAvailabilityFilteringRuleAvailableServersCountIsZero5() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link ResponseTimeWeightedRule#name} is {@code default}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_thenResponseTimeWeightedRuleNameIsDefault() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(null, rule);

    // Assert
    assertEquals("default", rule.name);
    assertNull(actualBaseLoadBalancer.getPing());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>Then return LoadBalancerStats ZoneStats containsKey {@code unknown}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenReturnLoadBalancerStatsZoneStatsContainsKeyUnknown() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.incrementZoneCounter(new Server(""));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, mock(IPing.class));

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    Map<String, ZoneStats> zoneStats = actualBaseLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    assertTrue(zoneStats.containsKey("unknown"));
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>Then return LoadBalancerStats ZoneStats size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_thenReturnLoadBalancerStatsZoneStatsSizeIsOne() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server(""));

    // Act and Assert
    Map<String, ZoneStats> zoneStats = (new BaseLoadBalancer(" ", rule, lbStats)).getLoadBalancerStats().getZoneStats();
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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>Then return LoadBalancerStats ZoneStats size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_thenReturnLoadBalancerStatsZoneStatsSizeIsOne2() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.incrementZoneCounter(new Server("42"));
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(
        "com.netflix.loadbalancer.IPing$MockitoMock$780772852", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    Map<String, ZoneStats> zoneStats = actualBaseLoadBalancer.getLoadBalancerStats().getZoneStats();
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
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>Then return Name is {@code NFLoadBalancer-PingTimer-}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenReturnNameIsNFLoadBalancerPingTimer() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("NFLoadBalancer-PingTimer-", null,
        new LoadBalancerStats(), ping);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertEquals("NFLoadBalancer-PingTimer-", actualBaseLoadBalancer.getName());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>Then return Name is {@code Shutdown hook installed for: {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_thenReturnNameIsShutdownHookInstalledFor() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Shutdown hook installed for: {}", rule, stats, ping,
        pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertEquals("Shutdown hook installed for: {}", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>Then return Rule is {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_thenReturnRuleIsResponseTimeWeightedRule() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>Then return Rule is {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_thenReturnRuleIsResponseTimeWeightedRule2() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false, true, false, true, false, true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>Then return Rule is {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_thenReturnRuleIsWeightedResponseTimeRule() {
    // Arrange
    WeightedResponseTimeRule rule = new WeightedResponseTimeRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>Then Rule return {@link AvailabilityFilteringRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_thenRuleReturnAvailabilityFilteringRule() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), null);

    // Assert
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof AvailabilityFilteringRule);
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule2).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>Then Rule return {@link BestAvailableRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_thenRuleReturnBestAvailableRule() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();
    LoadBalancerStats stats = new LoadBalancerStats(" ");
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof BestAvailableRule);
    LoadBalancerStats loadBalancerStats = actualBaseLoadBalancer.getLoadBalancerStats();
    assertEquals(" ", loadBalancerStats.getName());
    assertSame(actualBaseLoadBalancer, ((BestAvailableRule) rule2).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
    assertSame(stats, loadBalancerStats);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_thenRuleReturnRoundRobinRule() {
    // Arrange
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any())).thenThrow(new RuntimeException(" "));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", null, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertTrue(actualBaseLoadBalancer.getLoadBalancerStats().getZoneStats().isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then Rule return {@link ResponseTimeWeightedRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_when42_thenRuleReturnResponseTimeWeightedRule() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("42", rule, new LoadBalancerStats(),
        mock(IPing.class));

    // Assert
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof ResponseTimeWeightedRule);
    assertEquals("42", actualBaseLoadBalancer.getName());
    assertEquals("42", rule.name);
    assertEquals("42", ((ResponseTimeWeightedRule) rule2).name);
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>When {@link AvailabilityFilteringRule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenAvailabilityFilteringRule() {
    // Arrange
    IPing ping = mock(IPing.class);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>When {@link AvailabilityFilteringRule} (default constructor).</li>
   *   <li>Then return Ping is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenAvailabilityFilteringRule_thenReturnPingIsNull() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(null, rule);

    // Assert
    assertNull(actualBaseLoadBalancer.getPing());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When {@link AvailabilityFilteringRule} (default constructor).</li>
   *   <li>Then return Ping is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenAvailabilityFilteringRule_thenReturnPingIsNull2() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), null);

    // Assert
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>When {@link AvailabilityFilteringRule} (default constructor).</li>
   *   <li>Then return Ping is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenAvailabilityFilteringRule_thenReturnPingIsNull3() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(null, rule);

    // Assert
    assertNull(actualBaseLoadBalancer.getPing());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).</li>
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule() {
    // Arrange
    IPing ping = mock(IPing.class);
    BestAvailableRule rule = new BestAvailableRule();

    // Act and Assert
    assertSame(rule, (new BaseLoadBalancer(ping, rule)).getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).</li>
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule2() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act and Assert
    assertSame(rule, (new BaseLoadBalancer("Lb Name", rule, new LoadBalancerStats())).getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).</li>
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule3() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act and Assert
    assertSame(rule, (new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), mock(IPing.class))).getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).</li>
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule4() {
    // Arrange
    IPing ping = mock(IPing.class);
    BestAvailableRule rule = new BestAvailableRule();

    // Act and Assert
    assertSame(rule, (new BaseLoadBalancer(ping, rule)).getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).</li>
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule5() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act and Assert
    assertSame(rule, (new BaseLoadBalancer("Lb Name", rule, new LoadBalancerStats())).getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).</li>
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule6() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act and Assert
    assertSame(rule, (new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), mock(IPing.class))).getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>When {@link BestAvailableRule} (default constructor).</li>
   *   <li>Then return Rule is {@link BestAvailableRule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_whenBestAvailableRule_thenReturnRuleIsBestAvailableRule7() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>When {@code class}.</li>
   *   <li>Then return Name is {@code class}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenClass_thenReturnNameIsClass() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("class", rule, new LoadBalancerStats());

    // Assert
    assertEquals("class", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>When {@code default}.</li>
   *   <li>Then return Name is {@code default}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_whenDefault_thenReturnNameIsDefault() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("default", rule, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    assertEquals("default", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>When {@link DummyPing} (default constructor).</li>
   *   <li>Then Ping return {@link DummyPing}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenDummyPing_thenPingReturnDummyPing() {
    // Arrange
    DummyPing ping = new DummyPing();
    ClientConfigEnabledRoundRobinRule rule = new ClientConfigEnabledRoundRobinRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    IPing ping2 = actualBaseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertNull(((DummyPing) ping2).getLoadBalancer());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, ping2);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>When {@link DummyPing} (default constructor).</li>
   *   <li>Then Ping return {@link DummyPing}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenDummyPing_thenPingReturnDummyPing2() {
    // Arrange
    DummyPing ping = new DummyPing();
    ClientConfigEnabledRoundRobinRule rule = new ClientConfigEnabledRoundRobinRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, rule);

    // Assert
    IPing ping2 = actualBaseLoadBalancer.getPing();
    assertTrue(ping2 instanceof DummyPing);
    assertNull(((DummyPing) ping2).getLoadBalancer());
    assertSame(rule, actualBaseLoadBalancer.getRule());
    assertSame(ping, ping2);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>When {@link IPing}.</li>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenIPing_thenRuleReturnRoundRobinRule() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, null);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>When {@code Lb Name}.</li>
   *   <li>Then return Name is {@code Lb Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenLbName_thenReturnNameIsLbName() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", rule, new LoadBalancerStats());

    // Assert
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenNull_thenReturnName() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", null, new LoadBalancerStats(),
        mock(IPing.class));

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertTrue(actualBaseLoadBalancer.getLoadBalancerStats().getZoneStats().isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Ping is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenNull_thenReturnPingIsNull() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", null, new LoadBalancerStats(), null);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then Rule return {@link AvailabilityFilteringRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnAvailabilityFilteringRule() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", new AvailabilityFilteringRule(), null);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getLoadBalancerStats());
    assertSame(actualBaseLoadBalancer, ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", null, lbStats);

    // Assert
    assertTrue(actualBaseLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualBaseLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualBaseLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertNull(actualBaseLoadBalancer.getPrimeConnections());
    assertNull(actualBaseLoadBalancer.getClientConfig());
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertEquals(10, actualBaseLoadBalancer.getPingInterval());
    assertEquals(5, actualBaseLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualBaseLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualBaseLoadBalancer.isPingInProgress());
    assertTrue(actualBaseLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualBaseLoadBalancer.allServerList.isEmpty());
    assertTrue(actualBaseLoadBalancer.upServerList.isEmpty());
    assertSame(lbStats, actualBaseLoadBalancer.getLoadBalancerStats());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", null, new LoadBalancerStats(), ping);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(IPing, IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(IPing, IRule)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule3() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(ping, null);

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualBaseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
    assertSame(ping, actualBaseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule4() {
    // Arrange and Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Lb Name", null, new LoadBalancerStats());

    // Assert
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertEquals("Lb Name", actualBaseLoadBalancer.getName());
    assertTrue(actualBaseLoadBalancer.getLoadBalancerStats().getZoneStats().isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing, IPingStrategy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing, IPingStrategy)"})
  public void testNewBaseLoadBalancer_whenNull_thenRuleReturnRoundRobinRule5() {
    // Arrange
    LoadBalancerStats stats = new LoadBalancerStats();
    IPing ping = mock(IPing.class);
    IPingStrategy pingStrategy = mock(IPingStrategy.class);
    when(pingStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", null, stats, ping, pingStrategy);

    // Assert
    verify(pingStrategy, atLeast(1)).pingServers(isA(IPing.class), Mockito.<Server[]>any());
    IRule rule = actualBaseLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertEquals("Name", actualBaseLoadBalancer.getName());
    assertTrue(actualBaseLoadBalancer.getLoadBalancerStats().getZoneStats().isEmpty());
    assertSame(actualBaseLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When {@link RandomRule} (default constructor).</li>
   *   <li>Then Rule return {@link RandomRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenRandomRule_thenRuleReturnRandomRule() {
    // Arrange
    RandomRule rule = new RandomRule();

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.incrementZoneCounter(new Server("com.netflix.loadbalancer.Server"));

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, stats, mock(IPing.class));

    // Assert
    IRule rule2 = actualBaseLoadBalancer.getRule();
    assertTrue(rule2 instanceof RandomRule);
    Map<String, ZoneStats> zoneStats = actualBaseLoadBalancer.getLoadBalancerStats().getZoneStats();
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
    assertSame(actualBaseLoadBalancer, rule2.getLoadBalancer());
    assertSame(rule, rule2);
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenResponseTimeWeightedRule() {
    // Arrange
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("Name", rule, new LoadBalancerStats(), null);

    // Assert
    assertEquals("Name", rule.name);
    assertNull(actualBaseLoadBalancer.getPing());
    assertNull(actualBaseLoadBalancer.lbTimer);
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>When space.</li>
   *   <li>Then return Name is space.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenSpace_thenReturnNameIsSpace() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(" ", rule, new LoadBalancerStats());

    // Assert
    assertEquals(" ", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}.
   * <ul>
   *   <li>When space.</li>
   *   <li>Then return Name is space.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats)"})
  public void testNewBaseLoadBalancer_whenSpace_thenReturnNameIsSpace2() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(" ", rule, new LoadBalancerStats());

    // Assert
    assertEquals(" ", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When space.</li>
   *   <li>Then return Name is space.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenSpace_thenReturnNameIsSpace3() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer(" ", rule, new LoadBalancerStats(),
        mock(IPing.class));

    // Assert
    assertEquals(" ", actualBaseLoadBalancer.getName());
    assertEquals(0, rule.getAvailableServersCount());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}.
   * <ul>
   *   <li>When {@code tagArray}.</li>
   *   <li>Then return Name is {@code tagArray}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#BaseLoadBalancer(String, IRule, LoadBalancerStats, IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.<init>(String, IRule, LoadBalancerStats, IPing)"})
  public void testNewBaseLoadBalancer_whenTagArray_thenReturnNameIsTagArray() {
    // Arrange
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    BaseLoadBalancer actualBaseLoadBalancer = new BaseLoadBalancer("tagArray", rule, new LoadBalancerStats(),
        mock(IPing.class));

    // Assert
    assertEquals("tagArray", actualBaseLoadBalancer.getName());
    assertSame(rule, actualBaseLoadBalancer.getRule());
  }

  /**
   * Test PingTask {@link PingTask#run()}.
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code false}.</li>
   *   <li>Then calls {@link IPing#isAlive(Server)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PingTask#run()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingTask.run()"})
  public void testPingTaskRun_givenIPingIsAliveReturnFalse_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(false);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    (baseLoadBalancer.new PingTask()).run();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Test PingTask {@link PingTask#run()}.
   * <ul>
   *   <li>Then calls {@link ServerStatusChangeListener#serverStatusChanged(Collection)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PingTask#run()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingTask.run()"})
  public void testPingTaskRun_thenCallsServerStatusChanged() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    (baseLoadBalancer.new PingTask()).run();

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   * <p>
   * Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger() throws Exception {
    // Arrange
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    ((new BaseLoadBalancer()).new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(pingerStrategy).pingServers(isNull(), isA(Server[].class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   * <p>
   * Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger2() throws Exception {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    (baseLoadBalancer.new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   * <p>
   * Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger3() throws Exception {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    (baseLoadBalancer.new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    verify(listener, atLeast(1)).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger_givenBaseLoadBalancerAddServerServerWithIdIs42() throws Exception {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    (baseLoadBalancer.new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(pingerStrategy).pingServers(isNull(), isA(Server[].class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code false}.</li>
   *   <li>Then calls {@link IPing#isAlive(Server)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger_givenIPingIsAliveReturnFalse_thenCallsIsAlive() throws Exception {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(false);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    (baseLoadBalancer.new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
  }

  /**
   * Test Pinger {@link Pinger#runPinger()}.
   * <ul>
   *   <li>Then calls {@link ServerStatusChangeListener#serverStatusChanged(Collection)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pinger#runPinger()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Pinger.runPinger()"})
  public void testPingerRunPinger_thenCallsServerStatusChanged() throws Exception {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new RandomRule());
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));
    IPingStrategy pingerStrategy = mock(IPingStrategy.class);
    when(pingerStrategy.pingServers(Mockito.<IPing>any(), Mockito.<Server[]>any()))
        .thenReturn(new boolean[]{true, false, true, false});

    // Act
    (baseLoadBalancer.new Pinger(pingerStrategy)).runPinger();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(pingerStrategy).pingServers(isA(IPing.class), isA(Server[].class));
    verify(listener, atLeast(1)).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test {@link BaseLoadBalancer#removeServerListChangeListener(ServerListChangeListener)}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#removeServerListChangeListener(ServerListChangeListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.removeServerListChangeListener(ServerListChangeListener)"})
  public void testRemoveServerListChangeListener_thenBaseLoadBalancerReachableServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.removeServerListChangeListener(mock(ServerListChangeListener.class));

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName("LoadBalancer:  PingTask executing [{}] servers configured");

    // Assert
    assertEquals("LoadBalancer:  PingTask executing [{}] servers configured", baseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertEquals("LoadBalancer:  PingTask executing [{}] servers configured", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} LoadBalancerStats is {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_givenBaseLoadBalancerLoadBalancerStatsIsLoadBalancerStats_whenName() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(new LoadBalancerStats());

    // Act
    baseLoadBalancer.setName("Name");

    // Assert that nothing has changed
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_givenBaseLoadBalancer_whenName() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setName("Name");

    // Assert that nothing has changed
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Name is {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_whenName_thenBaseLoadBalancerNameIsName() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName("Name");

    // Assert
    assertEquals("Name", baseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertEquals("Name", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setName(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} Name is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setName(String)"})
  public void testSetName_whenNull_thenBaseLoadBalancerNameIsNull() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(null);

    // Act
    baseLoadBalancer.setName(null);

    // Assert
    assertNull(baseLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = baseLoadBalancer.getLoadBalancerStats();
    assertNull(loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#lockAllServerList(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} MaxTotalPingTime is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#lockAllServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockAllServerList(boolean)"})
  public void testLockAllServerList_givenBaseLoadBalancerMaxTotalPingTimeIsThree() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Act
    baseLoadBalancer.lockAllServerList(false);

    // Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertEquals(1, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Test {@link BaseLoadBalancer#lockAllServerList(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Ping is {@link IPing}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#lockAllServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockAllServerList(boolean)"})
  public void testLockAllServerList_givenBaseLoadBalancerPingIsIPing() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPing(ping);
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Act
    Lock actualLockAllServerListResult = baseLoadBalancer.lockAllServerList(true);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ReadWriteLock readWriteLock = baseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(actualLockAllServerListResult instanceof WriteLock);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Test {@link BaseLoadBalancer#lockAllServerList(boolean)}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#allServerLock} ReadLockCount is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#lockAllServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockAllServerList(boolean)"})
  public void testLockAllServerList_thenBaseLoadBalancerAllServerLockReadLockCountIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.lockAllServerList(false);

    // Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertEquals(1, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Test {@link BaseLoadBalancer#lockAllServerList(boolean)}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#allServerLock} ReadLockCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#lockAllServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockAllServerList(boolean)"})
  public void testLockAllServerList_thenBaseLoadBalancerAllServerLockReadLockCountIsZero() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act and Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.lockAllServerList(true) instanceof WriteLock);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Test {@link BaseLoadBalancer#lockUpServerList(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServers array of {@link Object} with forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockUpServerList(boolean)"})
  public void testLockUpServerList_givenBaseLoadBalancerAddServersArrayOfObjectWithFortyTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{42});

    // Act
    baseLoadBalancer.lockUpServerList(false);

    // Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.upServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertEquals(1, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Test {@link BaseLoadBalancer#lockUpServerList(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServers array of {@link Object} with two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockUpServerList(boolean)"})
  public void testLockUpServerList_givenBaseLoadBalancerAddServersArrayOfObjectWithTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{2});

    // Act and Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.upServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.lockUpServerList(true) instanceof WriteLock);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Test {@link BaseLoadBalancer#lockUpServerList(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@link WriteLock}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockUpServerList(boolean)"})
  public void testLockUpServerList_givenBaseLoadBalancer_whenTrue_thenReturnWriteLock() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act and Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.upServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertTrue(baseLoadBalancer.lockUpServerList(true) instanceof WriteLock);
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertTrue(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Test {@link BaseLoadBalancer#lockUpServerList(boolean)}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerLock} ReadLockCount is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#lockUpServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Lock BaseLoadBalancer.lockUpServerList(boolean)"})
  public void testLockUpServerList_thenBaseLoadBalancerUpServerLockReadLockCountIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.lockUpServerList(false);

    // Assert
    ReadWriteLock readWriteLock = baseLoadBalancer.upServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    assertEquals(1, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
  }

  /**
   * Test {@link BaseLoadBalancer#setPingInterval(int)}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} PingInterval is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setPingInterval(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setPingInterval(int)"})
  public void testSetPingInterval_thenBaseLoadBalancerPingIntervalIsFortyTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setPingInterval(42);

    // Assert
    assertEquals(42, baseLoadBalancer.getPingInterval());
  }

  /**
   * Test {@link BaseLoadBalancer#setMaxTotalPingTime(int)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} PingInterval is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setMaxTotalPingTime(int)"})
  public void testSetMaxTotalPingTime_givenBaseLoadBalancerPingIntervalIsFortyTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setPingInterval(42);
    baseLoadBalancer.addServers(new ArrayList<>());

    // Act
    baseLoadBalancer.setMaxTotalPingTime(0);

    // Assert that nothing has changed
    assertEquals(5, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Test {@link BaseLoadBalancer#setMaxTotalPingTime(int)}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} MaxTotalPingTime is five.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setMaxTotalPingTime(int)"})
  public void testSetMaxTotalPingTime_thenBaseLoadBalancerMaxTotalPingTimeIsFive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setMaxTotalPingTime(0);

    // Assert that nothing has changed
    assertEquals(5, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Test {@link BaseLoadBalancer#setMaxTotalPingTime(int)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} MaxTotalPingTime is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setMaxTotalPingTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setMaxTotalPingTime(int)"})
  public void testSetMaxTotalPingTime_whenThree_thenBaseLoadBalancerMaxTotalPingTimeIsThree() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.setMaxTotalPingTime(3);

    // Assert
    assertEquals(3, baseLoadBalancer.getMaxTotalPingTime());
  }

  /**
   * Test {@link BaseLoadBalancer#isPingInProgress()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#isPingInProgress()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean BaseLoadBalancer.isPingInProgress()"})
  public void testIsPingInProgress() {
    // Arrange, Act and Assert
    assertFalse((new BaseLoadBalancer()).isPingInProgress());
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    IPing ping2 = mock(IPing.class);

    // Act
    baseLoadBalancer.setPing(ping2);

    // Assert
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(ping2, baseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("com.netflix.loadbalancer.IPing$MockitoMock$1813567985")).when(listener3)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener3);
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(false);

    // Act
    baseLoadBalancer.setPing(ping2);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping2).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3, atLeast(1)).serverStatusChanged(isA(Collection.class));
    verify(listener2, atLeast(1)).serverStatusChanged(isA(Collection.class));
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_givenBaseLoadBalancer_thenBaseLoadBalancerReachableServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    IPing ping = mock(IPing.class);

    // Act
    baseLoadBalancer.setPing(ping);

    // Assert
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
    assertSame(ping, baseLoadBalancer.getPing());
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_thenBaseLoadBalancerAllServersSizeIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    // Act
    baseLoadBalancer.setPing(ping);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
    List<Server> serverList = baseLoadBalancer.allServerList;
    assertEquals(serverList, baseLoadBalancer.getReachableServers());
    assertEquals(serverList, baseLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   * <ul>
   *   <li>Then calls {@link ServerStatusChangeListener#serverStatusChanged(Collection)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_thenCallsServerStatusChanged() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(false);

    // Act
    baseLoadBalancer.setPing(ping2);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping2).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2, atLeast(1)).serverStatusChanged(isA(Collection.class));
    assertTrue(baseLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#setPing(IPing)}.
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link BaseLoadBalancer#lbTimer} {@link ShutdownEnabledTimer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#setPing(IPing)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.setPing(IPing)"})
  public void testSetPing_thenDynamicServerListLoadBalancerLbTimerShutdownEnabledTimer() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));

    // Assert
    assertTrue(dynamicServerListLoadBalancer.lbTimer instanceof ShutdownEnabledTimer);
    List<Server> allServers = dynamicServerListLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
    List<Server> serverList = dynamicServerListLoadBalancer.allServerList;
    assertEquals(serverList, dynamicServerListLoadBalancer.getReachableServers());
    assertEquals(serverList, dynamicServerListLoadBalancer.upServerList);
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServers(new Object[]{","});

    // Act
    int actualServerCount = baseLoadBalancer.getServerCount(true);

    // Assert
    verify(ping, atLeast(1)).isAlive(Mockito.<Server>any());
    assertEquals(0, actualServerCount);
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_givenBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act and Assert
    assertEquals(0, baseLoadBalancer.getServerCount(true));
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)} with id is {@code 42}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_givenBaseLoadBalancerAddServerServerWithIdIs42_thenReturnOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act and Assert
    assertEquals(1, baseLoadBalancer.getServerCount(false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Rule is {@link AvailabilityFilteringRule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_givenBaseLoadBalancerRuleIsAvailabilityFilteringRule() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());

    // Act and Assert
    assertEquals(0, baseLoadBalancer.getServerCount(true));
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_givenBaseLoadBalancer_whenFalse() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act and Assert
    assertEquals(0, baseLoadBalancer.getServerCount(false));
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code false}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_givenBaseLoadBalancer_whenFalse_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new BaseLoadBalancer()).getServerCount(false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code true}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_givenBaseLoadBalancer_whenTrue_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new BaseLoadBalancer()).getServerCount(true));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerList} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_thenBaseLoadBalancerUpServerListSizeIsTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServers(new Object[]{","});

    // Act and Assert
    assertEquals(2, baseLoadBalancer.getServerCount(true));
    assertEquals(2, baseLoadBalancer.upServerList.size());
    List<Server> expectedReachableServers = baseLoadBalancer.upServerList;
    assertEquals(expectedReachableServers, baseLoadBalancer.getReachableServers());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerCount(boolean)}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerCount(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BaseLoadBalancer.getServerCount(boolean)"})
  public void testGetServerCount_thenReturnOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{","});

    // Act and Assert
    assertEquals(1, baseLoadBalancer.getServerCount(true));
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers is {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer_thenBaseLoadBalancerAllServersIsBaseLoadBalancerUpServerList() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServer(new Server("42"));

    // Assert
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServer(Server)}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers is {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#addServer(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.addServer(Server)"})
  public void testAddServer_thenBaseLoadBalancerAllServersIsBaseLoadBalancerUpServerList2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServer(new Server("42"));

    // Assert
    List<Server> expectedAllServers = baseLoadBalancer.upServerList;
    assertEquals(expectedAllServers, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_givenBaseLoadBalancer_whenArrayList_thenArrayListEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    ArrayList<Server> newServers = new ArrayList<>();

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert that nothing has changed
    assertTrue(newServers.isEmpty());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_givenBaseLoadBalancer_whenArrayList_thenArrayListEmpty2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    ArrayList<Server> newServers = new ArrayList<>();

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert that nothing has changed
    assertTrue(newServers.isEmpty());
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
    assertTrue(baseLoadBalancer.allServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_thenArrayListIsBaseLoadBalancerUpServerList() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(serverList, newServers);
    assertEquals(serverList, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_thenArrayListIsBaseLoadBalancerUpServerList2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(serverList, newServers);
    assertEquals(serverList, baseLoadBalancer.getAllServers());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_thenBaseLoadBalancerAllServersSizeIsTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));
    newServers.add(new Server("42"));

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    assertEquals(2, baseLoadBalancer.getAllServers().size());
    assertEquals(2, baseLoadBalancer.getReachableServers().size());
    assertEquals(2, baseLoadBalancer.allServerList.size());
    assertEquals(2, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(List)} with {@code List}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#addServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(List)"})
  public void testAddServersWithList_thenBaseLoadBalancerReachableServersSizeIsTwo() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));
    Server server = new Server("42");
    newServers.add(server);

    // Act
    baseLoadBalancer.addServers(newServers);

    // Assert
    List<Server> reachableServers = baseLoadBalancer.getReachableServers();
    assertEquals(2, reachableServers.size());
    List<Server> serverList = baseLoadBalancer.upServerList;
    assertEquals(2, serverList.size());
    assertSame(server, reachableServers.get(1));
    assertSame(server, serverList.get(1));
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject_thenBaseLoadBalancerReachableServersSizeIsOne() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#addServers(Object[])} with {@code Object[]}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#addServers(Object[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.addServers(Object[])"})
  public void testAddServersWithObject_thenBaseLoadBalancerReachableServersSizeIsOne2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Assert
    assertEquals(1, baseLoadBalancer.getReachableServers().size());
    assertEquals(1, baseLoadBalancer.allServerList.size());
    assertEquals(1, baseLoadBalancer.upServerList.size());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_givenBaseLoadBalancerAddServerServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.getServerByIndex(1, true));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_givenBaseLoadBalancer_whenFalse_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).getServerByIndex(1, false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_givenBaseLoadBalancer_whenFalse_thenReturnNull2() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).getServerByIndex(1, false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_givenBaseLoadBalancer_whenOne_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).getServerByIndex(1, true));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_givenBaseLoadBalancer_whenTrue_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).getServerByIndex(1, true));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_thenReturnServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.getServerByIndex(1, true));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   * <ul>
   *   <li>Then return {@link Server#Server(String, String, int)} with {@code Scheme} and host is {@code localhost} and port is {@code 8080}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_thenReturnServerWithSchemeAndHostIsLocalhostAndPortIs8080() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    Server newServer = new Server("Scheme", "localhost", 8080);

    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.getServerByIndex(1, true));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerByIndex(int, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerByIndex(int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server BaseLoadBalancer.getServerByIndex(int, boolean)"})
  public void testGetServerByIndex_whenFalse_thenReturnServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    Server newServer = new Server("42");
    baseLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, baseLoadBalancer.getServerByIndex(1, false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly() {
    // Arrange
    DummyPing ping = new DummyPing();

    // Act and Assert
    assertTrue((new BaseLoadBalancer(ping, new AvailabilityFilteringRule())).getServerList(true).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_givenBaseLoadBalancer_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(true).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_givenBaseLoadBalancer_whenFalse() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(false).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_givenBaseLoadBalancer_whenTrue() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(true).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   * <ul>
   *   <li>Then return {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_thenReturnBaseLoadBalancerUpServerList() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42UNKNOWN"));

    // Act and Assert
    assertEquals(baseLoadBalancer.upServerList, baseLoadBalancer.getServerList(false));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   * <ul>
   *   <li>Then return {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_thenReturnZoneAwareLoadBalancerUpServerList() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServers(new Object[]{"New Servers"});

    // Act and Assert
    assertEquals(zoneAwareLoadBalancer.upServerList, zoneAwareLoadBalancer.getServerList(true));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly_whenFalse_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(false).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setEnablePrimingConnections(true);

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(ServerGroup.STATUS_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{Integer.MIN_VALUE});
    baseLoadBalancer.addServers(new Object[]{});

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(ServerGroup.STATUS_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup3() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server(Server.UNKNOWN_ZONE));

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancerAddServerServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServers(new Object[]{"NFLoadBalancer-PingTimer-"});

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancerAddServerServerWithIdIs422() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancerAddServerServerWithIdIs423() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code ALL}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancer_whenAll_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(ServerGroup.ALL).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code ALL}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancer_whenAll_thenReturnEmpty2() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(ServerGroup.ALL).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code STATUS_NOT_UP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancer_whenStatusNotUp() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code STATUS_NOT_UP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancer_whenStatusNotUp2() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code STATUS_UP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancer_whenStatusUp() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(ServerGroup.STATUS_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code STATUS_UP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_givenBaseLoadBalancer_whenStatusUp2() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getServerList(ServerGroup.STATUS_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Then return {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_thenReturnBaseLoadBalancerUpServerList() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{"NFLoadBalancer-PingTimer-"});

    // Act and Assert
    assertSame(baseLoadBalancer.upServerList, baseLoadBalancer.getServerList(ServerGroup.STATUS_UP));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>Then return {@link BaseLoadBalancer#BaseLoadBalancer()} {@link BaseLoadBalancer#upServerList}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_thenReturnBaseLoadBalancerUpServerList2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{Integer.MIN_VALUE});
    baseLoadBalancer.addServers(new Object[]{"NFLoadBalancer-PingTimer-"});

    // Act and Assert
    assertSame(baseLoadBalancer.upServerList, baseLoadBalancer.getServerList(ServerGroup.STATUS_UP));
  }

  /**
   * Test {@link BaseLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   * <ul>
   *   <li>When {@code STATUS_NOT_UP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup_whenStatusNotUp() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{"NFLoadBalancer-PingTimer-"});

    // Act and Assert
    assertTrue(baseLoadBalancer.getServerList(ServerGroup.STATUS_NOT_UP).isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#cancelPingTask()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#cancelPingTask()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.cancelPingTask()"})
  public void testCancelPingTask() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException("foo"));

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server(Server.UNKNOWN_ZONE));

    // Act
    baseLoadBalancer.cancelPingTask();

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Test {@link BaseLoadBalancer#getReachableServers()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getReachableServers()"})
  public void testGetReachableServers() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertTrue((new BaseLoadBalancer(ping, new ClientConfigEnabledRoundRobinRule())).getReachableServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getReachableServers()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getReachableServers()"})
  public void testGetReachableServers2() {
    // Arrange
    NoOpPing ping = new NoOpPing();

    // Act and Assert
    assertTrue((new BaseLoadBalancer(ping, new ClientConfigEnabledRoundRobinRule())).getReachableServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getReachableServers()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getReachableServers()"})
  public void testGetReachableServers3() {
    // Arrange
    PingFake ping = mock(PingFake.class);

    // Act and Assert
    assertTrue((new BaseLoadBalancer(ping, new ClientConfigEnabledRoundRobinRule())).getReachableServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getReachableServers()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getReachableServers()"})
  public void testGetReachableServers4() {
    // Arrange
    NoOpPing ping = new NoOpPing();

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new ClientConfigEnabledRoundRobinRule());
    baseLoadBalancer.addServers(new Object[]{1});

    // Act and Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getReachableServers()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getReachableServers()"})
  public void testGetReachableServers5() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new ClientConfigEnabledRoundRobinRule());
    baseLoadBalancer.setPrimeConnections(new PrimeConnections("Name", 3, 1L, "Prime Connections URI"));
    baseLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act and Assert
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getReachableServers()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getReachableServers()"})
  public void testGetReachableServers6() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getReachableServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getReachableServers()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getReachableServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getReachableServers()"})
  public void testGetReachableServers_givenBaseLoadBalancer() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getReachableServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getAllServers()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getAllServers()"})
  public void testGetAllServers() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getAllServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getAllServers()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getAllServers()"})
  public void testGetAllServers2() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server(""));
    lbStats.updateZoneServerMapping(new HashMap<>());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(lbStats);

    // Act and Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getAllServers()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getAllServers()"})
  public void testGetAllServers3() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.incrementZoneCounter(new Server("com.netflix.loadbalancer.Server"));
    lbStats.updateZoneServerMapping(new HashMap<>());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(lbStats);

    // Act and Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getAllServers()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getAllServers()"})
  public void testGetAllServers_givenBaseLoadBalancer() {
    // Arrange, Act and Assert
    assertTrue((new BaseLoadBalancer()).getAllServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getAllServers()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServers array of {@link Object} with one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getAllServers()"})
  public void testGetAllServers_givenBaseLoadBalancerAddServersArrayOfObjectWithOne() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(new HashMap<>());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{1});
    baseLoadBalancer.setLoadBalancerStats(lbStats);

    // Act and Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getAllServers()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} LoadBalancerStats is {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getAllServers()"})
  public void testGetAllServers_givenBaseLoadBalancerLoadBalancerStatsIsLoadBalancerStats() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(new LoadBalancerStats());

    // Act and Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getAllServers()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} LoadBalancerStats is {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getAllServers()"})
  public void testGetAllServers_givenBaseLoadBalancerLoadBalancerStatsIsLoadBalancerStats2() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(new HashMap<>());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setLoadBalancerStats(lbStats);

    // Act and Assert
    assertTrue(baseLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#getAllServers()}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} LoadBalancerStats is {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#getAllServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List BaseLoadBalancer.getAllServers()"})
  public void testGetAllServers_givenZoneAwareLoadBalancerLoadBalancerStatsIsLoadBalancerStats() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    lbStats.updateZoneServerMapping(new HashMap<>());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setLoadBalancerStats(lbStats);

    // Act and Assert
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#chooseServer(Object)} with {@code Object}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server BaseLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject_givenBaseLoadBalancer_whenKey_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).chooseServer("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#chooseServer(Object)} with {@code Object}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server BaseLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject_thenBaseLoadBalancerReachableServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act and Assert
    assertNull(baseLoadBalancer.chooseServer("Key"));
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)} with id is {@code 42}.</li>
   *   <li>Then return {@code 42:80}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose_givenBaseLoadBalancerAddServerServerWithIdIs42_thenReturn4280() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertEquals("42:80", baseLoadBalancer.choose("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)} with id is {@code 42}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code 42:80}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose_givenBaseLoadBalancerAddServerServerWithIdIs42_whenKey_thenReturn4280() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act and Assert
    assertEquals("42:80", baseLoadBalancer.choose("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Rule is {@link AvailabilityFilteringRule} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose_givenBaseLoadBalancerRuleIsAvailabilityFilteringRule_thenReturnNull() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());

    // Act and Assert
    assertNull(baseLoadBalancer.choose("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Rule is {@link AvailabilityFilteringRule} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose_givenBaseLoadBalancerRuleIsAvailabilityFilteringRule_thenReturnNull2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setRule(new AvailabilityFilteringRule());

    // Act and Assert
    assertNull(baseLoadBalancer.choose("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose_givenBaseLoadBalancer_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).choose("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#choose(Object)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String BaseLoadBalancer.choose(Object)"})
  public void testChoose_givenBaseLoadBalancer_whenKey_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BaseLoadBalancer()).choose("Key"));
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServers(new Object[]{"New Servers"});

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert that nothing has changed
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   * <ul>
   *   <li>Then calls {@link ServerStatusChangeListener#serverStatusChanged(Collection)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_thenCallsServerStatusChanged() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   * <ul>
   *   <li>Then calls {@link ServerStatusChangeListener#serverStatusChanged(Collection)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_thenCallsServerStatusChanged2() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("http://")).when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then not {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_when42_thenNotBaseLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42");

    // Assert
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   * <ul>
   *   <li>When {@code 42:80}.</li>
   *   <li>Then calls {@link ServerStatusChangeListener#serverStatusChanged(Collection)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_when4280_thenCallsServerStatusChanged() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("http://")).when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("42:80");

    // Assert
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertFalse(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   * <ul>
   *   <li>When {@code http://}.</li>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_whenHttp_thenBaseLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("http://");

    // Assert that nothing has changed
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   * <ul>
   *   <li>When {@code http://}.</li>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_whenHttp_thenBaseLoadBalancerAllServersFirstAlive2() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("http://");

    // Assert that nothing has changed
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   * <ul>
   *   <li>When {@code https://}.</li>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_whenHttps_thenBaseLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("https://");

    // Assert that nothing has changed
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_whenNull_thenBaseLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown((String) null);

    // Assert that nothing has changed
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(String)} with {@code id}.
   * <ul>
   *   <li>When {@code /}.</li>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers first Alive.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(String)"})
  public void testMarkServerDownWithId_whenSlash_thenBaseLoadBalancerAllServersFirstAlive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.markServerDown("/");

    // Assert that nothing has changed
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertTrue(allServers.get(0).isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  markServerDown called on [{}]")).when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException("foofoofoo")).when(listener3)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener3);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(listener3).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} EnablePrimingConnections is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer_givenBaseLoadBalancerEnablePrimingConnectionsIsTrue() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  markServerDown called on [{}]")).when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException("foofoofoo")).when(listener3)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.addServerStatusChangeListener(listener3);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(listener3).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then not {@link Server#Server(String)} with id is {@code 42} Alive.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer_givenBaseLoadBalancer_thenNotServerWithIdIs42Alive() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    assertFalse(server.isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>When {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer_givenBaseLoadBalancer_whenServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    Server server = new Server("42");

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert that nothing has changed
    assertFalse(server.isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} Schemea is {@code Scheme}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer_givenServerWithIdIs42SchemeaIsScheme() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  markServerDown called on [{}]")).when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    Server newServer = new Server("42");
    newServer.setSchemea("Scheme");
    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener3).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener3);
    baseLoadBalancer.addServer(newServer);
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(listener3).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   * <ul>
   *   <li>Then not {@link Server#Server(String)} with id is {@code 42} Alive.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer_thenNotServerWithIdIs42Alive() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener);

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(listener).serverStatusChanged(isA(Collection.class));
    assertFalse(server.isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   * <ul>
   *   <li>Then not {@link Server#Server(String)} with id is {@code 42} Alive.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer_thenNotServerWithIdIs42Alive2() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  markServerDown called on [{}]")).when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);

    Server server = new Server("42");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    assertFalse(server.isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#markServerDown(Server)} with {@code server}.
   * <ul>
   *   <li>Then not {@link Server#Server(String)} with id is {@code 42/} Alive.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#markServerDown(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.markServerDown(Server)"})
  public void testMarkServerDownWithServer_thenNotServerWithIdIs42Alive3() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new RuntimeException("LoadBalancer [{}]:  markServerDown called on [{}]")).when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());
    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException("LoadBalancer [{}]:  markServerDown called on [{}]")).when(listener3)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServerStatusChangeListener(listener3);
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.addServerStatusChangeListener(listener2);
    baseLoadBalancer.addServerStatusChangeListener(listener);

    Server server = new Server("42/");
    server.setAlive(true);

    // Act
    baseLoadBalancer.markServerDown(server);

    // Assert
    verify(listener3).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    assertFalse(server.isAlive());
  }

  /**
   * Test {@link BaseLoadBalancer#forceQuickPing()}.
   * <p>
   * Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.forceQuickPing()"})
  public void testForceQuickPing() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any()))
        .thenThrow(new RuntimeException("com.netflix.loadbalancer.IPing$MockitoMock$1813567985"));
    baseLoadBalancer.setPing(ping2);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert
    verify(ping2, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
  }

  /**
   * Test {@link BaseLoadBalancer#forceQuickPing()}.
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code false}.</li>
   *   <li>Then calls {@link IPing#isAlive(Server)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.forceQuickPing()"})
  public void testForceQuickPing_givenIPingIsAliveReturnFalse_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(false);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Test {@link BaseLoadBalancer#forceQuickPing()}.
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code true}.</li>
   *   <li>Then calls {@link IPing#isAlive(Server)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.forceQuickPing()"})
  public void testForceQuickPing_givenIPingIsAliveReturnTrue_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
  }

  /**
   * Test {@link BaseLoadBalancer#forceQuickPing()}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} ReachableServers Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.forceQuickPing()"})
  public void testForceQuickPing_thenBaseLoadBalancerReachableServersEmpty() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert that nothing has changed
    assertTrue(baseLoadBalancer.getReachableServers().isEmpty());
    assertTrue(baseLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link BaseLoadBalancer#forceQuickPing()}.
   * <ul>
   *   <li>Then calls {@link ServerListChangeListener#serverListChanged(List, List)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#forceQuickPing()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.forceQuickPing()"})
  public void testForceQuickPing_thenCallsServerListChanged() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("foo")).when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    baseLoadBalancer.addServerListChangeListener(listener);
    baseLoadBalancer.addServer(new Server("42"));

    // Act
    baseLoadBalancer.forceQuickPing();

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
  }

  /**
   * Test {@link BaseLoadBalancer#init()}.
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} PrimeConnections EndStats {@link PrimeConnections.PrimeConnectionEndStats#failure} is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseLoadBalancer#init()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BaseLoadBalancer.init()"})
  public void testInit_thenBaseLoadBalancerPrimeConnectionsEndStatsFailureIsZero() {
    // Arrange
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    baseLoadBalancer.addServer(new Server("42"));
    baseLoadBalancer.setEnablePrimingConnections(true);
    baseLoadBalancer.setPrimeConnections(new PrimeConnections("Name", 3, 1L, "Prime Connections URI"));

    // Act
    baseLoadBalancer.init();

    // Assert
    PrimeConnectionEndStats endStats = baseLoadBalancer.getPrimeConnections().getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    List<Server> allServers = baseLoadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(1, endStats.total);
    assertEquals(1L, endStats.totalTime);
    assertFalse(allServers.get(0).isReadyToServe());
  }
}
