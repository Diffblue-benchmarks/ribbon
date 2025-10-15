package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
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
   * Test {@link ServerListSubsetFilter#ServerListSubsetFilter(IClientConfig)}.
   *
   * <p>Method under test: {@link ServerListSubsetFilter#ServerListSubsetFilter(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerListSubsetFilter.<init>(IClientConfig)"})
  public void testNewServerListSubsetFilter2() {
    // Arrange
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "[{}] get dynamic property key={} ns={}");

    // Act
    ServerListSubsetFilter<Server> actualServerListSubsetFilter =
        new ServerListSubsetFilter<>(clientConfig);

    // Assert
    assertNull(actualServerListSubsetFilter.getLoadBalancerStats());
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Test {@link ServerListSubsetFilter#ServerListSubsetFilter(IClientConfig)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then EmptyConfig RefreshCount is four.
   * </ul>
   *
   * <p>Method under test: {@link ServerListSubsetFilter#ServerListSubsetFilter(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerListSubsetFilter.<init>(IClientConfig)"})
  public void testNewServerListSubsetFilter_whenEmptyConfig_thenEmptyConfigRefreshCountIsFour() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    ServerListSubsetFilter<Server> actualServerListSubsetFilter =
        new ServerListSubsetFilter<>(clientConfig);

    // Assert
    assertNull(actualServerListSubsetFilter.getLoadBalancerStats());
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Test {@link ServerListSubsetFilter#initWithNiwsConfig(IClientConfig)} with {@code
   * clientConfig}.
   *
   * <p>Method under test: {@link ServerListSubsetFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerListSubsetFilter.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "[{}] get dynamic property key={} ns={}");

    // Act
    serverListSubsetFilter.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Test {@link ServerListSubsetFilter#initWithNiwsConfig(IClientConfig)} with {@code
   * clientConfig}.
   *
   * <ul>
   *   <li>Then EmptyConfig RefreshCount is four.
   * </ul>
   *
   * <p>Method under test: {@link ServerListSubsetFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerListSubsetFilter.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig_thenEmptyConfigRefreshCountIsFour() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    serverListSubsetFilter.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Test {@link ServerListSubsetFilter#getFilteredListOfServers(List)}.
   *
   * <p>Method under test: {@link ServerListSubsetFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ServerListSubsetFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers() {
    // Arrange
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    ServerListSubsetFilter<Server> serverListSubsetFilter =
        new ServerListSubsetFilter<>(clientConfig);

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link ServerListSubsetFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ServerListSubsetFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ServerListSubsetFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_givenServerWithIdIs42_thenReturnArrayList() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    List<Server> actualFilteredListOfServers =
        serverListSubsetFilter.getFilteredListOfServers(servers);

    // Assert
    assertEquals(servers, actualFilteredListOfServers);
  }

  /**
   * Test {@link ServerListSubsetFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link ServerListSubsetFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ServerListSubsetFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_givenServerWithIdIs42_thenReturnSizeIsOne() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);
    servers.add(new Server("42"));

    // Act
    List<Server> actualFilteredListOfServers =
        serverListSubsetFilter.getFilteredListOfServers(servers);

    // Assert
    assertEquals(1, actualFilteredListOfServers.size());
    assertSame(server, actualFilteredListOfServers.get(0));
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
