package com.netflix.client.config;

import static org.junit.Assert.assertFalse;
import java.util.Optional;
import org.junit.Test;

public class PropertyUtilsDiffblueTest {
  /**
   * Method under test: {@link PropertyUtils#resolveWithValueOf(Class, String)}
   */
  @Test
  public void testResolveWithValueOf() {
    // Arrange
    Class<Object> type = Object.class;

    // Act
    Optional<Object> actualResolveWithValueOfResult = PropertyUtils.resolveWithValueOf(type, "42");

    // Assert
    assertFalse(actualResolveWithValueOfResult.isPresent());
  }
}
