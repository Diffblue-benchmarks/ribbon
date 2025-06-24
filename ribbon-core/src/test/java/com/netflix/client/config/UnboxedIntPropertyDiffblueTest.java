package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Optional;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class UnboxedIntPropertyDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UnboxedIntProperty#UnboxedIntProperty(int)}
   *   <li>{@link UnboxedIntProperty#get()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void UnboxedIntProperty.<init>(int)", "int UnboxedIntProperty.get()"})
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(42, (new UnboxedIntProperty(42)).get());
  }

  /**
   * Test {@link UnboxedIntProperty#UnboxedIntProperty(Property)}.
   * <ul>
   *   <li>Given of one.</li>
   *   <li>Then return {@link UnboxedIntProperty#get()} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link UnboxedIntProperty#UnboxedIntProperty(Property)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void UnboxedIntProperty.<init>(Property)"})
  public void testNewUnboxedIntProperty_givenOfOne_thenReturnGetIsOne() {
    // Arrange
    Property<Integer> primary = mock(Property.class);
    doNothing().when(primary).onChange(Mockito.<Consumer<Integer>>any());
    Optional<Integer> ofResult = Optional.<Integer>of(1);
    when(primary.get()).thenReturn(ofResult);
    Property<Integer> fallback = mock(Property.class);
    doNothing().when(fallback).onChange(Mockito.<Consumer<Integer>>any());

    // Act
    UnboxedIntProperty actualUnboxedIntProperty = new UnboxedIntProperty(new FallbackProperty<>(primary, fallback));

    // Assert
    verify(primary).get();
    verify(primary).onChange(isA(Consumer.class));
    verify(fallback).onChange(isA(Consumer.class));
    assertEquals(1, actualUnboxedIntProperty.get());
  }
}
