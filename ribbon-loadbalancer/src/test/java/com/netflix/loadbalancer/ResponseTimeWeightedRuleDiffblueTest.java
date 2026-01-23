package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
   *   <li>Given array of {@link Object} with {@code New Servers}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_givenArrayOfObjectWithNewServers() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[] {"New Servers"});
    lb.addServer(new Server("42"));
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
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then calls {@link BaseLoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenArrayListAddNull_whenBaseLoadBalancer_thenCallsGetAllServers() {
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
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenLoadBalancerStats() {
    // Arrange
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(new ArrayList<>());
    when(lb.getLoadBalancerStats()).thenReturn(new LoadBalancerStats());
    when(lb.getName()).thenReturn("Name");

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setLoadBalancer(lb);

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.setLoadBalancerStats(new LoadBalancerStats());

    // Act
    responseTimeWeightedRule.initialize(lb2);

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
   * Test {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}.
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer}.
   *   <li>Then calls {@link LoadBalancerStats#getSingleServerStat(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.initialize(ILoadBalancer)"})
  public void testInitialize_whenBaseLoadBalancer_thenCallsGetSingleServerStat() {
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
    responseTimeWeightedRule.initialize(mock(BaseLoadBalancer.class));

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
    verify(loadBalancerStats, atLeast(1)).getSingleServerStat(isA(Server.class));
  }

  /**
   * Test {@link ResponseTimeWeightedRule#shutdown()}.
   *
   * <ul>
   *   <li>Then calls {@link BaseLoadBalancer#getLoadBalancerStats()}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ResponseTimeWeightedRule.shutdown()"})
  public void testShutdown_thenCallsGetLoadBalancerStats() {
    // Arrange
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getLoadBalancerStats()).thenReturn(null);
    when(lb.getName()).thenReturn("Name");

    ArrayList<Double> weights = new ArrayList<>();
    weights.add(10.0d);

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setWeights(weights);
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Act
    responseTimeWeightedRule.shutdown();

    // Assert
    verify(lb).getLoadBalancerStats();
    verify(lb).getName();
  }

  /**
   * Test {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ResponseTimeWeightedRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServers(new Object[] {"New Servers"});
    lb.addServerListChangeListener(listener);
    lb.setEnablePrimingConnections(true);
    lb.setPing(ping2);
    lb.addServer(new Server("42"));

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setLoadBalancer(lb);

    IPing ping3 = mock(IPing.class);
    when(ping3.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer(ping3, new AvailabilityFilteringRule());
    lb2.addServerListChangeListener(listener3);
    lb2.addServerListChangeListener(listener2);
    lb2.addServer(new Server("42"));

    // Act
    Server actualChooseResult = responseTimeWeightedRule.choose(lb2, "Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping3).isAlive(isA(Server.class));
    verify(ping2, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    assertEquals("New Servers", actualChooseResult.getHost());
    assertEquals("New Servers:80", actualChooseResult.getHostPort());
    assertEquals("New Servers:80", actualChooseResult.getId());
    assertNull(actualChooseResult.getScheme());
    assertEquals(80, actualChooseResult.getPort());
    assertTrue(actualChooseResult.isAlive());
    assertTrue(actualChooseResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualChooseResult.getZone());
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
   *   <li>Then return Host is {@code New Servers}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ResponseTimeWeightedRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_thenReturnHostIsNewServers() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServers(new Object[] {"New Servers"});
    lb.setEnablePrimingConnections(true);
    lb.setPing(ping2);
    lb.addServer(new Server("42"));

    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.setLoadBalancer(lb);

    IPing ping3 = mock(IPing.class);
    when(ping3.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer(ping3, new AvailabilityFilteringRule());
    lb2.addServerListChangeListener(listener);
    lb2.addServer(new Server("42"));

    // Act
    Server actualChooseResult = responseTimeWeightedRule.choose(lb2, "Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(ping3).isAlive(isA(Server.class));
    verify(ping2, atLeast(1)).isAlive(Mockito.<Server>any());
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertEquals("New Servers", actualChooseResult.getHost());
    assertEquals("New Servers:80", actualChooseResult.getHostPort());
    assertEquals("New Servers:80", actualChooseResult.getId());
    assertNull(actualChooseResult.getScheme());
    assertEquals(80, actualChooseResult.getPort());
    assertTrue(actualChooseResult.isAlive());
    assertTrue(actualChooseResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualChooseResult.getZone());
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

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb2 = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb2.addServer(new Server("42"));

    // Act
    Server actualChooseResult = responseTimeWeightedRule.choose(lb2, "Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertSame(newServer, actualChooseResult);
  }

  /**
   * Test {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ResponseTimeWeightedRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_whenBaseLoadBalancer_thenReturnNull() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();

    // Act and Assert
    assertNull(responseTimeWeightedRule.choose(new BaseLoadBalancer(), "Key"));
  }
}
