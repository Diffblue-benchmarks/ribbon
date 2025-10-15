package com.netflix.loadbalancer.reactive;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.ExecutionListener.AbortExecutionException;
import java.util.ArrayList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class ExecutionContextListenerInvokerDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionStart(ExecutionContext)} with {@code
   * ExecutionContext}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionListener#onExecutionStart(ExecutionContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionContextListenerInvoker#onExecutionStart(ExecutionContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionContextListenerInvoker.onExecutionStart(ExecutionContext)"})
  public void testOnExecutionStartWithExecutionContext_thenCallsOnExecutionStart()
      throws AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener).onExecutionStart(Mockito.<ExecutionContext<Object>>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Act
    executionContextListenerInvoker.onExecutionStart(context);

    // Assert
    verify(executionListener).onExecutionStart(isA(ExecutionContext.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionStart(ExecutionContext)} with {@code
   * ExecutionContext}.
   *
   * <ul>
   *   <li>Then throw {@link AbortExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionContextListenerInvoker#onExecutionStart(ExecutionContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionContextListenerInvoker.onExecutionStart(ExecutionContext)"})
  public void testOnExecutionStartWithExecutionContext_thenThrowAbortExecutionException()
      throws AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doThrow(new AbortExecutionException("An error occurred"))
        .when(executionListener)
        .onExecutionStart(Mockito.<ExecutionContext<Object>>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    thrown.expect(AbortExecutionException.class);
    executionContextListenerInvoker.onExecutionStart(context);
    verify(executionListener).onExecutionStart(isA(ExecutionContext.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionStart()}.
   *
   * <ul>
   *   <li>Given {@link ExecutionListener} {@link
   *       ExecutionListener#onExecutionStart(ExecutionContext)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onExecutionStart()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionContextListenerInvoker.onExecutionStart()"})
  public void testOnExecutionStart_givenExecutionListenerOnExecutionStartDoesNothing()
      throws AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener).onExecutionStart(Mockito.<ExecutionContext<Object>>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(context, listeners);

    // Act
    executionContextListenerInvoker.onExecutionStart();

    // Assert
    verify(executionListener).onExecutionStart(isA(ExecutionContext.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionStart()}.
   *
   * <ul>
   *   <li>Then throw {@link AbortExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onExecutionStart()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionContextListenerInvoker.onExecutionStart()"})
  public void testOnExecutionStart_thenThrowAbortExecutionException()
      throws AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doThrow(new AbortExecutionException("An error occurred"))
        .when(executionListener)
        .onExecutionStart(Mockito.<ExecutionContext<Object>>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(context, listeners);

    // Act and Assert
    thrown.expect(AbortExecutionException.class);
    executionContextListenerInvoker.onExecutionStart();
    verify(executionListener).onExecutionStart(isA(ExecutionContext.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionContext, ExecutionInfo)}
   * with {@code context}, {@code info}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionListener#onStartWithServer(ExecutionContext, ExecutionInfo)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionContextListenerInvoker#onStartWithServer(ExecutionContext, ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onStartWithServer(ExecutionContext, ExecutionInfo)"
  })
  public void testOnStartWithServerWithContextInfo_thenCallsOnStartWithServer()
      throws AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing()
        .when(executionListener)
        .onStartWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Act
    executionContextListenerInvoker.onStartWithServer(
        context, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onStartWithServer(isA(ExecutionContext.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionContext, ExecutionInfo)}
   * with {@code context}, {@code info}.
   *
   * <ul>
   *   <li>Then throw {@link AbortExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionContextListenerInvoker#onStartWithServer(ExecutionContext, ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onStartWithServer(ExecutionContext, ExecutionInfo)"
  })
  public void testOnStartWithServerWithContextInfo_thenThrowAbortExecutionException()
      throws AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doThrow(new AbortExecutionException("An error occurred"))
        .when(executionListener)
        .onStartWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    thrown.expect(AbortExecutionException.class);
    executionContextListenerInvoker.onStartWithServer(
        context, ExecutionInfo.create(new Server("42"), 10, 10));
    verify(executionListener)
        .onStartWithServer(isA(ExecutionContext.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionInfo)} with {@code
   * info}.
   *
   * <ul>
   *   <li>Given {@link ExecutionListener} {@link
   *       ExecutionListener#onStartWithServer(ExecutionContext, ExecutionInfo)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionContextListenerInvoker.onStartWithServer(ExecutionInfo)"})
  public void testOnStartWithServerWithInfo_givenExecutionListenerOnStartWithServerDoesNothing()
      throws AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing()
        .when(executionListener)
        .onStartWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(context, listeners);

    // Act
    executionContextListenerInvoker.onStartWithServer(
        ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onStartWithServer(isA(ExecutionContext.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionInfo)} with {@code
   * info}.
   *
   * <ul>
   *   <li>Then throw {@link AbortExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionContextListenerInvoker.onStartWithServer(ExecutionInfo)"})
  public void testOnStartWithServerWithInfo_thenThrowAbortExecutionException()
      throws AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doThrow(new AbortExecutionException("An error occurred"))
        .when(executionListener)
        .onStartWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(context, listeners);

    // Act and Assert
    thrown.expect(AbortExecutionException.class);
    executionContextListenerInvoker.onStartWithServer(
        ExecutionInfo.create(new Server("42"), 10, 10));
    verify(executionListener)
        .onStartWithServer(isA(ExecutionContext.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExceptionWithServer(ExecutionContext, Throwable,
   * ExecutionInfo)} with {@code context}, {@code exception}, {@code info}.
   *
   * <p>Method under test: {@link
   * ExecutionContextListenerInvoker#onExceptionWithServer(ExecutionContext, Throwable,
   * ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExceptionWithServer(ExecutionContext, Throwable, ExecutionInfo)"
  })
  public void testOnExceptionWithServerWithContextExceptionInfo() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doThrow(new AbortExecutionException("An error occurred"))
        .when(executionListener)
        .onExceptionWithServer(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());
    Throwable exception = new Throwable();

    // Act
    executionContextListenerInvoker.onExceptionWithServer(
        context, exception, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExceptionWithServer(
            isA(ExecutionContext.class), isA(Throwable.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExceptionWithServer(ExecutionContext, Throwable,
   * ExecutionInfo)} with {@code context}, {@code exception}, {@code info}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionListener#onExceptionWithServer(ExecutionContext, Throwable,
   *       ExecutionInfo)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionContextListenerInvoker#onExceptionWithServer(ExecutionContext, Throwable,
   * ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExceptionWithServer(ExecutionContext, Throwable, ExecutionInfo)"
  })
  public void testOnExceptionWithServerWithContextExceptionInfo_thenCallsOnExceptionWithServer() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing()
        .when(executionListener)
        .onExceptionWithServer(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());
    Throwable exception = new Throwable();

    // Act
    executionContextListenerInvoker.onExceptionWithServer(
        context, exception, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExceptionWithServer(
            isA(ExecutionContext.class), isA(Throwable.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExceptionWithServer(Throwable, ExecutionInfo)}
   * with {@code exception}, {@code info}.
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onExceptionWithServer(Throwable,
   * ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExceptionWithServer(Throwable, ExecutionInfo)"
  })
  public void testOnExceptionWithServerWithExceptionInfo() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing()
        .when(executionListener)
        .onExceptionWithServer(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(context, listeners);
    Throwable exception = new Throwable();

    // Act
    executionContextListenerInvoker.onExceptionWithServer(
        exception, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExceptionWithServer(
            isA(ExecutionContext.class), isA(Throwable.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExceptionWithServer(Throwable, ExecutionInfo)}
   * with {@code exception}, {@code info}.
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onExceptionWithServer(Throwable,
   * ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExceptionWithServer(Throwable, ExecutionInfo)"
  })
  public void testOnExceptionWithServerWithExceptionInfo2() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doThrow(new AbortExecutionException("An error occurred"))
        .when(executionListener)
        .onExceptionWithServer(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(context, listeners);
    Throwable exception = new Throwable();

    // Act
    executionContextListenerInvoker.onExceptionWithServer(
        exception, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExceptionWithServer(
            isA(ExecutionContext.class), isA(Throwable.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionSuccess(ExecutionContext, Object,
   * ExecutionInfo)} with {@code context}, {@code response}, {@code info}.
   *
   * <p>Method under test: {@link
   * ExecutionContextListenerInvoker#onExecutionSuccess(ExecutionContext, Object, ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExecutionSuccess(ExecutionContext, Object, ExecutionInfo)"
  })
  public void testOnExecutionSuccessWithContextResponseInfo() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doThrow(new AbortExecutionException("An error occurred"))
        .when(executionListener)
        .onExecutionSuccess(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Object>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Act
    executionContextListenerInvoker.onExecutionSuccess(
        context, "Response", ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExecutionSuccess(
            isA(ExecutionContext.class), isA(Object.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionSuccess(ExecutionContext, Object,
   * ExecutionInfo)} with {@code context}, {@code response}, {@code info}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionListener#onExecutionSuccess(ExecutionContext, Object,
   *       ExecutionInfo)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionContextListenerInvoker#onExecutionSuccess(ExecutionContext, Object, ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExecutionSuccess(ExecutionContext, Object, ExecutionInfo)"
  })
  public void testOnExecutionSuccessWithContextResponseInfo_thenCallsOnExecutionSuccess() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing()
        .when(executionListener)
        .onExecutionSuccess(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Object>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Act
    executionContextListenerInvoker.onExecutionSuccess(
        context, "Response", ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExecutionSuccess(
            isA(ExecutionContext.class), isA(Object.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionSuccess(Object, ExecutionInfo)} with
   * {@code response}, {@code info}.
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onExecutionSuccess(Object,
   * ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExecutionSuccess(Object, ExecutionInfo)"
  })
  public void testOnExecutionSuccessWithResponseInfo() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing()
        .when(executionListener)
        .onExecutionSuccess(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Object>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(context, listeners);

    // Act
    executionContextListenerInvoker.onExecutionSuccess(
        "Response", ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExecutionSuccess(
            isA(ExecutionContext.class), isA(Object.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionSuccess(Object, ExecutionInfo)} with
   * {@code response}, {@code info}.
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onExecutionSuccess(Object,
   * ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExecutionSuccess(Object, ExecutionInfo)"
  })
  public void testOnExecutionSuccessWithResponseInfo2() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doThrow(new AbortExecutionException("An error occurred"))
        .when(executionListener)
        .onExecutionSuccess(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Object>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(context, listeners);

    // Act
    executionContextListenerInvoker.onExecutionSuccess(
        "Response", ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExecutionSuccess(
            isA(ExecutionContext.class), isA(Object.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionFailed(ExecutionContext, Throwable,
   * ExecutionInfo)} with {@code context}, {@code finalException}, {@code info}.
   *
   * <p>Method under test: {@link
   * ExecutionContextListenerInvoker#onExecutionFailed(ExecutionContext, Throwable, ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExecutionFailed(ExecutionContext, Throwable, ExecutionInfo)"
  })
  public void testOnExecutionFailedWithContextFinalExceptionInfo() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doThrow(new AbortExecutionException("An error occurred"))
        .when(executionListener)
        .onExecutionFailed(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());
    Throwable finalException = new Throwable();

    // Act
    executionContextListenerInvoker.onExecutionFailed(
        context, finalException, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExecutionFailed(
            isA(ExecutionContext.class), isA(Throwable.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionFailed(ExecutionContext, Throwable,
   * ExecutionInfo)} with {@code context}, {@code finalException}, {@code info}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionListener#onExecutionFailed(ExecutionContext, Throwable,
   *       ExecutionInfo)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionContextListenerInvoker#onExecutionFailed(ExecutionContext, Throwable, ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExecutionFailed(ExecutionContext, Throwable, ExecutionInfo)"
  })
  public void testOnExecutionFailedWithContextFinalExceptionInfo_thenCallsOnExecutionFailed() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing()
        .when(executionListener)
        .onExecutionFailed(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());
    Throwable finalException = new Throwable();

    // Act
    executionContextListenerInvoker.onExecutionFailed(
        context, finalException, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExecutionFailed(
            isA(ExecutionContext.class), isA(Throwable.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionFailed(Throwable, ExecutionInfo)} with
   * {@code finalException}, {@code info}.
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onExecutionFailed(Throwable,
   * ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExecutionFailed(Throwable, ExecutionInfo)"
  })
  public void testOnExecutionFailedWithFinalExceptionInfo() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing()
        .when(executionListener)
        .onExecutionFailed(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(context, listeners);
    Throwable finalException = new Throwable();

    // Act
    executionContextListenerInvoker.onExecutionFailed(
        finalException, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExecutionFailed(
            isA(ExecutionContext.class), isA(Throwable.class), isA(ExecutionInfo.class));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionFailed(Throwable, ExecutionInfo)} with
   * {@code finalException}, {@code info}.
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onExecutionFailed(Throwable,
   * ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContextListenerInvoker.onExecutionFailed(Throwable, ExecutionInfo)"
  })
  public void testOnExecutionFailedWithFinalExceptionInfo2() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doThrow(new AbortExecutionException("An error occurred"))
        .when(executionListener)
        .onExecutionFailed(
            Mockito.<ExecutionContext<Object>>any(),
            Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> context =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(context, listeners);
    Throwable finalException = new Throwable();

    // Act
    executionContextListenerInvoker.onExecutionFailed(
        finalException, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener)
        .onExecutionFailed(
            isA(ExecutionContext.class), isA(Throwable.class), isA(ExecutionInfo.class));
  }
}
