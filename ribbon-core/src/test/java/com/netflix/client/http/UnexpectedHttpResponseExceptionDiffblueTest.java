package com.netflix.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UnexpectedHttpResponseExceptionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UnexpectedHttpResponseException#UnexpectedHttpResponseException(int, String)}
   *   <li>{@link UnexpectedHttpResponseException#getStatusCode()}
   *   <li>{@link UnexpectedHttpResponseException#getStatusLine()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void UnexpectedHttpResponseException.<init>(int, String)",
      "int UnexpectedHttpResponseException.getStatusCode()", "String UnexpectedHttpResponseException.getStatusLine()"})
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
