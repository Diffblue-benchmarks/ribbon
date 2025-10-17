package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DefaultLoadBalancerRetryHandlerDiffblueTest {
  /**
   * Test {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler()}.
   *
   * <p>Method under test: {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultLoadBalancerRetryHandler.<init>()"})
  public void testNewDefaultLoadBalancerRetryHandler() {
    // Arrange and Act
    DefaultLoadBalancerRetryHandler actualDefaultLoadBalancerRetryHandler =
        new DefaultLoadBalancerRetryHandler();

    // Assert
    assertEquals(0, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualDefaultLoadBalancerRetryHandler.getCircuitRelatedExceptions();
    assertEquals(2, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualDefaultLoadBalancerRetryHandler.getRetriableExceptions();
    assertEquals(2, retriableExceptions.size());
    assertFalse(actualDefaultLoadBalancerRetryHandler.retryEnabled);
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    assertEquals(expectedGetResult3, circuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult4 = SocketTimeoutException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(1));
  }

  /**
   * Test {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler(int, int,
   * boolean)}.
   *
   * <p>Method under test: {@link
   * DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler(int, int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultLoadBalancerRetryHandler.<init>(int, int, boolean)"})
  public void testNewDefaultLoadBalancerRetryHandler2() {
    // Arrange and Act
    DefaultLoadBalancerRetryHandler actualDefaultLoadBalancerRetryHandler =
        new DefaultLoadBalancerRetryHandler(1, 1, true);

    // Assert
    assertEquals(1, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualDefaultLoadBalancerRetryHandler.getCircuitRelatedExceptions();
    assertEquals(2, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualDefaultLoadBalancerRetryHandler.getRetriableExceptions();
    assertEquals(2, retriableExceptions.size());
    assertTrue(actualDefaultLoadBalancerRetryHandler.retryEnabled);
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    assertEquals(expectedGetResult3, circuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult4 = SocketTimeoutException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(1));
  }

  /**
   * Test {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler()}.
   *
   * <p>Method under test: {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultLoadBalancerRetryHandler.<init>()"})
  public void testNewDefaultLoadBalancerRetryHandler3() {
    // Arrange and Act
    DefaultLoadBalancerRetryHandler actualDefaultLoadBalancerRetryHandler =
        new DefaultLoadBalancerRetryHandler();

    // Assert
    assertEquals(0, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualDefaultLoadBalancerRetryHandler.getCircuitRelatedExceptions();
    assertEquals(2, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualDefaultLoadBalancerRetryHandler.getRetriableExceptions();
    assertEquals(2, retriableExceptions.size());
    assertFalse(actualDefaultLoadBalancerRetryHandler.retryEnabled);
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    assertEquals(expectedGetResult3, circuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult4 = SocketTimeoutException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(1));
  }

  /**
   * Test {@link DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler(int, int,
   * boolean)}.
   *
   * <p>Method under test: {@link
   * DefaultLoadBalancerRetryHandler#DefaultLoadBalancerRetryHandler(int, int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultLoadBalancerRetryHandler.<init>(int, int, boolean)"})
  public void testNewDefaultLoadBalancerRetryHandler4() {
    // Arrange and Act
    DefaultLoadBalancerRetryHandler actualDefaultLoadBalancerRetryHandler =
        new DefaultLoadBalancerRetryHandler(1, 1, true);

    // Assert
    assertEquals(1, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualDefaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualDefaultLoadBalancerRetryHandler.getCircuitRelatedExceptions();
    assertEquals(2, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualDefaultLoadBalancerRetryHandler.getRetriableExceptions();
    assertEquals(2, retriableExceptions.size());
    assertTrue(actualDefaultLoadBalancerRetryHandler.retryEnabled);
    Class<ConnectException> expectedGetResult = ConnectException.class;
    assertEquals(expectedGetResult, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult2 = SocketException.class;
    assertEquals(expectedGetResult2, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult3 = SocketTimeoutException.class;
    assertEquals(expectedGetResult3, circuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult4 = SocketTimeoutException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(1));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link DefaultLoadBalancerRetryHandler#getCircuitRelatedExceptions()}
   *   <li>{@link DefaultLoadBalancerRetryHandler#getMaxRetriesOnNextServer()}
   *   <li>{@link DefaultLoadBalancerRetryHandler#getMaxRetriesOnSameServer()}
   *   <li>{@link DefaultLoadBalancerRetryHandler#getRetriableExceptions()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DefaultLoadBalancerRetryHandler.getCircuitRelatedExceptions()",
    "int DefaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer()",
    "int DefaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer()",
    "List DefaultLoadBalancerRetryHandler.getRetriableExceptions()"
  })
  public void testGettersAndSetters() {
    // Arrange
    DefaultLoadBalancerRetryHandler defaultLoadBalancerRetryHandler =
        new DefaultLoadBalancerRetryHandler();

    // Act
    List<Class<? extends Throwable>> actualCircuitRelatedExceptions =
        defaultLoadBalancerRetryHandler.getCircuitRelatedExceptions();
    int actualMaxRetriesOnNextServer = defaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer();
    int actualMaxRetriesOnSameServer = defaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer();
    List<Class<? extends Throwable>> actualRetriableExceptions =
        defaultLoadBalancerRetryHandler.getRetriableExceptions();

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
    assertEquals(expectedGetResult3, actualCircuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult4 = SocketTimeoutException.class;
    assertEquals(expectedGetResult4, actualRetriableExceptions.get(1));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link DefaultLoadBalancerRetryHandler#getCircuitRelatedExceptions()}
   *   <li>{@link DefaultLoadBalancerRetryHandler#getMaxRetriesOnNextServer()}
   *   <li>{@link DefaultLoadBalancerRetryHandler#getMaxRetriesOnSameServer()}
   *   <li>{@link DefaultLoadBalancerRetryHandler#getRetriableExceptions()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DefaultLoadBalancerRetryHandler.getCircuitRelatedExceptions()",
    "int DefaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer()",
    "int DefaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer()",
    "List DefaultLoadBalancerRetryHandler.getRetriableExceptions()"
  })
  public void testGettersAndSetters2() {
    // Arrange
    DefaultLoadBalancerRetryHandler defaultLoadBalancerRetryHandler =
        new DefaultLoadBalancerRetryHandler();

    // Act
    List<Class<? extends Throwable>> actualCircuitRelatedExceptions =
        defaultLoadBalancerRetryHandler.getCircuitRelatedExceptions();
    int actualMaxRetriesOnNextServer = defaultLoadBalancerRetryHandler.getMaxRetriesOnNextServer();
    int actualMaxRetriesOnSameServer = defaultLoadBalancerRetryHandler.getMaxRetriesOnSameServer();
    List<Class<? extends Throwable>> actualRetriableExceptions =
        defaultLoadBalancerRetryHandler.getRetriableExceptions();

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
    assertEquals(expectedGetResult3, actualCircuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult4 = SocketTimeoutException.class;
    assertEquals(expectedGetResult4, actualRetriableExceptions.get(1));
  }
}
