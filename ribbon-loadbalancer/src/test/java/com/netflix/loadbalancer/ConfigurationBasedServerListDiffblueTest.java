package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.util.List;
import org.junit.Test;

public class ConfigurationBasedServerListDiffblueTest {
  /**
   * Method under test:
   * {@link ConfigurationBasedServerList#getInitialListOfServers()}
   */
  @Test
  public void testGetInitialListOfServers() {
    // Arrange
    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    configurationBasedServerList.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Act and Assert
    assertTrue(configurationBasedServerList.getInitialListOfServers().isEmpty());
  }

  /**
   * Method under test:
   * {@link ConfigurationBasedServerList#getInitialListOfServers()}
   */
  @Test
  public void testGetInitialListOfServers2() {
    // Arrange
    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    configurationBasedServerList
        .initWithNiwsConfig(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"));

    // Act and Assert
    assertTrue(configurationBasedServerList.getInitialListOfServers().isEmpty());
  }

  /**
   * Method under test:
   * {@link ConfigurationBasedServerList#getInitialListOfServers()}
   */
  @Test
  public void testGetInitialListOfServers3() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    configurationBasedServerList.initWithNiwsConfig(clientConfig);

    // Act and Assert
    assertTrue(configurationBasedServerList.getInitialListOfServers().isEmpty());
  }

  /**
   * Method under test:
   * {@link ConfigurationBasedServerList#getInitialListOfServers()}
   */
  @Test
  public void testGetInitialListOfServers4() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setNameSpace("4242");
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    configurationBasedServerList.initWithNiwsConfig(clientConfig);

    // Act and Assert
    assertTrue(configurationBasedServerList.getInitialListOfServers().isEmpty());
  }

  /**
   * Method under test:
   * {@link ConfigurationBasedServerList#getUpdatedListOfServers()}
   */
  @Test
  public void testGetUpdatedListOfServers() {
    // Arrange
    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    configurationBasedServerList.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Act and Assert
    assertTrue(configurationBasedServerList.getUpdatedListOfServers().isEmpty());
  }

  /**
   * Method under test:
   * {@link ConfigurationBasedServerList#getUpdatedListOfServers()}
   */
  @Test
  public void testGetUpdatedListOfServers2() {
    // Arrange
    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    configurationBasedServerList
        .initWithNiwsConfig(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"));

    // Act and Assert
    assertTrue(configurationBasedServerList.getUpdatedListOfServers().isEmpty());
  }

  /**
   * Method under test:
   * {@link ConfigurationBasedServerList#getUpdatedListOfServers()}
   */
  @Test
  public void testGetUpdatedListOfServers3() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    configurationBasedServerList.initWithNiwsConfig(clientConfig);

    // Act and Assert
    assertTrue(configurationBasedServerList.getUpdatedListOfServers().isEmpty());
  }

  /**
   * Method under test:
   * {@link ConfigurationBasedServerList#getUpdatedListOfServers()}
   */
  @Test
  public void testGetUpdatedListOfServers4() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setClientName("Mr John SmithProf Albert Einstein");

    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    configurationBasedServerList.initWithNiwsConfig(clientConfig);

    // Act and Assert
    assertTrue(configurationBasedServerList.getUpdatedListOfServers().isEmpty());
  }

  /**
   * Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  public void testDerive() {
    // Arrange and Act
    List<Server> actualDeriveResult = (new ConfigurationBasedServerList()).derive("42");

    // Assert
    assertEquals(1, actualDeriveResult.size());
    Server getResult = actualDeriveResult.get(0);
    assertEquals("42", getResult.getHost());
    assertEquals("42:80", getResult.getHostPort());
    assertEquals("42:80", getResult.getId());
    assertNull(getResult.getScheme());
    assertEquals(80, getResult.getPort());
    assertFalse(getResult.isAlive());
    assertTrue(getResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, getResult.getZone());
  }

  /**
   * Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  public void testDerive2() {
    // Arrange, Act and Assert
    assertTrue((new ConfigurationBasedServerList()).derive(null).isEmpty());
  }

  /**
   * Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  public void testDerive3() {
    // Arrange and Act
    List<Server> actualDeriveResult = (new ConfigurationBasedServerList()).derive("http://");

    // Assert
    assertEquals(1, actualDeriveResult.size());
    Server getResult = actualDeriveResult.get(0);
    assertEquals("", getResult.getHost());
    assertEquals(":80", getResult.getHostPort());
    assertEquals(":80", getResult.getId());
    assertEquals("http", getResult.getScheme());
    assertEquals(80, getResult.getPort());
    assertFalse(getResult.isAlive());
    assertTrue(getResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, getResult.getZone());
  }

  /**
   * Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  public void testDerive4() {
    // Arrange and Act
    List<Server> actualDeriveResult = (new ConfigurationBasedServerList()).derive("https://");

    // Assert
    assertEquals(1, actualDeriveResult.size());
    Server getResult = actualDeriveResult.get(0);
    assertEquals("", getResult.getHost());
    assertEquals(":443", getResult.getHostPort());
    assertEquals(":443", getResult.getId());
    assertEquals("https", getResult.getScheme());
    assertEquals(443, getResult.getPort());
    assertFalse(getResult.isAlive());
    assertTrue(getResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, getResult.getZone());
  }

  /**
   * Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  public void testDerive5() {
    // Arrange and Act
    List<Server> actualDeriveResult = (new ConfigurationBasedServerList()).derive("/");

    // Assert
    assertEquals(1, actualDeriveResult.size());
    Server getResult = actualDeriveResult.get(0);
    assertEquals("", getResult.getHost());
    assertEquals(":80", getResult.getHostPort());
    assertEquals(":80", getResult.getId());
    assertNull(getResult.getScheme());
    assertEquals(80, getResult.getPort());
    assertFalse(getResult.isAlive());
    assertTrue(getResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, getResult.getZone());
  }

  /**
   * Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  public void testDerive6() {
    // Arrange, Act and Assert
    assertTrue((new ConfigurationBasedServerList()).derive("").isEmpty());
  }

  /**
   * Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  public void testDerive7() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    configurationBasedServerList.initWithNiwsConfig(clientConfig);

    // Act
    List<Server> actualDeriveResult = configurationBasedServerList.derive("42");

    // Assert
    assertEquals(1, actualDeriveResult.size());
    Server getResult = actualDeriveResult.get(0);
    assertEquals("42", getResult.getHost());
    assertEquals("42:80", getResult.getHostPort());
    assertEquals("42:80", getResult.getId());
    assertNull(getResult.getScheme());
    assertEquals(80, getResult.getPort());
    assertFalse(getResult.isAlive());
    assertTrue(getResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, getResult.getZone());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link ConfigurationBasedServerList}
   *   <li>{@link ConfigurationBasedServerList#initWithNiwsConfig(IClientConfig)}
   *   <li>{@link ConfigurationBasedServerList#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ConfigurationBasedServerList actualConfigurationBasedServerList = new ConfigurationBasedServerList();
    actualConfigurationBasedServerList.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertEquals("ConfigurationBasedServerList:[]", actualConfigurationBasedServerList.toString());
  }
}
