package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class DefaultClientConfigImplDiffblueTest {
  /**
   * Method under test: {@link DefaultClientConfigImpl#loadDefaultValues()}
   */
  @Test
  public void testLoadDefaultValues() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.loadDefaultValues();

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("EnableZoneAffinity"));
    String expectedString2 = Boolean.FALSE.toString();
    assertEquals(expectedString2, properties.get("FollowRedirects"));
    String expectedString3 = Boolean.FALSE.toString();
    assertEquals(expectedString3, properties.get("IsClientAuthRequired"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, properties.get("ClientClassName"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI, properties.get("PrimeConnectionsURI"));
  }

  /**
   * Method under test:
   * {@link DefaultClientConfigImpl#getDefaultPropName(IClientConfigKey)}
   */
  @Test
  public void testGetDefaultPropName() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    IClientConfigKey propName = mock(IClientConfigKey.class);
    when(propName.key()).thenReturn("Key");

    // Act
    String actualDefaultPropName = emptyConfig.getDefaultPropName(propName);

    // Assert
    verify(propName).key();
    assertEquals("ribbon.Key", actualDefaultPropName);
  }

  /**
   * Method under test: {@link DefaultClientConfigImpl#getDefaultPropName(String)}
   */
  @Test
  public void testGetDefaultPropName2() {
    // Arrange, Act and Assert
    assertEquals("ribbon.Prop Name", DefaultClientConfigImpl.getEmptyConfig().getDefaultPropName("Prop Name"));
  }

  /**
   * Method under test:
   * {@link DefaultClientConfigImpl#getInstancePropName(String, IClientConfigKey)}
   */
  @Test
  public void testGetInstancePropName() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    IClientConfigKey configKey = mock(IClientConfigKey.class);
    when(configKey.key()).thenReturn("Key");

    // Act
    String actualInstancePropName = emptyConfig.getInstancePropName("Dr Jane Doe", configKey);

    // Assert
    verify(configKey).key();
    assertEquals("Dr Jane Doe.ribbon.Key", actualInstancePropName);
  }

  /**
   * Method under test:
   * {@link DefaultClientConfigImpl#getInstancePropName(String, String)}
   */
  @Test
  public void testGetInstancePropName2() {
    // Arrange, Act and Assert
    assertEquals("Dr Jane Doe.ribbon.Key",
        DefaultClientConfigImpl.getEmptyConfig().getInstancePropName("Dr Jane Doe", "Key"));
  }

  /**
   * Method under test:
   * {@link DefaultClientConfigImpl#getClientConfigWithDefaultValues()}
   */
  @Test
  public void testGetClientConfigWithDefaultValues() {
    // Arrange and Act
    DefaultClientConfigImpl actualClientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues();

    // Assert
    assertTrue(actualClientConfigWithDefaultValues.getPropertyResolver() instanceof ArchaiusPropertyResolver);
    Map<String, Object> properties = actualClientConfigWithDefaultValues.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("default", actualClientConfigWithDefaultValues.getClientName());
    assertNull(actualClientConfigWithDefaultValues.getResolver());
    assertNull(actualClientConfigWithDefaultValues.getAppName());
    assertNull(actualClientConfigWithDefaultValues.getVersion());
    assertEquals(0, actualClientConfigWithDefaultValues.getDefaultMaxAutoRetries());
    assertEquals(0L, actualClientConfigWithDefaultValues.getRefreshCount());
    assertEquals(1, actualClientConfigWithDefaultValues.getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, actualClientConfigWithDefaultValues.getDefaultPoolMinThreads());
    assertEquals(TimeUnit.SECONDS, actualClientConfigWithDefaultValues.getDefaultPoolKeepAliveTimeUnits());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableGzipContentEncodingFilter());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnablePrimeConnections());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableRequestThrottling());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableZoneAffinity());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableZoneExclusivity());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultFollowRedirects());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultIsClientAuthRequired());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultOkToRetryOnAllOperations());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultUseIpAddressForServer());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultEnableConnectionPool());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultEnableLoadbalancer());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultPrioritizeVipAddressBasedServers());
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("EnableZoneAffinity"));
    String expectedString2 = Boolean.FALSE.toString();
    assertEquals(expectedString2, properties.get("FollowRedirects"));
    String expectedString3 = Boolean.FALSE.toString();
    assertEquals(expectedString3, properties.get("IsClientAuthRequired"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultClientClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, properties.get("ClientClassName"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualClientConfigWithDefaultValues.getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualClientConfigWithDefaultValues.getDefaultConnectionidleTimeInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualClientConfigWithDefaultValues.getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        actualClientConfigWithDefaultValues.getDefaultConnectTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        actualClientConfigWithDefaultValues.getDefaultConnectionManagerTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        actualClientConfigWithDefaultValues.getDefaultMaxConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        actualClientConfigWithDefaultValues.getDefaultMaxHttpConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_REQUESTS_ALLOWED_PER_WINDOW,
        actualClientConfigWithDefaultValues.getDefaultMaxRequestsAllowedPerWindow());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_RETRIES_PER_SERVER_PRIME_CONNECTION,
        actualClientConfigWithDefaultValues.getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        actualClientConfigWithDefaultValues.getDefaultMaxTotalConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        actualClientConfigWithDefaultValues.getDefaultMaxTotalHttpConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        actualClientConfigWithDefaultValues.getDefaultPoolMaxThreads());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MIN_PRIME_CONNECTIONS_RATIO,
        actualClientConfigWithDefaultValues.getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultNfloadbalancerClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_PING_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultNfloadbalancerPingClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_RULE_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultNfloadbalancerRuleClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PERCENTAGE_NIWS_EVENT_LOGGED,
        actualClientConfigWithDefaultValues.getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_POOL_KEEP_ALIVE_TIME,
        actualClientConfigWithDefaultValues.getDefaultPoolKeepAliveTime());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PORT, actualClientConfigWithDefaultValues.getDefaultPort());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_CLASS,
        actualClientConfigWithDefaultValues.getDefaultPrimeConnectionsClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        actualClientConfigWithDefaultValues.getDefaultPrimeConnectionsUri());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI, properties.get("PrimeConnectionsURI"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_READ_TIMEOUT,
        actualClientConfigWithDefaultValues.getDefaultReadTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_REQUEST_THROTTLING_WINDOW_IN_MILLIS,
        actualClientConfigWithDefaultValues.getDefaultRequestThrottlingWindowInMillis());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_SEVER_LIST_CLASS,
        actualClientConfigWithDefaultValues.getDefaultSeverListClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_VIPADDRESS_RESOLVER_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultVipaddressResolverClassname());
    assertEquals(DefaultClientConfigImpl.DEFAULT_PROPERTY_NAME_SPACE,
        actualClientConfigWithDefaultValues.getNameSpace());
  }

  /**
   * Method under test:
   * {@link DefaultClientConfigImpl#getClientConfigWithDefaultValues(String)}
   */
  @Test
  public void testGetClientConfigWithDefaultValues2() {
    // Arrange and Act
    DefaultClientConfigImpl actualClientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe");

    // Assert
    assertTrue(actualClientConfigWithDefaultValues.getPropertyResolver() instanceof ArchaiusPropertyResolver);
    Map<String, Object> properties = actualClientConfigWithDefaultValues.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("Dr Jane Doe", actualClientConfigWithDefaultValues.getClientName());
    assertNull(actualClientConfigWithDefaultValues.getResolver());
    assertNull(actualClientConfigWithDefaultValues.getAppName());
    assertNull(actualClientConfigWithDefaultValues.getVersion());
    assertEquals(0, actualClientConfigWithDefaultValues.getDefaultMaxAutoRetries());
    assertEquals(0L, actualClientConfigWithDefaultValues.getRefreshCount());
    assertEquals(1, actualClientConfigWithDefaultValues.getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, actualClientConfigWithDefaultValues.getDefaultPoolMinThreads());
    assertEquals(TimeUnit.SECONDS, actualClientConfigWithDefaultValues.getDefaultPoolKeepAliveTimeUnits());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableGzipContentEncodingFilter());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnablePrimeConnections());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableRequestThrottling());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableZoneAffinity());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableZoneExclusivity());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultFollowRedirects());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultIsClientAuthRequired());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultOkToRetryOnAllOperations());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultUseIpAddressForServer());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultEnableConnectionPool());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultEnableLoadbalancer());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultPrioritizeVipAddressBasedServers());
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("EnableZoneAffinity"));
    String expectedString2 = Boolean.FALSE.toString();
    assertEquals(expectedString2, properties.get("FollowRedirects"));
    String expectedString3 = Boolean.FALSE.toString();
    assertEquals(expectedString3, properties.get("IsClientAuthRequired"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultClientClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, properties.get("ClientClassName"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualClientConfigWithDefaultValues.getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualClientConfigWithDefaultValues.getDefaultConnectionidleTimeInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualClientConfigWithDefaultValues.getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        actualClientConfigWithDefaultValues.getDefaultConnectTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        actualClientConfigWithDefaultValues.getDefaultConnectionManagerTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        actualClientConfigWithDefaultValues.getDefaultMaxConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        actualClientConfigWithDefaultValues.getDefaultMaxHttpConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_REQUESTS_ALLOWED_PER_WINDOW,
        actualClientConfigWithDefaultValues.getDefaultMaxRequestsAllowedPerWindow());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_RETRIES_PER_SERVER_PRIME_CONNECTION,
        actualClientConfigWithDefaultValues.getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        actualClientConfigWithDefaultValues.getDefaultMaxTotalConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        actualClientConfigWithDefaultValues.getDefaultMaxTotalHttpConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        actualClientConfigWithDefaultValues.getDefaultPoolMaxThreads());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MIN_PRIME_CONNECTIONS_RATIO,
        actualClientConfigWithDefaultValues.getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultNfloadbalancerClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_PING_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultNfloadbalancerPingClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_RULE_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultNfloadbalancerRuleClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PERCENTAGE_NIWS_EVENT_LOGGED,
        actualClientConfigWithDefaultValues.getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_POOL_KEEP_ALIVE_TIME,
        actualClientConfigWithDefaultValues.getDefaultPoolKeepAliveTime());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PORT, actualClientConfigWithDefaultValues.getDefaultPort());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_CLASS,
        actualClientConfigWithDefaultValues.getDefaultPrimeConnectionsClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        actualClientConfigWithDefaultValues.getDefaultPrimeConnectionsUri());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI, properties.get("PrimeConnectionsURI"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_READ_TIMEOUT,
        actualClientConfigWithDefaultValues.getDefaultReadTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_REQUEST_THROTTLING_WINDOW_IN_MILLIS,
        actualClientConfigWithDefaultValues.getDefaultRequestThrottlingWindowInMillis());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_SEVER_LIST_CLASS,
        actualClientConfigWithDefaultValues.getDefaultSeverListClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_VIPADDRESS_RESOLVER_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultVipaddressResolverClassname());
    assertEquals(DefaultClientConfigImpl.DEFAULT_PROPERTY_NAME_SPACE,
        actualClientConfigWithDefaultValues.getNameSpace());
  }

  /**
   * Method under test:
   * {@link DefaultClientConfigImpl#getClientConfigWithDefaultValues(String, String)}
   */
  @Test
  public void testGetClientConfigWithDefaultValues3() {
    // Arrange and Act
    DefaultClientConfigImpl actualClientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");

    // Assert
    assertTrue(actualClientConfigWithDefaultValues.getPropertyResolver() instanceof ArchaiusPropertyResolver);
    Map<String, Object> properties = actualClientConfigWithDefaultValues.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("Dr Jane Doe", actualClientConfigWithDefaultValues.getClientName());
    assertNull(actualClientConfigWithDefaultValues.getResolver());
    assertNull(actualClientConfigWithDefaultValues.getAppName());
    assertNull(actualClientConfigWithDefaultValues.getVersion());
    assertEquals(0, actualClientConfigWithDefaultValues.getDefaultMaxAutoRetries());
    assertEquals(0L, actualClientConfigWithDefaultValues.getRefreshCount());
    assertEquals(1, actualClientConfigWithDefaultValues.getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, actualClientConfigWithDefaultValues.getDefaultPoolMinThreads());
    assertEquals(TimeUnit.SECONDS, actualClientConfigWithDefaultValues.getDefaultPoolKeepAliveTimeUnits());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableGzipContentEncodingFilter());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnablePrimeConnections());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableRequestThrottling());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableZoneAffinity());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultEnableZoneExclusivity());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultFollowRedirects());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultIsClientAuthRequired());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultOkToRetryOnAllOperations());
    assertFalse(actualClientConfigWithDefaultValues.getDefaultUseIpAddressForServer());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultEnableConnectionPool());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultEnableLoadbalancer());
    assertTrue(actualClientConfigWithDefaultValues.getDefaultPrioritizeVipAddressBasedServers());
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("EnableZoneAffinity"));
    String expectedString2 = Boolean.FALSE.toString();
    assertEquals(expectedString2, properties.get("FollowRedirects"));
    String expectedString3 = Boolean.FALSE.toString();
    assertEquals(expectedString3, properties.get("IsClientAuthRequired"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultClientClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, properties.get("ClientClassName"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualClientConfigWithDefaultValues.getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualClientConfigWithDefaultValues.getDefaultConnectionidleTimeInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualClientConfigWithDefaultValues.getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        actualClientConfigWithDefaultValues.getDefaultConnectTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        actualClientConfigWithDefaultValues.getDefaultConnectionManagerTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        actualClientConfigWithDefaultValues.getDefaultMaxConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        actualClientConfigWithDefaultValues.getDefaultMaxHttpConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_REQUESTS_ALLOWED_PER_WINDOW,
        actualClientConfigWithDefaultValues.getDefaultMaxRequestsAllowedPerWindow());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_RETRIES_PER_SERVER_PRIME_CONNECTION,
        actualClientConfigWithDefaultValues.getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        actualClientConfigWithDefaultValues.getDefaultMaxTotalConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        actualClientConfigWithDefaultValues.getDefaultMaxTotalHttpConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        actualClientConfigWithDefaultValues.getDefaultPoolMaxThreads());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MIN_PRIME_CONNECTIONS_RATIO,
        actualClientConfigWithDefaultValues.getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultNfloadbalancerClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_PING_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultNfloadbalancerPingClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_RULE_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultNfloadbalancerRuleClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PERCENTAGE_NIWS_EVENT_LOGGED,
        actualClientConfigWithDefaultValues.getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_POOL_KEEP_ALIVE_TIME,
        actualClientConfigWithDefaultValues.getDefaultPoolKeepAliveTime());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PORT, actualClientConfigWithDefaultValues.getDefaultPort());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_CLASS,
        actualClientConfigWithDefaultValues.getDefaultPrimeConnectionsClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        actualClientConfigWithDefaultValues.getDefaultPrimeConnectionsUri());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI, properties.get("PrimeConnectionsURI"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_READ_TIMEOUT,
        actualClientConfigWithDefaultValues.getDefaultReadTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_REQUEST_THROTTLING_WINDOW_IN_MILLIS,
        actualClientConfigWithDefaultValues.getDefaultRequestThrottlingWindowInMillis());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_SEVER_LIST_CLASS,
        actualClientConfigWithDefaultValues.getDefaultSeverListClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_VIPADDRESS_RESOLVER_CLASSNAME,
        actualClientConfigWithDefaultValues.getDefaultVipaddressResolverClassname());
    assertEquals(DefaultClientConfigImpl.DEFAULT_PROPERTY_NAME_SPACE,
        actualClientConfigWithDefaultValues.getNameSpace());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DefaultClientConfigImpl#getDefaultClientClassname()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultConnectTimeout()}
   *   <li>
   * {@link DefaultClientConfigImpl#getDefaultConnectionIdleTimertaskRepeatInMsecs()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultConnectionManagerTimeout()}
   *   <li>
   * {@link DefaultClientConfigImpl#getDefaultConnectionPoolCleanerTaskEnabled()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultConnectionidleTimeInMsecs()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultEnableConnectionPool()}
   *   <li>
   * {@link DefaultClientConfigImpl#getDefaultEnableGzipContentEncodingFilter()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultEnableLoadbalancer()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultEnablePrimeConnections()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultEnableRequestThrottling()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultEnableZoneAffinity()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultEnableZoneExclusivity()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultFollowRedirects()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultIsClientAuthRequired()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultMaxAutoRetries()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultMaxAutoRetriesNextServer()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultMaxConnectionsPerHost()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultMaxHttpConnectionsPerHost()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultMaxRequestsAllowedPerWindow()}
   *   <li>
   * {@link DefaultClientConfigImpl#getDefaultMaxRetriesPerServerPrimeConnection()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultMaxTotalConnections()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultMaxTotalHttpConnections()}
   *   <li>
   * {@link DefaultClientConfigImpl#getDefaultMaxTotalTimeToPrimeConnections()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultMinPrimeConnectionsRatio()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultNfloadbalancerClassname()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultNfloadbalancerPingClassname()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultNfloadbalancerRuleClassname()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultOkToRetryOnAllOperations()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultPercentageNiwsEventLogged()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultPoolKeepAliveTime()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultPoolKeepAliveTimeUnits()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultPoolMaxThreads()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultPoolMinThreads()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultPort()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultPrimeConnectionsClass()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultPrimeConnectionsUri()}
   *   <li>
   * {@link DefaultClientConfigImpl#getDefaultPrioritizeVipAddressBasedServers()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultReadTimeout()}
   *   <li>
   * {@link DefaultClientConfigImpl#getDefaultRequestThrottlingWindowInMillis()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultSeverListClass()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultUseIpAddressForServer()}
   *   <li>{@link DefaultClientConfigImpl#getDefaultVipaddressResolverClassname()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    String actualDefaultClientClassname = emptyConfig.getDefaultClientClassname();
    int actualDefaultConnectTimeout = emptyConfig.getDefaultConnectTimeout();
    int actualDefaultConnectionIdleTimertaskRepeatInMsecs = emptyConfig
        .getDefaultConnectionIdleTimertaskRepeatInMsecs();
    int actualDefaultConnectionManagerTimeout = emptyConfig.getDefaultConnectionManagerTimeout();
    Boolean actualDefaultConnectionPoolCleanerTaskEnabled = emptyConfig.getDefaultConnectionPoolCleanerTaskEnabled();
    int actualDefaultConnectionidleTimeInMsecs = emptyConfig.getDefaultConnectionidleTimeInMsecs();
    Boolean actualDefaultEnableConnectionPool = emptyConfig.getDefaultEnableConnectionPool();
    Boolean actualDefaultEnableGzipContentEncodingFilter = emptyConfig.getDefaultEnableGzipContentEncodingFilter();
    Boolean actualDefaultEnableLoadbalancer = emptyConfig.getDefaultEnableLoadbalancer();
    Boolean actualDefaultEnablePrimeConnections = emptyConfig.getDefaultEnablePrimeConnections();
    Boolean actualDefaultEnableRequestThrottling = emptyConfig.getDefaultEnableRequestThrottling();
    Boolean actualDefaultEnableZoneAffinity = emptyConfig.getDefaultEnableZoneAffinity();
    Boolean actualDefaultEnableZoneExclusivity = emptyConfig.getDefaultEnableZoneExclusivity();
    Boolean actualDefaultFollowRedirects = emptyConfig.getDefaultFollowRedirects();
    Boolean actualDefaultIsClientAuthRequired = emptyConfig.getDefaultIsClientAuthRequired();
    int actualDefaultMaxAutoRetries = emptyConfig.getDefaultMaxAutoRetries();
    int actualDefaultMaxAutoRetriesNextServer = emptyConfig.getDefaultMaxAutoRetriesNextServer();
    int actualDefaultMaxConnectionsPerHost = emptyConfig.getDefaultMaxConnectionsPerHost();
    int actualDefaultMaxHttpConnectionsPerHost = emptyConfig.getDefaultMaxHttpConnectionsPerHost();
    int actualDefaultMaxRequestsAllowedPerWindow = emptyConfig.getDefaultMaxRequestsAllowedPerWindow();
    int actualDefaultMaxRetriesPerServerPrimeConnection = emptyConfig.getDefaultMaxRetriesPerServerPrimeConnection();
    int actualDefaultMaxTotalConnections = emptyConfig.getDefaultMaxTotalConnections();
    int actualDefaultMaxTotalHttpConnections = emptyConfig.getDefaultMaxTotalHttpConnections();
    int actualDefaultMaxTotalTimeToPrimeConnections = emptyConfig.getDefaultMaxTotalTimeToPrimeConnections();
    float actualDefaultMinPrimeConnectionsRatio = emptyConfig.getDefaultMinPrimeConnectionsRatio();
    String actualDefaultNfloadbalancerClassname = emptyConfig.getDefaultNfloadbalancerClassname();
    String actualDefaultNfloadbalancerPingClassname = emptyConfig.getDefaultNfloadbalancerPingClassname();
    String actualDefaultNfloadbalancerRuleClassname = emptyConfig.getDefaultNfloadbalancerRuleClassname();
    Boolean actualDefaultOkToRetryOnAllOperations = emptyConfig.getDefaultOkToRetryOnAllOperations();
    float actualDefaultPercentageNiwsEventLogged = emptyConfig.getDefaultPercentageNiwsEventLogged();
    long actualDefaultPoolKeepAliveTime = emptyConfig.getDefaultPoolKeepAliveTime();
    TimeUnit actualDefaultPoolKeepAliveTimeUnits = emptyConfig.getDefaultPoolKeepAliveTimeUnits();
    int actualDefaultPoolMaxThreads = emptyConfig.getDefaultPoolMaxThreads();
    int actualDefaultPoolMinThreads = emptyConfig.getDefaultPoolMinThreads();
    int actualDefaultPort = emptyConfig.getDefaultPort();
    String actualDefaultPrimeConnectionsClass = emptyConfig.getDefaultPrimeConnectionsClass();
    String actualDefaultPrimeConnectionsUri = emptyConfig.getDefaultPrimeConnectionsUri();
    Boolean actualDefaultPrioritizeVipAddressBasedServers = emptyConfig.getDefaultPrioritizeVipAddressBasedServers();
    int actualDefaultReadTimeout = emptyConfig.getDefaultReadTimeout();
    int actualDefaultRequestThrottlingWindowInMillis = emptyConfig.getDefaultRequestThrottlingWindowInMillis();
    String actualDefaultSeverListClass = emptyConfig.getDefaultSeverListClass();
    boolean actualDefaultUseIpAddressForServer = emptyConfig.getDefaultUseIpAddressForServer();

    // Assert
    assertEquals(0, actualDefaultMaxAutoRetries);
    assertEquals(1, actualDefaultMaxAutoRetriesNextServer);
    assertEquals(1, actualDefaultPoolMinThreads);
    assertEquals(TimeUnit.SECONDS, actualDefaultPoolKeepAliveTimeUnits);
    assertFalse(actualDefaultEnableGzipContentEncodingFilter);
    assertFalse(actualDefaultEnablePrimeConnections);
    assertFalse(actualDefaultEnableRequestThrottling);
    assertFalse(actualDefaultEnableZoneAffinity);
    assertFalse(actualDefaultEnableZoneExclusivity);
    assertFalse(actualDefaultFollowRedirects);
    assertFalse(actualDefaultIsClientAuthRequired);
    assertFalse(actualDefaultOkToRetryOnAllOperations);
    assertFalse(actualDefaultUseIpAddressForServer);
    assertTrue(actualDefaultConnectionPoolCleanerTaskEnabled);
    assertTrue(actualDefaultEnableConnectionPool);
    assertTrue(actualDefaultEnableLoadbalancer);
    assertTrue(actualDefaultPrioritizeVipAddressBasedServers);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, actualDefaultClientClassname);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualDefaultConnectionIdleTimertaskRepeatInMsecs);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualDefaultConnectionidleTimeInMsecs);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        actualDefaultMaxTotalTimeToPrimeConnections);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT, actualDefaultConnectTimeout);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        actualDefaultConnectionManagerTimeout);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST, actualDefaultMaxConnectionsPerHost);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        actualDefaultMaxHttpConnectionsPerHost);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_REQUESTS_ALLOWED_PER_WINDOW,
        actualDefaultMaxRequestsAllowedPerWindow);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_RETRIES_PER_SERVER_PRIME_CONNECTION,
        actualDefaultMaxRetriesPerServerPrimeConnection);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS, actualDefaultMaxTotalConnections);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS, actualDefaultMaxTotalHttpConnections);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS, actualDefaultPoolMaxThreads);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MIN_PRIME_CONNECTIONS_RATIO,
        actualDefaultMinPrimeConnectionsRatio, 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        actualDefaultNfloadbalancerClassname);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_PING_CLASSNAME,
        actualDefaultNfloadbalancerPingClassname);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_RULE_CLASSNAME,
        actualDefaultNfloadbalancerRuleClassname);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PERCENTAGE_NIWS_EVENT_LOGGED,
        actualDefaultPercentageNiwsEventLogged, 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_POOL_KEEP_ALIVE_TIME, actualDefaultPoolKeepAliveTime);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PORT, actualDefaultPort);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_CLASS, actualDefaultPrimeConnectionsClass);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI, actualDefaultPrimeConnectionsUri);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_READ_TIMEOUT, actualDefaultReadTimeout);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_REQUEST_THROTTLING_WINDOW_IN_MILLIS,
        actualDefaultRequestThrottlingWindowInMillis);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_SEVER_LIST_CLASS, actualDefaultSeverListClass);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_VIPADDRESS_RESOLVER_CLASSNAME,
        emptyConfig.getDefaultVipaddressResolverClassname());
  }
}
