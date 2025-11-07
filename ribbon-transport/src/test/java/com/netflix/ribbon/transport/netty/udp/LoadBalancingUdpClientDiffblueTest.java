package com.netflix.ribbon.transport.netty.udp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.loadbalancer.NoOpLoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import com.netflix.ribbon.transport.netty.http.NettyHttpLoadBalancerErrorHandler;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import io.reactivex.netty.client.ClientMetricsEvent;
import io.reactivex.netty.client.RxClient;
import io.reactivex.netty.metrics.MetricEventsListener;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.udp.client.UdpClient;
import io.reactivex.netty.servo.udp.UdpClientListener;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LoadBalancingUdpClientDiffblueTest {
  /**
   * Test {@link LoadBalancingUdpClient#LoadBalancingUdpClient(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)}.
   * <p>
   * Method under test: {@link LoadBalancingUdpClient#LoadBalancingUdpClient(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void LoadBalancingUdpClient.<init>(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)"})
  public void testNewLoadBalancingUdpClient() {
    // Arrange
    NoOpLoadBalancer lb = new NoOpLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingUdpClient<Object, Object> actualLoadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Assert
    LoadBalancerContext loadBalancerContext = actualLoadBalancingUdpClient.getLoadBalancerContext();
    Timer executeTracer = loadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, loadBalancerContext.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancingUdpClient#LoadBalancingUdpClient(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)}.
   * <p>
   * Method under test: {@link LoadBalancingUdpClient#LoadBalancingUdpClient(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void LoadBalancingUdpClient.<init>(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)"})
  public void testNewLoadBalancingUdpClient2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancingUdpClient<Object, Object> actualLoadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Assert
    LoadBalancerContext loadBalancerContext = actualLoadBalancingUdpClient.getLoadBalancerContext();
    ILoadBalancer loadBalancer = loadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertTrue(((BaseLoadBalancer) loadBalancer).getRule() instanceof RoundRobinRule);
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertSame(config, actualLoadBalancingUdpClient.getClientConfig());
  }

  /**
   * Test {@link LoadBalancingUdpClient#LoadBalancingUdpClient(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)}.
   * <ul>
   *   <li>Then return LoadBalancerContext ClientName is {@code default}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingUdpClient#LoadBalancingUdpClient(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void LoadBalancingUdpClient.<init>(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)"})
  public void testNewLoadBalancingUdpClient_thenReturnLoadBalancerContextClientNameIsDefault() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingUdpClient<Object, Object> actualLoadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Assert
    LoadBalancerContext loadBalancerContext = actualLoadBalancingUdpClient.getLoadBalancerContext();
    assertEquals("default", loadBalancerContext.getClientName());
    assertSame(lb, loadBalancerContext.getLoadBalancer());
    assertSame(config, actualLoadBalancingUdpClient.getClientConfig());
  }

  /**
   * Test {@link LoadBalancingUdpClient#createRxClient(Server)}.
   * <p>
   * Method under test: {@link LoadBalancingUdpClient#createRxClient(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient LoadBalancingUdpClient.createRxClient(Server)"})
  public void testCreateRxClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), null);

    // Act
    RxClient<Object, Object> actualCreateRxClientResult = loadBalancingUdpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Test {@link LoadBalancingUdpClient#createRxClient(Server)}.
   * <ul>
   *   <li>Then return {@link UdpClient}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingUdpClient#createRxClient(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient LoadBalancingUdpClient.createRxClient(Server)"})
  public void testCreateRxClient_thenReturnUdpClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualCreateRxClientResult = loadBalancingUdpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Test {@link LoadBalancingUdpClient#createListener(String)}.
   * <ul>
   *   <li>Then ConnectionTimes return {@link BasicTimer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingUdpClient#createListener(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"MetricEventsListener LoadBalancingUdpClient.createListener(String)"})
  public void testCreateListener_thenConnectionTimesReturnBasicTimer() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    MetricEventsListener<? extends ClientMetricsEvent<?>> actualCreateListenerResult = loadBalancingUdpClient
        .createListener("Name");

    // Assert
    assertTrue(((UdpClientListener) actualCreateListenerResult).getConnectionTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) actualCreateListenerResult).getFlushTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) actualCreateListenerResult).getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) actualCreateListenerResult).getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(((UdpClientListener) actualCreateListenerResult).getWriteTimes() instanceof BasicTimer);
    assertTrue(actualCreateListenerResult instanceof UdpClientListener);
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getBytesRead());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getBytesWritten());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getConnectionCount());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getFailedConnectionClose());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getFailedConnects());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getFailedFlushes());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getFailedPoolAcquires());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getFailedPoolReleases());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getFailedWrites());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getLiveConnections());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getPendingConnectionClose());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getPendingConnects());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getPendingFlushes());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getPendingPoolAcquires());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getPendingPoolReleases());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getPendingWrites());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getPoolAcquires());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getPoolEvictions());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getPoolReleases());
    assertEquals(0L, ((UdpClientListener) actualCreateListenerResult).getPoolReuse());
  }
}
