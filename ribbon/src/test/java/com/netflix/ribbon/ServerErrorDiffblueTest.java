package com.netflix.ribbon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ServerErrorDiffblueTest {
  /**
   * Method under test: {@link ServerError#ServerError(String)}
   */
  @Test
  public void testNewServerError() {
    // Arrange and Act
    ServerError actualServerError = new ServerError("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", actualServerError.getMessage());
    assertNull(actualServerError.getCause());
    assertEquals(0, actualServerError.getSuppressed().length);
  }

  /**
   * Method under test: {@link ServerError#ServerError(String, Throwable)}
   */
  @Test
  public void testNewServerError2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ServerError actualServerError = new ServerError("Not all who wander are lost", cause);

    // Assert
    assertEquals("Not all who wander are lost", actualServerError.getMessage());
    assertEquals(0, actualServerError.getSuppressed().length);
    assertSame(cause, actualServerError.getCause());
  }

  /**
   * Method under test: {@link ServerError#ServerError(Throwable)}
   */
  @Test
  public void testNewServerError3() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ServerError actualServerError = new ServerError(cause);

    // Assert
    assertEquals("java.lang.Throwable", actualServerError.getMessage());
    assertEquals(0, actualServerError.getSuppressed().length);
    assertSame(cause, actualServerError.getCause());
  }
}
