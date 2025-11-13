package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.IClientConfig.Builder;
import java.util.Map;
import java.util.function.BiConsumer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class ReloadableClientConfigDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

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
    emptyConfig.setClientName("Dr Jane Doe");

    // Assert
    assertEquals("Dr Jane Doe", emptyConfig.getClientName());
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
    emptyConfig.setNameSpace("Name Space");

    // Assert
    assertEquals("Name Space", emptyConfig.getNameSpace());
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
            "Dr Jane Doe", "[{}] loading config");

    // Act
    clientConfigWithDefaultValues.loadProperties("Dr Jane Doe");

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
   *   <li>When {@code Dr Jane Doe}.
   *   <li>Then EmptyConfig ClientName is {@code Dr Jane Doe}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.loadProperties(String)"})
  public void testLoadProperties_whenDrJaneDoe_thenEmptyConfigClientNameIsDrJaneDoe() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.loadProperties("Dr Jane Doe");

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(39, properties.size());
    assertEquals("", properties.get("listOfServers"));
    assertEquals("Dr Jane Doe", emptyConfig.getClientName());
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
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
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
   * Test {@link ReloadableClientConfig#get(IClientConfigKey, Object)} with {@code key}, {@code
   * defaultValue}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey, Object)"})
  public void testGetWithKeyDefaultValue() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");

    // Act and Assert
    assertEquals(
        "Default Value",
        clientConfigWithDefaultValues.get(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey, Object)} with {@code key}, {@code
   * defaultValue}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey, Object)"})
  public void testGetWithKeyDefaultValue2() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space");

    // Act and Assert
    assertEquals(
        "Default Value",
        clientConfigWithDefaultValues.get(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey, Object)} with {@code key}, {@code
   * defaultValue}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code null} is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey, Object)"})
  public void testGetWithKeyDefaultValue_givenClientConfigWithDefaultValuesNullIsNameSpace() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space");

    // Act and Assert
    assertEquals(
        "Default Value",
        clientConfigWithDefaultValues.get(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey, Object)} with {@code key}, {@code
   * defaultValue}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>Then return {@code Default Value}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey, Object)"})
  public void testGetWithKeyDefaultValue_givenEmptyConfig_thenReturnDefaultValue() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    assertEquals(
        "Default Value",
        emptyConfig.get(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey, Object)} with {@code key}, {@code
   * defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey, Object)"})
  public void testGetWithKeyDefaultValue_thenReturnValue() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setDefault(
        new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Value");

    // Act and Assert
    assertEquals(
        "Value",
        emptyConfig.get(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Default Value"));
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey)"})
  public void testGetWithKey_givenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");

    // Act
    Object actualGetResult =
        clientConfigWithDefaultValues.get(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"));

    // Assert
    assertNull(actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues empty string is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey)"})
  public void testGetWithKey_givenClientConfigWithDefaultValuesEmptyStringIsNameSpace() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space");

    // Act
    Object actualGetResult =
        clientConfigWithDefaultValues.get(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"));

    // Assert
    assertNull(actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code null} is {@code Name Space}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey)"})
  public void testGetWithKey_givenClientConfigWithDefaultValuesNullIsNameSpace_thenReturnNull() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space");

    // Act
    Object actualGetResult =
        clientConfigWithDefaultValues.get(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"));

    // Assert
    assertNull(actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey)"})
  public void testGetWithKey_givenEmptyConfig_thenReturnNull() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    Object actualGetResult =
        emptyConfig.get(new DefaultClientConfigImplTest().new NewConfigKey("Config Key"));

    // Assert
    assertNull(actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey)"})
  public void testGetWithKey_thenReturnValue() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setDefault(
        new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Value");

    // Act
    Object actualGetResult =
        emptyConfig.get(new DefaultClientConfigImplTest().new NewConfigKey("Config Key"));

    // Assert
    assertEquals("Value", actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)} with {@code key},
   * {@code value}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setDefault(IClientConfigKey, Object)"})
  public void testSetDefaultWithKeyValue() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "key cannot be null");

    // Act
    clientConfigWithDefaultValues.setDefault(
        new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Value");

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
   * Test {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)} with {@code key},
   * {@code value}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setDefault(IClientConfigKey, Object)"})
  public void testSetDefaultWithKeyValue2() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "key cannot be null");

    // Act
    clientConfigWithDefaultValues.setDefault(
        new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Value");

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
   * Test {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)} with {@code key},
   * {@code value}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>Then EmptyConfig Properties size is one.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setDefault(IClientConfigKey, Object)"})
  public void testSetDefaultWithKeyValue_givenEmptyConfig_thenEmptyConfigPropertiesSizeIsOne() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setDefault(
        new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Value");

    // Assert
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertEquals("Value", properties.get("Config Key"));
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When forty-two.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_givenEmptyConfig_whenFortyTwo_thenThrowIllegalArgumentException() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.set(new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), 42);
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@code null}.
   *   <li>Then return EmptyConfig.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_givenEmptyConfig_whenNull_thenReturnEmptyConfig() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    IClientConfig actualSetResult =
        emptyConfig.set(new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), null);

    // Assert
    assertSame(emptyConfig, actualSetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@code Value}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_givenEmptyConfig_whenValue_thenThrowIllegalArgumentException() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.set(new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Value");
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>Then return ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code key cannot be
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_thenReturnClientConfigWithDefaultValuesDrJaneDoeIsKeyCannotBeNull() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "key cannot be null");

    // Act
    IClientConfig actualSetResult =
        clientConfigWithDefaultValues.set(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), null);

    // Assert
    assertSame(clientConfigWithDefaultValues, actualSetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>Then return ClientConfigWithDefaultValues empty string is {@code key cannot be null}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_thenReturnClientConfigWithDefaultValuesEmptyStringIsKeyCannotBeNull() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "key cannot be null");

    // Act
    IClientConfig actualSetResult =
        clientConfigWithDefaultValues.set(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), null);

    // Assert
    assertSame(clientConfigWithDefaultValues, actualSetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>Then return ClientConfigWithDefaultValues {@code null} is {@code key cannot be null}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_thenReturnClientConfigWithDefaultValuesNullIsKeyCannotBeNull() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "key cannot be null");

    // Act
    IClientConfig actualSetResult =
        clientConfigWithDefaultValues.set(
            new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), null);

    // Assert
    assertSame(clientConfigWithDefaultValues, actualSetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setProperty(CommonClientConfigKey.ConnectionPoolCleanerTaskEnabled, "Value");

    // Assert
    assertNull(emptyConfig.getAppName());
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertEquals(Boolean.FALSE.toString(), properties.get("ConnectionPoolCleanerTaskEnabled"));
  }

  /**
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.setProperty(
        new DefaultClientConfigImplTest().new NewConfigKey("Value may not be null"), "Value");
  }

  /**
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty3() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "Value may not be null");
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
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty4() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.setProperty(CommonClientConfigKey.ConnectionPoolCleanerTaskEnabled, 42);

    // Assert
    assertNull(emptyConfig.getAppName());
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertEquals(Boolean.FALSE.toString(), properties.get("ConnectionPoolCleanerTaskEnabled"));
  }

  /**
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty5() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.setProperty(
        new DefaultClientConfigImplTest().new NewConfigKey("Value may not be null"), 42);
  }

  /**
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty6() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Value may not be null");

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
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty7() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Value may not be null");

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
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Value may not be null}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty_givenClientConfigWithDefaultValuesDrJaneDoeIsValueMayNotBeNull() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "Value may not be null");

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
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@link CommonClientConfigKey#AppName}.
   *   <li>Then EmptyConfig AppName is {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty_givenEmptyConfig_whenAppName_thenEmptyConfigAppNameIsValue() {
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
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When forty-two.
   *   <li>Then EmptyConfig AppName is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty_givenEmptyConfig_whenFortyTwo_thenEmptyConfigAppNameIs42() {
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
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>When {@link CommonClientConfigKey#BackoffInterval}.
   *   <li>Then EmptyConfig Properties {@code BackoffTimeout} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty_whenBackoffInterval_thenEmptyConfigPropertiesBackoffTimeoutIs42() {
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
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   *
   * <ul>
   *   <li>When {@link CommonClientConfigKey#BackoffInterval}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty_whenBackoffInterval_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    DefaultClientConfigImpl.getEmptyConfig()
        .setProperty(CommonClientConfigKey.BackoffInterval, "Value");
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key},
   * {@code defaultVal}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal() {
    // Arrange, Act and Assert
    assertEquals(
        "Default Val",
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
            .getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key},
   * {@code defaultVal}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal2() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space");

    // Act and Assert
    assertEquals(
        "Default Val",
        clientConfigWithDefaultValues.getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key},
   * {@code defaultVal}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal3() {
    // Arrange, Act and Assert
    assertEquals(
        "Default Val",
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
            .getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key},
   * {@code defaultVal}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>Then return {@code Default Val}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal_givenEmptyConfig_thenReturnDefaultVal() {
    // Arrange, Act and Assert
    assertEquals(
        "Default Val",
        DefaultClientConfigImpl.getEmptyConfig()
            .getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key},
   * {@code defaultVal}.
   *
   * <ul>
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal_thenReturn42() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertEquals("42", emptyConfig.getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key},
   * {@code defaultVal}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        (Boolean)
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
                .getProperty(CommonClientConfigKey.EnableGZIPContentEncodingFilter, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key},
   * {@code defaultVal}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        (Boolean)
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
                .getProperty(
                    CommonClientConfigKey.ConnectionPoolCleanerTaskEnabled, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_givenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange, Act and Assert
    assertNull(
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
            .getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues empty string is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_givenClientConfigWithDefaultValuesEmptyStringIsNameSpace() {
    // Arrange, Act and Assert
    assertNull(
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
            .getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code null} is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_givenClientConfigWithDefaultValuesNullIsNameSpace() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space");

    // Act and Assert
    assertNull(clientConfigWithDefaultValues.getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@link CommonClientConfigKey#AppName}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_givenEmptyConfig_whenAppName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_thenReturn42() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertEquals("42", emptyConfig.getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>When {@link CommonClientConfigKey#ConnectionPoolCleanerTaskEnabled}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_whenConnectionPoolCleanerTaskEnabled_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        (Boolean)
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
                .getProperty(CommonClientConfigKey.ConnectionPoolCleanerTaskEnabled));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   *
   * <ul>
   *   <li>When {@link CommonClientConfigKey#EnableGZIPContentEncodingFilter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_whenEnableGZIPContentEncodingFilter_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        (Boolean)
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
                .getProperty(CommonClientConfigKey.EnableGZIPContentEncodingFilter));
  }

  /**
   * Test {@link ReloadableClientConfig#getPrefixMappedProperty(IClientConfigKey)}.
   *
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getPrefixMappedProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.netflix.client.config.Property ReloadableClientConfig.getPrefixMappedProperty(IClientConfigKey)"
  })
  public void testGetPrefixMappedProperty_thenThrowUnsupportedOperationException() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    thrown.expect(UnsupportedOperationException.class);
    emptyConfig.getPrefixMappedProperty(
        new DefaultClientConfigImplTest().new NewConfigKey("Config Key"));
  }

  /**
   * Test {@link ReloadableClientConfig#getIfSet(IClientConfigKey)}.
   *
   * <ul>
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getIfSet(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional ReloadableClientConfig.getIfSet(IClientConfigKey)"})
  public void testGetIfSet_thenReturnNotPresent() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    assertFalse(
        emptyConfig
            .getIfSet(new DefaultClientConfigImplTest().new NewConfigKey("Config Key"))
            .isPresent());
  }

  /**
   * Test {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@link CommonClientConfigKey#AppName}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ReloadableClientConfig.containsProperty(IClientConfigKey)"})
  public void testContainsProperty_givenEmptyConfig_whenAppName_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        DefaultClientConfigImpl.getEmptyConfig().containsProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ReloadableClientConfig.containsProperty(IClientConfigKey)"})
  public void testContainsProperty_thenReturnTrue() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertTrue(emptyConfig.containsProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey,
   * int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ReloadableClientConfig.getPropertyAsInteger(IClientConfigKey, int)"})
  public void testGetPropertyAsInteger_givenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange, Act and Assert
    assertEquals(
        42,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
            .getPropertyAsInteger(CommonClientConfigKey.AppName, 42));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@link CommonClientConfigKey#AppName}.
   *   <li>Then return forty-two.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey,
   * int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ReloadableClientConfig.getPropertyAsInteger(IClientConfigKey, int)"})
  public void testGetPropertyAsInteger_givenEmptyConfig_whenAppName_thenReturnFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(
        42,
        DefaultClientConfigImpl.getEmptyConfig()
            .getPropertyAsInteger(CommonClientConfigKey.AppName, 42));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ReloadableClientConfig.getPropertyAsString(IClientConfigKey, String)"})
  public void testGetPropertyAsString_givenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange, Act and Assert
    assertEquals(
        "42",
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
            .getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues empty string is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ReloadableClientConfig.getPropertyAsString(IClientConfigKey, String)"})
  public void testGetPropertyAsString_givenClientConfigWithDefaultValuesEmptyStringIsNameSpace() {
    // Arrange, Act and Assert
    assertEquals(
        "42",
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
            .getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code null} is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ReloadableClientConfig.getPropertyAsString(IClientConfigKey, String)"})
  public void testGetPropertyAsString_givenClientConfigWithDefaultValuesNullIsNameSpace() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space");

    // Act and Assert
    assertEquals(
        "42",
        clientConfigWithDefaultValues.getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}.
   *
   * <ul>
   *   <li>Given EmptyConfig DefaultIntegerProperty {@link CommonClientConfigKey#AppName} is
   *       forty-two.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ReloadableClientConfig.getPropertyAsString(IClientConfigKey, String)"})
  public void testGetPropertyAsString_givenEmptyConfigDefaultIntegerPropertyAppNameIsFortyTwo() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.AppName, 42);

    // Act and Assert
    assertEquals("42", emptyConfig.getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@link CommonClientConfigKey#AppName}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ReloadableClientConfig.getPropertyAsString(IClientConfigKey, String)"})
  public void testGetPropertyAsString_givenEmptyConfig_whenAppName_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals(
        "42",
        DefaultClientConfigImpl.getEmptyConfig()
            .getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}.
   *
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ReloadableClientConfig.getPropertyAsBoolean(IClientConfigKey, boolean)"
  })
  public void testGetPropertyAsBoolean_givenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange, Act and Assert
    assertTrue(
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
            .getPropertyAsBoolean(CommonClientConfigKey.AppName, true));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@link CommonClientConfigKey#AppName}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ReloadableClientConfig.getPropertyAsBoolean(IClientConfigKey, boolean)"
  })
  public void testGetPropertyAsBoolean_givenEmptyConfig_whenAppName_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        DefaultClientConfigImpl.getEmptyConfig()
            .getPropertyAsBoolean(CommonClientConfigKey.AppName, true));
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "Value may not be null");

    // Act
    IClientConfig actualApplyOverrideResult =
        clientConfigWithDefaultValues.applyOverride(
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"));

    // Assert
    assertSame(clientConfigWithDefaultValues, actualApplyOverrideResult);
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride2() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(
            "Dr Jane Doe", "Value may not be null");

    // Act
    IClientConfig actualApplyOverrideResult =
        clientConfigWithDefaultValues.applyOverride(
            Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build());

    // Assert
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(40, properties.size());
    assertTrue(properties.containsKey("ClientClassName"));
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
    assertSame(clientConfigWithDefaultValues, actualApplyOverrideResult);
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   *
   * <p>Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride3() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Value may not be null");

    // Act
    IClientConfig actualApplyOverrideResult =
        clientConfigWithDefaultValues.applyOverride(
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"));

    // Assert
    assertSame(clientConfigWithDefaultValues, actualApplyOverrideResult);
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When EmptyConfig.
   *   <li>Then return EmptyConfig.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_givenEmptyConfig_whenEmptyConfig_thenReturnEmptyConfig() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    IClientConfig actualApplyOverrideResult =
        emptyConfig.applyOverride(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertSame(emptyConfig, actualApplyOverrideResult);
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   *
   * <ul>
   *   <li>Given EmptyConfig.
   *   <li>When {@code null}.
   *   <li>Then return EmptyConfig.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_givenEmptyConfig_whenNull_thenReturnEmptyConfig() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    IClientConfig actualApplyOverrideResult = emptyConfig.applyOverride(null);

    // Assert
    assertSame(emptyConfig, actualApplyOverrideResult);
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   *
   * <ul>
   *   <li>Given forty-two.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_givenFortyTwo() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    DefaultClientConfigImpl override = DefaultClientConfigImpl.getEmptyConfig();
    override.setDefault(new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), 42);

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.applyOverride(override);
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_givenValue() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    DefaultClientConfigImpl override = DefaultClientConfigImpl.getEmptyConfig();
    override.setDefault(new DefaultClientConfigImplTest().new NewConfigKey("Config Key"), "Value");

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.applyOverride(override);
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   *
   * <ul>
   *   <li>Then EmptyConfig Properties size is thirty-nine.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_thenEmptyConfigPropertiesSizeIsThirtyNine() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.applyOverride(
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"));

    // Assert
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
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   *
   * <ul>
   *   <li>Then return ClientConfigWithDefaultValues {@code null} is {@code Value may not be null}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_thenReturnClientConfigWithDefaultValuesNullIsValueMayNotBeNull() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Value may not be null");

    // Act
    IClientConfig actualApplyOverrideResult =
        clientConfigWithDefaultValues.applyOverride(
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"));

    // Assert
    assertSame(clientConfigWithDefaultValues, actualApplyOverrideResult);
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   *
   * <ul>
   *   <li>Then return {@link DefaultClientConfigImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_thenReturnDefaultClientConfigImpl() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    IClientConfig actualApplyOverrideResult =
        emptyConfig.applyOverride(
            Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build());

    // Assert
    assertTrue(actualApplyOverrideResult instanceof DefaultClientConfigImpl);
    Map<String, Object> properties = actualApplyOverrideResult.getProperties();
    assertEquals(1, properties.size());
    Map<String, Object> properties2 = emptyConfig.getProperties();
    assertEquals(1, properties2.size());
    assertEquals(
        Boolean.TRUE.toString(), properties.get("IgnoreUserTokenInConnectionPoolForSecureClient"));
    assertEquals(
        Boolean.TRUE.toString(), properties2.get("IgnoreUserTokenInConnectionPoolForSecureClient"));
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
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "ClientConfig:")
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
