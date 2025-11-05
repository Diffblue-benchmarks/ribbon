package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IClientConfigDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test: {@link IClientConfig.Builder#build()}
   */
  @Test
  public void testBuilderBuild() {
    // Arrange, Act and Assert
    assertTrue(IClientConfig.Builder.newBuilder()
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
   * Method under test:
   * {@link IClientConfig.Builder#ignoreUserTokenInConnectionPoolForSecureClient(boolean)}
   */
  @Test
  public void testBuilderIgnoreUserTokenInConnectionPoolForSecureClient() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#newBuilder(Class)}
   */
  @Test
  public void testBuilderNewBuilder() {
    // Arrange
    Class<IClientConfig> implClass = IClientConfig.class;

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    IClientConfig.Builder.newBuilder(implClass);
  }

  /**
   * Method under test: {@link IClientConfig.Builder#newBuilder(Class, String)}
   */
  @Test
  public void testBuilderNewBuilder2() {
    // Arrange
    Class<IClientConfig> implClass = IClientConfig.class;

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    IClientConfig.Builder.newBuilder(implClass, "Dr Jane Doe");
  }

  /**
   * Method under test: {@link IClientConfig.Builder#newBuilder(String)}
   */
  @Test
  public void testBuilderNewBuilder3() {
    // Arrange, Act and Assert
    IClientConfig buildResult = IClientConfig.Builder.newBuilder("Dr Jane Doe").build();
    assertTrue(((DefaultClientConfigImpl) buildResult).getPropertyResolver() instanceof ArchaiusPropertyResolver);
    assertTrue(buildResult instanceof DefaultClientConfigImpl);
    Map<String, Object> properties = buildResult.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("Dr Jane Doe", buildResult.getClientName());
    assertNull(((DefaultClientConfigImpl) buildResult).getResolver());
    assertNull(((DefaultClientConfigImpl) buildResult).getAppName());
    assertNull(((DefaultClientConfigImpl) buildResult).getVersion());
    assertEquals(0, ((DefaultClientConfigImpl) buildResult).getDefaultMaxAutoRetries());
    assertEquals(0L, ((DefaultClientConfigImpl) buildResult).getRefreshCount());
    assertEquals(1, ((DefaultClientConfigImpl) buildResult).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) buildResult).getDefaultPoolMinThreads());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) buildResult).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultUseIpAddressForServer());
    assertTrue(((DefaultClientConfigImpl) buildResult).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) buildResult).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) buildResult).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) buildResult).getDefaultPrioritizeVipAddressBasedServers());
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("IsClientAuthRequired"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        ((DefaultClientConfigImpl) buildResult).getDefaultClientClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, properties.get("ClientClassName"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((DefaultClientConfigImpl) buildResult).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((DefaultClientConfigImpl) buildResult).getDefaultConnectionidleTimeInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        ((DefaultClientConfigImpl) buildResult).getDefaultConnectTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        ((DefaultClientConfigImpl) buildResult).getDefaultConnectionManagerTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_REQUESTS_ALLOWED_PER_WINDOW,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxRequestsAllowedPerWindow());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_RETRIES_PER_SERVER_PRIME_CONNECTION,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxTotalConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxTotalHttpConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((DefaultClientConfigImpl) buildResult).getDefaultPoolMaxThreads());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MIN_PRIME_CONNECTIONS_RATIO,
        ((DefaultClientConfigImpl) buildResult).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        ((DefaultClientConfigImpl) buildResult).getDefaultNfloadbalancerClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_PING_CLASSNAME,
        ((DefaultClientConfigImpl) buildResult).getDefaultNfloadbalancerPingClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_RULE_CLASSNAME,
        ((DefaultClientConfigImpl) buildResult).getDefaultNfloadbalancerRuleClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PERCENTAGE_NIWS_EVENT_LOGGED,
        ((DefaultClientConfigImpl) buildResult).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_POOL_KEEP_ALIVE_TIME,
        ((DefaultClientConfigImpl) buildResult).getDefaultPoolKeepAliveTime());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PORT,
        ((DefaultClientConfigImpl) buildResult).getDefaultPort());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_CLASS,
        ((DefaultClientConfigImpl) buildResult).getDefaultPrimeConnectionsClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        ((DefaultClientConfigImpl) buildResult).getDefaultPrimeConnectionsUri());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI, properties.get("PrimeConnectionsURI"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_READ_TIMEOUT,
        ((DefaultClientConfigImpl) buildResult).getDefaultReadTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_REQUEST_THROTTLING_WINDOW_IN_MILLIS,
        ((DefaultClientConfigImpl) buildResult).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_SEVER_LIST_CLASS,
        ((DefaultClientConfigImpl) buildResult).getDefaultSeverListClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_VIPADDRESS_RESOLVER_CLASSNAME,
        ((DefaultClientConfigImpl) buildResult).getDefaultVipaddressResolverClassname());
    assertEquals(CommonClientConfigKey.DEFAULT_NAME_SPACE, buildResult.getNameSpace());
  }

  /**
   * Method under test: {@link IClientConfig.Builder#newBuilder(String, String)}
   */
  @Test
  public void testBuilderNewBuilder4() {
    // Arrange, Act and Assert
    IClientConfig buildResult = IClientConfig.Builder.newBuilder("Dr Jane Doe", "Property Name Space").build();
    assertTrue(((DefaultClientConfigImpl) buildResult).getPropertyResolver() instanceof ArchaiusPropertyResolver);
    assertTrue(buildResult instanceof DefaultClientConfigImpl);
    Map<String, Object> properties = buildResult.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("Dr Jane Doe", buildResult.getClientName());
    assertEquals("Property Name Space", buildResult.getNameSpace());
    assertNull(((DefaultClientConfigImpl) buildResult).getResolver());
    assertNull(((DefaultClientConfigImpl) buildResult).getAppName());
    assertNull(((DefaultClientConfigImpl) buildResult).getVersion());
    assertEquals(0, ((DefaultClientConfigImpl) buildResult).getDefaultMaxAutoRetries());
    assertEquals(0L, ((DefaultClientConfigImpl) buildResult).getRefreshCount());
    assertEquals(1, ((DefaultClientConfigImpl) buildResult).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) buildResult).getDefaultPoolMinThreads());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) buildResult).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) buildResult).getDefaultUseIpAddressForServer());
    assertTrue(((DefaultClientConfigImpl) buildResult).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) buildResult).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) buildResult).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) buildResult).getDefaultPrioritizeVipAddressBasedServers());
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("IsClientAuthRequired"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        ((DefaultClientConfigImpl) buildResult).getDefaultClientClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, properties.get("ClientClassName"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((DefaultClientConfigImpl) buildResult).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((DefaultClientConfigImpl) buildResult).getDefaultConnectionidleTimeInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        ((DefaultClientConfigImpl) buildResult).getDefaultConnectTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        ((DefaultClientConfigImpl) buildResult).getDefaultConnectionManagerTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_REQUESTS_ALLOWED_PER_WINDOW,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxRequestsAllowedPerWindow());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_RETRIES_PER_SERVER_PRIME_CONNECTION,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxTotalConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((DefaultClientConfigImpl) buildResult).getDefaultMaxTotalHttpConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((DefaultClientConfigImpl) buildResult).getDefaultPoolMaxThreads());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MIN_PRIME_CONNECTIONS_RATIO,
        ((DefaultClientConfigImpl) buildResult).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        ((DefaultClientConfigImpl) buildResult).getDefaultNfloadbalancerClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_PING_CLASSNAME,
        ((DefaultClientConfigImpl) buildResult).getDefaultNfloadbalancerPingClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_RULE_CLASSNAME,
        ((DefaultClientConfigImpl) buildResult).getDefaultNfloadbalancerRuleClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PERCENTAGE_NIWS_EVENT_LOGGED,
        ((DefaultClientConfigImpl) buildResult).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_POOL_KEEP_ALIVE_TIME,
        ((DefaultClientConfigImpl) buildResult).getDefaultPoolKeepAliveTime());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PORT,
        ((DefaultClientConfigImpl) buildResult).getDefaultPort());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_CLASS,
        ((DefaultClientConfigImpl) buildResult).getDefaultPrimeConnectionsClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        ((DefaultClientConfigImpl) buildResult).getDefaultPrimeConnectionsUri());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI, properties.get("PrimeConnectionsURI"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_READ_TIMEOUT,
        ((DefaultClientConfigImpl) buildResult).getDefaultReadTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_REQUEST_THROTTLING_WINDOW_IN_MILLIS,
        ((DefaultClientConfigImpl) buildResult).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_SEVER_LIST_CLASS,
        ((DefaultClientConfigImpl) buildResult).getDefaultSeverListClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_VIPADDRESS_RESOLVER_CLASSNAME,
        ((DefaultClientConfigImpl) buildResult).getDefaultVipaddressResolverClassname());
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#prioritizeVipAddressBasedServers(boolean)}
   */
  @Test
  public void testBuilderPrioritizeVipAddressBasedServers() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.prioritizeVipAddressBasedServers(true));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withClientAuthRequired(boolean)}
   */
  @Test
  public void testBuilderWithClientAuthRequired() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withClientAuthRequired(true));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withConnIdleEvictTimeMilliSeconds(int)}
   */
  @Test
  public void testBuilderWithConnIdleEvictTimeMilliSeconds() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withConnIdleEvictTimeMilliSeconds(42));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withConnectTimeout(int)}
   */
  @Test
  public void testBuilderWithConnectTimeout() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withConnectTimeout(42));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withConnectionCleanerRepeatIntervalMills(int)}
   */
  @Test
  public void testBuilderWithConnectionCleanerRepeatIntervalMills() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withConnectionCleanerRepeatIntervalMills(42));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withConnectionManagerTimeout(int)}
   */
  @Test
  public void testBuilderWithConnectionManagerTimeout() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withConnectionManagerTimeout(42));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withConnectionPoolCleanerTaskEnabled(boolean)}
   */
  @Test
  public void testBuilderWithConnectionPoolCleanerTaskEnabled() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withConnectionPoolCleanerTaskEnabled(true));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withCustomSSLSocketFactoryClassName(String)}
   */
  @Test
  public void testBuilderWithCustomSSLSocketFactoryClassName() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withCustomSSLSocketFactoryClassName("42"));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withDefaultValues()}
   */
  @Test
  public void testBuilderWithDefaultValues() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withDefaultValues());
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withDeploymentContextBasedVipAddresses(String)}
   */
  @Test
  public void testBuilderWithDeploymentContextBasedVipAddresses() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St"));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withEnablePrimeConnections(boolean)}
   */
  @Test
  public void testBuilderWithEnablePrimeConnections() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withEnablePrimeConnections(true));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withFollowRedirects(boolean)}
   */
  @Test
  public void testBuilderWithFollowRedirects() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withFollowRedirects(true));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withForceClientPortConfiguration(boolean)}
   */
  @Test
  public void testBuilderWithForceClientPortConfiguration() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withForceClientPortConfiguration(true));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withGZIPContentEncodingFilterEnabled(boolean)}
   */
  @Test
  public void testBuilderWithGZIPContentEncodingFilterEnabled() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withGZIPContentEncodingFilterEnabled(true));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withHostnameValidationRequired(boolean)}
   */
  @Test
  public void testBuilderWithHostnameValidationRequired() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withHostnameValidationRequired(true));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withKeyStore(String)}
   */
  @Test
  public void testBuilderWithKeyStore() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withKeyStore("42"));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withKeyStorePassword(String)}
   */
  @Test
  public void testBuilderWithKeyStorePassword() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withKeyStorePassword("42"));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withLoadBalancerEnabled(boolean)}
   */
  @Test
  public void testBuilderWithLoadBalancerEnabled() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withLoadBalancerEnabled(true));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withMaxAutoRetries(int)}
   */
  @Test
  public void testBuilderWithMaxAutoRetries() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withMaxAutoRetries(42));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withMaxAutoRetriesNextServer(int)}
   */
  @Test
  public void testBuilderWithMaxAutoRetriesNextServer() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withMaxAutoRetriesNextServer(42));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withMaxConnectionsPerHost(int)}
   */
  @Test
  public void testBuilderWithMaxConnectionsPerHost() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withMaxConnectionsPerHost(42));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withMaxTotalConnections(int)}
   */
  @Test
  public void testBuilderWithMaxTotalConnections() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withMaxTotalConnections(42));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withProxyHost(String)}
   */
  @Test
  public void testBuilderWithProxyHost() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withProxyHost("localhost"));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withProxyPort(int)}
   */
  @Test
  public void testBuilderWithProxyPort() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withProxyPort(42));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withReadTimeout(int)}
   */
  @Test
  public void testBuilderWithReadTimeout() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withReadTimeout(42));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withRequestSpecificRetryOn(boolean)}
   */
  @Test
  public void testBuilderWithRequestSpecificRetryOn() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withRequestSpecificRetryOn(true));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withRetryOnAllOperations(boolean)}
   */
  @Test
  public void testBuilderWithRetryOnAllOperations() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withRetryOnAllOperations(true));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withSecure(boolean)}
   */
  @Test
  public void testBuilderWithSecure() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withSecure(true));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withServerListRefreshIntervalMills(int)}
   */
  @Test
  public void testBuilderWithServerListRefreshIntervalMills() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withServerListRefreshIntervalMills(42));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withTargetRegion(String)}
   */
  @Test
  public void testBuilderWithTargetRegion() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withTargetRegion("42"));
  }

  /**
   * Method under test: {@link IClientConfig.Builder#withTrustStore(String)}
   */
  @Test
  public void testBuilderWithTrustStore() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withTrustStore("42"));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withTrustStorePassword(String)}
   */
  @Test
  public void testBuilderWithTrustStorePassword() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withTrustStorePassword("42"));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withZoneAffinityEnabled(boolean)}
   */
  @Test
  public void testBuilderWithZoneAffinityEnabled() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withZoneAffinityEnabled(true));
  }

  /**
   * Method under test:
   * {@link IClientConfig.Builder#withZoneExclusivityEnabled(boolean)}
   */
  @Test
  public void testBuilderWithZoneExclusivityEnabled() {
    // Arrange
    IClientConfig.Builder newBuilderResult = IClientConfig.Builder.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withZoneExclusivityEnabled(true));
  }

  /**
   * Method under test: {@link IClientConfig#getOrDefault(IClientConfigKey)}
   */
  @Test
  public void testGetOrDefault() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    assertNull(emptyConfig.getOrDefault((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")));
  }

  /**
   * Method under test: {@link IClientConfig#getOrDefault(IClientConfigKey)}
   */
  @Test
  public void testGetOrDefault2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertNull(emptyConfig.getOrDefault((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")));
  }

  /**
   * Method under test: {@link IClientConfig#getIfSet(IClientConfigKey)}
   */
  @Test
  public void testGetIfSet() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    assertFalse(emptyConfig.getIfSet((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")).isPresent());
  }

  /**
   * Method under test: {@link IClientConfig#getIfSet(IClientConfigKey)}
   */
  @Test
  public void testGetIfSet2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertFalse(emptyConfig.getIfSet((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")).isPresent());
  }
}
