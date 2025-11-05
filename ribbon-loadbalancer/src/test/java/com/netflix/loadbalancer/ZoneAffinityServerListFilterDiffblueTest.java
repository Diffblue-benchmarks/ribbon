package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ZoneAffinityServerListFilterDiffblueTest {
  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>();
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    zoneAffinityServerListFilter.initWithNiwsConfig(niwsClientConfig);

    // Assert
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig2() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>();
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "[{}] get global property '{}' with default '{}'");

    // Act
    zoneAffinityServerListFilter.initWithNiwsConfig(niwsClientConfig);

    // Assert
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig3() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>();
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getEmptyConfig();
    niwsClientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    zoneAffinityServerListFilter.initWithNiwsConfig(niwsClientConfig);

    // Assert
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig4() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    DefaultClientConfigImpl clientConfig = new DefaultClientConfigImpl();

    // Act
    serverListSubsetFilter.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>();
    ArrayList<Server> servers = new ArrayList<>();

    // Act
    List<Server> actualFilteredListOfServers = zoneAffinityServerListFilter.getFilteredListOfServers(servers);

    // Assert
    assertTrue(actualFilteredListOfServers.isEmpty());
    assertSame(servers, actualFilteredListOfServers);
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers2() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers3() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>(
        DefaultClientConfigImpl.getEmptyConfig());

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers4() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>(
        DefaultClientConfigImpl.getEmptyConfig());
    ArrayList<Server> servers = new ArrayList<>();

    // Act
    List<Server> actualFilteredListOfServers = zoneAffinityServerListFilter.getFilteredListOfServers(servers);

    // Assert
    assertTrue(actualFilteredListOfServers.isEmpty());
    assertSame(servers, actualFilteredListOfServers);
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers5() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertSame(servers, zoneAffinityServerListFilter.getFilteredListOfServers(servers));
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers6() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));

    // Act and Assert
    assertSame(servers, zoneAffinityServerListFilter.getFilteredListOfServers(servers));
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers7() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("/"));
    servers.add(new Server("42"));

    // Act and Assert
    assertSame(servers, zoneAffinityServerListFilter.getFilteredListOfServers(servers));
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers8() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>(clientConfig);

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers9() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setClientName("Dr Jane Doe");
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>(clientConfig);

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  public void testGetFilteredListOfServers10() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setClientName("Mr John Smith");
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>(clientConfig);

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ZoneAffinityServerListFilter#ZoneAffinityServerListFilter()}
   *   <li>{@link ZoneAffinityServerListFilter#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ZoneAffinityServerListFilter<Server> actualZoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>();

    // Assert
    assertEquals("ZoneAffinityServerListFilter:, zone: null, zoneAffinity:false, zoneExclusivity:false",
        actualZoneAffinityServerListFilter.toString());
    assertNull(actualZoneAffinityServerListFilter.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#ZoneAffinityServerListFilter(IClientConfig)}
   */
  @Test
  public void testNewZoneAffinityServerListFilter() {
    // Arrange
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    ZoneAffinityServerListFilter<Server> actualZoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>(
        niwsClientConfig);

    // Assert
    assertNull(actualZoneAffinityServerListFilter.getLoadBalancerStats());
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#ZoneAffinityServerListFilter(IClientConfig)}
   */
  @Test
  public void testNewZoneAffinityServerListFilter2() {
    // Arrange
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "[{}] get global property '{}' with default '{}'");

    // Act
    ZoneAffinityServerListFilter<Server> actualZoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>(
        niwsClientConfig);

    // Assert
    assertNull(actualZoneAffinityServerListFilter.getLoadBalancerStats());
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ZoneAffinityServerListFilter#ZoneAffinityServerListFilter(IClientConfig)}
   */
  @Test
  public void testNewZoneAffinityServerListFilter3() {
    // Arrange
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getEmptyConfig();
    niwsClientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    ZoneAffinityServerListFilter<Server> actualZoneAffinityServerListFilter = new ZoneAffinityServerListFilter<>(
        niwsClientConfig);

    // Assert
    assertNull(actualZoneAffinityServerListFilter.getLoadBalancerStats());
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }
}
