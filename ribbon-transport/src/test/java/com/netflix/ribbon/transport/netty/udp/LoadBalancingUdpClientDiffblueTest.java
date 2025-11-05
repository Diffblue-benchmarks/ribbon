package com.netflix.ribbon.transport.netty.udp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.loadbalancer.NoOpLoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.ribbon.transport.netty.http.NettyHttpLoadBalancerErrorHandler;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.DefaultPublishingPolicy;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.PublishingPolicy;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import com.netflix.servo.tag.BasicTag;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.Tag;
import com.netflix.servo.tag.TagList;
import io.reactivex.netty.client.ClientMetricsEvent;
import io.reactivex.netty.client.RxClient;
import io.reactivex.netty.metrics.MetricEventsListener;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.udp.client.UdpClient;
import io.reactivex.netty.servo.udp.UdpClientListener;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class LoadBalancingUdpClientDiffblueTest {
  /**
   * Method under test: {@link LoadBalancingUdpClient#createRxClient(Server)}
   */
  @Test
  public void testCreateRxClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualCreateRxClientResult = loadBalancingUdpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Method under test: {@link LoadBalancingUdpClient#createRxClient(Server)}
   */
  @Test
  public void testCreateRxClient2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), null);

    // Act
    RxClient<Object, Object> actualCreateRxClientResult = loadBalancingUdpClient.createRxClient(new Server("42"));

    // Assert
    assertTrue(actualCreateRxClientResult instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualCreateRxClientResult.name());
  }

  /**
   * Method under test: {@link LoadBalancingUdpClient#createListener(String)}
   */
  @Test
  public void testCreateListener() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    MetricEventsListener<? extends ClientMetricsEvent<?>> actualCreateListenerResult = loadBalancingUdpClient
        .createListener("Name");

    // Assert
    Timer connectionTimes = ((UdpClientListener) actualCreateListenerResult).getConnectionTimes();
    assertTrue(connectionTimes instanceof BasicTimer);
    Timer flushTimes = ((UdpClientListener) actualCreateListenerResult).getFlushTimes();
    assertTrue(flushTimes instanceof BasicTimer);
    Timer poolAcquireTimes = ((UdpClientListener) actualCreateListenerResult).getPoolAcquireTimes();
    assertTrue(poolAcquireTimes instanceof BasicTimer);
    Timer poolReleaseTimes = ((UdpClientListener) actualCreateListenerResult).getPoolReleaseTimes();
    assertTrue(poolReleaseTimes instanceof BasicTimer);
    Timer writeTimes = ((UdpClientListener) actualCreateListenerResult).getWriteTimes();
    assertTrue(writeTimes instanceof BasicTimer);
    MonitorConfig config2 = connectionTimes.getConfig();
    PublishingPolicy publishingPolicy = config2.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) connectionTimes).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    List<Monitor<?>> monitors2 = ((BasicTimer) flushTimes).getMonitors();
    assertEquals(4, monitors2.size());
    Monitor<?> getResult2 = monitors2.get(1);
    assertTrue(getResult2 instanceof StepCounter);
    List<Monitor<?>> monitors3 = ((BasicTimer) poolAcquireTimes).getMonitors();
    assertEquals(4, monitors3.size());
    Monitor<?> getResult3 = monitors3.get(1);
    assertTrue(getResult3 instanceof StepCounter);
    List<Monitor<?>> monitors4 = ((BasicTimer) poolReleaseTimes).getMonitors();
    assertEquals(4, monitors4.size());
    Monitor<?> getResult4 = monitors4.get(1);
    assertTrue(getResult4 instanceof StepCounter);
    List<Monitor<?>> monitors5 = ((BasicTimer) writeTimes).getMonitors();
    assertEquals(4, monitors5.size());
    Monitor<?> getResult5 = monitors5.get(1);
    assertTrue(getResult5 instanceof StepCounter);
    MonitorConfig config3 = getResult.getConfig();
    TagList tags = config3.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config2.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    MonitorConfig config4 = getResult2.getConfig();
    TagList tags3 = config4.getTags();
    assertTrue(tags3 instanceof BasicTagList);
    MonitorConfig config5 = getResult3.getConfig();
    TagList tags4 = config5.getTags();
    assertTrue(tags4 instanceof BasicTagList);
    MonitorConfig config6 = getResult4.getConfig();
    TagList tags5 = config6.getTags();
    assertTrue(tags5 instanceof BasicTagList);
    MonitorConfig config7 = getResult5.getConfig();
    TagList tags6 = config7.getTags();
    assertTrue(tags6 instanceof BasicTagList);
    assertTrue(actualCreateListenerResult instanceof UdpClientListener);
    assertEquals("connectionTimes", config2.getName());
    assertEquals("connectionTimes", config3.getName());
    assertEquals("count", nextResult.getValue());
    MonitorConfig config8 = flushTimes.getConfig();
    assertEquals("flushTimes", config8.getName());
    assertEquals("flushTimes", config4.getName());
    MonitorConfig config9 = poolAcquireTimes.getConfig();
    assertEquals("poolAcquireTimes", config9.getName());
    assertEquals("poolAcquireTimes", config5.getName());
    MonitorConfig config10 = poolReleaseTimes.getConfig();
    assertEquals("poolReleaseTimes", config10.getName());
    assertEquals("poolReleaseTimes", config6.getName());
    assertEquals("statistic", nextResult.getKey());
    MonitorConfig config11 = writeTimes.getConfig();
    assertEquals("writeTimes", config11.getName());
    assertEquals("writeTimes", config7.getName());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) connectionTimes).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) flushTimes).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) poolAcquireTimes).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) poolReleaseTimes).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) writeTimes).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) connectionTimes).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) flushTimes).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) poolAcquireTimes).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) poolReleaseTimes).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) writeTimes).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) connectionTimes).getTotalTime().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) flushTimes).getTotalTime().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) poolAcquireTimes).getTotalTime().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) poolReleaseTimes).getTotalTime().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) writeTimes).getTotalTime().doubleValue(), 0.0);
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
    assertEquals(0L, ((BasicTimer) connectionTimes).getCount().longValue());
    assertEquals(0L, ((BasicTimer) flushTimes).getCount().longValue());
    assertEquals(0L, ((BasicTimer) poolAcquireTimes).getCount().longValue());
    assertEquals(0L, ((BasicTimer) poolReleaseTimes).getCount().longValue());
    assertEquals(0L, ((BasicTimer) writeTimes).getCount().longValue());
    assertEquals(0L, connectionTimes.getValue().longValue());
    assertEquals(0L, flushTimes.getValue().longValue());
    assertEquals(0L, poolAcquireTimes.getValue().longValue());
    assertEquals(0L, poolReleaseTimes.getValue().longValue());
    assertEquals(0L, writeTimes.getValue().longValue());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, connectionTimes.getTimeUnit());
    assertEquals(TimeUnit.MILLISECONDS, flushTimes.getTimeUnit());
    assertEquals(TimeUnit.MILLISECONDS, poolAcquireTimes.getTimeUnit());
    assertEquals(TimeUnit.MILLISECONDS, poolReleaseTimes.getTimeUnit());
    assertEquals(TimeUnit.MILLISECONDS, writeTimes.getTimeUnit());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertEquals(tags, tags3);
    assertEquals(tags, tags4);
    assertEquals(tags, tags5);
    assertEquals(tags, tags6);
    assertSame(publishingPolicy, config8.getPublishingPolicy());
    assertSame(publishingPolicy, config9.getPublishingPolicy());
    assertSame(publishingPolicy, config10.getPublishingPolicy());
    assertSame(publishingPolicy, config11.getPublishingPolicy());
    assertSame(publishingPolicy, config3.getPublishingPolicy());
    assertSame(publishingPolicy, config4.getPublishingPolicy());
    assertSame(publishingPolicy, config5.getPublishingPolicy());
    assertSame(publishingPolicy, config6.getPublishingPolicy());
    assertSame(publishingPolicy, config7.getPublishingPolicy());
    assertSame(tags2, config8.getTags());
    assertSame(tags2, config9.getTags());
    assertSame(tags2, config10.getTags());
    assertSame(tags2, config11.getTags());
  }

  /**
   * Method under test:
   * {@link LoadBalancingUdpClient#LoadBalancingUdpClient(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)}
   */
  @Test
  public void testNewLoadBalancingUdpClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingUdpClient<Object, Object> actualLoadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        retryHandler, mock(PipelineConfigurator.class));

    // Assert
    LoadBalancerContext loadBalancerContext = actualLoadBalancingUdpClient.getLoadBalancerContext();
    Timer executeTracer = loadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config2 = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config2.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config3 = getResult.getConfig();
    TagList tags = config3.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config2.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", loadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config3.getName());
    assertEquals("statistic", nextResult.getKey());
    assertEquals(-1, actualLoadBalancingUdpClient.getMaxConcurrentRequests());
    assertEquals(0, loadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, loadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(7000, actualLoadBalancingUdpClient.getResponseTimeOut());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(loadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, loadBalancerContext.getLoadBalancer());
    assertSame(retryHandler, loadBalancerContext.getRetryHandler());
    assertSame(config, actualLoadBalancingUdpClient.getClientConfig());
    assertSame(publishingPolicy, config3.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancingUdpClient#LoadBalancingUdpClient(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)}
   */
  @Test
  public void testNewLoadBalancingUdpClient2() {
    // Arrange
    NoOpLoadBalancer lb = new NoOpLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingUdpClient<Object, Object> actualLoadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        retryHandler, mock(PipelineConfigurator.class));

    // Assert
    LoadBalancerContext loadBalancerContext = actualLoadBalancingUdpClient.getLoadBalancerContext();
    Timer executeTracer = loadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config2 = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config2.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config3 = getResult.getConfig();
    TagList tags = config3.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config2.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", loadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config3.getName());
    assertEquals("statistic", nextResult.getKey());
    assertEquals(-1, actualLoadBalancingUdpClient.getMaxConcurrentRequests());
    assertEquals(0, loadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, loadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(7000, actualLoadBalancingUdpClient.getResponseTimeOut());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(loadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, loadBalancerContext.getLoadBalancer());
    assertSame(retryHandler, loadBalancerContext.getRetryHandler());
    assertSame(config, actualLoadBalancingUdpClient.getClientConfig());
    assertSame(publishingPolicy, config3.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancingUdpClient#LoadBalancingUdpClient(ILoadBalancer, IClientConfig, RetryHandler, PipelineConfigurator)}
   */
  @Test
  public void testNewLoadBalancingUdpClient3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");
    NettyHttpLoadBalancerErrorHandler retryHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    LoadBalancingUdpClient<Object, Object> actualLoadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        retryHandler, mock(PipelineConfigurator.class));

    // Assert
    LoadBalancerContext loadBalancerContext = actualLoadBalancingUdpClient.getLoadBalancerContext();
    Timer executeTracer = loadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config2 = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config2.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config3 = getResult.getConfig();
    TagList tags = config3.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config2.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", config3.getName());
    assertEquals("count", nextResult.getValue());
    assertEquals("statistic", nextResult.getKey());
    assertEquals(-1, actualLoadBalancingUdpClient.getMaxConcurrentRequests());
    assertEquals(0, loadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, loadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(7000, actualLoadBalancingUdpClient.getResponseTimeOut());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(loadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, loadBalancerContext.getLoadBalancer());
    assertSame(retryHandler, loadBalancerContext.getRetryHandler());
    assertSame(config, actualLoadBalancingUdpClient.getClientConfig());
    assertSame(publishingPolicy, config3.getPublishingPolicy());
  }
}
