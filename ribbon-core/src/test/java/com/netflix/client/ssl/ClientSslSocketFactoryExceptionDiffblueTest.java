package com.netflix.client.ssl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClientSslSocketFactoryExceptionDiffblueTest {
  /**
   * Test {@link ClientSslSocketFactoryException#ClientSslSocketFactoryException(String, Throwable)}.
   * <p>
   * Method under test: {@link ClientSslSocketFactoryException#ClientSslSocketFactoryException(String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientSslSocketFactoryException.<init>(String, Throwable)"})
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
