package com.netflix.serialization;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JacksonCodecDiffblueTest {
  /**
   * Test {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}.
   * <ul>
   *   <li>Then {@link ByteArrayOutputStream#ByteArrayOutputStream(int)} with one toByteArray is array of {@code byte} with {@code 4} and {@code 2}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JacksonCodec.serialize(OutputStream, Object, TypeDef)"})
  public void testSerialize_thenByteArrayOutputStreamWithOneToByteArrayIsArrayOfByteWith4And2() throws IOException {
    // Arrange
    JacksonCodec<Object> instance = JacksonCodec.getInstance();
    ByteArrayOutputStream out = new ByteArrayOutputStream(1);

    // Act
    instance.serialize(out, 42, null);

    // Assert
    assertArrayEquals(new byte[]{'4', '2'}, out.toByteArray());
  }

  /**
   * Test {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}.
   * <ul>
   *   <li>Then {@link ByteArrayOutputStream#ByteArrayOutputStream(int)} with one toByteArray is {@code null} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JacksonCodec.serialize(OutputStream, Object, TypeDef)"})
  public void testSerialize_thenByteArrayOutputStreamWithOneToByteArrayIsNullBytesIsUtf8() throws IOException {
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
   * Test {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}.
   * <ul>
   *   <li>Then {@link ByteArrayOutputStream#ByteArrayOutputStream(int)} with one toByteArray is {@code "Object"} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JacksonCodec.serialize(OutputStream, Object, TypeDef)"})
  public void testSerialize_thenByteArrayOutputStreamWithOneToByteArrayIsObjectBytesIsUtf8() throws IOException {
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
   * Test {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}.
   * <ul>
   *   <li>Then {@link ByteArrayOutputStream#ByteArrayOutputStream(int)} with zero toByteArray is {@code "Object"} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JacksonCodec.serialize(OutputStream, Object, TypeDef)"})
  public void testSerialize_thenByteArrayOutputStreamWithZeroToByteArrayIsObjectBytesIsUtf8() throws IOException {
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
   * Test getters and setters.
   * <p>
   * Method under test: {@link JacksonCodec#getInstance()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JacksonCodec JacksonCodec.getInstance()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    JacksonCodec<Object> actualInstance = JacksonCodec.getInstance();

    // Assert
    assertSame(actualInstance, actualInstance.getInstance());
  }
}
