package com.netflix.loadbalancer.reactive;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.loadbalancer.Server;
import java.util.ArrayList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class ExecutionContextListenerInvokerDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test: {@link ExecutionContextListenerInvoker#onExecutionStart()}
   */
  @Test
  public void testOnExecutionStart() throws ExecutionListener.AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener).onExecutionStart(Mockito.<ExecutionContext<Object>>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()),
        listeners);

    // Act
    executionContextListenerInvoker.onExecutionStart();

    // Assert
    verify(executionListener).onExecutionStart(isA(ExecutionContext.class));
  }

  /**
   * Method under test: {@link ExecutionContextListenerInvoker#onExecutionStart()}
   */
  @Test
  public void testOnExecutionStart2() throws ExecutionListener.AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener).onExecutionStart(Mockito.<ExecutionContext<Object>>any());
    ExecutionListener<Object, Object> executionListener2 = mock(ExecutionListener.class);
    doThrow(new ExecutionListener.AbortExecutionException("An error occurred")).when(executionListener2)
        .onExecutionStart(Mockito.<ExecutionContext<Object>>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener2);
    listeners.add(executionListener);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()),
        listeners);

    // Act and Assert
    thrown.expect(ExecutionListener.AbortExecutionException.class);
    executionContextListenerInvoker.onExecutionStart();
    verify(executionListener2).onExecutionStart(isA(ExecutionContext.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExecutionStart(ExecutionContext)}
   */
  @Test
  public void testOnExecutionStart3() throws ExecutionListener.AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener).onExecutionStart(Mockito.<ExecutionContext<Object>>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        listeners);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    executionContextListenerInvoker.onExecutionStart(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()));

    // Assert
    verify(executionListener).onExecutionStart(isA(ExecutionContext.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExecutionStart(ExecutionContext)}
   */
  @Test
  public void testOnExecutionStart4() throws ExecutionListener.AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener).onExecutionStart(Mockito.<ExecutionContext<Object>>any());
    ExecutionListener<Object, Object> executionListener2 = mock(ExecutionListener.class);
    doThrow(new ExecutionListener.AbortExecutionException("An error occurred")).when(executionListener2)
        .onExecutionStart(Mockito.<ExecutionContext<Object>>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener2);
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        listeners);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(ExecutionListener.AbortExecutionException.class);
    executionContextListenerInvoker.onExecutionStart(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()));
    verify(executionListener2).onExecutionStart(isA(ExecutionContext.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionContext, ExecutionInfo)}
   */
  @Test
  public void testOnStartWithServer() throws ExecutionListener.AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onStartWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        listeners);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> context = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    // Act
    executionContextListenerInvoker.onStartWithServer(context, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener).onStartWithServer(isA(ExecutionContext.class), isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionContext, ExecutionInfo)}
   */
  @Test
  public void testOnStartWithServer2() throws ExecutionListener.AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onStartWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<ExecutionInfo>any());
    ExecutionListener<Object, Object> executionListener2 = mock(ExecutionListener.class);
    doThrow(new ExecutionListener.AbortExecutionException("An error occurred")).when(executionListener2)
        .onStartWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener2);
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        listeners);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> context = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    thrown.expect(ExecutionListener.AbortExecutionException.class);
    executionContextListenerInvoker.onStartWithServer(context, ExecutionInfo.create(new Server("42"), 10, 10));
    verify(executionListener2).onStartWithServer(isA(ExecutionContext.class), isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionInfo)}
   */
  @Test
  public void testOnStartWithServer3() throws ExecutionListener.AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onStartWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()),
        listeners);

    // Act
    executionContextListenerInvoker.onStartWithServer(ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener).onStartWithServer(isA(ExecutionContext.class), isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionInfo)}
   */
  @Test
  public void testOnStartWithServer4() throws ExecutionListener.AbortExecutionException {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onStartWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<ExecutionInfo>any());
    ExecutionListener<Object, Object> executionListener2 = mock(ExecutionListener.class);
    doThrow(new ExecutionListener.AbortExecutionException("An error occurred")).when(executionListener2)
        .onStartWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener2);
    listeners.add(executionListener);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()),
        listeners);

    // Act and Assert
    thrown.expect(ExecutionListener.AbortExecutionException.class);
    executionContextListenerInvoker.onStartWithServer(ExecutionInfo.create(new Server("42"), 10, 10));
    verify(executionListener2).onStartWithServer(isA(ExecutionContext.class), isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExceptionWithServer(ExecutionContext, Throwable, ExecutionInfo)}
   */
  @Test
  public void testOnExceptionWithServer() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExceptionWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        listeners);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> context = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    Throwable exception = new Throwable();

    // Act
    executionContextListenerInvoker.onExceptionWithServer(context, exception,
        ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener).onExceptionWithServer(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExceptionWithServer(ExecutionContext, Throwable, ExecutionInfo)}
   */
  @Test
  public void testOnExceptionWithServer2() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExceptionWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());
    ExecutionListener<Object, Object> executionListener2 = mock(ExecutionListener.class);
    doThrow(new ExecutionListener.AbortExecutionException("An error occurred")).when(executionListener2)
        .onExceptionWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener2);
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        listeners);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> context = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    Throwable exception = new Throwable();

    // Act
    executionContextListenerInvoker.onExceptionWithServer(context, exception,
        ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener2).onExceptionWithServer(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
    verify(executionListener).onExceptionWithServer(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExceptionWithServer(Throwable, ExecutionInfo)}
   */
  @Test
  public void testOnExceptionWithServer3() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExceptionWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()),
        listeners);
    Throwable exception = new Throwable();

    // Act
    executionContextListenerInvoker.onExceptionWithServer(exception, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener).onExceptionWithServer(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExceptionWithServer(Throwable, ExecutionInfo)}
   */
  @Test
  public void testOnExceptionWithServer4() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExceptionWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());
    ExecutionListener<Object, Object> executionListener2 = mock(ExecutionListener.class);
    doThrow(new ExecutionListener.AbortExecutionException("An error occurred")).when(executionListener2)
        .onExceptionWithServer(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener2);
    listeners.add(executionListener);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()),
        listeners);
    Throwable exception = new Throwable();

    // Act
    executionContextListenerInvoker.onExceptionWithServer(exception, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener2).onExceptionWithServer(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
    verify(executionListener).onExceptionWithServer(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExecutionSuccess(ExecutionContext, Object, ExecutionInfo)}
   */
  @Test
  public void testOnExecutionSuccess() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExecutionSuccess(Mockito.<ExecutionContext<Object>>any(), Mockito.<Object>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        listeners);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> context = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    // Act
    executionContextListenerInvoker.onExecutionSuccess(context, "Response",
        ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener).onExecutionSuccess(isA(ExecutionContext.class), isA(Object.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExecutionSuccess(ExecutionContext, Object, ExecutionInfo)}
   */
  @Test
  public void testOnExecutionSuccess2() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExecutionSuccess(Mockito.<ExecutionContext<Object>>any(), Mockito.<Object>any(),
            Mockito.<ExecutionInfo>any());
    ExecutionListener<Object, Object> executionListener2 = mock(ExecutionListener.class);
    doThrow(new ExecutionListener.AbortExecutionException("An error occurred")).when(executionListener2)
        .onExecutionSuccess(Mockito.<ExecutionContext<Object>>any(), Mockito.<Object>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener2);
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        listeners);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> context = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    // Act
    executionContextListenerInvoker.onExecutionSuccess(context, "Response",
        ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener2).onExecutionSuccess(isA(ExecutionContext.class), isA(Object.class),
        isA(ExecutionInfo.class));
    verify(executionListener).onExecutionSuccess(isA(ExecutionContext.class), isA(Object.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExecutionSuccess(Object, ExecutionInfo)}
   */
  @Test
  public void testOnExecutionSuccess3() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExecutionSuccess(Mockito.<ExecutionContext<Object>>any(), Mockito.<Object>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()),
        listeners);

    // Act
    executionContextListenerInvoker.onExecutionSuccess("Response", ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener).onExecutionSuccess(isA(ExecutionContext.class), isA(Object.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExecutionSuccess(Object, ExecutionInfo)}
   */
  @Test
  public void testOnExecutionSuccess4() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExecutionSuccess(Mockito.<ExecutionContext<Object>>any(), Mockito.<Object>any(),
            Mockito.<ExecutionInfo>any());
    ExecutionListener<Object, Object> executionListener2 = mock(ExecutionListener.class);
    doThrow(new ExecutionListener.AbortExecutionException("An error occurred")).when(executionListener2)
        .onExecutionSuccess(Mockito.<ExecutionContext<Object>>any(), Mockito.<Object>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener2);
    listeners.add(executionListener);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()),
        listeners);

    // Act
    executionContextListenerInvoker.onExecutionSuccess("Response", ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener2).onExecutionSuccess(isA(ExecutionContext.class), isA(Object.class),
        isA(ExecutionInfo.class));
    verify(executionListener).onExecutionSuccess(isA(ExecutionContext.class), isA(Object.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExecutionFailed(ExecutionContext, Throwable, ExecutionInfo)}
   */
  @Test
  public void testOnExecutionFailed() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExecutionFailed(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        listeners);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> context = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    Throwable finalException = new Throwable();

    // Act
    executionContextListenerInvoker.onExecutionFailed(context, finalException,
        ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener).onExecutionFailed(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExecutionFailed(ExecutionContext, Throwable, ExecutionInfo)}
   */
  @Test
  public void testOnExecutionFailed2() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExecutionFailed(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());
    ExecutionListener<Object, Object> executionListener2 = mock(ExecutionListener.class);
    doThrow(new ExecutionListener.AbortExecutionException("An error occurred")).when(executionListener2)
        .onExecutionFailed(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener2);
    listeners.add(executionListener);
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        listeners);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> context = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    Throwable finalException = new Throwable();

    // Act
    executionContextListenerInvoker.onExecutionFailed(context, finalException,
        ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener2).onExecutionFailed(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
    verify(executionListener).onExecutionFailed(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExecutionFailed(Throwable, ExecutionInfo)}
   */
  @Test
  public void testOnExecutionFailed3() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExecutionFailed(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()),
        listeners);
    Throwable finalException = new Throwable();

    // Act
    executionContextListenerInvoker.onExecutionFailed(finalException, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener).onExecutionFailed(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
  }

  /**
   * Method under test:
   * {@link ExecutionContextListenerInvoker#onExecutionFailed(Throwable, ExecutionInfo)}
   */
  @Test
  public void testOnExecutionFailed4() {
    // Arrange
    ExecutionListener<Object, Object> executionListener = mock(ExecutionListener.class);
    doNothing().when(executionListener)
        .onExecutionFailed(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());
    ExecutionListener<Object, Object> executionListener2 = mock(ExecutionListener.class);
    doThrow(new ExecutionListener.AbortExecutionException("An error occurred")).when(executionListener2)
        .onExecutionFailed(Mockito.<ExecutionContext<Object>>any(), Mockito.<Throwable>any(),
            Mockito.<ExecutionInfo>any());

    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(executionListener2);
    listeners.add(executionListener);
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker = new ExecutionContextListenerInvoker<>(
        new ExecutionContext<>("Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler()),
        listeners);
    Throwable finalException = new Throwable();

    // Act
    executionContextListenerInvoker.onExecutionFailed(finalException, ExecutionInfo.create(new Server("42"), 10, 10));

    // Assert
    verify(executionListener2).onExecutionFailed(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
    verify(executionListener).onExecutionFailed(isA(ExecutionContext.class), isA(Throwable.class),
        isA(ExecutionInfo.class));
  }
}
