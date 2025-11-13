package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.loadbalancer.Server;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient.Builder;
import com.netflix.ribbon.transport.netty.http.SSEClient;
import com.netflix.ribbon.transport.netty.udp.LoadBalancingUdpClient;
import io.netty.channel.local.LocalEventLoopGroup;
import io.reactivex.netty.metrics.MetricEventsListener;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientImpl;
import io.reactivex.netty.protocol.text.sse.ServerSentEvent;
import io.reactivex.netty.servo.http.HttpClientListener;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subscriptions.BooleanSubscription;

public class LoadBalancingRxClientDiffblueTest {
  /**
   * Test {@link LoadBalancingRxClient#getClientConfig()}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getClientConfig()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig LoadBalancingRxClient.getClientConfig()"})
  public void testGetClientConfig() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    IClientConfig actualClientConfig = loadBalancingHttpClient.getClientConfig();

    // Assert
    assertSame(loadBalancingHttpClient.clientConfig, actualClientConfig);
  }

  /**
   * Test {@link LoadBalancingRxClient#getResponseTimeOut()}.
   *
   * <ul>
   *   <li>Then return {@code 7000}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getResponseTimeOut()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancingRxClient.getResponseTimeOut()"})
  public void testGetResponseTimeOut_thenReturn7000() {
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
    assertEquals(
        7000,
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .getResponseTimeOut());
  }

  /**
   * Test {@link LoadBalancingRxClient#getMaxConcurrentRequests()}.
   *
   * <ul>
   *   <li>Then return minus one.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getMaxConcurrentRequests()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancingRxClient.getMaxConcurrentRequests()"})
  public void testGetMaxConcurrentRequests_thenReturnMinusOne() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals(-1, loadBalancingUdpClient.getMaxConcurrentRequests());
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"
  })
  public void testGetProperty() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);

    // Act and Assert
    assertEquals(
        "Default Value",
        loadBalancingHttpClient.getProperty(
            key,
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build(),
            "Default Value"));
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   *
   * <ul>
   *   <li>When ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"
  })
  public void testGetProperty_whenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty =
        loadBalancingHttpClient.getProperty(
            key,
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"),
            "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   *
   * <ul>
   *   <li>When ClientConfigWithDefaultValues empty string is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"
  })
  public void testGetProperty_whenClientConfigWithDefaultValuesEmptyStringIsNameSpace() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty =
        loadBalancingHttpClient.getProperty(
            key,
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space"),
            "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   *
   * <ul>
   *   <li>When ClientConfigWithDefaultValues {@code null} is {@code Name Space}.
   *   <li>Then calls {@link IClientConfigKey#key()}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"
  })
  public void testGetProperty_whenClientConfigWithDefaultValuesNullIsNameSpace_thenCallsKey() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty =
        loadBalancingHttpClient.getProperty(
            key,
            DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space"),
            "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   *
   * <ul>
   *   <li>When {@link IClientConfigKey}.
   *   <li>Then return {@code Default Value}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"
  })
  public void testGetProperty_whenIClientConfigKey_thenReturnDefaultValue() {
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
    assertEquals(
        "Default Value",
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .getProperty(mock(IClientConfigKey.class), null, "Default Value"));
  }

  /**
   * Test {@link LoadBalancingRxClient#getResourceForOptionalProperty(IClientConfigKey)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * LoadBalancingRxClient#getResourceForOptionalProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.net.URL LoadBalancingRxClient.getResourceForOptionalProperty(IClientConfigKey)"
  })
  public void testGetResourceForOptionalProperty_thenReturnNull() {
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
            .getResourceForOptionalProperty(mock(IClientConfigKey.class)));
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "io.reactivex.netty.client.RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"
  })
  public void testGetOrCreateRxClient() {
    // Arrange
    Builder<Object, ServerSentEvent> sseClientBuilderResult = SSEClient.sseClientBuilder();

    Builder<Object, ServerSentEvent> withBackoffStrategyResult =
        sseClientBuilderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, ServerSentEvent> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, ServerSentEvent> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, ServerSentEvent> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));
    LoadBalancingHttpClient<Object, ServerSentEvent> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    HttpClient<Object, ServerSentEvent> actualOrCreateRxClient =
        loadBalancingHttpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof HttpClientImpl);
    assertEquals("HttpClient--no-name", actualOrCreateRxClient.name());
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "io.reactivex.netty.client.RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"
  })
  public void testGetOrCreateRxClient2() {
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
    HttpClient<Object, Object> actualOrCreateRxClient =
        loadBalancingHttpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof HttpClientImpl);
    assertEquals("HttpClient--no-name", actualOrCreateRxClient.name());
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "io.reactivex.netty.client.RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"
  })
  public void testGetOrCreateRxClient3() {
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

    Builder<Object, Object> withPipelineConfiguratorResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class));

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withPipelineConfiguratorResult
            .withPoolCleanerScheduler(new LocalEventLoopGroup())
            .withResponseToErrorPolicy(mock(Func2.class));
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    HttpClient<Object, Object> actualOrCreateRxClient =
        loadBalancingHttpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    ScheduledExecutorService scheduledExecutorService =
        loadBalancingHttpClient.poolCleanerScheduler;
    assertTrue(scheduledExecutorService instanceof LocalEventLoopGroup);
    assertTrue(actualOrCreateRxClient instanceof HttpClientImpl);
    assertEquals("HttpClient--no-name", actualOrCreateRxClient.name());
    assertTrue(((LocalEventLoopGroup) scheduledExecutorService).iterator().hasNext());
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "io.reactivex.netty.client.RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"
  })
  public void testGetOrCreateRxClient4() {
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
    HttpClient<Object, Object> actualOrCreateRxClient =
        loadBalancingHttpClient.getOrCreateRxClient(new Server(null));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof HttpClientImpl);
    assertEquals("HttpClient--no-name", actualOrCreateRxClient.name());
  }

  /**
   * Test {@link LoadBalancingRxClient#removeClient(Server)}.
   *
   * <ul>
   *   <li>When {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#removeClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "io.reactivex.netty.client.RxClient LoadBalancingRxClient.removeClient(Server)"
  })
  public void testRemoveClient_whenServerWithIdIs42_thenReturnNull() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    HttpClient<Object, Object> actualRemoveClientResult =
        loadBalancingHttpClient.removeClient(new Server("42"));

    // Assert
    assertNull(actualRemoveClientResult);
  }

  /**
   * Test {@link LoadBalancingRxClient#removeClient(Server)}.
   *
   * <ul>
   *   <li>When {@link Server#Server(String)} with id is {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#removeClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "io.reactivex.netty.client.RxClient LoadBalancingRxClient.removeClient(Server)"
  })
  public void testRemoveClient_whenServerWithIdIsNull_thenReturnNull() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    HttpClient<Object, Object> actualRemoveClientResult =
        loadBalancingHttpClient.removeClient(new Server(null));

    // Assert
    assertNull(actualRemoveClientResult);
  }

  /**
   * Test {@link LoadBalancingRxClient#name()}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#name()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String LoadBalancingRxClient.name()"})
  public void testName_thenReturnEmptyString() {
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
    assertEquals(
        "",
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .name());
  }

  /**
   * Test {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Subscription LoadBalancingRxClient.subscribe(MetricEventsListener)"})
  public void testSubscribe() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    Subscription actualSubscribeResult =
        loadBalancingHttpClient.subscribe(new DynamicPropertyBasedPoolStrategy(3, "Property Name"));

    // Assert
    assertTrue(actualSubscribeResult instanceof BooleanSubscription);
    assertFalse(actualSubscribeResult.isUnsubscribed());
  }

  /**
   * Test {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}.
   *
   * <ul>
   *   <li>When newHttpListener empty string.
   *   <li>Then return {@link BooleanSubscription}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Subscription LoadBalancingRxClient.subscribe(MetricEventsListener)"})
  public void testSubscribe_whenNewHttpListenerEmptyString_thenReturnBooleanSubscription() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    Subscription actualSubscribeResult =
        loadBalancingHttpClient.subscribe(HttpClientListener.newHttpListener(""));

    // Assert
    assertTrue(actualSubscribeResult instanceof BooleanSubscription);
    assertFalse(actualSubscribeResult.isUnsubscribed());
  }

  /**
   * Test {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}.
   *
   * <ul>
   *   <li>When newHttpListener {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Subscription LoadBalancingRxClient.subscribe(MetricEventsListener)"})
  public void testSubscribe_whenNewHttpListenerHttpsExampleOrgExample() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    Subscription actualSubscribeResult =
        loadBalancingHttpClient.subscribe(
            HttpClientListener.newHttpListener("https://example.org/example"));

    // Assert
    assertTrue(actualSubscribeResult instanceof BooleanSubscription);
    assertFalse(actualSubscribeResult.isUnsubscribed());
  }

  /**
   * Test {@link LoadBalancingRxClient#getLoadBalancerContext()}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getLoadBalancerContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LoadBalancerContext LoadBalancingRxClient.getLoadBalancerContext()"})
  public void testGetLoadBalancerContext() {
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
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        loadBalancingHttpClient.getLoadBalancerContext();

    // Assert
    assertSame(loadBalancingHttpClient.lbContext, actualLoadBalancerContext);
  }
}
