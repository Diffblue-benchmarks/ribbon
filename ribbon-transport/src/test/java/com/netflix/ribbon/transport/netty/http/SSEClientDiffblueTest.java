package com.netflix.ribbon.transport.netty.http;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient.Builder;
import io.reactivex.netty.protocol.text.sse.ServerSentEvent;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SSEClientDiffblueTest {
  /**
   * Test {@link SSEClient#sseClientBuilder()}.
   *
   * <p>Method under test: {@link SSEClient#sseClientBuilder()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder SSEClient.sseClientBuilder()"})
  public void testSseClientBuilder() {
    // Arrange and Act
    Builder<Object, ServerSentEvent> actualSseClientBuilderResult = SSEClient.sseClientBuilder();

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
