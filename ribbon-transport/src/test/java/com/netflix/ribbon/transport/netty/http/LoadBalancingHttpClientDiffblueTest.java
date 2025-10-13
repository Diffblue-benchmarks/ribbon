package com.netflix.ribbon.transport.netty.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RequestSpecificRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.SimpleVipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.ExecutionListener;
import com.netflix.ribbon.transport.netty.RibbonTransport;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient.Builder;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.utils.ScheduledThreadPoolExectuorWithDynamicSize;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.client.ClientMetricsEvent;
import io.reactivex.netty.metrics.MetricEventsListener;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientImpl;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import io.reactivex.netty.servo.http.HttpClientListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import rx.functions.Func1;
import rx.functions.Func2;

public class LoadBalancingHttpClientDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test Builder {@link Builder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Builder#build()}
   *   <li>{@link Builder#withBackoffStrategy(Func1)}
   *   <li>{@link Builder#withClientConfig(IClientConfig)}
   *   <li>{@link Builder#withExecutorListeners(List)}
   *   <li>{@link Builder#withLoadBalancer(ILoadBalancer)}
   *   <li>{@link Builder#withPipelineConfigurator(PipelineConfigurator)}
   *   <li>{@link Builder#withPoolCleanerScheduler(ScheduledExecutorService)}
   *   <li>{@link Builder#withResponseToErrorPolicy(Func2)}
   *   <li>{@link Builder#withRetryHandler(RetryHandler)}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Builder.<init>(Func1)",
    "LoadBalancingHttpClient Builder.build()",
    "Builder Builder.withBackoffStrategy(Func1)",
    "Builder Builder.withClientConfig(IClientConfig)",
    "Builder Builder.withExecutorListeners(List)",
    "Builder Builder.withLoadBalancer(ILoadBalancer)",
    "Builder Builder.withPipelineConfigurator(PipelineConfigurator)",
    "Builder Builder.withPoolCleanerScheduler(ScheduledExecutorService)",
    "Builder Builder.withResponseToErrorPolicy(Func2)",
    "Builder Builder.withRetryHandler(RetryHandler)"
  })
  public void testBuilderBuild() {
    // Arrange and Act
    Builder<Object, Object> actualBuilderResult = LoadBalancingHttpClient.builder();
    Builder<Object, Object> actualWithBackoffStrategyResult =
        actualBuilderResult.withBackoffStrategy(mock(Func1.class));
    Builder<Object, Object> actualWithClientConfigResult =
        actualWithBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());
    Builder<Object, Object> actualWithExecutorListenersResult =
        actualWithClientConfigResult.withExecutorListeners(new ArrayList<>());
    Builder<Object, Object> actualWithLoadBalancerResult =
        actualWithExecutorListenersResult.withLoadBalancer(new BaseLoadBalancer());
    Builder<Object, Object> actualWithPipelineConfiguratorResult =
        actualWithLoadBalancerResult.withPipelineConfigurator(mock(PipelineConfigurator.class));
    Builder<Object, Object> actualWithPoolCleanerSchedulerResult =
        actualWithPipelineConfiguratorResult.withPoolCleanerScheduler(
            RibbonTransport.poolCleanerScheduler);
    Builder<Object, Object> actualWithResponseToErrorPolicyResult =
        actualWithPoolCleanerSchedulerResult.withResponseToErrorPolicy(mock(Func2.class));
    Builder<Object, Object> actualWithRetryHandlerResult =
        actualWithResponseToErrorPolicyResult.withRetryHandler(
            new DefaultLoadBalancerRetryHandler());
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        actualWithRetryHandlerResult.build();

    // Assert
    assertTrue(
        actualWithBackoffStrategyResult.retryHandler instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(
        actualWithClientConfigResult.retryHandler instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(
        actualWithExecutorListenersResult.retryHandler instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(
        actualWithLoadBalancerResult.retryHandler instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(
        actualWithPipelineConfiguratorResult.retryHandler
            instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(
        actualWithPoolCleanerSchedulerResult.retryHandler
            instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(
        actualWithResponseToErrorPolicyResult.retryHandler
            instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(
        actualWithRetryHandlerResult.retryHandler instanceof DefaultLoadBalancerRetryHandler);
    LoadBalancerContext loadBalancerContext =
        actualLoadBalancingHttpClient.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getRetryHandler() instanceof RequestSpecificRetryHandler);
    assertTrue(actualWithBackoffStrategyResult.config instanceof DefaultClientConfigImpl);
    assertTrue(actualWithClientConfigResult.config instanceof DefaultClientConfigImpl);
    assertTrue(actualWithExecutorListenersResult.config instanceof DefaultClientConfigImpl);
    assertTrue(actualWithLoadBalancerResult.config instanceof DefaultClientConfigImpl);
    assertTrue(actualWithPipelineConfiguratorResult.config instanceof DefaultClientConfigImpl);
    assertTrue(actualWithPoolCleanerSchedulerResult.config instanceof DefaultClientConfigImpl);
    assertTrue(actualWithResponseToErrorPolicyResult.config instanceof DefaultClientConfigImpl);
    assertTrue(actualWithRetryHandlerResult.config instanceof DefaultClientConfigImpl);
    assertTrue(actualWithBackoffStrategyResult.lb instanceof BaseLoadBalancer);
    assertTrue(actualWithClientConfigResult.lb instanceof BaseLoadBalancer);
    assertTrue(actualWithExecutorListenersResult.lb instanceof BaseLoadBalancer);
    assertTrue(actualWithLoadBalancerResult.lb instanceof BaseLoadBalancer);
    assertTrue(actualWithPipelineConfiguratorResult.lb instanceof BaseLoadBalancer);
    assertTrue(actualWithPoolCleanerSchedulerResult.lb instanceof BaseLoadBalancer);
    assertTrue(actualWithResponseToErrorPolicyResult.lb instanceof BaseLoadBalancer);
    assertTrue(actualWithRetryHandlerResult.lb instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    HttpClientListener listener = actualLoadBalancingHttpClient.getListener();
    assertTrue(listener.getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(listener.getResponseReadTimes() instanceof BasicTimer);
    assertTrue(listener.getConnectionTimes() instanceof BasicTimer);
    assertTrue(listener.getFlushTimes() instanceof BasicTimer);
    assertTrue(listener.getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(listener.getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(listener.getWriteTimes() instanceof BasicTimer);
    assertTrue(
        actualWithBackoffStrategyResult.poolCleanerScheduler
            instanceof ScheduledThreadPoolExectuorWithDynamicSize);
    assertTrue(
        actualWithClientConfigResult.poolCleanerScheduler
            instanceof ScheduledThreadPoolExectuorWithDynamicSize);
    assertTrue(
        actualWithExecutorListenersResult.poolCleanerScheduler
            instanceof ScheduledThreadPoolExectuorWithDynamicSize);
    assertTrue(
        actualWithLoadBalancerResult.poolCleanerScheduler
            instanceof ScheduledThreadPoolExectuorWithDynamicSize);
    assertTrue(
        actualWithPipelineConfiguratorResult.poolCleanerScheduler
            instanceof ScheduledThreadPoolExectuorWithDynamicSize);
    assertTrue(
        actualWithPoolCleanerSchedulerResult.poolCleanerScheduler
            instanceof ScheduledThreadPoolExectuorWithDynamicSize);
    assertTrue(
        actualWithResponseToErrorPolicyResult.poolCleanerScheduler
            instanceof ScheduledThreadPoolExectuorWithDynamicSize);
    assertTrue(
        actualWithRetryHandlerResult.poolCleanerScheduler
            instanceof ScheduledThreadPoolExectuorWithDynamicSize);
    assertEquals("default", loadBalancerContext.getClientName());
    assertEquals(0, loadBalancerContext.getMaxAutoRetries());
    assertEquals(0L, listener.getFailedContentSource());
    assertEquals(0L, listener.getFailedResponses());
    assertEquals(0L, listener.getInflightRequests());
    assertEquals(0L, listener.getProcessedRequests());
    assertEquals(0L, listener.getRequestBacklog());
    assertEquals(0L, listener.getRequestWriteFailed());
    assertEquals(0L, listener.getBytesRead());
    assertEquals(0L, listener.getBytesWritten());
    assertEquals(0L, listener.getConnectionCount());
    assertEquals(0L, listener.getFailedConnectionClose());
    assertEquals(0L, listener.getFailedConnects());
    assertEquals(0L, listener.getFailedFlushes());
    assertEquals(0L, listener.getFailedPoolAcquires());
    assertEquals(0L, listener.getFailedPoolReleases());
    assertEquals(0L, listener.getFailedWrites());
    assertEquals(0L, listener.getLiveConnections());
    assertEquals(0L, listener.getPendingConnectionClose());
    assertEquals(0L, listener.getPendingConnects());
    assertEquals(0L, listener.getPendingFlushes());
    assertEquals(0L, listener.getPendingPoolAcquires());
    assertEquals(0L, listener.getPendingPoolReleases());
    assertEquals(0L, listener.getPendingWrites());
    assertEquals(0L, listener.getPoolAcquires());
    assertEquals(0L, listener.getPoolEvictions());
    assertEquals(0L, listener.getPoolReleases());
    assertEquals(0L, listener.getPoolReuse());
    assertEquals(1, loadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(200, actualLoadBalancingHttpClient.getMaxConcurrentRequests());
    assertEquals(7000, actualLoadBalancingHttpClient.getResponseTimeOut());
    assertFalse(loadBalancerContext.isOkToRetryOnAllOperations());
    assertTrue(actualWithBackoffStrategyResult.listeners.isEmpty());
    assertTrue(actualWithClientConfigResult.listeners.isEmpty());
    assertTrue(actualWithExecutorListenersResult.listeners.isEmpty());
    assertTrue(actualWithLoadBalancerResult.listeners.isEmpty());
    assertTrue(actualWithPipelineConfiguratorResult.listeners.isEmpty());
    assertTrue(actualWithPoolCleanerSchedulerResult.listeners.isEmpty());
    assertTrue(actualWithResponseToErrorPolicyResult.listeners.isEmpty());
    assertTrue(actualWithRetryHandlerResult.listeners.isEmpty());
    assertTrue(actualLoadBalancingHttpClient.getRxClients().isEmpty());
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient() {
    // Arrange
    ArrayList<ExecutionListener<HttpClientRequest<Object>, HttpClientResponse<Object>>> listeners =
        new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();

    TestExecutionListener<Object, Object> testExecutionListener =
        new TestExecutionListener<>(expectedRequest, requestConfig);
    listeners.add(testExecutionListener);

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    BaseLoadBalancer lb = new BaseLoadBalancer();
    builder.withLoadBalancer(lb);
    builder.withExecutorListeners(listeners);
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build());

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    assertSame(lb, actualLoadBalancingHttpClient.getLoadBalancerContext().getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient2() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(new ArrayList<>());
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(config);

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    IClientConfig clientConfig = actualLoadBalancingHttpClient.getClientConfig();
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(2, properties.size());
    assertEquals("42 Main St", properties.get("DeploymentContextBasedVipAddresses"));
    assertTrue(properties.containsKey("IgnoreUserTokenInConnectionPoolForSecureClient"));
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient3() {
    // Arrange
    ArrayList<ExecutionListener<HttpClientRequest<Object>, HttpClientResponse<Object>>> listeners =
        new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();

    TestExecutionListener<Object, Object> testExecutionListener =
        new TestExecutionListener<>(expectedRequest, requestConfig);
    listeners.add(testExecutionListener);

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    BaseLoadBalancer lb = new BaseLoadBalancer();
    builder.withLoadBalancer(lb);
    builder.withExecutorListeners(listeners);
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build());

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    assertSame(lb, actualLoadBalancingHttpClient.getLoadBalancerContext().getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient4() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(new ArrayList<>());
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(config);

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    IClientConfig clientConfig = actualLoadBalancingHttpClient.getClientConfig();
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(2, properties.size());
    assertEquals("42 Main St", properties.get("DeploymentContextBasedVipAddresses"));
    assertTrue(properties.containsKey("IgnoreUserTokenInConnectionPoolForSecureClient"));
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <ul>
   *   <li>Then return ClientConfig Properties size is three.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient_thenReturnClientConfigPropertiesSizeIsThree() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withClientAuthRequired(true);
    newBuilderResult.withSecure(true);
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(new ArrayList<>());
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(config);

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    IClientConfig clientConfig = actualLoadBalancingHttpClient.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(3, properties.size());
    assertTrue(properties.containsKey("IgnoreUserTokenInConnectionPoolForSecureClient"));
    assertEquals(Boolean.TRUE.toString(), properties.get("IsClientAuthRequired"));
    assertEquals(Boolean.TRUE.toString(), properties.get("IsSecure"));
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <ul>
   *   <li>Then return ClientConfig Properties size is three.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient_thenReturnClientConfigPropertiesSizeIsThree2() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withClientAuthRequired(true);
    newBuilderResult.withSecure(true);
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(new ArrayList<>());
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(config);

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    IClientConfig clientConfig = actualLoadBalancingHttpClient.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(3, properties.size());
    assertTrue(properties.containsKey("IgnoreUserTokenInConnectionPoolForSecureClient"));
    assertEquals(Boolean.TRUE.toString(), properties.get("IsClientAuthRequired"));
    assertEquals(Boolean.TRUE.toString(), properties.get("IsSecure"));
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <ul>
   *   <li>Then return ClientConfig Resolver is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient_thenReturnClientConfigResolverIsNull() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withSecure(true);
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(new ArrayList<>());
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(config);

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    IClientConfig clientConfig = actualLoadBalancingHttpClient.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(actualLoadBalancingHttpClient.getLoadBalancerContext().getLoadBalancer());
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(2, properties.size());
    assertTrue(properties.containsKey("IgnoreUserTokenInConnectionPoolForSecureClient"));
    assertEquals(Boolean.TRUE.toString(), properties.get("IsSecure"));
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <ul>
   *   <li>Then return ClientConfig Resolver is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient_thenReturnClientConfigResolverIsNull2() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withSecure(true);
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(new ArrayList<>());
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(config);

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    IClientConfig clientConfig = actualLoadBalancingHttpClient.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(actualLoadBalancingHttpClient.getLoadBalancerContext().getLoadBalancer());
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(2, properties.size());
    assertTrue(properties.containsKey("IgnoreUserTokenInConnectionPoolForSecureClient"));
    assertEquals(Boolean.TRUE.toString(), properties.get("IsSecure"));
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <ul>
   *   <li>Then return MaxConcurrentRequests is two hundred.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient_thenReturnMaxConcurrentRequestsIsTwoHundred() {
    // Arrange
    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(new ArrayList<>());
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build());

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    assertEquals(200, actualLoadBalancingHttpClient.getMaxConcurrentRequests());
    assertEquals(7000, actualLoadBalancingHttpClient.getResponseTimeOut());
    assertTrue(actualLoadBalancingHttpClient.getRxClients().isEmpty());
    assertSame(builder.config, actualLoadBalancingHttpClient.getClientConfig());
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <ul>
   *   <li>Then return MaxConcurrentRequests is two hundred.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient_thenReturnMaxConcurrentRequestsIsTwoHundred2() {
    // Arrange
    ArrayList<ExecutionListener<HttpClientRequest<Object>, HttpClientResponse<Object>>> listeners =
        new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();

    TestExecutionListener<Object, Object> testExecutionListener =
        new TestExecutionListener<>(expectedRequest, requestConfig);
    listeners.add(testExecutionListener);

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(listeners);
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build());

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    assertEquals(200, actualLoadBalancingHttpClient.getMaxConcurrentRequests());
    assertEquals(7000, actualLoadBalancingHttpClient.getResponseTimeOut());
    assertTrue(actualLoadBalancingHttpClient.getRxClients().isEmpty());
    assertSame(builder.config, actualLoadBalancingHttpClient.getClientConfig());
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <ul>
   *   <li>Then return MaxConcurrentRequests is two hundred.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient_thenReturnMaxConcurrentRequestsIsTwoHundred3() {
    // Arrange
    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(new ArrayList<>());
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build());

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    assertEquals(200, actualLoadBalancingHttpClient.getMaxConcurrentRequests());
    assertEquals(7000, actualLoadBalancingHttpClient.getResponseTimeOut());
    assertTrue(actualLoadBalancingHttpClient.getRxClients().isEmpty());
    assertSame(builder.config, actualLoadBalancingHttpClient.getClientConfig());
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <ul>
   *   <li>Then return MaxConcurrentRequests is two hundred.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient_thenReturnMaxConcurrentRequestsIsTwoHundred4() {
    // Arrange
    ArrayList<ExecutionListener<HttpClientRequest<Object>, HttpClientResponse<Object>>> listeners =
        new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();

    TestExecutionListener<Object, Object> testExecutionListener =
        new TestExecutionListener<>(expectedRequest, requestConfig);
    listeners.add(testExecutionListener);

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(listeners);
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build());

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    assertEquals(200, actualLoadBalancingHttpClient.getMaxConcurrentRequests());
    assertEquals(7000, actualLoadBalancingHttpClient.getResponseTimeOut());
    assertTrue(actualLoadBalancingHttpClient.getRxClients().isEmpty());
    assertSame(builder.config, actualLoadBalancingHttpClient.getClientConfig());
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <ul>
   *   <li>Then return MaxConcurrentRequests is two hundred.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient_thenReturnMaxConcurrentRequestsIsTwoHundred5() {
    // Arrange
    ArrayList<ExecutionListener<HttpClientRequest<Object>, HttpClientResponse<Object>>> listeners =
        new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();

    TestExecutionListener<Object, Object> testExecutionListener =
        new TestExecutionListener<>(expectedRequest, requestConfig);
    listeners.add(testExecutionListener);
    HttpClientRequest<ByteBuf> expectedRequest2 = mock(HttpClientRequest.class);
    IClientConfig requestConfig2 =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();

    TestExecutionListener<Object, Object> testExecutionListener2 =
        new TestExecutionListener<>(expectedRequest2, requestConfig2);
    listeners.add(testExecutionListener2);

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withExecutorListeners(listeners);
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build());

    // Act
    LoadBalancingHttpClient<Object, Object> actualLoadBalancingHttpClient =
        new LoadBalancingHttpClient<>(builder);

    // Assert
    assertEquals(200, actualLoadBalancingHttpClient.getMaxConcurrentRequests());
    assertEquals(7000, actualLoadBalancingHttpClient.getResponseTimeOut());
    assertTrue(actualLoadBalancingHttpClient.getRxClients().isEmpty());
    assertSame(builder.config, actualLoadBalancingHttpClient.getClientConfig());
  }

  /**
   * Test {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#LoadBalancingHttpClient(Builder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.<init>(Builder)"})
  public void testNewLoadBalancingHttpClient_thenThrowIllegalArgumentException() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withKeyStorePassword("42");
    newBuilderResult.withKeyStore("42");
    newBuilderResult.withSecure(true);
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builder = LoadBalancingHttpClient.builder();
    builder.withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler);
    builder.withExecutorListeners(new ArrayList<>());
    builder.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    builder.withClientConfig(config);

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new LoadBalancingHttpClient<>(builder);
  }

  /**
   * Test {@link LoadBalancingHttpClient#getServerStats(Server)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#getServerStats(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServerStats LoadBalancingHttpClient.getServerStats(Server)"})
  public void testGetServerStats_whenNull_thenReturnNull() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act and Assert
    assertNull(
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .getServerStats(null));
  }

  /**
   * Test {@link LoadBalancingHttpClient#getServerStats(Server)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#getServerStats(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServerStats LoadBalancingHttpClient.getServerStats(Server)"})
  public void testGetServerStats_whenNull_thenReturnNull2() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act and Assert
    assertNull(
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .getServerStats(null));
  }

  /**
   * Test {@link LoadBalancingHttpClient#createRxClient(Server)}.
   *
   * <ul>
   *   <li>Given newBuilder withFollowRedirects {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#createRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpClient LoadBalancingHttpClient.createRxClient(Server)"})
  public void testCreateRxClient_givenNewBuilderWithFollowRedirectsFalse() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withFollowRedirects(false);
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withClientConfigResult =
        builderResult.withBackoffStrategy(mock(Func1.class)).withClientConfig(config);

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(null)
            .withResponseToErrorPolicy(mock(Func2.class));
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    HttpClient<Object, Object> actualCreateRxClientResult =
        loadBalancingHttpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof HttpClientImpl);
    assertEquals("HttpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Test {@link LoadBalancingHttpClient#createRxClient(Server)}.
   *
   * <ul>
   *   <li>Given newBuilder withFollowRedirects {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#createRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpClient LoadBalancingHttpClient.createRxClient(Server)"})
  public void testCreateRxClient_givenNewBuilderWithFollowRedirectsFalse2() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withFollowRedirects(false);
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withClientConfigResult =
        builderResult.withBackoffStrategy(mock(Func1.class)).withClientConfig(config);

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(null)
            .withResponseToErrorPolicy(mock(Func2.class));
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    HttpClient<Object, Object> actualCreateRxClientResult =
        loadBalancingHttpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof HttpClientImpl);
    assertEquals("HttpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Test {@link LoadBalancingHttpClient#createRxClient(Server)}.
   *
   * <ul>
   *   <li>Given newBuilder withFollowRedirects {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#createRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpClient LoadBalancingHttpClient.createRxClient(Server)"})
  public void testCreateRxClient_givenNewBuilderWithFollowRedirectsTrue() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withFollowRedirects(true);
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withClientConfigResult =
        builderResult.withBackoffStrategy(mock(Func1.class)).withClientConfig(config);

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(null)
            .withResponseToErrorPolicy(mock(Func2.class));
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    HttpClient<Object, Object> actualCreateRxClientResult =
        loadBalancingHttpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof HttpClientImpl);
    assertEquals("HttpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Test {@link LoadBalancingHttpClient#createRxClient(Server)}.
   *
   * <ul>
   *   <li>Given newBuilder withFollowRedirects {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#createRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpClient LoadBalancingHttpClient.createRxClient(Server)"})
  public void testCreateRxClient_givenNewBuilderWithFollowRedirectsTrue2() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();
    newBuilderResult.withFollowRedirects(true);
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withClientConfigResult =
        builderResult.withBackoffStrategy(mock(Func1.class)).withClientConfig(config);

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(null)
            .withResponseToErrorPolicy(mock(Func2.class));
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    HttpClient<Object, Object> actualCreateRxClientResult =
        loadBalancingHttpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof HttpClientImpl);
    assertEquals("HttpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Test {@link LoadBalancingHttpClient#createRxClient(Server)}.
   *
   * <ul>
   *   <li>Then return {@link HttpClientImpl}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#createRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpClient LoadBalancingHttpClient.createRxClient(Server)"})
  public void testCreateRxClient_thenReturnHttpClientImpl() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(null)
            .withResponseToErrorPolicy(mock(Func2.class));
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    HttpClient<Object, Object> actualCreateRxClientResult =
        loadBalancingHttpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof HttpClientImpl);
    assertEquals("HttpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Test {@link LoadBalancingHttpClient#createRxClient(Server)}.
   *
   * <ul>
   *   <li>Then return {@link HttpClientImpl}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#createRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpClient LoadBalancingHttpClient.createRxClient(Server)"})
  public void testCreateRxClient_thenReturnHttpClientImpl2() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(null)
            .withResponseToErrorPolicy(mock(Func2.class));
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    HttpClient<Object, Object> actualCreateRxClientResult =
        loadBalancingHttpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof HttpClientImpl);
    assertEquals("HttpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Test {@link LoadBalancingHttpClient#getListener()}.
   *
   * <ul>
   *   <li>Then RequestWriteTimes return {@link BasicTimer}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#getListener()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpClientListener LoadBalancingHttpClient.getListener()"})
  public void testGetListener_thenRequestWriteTimesReturnBasicTimer() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act
    HttpClientListener actualListener =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .getListener();

    // Assert
    assertTrue(actualListener.getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(actualListener.getResponseReadTimes() instanceof BasicTimer);
    assertTrue(actualListener.getConnectionTimes() instanceof BasicTimer);
    assertTrue(actualListener.getFlushTimes() instanceof BasicTimer);
    assertTrue(actualListener.getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(actualListener.getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(actualListener.getWriteTimes() instanceof BasicTimer);
    assertEquals(0L, actualListener.getFailedContentSource());
    assertEquals(0L, actualListener.getFailedResponses());
    assertEquals(0L, actualListener.getInflightRequests());
    assertEquals(0L, actualListener.getProcessedRequests());
    assertEquals(0L, actualListener.getRequestBacklog());
    assertEquals(0L, actualListener.getRequestWriteFailed());
    assertEquals(0L, actualListener.getBytesRead());
    assertEquals(0L, actualListener.getBytesWritten());
    assertEquals(0L, actualListener.getConnectionCount());
    assertEquals(0L, actualListener.getFailedConnectionClose());
    assertEquals(0L, actualListener.getFailedConnects());
    assertEquals(0L, actualListener.getFailedFlushes());
    assertEquals(0L, actualListener.getFailedPoolAcquires());
    assertEquals(0L, actualListener.getFailedPoolReleases());
    assertEquals(0L, actualListener.getFailedWrites());
    assertEquals(0L, actualListener.getLiveConnections());
    assertEquals(0L, actualListener.getPendingConnectionClose());
    assertEquals(0L, actualListener.getPendingConnects());
    assertEquals(0L, actualListener.getPendingFlushes());
    assertEquals(0L, actualListener.getPendingPoolAcquires());
    assertEquals(0L, actualListener.getPendingPoolReleases());
    assertEquals(0L, actualListener.getPendingWrites());
    assertEquals(0L, actualListener.getPoolAcquires());
    assertEquals(0L, actualListener.getPoolEvictions());
    assertEquals(0L, actualListener.getPoolReleases());
    assertEquals(0L, actualListener.getPoolReuse());
  }

  /**
   * Test {@link LoadBalancingHttpClient#getListener()}.
   *
   * <ul>
   *   <li>Then RequestWriteTimes return {@link BasicTimer}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#getListener()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpClientListener LoadBalancingHttpClient.getListener()"})
  public void testGetListener_thenRequestWriteTimesReturnBasicTimer2() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act
    HttpClientListener actualListener =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .getListener();

    // Assert
    assertTrue(actualListener.getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(actualListener.getResponseReadTimes() instanceof BasicTimer);
    assertTrue(actualListener.getConnectionTimes() instanceof BasicTimer);
    assertTrue(actualListener.getFlushTimes() instanceof BasicTimer);
    assertTrue(actualListener.getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(actualListener.getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(actualListener.getWriteTimes() instanceof BasicTimer);
    assertEquals(0L, actualListener.getFailedContentSource());
    assertEquals(0L, actualListener.getFailedResponses());
    assertEquals(0L, actualListener.getInflightRequests());
    assertEquals(0L, actualListener.getProcessedRequests());
    assertEquals(0L, actualListener.getRequestBacklog());
    assertEquals(0L, actualListener.getRequestWriteFailed());
    assertEquals(0L, actualListener.getBytesRead());
    assertEquals(0L, actualListener.getBytesWritten());
    assertEquals(0L, actualListener.getConnectionCount());
    assertEquals(0L, actualListener.getFailedConnectionClose());
    assertEquals(0L, actualListener.getFailedConnects());
    assertEquals(0L, actualListener.getFailedFlushes());
    assertEquals(0L, actualListener.getFailedPoolAcquires());
    assertEquals(0L, actualListener.getFailedPoolReleases());
    assertEquals(0L, actualListener.getFailedWrites());
    assertEquals(0L, actualListener.getLiveConnections());
    assertEquals(0L, actualListener.getPendingConnectionClose());
    assertEquals(0L, actualListener.getPendingConnects());
    assertEquals(0L, actualListener.getPendingFlushes());
    assertEquals(0L, actualListener.getPendingPoolAcquires());
    assertEquals(0L, actualListener.getPendingPoolReleases());
    assertEquals(0L, actualListener.getPendingWrites());
    assertEquals(0L, actualListener.getPoolAcquires());
    assertEquals(0L, actualListener.getPoolEvictions());
    assertEquals(0L, actualListener.getPoolReleases());
    assertEquals(0L, actualListener.getPoolReuse());
  }

  /**
   * Test {@link LoadBalancingHttpClient#getRxClients()}.
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#getRxClients()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map LoadBalancingHttpClient.getRxClients()"})
  public void testGetRxClients() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act and Assert
    assertTrue(
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .getRxClients()
            .isEmpty());
  }

  /**
   * Test {@link LoadBalancingHttpClient#getRxClients()}.
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#getRxClients()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map LoadBalancingHttpClient.getRxClients()"})
  public void testGetRxClients2() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act and Assert
    assertTrue(
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .getRxClients()
            .isEmpty());
  }

  /**
   * Test {@link LoadBalancingHttpClient#createListener(String)}.
   *
   * <ul>
   *   <li>Then RequestWriteTimes return {@link BasicTimer}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#createListener(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"MetricEventsListener LoadBalancingHttpClient.createListener(String)"})
  public void testCreateListener_thenRequestWriteTimesReturnBasicTimer() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act
    MetricEventsListener<? extends ClientMetricsEvent<?>> actualCreateListenerResult =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .createListener("https://example.org/example");

    // Assert
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getRequestWriteTimes()
            instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getResponseReadTimes()
            instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getConnectionTimes()
            instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getPoolAcquireTimes()
            instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getPoolReleaseTimes()
            instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getWriteTimes() instanceof BasicTimer);
    assertTrue(actualCreateListenerResult instanceof HttpClientListener);
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedContentSource());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedResponses());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getInflightRequests());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getProcessedRequests());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getRequestBacklog());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getRequestWriteFailed());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getBytesRead());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getBytesWritten());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getConnectionCount());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedConnectionClose());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedConnects());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedFlushes());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedPoolAcquires());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedPoolReleases());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedWrites());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getLiveConnections());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingConnectionClose());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingConnects());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingFlushes());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingPoolAcquires());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingPoolReleases());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingWrites());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPoolAcquires());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPoolEvictions());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPoolReleases());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPoolReuse());
  }

  /**
   * Test {@link LoadBalancingHttpClient#createListener(String)}.
   *
   * <ul>
   *   <li>Then RequestWriteTimes return {@link BasicTimer}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#createListener(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"MetricEventsListener LoadBalancingHttpClient.createListener(String)"})
  public void testCreateListener_thenRequestWriteTimesReturnBasicTimer2() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act
    MetricEventsListener<? extends ClientMetricsEvent<?>> actualCreateListenerResult =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .createListener("https://example.org/example");

    // Assert
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getRequestWriteTimes()
            instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getResponseReadTimes()
            instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getConnectionTimes()
            instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getPoolAcquireTimes()
            instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getPoolReleaseTimes()
            instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) actualCreateListenerResult).getWriteTimes() instanceof BasicTimer);
    assertTrue(actualCreateListenerResult instanceof HttpClientListener);
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedContentSource());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedResponses());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getInflightRequests());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getProcessedRequests());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getRequestBacklog());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getRequestWriteFailed());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getBytesRead());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getBytesWritten());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getConnectionCount());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedConnectionClose());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedConnects());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedFlushes());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedPoolAcquires());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedPoolReleases());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getFailedWrites());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getLiveConnections());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingConnectionClose());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingConnects());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingFlushes());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingPoolAcquires());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingPoolReleases());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPendingWrites());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPoolAcquires());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPoolEvictions());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPoolReleases());
    assertEquals(0L, ((HttpClientListener) actualCreateListenerResult).getPoolReuse());
  }
}
