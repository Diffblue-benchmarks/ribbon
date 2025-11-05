package com.netflix.serialization;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class SerializationUtilsDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test:
   * {@link SerializationUtils#deserializeFromString(Deserializer, String, TypeDef)}
   */
  @Test
  public void testDeserializeFromString() throws IOException {
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
   * Method under test:
   * {@link SerializationUtils#deserializeFromString(Deserializer, String, TypeDef)}
   */
  @Test
  public void testDeserializeFromString2() throws IOException {
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
   * Method under test:
   * {@link SerializationUtils#serializeToString(Serializer, Object, TypeDef)}
   */
  @Test
  public void testSerializeToString() throws IOException {
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
   * Method under test:
   * {@link SerializationUtils#serializeToString(Serializer, Object, TypeDef)}
   */
  @Test
  public void testSerializeToString2() throws IOException {
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
   * Method under test:
   * {@link SerializationUtils#serializeToBytes(Serializer, Object, TypeDef)}
   */
  @Test
  public void testSerializeToBytes() throws IOException {
    // Arrange
    Serializer<Object> serializer = mock(Serializer.class);
    doNothing().when(serializer)
        .serialize(Mockito.<OutputStream>any(), Mockito.<Object>any(), Mockito.<TypeDef<Object>>any());

    // Act
    byte[] actualSerializeToBytesResult = SerializationUtils.serializeToBytes(serializer, "Obj", mock(TypeDef.class));

    // Assert
    verify(serializer).serialize(isA(OutputStream.class), isA(Object.class), isA(TypeDef.class));
    assertEquals(0, actualSerializeToBytesResult.length);
  }

  /**
   * Method under test:
   * {@link SerializationUtils#serializeToBytes(Serializer, Object, TypeDef)}
   */
  @Test
  public void testSerializeToBytes2() throws IOException {
    // Arrange
    Serializer<Object> serializer = mock(Serializer.class);
    doThrow(new IOException("foo")).when(serializer)
        .serialize(Mockito.<OutputStream>any(), Mockito.<Object>any(), Mockito.<TypeDef<Object>>any());

    // Act and Assert
    thrown.expect(IOException.class);
    SerializationUtils.serializeToBytes(serializer, "Obj", mock(TypeDef.class));
    verify(serializer).serialize(isA(OutputStream.class), isA(Object.class), isA(TypeDef.class));
  }
}
