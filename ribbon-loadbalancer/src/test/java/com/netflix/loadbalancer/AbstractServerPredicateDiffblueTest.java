package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractServerPredicateDiffblueTest {
  /**
   * Test {@link AbstractServerPredicate#alwaysTrue()}.
   * <p>
   * Method under test: {@link AbstractServerPredicate#alwaysTrue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Given {@link CompositePredicate} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getLBStats()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancerStats AbstractServerPredicate.getLBStats()"})
  public void testGetLBStats_givenCompositePredicate_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new CompositePredicate()).getLBStats());
  }

  /**
   * Test {@link AbstractServerPredicate#getLBStats()}.
   * <ul>
   *   <li>Then return {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getLBStats()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <p>
   * Method under test: {@link AbstractServerPredicate#setLoadBalancerStats(LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate.withPredicate(new AvailabilityPredicate(null)).build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, buildResult.getEligibleServers(servers));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers2() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate.withPredicate(new ZoneAffinityPredicate("Zone")).build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(buildResult.getEligibleServers(servers).isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers3() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicates(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, buildResult.getEligibleServers(servers));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnArrayList() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, zoneAffinityPredicate.getEligibleServers(servers, "Load Balancer Key"));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnArrayList2() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, zoneAffinityPredicate.getEligibleServers(servers, null));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnEmpty() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(servers, "Load Balancer Key").isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnEmpty2() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(servers, "Load Balancer Key").isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnEmpty3() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(servers, null).isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return size is five.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return size is four.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_whenArrayList_thenReturnEmpty() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(new ArrayList<>(), "Load Balancer Key").isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_whenArrayList_thenReturnEmpty2() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    // Act and Assert
    assertTrue(zoneAffinityPredicate.getEligibleServers(new ArrayList<>(), null).isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   * <ul>
   *   <li>Given {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)} with {@code Zone}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers_givenZoneAffinityPredicateWithZone() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate.withPredicates(new ZoneAffinityPredicate("Zone")).build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(buildResult.getEligibleServers(servers).isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers_thenReturnArrayList() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, buildResult.getEligibleServers(servers));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   * <ul>
   *   <li>Then return size is five.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers_thenReturnSizeIsFive() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    Server server = new Server("42");
    servers.add(server);

    // Act
    List<Server> actualEligibleServers = buildResult.getEligibleServers(servers);

    // Assert
    assertEquals(5, actualEligibleServers.size());
    assertSame(server, actualEligibleServers.get(4));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   * <ul>
   *   <li>Then return size is four.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers_thenReturnSizeIsFour() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));
    Server server = new Server("42");
    servers.add(server);
    Server server2 = new Server("42");
    servers.add(server2);

    // Act
    List<Server> actualEligibleServers = buildResult.getEligibleServers(servers);

    // Assert
    assertEquals(4, actualEligibleServers.size());
    assertSame(server, actualEligibleServers.get(2));
    assertSame(server2, actualEligibleServers.get(3));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers_thenReturnSizeIsTwo() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    Server server = new Server("42");
    servers.add(server);

    // Act
    List<Server> actualEligibleServers = buildResult.getEligibleServers(servers);

    // Assert
    assertEquals(2, actualEligibleServers.size());
    assertSame(server, actualEligibleServers.get(1));
  }

  /**
   * Test {@link AbstractServerPredicate#getEligibleServers(List)} with {@code servers}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractServerPredicate#getEligibleServers(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractServerPredicate.getEligibleServers(List)"})
  public void testGetEligibleServersWithServers_whenArrayList_thenReturnEmpty() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();

    // Act and Assert
    assertTrue(compositePredicate.getEligibleServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link AbstractServerPredicate#chooseRandomlyAfterFiltering(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <p>
   * Method under test: {@link AbstractServerPredicate#chooseRandomlyAfterFiltering(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "com.google.common.base.Optional AbstractServerPredicate.chooseRandomlyAfterFiltering(List, Object)"})
  public void testChooseRandomlyAfterFilteringWithServersLoadBalancerKey() {
    // Arrange
    IRule rule = mock(IRule.class);
    when(rule.getLoadBalancer()).thenReturn(null);
    CompositePredicate buildResult = CompositePredicate.withPredicate(new AvailabilityPredicate(rule)).build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    buildResult.chooseRandomlyAfterFiltering(servers, null);

    // Assert
    verify(rule).getLoadBalancer();
  }

  /**
   * Test {@link AbstractServerPredicate#chooseRoundRobinAfterFiltering(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <p>
   * Method under test: {@link AbstractServerPredicate#chooseRoundRobinAfterFiltering(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "com.google.common.base.Optional AbstractServerPredicate.chooseRoundRobinAfterFiltering(List, Object)"})
  public void testChooseRoundRobinAfterFilteringWithServersLoadBalancerKey() {
    // Arrange
    IRule rule = mock(IRule.class);
    when(rule.getLoadBalancer()).thenReturn(null);
    CompositePredicate buildResult = CompositePredicate.withPredicate(new AvailabilityPredicate(rule)).build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    buildResult.chooseRoundRobinAfterFiltering(servers, null);

    // Assert
    verify(rule).getLoadBalancer();
  }

  /**
   * Test {@link AbstractServerPredicate#ofKeyPredicate(Predicate)}.
   * <p>
   * Method under test: {@link AbstractServerPredicate#ofKeyPredicate(Predicate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractServerPredicate AbstractServerPredicate.ofKeyPredicate(Predicate)"})
  public void testOfKeyPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualOfKeyPredicateResult = AbstractServerPredicate
        .ofKeyPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()));

    // Assert
    assertNull(actualOfKeyPredicateResult.rule);
    assertNull(actualOfKeyPredicateResult.getLBStats());
  }

  /**
   * Test {@link AbstractServerPredicate#ofServerPredicate(Predicate)}.
   * <p>
   * Method under test: {@link AbstractServerPredicate#ofServerPredicate(Predicate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractServerPredicate AbstractServerPredicate.ofServerPredicate(Predicate)"})
  public void testOfServerPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualOfServerPredicateResult = AbstractServerPredicate.ofServerPredicate(null);

    // Assert
    assertNull(actualOfServerPredicateResult.rule);
    assertNull(actualOfServerPredicateResult.getLBStats());
  }
}
