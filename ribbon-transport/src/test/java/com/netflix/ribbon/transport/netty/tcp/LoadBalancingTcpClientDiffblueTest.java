package com.netflix.ribbon.transport.netty.tcp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.loadbalancer.NoOpLoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.ribbon.transport.netty.RibbonTransport;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import io.reactivex.netty.client.ClientMetricsEvent;
import io.reactivex.netty.client.RxClient;
import io.reactivex.netty.client.RxClientImpl;
import io.reactivex.netty.metrics.MetricEventsListener;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.servo.tcp.TcpClientListener;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LoadBalancingTcpClientDiffblueTest {
  /**
   * Test {@link LoadBalancingTcpClient#LoadBalancingTcpClient(ILoadBalancer, IClientConfig,
   * RetryHandler, PipelineConfigurator, ScheduledExecutorService)}.
   *
   * <p>Method under test: {@link LoadBalancingTcpClient#LoadBalancingTcpClient(ILoadBalancer,
   * IClientConfig, RetryHandler, PipelineConfigurator, ScheduledExecutorService)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void LoadBalancingTcpClient.<init>(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator, ScheduledExecutorService)"
  })
  public void testNewLoadBalancingTcpClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingTcpClient<Object, Object> actualLoadBalancingTcpClient =
        new LoadBalancingTcpClient<>(
            lb,
            config,
            new DefaultLoadBalancerRetryHandler(),
            mock(PipelineConfigurator.class),
            RibbonTransport.poolCleanerScheduler);

    // Assert
    assertSame(lb, actualLoadBalancingTcpClient.getLoadBalancerContext().getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancingTcpClient#LoadBalancingTcpClient(ILoadBalancer, IClientConfig,
   * RetryHandler, PipelineConfigurator, ScheduledExecutorService)}.
   *
   * <p>Method under test: {@link LoadBalancingTcpClient#LoadBalancingTcpClient(ILoadBalancer,
   * IClientConfig, RetryHandler, PipelineConfigurator, ScheduledExecutorService)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void LoadBalancingTcpClient.<init>(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator, ScheduledExecutorService)"
  })
  public void testNewLoadBalancingTcpClient2() {
    // Arrange
    NoOpLoadBalancer lb = new NoOpLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancingTcpClient<Object, Object> actualLoadBalancingTcpClient =
        new LoadBalancingTcpClient<>(
            lb,
            config,
            new DefaultLoadBalancerRetryHandler(),
            mock(PipelineConfigurator.class),
            RibbonTransport.poolCleanerScheduler);

    // Assert
    LoadBalancerContext loadBalancerContext = actualLoadBalancingTcpClient.getLoadBalancerContext();
    Timer executeTracer = loadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, loadBalancerContext.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancingTcpClient#createRxClient(Server)}.
   *
   * <p>Method under test: {@link LoadBalancingTcpClient#createRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient LoadBalancingTcpClient.createRxClient(Server)"})
  public void testCreateRxClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingTcpClient<Object, Object> loadBalancingTcpClient =
        new LoadBalancingTcpClient<>(
            lb,
            config,
            new DefaultLoadBalancerRetryHandler(),
            mock(PipelineConfigurator.class),
            null);

    // Act
    RxClient<Object, Object> actualCreateRxClientResult =
        loadBalancingTcpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof RxClientImpl);
    assertEquals("TcpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Test {@link LoadBalancingTcpClient#createRxClient(Server)}.
   *
   * <p>Method under test: {@link LoadBalancingTcpClient#createRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient LoadBalancingTcpClient.createRxClient(Server)"})
  public void testCreateRxClient2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingTcpClient<Object, Object> loadBalancingTcpClient =
        new LoadBalancingTcpClient<>(lb, config, new DefaultLoadBalancerRetryHandler(), null, null);

    // Act
    RxClient<Object, Object> actualCreateRxClientResult =
        loadBalancingTcpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof RxClientImpl);
    assertEquals("TcpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Test {@link LoadBalancingTcpClient#createListener(String)}.
   *
   * <ul>
   *   <li>Then ConnectionTimes return {@link BasicTimer}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingTcpClient#createListener(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"MetricEventsListener LoadBalancingTcpClient.createListener(String)"})
  public void testCreateListener_thenConnectionTimesReturnBasicTimer() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingTcpClient<Object, Object> loadBalancingTcpClient =
        new LoadBalancingTcpClient<>(
            lb,
            config,
            new DefaultLoadBalancerRetryHandler(),
            mock(PipelineConfigurator.class),
            RibbonTransport.poolCleanerScheduler);

    // Act
    MetricEventsListener<? extends ClientMetricsEvent<?>> actualCreateListenerResult =
        loadBalancingTcpClient.createListener("Name");

    // Assert
    assertTrue(
        ((TcpClientListener) actualCreateListenerResult).getConnectionTimes()
            instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) actualCreateListenerResult).getFlushTimes() instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) actualCreateListenerResult).getPoolAcquireTimes()
            instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) actualCreateListenerResult).getPoolReleaseTimes()
            instanceof BasicTimer);
    assertTrue(
        ((TcpClientListener) actualCreateListenerResult).getWriteTimes() instanceof BasicTimer);
    assertTrue(actualCreateListenerResult instanceof TcpClientListener);
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getBytesRead());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getBytesWritten());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getConnectionCount());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getFailedConnectionClose());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getFailedConnects());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getFailedFlushes());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getFailedPoolAcquires());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getFailedPoolReleases());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getFailedWrites());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getLiveConnections());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getPendingConnectionClose());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getPendingConnects());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getPendingFlushes());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getPendingPoolAcquires());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getPendingPoolReleases());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getPendingWrites());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getPoolAcquires());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getPoolEvictions());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getPoolReleases());
    assertEquals(0L, ((TcpClientListener) actualCreateListenerResult).getPoolReuse());
  }
}
