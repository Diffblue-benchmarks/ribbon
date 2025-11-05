package com.netflix.ribbon.transport.netty.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.RequestSpecificRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.ribbon.transport.netty.RibbonTransport;
import com.netflix.servo.monitor.BasicTimer;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import io.reactivex.netty.protocol.http.client.HttpRequestHeaders;
import io.reactivex.netty.servo.http.HttpClientListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class LoadBalancingHttpClientDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link LoadBalancingHttpClient.Builder#build()}
   *   <li>{@link LoadBalancingHttpClient.Builder#withBackoffStrategy(Func1)}
   *   <li>{@link LoadBalancingHttpClient.Builder#withClientConfig(IClientConfig)}
   *   <li>{@link LoadBalancingHttpClient.Builder#withExecutorListeners(List)}
   *   <li>{@link LoadBalancingHttpClient.Builder#withLoadBalancer(ILoadBalancer)}
   *   <li>
   * {@link LoadBalancingHttpClient.Builder#withPipelineConfigurator(PipelineConfigurator)}
   *   <li>
   * {@link LoadBalancingHttpClient.Builder#withPoolCleanerScheduler(ScheduledExecutorService)}
   *   <li>{@link LoadBalancingHttpClient.Builder#withResponseToErrorPolicy(Func2)}
   *   <li>{@link LoadBalancingHttpClient.Builder#withRetryHandler(RetryHandler)}
   * </ul>
   */
  @Test
  public void testBuilderBuild() {
    // Arrange
    LoadBalancingHttpClient.Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();
    LoadBalancingHttpClient.Builder<Object, Object> withBackoffStrategyResult = builderResult
        .withBackoffStrategy(mock(Func1.class));
    LoadBalancingHttpClient.Builder<Object, Object> withClientConfigResult = withBackoffStrategyResult
        .withClientConfig(DefaultClientConfigImpl.getEmptyConfig());
    LoadBalancingHttpClient.Builder<Object, Object> withExecutorListenersResult = withClientConfigResult
        .withExecutorListeners(new ArrayList<>());
    LoadBalancingHttpClient.Builder<Object, Object> withResponseToErrorPolicyResult = withExecutorListenersResult
        .withLoadBalancer(new BaseLoadBalancer())
        .withPipelineConfigurator(mock(PipelineConfigurator.class))
        .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
        .withResponseToErrorPolicy(mock(Func2.class));

    // Act
    LoadBalancingHttpClient<Object, Object> actualBuildResult = withResponseToErrorPolicyResult
        .withRetryHandler(new NettyHttpLoadBalancerErrorHandler())
        .build();

    // Assert
    LoadBalancerContext loadBalancerContext = actualBuildResult.getLoadBalancerContext();
    assertTrue(loadBalancerContext.getRetryHandler() instanceof RequestSpecificRetryHandler);
    assertTrue(loadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    HttpClientListener listener = actualBuildResult.getListener();
    assertTrue(listener.getRequestWriteTimes() instanceof BasicTimer);
    assertTrue(listener.getResponseReadTimes() instanceof BasicTimer);
    assertTrue(listener.getConnectionTimes() instanceof BasicTimer);
    assertTrue(listener.getFlushTimes() instanceof BasicTimer);
    assertTrue(listener.getPoolAcquireTimes() instanceof BasicTimer);
    assertTrue(listener.getPoolReleaseTimes() instanceof BasicTimer);
    assertTrue(listener.getWriteTimes() instanceof BasicTimer);
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
    assertEquals(200, actualBuildResult.getMaxConcurrentRequests());
    assertEquals(7000, actualBuildResult.getResponseTimeOut());
    assertFalse(loadBalancerContext.isOkToRetryOnAllOperations());
    assertTrue(actualBuildResult.getRxClients().isEmpty());
  }

  /**
   * Method under test:
   * {@link LoadBalancingHttpClient#setHostHeader(HttpClientRequest, String)}
   */
  @Test
  public void testSetHostHeader() {
    // Arrange
    HttpClientRequest<?> request = mock(HttpClientRequest.class);
    HttpVersion httpVersion = new HttpVersion("https://example.org/example", 1, 1, true);

    when(request.getHeaders()).thenReturn(new HttpRequestHeaders(new DefaultFullHttpRequest(httpVersion,
        HttpMethod.valueOf("https://example.org/example"), "https://example.org/example")));

    // Act
    LoadBalancingHttpClient.setHostHeader(request, "https://example.org/example");

    // Assert
    verify(request).getHeaders();
  }
}
