package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import java.net.SocketException;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RequestSpecificRetryHandlerDiffblueTest {
  /**
   * Test {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean, RetryHandler, IClientConfig)}.
   * <ul>
   *   <li>Then return MaxRetriesOnNextServer is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean, RetryHandler, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RequestSpecificRetryHandler.<init>(boolean, boolean, RetryHandler, IClientConfig)"})
  public void testNewRequestSpecificRetryHandler_thenReturnMaxRetriesOnNextServerIsOne() {
    // Arrange
    IClientConfig requestConfig = Builder.newBuilder("Dr Jane Doe")
        .ignoreUserTokenInConnectionPoolForSecureClient(true)
        .build();

    // Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true,
        RetryHandler.DEFAULT, requestConfig);

    // Assert
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    List<Class<? extends Throwable>> resultClassList = actualRequestSpecificRetryHandler.connectionRelated;
    assertEquals(1, resultClassList.size());
    Class<SocketException> expectedGetResult = SocketException.class;
    assertEquals(expectedGetResult, resultClassList.get(0));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean, RetryHandler, IClientConfig)}.
   * <ul>
   *   <li>Then return MaxRetriesOnNextServer is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean, RetryHandler, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RequestSpecificRetryHandler.<init>(boolean, boolean, RetryHandler, IClientConfig)"})
  public void testNewRequestSpecificRetryHandler_thenReturnMaxRetriesOnNextServerIsZero() {
    // Arrange and Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true,
        RetryHandler.DEFAULT, null);

    // Assert
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> resultClassList = actualRequestSpecificRetryHandler.connectionRelated;
    assertEquals(1, resultClassList.size());
    Class<SocketException> expectedGetResult = SocketException.class;
    assertEquals(expectedGetResult, resultClassList.get(0));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean, RetryHandler, IClientConfig)}.
   * <ul>
   *   <li>When EmptyConfig.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean, RetryHandler, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RequestSpecificRetryHandler.<init>(boolean, boolean, RetryHandler, IClientConfig)"})
  public void testNewRequestSpecificRetryHandler_whenEmptyConfig() {
    // Arrange and Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true,
        RetryHandler.DEFAULT, DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> resultClassList = actualRequestSpecificRetryHandler.connectionRelated;
    assertEquals(1, resultClassList.size());
    Class<SocketException> expectedGetResult = SocketException.class;
    assertEquals(expectedGetResult, resultClassList.get(0));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RequestSpecificRetryHandler.<init>(boolean, boolean)"})
  public void testNewRequestSpecificRetryHandler_whenFalse() {
    // Arrange and Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler = new RequestSpecificRetryHandler(false, true);

    // Assert
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> resultClassList = actualRequestSpecificRetryHandler.connectionRelated;
    assertEquals(1, resultClassList.size());
    Class<SocketException> expectedGetResult = SocketException.class;
    assertEquals(expectedGetResult, resultClassList.get(0));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean)}.
   * <ul>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RequestSpecificRetryHandler.<init>(boolean, boolean)"})
  public void testNewRequestSpecificRetryHandler_whenTrue() {
    // Arrange and Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true);

    // Assert
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> resultClassList = actualRequestSpecificRetryHandler.connectionRelated;
    assertEquals(1, resultClassList.size());
    Class<SocketException> expectedGetResult = SocketException.class;
    assertEquals(expectedGetResult, resultClassList.get(0));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#isConnectionException(Throwable)}.
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#isConnectionException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RequestSpecificRetryHandler.isConnectionException(Throwable)"})
  public void testIsConnectionException() {
    // Arrange
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isConnectionException(new Throwable()));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#isRetriableException(Throwable, boolean)}.
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RequestSpecificRetryHandler.isRetriableException(Throwable, boolean)"})
  public void testIsRetriableException() {
    // Arrange
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, false);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#isRetriableException(Throwable, boolean)}.
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RequestSpecificRetryHandler.isRetriableException(Throwable, boolean)"})
  public void testIsRetriableException2() {
    // Arrange
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(false, false);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#isRetriableException(Throwable, boolean)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RequestSpecificRetryHandler.isRetriableException(Throwable, boolean)"})
  public void testIsRetriableException_thenReturnTrue() {
    // Arrange
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true);

    // Act and Assert
    assertTrue(requestSpecificRetryHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#isCircuitTrippingException(Throwable)}.
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RequestSpecificRetryHandler.isCircuitTrippingException(Throwable)"})
  public void testIsCircuitTrippingException() {
    // Arrange
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#isCircuitTrippingException(Throwable)}.
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RequestSpecificRetryHandler.isCircuitTrippingException(Throwable)"})
  public void testIsCircuitTrippingException2() {
    // Arrange
    RequestSpecificRetryHandler baseRetryHandler = new RequestSpecificRetryHandler(true, true);

    IClientConfig requestConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true,
        baseRetryHandler, requestConfig);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#getMaxRetriesOnSameServer()}.
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#getMaxRetriesOnSameServer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int RequestSpecificRetryHandler.getMaxRetriesOnSameServer()"})
  public void testGetMaxRetriesOnSameServer() {
    // Arrange, Act and Assert
    assertEquals(0, (new RequestSpecificRetryHandler(true, true)).getMaxRetriesOnSameServer());
  }

  /**
   * Test {@link RequestSpecificRetryHandler#getMaxRetriesOnSameServer()}.
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#getMaxRetriesOnSameServer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int RequestSpecificRetryHandler.getMaxRetriesOnSameServer()"})
  public void testGetMaxRetriesOnSameServer2() {
    // Arrange
    RequestSpecificRetryHandler baseRetryHandler = new RequestSpecificRetryHandler(true, true);

    IClientConfig requestConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act and Assert
    assertEquals(0,
        (new RequestSpecificRetryHandler(true, true, baseRetryHandler, requestConfig)).getMaxRetriesOnSameServer());
  }

  /**
   * Test {@link RequestSpecificRetryHandler#getMaxRetriesOnSameServer()}.
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#getMaxRetriesOnSameServer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int RequestSpecificRetryHandler.getMaxRetriesOnSameServer()"})
  public void testGetMaxRetriesOnSameServer3() {
    // Arrange
    RequestSpecificRetryHandler baseRetryHandler = new RequestSpecificRetryHandler(true, true);

    IClientConfig requestConfig = Builder.newBuilder("Dr Jane Doe")
        .ignoreUserTokenInConnectionPoolForSecureClient(true)
        .build();

    // Act and Assert
    assertEquals(0,
        (new RequestSpecificRetryHandler(true, true, baseRetryHandler, requestConfig)).getMaxRetriesOnSameServer());
  }

  /**
   * Test {@link RequestSpecificRetryHandler#getMaxRetriesOnNextServer()}.
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#getMaxRetriesOnNextServer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int RequestSpecificRetryHandler.getMaxRetriesOnNextServer()"})
  public void testGetMaxRetriesOnNextServer() {
    // Arrange
    RequestSpecificRetryHandler baseRetryHandler = new RequestSpecificRetryHandler(true, true);

    IClientConfig requestConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act and Assert
    assertEquals(0,
        (new RequestSpecificRetryHandler(true, true, baseRetryHandler, requestConfig)).getMaxRetriesOnNextServer());
  }

  /**
   * Test {@link RequestSpecificRetryHandler#getMaxRetriesOnNextServer()}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#getMaxRetriesOnNextServer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int RequestSpecificRetryHandler.getMaxRetriesOnNextServer()"})
  public void testGetMaxRetriesOnNextServer_thenReturnOne() {
    // Arrange
    RequestSpecificRetryHandler baseRetryHandler = new RequestSpecificRetryHandler(true, true);

    IClientConfig requestConfig = Builder.newBuilder("Dr Jane Doe")
        .ignoreUserTokenInConnectionPoolForSecureClient(true)
        .build();

    // Act and Assert
    assertEquals(1,
        (new RequestSpecificRetryHandler(true, true, baseRetryHandler, requestConfig)).getMaxRetriesOnNextServer());
  }

  /**
   * Test {@link RequestSpecificRetryHandler#getMaxRetriesOnNextServer()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestSpecificRetryHandler#getMaxRetriesOnNextServer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int RequestSpecificRetryHandler.getMaxRetriesOnNextServer()"})
  public void testGetMaxRetriesOnNextServer_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new RequestSpecificRetryHandler(true, true)).getMaxRetriesOnNextServer());
  }
}
