package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Test;

public class ZoneAwareLoadBalancerDiffblueTest {
  /**
   * Method under test: {@link ZoneAwareLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Act and Assert
    assertNull(zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act and Assert
    assertNull(zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer3() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    Server newServer = new Server("42");
    zoneAwareLoadBalancer.addServer(newServer);

    // Act and Assert
    assertSame(newServer, zoneAwareLoadBalancer.chooseServer("Key"));
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  public void testGetLoadBalancer() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    IRule rule = actualLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    LoadBalancerStats loadBalancerStats = actualLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertEquals("default_zone", actualLoadBalancer.getName());
    assertNull(actualLoadBalancer.getPrimeConnections());
    assertNull(actualLoadBalancer.getClientConfig());
    assertNull(actualLoadBalancer.getPing());
    assertNull(actualLoadBalancer.lbTimer);
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualLoadBalancer.isPingInProgress());
    assertFalse(actualLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualLoadBalancer.allServerList.isEmpty());
    assertTrue(actualLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#getLoadBalancer(String)}
   */
  @Test
  public void testGetLoadBalancer2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    BaseLoadBalancer actualLoadBalancer = zoneAwareLoadBalancer.getLoadBalancer("Zone");

    // Assert
    IRule rule = actualLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    LoadBalancerStats loadBalancerStats = actualLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertEquals("default_zone", actualLoadBalancer.getName());
    assertNull(actualLoadBalancer.getPrimeConnections());
    assertNull(actualLoadBalancer.getClientConfig());
    assertNull(actualLoadBalancer.getPing());
    assertNull(actualLoadBalancer.lbTimer);
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualLoadBalancer.isPingInProgress());
    assertFalse(actualLoadBalancer.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(actualLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualLoadBalancer.allServerList.isEmpty());
    assertTrue(actualLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  public void testSetRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    assertTrue(zoneAwareLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals(0, rule.getAvailableServersCount());
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(zoneAwareLoadBalancer.upServerList.isEmpty());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(zoneAwareLoadBalancer, rule.getLoadBalancer());
    assertSame(zoneAwareLoadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  public void testSetRule2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(new HashMap<>());

    // Act
    zoneAwareLoadBalancer.setRule(null);

    // Assert
    assertTrue(zoneAwareLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(zoneAwareLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(zoneAwareLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  public void testSetRule3() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    assertTrue(zoneAwareLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals(0, rule.getAvailableServersCount());
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(zoneAwareLoadBalancer.upServerList.isEmpty());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(zoneAwareLoadBalancer, rule.getLoadBalancer());
    assertSame(zoneAwareLoadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  public void testSetRule4() {
    // Arrange
    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("foo", new ArrayList<>());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Act
    zoneAwareLoadBalancer.setRule(null);

    // Assert
    assertTrue(zoneAwareLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(zoneAwareLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(zoneAwareLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  public void testSetRule5() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(new HashMap<>());
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    assertTrue(zoneAwareLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(zoneAwareLoadBalancer.upServerList.isEmpty());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(zoneAwareLoadBalancer, rule.getLoadBalancer());
    assertSame(zoneAwareLoadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  public void testSetRule6() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(new HashMap<>());
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    assertTrue(zoneAwareLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default", rule.name);
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(zoneAwareLoadBalancer.upServerList.isEmpty());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(zoneAwareLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  public void testSetRule7() {
    // Arrange
    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("foo", new ArrayList<>());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    assertTrue(zoneAwareLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals(0, rule.getAvailableServersCount());
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(zoneAwareLoadBalancer.upServerList.isEmpty());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(zoneAwareLoadBalancer, rule.getLoadBalancer());
    assertSame(zoneAwareLoadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  public void testSetRule8() {
    // Arrange
    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("foo", new ArrayList<>());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);
    BestAvailableRule rule = new BestAvailableRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    assertTrue(zoneAwareLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(zoneAwareLoadBalancer.upServerList.isEmpty());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(zoneAwareLoadBalancer, rule.getLoadBalancer());
    assertSame(zoneAwareLoadBalancer, rule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#setRule(IRule)}
   */
  @Test
  public void testSetRule9() {
    // Arrange
    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("foo", new ArrayList<>());

    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);
    RandomRule rule = new RandomRule();

    // Act
    zoneAwareLoadBalancer.setRule(rule);

    // Assert
    assertTrue(zoneAwareLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertTrue(zoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(zoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(zoneAwareLoadBalancer.upServerList.isEmpty());
    assertSame(rule, zoneAwareLoadBalancer.getRule());
    assertSame(zoneAwareLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}
   */
  @Test
  public void testNewZoneAwareLoadBalancer() {
    // Arrange and Act
    ZoneAwareLoadBalancer<Server> actualZoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();

    // Assert
    IRule rule = actualZoneAwareLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualZoneAwareLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualZoneAwareLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualZoneAwareLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualZoneAwareLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualZoneAwareLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualZoneAwareLoadBalancer.getPrimeConnections());
    assertNull(actualZoneAwareLoadBalancer.getClientConfig());
    assertNull(actualZoneAwareLoadBalancer.getPing());
    assertNull(actualZoneAwareLoadBalancer.getServerListImpl());
    assertNull(actualZoneAwareLoadBalancer.getFilter());
    assertNull(actualZoneAwareLoadBalancer.getServerListUpdater());
    assertNull(actualZoneAwareLoadBalancer.lbTimer);
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualZoneAwareLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualZoneAwareLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualZoneAwareLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualZoneAwareLoadBalancer.isPingInProgress());
    assertFalse(actualZoneAwareLoadBalancer.pingInProgress.get());
    assertFalse(actualZoneAwareLoadBalancer.serverListUpdateInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertFalse(actualZoneAwareLoadBalancer.isSecure);
    assertFalse(actualZoneAwareLoadBalancer.useTunnel);
    assertTrue(actualZoneAwareLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualZoneAwareLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualZoneAwareLoadBalancer.allServerList.isEmpty());
    assertTrue(actualZoneAwareLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualZoneAwareLoadBalancer, rule.getLoadBalancer());
  }
}
