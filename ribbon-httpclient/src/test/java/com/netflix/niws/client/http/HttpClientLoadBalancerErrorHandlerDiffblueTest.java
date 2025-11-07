package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import org.apache.http.ConnectionClosedException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HttpClientLoadBalancerErrorHandlerDiffblueTest {
  /**
   * Test {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler()}.
   * <p>
   * Method under test: {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HttpClientLoadBalancerErrorHandler.<init>()"})
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
   * Test {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(int, int, boolean)}.
   * <p>
   * Method under test: {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(int, int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HttpClientLoadBalancerErrorHandler.<init>(int, int, boolean)"})
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
   * Test {@link HttpClientLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}.
   * <p>
   * Method under test: {@link HttpClientLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean HttpClientLoadBalancerErrorHandler.isCircuitTrippingException(Throwable)"})
  public void testIsCircuitTrippingException() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler();

    // Act and Assert
    assertFalse(httpClientLoadBalancerErrorHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Test {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   * <ul>
   *   <li>Given {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean HttpClientLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"})
  public void testIsRetriableException_givenHttpClientLoadBalancerErrorHandler_thenReturnFalse() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler();

    // Act and Assert
    assertFalse(httpClientLoadBalancerErrorHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean HttpClientLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"})
  public void testIsRetriableException_thenReturnFalse() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler(1, 1,
        true);

    // Act and Assert
    assertFalse(httpClientLoadBalancerErrorHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean HttpClientLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"})
  public void testIsRetriableException_whenFalse_thenReturnTrue() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler = new HttpClientLoadBalancerErrorHandler(1, 1,
        true);

    // Act and Assert
    assertTrue(httpClientLoadBalancerErrorHandler.isRetriableException(new Throwable(), false));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HttpClientLoadBalancerErrorHandler#getCircuitRelatedExceptions()}
   *   <li>{@link HttpClientLoadBalancerErrorHandler#getRetriableExceptions()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List HttpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions()",
      "List HttpClientLoadBalancerErrorHandler.getRetriableExceptions()"})
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
}
