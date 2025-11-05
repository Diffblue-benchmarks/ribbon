package com.netflix.ribbon.evache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class CacheMissExceptionDiffblueTest {
  /**
   * Method under test: default or parameterless constructor of
   * {@link CacheMissException}
   */
  @Test
  public void testNewCacheMissException() {
    // Arrange and Act
    CacheMissException actualCacheMissException = new CacheMissException();

    // Assert
    assertNull(actualCacheMissException.getMessage());
    assertNull(actualCacheMissException.getCause());
    assertEquals(0, actualCacheMissException.getSuppressed().length);
  }
}
