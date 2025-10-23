package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RibbonProxyExceptionDiffblueTest {
  /**
   * Test {@link RibbonProxyException#RibbonProxyException(String)}.
   *
   * <p>Method under test: {@link RibbonProxyException#RibbonProxyException(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RibbonProxyException.<init>(String)",
    "void RibbonProxyException.<init>(String, Throwable)"
  })
  public void testNewRibbonProxyException() {
    // Arrange and Act
    RibbonProxyException actualRibbonProxyException =
        new RibbonProxyException(
            "\"Failed to establish connection with Ribbon Proxy Server: Connection timeout exceeded.\"");

    // Assert
    assertEquals(
        "\"Failed to establish connection with Ribbon Proxy Server: Connection timeout exceeded.\"",
        actualRibbonProxyException.getMessage());
    assertNull(actualRibbonProxyException.getCause());
    assertEquals(0, actualRibbonProxyException.getSuppressed().length);
  }

  /**
   * Test {@link RibbonProxyException#RibbonProxyException(String, Throwable)}.
   *
   * <ul>
   *   <li>When a string.
   *   <li>Then return Message is a string.
   * </ul>
   *
   * <p>Method under test: {@link RibbonProxyException#RibbonProxyException(String, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RibbonProxyException.<init>(String)",
    "void RibbonProxyException.<init>(String, Throwable)"
  })
  public void testNewRibbonProxyException_whenAString_thenReturnMessageIsAString() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    RibbonProxyException actualRibbonProxyException =
        new RibbonProxyException(
            "\"Failed to establish connection with the Ribbon Proxy Server. Please check your network settings and"
                + " try again.\"",
            cause);

    // Assert
    assertEquals(
        "\"Failed to establish connection with the Ribbon Proxy Server. Please check your network settings and"
            + " try again.\"",
        actualRibbonProxyException.getMessage());
    assertEquals(0, actualRibbonProxyException.getSuppressed().length);
    assertSame(cause, actualRibbonProxyException.getCause());
  }
}
