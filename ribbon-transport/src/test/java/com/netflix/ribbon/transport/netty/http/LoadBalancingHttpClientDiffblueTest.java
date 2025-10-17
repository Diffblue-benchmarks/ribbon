package com.netflix.ribbon.transport.netty.http;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.ribbon.transport.netty.RibbonTransport;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient.Builder;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpRequestHeaders;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import rx.functions.Func1;
import rx.functions.Func2;

public class LoadBalancingHttpClientDiffblueTest {
  /**
   * Test {@link LoadBalancingHttpClient#setHostHeader(HttpClientRequest, String)}.
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#setHostHeader(HttpClientRequest, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.setHostHeader(HttpClientRequest, String)"})
  public void testSetHostHeader() {
    // Arrange
    HttpClientRequest<?> request = mock(HttpClientRequest.class);
    HttpVersion httpVersion = new HttpVersion("https://example.org/example", 1, 1, true);
    DefaultFullHttpRequest nettyRequest =
        new DefaultFullHttpRequest(
            httpVersion,
            HttpMethod.valueOf("https://example.org/example"),
            "https://example.org/example");
    when(request.getHeaders()).thenReturn(new HttpRequestHeaders(nettyRequest));

    // Act
    LoadBalancingHttpClient.setHostHeader(request, "https://example.org/example");

    // Assert
    verify(request).getHeaders();
  }

  /**
   * Test {@link LoadBalancingHttpClient#setHostHeader(HttpClientRequest, String)}.
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#setHostHeader(HttpClientRequest, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.setHostHeader(HttpClientRequest, String)"})
  public void testSetHostHeader2() {
    // Arrange
    HttpVersion httpVersion = new HttpVersion("https://example.org/example", 1, 1, true);
    DefaultFullHttpRequest nettyRequest =
        new DefaultFullHttpRequest(
            httpVersion,
            HttpMethod.valueOf("https://example.org/example"),
            "https://example.org/example");

    HttpRequestHeaders httpRequestHeaders = new HttpRequestHeaders(nettyRequest);
    httpRequestHeaders.add("Host", "Value");

    HttpClientRequest<?> request = mock(HttpClientRequest.class);
    when(request.getHeaders()).thenReturn(httpRequestHeaders);

    // Act
    LoadBalancingHttpClient.setHostHeader(request, "https://example.org/example");

    // Assert
    verify(request).getHeaders();
  }

  /**
   * Test {@link LoadBalancingHttpClient#setHostHeader(HttpClientRequest, String)}.
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#setHostHeader(HttpClientRequest, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancingHttpClient.setHostHeader(HttpClientRequest, String)"})
  public void testSetHostHeader3() {
    // Arrange
    HttpVersion httpVersion = new HttpVersion("https://example.org/example", 1, 1, true);
    DefaultFullHttpRequest nettyRequest =
        new DefaultFullHttpRequest(
            httpVersion,
            HttpMethod.valueOf("https://example.org/example"),
            "https://example.org/example");

    HttpRequestHeaders httpRequestHeaders = new HttpRequestHeaders(nettyRequest);
    httpRequestHeaders.setHost((CharSequence) Names.ACCEPT);
    httpRequestHeaders.add("Host", "Value");

    HttpClientRequest<?> request = mock(HttpClientRequest.class);
    when(request.getHeaders()).thenReturn(httpRequestHeaders);

    // Act
    LoadBalancingHttpClient.setHostHeader(request, "https://example.org/example");

    // Assert
    verify(request).getHeaders();
  }

  /**
   * Test {@link LoadBalancingHttpClient#getRxClients()}.
   *
   * <p>Method under test: {@link LoadBalancingHttpClient#getRxClients()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Map LoadBalancingHttpClient.getRxClients()"})
  public void testGetRxClients() {
    // Arrange
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withLoadBalancerResult =
        withBackoffStrategyResult.withLoadBalancer(new BaseLoadBalancer());

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
}
