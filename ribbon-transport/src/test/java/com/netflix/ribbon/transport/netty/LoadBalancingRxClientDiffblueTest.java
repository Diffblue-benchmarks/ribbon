package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.LoadBalancerContext;
import com.netflix.loadbalancer.Server;
import com.netflix.ribbon.transport.netty.tcp.LoadBalancingTcpClient;
import com.netflix.ribbon.transport.netty.udp.LoadBalancingUdpClient;
import io.reactivex.netty.client.RxClient;
import io.reactivex.netty.client.RxClientImpl;
import io.reactivex.netty.metrics.MetricEventsListener;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.udp.client.UdpClient;
import io.reactivex.netty.servo.http.HttpClientListener;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import rx.Subscription;
import rx.subscriptions.BooleanSubscription;

public class LoadBalancingRxClientDiffblueTest {
  /**
   * Test {@link LoadBalancingRxClient#getClientConfig()}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getClientConfig()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig LoadBalancingRxClient.getClientConfig()"})
  public void testGetClientConfig() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act
    IClientConfig actualClientConfig = loadBalancingUdpClient.getClientConfig();

    // Assert
    assertSame(loadBalancingUdpClient.clientConfig, actualClientConfig);
  }

  /**
   * Test {@link LoadBalancingRxClient#getResponseTimeOut()}.
   *
   * <ul>
   *   <li>Then return {@code 7000}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getResponseTimeOut()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancingRxClient.getResponseTimeOut()"})
  public void testGetResponseTimeOut_thenReturn7000() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals(7000, loadBalancingUdpClient.getResponseTimeOut());
  }

  /**
   * Test {@link LoadBalancingRxClient#getResponseTimeOut()}.
   *
   * <ul>
   *   <li>Then return {@code 14000}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getResponseTimeOut()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancingRxClient.getResponseTimeOut()"})
  public void testGetResponseTimeOut_thenReturn14000() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(lb, config, null, mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals(14000, loadBalancingUdpClient.getResponseTimeOut());
  }

  /**
   * Test {@link LoadBalancingRxClient#getMaxConcurrentRequests()}.
   *
   * <ul>
   *   <li>Then return minus one.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getMaxConcurrentRequests()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancingRxClient.getMaxConcurrentRequests()"})
  public void testGetMaxConcurrentRequests_thenReturnMinusOne() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals(-1, loadBalancingUdpClient.getMaxConcurrentRequests());
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   *
   * <ul>
   *   <li>When ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"
  })
  public void testGetProperty_whenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty =
        loadBalancingUdpClient.getProperty(
            key,
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"),
            "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   *
   * <ul>
   *   <li>When ClientConfigWithDefaultValues empty string is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"
  })
  public void testGetProperty_whenClientConfigWithDefaultValuesEmptyStringIsNameSpace() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty =
        loadBalancingUdpClient.getProperty(
            key,
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space"),
            "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   *
   * <ul>
   *   <li>When ClientConfigWithDefaultValues {@code null} is {@code Name Space}.
   *   <li>Then calls {@link IClientConfigKey#key()}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"
  })
  public void testGetProperty_whenClientConfigWithDefaultValuesNullIsNameSpace_thenCallsKey() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty =
        loadBalancingUdpClient.getProperty(
            key,
            DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space"),
            "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then return {@code Default Value}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"
  })
  public void testGetProperty_whenEmptyConfig_thenReturnDefaultValue() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals(
        "Default Value",
        loadBalancingUdpClient.getProperty(
            mock(IClientConfigKey.class),
            DefaultClientConfigImpl.getEmptyConfig(),
            "Default Value"));
  }

  /**
   * Test {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code Default Value}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object LoadBalancingRxClient.getProperty(IClientConfigKey, IClientConfig, Object)"
  })
  public void testGetProperty_whenNull_thenReturnDefaultValue() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals(
        "Default Value",
        loadBalancingUdpClient.getProperty(mock(IClientConfigKey.class), null, "Default Value"));
  }

  /**
   * Test {@link LoadBalancingRxClient#getResourceForOptionalProperty(IClientConfigKey)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * LoadBalancingRxClient#getResourceForOptionalProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.net.URL LoadBalancingRxClient.getResourceForOptionalProperty(IClientConfigKey)"
  })
  public void testGetResourceForOptionalProperty_thenReturnNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertNull(loadBalancingUdpClient.getResourceForOptionalProperty(mock(IClientConfigKey.class)));
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"})
  public void testGetOrCreateRxClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualOrCreateRxClient =
        loadBalancingUdpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"})
  public void testGetOrCreateRxClient2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(lb, config, new DefaultLoadBalancerRetryHandler(), null);

    // Act
    RxClient<Object, Object> actualOrCreateRxClient =
        loadBalancingUdpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   *
   * <ul>
   *   <li>Then return {@link RxClientImpl}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"})
  public void testGetOrCreateRxClient_thenReturnRxClientImpl() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingTcpClient<Object, Object> loadBalancingTcpClient =
        new LoadBalancingTcpClient<>(
            lb,
            config,
            new DefaultLoadBalancerRetryHandler(),
            mock(PipelineConfigurator.class),
            null);

    // Act
    RxClient<Object, Object> actualOrCreateRxClient =
        loadBalancingTcpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof RxClientImpl);
    assertEquals("TcpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingTcpClient.rxClientCache.size());
  }

  /**
   * Test {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}.
   *
   * <ul>
   *   <li>Then return {@link RxClientImpl}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.getOrCreateRxClient(Server)"})
  public void testGetOrCreateRxClient_thenReturnRxClientImpl2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingTcpClient<Object, Object> loadBalancingTcpClient =
        new LoadBalancingTcpClient<>(
            lb,
            config,
            new DefaultLoadBalancerRetryHandler(),
            mock(PipelineConfigurator.class),
            null);

    // Act
    RxClient<Object, Object> actualOrCreateRxClient =
        loadBalancingTcpClient.getOrCreateRxClient(new Server(null));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof RxClientImpl);
    assertEquals("TcpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingTcpClient.rxClientCache.size());
  }

  /**
   * Test {@link LoadBalancingRxClient#removeClient(Server)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#removeClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.removeClient(Server)"})
  public void testRemoveClient_thenReturnNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualRemoveClientResult =
        loadBalancingUdpClient.removeClient(new Server("42"));

    // Assert
    assertNull(actualRemoveClientResult);
  }

  /**
   * Test {@link LoadBalancingRxClient#removeClient(Server)}.
   *
   * <ul>
   *   <li>When {@link Server#Server(String)} with id is {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#removeClient(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RxClient LoadBalancingRxClient.removeClient(Server)"})
  public void testRemoveClient_whenServerWithIdIsNull_thenReturnNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualRemoveClientResult =
        loadBalancingUdpClient.removeClient(new Server(null));

    // Assert
    assertNull(actualRemoveClientResult);
  }

  /**
   * Test {@link LoadBalancingRxClient#name()}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#name()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String LoadBalancingRxClient.name()"})
  public void testName_thenReturnEmptyString() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals("", loadBalancingUdpClient.name());
  }

  /**
   * Test {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}.
   *
   * <ul>
   *   <li>Then return {@link BooleanSubscription}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Subscription LoadBalancingRxClient.subscribe(MetricEventsListener)"})
  public void testSubscribe_thenReturnBooleanSubscription() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act
    Subscription actualSubscribeResult =
        loadBalancingUdpClient.subscribe(new DynamicPropertyBasedPoolStrategy(3, "Property Name"));

    // Assert
    assertTrue(actualSubscribeResult instanceof BooleanSubscription);
    assertFalse(actualSubscribeResult.isUnsubscribed());
  }

  /**
   * Test {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}.
   *
   * <ul>
   *   <li>When newHttpListener empty string.
   *   <li>Then return {@link BooleanSubscription}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Subscription LoadBalancingRxClient.subscribe(MetricEventsListener)"})
  public void testSubscribe_whenNewHttpListenerEmptyString_thenReturnBooleanSubscription() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act
    Subscription actualSubscribeResult =
        loadBalancingUdpClient.subscribe(HttpClientListener.newHttpListener(""));

    // Assert
    assertTrue(actualSubscribeResult instanceof BooleanSubscription);
    assertFalse(actualSubscribeResult.isUnsubscribed());
  }

  /**
   * Test {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}.
   *
   * <ul>
   *   <li>When newHttpListener {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClient#subscribe(MetricEventsListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Subscription LoadBalancingRxClient.subscribe(MetricEventsListener)"})
  public void testSubscribe_whenNewHttpListenerHttpsExampleOrgExample() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act
    Subscription actualSubscribeResult =
        loadBalancingUdpClient.subscribe(
            HttpClientListener.newHttpListener("https://example.org/example"));

    // Assert
    assertTrue(actualSubscribeResult instanceof BooleanSubscription);
    assertFalse(actualSubscribeResult.isUnsubscribed());
  }

  /**
   * Test {@link LoadBalancingRxClient#getLoadBalancerContext()}.
   *
   * <p>Method under test: {@link LoadBalancingRxClient#getLoadBalancerContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LoadBalancerContext LoadBalancingRxClient.getLoadBalancerContext()"})
  public void testGetLoadBalancerContext() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient =
        new LoadBalancingUdpClient<>(
            lb, config, new DefaultLoadBalancerRetryHandler(), mock(PipelineConfigurator.class));

    // Act
    LoadBalancerContext actualLoadBalancerContext = loadBalancingUdpClient.getLoadBalancerContext();

    // Assert
    assertSame(loadBalancingUdpClient.lbContext, actualLoadBalancerContext);
  }
}
