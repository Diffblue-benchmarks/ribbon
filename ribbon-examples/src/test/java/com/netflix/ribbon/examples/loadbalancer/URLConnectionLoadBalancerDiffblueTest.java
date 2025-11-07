package com.netflix.ribbon.examples.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.Server;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class URLConnectionLoadBalancerDiffblueTest {
  /**
   * Test {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void URLConnectionLoadBalancer.<init>(List)"})
  public void testNewURLConnectionLoadBalancer_givenNull_whenArrayListAddNull() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(null);

    // Act and Assert
    LoadBalancerStats loadBalancerStats = (new URLConnectionLoadBalancer(serverList)).getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}.
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void URLConnectionLoadBalancer.<init>(List)"})
  public void testNewURLConnectionLoadBalancer_givenServerWithIdIs42_thenArrayListSizeIsOne() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));

    // Act
    URLConnectionLoadBalancer actualUrlConnectionLoadBalancer = new URLConnectionLoadBalancer(serverList);

    // Assert
    assertEquals(1, serverList.size());
    LoadBalancerStats loadBalancerStats = actualUrlConnectionLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertTrue(serverList.get(0).isAlive());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}.
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void URLConnectionLoadBalancer.<init>(List)"})
  public void testNewURLConnectionLoadBalancer_givenServerWithIdIs42_thenArrayListSizeIsTwo() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));

    // Act
    URLConnectionLoadBalancer actualUrlConnectionLoadBalancer = new URLConnectionLoadBalancer(serverList);

    // Assert
    assertEquals(2, serverList.size());
    LoadBalancerStats loadBalancerStats = actualUrlConnectionLoadBalancer.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertTrue(serverList.get(0).isAlive());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link URLConnectionLoadBalancer#URLConnectionLoadBalancer(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void URLConnectionLoadBalancer.<init>(List)"})
  public void testNewURLConnectionLoadBalancer_whenArrayList() {
    // Arrange, Act and Assert
    LoadBalancerStats loadBalancerStats = (new URLConnectionLoadBalancer(new ArrayList<>())).getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link URLConnectionLoadBalancer#getLoadBalancerStats()}.
   * <p>
   * Method under test: {@link URLConnectionLoadBalancer#getLoadBalancerStats()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancerStats URLConnectionLoadBalancer.getLoadBalancerStats()"})
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
}
