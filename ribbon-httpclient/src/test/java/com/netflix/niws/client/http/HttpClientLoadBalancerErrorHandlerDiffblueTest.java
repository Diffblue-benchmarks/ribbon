package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import org.apache.http.ConnectionClosedException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.Test;

public class HttpClientLoadBalancerErrorHandlerDiffblueTest {
  /**
   * Method under test:
   * {@link HttpClientLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  public void testIsCircuitTrippingException() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler();

    // Act and Assert
    assertFalse(httpClientLoadBalancerErrorHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Method under test:
   * {@link HttpClientLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  public void testIsCircuitTrippingException2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler(
        clientConfig);

    // Act and Assert
    assertFalse(httpClientLoadBalancerErrorHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Method under test:
   * {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  public void testIsRetriableException() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler();

    // Act and Assert
    assertFalse(httpClientLoadBalancerErrorHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Method under test:
   * {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  public void testIsRetriableException2() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler(1, 1,
        true);

    // Act and Assert
    assertFalse(httpClientLoadBalancerErrorHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Method under test:
   * {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  public void testIsRetriableException3() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler(1, 1,
        true);

    // Act and Assert
    assertTrue(httpClientLoadBalancerErrorHandler.isRetriableException(new Throwable(), false));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link HttpClientLoadBalancerErrorHandler#getCircuitRelatedExceptions()}
   *   <li>{@link HttpClientLoadBalancerErrorHandler#getRetriableExceptions()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler();

    // Act
    List<Class<? extends Throwable>> actualCircuitRelatedExceptions = httpClientLoadBalancerErrorHandler
        .getCircuitRelatedExceptions();

    // Assert
    assertSame(httpClientLoadBalancerErrorHandler.circuitRelated, actualCircuitRelatedExceptions);
    assertSame(httpClientLoadBalancerErrorHandler.retriable,
        httpClientLoadBalancerErrorHandler.getRetriableExceptions());
  }

  /**
   * Method under test:
   * {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler()}
   */
  @Test
  public void testNewHttpClientLoadBalancerErrorHandler() {
    // Arrange and Act
    HttpClientLoadBalancerErrorHandler actualHttpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler();

    // Assert
    assertEquals(0, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualHttpClientLoadBalancerErrorHandler
        .getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualHttpClientLoadBalancerErrorHandler
        .getRetriableExceptions();
    assertEquals(7, retriableExceptions.size());
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult3, getResult);
    Class<ConnectionClosedException> expectedGetResult4 = ConnectionClosedException.class;
    Class<? extends Throwable> getResult2 = circuitRelatedExceptions.get(3);
    assertEquals(expectedGetResult4, getResult2);
    Class<ConnectTimeoutException> expectedGetResult5 = ConnectTimeoutException.class;
    Class<? extends Throwable> getResult3 = circuitRelatedExceptions.get(2);
    assertEquals(expectedGetResult5, getResult3);
    Class<ConnectionPoolTimeoutException> expectedGetResult6 = ConnectionPoolTimeoutException.class;
    assertEquals(expectedGetResult6, retriableExceptions.get(4));
    Class<HttpHostConnectException> expectedGetResult7 = HttpHostConnectException.class;
    Class<? extends Throwable> getResult4 = circuitRelatedExceptions.get(4);
    assertEquals(expectedGetResult7, getResult4);
    assertSame(getResult, retriableExceptions.get(1));
    assertSame(getResult3, retriableExceptions.get(2));
    assertSame(getResult2, retriableExceptions.get(5));
    assertSame(getResult4, retriableExceptions.get(6));
  }

