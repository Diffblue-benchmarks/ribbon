package com.netflix.serialization;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class SerializationUtilsDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link SerializationUtils#deserializeFromString(Deserializer, String, TypeDef)}.
   *
   * <ul>
   *   <li>When {@link Deserializer}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SerializationUtils#deserializeFromString(Deserializer, String,
   * TypeDef)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object SerializationUtils.deserializeFromString(Deserializer, String, TypeDef)"
  })
  public void testDeserializeFromString_whenDeserializer_thenReturnNull() throws IOException {
    // Arrange and Act
    Object actualDeserializeFromStringResult =
        SerializationUtils.deserializeFromString(
            mock(Deserializer.class),
            "\"{\\\"name\\\":\\\"Breaking Bad\\\",\\\"genre\\\":\\\"Drama\\\",\\\"seasons\\\":5,\\\"rating\\\":9.5,\\\"streamingPlatform\\\":\\"
                + "\"Netflix\\\"}\"",
            mock(TypeDef.class));

    // Assert
    assertNull(actualDeserializeFromStringResult);
  }

  /**
   * Test {@link SerializationUtils#serializeToString(Serializer, Object, TypeDef)}.
   *
   * <ul>
   *   <li>Given {@link IOException#IOException()}.
   *   <li>Then throw {@link IOException}.
   * </ul>
   *
   * <p>Method under test: {@link SerializationUtils#serializeToString(Serializer, Object, TypeDef)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String SerializationUtils.serializeToString(Serializer, Object, TypeDef)"})
  public void testSerializeToString_givenIOException_thenThrowIOException() throws IOException {
    // Arrange
    Serializer<Object> serializer = mock(Serializer.class);
    doThrow(new IOException())
        .when(serializer)
        .serialize(Mockito.<OutputStream>any(), Mockito.<Object>any(), Mockito.<TypeDef<?>>any());

    // Act and Assert
    thrown.expect(IOException.class);
    SerializationUtils.serializeToString(serializer, "Obj", mock(TypeDef.class));
    verify(serializer).serialize(isA(OutputStream.class), isA(Object.class), isA(TypeDef.class));
  }

  /**
   * Test {@link SerializationUtils#serializeToString(Serializer, Object, TypeDef)}.
   *
   * <ul>
   *   <li>When {@link Serializer} {@link Serializer#serialize(OutputStream, Object, TypeDef)} does
   *       nothing.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link SerializationUtils#serializeToString(Serializer, Object, TypeDef)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String SerializationUtils.serializeToString(Serializer, Object, TypeDef)"})
  public void testSerializeToString_whenSerializerSerializeDoesNothing_thenReturnEmptyString()
      throws IOException {
    // Arrange
    Serializer<Object> serializer = mock(Serializer.class);
    doNothing()
        .when(serializer)
        .serialize(Mockito.<OutputStream>any(), Mockito.<Object>any(), Mockito.<TypeDef<?>>any());

    // Act
    String actualSerializeToStringResult =
        SerializationUtils.serializeToString(serializer, "Obj", mock(TypeDef.class));

    // Assert
    verify(serializer).serialize(isA(OutputStream.class), isA(Object.class), isA(TypeDef.class));
    assertEquals("", actualSerializeToStringResult);
  }

  /**
   * Test {@link SerializationUtils#serializeToBytes(Serializer, Object, TypeDef)}.
   *
   * <ul>
   *   <li>Given {@link IOException#IOException()}.
   *   <li>Then throw {@link IOException}.
   * </ul>
   *
   * <p>Method under test: {@link SerializationUtils#serializeToBytes(Serializer, Object, TypeDef)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SerializationUtils.serializeToBytes(Serializer, Object, TypeDef)"})
  public void testSerializeToBytes_givenIOException_thenThrowIOException() throws IOException {
    // Arrange
    Serializer<Object> serializer = mock(Serializer.class);
    doThrow(new IOException())
        .when(serializer)
        .serialize(Mockito.<OutputStream>any(), Mockito.<Object>any(), Mockito.<TypeDef<?>>any());

    // Act and Assert
    thrown.expect(IOException.class);
    SerializationUtils.serializeToBytes(serializer, "Obj", mock(TypeDef.class));
    verify(serializer).serialize(isA(OutputStream.class), isA(Object.class), isA(TypeDef.class));
  }

  /**
   * Test {@link SerializationUtils#serializeToBytes(Serializer, Object, TypeDef)}.
   *
   * <ul>
   *   <li>Then return empty array of {@code byte}.
   * </ul>
   *
   * <p>Method under test: {@link SerializationUtils#serializeToBytes(Serializer, Object, TypeDef)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SerializationUtils.serializeToBytes(Serializer, Object, TypeDef)"})
  public void testSerializeToBytes_thenReturnEmptyArrayOfByte() throws IOException {
    // Arrange
    Serializer<Object> serializer = mock(Serializer.class);
    doNothing()
        .when(serializer)
        .serialize(Mockito.<OutputStream>any(), Mockito.<Object>any(), Mockito.<TypeDef<?>>any());

    // Act
    byte[] actualSerializeToBytesResult =
        SerializationUtils.serializeToBytes(serializer, "Obj", mock(TypeDef.class));

    // Assert
    verify(serializer).serialize(isA(OutputStream.class), isA(Object.class), isA(TypeDef.class));
    assertArrayEquals(new byte[] {}, actualSerializeToBytesResult);
  }
}
