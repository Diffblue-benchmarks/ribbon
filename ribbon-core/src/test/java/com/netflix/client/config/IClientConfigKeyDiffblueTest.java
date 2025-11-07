package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImplTest.NewConfigKey;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class IClientConfigKeyDiffblueTest {
  /**
   * Test {@link IClientConfigKey#defaultValue()}.
   * <p>
   * Method under test: {@link IClientConfigKey#defaultValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object IClientConfigKey.defaultValue()"})
  public void testDefaultValue() {
    // Arrange
    NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey<>("Config Key");

    // Act and Assert
    assertNull(newConfigKey.defaultValue());
  }

  /**
   * Test {@link IClientConfigKey#format(Object[])}.
   * <p>
   * Method under test: {@link IClientConfigKey#format(Object[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfigKey IClientConfigKey.format(Object[])"})
  public void testFormat() {
    // Arrange
    NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey<>("Config Key");

    // Act and Assert
    assertNull(newConfigKey.format("Args").defaultValue());
  }

  /**
   * Test {@link IClientConfigKey#create(String, Class, Object)}.
   * <p>
   * Method under test: {@link IClientConfigKey#create(String, Class, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfigKey IClientConfigKey.create(String, Class, Object)"})
  public void testCreate() {
    // Arrange
    NewConfigKey<Object> newConfigKey = (new DefaultClientConfigImplTest()).new NewConfigKey<>("Config Key");
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Default Value", newConfigKey.create("Key", type, "Default Value").defaultValue());
    Class<Object> expectedTypeResult = Object.class;
    assertEquals(expectedTypeResult, newConfigKey.type());
  }
}
