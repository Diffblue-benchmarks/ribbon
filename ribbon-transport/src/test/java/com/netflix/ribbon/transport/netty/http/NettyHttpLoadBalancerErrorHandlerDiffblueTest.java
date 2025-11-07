package com.netflix.ribbon.transport.netty.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
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
   * <p>
   * Method under test: {@link NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NettyHttpLoadBalancerErrorHandler.<init>()"})
  public void testNewNettyHttpLoadBalancerErrorHandler() {
    // Arrange and Act
    NettyHttpLoadBalancerErrorHandler actualNettyHttpLoadBalancerErrorHandler = new NettyHttpLoadBalancerErrorHandler();

    // Assert
    assertEquals(0, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualNettyHttpLoadBalancerErrorHandler
        .getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualNettyHttpLoadBalancerErrorHandler
        .getRetriableExceptions();
    assertEquals(5, retriableExceptions.size());
    Class<ConnectTimeoutException> expectedGetResult = ConnectTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(3);
    assertEquals(expectedGetResult, getResult);
    Class<ReadTimeoutException> expectedGetResult2 = ReadTimeoutException.class;
    Class<? extends Throwable> getResult2 = circuitRelatedExceptions.get(2);
    assertEquals(expectedGetResult2, getResult2);
    Class<PoolExhaustedException> expectedGetResult3 = PoolExhaustedException.class;
    Class<? extends Throwable> getResult3 = circuitRelatedExceptions.get(4);
    assertEquals(expectedGetResult3, getResult3);
    Class<ConnectException> expectedGetResult4 = ConnectException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult5 = SocketException.class;
    assertEquals(expectedGetResult5, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult6 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult4 = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult6, getResult4);
    assertSame(getResult4, retriableExceptions.get(1));
    assertSame(getResult2, retriableExceptions.get(2));
    assertSame(getResult, retriableExceptions.get(3));
    assertSame(getResult3, retriableExceptions.get(4));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler(int, int, boolean)}.
   * <p>
   * Method under test: {@link NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler(int, int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NettyHttpLoadBalancerErrorHandler.<init>(int, int, boolean)"})
  public void testNewNettyHttpLoadBalancerErrorHandler2() {
    // Arrange and Act
    NettyHttpLoadBalancerErrorHandler actualNettyHttpLoadBalancerErrorHandler = new NettyHttpLoadBalancerErrorHandler(1,
        1, true);

    // Assert
    assertEquals(1, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualNettyHttpLoadBalancerErrorHandler
        .getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualNettyHttpLoadBalancerErrorHandler
        .getRetriableExceptions();
    assertEquals(5, retriableExceptions.size());
    Class<ConnectTimeoutException> expectedGetResult = ConnectTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(3);
    assertEquals(expectedGetResult, getResult);
    Class<ReadTimeoutException> expectedGetResult2 = ReadTimeoutException.class;
    Class<? extends Throwable> getResult2 = circuitRelatedExceptions.get(2);
    assertEquals(expectedGetResult2, getResult2);
    Class<PoolExhaustedException> expectedGetResult3 = PoolExhaustedException.class;
    Class<? extends Throwable> getResult3 = circuitRelatedExceptions.get(4);
    assertEquals(expectedGetResult3, getResult3);
    Class<ConnectException> expectedGetResult4 = ConnectException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult5 = SocketException.class;
    assertEquals(expectedGetResult5, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult6 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult4 = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult6, getResult4);
    assertSame(getResult4, retriableExceptions.get(1));
    assertSame(getResult2, retriableExceptions.get(2));
    assertSame(getResult, retriableExceptions.get(3));
    assertSame(getResult3, retriableExceptions.get(4));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler(IClientConfig)}.
   * <ul>
   *   <li>Then return MaxRetriesOnSameServer is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler(IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NettyHttpLoadBalancerErrorHandler.<init>(IClientConfig)"})
  public void testNewNettyHttpLoadBalancerErrorHandler_thenReturnMaxRetriesOnSameServerIsZero() {
    // Arrange and Act
    NettyHttpLoadBalancerErrorHandler actualNettyHttpLoadBalancerErrorHandler = new NettyHttpLoadBalancerErrorHandler(
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertEquals(0, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualNettyHttpLoadBalancerErrorHandler.getMaxRetriesOnNextServer());
    List<Class<? extends Throwable>> circuitRelatedExceptions = actualNettyHttpLoadBalancerErrorHandler
        .getCircuitRelatedExceptions();
    assertEquals(5, circuitRelatedExceptions.size());
    List<Class<? extends Throwable>> retriableExceptions = actualNettyHttpLoadBalancerErrorHandler
        .getRetriableExceptions();
    assertEquals(5, retriableExceptions.size());
    Class<ConnectTimeoutException> expectedGetResult = ConnectTimeoutException.class;
    Class<? extends Throwable> getResult = circuitRelatedExceptions.get(3);
    assertEquals(expectedGetResult, getResult);
    Class<ReadTimeoutException> expectedGetResult2 = ReadTimeoutException.class;
    Class<? extends Throwable> getResult2 = circuitRelatedExceptions.get(2);
    assertEquals(expectedGetResult2, getResult2);
    Class<PoolExhaustedException> expectedGetResult3 = PoolExhaustedException.class;
    Class<? extends Throwable> getResult3 = circuitRelatedExceptions.get(4);
    assertEquals(expectedGetResult3, getResult3);
    Class<ConnectException> expectedGetResult4 = ConnectException.class;
    assertEquals(expectedGetResult4, retriableExceptions.get(0));
    Class<SocketException> expectedGetResult5 = SocketException.class;
    assertEquals(expectedGetResult5, circuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult6 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult4 = circuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult6, getResult4);
    assertSame(getResult4, retriableExceptions.get(1));
    assertSame(getResult2, retriableExceptions.get(2));
    assertSame(getResult, retriableExceptions.get(3));
    assertSame(getResult3, retriableExceptions.get(4));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}.
   * <p>
   * Method under test: {@link NettyHttpLoadBalancerErrorHandler#isCircuitTrippingException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean NettyHttpLoadBalancerErrorHandler.isCircuitTrippingException(Throwable)"})
  public void testIsCircuitTrippingException() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act and Assert
    assertFalse(nettyHttpLoadBalancerErrorHandler.isCircuitTrippingException(new Throwable()));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   * <ul>
   *   <li>Given {@link NettyHttpLoadBalancerErrorHandler#NettyHttpLoadBalancerErrorHandler()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean NettyHttpLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"})
  public void testIsRetriableException_givenNettyHttpLoadBalancerErrorHandler_thenReturnFalse() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act and Assert
    assertFalse(nettyHttpLoadBalancerErrorHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean NettyHttpLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"})
  public void testIsRetriableException_thenReturnFalse() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler = new NettyHttpLoadBalancerErrorHandler(1, 1,
        true);

    // Act and Assert
    assertFalse(nettyHttpLoadBalancerErrorHandler.isRetriableException(new Throwable(), true));
  }

  /**
   * Test {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NettyHttpLoadBalancerErrorHandler#isRetriableException(Throwable, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean NettyHttpLoadBalancerErrorHandler.isRetriableException(Throwable, boolean)"})
  public void testIsRetriableException_whenFalse_thenReturnTrue() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler = new NettyHttpLoadBalancerErrorHandler(1, 1,
        true);

    // Act and Assert
    assertTrue(nettyHttpLoadBalancerErrorHandler.isRetriableException(new Throwable(), false));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NettyHttpLoadBalancerErrorHandler#getCircuitRelatedExceptions()}
   *   <li>{@link NettyHttpLoadBalancerErrorHandler#getRetriableExceptions()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List NettyHttpLoadBalancerErrorHandler.getCircuitRelatedExceptions()",
      "List NettyHttpLoadBalancerErrorHandler.getRetriableExceptions()"})
  public void testGettersAndSetters() {
    // Arrange
    NettyHttpLoadBalancerErrorHandler nettyHttpLoadBalancerErrorHandler = new NettyHttpLoadBalancerErrorHandler();

    // Act
    List<Class<? extends Throwable>> actualCircuitRelatedExceptions = nettyHttpLoadBalancerErrorHandler
        .getCircuitRelatedExceptions();
    List<Class<? extends Throwable>> actualRetriableExceptions = nettyHttpLoadBalancerErrorHandler
        .getRetriableExceptions();

    // Assert
    assertEquals(5, actualCircuitRelatedExceptions.size());
    assertEquals(5, actualRetriableExceptions.size());
    Class<ConnectTimeoutException> expectedGetResult = ConnectTimeoutException.class;
    Class<? extends Throwable> getResult = actualCircuitRelatedExceptions.get(3);
    assertEquals(expectedGetResult, getResult);
    Class<ReadTimeoutException> expectedGetResult2 = ReadTimeoutException.class;
    Class<? extends Throwable> getResult2 = actualCircuitRelatedExceptions.get(2);
    assertEquals(expectedGetResult2, getResult2);
    Class<PoolExhaustedException> expectedGetResult3 = PoolExhaustedException.class;
    Class<? extends Throwable> getResult3 = actualCircuitRelatedExceptions.get(4);
    assertEquals(expectedGetResult3, getResult3);
    Class<ConnectException> expectedGetResult4 = ConnectException.class;
    assertEquals(expectedGetResult4, actualRetriableExceptions.get(0));
    Class<SocketException> expectedGetResult5 = SocketException.class;
    assertEquals(expectedGetResult5, actualCircuitRelatedExceptions.get(0));
    Class<SocketTimeoutException> expectedGetResult6 = SocketTimeoutException.class;
    Class<? extends Throwable> getResult4 = actualCircuitRelatedExceptions.get(1);
    assertEquals(expectedGetResult6, getResult4);
    assertSame(getResult4, actualRetriableExceptions.get(1));
    assertSame(getResult2, actualRetriableExceptions.get(2));
    assertSame(getResult, actualRetriableExceptions.get(3));
    assertSame(getResult3, actualRetriableExceptions.get(4));
  }
}
