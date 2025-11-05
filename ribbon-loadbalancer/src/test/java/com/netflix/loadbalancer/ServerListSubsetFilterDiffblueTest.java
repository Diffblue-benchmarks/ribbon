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
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;

public class ServerListSubsetFilterDiffblueTest {
  /**
   * Method under test:
   * {@link ServerListSubsetFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    serverListSubsetFilter.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig2() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "[{}] get dynamic property key={} ns={}");

    // Act
    serverListSubsetFilter.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig3() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    serverListSubsetFilter.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers2() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>(
        DefaultClientConfigImpl.getEmptyConfig());

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers3() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, serverListSubsetFilter.getFilteredListOfServers(servers));
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers4() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);
    servers.add(new Server("42"));

    // Act
    List<Server> actualFilteredListOfServers = serverListSubsetFilter.getFilteredListOfServers(servers);

    // Assert
    assertEquals(1, actualFilteredListOfServers.size());
    assertSame(server, actualFilteredListOfServers.get(0));
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers5() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>(clientConfig);

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers6() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>(
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Mr John Smith", "Name Space"));

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test: {@link ServerListSubsetFilter#compare(Server, Server)}
   */
  @Test
  public void testCompare() {
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
   * Method under test: {@link ServerListSubsetFilter#compare(Server, Server)}
   */
  @Test
  public void testCompare2() {
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
   * Method under test: {@link ServerListSubsetFilter#compare(Server, Server)}
   */
  @Test
  public void testCompare3() {
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

  /**
   * Method under test: {@link ServerListSubsetFilter#ServerListSubsetFilter()}
   */
  @Test
  public void testNewServerListSubsetFilter() {
    // Arrange and Act
    ServerListSubsetFilter<Server> actualServerListSubsetFilter = new ServerListSubsetFilter<>();

    // Assert
    assertNull(actualServerListSubsetFilter.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#ServerListSubsetFilter(IClientConfig)}
   */
  @Test
  public void testNewServerListSubsetFilter2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    ServerListSubsetFilter<Server> actualServerListSubsetFilter = new ServerListSubsetFilter<>(clientConfig);

    // Assert
    assertNull(actualServerListSubsetFilter.getLoadBalancerStats());
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#ServerListSubsetFilter(IClientConfig)}
   */
  @Test
  public void testNewServerListSubsetFilter3() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "[{}] get dynamic property key={} ns={}");

    // Act
    ServerListSubsetFilter<Server> actualServerListSubsetFilter = new ServerListSubsetFilter<>(clientConfig);

    // Assert
    assertNull(actualServerListSubsetFilter.getLoadBalancerStats());
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ServerListSubsetFilter#ServerListSubsetFilter(IClientConfig)}
   */
  @Test
  public void testNewServerListSubsetFilter4() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    ServerListSubsetFilter<Server> actualServerListSubsetFilter = new ServerListSubsetFilter<>(clientConfig);

    // Assert
    assertNull(actualServerListSubsetFilter.getLoadBalancerStats());
    assertEquals(4L, clientConfig.getRefreshCount());
  }
}
