package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.net.SocketException;
import java.util.List;
import org.junit.Test;

public class RequestSpecificRetryHandlerDiffblueTest {
  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#isConnectionException(Throwable)}
   */
  @Test
  public void testIsConnectionException() {
    // Arrange
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isConnectionException(new Throwable()));
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#isConnectionException(Throwable)}
   */
  @Test
  public void testIsConnectionException2() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    requestConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true,
        RetryHandler.DEFAULT, requestConfig);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isConnectionException(new Throwable()));
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  public void testIsRetriableException() {
    // Arrange
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true);

    // Act and Assert
    assertTrue(requestSpecificRetryHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  public void testIsRetriableException2() {
    // Arrange
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, false);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  public void testIsRetriableException3() {
    // Arrange
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(false, false);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  public void testIsCircuitTrippingException() {
    // Arrange
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  public void testIsCircuitTrippingException2() {
    // Arrange
    RequestSpecificRetryHandler baseRetryHandler = new RequestSpecificRetryHandler(true, true);

    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true,
        baseRetryHandler, DefaultClientConfigImpl.getEmptyConfig());

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  public void testIsCircuitTrippingException3() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    requestConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    RequestSpecificRetryHandler requestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true,
        new RequestSpecificRetryHandler(true, true), requestConfig);

    // Act and Assert
    assertFalse(requestSpecificRetryHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#getMaxRetriesOnSameServer()}
   */
  @Test
  public void testGetMaxRetriesOnSameServer() {
    // Arrange, Act and Assert
    assertEquals(0, (new RequestSpecificRetryHandler(true, true)).getMaxRetriesOnSameServer());
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#getMaxRetriesOnSameServer()}
   */
  @Test
  public void testGetMaxRetriesOnSameServer2() {
    // Arrange
    RequestSpecificRetryHandler baseRetryHandler = new RequestSpecificRetryHandler(true, true);

    // Act and Assert
    assertEquals(0,
        (new RequestSpecificRetryHandler(true, true, baseRetryHandler, DefaultClientConfigImpl.getEmptyConfig()))
            .getMaxRetriesOnSameServer());
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#getMaxRetriesOnSameServer()}
   */
  @Test
  public void testGetMaxRetriesOnSameServer3() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    requestConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertEquals(0,
        (new RequestSpecificRetryHandler(true, true, new RequestSpecificRetryHandler(true, true), requestConfig))
            .getMaxRetriesOnSameServer());
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#getMaxRetriesOnNextServer()}
   */
  @Test
  public void testGetMaxRetriesOnNextServer() {
    // Arrange, Act and Assert
    assertEquals(0, (new RequestSpecificRetryHandler(true, true)).getMaxRetriesOnNextServer());
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#getMaxRetriesOnNextServer()}
   */
  @Test
  public void testGetMaxRetriesOnNextServer2() {
    // Arrange
    RequestSpecificRetryHandler baseRetryHandler = new RequestSpecificRetryHandler(true, true);

    // Act and Assert
    assertEquals(0,
        (new RequestSpecificRetryHandler(true, true, baseRetryHandler, DefaultClientConfigImpl.getEmptyConfig()))
            .getMaxRetriesOnNextServer());
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#getMaxRetriesOnNextServer()}
   */
  @Test
  public void testGetMaxRetriesOnNextServer3() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    requestConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertEquals(0,
        (new RequestSpecificRetryHandler(true, true, new RequestSpecificRetryHandler(true, true), requestConfig))
            .getMaxRetriesOnNextServer());
  }

  /**
   * Method under test:
   * {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean)}
   */
  @Test
  public void testNewRequestSpecificRetryHandler() {
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
   * Method under test:
   * {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean)}
   */
  @Test
  public void testNewRequestSpecificRetryHandler2() {
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
   * Method under test:
   * {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean, RetryHandler, IClientConfig)}
   */
  @Test
  public void testNewRequestSpecificRetryHandler3() {
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
   * Method under test:
   * {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean, RetryHandler, IClientConfig)}
   */
  @Test
  public void testNewRequestSpecificRetryHandler4() {
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
   * Method under test:
   * {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean, RetryHandler, IClientConfig)}
   */
  @Test
  public void testNewRequestSpecificRetryHandler5() {
    // Arrange
    DefaultClientConfigImpl requestConfig = DefaultClientConfigImpl.getEmptyConfig();
    requestConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler = new RequestSpecificRetryHandler(true, true,
        RetryHandler.DEFAULT, requestConfig);

    // Assert
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> resultClassList = actualRequestSpecificRetryHandler.connectionRelated;
    assertEquals(1, resultClassList.size());
    Class<SocketException> expectedGetResult = SocketException.class;
    assertEquals(expectedGetResult, resultClassList.get(0));
  }
}
