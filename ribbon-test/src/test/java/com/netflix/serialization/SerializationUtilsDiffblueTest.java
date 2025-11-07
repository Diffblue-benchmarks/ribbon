package com.netflix.serialization;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class SerializationUtilsDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link SerializationUtils#deserializeFromString(Deserializer, String, TypeDef)}.
   * <ul>
   *   <li>Given {@code Deserialize}.</li>
   *   <li>Then return {@code Deserialize}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SerializationUtils#deserializeFromString(Deserializer, String, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object SerializationUtils.deserializeFromString(Deserializer, String, TypeDef)"})
  public void testDeserializeFromString_givenDeserialize_thenReturnDeserialize() throws IOException {
    // Arrange
    Deserializer<Object> deserializer = mock(Deserializer.class);
    when(deserializer.deserialize(Mockito.<InputStream>any(), Mockito.<TypeDef<Object>>any()))
        .thenReturn("Deserialize");

    // Act
    Object actualDeserializeFromStringResult = SerializationUtils.deserializeFromString(deserializer,
        "Not all who wander are lost", mock(TypeDef.class));

    // Assert
    verify(deserializer).deserialize(isA(InputStream.class), isA(TypeDef.class));
    assertEquals("Deserialize", actualDeserializeFromStringResult);
  }

  /**
   * Test {@link SerializationUtils#deserializeFromString(Deserializer, String, TypeDef)}.
   * <ul>
   *   <li>Given {@link IOException#IOException(String)} with {@code UTF-8}.</li>
   *   <li>Then throw {@link IOException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SerializationUtils#deserializeFromString(Deserializer, String, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object SerializationUtils.deserializeFromString(Deserializer, String, TypeDef)"})
  public void testDeserializeFromString_givenIOExceptionWithUtf8_thenThrowIOException() throws IOException {
    // Arrange
    Deserializer<Object> deserializer = mock(Deserializer.class);
    when(deserializer.deserialize(Mockito.<InputStream>any(), Mockito.<TypeDef<Object>>any()))
        .thenThrow(new IOException("UTF-8"));

    // Act and Assert
    thrown.expect(IOException.class);
    SerializationUtils.deserializeFromString(deserializer, "Not all who wander are lost", mock(TypeDef.class));
    verify(deserializer).deserialize(isA(InputStream.class), isA(TypeDef.class));
  }

  /**
   * Test {@link SerializationUtils#serializeToString(Serializer, Object, TypeDef)}.
   * <ul>
   *   <li>Given {@link IOException#IOException(String)} with {@code UTF-8}.</li>
   *   <li>Then throw {@link IOException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SerializationUtils#serializeToString(Serializer, Object, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SerializationUtils.serializeToString(Serializer, Object, TypeDef)"})
  public void testSerializeToString_givenIOExceptionWithUtf8_thenThrowIOException() throws IOException {
    // Arrange
    Serializer<Object> serializer = mock(Serializer.class);
    doThrow(new IOException("UTF-8")).when(serializer)
        .serialize(Mockito.<OutputStream>any(), Mockito.<Object>any(), Mockito.<TypeDef<Object>>any());

    // Act and Assert
    thrown.expect(IOException.class);
    SerializationUtils.serializeToString(serializer, "Obj", mock(TypeDef.class));
    verify(serializer).serialize(isA(OutputStream.class), isA(Object.class), isA(TypeDef.class));
  }

  /**
   * Test {@link SerializationUtils#serializeToString(Serializer, Object, TypeDef)}.
   * <ul>
   *   <li>When {@link Serializer} {@link Serializer#serialize(OutputStream, Object, TypeDef)} does nothing.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SerializationUtils#serializeToString(Serializer, Object, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SerializationUtils.serializeToString(Serializer, Object, TypeDef)"})
  public void testSerializeToString_whenSerializerSerializeDoesNothing_thenReturnEmptyString() throws IOException {
    // Arrange
    Serializer<Object> serializer = mock(Serializer.class);
    doNothing().when(serializer)
        .serialize(Mockito.<OutputStream>any(), Mockito.<Object>any(), Mockito.<TypeDef<Object>>any());

    // Act
    String actualSerializeToStringResult = SerializationUtils.serializeToString(serializer, "Obj", mock(TypeDef.class));

    // Assert
    verify(serializer).serialize(isA(OutputStream.class), isA(Object.class), isA(TypeDef.class));
    assertEquals("", actualSerializeToStringResult);
  }

  /**
   * Test {@link SerializationUtils#serializeToBytes(Serializer, Object, TypeDef)}.
   * <ul>
   *   <li>Given {@link IOException#IOException(String)} with {@code foo}.</li>
   *   <li>Then throw {@link IOException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SerializationUtils#serializeToBytes(Serializer, Object, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"byte[] SerializationUtils.serializeToBytes(Serializer, Object, TypeDef)"})
  public void testSerializeToBytes_givenIOExceptionWithFoo_thenThrowIOException() throws IOException {
    // Arrange
    Serializer<Object> serializer = mock(Serializer.class);
    doThrow(new IOException("foo")).when(serializer)
        .serialize(Mockito.<OutputStream>any(), Mockito.<Object>any(), Mockito.<TypeDef<Object>>any());

    // Act and Assert
    thrown.expect(IOException.class);
    SerializationUtils.serializeToBytes(serializer, "Obj", mock(TypeDef.class));
    verify(serializer).serialize(isA(OutputStream.class), isA(Object.class), isA(TypeDef.class));
  }

  /**
   * Test {@link SerializationUtils#serializeToBytes(Serializer, Object, TypeDef)}.
   * <ul>
   *   <li>Then return empty array of {@code byte}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SerializationUtils#serializeToBytes(Serializer, Object, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"byte[] SerializationUtils.serializeToBytes(Serializer, Object, TypeDef)"})
  public void testSerializeToBytes_thenReturnEmptyArrayOfByte() throws IOException {
    // Arrange
    Serializer<Object> serializer = mock(Serializer.class);
    doNothing().when(serializer)
        .serialize(Mockito.<OutputStream>any(), Mockito.<Object>any(), Mockito.<TypeDef<Object>>any());

    // Act
    byte[] actualSerializeToBytesResult = SerializationUtils.serializeToBytes(serializer, "Obj", mock(TypeDef.class));

    // Assert
    verify(serializer).serialize(isA(OutputStream.class), isA(Object.class), isA(TypeDef.class));
    assertArrayEquals(new byte[]{}, actualSerializeToBytesResult);
  }
}
