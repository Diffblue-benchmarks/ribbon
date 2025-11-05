package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.io.InterruptedIOException;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

public class NFHttpMethodRetryHandlerDiffblueTest {
  /**
   * Method under test:
   * {@link NFHttpMethodRetryHandler#NFHttpMethodRetryHandler(String, int, boolean, int)}
   */
  @Test
  public void testNewNFHttpMethodRetryHandler() {
    // Arrange and Act
    NFHttpMethodRetryHandler actualNfHttpMethodRetryHandler = new NFHttpMethodRetryHandler("Dr Jane Doe", 3, true, 3);

    // Assert
    assertEquals(3, actualNfHttpMethodRetryHandler.getRetryCount());
    assertTrue(actualNfHttpMethodRetryHandler.isRequestSentRetryEnabled());
  }

  /**
   * Method under test:
   * {@link NFHttpMethodRetryHandler#retryRequest(IOException, int, HttpContext)}
   */
  @Test
  public void testRetryRequest() {
    // Arrange
    NFHttpMethodRetryHandler nfHttpMethodRetryHandler = new NFHttpMethodRetryHandler("Dr Jane Doe", 3, true, 3);

    InterruptedIOException exception = new InterruptedIOException("foo");
    exception.addSuppressed(new Throwable());

    // Act and Assert
    assertFalse(nfHttpMethodRetryHandler.retryRequest(exception, 3, new DefaultClientConnection()));
  }

  /**
   * Method under test:
   * {@link NFHttpMethodRetryHandler#retryRequest(IOException, int, HttpContext)}
   */
  @Test
  public void testRetryRequest2() {
    // Arrange
    NFHttpMethodRetryHandler nfHttpMethodRetryHandler = new NFHttpMethodRetryHandler("Dr Jane Doe", 1, true, 3);

    InterruptedIOException exception = new InterruptedIOException("foo");
    exception.addSuppressed(new Throwable());

    // Act and Assert
    assertFalse(nfHttpMethodRetryHandler.retryRequest(exception, 3, new DefaultClientConnection()));
  }
}
