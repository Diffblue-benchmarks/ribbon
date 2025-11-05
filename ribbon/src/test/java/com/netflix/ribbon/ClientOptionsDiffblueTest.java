package com.netflix.ribbon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.util.Map;
import org.junit.Test;

public class ClientOptionsDiffblueTest {
  /**
   * Method under test: {@link ClientOptions#create()}
   */
  @Test
  public void testCreate() {
    // Arrange, Act and Assert
    assertTrue(ClientOptions.create().getOptions().isEmpty());
  }

  /**
   * Method under test: {@link ClientOptions#from(IClientConfig)}
   */
  @Test
  public void testFrom() {
    // Arrange, Act and Assert
    assertTrue(ClientOptions.from(DefaultClientConfigImpl.getEmptyConfig()).getOptions().isEmpty());
  }

  /**
   * Method under test: {@link ClientOptions#from(IClientConfig)}
   */
  @Test
  public void testFrom2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "Name Space");

    // Act
    ClientOptions actualFromResult = ClientOptions.from(config);

    // Assert
    Map<String, Object> properties = config.getProperties();
    assertEquals(39, properties.size());
    assertEquals("30000", properties.get("ConnIdleEvictTimeMilliSeconds"));
    assertEquals("30000", properties.get("ConnectionCleanerRepeatInterval"));
    assertEquals("30000", properties.get("MaxTotalTimeToPrimeConnections"));
    assertEquals("7001", properties.get("Port"));
    assertEquals(39, actualFromResult.getOptions().size());
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
  }

  /**
   * Method under test: {@link ClientOptions#from(IClientConfig)}
   */
  @Test
  public void testFrom3() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertTrue(ClientOptions.from(config).getOptions().isEmpty());
  }

  /**
   * Method under test:
   * {@link ClientOptions#withDiscoveryServiceIdentifier(String)}
   */
  @Test
  public void testWithDiscoveryServiceIdentifier() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithDiscoveryServiceIdentifierResult = createResult.withDiscoveryServiceIdentifier("42");

    // Assert
    assertEquals(1, actualWithDiscoveryServiceIdentifierResult.getOptions().size());
    assertSame(createResult, actualWithDiscoveryServiceIdentifierResult);
  }

  /**
   * Method under test:
   * {@link ClientOptions#withDiscoveryServiceIdentifier(String)}
   */
  @Test
  public void testWithDiscoveryServiceIdentifier2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithDiscoveryServiceIdentifierResult = fromResult.withDiscoveryServiceIdentifier("42");

    // Assert
    assertEquals(1, actualWithDiscoveryServiceIdentifierResult.getOptions().size());
    assertSame(fromResult, actualWithDiscoveryServiceIdentifierResult);
  }

  /**
   * Method under test:
   * {@link ClientOptions#withConfigurationBasedServerList(String)}
   */
  @Test
  public void testWithConfigurationBasedServerList() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithConfigurationBasedServerListResult = createResult
        .withConfigurationBasedServerList("Server List");

    // Assert
    assertEquals(1, actualWithConfigurationBasedServerListResult.getOptions().size());
    assertSame(createResult, actualWithConfigurationBasedServerListResult);
  }

  /**
   * Method under test:
   * {@link ClientOptions#withConfigurationBasedServerList(String)}
   */
  @Test
  public void testWithConfigurationBasedServerList2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithConfigurationBasedServerListResult = fromResult
        .withConfigurationBasedServerList("Server List");

    // Assert
    assertEquals(1, actualWithConfigurationBasedServerListResult.getOptions().size());
    assertSame(fromResult, actualWithConfigurationBasedServerListResult);
  }

  /**
   * Method under test: {@link ClientOptions#withMaxAutoRetries(int)}
   */
  @Test
  public void testWithMaxAutoRetries() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithMaxAutoRetriesResult = createResult.withMaxAutoRetries(42);

    // Assert
    assertEquals(1, actualWithMaxAutoRetriesResult.getOptions().size());
    assertSame(createResult, actualWithMaxAutoRetriesResult);
  }

  /**
   * Method under test: {@link ClientOptions#withMaxAutoRetries(int)}
   */
  @Test
  public void testWithMaxAutoRetries2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithMaxAutoRetriesResult = fromResult.withMaxAutoRetries(42);

    // Assert
    assertEquals(1, actualWithMaxAutoRetriesResult.getOptions().size());
    assertSame(fromResult, actualWithMaxAutoRetriesResult);
  }

  /**
   * Method under test: {@link ClientOptions#withMaxAutoRetriesNextServer(int)}
   */
  @Test
  public void testWithMaxAutoRetriesNextServer() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithMaxAutoRetriesNextServerResult = createResult.withMaxAutoRetriesNextServer(42);

    // Assert
    assertEquals(1, actualWithMaxAutoRetriesNextServerResult.getOptions().size());
    assertSame(createResult, actualWithMaxAutoRetriesNextServerResult);
  }

  /**
   * Method under test: {@link ClientOptions#withMaxAutoRetriesNextServer(int)}
   */
  @Test
  public void testWithMaxAutoRetriesNextServer2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithMaxAutoRetriesNextServerResult = fromResult.withMaxAutoRetriesNextServer(42);

    // Assert
    assertEquals(1, actualWithMaxAutoRetriesNextServerResult.getOptions().size());
    assertSame(fromResult, actualWithMaxAutoRetriesNextServerResult);
  }

  /**
   * Method under test: {@link ClientOptions#withRetryOnAllOperations(boolean)}
   */
  @Test
  public void testWithRetryOnAllOperations() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithRetryOnAllOperationsResult = createResult.withRetryOnAllOperations(true);

    // Assert
    assertEquals(1, actualWithRetryOnAllOperationsResult.getOptions().size());
    assertSame(createResult, actualWithRetryOnAllOperationsResult);
  }

  /**
   * Method under test: {@link ClientOptions#withRetryOnAllOperations(boolean)}
   */
  @Test
  public void testWithRetryOnAllOperations2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithRetryOnAllOperationsResult = fromResult.withRetryOnAllOperations(true);

    // Assert
    assertEquals(1, actualWithRetryOnAllOperationsResult.getOptions().size());
    assertSame(fromResult, actualWithRetryOnAllOperationsResult);
  }

  /**
   * Method under test: {@link ClientOptions#withMaxConnectionsPerHost(int)}
   */
  @Test
  public void testWithMaxConnectionsPerHost() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithMaxConnectionsPerHostResult = createResult.withMaxConnectionsPerHost(42);

    // Assert
    assertEquals(1, actualWithMaxConnectionsPerHostResult.getOptions().size());
    assertSame(createResult, actualWithMaxConnectionsPerHostResult);
  }

  /**
   * Method under test: {@link ClientOptions#withMaxConnectionsPerHost(int)}
   */
  @Test
  public void testWithMaxConnectionsPerHost2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithMaxConnectionsPerHostResult = fromResult.withMaxConnectionsPerHost(42);

    // Assert
    assertEquals(1, actualWithMaxConnectionsPerHostResult.getOptions().size());
    assertSame(fromResult, actualWithMaxConnectionsPerHostResult);
  }

  /**
   * Method under test: {@link ClientOptions#withMaxTotalConnections(int)}
   */
  @Test
  public void testWithMaxTotalConnections() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithMaxTotalConnectionsResult = createResult.withMaxTotalConnections(42);

    // Assert
    assertEquals(1, actualWithMaxTotalConnectionsResult.getOptions().size());
    assertSame(createResult, actualWithMaxTotalConnectionsResult);
  }

  /**
   * Method under test: {@link ClientOptions#withMaxTotalConnections(int)}
   */
  @Test
  public void testWithMaxTotalConnections2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithMaxTotalConnectionsResult = fromResult.withMaxTotalConnections(42);

    // Assert
    assertEquals(1, actualWithMaxTotalConnectionsResult.getOptions().size());
    assertSame(fromResult, actualWithMaxTotalConnectionsResult);
  }

  /**
   * Method under test: {@link ClientOptions#withConnectTimeout(int)}
   */
  @Test
  public void testWithConnectTimeout() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithConnectTimeoutResult = createResult.withConnectTimeout(42);

    // Assert
    assertEquals(1, actualWithConnectTimeoutResult.getOptions().size());
    assertSame(createResult, actualWithConnectTimeoutResult);
  }

  /**
   * Method under test: {@link ClientOptions#withConnectTimeout(int)}
   */
  @Test
  public void testWithConnectTimeout2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithConnectTimeoutResult = fromResult.withConnectTimeout(42);

    // Assert
    assertEquals(1, actualWithConnectTimeoutResult.getOptions().size());
    assertSame(fromResult, actualWithConnectTimeoutResult);
  }

  /**
   * Method under test: {@link ClientOptions#withReadTimeout(int)}
   */
  @Test
  public void testWithReadTimeout() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithReadTimeoutResult = createResult.withReadTimeout(42);

    // Assert
    assertEquals(1, actualWithReadTimeoutResult.getOptions().size());
    assertSame(createResult, actualWithReadTimeoutResult);
  }

  /**
   * Method under test: {@link ClientOptions#withReadTimeout(int)}
   */
  @Test
  public void testWithReadTimeout2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithReadTimeoutResult = fromResult.withReadTimeout(42);

    // Assert
    assertEquals(1, actualWithReadTimeoutResult.getOptions().size());
    assertSame(fromResult, actualWithReadTimeoutResult);
  }

  /**
   * Method under test: {@link ClientOptions#withFollowRedirects(boolean)}
   */
  @Test
  public void testWithFollowRedirects() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithFollowRedirectsResult = createResult.withFollowRedirects(true);

    // Assert
    assertEquals(1, actualWithFollowRedirectsResult.getOptions().size());
    assertSame(createResult, actualWithFollowRedirectsResult);
  }

  /**
   * Method under test: {@link ClientOptions#withFollowRedirects(boolean)}
   */
  @Test
  public void testWithFollowRedirects2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithFollowRedirectsResult = fromResult.withFollowRedirects(true);

    // Assert
    assertEquals(1, actualWithFollowRedirectsResult.getOptions().size());
    assertSame(fromResult, actualWithFollowRedirectsResult);
  }

  /**
   * Method under test:
   * {@link ClientOptions#withConnectionPoolIdleEvictTimeMilliseconds(int)}
   */
  @Test
  public void testWithConnectionPoolIdleEvictTimeMilliseconds() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithConnectionPoolIdleEvictTimeMillisecondsResult = createResult
        .withConnectionPoolIdleEvictTimeMilliseconds(42);

    // Assert
    assertEquals(1, actualWithConnectionPoolIdleEvictTimeMillisecondsResult.getOptions().size());
    assertSame(createResult, actualWithConnectionPoolIdleEvictTimeMillisecondsResult);
  }

  /**
   * Method under test:
   * {@link ClientOptions#withConnectionPoolIdleEvictTimeMilliseconds(int)}
   */
  @Test
  public void testWithConnectionPoolIdleEvictTimeMilliseconds2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithConnectionPoolIdleEvictTimeMillisecondsResult = fromResult
        .withConnectionPoolIdleEvictTimeMilliseconds(42);

    // Assert
    assertEquals(1, actualWithConnectionPoolIdleEvictTimeMillisecondsResult.getOptions().size());
    assertSame(fromResult, actualWithConnectionPoolIdleEvictTimeMillisecondsResult);
  }

  /**
   * Method under test: {@link ClientOptions#withLoadBalancerEnabled(boolean)}
   */
  @Test
  public void testWithLoadBalancerEnabled() {
    // Arrange
    ClientOptions createResult = ClientOptions.create();

    // Act
    ClientOptions actualWithLoadBalancerEnabledResult = createResult.withLoadBalancerEnabled(true);

    // Assert
    assertEquals(1, actualWithLoadBalancerEnabledResult.getOptions().size());
    assertSame(createResult, actualWithLoadBalancerEnabledResult);
  }

  /**
   * Method under test: {@link ClientOptions#withLoadBalancerEnabled(boolean)}
   */
  @Test
  public void testWithLoadBalancerEnabled2() {
    // Arrange
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));
    ClientOptions fromResult = ClientOptions.from(config);

    // Act
    ClientOptions actualWithLoadBalancerEnabledResult = fromResult.withLoadBalancerEnabled(true);

    // Assert
    assertEquals(1, actualWithLoadBalancerEnabledResult.getOptions().size());
    assertSame(fromResult, actualWithLoadBalancerEnabledResult);
  }

  /**
   * Method under test: {@link ClientOptions#getOptions()}
   */
  @Test
  public void testGetOptions() {
    // Arrange, Act and Assert
    assertTrue(ClientOptions.create().getOptions().isEmpty());
  }
}
