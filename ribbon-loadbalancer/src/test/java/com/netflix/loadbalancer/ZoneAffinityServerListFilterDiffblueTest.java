package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
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

public class ZoneAffinityServerListFilterDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ZoneAffinityServerListFilter#ZoneAffinityServerListFilter()}
   *   <li>{@link ZoneAffinityServerListFilter#toString()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ZoneAffinityServerListFilter.<init>()",
    "java.lang.String ZoneAffinityServerListFilter.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ZoneAffinityServerListFilter<Server> actualZoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>();

    // Assert
    assertEquals(
        "ZoneAffinityServerListFilter:, zone: null, zoneAffinity:false, zoneExclusivity:false",
        actualZoneAffinityServerListFilter.toString());
    assertNull(actualZoneAffinityServerListFilter.getLoadBalancerStats());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#ZoneAffinityServerListFilter(IClientConfig)}.
   *
   * <p>Method under test: {@link
   * ZoneAffinityServerListFilter#ZoneAffinityServerListFilter(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAffinityServerListFilter.<init>(IClientConfig)"})
  public void testNewZoneAffinityServerListFilter() {
    // Arrange
    DefaultClientConfigImpl niwsClientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "[{}] get global property '{}' with default '{}'");

    // Act
    ZoneAffinityServerListFilter<Server> actualZoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>(niwsClientConfig);

    // Assert
    assertNull(actualZoneAffinityServerListFilter.getLoadBalancerStats());
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#ZoneAffinityServerListFilter(IClientConfig)}.
   *
   * <ul>
   *   <li>Then EmptyConfig RefreshCount is four.
   * </ul>
   *
   * <p>Method under test: {@link
   * ZoneAffinityServerListFilter#ZoneAffinityServerListFilter(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAffinityServerListFilter.<init>(IClientConfig)"})
  public void testNewZoneAffinityServerListFilter_thenEmptyConfigRefreshCountIsFour() {
    // Arrange
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    ZoneAffinityServerListFilter<Server> actualZoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>(niwsClientConfig);

    // Assert
    assertNull(actualZoneAffinityServerListFilter.getLoadBalancerStats());
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#initWithNiwsConfig(IClientConfig)} with {@code
   * niwsClientConfig}.
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAffinityServerListFilter.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithNiwsClientConfig() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>();
    DefaultClientConfigImpl niwsClientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "[{}] get global property '{}' with default '{}'");

    // Act
    zoneAffinityServerListFilter.initWithNiwsConfig(niwsClientConfig);

    // Assert
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#initWithNiwsConfig(IClientConfig)} with {@code
   * niwsClientConfig}.
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAffinityServerListFilter.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithNiwsClientConfig2() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    DefaultClientConfigImpl clientConfig = new DefaultClientConfigImpl();

    // Act
    serverListSubsetFilter.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(4L, clientConfig.getRefreshCount());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#initWithNiwsConfig(IClientConfig)} with {@code
   * niwsClientConfig}.
   *
   * <ul>
   *   <li>Then EmptyConfig RefreshCount is four.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ZoneAffinityServerListFilter.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithNiwsClientConfig_thenEmptyConfigRefreshCountIsFour() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>();
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    zoneAffinityServerListFilter.initWithNiwsConfig(niwsClientConfig);

    // Assert
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
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
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers2() {
    // Arrange
    IClientConfig niwsClientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>(niwsClientConfig);

    // Act and Assert
    assertTrue(zoneAffinityServerListFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers3() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withZoneAffinityEnabled(true);
    IClientConfig niwsClientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>(niwsClientConfig);
    zoneAffinityServerListFilter.setLoadBalancerStats(new LoadBalancerStats());

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    List<Server> actualFilteredListOfServers =
        zoneAffinityServerListFilter.getFilteredListOfServers(servers);

    // Assert
    assertSame(servers, actualFilteredListOfServers);
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Given newBuilder withZoneAffinityEnabled {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_givenNewBuilderWithZoneAffinityEnabledTrue() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withZoneAffinityEnabled(true);
    IClientConfig niwsClientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>(niwsClientConfig);

    // Act and Assert
    assertTrue(zoneAffinityServerListFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Given newBuilder withZoneAffinityEnabled {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_givenNewBuilderWithZoneAffinityEnabledTrue2() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withZoneAffinityEnabled(true);
    IClientConfig niwsClientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>(niwsClientConfig);

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityServerListFilter.getFilteredListOfServers(servers).isEmpty());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Given newBuilder withZoneExclusivityEnabled {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_givenNewBuilderWithZoneExclusivityEnabledTrue() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withZoneExclusivityEnabled(true);
    IClientConfig niwsClientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>(niwsClientConfig);

    // Act and Assert
    assertTrue(zoneAffinityServerListFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Given newBuilder withZoneExclusivityEnabled {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_givenNewBuilderWithZoneExclusivityEnabledTrue2() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withZoneExclusivityEnabled(true);
    IClientConfig niwsClientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>(niwsClientConfig);

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(zoneAffinityServerListFilter.getFilteredListOfServers(servers).isEmpty());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Given {@link ServerListSubsetFilter#ServerListSubsetFilter()}.
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_givenServerListSubsetFilter_whenArrayList() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAffinityServerListFilter#ZoneAffinityServerListFilter()}.
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_givenZoneAffinityServerListFilter_whenArrayList() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>();

    // Act and Assert
    assertTrue(zoneAffinityServerListFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_thenReturnArrayList() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    List<Server> actualFilteredListOfServers =
        zoneAffinityServerListFilter.getFilteredListOfServers(servers);

    // Assert
    assertSame(servers, actualFilteredListOfServers);
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_thenReturnSizeIsTwo() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    Server server = new Server("42");
    servers.add(server);

    // Act
    List<Server> actualFilteredListOfServers =
        zoneAffinityServerListFilter.getFilteredListOfServers(servers);

    // Assert
    assertEquals(2, actualFilteredListOfServers.size());
    assertSame(server, actualFilteredListOfServers.get(1));
  }
}