  /**
   * Method under test:
   * {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(int, int, boolean)}
   */
  @Test
  public void testNewHttpClientLoadBalancerErrorHandler2() {
    // Arrange and Act
    HttpClientLoadBalancerErrorHandler actualHttpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler(
        1, 1, true);

    // Assert
    assertEquals(1, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualHttpClientLoadBalancerErrorHandler
        .getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualHttpClientLoadBalancerErrorHandler
        .getRetriableExceptions();
    assertEquals(7, retriableExceptions.size());
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult3, getResult);
    Class<ConnectionClosedException> expectedGetResult4 = ConnectionClosedException.class;
    Class<? extends Throwable> getResult2 = circuitRelatedExceptions.get(3);
    assertEquals(expectedGetResult4, getResult2);
    Class<ConnectTimeoutException> expectedGetResult5 = ConnectTimeoutException.class;
    Class<? extends Throwable> getResult3 = circuitRelatedExceptions.get(2);
    assertEquals(expectedGetResult5, getResult3);
    Class<ConnectionPoolTimeoutException> expectedGetResult6 = ConnectionPoolTimeoutException.class;
    assertEquals(expectedGetResult6, retriableExceptions.get(4));
    Class<HttpHostConnectException> expectedGetResult7 = HttpHostConnectException.class;
    Class<? extends Throwable> getResult4 = circuitRelatedExceptions.get(4);
    assertEquals(expectedGetResult7, getResult4);
    assertSame(getResult, retriableExceptions.get(1));
    assertSame(getResult3, retriableExceptions.get(2));
    assertSame(getResult2, retriableExceptions.get(5));
    assertSame(getResult4, retriableExceptions.get(6));
  }

  /**
   * Method under test:
   * {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(IClientConfig)}
   */
  @Test
  public void testNewHttpClientLoadBalancerErrorHandler3() {
    // Arrange and Act
    HttpClientLoadBalancerErrorHandler actualHttpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler(
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertEquals(0, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualHttpClientLoadBalancerErrorHandler
        .getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualHttpClientLoadBalancerErrorHandler
        .getRetriableExceptions();
    assertEquals(7, retriableExceptions.size());
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult3, getResult);
    Class<ConnectionClosedException> expectedGetResult4 = ConnectionClosedException.class;
    Class<? extends Throwable> getResult2 = circuitRelatedExceptions.get(3);
    assertEquals(expectedGetResult4, getResult2);
    Class<ConnectTimeoutException> expectedGetResult5 = ConnectTimeoutException.class;
    Class<? extends Throwable> getResult3 = circuitRelatedExceptions.get(2);
    assertEquals(expectedGetResult5, getResult3);
    Class<ConnectionPoolTimeoutException> expectedGetResult6 = ConnectionPoolTimeoutException.class;
    assertEquals(expectedGetResult6, retriableExceptions.get(4));
    Class<HttpHostConnectException> expectedGetResult7 = HttpHostConnectException.class;
    Class<? extends Throwable> getResult4 = circuitRelatedExceptions.get(4);
    assertEquals(expectedGetResult7, getResult4);
    assertSame(getResult, retriableExceptions.get(1));
    assertSame(getResult3, retriableExceptions.get(2));
    assertSame(getResult2, retriableExceptions.get(5));
    assertSame(getResult4, retriableExceptions.get(6));
  }

  /**
   * Method under test:
   * {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(IClientConfig)}
   */
  @Test
  public void testNewHttpClientLoadBalancerErrorHandler4() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    HttpClientLoadBalancerErrorHandler actualHttpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler(
        clientConfig);

    // Assert
    assertEquals(0, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualHttpClientLoadBalancerErrorHandler
        .getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualHttpClientLoadBalancerErrorHandler
        .getRetriableExceptions();
    assertEquals(7, retriableExceptions.size());
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult3, getResult);
    Class<ConnectionClosedException> expectedGetResult4 = ConnectionClosedException.class;
    Class<? extends Throwable> getResult2 = circuitRelatedExceptions.get(3);
    assertEquals(expectedGetResult4, getResult2);
    Class<ConnectTimeoutException> expectedGetResult5 = ConnectTimeoutException.class;
    Class<? extends Throwable> getResult3 = circuitRelatedExceptions.get(2);
    assertEquals(expectedGetResult5, getResult3);
    Class<ConnectionPoolTimeoutException> expectedGetResult6 = ConnectionPoolTimeoutException.class;
    assertEquals(expectedGetResult6, retriableExceptions.get(4));
    Class<HttpHostConnectException> expectedGetResult7 = HttpHostConnectException.class;
    Class<? extends Throwable> getResult4 = circuitRelatedExceptions.get(4);
    assertEquals(expectedGetResult7, getResult4);
    assertSame(getResult, retriableExceptions.get(1));
    assertSame(getResult3, retriableExceptions.get(2));
    assertSame(getResult2, retriableExceptions.get(5));
    assertSame(getResult4, retriableExceptions.get(6));
  }
}
