package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class IClientConfigKeyDiffblueTest {
  /**
   * Method under test: {@link IClientConfigKey#defaultValue()}
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
   * Method under test: {@link IClientConfigKey#format(Object[])}
   */
  @Test
  public void testFormat() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");

    // Act and Assert
    assertNull(newConfigKey.format("Args").defaultValue());
  }

  /**
   * Method under test: {@link IClientConfigKey#create(String, Class, Object)}
   */
  @Test
  public void testCreate() {
    // Arrange
    DefaultClientConfigImplTest.NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey(
        "Config Key");
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Default Value", newConfigKey.create("Key", type, "Default Value").defaultValue());
    Class<Object> expectedTypeResult = Object.class;
    Class<Object> typeResult = newConfigKey.type();
    assertEquals(expectedTypeResult, typeResult);
    assertSame(type, typeResult);
  }
}
