package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.io.InterruptedIOException;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.protocol.HttpContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class NFHttpMethodRetryHandlerDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link NFHttpMethodRetryHandler#NFHttpMethodRetryHandler(String, int, boolean, int)}.
   *
   * <p>Method under test: {@link NFHttpMethodRetryHandler#NFHttpMethodRetryHandler(String, int,
   * boolean, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void NFHttpMethodRetryHandler.<init>(String, int, boolean, int)"})
  public void testNewNFHttpMethodRetryHandler() {
    // Arrange and Act
    NFHttpMethodRetryHandler actualNfHttpMethodRetryHandler =
        new NFHttpMethodRetryHandler("\"DefaultNetflixHttpClient\"", 3, true, 3);

    // Assert
    assertEquals(3, actualNfHttpMethodRetryHandler.getRetryCount());
    assertTrue(actualNfHttpMethodRetryHandler.isRequestSentRetryEnabled());
  }

  /**
   * Test {@link NFHttpMethodRetryHandler#retryRequest(IOException, int, HttpContext)}.
   *
   * <p>Method under test: {@link NFHttpMethodRetryHandler#retryRequest(IOException, int,
   * HttpContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NFHttpMethodRetryHandler.retryRequest(IOException, int, HttpContext)"
  })
  public void testRetryRequest() {
    // Arrange
    NFHttpMethodRetryHandler nfHttpMethodRetryHandler =
        new NFHttpMethodRetryHandler("\"DefaultNetflixHttpClient\"", 1, true, 3);

    InterruptedIOException exception = new InterruptedIOException();
    exception.addSuppressed(new Throwable());

    // Act and Assert
    assertFalse(nfHttpMethodRetryHandler.retryRequest(exception, 3, new DefaultClientConnection()));
  }

  /**
   * Test {@link NFHttpMethodRetryHandler#retryRequest(IOException, int, HttpContext)}.
   *
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link NFHttpMethodRetryHandler#retryRequest(IOException, int,
   * HttpContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NFHttpMethodRetryHandler.retryRequest(IOException, int, HttpContext)"
  })
  public void testRetryRequest_givenThrowable_thenReturnFalse() {
    // Arrange
    NFHttpMethodRetryHandler nfHttpMethodRetryHandler =
        new NFHttpMethodRetryHandler("\"DefaultNetflixHttpClient\"", 3, true, 3);

    InterruptedIOException exception = new InterruptedIOException();
    exception.addSuppressed(new Throwable());

    // Act and Assert
    assertFalse(nfHttpMethodRetryHandler.retryRequest(exception, 3, new DefaultClientConnection()));
  }

  /**
   * Test {@link NFHttpMethodRetryHandler#retryRequest(IOException, int, HttpContext)}.
   *
   * <ul>
   *   <li>When {@link IOException#IOException()}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NFHttpMethodRetryHandler#retryRequest(IOException, int,
   * HttpContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NFHttpMethodRetryHandler.retryRequest(IOException, int, HttpContext)"
  })
  public void testRetryRequest_whenIOException_thenThrowIllegalArgumentException() {
    // Arrange
    NFHttpMethodRetryHandler nfHttpMethodRetryHandler =
        new NFHttpMethodRetryHandler("\"DefaultNetflixHttpClient\"", 3, true, 3);

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    nfHttpMethodRetryHandler.retryRequest(new IOException(), 3, null);
  }

  /**
   * Test {@link NFHttpMethodRetryHandler#retryRequest(IOException, int, HttpContext)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NFHttpMethodRetryHandler#retryRequest(IOException, int,
   * HttpContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NFHttpMethodRetryHandler.retryRequest(IOException, int, HttpContext)"
  })
  public void testRetryRequest_whenNull_thenThrowIllegalArgumentException() {
    // Arrange
    NFHttpMethodRetryHandler nfHttpMethodRetryHandler =
        new NFHttpMethodRetryHandler("\"DefaultNetflixHttpClient\"", 3, true, 3);

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    nfHttpMethodRetryHandler.retryRequest(null, 3, new DefaultClientConnection());
  }
}
