package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertNull;
import org.junit.Test;

public class UtilsDiffblueTest {
  /**
   * Method under test: {@link Utils#methodByName(Class, String)}
   */
  @Test
  public void testMethodByName() {
    // Arrange
    Class<Object> aClass = Object.class;

    // Act and Assert
    assertNull(Utils.methodByName(aClass, "Name"));
  }
}
