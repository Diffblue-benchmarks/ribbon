package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.DummyPing;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListFilter;
import com.netflix.loadbalancer.ZoneAffinityServerListFilter;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class ClientFactoryDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link ClientFactory#getNamedLoadBalancer(String)} with {@code name}.
   *
   * <p>Method under test: {@link ClientFactory#getNamedLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ILoadBalancer ClientFactory.getNamedLoadBalancer(String)"})
  public void testGetNamedLoadBalancerWithName() {
    // Arrange and Act
    ILoadBalancer actualNamedLoadBalancer =
        ClientFactory.getNamedLoadBalancer("\"TestLoadBalancer\"");

    // Assert
    IClientConfig clientConfig =
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    IPing ping = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPing();
    assertTrue(ping instanceof DummyPing);
    ServerListFilter<Server> filter =
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getFilter();
    assertTrue(filter instanceof ZoneAffinityServerListFilter);
    assertTrue(actualNamedLoadBalancer instanceof ZoneAwareLoadBalancer);
    assertEquals("\"TestLoadBalancer\"", clientConfig.getClientName());
    assertEquals(
        "\"TestLoadBalancer\"",
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getName());
    LoadBalancerStats loadBalancerStats =
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getLoadBalancerStats();
    assertEquals("\"TestLoadBalancer\"", loadBalancerStats.getName());
    assertSame(
        loadBalancerStats, ((ZoneAffinityServerListFilter<Server>) filter).getLoadBalancerStats());
    assertSame(actualNamedLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualNamedLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link ClientFactory#getNamedLoadBalancer(String)} with {@code name}.
   *
   * <ul>
   *   <li>Then return ClientConfig ClientName is {@code ribbon}.
   * </ul>
   *
   * <p>Method under test: {@link ClientFactory#getNamedLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ILoadBalancer ClientFactory.getNamedLoadBalancer(String)"})
  public void testGetNamedLoadBalancerWithName_thenReturnClientConfigClientNameIsRibbon() {
    // Arrange and Act
    ILoadBalancer actualNamedLoadBalancer = ClientFactory.getNamedLoadBalancer("ribbon");

    // Assert
    IClientConfig clientConfig =
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    IPing ping = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPing();
    assertTrue(ping instanceof DummyPing);
    ServerListFilter<Server> filter =
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getFilter();
    assertTrue(filter instanceof ZoneAffinityServerListFilter);
    assertTrue(actualNamedLoadBalancer instanceof ZoneAwareLoadBalancer);
    assertEquals("ribbon", clientConfig.getClientName());
    assertEquals("ribbon", ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getName());
    LoadBalancerStats loadBalancerStats =
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getLoadBalancerStats();
    assertEquals("ribbon", loadBalancerStats.getName());
    assertSame(
        loadBalancerStats, ((ZoneAffinityServerListFilter<Server>) filter).getLoadBalancerStats());
    assertSame(actualNamedLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualNamedLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link ClientFactory#getNamedLoadBalancer(String)} with {@code name}.
   *
   * <ul>
   *   <li>Then return ClientConfig ClientName is space.
   * </ul>
   *
   * <p>Method under test: {@link ClientFactory#getNamedLoadBalancer(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ILoadBalancer ClientFactory.getNamedLoadBalancer(String)"})
  public void testGetNamedLoadBalancerWithName_thenReturnClientConfigClientNameIsSpace() {
    // Arrange and Act
    ILoadBalancer actualNamedLoadBalancer = ClientFactory.getNamedLoadBalancer(" ");

    // Assert
    IClientConfig clientConfig =
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    IPing ping = ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPing();
    assertTrue(ping instanceof DummyPing);
    ServerListFilter<Server> filter =
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getFilter();
    assertTrue(filter instanceof ZoneAffinityServerListFilter);
    assertTrue(actualNamedLoadBalancer instanceof ZoneAwareLoadBalancer);
    assertEquals(" ", clientConfig.getClientName());
    assertEquals(" ", ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getName());
    LoadBalancerStats loadBalancerStats =
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getLoadBalancerStats();
    assertEquals(" ", loadBalancerStats.getName());
    assertSame(
        loadBalancerStats, ((ZoneAffinityServerListFilter<Server>) filter).getLoadBalancerStats());
    assertSame(actualNamedLoadBalancer, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualNamedLoadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link ClientFactory#registerNamedLoadBalancerFromProperties(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then throw {@link ClientException}.
   * </ul>
   *
   * <p>Method under test: {@link ClientFactory#registerNamedLoadBalancerFromProperties(String,
   * Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ILoadBalancer ClientFactory.registerNamedLoadBalancerFromProperties(String, Class)"
  })
  public void testRegisterNamedLoadBalancerFromProperties_whenName_thenThrowClientException()
      throws ClientException {
    // Arrange
    Class<IClientConfig> configClass = IClientConfig.class;

    // Act and Assert
    thrown.expect(ClientException.class);
    ClientFactory.registerNamedLoadBalancerFromProperties("Name", configClass);
  }

  /**
   * Test {@link ClientFactory#getNamedConfig(String, Class)} with {@code name}, {@code
   * clientConfigClass}.
   *
   * <ul>
   *   <li>When {@code "testClientConfig"}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ClientFactory#getNamedConfig(String, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ClientFactory.getNamedConfig(String, Class)"})
  public void testGetNamedConfigWithNameClientConfigClass_whenTestClientConfig_thenReturnNull() {
    // Arrange
    Class<IClientConfig> clientConfigClass = IClientConfig.class;

    // Act and Assert
    assertNull(ClientFactory.getNamedConfig("\"testClientConfig\"", clientConfigClass));
  }

  /**
   * Test {@link ClientFactory#getNamedConfig(String, Supplier)} with {@code name}, {@code factory}.
   *
   * <ul>
   *   <li>When {@code "testClientConfig"}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ClientFactory#getNamedConfig(String, Supplier)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ClientFactory.getNamedConfig(String, Supplier)"})
  public void testGetNamedConfigWithNameFactory_whenTestClientConfig_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        ClientFactory.getNamedConfig(
            "\"testClientConfig\"", new ClientRequest()::getOverrideConfig));
  }

  /**
   * Test {@link ClientFactory#getNamedConfig(String)} with {@code name}.
   *
   * <ul>
   *   <li>Then return ClientName is {@code "loadBalancerClientConfig"}.
   * </ul>
   *
   * <p>Method under test: {@link ClientFactory#getNamedConfig(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ClientFactory.getNamedConfig(String)"})
  public void testGetNamedConfigWithName_thenReturnClientNameIsLoadBalancerClientConfig() {
    // Arrange and Act
    IClientConfig actualNamedConfig = ClientFactory.getNamedConfig("\"loadBalancerClientConfig\"");

    // Assert
    assertTrue(actualNamedConfig instanceof DefaultClientConfigImpl);
    assertEquals(
        "/", ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPrimeConnectionsUri());
    assertEquals("\"loadBalancerClientConfig\"", actualNamedConfig.getClientName());
    assertEquals(
        "com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultVipaddressResolverClassname());
    assertEquals(
        "com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals(
        "com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultSeverListClass());
    assertEquals(
        "com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals(
        "com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultNfloadbalancerClassname());
    assertEquals(
        "com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPrimeConnectionsClass());
    assertEquals(
        "com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultClientClassname());
    assertEquals("ribbon", actualNamedConfig.getNameSpace());
    assertNull(((DefaultClientConfigImpl) actualNamedConfig).getResolver());
    assertNull(((DefaultClientConfigImpl) actualNamedConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) actualNamedConfig).getVersion());
    assertEquals(0, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxAutoRetries());
    assertEquals(
        0.0f,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPercentageNiwsEventLogged(),
        0.0f);
    assertEquals(0L, ((DefaultClientConfigImpl) actualNamedConfig).getRefreshCount());
    assertEquals(
        1, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolMinThreads());
    assertEquals(
        1.0f,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMinPrimeConnectionsRatio(),
        0.0f);
    assertEquals(
        200, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxTotalConnections());
    assertEquals(
        200, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectTimeout());
    assertEquals(
        2000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectionManagerTimeout());
    assertEquals(
        30000,
        ((DefaultClientConfigImpl) actualNamedConfig)
            .getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(
        30000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(
        30000,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxTotalTimeToPrimeConnections());
    Map<String, Object> properties = actualNamedConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals(
        50, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(
        50, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultReadTimeout());
    assertEquals(
        60000,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPort());
    assertEquals(
        9,
        ((DefaultClientConfigImpl) actualNamedConfig)
            .getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolKeepAliveTime());
    assertEquals(
        TimeUnit.SECONDS,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultUseIpAddressForServer());
    assertTrue(
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableLoadbalancer());
    assertTrue(
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(properties.containsKey("ClientClassName"));
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
    assertEquals(
        Integer.MAX_VALUE,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxRequestsAllowedPerWindow());
  }

  /**
   * Test {@link ClientFactory#getNamedConfig(String)} with {@code name}.
   *
   * <ul>
   *   <li>Then return ClientName is {@code %s isn't parameterized}.
   * </ul>
   *
   * <p>Method under test: {@link ClientFactory#getNamedConfig(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ClientFactory.getNamedConfig(String)"})
  public void testGetNamedConfigWithName_thenReturnClientNameIsSIsnTParameterized() {
    // Arrange and Act
    IClientConfig actualNamedConfig = ClientFactory.getNamedConfig("%s isn't parameterized");

    // Assert
    assertTrue(actualNamedConfig instanceof DefaultClientConfigImpl);
    assertEquals("%s isn't parameterized", actualNamedConfig.getClientName());
    assertEquals(
        "/", ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPrimeConnectionsUri());
    assertEquals(
        "com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultVipaddressResolverClassname());
    assertEquals(
        "com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals(
        "com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultSeverListClass());
    assertEquals(
        "com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals(
        "com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultNfloadbalancerClassname());
    assertEquals(
        "com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPrimeConnectionsClass());
    assertEquals(
        "com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultClientClassname());
    assertEquals("ribbon", actualNamedConfig.getNameSpace());
    assertNull(((DefaultClientConfigImpl) actualNamedConfig).getResolver());
    assertNull(((DefaultClientConfigImpl) actualNamedConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) actualNamedConfig).getVersion());
    assertEquals(0, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxAutoRetries());
    assertEquals(
        0.0f,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPercentageNiwsEventLogged(),
        0.0f);
    assertEquals(0L, ((DefaultClientConfigImpl) actualNamedConfig).getRefreshCount());
    assertEquals(
        1, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolMinThreads());
    assertEquals(
        1.0f,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMinPrimeConnectionsRatio(),
        0.0f);
    assertEquals(
        200, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxTotalConnections());
    assertEquals(
        200, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectTimeout());
    assertEquals(
        2000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectionManagerTimeout());
    assertEquals(
        30000,
        ((DefaultClientConfigImpl) actualNamedConfig)
            .getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(
        30000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(
        30000,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxTotalTimeToPrimeConnections());
    Map<String, Object> properties = actualNamedConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals(
        50, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(
        50, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultReadTimeout());
    assertEquals(
        60000,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPort());
    assertEquals(
        9,
        ((DefaultClientConfigImpl) actualNamedConfig)
            .getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolKeepAliveTime());
    assertEquals(
        TimeUnit.SECONDS,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) actualNamedConfig).getDefaultUseIpAddressForServer());
    assertTrue(
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) actualNamedConfig).getDefaultEnableLoadbalancer());
    assertTrue(
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(properties.containsKey("ClientClassName"));
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
    assertEquals(
        Integer.MAX_VALUE,
        ((DefaultClientConfigImpl) actualNamedConfig).getDefaultMaxRequestsAllowedPerWindow());
  }
}
