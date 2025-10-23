package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractServerPredicateDiffblueTest {
  /**
   * Test {@link AbstractServerPredicate#alwaysTrue()}.
   *
   * <p>Method under test: {@link AbstractServerPredicate#alwaysTrue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractServerPredicate AbstractServerPredicate.alwaysTrue()"})
  public void testAlwaysTrue() {
    // Arrange and Act
    AbstractServerPredicate actualAlwaysTrueResult = AbstractServerPredicate.alwaysTrue();

    // Assert
    assertNull(actualAlwaysTrueResult.rule);
    assertNull(actualAlwaysTrueResult.getLBStats());
  }

  /**
   * Test {@link AbstractServerPredicate#getLBStats()}.
   *
   * <ul>
   *   <li>Given {@link CompositePredicate} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getLBStats()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LoadBalancerStats AbstractServerPredicate.getLBStats()"})
  public void testGetLBStats_givenCompositePredicate_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new CompositePredicate().getLBStats());
  }

  /**
   * Test {@link AbstractServerPredicate#getLBStats()}.
   *
   * <ul>
   *   <li>Then return {@link LoadBalancerStats#LoadBalancerStats()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getLBStats()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LoadBalancerStats AbstractServerPredicate.getLBStats()"})
  public void testGetLBStats_thenReturnLoadBalancerStats() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();
    LoadBalancerStats stats = new LoadBalancerStats();
    compositePredicate.setLoadBalancerStats(stats);

    // Act and Assert
    assertSame(stats, compositePredicate.getLBStats());
  }

  /**
   * Test {@link AbstractServerPredicate#setLoadBalancerStats(LoadBalancerStats)}.
   *
   * <p>Method under test: {@link AbstractServerPredicate#setLoadBalancerStats(LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractServerPredicate.setLoadBalancerStats(LoadBalancerStats)"})
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
   * Test {@link AbstractServerPredicate#getServerOnlyPredicate()}.
   *
   * <ul>
   *   <li>Then {@link CompositePredicate} (default constructor) {@link
   *       AbstractServerPredicate#rule} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getServerOnlyPredicate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Predicate AbstractServerPredicate.getServerOnlyPredicate()"})
  public void testGetServerOnlyPredicate_thenCompositePredicateRuleIsNull() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();

    // Act
    compositePredicate.getServerOnlyPredicate();

    // Assert that nothing has changed
    assertNull(compositePredicate.rule);
    assertNull(compositePredicate.getLBStats());
  }

  /**
   * Test {@link AbstractServerPredicate#getServerOnlyPredicate()}.
   *
   * <ul>
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Host is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getServerOnlyPredicate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Predicate AbstractServerPredicate.getServerOnlyPredicate()"})
  public void testGetServerOnlyPredicate_thenServerWithIdIs42HostIs42() {
    // Arrange
    CompositePredicate primaryPredicate = CompositePredicate.withPredicates().build();
    CompositePredicate compositePredicate =
        CompositePredicate.withPredicate(primaryPredicate).build();

    // Act
    Predicate<Server> actualServerOnlyPredicate = compositePredicate.getServerOnlyPredicate();
    Server server = new Server("42");
    boolean actualApplyResult = actualServerOnlyPredicate.apply(server);

    // Assert
    assertEquals("42", server.getHost());
    assertEquals("42:80", server.getHostPort());
    assertEquals("42:80", server.getId());
    assertNull(compositePredicate.rule);
    assertNull(compositePredicate.getLBStats());
    assertNull(server.getScheme());
    assertEquals(80, server.getPort());
    assertFalse(server.isAlive());
    assertTrue(actualApplyResult);
    assertTrue(server.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, server.getZone());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers},
   * {@code loadBalancerKey}.
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(servers, "Load Balancer Key").isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers},
   * {@code loadBalancerKey}.
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey2() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(servers, "Load Balancer Key").isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers},
   * {@code loadBalancerKey}.
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey3() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(servers, null).isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers},
   * {@code loadBalancerKey}.
   *
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnArrayList() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    List<Server> actualEligibleServers =
        zoneAffinityPredicate.getEligibleServers(servers, "Load Balancer Key");

    // Assert
    assertEquals(servers, actualEligibleServers);
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers},
   * {@code loadBalancerKey}.
   *
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnArrayList2() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    List<Server> actualEligibleServers = zoneAffinityPredicate.getEligibleServers(servers, null);

    // Assert
    assertEquals(servers, actualEligibleServers);
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers},
   * {@code loadBalancerKey}.
   *
   * <ul>
   *   <li>Then return size is five.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnSizeIsFive() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    Server server = new Server("42");
    servers.add(server);

    // Act
    List<Server> actualEligibleServers = zoneAffinityPredicate.getEligibleServers(servers, null);

    // Assert
    assertEquals(5, actualEligibleServers.size());
    assertSame(server, actualEligibleServers.get(4));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers},
   * {@code loadBalancerKey}.
   *
   * <ul>
   *   <li>Then return size is four.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnSizeIsFour() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    Server server = new Server("42");
    servers.add(server);
    Server server2 = new Server("42");
    servers.add(server2);

    // Act
    List<Server> actualEligibleServers = zoneAffinityPredicate.getEligibleServers(servers, null);

    // Assert
    assertEquals(4, actualEligibleServers.size());
    assertSame(server, actualEligibleServers.get(2));
    assertSame(server2, actualEligibleServers.get(3));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers},
   * {@code loadBalancerKey}.
   *
   * <ul>
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnSizeIsTwo() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    Server server = new Server("42");
    servers.add(server);

    // Act
    List<Server> actualEligibleServers = zoneAffinityPredicate.getEligibleServers(servers, null);

    // Assert
    assertEquals(2, actualEligibleServers.size());
    assertSame(server, actualEligibleServers.get(1));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers},
   * {@code loadBalancerKey}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_whenArrayList() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    // Act and Assert
    assertTrue(
        zoneAffinityPredicate.getEligibleServers(new ArrayList<>(), "Load Balancer Key").isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers},
   * {@code loadBalancerKey}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_whenArrayList2() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();

    // Act and Assert
    assertTrue(compositePredicate.getEligibleServers(new ArrayList<>(), null).isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   *
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers_thenReturnArrayList() {
    // Arrange
    CompositePredicate primaryPredicate = CompositePredicate.withPredicates().build();
    CompositePredicate compositePredicate =
        CompositePredicate.withPredicate(primaryPredicate).build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    List<Server> actualEligibleServers = compositePredicate.getEligibleServers(servers);

    // Assert
    assertEquals(servers, actualEligibleServers);
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers_whenArrayList_thenReturnEmpty() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();

    // Act and Assert
    assertTrue(compositePredicate.getEligibleServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#ofKeyPredicate(Predicate)}.
   *
   * <p>Method under test: {@link AbstractServerPredicate#ofKeyPredicate(Predicate)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractServerPredicate AbstractServerPredicate.ofKeyPredicate(Predicate)"})
  public void testOfKeyPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualOfKeyPredicateResult =
        AbstractServerPredicate.ofKeyPredicate(
            new AvailabilityPredicate(new AvailabilityFilteringRule()));

    // Assert
    assertNull(actualOfKeyPredicateResult.rule);
    assertNull(actualOfKeyPredicateResult.getLBStats());
  }

  /**
   * Test {@link AbstractServerPredicate#ofServerPredicate(Predicate)}.
   *
   * <p>Method under test: {@link AbstractServerPredicate#ofServerPredicate(Predicate)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractServerPredicate AbstractServerPredicate.ofServerPredicate(Predicate)"
  })
  public void testOfServerPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualOfServerPredicateResult =
        AbstractServerPredicate.ofServerPredicate(null);

    // Assert
    assertNull(actualOfServerPredicateResult.rule);
    assertNull(actualOfServerPredicateResult.getLBStats());
  }
}
