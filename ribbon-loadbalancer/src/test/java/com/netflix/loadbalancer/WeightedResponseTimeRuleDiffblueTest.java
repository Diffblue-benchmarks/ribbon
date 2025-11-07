package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.WeightedResponseTimeRule.DynamicServerWeightTask;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class WeightedResponseTimeRuleDiffblueTest {
  /**
   * Test DynamicServerWeightTask {@link DynamicServerWeightTask#run()}.
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code false}.</li>
   *   <li>Then calls {@link IPing#isAlive(Server)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerWeightTask#run()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerWeightTask.run()"})
  public void testDynamicServerWeightTaskRun_givenIPingIsAliveReturnFalse_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(false);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServer(new Server("42"));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act
    (weightedResponseTimeRule.new DynamicServerWeightTask()).run();

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Test DynamicServerWeightTask {@link DynamicServerWeightTask#run()}.
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code true}.</li>
   *   <li>Then calls {@link IPing#isAlive(Server)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerWeightTask#run()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerWeightTask.run()"})
  public void testDynamicServerWeightTaskRun_givenIPingIsAliveReturnTrue_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServer(new Server("42/"));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act
    (weightedResponseTimeRule.new DynamicServerWeightTask()).run();

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Test {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.<init>()"})
  public void testNewWeightedResponseTimeRule() {
    // Arrange and Act
    WeightedResponseTimeRule actualWeightedResponseTimeRule = new WeightedResponseTimeRule();

    // Assert
    assertEquals("unknown", actualWeightedResponseTimeRule.name);
    assertNull(actualWeightedResponseTimeRule.getLoadBalancer());
    assertNull(actualWeightedResponseTimeRule.serverWeightTimer);
    assertFalse(actualWeightedResponseTimeRule.serverWeightAssignmentInProgress.get());
    assertTrue(actualWeightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}.
   * <ul>
   *   <li>Then {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} LoadBalancer is {@link NoOpLoadBalancer} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_thenWeightedResponseTimeRuleLoadBalancerIsNoOpLoadBalancer() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    NoOpLoadBalancer lb = new NoOpLoadBalancer();

    // Act
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Assert
    assertEquals("unknown", weightedResponseTimeRule.name);
    assertSame(lb, weightedResponseTimeRule.getLoadBalancer());
  }

  /**
   * Test {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}.
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_whenBaseLoadBalancer() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", weightedResponseTimeRule.name);
    assertSame(lb, weightedResponseTimeRule.getLoadBalancer());
  }

  /**
   * Test {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}.
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_whenBaseLoadBalancerAddServerServerWithIdIs42() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("42"));

    // Act
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", weightedResponseTimeRule.name);
    assertSame(lb, weightedResponseTimeRule.getLoadBalancer());
  }

  /**
   * Test {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} {@link WeightedResponseTimeRule#name} is {@code unknown}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_whenNull_thenWeightedResponseTimeRuleNameIsUnknown() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();

    // Act
    weightedResponseTimeRule.setLoadBalancer(null);

    // Assert that nothing has changed
    assertEquals("unknown", weightedResponseTimeRule.name);
  }

  /**
   * Test {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String, int)} with host is {@code localhost} and port is {@code 8080}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenArrayListAddServerWithHostIsLocalhostAndPortIs8080() {
    // Arrange
    DummyServerStats dummyServerStats = mock(DummyServerStats.class);
    when(dummyServerStats.getResponseTimeAvg()).thenReturn(10.0d);
    LoadBalancerStats loadBalancerStats = mock(LoadBalancerStats.class);
    when(loadBalancerStats.getSingleServerStat(Mockito.<Server>any())).thenReturn(dummyServerStats);

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("localhost", 8080));
    serverList.add(new Server("42"));
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(serverList);
    when(lb.getLoadBalancerStats()).thenReturn(loadBalancerStats);
    when(lb.getName()).thenReturn("Name");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);
    Server newServer = mock(Server.class);
    when(newServer.getId()).thenReturn("42");
    doNothing().when(newServer).setAlive(anyBoolean());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.setLoadBalancerStats(new LoadBalancerStats());
    lb2.addServer(newServer);

    // Act
    weightedResponseTimeRule.initialize(lb2);

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
    verify(loadBalancerStats, atLeast(1)).getSingleServerStat(Mockito.<Server>any());
    verify(newServer).getId();
    verify(newServer).setAlive(eq(true));
    verify(dummyServerStats, atLeast(1)).getResponseTimeAvg();
  }

  /**
   * Test {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer} {@link BaseLoadBalancer#getLoadBalancerStats()} return {@link LoadBalancerStats#LoadBalancerStats()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenBaseLoadBalancerGetLoadBalancerStatsReturnLoadBalancerStats() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(serverList);
    when(lb.getLoadBalancerStats()).thenReturn(new LoadBalancerStats());
    when(lb.getName()).thenReturn("Name");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act
    weightedResponseTimeRule.initialize(new BaseLoadBalancer());

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
  }

  /**
   * Test {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer} {@link BaseLoadBalancer#getLoadBalancerStats()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenBaseLoadBalancerGetLoadBalancerStatsReturnNull() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getLoadBalancerStats()).thenReturn(null);
    when(lb.getName()).thenReturn("Name");
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act
    weightedResponseTimeRule.initialize(new BaseLoadBalancer());

    // Assert
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
  }

  /**
   * Test {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}.
   * <ul>
   *   <li>Given {@link LoadBalancerStats} {@link LoadBalancerStats#getSingleServerStat(Server)} return {@link ServerStats#ServerStats()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenLoadBalancerStatsGetSingleServerStatReturnServerStats() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    Server server = new Server("42");

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(server);
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(serverList);
    LoadBalancerStats loadBalancerStats = mock(LoadBalancerStats.class);
    when(loadBalancerStats.getSingleServerStat(Mockito.<Server>any())).thenReturn(new ServerStats());
    when(lb.getLoadBalancerStats()).thenReturn(loadBalancerStats);
    when(lb.getName()).thenReturn("Name");
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act
    weightedResponseTimeRule.initialize(new BaseLoadBalancer());

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
    verify(loadBalancerStats, atLeast(1)).getSingleServerStat(isA(Server.class));
  }

  /**
   * Test {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}.
   * <ul>
   *   <li>Given {@link Server} {@link Server#getId()} return {@code 42}.</li>
   *   <li>Then calls {@link Server#getId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenServerGetIdReturn42_thenCallsGetId() {
    // Arrange
    DummyServerStats dummyServerStats = mock(DummyServerStats.class);
    when(dummyServerStats.getResponseTimeAvg()).thenReturn(10.0d);
    LoadBalancerStats loadBalancerStats = mock(LoadBalancerStats.class);
    when(loadBalancerStats.getSingleServerStat(Mockito.<Server>any())).thenReturn(dummyServerStats);

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(serverList);
    when(lb.getLoadBalancerStats()).thenReturn(loadBalancerStats);
    when(lb.getName()).thenReturn("Name");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);
    Server newServer = mock(Server.class);
    when(newServer.getId()).thenReturn("42");
    doNothing().when(newServer).setAlive(anyBoolean());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.setLoadBalancerStats(new LoadBalancerStats());
    lb2.addServer(newServer);

    // Act
    weightedResponseTimeRule.initialize(lb2);

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
    verify(loadBalancerStats, atLeast(1)).getSingleServerStat(isA(Server.class));
    verify(newServer).getId();
    verify(newServer).setAlive(eq(true));
    verify(dummyServerStats, atLeast(1)).getResponseTimeAvg();
  }

  /**
   * Test {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}.
   * <ul>
   *   <li>Then calls {@link ServerListChangeListener#serverListChanged(List, List)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_thenCallsServerListChanged() {
    // Arrange
    DummyServerStats dummyServerStats = mock(DummyServerStats.class);
    when(dummyServerStats.getResponseTimeAvg()).thenReturn(10.0d);
    LoadBalancerStats loadBalancerStats = mock(LoadBalancerStats.class);
    when(loadBalancerStats.getSingleServerStat(Mockito.<Server>any())).thenReturn(dummyServerStats);

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(serverList);
    when(lb.getLoadBalancerStats()).thenReturn(loadBalancerStats);
    when(lb.getName()).thenReturn("Name");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);
    Server newServer = mock(Server.class);
    when(newServer.getId()).thenReturn("42");
    doNothing().when(newServer).setAlive(anyBoolean());
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(listener);
    lb2.setLoadBalancerStats(new LoadBalancerStats());
    lb2.addServer(newServer);

    // Act
    weightedResponseTimeRule.initialize(lb2);

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
    verify(loadBalancerStats, atLeast(1)).getSingleServerStat(isA(Server.class));
    verify(newServer).getId();
    verify(newServer).setAlive(eq(true));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(dummyServerStats, atLeast(1)).getResponseTimeAvg();
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initialize(new NoOpLoadBalancer());

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("LoadBalancer_ChooseServer");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("NFLoadBalancer-PingTimer-");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights5() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.chooseServer("LoadBalancer_ChooseServer");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights6() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.chooseServer("LoadBalancer_ChooseServer");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights7() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[]{1});
    lb.chooseServer("NFLoadBalancer-PingTimer-");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights8() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.addServers(new Object[]{42});
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.chooseServer("LoadBalancer_ChooseServer");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights9() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.addServers(new Object[]{42});
    lb.addServers(new Object[]{42});
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.chooseServer("LoadBalancer_ChooseServer");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} chooseServer {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerChooseServer42() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.chooseServer("42");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} chooseServer {@code ,}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerChooseServerComma() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[]{1});
    lb.chooseServer(",");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} chooseServer {@code Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerChooseServerKey() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} chooseServer {@link Integer#MIN_VALUE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerChooseServerMin_value() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[]{1});
    lb.chooseServer(Integer.MIN_VALUE);

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} EnablePrimingConnections is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerEnablePrimingConnectionsIsTrue() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.chooseServer("LoadBalancer_ChooseServer");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Ping is {@link IPing}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerPingIsIPing() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPing(mock(IPing.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.chooseServer("LoadBalancer_ChooseServer");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Ping is {@link IPing}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerPingIsIPing2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPing(mock(IPing.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.chooseServer(Integer.MIN_VALUE);

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenWeightedResponseTimeRule() {
    // Arrange, Act and Assert
    assertTrue((new WeightedResponseTimeRule()).getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} chooseServer zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenZoneAwareLoadBalancerChooseServerZero() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.addServers(new Object[]{42});
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.chooseServer(0);

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenBaseLoadBalancerAddServerNull_thenReturnNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(null);

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServer(new Server("42"));

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(lb2, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} EnablePrimingConnections is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenBaseLoadBalancerEnablePrimingConnectionsIsTrue() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.addServer(new Server("42"));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServer(new Server("42"));

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(lb2, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} LoadBalancer is {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenWeightedResponseTimeRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new BaseLoadBalancer());

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("42"));

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(lb, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenWeightedResponseTimeRule_thenReturnNull() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("42"));

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(lb, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.</li>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenWeightedResponseTimeRule_whenBaseLoadBalancer() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(new BaseLoadBalancer(), "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenWeightedResponseTimeRule_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new WeightedResponseTimeRule()).choose(null, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_thenReturnServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServer(new Server("42"));

    // Act and Assert
    assertSame(newServer, weightedResponseTimeRule.choose(lb2, "Key"));
  }
}
