package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.IClientConfigAware;
import com.netflix.client.IClientConfigAware.Factory;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class LoadBalancerBuilderDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link LoadBalancerBuilder#withClientConfig(IClientConfig)}
   *   <li>{@link LoadBalancerBuilder#withDynamicServerList(ServerList)}
   *   <li>{@link LoadBalancerBuilder#withFactory(Factory)}
   *   <li>{@link LoadBalancerBuilder#withPing(IPing)}
   *   <li>{@link LoadBalancerBuilder#withRule(IRule)}
   *   <li>{@link LoadBalancerBuilder#withServerListFilter(ServerListFilter)}
   *   <li>{@link LoadBalancerBuilder#withServerListUpdater(ServerListUpdater)}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancerBuilder LoadBalancerBuilder.withClientConfig(IClientConfig)",
    "LoadBalancerBuilder LoadBalancerBuilder.withDynamicServerList(ServerList)",
    "LoadBalancerBuilder LoadBalancerBuilder.withFactory(Factory)",
    "LoadBalancerBuilder LoadBalancerBuilder.withPing(IPing)",
    "LoadBalancerBuilder LoadBalancerBuilder.withRule(IRule)",
    "LoadBalancerBuilder LoadBalancerBuilder.withServerListFilter(ServerListFilter)",
    "LoadBalancerBuilder LoadBalancerBuilder.withServerListUpdater(ServerListUpdater)"
  })
  public void testGettersAndSetters() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act
    LoadBalancerBuilder<Server> actualWithClientConfigResult =
        newBuilderResult.withClientConfig(
            Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build());
    LoadBalancerBuilder<Server> actualWithDynamicServerListResult =
        newBuilderResult.withDynamicServerList(new ConfigurationBasedServerList());
    LoadBalancerBuilder<Server> actualWithFactoryResult =
        newBuilderResult.withFactory(mock(Factory.class));
    LoadBalancerBuilder<Server> actualWithPingResult = newBuilderResult.withPing(mock(IPing.class));
    LoadBalancerBuilder<Server> actualWithRuleResult =
        newBuilderResult.withRule(new AvailabilityFilteringRule());
    LoadBalancerBuilder<Server> actualWithServerListFilterResult =
        newBuilderResult.withServerListFilter(mock(ServerListFilter.class));
    LoadBalancerBuilder<Server> actualWithServerListUpdaterResult =
        newBuilderResult.withServerListUpdater(new PollingServerListUpdater());

    // Assert
    assertSame(newBuilderResult, actualWithClientConfigResult);
    assertSame(newBuilderResult, actualWithDynamicServerListResult);
    assertSame(newBuilderResult, actualWithFactoryResult);
    assertSame(newBuilderResult, actualWithPingResult);
    assertSame(newBuilderResult, actualWithRuleResult);
    assertSame(newBuilderResult, actualWithServerListFilterResult);
    assertSame(newBuilderResult, actualWithServerListUpdaterResult);
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withRule(null);
    newBuilderResult.withPing(ping);

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    List<Server> reachableServers =
        actualBuildFixedServerListLoadBalancerResult.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = actualBuildFixedServerListLoadBalancerResult.upServerList;
    assertEquals(1, serverList.size());
    assertSame(server, reachableServers.get(0));
    assertSame(server, serverList.get(0));
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    RetryRule rule = new RetryRule(new AvailabilityFilteringRule(), 1L);
    newBuilderResult.withRule(rule);
    newBuilderResult.withPing(ping);

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertSame(rule, actualBuildFixedServerListLoadBalancerResult.getRule());
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer3()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    Factory factory = mock(Factory.class);
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenReturn(availabilityFilteringRule);

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);
    newBuilderResult.withRule(null);
    newBuilderResult.withPing(ping);

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    verify(factory)
        .create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
    verify(ping).isAlive(isA(Server.class));
    IRule rule = actualBuildFixedServerListLoadBalancerResult.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    List<Server> reachableServers =
        actualBuildFixedServerListLoadBalancerResult.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = actualBuildFixedServerListLoadBalancerResult.upServerList;
    assertEquals(1, serverList.size());
    assertSame(availabilityFilteringRule, rule);
    assertSame(server, reachableServers.get(0));
    assertSame(server, serverList.get(0));
    assertSame(
        availabilityFilteringRule.roundRobinRule,
        ((AvailabilityFilteringRule) rule).roundRobinRule);
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Given newBuilder.
   *   <li>Then Ping return {@link DummyPing}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_givenNewBuilder_thenPingReturnDummyPing() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    IPing ping = actualBuildFixedServerListLoadBalancerResult.getPing();
    assertTrue(ping instanceof DummyPing);
    assertNull(actualBuildFixedServerListLoadBalancerResult.lbTimer);
    List<Server> reachableServers =
        actualBuildFixedServerListLoadBalancerResult.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = actualBuildFixedServerListLoadBalancerResult.upServerList;
    assertEquals(1, serverList.size());
    assertSame(server, reachableServers.get(0));
    assertSame(server, serverList.get(0));
    assertSame(actualBuildFixedServerListLoadBalancerResult, ((DummyPing) ping).getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Then return {@link BaseLoadBalancer#allServerList} size is one.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_thenReturnAllServerListSizeIsOne() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withRule(null);
    newBuilderResult.withPing(ping);

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("localhost", 8080);
    servers.add(server);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    List<Server> allServers = actualBuildFixedServerListLoadBalancerResult.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers =
        actualBuildFixedServerListLoadBalancerResult.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = actualBuildFixedServerListLoadBalancerResult.allServerList;
    assertEquals(1, serverList.size());
    List<Server> serverList2 = actualBuildFixedServerListLoadBalancerResult.upServerList;
    assertEquals(1, serverList2.size());
    assertSame(server, allServers.get(0));
    assertSame(server, reachableServers.get(0));
    assertSame(server, serverList.get(0));
    assertSame(server, serverList2.get(0));
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Then return AllServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_thenReturnAllServersEmpty() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    newBuilderResult.withRule(rule);
    newBuilderResult.withPing(mock(IPing.class));

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(null);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getAllServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.allServerList.isEmpty());
    assertSame(rule, actualBuildFixedServerListLoadBalancerResult.getRule());
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Then return AllServers size is two.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_thenReturnAllServersSizeIsTwo() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    Server server = new Server("42");
    servers.add(server);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    List<Server> allServers = actualBuildFixedServerListLoadBalancerResult.getAllServers();
    assertEquals(2, allServers.size());
    List<Server> reachableServers =
        actualBuildFixedServerListLoadBalancerResult.getReachableServers();
    assertEquals(2, reachableServers.size());
    List<Server> serverList = actualBuildFixedServerListLoadBalancerResult.allServerList;
    assertEquals(2, serverList.size());
    List<Server> serverList2 = actualBuildFixedServerListLoadBalancerResult.upServerList;
    assertEquals(2, serverList2.size());
    assertSame(server, allServers.get(1));
    assertSame(server, reachableServers.get(1));
    assertSame(server, serverList.get(1));
    assertSame(server, serverList2.get(1));
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Then return ClientConfig RefreshCount is seven.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_thenReturnClientConfigRefreshCountIsSeven() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException());

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    newBuilderResult.withRule(rule);
    newBuilderResult.withPing(ping);

    Server server = new Server("42");
    server.setAlive(true);

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(server);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    IClientConfig clientConfig = actualBuildFixedServerListLoadBalancerResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule2 = actualBuildFixedServerListLoadBalancerResult.getRule();
    assertTrue(rule2 instanceof AvailabilityFilteringRule);
    List<Server> allServers = actualBuildFixedServerListLoadBalancerResult.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(7L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertFalse(allServers.get(0).isAlive());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getReachableServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.upServerList.isEmpty());
    assertSame(rule, rule2);
    assertSame(rule.roundRobinRule, ((AvailabilityFilteringRule) rule2).roundRobinRule);
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Then return ClientConfig RefreshCount is ten.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_thenReturnClientConfigRefreshCountIsTen() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException());

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withRule(null);
    newBuilderResult.withPing(ping);

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    IClientConfig clientConfig = actualBuildFixedServerListLoadBalancerResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    List<Server> allServers = actualBuildFixedServerListLoadBalancerResult.getAllServers();
    assertEquals(1, allServers.size());
    assertEquals(10L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertFalse(allServers.get(0).isAlive());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getReachableServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.upServerList.isEmpty());
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Then return Rule AvailableServersCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_thenReturnRuleAvailableServersCountIsZero() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withRule(null);
    newBuilderResult.withPing(mock(IPing.class));

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(null);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    IClientConfig clientConfig = actualBuildFixedServerListLoadBalancerResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = actualBuildFixedServerListLoadBalancerResult.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(10L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getAllServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getReachableServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.allServerList.isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.upServerList.isEmpty());
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Then return Rule is {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_thenReturnRuleIsResponseTimeWeightedRule() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    newBuilderResult.withRule(rule);
    newBuilderResult.withPing(ping);

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertSame(rule, actualBuildFixedServerListLoadBalancerResult.getRule());
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Then return Rule is {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_thenReturnRuleIsResponseTimeWeightedRule2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    ResponseTimeWeightedRule rule = new ResponseTimeWeightedRule();
    newBuilderResult.withRule(rule);
    newBuilderResult.withPing(ping);

    Server server = new Server("42");
    server.setAlive(true);

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(server);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertSame(rule, actualBuildFixedServerListLoadBalancerResult.getRule());
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Then Rule return {@link BestAvailableRule}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_thenRuleReturnBestAvailableRule() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    BestAvailableRule rule = new BestAvailableRule();
    newBuilderResult.withRule(rule);
    newBuilderResult.withPing(ping);

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(servers);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    IRule rule2 = actualBuildFixedServerListLoadBalancerResult.getRule();
    assertTrue(rule2 instanceof BestAvailableRule);
    List<Server> reachableServers =
        actualBuildFixedServerListLoadBalancerResult.getReachableServers();
    assertEquals(1, reachableServers.size());
    List<Server> serverList = actualBuildFixedServerListLoadBalancerResult.upServerList;
    assertEquals(1, serverList.size());
    assertSame(rule, rule2);
    assertSame(server, reachableServers.get(0));
    assertSame(server, serverList.get(0));
    assertSame(rule.roundRobinRule, ((BestAvailableRule) rule2).roundRobinRule);
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_thenThrowRuntimeException()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    Factory factory = mock(Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new IllegalArgumentException());

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);
    newBuilderResult.withRule(null);
    newBuilderResult.withPing(mock(IPing.class));

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    thrown.expect(RuntimeException.class);
    newBuilderResult.buildFixedServerListLoadBalancer(servers);
    verify(factory)
        .create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
  }

  /**
   * Test {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then Ping return {@link DummyPing}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BaseLoadBalancer LoadBalancerBuilder.buildFixedServerListLoadBalancer(List)"})
  public void testBuildFixedServerListLoadBalancer_whenArrayList_thenPingReturnDummyPing() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult =
        newBuilderResult.buildFixedServerListLoadBalancer(new ArrayList<>());

    // Assert
    IRule rule = actualBuildFixedServerListLoadBalancerResult.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    IPing ping = actualBuildFixedServerListLoadBalancerResult.getPing();
    assertTrue(ping instanceof DummyPing);
    assertNull(actualBuildFixedServerListLoadBalancerResult.lbTimer);
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getAllServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.allServerList.isEmpty());
    assertSame(actualBuildFixedServerListLoadBalancerResult, ((DummyPing) ping).getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancer()}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.netflix.loadbalancer.ZoneAwareLoadBalancer LoadBalancerBuilder.buildDynamicServerListLoadBalancer()"
  })
  public void testBuildDynamicServerListLoadBalancer_thenThrowIllegalArgumentException() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildDynamicServerListLoadBalancer();
  }

  /**
   * Test {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancer()}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.netflix.loadbalancer.ZoneAwareLoadBalancer LoadBalancerBuilder.buildDynamicServerListLoadBalancer()"
  })
  public void testBuildDynamicServerListLoadBalancer_thenThrowRuntimeException()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    Factory factory = mock(Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new IllegalArgumentException());

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);
    newBuilderResult.withDynamicServerList(new ConfigurationBasedServerList());
    newBuilderResult.withRule(null);
    newBuilderResult.withServerListFilter(new ServerListSubsetFilter<>());
    newBuilderResult.withPing(new DummyPing());

    // Act and Assert
    thrown.expect(RuntimeException.class);
    newBuilderResult.buildDynamicServerListLoadBalancer();
    verify(factory)
        .create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
  }

  /**
   * Test {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}.
   *
   * <p>Method under test: {@link
   * LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.netflix.loadbalancer.ZoneAwareLoadBalancer LoadBalancerBuilder.buildDynamicServerListLoadBalancerWithUpdater()"
  })
  public void testBuildDynamicServerListLoadBalancerWithUpdater() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildDynamicServerListLoadBalancerWithUpdater();
  }

  /**
   * Test {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.netflix.loadbalancer.ZoneAwareLoadBalancer LoadBalancerBuilder.buildDynamicServerListLoadBalancerWithUpdater()"
  })
  public void testBuildDynamicServerListLoadBalancerWithUpdater_thenThrowRuntimeException()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    Factory factory = mock(Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new IllegalArgumentException());

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);
    newBuilderResult.withDynamicServerList(new ConfigurationBasedServerList());
    newBuilderResult.withRule(null);
    newBuilderResult.withServerListUpdater(null);
    newBuilderResult.withServerListFilter(new ServerListSubsetFilter<>());
    newBuilderResult.withPing(new DummyPing());

    // Act and Assert
    thrown.expect(RuntimeException.class);
    newBuilderResult.buildDynamicServerListLoadBalancerWithUpdater();
    verify(factory)
        .create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
  }

  /**
   * Test {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.netflix.loadbalancer.ZoneAwareLoadBalancer LoadBalancerBuilder.buildDynamicServerListLoadBalancerWithUpdater()"
  })
  public void testBuildDynamicServerListLoadBalancerWithUpdater_thenThrowRuntimeException2()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    Factory factory = mock(Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new IllegalArgumentException());

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);
    newBuilderResult.withDynamicServerList(new ConfigurationBasedServerList());
    newBuilderResult.withRule(new AvailabilityFilteringRule());
    newBuilderResult.withServerListUpdater(null);
    newBuilderResult.withServerListFilter(new ServerListSubsetFilter<>());
    newBuilderResult.withPing(new DummyPing());

    // Act and Assert
    thrown.expect(RuntimeException.class);
    newBuilderResult.buildDynamicServerListLoadBalancerWithUpdater();
    verify(factory)
        .create(eq("com.netflix.loadbalancer.PollingServerListUpdater"), isA(IClientConfig.class));
  }

  /**
   * Test {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}.
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ILoadBalancer LoadBalancerBuilder.buildLoadBalancerFromConfigWithReflection()"
  })
  public void testBuildLoadBalancerFromConfigWithReflection()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    Factory factory = mock(Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new IllegalArgumentException());

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);
    newBuilderResult.withClientConfig(
        Builder.newBuilder("Dr Jane Doe")
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build());

    // Act and Assert
    thrown.expect(RuntimeException.class);
    newBuilderResult.buildLoadBalancerFromConfigWithReflection();
    verify(factory)
        .create(eq("com.netflix.loadbalancer.ZoneAwareLoadBalancer"), isA(IClientConfig.class));
  }

  /**
   * Test {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}.
   *
   * <ul>
   *   <li>Then return {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ILoadBalancer LoadBalancerBuilder.buildLoadBalancerFromConfigWithReflection()"
  })
  public void testBuildLoadBalancerFromConfigWithReflection_thenReturnBaseLoadBalancer()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    Factory factory = mock(Factory.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenReturn(baseLoadBalancer);

    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);
    newBuilderResult.withClientConfig(
        Builder.newBuilder("Dr Jane Doe")
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build());

    // Act
    ILoadBalancer actualBuildLoadBalancerFromConfigWithReflectionResult =
        newBuilderResult.buildLoadBalancerFromConfigWithReflection();

    // Assert
    verify(factory)
        .create(eq("com.netflix.loadbalancer.ZoneAwareLoadBalancer"), isA(IClientConfig.class));
    assertSame(baseLoadBalancer, actualBuildLoadBalancerFromConfigWithReflectionResult);
  }

  /**
   * Test {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ILoadBalancer LoadBalancerBuilder.buildLoadBalancerFromConfigWithReflection()"
  })
  public void testBuildLoadBalancerFromConfigWithReflection_thenThrowIllegalArgumentException() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildLoadBalancerFromConfigWithReflection();
  }
}
