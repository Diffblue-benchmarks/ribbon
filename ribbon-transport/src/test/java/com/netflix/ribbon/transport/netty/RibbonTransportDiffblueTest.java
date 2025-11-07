package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.RetryHandler;
import com.netflix.client.SimpleVipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.NoOpLoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerStatusChangeListener;
import com.netflix.loadbalancer.reactive.ExecutionListener;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient;
import com.netflix.ribbon.transport.netty.http.NettyHttpLoadBalancerErrorHandler;
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
import io.reactivex.netty.servo.udp.UdpClientListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class RibbonTransportDiffblueTest {
  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newTcpClient(ILoadBalancer, IClientConfig)"})
  public void testNewTcpClientWithLoadBalancerConfig() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setPing(ping);
    Server newServer = new Server("42");
    loadBalancer.addServer(newServer);

    // Act
    RxClient<ByteBuf, ByteBuf> actualNewTcpClientResult = RibbonTransport.newTcpClient(loadBalancer,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer2 = ((LoadBalancingTcpClient<ByteBuf, ByteBuf>) actualNewTcpClientResult)
        .getLoadBalancerContext()
        .getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    assertEquals(1, loadBalancer2.getAllServers().size());
    List<Server> reachableServers = loadBalancer2.getReachableServers();
    assertEquals(1, reachableServers.size());
    assertSame(newServer, reachableServers.get(0));
  }

  /**
   * Test {@link RibbonTransport#newTcpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newTcpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newTcpClient(ILoadBalancer, IClientConfig)"})
  public void testNewTcpClientWithLoadBalancerConfig2() {
    // Arrange
    Server newServer = new Server("42");

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.addServer(newServer);
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    loadBalancer.setPing(ping);
    Server newServer2 = new Server("Id");
    loadBalancer.addServer(newServer2);

    // Act
    RxClient<ByteBuf, ByteBuf> actualNewTcpClientResult = RibbonTransport.newTcpClient(loadBalancer,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping, atLeast(1)).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer2 = ((LoadBalancingTcpClient<ByteBuf, ByteBuf>) actualNewTcpClientResult)
        .getLoadBalancerContext()
        .getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(actualNewTcpClientResult instanceof LoadBalancingTcpClient);
    List<Server> allServers = loadBalancer2.getAllServers();
    assertEquals(2, allServers.size());
    assertSame(newServer2, allServers.get(1));
    Server expectedGetResult = allServers.get(0);
    assertSame(expectedGetResult, loadBalancer2.getReachableServers().get(0));
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig2() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(loadBalancer, loadBalancer2);
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig3() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        config);

    // Assert
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertEquals("Dr Jane Doe",
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).getLoadBalancerContext()
            .getClientName());
    assertSame(config,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).getClientConfig());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig4() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setEnablePrimingConnections(true);

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig5() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setNameSpace(" ");

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        config);

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(loadBalancer, loadBalancer2);
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig6() {
    // Arrange
    DynamicServerListLoadBalancer<Server> loadBalancer = new DynamicServerListLoadBalancer<>();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    config.setNameSpace(" ");

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        config);

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof DynamicServerListLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(config,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).getClientConfig());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig7() {
    // Arrange
    DynamicServerListLoadBalancer<Server> loadBalancer = new DynamicServerListLoadBalancer<>();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    config.setNameSpace(" ");

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        config);

    // Assert
    IClientConfig clientConfig = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getClientConfig();
    assertTrue(((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(2, properties.size());
    assertEquals("42 Main St", properties.get("DeploymentContextBasedVipAddresses"));
    String expectedString = Boolean.TRUE.toString();
    assertEquals(expectedString, properties.get("IgnoreUserTokenInConnectionPoolForSecureClient"));
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig8() {
    // Arrange
    DynamicServerListLoadBalancer<Server> loadBalancer = new DynamicServerListLoadBalancer<>();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withSecure(true);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    config.setNameSpace(" ");

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        config);

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof DynamicServerListLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(config,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).getClientConfig());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <ul>
   *   <li>Then return ResponseTimeOut is {@code 4084}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig_thenReturnResponseTimeOutIs4084() {
    // Arrange
    DynamicServerListLoadBalancer<Server> loadBalancer = new DynamicServerListLoadBalancer<>();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withReadTimeout(42);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    config.setNameSpace(" ");

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        config);

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof DynamicServerListLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertEquals(4084,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).getResponseTimeOut());
    assertSame(config,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).getClientConfig());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <ul>
   *   <li>When EmptyConfig NameSpace is space.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig_whenEmptyConfigNameSpaceIsSpace() {
    // Arrange
    DynamicServerListLoadBalancer<Server> loadBalancer = new DynamicServerListLoadBalancer<>();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setNameSpace(" ");

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        config);

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof DynamicServerListLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <ul>
   *   <li>When newBuilder withClientAuthRequired {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig_whenNewBuilderWithClientAuthRequiredTrue() {
    // Arrange
    DynamicServerListLoadBalancer<Server> loadBalancer = new DynamicServerListLoadBalancer<>();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withClientAuthRequired(true);
    newBuilderResult.withSecure(true);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    config.setNameSpace(" ");

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        config);

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof DynamicServerListLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(config,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).getClientConfig());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <ul>
   *   <li>When newBuilder withClientAuthRequired {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient RibbonTransport.newUdpClient(ILoadBalancer, IClientConfig)"})
  public void testNewUdpClientWithLoadBalancerConfig_whenNewBuilderWithClientAuthRequiredTrue2() {
    // Arrange
    DynamicServerListLoadBalancer<Server> loadBalancer = new DynamicServerListLoadBalancer<>();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withClientAuthRequired(true);
    newBuilderResult.withTrustStore("42");
    newBuilderResult.withSecure(true);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    config.setNameSpace(" ");

    // Act
    RxClient<DatagramPacket, DatagramPacket> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer,
        config);

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof DynamicServerListLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(config,
        ((LoadBalancingUdpClient<DatagramPacket, DatagramPacket>) actualNewUdpClientResult).getClientConfig());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "RxClient RibbonTransport.newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"})
  public void testNewUdpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    RxClient<Object, Object> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer, pipelineConfigurator,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "RxClient RibbonTransport.newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"})
  public void testNewUdpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler2() {
    // Arrange
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer(config);
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config2 = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    RxClient<Object, Object> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer, pipelineConfigurator,
        config2, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof BaseLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(loadBalancer, loadBalancer2);
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "RxClient RibbonTransport.newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"})
  public void testNewUdpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler3() {
    // Arrange
    NoOpLoadBalancer loadBalancer = new NoOpLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    RxClient<Object, Object> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer, pipelineConfigurator,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    ILoadBalancer loadBalancer2 = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer2 instanceof NoOpLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
    assertSame(loadBalancer, loadBalancer2);
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "RxClient RibbonTransport.newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"})
  public void testNewUdpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler4() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    RxClient<Object, Object> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer, pipelineConfigurator,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertEquals("Dr Jane Doe",
        ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult).getLoadBalancerContext().getClientName());
    assertSame(config, ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult).getClientConfig());
  }

  /**
   * Test {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)} with {@code loadBalancer}, {@code pipelineConfigurator}, {@code config}, {@code retryHandler}.
   * <p>
   * Method under test: {@link RibbonTransport#newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "RxClient RibbonTransport.newUdpClient(ILoadBalancer, PipelineConfigurator, IClientConfig, RetryHandler)"})
  public void testNewUdpClientWithLoadBalancerPipelineConfiguratorConfigRetryHandler5() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    loadBalancer.setLoadBalancerStats(new LoadBalancerStats());
    PipelineConfigurator<Object, Object> pipelineConfigurator = mock(PipelineConfigurator.class);
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    RxClient<Object, Object> actualNewUdpClientResult = RibbonTransport.newUdpClient(loadBalancer, pipelineConfigurator,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    LoadBalancerContext loadBalancerContext = ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult)
        .getLoadBalancerContext();
    assertTrue(loadBalancerContext.getLoadBalancer() instanceof BaseLoadBalancer);
    assertTrue(actualNewUdpClientResult instanceof LoadBalancingUdpClient);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    MetricEventsListener<? extends ClientMetricsEvent<?>> metricEventsListener = ((LoadBalancingUdpClient<Object, Object>) actualNewUdpClientResult).listener;
    assertTrue(((UdpClientListener) metricEventsListener).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) metricEventsListener).getWriteTimes() instanceof BasicTimer);
    assertTrue(metricEventsListener instanceof UdpClientListener);
  }

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
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer(config);

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
  public void testNewHttpClientWithLoadBalancer3() {
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
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config);

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
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config);

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
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config);

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
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithLoadBalancerConfig6() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withSecure(true);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config);

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
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

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
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler2() {
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
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler3() {
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
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler4() {
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
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler5() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        DefaultClientConfigImpl.getEmptyConfig(), null);

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
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("MILLISECONDS", " ");

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    IClientConfig clientConfig = actualNewHttpClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("MILLISECONDS", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewHttpClientResult.getLoadBalancerContext();
    assertEquals("MILLISECONDS", loadBalancerContext.getClientName());
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
  public void testNewHttpClientWithLoadBalancerConfigRetryHandler7() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config, new NettyHttpLoadBalancerErrorHandler());

    // Assert
    assertSame(config, actualNewHttpClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewHttpClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, actualNewHttpClientResult.getLoadBalancerContext().getRetryHandler());
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
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        null, retryHandler, new ArrayList<>());

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
  public void testNewHttpClientWithLoadBalancerConfigRetryHandlerListeners7() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withLoadBalancerEnabled(true);
    IClientConfig requestConfig = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    TestExecutionListener<ByteBuf, ByteBuf> testExecutionListener = new TestExecutionListener<>(
        mock(HttpClientRequest.class), requestConfig);

    ArrayList<ExecutionListener<HttpClientRequest<ByteBuf>, HttpClientResponse<ByteBuf>>> listeners = new ArrayList<>();
    listeners.add(testExecutionListener);

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
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <ul>
   *   <li>When EmptyConfig.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithLoadBalancerConfig_whenEmptyConfig() {
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
   * <ul>
   *   <li>When newBuilder withClientAuthRequired {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithLoadBalancerConfig_whenNewBuilderWithClientAuthRequiredTrue() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withClientAuthRequired(true);
    newBuilderResult.withSecure(true);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config);

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
   * Test {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <ul>
   *   <li>When newBuilder withClientAuthRequired {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newHttpClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newHttpClient(ILoadBalancer, IClientConfig)"})
  public void testNewHttpClientWithLoadBalancerConfig_whenNewBuilderWithClientAuthRequiredTrue2() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withClientAuthRequired(true);
    newBuilderResult.withTrustStore("42");
    newBuilderResult.withSecure(true);
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ByteBuf> actualNewHttpClientResult = RibbonTransport.newHttpClient(loadBalancer,
        config);

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
    loadBalancer.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));

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
  public void testNewHttpClientWithPipelineConfiguratorLoadBalancerConfig4() {
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
    IClientConfig clientConfig = actualNewSSEClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertEquals("Dr Jane Doe", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, clientConfig);
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
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("tagArray", " ");

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(loadBalancer, config);

    // Assert
    IClientConfig clientConfig = actualNewSSEClientResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertEquals("tagArray", clientConfig.getClientName());
    LoadBalancerContext loadBalancerContext = actualNewSSEClientResult.getLoadBalancerContext();
    assertEquals("tagArray", loadBalancerContext.getClientName());
    assertSame(config, clientConfig);
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
  public void testNewSSEClientWithLoadBalancerConfig5() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(loadBalancer, config);

    // Assert
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertSame(config, actualNewSSEClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, actualNewSSEClientResult.getLoadBalancerContext().getRetryHandler());
  }

  /**
   * Test {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)} with {@code loadBalancer}, {@code config}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When newBuilder withSecure {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonTransport#newSSEClient(ILoadBalancer, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancingHttpClient RibbonTransport.newSSEClient(ILoadBalancer, IClientConfig)"})
  public void testNewSSEClientWithLoadBalancerConfig_givenTrue_whenNewBuilderWithSecureTrue() {
    // Arrange
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withSecure(true);
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig config = newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancingHttpClient<ByteBuf, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(loadBalancer, config);

    // Assert
    assertTrue(actualNewSSEClientResult instanceof SSEClient);
    assertSame(config, actualNewSSEClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, actualNewSSEClientResult.getLoadBalancerContext().getRetryHandler());
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
  public void testNewSSEClientWithPipelineConfiguratorLoadBalancerConfig3() {
    // Arrange
    PipelineConfigurator<HttpClientResponse<ServerSentEvent>, HttpClientRequest<Object>> pipelineConfigurator = mock(
        PipelineConfigurator.class);
    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingHttpClient<Object, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(pipelineConfigurator, loadBalancer, config);

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
    assertSame(config, actualNewSSEClientResult.getClientConfig());
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
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "");

    // Act
    LoadBalancingHttpClient<Object, ServerSentEvent> actualNewSSEClientResult = RibbonTransport
        .newSSEClient(pipelineConfigurator, loadBalancer, config);

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
    assertSame(config, actualNewSSEClientResult.getClientConfig());
    RetryHandler expectedRetryHandler = actualNewSSEClientResult.defaultRetryHandler;
    assertSame(expectedRetryHandler, loadBalancerContext.getRetryHandler());
  }
}
