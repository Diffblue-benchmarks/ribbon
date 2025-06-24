package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImplTest.NewConfigKey;
import com.netflix.client.config.IClientConfig.Builder;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class ReloadableClientConfigDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link ReloadableClientConfig#setClientName(String)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setClientName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <p>
   * Method under test: {@link ReloadableClientConfig#getClientName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ReloadableClientConfig.getClientName()"})
  public void testGetClientName() {
    // Arrange, Act and Assert
    assertEquals("", DefaultClientConfigImpl.getEmptyConfig().getClientName());
  }

  /**
   * Test {@link ReloadableClientConfig#getNameSpace()}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#getNameSpace()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ReloadableClientConfig.getNameSpace()"})
  public void testGetNameSpace() {
    // Arrange, Act and Assert
    assertEquals(CommonClientConfigKey.DEFAULT_NAME_SPACE, DefaultClientConfigImpl.getEmptyConfig().getNameSpace());
  }

  /**
   * Test {@link ReloadableClientConfig#setNameSpace(String)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setNameSpace(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <p>
   * Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.loadProperties(String)"})
  public void testLoadProperties() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "[{}] loading config");

    // Act
    clientConfigWithDefaultValues.loadProperties("Dr Jane Doe");

    // Assert that nothing has changed
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
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
   * Test {@link ReloadableClientConfig#loadProperties(String)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then EmptyConfig ClientName is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * Test {@link ReloadableClientConfig#loadProperties(String)}.
   * <ul>
   *   <li>When {@code Dr Jane Doe}.</li>
   *   <li>Then EmptyConfig ClientName is {@code Dr Jane Doe}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * Test {@link ReloadableClientConfig#loadProperties(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then EmptyConfig ClientName is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#loadProperties(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * Test {@link ReloadableClientConfig#getProperties()}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperties()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map ReloadableClientConfig.getProperties()"})
  public void testGetProperties_givenEmptyConfig_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(DefaultClientConfigImpl.getEmptyConfig().getProperties().isEmpty());
  }

  /**
   * Test {@link ReloadableClientConfig#getProperties()}.
   * <ul>
   *   <li>Then return size is thirty-nine.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperties()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map ReloadableClientConfig.getProperties()"})
  public void testGetProperties_thenReturnSizeIsThirtyNine() {
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
   * Test {@link ReloadableClientConfig#get(IClientConfigKey, Object)} with {@code key}, {@code defaultValue}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey, Object)"})
  public void testGetWithKeyDefaultValue() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl.getClientConfigWithDefaultValues("",
        "Name Space");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualGetResult = clientConfigWithDefaultValues.get(key, "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey, Object)} with {@code key}, {@code defaultValue}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code null} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey, Object)"})
  public void testGetWithKeyDefaultValue_givenClientConfigWithDefaultValuesNullIsNameSpace() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "Name Space");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualGetResult = clientConfigWithDefaultValues.get(key, "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey, Object)} with {@code key}, {@code defaultValue}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@link NewConfigKey}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey, Object)"})
  public void testGetWithKeyDefaultValue_givenEmptyConfig_whenNewConfigKey() {
    // Arrange, Act and Assert
    assertEquals("Default Value",
        DefaultClientConfigImpl.getEmptyConfig().get(mock(NewConfigKey.class), "Default Value"));
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey, Object)} with {@code key}, {@code defaultValue}.
   * <ul>
   *   <li>Then calls {@link CommonClientConfigKey#type()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey, Object)"})
  public void testGetWithKeyDefaultValue_thenCallsType() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualGetResult = clientConfigWithDefaultValues.get(key, "Default Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertEquals("Default Value", actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues empty string is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey)"})
  public void testGetWithKey_givenClientConfigWithDefaultValuesEmptyStringIsNameSpace() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl.getClientConfigWithDefaultValues("",
        "Name Space");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualGetResult = clientConfigWithDefaultValues.get(key);

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertNull(actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code null} is {@code Name Space}.</li>
   *   <li>Then calls {@link CommonClientConfigKey#type()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey)"})
  public void testGetWithKey_givenClientConfigWithDefaultValuesNullIsNameSpace_thenCallsType() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "Name Space");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualGetResult = clientConfigWithDefaultValues.get(key);

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertNull(actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@link NewConfigKey}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey)"})
  public void testGetWithKey_givenEmptyConfig_whenNewConfigKey_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().<Object>get(mock(NewConfigKey.class)));
  }

  /**
   * Test {@link ReloadableClientConfig#get(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>Then calls {@link CommonClientConfigKey#type()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#get(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.get(IClientConfigKey)"})
  public void testGetWithKey_thenCallsType() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    Object actualGetResult = clientConfigWithDefaultValues.get(key);

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertNull(actualGetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#setDefault(IClientConfigKey)} with {@code key}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setDefault(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setDefault(IClientConfigKey)"})
  public void testSetDefaultWithKey() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.defaultValue()).thenReturn("Default Value");
    when(key.key()).thenReturn("Key");

    // Act
    clientConfigWithDefaultValues.setDefault(key);

    // Assert
    verify(key).defaultValue();
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
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
   * Test {@link ReloadableClientConfig#setDefault(IClientConfigKey)} with {@code key}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setDefault(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setDefault(IClientConfigKey)"})
  public void testSetDefaultWithKey2() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "Name Space");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.defaultValue()).thenReturn("Default Value");
    when(key.key()).thenReturn("Key");

    // Act
    clientConfigWithDefaultValues.setDefault(key);

    // Assert
    verify(key).defaultValue();
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
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
   * Test {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)} with {@code key}, {@code value}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setDefault(IClientConfigKey, Object)"})
  public void testSetDefaultWithKeyValue() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "key cannot be null");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    clientConfigWithDefaultValues.setDefault(key, "Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
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
   * Test {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)} with {@code key}, {@code value}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setDefault(IClientConfigKey, Object)"})
  public void testSetDefaultWithKeyValue2() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "key cannot be null");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    clientConfigWithDefaultValues.setDefault(key, "Value");

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
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
   * Test {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)} with {@code key}, {@code value}.
   * <ul>
   *   <li>Then EmptyConfig Properties size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#setDefault(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setDefault(IClientConfigKey, Object)"})
  public void testSetDefaultWithKeyValue_thenEmptyConfigPropertiesSizeIsOne() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    emptyConfig.setDefault(key, "Value");

    // Assert
    verify(key).key();
    verify(key).type();
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertEquals("Value", properties.get("Key"));
  }

  /**
   * Test {@link ReloadableClientConfig#setDefault(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>Then EmptyConfig Properties size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#setDefault(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setDefault(IClientConfigKey)"})
  public void testSetDefaultWithKey_givenEmptyConfig_thenEmptyConfigPropertiesSizeIsOne() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.defaultValue()).thenReturn("Default Value");
    when(key.key()).thenReturn("Key");

    // Act
    emptyConfig.setDefault(key);

    // Assert
    verify(key).defaultValue();
    verify(key).key();
    verify(key).type();
    Map<String, Object> properties = emptyConfig.getProperties();
    assertEquals(1, properties.size());
    assertEquals("Default Value", properties.get("Key"));
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@link NewConfigKey}.</li>
   *   <li>Then return EmptyConfig.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_givenEmptyConfig_whenNewConfigKey_thenReturnEmptyConfig() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    IClientConfig actualSetResult = emptyConfig.set(mock(NewConfigKey.class), null);

    // Assert
    assertTrue(emptyConfig.getProperties().isEmpty());
    assertSame(emptyConfig, actualSetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   * <ul>
   *   <li>Given {@code Object}.</li>
   *   <li>When forty-two.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_givenJavaLangObject_whenFortyTwo_thenThrowIllegalArgumentException() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.set(key, 42);
    verify(key).key();
    verify(key).type();
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   * <ul>
   *   <li>Given {@code Object}.</li>
   *   <li>When {@link NewConfigKey} {@link CommonClientConfigKey#type()} return {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_givenJavaLangObject_whenNewConfigKeyTypeReturnObject() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    emptyConfig.set(key, "Value");
    verify(key).key();
    verify(key).type();
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   * <ul>
   *   <li>Then return ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code key cannot be null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_thenReturnClientConfigWithDefaultValuesDrJaneDoeIsKeyCannotBeNull() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "key cannot be null");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    IClientConfig actualSetResult = clientConfigWithDefaultValues.set(key, null);

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertSame(clientConfigWithDefaultValues, actualSetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   * <ul>
   *   <li>Then return ClientConfigWithDefaultValues empty string is {@code key cannot be null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_thenReturnClientConfigWithDefaultValuesEmptyStringIsKeyCannotBeNull() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl.getClientConfigWithDefaultValues("",
        "key cannot be null");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    IClientConfig actualSetResult = clientConfigWithDefaultValues.set(key, null);

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertSame(clientConfigWithDefaultValues, actualSetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#set(IClientConfigKey, Object)}.
   * <ul>
   *   <li>Then return ClientConfigWithDefaultValues {@code null} is {@code key cannot be null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#set(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.set(IClientConfigKey, Object)"})
  public void testSet_thenReturnClientConfigWithDefaultValuesNullIsKeyCannotBeNull() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "key cannot be null");
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act
    IClientConfig actualSetResult = clientConfigWithDefaultValues.set(key, null);

    // Assert
    verify(key, atLeast(1)).key();
    verify(key, atLeast(1)).type();
    assertSame(clientConfigWithDefaultValues, actualSetResult);
  }

  /**
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Value may not be null");

    // Act
    clientConfigWithDefaultValues.setProperty(CommonClientConfigKey.AppName, "Value");

    // Assert
    assertEquals("Value", clientConfigWithDefaultValues.getAppName());
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(40, properties.size());
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, properties.get("ClientClassName"));
  }

  /**
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty2() {
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
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty3() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "Value may not be null");

    // Act
    clientConfigWithDefaultValues.setProperty(CommonClientConfigKey.AppName, "Value");

    // Assert
    assertEquals("Value", clientConfigWithDefaultValues.getAppName());
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(40, properties.size());
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, properties.get("ClientClassName"));
  }

  /**
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty4() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl.getClientConfigWithDefaultValues("",
        "Value may not be null");

    // Act
    clientConfigWithDefaultValues.setProperty(CommonClientConfigKey.AppName, "Value");

    // Assert
    assertEquals("Value", clientConfigWithDefaultValues.getAppName());
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(40, properties.size());
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME, properties.get("ClientClassName"));
  }

  /**
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty5() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Value may not be null");

    // Act
    clientConfigWithDefaultValues.setProperty(CommonClientConfigKey.ClientClassName, "Value");

    // Assert
    Map<String, Object> properties = clientConfigWithDefaultValues.getProperties();
    assertEquals(39, properties.size());
    assertEquals("Value", properties.get("ClientClassName"));
    assertNull(clientConfigWithDefaultValues.getAppName());
    assertTrue(properties.containsKey("EnableZoneAffinity"));
    assertTrue(properties.containsKey("FollowRedirects"));
    assertTrue(properties.containsKey("IsClientAuthRequired"));
    assertTrue(properties.containsKey("PrimeConnectionsURI"));
    assertTrue(properties.containsKey("listOfServers"));
  }

  /**
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty6() {
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
   * Test {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@link CommonClientConfigKey#AppName}.</li>
   *   <li>Then EmptyConfig AppName is {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When forty-two.</li>
   *   <li>Then EmptyConfig AppName is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then EmptyConfig Properties {@code BackoffTimeout} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty_when42_thenEmptyConfigPropertiesBackoffTimeoutIs42() {
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
   * <ul>
   *   <li>When {@link CommonClientConfigKey#BackoffInterval}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#setProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ReloadableClientConfig.setProperty(IClientConfigKey, Object)"})
  public void testSetProperty_whenBackoffInterval_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    DefaultClientConfigImpl.getEmptyConfig().setProperty(CommonClientConfigKey.BackoffInterval, "Value");
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key}, {@code defaultVal}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal() {
    // Arrange, Act and Assert
    assertEquals("Default Val", DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space")
        .getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key}, {@code defaultVal}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal2() {
    // Arrange, Act and Assert
    assertEquals("Default Val", DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
        .getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key}, {@code defaultVal}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>Then return {@code Default Val}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal_givenEmptyConfig_thenReturnDefaultVal() {
    // Arrange, Act and Assert
    assertEquals("Default Val",
        DefaultClientConfigImpl.getEmptyConfig().getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key}, {@code defaultVal}.
   * <ul>
   *   <li>Then return {@code Default Val}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal_thenReturnDefaultVal() {
    // Arrange, Act and Assert
    assertEquals("Default Val", DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getProperty(CommonClientConfigKey.AppName, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key}, {@code defaultVal}.
   * <ul>
   *   <li>Then return {@link AbstractDefaultClientConfigImpl#DEFAULT_CLIENT_CLASSNAME}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal_thenReturnDefault_client_classname() {
    // Arrange, Act and Assert
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
            .getProperty(CommonClientConfigKey.ClientClassName, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key}, {@code defaultVal}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((Boolean) DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getProperty(CommonClientConfigKey.EnableGZIPContentEncodingFilter, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)} with {@code key}, {@code defaultVal}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey, Object)"})
  public void testGetPropertyWithKeyDefaultVal_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((Boolean) DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getProperty(CommonClientConfigKey.ConnectionPoolCleanerTaskEnabled, "Default Val"));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues empty string is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_givenClientConfigWithDefaultValuesEmptyStringIsNameSpace() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
        .getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code null} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_givenClientConfigWithDefaultValuesNullIsNameSpace() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space")
        .getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@link CommonClientConfigKey#AppName}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_givenEmptyConfig_whenAppName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>When {@link CommonClientConfigKey#ClientClassName}.</li>
   *   <li>Then return {@link AbstractDefaultClientConfigImpl#DEFAULT_CLIENT_CLASSNAME}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_whenClientClassName_thenReturnDefault_client_classname() {
    // Arrange, Act and Assert
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
            .getProperty(CommonClientConfigKey.ClientClassName));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>When {@link CommonClientConfigKey#ConnectionPoolCleanerTaskEnabled}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_whenConnectionPoolCleanerTaskEnabled_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((Boolean) DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getProperty(CommonClientConfigKey.ConnectionPoolCleanerTaskEnabled));
  }

  /**
   * Test {@link ReloadableClientConfig#getProperty(IClientConfigKey)} with {@code key}.
   * <ul>
   *   <li>When {@link CommonClientConfigKey#EnableGZIPContentEncodingFilter}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getProperty(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReloadableClientConfig.getProperty(IClientConfigKey)"})
  public void testGetPropertyWithKey_whenEnableGZIPContentEncodingFilter_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((Boolean) DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getProperty(CommonClientConfigKey.EnableGZIPContentEncodingFilter));
  }

  /**
   * Test {@link ReloadableClientConfig#getPrefixMappedProperty(IClientConfigKey)}.
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPrefixMappedProperty(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "com.netflix.client.config.Property ReloadableClientConfig.getPrefixMappedProperty(IClientConfigKey)"})
  public void testGetPrefixMappedProperty_thenThrowUnsupportedOperationException() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    NewConfigKey<Object> key = mock(NewConfigKey.class);
    Class<Object> forNameResult = Object.class;
    when(key.type()).thenReturn(forNameResult);
    when(key.key()).thenReturn("Key");

    // Act and Assert
    thrown.expect(UnsupportedOperationException.class);
    emptyConfig.getPrefixMappedProperty(key);
    verify(key).key();
    verify(key, atLeast(1)).type();
  }

  /**
   * Test {@link ReloadableClientConfig#getIfSet(IClientConfigKey)}.
   * <ul>
   *   <li>When {@link NewConfigKey}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getIfSet(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional ReloadableClientConfig.getIfSet(IClientConfigKey)"})
  public void testGetIfSet_whenNewConfigKey_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse(DefaultClientConfigImpl.getEmptyConfig().<Object>getIfSet(mock(NewConfigKey.class)).isPresent());
  }

  /**
   * Test {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@link CommonClientConfigKey#AppName}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ReloadableClientConfig.containsProperty(IClientConfigKey)"})
  public void testContainsProperty_givenEmptyConfig_whenAppName_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(DefaultClientConfigImpl.getEmptyConfig().containsProperty(CommonClientConfigKey.AppName));
  }

  /**
   * Test {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#containsProperty(IClientConfigKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ReloadableClientConfig.containsProperty(IClientConfigKey)"})
  public void testContainsProperty_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .containsProperty(CommonClientConfigKey.ClientClassName));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ReloadableClientConfig.getPropertyAsInteger(IClientConfigKey, int)"})
  public void testGetPropertyAsInteger() {
    // Arrange, Act and Assert
    assertEquals(42, DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
        .getPropertyAsInteger(CommonClientConfigKey.AppName, 42));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ReloadableClientConfig.getPropertyAsInteger(IClientConfigKey, int)"})
  public void testGetPropertyAsInteger_givenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange, Act and Assert
    assertEquals(42, DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getPropertyAsInteger(CommonClientConfigKey.AppName, 42));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code null} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ReloadableClientConfig.getPropertyAsInteger(IClientConfigKey, int)"})
  public void testGetPropertyAsInteger_givenClientConfigWithDefaultValuesNullIsNameSpace() {
    // Arrange, Act and Assert
    assertEquals(42, DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space")
        .getPropertyAsInteger(CommonClientConfigKey.AppName, 42));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@link CommonClientConfigKey#AppName}.</li>
   *   <li>Then return forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ReloadableClientConfig.getPropertyAsInteger(IClientConfigKey, int)"})
  public void testGetPropertyAsInteger_givenEmptyConfig_whenAppName_thenReturnFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, DefaultClientConfigImpl.getEmptyConfig().getPropertyAsInteger(CommonClientConfigKey.AppName, 42));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}.
   * <ul>
   *   <li>Then return {@link AbstractDefaultClientConfigImpl#DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsInteger(IClientConfigKey, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ReloadableClientConfig.getPropertyAsInteger(IClientConfigKey, int)"})
  public void testGetPropertyAsInteger_thenReturnDefault_connectionidle_time_in_msecs() {
    // Arrange, Act and Assert
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
            .getPropertyAsInteger(CommonClientConfigKey.ConnIdleEvictTimeMilliSeconds, 42));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ReloadableClientConfig.getPropertyAsString(IClientConfigKey, String)"})
  public void testGetPropertyAsString_givenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange, Act and Assert
    assertEquals("42", DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues empty string is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ReloadableClientConfig.getPropertyAsString(IClientConfigKey, String)"})
  public void testGetPropertyAsString_givenClientConfigWithDefaultValuesEmptyStringIsNameSpace() {
    // Arrange, Act and Assert
    assertEquals("42", DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
        .getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code null} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ReloadableClientConfig.getPropertyAsString(IClientConfigKey, String)"})
  public void testGetPropertyAsString_givenClientConfigWithDefaultValuesNullIsNameSpace() {
    // Arrange, Act and Assert
    assertEquals("42", DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space")
        .getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@link CommonClientConfigKey#AppName}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ReloadableClientConfig.getPropertyAsString(IClientConfigKey, String)"})
  public void testGetPropertyAsString_givenEmptyConfig_whenAppName_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42",
        DefaultClientConfigImpl.getEmptyConfig().getPropertyAsString(CommonClientConfigKey.AppName, "42"));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}.
   * <ul>
   *   <li>When {@link CommonClientConfigKey#ClientClassName}.</li>
   *   <li>Then return {@link AbstractDefaultClientConfigImpl#DEFAULT_CLIENT_CLASSNAME}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsString(IClientConfigKey, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ReloadableClientConfig.getPropertyAsString(IClientConfigKey, String)"})
  public void testGetPropertyAsString_whenClientClassName_thenReturnDefault_client_classname() {
    // Arrange, Act and Assert
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CLIENT_CLASSNAME,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
            .getPropertyAsString(CommonClientConfigKey.ClientClassName, "42"));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ReloadableClientConfig.getPropertyAsBoolean(IClientConfigKey, boolean)"})
  public void testGetPropertyAsBoolean() {
    // Arrange, Act and Assert
    assertTrue(DefaultClientConfigImpl.getClientConfigWithDefaultValues("", "Name Space")
        .getPropertyAsBoolean(CommonClientConfigKey.AppName, true));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ReloadableClientConfig.getPropertyAsBoolean(IClientConfigKey, boolean)"})
  public void testGetPropertyAsBoolean_givenClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange, Act and Assert
    assertTrue(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getPropertyAsBoolean(CommonClientConfigKey.AppName, true));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}.
   * <ul>
   *   <li>Given ClientConfigWithDefaultValues {@code null} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ReloadableClientConfig.getPropertyAsBoolean(IClientConfigKey, boolean)"})
  public void testGetPropertyAsBoolean_givenClientConfigWithDefaultValuesNullIsNameSpace() {
    // Arrange, Act and Assert
    assertTrue(DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space")
        .getPropertyAsBoolean(CommonClientConfigKey.AppName, true));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@link CommonClientConfigKey#AppName}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ReloadableClientConfig.getPropertyAsBoolean(IClientConfigKey, boolean)"})
  public void testGetPropertyAsBoolean_givenEmptyConfig_whenAppName_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(DefaultClientConfigImpl.getEmptyConfig().getPropertyAsBoolean(CommonClientConfigKey.AppName, true));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ReloadableClientConfig.getPropertyAsBoolean(IClientConfigKey, boolean)"})
  public void testGetPropertyAsBoolean_givenEmptyConfig_whenFalse_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(DefaultClientConfigImpl.getEmptyConfig().getPropertyAsBoolean(CommonClientConfigKey.AppName, false));
  }

  /**
   * Test {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}.
   * <ul>
   *   <li>When {@link CommonClientConfigKey#ConnectionPoolCleanerTaskEnabled}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#getPropertyAsBoolean(IClientConfigKey, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ReloadableClientConfig.getPropertyAsBoolean(IClientConfigKey, boolean)"})
  public void testGetPropertyAsBoolean_whenConnectionPoolCleanerTaskEnabled() {
    // Arrange, Act and Assert
    assertTrue(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")
        .getPropertyAsBoolean(CommonClientConfigKey.ConnectionPoolCleanerTaskEnabled, true));
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");
    IClientConfig override = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    IClientConfig actualApplyOverrideResult = clientConfigWithDefaultValues.applyOverride(override);

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
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>Then EmptyConfig Properties size is thirty-nine.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_givenEmptyConfig_thenEmptyConfigPropertiesSizeIsThirtyNine() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    emptyConfig.applyOverride(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"));

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
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When EmptyConfig.</li>
   *   <li>Then return EmptyConfig.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_givenEmptyConfig_whenEmptyConfig_thenReturnEmptyConfig() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    assertSame(emptyConfig, emptyConfig.applyOverride(DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return EmptyConfig.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_givenEmptyConfig_whenNull_thenReturnEmptyConfig() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act and Assert
    assertSame(emptyConfig, emptyConfig.applyOverride(null));
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   * <ul>
   *   <li>Then return ClientConfigWithDefaultValues {@code Dr Jane Doe} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_thenReturnClientConfigWithDefaultValuesDrJaneDoeIsNameSpace() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space");

    // Act and Assert
    assertSame(clientConfigWithDefaultValues, clientConfigWithDefaultValues
        .applyOverride(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")));
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   * <ul>
   *   <li>Then return ClientConfigWithDefaultValues empty string is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_thenReturnClientConfigWithDefaultValuesEmptyStringIsNameSpace() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl.getClientConfigWithDefaultValues("",
        "Name Space");

    // Act and Assert
    assertSame(clientConfigWithDefaultValues, clientConfigWithDefaultValues
        .applyOverride(DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "Name Space")));
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   * <ul>
   *   <li>Then return ClientConfigWithDefaultValues {@code null} is {@code Name Space}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_thenReturnClientConfigWithDefaultValuesNullIsNameSpace() {
    // Arrange
    DefaultClientConfigImpl clientConfigWithDefaultValues = DefaultClientConfigImpl
        .getClientConfigWithDefaultValues(null, "Name Space");

    // Act and Assert
    assertSame(clientConfigWithDefaultValues, clientConfigWithDefaultValues
        .applyOverride(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space")));
  }

  /**
   * Test {@link ReloadableClientConfig#applyOverride(IClientConfig)}.
   * <ul>
   *   <li>Then return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#applyOverride(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfig ReloadableClientConfig.applyOverride(IClientConfig)"})
  public void testApplyOverride_thenReturnDefaultClientConfigImpl() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    IClientConfig override = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    IClientConfig actualApplyOverrideResult = emptyConfig.applyOverride(override);

    // Assert
    assertTrue(actualApplyOverrideResult instanceof DefaultClientConfigImpl);
    Map<String, Object> properties = actualApplyOverrideResult.getProperties();
    assertEquals(1, properties.size());
    Map<String, Object> properties2 = emptyConfig.getProperties();
    assertEquals(1, properties2.size());
    String expectedString = Boolean.TRUE.toString();
    assertEquals(expectedString, properties.get("IgnoreUserTokenInConnectionPoolForSecureClient"));
    String expectedString2 = Boolean.TRUE.toString();
    assertEquals(expectedString2, properties2.get("IgnoreUserTokenInConnectionPoolForSecureClient"));
  }

  /**
   * Test {@link ReloadableClientConfig#toString()}.
   * <ul>
   *   <li>Given EmptyConfig.</li>
   *   <li>Then return {@code ClientConfig:}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ReloadableClientConfig.toString()"})
  public void testToString_givenEmptyConfig_thenReturnClientConfig() {
    // Arrange, Act and Assert
    assertEquals("ClientConfig:", DefaultClientConfigImpl.getEmptyConfig().toString());
  }

  /**
   * Test {@link ReloadableClientConfig#toString()}.
   * <ul>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReloadableClientConfig#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ReloadableClientConfig.toString()"})
  public void testToString_thenReturnAString() {
    // Arrange, Act and Assert
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
   * Test {@link ReloadableClientConfig#getRefreshCount()}.
   * <p>
   * Method under test: {@link ReloadableClientConfig#getRefreshCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long ReloadableClientConfig.getRefreshCount()"})
  public void testGetRefreshCount() {
    // Arrange, Act and Assert
    assertEquals(0L, DefaultClientConfigImpl.getEmptyConfig().getRefreshCount());
  }
}
