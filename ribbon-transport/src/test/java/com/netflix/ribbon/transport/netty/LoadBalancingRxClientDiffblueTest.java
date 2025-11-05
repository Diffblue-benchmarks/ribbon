package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.ribbon.transport.netty.http.NettyHttpLoadBalancerErrorHandler;
import com.netflix.ribbon.transport.netty.udp.LoadBalancingUdpClient;
import io.reactivex.netty.client.RxClient;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.udp.client.UdpClient;
import java.net.URL;
import org.junit.Test;

public class LoadBalancingRxClientDiffblueTest {
  /**
   * Method under test: {@link LoadBalancingRxClient#getResponseTimeOut()}
   */
  @Test
  public void testGetResponseTimeOut() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals(7000, loadBalancingUdpClient.getResponseTimeOut());
  }

  /**
   * Method under test: {@link LoadBalancingRxClient#getResponseTimeOut()}
   */
  @Test
  public void testGetResponseTimeOut2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb,
        DefaultClientConfigImpl.getEmptyConfig(), null, mock(PipelineConfigurator.class));

    // Act and Assert
    assertEquals(14000, loadBalancingUdpClient.getResponseTimeOut());
  }

  /**
   * Method under test:
   * {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  public void testGetProperty() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);

    // Act and Assert
    assertEquals("Default Value",
        loadBalancingUdpClient.getProperty(key, DefaultClientConfigImpl.getEmptyConfig(), "Default Value"));
  }

  /**
   * Method under test:
   * {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  public void testGetProperty2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "Name Space");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty = loadBalancingUdpClient.getProperty(key, DefaultClientConfigImpl.getEmptyConfig(),
        "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Method under test:
   * {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  public void testGetProperty3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty = loadBalancingUdpClient.getProperty(key, DefaultClientConfigImpl.getEmptyConfig(),
        "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Method under test:
   * {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  public void testGetProperty4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty = loadBalancingUdpClient.getProperty(key, DefaultClientConfigImpl.getEmptyConfig(),
        "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Method under test:
   * {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  public void testGetProperty5() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "Name Space");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<Object> key = mock(IClientConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualProperty = loadBalancingUdpClient.getProperty(key, null, "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualProperty);
  }

  /**
   * Method under test:
   * {@link LoadBalancingRxClient#getProperty(IClientConfigKey, IClientConfig, Object)}
   */
  @Test
  public void testGetProperty6() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "Name Space");
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
   * Method under test:
   * {@link LoadBalancingRxClient#getResourceForOptionalProperty(IClientConfigKey)}
   */
  @Test
  public void testGetResourceForOptionalProperty() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertNull(loadBalancingUdpClient.getResourceForOptionalProperty(mock(IClientConfigKey.class)));
  }

  /**
   * Method under test:
   * {@link LoadBalancingRxClient#getResourceForOptionalProperty(IClientConfigKey)}
   */
  @Test
  public void testGetResourceForOptionalProperty2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "Name Space");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<String> configKey = mock(IClientConfigKey.class);
    Class<String> forNameResult = String.class;
    when(configKey.type()).thenReturn(forNameResult);
    when(configKey.key()).thenReturn("Key");

    // Act
    URL actualResourceForOptionalProperty = loadBalancingUdpClient.getResourceForOptionalProperty(configKey);

    // Assert
    verify(configKey, atLeast(1)).key();
    verify(configKey, atLeast(1)).type();
    assertNull(actualResourceForOptionalProperty);
  }

  /**
   * Method under test:
   * {@link LoadBalancingRxClient#getResourceForOptionalProperty(IClientConfigKey)}
   */
  @Test
  public void testGetResourceForOptionalProperty3() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "Name Space");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(null, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<String> configKey = mock(IClientConfigKey.class);
    Class<String> forNameResult = String.class;
    when(configKey.type()).thenReturn(forNameResult);
    when(configKey.key()).thenReturn("Key");

    // Act
    URL actualResourceForOptionalProperty = loadBalancingUdpClient.getResourceForOptionalProperty(configKey);

    // Assert
    verify(configKey, atLeast(1)).key();
    verify(configKey, atLeast(1)).type();
    assertNull(actualResourceForOptionalProperty);
  }

  /**
   * Method under test:
   * {@link LoadBalancingRxClient#getResourceForOptionalProperty(IClientConfigKey)}
   */
  @Test
  public void testGetResourceForOptionalProperty4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<String> configKey = mock(IClientConfigKey.class);
    Class<String> forNameResult = String.class;
    when(configKey.type()).thenReturn(forNameResult);
    when(configKey.key()).thenReturn("Key");

    // Act
    URL actualResourceForOptionalProperty = loadBalancingUdpClient.getResourceForOptionalProperty(configKey);

    // Assert
    verify(configKey, atLeast(1)).key();
    verify(configKey, atLeast(1)).type();
    assertNull(actualResourceForOptionalProperty);
  }

  /**
   * Method under test:
   * {@link LoadBalancingRxClient#getResourceForOptionalProperty(IClientConfigKey)}
   */
  @Test
  public void testGetResourceForOptionalProperty5() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));
    IClientConfigKey<String> configKey = mock(IClientConfigKey.class);
    Class<String> forNameResult = String.class;
    when(configKey.type()).thenReturn(forNameResult);
    when(configKey.key()).thenReturn("Key");

    // Act
    URL actualResourceForOptionalProperty = loadBalancingUdpClient.getResourceForOptionalProperty(configKey);

    // Assert
    verify(configKey, atLeast(1)).key();
    verify(configKey, atLeast(1)).type();
    assertNull(actualResourceForOptionalProperty);
  }

  /**
   * Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  public void testGetOrCreateRxClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualOrCreateRxClient = loadBalancingUdpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
    assertSame(lb, loadBalancingUdpClient.getLoadBalancerContext().getLoadBalancer());
  }

  /**
   * Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  public void testGetOrCreateRxClient2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "class");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualOrCreateRxClient = loadBalancingUdpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
    assertSame(lb, loadBalancingUdpClient.getLoadBalancerContext().getLoadBalancer());
  }

  /**
   * Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  public void testGetOrCreateRxClient3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "class");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualOrCreateRxClient = loadBalancingUdpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
    assertSame(lb, loadBalancingUdpClient.getLoadBalancerContext().getLoadBalancer());
  }

  /**
   * Method under test: {@link LoadBalancingRxClient#getOrCreateRxClient(Server)}
   */
  @Test
  public void testGetOrCreateRxClient4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("Id"));
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "class");
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act
    RxClient<Object, Object> actualOrCreateRxClient = loadBalancingUdpClient.getOrCreateRxClient(new Server("42"));

    // Assert
    assertTrue(actualOrCreateRxClient instanceof UdpClient);
    assertEquals("UdpClient--no-name", actualOrCreateRxClient.name());
    assertEquals(1, loadBalancingUdpClient.rxClientCache.size());
    assertSame(lb, loadBalancingUdpClient.getLoadBalancerContext().getLoadBalancer());
  }

  /**
   * Method under test: {@link LoadBalancingRxClient#removeClient(Server)}
   */
  @Test
  public void testRemoveClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertNull(loadBalancingUdpClient.removeClient(new Server("42")));
  }

  /**
   * Method under test: {@link LoadBalancingRxClient#removeClient(Server)}
   */
  @Test
  public void testRemoveClient2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    LoadBalancingUdpClient<Object, Object> loadBalancingUdpClient = new LoadBalancingUdpClient<>(lb, config,
        new NettyHttpLoadBalancerErrorHandler(), mock(PipelineConfigurator.class));

    // Act and Assert
    assertNull(loadBalancingUdpClient.removeClient(new Server(null)));
  }
}
