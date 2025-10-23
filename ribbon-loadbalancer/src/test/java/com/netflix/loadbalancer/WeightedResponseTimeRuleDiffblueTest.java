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
import com.netflix.loadbalancer.WeightedResponseTimeRule.DynamicServerWeightTask;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class WeightedResponseTimeRuleDiffblueTest {
  /**
   * Test DynamicServerWeightTask {@link DynamicServerWeightTask#run()}.
   *
   * <ul>
   *   <li>Then calls {@link WeightedResponseTimeRule#getLoadBalancer()}.
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
    WeightedResponseTimeRule weightedResponseTimeRule = mock(WeightedResponseTimeRule.class);
    when(weightedResponseTimeRule.getLoadBalancer()).thenReturn(new BaseLoadBalancer());

    // Act
    weightedResponseTimeRule.new DynamicServerWeightTask().run();

    // Assert
    verify(weightedResponseTimeRule).getLoadBalancer();
  }

  /**
   * Test {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} {@link
   *       WeightedResponseTimeRule#name} is {@code unknown}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link BaseLoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenArrayListAddNull_thenCallsGetAllServers() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(null);

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
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>Then calls {@link BaseLoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenArrayListAddServerWithIdIs42_thenCallsGetAllServers() {
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
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer} {@link BaseLoadBalancer#getLoadBalancerStats()} return
   *       {@link LoadBalancerStats#LoadBalancerStats()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenBaseLoadBalancerGetLoadBalancerStatsReturnLoadBalancerStats() {
    // Arrange
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(new ArrayList<>());
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
   *
   * <ul>
   *   <li>Then calls {@link LoadBalancerStats#getSingleServerStat(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
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

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
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
   * Test {@link WeightedResponseTimeRule#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer} {@link BaseLoadBalancer#getName()} return {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.shutdown()"})
  public void testShutdown_givenBaseLoadBalancerGetNameReturnFoo() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initialize(null);

    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getLoadBalancerStats()).thenReturn(null);
    when(lb.getName()).thenReturn("foo");
    doNothing().when(lb).addServerListChangeListener(Mockito.<ServerListChangeListener>any());
    lb.addServerListChangeListener(mock(ServerListChangeListener.class));
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act
    weightedResponseTimeRule.shutdown();

    // Assert
    verify(lb).addServerListChangeListener(isA(ServerListChangeListener.class));
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
  }

  /**
   * Test {@link WeightedResponseTimeRule#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer} {@link BaseLoadBalancer#getName()} return {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.shutdown()"})
  public void testShutdown_givenBaseLoadBalancerGetNameReturnName() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initialize(null);

    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getLoadBalancerStats()).thenReturn(null);
    when(lb.getName()).thenReturn("Name");
    doNothing().when(lb).addServerListChangeListener(Mockito.<ServerListChangeListener>any());
    lb.addServerListChangeListener(mock(ServerListChangeListener.class));
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act
    weightedResponseTimeRule.shutdown();

    // Assert
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
    verify(lb).addServerListChangeListener(isA(ServerListChangeListener.class));
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new BaseLoadBalancer());
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new NoOpLoadBalancer());
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new ZoneAwareLoadBalancer<>());
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights5() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb2 = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights6() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights7() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights8() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServers(new ArrayList<>());
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights9() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb2 = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb2.addServers(new ArrayList<>());
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights10() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));
    lb2.addServers(new ArrayList<>());
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights11() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServers(new ArrayList<>());
    lb2.addServers(new ArrayList<>());
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServers {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerAddServersArrayList() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServers array of {@link Object} with
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerAddServersArrayOfObjectWithOne() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServers(new Object[] {1});
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServers array of {@link Object} with
   *       two.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerAddServersArrayOfObjectWithTwo() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServers(new Object[] {2});
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} chooseServer {@code Key}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerChooseServerKey() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.chooseServer("Key");
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} MaxTotalPingTime is three.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerMaxTotalPingTimeIsThree() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.setMaxTotalPingTime(3);
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} PingInterval is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerPingIntervalIsFortyTwo() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.setPingInterval(42);
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Ping is {@link IPing}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerPingIsIPing() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.setPing(mock(IPing.class));
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} Rule is {@link
   *       AvailabilityFilteringRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenBaseLoadBalancerRuleIsAvailabilityFilteringRule() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.setRule(new AvailabilityFilteringRule());
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb2);
    weightedResponseTimeRule.initialize(lb);

    // Act and Assert
    assertTrue(weightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#getAccumulatedWeights()}.
   *
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenWeightedResponseTimeRule() {
    // Arrange, Act and Assert
    assertTrue(new WeightedResponseTimeRule().getAccumulatedWeights().isEmpty());
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} EnablePrimingConnections is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenWeightedResponseTimeRule_whenBaseLoadBalancer() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(new BaseLoadBalancer(), "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenWeightedResponseTimeRule_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new WeightedResponseTimeRule().choose(null, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
