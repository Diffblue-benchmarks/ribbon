package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import org.junit.Test;

public class DefaultLoadBalancerRetryHandlerDiffblueTest {
  /**
   * Method under test:
   * {@link DefaultLoadBalancerRetryHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  public void testIsRetriableException() {
    // Arrange
    DefaultLoadBalancerRetryHandler defaultLoadBalancerRetryHandler = new DefaultLoadBalancerRetryHandler();

    // Act and Assert
    assertFalse(defaultLoadBalancerRetryHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Method under test:
   * {@link DefaultLoadBalancerRetryHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  public void testIsRetriableException2() {
    // Arrange
    DefaultLoadBalancerRetryHandler defaultLoadBalancerRetryHandler = new DefaultLoadBalancerRetryHandler(1, 1, true);

    // Act and Assert
    assertFalse(defaultLoadBalancerRetryHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Method under test:
   * {@link DefaultLoadBalancerRetryHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  public void testIsRetriableException3() {
    // Arrange
    DefaultLoadBalancerRetryHandler defaultLoadBalancerRetryHandler = new DefaultLoadBalancerRetryHandler(1, 1, true);

    // Act and Assert
    assertTrue(defaultLoadBalancerRetryHandler.isRetriableException(new Throwable(), false));
  }

  /**
   * Method under test:
   * {@link DefaultLoadBalancerRetryHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  public void testIsCircuitTrippingException() {
    // Arrange
    DefaultLoadBalancerRetryHandler defaultLoadBalancerRetryHandler = new DefaultLoadBalancerRetryHandler();

    // Act and Assert
    assertFalse(defaultLoadBalancerRetryHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Method under test:
   * {@link DefaultLoadBalancerRetryHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  public void testIsCircuitTrippingException2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    DefaultLoadBalancerRetryHandler defaultLoadBalancerRetryHandler = new DefaultLoadBalancerRetryHandler(clientConfig);

    // Act and Assert
    assertFalse(defaultLoadBalancerRetryHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DefaultLoadBalancerRetryHandler#getCircuitRelatedExceptions()}
   *   <li>{@link DefaultLoadBalancerRetryHandler#getMaxRetriesOnNextServer()}
   *   <li>{@link DefaultLoadBalancerRetryHandler#getMaxRetriesOnSameServer()}
   *   <li>{@link DefaultLoadBalancerRetryHandler#getRetriableExceptions()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    DefaultLoadBalancerRetryHandler defaultLoadBalancerRetryHandler = new DefaultLoadBalancerRetryHandler();

    // Act
    List<Class<? extends Throwable>> actualCircuitRelatedExceptions = defaultLoadBalancerRetryHandler
        .getCircuitRelatedExceptions();
    int actualMaxRetriesOnNextServer = defaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer();
    int actualMaxRetriesOnSameServer = defaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer();
    List<Class<? extends Throwable>> actualRetriableExceptions = defaultLoadBalancerRetryHandler
        .getRetriableExceptions();

    // Assert
    assertEquals(0, actualMaxRetriesOnNextServer);
    assertEquals(0, actualMaxRetriesOnSameServer);
    assertEquals(2, actualCircuitRelatedExceptions.size());
    assertEquals(2, actualRetriableExceptions.size());
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, actualRetriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, actualCircuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult = actualCircuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult3, getResult);
    assertSame(getResult, actualRetriableExceptions.get(1));
  }

  /**
   * Method under test:
   * {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler()}
   */
  @Test
  public void testNewDefaultLoadBalancerRetryHandler() {
    // Arrange and Act
    DefaultLoadBalancerRetryHandler actualDefaultLoadBalancerRetryHandler = new DefaultLoadBalancerRetryHandler();

    // Assert
    assertEquals(0, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualDefaultLoadBalancerRetryHandler
        .getCircuitRelatedExceptions();
    assertEquals(2, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualDefaultLoadBalancerRetryHandler
        .getRetriableExceptions();
    assertEquals(2, retriableExceptions.size());
    assertFalse(actualDefaultLoadBalancerRetryHandler.retryEnabled);
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult3, getResult);
    assertSame(getResult, retriableExceptions.get(1));
  }

  /**
   * Method under test:
   * {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler(int, int, boolean)}
   */
  @Test
  public void testNewDefaultLoadBalancerRetryHandler2() {
    // Arrange and Act
    DefaultLoadBalancerRetryHandler actualDefaultLoadBalancerRetryHandler = new DefaultLoadBalancerRetryHandler(1, 1,
        true);

    // Assert
    assertEquals(1, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualDefaultLoadBalancerRetryHandler
        .getCircuitRelatedExceptions();
    assertEquals(2, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualDefaultLoadBalancerRetryHandler
        .getRetriableExceptions();
    assertEquals(2, retriableExceptions.size());
    assertTrue(actualDefaultLoadBalancerRetryHandler.retryEnabled);
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult3, getResult);
    assertSame(getResult, retriableExceptions.get(1));
  }

  /**
   * Method under test:
   * {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler(IClientConfig)}
   */
  @Test
  public void testNewDefaultLoadBalancerRetryHandler3() {
    // Arrange and Act
    DefaultLoadBalancerRetryHandler actualDefaultLoadBalancerRetryHandler = new DefaultLoadBalancerRetryHandler(
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertEquals(0, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualDefaultLoadBalancerRetryHandler
        .getCircuitRelatedExceptions();
    assertEquals(2, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualDefaultLoadBalancerRetryHandler
        .getRetriableExceptions();
    assertEquals(2, retriableExceptions.size());
    assertFalse(actualDefaultLoadBalancerRetryHandler.retryEnabled);
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult3, getResult);
    assertSame(getResult, retriableExceptions.get(1));
  }

  /**
   * Method under test:
   * {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler(IClientConfig)}
   */
  @Test
  public void testNewDefaultLoadBalancerRetryHandler4() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    DefaultLoadBalancerRetryHandler actualDefaultLoadBalancerRetryHandler = new DefaultLoadBalancerRetryHandler(
        clientConfig);

    // Assert
    assertEquals(0, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualDefaultLoadBalancerRetryHandler
        .getCircuitRelatedExceptions();
    assertEquals(2, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualDefaultLoadBalancerRetryHandler
        .getRetriableExceptions();
    assertEquals(2, retriableExceptions.size());
    assertFalse(actualDefaultLoadBalancerRetryHandler.retryEnabled);
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult3, getResult);
    assertSame(getResult, retriableExceptions.get(1));
  }
}
