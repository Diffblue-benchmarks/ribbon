package com.netflix.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UnexpectedHttpResponseExceptionDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link UnexpectedHttpResponseException#UnexpectedHttpResponseException(int, String)}
   *   <li>{@link UnexpectedHttpResponseException#getStatusCode()}
   *   <li>{@link UnexpectedHttpResponseException#getStatusLine()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UnexpectedHttpResponseException.<init>(int, String)",
    "int UnexpectedHttpResponseException.getStatusCode()",
    "String UnexpectedHttpResponseException.getStatusLine()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    UnexpectedHttpResponseException actualUnexpectedHttpResponseException =
        new UnexpectedHttpResponseException(1, "\"HTTP/1.1 404 Not Found\"");
    int actualStatusCode = actualUnexpectedHttpResponseException.getStatusCode();

    // Assert
    assertEquals(
        "\"HTTP/1.1 404 Not Found\"", actualUnexpectedHttpResponseException.getStatusLine());
    assertEquals("\"HTTP/1.1 404 Not Found\"", actualUnexpectedHttpResponseException.getMessage());
    assertNull(actualUnexpectedHttpResponseException.getCause());
    assertEquals(0, actualUnexpectedHttpResponseException.getSuppressed().length);
    assertEquals(1, actualStatusCode);
  }
}
