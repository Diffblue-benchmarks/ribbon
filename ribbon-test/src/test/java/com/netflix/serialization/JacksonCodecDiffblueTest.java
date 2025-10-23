package com.netflix.serialization;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JacksonCodecDiffblueTest {
  /**
   * Test {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then {@link ByteArrayOutputStream#ByteArrayOutputStream()} toByteArray is {@code
   *       "Object"} Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link JacksonCodec#serialize(OutputStream, Object, TypeDef)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JacksonCodec.serialize(OutputStream, Object, TypeDef)"})
  public void testSerialize_whenObject_thenByteArrayOutputStreamToByteArrayIsObjectBytesIsUtf8()
      throws IOException {
    // Arrange
    JacksonCodec<Object> instance = JacksonCodec.getInstance();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    // Act
    instance.serialize(out, "Object", null);

    // Assert
    byte[] expectedToByteArrayResult = "\"Object\"".getBytes("UTF-8");
    assertArrayEquals(expectedToByteArrayResult, out.toByteArray());
  }

  /**
   * Test getters and setters.
   *
   * <p>Method under test: {@link JacksonCodec#getInstance()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JacksonCodec JacksonCodec.getInstance()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    JacksonCodec<Object> actualInstance = JacksonCodec.getInstance();
    JacksonCodec<Object> actualInstance2 = actualInstance.getInstance();

    // Assert
    assertSame(actualInstance, actualInstance2);
  }
}
