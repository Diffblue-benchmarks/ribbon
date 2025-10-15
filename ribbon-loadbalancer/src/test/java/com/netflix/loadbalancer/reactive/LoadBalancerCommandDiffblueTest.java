package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.ExecutionListener.AbortExecutionException;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand.Builder;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand.ExecutionInfoContext;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class LoadBalancerCommandDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test Builder {@link Builder#withListeners(List)}.
   *
   * <ul>
   *   <li>Given builder withListeners {@link ArrayList#ArrayList()}.
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_givenBuilderWithListenersArrayList_whenArrayList() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();
    builderResult.withListeners(new ArrayList<>());

    // Act
    Builder<Object> actualWithListenersResult = builderResult.withListeners(new ArrayList<>());

    // Assert
    assertSame(builderResult, actualWithListenersResult);
  }

  /**
   * Test Builder {@link Builder#withListeners(List)}.
   *
   * <ul>
   *   <li>Given builder.
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_givenBuilder_whenArrayList() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    // Act
    Builder<Object> actualWithListenersResult = builderResult.withListeners(new ArrayList<>());

    // Assert
    assertSame(builderResult, actualWithListenersResult);
  }

  /**
   * Test Builder {@link Builder#withListeners(List)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionListener}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_givenExecutionListener() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    ArrayList<ExecutionListener<?, Object>> listeners = new ArrayList<>();
    listeners.add(mock(ExecutionListener.class));

    // Act
    Builder<Object> actualWithListenersResult = builderResult.withListeners(listeners);

    // Assert
    assertSame(builderResult, actualWithListenersResult);
  }

  /**
   * Test Builder {@link Builder#withListeners(List)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionListener}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_givenExecutionListener2() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    ArrayList<ExecutionListener<?, Object>> listeners = new ArrayList<>();
    listeners.add(mock(ExecutionListener.class));
    listeners.add(mock(ExecutionListener.class));

    // Act
    Builder<Object> actualWithListenersResult = builderResult.withListeners(listeners);

    // Assert
    assertSame(builderResult, actualWithListenersResult);
  }

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

    Builder<Object> withClientConfigResult =
        builderResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());
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
        withClientConfigResult.withExecutionContext(executionContext);

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

  /**
   * Test ExecutionInfoContext {@link ExecutionInfoContext#setServer(Server)}.
   *
   * <p>Method under test: {@link ExecutionInfoContext#setServer(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionInfoContext.setServer(Server)"})
  public void testExecutionInfoContextSetServer() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    Builder<Object> withClientConfigResult =
        builderResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());
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
        withClientConfigResult.withExecutionContext(executionContext);

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
    ExecutionInfoContext executionInfoContext = loadBalancerCommand.new ExecutionInfoContext();
    Server server = new Server("42");

    // Act
    executionInfoContext.setServer(server);

    // Assert
    assertEquals(1, executionInfoContext.getServerAttemptCount());
    assertSame(server, executionInfoContext.getServer());
  }

  /**
   * Test ExecutionInfoContext {@link ExecutionInfoContext#toExecutionInfo()}.
   *
   * <p>Method under test: {@link ExecutionInfoContext#toExecutionInfo()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionInfo ExecutionInfoContext.toExecutionInfo()"})
  public void testExecutionInfoContextToExecutionInfo() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    Builder<Object> withClientConfigResult =
        builderResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());
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
        withClientConfigResult.withExecutionContext(executionContext);

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
    ExecutionInfo actualToExecutionInfoResult =
        loadBalancerCommand.new ExecutionInfoContext().toExecutionInfo();

    // Assert
    assertNull(actualToExecutionInfoResult.getServer());
    assertEquals(-1, actualToExecutionInfoResult.getNumberOfPastAttemptsOnServer());
    assertEquals(-1, actualToExecutionInfoResult.getNumberOfPastServersAttempted());
  }

  /**
   * Test ExecutionInfoContext {@link ExecutionInfoContext#toFinalExecutionInfo()}.
   *
   * <p>Method under test: {@link ExecutionInfoContext#toFinalExecutionInfo()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionInfo ExecutionInfoContext.toFinalExecutionInfo()"})
  public void testExecutionInfoContextToFinalExecutionInfo() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    Builder<Object> withClientConfigResult =
        builderResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());
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
        withClientConfigResult.withExecutionContext(executionContext);

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
    ExecutionInfo actualToFinalExecutionInfoResult =
        loadBalancerCommand.new ExecutionInfoContext().toFinalExecutionInfo();

    // Assert
    assertNull(actualToFinalExecutionInfoResult.getServer());
    assertEquals(-1, actualToFinalExecutionInfoResult.getNumberOfPastServersAttempted());
    assertEquals(0, actualToFinalExecutionInfoResult.getNumberOfPastAttemptsOnServer());
  }

  /**
   * Test {@link LoadBalancerCommand#submit(ServerOperation)}.
   *
   * <ul>
   *   <li>Then throw {@link AbortExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerCommand#submit(ServerOperation)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"rx.Observable LoadBalancerCommand.submit(ServerOperation)"})
  public void testSubmit_thenThrowAbortExecutionException() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    Builder<Object> withClientConfigResult =
        builderResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());
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
        withClientConfigResult.withExecutionContext(executionContext);

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
    withRetryHandlerResult.withServer(new Server("42")).build();

    DefaultLoadBalancerRetryHandler retryHandler = mock(DefaultLoadBalancerRetryHandler.class);
    when(retryHandler.getMaxRetriesOnSameServer())
        .thenThrow(new AbortExecutionException("An error occurred"));

    Builder<Object> builderResult2 = LoadBalancerCommand.builder();

    Builder<Object> withClientConfigResult2 =
        builderResult2.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());
    IClientConfig requestConfig2 =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();
    IClientConfig clientConfig2 =
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build();

    ExecutionContext<Object> executionContext2 =
        new ExecutionContext<>(
            "Request", requestConfig2, clientConfig2, new DefaultLoadBalancerRetryHandler());

    Builder<Object> withExecutionContextResult2 =
        withClientConfigResult2.withExecutionContext(executionContext2);

    Builder<Object> withListenersResult2 =
        withExecutionContextResult2.withListeners(new ArrayList<>());

    Builder<Object> withLoadBalancerResult2 =
        withListenersResult2.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object> withRetryHandlerResult2 =
        withLoadBalancerResult2
            .withLoadBalancerContext(new LoadBalancerContext(new BaseLoadBalancer()))
            .withServerLocator("Key")
            .withLoadBalancerURI(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri())
            .withRetryHandler(retryHandler);

    // Act and Assert
    thrown.expect(AbortExecutionException.class);
    withRetryHandlerResult2
        .withServer(new Server("42"))
        .build()
        .submit(mock(ServerOperation.class));
    verify(retryHandler).getMaxRetriesOnSameServer();
  }
}
