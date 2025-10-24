package com.netflix.ribbon.test.resources;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EmbeddedResourcesDiffblueTest {
  /**
   * Test {@link EmbeddedResources#getStream()}.
   *
   * <ul>
   *   <li>Then array length is {@code 14890}.
   * </ul>
   *
   * <p>Method under test: {@link EmbeddedResources#getStream()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StreamingOutput EmbeddedResources.getStream()"})
  public void testGetStream_thenArrayLengthIs14890() throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualStream = new EmbeddedResources().getStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    actualStream.write(byteArrayOutputStream);

    // Assert
    assertEquals(14890, byteArrayOutputStream.toByteArray().length);
  }

  /**
   * Test {@link EmbeddedResources#getEntityStream()}.
   *
   * <ul>
   *   <li>Then array length is {@code 34890}.
   * </ul>
   *
   * <p>Method under test: {@link EmbeddedResources#getEntityStream()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StreamingOutput EmbeddedResources.getEntityStream()"})
  public void testGetEntityStream_thenArrayLengthIs34890()
      throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualEntityStream = new EmbeddedResources().getEntityStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    actualEntityStream.write(byteArrayOutputStream);

    // Assert
    assertEquals(34890, byteArrayOutputStream.toByteArray().length);
  }
}
