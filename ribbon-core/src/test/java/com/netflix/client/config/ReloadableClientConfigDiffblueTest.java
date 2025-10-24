package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import java.util.function.BiConsumer;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ReloadableClientConfigDiffblueTest {
  /**
   * Test {@link ReloadableClientConfig#setClientName(String)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setClientName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setClientName(String)"})
  public void testSetClientName() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setClientName("\"TestClientName\"");

    // Assert
    assertEquals("\"TestClientName\"", emptyConfig.getClientName());
  }

  /**
   * Test {@link ReloadableClientConfig#getClientName()}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#getClientName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ReloadableClientConfig.getClientName()"})
  public void testGetClientName() {
    // Arrange, Act and Assert
    assertEquals("", DefaultClientConfigImpl.getEmptyConfig().getClientName());
  }

  /**
   * Test {@link ReloadableClientConfig#getNameSpace()}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#getNameSpace()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ReloadableClientConfig.getNameSpace()"})
  public void testGetNameSpace() {
    // Arrange, Act and Assert
    assertEquals(
        CommonClientConfigKey.DEFAULT_NAME_SPACE,
        DefaultClientConfigImpl.getEmptyConfig().getNameSpace());
  }

  /**
   * Test {@link ReloadableClientConfig#setNameSpace(String)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setNameSpace(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setNameSpace(String)"})
  public void testSetNameSpace() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setNameSpace("\"client1.netflix.config\"");

    // Assert
    assertEquals("\"client1.netflix.config\"", emptyConfig.getNameSpace());
  }

  /**
   * Test {@link ReloadableClientConfig#loadProperties(String)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.loadProperties(String)"})
  public void testLoadProperties() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "\"TestClientName\"", "\"com.netflix.client.config.default\"");

    // Act
    clientConfigWithDefaultValues.loadProperties("\"testClient.netflix.config\"");

    // Assert that nothing has changed
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals(Boolean.FALSE.toString(), properties.get("EnableZoneAffinity"));
    assertEquals(Boolean.FALSE.toString(), properties.get("FollowRedirects"));
    assertEquals(Boolean.FALSE.toString(), properties.get("IsClientAuthRequired"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        properties.get("ClientClassName"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        properties.get("PrimeConnectionsURI"));
  }

  /**
   * Test {@link ReloadableClientConfig#loadProperties(String)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@code null}.
   *   <li>Then EmptyConfig ClientName is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.loadProperties(String)"})
  public void testLoadProperties_givenEmptyConfig_whenNull_thenEmptyConfigClientNameIsNull() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.loadProperties(null);

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertNull(emptyConfig.getClientName());
    assertEquals(Boolean.FALSE.toString(), properties.get("EnableZoneAffinity"));
    assertEquals(Boolean.FALSE.toString(), properties.get("FollowRedirects"));
    assertEquals(Boolean.FALSE.toString(), properties.get("IsClientAuthRequired"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        properties.get("ClientClassName"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        properties.get("PrimeConnectionsURI"));
  }

  /**
   * Test {@link ReloadableClientConfig#loadProperties(String)}.
   *
   * <ul>
   *   <li>Then EmptyConfig ClientName is {@code "testClient.netflix.config"}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.loadProperties(String)"})
  public void testLoadProperties_thenEmptyConfigClientNameIsTestClientNetflixConfig() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.loadProperties("\"testClient.netflix.config\"");

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("\"testClient.netflix.config\"", emptyConfig.getClientName());
    assertEquals(Boolean.FALSE.toString(), properties.get("EnableZoneAffinity"));
    assertEquals(Boolean.FALSE.toString(), properties.get("FollowRedirects"));
    assertEquals(Boolean.FALSE.toString(), properties.get("IsClientAuthRequired"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        properties.get("ClientClassName"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        properties.get("PrimeConnectionsURI"));
  }

  /**
   * Test {@link ReloadableClientConfig#loadProperties(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then EmptyConfig ClientName is empty string.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.loadProperties(String)"})
  public void testLoadProperties_whenEmptyString_thenEmptyConfigClientNameIsEmptyString() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.loadProperties("");

    // Assert
    assertEquals("", emptyConfig.getClientName());
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals(Boolean.FALSE.toString(), properties.get("EnableZoneAffinity"));
    assertEquals(Boolean.FALSE.toString(), properties.get("FollowRedirects"));
    assertEquals(Boolean.FALSE.toString(), properties.get("IsClientAuthRequired"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        properties.get("ClientClassName"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        properties.get("PrimeConnectionsURI"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperties()}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperties()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ReloadableClientConfig.getProperties()"})
  public void testGetProperties_givenEmptyConfig_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(DefaultClientConfigImpl.getEmptyConfig().getProperties().isEmpty());
  }

  /**
   * Test {@link ReloadableClientConfig#getProperties()}.
   *
   * <ul>
   *   <li>Then return size is thirty-nine.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperties()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ReloadableClientConfig.getProperties()"})
  public void testGetProperties_thenReturnSizeIsThirtyNine() {
    // Arrange and Act
    Map<String, Object> actualProperties =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
                "\"TestClientName\"", "\"com.netflix.client.config.default\"")
            .getProperties();

    // Assert
    assertEquals(39, actualProperties.size());
    assertEquals("", actualProperties.get("listOfServers"));
    assertEquals("5000", actualProperties.get("ReadTimeout"));
    assertEquals("7001", actualProperties.get("Port"));
    assertEquals(Boolean.FALSE.toString(), actualProperties.get("EnableZoneAffinity"));
    assertEquals(Boolean.FALSE.toString(), actualProperties.get("FollowRedirects"));
    assertEquals(Boolean.FALSE.toString(), actualProperties.get("IsClientAuthRequired"));
    assertEquals(Boolean.FALSE.toString(), actualProperties.get("OkToRetryOnAllOperations"));
    assertEquals(Boolean.TRUE.toString(), actualProperties.get("PrioritizeVipAddressBasedServers"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        actualProperties.get("ClientClassName"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        actualProperties.get("NFLoadBalancerClassName"));
    assertEquals(
        AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        actualProperties.get("PrimeConnectionsURI"));
  }

  /**
   * Test {@link ReloadableClientConfig#forEach(BiConsumer)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@link BiConsumer}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#forEach(BiConsumer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.forEach(BiConsumer)"})
  public void testForEach_givenEmptyConfig_whenBiConsumer_thenDoesNotThrow() {
    // Arrange, Act and Assert
    DefaultClientConfigImpl.getEmptyConfig().forEach(mock(BiConsumer.class));
  }

  /**
   * Test {@link ReloadableClientConfig#toString()}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>Then return {@code ClientConfig:}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ReloadableClientConfig.toString()"})
  public void testToString_givenEmptyConfig_thenReturnClientConfig() {
    // Arrange, Act and Assert
    assertEquals("ClientConfig:", DefaultClientConfigImpl.getEmptyConfig().toString());
  }

  /**
   * Test {@link ReloadableClientConfig#toString()}.
   *
   * <ul>
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ReloadableClientConfig.toString()"})
  public void testToString_thenReturnAString() {
    // Arrange, Act and Assert
    assertEquals(
        "ClientConfig:ConnectTimeout:2000, MaxConnectionsPerHost:50, MaxRetriesPerServerPrimeConnection:9,"
            + " MaxAutoRetries:0, MaxTotalConnections:200, PoolKeepAliveTime:900, ConnectionManagerTimeout:2000,"
            + " UseIPAddrForServer:false, VipAddressResolverClassName:com.netflix.client.SimpleVipAddressResolver,"
            + " MaxTotalHttpConnections:200, EnableConnectionPool:true, PoolMaxThreads:200, EnablePrimeConnections:false,"
            + " MinPrimeConnectionsRatio:1.0, PoolKeepAliveTimeUnits:SECONDS, EnableGZIPContentEncodingFilter:false,"
            + " NFLoadBalancerPingClassName:com.netflix.loadbalancer.DummyPing, EnableZoneExclusivity:false,"
            + " MaxAutoRetriesNextServer:1, NFLoadBalancerRuleClassName:com.netflix.loadbalancer.AvailabilityFilteringRule,"
            + " ProxyPort:null, PrimeConnectionsURI:/, IsClientAuthRequired:false, listOfServers:, ConnectionPoolCl"
            + "eanerTaskEnabled:true, EnableZoneAffinity:false, FollowRedirects:false, Port:7001, PrioritizeVipAddr"
            + "essBasedServers:true, ReadTimeout:5000, OkToRetryOnAllOperations:false, NFLoadBalancerClassName:com"
            + ".netflix.loadbalancer.ZoneAwareLoadBalancer, ClientClassName:com.netflix.niws.client.http.RestClient,"
            + " MaxHttpConnectionsPerHost:50, ConnectionCleanerRepeatInterval:30000, PoolMinThreads:1, PrimeConnect"
            + "ionsClassName:com.netflix.niws.client.http.HttpPrimeConnection, ProxyHost:null, ConnIdleEvictTimeMilliSeconds"
            + ":30000, MaxTotalTimeToPrimeConnections:30000, NIWSServerListClassName:com.netflix.loadbalancer"
            + ".ConfigurationBasedServerList",
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
                "\"TestClientName\"", "\"com.netflix.client.config.default\"")
            .toString());
  }

  /**
   * Test {@link ReloadableClientConfig#getRefreshCount()}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#getRefreshCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ReloadableClientConfig.getRefreshCount()"})
  public void testGetRefreshCount() {
    // Arrange, Act and Assert
    assertEquals(0L, DefaultClientConfigImpl.getEmptyConfig().getRefreshCount());
  }
}
