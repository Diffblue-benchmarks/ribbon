package com.netflix.ribbon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClientOptionsDiffblueTest {
  /**
   * Test {@link ClientOptions#create()}.
   * <p>
   * Method under test: {@link ClientOptions#create()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.create()"})
  public void testCreate() {
    // Arrange, Act and Assert
    assertTrue(ClientOptions.create().getOptions().isEmpty());
  }

  /**
   * Test {@link ClientOptions#from(IClientConfig)}.
   * <ul>
   *   <li>Then return Options size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientOptions#from(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.from(IClientConfig)"})
  public void testFrom_thenReturnOptionsSizeIsOne() {
    // Arrange
    IClientConfig config = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act and Assert
    assertEquals(1, ClientOptions.from(config).getOptions().size());
  }

  /**
   * Test {@link ClientOptions#from(IClientConfig)}.
   * <ul>
   *   <li>When EmptyConfig.</li>
   *   <li>Then return Options Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientOptions#from(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.from(IClientConfig)"})
  public void testFrom_whenEmptyConfig_thenReturnOptionsEmpty() {
    // Arrange, Act and Assert
    assertTrue(ClientOptions.from(DefaultClientConfigImpl.getEmptyConfig()).getOptions().isEmpty());
  }

  /**
   * Test {@link ClientOptions#withDiscoveryServiceIdentifier(String)}.
   * <p>
   * Method under test: {@link ClientOptions#withDiscoveryServiceIdentifier(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withDiscoveryServiceIdentifier(String)"})
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
   * Test {@link ClientOptions#withConfigurationBasedServerList(String)}.
   * <p>
   * Method under test: {@link ClientOptions#withConfigurationBasedServerList(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withConfigurationBasedServerList(String)"})
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
   * Test {@link ClientOptions#withMaxAutoRetries(int)}.
   * <p>
   * Method under test: {@link ClientOptions#withMaxAutoRetries(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withMaxAutoRetries(int)"})
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
   * Test {@link ClientOptions#withMaxAutoRetriesNextServer(int)}.
   * <p>
   * Method under test: {@link ClientOptions#withMaxAutoRetriesNextServer(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withMaxAutoRetriesNextServer(int)"})
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
   * Test {@link ClientOptions#withRetryOnAllOperations(boolean)}.
   * <p>
   * Method under test: {@link ClientOptions#withRetryOnAllOperations(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withRetryOnAllOperations(boolean)"})
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
   * Test {@link ClientOptions#withMaxConnectionsPerHost(int)}.
   * <p>
   * Method under test: {@link ClientOptions#withMaxConnectionsPerHost(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withMaxConnectionsPerHost(int)"})
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
   * Test {@link ClientOptions#withMaxTotalConnections(int)}.
   * <p>
   * Method under test: {@link ClientOptions#withMaxTotalConnections(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withMaxTotalConnections(int)"})
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
   * Test {@link ClientOptions#withConnectTimeout(int)}.
   * <p>
   * Method under test: {@link ClientOptions#withConnectTimeout(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withConnectTimeout(int)"})
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
   * Test {@link ClientOptions#withReadTimeout(int)}.
   * <p>
   * Method under test: {@link ClientOptions#withReadTimeout(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withReadTimeout(int)"})
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
   * Test {@link ClientOptions#withFollowRedirects(boolean)}.
   * <p>
   * Method under test: {@link ClientOptions#withFollowRedirects(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withFollowRedirects(boolean)"})
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
   * Test {@link ClientOptions#withConnectionPoolIdleEvictTimeMilliseconds(int)}.
   * <p>
   * Method under test: {@link ClientOptions#withConnectionPoolIdleEvictTimeMilliseconds(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withConnectionPoolIdleEvictTimeMilliseconds(int)"})
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
   * Test {@link ClientOptions#withLoadBalancerEnabled(boolean)}.
   * <p>
   * Method under test: {@link ClientOptions#withLoadBalancerEnabled(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientOptions ClientOptions.withLoadBalancerEnabled(boolean)"})
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
   * Test {@link ClientOptions#getOptions()}.
   * <p>
   * Method under test: {@link ClientOptions#getOptions()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Map ClientOptions.getOptions()"})
  public void testGetOptions() {
    // Arrange, Act and Assert
    assertTrue(ClientOptions.create().getOptions().isEmpty());
  }
}
