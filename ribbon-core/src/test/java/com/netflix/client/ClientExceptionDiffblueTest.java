package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ClientExceptionDiffblueTest {
  /**
   * Method under test: {@link ClientException.ErrorType#getName(int)}
   */
  @Test
  public void testErrorTypeGetName() {
    // Arrange, Act and Assert
    assertEquals("CONFIGURATION", ClientException.ErrorType.getName(1));
  }

  /**
   * Method under test: {@link ClientException#getInternalMessage()}
   */
  @Test
  public void testGetInternalMessage() {
    // Arrange, Act and Assert
    assertEquals("{no message: 1}", (new ClientException(1)).getInternalMessage());
  }

  /**
   * Method under test: {@link ClientException#getErrorCodes(Class)}
   */
  @Test
  public void testGetErrorCodes() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertTrue(ClientException.getErrorCodes(clazz).isEmpty());
  }

  /**
   * Method under test: {@link ClientException#ClientException(int)}
   */
  @Test
  public void testNewClientException() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(0);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test: {@link ClientException#ClientException(int)}
   */
  @Test
  public void testNewClientException2() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(1);

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(1, actualClientException.getErrorCode());
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test: {@link ClientException#ClientException(int)}
   */
  @Test
  public void testNewClientException3() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(23);

    // Assert
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getLocalizedMessage());
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getMessage());
    assertEquals("{no message: 23}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(23, actualClientException.getErrorCode());
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test: {@link ClientException#ClientException(int, String)}
   */
  @Test
  public void testNewClientException4() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(-1, "An error occurred");

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
    assertEquals("{no message: -1}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getCause());
    assertEquals(-1, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test: {@link ClientException#ClientException(int, String)}
   */
  @Test
  public void testNewClientException5() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(0, null);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test: {@link ClientException#ClientException(int, String)}
   */
  @Test
  public void testNewClientException6() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(1, null);

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(1, actualClientException.getErrorCode());
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test: {@link ClientException#ClientException(int, String)}
   */
  @Test
  public void testNewClientException7() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(23, null);

    // Assert
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getLocalizedMessage());
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getMessage());
    assertEquals("{no message: 23}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(23, actualClientException.getErrorCode());
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(int, String, Throwable)}
   */
  @Test
  public void testNewClientException8() {
    // Arrange
    Throwable chainedException = new Throwable();

    // Act
    ClientException actualClientException = new ClientException(-1, "An error occurred", chainedException);

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
    assertEquals("{no message: -1}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertEquals(-1, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(int, String, Throwable)}
   */
  @Test
  public void testNewClientException9() {
    // Arrange
    Throwable chainedException = new Throwable();

    // Act
    ClientException actualClientException = new ClientException(0, null, chainedException);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(int, String, Throwable)}
   */
  @Test
  public void testNewClientException10() {
    // Arrange
    Throwable chainedException = new Throwable();

    // Act
    ClientException actualClientException = new ClientException(1, null, chainedException);

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(1, actualClientException.getErrorCode());
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(int, String, Throwable)}
   */
  @Test
  public void testNewClientException11() {
    // Arrange
    Throwable chainedException = new Throwable();

    // Act
    ClientException actualClientException = new ClientException(23, null, chainedException);

    // Assert
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getLocalizedMessage());
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getMessage());
    assertEquals("{no message: 23}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(23, actualClientException.getErrorCode());
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(ClientException.ErrorType)}
   */
  @Test
  public void testNewClientException12() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ClientException.ErrorType.GENERAL);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(ClientException.ErrorType)}
   */
  @Test
  public void testNewClientException13() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ClientException.ErrorType.CONFIGURATION);

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(1, actualClientException.getErrorCode());
    assertEquals(ClientException.ErrorType.CONFIGURATION, actualClientException.getErrorType());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(ClientException.ErrorType, String)}
   */
  @Test
  public void testNewClientException14() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ClientException.ErrorType.GENERAL, "An error occurred");

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(ClientException.ErrorType, String)}
   */
  @Test
  public void testNewClientException15() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ClientException.ErrorType.GENERAL, null);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(ClientException.ErrorType, String)}
   */
  @Test
  public void testNewClientException16() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ClientException.ErrorType.CONFIGURATION, null);

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(1, actualClientException.getErrorCode());
    assertEquals(ClientException.ErrorType.CONFIGURATION, actualClientException.getErrorType());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(ClientException.ErrorType, String, Throwable)}
   */
  @Test
  public void testNewClientException17() {
    // Arrange
    Throwable chainedException = new Throwable();

    // Act
    ClientException actualClientException = new ClientException(ClientException.ErrorType.GENERAL, "An error occurred",
        chainedException);

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(ClientException.ErrorType, String, Throwable)}
   */
  @Test
  public void testNewClientException18() {
    // Arrange
    Throwable chainedException = new Throwable();

    // Act
    ClientException actualClientException = new ClientException(ClientException.ErrorType.GENERAL, null,
        chainedException);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }

  /**
   * Method under test:
   * {@link ClientException#ClientException(ClientException.ErrorType, String, Throwable)}
   */
  @Test
  public void testNewClientException19() {
    // Arrange
    Throwable chainedException = new Throwable();

    // Act
    ClientException actualClientException = new ClientException(ClientException.ErrorType.CONFIGURATION, null,
        chainedException);

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(1, actualClientException.getErrorCode());
    assertEquals(ClientException.ErrorType.CONFIGURATION, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }

  /**
   * Method under test: {@link ClientException#ClientException(String)}
   */
  @Test
  public void testNewClientException20() {
    // Arrange and Act
    ClientException actualClientException = new ClientException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test: {@link ClientException#ClientException(String)}
   */
  @Test
  public void testNewClientException21() {
    // Arrange and Act
    ClientException actualClientException = new ClientException((String) null);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertNull(actualClientException.getCause());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Method under test: {@link ClientException#ClientException(String, Throwable)}
   */
  @Test
  public void testNewClientException22() {
    // Arrange
    Throwable chainedException = new Throwable();

    // Act
    ClientException actualClientException = new ClientException("An error occurred", chainedException);

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }

  /**
   * Method under test: {@link ClientException#ClientException(String, Throwable)}
   */
  @Test
  public void testNewClientException23() {
    // Arrange
    Throwable chainedException = new Throwable();

    // Act
    ClientException actualClientException = new ClientException(null, chainedException);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }

  /**
   * Method under test: {@link ClientException#ClientException(Throwable)}
   */
  @Test
  public void testNewClientException24() {
    // Arrange
    Throwable chainedException = new Throwable();

    // Act
    ClientException actualClientException = new ClientException(chainedException);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorObject());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(0, actualClientException.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }
}
