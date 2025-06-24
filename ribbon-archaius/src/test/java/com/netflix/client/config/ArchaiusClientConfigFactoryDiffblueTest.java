package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ArchaiusClientConfigFactoryDiffblueTest {
  /**
   * Test {@link ArchaiusClientConfigFactory#newConfig()}.
   * <p>
   * Method under test: {@link ArchaiusClientConfigFactory#newConfig()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ArchaiusClientConfigFactory.newConfig()"})
  public void testNewConfig() {
    // Arrange and Act
    IClientConfig actualNewConfigResult = (new ArchaiusClientConfigFactory()).newConfig();

    // Assert
    assertTrue(actualNewConfigResult instanceof DefaultClientConfigImpl);
    assertEquals("", actualNewConfigResult.getClientName());
    assertNull(((DefaultClientConfigImpl) actualNewConfigResult).getResolver());
    assertNull(((DefaultClientConfigImpl) actualNewConfigResult).getAppName());
    assertNull(((DefaultClientConfigImpl) actualNewConfigResult).getVersion());
    assertEquals(0, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxAutoRetries());
    assertEquals(0L, ((DefaultClientConfigImpl) actualNewConfigResult).getRefreshCount());
    assertEquals(1, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPoolMinThreads());
    assertEquals(TimeUnit.SECONDS,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultUseIpAddressForServer());
    assertTrue(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(actualNewConfigResult.getProperties().isEmpty());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultClientClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultConnectionidleTimeInMsecs());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultConnectTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultConnectionManagerTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_REQUESTS_ALLOWED_PER_WINDOW,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxRequestsAllowedPerWindow());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_RETRIES_PER_SERVER_PRIME_CONNECTION,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxTotalConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxTotalHttpConnections());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPoolMaxThreads());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MIN_PRIME_CONNECTIONS_RATIO,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultNfloadbalancerClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_PING_CLASSNAME,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultNfloadbalancerPingClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_RULE_CLASSNAME,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultNfloadbalancerRuleClassname());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PERCENTAGE_NIWS_EVENT_LOGGED,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_POOL_KEEP_ALIVE_TIME,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPoolKeepAliveTime());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PORT,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPort());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_CLASS,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPrimeConnectionsClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPrimeConnectionsUri());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_READ_TIMEOUT,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultReadTimeout());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_REQUEST_THROTTLING_WINDOW_IN_MILLIS,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_SEVER_LIST_CLASS,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultSeverListClass());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_VIPADDRESS_RESOLVER_CLASSNAME,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultVipaddressResolverClassname());
    assertEquals(CommonClientConfigKey.DEFAULT_NAME_SPACE, actualNewConfigResult.getNameSpace());
  }

  /**
   * Test new {@link ArchaiusClientConfigFactory} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ArchaiusClientConfigFactory}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ArchaiusClientConfigFactory.<init>()"})
  public void testNewArchaiusClientConfigFactory() {
    // Arrange, Act and Assert
    assertEquals(0, (new ArchaiusClientConfigFactory()).getPriority());
  }
}
