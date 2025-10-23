package com.netflix.ribbon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UnsuccessfulResponseExceptionDiffblueTest {
  /**
   * Test {@link UnsuccessfulResponseException#UnsuccessfulResponseException(String)}.
   *
   * <p>Method under test: {@link
   * UnsuccessfulResponseException#UnsuccessfulResponseException(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UnsuccessfulResponseException.<init>(String)",
    "void UnsuccessfulResponseException.<init>(String, Throwable)",
    "void UnsuccessfulResponseException.<init>(Throwable)"
  })
  public void testNewUnsuccessfulResponseException() {
    // Arrange and Act
    UnsuccessfulResponseException actualUnsuccessfulResponseException =
        new UnsuccessfulResponseException(
            "\"Error: Unsuccessful response received from the server while trying to stream a Netflix movie.\"");

    // Assert
    assertEquals(
        "\"Error: Unsuccessful response received from the server while trying to stream a Netflix movie.\"",
        actualUnsuccessfulResponseException.getMessage());
    assertNull(actualUnsuccessfulResponseException.getCause());
    assertEquals(0, actualUnsuccessfulResponseException.getSuppressed().length);
  }

  /**
   * Test {@link UnsuccessfulResponseException#UnsuccessfulResponseException(Throwable)}.
   *
   * <ul>
   *   <li>Then return Message is {@code Throwable}.
   * </ul>
   *
   * <p>Method under test: {@link
   * UnsuccessfulResponseException#UnsuccessfulResponseException(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UnsuccessfulResponseException.<init>(String)",
    "void UnsuccessfulResponseException.<init>(String, Throwable)",
    "void UnsuccessfulResponseException.<init>(Throwable)"
  })
  public void testNewUnsuccessfulResponseException_thenReturnMessageIsJavaLangThrowable() {
    // Arrange
    Throwable arg0 = new Throwable();

    // Act
    UnsuccessfulResponseException actualUnsuccessfulResponseException =
        new UnsuccessfulResponseException(arg0);

    // Assert
    assertEquals("java.lang.Throwable", actualUnsuccessfulResponseException.getMessage());
    assertEquals(0, actualUnsuccessfulResponseException.getSuppressed().length);
    assertSame(arg0, actualUnsuccessfulResponseException.getCause());
  }

  /**
   * Test {@link UnsuccessfulResponseException#UnsuccessfulResponseException(String, Throwable)}.
   *
   * <ul>
   *   <li>When a string.
   *   <li>Then return Message is a string.
   * </ul>
   *
   * <p>Method under test: {@link
   * UnsuccessfulResponseException#UnsuccessfulResponseException(String, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UnsuccessfulResponseException.<init>(String)",
    "void UnsuccessfulResponseException.<init>(String, Throwable)",
    "void UnsuccessfulResponseException.<init>(Throwable)"
  })
  public void testNewUnsuccessfulResponseException_whenAString_thenReturnMessageIsAString() {
    // Arrange
    Throwable arg1 = new Throwable();

    // Act
    UnsuccessfulResponseException actualUnsuccessfulResponseException =
        new UnsuccessfulResponseException(
            "\"Failed to retrieve data from Netflix server due to a timeout exception. Please check your network"
                + " connection and try again.\"",
            arg1);

    // Assert
    assertEquals(
        "\"Failed to retrieve data from Netflix server due to a timeout exception. Please check your network"
            + " connection and try again.\"",
        actualUnsuccessfulResponseException.getMessage());
    assertEquals(0, actualUnsuccessfulResponseException.getSuppressed().length);
    assertSame(arg1, actualUnsuccessfulResponseException.getCause());
  }
}
