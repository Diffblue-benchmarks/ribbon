package com.netflix.ribbon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class UnsuccessfulResponseExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link UnsuccessfulResponseException#UnsuccessfulResponseException(String)}
   */
  @Test
  public void testNewUnsuccessfulResponseException() {
    // Arrange and Act
    UnsuccessfulResponseException actualUnsuccessfulResponseException = new UnsuccessfulResponseException("Arg0");

    // Assert
    assertEquals("Arg0", actualUnsuccessfulResponseException.getMessage());
    assertNull(actualUnsuccessfulResponseException.getCause());
    assertEquals(0, actualUnsuccessfulResponseException.getSuppressed().length);
  }

  /**
   * Method under test:
   * {@link UnsuccessfulResponseException#UnsuccessfulResponseException(String, Throwable)}
   */
  @Test
  public void testNewUnsuccessfulResponseException2() {
    // Arrange
    Throwable arg1 = new Throwable();

    // Act
    UnsuccessfulResponseException actualUnsuccessfulResponseException = new UnsuccessfulResponseException("Arg0", arg1);

    // Assert
    assertEquals("Arg0", actualUnsuccessfulResponseException.getMessage());
    assertEquals(0, actualUnsuccessfulResponseException.getSuppressed().length);
    assertSame(arg1, actualUnsuccessfulResponseException.getCause());
  }

  /**
   * Method under test:
   * {@link UnsuccessfulResponseException#UnsuccessfulResponseException(Throwable)}
   */
  @Test
  public void testNewUnsuccessfulResponseException3() {
    // Arrange
    Throwable arg0 = new Throwable();

    // Act
    UnsuccessfulResponseException actualUnsuccessfulResponseException = new UnsuccessfulResponseException(arg0);

    // Assert
    assertEquals("java.lang.Throwable", actualUnsuccessfulResponseException.getMessage());
    assertEquals(0, actualUnsuccessfulResponseException.getSuppressed().length);
    assertSame(arg0, actualUnsuccessfulResponseException.getCause());
  }
}
