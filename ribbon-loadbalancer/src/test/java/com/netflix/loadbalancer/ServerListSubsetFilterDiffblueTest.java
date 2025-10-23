package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ServerListSubsetFilterDiffblueTest {
  /**
   * Test {@link ServerListSubsetFilter#ServerListSubsetFilter()}.
   *
   * <p>Method under test: {@link ServerListSubsetFilter#ServerListSubsetFilter()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerListSubsetFilter.<init>()"})
  public void testNewServerListSubsetFilter() {
    // Arrange and Act
    ServerListSubsetFilter<Server> actualServerListSubsetFilter = new ServerListSubsetFilter<>();

    // Assert
    assertNull(actualServerListSubsetFilter.getLoadBalancerStats());
  }

  /**
   * Test {@link ServerListSubsetFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ServerListSubsetFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ServerListSubsetFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_whenArrayList_thenReturnEmpty() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link ServerListSubsetFilter#compare(Server, Server)} with {@code Server}, {@code
   * Server}.
   *
   * <p>Method under test: {@link ServerListSubsetFilter#compare(Server, Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerListSubsetFilter.compare(Server, Server)"})
  public void testCompareWithServerServer() {
    // Arrange
    LoadBalancerStats stats = mock(LoadBalancerStats.class);
    when(stats.getSingleServerStat(Mockito.<Server>any())).thenReturn(new ServerStats());

    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    serverListSubsetFilter.setLoadBalancerStats(stats);
    Server server = new Server("42");

    // Act
    int actualCompareResult = serverListSubsetFilter.compare(server, new Server("42"));

    // Assert
    verify(stats, atLeast(1)).getSingleServerStat(isA(Server.class));
    assertEquals(0, actualCompareResult);
  }

  /**
   * Test {@link ServerListSubsetFilter#compare(Server, Server)} with {@code Server}, {@code
   * Server}.
   *
   * <p>Method under test: {@link ServerListSubsetFilter#compare(Server, Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerListSubsetFilter.compare(Server, Server)"})
  public void testCompareWithServerServer2() {
    // Arrange
    LoadBalancerStats stats = mock(LoadBalancerStats.class);
    when(stats.getSingleServerStat(Mockito.<Server>any())).thenReturn(new DummyServerStats(3, 3));

    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    serverListSubsetFilter.setLoadBalancerStats(stats);
    Server server = new Server("42");

    // Act
    int actualCompareResult = serverListSubsetFilter.compare(server, new Server("42"));

    // Assert
    verify(stats, atLeast(1)).getSingleServerStat(isA(Server.class));
    assertEquals(0, actualCompareResult);
  }

  /**
   * Test {@link ServerListSubsetFilter#compare(Server, Server)} with {@code Server}, {@code
   * Server}.
   *
   * <ul>
   *   <li>Given {@link ServerStats#ServerStats()} incrementActiveRequestsCount.
   * </ul>
   *
   * <p>Method under test: {@link ServerListSubsetFilter#compare(Server, Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerListSubsetFilter.compare(Server, Server)"})
  public void testCompareWithServerServer_givenServerStatsIncrementActiveRequestsCount() {
    // Arrange
    ServerStats serverStats = new ServerStats();
    serverStats.incrementActiveRequestsCount();

    LoadBalancerStats stats = mock(LoadBalancerStats.class);
    when(stats.getSingleServerStat(Mockito.<Server>any())).thenReturn(serverStats);

    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    serverListSubsetFilter.setLoadBalancerStats(stats);
    Server server = new Server("42");

    // Act
    int actualCompareResult = serverListSubsetFilter.compare(server, new Server("42"));

    // Assert
    verify(stats, atLeast(1)).getSingleServerStat(isA(Server.class));
    assertEquals(0, actualCompareResult);
  }
}
