package com.netflix.client.ssl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ClientSslSocketFactoryExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link ClientSslSocketFactoryException#ClientSslSocketFactoryException(String, Throwable)}
   */
  @Test
  public void testNewClientSslSocketFactoryException() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ClientSslSocketFactoryException actualClientSslSocketFactoryException = new ClientSslSocketFactoryException(
        "An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualClientSslSocketFactoryException.getMessage());
    assertEquals(0, actualClientSslSocketFactoryException.getSuppressed().length);
    assertSame(cause, actualClientSslSocketFactoryException.getCause());
  }
}
