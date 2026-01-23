package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.PrimeConnections;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.ClientConfigEnabledRoundRobinRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListChangeListener;
import com.netflix.loadbalancer.ServerStatusChangeListener;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import com.sun.jersey.api.client.Client;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class RestClientDiffblueTest {
  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient2() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient3() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setLoadBalancerStats(new LoadBalancerStats());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient4() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    PrimeConnections primeConnections = new PrimeConnections("default", 3, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient5() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    PrimeConnections primeConnections = new PrimeConnections("default", 2, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient6() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    PrimeConnections primeConnections = new PrimeConnections("default", 0, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient7() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setRule(new AvailabilityFilteringRule());
    PrimeConnections primeConnections = new PrimeConnections("default", 2, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient8() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    LoadBalancerStats lbStats = new LoadBalancerStats();
    lb.setLoadBalancerStats(lbStats);
    PrimeConnections primeConnections = new PrimeConnections("default", 0, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act and Assert
    ILoadBalancer loadBalancer = new RestClient(lb).getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    PrimeConnections primeConnections2 = ((BaseLoadBalancer) loadBalancer).getPrimeConnections();
    assertNull(primeConnections2.getEndStats());
    LoadBalancerStats loadBalancerStats = ((BaseLoadBalancer) loadBalancer).getLoadBalancerStats();
    assertNull(loadBalancerStats.getName());
    assertSame(primeConnections, primeConnections2);
    assertSame(lbStats, loadBalancerStats);
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient9() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new BestAvailableRule());
    PrimeConnections primeConnections = new PrimeConnections("default", 0, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient10() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    PrimeConnections primeConnections = new PrimeConnections("default", 0, 2L, "42");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient11() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setRule(new AvailabilityFilteringRule());
    PrimeConnections primeConnections = new PrimeConnections("default", 2, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient12() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setRule(new AvailabilityFilteringRule());
    PrimeConnections primeConnections = new PrimeConnections("default", 1, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient13() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    LoadBalancerStats lbStats = new LoadBalancerStats();
    lb.setLoadBalancerStats(lbStats);
    PrimeConnections primeConnections = new PrimeConnections("default", 1, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act and Assert
    ILoadBalancer loadBalancer = new RestClient(lb).getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    PrimeConnections primeConnections2 = ((BaseLoadBalancer) loadBalancer).getPrimeConnections();
    assertNull(primeConnections2.getEndStats());
    LoadBalancerStats loadBalancerStats = ((BaseLoadBalancer) loadBalancer).getLoadBalancerStats();
    assertNull(loadBalancerStats.getName());
    assertSame(primeConnections, primeConnections2);
    assertSame(lbStats, loadBalancerStats);
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given {@link ClientConfigEnabledRoundRobinRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenClientConfigEnabledRoundRobinRule() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setRule(new ClientConfigEnabledRoundRobinRule());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given forty-two.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenFortyTwo() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setPingInterval(42);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given one.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenOne() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setPingInterval(1);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given {@link ServerListChangeListener}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenServerListChangeListener() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServerListChangeListener(mock(ServerListChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given zero.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenZero() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setPingInterval(0);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given zero.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenZero2() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setRule(new AvailabilityFilteringRule());
    lb.setPingInterval(0);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} LoadBalancerStats is {@link
   *       LoadBalancerStats#LoadBalancerStats()}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_whenBaseLoadBalancerLoadBalancerStatsIsLoadBalancerStats() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setLoadBalancerStats(new LoadBalancerStats());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} Rule is {@link
   *       AvailabilityFilteringRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_whenBaseLoadBalancerRuleIsAvailabilityFilteringRule() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setRule(new AvailabilityFilteringRule());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return LoadBalancer is {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_whenBaseLoadBalancer_thenReturnLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#getResource(String)}.
   *
   * <p>Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"URL RestClient.getResource(String)"})
  public void testGetResource() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("https://example.org/example");

    // Assert
    String expectedToStringResult =
        String.join(
            "",
            "file:",
            Paths.get(System.getProperty("user.dir"), "https").toString(),
            ":/example.org/example");
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Test {@link RestClient#getResource(String)}.
   *
   * <ul>
   *   <li>When {@code %s.nfhttpclient.connIdleEvictTimeMilliSeconds}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"URL RestClient.getResource(String)"})
  public void testGetResource_whenSNfhttpclientConnIdleEvictTimeMilliSeconds_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(RestClient.getResource("%s.nfhttpclient.connIdleEvictTimeMilliSeconds"));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link RestClient#setJerseyClient(Client)}
   *   <li>{@link RestClient#getJerseyClient()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Client RestClient.getJerseyClient()",
    "void RestClient.setJerseyClient(Client)"
  })
  public void testGettersAndSetters() {
    // Arrange
    RestClient restClient = new RestClient();
    Client c = new Client();

    // Act
    restClient.setJerseyClient(c);

    // Assert
    assertSame(c, restClient.getJerseyClient());
  }

  /**
   * Test {@link RestClient#getDefaultPortFromScheme(String)}.
   *
   * <ul>
   *   <li>When {@code http}.
   *   <li>Then return eighty.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int RestClient.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttp_thenReturnEighty() {
    // Arrange, Act and Assert
    assertEquals(80, new RestClient().getDefaultPortFromScheme("http"));
  }

  /**
   * Test {@link RestClient#getDefaultPortFromScheme(String)}.
   *
   * <ul>
   *   <li>When {@code https://example.org/example}.
   *   <li>Then return eighty.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int RestClient.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttpsExampleOrgExample_thenReturnEighty() {
    // Arrange, Act and Assert
    assertEquals(80, new RestClient().getDefaultPortFromScheme("https://example.org/example"));
  }

  /**
   * Test {@link RestClient#getDefaultPortFromScheme(String)}.
   *
   * <ul>
   *   <li>When {@code https}.
   *   <li>Then return four hundred forty-three.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int RestClient.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttps_thenReturnFourHundredFortyThree() {
    // Arrange, Act and Assert
    assertEquals(443, new RestClient().getDefaultPortFromScheme("https"));
  }

  /**
   * Test {@link RestClient#isRetriableException(Throwable)}.
   *
   * <p>Method under test: {@link RestClient#isRetriableException(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RestClient.isRetriableException(Throwable)"})
  public void testIsRetriableException() {
    // Arrange
    RestClient restClient = new RestClient();

    // Act and Assert
    assertFalse(restClient.isRetriableException(new Throwable()));
  }

  /**
   * Test {@link RestClient#isCircuitBreakerException(Throwable)}.
   *
   * <p>Method under test: {@link RestClient#isCircuitBreakerException(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RestClient.isCircuitBreakerException(Throwable)"})
  public void testIsCircuitBreakerException() {
    // Arrange
    RestClient restClient = new RestClient();

    // Act and Assert
    assertFalse(restClient.isCircuitBreakerException(new Throwable()));
  }

  /**
   * Test {@link RestClient#shutdown()}.
   *
   * <p>Method under test: {@link RestClient#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.shutdown()"})
  public void testShutdown() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener4 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener4)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerListChangeListener(listener4);
    lb.addServerListChangeListener(listener3);
    lb.addServerListChangeListener(listener2);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerListChangeListener(listener);
    lb.setPing(mock(IPing.class));
    lb.addServer(new Server("42"));

    // Act
    new RestClient(lb).shutdown();

    // Assert
    verify(listener4).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
  }

  /**
   * Test {@link RestClient#shutdown()}.
   *
   * <ul>
   *   <li>Then calls {@link ServerStatusChangeListener#serverStatusChanged(Collection)}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.shutdown()"})
  public void testShutdown_thenCallsServerStatusChanged() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener3)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener4 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener4)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerListChangeListener listener5 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener5)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener6 = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener6)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener7 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener7).serverStatusChanged(Mockito.<Collection<Server>>any());
    IPing ping2 = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    lb.addServerStatusChangeListener(listener7);
    lb.addServerStatusChangeListener(listener6);
    lb.addServerListChangeListener(listener5);
    lb.addServerListChangeListener(listener4);
    lb.addServerListChangeListener(listener3);
    lb.addServerStatusChangeListener(listener2);
    lb.addServerListChangeListener(listener);
    lb.setPing(ping);
    lb.addServer(new Server("42"));

    // Act
    new RestClient(lb).shutdown();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener5).serverListChanged(isA(List.class), isA(List.class));
    verify(listener4).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3).serverListChanged(isA(List.class), isA(List.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener7).serverStatusChanged(isA(Collection.class));
    verify(listener6).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
  }
}
