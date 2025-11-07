package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.reactive.ExecutionListener.AbortExecutionException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExecutionListenerDiffblueTest {
  /**
   * Test AbortExecutionException {@link AbortExecutionException#AbortExecutionException(String)}.
   * <ul>
   *   <li>Then return Cause is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbortExecutionException#AbortExecutionException(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbortExecutionException.<init>(String)",
      "void AbortExecutionException.<init>(String, Throwable)"})
  public void testAbortExecutionExceptionNewAbortExecutionException_thenReturnCauseIsNull() {
    // Arrange and Act
    AbortExecutionException actualAbortExecutionException = new AbortExecutionException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualAbortExecutionException.getMessage());
    assertNull(actualAbortExecutionException.getCause());
    assertEquals(0, actualAbortExecutionException.getSuppressed().length);
  }

  /**
   * Test AbortExecutionException {@link AbortExecutionException#AbortExecutionException(String, Throwable)}.
   * <ul>
   *   <li>Then return Cause is {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbortExecutionException#AbortExecutionException(String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbortExecutionException.<init>(String)",
      "void AbortExecutionException.<init>(String, Throwable)"})
  public void testAbortExecutionExceptionNewAbortExecutionException_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    AbortExecutionException actualAbortExecutionException = new AbortExecutionException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualAbortExecutionException.getMessage());
    assertEquals(0, actualAbortExecutionException.getSuppressed().length);
    assertSame(cause, actualAbortExecutionException.getCause());
  }
}
