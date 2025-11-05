package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReloadableClientConfigDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test: {@link ReloadableClientConfig#setClientName(String)}
   */
  @Test
  public void testSetClientName() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setClientName("Dr Jane Doe");

    // Assert
    assertEquals("Dr Jane Doe", emptyConfig.getClientName());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#setClientName(String)}
   */
  @Test
  public void testSetClientName2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    emptyConfig.setClientName("Dr Jane Doe");

    // Assert
    assertEquals("Dr Jane Doe", emptyConfig.getClientName());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#getClientName()}
   */
  @Test
  public void testGetClientName() {
    // Arrange, Act and Assert
    assertEquals("", DefaultClientConfigImpl.getEmptyConfig().getClientName());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#getClientName()}
   */
  @Test
  public void testGetClientName2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertEquals("", emptyConfig.getClientName());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#getNameSpace()}
   */
  @Test
  public void testGetNameSpace() {
    // Arrange, Act and Assert
    assertEquals(CommonClientConfigKey.DEFAULT_NAME_SPACE, DefaultClientConfigImpl.getEmptyConfig().getNameSpace());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#getNameSpace()}
   */
  @Test
  public void testGetNameSpace2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertEquals(CommonClientConfigKey.DEFAULT_NAME_SPACE, emptyConfig.getNameSpace());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#setNameSpace(String)}
   */
  @Test
  public void testSetNameSpace() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setNameSpace("Name Space");

    // Assert
    assertEquals("Name Space", emptyConfig.getNameSpace());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#setNameSpace(String)}
   */
  @Test
  public void testSetNameSpace2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    emptyConfig.setNameSpace("Name Space");

    // Assert
    assertEquals("Name Space", emptyConfig.getNameSpace());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  public void testLoadProperties() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.loadProperties("Dr Jane Doe");

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("Dr Jane Doe", emptyConfig.getClientName());
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
   * Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  public void testLoadProperties2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.loadProperties(null);

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertNull(emptyConfig.getClientName());
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
   * Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  public void testLoadProperties3() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.loadProperties("");

    // Assert
    assertEquals("", emptyConfig.getClientName());
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
   * Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  public void testLoadProperties4() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    emptyConfig.loadProperties("Dr Jane Doe");

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("Dr Jane Doe", emptyConfig.getClientName());
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
   * Method under test: {@link ReloadableClientConfig#getProperties()}
   */
  @Test
  public void testGetProperties() {
    // Arrange, Act and Assert
    assertTrue(DefaultClientConfigImpl.getEmptyConfig().getProperties().isEmpty());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#getProperties()}
   */
  @Test
  public void testGetProperties2() {
    // Arrange and Act
    Map<String, Object> actualProperties = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getProperties();

    // Assert
    assertEquals(39, actualProperties.size());
    assertEquals("", actualProperties.get("listOfServers"));
    assertEquals("5000", actualProperties.get("ReadTimeout"));
    assertEquals("7001", actualProperties.get("Port"));
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, actualProperties.get("EnableZoneAffinity"));
    String expectedString2 = Boolean.FALSE.toString();
    assertEquals(expectedString2, actualProperties.get("FollowRedirects"));
    String expectedString3 = Boolean.FALSE.toString();
    assertEquals(expectedString3, actualProperties.get("IsClientAuthRequired"));
    String expectedString4 = Boolean.FALSE.toString();
    assertEquals(expectedString4, actualProperties.get("OkToRetryOnAllOperations"));
    String expectedString5 = Boolean.TRUE.toString();
    assertEquals(expectedString5, actualProperties.get("PrioritizeVipAddressBasedServers"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, actualProperties.get("ClientClassName"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        actualProperties.get("NFLoadBalancerClassName"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        actualProperties.get("PrimeConnectionsURI"));
  }

  /**
   * Method under test: {@link ReloadableClientConfig#getProperties()}
   */
  @Test
  public void testGetProperties3() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertTrue(emptyConfig.getProperties().isEmpty());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  public void testGet() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    assertNull(emptyConfig.get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")));
  }

  /**
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  public void testGet2() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");

    // Act and Assert
    assertNull(clientConfigWithDefaultValues.get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")));
  }

  /**
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  public void testGet3() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertNull(emptyConfig.get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")));
  }

  /**
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  public void testGet4() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setDefault((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Value");

    // Act and Assert
    assertEquals("Value", emptyConfig.get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")));
  }

  /**
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  public void testGet5() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "Name Space");

    // Act and Assert
    assertNull(clientConfigWithDefaultValues.get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")));
  }

  /**
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  public void testGet6() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl.getClientConfigWithDefaultValues("",
        "Name Space");

    // Act and Assert
    assertNull(clientConfigWithDefaultValues.get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  public void testGet7() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    assertEquals("Default Value",
        emptyConfig.get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  public void testGet8() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");

    // Act and Assert
    assertEquals("Default Value", clientConfigWithDefaultValues
        .get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  public void testGet9() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertEquals("Default Value",
        emptyConfig.get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  public void testGet10() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setDefault((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Value");

    // Act and Assert
    assertEquals("Value",
        emptyConfig.get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  public void testGet11() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "Name Space");

    // Act and Assert
    assertEquals("Default Value", clientConfigWithDefaultValues
        .get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  public void testGet12() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl.getClientConfigWithDefaultValues("",
        "Name Space");

    // Act and Assert
    assertEquals("Default Value", clientConfigWithDefaultValues
        .get((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)}
   */
  @Test
  public void testSetDefault() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setDefault((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Value");

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertTrue(properties.containsKey("Config Key"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)}
   */
  @Test
  public void testSetDefault2() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "key cannot be null");

    // Act
    clientConfigWithDefaultValues.setDefault((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"),
        "Value");

    // Assert
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(40, properties.size());
    assertTrue(properties.containsKey("ClientClassName"));
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)}
   */
  @Test
  public void testSetDefault3() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImplTest.NewConfigKey<Object> key = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act
    emptyConfig.setDefault(key,
        new DatabaseConfiguration(mock(DataSource.class), "key cannot be null", "key cannot be null", "42"));

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertTrue(properties.containsKey("Config Key"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)}
   */
  @Test
  public void testSetDefault4() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "key cannot be null");

    // Act
    clientConfigWithDefaultValues.setDefault((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"),
        "Value");

    // Assert
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(40, properties.size());
    assertTrue(properties.containsKey("ClientClassName"));
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  public void testSet() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.set((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Value");
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  public void testSet2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.set((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), 42);
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  public void testSet3() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    assertSame(emptyConfig, emptyConfig.set((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), null));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setProperty(CommonClientConfigKey.AppName, "Value");

    // Assert
    assertEquals("Value", emptyConfig.getAppName());
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertEquals("Value", properties.get("AppName"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty2() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Value may not be null");

    // Act
    clientConfigWithDefaultValues.setProperty(CommonClientConfigKey.AppName, "Value");

    // Assert
    assertEquals("Value", clientConfigWithDefaultValues.getAppName());
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(40, properties.size());
    assertTrue(properties.containsKey("ClientClassName"));
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty3() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    DefaultClientConfigImpl.getEmptyConfig().setProperty(CommonClientConfigKey.BackoffInterval, "Value");
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty4() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setProperty(CommonClientConfigKey.ConnectionPoolCleanerTaskEnabled, "Value");

    // Assert
    assertNull(emptyConfig.getAppName());
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("ConnectionPoolCleanerTaskEnabled"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty5() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.setProperty((new DefaultClientConfigImplTest()).new NewConfigKey("Value may not be null"), "Value");
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty6() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setProperty(CommonClientConfigKey.AppName, 42);

    // Assert
    assertEquals("42", emptyConfig.getAppName());
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertEquals("42", properties.get("AppName"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty7() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    emptyConfig.setProperty(CommonClientConfigKey.AppName, "Value");

    // Assert
    assertEquals("Value", emptyConfig.getAppName());
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertEquals("Value", properties.get("AppName"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty8() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "Value may not be null");

    // Act
    clientConfigWithDefaultValues.setProperty(CommonClientConfigKey.AppName, "Value");

    // Assert
    assertEquals("Value", clientConfigWithDefaultValues.getAppName());
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(40, properties.size());
    assertTrue(properties.containsKey("ClientClassName"));
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty9() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl.getClientConfigWithDefaultValues("",
        "Value may not be null");

    // Act
    clientConfigWithDefaultValues.setProperty(CommonClientConfigKey.AppName, "Value");

    // Assert
    assertEquals("Value", clientConfigWithDefaultValues.getAppName());
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(40, properties.size());
    assertTrue(properties.containsKey("ClientClassName"));
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty10() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Value may not be null");
    clientConfigWithDefaultValues.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 1);

    // Act
    clientConfigWithDefaultValues.setProperty(CommonClientConfigKey.AppName, "Value");

    // Assert
    assertEquals("Value", clientConfigWithDefaultValues.getAppName());
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(40, properties.size());
    assertTrue(properties.containsKey("ClientClassName"));
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty11() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setProperty(CommonClientConfigKey.BackoffInterval, "42");

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertEquals("42", properties.get("BackoffTimeout"));
    assertNull(emptyConfig.getAppName());
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty12() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setProperty(CommonClientConfigKey.ConnectionPoolCleanerTaskEnabled, 42);

    // Assert
    assertNull(emptyConfig.getAppName());
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    String expectedString = Boolean.FALSE.toString();
    assertEquals(expectedString, properties.get("ConnectionPoolCleanerTaskEnabled"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testSetProperty13() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.setProperty((new DefaultClientConfigImplTest()).new NewConfigKey("Value may not be null"), 42);
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  public void testGetProperty() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getProperty(CommonClientConfigKey.AppName));
    assertNull(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getProperty(CommonClientConfigKey.AppName));
    assertNull(DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space")
        .getProperty(CommonClientConfigKey.AppName));
    assertNull(DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
        .getProperty(CommonClientConfigKey.AppName));
    assertEquals("Default Val",
        DefaultClientConfigImpl.getEmptyConfig().getProperty(CommonClientConfigKey.AppName, "Default Val"));
    assertEquals("Default Val", DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getProperty(CommonClientConfigKey.AppName, "Default Val"));
    assertEquals("Default Val", DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space")
        .getProperty(CommonClientConfigKey.AppName, "Default Val"));
    assertEquals("Default Val", DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
        .getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  public void testGetProperty2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertEquals("42", emptyConfig.getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  public void testGetProperty3() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertEquals("42", emptyConfig.getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testGetProperty4() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertEquals("42", emptyConfig.getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  public void testGetProperty5() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertEquals("42", emptyConfig.getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getPrefixMappedProperty(IClientConfigKey)}
   */
  @Test
  public void testGetPrefixMappedProperty() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(UnsupportedOperationException.class);
    emptyConfig.getPrefixMappedProperty((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"));
  }

  /**
   * Method under test: {@link ReloadableClientConfig#getIfSet(IClientConfigKey)}
   */
  @Test
  public void testGetIfSet() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    assertFalse(emptyConfig.getIfSet((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")).isPresent());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#getIfSet(IClientConfigKey)}
   */
  @Test
  public void testGetIfSet2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertFalse(emptyConfig.getIfSet((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key")).isPresent());
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}
   */
  @Test
  public void testContainsProperty() {
    // Arrange, Act and Assert
    assertFalse(DefaultClientConfigImpl.getEmptyConfig().containsProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}
   */
  @Test
  public void testContainsProperty2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertTrue(emptyConfig.containsProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}
   */
  @Test
  public void testContainsProperty3() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertTrue(emptyConfig.containsProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}
   */
  @Test
  public void testGetPropertyAsInteger() {
    // Arrange, Act and Assert
    assertEquals(42, DefaultClientConfigImpl.getEmptyConfig().getPropertyAsInteger(CommonClientConfigKey.AppName, 42));
    assertEquals(42, DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getPropertyAsInteger(CommonClientConfigKey.AppName, 42));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}
   */
  @Test
  public void testGetPropertyAsString() {
    // Arrange, Act and Assert
    assertEquals("42",
        DefaultClientConfigImpl.getEmptyConfig().getPropertyAsString(CommonClientConfigKey.AppName, "42"));
    assertEquals("42", DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getPropertyAsString(CommonClientConfigKey.AppName, "42"));
    assertEquals("42", DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space")
        .getPropertyAsString(CommonClientConfigKey.AppName, "42"));
    assertEquals("42", DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
        .getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}
   */
  @Test
  public void testGetPropertyAsString2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertEquals("42", emptyConfig.getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}
   */
  @Test
  public void testGetPropertyAsString3() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertEquals("42", emptyConfig.getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}
   */
  @Test
  public void testGetPropertyAsBoolean() {
    // Arrange, Act and Assert
    assertTrue(DefaultClientConfigImpl.getEmptyConfig().getPropertyAsBoolean(CommonClientConfigKey.AppName, true));
    assertTrue(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getPropertyAsBoolean(CommonClientConfigKey.AppName, true));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  public void testApplyOverride() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl override = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    IClientConfig actualApplyOverrideResult = emptyConfig.applyOverride(override);

    // Assert
    assertTrue(override.getProperties().isEmpty());
    assertSame(emptyConfig, actualApplyOverrideResult);
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  public void testApplyOverride2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    IClientConfig actualApplyOverrideResult = emptyConfig.applyOverride(null);

    // Assert
    assertTrue(emptyConfig.getProperties().isEmpty());
    assertSame(emptyConfig, actualApplyOverrideResult);
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  public void testApplyOverride3() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    IClientConfig actualApplyOverrideResult = emptyConfig
        .applyOverride(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"));

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
    assertSame(emptyConfig, actualApplyOverrideResult);
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  public void testApplyOverride4() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    DefaultClientConfigImpl override = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    IClientConfig actualApplyOverrideResult = emptyConfig.applyOverride(override);

    // Assert
    assertTrue(override.getProperties().isEmpty());
    assertSame(emptyConfig, actualApplyOverrideResult);
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  public void testApplyOverride5() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl override = DefaultClientConfigImpl.getEmptyConfig();
    override.setDefault((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), "Value");

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.applyOverride(override);
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  public void testApplyOverride6() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");

    // Act and Assert
    assertSame(clientConfigWithDefaultValues, clientConfigWithDefaultValues
        .applyOverride(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  public void testApplyOverride7() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultClientConfigImpl override = DefaultClientConfigImpl.getEmptyConfig();
    override.setDefault((new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"), 42);

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.applyOverride(override);
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  public void testApplyOverride8() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "Name Space");

    // Act and Assert
    assertSame(clientConfigWithDefaultValues, clientConfigWithDefaultValues
        .applyOverride(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  public void testApplyOverride9() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl.getClientConfigWithDefaultValues("",
        "Name Space");

    // Act and Assert
    assertSame(clientConfigWithDefaultValues, clientConfigWithDefaultValues
        .applyOverride(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")));
  }

  /**
   * Method under test:
   * {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  public void testApplyOverride10() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");
    DefaultClientConfigImpl override = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "Name Space");
    override.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertSame(clientConfigWithDefaultValues, clientConfigWithDefaultValues.applyOverride(override));
  }

  /**
   * Method under test: {@link ReloadableClientConfig#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("ClientConfig:", DefaultClientConfigImpl.getEmptyConfig().toString());
    assertEquals("ClientConfig:ConnectTimeout:2000, MaxConnectionsPerHost:50, MaxRetriesPerServerPrimeConnection:9,"
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
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "ClientConfig:").toString());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#toString()}
   */
  @Test
  public void testToString2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertEquals("ClientConfig:", emptyConfig.toString());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#getRefreshCount()}
   */
  @Test
  public void testGetRefreshCount() {
    // Arrange, Act and Assert
    assertEquals(0L, DefaultClientConfigImpl.getEmptyConfig().getRefreshCount());
  }

  /**
   * Method under test: {@link ReloadableClientConfig#getRefreshCount()}
   */
  @Test
  public void testGetRefreshCount2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertEquals(0L, emptyConfig.getRefreshCount());
  }
}
