package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
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
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_givenServerWithIdIs42() {
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
   *
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} initialize {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_givenWeightedResponseTimeRuleInitializeBaseLoadBalancer() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initialize(new BaseLoadBalancer());
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
   *   <li>Then {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} LoadBalancer is {@link
   *       NoOpLoadBalancer} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then calls {@link BaseLoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenArrayListAddNull_whenBaseLoadBalancer_thenCallsGetAllServers() {
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
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server}.
   *   <li>Then calls {@link BaseLoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenArrayListAddServer_thenCallsGetAllServers() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(mock(Server.class));

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
   *   <li>Given {@link BaseLoadBalancer} {@link BaseLoadBalancer#getName()} return {@code foo}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_givenBaseLoadBalancerGetNameReturnFoo_whenNull() {
    // Arrange
    LoadBalancerStats loadBalancerStats = mock(LoadBalancerStats.class);
    when(loadBalancerStats.getSingleServerStat(Mockito.<Server>any()))
        .thenReturn(new ServerStats());

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));

    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(serverList);
    when(lb.getLoadBalancerStats()).thenReturn(loadBalancerStats);
    when(lb.getName()).thenReturn("foo");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act
    weightedResponseTimeRule.initialize(null);

    // Assert
    verify(lb, atLeast(1)).getAllServers();
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
    verify(loadBalancerStats, atLeast(1)).getSingleServerStat(isA(Server.class));
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
   * Test {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then calls {@link LoadBalancerStats#getSingleServerStat(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#initialize(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.initialize(ILoadBalancer)"})
  public void testInitialize_whenNull_thenCallsGetSingleServerStat() {
    // Arrange
    LoadBalancerStats loadBalancerStats = mock(LoadBalancerStats.class);
    when(loadBalancerStats.getSingleServerStat(Mockito.<Server>any()))
        .thenReturn(new ServerStats());

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));

    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    when(lb.getAllServers()).thenReturn(serverList);
    when(lb.getLoadBalancerStats()).thenReturn(loadBalancerStats);
    when(lb.getName()).thenReturn("Name");

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act
    weightedResponseTimeRule.initialize(null);

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
   *   <li>Then calls {@link DynamicServerListLoadBalancer#getLoadBalancerStats()}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void WeightedResponseTimeRule.shutdown()"})
  public void testShutdown_thenCallsGetLoadBalancerStats() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initialize(new DynamicServerListLoadBalancer<>());

    DynamicServerListLoadBalancer<Server> lb = mock(DynamicServerListLoadBalancer.class);
    when(lb.getLoadBalancerStats()).thenReturn(null);
    when(lb.getName()).thenReturn("Name");
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act
    weightedResponseTimeRule.shutdown();

    // Assert
    verify(lb, atLeast(1)).getLoadBalancerStats();
    verify(lb).getName();
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
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initialize(new BaseLoadBalancer());

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
    lb.addServers(new Object[] {-1});

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
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
    lb.addServers(new Object[] {"LoadBalancer_ChooseServer"});

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
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
    lb.addServers(new Object[] {"LoadBalancer_ChooseServer"});

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
  public void testGetAccumulatedWeights5() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[] {"LoadBalancer_ChooseServer"});

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    weightedResponseTimeRule.setLoadBalancer(new BaseLoadBalancer(config));
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
    lb.addServers(new Object[] {"LoadBalancer_ChooseServer"});

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
  public void testGetAccumulatedWeights7() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[] {"LoadBalancer_ChooseServer"});
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb2 = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));
    lb2.addServer(null);

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
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} addServer
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List WeightedResponseTimeRule.getAccumulatedWeights()"})
  public void testGetAccumulatedWeights_givenDynamicServerListLoadBalancerAddServerNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[] {"LoadBalancer_ChooseServer"});

    DynamicServerListLoadBalancer<Server> lb2 = new DynamicServerListLoadBalancer<>();
    lb2.addServerListChangeListener(mock(ServerListChangeListener.class));
    lb2.addServer(null);

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
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initWithNiwsConfig(
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build());
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(null, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initWithNiwsConfig(
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build());
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(null, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initWithNiwsConfig(
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build());
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(null, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServers {@link
   *       ArrayList#ArrayList()}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenBaseLoadBalancerAddServersArrayList_whenNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(null, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServers {@link
   *       ArrayList#ArrayList()}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenBaseLoadBalancerAddServersArrayList_whenNull2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(null, "Key"));
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
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenServerWithIdIs42() {
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
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenWeightedResponseTimeRule() {
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
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} LoadBalancer is {@link
   *       NoOpLoadBalancer} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenWeightedResponseTimeRuleLoadBalancerIsNoOpLoadBalancer() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new NoOpLoadBalancer());

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(null, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} LoadBalancer is {@link
   *       NoOpLoadBalancer} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenWeightedResponseTimeRuleLoadBalancerIsNoOpLoadBalancer2() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new NoOpLoadBalancer());

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(null, "Key"));
  }

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
   *   <li>Given {@link WeightedResponseTimeRule#WeightedResponseTimeRule()} LoadBalancer is {@link
   *       NoOpLoadBalancer} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server WeightedResponseTimeRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenWeightedResponseTimeRuleLoadBalancerIsNoOpLoadBalancer3() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new NoOpLoadBalancer());

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(null, "Key"));
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

  /**
   * Test {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code
   * key}.
   *
   * <ul>
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
  public void testChooseWithLbKey_whenNull_thenReturnNull() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(null, "Key"));
  }
}
