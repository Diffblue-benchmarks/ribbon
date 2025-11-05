package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;

public class ExecutionContextDiffblueTest {
  /**
   * Method under test: {@link ExecutionContext#getChildContext(Object)}
   */
  @Test
  public void testGetChildContext() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();
    ExecutionContext<Object> executionContext = new ExecutionContext<>("Request", requestConfig, clientConfig,
        retryHandler);

    // Act
    ExecutionContext<Object> actualChildContext = executionContext.getChildContext("Obj");

    // Assert
    assertEquals("Request", actualChildContext.getRequest());
    assertSame(retryHandler, actualChildContext.getRetryHandler());
    assertSame(requestConfig, actualChildContext.getRequestConfig());
  }

  /**
   * Method under test: {@link ExecutionContext#getChildContext(Object)}
   */
  @Test
  public void testGetChildContext2() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    requestConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();
    ExecutionContext<Object> executionContext = new ExecutionContext<>("Request", requestConfig, clientConfig,
        retryHandler);

    // Act
    ExecutionContext<Object> actualChildContext = executionContext.getChildContext("Obj");

    // Assert
    assertEquals("Request", actualChildContext.getRequest());
    assertSame(retryHandler, actualChildContext.getRetryHandler());
    assertSame(requestConfig, actualChildContext.getRequestConfig());
  }

  /**
   * Method under test: {@link ExecutionContext#get(String)}
   */
  @Test
  public void testGet() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> executionContext = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    assertNull(executionContext.get("Name"));
  }

  /**
   * Method under test: {@link ExecutionContext#get(String)}
   */
  @Test
  public void testGet2() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    requestConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> executionContext = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    assertNull(executionContext.get("Name"));
  }

  /**
   * Method under test:
   * {@link ExecutionContext#getClientProperty(IClientConfigKey)}
   */
  @Test
  public void testGetClientProperty() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> executionContext = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    assertNull(executionContext.<Object>getClientProperty(mock(IClientConfigKey.class)));
  }

  /**
   * Method under test:
   * {@link ExecutionContext#getClientProperty(IClientConfigKey)}
   */
  @Test
  public void testGetClientProperty2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> executionContext = new ExecutionContext<>("Request", null, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    assertNull(executionContext.<Object>getClientProperty(mock(IClientConfigKey.class)));
  }

  /**
   * Method under test: {@link ExecutionContext#getGlobalContext()}
   */
  @Test
  public void testGetGlobalContext() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> executionContext = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    assertSame(executionContext, executionContext.getGlobalContext());
  }

  /**
   * Method under test: {@link ExecutionContext#getGlobalContext()}
   */
  @Test
  public void testGetGlobalContext2() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    requestConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    ExecutionContext<Object> executionContext = new ExecutionContext<>("Request", requestConfig, clientConfig,
        new DefaultLoadBalancerRetryHandler());

    // Act and Assert
    assertSame(executionContext, executionContext.getGlobalContext());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ExecutionContext#ExecutionContext(Object, IClientConfig, IClientConfig, RetryHandler)}
   *   <li>{@link ExecutionContext#getRequest()}
   *   <li>{@link ExecutionContext#getRequestConfig()}
   *   <li>{@link ExecutionContext#getRetryHandler()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    ExecutionContext<Object> actualExecutionContext = new ExecutionContext<>("Request", requestConfig, clientConfig,
        retryHandler);
    Object actualRequest = actualExecutionContext.getRequest();
    IClientConfig actualRequestConfig = actualExecutionContext.getRequestConfig();

    // Assert
    assertEquals("Request", actualRequest);
    assertSame(retryHandler, actualExecutionContext.getRetryHandler());
    assertSame(requestConfig, actualRequestConfig);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ExecutionContext#ExecutionContext(Object, IClientConfig, IClientConfig, RetryHandler, ConcurrentHashMap)}
   *   <li>{@link ExecutionContext#getRequest()}
   *   <li>{@link ExecutionContext#getRequestConfig()}
   *   <li>{@link ExecutionContext#getRetryHandler()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters2() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    ExecutionContext<Object> actualExecutionContext = new ExecutionContext<>("Request", requestConfig, clientConfig,
        retryHandler, new ConcurrentHashMap<>());
    Object actualRequest = actualExecutionContext.getRequest();
    IClientConfig actualRequestConfig = actualExecutionContext.getRequestConfig();

    // Assert
    assertEquals("Request", actualRequest);
    assertSame(retryHandler, actualExecutionContext.getRetryHandler());
    assertSame(requestConfig, actualRequestConfig);
  }
}
