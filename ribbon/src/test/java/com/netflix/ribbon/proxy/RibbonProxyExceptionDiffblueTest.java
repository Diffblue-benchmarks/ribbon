package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class RibbonProxyExceptionDiffblueTest {
  /**
   * Method under test: {@link RibbonProxyException#RibbonProxyException(String)}
   */
  @Test
  public void testNewRibbonProxyException() {
    // Arrange and Act
    RibbonProxyException actualRibbonProxyException = new RibbonProxyException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualRibbonProxyException.getMessage());
    assertNull(actualRibbonProxyException.getCause());
    assertEquals(0, actualRibbonProxyException.getSuppressed().length);
  }

  /**
   * Method under test:
   * {@link RibbonProxyException#RibbonProxyException(String, Throwable)}
   */
  @Test
  public void testNewRibbonProxyException2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    RibbonProxyException actualRibbonProxyException = new RibbonProxyException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualRibbonProxyException.getMessage());
    assertEquals(0, actualRibbonProxyException.getSuppressed().length);
    assertSame(cause, actualRibbonProxyException.getCause());
  }
}
