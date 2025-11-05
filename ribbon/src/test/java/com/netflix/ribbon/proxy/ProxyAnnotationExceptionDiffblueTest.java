package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class ProxyAnnotationExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link ProxyAnnotationException#ProxyAnnotationException(String)}
   */
  @Test
  public void testNewProxyAnnotationException() {
    // Arrange and Act
    ProxyAnnotationException actualProxyAnnotationException = new ProxyAnnotationException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualProxyAnnotationException.getMessage());
    assertNull(actualProxyAnnotationException.getCause());
    assertEquals(0, actualProxyAnnotationException.getSuppressed().length);
  }
}
