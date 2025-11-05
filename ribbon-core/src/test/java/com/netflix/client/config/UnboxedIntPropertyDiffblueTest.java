package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.function.Consumer;
import org.junit.Test;
import org.mockito.Mockito;

public class UnboxedIntPropertyDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UnboxedIntProperty#UnboxedIntProperty(int)}
   *   <li>{@link UnboxedIntProperty#get()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(42, (new UnboxedIntProperty(42)).get());
  }

  /**
   * Method under test: {@link UnboxedIntProperty#UnboxedIntProperty(Property)}
   */
  @Test
  public void testNewUnboxedIntProperty() {
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
