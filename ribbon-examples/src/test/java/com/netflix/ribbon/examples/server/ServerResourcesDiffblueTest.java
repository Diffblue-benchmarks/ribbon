package com.netflix.ribbon.examples.server;

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

public class ServerResourcesDiffblueTest {
  /**
   * Test {@link ServerResources#getStream()}.
   *
   * <ul>
   *   <li>Then array length is {@code 15890}.
   * </ul>
   *
   * <p>Method under test: {@link ServerResources#getStream()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StreamingOutput ServerResources.getStream()"})
  public void testGetStream_thenArrayLengthIs15890() throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualStream = new ServerResources().getStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    actualStream.write(byteArrayOutputStream);

    // Assert
    assertEquals(15890, byteArrayOutputStream.toByteArray().length);
  }

  /**
   * Test {@link ServerResources#getPersonStream()}.
   *
   * <ul>
   *   <li>Then array length is {@code 2990}.
   * </ul>
   *
   * <p>Method under test: {@link ServerResources#getPersonStream()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StreamingOutput ServerResources.getPersonStream()"})
  public void testGetPersonStream_thenArrayLengthIs2990()
      throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualPersonStream = new ServerResources().getPersonStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    actualPersonStream.write(byteArrayOutputStream);

    // Assert
    assertEquals(2990, byteArrayOutputStream.toByteArray().length);
  }

  /**
   * Test {@link ServerResources#getCustomeEvents()}.
   *
   * <ul>
   *   <li>Then array length is {@code 14890}.
   * </ul>
   *
   * <p>Method under test: {@link ServerResources#getCustomeEvents()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StreamingOutput ServerResources.getCustomeEvents()"})
  public void testGetCustomeEvents_thenArrayLengthIs14890()
      throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualCustomeEvents = new ServerResources().getCustomeEvents();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    actualCustomeEvents.write(byteArrayOutputStream);

    // Assert
    assertEquals(14890, byteArrayOutputStream.toByteArray().length);
  }
}
