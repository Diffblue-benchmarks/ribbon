package com.netflix.ribbon.examples.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.Server;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class URLConnectionLoadBalancerDiffblueTest {
  /**
   * Method under test: {@link URLConnectionLoadBalancer#getLoadBalancerStats()}
   */
  @Test
  public void testGetLoadBalancerStats() {
    // Arrange and Act
    LoadBalancerStats actualLoadBalancerStats = (new URLConnectionLoadBalancer(new ArrayList<>()))
        .getLoadBalancerStats();

    // Assert
    assertEquals("", actualLoadBalancerStats.getName());
    assertEquals(0, actualLoadBalancerStats.getCircuitBreakerTrippedCount());
    assertTrue(actualLoadBalancerStats.getServerStats().isEmpty());
    assertTrue(actualLoadBalancerStats.getZoneStats().isEmpty());
    assertTrue(actualLoadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Method under test:
   * {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}
   */
  @Test
  public void testNewURLConnectionLoadBalancer() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();

    // Act and Assert
    LoadBalancerStats loadBalancerStats = (new URLConnectionLoadBalancer(serverList)).getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertTrue(serverList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Method under test:
   * {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}
   */
  @Test
  public void testNewURLConnectionLoadBalancer2() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    Server server = new Server("42");
    serverList.add(server);

    // Act
    URLConnectionLoadBalancer actualUrlConnectionLoadBalancer = new URLConnectionLoadBalancer(serverList);

    // Assert
    assertEquals(1, serverList.size());
    LoadBalancerStats loadBalancerStats = actualUrlConnectionLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    Server getResult = serverList.get(0);
    assertTrue(getResult.isAlive());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(server, getResult);
  }

  /**
   * Method under test:
   * {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}
   */
  @Test
  public void testNewURLConnectionLoadBalancer3() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    Server server = new Server("42");
    serverList.add(server);
    Server server2 = new Server("42");
    serverList.add(server2);

    // Act
    URLConnectionLoadBalancer actualUrlConnectionLoadBalancer = new URLConnectionLoadBalancer(serverList);

    // Assert
    assertEquals(2, serverList.size());
    LoadBalancerStats loadBalancerStats = actualUrlConnectionLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    Server getResult = serverList.get(0);
    assertTrue(getResult.isAlive());
    Server getResult2 = serverList.get(1);
    assertTrue(getResult2.isAlive());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(server, getResult);
    assertSame(server2, getResult2);
  }

  /**
   * Method under test:
   * {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}
   */
  @Test
  public void testNewURLConnectionLoadBalancer4() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(null);

    // Act
    URLConnectionLoadBalancer actualUrlConnectionLoadBalancer = new URLConnectionLoadBalancer(serverList);

    // Assert
    assertEquals(1, serverList.size());
    LoadBalancerStats loadBalancerStats = actualUrlConnectionLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertNull(serverList.get(0));
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }
}
