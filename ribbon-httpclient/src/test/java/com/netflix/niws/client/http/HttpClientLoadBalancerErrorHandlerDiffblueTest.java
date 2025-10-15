package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.experimental.categories.Category;

public class HttpClientLoadBalancerErrorHandlerDiffblueTest {
  /**
   * Test {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler()}.
   *
   * <p>Method under test: {@link
   * HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HttpClientLoadBalancerErrorHandler.<init>()"})
  public void testNewHttpClientLoadBalancerErrorHandler() {
    // Arrange and Act
    HttpClientLoadBalancerErrorHandler actualHttpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler();

    // Assert
    assertEquals(0, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualHttpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualHttpClientLoadBalancerErrorHandler.getRetriableExceptions();
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
   * Test {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(int, int,
   * boolean)}.
   *
   * <p>Method under test: {@link
   * HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(int, int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HttpClientLoadBalancerErrorHandler.<init>(int, int, boolean)"})
  public void testNewHttpClientLoadBalancerErrorHandler2() {
    // Arrange and Act
    HttpClientLoadBalancerErrorHandler actualHttpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler(1, 1, true);

    // Assert
    assertEquals(1, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualHttpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualHttpClientLoadBalancerErrorHandler.getRetriableExceptions();
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
   * Test {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler()}.
   *
   * <p>Method under test: {@link
   * HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HttpClientLoadBalancerErrorHandler.<init>()"})
  public void testNewHttpClientLoadBalancerErrorHandler3() {
    // Arrange and Act
    HttpClientLoadBalancerErrorHandler actualHttpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler();

    // Assert
    assertEquals(0, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualHttpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualHttpClientLoadBalancerErrorHandler.getRetriableExceptions();
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
   * Test {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(int, int,
   * boolean)}.
   *
   * <p>Method under test: {@link
   * HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(int, int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HttpClientLoadBalancerErrorHandler.<init>(int, int, boolean)"})
  public void testNewHttpClientLoadBalancerErrorHandler4() {
    // Arrange and Act
    HttpClientLoadBalancerErrorHandler actualHttpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler(1, 1, true);

    // Assert
    assertEquals(1, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualHttpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualHttpClientLoadBalancerErrorHandler.getRetriableExceptions();
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
   * Test {@link
   * HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(IClientConfig)}.
   *
   * <ul>
   *   <li>Then return MaxRetriesOnSameServer is zero.
   * </ul>
   *
   * <p>Method under test: {@link
   * HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HttpClientLoadBalancerErrorHandler.<init>(IClientConfig)"})
  public void testNewHttpClientLoadBalancerErrorHandler_thenReturnMaxRetriesOnSameServerIsZero() {
    // Arrange and Act
    HttpClientLoadBalancerErrorHandler actualHttpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertEquals(0, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualHttpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualHttpClientLoadBalancerErrorHandler.getRetriableExceptions();
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
   * Test {@link
   * HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(IClientConfig)}.
   *
   * <ul>
   *   <li>Then return MaxRetriesOnSameServer is zero.
   * </ul>
   *
   * <p>Method under test: {@link
   * HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HttpClientLoadBalancerErrorHandler.<init>(IClientConfig)"})
  public void testNewHttpClientLoadBalancerErrorHandler_thenReturnMaxRetriesOnSameServerIsZero2() {
    // Arrange and Act
    HttpClientLoadBalancerErrorHandler actualHttpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertEquals(0, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualHttpClientLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualHttpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualHttpClientLoadBalancerErrorHandler.getRetriableExceptions();
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
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link HttpClientLoadBalancerErrorHandler#getCircuitRelatedExceptions()}
   *   <li>{@link HttpClientLoadBalancerErrorHandler#getRetriableExceptions()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HttpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions()",
    "List HttpClientLoadBalancerErrorHandler.getRetriableExceptions()"
  })
  public void testGettersAndSetters() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler();

    // Act
    List<Class<? extends Throwable>> actualCircuitRelatedExceptions =
        httpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    List<Class<? extends Throwable>> actualRetriableExceptions =
        httpClientLoadBalancerErrorHandler.getRetriableExceptions();

    // Assert
    assertSame(httpClientLoadBalancerErrorHandler.circuitRelated, actualCircuitRelatedExceptions);
    assertSame(httpClientLoadBalancerErrorHandler.retriable, actualRetriableExceptions);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link HttpClientLoadBalancerErrorHandler#getCircuitRelatedExceptions()}
   *   <li>{@link HttpClientLoadBalancerErrorHandler#getRetriableExceptions()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HttpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions()",
    "List HttpClientLoadBalancerErrorHandler.getRetriableExceptions()"
  })
  public void testGettersAndSetters2() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler();

    // Act
    List<Class<? extends Throwable>> actualCircuitRelatedExceptions =
        httpClientLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    List<Class<? extends Throwable>> actualRetriableExceptions =
        httpClientLoadBalancerErrorHandler.getRetriableExceptions();

    // Assert
    assertSame(httpClientLoadBalancerErrorHandler.circuitRelated, actualCircuitRelatedExceptions);
    assertSame(httpClientLoadBalancerErrorHandler.retriable, actualRetriableExceptions);
  }
}
