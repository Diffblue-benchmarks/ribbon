package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RibbonProxyExceptionDiffblueTest {
  /**
   * Test {@link RibbonProxyException#RibbonProxyException(String)}.
   * <ul>
   *   <li>When {@code An error occurred}.</li>
   *   <li>Then return Cause is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonProxyException#RibbonProxyException(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RibbonProxyException.<init>(String)", "void RibbonProxyException.<init>(String, Throwable)"})
  public void testNewRibbonProxyException_whenAnErrorOccurred_thenReturnCauseIsNull() {
    // Arrange and Act
    RibbonProxyException actualRibbonProxyException = new RibbonProxyException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualRibbonProxyException.getMessage());
    assertNull(actualRibbonProxyException.getCause());
    assertEquals(0, actualRibbonProxyException.getSuppressed().length);
  }

  /**
   * Test {@link RibbonProxyException#RibbonProxyException(String, Throwable)}.
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.</li>
   *   <li>Then return Cause is {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonProxyException#RibbonProxyException(String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RibbonProxyException.<init>(String)", "void RibbonProxyException.<init>(String, Throwable)"})
  public void testNewRibbonProxyException_whenThrowable_thenReturnCauseIsThrowable() {
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
