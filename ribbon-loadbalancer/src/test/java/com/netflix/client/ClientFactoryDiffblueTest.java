package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.ConfigurationBasedServerList;
import com.netflix.loadbalancer.DummyPing;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.PollingServerListUpdater;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ServerListFilter;
import com.netflix.loadbalancer.ServerListUpdater;
import com.netflix.loadbalancer.ZoneAffinityServerListFilter;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ClientFactoryDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test: {@link ClientFactory#getNamedLoadBalancer(String)}
   */
  @Test
  public void testGetNamedLoadBalancer() {
    // Arrange and Act
    ILoadBalancer actualNamedLoadBalancer = ClientFactory.getNamedLoadBalancer("Name");

    // Assert
    IClientConfig clientConfig = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertTrue(((AvailabilityFilteringRule) rule).getPredicate() instanceof CompositePredicate);
    ServerList<Server> serverListImpl = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getServerListImpl();
    assertTrue(serverListImpl instanceof ConfigurationBasedServerList);
    IPing ping = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPing();
    assertTrue(ping instanceof DummyPing);
    ServerListUpdater serverListUpdater = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer)
        .getServerListUpdater();
    assertTrue(serverListUpdater instanceof PollingServerListUpdater);
    ServerListFilter<Server> filter = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getFilter();
    assertTrue(filter instanceof ZoneAffinityServerListFilter);
    assertTrue(actualNamedLoadBalancer instanceof ZoneAwareLoadBalancer);
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("/", ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsUri());
    assertEquals("/", properties.get("PrimeConnectionsURI"));
    assertEquals("Name", clientConfig.getClientName());
    assertEquals("Name", ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getName());
    LoadBalancerStats loadBalancerStats = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer)
        .getLoadBalancerStats();
    assertEquals("Name", loadBalancerStats.getName());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) clientConfig).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) clientConfig).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) clientConfig).getDefaultClientClassname());
    assertEquals("com.netflix.niws.client.http.RestClient", properties.get("ClientClassName"));
    assertEquals("ribbon", clientConfig.getNameSpace());
    assertNull(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPrimeConnections());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(((DefaultClientConfigImpl) clientConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getVersion());
    assertEquals(0, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetries());
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(17L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertEquals(2, ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getMaxTotalPingTime());
    assertEquals(2, ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getCoreThreads());
    assertEquals(2, serverListUpdater.getCoreThreads());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionManagerTimeout());
    assertEquals(30, ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPingInterval());
    assertEquals(30000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(30000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(30000, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) clientConfig).getDefaultReadTimeout());
    assertEquals(60000, ((DefaultClientConfigImpl) clientConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) clientConfig).getDefaultPort());
    assertEquals(9, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTime());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultUseIpAddressForServer());
    assertFalse(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).isEnablePrimingConnections());
    assertFalse(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).isPingInProgress());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(actualNamedLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualNamedLoadBalancer.getReachableServers().isEmpty());
    assertTrue(serverListImpl.getInitialListOfServers().isEmpty());
    assertTrue(serverListImpl.getUpdatedListOfServers().isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("EnableZoneAffinity"));
    assertEquals(Integer.MAX_VALUE, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRequestsAllowedPerWindow());
    assertSame(loadBalancerStats, ((ZoneAffinityServerListFilter<Server>) filter).getLoadBalancerStats());
    assertSame(actualNamedLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualNamedLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ClientFactory#getNamedLoadBalancer(String, Class)}
   */
  @Test
  public void testGetNamedLoadBalancer2() {
    // Arrange
    Class<IClientConfig> configClass = IClientConfig.class;

    // Act
    ILoadBalancer actualNamedLoadBalancer = ClientFactory.getNamedLoadBalancer("Name", configClass);

    // Assert
    IClientConfig clientConfig = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    assertTrue(((AvailabilityFilteringRule) rule).getPredicate() instanceof CompositePredicate);
    ServerList<Server> serverListImpl = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getServerListImpl();
    assertTrue(serverListImpl instanceof ConfigurationBasedServerList);
    IPing ping = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPing();
    assertTrue(ping instanceof DummyPing);
    ServerListUpdater serverListUpdater = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer)
        .getServerListUpdater();
    assertTrue(serverListUpdater instanceof PollingServerListUpdater);
    ServerListFilter<Server> filter = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getFilter();
    assertTrue(filter instanceof ZoneAffinityServerListFilter);
    assertTrue(actualNamedLoadBalancer instanceof ZoneAwareLoadBalancer);
    Map<String, Object> properties = clientConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("/", ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsUri());
    assertEquals("/", properties.get("PrimeConnectionsURI"));
    assertEquals("Name", clientConfig.getClientName());
    assertEquals("Name", ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getName());
    LoadBalancerStats loadBalancerStats = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer)
        .getLoadBalancerStats();
    assertEquals("Name", loadBalancerStats.getName());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) clientConfig).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) clientConfig).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) clientConfig).getDefaultClientClassname());
    assertEquals("com.netflix.niws.client.http.RestClient", properties.get("ClientClassName"));
    assertEquals("ribbon", clientConfig.getNameSpace());
    assertNull(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPrimeConnections());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(((DefaultClientConfigImpl) clientConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getVersion());
    assertEquals(0, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetries());
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(17L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertEquals(2, ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getMaxTotalPingTime());
    assertEquals(2, ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getCoreThreads());
    assertEquals(2, serverListUpdater.getCoreThreads());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionManagerTimeout());
    assertEquals(30, ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPingInterval());
    assertEquals(30000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(30000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(30000, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) clientConfig).getDefaultReadTimeout());
    assertEquals(60000, ((DefaultClientConfigImpl) clientConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) clientConfig).getDefaultPort());
    assertEquals(9, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTime());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultUseIpAddressForServer());
    assertFalse(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).isEnablePrimingConnections());
    assertFalse(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).isPingInProgress());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(actualNamedLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualNamedLoadBalancer.getReachableServers().isEmpty());
    assertTrue(serverListImpl.getInitialListOfServers().isEmpty());
    assertTrue(serverListImpl.getUpdatedListOfServers().isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("EnableZoneAffinity"));
    assertEquals(Integer.MAX_VALUE, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRequestsAllowedPerWindow());
    assertSame(loadBalancerStats, ((ZoneAffinityServerListFilter<Server>) filter).getLoadBalancerStats());
    assertSame(actualNamedLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualNamedLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ClientFactory#getNamedLoadBalancer(String, Class)}
   */
  @Test
  public void testGetNamedLoadBalancer3() {
    // Arrange
    Class<IClientConfig> configClass = IClientConfig.class;

    // Act and Assert
    thrown.expect(RuntimeException.class);
    ClientFactory.getNamedLoadBalancer("com.netflix.client.ClientFactory", configClass);
  }

  /**
   * Method under test:
   * {@link ClientFactory#registerNamedLoadBalancerFromclientConfig(String, IClientConfig)}
   */
  @Test
  public void testRegisterNamedLoadBalancerFromclientConfig() throws ClientException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    ClientFactory.registerNamedLoadBalancerFromclientConfig("Name", DefaultClientConfigImpl.getEmptyConfig());
  }

  /**
   * Method under test:
   * {@link ClientFactory#registerNamedLoadBalancerFromProperties(String, Class)}
   */
  @Test
  public void testRegisterNamedLoadBalancerFromProperties() throws ClientException {
    // Arrange
    Class<IClientConfig> configClass = IClientConfig.class;

    // Act and Assert
    thrown.expect(ClientException.class);
    ClientFactory.registerNamedLoadBalancerFromProperties("Name", configClass);
  }

  /**
   * Method under test:
   * {@link ClientFactory#registerNamedLoadBalancerFromProperties(String, Class)}
   */
  @Test
  public void testRegisterNamedLoadBalancerFromProperties2() throws ClientException {
    // Arrange
    Class<IClientConfig> configClass = IClientConfig.class;

    // Act and Assert
    thrown.expect(ClientException.class);
    ClientFactory.registerNamedLoadBalancerFromProperties("com.netflix.client.ClientFactory", configClass);
  }

  /**
   * Method under test:
   * {@link ClientFactory#instantiateInstanceWithClientConfig(String, IClientConfig)}
   */
  @Test
  public void testInstantiateInstanceWithClientConfig()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange, Act and Assert
    assertTrue(ClientFactory.instantiateInstanceWithClientConfig("com.netflix.client.ClientFactory",
        DefaultClientConfigImpl.getEmptyConfig()) instanceof ClientFactory);
  }

  /**
   * Method under test: {@link ClientFactory#getNamedConfig(String)}
   */
  @Test
  public void testGetNamedConfig() {
    // Arrange and Act
    IClientConfig actualNamedConfig = ClientFactory.getNamedConfig("NameName");

    // Assert
    assertTrue(actualNamedConfig instanceof DefaultClientConfigImpl);
    Map<String, Object> properties = actualNamedConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("/", ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPrimeConnectionsUri());
    assertEquals("/", properties.get("PrimeConnectionsURI"));
    assertEquals("NameName", actualNamedConfig.getClientName());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultClientClassname());
    assertEquals("com.netflix.niws.client.http.RestClient", properties.get("ClientClassName"));
    assertEquals("ribbon", actualNamedConfig.getNameSpace());
    assertNull(((DefaultClientConfigImpl) actualNamedConfig).getResolver());
    assertNull(((DefaultClientConfigImpl) actualNamedConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) actualNamedConfig).getVersion());
    assertEquals(0, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxAutoRetries());
    assertEquals(0.0f, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(0L, ((DefaultClientConfigImpl) actualNamedConfig).getRefreshCount());
    assertEquals(1, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(200, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectionManagerTimeout());
    assertEquals(30000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(30000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(30000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(50, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultReadTimeout());
    assertEquals(60000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPort());
    assertEquals(9, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolKeepAliveTime());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultUseIpAddressForServer());
    assertTrue(((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) actualNamedConfig).getDefaultPrioritizeVipAddressBasedServers());
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("EnableZoneAffinity"));
    String expectedString2 = Boolean.FALSE.toString();
    assertEquals(expectedString2, properties.get("FollowRedirects"));
    String expectedString3 = Boolean.FALSE.toString();
    assertEquals(expectedString3, properties.get("IsClientAuthRequired"));
    assertEquals(Integer.MAX_VALUE,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxRequestsAllowedPerWindow());
  }
}
