package com.netflix.client.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PropertyDiffblueTest {
  /**
   * Test {@link Property#fallbackWith(Property)}.
   * <p>
   * Method under test: {@link Property#fallbackWith(Property)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Property Property.fallbackWith(Property)"})
  public void testFallbackWith() {
    // Arrange
    FallbackProperty<Object> fallbackProperty = new FallbackProperty<>(mock(Property.class), mock(Property.class));

    // Act
    Property<Object> actualFallbackWithResult = fallbackProperty
        .fallbackWith(new FallbackProperty<>(mock(Property.class), mock(Property.class)));

    // Assert
    assertTrue(actualFallbackWithResult instanceof FallbackProperty);
    assertNull(actualFallbackWithResult.getOrDefault());
    assertFalse(actualFallbackWithResult.get().isPresent());
  }
}
