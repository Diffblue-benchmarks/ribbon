package com.netflix.ribbon.transport.netty.http;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
