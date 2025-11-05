package com.netflix.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class UnexpectedHttpResponseExceptionDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UnexpectedHttpResponseException#UnexpectedHttpResponseException(int, String)}
   *   <li>{@link UnexpectedHttpResponseException#getStatusCode()}
   *   <li>{@link UnexpectedHttpResponseException#getStatusLine()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    UnexpectedHttpResponseException actualUnexpectedHttpResponseException = new UnexpectedHttpResponseException(1,
        "https://example.org/example");
    int actualStatusCode = actualUnexpectedHttpResponseException.getStatusCode();

    // Assert
    assertEquals("https://example.org/example", actualUnexpectedHttpResponseException.getStatusLine());
    assertEquals("https://example.org/example", actualUnexpectedHttpResponseException.getMessage());
    assertNull(actualUnexpectedHttpResponseException.getCause());
    assertEquals(0, actualUnexpectedHttpResponseException.getSuppressed().length);
    assertEquals(1, actualStatusCode);
  }
}
