package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import java.util.Set;
import org.junit.Test;

public class CommonClientConfigKeyDiffblueTest {
  /**
   * Method under test: {@link CommonClientConfigKey#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        null);

    // Act and Assert
    assertNotEquals(newConfigKey, (new DefaultClientConfigImplTest()).new NewConfigKey("Config Key"));
  }

  /**
   * Method under test: {@link CommonClientConfigKey#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act and Assert
    assertNotEquals(newConfigKey, null);
  }

  /**
   * Method under test: {@link CommonClientConfigKey#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act and Assert
    assertNotEquals(newConfigKey, "Different type to CommonClientConfigKey");
  }

  /**
   * Method under test: {@link CommonClientConfigKey#values()}
   */
  @Test
  public void testValues() {
    // Arrange and Act
    IClientConfigKey[] actualValuesResult = CommonClientConfigKey.values();

    // Assert
    assertEquals("", (actualValuesResult[23]).defaultValue());
    assertEquals("SECONDS",
        (actualValuesResult[AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST]).defaultValue());
    assertNull((actualValuesResult[10]).defaultValue());
    assertNull((actualValuesResult[15]).defaultValue());
    assertNull((actualValuesResult[17]).defaultValue());
    assertNull((actualValuesResult[18]).defaultValue());
    assertNull((actualValuesResult[19]).defaultValue());
    assertNull((actualValuesResult[2]).defaultValue());
    assertNull((actualValuesResult[20]).defaultValue());
    assertNull((actualValuesResult[22]).defaultValue());
    assertNull((actualValuesResult[5]).defaultValue());
    assertNull((actualValuesResult[54]).defaultValue());
    assertNull((actualValuesResult[56]).defaultValue());
    assertNull((actualValuesResult[57]).defaultValue());
    assertNull((actualValuesResult[58]).defaultValue());
    assertNull((actualValuesResult[63]).defaultValue());
    assertNull((actualValuesResult[72]).defaultValue());
    assertNull((actualValuesResult[73]).defaultValue());
    assertNull((actualValuesResult[Short.SIZE]).defaultValue());
    assertEquals(75, actualValuesResult.length);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MIN_PRIME_CONNECTIONS_RATIO,
        ((Float) (actualValuesResult[12]).defaultValue()).floatValue(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        (actualValuesResult[66]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_PING_CLASSNAME,
        (actualValuesResult[14]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_RULE_CLASSNAME,
        (actualValuesResult[53]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_CLASS,
        (actualValuesResult[70]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        (actualValuesResult[21]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_SERVER_LIST_UPDATER_CLASS,
        (actualValuesResult[60]).defaultValue());
  }

  /**
   * Method under test: {@link CommonClientConfigKey#keys()}
   */
  @Test
  public void testKeys() {
    // Arrange and Act
    Set<IClientConfigKey> actualKeysResult = CommonClientConfigKey.keys();

    // Assert
    assertEquals(75, actualKeysResult.size());
  }

  /**
   * Method under test: {@link CommonClientConfigKey#valueOf(String)}
   */
  @Test
  public void testValueOf() {
    // Arrange, Act and Assert
    assertNull(CommonClientConfigKey.valueOf("Name").defaultValue());
  }

  /**
   * Method under test: {@link CommonClientConfigKey#type()}
   */
  @Test
  public void testType() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act
    Class<Object> actualTypeResult = newConfigKey.type();

    // Assert
    Class<Object> expectedTypeResult = Object.class;
    assertEquals(expectedTypeResult, actualTypeResult);
  }

  /**
   * Method under test: {@link CommonClientConfigKey#key()}
   */
  @Test
  public void testKey() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act and Assert
    assertEquals("Config Key", newConfigKey.key());
  }

  /**
   * Method under test: {@link CommonClientConfigKey#toString()}
   */
  @Test
  public void testToString() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act and Assert
    assertEquals("Config Key", newConfigKey.toString());
  }

  /**
   * Method under test: {@link CommonClientConfigKey#defaultValue()}
   */
  @Test
  public void testDefaultValue() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act and Assert
    assertNull(newConfigKey.defaultValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CommonClientConfigKey#equals(Object)}
   *   <li>{@link CommonClientConfigKey#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey2 = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act and Assert
    assertEquals(newConfigKey, newConfigKey2);
    int expectedHashCodeResult = newConfigKey.hashCode();
    assertEquals(expectedHashCodeResult, newConfigKey2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CommonClientConfigKey#equals(Object)}
   *   <li>{@link CommonClientConfigKey#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = mock(
        DefaultClientConfigImplTest.class).new NewConfigKey("Config Key");
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey2 = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act and Assert
    assertEquals(newConfigKey, newConfigKey2);
    int expectedHashCodeResult = newConfigKey.hashCode();
    assertEquals(expectedHashCodeResult, newConfigKey2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CommonClientConfigKey#equals(Object)}
   *   <li>{@link CommonClientConfigKey#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act and Assert
    assertEquals(newConfigKey, newConfigKey);
    int expectedHashCodeResult = newConfigKey.hashCode();
    assertEquals(expectedHashCodeResult, newConfigKey.hashCode());
  }
}
