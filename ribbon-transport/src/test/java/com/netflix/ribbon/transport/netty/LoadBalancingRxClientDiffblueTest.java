package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.ribbon.transport.netty.http.NettyHttpLoadBalancerErrorHandler;
import com.netflix.ribbon.transport.netty.udp.LoadBalancingUdpClient;
import io.reactivex.netty.client.RxClient;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.udp.client.UdpClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class LoadBalancingRxClientDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link LoadBalancingRxClient#getResponseTimeOut()}.
   * <ul>
   *   <li>Then return {@code 7000}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getResponseTimeOut()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancingRxClient.getResponseTimeOut()"})
  public void testGetResponseTimeOut_thenReturn7000() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals(7000, loadBalancingUdpClient.getResponseTimeOut());
  }

  /**
   * Test {@link LoadBalancingRxClient#getResponseTimeOut()}.
   * <ul>
   *   <li>Then return {@code 14000}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getResponseTimeOut()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancingRxClient.getResponseTimeOut()"})
  public void testGetResponseTimeOut_thenReturn14000() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config, null,
        mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals(14000, loadBalancingUdpClient.getResponseTimeOut());
  }

  /**
   * Test {@link LoadBalancingRxClient#getResponseTimeOut()}.
   * <ul>
   *   <li>Then return {@code 112000}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getResponseTimeOut()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancingRxClient.getResponseTimeOut()"})
  public void testGetResponseTimeOut_thenReturn112000() {
    // Arrange
    RetryHandler retryHandler = mock(RetryHandler.class);
    when(retryHandler.getMaxRetriesOnNextServer()).thenReturn(3);
    when(retryHandler.getMaxRetriesOnSameServer()).thenReturn(3);
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        retryHandler, mock(PipelineConfigurator.class));

    // Act
    int actualResponseTimeOut = loadBalancingUdpClient.getResponseTimeOut();

    // Assert
    verify(retryHandler).getMaxRetriesOnNextServer();
    verify(retryHandler).getMaxRetriesOnSameServer();
    assertEquals(112000, actualResponseTimeOut);
  }

  /**
   * Test {@link LoadBalancingRxClient#getResponseTimeOut()}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getResponseTimeOut()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancingRxClient.getResponseTimeOut()"})
  public void testGetResponseTimeOut_thenThrowIllegalArgumentException() {
    // Arrange
    RetryHandler retryHandler = mock(RetryHandler.class);
    when(retryHandler.getMaxRetriesOnNextServer()).thenThrow(new IllegalArgumentException("foo"));
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        retryHandler, mock(PipelineConfigurator.class));

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    loadBalancingUdpClient.getResponseTimeOut();
    verify(retryHandler).getMaxRetriesOnNextServer();
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"})
  public void testGetProperty() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(1, 1, true), mock(PipelineConfigurator.class));
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);

    // Act and Assert
    assertEquals("Default Value",
        loadBalancingUdpClient.getProperty(key, DefaultClientConfigImpl.getEmptyConfig(), "Default Value"));
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   * <ul>
   *   <li>When ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"})
  public void testGetProperty_whenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty = loadBalancingUdpClient.getProperty(key,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"), "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   * <ul>
   *   <li>When ClientConfigWithDefaultValues empty string is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"})
  public void testGetProperty_whenClientConfigWithDefaultValuesEmptyStringIsNameSpace() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty = loadBalancingUdpClient.getProperty(key,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space"), "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   * <ul>
   *   <li>When ClientConfigWithDefaultValues {@code null} is {@code Name Space}.</li>
   *   <li>Then calls {@link IClientConfigKey#key()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"})
  public void testGetProperty_whenClientConfigWithDefaultValuesNullIsNameSpace_thenCallsKey() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty = loadBalancingUdpClient.getProperty(key,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space"), "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   * <ul>
   *   <li>When EmptyConfig.</li>
   *   <li>Then return {@code Default Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"})
  public void testGetProperty_whenEmptyConfig_thenReturnDefaultValue() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);

    // Act and Assert
    assertEquals("Default Value",
        loadBalancingUdpClient.getProperty(key, DefaultClientConfigImpl.getEmptyConfig(), "Default Value"));
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code Default Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"})
  public void testGetProperty_whenNull_thenReturnDefaultValue() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals("Default Value",
        loadBalancingUdpClient.getProperty(mock(IClientConfigKey.class), null, "Default Value"));
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"})
  public void testGetOrCreateRxClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualOrCreateRxClient = loadBalancingUdpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"})
  public void testGetOrCreateRxClient2() {
    // Arrange
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer lb = new BaseLoadBalancer(config);
    IClientConfig config2 = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config2,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualOrCreateRxClient = loadBalancingUdpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"})
  public void testGetOrCreateRxClient3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.ignoreUserTokenInConnectionPoolForSecureClient(anyBoolean())).thenReturn(Builder.newBuilder());
    IClientConfig config = builder.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer lb = new BaseLoadBalancer();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualOrCreateRxClient = loadBalancingUdpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    verify(builder).ignoreUserTokenInConnectionPoolForSecureClient(eq(true));
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link Server} {@link Server#getHost()} return {@code 42}.</li>
   *   <li>Then calls {@link Server#getHost()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"})
  public void testGetOrCreateRxClient_given42_whenServerGetHostReturn42_thenCallsGetHost() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.ignoreUserTokenInConnectionPoolForSecureClient(anyBoolean())).thenReturn(Builder.newBuilder());
    IClientConfig config = builder.ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    BaseLoadBalancer lb = new BaseLoadBalancer();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    Server server = mock(Server.class);
    when(server.getPort()).thenReturn(8080);
    when(server.getHost()).thenReturn("42");

    // Act
    RxClient<Object, Object> actualOrCreateRxClient = loadBalancingUdpClient.getOrCreateRxClient(server);

    // Assert
    verify(builder).ignoreUserTokenInConnectionPoolForSecureClient(eq(true));
    verify(server).getHost();
    verify(server).getPort();
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} addServer {@link Server#Server(String)} with id is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"})
  public void testGetOrCreateRxClient_givenBaseLoadBalancerAddServerServerWithIdIsEmptyString() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server(""));
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualOrCreateRxClient = loadBalancingUdpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
  }
}
