package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import com.netflix.client.config.IClientConfigKey;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExecutionContextDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@link ConcurrentHashMap#ConcurrentHashMap()}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ExecutionContext#ExecutionContext(Object, IClientConfig, IClientConfig,
   *       RetryHandler, ConcurrentHashMap)}
   *   <li>{@link ExecutionContext#getRequest()}
   *   <li>{@link ExecutionContext#getRequestConfig()}
   *   <li>{@link ExecutionContext#getRetryHandler()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContext.<init>(Object, IClientConfig, IClientConfig, RetryHandler)",
    "void ExecutionContext.<init>(Object, IClientConfig, IClientConfig, RetryHandler, ConcurrentHashMap)",
    "Object ExecutionContext.getRequest()",
    "IClientConfig ExecutionContext.getRequestConfig()",
    "RetryHandler ExecutionContext.getRetryHandler()"
  })
  public void testGettersAndSetters_whenConcurrentHashMap() {
    // Arrange
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    ExecutionContext<Object> actualExecutionContext =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, retryHandler, new ConcurrentHashMap<>());
    Object actualRequest = actualExecutionContext.getRequest();
    IClientConfig actualRequestConfig = actualExecutionContext.getRequestConfig();

    // Assert
    assertEquals("Request", actualRequest);
    assertSame(retryHandler, actualExecutionContext.getRetryHandler());
    assertSame(requestConfig, actualRequestConfig);
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler()}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ExecutionContext#ExecutionContext(Object, IClientConfig, IClientConfig,
   *       RetryHandler)}
   *   <li>{@link ExecutionContext#getRequest()}
   *   <li>{@link ExecutionContext#getRequestConfig()}
   *   <li>{@link ExecutionContext#getRetryHandler()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionContext.<init>(Object, IClientConfig, IClientConfig, RetryHandler)",
    "void ExecutionContext.<init>(Object, IClientConfig, IClientConfig, RetryHandler, ConcurrentHashMap)",
    "Object ExecutionContext.getRequest()",
    "IClientConfig ExecutionContext.getRequestConfig()",
    "RetryHandler ExecutionContext.getRetryHandler()"
  })
  public void testGettersAndSetters_whenDefaultLoadBalancerRetryHandler() {
    // Arrange
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    ExecutionContext<Object> actualExecutionContext =
        new ExecutionContext<>("Request", requestConfig, clientConfig, retryHandler);
    Object actualRequest = actualExecutionContext.getRequest();
    IClientConfig actualRequestConfig = actualExecutionContext.getRequestConfig();

    // Assert
    assertEquals("Request", actualRequest);
    assertSame(retryHandler, actualExecutionContext.getRetryHandler());
    assertSame(requestConfig, actualRequestConfig);
  }

  /**
   * Test {@link ExecutionContext#getChildContext(Object)}.
   *
   * <ul>
   *   <li>Then RetryHandler return {@link DefaultLoadBalancerRetryHandler}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionContext#getChildContext(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionContext ExecutionContext.getChildContext(Object)"})
  public void testGetChildContext_thenRetryHandlerReturnDefaultLoadBalancerRetryHandler() {
    // Arrange
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    ExecutionContext<Object> executionContext =
        new ExecutionContext<>("Request", requestConfig, clientConfig, retryHandler);

    // Act
    ExecutionContext<Object> actualChildContext = executionContext.getChildContext("Obj");

    // Assert
    RetryHandler retryHandler2 = actualChildContext.getRetryHandler();
    assertTrue(retryHandler2 instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(actualChildContext.getRequestConfig() instanceof DefaultClientConfigImpl);
    assertEquals("Request", actualChildContext.getRequest());
    assertSame(retryHandler, retryHandler2);
  }

  /**
   * Test {@link ExecutionContext#get(String)}.
   *
   * <p>Method under test: {@link ExecutionContext#get(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ExecutionContext.get(String)"})
  public void testGet() {
    // Arrange
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> executionContext =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    assertNull(executionContext.get("Name"));
  }

  /**
   * Test {@link ExecutionContext#getClientProperty(IClientConfigKey)}.
   *
   * <p>Method under test: {@link ExecutionContext#getClientProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ExecutionContext.getClientProperty(IClientConfigKey)"})
  public void testGetClientProperty() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> executionContext =
        new ExecutionContext<>(
            "Request", null, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    assertNull(executionContext.getClientProperty(mock(IClientConfigKey.class)));
  }

  /**
   * Test {@link ExecutionContext#getClientProperty(IClientConfigKey)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionContext#getClientProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ExecutionContext.getClientProperty(IClientConfigKey)"})
  public void testGetClientProperty_thenReturnNull() {
    // Arrange
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> executionContext =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    assertNull(executionContext.getClientProperty(mock(IClientConfigKey.class)));
  }

  /**
   * Test {@link ExecutionContext#getGlobalContext()}.
   *
   * <p>Method under test: {@link ExecutionContext#getGlobalContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionContext ExecutionContext.getGlobalContext()"})
  public void testGetGlobalContext() {
    // Arrange
    IClientConfig requestConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    ExecutionContext<Object> executionContext =
        new ExecutionContext<>(
            "Request", requestConfig, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Act
    ExecutionContext<Object> actualGlobalContext = executionContext.getGlobalContext();

    // Assert
    assertSame(executionContext, actualGlobalContext);
  }
}
