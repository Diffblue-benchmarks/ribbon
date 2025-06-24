package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImplTest.NewConfigKey;
import com.netflix.client.config.IClientConfig.Builder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class IClientConfigDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test Builder {@link Builder#build()}.
   * <p>
   * Method under test: {@link Builder#build()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Builder.<init>()", "IClientConfig Builder.build()"})
  public void testBuilderBuild() {
    // Arrange, Act and Assert
    assertTrue(Builder.newBuilder()
        .ignoreUserTokenInConnectionPoolForSecureClient(true)
        .prioritizeVipAddressBasedServers(true)
        .withClientAuthRequired(true)
        .withConnIdleEvictTimeMilliSeconds(42)
        .withConnectTimeout(42)
        .withConnectionCleanerRepeatIntervalMills(42)
        .withConnectionManagerTimeout(42)
        .withConnectionPoolCleanerTaskEnabled(true)
        .withCustomSSLSocketFactoryClassName("42")
        .withDeploymentContextBasedVipAddresses("42 Main St")
        .withEnablePrimeConnections(true)
        .withFollowRedirects(true)
        .withForceClientPortConfiguration(true)
        .withGZIPContentEncodingFilterEnabled(true)
        .withHostnameValidationRequired(true)
        .withKeyStore("42")
        .withKeyStorePassword("42")
        .withLoadBalancerEnabled(true)
        .withMaxConnectionsPerHost(42)
        .withMaxTotalConnections(42)
        .withProxyHost("localhost")
        .withProxyPort(42)
        .withReadTimeout(42)
        .withRequestSpecificRetryOn(true)
        .withRetryOnAllOperations(true)
        .withSecure(true)
        .withServerListRefreshIntervalMills(42)
        .withTargetRegion("42")
        .withTrustStore("42")
        .withTrustStorePassword("42")
        .withZoneAffinityEnabled(true)
        .withZoneExclusivityEnabled(true)
        .build() instanceof DefaultClientConfigImpl);
  }

  /**
   * Test Builder {@link Builder#ignoreUserTokenInConnectionPoolForSecureClient(boolean)}.
   * <p>
   * Method under test: {@link Builder#ignoreUserTokenInConnectionPoolForSecureClient(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.ignoreUserTokenInConnectionPoolForSecureClient(boolean)"})
  public void testBuilderIgnoreUserTokenInConnectionPoolForSecureClient() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualIgnoreUserTokenInConnectionPoolForSecureClientResult = newBuilderResult
        .ignoreUserTokenInConnectionPoolForSecureClient(true);

    // Assert
    assertTrue(actualIgnoreUserTokenInConnectionPoolForSecureClientResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualIgnoreUserTokenInConnectionPoolForSecureClientResult);
  }

  /**
   * Test Builder {@link Builder#newBuilder()}.
   * <p>
   * Method under test: {@link Builder#newBuilder()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.newBuilder()"})
  public void testBuilderNewBuilder() {
    // Arrange, Act and Assert
    assertTrue(Builder.newBuilder().build() instanceof DefaultClientConfigImpl);
  }

  /**
   * Test Builder {@link Builder#newBuilder(String)} with {@code clientName}.
   * <p>
   * Method under test: {@link Builder#newBuilder(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.newBuilder(String)"})
  public void testBuilderNewBuilderWithClientName() {
    // Arrange, Act and Assert
    assertTrue(Builder.newBuilder("Dr Jane Doe").build() instanceof DefaultClientConfigImpl);
  }

  /**
   * Test Builder {@link Builder#newBuilder(String, String)} with {@code clientName}, {@code propertyNameSpace}.
   * <p>
   * Method under test: {@link Builder#newBuilder(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.newBuilder(String, String)"})
  public void testBuilderNewBuilderWithClientNamePropertyNameSpace() {
    // Arrange, Act and Assert
    assertTrue(Builder.newBuilder("Dr Jane Doe", "Property Name Space").build() instanceof DefaultClientConfigImpl);
  }

  /**
   * Test Builder {@link Builder#newBuilder(Class)} with {@code implClass}.
   * <p>
   * Method under test: {@link Builder#newBuilder(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.newBuilder(Class)"})
  public void testBuilderNewBuilderWithImplClass() {
    // Arrange
    Class<IClientConfig> implClass = IClientConfig.class;

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Builder.newBuilder(implClass);
  }

  /**
   * Test Builder {@link Builder#newBuilder(Class, String)} with {@code implClass}, {@code clientName}.
   * <p>
   * Method under test: {@link Builder#newBuilder(Class, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.newBuilder(Class, String)"})
  public void testBuilderNewBuilderWithImplClassClientName() {
    // Arrange
    Class<IClientConfig> implClass = IClientConfig.class;

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Builder.newBuilder(implClass, "Dr Jane Doe");
  }

  /**
   * Test Builder {@link Builder#prioritizeVipAddressBasedServers(boolean)}.
   * <p>
   * Method under test: {@link Builder#prioritizeVipAddressBasedServers(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.prioritizeVipAddressBasedServers(boolean)"})
  public void testBuilderPrioritizeVipAddressBasedServers() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualPrioritizeVipAddressBasedServersResult = newBuilderResult.prioritizeVipAddressBasedServers(true);

    // Assert
    assertTrue(actualPrioritizeVipAddressBasedServersResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualPrioritizeVipAddressBasedServersResult);
  }

  /**
   * Test Builder {@link Builder#withClientAuthRequired(boolean)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withClientAuthRequired(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withClientAuthRequired(boolean)"})
  public void testBuilderWithClientAuthRequired_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithClientAuthRequiredResult = newBuilderResult.withClientAuthRequired(true);

    // Assert
    assertTrue(actualWithClientAuthRequiredResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithClientAuthRequiredResult);
  }

  /**
   * Test Builder {@link Builder#withConnIdleEvictTimeMilliSeconds(int)}.
   * <p>
   * Method under test: {@link Builder#withConnIdleEvictTimeMilliSeconds(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withConnIdleEvictTimeMilliSeconds(int)"})
  public void testBuilderWithConnIdleEvictTimeMilliSeconds() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithConnIdleEvictTimeMilliSecondsResult = newBuilderResult.withConnIdleEvictTimeMilliSeconds(42);

    // Assert
    assertTrue(actualWithConnIdleEvictTimeMilliSecondsResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithConnIdleEvictTimeMilliSecondsResult);
  }

  /**
   * Test Builder {@link Builder#withConnectTimeout(int)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withConnectTimeout(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withConnectTimeout(int)"})
  public void testBuilderWithConnectTimeout_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithConnectTimeoutResult = newBuilderResult.withConnectTimeout(42);

    // Assert
    assertTrue(actualWithConnectTimeoutResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithConnectTimeoutResult);
  }

  /**
   * Test Builder {@link Builder#withConnectionCleanerRepeatIntervalMills(int)}.
   * <p>
   * Method under test: {@link Builder#withConnectionCleanerRepeatIntervalMills(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withConnectionCleanerRepeatIntervalMills(int)"})
  public void testBuilderWithConnectionCleanerRepeatIntervalMills() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithConnectionCleanerRepeatIntervalMillsResult = newBuilderResult
        .withConnectionCleanerRepeatIntervalMills(42);

    // Assert
    assertTrue(actualWithConnectionCleanerRepeatIntervalMillsResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithConnectionCleanerRepeatIntervalMillsResult);
  }

  /**
   * Test Builder {@link Builder#withConnectionManagerTimeout(int)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withConnectionManagerTimeout(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withConnectionManagerTimeout(int)"})
  public void testBuilderWithConnectionManagerTimeout_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithConnectionManagerTimeoutResult = newBuilderResult.withConnectionManagerTimeout(42);

    // Assert
    assertTrue(actualWithConnectionManagerTimeoutResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithConnectionManagerTimeoutResult);
  }

  /**
   * Test Builder {@link Builder#withConnectionPoolCleanerTaskEnabled(boolean)}.
   * <p>
   * Method under test: {@link Builder#withConnectionPoolCleanerTaskEnabled(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withConnectionPoolCleanerTaskEnabled(boolean)"})
  public void testBuilderWithConnectionPoolCleanerTaskEnabled() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithConnectionPoolCleanerTaskEnabledResult = newBuilderResult
        .withConnectionPoolCleanerTaskEnabled(true);

    // Assert
    assertTrue(actualWithConnectionPoolCleanerTaskEnabledResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithConnectionPoolCleanerTaskEnabledResult);
  }

  /**
   * Test Builder {@link Builder#withCustomSSLSocketFactoryClassName(String)}.
   * <p>
   * Method under test: {@link Builder#withCustomSSLSocketFactoryClassName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withCustomSSLSocketFactoryClassName(String)"})
  public void testBuilderWithCustomSSLSocketFactoryClassName() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithCustomSSLSocketFactoryClassNameResult = newBuilderResult
        .withCustomSSLSocketFactoryClassName("42");

    // Assert
    assertTrue(actualWithCustomSSLSocketFactoryClassNameResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithCustomSSLSocketFactoryClassNameResult);
  }

  /**
   * Test Builder {@link Builder#withDefaultValues()}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withDefaultValues()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withDefaultValues()"})
  public void testBuilderWithDefaultValues_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithDefaultValuesResult = newBuilderResult.withDefaultValues();

    // Assert
    assertTrue(actualWithDefaultValuesResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithDefaultValuesResult);
  }

  /**
   * Test Builder {@link Builder#withDeploymentContextBasedVipAddresses(String)}.
   * <p>
   * Method under test: {@link Builder#withDeploymentContextBasedVipAddresses(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withDeploymentContextBasedVipAddresses(String)"})
  public void testBuilderWithDeploymentContextBasedVipAddresses() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithDeploymentContextBasedVipAddressesResult = newBuilderResult
        .withDeploymentContextBasedVipAddresses("42 Main St");

    // Assert
    assertTrue(actualWithDeploymentContextBasedVipAddressesResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithDeploymentContextBasedVipAddressesResult);
  }

  /**
   * Test Builder {@link Builder#withEnablePrimeConnections(boolean)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withEnablePrimeConnections(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withEnablePrimeConnections(boolean)"})
  public void testBuilderWithEnablePrimeConnections_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithEnablePrimeConnectionsResult = newBuilderResult.withEnablePrimeConnections(true);

    // Assert
    assertTrue(actualWithEnablePrimeConnectionsResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithEnablePrimeConnectionsResult);
  }

  /**
   * Test Builder {@link Builder#withFollowRedirects(boolean)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withFollowRedirects(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withFollowRedirects(boolean)"})
  public void testBuilderWithFollowRedirects_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithFollowRedirectsResult = newBuilderResult.withFollowRedirects(true);

    // Assert
    assertTrue(actualWithFollowRedirectsResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithFollowRedirectsResult);
  }

  /**
   * Test Builder {@link Builder#withForceClientPortConfiguration(boolean)}.
   * <p>
   * Method under test: {@link Builder#withForceClientPortConfiguration(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withForceClientPortConfiguration(boolean)"})
  public void testBuilderWithForceClientPortConfiguration() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithForceClientPortConfigurationResult = newBuilderResult.withForceClientPortConfiguration(true);

    // Assert
    assertTrue(actualWithForceClientPortConfigurationResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithForceClientPortConfigurationResult);
  }

  /**
   * Test Builder {@link Builder#withGZIPContentEncodingFilterEnabled(boolean)}.
   * <p>
   * Method under test: {@link Builder#withGZIPContentEncodingFilterEnabled(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withGZIPContentEncodingFilterEnabled(boolean)"})
  public void testBuilderWithGZIPContentEncodingFilterEnabled() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithGZIPContentEncodingFilterEnabledResult = newBuilderResult
        .withGZIPContentEncodingFilterEnabled(true);

    // Assert
    assertTrue(actualWithGZIPContentEncodingFilterEnabledResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithGZIPContentEncodingFilterEnabledResult);
  }

  /**
   * Test Builder {@link Builder#withHostnameValidationRequired(boolean)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withHostnameValidationRequired(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withHostnameValidationRequired(boolean)"})
  public void testBuilderWithHostnameValidationRequired_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithHostnameValidationRequiredResult = newBuilderResult.withHostnameValidationRequired(true);

    // Assert
    assertTrue(actualWithHostnameValidationRequiredResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithHostnameValidationRequiredResult);
  }

  /**
   * Test Builder {@link Builder#withKeyStorePassword(String)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withKeyStorePassword(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withKeyStorePassword(String)"})
  public void testBuilderWithKeyStorePassword_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithKeyStorePasswordResult = newBuilderResult.withKeyStorePassword("42");

    // Assert
    assertTrue(actualWithKeyStorePasswordResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithKeyStorePasswordResult);
  }

  /**
   * Test Builder {@link Builder#withKeyStore(String)}.
   * <ul>
   *   <li>Given newBuilder.</li>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withKeyStore(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withKeyStore(String)"})
  public void testBuilderWithKeyStore_givenNewBuilder_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithKeyStoreResult = newBuilderResult.withKeyStore("42");

    // Assert
    assertTrue(actualWithKeyStoreResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithKeyStoreResult);
  }

  /**
   * Test Builder {@link Builder#withLoadBalancerEnabled(boolean)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withLoadBalancerEnabled(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withLoadBalancerEnabled(boolean)"})
  public void testBuilderWithLoadBalancerEnabled_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithLoadBalancerEnabledResult = newBuilderResult.withLoadBalancerEnabled(true);

    // Assert
    assertTrue(actualWithLoadBalancerEnabledResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithLoadBalancerEnabledResult);
  }

  /**
   * Test Builder {@link Builder#withMaxAutoRetriesNextServer(int)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withMaxAutoRetriesNextServer(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withMaxAutoRetriesNextServer(int)"})
  public void testBuilderWithMaxAutoRetriesNextServer_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithMaxAutoRetriesNextServerResult = newBuilderResult.withMaxAutoRetriesNextServer(42);

    // Assert
    assertTrue(actualWithMaxAutoRetriesNextServerResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithMaxAutoRetriesNextServerResult);
  }

  /**
   * Test Builder {@link Builder#withMaxAutoRetries(int)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withMaxAutoRetries(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withMaxAutoRetries(int)"})
  public void testBuilderWithMaxAutoRetries_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithMaxAutoRetriesResult = newBuilderResult.withMaxAutoRetries(42);

    // Assert
    assertTrue(actualWithMaxAutoRetriesResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithMaxAutoRetriesResult);
  }

  /**
   * Test Builder {@link Builder#withMaxConnectionsPerHost(int)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withMaxConnectionsPerHost(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withMaxConnectionsPerHost(int)"})
  public void testBuilderWithMaxConnectionsPerHost_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithMaxConnectionsPerHostResult = newBuilderResult.withMaxConnectionsPerHost(42);

    // Assert
    assertTrue(actualWithMaxConnectionsPerHostResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithMaxConnectionsPerHostResult);
  }

  /**
   * Test Builder {@link Builder#withMaxTotalConnections(int)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withMaxTotalConnections(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withMaxTotalConnections(int)"})
  public void testBuilderWithMaxTotalConnections_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithMaxTotalConnectionsResult = newBuilderResult.withMaxTotalConnections(42);

    // Assert
    assertTrue(actualWithMaxTotalConnectionsResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithMaxTotalConnectionsResult);
  }

  /**
   * Test Builder {@link Builder#withProxyHost(String)}.
   * <ul>
   *   <li>Given newBuilder.</li>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withProxyHost(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withProxyHost(String)"})
  public void testBuilderWithProxyHost_givenNewBuilder_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithProxyHostResult = newBuilderResult.withProxyHost("localhost");

    // Assert
    assertTrue(actualWithProxyHostResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithProxyHostResult);
  }

  /**
   * Test Builder {@link Builder#withProxyPort(int)}.
   * <ul>
   *   <li>Given newBuilder.</li>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withProxyPort(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withProxyPort(int)"})
  public void testBuilderWithProxyPort_givenNewBuilder_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithProxyPortResult = newBuilderResult.withProxyPort(42);

    // Assert
    assertTrue(actualWithProxyPortResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithProxyPortResult);
  }

  /**
   * Test Builder {@link Builder#withReadTimeout(int)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withReadTimeout(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withReadTimeout(int)"})
  public void testBuilderWithReadTimeout_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithReadTimeoutResult = newBuilderResult.withReadTimeout(42);

    // Assert
    assertTrue(actualWithReadTimeoutResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithReadTimeoutResult);
  }

  /**
   * Test Builder {@link Builder#withRequestSpecificRetryOn(boolean)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withRequestSpecificRetryOn(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withRequestSpecificRetryOn(boolean)"})
  public void testBuilderWithRequestSpecificRetryOn_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithRequestSpecificRetryOnResult = newBuilderResult.withRequestSpecificRetryOn(true);

    // Assert
    assertTrue(actualWithRequestSpecificRetryOnResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithRequestSpecificRetryOnResult);
  }

  /**
   * Test Builder {@link Builder#withRetryOnAllOperations(boolean)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withRetryOnAllOperations(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withRetryOnAllOperations(boolean)"})
  public void testBuilderWithRetryOnAllOperations_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithRetryOnAllOperationsResult = newBuilderResult.withRetryOnAllOperations(true);

    // Assert
    assertTrue(actualWithRetryOnAllOperationsResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithRetryOnAllOperationsResult);
  }

  /**
   * Test Builder {@link Builder#withSecure(boolean)}.
   * <ul>
   *   <li>Given newBuilder.</li>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withSecure(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withSecure(boolean)"})
  public void testBuilderWithSecure_givenNewBuilder_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithSecureResult = newBuilderResult.withSecure(true);

    // Assert
    assertTrue(actualWithSecureResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithSecureResult);
  }

  /**
   * Test Builder {@link Builder#withServerListRefreshIntervalMills(int)}.
   * <p>
   * Method under test: {@link Builder#withServerListRefreshIntervalMills(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withServerListRefreshIntervalMills(int)"})
  public void testBuilderWithServerListRefreshIntervalMills() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithServerListRefreshIntervalMillsResult = newBuilderResult.withServerListRefreshIntervalMills(42);

    // Assert
    assertTrue(actualWithServerListRefreshIntervalMillsResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithServerListRefreshIntervalMillsResult);
  }

  /**
   * Test Builder {@link Builder#withTargetRegion(String)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withTargetRegion(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withTargetRegion(String)"})
  public void testBuilderWithTargetRegion_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithTargetRegionResult = newBuilderResult.withTargetRegion("42");

    // Assert
    assertTrue(actualWithTargetRegionResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithTargetRegionResult);
  }

  /**
   * Test Builder {@link Builder#withTrustStorePassword(String)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withTrustStorePassword(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withTrustStorePassword(String)"})
  public void testBuilderWithTrustStorePassword_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithTrustStorePasswordResult = newBuilderResult.withTrustStorePassword("42");

    // Assert
    assertTrue(actualWithTrustStorePasswordResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithTrustStorePasswordResult);
  }

  /**
   * Test Builder {@link Builder#withTrustStore(String)}.
   * <ul>
   *   <li>Given newBuilder.</li>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withTrustStore(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withTrustStore(String)"})
  public void testBuilderWithTrustStore_givenNewBuilder_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithTrustStoreResult = newBuilderResult.withTrustStore("42");

    // Assert
    assertTrue(actualWithTrustStoreResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithTrustStoreResult);
  }

  /**
   * Test Builder {@link Builder#withZoneAffinityEnabled(boolean)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withZoneAffinityEnabled(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withZoneAffinityEnabled(boolean)"})
  public void testBuilderWithZoneAffinityEnabled_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithZoneAffinityEnabledResult = newBuilderResult.withZoneAffinityEnabled(true);

    // Assert
    assertTrue(actualWithZoneAffinityEnabledResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithZoneAffinityEnabledResult);
  }

  /**
   * Test Builder {@link Builder#withZoneExclusivityEnabled(boolean)}.
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withZoneExclusivityEnabled(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withZoneExclusivityEnabled(boolean)"})
  public void testBuilderWithZoneExclusivityEnabled_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithZoneExclusivityEnabledResult = newBuilderResult.withZoneExclusivityEnabled(true);

    // Assert
    assertTrue(actualWithZoneExclusivityEnabledResult.build() instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithZoneExclusivityEnabledResult);
  }

  /**
   * Test {@link IClientConfig#getOrDefault(IClientConfigKey)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>Then return {@code Default Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IClientConfig#getOrDefault(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object IClientConfig.getOrDefault(IClientConfigKey)"})
  public void testGetOrDefault_givenEmptyConfig_thenReturnDefaultValue() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    when(key.defaultValue()).thenReturn("Default Value");

    // Act
    Object actualOrDefault = emptyConfig.getOrDefault(key);

    // Assert
    verify(key).defaultValue();
    assertEquals("Default Value", actualOrDefault);
  }
}
