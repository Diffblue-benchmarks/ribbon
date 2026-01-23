package com.netflix.ribbon.evache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CacheFaultExceptionDiffblueTest {
  /**
   * Test {@link CacheFaultException#CacheFaultException(String)}.
   *
   * <ul>
   *   <li>When {@code An error occurred}.
   *   <li>Then return Cause is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CacheFaultException#CacheFaultException(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CacheFaultException.<init>(String)",
    "void CacheFaultException.<init>(String, Throwable)"
  })
  public void testNewCacheFaultException_whenAnErrorOccurred_thenReturnCauseIsNull() {
    // Arrange and Act
    CacheFaultException actualCacheFaultException = new CacheFaultException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualCacheFaultException.getMessage());
    assertNull(actualCacheFaultException.getCause());
    assertEquals(0, actualCacheFaultException.getSuppressed().length);
  }

  /**
   * Test {@link CacheFaultException#CacheFaultException(String, Throwable)}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   *   <li>Then return Cause is {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link CacheFaultException#CacheFaultException(String, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CacheFaultException.<init>(String)",
    "void CacheFaultException.<init>(String, Throwable)"
  })
  public void testNewCacheFaultException_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    CacheFaultException actualCacheFaultException =
        new CacheFaultException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualCacheFaultException.getMessage());
    assertEquals(0, actualCacheFaultException.getSuppressed().length);
    assertSame(cause, actualCacheFaultException.getCause());
  }
}
