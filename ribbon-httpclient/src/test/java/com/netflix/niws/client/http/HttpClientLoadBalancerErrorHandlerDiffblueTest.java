package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.ConnectionClosedException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HttpClientLoadBalancerErrorHandlerDiffblueTest {
  @InjectMocks private HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler;

  @Mock private List<Class<? extends Throwable>> list;

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
    assertEquals(expectedGetResult3, circuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult4 = SocketTimeoutException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(1));
    Class<ConnectionClosedException> expectedGetResult5 = ConnectionClosedException.class;
    assertEquals(expectedGetResult5, circuitRelatedExceptions.get(3));
    Class<ConnectionClosedException> expectedGetResult6 = ConnectionClosedException.class;
    assertEquals(expectedGetResult6, retriableExceptions.get(5));
    Class<ConnectTimeoutException> expectedGetResult7 = ConnectTimeoutException.class;
    assertEquals(expectedGetResult7, circuitRelatedExceptions.get(2));
    Class<ConnectTimeoutException> expectedGetResult8 = ConnectTimeoutException.class;
    assertEquals(expectedGetResult8, retriableExceptions.get(2));
    Class<ConnectionPoolTimeoutException> expectedGetResult9 = ConnectionPoolTimeoutException.class;
    assertEquals(expectedGetResult9, retriableExceptions.get(4));
    Class<HttpHostConnectException> expectedGetResult10 = HttpHostConnectException.class;
    assertEquals(expectedGetResult10, circuitRelatedExceptions.get(4));
    Class<HttpHostConnectException> expectedGetResult11 = HttpHostConnectException.class;
    assertEquals(expectedGetResult11, retriableExceptions.get(6));
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
    assertEquals(expectedGetResult3, circuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult4 = SocketTimeoutException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(1));
    Class<ConnectionClosedException> expectedGetResult5 = ConnectionClosedException.class;
    assertEquals(expectedGetResult5, circuitRelatedExceptions.get(3));
    Class<ConnectionClosedException> expectedGetResult6 = ConnectionClosedException.class;
    assertEquals(expectedGetResult6, retriableExceptions.get(5));
    Class<ConnectTimeoutException> expectedGetResult7 = ConnectTimeoutException.class;
    assertEquals(expectedGetResult7, circuitRelatedExceptions.get(2));
    Class<ConnectTimeoutException> expectedGetResult8 = ConnectTimeoutException.class;
    assertEquals(expectedGetResult8, retriableExceptions.get(2));
    Class<ConnectionPoolTimeoutException> expectedGetResult9 = ConnectionPoolTimeoutException.class;
    assertEquals(expectedGetResult9, retriableExceptions.get(4));
    Class<HttpHostConnectException> expectedGetResult10 = HttpHostConnectException.class;
    assertEquals(expectedGetResult10, circuitRelatedExceptions.get(4));
    Class<HttpHostConnectException> expectedGetResult11 = HttpHostConnectException.class;
    assertEquals(expectedGetResult11, retriableExceptions.get(6));
  }

  /**
   * Test {@link HttpClientLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}.
   *
   * <ul>
   *   <li>Given {@code Throwable}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HttpClientLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HttpClientLoadBalancerErrorHandler.isCircuitTrippingException(Throwable)"
  })
  public void testIsCircuitTrippingException_givenJavaLangThrowable_thenReturnTrue() {
    // Arrange
    ArrayList<Class<? extends Throwable>> resultClassList = new ArrayList<>();
    Class<Throwable> forNameResult = Throwable.class;
    resultClassList.add(forNameResult);
    org.mockito.Mockito.<Iterator<Class<? extends Throwable>>>when(list.iterator())
        .thenReturn(resultClassList.iterator());

    // Act
    boolean actualIsCircuitTrippingExceptionResult =
        httpClientLoadBalancerErrorHandler.isCircuitTrippingException(new Throwable());

    // Assert
    verify(list).iterator();
    assertTrue(actualIsCircuitTrippingExceptionResult);
  }

  /**
   * Test {@link HttpClientLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HttpClientLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HttpClientLoadBalancerErrorHandler.isCircuitTrippingException(Throwable)"
  })
  public void testIsCircuitTrippingException_thenReturnFalse() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler();

    // Act and Assert
    assertFalse(httpClientLoadBalancerErrorHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Test {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   *
   * <ul>
   *   <li>Given {@link HttpClientLoadBalancerErrorHandler#HttpClientLoadBalancerErrorHandler()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HttpClientLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"
  })
  public void testIsRetriableException_givenHttpClientLoadBalancerErrorHandler_thenReturnFalse() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler();

    // Act and Assert
    assertFalse(httpClientLoadBalancerErrorHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HttpClientLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"
  })
  public void testIsRetriableException_thenReturnFalse() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler(1, 1, true);

    // Act and Assert
    assertFalse(httpClientLoadBalancerErrorHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   *
   * <ul>
   *   <li>When {@code false}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link HttpClientLoadBalancerErrorHandler#isRetriableException(Throwable,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HttpClientLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"
  })
  public void testIsRetriableException_whenFalse_thenReturnTrue() {
    // Arrange
    HttpClientLoadBalancerErrorHandler httpClientLoadBalancerErrorHandler =
        new HttpClientLoadBalancerErrorHandler(1, 1, true);

    // Act and Assert
    assertTrue(httpClientLoadBalancerErrorHandler.isRetriableException(new Throwable(), false));
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
}
