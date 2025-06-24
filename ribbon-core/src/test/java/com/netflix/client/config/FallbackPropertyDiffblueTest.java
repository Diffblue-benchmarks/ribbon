package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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

public class FallbackPropertyDiffblueTest {
  /**
   * Test {@link FallbackProperty#onChange(Consumer)}.
   * <p>
   * Method under test: {@link FallbackProperty#onChange(Consumer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FallbackProperty.onChange(Consumer)"})
  public void testOnChange() {
    // Arrange
    Property<Object> primary = mock(Property.class);
    doNothing().when(primary).onChange(Mockito.<Consumer<Object>>any());
    Property<Object> fallback = mock(Property.class);
    doNothing().when(fallback).onChange(Mockito.<Consumer<Object>>any());
    FallbackProperty<Object> fallbackProperty = new FallbackProperty<>(primary, fallback);

    // Act
    fallbackProperty.onChange(mock(Consumer.class));

    // Assert
    verify(primary).onChange(isA(Consumer.class));
    verify(fallback).onChange(isA(Consumer.class));
  }

  /**
   * Test {@link FallbackProperty#get()}.
   * <p>
   * Method under test: {@link FallbackProperty#get()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Optional FallbackProperty.get()"})
  public void testGet() {
    // Arrange
    Property<Object> primary = mock(Property.class);
    Optional<Object> ofResult = Optional.of("42");
    when(primary.get()).thenReturn(ofResult);
    FallbackProperty<Object> fallbackProperty = new FallbackProperty<>(primary, mock(Property.class));

    // Act
    Optional<Object> actualGetResult = fallbackProperty.get();

    // Assert
    verify(primary).get();
    assertSame(ofResult, actualGetResult);
  }

  /**
   * Test {@link FallbackProperty#get()}.
   * <ul>
   *   <li>Given {@link Property} {@link Property#get()} return empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link FallbackProperty#get()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Optional FallbackProperty.get()"})
  public void testGet_givenPropertyGetReturnEmpty() {
    // Arrange
    Property<Object> primary = mock(Property.class);
    Optional<Object> emptyResult = Optional.empty();
    when(primary.get()).thenReturn(emptyResult);
    Property<Object> fallback = mock(Property.class);
    Optional<Object> ofResult = Optional.of("42");
    when(fallback.get()).thenReturn(ofResult);
    FallbackProperty<Object> fallbackProperty = new FallbackProperty<>(primary, fallback);

    // Act
    Optional<Object> actualGetResult = fallbackProperty.get();

    // Assert
    verify(primary).get();
    verify(fallback).get();
    assertSame(ofResult, actualGetResult);
  }

  /**
   * Test {@link FallbackProperty#getOrDefault()}.
   * <p>
   * Method under test: {@link FallbackProperty#getOrDefault()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object FallbackProperty.getOrDefault()"})
  public void testGetOrDefault() {
    // Arrange
    Property<Object> primary = mock(Property.class);
    Optional<Object> ofResult = Optional.of("42");
    when(primary.get()).thenReturn(ofResult);
    FallbackProperty<Object> fallbackProperty = new FallbackProperty<>(primary, mock(Property.class));

    // Act
    Object actualOrDefault = fallbackProperty.getOrDefault();

    // Assert
    verify(primary).get();
    assertEquals("42", actualOrDefault);
  }
}
