package com.netflix.ribbon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UnsuccessfulResponseExceptionDiffblueTest {
  /**
   * Test {@link UnsuccessfulResponseException#UnsuccessfulResponseException(Throwable)}.
   * <ul>
   *   <li>Then return Message is {@code Throwable}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UnsuccessfulResponseException#UnsuccessfulResponseException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void UnsuccessfulResponseException.<init>(String)",
      "void UnsuccessfulResponseException.<init>(String, Throwable)",
      "void UnsuccessfulResponseException.<init>(Throwable)"})
  public void testNewUnsuccessfulResponseException_thenReturnMessageIsJavaLangThrowable() {
    // Arrange
    Throwable arg0 = new Throwable();

    // Act
    UnsuccessfulResponseException actualUnsuccessfulResponseException = new UnsuccessfulResponseException(arg0);

    // Assert
    assertEquals("java.lang.Throwable", actualUnsuccessfulResponseException.getMessage());
    assertEquals(0, actualUnsuccessfulResponseException.getSuppressed().length);
    assertSame(arg0, actualUnsuccessfulResponseException.getCause());
  }

  /**
   * Test {@link UnsuccessfulResponseException#UnsuccessfulResponseException(String)}.
   * <ul>
   *   <li>When {@code Arg0}.</li>
   *   <li>Then return Cause is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UnsuccessfulResponseException#UnsuccessfulResponseException(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void UnsuccessfulResponseException.<init>(String)",
      "void UnsuccessfulResponseException.<init>(String, Throwable)",
      "void UnsuccessfulResponseException.<init>(Throwable)"})
  public void testNewUnsuccessfulResponseException_whenArg0_thenReturnCauseIsNull() {
    // Arrange and Act
    UnsuccessfulResponseException actualUnsuccessfulResponseException = new UnsuccessfulResponseException("Arg0");

    // Assert
    assertEquals("Arg0", actualUnsuccessfulResponseException.getMessage());
    assertNull(actualUnsuccessfulResponseException.getCause());
    assertEquals(0, actualUnsuccessfulResponseException.getSuppressed().length);
  }

  /**
   * Test {@link UnsuccessfulResponseException#UnsuccessfulResponseException(String, Throwable)}.
   * <ul>
   *   <li>When {@code Arg0}.</li>
   *   <li>Then return Message is {@code Arg0}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UnsuccessfulResponseException#UnsuccessfulResponseException(String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void UnsuccessfulResponseException.<init>(String)",
      "void UnsuccessfulResponseException.<init>(String, Throwable)",
      "void UnsuccessfulResponseException.<init>(Throwable)"})
  public void testNewUnsuccessfulResponseException_whenArg0_thenReturnMessageIsArg0() {
    // Arrange
    Throwable arg1 = new Throwable();

    // Act
    UnsuccessfulResponseException actualUnsuccessfulResponseException = new UnsuccessfulResponseException("Arg0", arg1);

    // Assert
    assertEquals("Arg0", actualUnsuccessfulResponseException.getMessage());
    assertEquals(0, actualUnsuccessfulResponseException.getSuppressed().length);
    assertSame(arg1, actualUnsuccessfulResponseException.getCause());
  }
}
