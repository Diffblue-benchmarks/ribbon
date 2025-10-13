package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImplTest.NewConfigKey;
import com.netflix.client.config.IClientConfig.Builder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class IClientConfigDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test Builder {@link Builder#build()}.
   *
   * <p>Method under test: {@link Builder#build()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Builder.<init>()", "IClientConfig Builder.build()"})
  public void testBuilderBuild() {
    // Arrange and Act
    IClientConfig actualIClientConfig =
        Builder.newBuilder()
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
            .build();

    // Assert
    assertTrue(actualIClientConfig instanceof DefaultClientConfigImpl);
  }

  /**
   * Test Builder {@link Builder#ignoreUserTokenInConnectionPoolForSecureClient(boolean)}.
   *
   * <p>Method under test: {@link Builder#ignoreUserTokenInConnectionPoolForSecureClient(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.ignoreUserTokenInConnectionPoolForSecureClient(boolean)"})
  public void testBuilderIgnoreUserTokenInConnectionPoolForSecureClient() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualIgnoreUserTokenInConnectionPoolForSecureClientResult =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true);

    // Assert
    IClientConfig iClientConfig =
        actualIgnoreUserTokenInConnectionPoolForSecureClientResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualIgnoreUserTokenInConnectionPoolForSecureClientResult);
  }

  /**
   * Test Builder {@link Builder#newBuilder()}.
   *
   * <p>Method under test: {@link Builder#newBuilder()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.newBuilder()"})
  public void testBuilderNewBuilder() {
    // Arrange, Act and Assert
    IClientConfig iClientConfig = Builder.newBuilder().build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
  }

  /**
   * Test Builder {@link Builder#newBuilder(String)} with {@code clientName}.
   *
   * <p>Method under test: {@link Builder#newBuilder(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.newBuilder(String)"})
  public void testBuilderNewBuilderWithClientName() {
    // Arrange, Act and Assert
    IClientConfig iClientConfig = Builder.newBuilder("Dr Jane Doe").build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
  }

  /**
   * Test Builder {@link Builder#newBuilder(String, String)} with {@code clientName}, {@code
   * propertyNameSpace}.
   *
   * <p>Method under test: {@link Builder#newBuilder(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.newBuilder(String, String)"})
  public void testBuilderNewBuilderWithClientNamePropertyNameSpace() {
    // Arrange, Act and Assert
    IClientConfig iClientConfig = Builder.newBuilder("Dr Jane Doe", "Property Name Space").build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
  }

  /**
   * Test Builder {@link Builder#newBuilder(Class)} with {@code implClass}.
   *
   * <p>Method under test: {@link Builder#newBuilder(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.newBuilder(Class)"})
  public void testBuilderNewBuilderWithImplClass() {
    // Arrange
    Class<IClientConfig> implClass = IClientConfig.class;

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Builder.newBuilder(implClass);
  }

  /**
   * Test Builder {@link Builder#newBuilder(Class, String)} with {@code implClass}, {@code
   * clientName}.
   *
   * <p>Method under test: {@link Builder#newBuilder(Class, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link Builder#prioritizeVipAddressBasedServers(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.prioritizeVipAddressBasedServers(boolean)"})
  public void testBuilderPrioritizeVipAddressBasedServers() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualPrioritizeVipAddressBasedServersResult =
        newBuilderResult.prioritizeVipAddressBasedServers(true);

    // Assert
    IClientConfig iClientConfig = actualPrioritizeVipAddressBasedServersResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualPrioritizeVipAddressBasedServersResult);
  }

  /**
   * Test Builder {@link Builder#withClientAuthRequired(boolean)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withClientAuthRequired(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withClientAuthRequired(boolean)"})
  public void testBuilderWithClientAuthRequired_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithClientAuthRequiredResult = newBuilderResult.withClientAuthRequired(true);

    // Assert
    IClientConfig iClientConfig = actualWithClientAuthRequiredResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithClientAuthRequiredResult);
  }

  /**
   * Test Builder {@link Builder#withConnIdleEvictTimeMilliSeconds(int)}.
   *
   * <p>Method under test: {@link Builder#withConnIdleEvictTimeMilliSeconds(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withConnIdleEvictTimeMilliSeconds(int)"})
  public void testBuilderWithConnIdleEvictTimeMilliSeconds() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithConnIdleEvictTimeMilliSecondsResult =
        newBuilderResult.withConnIdleEvictTimeMilliSeconds(42);

    // Assert
    IClientConfig iClientConfig = actualWithConnIdleEvictTimeMilliSecondsResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithConnIdleEvictTimeMilliSecondsResult);
  }

  /**
   * Test Builder {@link Builder#withConnectTimeout(int)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withConnectTimeout(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withConnectTimeout(int)"})
  public void testBuilderWithConnectTimeout_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithConnectTimeoutResult = newBuilderResult.withConnectTimeout(42);

    // Assert
    IClientConfig iClientConfig = actualWithConnectTimeoutResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithConnectTimeoutResult);
  }

  /**
   * Test Builder {@link Builder#withConnectionCleanerRepeatIntervalMills(int)}.
   *
   * <p>Method under test: {@link Builder#withConnectionCleanerRepeatIntervalMills(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withConnectionCleanerRepeatIntervalMills(int)"})
  public void testBuilderWithConnectionCleanerRepeatIntervalMills() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithConnectionCleanerRepeatIntervalMillsResult =
        newBuilderResult.withConnectionCleanerRepeatIntervalMills(42);

    // Assert
    IClientConfig iClientConfig = actualWithConnectionCleanerRepeatIntervalMillsResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithConnectionCleanerRepeatIntervalMillsResult);
  }

  /**
   * Test Builder {@link Builder#withConnectionManagerTimeout(int)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withConnectionManagerTimeout(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withConnectionManagerTimeout(int)"})
  public void testBuilderWithConnectionManagerTimeout_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithConnectionManagerTimeoutResult =
        newBuilderResult.withConnectionManagerTimeout(42);

    // Assert
    IClientConfig iClientConfig = actualWithConnectionManagerTimeoutResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithConnectionManagerTimeoutResult);
  }

  /**
   * Test Builder {@link Builder#withConnectionPoolCleanerTaskEnabled(boolean)}.
   *
   * <p>Method under test: {@link Builder#withConnectionPoolCleanerTaskEnabled(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withConnectionPoolCleanerTaskEnabled(boolean)"})
  public void testBuilderWithConnectionPoolCleanerTaskEnabled() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithConnectionPoolCleanerTaskEnabledResult =
        newBuilderResult.withConnectionPoolCleanerTaskEnabled(true);

    // Assert
    IClientConfig iClientConfig = actualWithConnectionPoolCleanerTaskEnabledResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithConnectionPoolCleanerTaskEnabledResult);
  }

  /**
   * Test Builder {@link Builder#withCustomSSLSocketFactoryClassName(String)}.
   *
   * <p>Method under test: {@link Builder#withCustomSSLSocketFactoryClassName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withCustomSSLSocketFactoryClassName(String)"})
  public void testBuilderWithCustomSSLSocketFactoryClassName() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithCustomSSLSocketFactoryClassNameResult =
        newBuilderResult.withCustomSSLSocketFactoryClassName("42");

    // Assert
    IClientConfig iClientConfig = actualWithCustomSSLSocketFactoryClassNameResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithCustomSSLSocketFactoryClassNameResult);
  }

  /**
   * Test Builder {@link Builder#withDefaultValues()}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withDefaultValues()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withDefaultValues()"})
  public void testBuilderWithDefaultValues_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithDefaultValuesResult = newBuilderResult.withDefaultValues();

    // Assert
    IClientConfig iClientConfig = actualWithDefaultValuesResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithDefaultValuesResult);
  }

  /**
   * Test Builder {@link Builder#withDeploymentContextBasedVipAddresses(String)}.
   *
   * <p>Method under test: {@link Builder#withDeploymentContextBasedVipAddresses(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withDeploymentContextBasedVipAddresses(String)"})
  public void testBuilderWithDeploymentContextBasedVipAddresses() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithDeploymentContextBasedVipAddressesResult =
        newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");

    // Assert
    IClientConfig iClientConfig = actualWithDeploymentContextBasedVipAddressesResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithDeploymentContextBasedVipAddressesResult);
  }

  /**
   * Test Builder {@link Builder#withEnablePrimeConnections(boolean)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withEnablePrimeConnections(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withEnablePrimeConnections(boolean)"})
  public void testBuilderWithEnablePrimeConnections_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithEnablePrimeConnectionsResult =
        newBuilderResult.withEnablePrimeConnections(true);

    // Assert
    IClientConfig iClientConfig = actualWithEnablePrimeConnectionsResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithEnablePrimeConnectionsResult);
  }

  /**
   * Test Builder {@link Builder#withFollowRedirects(boolean)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withFollowRedirects(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withFollowRedirects(boolean)"})
  public void testBuilderWithFollowRedirects_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithFollowRedirectsResult = newBuilderResult.withFollowRedirects(true);

    // Assert
    IClientConfig iClientConfig = actualWithFollowRedirectsResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithFollowRedirectsResult);
  }

  /**
   * Test Builder {@link Builder#withForceClientPortConfiguration(boolean)}.
   *
   * <p>Method under test: {@link Builder#withForceClientPortConfiguration(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withForceClientPortConfiguration(boolean)"})
  public void testBuilderWithForceClientPortConfiguration() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithForceClientPortConfigurationResult =
        newBuilderResult.withForceClientPortConfiguration(true);

    // Assert
    IClientConfig iClientConfig = actualWithForceClientPortConfigurationResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithForceClientPortConfigurationResult);
  }

  /**
   * Test Builder {@link Builder#withGZIPContentEncodingFilterEnabled(boolean)}.
   *
   * <p>Method under test: {@link Builder#withGZIPContentEncodingFilterEnabled(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withGZIPContentEncodingFilterEnabled(boolean)"})
  public void testBuilderWithGZIPContentEncodingFilterEnabled() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithGZIPContentEncodingFilterEnabledResult =
        newBuilderResult.withGZIPContentEncodingFilterEnabled(true);

    // Assert
    IClientConfig iClientConfig = actualWithGZIPContentEncodingFilterEnabledResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithGZIPContentEncodingFilterEnabledResult);
  }

  /**
   * Test Builder {@link Builder#withHostnameValidationRequired(boolean)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withHostnameValidationRequired(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withHostnameValidationRequired(boolean)"})
  public void testBuilderWithHostnameValidationRequired_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithHostnameValidationRequiredResult =
        newBuilderResult.withHostnameValidationRequired(true);

    // Assert
    IClientConfig iClientConfig = actualWithHostnameValidationRequiredResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithHostnameValidationRequiredResult);
  }

  /**
   * Test Builder {@link Builder#withKeyStorePassword(String)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withKeyStorePassword(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withKeyStorePassword(String)"})
  public void testBuilderWithKeyStorePassword_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithKeyStorePasswordResult = newBuilderResult.withKeyStorePassword("42");

    // Assert
    IClientConfig iClientConfig = actualWithKeyStorePasswordResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithKeyStorePasswordResult);
  }

  /**
   * Test Builder {@link Builder#withKeyStore(String)}.
   *
   * <ul>
   *   <li>Given newBuilder.
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withKeyStore(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withKeyStore(String)"})
  public void testBuilderWithKeyStore_givenNewBuilder_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithKeyStoreResult = newBuilderResult.withKeyStore("42");

    // Assert
    IClientConfig iClientConfig = actualWithKeyStoreResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithKeyStoreResult);
  }

  /**
   * Test Builder {@link Builder#withLoadBalancerEnabled(boolean)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withLoadBalancerEnabled(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withLoadBalancerEnabled(boolean)"})
  public void testBuilderWithLoadBalancerEnabled_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithLoadBalancerEnabledResult = newBuilderResult.withLoadBalancerEnabled(true);

    // Assert
    IClientConfig iClientConfig = actualWithLoadBalancerEnabledResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithLoadBalancerEnabledResult);
  }

  /**
   * Test Builder {@link Builder#withMaxAutoRetriesNextServer(int)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withMaxAutoRetriesNextServer(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withMaxAutoRetriesNextServer(int)"})
  public void testBuilderWithMaxAutoRetriesNextServer_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithMaxAutoRetriesNextServerResult =
        newBuilderResult.withMaxAutoRetriesNextServer(42);

    // Assert
    IClientConfig iClientConfig = actualWithMaxAutoRetriesNextServerResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithMaxAutoRetriesNextServerResult);
  }

  /**
   * Test Builder {@link Builder#withMaxAutoRetries(int)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withMaxAutoRetries(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withMaxAutoRetries(int)"})
  public void testBuilderWithMaxAutoRetries_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithMaxAutoRetriesResult = newBuilderResult.withMaxAutoRetries(42);

    // Assert
    IClientConfig iClientConfig = actualWithMaxAutoRetriesResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithMaxAutoRetriesResult);
  }

  /**
   * Test Builder {@link Builder#withMaxConnectionsPerHost(int)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withMaxConnectionsPerHost(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withMaxConnectionsPerHost(int)"})
  public void testBuilderWithMaxConnectionsPerHost_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithMaxConnectionsPerHostResult = newBuilderResult.withMaxConnectionsPerHost(42);

    // Assert
    IClientConfig iClientConfig = actualWithMaxConnectionsPerHostResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithMaxConnectionsPerHostResult);
  }

  /**
   * Test Builder {@link Builder#withMaxTotalConnections(int)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withMaxTotalConnections(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withMaxTotalConnections(int)"})
  public void testBuilderWithMaxTotalConnections_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithMaxTotalConnectionsResult = newBuilderResult.withMaxTotalConnections(42);

    // Assert
    IClientConfig iClientConfig = actualWithMaxTotalConnectionsResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithMaxTotalConnectionsResult);
  }

  /**
   * Test Builder {@link Builder#withProxyHost(String)}.
   *
   * <ul>
   *   <li>Given newBuilder.
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withProxyHost(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withProxyHost(String)"})
  public void testBuilderWithProxyHost_givenNewBuilder_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithProxyHostResult = newBuilderResult.withProxyHost("localhost");

    // Assert
    IClientConfig iClientConfig = actualWithProxyHostResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithProxyHostResult);
  }

  /**
   * Test Builder {@link Builder#withProxyPort(int)}.
   *
   * <ul>
   *   <li>Given newBuilder.
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withProxyPort(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withProxyPort(int)"})
  public void testBuilderWithProxyPort_givenNewBuilder_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithProxyPortResult = newBuilderResult.withProxyPort(42);

    // Assert
    IClientConfig iClientConfig = actualWithProxyPortResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithProxyPortResult);
  }

  /**
   * Test Builder {@link Builder#withReadTimeout(int)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withReadTimeout(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withReadTimeout(int)"})
  public void testBuilderWithReadTimeout_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithReadTimeoutResult = newBuilderResult.withReadTimeout(42);

    // Assert
    IClientConfig iClientConfig = actualWithReadTimeoutResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithReadTimeoutResult);
  }

  /**
   * Test Builder {@link Builder#withRequestSpecificRetryOn(boolean)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withRequestSpecificRetryOn(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withRequestSpecificRetryOn(boolean)"})
  public void testBuilderWithRequestSpecificRetryOn_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithRequestSpecificRetryOnResult =
        newBuilderResult.withRequestSpecificRetryOn(true);

    // Assert
    IClientConfig iClientConfig = actualWithRequestSpecificRetryOnResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithRequestSpecificRetryOnResult);
  }

  /**
   * Test Builder {@link Builder#withRetryOnAllOperations(boolean)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withRetryOnAllOperations(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withRetryOnAllOperations(boolean)"})
  public void testBuilderWithRetryOnAllOperations_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithRetryOnAllOperationsResult = newBuilderResult.withRetryOnAllOperations(true);

    // Assert
    IClientConfig iClientConfig = actualWithRetryOnAllOperationsResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithRetryOnAllOperationsResult);
  }

  /**
   * Test Builder {@link Builder#withSecure(boolean)}.
   *
   * <ul>
   *   <li>Given newBuilder.
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withSecure(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withSecure(boolean)"})
  public void testBuilderWithSecure_givenNewBuilder_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithSecureResult = newBuilderResult.withSecure(true);

    // Assert
    IClientConfig iClientConfig = actualWithSecureResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithSecureResult);
  }

  /**
   * Test Builder {@link Builder#withServerListRefreshIntervalMills(int)}.
   *
   * <p>Method under test: {@link Builder#withServerListRefreshIntervalMills(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withServerListRefreshIntervalMills(int)"})
  public void testBuilderWithServerListRefreshIntervalMills() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithServerListRefreshIntervalMillsResult =
        newBuilderResult.withServerListRefreshIntervalMills(42);

    // Assert
    IClientConfig iClientConfig = actualWithServerListRefreshIntervalMillsResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithServerListRefreshIntervalMillsResult);
  }

  /**
   * Test Builder {@link Builder#withTargetRegion(String)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withTargetRegion(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withTargetRegion(String)"})
  public void testBuilderWithTargetRegion_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithTargetRegionResult = newBuilderResult.withTargetRegion("42");

    // Assert
    IClientConfig iClientConfig = actualWithTargetRegionResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithTargetRegionResult);
  }

  /**
   * Test Builder {@link Builder#withTrustStorePassword(String)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withTrustStorePassword(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withTrustStorePassword(String)"})
  public void testBuilderWithTrustStorePassword_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithTrustStorePasswordResult = newBuilderResult.withTrustStorePassword("42");

    // Assert
    IClientConfig iClientConfig = actualWithTrustStorePasswordResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithTrustStorePasswordResult);
  }

  /**
   * Test Builder {@link Builder#withTrustStore(String)}.
   *
   * <ul>
   *   <li>Given newBuilder.
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withTrustStore(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withTrustStore(String)"})
  public void testBuilderWithTrustStore_givenNewBuilder_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithTrustStoreResult = newBuilderResult.withTrustStore("42");

    // Assert
    IClientConfig iClientConfig = actualWithTrustStoreResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithTrustStoreResult);
  }

  /**
   * Test Builder {@link Builder#withZoneAffinityEnabled(boolean)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withZoneAffinityEnabled(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withZoneAffinityEnabled(boolean)"})
  public void testBuilderWithZoneAffinityEnabled_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithZoneAffinityEnabledResult = newBuilderResult.withZoneAffinityEnabled(true);

    // Assert
    IClientConfig iClientConfig = actualWithZoneAffinityEnabledResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithZoneAffinityEnabledResult);
  }

  /**
   * Test Builder {@link Builder#withZoneExclusivityEnabled(boolean)}.
   *
   * <ul>
   *   <li>Then build return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withZoneExclusivityEnabled(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withZoneExclusivityEnabled(boolean)"})
  public void testBuilderWithZoneExclusivityEnabled_thenBuildReturnDefaultClientConfigImpl() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();

    // Act
    Builder actualWithZoneExclusivityEnabledResult =
        newBuilderResult.withZoneExclusivityEnabled(true);

    // Assert
    IClientConfig iClientConfig = actualWithZoneExclusivityEnabledResult.build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
    assertSame(newBuilderResult, actualWithZoneExclusivityEnabledResult);
  }

  /**
   * Test {@link IClientConfig#getOrDefault(IClientConfigKey)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>Then return {@code Default Value}.
   * </ul>
   *
   * <p>Method under test: {@link IClientConfig#getOrDefault(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
