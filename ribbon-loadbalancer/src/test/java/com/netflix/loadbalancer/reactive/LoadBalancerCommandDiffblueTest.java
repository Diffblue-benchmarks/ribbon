package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand.Builder;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand.ExecutionInfoContext;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LoadBalancerCommandDiffblueTest {
  /**
   * Test ExecutionInfoContext getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ExecutionInfoContext#ExecutionInfoContext(LoadBalancerCommand)}
   *   <li>{@link ExecutionInfoContext#incAttemptCount()}
   *   <li>{@link ExecutionInfoContext#getAttemptCount()}
   *   <li>{@link ExecutionInfoContext#getServer()}
   *   <li>{@link ExecutionInfoContext#getServerAttemptCount()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionInfoContext.<init>(LoadBalancerCommand)",
    "int ExecutionInfoContext.getAttemptCount()",
    "Server ExecutionInfoContext.getServer()",
    "int ExecutionInfoContext.getServerAttemptCount()",
    "void ExecutionInfoContext.incAttemptCount()"
  })
  public void testExecutionInfoContextGettersAndSetters() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();
    IClientConfig requestConfig =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();
    IClientConfig clientConfig =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();

    ExecutionContext<Object> executionContext =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    Builder<Object> withExecutionContextResult =
        builderResult.withExecutionContext(executionContext);

    Builder<Object> withListenersResult =
        withExecutionContextResult.withListeners(new ArrayList<>());

    Builder<Object> withLoadBalancerResult =
        withListenersResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object> withLoadBalancerURIResult =
        withLoadBalancerResult
            .withLoadBalancerContext(new LoadBalancerContext(new BaseLoadBalancer()))
            .withServerLocator("Key")
            .withLoadBalancerURI(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    Builder<Object> withRetryHandlerResult =
        withLoadBalancerURIResult.withRetryHandler(new DefaultLoadBalancerRetryHandler());
    LoadBalancerCommand<Object> loadBalancerCommand =
        withRetryHandlerResult.withServer(new Server("42")).build();

    // Act
    ExecutionInfoContext actualExecutionInfoContext =
        loadBalancerCommand.new ExecutionInfoContext();
    actualExecutionInfoContext.incAttemptCount();
    int actualAttemptCount = actualExecutionInfoContext.getAttemptCount();
    Server actualServer = actualExecutionInfoContext.getServer();

    // Assert
    assertNull(actualServer);
    assertEquals(0, actualExecutionInfoContext.getServerAttemptCount());
    assertEquals(1, actualAttemptCount);
  }
}
