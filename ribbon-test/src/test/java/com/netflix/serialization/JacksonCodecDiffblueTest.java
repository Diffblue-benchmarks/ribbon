package com.netflix.serialization;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.Test;

public class JacksonCodecDiffblueTest {
  /**
   * Method under test: {@link JacksonCodec#getInstance()}
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    JacksonCodec<Object> actualInstance = JacksonCodec.getInstance();

    // Assert
    assertSame(actualInstance, actualInstance.getInstance());
  }

  /**
   * Method under test:
   * {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}
   */
  @Test
  public void testSerialize() throws IOException {
    // Arrange
    JacksonCodec<Object> instance = JacksonCodec.getInstance();
    ByteArrayOutputStream out = new ByteArrayOutputStream(1);

    // Act
    instance.serialize(out, "Object", null);

    // Assert
    byte[] expectedToByteArrayResult = "\"Object\"".getBytes("UTF-8");
    assertArrayEquals(expectedToByteArrayResult, out.toByteArray());
  }

  /**
   * Method under test:
   * {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}
   */
  @Test
  public void testSerialize2() throws IOException {
    // Arrange
    JacksonCodec<Object> instance = JacksonCodec.getInstance();
    ByteArrayOutputStream out = new ByteArrayOutputStream(0);

    // Act
    instance.serialize(out, "Object", null);

    // Assert
    byte[] expectedToByteArrayResult = "\"Object\"".getBytes("UTF-8");
    assertArrayEquals(expectedToByteArrayResult, out.toByteArray());
  }

  /**
   * Method under test:
   * {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}
   */
  @Test
  public void testSerialize3() throws IOException {
    // Arrange
    JacksonCodec<Object> instance = JacksonCodec.getInstance();
    ByteArrayOutputStream out = new ByteArrayOutputStream(1);

    // Act
    instance.serialize(out, null, null);

    // Assert
    byte[] expectedToByteArrayResult = "null".getBytes("UTF-8");
    assertArrayEquals(expectedToByteArrayResult, out.toByteArray());
  }

  /**
   * Method under test:
   * {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}
   */
  @Test
  public void testSerialize4() throws IOException {
    // Arrange
    JacksonCodec<Object> instance = JacksonCodec.getInstance();
    ByteArrayOutputStream out = new ByteArrayOutputStream(1);

    // Act
    instance.serialize(out, 42, null);

    // Assert
    assertArrayEquals(new byte[]{'4', '2'}, out.toByteArray());
  }
}
