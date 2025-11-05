package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class AbstractServerPredicateDiffblueTest {
  /**
   * Method under test: {@link AbstractServerPredicate#alwaysTrue()}
   */
  @Test
  public void testAlwaysTrue() {
    // Arrange and Act
    AbstractServerPredicate actualAlwaysTrueResult = AbstractServerPredicate.alwaysTrue();

    // Assert
    assertNull(actualAlwaysTrueResult.rule);
    assertNull(actualAlwaysTrueResult.getLBStats());
  }

  /**
   * Method under test: {@link AbstractServerPredicate#getLBStats()}
   */
  @Test
  public void testGetLBStats() {
    // Arrange, Act and Assert
    assertNull((new CompositePredicate()).getLBStats());
  }

  /**
   * Method under test: {@link AbstractServerPredicate#getLBStats()}
   */
  @Test
  public void testGetLBStats2() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();
    LoadBalancerStats stats = new LoadBalancerStats();
    compositePredicate.setLoadBalancerStats(stats);

    // Act and Assert
    assertSame(stats, compositePredicate.getLBStats());
  }

  /**
   * Method under test: {@link AbstractServerPredicate#getLBStats()}
   */
  @Test
  public void testGetLBStats3() {
    // Arrange
    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.updateZoneServerMapping(map);

    CompositePredicate compositePredicate = new CompositePredicate();
    compositePredicate.setLoadBalancerStats(stats);

    // Act and Assert
    assertSame(stats, compositePredicate.getLBStats());
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#setLoadBalancerStats(LoadBalancerStats)}
   */
  @Test
  public void testSetLoadBalancerStats() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();
    LoadBalancerStats stats = new LoadBalancerStats();

    // Act
    compositePredicate.setLoadBalancerStats(stats);

    // Assert
    assertSame(stats, compositePredicate.getLBStats());
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#setLoadBalancerStats(LoadBalancerStats)}
   */
  @Test
  public void testSetLoadBalancerStats2() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();

    HashMap<String, List<Server>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    LoadBalancerStats stats = new LoadBalancerStats();
    stats.updateZoneServerMapping(map);

    // Act
    compositePredicate.setLoadBalancerStats(stats);

    // Assert
    assertSame(stats, compositePredicate.getLBStats());
  }

  /**
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  public void testGetEligibleServers() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();

    // Act and Assert
    assertTrue(compositePredicate.getEligibleServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers2() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(new ArrayList<>(), "Load Balancer Key").isEmpty());
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers3() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(servers, "Load Balancer Key").isEmpty());
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers4() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(servers, "Load Balancer Key").isEmpty());
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers5() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(new ArrayList<>(), null).isEmpty());
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers6() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, zoneAffinityPredicate.getEligibleServers(servers, "Load Balancer Key"));
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers7() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(servers, null).isEmpty());
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers8() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, zoneAffinityPredicate.getEligibleServers(servers, null));
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers9() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, zoneAffinityPredicate.getEligibleServers(servers, null));
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers10() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, zoneAffinityPredicate.getEligibleServers(servers, null));
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers11() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, zoneAffinityPredicate.getEligibleServers(servers, null));
  }

  /**
   * Method under test: {@link AbstractServerPredicate#ofKeyPredicate(Predicate)}
   */
  @Test
  public void testOfKeyPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualOfKeyPredicateResult = AbstractServerPredicate
        .ofKeyPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()));

    // Assert
    assertNull(actualOfKeyPredicateResult.rule);
    assertNull(actualOfKeyPredicateResult.getLBStats());
  }

  /**
   * Method under test:
   * {@link AbstractServerPredicate#ofServerPredicate(Predicate)}
   */
  @Test
  public void testOfServerPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualOfServerPredicateResult = AbstractServerPredicate.ofServerPredicate(null);

    // Assert
    assertNull(actualOfServerPredicateResult.rule);
    assertNull(actualOfServerPredicateResult.getLBStats());
  }
}
