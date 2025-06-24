package com.netflix.ribbon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.ArchaiusClientConfigFactory;
import com.netflix.client.config.ClientConfigFactory;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.ribbon.RibbonTransportFactory.DefaultRibbonTransportFactory;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RibbonResourceFactoryDiffblueTest {
  /**
   * Test {@link RibbonResourceFactory#getTransportFactory()}.
   * <p>
   * Method under test: {@link RibbonResourceFactory#getTransportFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"RibbonTransportFactory RibbonResourceFactory.getTransportFactory()"})
  public void testGetTransportFactory() {
    // Arrange
    ClientConfigFactory clientConfigFactory = mock(ClientConfigFactory.class);
    DefaultResourceFactory defaultResourceFactory = new DefaultResourceFactory(clientConfigFactory,
        new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act
    RibbonTransportFactory actualTransportFactory = defaultResourceFactory.getTransportFactory();

    // Assert
    assertTrue(actualTransportFactory instanceof DefaultRibbonTransportFactory);
    assertSame(defaultResourceFactory.transportFactory, actualTransportFactory);
  }

  /**
   * Test {@link RibbonResourceFactory#getClientConfigFactory()}.
   * <p>
   * Method under test: {@link RibbonResourceFactory#getClientConfigFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientConfigFactory RibbonResourceFactory.getClientConfigFactory()"})
  public void testGetClientConfigFactory() {
    // Arrange and Act
    ClientConfigFactory actualClientConfigFactory = RibbonResourceFactory.DEFAULT.getClientConfigFactory();
    IClientConfig actualNewConfigResult = actualClientConfigFactory.newConfig();

    // Assert
    assertTrue(actualClientConfigFactory instanceof ArchaiusClientConfigFactory);
    assertTrue(actualNewConfigResult instanceof DefaultClientConfigImpl);
    assertEquals("", actualNewConfigResult.getClientName());
    assertEquals("/", ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPrimeConnectionsUri());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultClientClassname());
    assertEquals("ribbon", actualNewConfigResult.getNameSpace());
    assertNull(((DefaultClientConfigImpl) actualNewConfigResult).getResolver());
    assertNull(((DefaultClientConfigImpl) actualNewConfigResult).getAppName());
    assertNull(((DefaultClientConfigImpl) actualNewConfigResult).getVersion());
    assertEquals(0, actualClientConfigFactory.getPriority());
    assertEquals(0, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxAutoRetries());
    assertEquals(0.0f, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(0L, ((DefaultClientConfigImpl) actualNewConfigResult).getRefreshCount());
    assertEquals(1, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(200, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultConnectionManagerTimeout());
    assertEquals(30000,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(30000, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultConnectionidleTimeInMsecs());
    assertEquals(30000, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(50, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultReadTimeout());
    assertEquals(60000, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPort());
    assertEquals(9, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultPoolKeepAliveTime());
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
    assertEquals(Integer.MAX_VALUE,
        ((DefaultClientConfigImpl) actualNewConfigResult).getDefaultMaxRequestsAllowedPerWindow());
  }
}
