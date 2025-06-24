package com.netflix.client.config;

import static org.junit.Assert.assertFalse;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Optional;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PropertyUtilsDiffblueTest {
  /**
   * Test {@link PropertyUtils#resolveWithValueOf(Class, String)}.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link PropertyUtils#resolveWithValueOf(Class, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Optional PropertyUtils.resolveWithValueOf(Class, String)"})
  public void testResolveWithValueOf_whenJavaLangObject_thenReturnNotPresent() {
    // Arrange
    Class<Object> type = Object.class;

    // Act
    Optional<Object> actualResolveWithValueOfResult = PropertyUtils.resolveWithValueOf(type, "42");

    // Assert
    assertFalse(actualResolveWithValueOfResult.isPresent());
  }

  /**
   * Test {@link PropertyUtils#resolveWithValueOf(Class, String)}.
   * <ul>
   *   <li>When {@code valueOf}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link PropertyUtils#resolveWithValueOf(Class, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Optional PropertyUtils.resolveWithValueOf(Class, String)"})
  public void testResolveWithValueOf_whenValueOf_thenReturnNotPresent() {
    // Arrange
    Class<Object> type = Object.class;

    // Act
    Optional<Object> actualResolveWithValueOfResult = PropertyUtils.resolveWithValueOf(type, "valueOf");

    // Assert
    assertFalse(actualResolveWithValueOfResult.isPresent());
  }
}
