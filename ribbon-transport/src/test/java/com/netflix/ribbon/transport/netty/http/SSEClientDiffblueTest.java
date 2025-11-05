package com.netflix.ribbon.transport.netty.http;

import static org.junit.Assert.assertNull;
import io.reactivex.netty.protocol.text.sse.ServerSentEvent;
import org.junit.Test;

public class SSEClientDiffblueTest {
  /**
   * Method under test: {@link SSEClient#sseClientBuilder()}
   */
  @Test
  public void testSseClientBuilder() {
    // Arrange and Act
    LoadBalancingHttpClient.Builder<Object, ServerSentEvent> actualSseClientBuilderResult = SSEClient
        .sseClientBuilder();

    // Assert
    assertNull(actualSseClientBuilderResult.retryHandler);
    assertNull(actualSseClientBuilderResult.config);
    assertNull(actualSseClientBuilderResult.lb);
    assertNull(actualSseClientBuilderResult.pipelineConfigurator);
    assertNull(actualSseClientBuilderResult.listeners);
    assertNull(actualSseClientBuilderResult.poolCleanerScheduler);
    assertNull(actualSseClientBuilderResult.backoffStrategy);
    assertNull(actualSseClientBuilderResult.responseToErrorPolicy);
  }
}
