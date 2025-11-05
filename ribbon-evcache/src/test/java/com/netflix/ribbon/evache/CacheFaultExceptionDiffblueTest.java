package com.netflix.ribbon.evache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class CacheFaultExceptionDiffblueTest {
  /**
   * Method under test: {@link CacheFaultException#CacheFaultException(String)}
   */
  @Test
  public void testNewCacheFaultException() {
    // Arrange and Act
    CacheFaultException actualCacheFaultException = new CacheFaultException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualCacheFaultException.getMessage());
    assertNull(actualCacheFaultException.getCause());
    assertEquals(0, actualCacheFaultException.getSuppressed().length);
  }

  /**
   * Method under test:
   * {@link CacheFaultException#CacheFaultException(String, Throwable)}
   */
  @Test
  public void testNewCacheFaultException2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    CacheFaultException actualCacheFaultException = new CacheFaultException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualCacheFaultException.getMessage());
    assertEquals(0, actualCacheFaultException.getSuppressed().length);
    assertSame(cause, actualCacheFaultException.getCause());
  }
}
