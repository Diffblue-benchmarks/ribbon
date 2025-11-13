package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ServerStatsDiffblueTest {
  /**
   * Test {@link ServerStats#ServerStats()}.
   *
   * <p>Method under test: {@link ServerStats#ServerStats()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerStats.<init>()"})
  public void testNewServerStats() {
    // Arrange and Act
    ServerStats actualServerStats = new ServerStats();

    // Assert
    assertNull(actualServerStats.getServer());
    assertEquals(0, actualServerStats.getActiveRequestsCount());
    assertEquals(0, actualServerStats.getMonitoredActiveRequestsCount());
    assertEquals(0, actualServerStats.getOpenConnectionsCount());
    assertEquals(0, actualServerStats.getResponseTimePercentileNumValues());
    assertEquals(0, actualServerStats.getSuccessiveConnectionFailureCount());
    assertEquals(0.0d, actualServerStats.getResponseTime10thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime25thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime50thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime75thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime90thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime95thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime98thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime99point5thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime99thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTimeAvg(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTimeAvgRecent(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTimeMax(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTimeMin(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTimeStdDev(), 0.0);
    assertEquals(0L, actualServerStats.getFailureCount());
    assertEquals(0L, actualServerStats.getMeasuredRequestsCount());
    assertEquals(0L, actualServerStats.getResponseTimePercentileTimeMillis());
    assertEquals(0L, actualServerStats.getTotalRequestsCount());
    assertEquals(1000L, actualServerStats.getFailureCountSlidingWindowInterval());
    assertEquals(60000, actualServerStats.bufferSize);
    assertEquals(60000, actualServerStats.publishInterval);
  }

  /**
   * Test {@link ServerStats#ServerStats(LoadBalancerStats)}.
   *
   * <ul>
   *   <li>When {@link LoadBalancerStats#LoadBalancerStats()}.
   *   <li>Then return Server is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServerStats#ServerStats(LoadBalancerStats)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerStats.<init>(LoadBalancerStats)"})
  public void testNewServerStats_whenLoadBalancerStats_thenReturnServerIsNull() {
    // Arrange and Act
    ServerStats actualServerStats = new ServerStats(new LoadBalancerStats());

    // Assert
    assertNull(actualServerStats.getServer());
    assertEquals(0, actualServerStats.getActiveRequestsCount());
    assertEquals(0, actualServerStats.getMonitoredActiveRequestsCount());
    assertEquals(0, actualServerStats.getOpenConnectionsCount());
    assertEquals(0, actualServerStats.getResponseTimePercentileNumValues());
    assertEquals(0, actualServerStats.getSuccessiveConnectionFailureCount());
    assertEquals(0.0d, actualServerStats.getResponseTime10thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime25thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime50thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime75thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime90thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime95thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime98thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime99point5thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTime99thPercentile(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTimeAvg(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTimeAvgRecent(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTimeMax(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTimeMin(), 0.0);
    assertEquals(0.0d, actualServerStats.getResponseTimeStdDev(), 0.0);
    assertEquals(0L, actualServerStats.getFailureCount());
    assertEquals(0L, actualServerStats.getMeasuredRequestsCount());
    assertEquals(0L, actualServerStats.getResponseTimePercentileTimeMillis());
    assertEquals(0L, actualServerStats.getTotalRequestsCount());
    assertEquals(1000L, actualServerStats.getFailureCountSlidingWindowInterval());
    assertEquals(60000, actualServerStats.bufferSize);
    assertEquals(60000, actualServerStats.publishInterval);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ServerStats#setBufferSize(int)}
   *   <li>{@link ServerStats#setFailureCountSlidingWindowInterval(long)}
   *   <li>{@link ServerStats#setPublishInterval(int)}
   *   <li>{@link ServerStats#getFailureCountSlidingWindowInterval()}
   *   <li>{@link ServerStats#getServer()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long ServerStats.getFailureCountSlidingWindowInterval()",
    "com.netflix.loadbalancer.Server ServerStats.getServer()",
    "void ServerStats.setBufferSize(int)",
    "void ServerStats.setFailureCountSlidingWindowInterval(long)",
    "void ServerStats.setPublishInterval(int)"
  })
  public void testGettersAndSetters() {
    // Arrange
    ServerStats serverStats = new ServerStats();

    // Act
    serverStats.setBufferSize(3);
    serverStats.setFailureCountSlidingWindowInterval(1L);
    serverStats.setPublishInterval(42);
    long actualFailureCountSlidingWindowInterval =
        serverStats.getFailureCountSlidingWindowInterval();

    // Assert
    assertNull(serverStats.getServer());
    assertEquals(1L, actualFailureCountSlidingWindowInterval);
  }

  /**
   * Test {@link ServerStats#getFailureCount()}.
   *
   * <p>Method under test: {@link ServerStats#getFailureCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ServerStats.getFailureCount()"})
  public void testGetFailureCount() {
    // Arrange, Act and Assert
    assertEquals(0L, new ServerStats().getFailureCount());
  }

  /**
   * Test {@link ServerStats#noteResponseTime(double)}.
   *
   * <p>Method under test: {@link ServerStats#noteResponseTime(double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerStats.noteResponseTime(double)"})
  public void testNoteResponseTime() {
    // Arrange
    ServerStats serverStats = new ServerStats();

    // Act
    serverStats.noteResponseTime(10.0d);

    // Assert
    assertEquals(10.0d, serverStats.getResponseTimeAvg(), 0.0);
    assertEquals(10.0d, serverStats.getResponseTimeMax(), 0.0);
    assertEquals(10.0d, serverStats.getResponseTimeMin(), 0.0);
  }

  /**
   * Test {@link ServerStats#incrementNumRequests()}.
   *
   * <p>Method under test: {@link ServerStats#incrementNumRequests()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerStats.incrementNumRequests()"})
  public void testIncrementNumRequests() {
    // Arrange
    ServerStats serverStats = new ServerStats();

    // Act
    serverStats.incrementNumRequests();

    // Assert
    AtomicLong atomicLong = serverStats.totalRequests;
    assertEquals(1L, atomicLong.get());
    assertEquals(1L, atomicLong.getAndDecrement());
    assertEquals(0L, atomicLong.getAndIncrement());
    assertEquals(1L, serverStats.getTotalRequestsCount());
  }

  /**
   * Test {@link ServerStats#incrementActiveRequestsCount()}.
   *
   * <ul>
   *   <li>Then {@link ServerStats#ServerStats()} {@link ServerStats#activeRequestsCount} is one.
   * </ul>
   *
   * <p>Method under test: {@link ServerStats#incrementActiveRequestsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerStats.incrementActiveRequestsCount()"})
  public void testIncrementActiveRequestsCount_thenServerStatsActiveRequestsCountIsOne() {
    // Arrange
    ServerStats serverStats = new ServerStats();

    // Act
    serverStats.incrementActiveRequestsCount();

    // Assert
    AtomicInteger atomicInteger = serverStats.activeRequestsCount;
    assertEquals(1, atomicInteger.get());
    assertEquals(1, atomicInteger.getAndDecrement());
    assertEquals(0, atomicInteger.getAndIncrement());
    assertEquals(1, serverStats.getMonitoredActiveRequestsCount());
  }

  /**
   * Test {@link ServerStats#incrementActiveRequestsCount()}.
   *
   * <ul>
   *   <li>Then {@link ServerStats#ServerStats()} {@link ServerStats#activeRequestsCount} is two.
   * </ul>
   *
   * <p>Method under test: {@link ServerStats#incrementActiveRequestsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerStats.incrementActiveRequestsCount()"})
  public void testIncrementActiveRequestsCount_thenServerStatsActiveRequestsCountIsTwo() {
    // Arrange
    ServerStats serverStats = new ServerStats();
    serverStats.incrementActiveRequestsCount();

    // Act
    serverStats.incrementActiveRequestsCount();

    // Assert
    AtomicInteger atomicInteger = serverStats.activeRequestsCount;
    assertEquals(2, atomicInteger.get());
    assertEquals(2, atomicInteger.getAndDecrement());
    assertEquals(1, atomicInteger.getAndIncrement());
    assertEquals(2, serverStats.getMonitoredActiveRequestsCount());
  }

  /**
   * Test {@link ServerStats#incrementOpenConnectionsCount()}.
   *
   * <p>Method under test: {@link ServerStats#incrementOpenConnectionsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerStats.incrementOpenConnectionsCount()"})
  public void testIncrementOpenConnectionsCount() {
    // Arrange
    ServerStats serverStats = new ServerStats();

    // Act
    serverStats.incrementOpenConnectionsCount();

    // Assert
    AtomicInteger atomicInteger = serverStats.openConnectionsCount;
    assertEquals(1, atomicInteger.get());
    assertEquals(1, atomicInteger.getAndDecrement());
    assertEquals(0, atomicInteger.getAndIncrement());
    assertEquals(1, serverStats.getOpenConnectionsCount());
  }

  /**
   * Test {@link ServerStats#getActiveRequestsCount(long)} with {@code long}.
   *
   * <ul>
   *   <li>Given {@link ServerStats#ServerStats()}.
   *   <li>When three.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link ServerStats#getActiveRequestsCount(long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerStats.getActiveRequestsCount(long)"})
  public void testGetActiveRequestsCountWithLong_givenServerStats_whenThree_thenReturnZero() {
    // Arrange
    ServerStats serverStats = new ServerStats();

    // Act and Assert
    assertEquals(0, serverStats.getActiveRequestsCount(3L));
    AtomicInteger atomicInteger = serverStats.activeRequestsCount;
    assertEquals(0, atomicInteger.get());
    assertEquals(0, atomicInteger.getAndDecrement());
    assertEquals(-1, atomicInteger.getAndIncrement());
    assertEquals(0, serverStats.getActiveRequestsCount());
    assertEquals(0, serverStats.getMonitoredActiveRequestsCount());
  }

  /**
   * Test {@link ServerStats#getActiveRequestsCount(long)} with {@code long}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link ServerStats#getActiveRequestsCount(long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerStats.getActiveRequestsCount(long)"})
  public void testGetActiveRequestsCountWithLong_thenReturnOne() {
    // Arrange
    ServerStats serverStats = new ServerStats();
    serverStats.incrementActiveRequestsCount();

    // Act and Assert
    assertEquals(1, serverStats.getActiveRequestsCount(3L));
    AtomicInteger atomicInteger = serverStats.activeRequestsCount;
    assertEquals(1, atomicInteger.get());
    assertEquals(1, atomicInteger.getAndDecrement());
    assertEquals(0, atomicInteger.getAndIncrement());
    assertEquals(1, serverStats.getMonitoredActiveRequestsCount());
  }

  /**
   * Test {@link ServerStats#getActiveRequestsCount(long)} with {@code long}.
   *
   * <ul>
   *   <li>When {@link Long#MAX_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link ServerStats#getActiveRequestsCount(long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerStats.getActiveRequestsCount(long)"})
  public void testGetActiveRequestsCountWithLong_whenMax_value() {
    // Arrange
    ServerStats serverStats = new ServerStats();
    serverStats.incrementActiveRequestsCount();

    // Act
    serverStats.getActiveRequestsCount(Long.MAX_VALUE);

    // Assert
    AtomicInteger atomicInteger = serverStats.activeRequestsCount;
    assertEquals(0, atomicInteger.get());
    assertEquals(0, atomicInteger.getAndDecrement());
    assertEquals(-1, atomicInteger.getAndIncrement());
    assertEquals(0, serverStats.getActiveRequestsCount());
    assertEquals(0, serverStats.getMonitoredActiveRequestsCount());
  }

  /**
   * Test {@link ServerStats#getActiveRequestsCount()}.
   *
   * <ul>
   *   <li>Given {@link ServerStats#ServerStats()}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link ServerStats#getActiveRequestsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerStats.getActiveRequestsCount()"})
  public void testGetActiveRequestsCount_givenServerStats_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, new ServerStats().getActiveRequestsCount());
  }

  /**
   * Test {@link ServerStats#getActiveRequestsCount()}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link ServerStats#getActiveRequestsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerStats.getActiveRequestsCount()"})
  public void testGetActiveRequestsCount_thenReturnOne() {
    // Arrange
    ServerStats serverStats = new ServerStats();
    serverStats.incrementActiveRequestsCount();

    // Act and Assert
    assertEquals(1, serverStats.getActiveRequestsCount());
  }

  /**
   * Test {@link ServerStats#getOpenConnectionsCount()}.
   *
   * <p>Method under test: {@link ServerStats#getOpenConnectionsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerStats.getOpenConnectionsCount()"})
  public void testGetOpenConnectionsCount() {
    // Arrange, Act and Assert
    assertEquals(0, new ServerStats().getOpenConnectionsCount());
  }

  /**
   * Test {@link ServerStats#getMeasuredRequestsCount()}.
   *
   * <p>Method under test: {@link ServerStats#getMeasuredRequestsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ServerStats.getMeasuredRequestsCount()"})
  public void testGetMeasuredRequestsCount() {
    // Arrange, Act and Assert
    assertEquals(0L, new ServerStats().getMeasuredRequestsCount());
  }

  /**
   * Test {@link ServerStats#getMonitoredActiveRequestsCount()}.
   *
   * <p>Method under test: {@link ServerStats#getMonitoredActiveRequestsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerStats.getMonitoredActiveRequestsCount()"})
  public void testGetMonitoredActiveRequestsCount() {
    // Arrange, Act and Assert
    assertEquals(0, new ServerStats().getMonitoredActiveRequestsCount());
  }

  /**
   * Test {@link ServerStats#isCircuitBreakerTripped()}.
   *
   * <p>Method under test: {@link ServerStats#isCircuitBreakerTripped()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServerStats.isCircuitBreakerTripped()"})
  public void testIsCircuitBreakerTripped() {
    // Arrange, Act and Assert
    assertFalse(new ServerStats().isCircuitBreakerTripped());
  }

  /**
   * Test {@link ServerStats#isCircuitBreakerTripped(long)} with {@code long}.
   *
   * <p>Method under test: {@link ServerStats#isCircuitBreakerTripped(long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServerStats.isCircuitBreakerTripped(long)"})
  public void testIsCircuitBreakerTrippedWithLong() {
    // Arrange, Act and Assert
    assertFalse(new ServerStats().isCircuitBreakerTripped(1L));
  }

  /**
   * Test {@link ServerStats#incrementSuccessiveConnectionFailureCount()}.
   *
   * <p>Method under test: {@link ServerStats#incrementSuccessiveConnectionFailureCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerStats.incrementSuccessiveConnectionFailureCount()"})
  public void testIncrementSuccessiveConnectionFailureCount() {
    // Arrange
    ServerStats serverStats = new ServerStats();

    // Act
    serverStats.incrementSuccessiveConnectionFailureCount();

    // Assert
    AtomicInteger atomicInteger = serverStats.successiveConnectionFailureCount;
    assertEquals(1, atomicInteger.get());
    assertEquals(1, atomicInteger.getAndDecrement());
    assertEquals(0, atomicInteger.getAndIncrement());
    assertEquals(1, serverStats.getSuccessiveConnectionFailureCount());
  }

  /**
   * Test {@link ServerStats#incrementSuccessiveConnectionFailureCount()}.
   *
   * <p>Method under test: {@link ServerStats#incrementSuccessiveConnectionFailureCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerStats.incrementSuccessiveConnectionFailureCount()"})
  public void testIncrementSuccessiveConnectionFailureCount2() {
    // Arrange
    ServerStats serverStats = new ServerStats();
    serverStats.incrementSuccessiveConnectionFailureCount();
    serverStats.incrementSuccessiveConnectionFailureCount();

    // Act
    serverStats.incrementSuccessiveConnectionFailureCount();

    // Assert
    AtomicInteger atomicInteger = serverStats.successiveConnectionFailureCount;
    assertEquals(3, atomicInteger.get());
    assertEquals(3, atomicInteger.getAndDecrement());
    assertEquals(2, atomicInteger.getAndIncrement());
    assertEquals(3, serverStats.getSuccessiveConnectionFailureCount());
  }

  /**
   * Test {@link ServerStats#incrementSuccessiveConnectionFailureCount()}.
   *
   * <p>Method under test: {@link ServerStats#incrementSuccessiveConnectionFailureCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerStats.incrementSuccessiveConnectionFailureCount()"})
  public void testIncrementSuccessiveConnectionFailureCount3() {
    // Arrange
    ServerStats serverStats = new ServerStats();
    serverStats.incrementSuccessiveConnectionFailureCount();
    serverStats.incrementSuccessiveConnectionFailureCount();
    serverStats.incrementSuccessiveConnectionFailureCount();
    serverStats.incrementSuccessiveConnectionFailureCount();

    // Act
    serverStats.incrementSuccessiveConnectionFailureCount();

    // Assert
    AtomicInteger atomicInteger = serverStats.successiveConnectionFailureCount;
    assertEquals(5, atomicInteger.get());
    assertEquals(5, atomicInteger.getAndDecrement());
    assertEquals(4, atomicInteger.getAndIncrement());
    assertEquals(5, serverStats.getSuccessiveConnectionFailureCount());
  }

  /**
   * Test {@link ServerStats#getSuccessiveConnectionFailureCount()}.
   *
   * <p>Method under test: {@link ServerStats#getSuccessiveConnectionFailureCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerStats.getSuccessiveConnectionFailureCount()"})
  public void testGetSuccessiveConnectionFailureCount() {
    // Arrange, Act and Assert
    assertEquals(0, new ServerStats().getSuccessiveConnectionFailureCount());
  }

  /**
   * Test {@link ServerStats#getResponseTimeAvg()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTimeAvg()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTimeAvg()"})
  public void testGetResponseTimeAvg() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTimeAvg(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTimeMax()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTimeMax()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTimeMax()"})
  public void testGetResponseTimeMax() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTimeMax(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTimeMin()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTimeMin()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTimeMin()"})
  public void testGetResponseTimeMin() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTimeMin(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTimeStdDev()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTimeStdDev()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTimeStdDev()"})
  public void testGetResponseTimeStdDev() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTimeStdDev(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTimePercentileNumValues()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTimePercentileNumValues()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ServerStats.getResponseTimePercentileNumValues()"})
  public void testGetResponseTimePercentileNumValues() {
    // Arrange, Act and Assert
    assertEquals(0, new ServerStats().getResponseTimePercentileNumValues());
  }

  /**
   * Test {@link ServerStats#getResponseTimePercentileTimeMillis()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTimePercentileTimeMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ServerStats.getResponseTimePercentileTimeMillis()"})
  public void testGetResponseTimePercentileTimeMillis() {
    // Arrange, Act and Assert
    assertEquals(0L, new ServerStats().getResponseTimePercentileTimeMillis());
  }

  /**
   * Test {@link ServerStats#getResponseTimeAvgRecent()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTimeAvgRecent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTimeAvgRecent()"})
  public void testGetResponseTimeAvgRecent() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTimeAvgRecent(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTime10thPercentile()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTime10thPercentile()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTime10thPercentile()"})
  public void testGetResponseTime10thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTime10thPercentile(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTime25thPercentile()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTime25thPercentile()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTime25thPercentile()"})
  public void testGetResponseTime25thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTime25thPercentile(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTime50thPercentile()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTime50thPercentile()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTime50thPercentile()"})
  public void testGetResponseTime50thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTime50thPercentile(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTime75thPercentile()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTime75thPercentile()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTime75thPercentile()"})
  public void testGetResponseTime75thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTime75thPercentile(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTime90thPercentile()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTime90thPercentile()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTime90thPercentile()"})
  public void testGetResponseTime90thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTime90thPercentile(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTime95thPercentile()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTime95thPercentile()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTime95thPercentile()"})
  public void testGetResponseTime95thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTime95thPercentile(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTime98thPercentile()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTime98thPercentile()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTime98thPercentile()"})
  public void testGetResponseTime98thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTime98thPercentile(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTime99thPercentile()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTime99thPercentile()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTime99thPercentile()"})
  public void testGetResponseTime99thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTime99thPercentile(), 0.0);
  }

  /**
   * Test {@link ServerStats#getResponseTime99point5thPercentile()}.
   *
   * <p>Method under test: {@link ServerStats#getResponseTime99point5thPercentile()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double ServerStats.getResponseTime99point5thPercentile()"})
  public void testGetResponseTime99point5thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new ServerStats().getResponseTime99point5thPercentile(), 0.0);
  }

  /**
   * Test {@link ServerStats#getTotalRequestsCount()}.
   *
   * <p>Method under test: {@link ServerStats#getTotalRequestsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ServerStats.getTotalRequestsCount()"})
  public void testGetTotalRequestsCount() {
    // Arrange, Act and Assert
    assertEquals(0L, new ServerStats().getTotalRequestsCount());
  }
}
