package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.PrimeConnections;
import com.netflix.client.RetryHandler;
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
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.loadbalancer.reactive.ExecutionListener;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient;
import com.netflix.ribbon.transport.netty.http.SSEClient;
import com.netflix.ribbon.transport.netty.http.TestExecutionListener;
import com.netflix.ribbon.transport.netty.tcp.LoadBalancingTcpClient;
import com.netflix.ribbon.transport.netty.udp.LoadBalancingUdpClient;
import com.netflix.servo.monitor.BasicTimer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;
import io.reactivex.netty.client.ClientMetricsEvent;
import io.reactivex.netty.client.RxClient;
import io.reactivex.netty.metrics.MetricEventsListener;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import io.reactivex.netty.protocol.text.sse.ServerSentEvent;
import io.reactivex.netty.servo.http.HttpClientListener;
import io.reactivex.netty.servo.tcp.TcpClientListener;
import io.reactivex.netty.servo.udp.UdpClientListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class RibbonTransportDiffblueTest {
  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient RibbonTransport.newTcpClient(ILoadBalancer, IClientConfig)"})
  public void testNewTcpClientWithLoadBalancerConfig() {
    // Arrange and Act
    RxClient<ByteBuf, ByteBuf> actualNewTcpClientResult =
        RibbonTransport.newTcpClient(
            new BaseLoadBalancer(), DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingTcpClient<ByteBuf, ByteBuf>) actualNewTcpClientResult)
            .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((LoadBalancingTcpClient<ByteBuf, ByteBuf>) actualNewTcpClientResult).listener;
    assertTrue(
        ((TcpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof TcpClientListener);
    assertSame(
        ((LoadBalancingRxClient) actualNewTcpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient RibbonTransport.newTcpClient(ILoadBalancer, IClientConfig)"})
  public void testNewTcpClientWithLoadBalancerConfig2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    RxClient<ByteBuf, ByteBuf> actualNewTcpClientResult =
        RibbonTransport.newTcpClient(loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingTcpClient<ByteBuf, ByteBuf>) actualNewTcpClientResult)
            .getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((LoadBalancingTcpClient<ByteBuf, ByteBuf>) actualNewTcpClientResult).listener;
    assertTrue(
        ((TcpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof TcpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        ((LoadBalancingRxClient) actualNewTcpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient RibbonTransport.newTcpClient(ILoadBalancer, IClientConfig)"})
  public void testNewTcpClientWithLoadBalancerConfig3() {
    // Arrange
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    RxClient<ByteBuf, ByteBuf> actualNewTcpClientResult =
        RibbonTransport.newTcpClient(new BaseLoadBalancer(), config);

    // Assert
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingTcpClient<ByteBuf, ByteBuf>) actualNewTcpClientResult)
            .getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(
        config,
        ((LoadBalancingTcpClient<ByteBuf, ByteBuf>) actualNewTcpClientResult).getClientConfig());
    assertSame(
        ((LoadBalancingRxClient) actualNewTcpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig,
   * RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code
   * retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RxClient RibbonTransport.newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"
  })
  public void testNewTcpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    RxClient<Object, Object> actualNewTcpClientResult =
        RibbonTransport.newTcpClient(
            loadBalancer, pipelineConfigurator, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult)
            .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult).listener;
    assertTrue(
        ((TcpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof TcpClientListener);
    assertSame(
        ((LoadBalancingRxClient) actualNewTcpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig,
   * RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code
   * retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RxClient RibbonTransport.newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"
  })
  public void testNewTcpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    RxClient<Object, Object> actualNewTcpClientResult =
        RibbonTransport.newTcpClient(
            loadBalancer, pipelineConfigurator, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult)
            .getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult).listener;
    assertTrue(
        ((TcpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof TcpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        ((LoadBalancingRxClient) actualNewTcpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig,
   * RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code
   * retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RxClient RibbonTransport.newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"
  })
  public void testNewTcpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler3() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    RxClient<Object, Object> actualNewTcpClientResult =
        RibbonTransport.newTcpClient(
            loadBalancer, pipelineConfigurator, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult)
            .getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(
        config,
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult).getClientConfig());
    assertSame(
        ((LoadBalancingRxClient) actualNewTcpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig,
   * RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code
   * retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RxClient RibbonTransport.newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"
  })
  public void testNewTcpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setPing(ping);
    loadBalancer.addServer(new Server("42"));
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    RxClient<Object, Object> actualNewTcpClientResult =
        RibbonTransport.newTcpClient(
            loadBalancer, pipelineConfigurator, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    verify(ping).isAlive(isA(Server.class));
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult)
            .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult).listener;
    assertTrue(
        ((TcpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof TcpClientListener);
    assertSame(
        ((LoadBalancingRxClient) actualNewTcpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig,
   * RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code
   * retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RxClient RibbonTransport.newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"
  })
  public void testNewTcpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler5() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setAlive(true);

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    IPing ping2 = mock(IPing.class);

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    loadBalancer.setPing(ping);
    loadBalancer.addServer(newServer);
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    RxClient<Object, Object> actualNewTcpClientResult =
        RibbonTransport.newTcpClient(
            loadBalancer, pipelineConfigurator, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    verify(ping).isAlive(isA(Server.class));
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult)
            .getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult).listener;
    assertTrue(
        ((TcpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((TcpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof TcpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        ((LoadBalancingRxClient) actualNewTcpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig,
   * RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code
   * retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RxClient RibbonTransport.newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"
  })
  public void testNewTcpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler6() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    RxClient<Object, Object> actualNewTcpClientResult =
        RibbonTransport.newTcpClient(
            loadBalancer, pipelineConfigurator, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    assertSame(
        config,
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult).getClientConfig());
    assertSame(
        ((LoadBalancingRxClient) actualNewTcpClientResult).defaultRetryHandler,
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult)
            .getLoadBalancerContext()
            .getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig,
   * RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code
   * retryHandler}.
   *
   * <ul>
   *   <li>Given {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, PipelineConfigurator,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RxClient RibbonTransport.newTcpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"
  })
  public void testNewTcpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler_givenTrue() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withSecure(true);
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    RxClient<Object, Object> actualNewTcpClientResult =
        RibbonTransport.newTcpClient(
            loadBalancer, pipelineConfigurator, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    assertSame(
        config,
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult).getClientConfig());
    assertSame(
        ((LoadBalancingRxClient) actualNewTcpClientResult).defaultRetryHandler,
        ((LoadBalancingTcpClient<Object, Object>) actualNewTcpClientResult)
            .getLoadBalancerContext()
            .getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig() {
    // Arrange and Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult =
        RibbonTransport.newUdpClient(
            new BaseLoadBalancer(), DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .listener;
    assertTrue(
        ((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(
        ((LoadBalancingRxClient) actualNewUdpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult =
        RibbonTransport.newUdpClient(loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .listener;
    assertTrue(
        ((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        ((LoadBalancingRxClient) actualNewUdpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig3() {
    // Arrange
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult =
        RibbonTransport.newUdpClient(new BaseLoadBalancer(), config);

    // Assert
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(
        config,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .getClientConfig());
    assertSame(
        ((LoadBalancingRxClient) actualNewUdpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setPing(ping);
    Server newServer = new Server("42");
    loadBalancer.addServer(newServer);

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult =
        RibbonTransport.newUdpClient(loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer2 =
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .getLoadBalancerContext()
            .getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    List<Server> allServers = loadBalancer2.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = loadBalancer2.getReachableServers();
    assertEquals(1, reachableServers.size());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig5() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult =
        RibbonTransport.newUdpClient(loadBalancer, config);

    // Assert
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertSame(
        config,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .getClientConfig());
    assertSame(
        ((LoadBalancingRxClient) actualNewUdpClientResult).defaultRetryHandler,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .getLoadBalancerContext()
            .getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig6() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withSecure(true);
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult =
        RibbonTransport.newUdpClient(loadBalancer, config);

    // Assert
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertSame(
        config,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .getClientConfig());
    assertSame(
        ((LoadBalancingRxClient) actualNewUdpClientResult).defaultRetryHandler,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .getLoadBalancerContext()
            .getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig7() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withClientAuthRequired(true);
    newBuilderResult.withSecure(true);
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult =
        RibbonTransport.newUdpClient(loadBalancer, config);

    // Assert
    IClientConfig clientConfig =
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
            .getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(4, properties.size());
    assertTrue(properties.containsKey("DeploymentContextBasedVipAddresses"));
    assertTrue(properties.containsKey("IgnoreUserTokenInConnectionPoolForSecureClient"));
    assertEquals(Boolean.TRUE.toString(), properties.get("IsClientAuthRequired"));
    assertEquals(Boolean.TRUE.toString(), properties.get("IsSecure"));
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig,
   * RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code
   * retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RxClient RibbonTransport.newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"
  })
  public void testNewUdpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    RxClient<Object, Object> actualNewUdpClientResult =
        RibbonTransport.newUdpClient(
            loadBalancer, pipelineConfigurator, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult)
            .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult).listener;
    assertTrue(
        ((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(
        ((LoadBalancingRxClient) actualNewUdpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig,
   * RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code
   * retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RxClient RibbonTransport.newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"
  })
  public void testNewUdpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    RxClient<Object, Object> actualNewUdpClientResult =
        RibbonTransport.newUdpClient(
            loadBalancer, pipelineConfigurator, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult)
            .getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult).listener;
    assertTrue(
        ((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        ((LoadBalancingRxClient) actualNewUdpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig,
   * RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code
   * retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RxClient RibbonTransport.newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"
  })
  public void testNewUdpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler3() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    RxClient<Object, Object> actualNewUdpClientResult =
        RibbonTransport.newUdpClient(
            loadBalancer, pipelineConfigurator, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    LoadBalancerContext loadBalancerContext =
        ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult)
            .getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(
        config,
        ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult).getClientConfig());
    assertSame(
        ((LoadBalancingRxClient) actualNewUdpClientResult).defaultRetryHandler,
        loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer)} with {@code loadBalancer}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer)"})
  public void testNewHttpClientWithLoadBalancer() {
    // Arrange and Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(new BaseLoadBalancer());

    // Assert
    ILoadBalancer loadBalancer =
        actualNewHttpClientResult.getLoadBalancerContext().getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertNull(((BaseLoadBalancer) loadBalancer).getPrimeConnections());
    assertNull(((BaseLoadBalancer) loadBalancer).getPing());
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(loadBalancer.getReachableServers().isEmpty());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer)} with {@code loadBalancer}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer)"})
  public void testNewHttpClientWithLoadBalancer2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer)} with {@code loadBalancer}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer)"})
  public void testNewHttpClientWithLoadBalancer3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setPing(ping);
    Server newServer = new Server("42");
    loadBalancer.addServer(newServer);

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer2 =
        actualNewHttpClientResult.getLoadBalancerContext().getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertNull(((BaseLoadBalancer) loadBalancer2).getPrimeConnections());
    List<Server> allServers = loadBalancer2.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = loadBalancer2.getReachableServers();
    assertEquals(1, reachableServers.size());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer)} with {@code loadBalancer}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer)"})
  public void testNewHttpClientWithLoadBalancer4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener3).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener4 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener4).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener5 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener5).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerListChangeListener listener6 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener6)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener7 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener7).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.addServerStatusChangeListener(listener7);
    loadBalancer.addServerListChangeListener(listener6);
    PrimeConnections primeConnections = new PrimeConnections(" ", 1, 1L, " ", 10.0f);
    loadBalancer.setPrimeConnections(primeConnections);
    loadBalancer.addServerStatusChangeListener(listener5);
    loadBalancer.addServerStatusChangeListener(listener4);
    loadBalancer.addServerStatusChangeListener(listener3);
    loadBalancer.addServerStatusChangeListener(listener2);
    loadBalancer.addServerStatusChangeListener(listener);
    loadBalancer.setPing(ping);
    Server newServer = new Server("42");
    loadBalancer.addServer(newServer);

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener6).serverListChanged(isA(List.class), isA(List.class));
    verify(listener7).serverStatusChanged(isA(Collection.class));
    verify(listener5).serverStatusChanged(isA(Collection.class));
    verify(listener4).serverStatusChanged(isA(Collection.class));
    verify(listener3).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    ILoadBalancer loadBalancer2 =
        actualNewHttpClientResult.getLoadBalancerContext().getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    PrimeConnections primeConnections2 = ((BaseLoadBalancer) loadBalancer2).getPrimeConnections();
    assertNull(primeConnections2.getEndStats());
    List<Server> allServers = loadBalancer2.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = loadBalancer2.getReachableServers();
    assertEquals(1, reachableServers.size());
    assertSame(primeConnections, primeConnections2);
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"
  })
  public void testNewHttpClientWithLoadBalancerConfig() {
    // Arrange and Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            new BaseLoadBalancer(), DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"
  })
  public void testNewHttpClientWithLoadBalancerConfig2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"
  })
  public void testNewHttpClientWithLoadBalancerConfig3() {
    // Arrange
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(new BaseLoadBalancer(), config);

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("Dr Jane Doe", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, clientConfig);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"
  })
  public void testNewHttpClientWithLoadBalancerConfig4() {
    // Arrange and Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            new BaseLoadBalancer(),
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("key cannot be null", " "));

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"
  })
  public void testNewHttpClientWithLoadBalancerConfig5() {
    // Arrange
    ArrayList<Server> newServers = new ArrayList<>();
    newServers.add(new Server("42"));

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer(ping, new ZoneAvoidanceRule());
    loadBalancer.setPingInterval(42);
    loadBalancer.addServerStatusChangeListener(listener);
    loadBalancer.setEnablePrimingConnections(true);
    loadBalancer.addServers(new ArrayList<>());
    loadBalancer.addServers(newServers);

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            loadBalancer,
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("key cannot be null", " "));

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with
   * {@code loadBalancer}, {@code config}, {@code retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with
   * {@code loadBalancer}, {@code config}, {@code retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with
   * {@code loadBalancer}, {@code config}, {@code retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler3() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, config, new DefaultLoadBalancerRetryHandler());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("Dr Jane Doe", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, clientConfig);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with
   * {@code loadBalancer}, {@code config}, {@code retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler4() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, null, new DefaultLoadBalancerRetryHandler());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertEquals("default", clientConfig.getClientName());
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)} with
   * {@code loadBalancer}, {@code config}, {@code retryHandler}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler5() {
    // Arrange and Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            new BaseLoadBalancer(), DefaultClientConfigImpl.getEmptyConfig(), null);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   * with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, config, retryHandler, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   * with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, config, retryHandler, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   * with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners3() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, config, retryHandler, new ArrayList<>());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("Dr Jane Doe", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, clientConfig);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   * with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners4() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, null, retryHandler, new ArrayList<>());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertEquals("default", clientConfig.getClientName());
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   * with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners5() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    ArrayList<ExecutionListener<HttpClientRequest<ByteBuf>, HttpClientResponse<ByteBuf>>>
        listeners = new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    TestExecutionListener<ByteBuf, ByteBuf> testExecutionListener =
        new TestExecutionListener<>(expectedRequest, requestConfig);
    listeners.add(testExecutionListener);

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, config, retryHandler, listeners);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   * with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners6() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    ArrayList<ExecutionListener<HttpClientRequest<ByteBuf>, HttpClientResponse<ByteBuf>>>
        listeners = new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    TestExecutionListener<ByteBuf, ByteBuf> testExecutionListener =
        new TestExecutionListener<>(expectedRequest, requestConfig);
    listeners.add(testExecutionListener);
    HttpClientRequest<ByteBuf> expectedRequest2 = mock(HttpClientRequest.class);
    IClientConfig requestConfig2 =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    TestExecutionListener<ByteBuf, ByteBuf> testExecutionListener2 =
        new TestExecutionListener<>(expectedRequest2, requestConfig2);
    listeners.add(testExecutionListener2);

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, config, retryHandler, listeners);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)}
   * with {@code loadBalancer}, {@code config}, {@code retryHandler}, {@code listeners}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig,
   * RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners_whenNull() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer, config, null, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer)} with {@code loadBalancer}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} Zone is {@code Zone}.
   * </ul>
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer)"})
  public void testNewHttpClientWithLoadBalancer_givenServerWithIdIs42ZoneIsZone() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setZone("Zone");

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener3).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener4 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener4).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener5 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener5).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerListChangeListener listener6 = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener6)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    ServerStatusChangeListener listener7 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener7).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener8 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener8).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener9 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener9).serverStatusChanged(Mockito.<Collection<Server>>any());

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.addServerStatusChangeListener(listener9);
    loadBalancer.addServerStatusChangeListener(listener8);
    loadBalancer.addServerStatusChangeListener(listener7);
    loadBalancer.addServerListChangeListener(listener6);
    PrimeConnections primeConnections = new PrimeConnections(" ", 1, 1L, " ", 10.0f);
    loadBalancer.setPrimeConnections(primeConnections);
    loadBalancer.addServerStatusChangeListener(listener5);
    loadBalancer.addServerStatusChangeListener(listener4);
    loadBalancer.addServerStatusChangeListener(listener3);
    loadBalancer.addServerStatusChangeListener(listener2);
    loadBalancer.addServerStatusChangeListener(listener);
    loadBalancer.setPing(ping);
    loadBalancer.addServer(newServer);

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(loadBalancer);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener6).serverListChanged(isA(List.class), isA(List.class));
    verify(listener9).serverStatusChanged(isA(Collection.class));
    verify(listener8).serverStatusChanged(isA(Collection.class));
    verify(listener7).serverStatusChanged(isA(Collection.class));
    verify(listener5).serverStatusChanged(isA(Collection.class));
    verify(listener4).serverStatusChanged(isA(Collection.class));
    verify(listener3).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
    ILoadBalancer loadBalancer2 =
        actualNewHttpClientResult.getLoadBalancerContext().getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    PrimeConnections primeConnections2 = ((BaseLoadBalancer) loadBalancer2).getPrimeConnections();
    assertNull(primeConnections2.getEndStats());
    List<Server> allServers = loadBalancer2.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = loadBalancer2.getReachableServers();
    assertEquals(1, reachableServers.size());
    assertSame(primeConnections, primeConnections2);
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   * with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"
  })
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfig() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            pipelineConfigurator, new BaseLoadBalancer(), DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   * with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"
  })
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfig2() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            pipelineConfigurator, loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   * with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"
  })
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfig3() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(pipelineConfigurator, new BaseLoadBalancer(), config);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, actualNewHttpClientResult.getClientConfig());
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig,
   * RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config},
   * {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            pipelineConfigurator, loadBalancer, config, retryHandler, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig,
   * RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config},
   * {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners2() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            pipelineConfigurator, loadBalancer, config, retryHandler, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig,
   * RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config},
   * {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners3() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            pipelineConfigurator, loadBalancer, config, retryHandler, new ArrayList<>());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("Dr Jane Doe", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, clientConfig);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig,
   * RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config},
   * {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners4() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            pipelineConfigurator, loadBalancer, null, retryHandler, new ArrayList<>());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertEquals("default", clientConfig.getClientName());
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig,
   * RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config},
   * {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners5() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            pipelineConfigurator, loadBalancer, config, null, new ArrayList<>());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig,
   * RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config},
   * {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners6() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    ArrayList<ExecutionListener<HttpClientRequest<Object>, HttpClientResponse<Object>>> listeners =
        new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    TestExecutionListener<Object, Object> testExecutionListener =
        new TestExecutionListener<>(expectedRequest, requestConfig);
    listeners.add(testExecutionListener);

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            pipelineConfigurator, loadBalancer, config, retryHandler, listeners);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig,
   * RetryHandler, List)} with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config},
   * {@code retryHandler}, {@code listeners}.
   *
   * <p>Method under test: {@link RibbonTransport#newHttpClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig, RetryHandler, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newHttpClient(PipelineConfigurator, ILoadBalancer, IClientConfig, RetryHandler, List)"
  })
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfigRetryHandlerListeners7() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<Object>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    ArrayList<ExecutionListener<HttpClientRequest<Object>, HttpClientResponse<Object>>> listeners =
        new ArrayList<>();
    HttpClientRequest<ByteBuf> expectedRequest = mock(HttpClientRequest.class);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    TestExecutionListener<Object, Object> testExecutionListener =
        new TestExecutionListener<>(expectedRequest, requestConfig);
    listeners.add(testExecutionListener);
    HttpClientRequest<ByteBuf> expectedRequest2 = mock(HttpClientRequest.class);
    IClientConfig requestConfig2 =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    TestExecutionListener<Object, Object> testExecutionListener2 =
        new TestExecutionListener<>(expectedRequest2, requestConfig2);
    listeners.add(testExecutionListener2);

    // Act
    LoadBalancingHttpClient<Object, Object> actualNewHttpClientResult =
        RibbonTransport.newHttpClient(
            pipelineConfigurator, loadBalancer, config, retryHandler, listeners);

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        actualNewHttpClientResult.listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(
        actualNewHttpClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newSSEClient(ILoadBalancer, IClientConfig)"
  })
  public void testNewSSEClientWithLoadBalancerConfig() {
    // Arrange and Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult =
        RibbonTransport.newSSEClient(
            new BaseLoadBalancer(), DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((SSEClient<ByteBuf>) actualNewSSEClientResult).listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(actualNewSSEClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newSSEClient(ILoadBalancer, IClientConfig)"
  })
  public void testNewSSEClientWithLoadBalancerConfig2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult =
        RibbonTransport.newSSEClient(loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((SSEClient<ByteBuf>) actualNewSSEClientResult).listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(actualNewSSEClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newSSEClient(ILoadBalancer, IClientConfig)"
  })
  public void testNewSSEClientWithLoadBalancerConfig3() {
    // Arrange
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult =
        RibbonTransport.newSSEClient(new BaseLoadBalancer(), config);

    // Assert
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, actualNewSSEClientResult.getClientConfig());
    assertSame(actualNewSSEClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newSSEClient(ILoadBalancer, IClientConfig)"
  })
  public void testNewSSEClientWithLoadBalancerConfig_givenServerWithIdIs42() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setPing(ping);
    Server newServer = new Server("42");
    loadBalancer.addServer(newServer);

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult =
        RibbonTransport.newSSEClient(loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer2 =
        actualNewSSEClientResult.getLoadBalancerContext().getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    List<Server> allServers = loadBalancer2.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = loadBalancer2.getReachableServers();
    assertEquals(1, reachableServers.size());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)} with {@code
   * loadBalancer}, {@code config}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} Alive is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newSSEClient(ILoadBalancer, IClientConfig)"
  })
  public void testNewSSEClientWithLoadBalancerConfig_givenServerWithIdIs42AliveIsTrue() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setAlive(true);

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setPing(ping);
    loadBalancer.addServer(newServer);

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult =
        RibbonTransport.newSSEClient(loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer2 =
        actualNewSSEClientResult.getLoadBalancerContext().getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    List<Server> allServers = loadBalancer2.getAllServers();
    assertEquals(1, allServers.size());
    List<Server> reachableServers = loadBalancer2.getReachableServers();
    assertEquals(1, reachableServers.size());
    assertSame(newServer, allServers.get(0));
    assertSame(newServer, reachableServers.get(0));
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   * with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"
  })
  public void testNewSSEClientWithPipelineConfiguratorLoadBalancerConfig() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<ServerSentEvent>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);

    // Act
    LoadBalancingHttpClient<Object, ServerSentEvent> actualNewSSEClientResult =
        RibbonTransport.newSSEClient(
            pipelineConfigurator, new BaseLoadBalancer(), DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((SSEClient<Object>) actualNewSSEClientResult).listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(actualNewSSEClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   * with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"
  })
  public void testNewSSEClientWithPipelineConfiguratorLoadBalancerConfig2() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<ServerSentEvent>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    LoadBalancingHttpClient<Object, ServerSentEvent> actualNewSSEClientResult =
        RibbonTransport.newSSEClient(
            pipelineConfigurator, loadBalancer, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener =
        ((SSEClient<Object>) actualNewSSEClientResult).listener;
    assertTrue(
        ((HttpClientListener) metricEventsListener).getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getResponseReadTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(
        ((HttpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((HttpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof HttpClientListener);
    assertSame(loadBalancer, loadBalancer2);
    assertSame(actualNewSSEClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)}
   * with {@code pipelineConfigurator}, {@code loadBalancer}, {@code config}.
   *
   * <p>Method under test: {@link RibbonTransport#newSSEClient(PipelineConfigurator, ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LoadBalancingHttpClient RibbonTransport.newSSEClient(PipelineConfigurator, ILoadBalancer, IClientConfig)"
  })
  public void testNewSSEClientWithPipelineConfiguratorLoadBalancerConfig3() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<ServerSentEvent>, HttpClientRequest<Object>>
        pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<Object, ServerSentEvent> actualNewSSEClientResult =
        RibbonTransport.newSSEClient(pipelineConfigurator, new BaseLoadBalancer(), config);

    // Assert
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, actualNewSSEClientResult.getClientConfig());
    assertSame(actualNewSSEClientResult.defaultRetryHandler, loadBalancerContext.getRetryHandler());
  }
}
