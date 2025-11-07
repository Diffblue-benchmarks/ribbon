package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.io.InterruptedIOException;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NFHttpMethodRetryHandlerDiffblueTest {
  /**
   * Test {@link NFHttpMethodRetryHandler#NFHttpMethodRetryHandler(String, int, boolean, int)}.
   * <p>
   * Method under test: {@link NFHttpMethodRetryHandler#NFHttpMethodRetryHandler(String, int, boolean, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NFHttpMethodRetryHandler.<init>(String, int, boolean, int)"})
  public void testNewNFHttpMethodRetryHandler() {
    // Arrange and Act
    NFHttpMethodRetryHandler actualNfHttpMethodRetryHandler = new NFHttpMethodRetryHandler("Dr Jane Doe", 3, true, 3);

    // Assert
    assertEquals(3, actualNfHttpMethodRetryHandler.getRetryCount());
    assertTrue(actualNfHttpMethodRetryHandler.isRequestSentRetryEnabled());
  }

  /**
   * Test {@link NFHttpMethodRetryHandler#retryRequest(IOException, int, HttpContext)}.
   * <p>
   * Method under test: {@link NFHttpMethodRetryHandler#retryRequest(IOException, int, HttpContext)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean NFHttpMethodRetryHandler.retryRequest(IOException, int, HttpContext)"})
  public void testRetryRequest() {
    // Arrange
    NFHttpMethodRetryHandler nfHttpMethodRetryHandler = new NFHttpMethodRetryHandler("Dr Jane Doe", 1, true, 3);

    InterruptedIOException exception = new InterruptedIOException("foo");
    exception.addSuppressed(new Throwable());

    // Act and Assert
    assertFalse(nfHttpMethodRetryHandler.retryRequest(exception, 3, new DefaultClientConnection()));
  }

  /**
   * Test {@link NFHttpMethodRetryHandler#retryRequest(IOException, int, HttpContext)}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NFHttpMethodRetryHandler#retryRequest(IOException, int, HttpContext)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean NFHttpMethodRetryHandler.retryRequest(IOException, int, HttpContext)"})
  public void testRetryRequest_givenThrowable_thenReturnFalse() {
    // Arrange
    NFHttpMethodRetryHandler nfHttpMethodRetryHandler = new NFHttpMethodRetryHandler("Dr Jane Doe", 3, true, 3);

    InterruptedIOException exception = new InterruptedIOException("foo");
    exception.addSuppressed(new Throwable());

    // Act and Assert
    assertFalse(nfHttpMethodRetryHandler.retryRequest(exception, 3, new DefaultClientConnection()));
  }
}
