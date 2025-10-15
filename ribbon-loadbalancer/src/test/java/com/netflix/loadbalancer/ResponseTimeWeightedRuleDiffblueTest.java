package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.loadbalancer.ResponseTimeWeightedRule.DynamicServerWeightTask;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ResponseTimeWeightedRuleDiffblueTest {
  /**
   * Test DynamicServerWeightTask {@link DynamicServerWeightTask#run()}.
   *
   * <ul>
   *   <li>Then calls {@link ResponseTimeWeightedRule#getLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerWeightTask#run()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerWeightTask.run()"})
  public void testDynamicServerWeightTaskRun_thenCallsGetLoadBalancer() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = mock(ResponseTimeWeightedRule.class);
    when(responseTimeWeightedRule.getLoadBalancer()).thenReturn(new BaseLoadBalancer());

    // Act
    responseTimeWeightedRule.new DynamicServerWeightTask().run();

    // Assert
    verify(responseTimeWeightedRule).getLoadBalancer();
  }

  /**
   * Test {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.<init>()"})
  public void testNewResponseTimeWeightedRule() {
    // Arrange and Act
    ResponseTimeWeightedRule actualResponseTimeWeightedRule = new ResponseTimeWeightedRule();

    // Assert
    assertEquals("unknown", actualResponseTimeWeightedRule.name);
    assertNull(actualResponseTimeWeightedRule.getLoadBalancer());
    assertNull(actualResponseTimeWeightedRule.serverWeightTimer);
    assertFalse(actualResponseTimeWeightedRule.serverWeightAssignmentInProgress.get());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} initialize {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_givenResponseTimeWeightedRuleInitializeBaseLoadBalancer() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.initialize(new BaseLoadBalancer());
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", responseTimeWeightedRule.name);
    assertSame(lb, responseTimeWeightedRule.getLoadBalancer());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_givenServerWithIdIs42() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("42"));

    // Act
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", responseTimeWeightedRule.name);
    assertSame(lb, responseTimeWeightedRule.getLoadBalancer());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} LoadBalancer is {@link
   *       NoOpLoadBalancer} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_thenResponseTimeWeightedRuleLoadBalancerIsNoOpLoadBalancer() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    NoOpLoadBalancer lb = new NoOpLoadBalancer();

    // Act
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Assert
    assertEquals("unknown", responseTimeWeightedRule.name);
    assertSame(lb, responseTimeWeightedRule.getLoadBalancer());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_whenBaseLoadBalancer() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", responseTimeWeightedRule.name);
    assertSame(lb, responseTimeWeightedRule.getLoadBalancer());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link
   *       ResponseTimeWeightedRule#name} is {@code unknown}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_whenNull_thenResponseTimeWeightedRuleNameIsUnknown() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();

    // Act
    responseTimeWeightedRule.setLoadBalancer(null);

    // Assert that nothing has changed
    assertEquals("unknown", responseTimeWeightedRule.name);
  }

  /**
   * Test {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link BaseLoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenArrayListAddNull_thenCallsGetAllServers() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(null);

    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(serverList);
    when(lb.getLoadBalancerStats()).thenReturn(new LoadBalancerStats());
    when(lb.getName()).thenReturn("Name");

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Act
    responseTimeWeightedRule.initialize(new BaseLoadBalancer());

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
  }

  /**
   * Test {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>Then calls {@link BaseLoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenArrayListAddServerWithIdIs42_thenCallsGetAllServers() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));

    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(serverList);
    when(lb.getLoadBalancerStats()).thenReturn(new LoadBalancerStats());
    when(lb.getName()).thenReturn("Name");

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Act
    responseTimeWeightedRule.initialize(new BaseLoadBalancer());

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
  }

  /**
   * Test {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer} {@link BaseLoadBalancer#getLoadBalancerStats()} return
   *       {@link LoadBalancerStats#LoadBalancerStats()}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenBaseLoadBalancerGetLoadBalancerStatsReturnLoadBalancerStats() {
    // Arrange
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(new ArrayList<>());
    when(lb.getLoadBalancerStats()).thenReturn(new LoadBalancerStats());
    when(lb.getName()).thenReturn("Name");

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Act
    responseTimeWeightedRule.initialize(new BaseLoadBalancer());

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
  }

  /**
   * Test {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Then calls {@link LoadBalancerStats#getSingleServerStat(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.initialize(ILoadBalancer)"})
  public void testInitialize_thenCallsGetSingleServerStat() {
    // Arrange
    LoadBalancerStats loadBalancerStats = mock(LoadBalancerStats.class);
    when(loadBalancerStats.getSingleServerStat(Mockito.<Server>any()))
        .thenReturn(new ServerStats());

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));

    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(serverList);
    when(lb.getLoadBalancerStats()).thenReturn(loadBalancerStats);
    when(lb.getName()).thenReturn("Name");

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Act
    responseTimeWeightedRule.initialize(new BaseLoadBalancer());

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
    verify(loadBalancerStats, atLeast(1)).getSingleServerStat(isA(Server.class));
  }

  /**
   * Test {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Given {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ResponseTimeWeightedRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenResponseTimeWeightedRule_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new ResponseTimeWeightedRule().choose(null, "Key"));
  }

  /**
   * Test {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers size is one.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ResponseTimeWeightedRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_thenBaseLoadBalancerAllServersSizeIsOne() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);
    lb.setPing(ping);
    lb.addServer(new Server("42"));

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setLoadBalancer(lb);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server("42"));

    // Act
    Server actualChooseResult = responseTimeWeightedRule.choose(lb2, "Key");

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    ILoadBalancer loadBalancer = responseTimeWeightedRule.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertEquals(1, lb2.getAllServers().size());
    assertEquals(1, lb2.getReachableServers().size());
    assertEquals(newServer, actualChooseResult);
    assertEquals(lb.allServerList, loadBalancer.getReachableServers());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ResponseTimeWeightedRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_thenReturnServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setLoadBalancer(lb);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server("42"));

    // Act
    Server actualChooseResult = responseTimeWeightedRule.choose(lb2, "Key");

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertSame(newServer, actualChooseResult);
  }

  /**
   * Test {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then {@link BaseLoadBalancer#BaseLoadBalancer()} AllServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ResponseTimeWeightedRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_whenBaseLoadBalancer_thenBaseLoadBalancerAllServersEmpty() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act and Assert
    assertNull(responseTimeWeightedRule.choose(lb, "Key"));
    assertTrue(lb.getAllServers().isEmpty());
    assertTrue(lb.getReachableServers().isEmpty());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#initWithNiwsConfig(IClientConfig)} with {@code
   * clientConfig}.
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig() {
    // Arrange
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getLoadBalancerStats()).thenReturn(null);
    when(lb.getName()).thenReturn("Name");
    doNothing().when(lb).addServerListChangeListener(Mockito.<ServerListChangeListener>any());
    lb.addServerListChangeListener(mock(ServerListChangeListener.class));

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setLoadBalancer(lb);

    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.get(Mockito.<IClientConfigKey<Integer>>any(), Mockito.<Integer>any()))
        .thenReturn(1);

    // Act
    responseTimeWeightedRule.initWithNiwsConfig(clientConfig);

    // Assert
    verify(clientConfig).get(isA(IClientConfigKey.class), eq(30000));
    verify(lb).addServerListChangeListener(isA(ServerListChangeListener.class));
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
  }
}
