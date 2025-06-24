package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.RetryHandler;
import com.netflix.client.SimpleVipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.loadbalancer.NoOpLoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListChangeListener;
import com.netflix.loadbalancer.ServerStatusChangeListener;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.loadbalancer.reactive.ExecutionListener;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient;
import com.netflix.ribbon.transport.netty.http.NettyHttpLoadBalancerErrorHandler;
import com.netflix.ribbon.transport.netty.http.SSEClient;
import com.netflix.ribbon.transport.netty.http.TestExecutionListener;
import com.netflix.servo.monitor.BasicTimer;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.client.ClientMetricsEvent;
import io.reactivex.netty.metrics.MetricEventsListener;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import io.reactivex.netty.protocol.text.sse.ServerSentEvent;
import io.reactivex.netty.servo.http.HttpClientListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RibbonTransportDiffblueTest {
  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer)} with {@code loadBalancer}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer)"})
  public void testNewHttpClientWithLoadBalancer() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer)} with {@code loadBalancer}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer)"})
  public void testNewHttpClientWithLoadBalancer2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithLoadBalancerConfig() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithLoadBalancerConfig2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithLoadBalancerConfig3() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " "));

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithLoadBalancerConfig4() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Mr John Smith", " ");

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config);

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("Mr John Smith", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("Mr John Smith", loadBalancerContext.getClientName());
    assertSame(config, clientConfig);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithLoadBalancerConfig5() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    loadBalancer.addServers(new ArrayList<>());

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " "));

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler2() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("Dr Jane Doe", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, clientConfig);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler3() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        null, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertEquals("default", clientConfig.getClientName());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler4() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setEnablePrimingConnections(true);

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        null, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertEquals("default", clientConfig.getClientName());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler5() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setEnablePrimingConnections(true);
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(config, actualNewHttpClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler6() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setEnablePrimingConnections(true);
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(2, properties.size());
    assertEquals("42 Main St", properties.get("DeploymentContextBasedVipAddresses"));
    String expectedString = Boolean.TRUE.toString();
    assertEquals(expectedString, properties.get("IgnoreUserTokenInConnectionPoolForSecureClient"));
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler7() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setEnablePrimingConnections(true);
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withSecure(true);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(config, actualNewHttpClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler8() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setEnablePrimingConnections(true);
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withClientAuthRequired(true);
    newBuilderResult.withSecure(true);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(config, actualNewHttpClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler9() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setEnablePrimingConnections(true);
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withClientAuthRequired(true);
    newBuilderResult.withKeyStore("42");
    newBuilderResult.withSecure(true);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(config, actualNewHttpClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler10() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setEnablePrimingConnections(true);
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withClientAuthRequired(true);
    newBuilderResult.withTrustStore("42");
    newBuilderResult.withSecure(true);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(config, actualNewHttpClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, retryHandler, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, retryHandler, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners3() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, retryHandler, new ArrayList<>());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("Dr Jane Doe", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, clientConfig);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners4() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    ArrayList<ExecutionListener<HttpClientRequest<ByteBuf>, HttpClientResponse<ByteBuf>>> listeners = new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    listeners.add(new TestExecutionListener<>(expectedRequest, requestConfig));

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, retryHandler, listeners);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners5() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    ArrayList<ExecutionListener<HttpClientRequest<ByteBuf>, HttpClientResponse<ByteBuf>>> listeners = new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    listeners.add(new TestExecutionListener<>(expectedRequest, requestConfig));
    HttpClientRequest<ByteBuf> expectedRequest2 = mock(HttpClientRequest.class);
    IClientConfig requestConfig2 = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    listeners.add(new TestExecutionListener<>(expectedRequest2, requestConfig2));

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, retryHandler, listeners);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners6() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("default", " ");
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, retryHandler, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(config, actualNewHttpClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners_whenNull() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        null, retryHandler, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners_whenNull2() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, null, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler_whenBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code config}, {@code retryHandler}.
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler_whenBaseLoadBalancer2() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        DefaultClientConfigImpl.getEmptyConfig(), null);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfig() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfig2() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfig3() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, config);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, actualNewHttpClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfig4() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    IPing ping = mock(IPing.class);

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    loadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, config, retryHandler, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners2() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, config, retryHandler, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners3() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, config, retryHandler, new ArrayList<>());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("Dr Jane Doe", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, clientConfig);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners4() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, null, retryHandler, new ArrayList<>());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertEquals("default", clientConfig.getClientName());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners5() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, config, null, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners6() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    ArrayList<ExecutionListener<HttpClientRequest<Object>, HttpClientResponse<Object>>> listeners = new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    listeners.add(new TestExecutionListener<>(expectedRequest, requestConfig));

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, config, retryHandler, listeners);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners7() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    ArrayList<ExecutionListener<HttpClientRequest<Object>, HttpClientResponse<Object>>> listeners = new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    listeners.add(new TestExecutionListener<>(expectedRequest, requestConfig));
    HttpClientRequest<ByteBuf> expectedRequest2 = mock(HttpClientRequest.class);
    IClientConfig requestConfig2 = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    listeners.add(new TestExecutionListener<>(expectedRequest2, requestConfig2));

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, config, retryHandler, listeners);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"})
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners8() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.addServers(new ArrayList<>());
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult = RibbonTransport
        .newHttpClient(pipelineConfigurator, loadBalancer, config, null, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = actualNewHttpClientResult.listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newSSEClient(ILoadBalancer, IClientConfig)"})
  public void testNewSSEClientWithLoadBalancerConfig() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((SSEClient<ByteBuf>) actualNewSSEClientResult).listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newSSEClient(ILoadBalancer, IClientConfig)"})
  public void testNewSSEClientWithLoadBalancerConfig2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((SSEClient<ByteBuf>) actualNewSSEClientResult).listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newSSEClient(ILoadBalancer, IClientConfig)"})
  public void testNewSSEClientWithLoadBalancerConfig3() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(loadBalancer, config);

    // Assert
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, actualNewSSEClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newSSEClient(ILoadBalancer, IClientConfig)"})
  public void testNewSSEClientWithLoadBalancerConfig4() {
    // Arrange
    ZoneAwareLoadBalancer<Server> loadBalancer = new ZoneAwareLoadBalancer<>();
    loadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof ZoneAwareLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((SSEClient<ByteBuf>) actualNewSSEClientResult).listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"})
  public void testNewSSEClientWithPipelineConfiguratorLoadBalancerConfig() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<ServerSentEvent>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    LoadBalancingHttpClient<Object, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(pipelineConfigurator, loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((SSEClient<Object>) actualNewSSEClientResult).listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"})
  public void testNewSSEClientWithPipelineConfiguratorLoadBalancerConfig2() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<ServerSentEvent>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer(config);

    // Act
    LoadBalancingHttpClient<Object, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(pipelineConfigurator, loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((SSEClient<Object>) actualNewSSEClientResult).listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"})
  public void testNewSSEClientWithPipelineConfiguratorLoadBalancerConfig3() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<ServerSentEvent>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    LoadBalancingHttpClient<Object, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(pipelineConfigurator, loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((SSEClient<Object>) actualNewSSEClientResult).listener;
    assertTrue(((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"})
  public void testNewSSEClientWithPipelineConfiguratorLoadBalancerConfig4() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<ServerSentEvent>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<Object, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(pipelineConfigurator, loadBalancer, config);

    // Assert
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, actualNewSSEClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }
}
