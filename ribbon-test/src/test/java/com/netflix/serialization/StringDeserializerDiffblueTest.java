package com.netflix.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class StringDeserializerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Method under test: {@link StringDeserializer#getInstance()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StringDeserializer StringDeserializer.getInstance()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    StringDeserializer actualInstance = StringDeserializer.getInstance();

    // Assert
    assertSame(actualInstance, actualInstance.getInstance());
  }

  /**
   * Test {@link StringDeserializer#deserialize(InputStream, TypeDef)}.
   * <ul>
   *   <li>Then return {@code AXAXAXAX}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringDeserializer#deserialize(InputStream, TypeDef)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String StringDeserializer.deserialize(InputStream, TypeDef)"})
  public void testDeserialize_thenReturnAxaxaxax() throws IOException {
    // Arrange
    StringDeserializer instance = StringDeserializer.getInstance();
    ByteArrayInputStream in = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertEquals("AXAXAXAX", instance.deserialize(in, mock(TypeDef.class)));
    assertEquals(-1, in.read(new byte[]{}));
  }
}
