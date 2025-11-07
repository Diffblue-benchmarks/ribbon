package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.ClientException.ErrorType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClientExceptionDiffblueTest {
  /**
   * Test ErrorType {@link ErrorType#getName(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code CONFIGURATION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorType#getName(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ErrorType.getName(int)"})
  public void testErrorTypeGetName_whenOne_thenReturnConfiguration() {
    // Arrange, Act and Assert
    assertEquals("CONFIGURATION", ErrorType.getName(1));
  }

  /**
   * Test {@link ClientException#ClientException(Throwable)}.
   * <p>
   * Method under test: {@link ClientException#ClientException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(Throwable)"})
  public void testNewClientException() {
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
    assertEquals(ErrorType.GENERAL, actualClientException.getErrorType());
    assertSame(chainedException, actualClientException.getCause());
  }

  /**
   * Test {@link ClientException#ClientException(int, String)}.
   * <ul>
   *   <li>Then return ErrorMessage is {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int, String)"})
  public void testNewClientException_thenReturnErrorMessageIsAnErrorOccurred() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(-1, "An error occurred");

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
    assertEquals("{no message: -1}", actualClientException.getInternalMessage());
    assertEquals(-1, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#ClientException(int, String, Throwable)}.
   * <ul>
   *   <li>Then return ErrorMessage is {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int, String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int, String, Throwable)"})
  public void testNewClientException_thenReturnErrorMessageIsAnErrorOccurred2() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(-1, "An error occurred", new Throwable());

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
    assertEquals("{no message: -1}", actualClientException.getInternalMessage());
    assertEquals(-1, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#ClientException(ErrorType, String)}.
   * <ul>
   *   <li>Then return ErrorMessage is {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(ErrorType, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(ErrorType, String)"})
  public void testNewClientException_thenReturnErrorMessageIsAnErrorOccurred3() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ErrorType.GENERAL, "An error occurred");

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Test {@link ClientException#ClientException(String)}.
   * <ul>
   *   <li>Then return ErrorMessage is {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(String)"})
  public void testNewClientException_thenReturnErrorMessageIsAnErrorOccurred4() {
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
    assertEquals(ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Test {@link ClientException#ClientException(String, Throwable)}.
   * <ul>
   *   <li>Then return ErrorMessage is {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(String, Throwable)"})
  public void testNewClientException_thenReturnErrorMessageIsAnErrorOccurred5() {
    // Arrange and Act
    ClientException actualClientException = new ClientException("An error occurred", new Throwable());

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
  }

  /**
   * Test {@link ClientException#ClientException(ErrorType)}.
   * <ul>
   *   <li>Then return LocalizedMessage is {@code , code=1->CONFIGURATION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(ErrorType)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(ErrorType)"})
  public void testNewClientException_thenReturnLocalizedMessageIsCode1Configuration() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ErrorType.CONFIGURATION);

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertEquals(1, actualClientException.getErrorCode());
    assertEquals(ErrorType.CONFIGURATION, actualClientException.getErrorType());
  }

  /**
   * Test {@link ClientException#ClientException(ErrorType, String)}.
   * <ul>
   *   <li>Then return LocalizedMessage is {@code , code=1->CONFIGURATION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(ErrorType, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(ErrorType, String)"})
  public void testNewClientException_thenReturnLocalizedMessageIsCode1Configuration2() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ErrorType.CONFIGURATION, null);

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertEquals(1, actualClientException.getErrorCode());
    assertEquals(ErrorType.CONFIGURATION, actualClientException.getErrorType());
  }

  /**
   * Test {@link ClientException#ClientException(ErrorType, String, Throwable)}.
   * <ul>
   *   <li>Then return LocalizedMessage is {@code , code=1->CONFIGURATION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(ErrorType, String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(ErrorType, String, Throwable)"})
  public void testNewClientException_thenReturnLocalizedMessageIsCode1Configuration3() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ErrorType.CONFIGURATION, null, new Throwable());

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertEquals(1, actualClientException.getErrorCode());
    assertEquals(ErrorType.CONFIGURATION, actualClientException.getErrorType());
  }

  /**
   * Test {@link ClientException#ClientException(int)}.
   * <ul>
   *   <li>Then return LocalizedMessage is {@code , code=23->UNKNOWN ERROR CODE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int)"})
  public void testNewClientException_thenReturnLocalizedMessageIsCode23UnknownErrorCode() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(23);

    // Assert
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getLocalizedMessage());
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getMessage());
    assertEquals("{no message: 23}", actualClientException.getInternalMessage());
    assertEquals(23, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#ClientException(int, String)}.
   * <ul>
   *   <li>Then return LocalizedMessage is {@code , code=23->UNKNOWN ERROR CODE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int, String)"})
  public void testNewClientException_thenReturnLocalizedMessageIsCode23UnknownErrorCode2() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(23, null);

    // Assert
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getLocalizedMessage());
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getMessage());
    assertEquals("{no message: 23}", actualClientException.getInternalMessage());
    assertEquals(23, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#ClientException(int, String, Throwable)}.
   * <ul>
   *   <li>Then return LocalizedMessage is {@code , code=23->UNKNOWN ERROR CODE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int, String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int, String, Throwable)"})
  public void testNewClientException_thenReturnLocalizedMessageIsCode23UnknownErrorCode3() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(23, null, new Throwable());

    // Assert
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getLocalizedMessage());
    assertEquals(", code=23->UNKNOWN ERROR CODE", actualClientException.getMessage());
    assertEquals("{no message: 23}", actualClientException.getInternalMessage());
    assertEquals(23, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#ClientException(ErrorType, String, Throwable)}.
   * <ul>
   *   <li>When {@code GENERAL}.</li>
   *   <li>Then return ErrorMessage is {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(ErrorType, String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(ErrorType, String, Throwable)"})
  public void testNewClientException_whenGeneral_thenReturnErrorMessageIsAnErrorOccurred() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ErrorType.GENERAL, "An error occurred",
        new Throwable());

    // Assert
    assertEquals("An error occurred", actualClientException.getErrorMessage());
    assertEquals("An error occurred", actualClientException.getLocalizedMessage());
    assertEquals("An error occurred", actualClientException.getMessage());
  }

  /**
   * Test {@link ClientException#ClientException(ErrorType, String)}.
   * <ul>
   *   <li>When {@code GENERAL}.</li>
   *   <li>Then return ErrorMessage is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(ErrorType, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(ErrorType, String)"})
  public void testNewClientException_whenGeneral_thenReturnErrorMessageIsNull() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ErrorType.GENERAL, null);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Test {@link ClientException#ClientException(ErrorType)}.
   * <ul>
   *   <li>When {@code GENERAL}.</li>
   *   <li>Then return InternalMessage is {@code {no message: 0}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(ErrorType)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(ErrorType)"})
  public void testNewClientException_whenGeneral_thenReturnInternalMessageIsNoMessage0() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ErrorType.GENERAL);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Test {@link ClientException#ClientException(ErrorType, String, Throwable)}.
   * <ul>
   *   <li>When {@code GENERAL}.</li>
   *   <li>Then return InternalMessage is {@code {no message: 0}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(ErrorType, String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(ErrorType, String, Throwable)"})
  public void testNewClientException_whenGeneral_thenReturnInternalMessageIsNoMessage02() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(ErrorType.GENERAL, null, new Throwable());

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertEquals(0, actualClientException.getErrorCode());
    assertEquals(ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Test {@link ClientException#ClientException(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return ErrorMessage is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(String)"})
  public void testNewClientException_whenNull_thenReturnErrorMessageIsNull() {
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
    assertEquals(ErrorType.GENERAL, actualClientException.getErrorType());
  }

  /**
   * Test {@link ClientException#ClientException(String, Throwable)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return ErrorMessage is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(String, Throwable)"})
  public void testNewClientException_whenNull_thenReturnErrorMessageIsNull2() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(null, new Throwable());

    // Assert
    assertNull(actualClientException.getErrorMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
  }

  /**
   * Test {@link ClientException#ClientException(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return LocalizedMessage is {@code , code=1->CONFIGURATION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int)"})
  public void testNewClientException_whenOne_thenReturnLocalizedMessageIsCode1Configuration() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(1);

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertEquals(1, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#ClientException(int, String)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return LocalizedMessage is {@code , code=1->CONFIGURATION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int, String)"})
  public void testNewClientException_whenOne_thenReturnLocalizedMessageIsCode1Configuration2() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(1, null);

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertEquals(1, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#ClientException(int, String, Throwable)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return LocalizedMessage is {@code , code=1->CONFIGURATION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int, String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int, String, Throwable)"})
  public void testNewClientException_whenOne_thenReturnLocalizedMessageIsCode1Configuration3() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(1, null, new Throwable());

    // Assert
    assertEquals(", code=1->CONFIGURATION", actualClientException.getLocalizedMessage());
    assertEquals(", code=1->CONFIGURATION", actualClientException.getMessage());
    assertEquals("{no message: 1}", actualClientException.getInternalMessage());
    assertEquals(1, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#ClientException(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return InternalMessage is {@code {no message: 0}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int)"})
  public void testNewClientException_whenZero_thenReturnInternalMessageIsNoMessage0() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(0);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertEquals(0, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#ClientException(int, String)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return InternalMessage is {@code {no message: 0}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int, String)"})
  public void testNewClientException_whenZero_thenReturnInternalMessageIsNoMessage02() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(0, null);

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertEquals(0, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#ClientException(int, String, Throwable)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return InternalMessage is {@code {no message: 0}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#ClientException(int, String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientException.<init>(int, String, Throwable)"})
  public void testNewClientException_whenZero_thenReturnInternalMessageIsNoMessage03() {
    // Arrange and Act
    ClientException actualClientException = new ClientException(0, null, new Throwable());

    // Assert
    assertEquals("{no message: 0}", actualClientException.getInternalMessage());
    assertNull(actualClientException.getLocalizedMessage());
    assertNull(actualClientException.getMessage());
    assertEquals(0, actualClientException.getErrorCode());
  }

  /**
   * Test {@link ClientException#getInternalMessage()}.
   * <ul>
   *   <li>Then return {@code {no message: 1}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#getInternalMessage()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ClientException.getInternalMessage()"})
  public void testGetInternalMessage_thenReturnNoMessage1() {
    // Arrange, Act and Assert
    assertEquals("{no message: 1}", (new ClientException(1)).getInternalMessage());
  }

  /**
   * Test {@link ClientException#getErrorCodes(Class)}.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientException#getErrorCodes(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.HashMap ClientException.getErrorCodes(Class)"})
  public void testGetErrorCodes_whenJavaLangObject_thenReturnEmpty() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertTrue(ClientException.getErrorCodes(clazz).isEmpty());
  }
}
