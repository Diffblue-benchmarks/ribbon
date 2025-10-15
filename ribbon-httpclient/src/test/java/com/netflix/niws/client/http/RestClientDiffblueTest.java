package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.ClientException;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.PrimeConnections;
import com.netflix.client.RequestSpecificRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.client.http.HttpRequest;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.PollingServerListUpdater;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListFilter;
import com.netflix.loadbalancer.ServerStatusChangeListener;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import com.netflix.util.Pair;
import com.sun.jersey.api.client.Client;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class RestClientDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link RestClient#RestClient()}.
   *
   * <p>Method under test: {@link RestClient#RestClient()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>()"})
  public void testNewRestClient() {
    // Arrange and Act
    RestClient actualRestClient = new RestClient();

    // Assert
    assertTrue(actualRestClient.getRetryHandler() instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(actualRestClient.getExecuteTracer() instanceof BasicTimer);
    assertEquals("default", actualRestClient.getClientName());
    assertNull(actualRestClient.getLoadBalancer());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(actualRestClient.bFollowRedirects);
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
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act and Assert
    assertSame(lb, new RestClient(lb).getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient3() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    ILoadBalancer loadBalancer = actualRestClient.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient5() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.setLoadBalancerStats(new LoadBalancerStats());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient6() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient7() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    PrimeConnections primeConnections = new PrimeConnections("default", 3, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient8() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServers(new ArrayList<>());
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    ILoadBalancer loadBalancer = actualRestClient.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient9() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.setPingInterval(42);
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient10() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setLoadBalancerStats(new LoadBalancerStats());
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient11() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setPingInterval(42);
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient12() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    PrimeConnections primeConnections = new PrimeConnections("default", 3, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient13() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(false).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient14() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.addServers(new ArrayList<>());
    lb.setEnablePrimingConnections(true);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient15() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(false).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    PrimeConnections primeConnections = new PrimeConnections("default", 3, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient16() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    PrimeConnections primeConnections = new PrimeConnections("default", 6, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} chooseServer
   *       {@code Key}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenKey_whenDynamicServerListLoadBalancerChooseServerKey() {
    // Arrange
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.chooseServer("Key");
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
   *   <li>Given {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>Then return LoadBalancer Filter is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenLoadBalancerStats_thenReturnLoadBalancerFilterIsNull() {
    // Arrange
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    LoadBalancerStats lbStats = new LoadBalancerStats();
    lb.setLoadBalancerStats(lbStats);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act and Assert
    ILoadBalancer loadBalancer = new RestClient(lb).getLoadBalancer();
    assertTrue(loadBalancer instanceof DynamicServerListLoadBalancer);
    assertNull(((DynamicServerListLoadBalancer<Server>) loadBalancer).getFilter());
    LoadBalancerStats loadBalancerStats =
        ((DynamicServerListLoadBalancer<Server>) loadBalancer).getLoadBalancerStats();
    assertNull(loadBalancerStats.getName());
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertSame(lbStats, loadBalancerStats);
  }

  /**
   * Test {@link RestClient#RestClient(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@code Or Default}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(IClientConfig)"})
  public void testNewRestClient_givenOrDefault_thenThrowIllegalArgumentException() {
    // Arrange
    IClientConfig ncc = mock(IClientConfig.class);
    when(ncc.getOrDefault(Mockito.<IClientConfigKey<Object>>any())).thenReturn("Or Default");
    when(ncc.getClientName()).thenThrow(new IllegalArgumentException());
    when(ncc.getOrDefault(Mockito.<IClientConfigKey<Integer>>any()))
        .thenThrow(new IllegalArgumentException());

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new RestClient(ncc);
    verify(ncc).getClientName();
    verify(ncc).getOrDefault(Mockito.<IClientConfigKey<Object>>any());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@code Or Default}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewRestClient_givenOrDefault_thenThrowIllegalArgumentException2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    IClientConfig ncc = mock(IClientConfig.class);
    when(ncc.getOrDefault(Mockito.<IClientConfigKey<Object>>any())).thenReturn("Or Default");
    when(ncc.getClientName()).thenThrow(new IllegalArgumentException());
    when(ncc.getOrDefault(Mockito.<IClientConfigKey<Integer>>any()))
        .thenThrow(new IllegalArgumentException());

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new RestClient(lb, ncc);
    verify(ncc).getClientName();
    verify(ncc).getOrDefault(Mockito.<IClientConfigKey<Object>>any());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <ul>
   *   <li>Given three.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient_givenThree() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.setMaxTotalPingTime(3);
    lb.addServers(new ArrayList<>());
    lb.setEnablePrimingConnections(true);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <ul>
   *   <li>Given three.
   *   <li>Then return LoadBalancer MaxTotalPingTime is three.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient_givenThree_thenReturnLoadBalancerMaxTotalPingTimeIsThree() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.setMaxTotalPingTime(3);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    ILoadBalancer loadBalancer = actualRestClient.getLoadBalancer();
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    assertEquals(3, ((BaseLoadBalancer) loadBalancer).getMaxTotalPingTime());
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, rule.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient_givenTrue() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.setEnablePrimingConnections(true);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <ul>
   *   <li>Then LoadBalancer Rule return {@link AvailabilityFilteringRule}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient_thenLoadBalancerRuleReturnAvailabilityFilteringRule() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    ILoadBalancer loadBalancer = actualRestClient.getLoadBalancer();
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, rule.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <ul>
   *   <li>Then LoadBalancer Rule return {@link AvailabilityFilteringRule}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient_thenLoadBalancerRuleReturnAvailabilityFilteringRule2() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    ILoadBalancer loadBalancer = actualRestClient.getLoadBalancer();
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, rule.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <ul>
   *   <li>Then LoadBalancer Rule return {@link AvailabilityFilteringRule}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient_thenLoadBalancerRuleReturnAvailabilityFilteringRule3() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    ILoadBalancer loadBalancer = actualRestClient.getLoadBalancer();
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, rule.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <ul>
   *   <li>Then LoadBalancer Rule return {@link AvailabilityFilteringRule}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient_thenLoadBalancerRuleReturnAvailabilityFilteringRule4() {
    // Arrange
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    ILoadBalancer loadBalancer = actualRestClient.getLoadBalancer();
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(lb, rule.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <ul>
   *   <li>Then LoadBalancer Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient_thenLoadBalancerRuleReturnRoundRobinRule() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    ILoadBalancer loadBalancer = actualRestClient.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Then return LoadBalancer AllServers first is {@link Server#Server(String)} with id is
   *       {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_thenReturnLoadBalancerAllServersFirstIsServerWithIdIs42() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalStateException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    Server newServer = new Server("42");
    lb.addServer(newServer);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(listener);
    lb.setPing(mock(IPing.class));

    // Act and Assert
    ILoadBalancer loadBalancer = new RestClient(lb).getLoadBalancer();
    assertTrue(loadBalancer instanceof DynamicServerListLoadBalancer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertSame(newServer, allServers.get(0));
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Then return LoadBalancer AllServers first is {@link Server#Server(String)} with id is
   *       {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_thenReturnLoadBalancerAllServersFirstIsServerWithIdIs422() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalStateException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalStateException())
        .when(listener2)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServerStatusChangeListener(listener2);
    Server newServer = new Server("42");
    lb.addServer(newServer);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(listener);
    lb.setPing(mock(IPing.class));

    // Act and Assert
    ILoadBalancer loadBalancer = new RestClient(lb).getLoadBalancer();
    assertTrue(loadBalancer instanceof DynamicServerListLoadBalancer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertSame(newServer, allServers.get(0));
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Then return LoadBalancer is {@link
   *       DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_thenReturnLoadBalancerIsDynamicServerListLoadBalancer() {
    // Arrange
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
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
   *   <li>Then return LoadBalancer is {@link
   *       DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_thenReturnLoadBalancerIsDynamicServerListLoadBalancer2() {
    // Arrange
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
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
   *   <li>Then return LoadBalancer is {@link
   *       DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_thenReturnLoadBalancerIsDynamicServerListLoadBalancer3() {
    // Arrange
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.setFilter(mock(ServerListFilter.class));
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
   *   <li>Then return LoadBalancer NumberMissedCycles is zero.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_thenReturnLoadBalancerNumberMissedCyclesIsZero() {
    // Arrange
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    PollingServerListUpdater serverListUpdater = new PollingServerListUpdater();
    lb.setServerListUpdater(serverListUpdater);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act and Assert
    ILoadBalancer loadBalancer = new RestClient(lb).getLoadBalancer();
    assertTrue(loadBalancer instanceof DynamicServerListLoadBalancer);
    assertEquals(0, ((DynamicServerListLoadBalancer<Server>) loadBalancer).getNumberMissedCycles());
    assertEquals(2, ((DynamicServerListLoadBalancer<Server>) loadBalancer).getCoreThreads());
    assertSame(
        serverListUpdater,
        ((DynamicServerListLoadBalancer<Server>) loadBalancer).getServerListUpdater());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} addServers {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient_whenBaseLoadBalancerAddServersArrayList() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb, (Client) null);

    // Assert
    ILoadBalancer loadBalancer = actualRestClient.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof HttpClientLoadBalancerErrorHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertEquals(
        7, ((HttpClientLoadBalancerErrorHandler) retryHandler).getRetriableExceptions().size());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} Ping is {@link IPing}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_whenBaseLoadBalancerPingIsIPing() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
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
   * Test {@link RestClient#RestClient(ILoadBalancer, Client)}.
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return LoadBalancer Ping is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer, Client)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer, Client)"})
  public void testNewRestClient_whenBaseLoadBalancer_thenReturnLoadBalancerPingIsNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Client jerseyClient = new Client();

    // Act
    RestClient actualRestClient = new RestClient(lb, jerseyClient);

    // Assert
    ILoadBalancer loadBalancer = actualRestClient.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertNull(((BaseLoadBalancer) loadBalancer).getPing());
    assertSame(jerseyClient, actualRestClient.getJerseyClient());
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
   * Test {@link RestClient#deriveHostAndPortFromVipAddress(String)}.
   *
   * <ul>
   *   <li>Then return first is {@code example.org}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair RestClient.deriveHostAndPortFromVipAddress(String)"})
  public void testDeriveHostAndPortFromVipAddress_thenReturnFirstIsExampleOrg()
      throws ClientException, URISyntaxException {
    // Arrange and Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult =
        new RestClient().deriveHostAndPortFromVipAddress("https://example.org/example");

    // Assert
    assertEquals("example.org", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(443, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Test {@link RestClient#deriveHostAndPortFromVipAddress(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return first is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair RestClient.deriveHostAndPortFromVipAddress(String)"})
  public void testDeriveHostAndPortFromVipAddress_when42_thenReturnFirstIs42()
      throws ClientException, URISyntaxException {
    // Arrange and Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult =
        new RestClient().deriveHostAndPortFromVipAddress("42");

    // Assert
    assertEquals("42", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(80, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Test {@link RestClient#deriveHostAndPortFromVipAddress(String)}.
   *
   * <ul>
   *   <li>When {@code http}.
   *   <li>Then return first is {@code http}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair RestClient.deriveHostAndPortFromVipAddress(String)"})
  public void testDeriveHostAndPortFromVipAddress_whenHttp_thenReturnFirstIsHttp()
      throws ClientException, URISyntaxException {
    // Arrange and Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult =
        new RestClient().deriveHostAndPortFromVipAddress("http");

    // Assert
    assertEquals("http", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(80, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Test {@link RestClient#getRequestSpecificRetryHandler(HttpRequest, IClientConfig)} with {@code
   * HttpRequest}, {@code IClientConfig}.
   *
   * <p>Method under test: {@link RestClient#getRequestSpecificRetryHandler(HttpRequest,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RequestSpecificRetryHandler RestClient.getRequestSpecificRetryHandler(HttpRequest, IClientConfig)"
  })
  public void testGetRequestSpecificRetryHandlerWithHttpRequestIClientConfig() {
    // Arrange
    RestClient restClient = new RestClient();

    HttpRequest request = mock(HttpRequest.class);
    when(request.isRetriable()).thenReturn(false);

    // Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler =
        restClient.getRequestSpecificRetryHandler(
            request, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(request).isRetriable();
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
  }

  /**
   * Test {@link RestClient#getRequestSpecificRetryHandler(HttpRequest, IClientConfig)} with {@code
   * HttpRequest}, {@code IClientConfig}.
   *
   * <p>Method under test: {@link RestClient#getRequestSpecificRetryHandler(HttpRequest,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RequestSpecificRetryHandler RestClient.getRequestSpecificRetryHandler(HttpRequest, IClientConfig)"
  })
  public void testGetRequestSpecificRetryHandlerWithHttpRequestIClientConfig2() {
    // Arrange
    RestClient restClient = new RestClient();

    HttpRequest request = mock(HttpRequest.class);
    when(request.isRetriable()).thenReturn(false);

    // Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler =
        restClient.getRequestSpecificRetryHandler(
            request,
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"));

    // Assert
    verify(request).isRetriable();
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
  }

  /**
   * Test {@link RestClient#getRequestSpecificRetryHandler(HttpRequest, IClientConfig)} with {@code
   * HttpRequest}, {@code IClientConfig}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#getRequestSpecificRetryHandler(HttpRequest,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RequestSpecificRetryHandler RestClient.getRequestSpecificRetryHandler(HttpRequest, IClientConfig)"
  })
  public void testGetRequestSpecificRetryHandlerWithHttpRequestIClientConfig_whenNull() {
    // Arrange
    RestClient restClient = new RestClient();

    HttpRequest request = mock(HttpRequest.class);
    when(request.isRetriable()).thenReturn(false);

    // Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler =
        restClient.getRequestSpecificRetryHandler(request, null);

    // Assert
    verify(request).isRetriable();
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
  }
}
