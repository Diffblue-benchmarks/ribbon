package com.netflix.client.config;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
   * Test {@link IClientConfig#getOrDefault(IClientConfigKey)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link IClientConfig#getOrDefault(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object IClientConfig.getOrDefault(IClientConfigKey)"})
  public void testGetOrDefault_thenReturnNull() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    Object actualOrDefault =
        emptyConfig.getOrDefault(new DefaultClientConfigImplTest().new NewConfigKey("Config Key"));

    // Assert
    assertNull(actualOrDefault);
  }
}
