package com.netflix.ribbon.transport.netty.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.http.UnexpectedHttpResponseException;
import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import io.reactivex.netty.client.PoolExhaustedException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NettyHttpLoadBalancerErrorHandlerDiffblueTest {
  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler()}.
   *
   * <p>Method under test: {@link
   * NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void NettyHttpLoadBalancerErrorHandler.<init>()"})
  public void testNewNettyHttpLoadBalancerErrorHandler() {
    // Arrange and Act
    NettyHttpLoadBalancerErrorHandler actualNettyHttpLoadBalancerErrorHandler =
        new NettyHttpLoadBalancerErrorHandler();

    // Assert
    assertEquals(0, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualNettyHttpLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualNettyHttpLoadBalancerErrorHandler.getRetriableExceptions();
    assertEquals(5, retriableExceptions.size());
    Class<ConnectTimeoutException> expectedGetResult = ConnectTimeoutException.class;
    assertEquals(expectedGetResult, circuitRelatedExceptions.get(3));
    Class<ConnectTimeoutException> expectedGetResult2 = ConnectTimeoutException.class;
    assertEquals(expectedGetResult2, retriableExceptions.get(3));
    Class<ReadTimeoutException> expectedGetResult3 = ReadTimeoutException.class;
    assertEquals(expectedGetResult3, circuitRelatedExceptions.get(2));
    Class<ReadTimeoutException> expectedGetResult4 = ReadTimeoutException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(2));
    Class<PoolExhaustedException> expectedGetResult5 = PoolExhaustedException.class;
    assertEquals(expectedGetResult5, circuitRelatedExceptions.get(4));
    Class<PoolExhaustedException> expectedGetResult6 = PoolExhaustedException.class;
    assertEquals(expectedGetResult6, retriableExceptions.get(4));
    Class<ConnectException> expectedGetResult7 = ConnectException.class;
    assertEquals(expectedGetResult7, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult8 = SocketException.class;
    assertEquals(expectedGetResult8, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult9 = SocketTimeoutException.class;
    assertEquals(expectedGetResult9, circuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult10 = SocketTimeoutException.class;
    assertEquals(expectedGetResult10, retriableExceptions.get(1));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler(int, int,
   * boolean)}.
   *
   * <p>Method under test: {@link
   * NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler(int, int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void NettyHttpLoadBalancerErrorHandler.<init>(int, int, boolean)"})
  public void testNewNettyHttpLoadBalancerErrorHandler2() {
    // Arrange and Act
    NettyHttpLoadBalancerErrorHandler actualNettyHttpLoadBalancerErrorHandler =
        new NettyHttpLoadBalancerErrorHandler(1, 1, true);

    // Assert
    assertEquals(1, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualNettyHttpLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualNettyHttpLoadBalancerErrorHandler.getRetriableExceptions();
    assertEquals(5, retriableExceptions.size());
    Class<ConnectTimeoutException> expectedGetResult = ConnectTimeoutException.class;
    assertEquals(expectedGetResult, circuitRelatedExceptions.get(3));
    Class<ConnectTimeoutException> expectedGetResult2 = ConnectTimeoutException.class;
    assertEquals(expectedGetResult2, retriableExceptions.get(3));
    Class<ReadTimeoutException> expectedGetResult3 = ReadTimeoutException.class;
    assertEquals(expectedGetResult3, circuitRelatedExceptions.get(2));
    Class<ReadTimeoutException> expectedGetResult4 = ReadTimeoutException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(2));
    Class<PoolExhaustedException> expectedGetResult5 = PoolExhaustedException.class;
    assertEquals(expectedGetResult5, circuitRelatedExceptions.get(4));
    Class<PoolExhaustedException> expectedGetResult6 = PoolExhaustedException.class;
    assertEquals(expectedGetResult6, retriableExceptions.get(4));
    Class<ConnectException> expectedGetResult7 = ConnectException.class;
    assertEquals(expectedGetResult7, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult8 = SocketException.class;
    assertEquals(expectedGetResult8, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult9 = SocketTimeoutException.class;
    assertEquals(expectedGetResult9, circuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult10 = SocketTimeoutException.class;
    assertEquals(expectedGetResult10, retriableExceptions.get(1));
  }

  /**
   * Test {@link
   * NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler(IClientConfig)}.
   *
   * <ul>
   *   <li>Then return MaxRetriesOnSameServer is zero.
   * </ul>
   *
   * <p>Method under test: {@link
   * NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void NettyHttpLoadBalancerErrorHandler.<init>(IClientConfig)"})
  public void testNewNettyHttpLoadBalancerErrorHandler_thenReturnMaxRetriesOnSameServerIsZero() {
    // Arrange and Act
    NettyHttpLoadBalancerErrorHandler actualNettyHttpLoadBalancerErrorHandler =
        new NettyHttpLoadBalancerErrorHandler(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertEquals(0, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions =
        actualNettyHttpLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions =
        actualNettyHttpLoadBalancerErrorHandler.getRetriableExceptions();
    assertEquals(5, retriableExceptions.size());
    Class<ConnectTimeoutException> expectedGetResult = ConnectTimeoutException.class;
    assertEquals(expectedGetResult, circuitRelatedExceptions.get(3));
    Class<ConnectTimeoutException> expectedGetResult2 = ConnectTimeoutException.class;
    assertEquals(expectedGetResult2, retriableExceptions.get(3));
    Class<ReadTimeoutException> expectedGetResult3 = ReadTimeoutException.class;
    assertEquals(expectedGetResult3, circuitRelatedExceptions.get(2));
    Class<ReadTimeoutException> expectedGetResult4 = ReadTimeoutException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(2));
    Class<PoolExhaustedException> expectedGetResult5 = PoolExhaustedException.class;
    assertEquals(expectedGetResult5, circuitRelatedExceptions.get(4));
    Class<PoolExhaustedException> expectedGetResult6 = PoolExhaustedException.class;
    assertEquals(expectedGetResult6, retriableExceptions.get(4));
    Class<ConnectException> expectedGetResult7 = ConnectException.class;
    assertEquals(expectedGetResult7, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult8 = SocketException.class;
    assertEquals(expectedGetResult8, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult9 = SocketTimeoutException.class;
    assertEquals(expectedGetResult9, circuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult10 = SocketTimeoutException.class;
    assertEquals(expectedGetResult10, retriableExceptions.get(1));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}.
   *
   * <p>Method under test: {@link
   * NettyHttpLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NettyHttpLoadBalancerErrorHandler.isCircuitTrippingException(Throwable)"
  })
  public void testIsCircuitTrippingException() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler =
        new NettyHttpLoadBalancerErrorHandler();

    // Act
    boolean actualIsCircuitTrippingExceptionResult =
        nettyHttpLoadBalancerErrorHandler.isCircuitTrippingException(
            new UnexpectedHttpResponseException(1, "https://example.org/example"));

    // Assert
    assertFalse(actualIsCircuitTrippingExceptionResult);
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * NettyHttpLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NettyHttpLoadBalancerErrorHandler.isCircuitTrippingException(Throwable)"
  })
  public void testIsCircuitTrippingException_thenReturnTrue() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler =
        new NettyHttpLoadBalancerErrorHandler();

    // Act
    boolean actualIsCircuitTrippingExceptionResult =
        nettyHttpLoadBalancerErrorHandler.isCircuitTrippingException(
            new UnexpectedHttpResponseException(503, "https://example.org/example"));

    // Assert
    assertTrue(actualIsCircuitTrippingExceptionResult);
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * NettyHttpLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NettyHttpLoadBalancerErrorHandler.isCircuitTrippingException(Throwable)"
  })
  public void testIsCircuitTrippingException_whenThrowable_thenReturnFalse() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler =
        new NettyHttpLoadBalancerErrorHandler();

    // Act and Assert
    assertFalse(nettyHttpLoadBalancerErrorHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   *
   * <ul>
   *   <li>Given {@link NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NettyHttpLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"
  })
  public void testIsRetriableException_givenNettyHttpLoadBalancerErrorHandler_thenReturnFalse() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler =
        new NettyHttpLoadBalancerErrorHandler();

    // Act and Assert
    assertFalse(nettyHttpLoadBalancerErrorHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NettyHttpLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"
  })
  public void testIsRetriableException_thenReturnFalse() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler =
        new NettyHttpLoadBalancerErrorHandler(1, 1, true);

    // Act and Assert
    assertFalse(nettyHttpLoadBalancerErrorHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   *
   * <ul>
   *   <li>When {@code false}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean NettyHttpLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"
  })
  public void testIsRetriableException_whenFalse_thenReturnTrue() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler =
        new NettyHttpLoadBalancerErrorHandler(1, 1, true);

    // Act and Assert
    assertTrue(nettyHttpLoadBalancerErrorHandler.isRetriableException(new Throwable(), false));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link NettyHttpLoadBalancerErrorHandler#getCircuitRelatedExceptions()}
   *   <li>{@link NettyHttpLoadBalancerErrorHandler#getRetriableExceptions()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List NettyHttpLoadBalancerErrorHandler.getCircuitRelatedExceptions()",
    "List NettyHttpLoadBalancerErrorHandler.getRetriableExceptions()"
  })
  public void testGettersAndSetters() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler =
        new NettyHttpLoadBalancerErrorHandler();

    // Act
    List<Class<? extends Throwable>> actualCircuitRelatedExceptions =
        nettyHttpLoadBalancerErrorHandler.getCircuitRelatedExceptions();
    List<Class<? extends Throwable>> actualRetriableExceptions =
        nettyHttpLoadBalancerErrorHandler.getRetriableExceptions();

    // Assert
    assertEquals(5, actualCircuitRelatedExceptions.size());
    assertEquals(5, actualRetriableExceptions.size());
    Class<ConnectTimeoutException> expectedGetResult = ConnectTimeoutException.class;
    assertEquals(expectedGetResult, actualCircuitRelatedExceptions.get(3));
    Class<ConnectTimeoutException> expectedGetResult2 = ConnectTimeoutException.class;
    assertEquals(expectedGetResult2, actualRetriableExceptions.get(3));
    Class<ReadTimeoutException> expectedGetResult3 = ReadTimeoutException.class;
    assertEquals(expectedGetResult3, actualCircuitRelatedExceptions.get(2));
    Class<ReadTimeoutException> expectedGetResult4 = ReadTimeoutException.class;
    assertEquals(expectedGetResult4, actualRetriableExceptions.get(2));
    Class<PoolExhaustedException> expectedGetResult5 = PoolExhaustedException.class;
    assertEquals(expectedGetResult5, actualCircuitRelatedExceptions.get(4));
    Class<PoolExhaustedException> expectedGetResult6 = PoolExhaustedException.class;
    assertEquals(expectedGetResult6, actualRetriableExceptions.get(4));
    Class<ConnectException> expectedGetResult7 = ConnectException.class;
    assertEquals(expectedGetResult7, actualRetriableExceptions.get(0));
    Class<SocketException> expectedGetResult8 = SocketException.class;
    assertEquals(expectedGetResult8, actualCircuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult9 = SocketTimeoutException.class;
    assertEquals(expectedGetResult9, actualCircuitRelatedExceptions.get(1));
    Class<SocketTimeoutException> expectedGetResult10 = SocketTimeoutException.class;
    assertEquals(expectedGetResult10, actualRetriableExceptions.get(1));
  }
}
