package com.netflix.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class StringDeserializerDiffblueTest {
  /**
   * Method under test:
   * {@link StringDeserializer#deserialize(InputStream, TypeDef)}
   */
  @Test
  public void testDeserialize() throws IOException {
    // Arrange
    StringDeserializer instance = StringDeserializer.getInstance();
    ByteArrayInputStream in = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertEquals("AXAXAXAX", instance.deserialize(in, mock(TypeDef.class)));
    assertEquals(-1, in.read(new byte[]{}));
  }

  /**
   * Method under test: {@link StringDeserializer#getInstance()}
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    StringDeserializer actualInstance = StringDeserializer.getInstance();

    // Assert
    assertSame(actualInstance, actualInstance.getInstance());
  }
}
