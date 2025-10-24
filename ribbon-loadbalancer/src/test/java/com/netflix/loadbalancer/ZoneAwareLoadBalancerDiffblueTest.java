package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Test;
import org.junit.experimental.categories.Category;

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
}
