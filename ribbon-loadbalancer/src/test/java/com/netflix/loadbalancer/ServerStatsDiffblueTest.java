package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.config.UnboxedIntProperty;
import org.junit.Test;

public class ServerStatsDiffblueTest {
  /**
   * Method under test: {@link ServerStats#close()}
   */
  @Test
  public void testClose() {
    // Arrange
    LoadBalancerStats lbStats = mock(LoadBalancerStats.class);
    when(lbStats.getActiveRequestsCountTimeout()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTripMaxTimeoutSeconds()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTrippedTimeoutFactor()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getConnectionFailureCountThreshold()).thenReturn(new UnboxedIntProperty(42));

    // Act
    (new ServerStats(lbStats)).close();

    // Assert that nothing has changed
    verify(lbStats).getActiveRequestsCountTimeout();
    verify(lbStats).getCircuitTripMaxTimeoutSeconds();
    verify(lbStats).getCircuitTrippedTimeoutFactor();
    verify(lbStats).getConnectionFailureCountThreshold();
  }

  /**
   * Method under test: {@link ServerStats#addToFailureCount()}
   */
  @Test
  public void testAddToFailureCount() {
    // Arrange
    LoadBalancerStats lbStats = mock(LoadBalancerStats.class);
    when(lbStats.getActiveRequestsCountTimeout()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTripMaxTimeoutSeconds()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTrippedTimeoutFactor()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getConnectionFailureCountThreshold()).thenReturn(new UnboxedIntProperty(42));

    // Act
    (new ServerStats(lbStats)).addToFailureCount();

    // Assert
    verify(lbStats).getActiveRequestsCountTimeout();
    verify(lbStats).getCircuitTripMaxTimeoutSeconds();
    verify(lbStats).getCircuitTrippedTimeoutFactor();
    verify(lbStats).getConnectionFailureCountThreshold();
  }

  /**
   * Method under test: {@link ServerStats#getFailureCount()}
   */
  @Test
  public void testGetFailureCount() {
    // Arrange, Act and Assert
    assertEquals(0L, (new ServerStats()).getFailureCount());
  }

  /**
   * Method under test: {@link ServerStats#noteResponseTime(double)}
   */
  @Test
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
   * Method under test: {@link ServerStats#decrementActiveRequestsCount()}
   */
  @Test
  public void testDecrementActiveRequestsCount() {
    // Arrange
    LoadBalancerStats lbStats = mock(LoadBalancerStats.class);
    when(lbStats.getActiveRequestsCountTimeout()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTripMaxTimeoutSeconds()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTrippedTimeoutFactor()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getConnectionFailureCountThreshold()).thenReturn(new UnboxedIntProperty(42));

    // Act
    (new ServerStats(lbStats)).decrementActiveRequestsCount();

    // Assert
    verify(lbStats).getActiveRequestsCountTimeout();
    verify(lbStats).getCircuitTripMaxTimeoutSeconds();
    verify(lbStats).getCircuitTrippedTimeoutFactor();
    verify(lbStats).getConnectionFailureCountThreshold();
  }

  /**
   * Method under test: {@link ServerStats#decrementOpenConnectionsCount()}
   */
  @Test
  public void testDecrementOpenConnectionsCount() {
    // Arrange
    LoadBalancerStats lbStats = mock(LoadBalancerStats.class);
    when(lbStats.getActiveRequestsCountTimeout()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTripMaxTimeoutSeconds()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTrippedTimeoutFactor()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getConnectionFailureCountThreshold()).thenReturn(new UnboxedIntProperty(42));

    // Act
    (new ServerStats(lbStats)).decrementOpenConnectionsCount();

    // Assert
    verify(lbStats).getActiveRequestsCountTimeout();
    verify(lbStats).getCircuitTripMaxTimeoutSeconds();
    verify(lbStats).getCircuitTrippedTimeoutFactor();
    verify(lbStats).getConnectionFailureCountThreshold();
  }

  /**
   * Method under test: {@link ServerStats#getActiveRequestsCount()}
   */
  @Test
  public void testGetActiveRequestsCount() {
    // Arrange, Act and Assert
    assertEquals(0, (new ServerStats()).getActiveRequestsCount());
  }

  /**
   * Method under test: {@link ServerStats#getActiveRequestsCount()}
   */
  @Test
  public void testGetActiveRequestsCount2() {
    // Arrange
    ServerStats serverStats = new ServerStats();
    serverStats.incrementActiveRequestsCount();

    // Act and Assert
    assertEquals(1, serverStats.getActiveRequestsCount());
  }

  /**
   * Method under test: {@link ServerStats#getActiveRequestsCount(long)}
   */
  @Test
  public void testGetActiveRequestsCount3() {
    // Arrange
    LoadBalancerStats lbStats = mock(LoadBalancerStats.class);
    when(lbStats.getActiveRequestsCountTimeout()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTripMaxTimeoutSeconds()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTrippedTimeoutFactor()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getConnectionFailureCountThreshold()).thenReturn(new UnboxedIntProperty(42));

    // Act
    int actualActiveRequestsCount = (new ServerStats(lbStats)).getActiveRequestsCount(3L);

    // Assert
    verify(lbStats).getActiveRequestsCountTimeout();
    verify(lbStats).getCircuitTripMaxTimeoutSeconds();
    verify(lbStats).getCircuitTrippedTimeoutFactor();
    verify(lbStats).getConnectionFailureCountThreshold();
    assertEquals(0, actualActiveRequestsCount);
  }

  /**
   * Method under test: {@link ServerStats#getActiveRequestsCount(long)}
   */
  @Test
  public void testGetActiveRequestsCount4() {
    // Arrange
    UnboxedIntProperty unboxedIntProperty = mock(UnboxedIntProperty.class);
    when(unboxedIntProperty.get()).thenReturn(1);
    LoadBalancerStats lbStats = mock(LoadBalancerStats.class);
    when(lbStats.getActiveRequestsCountTimeout()).thenReturn(unboxedIntProperty);
    when(lbStats.getCircuitTripMaxTimeoutSeconds()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTrippedTimeoutFactor()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getConnectionFailureCountThreshold()).thenReturn(new UnboxedIntProperty(42));

    ServerStats serverStats = new ServerStats(lbStats);
    serverStats.incrementActiveRequestsCount();

    // Act
    int actualActiveRequestsCount = serverStats.getActiveRequestsCount(3L);

    // Assert
    verify(unboxedIntProperty).get();
    verify(lbStats).getActiveRequestsCountTimeout();
    verify(lbStats).getCircuitTripMaxTimeoutSeconds();
    verify(lbStats).getCircuitTrippedTimeoutFactor();
    verify(lbStats).getConnectionFailureCountThreshold();
    assertEquals(1, actualActiveRequestsCount);
  }

  /**
   * Method under test: {@link ServerStats#getOpenConnectionsCount()}
   */
  @Test
  public void testGetOpenConnectionsCount() {
    // Arrange, Act and Assert
    assertEquals(0, (new ServerStats()).getOpenConnectionsCount());
  }

  /**
   * Method under test: {@link ServerStats#getMeasuredRequestsCount()}
   */
  @Test
  public void testGetMeasuredRequestsCount() {
    // Arrange, Act and Assert
    assertEquals(0L, (new ServerStats()).getMeasuredRequestsCount());
  }

  /**
   * Method under test: {@link ServerStats#getMonitoredActiveRequestsCount()}
   */
  @Test
  public void testGetMonitoredActiveRequestsCount() {
    // Arrange, Act and Assert
    assertEquals(0, (new ServerStats()).getMonitoredActiveRequestsCount());
  }

  /**
   * Method under test: {@link ServerStats#isCircuitBreakerTripped()}
   */
  @Test
  public void testIsCircuitBreakerTripped() {
    // Arrange, Act and Assert
    assertFalse((new ServerStats()).isCircuitBreakerTripped());
    assertFalse((new ServerStats()).isCircuitBreakerTripped(1L));
  }

  /**
   * Method under test:
   * {@link ServerStats#clearSuccessiveConnectionFailureCount()}
   */
  @Test
  public void testClearSuccessiveConnectionFailureCount() {
    // Arrange
    LoadBalancerStats lbStats = mock(LoadBalancerStats.class);
    when(lbStats.getActiveRequestsCountTimeout()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTripMaxTimeoutSeconds()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTrippedTimeoutFactor()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getConnectionFailureCountThreshold()).thenReturn(new UnboxedIntProperty(42));

    // Act
    (new ServerStats(lbStats)).clearSuccessiveConnectionFailureCount();

    // Assert
    verify(lbStats).getActiveRequestsCountTimeout();
    verify(lbStats).getCircuitTripMaxTimeoutSeconds();
    verify(lbStats).getCircuitTrippedTimeoutFactor();
    verify(lbStats).getConnectionFailureCountThreshold();
  }

  /**
   * Method under test: {@link ServerStats#getSuccessiveConnectionFailureCount()}
   */
  @Test
  public void testGetSuccessiveConnectionFailureCount() {
    // Arrange, Act and Assert
    assertEquals(0, (new ServerStats()).getSuccessiveConnectionFailureCount());
  }

  /**
   * Method under test: {@link ServerStats#getSuccessiveConnectionFailureCount()}
   */
  @Test
  public void testGetSuccessiveConnectionFailureCount2() {
    // Arrange
    ServerStats serverStats = new ServerStats(new LoadBalancerStats());
    serverStats.incrementSuccessiveConnectionFailureCount();

    // Act and Assert
    assertEquals(1, serverStats.getSuccessiveConnectionFailureCount());
  }

  /**
   * Method under test: {@link ServerStats#getResponseTimeAvg()}
   */
  @Test
  public void testGetResponseTimeAvg() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTimeAvg(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTimeMax()}
   */
  @Test
  public void testGetResponseTimeMax() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTimeMax(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTimeMin()}
   */
  @Test
  public void testGetResponseTimeMin() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTimeMin(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTimeStdDev()}
   */
  @Test
  public void testGetResponseTimeStdDev() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTimeStdDev(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTimePercentileNumValues()}
   */
  @Test
  public void testGetResponseTimePercentileNumValues() {
    // Arrange, Act and Assert
    assertEquals(0, (new ServerStats()).getResponseTimePercentileNumValues());
  }

  /**
   * Method under test: {@link ServerStats#getResponseTimePercentileTime()}
   */
  @Test
  public void testGetResponseTimePercentileTime() {
    // Arrange
    LoadBalancerStats lbStats = mock(LoadBalancerStats.class);
    when(lbStats.getActiveRequestsCountTimeout()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTripMaxTimeoutSeconds()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getCircuitTrippedTimeoutFactor()).thenReturn(new UnboxedIntProperty(42));
    when(lbStats.getConnectionFailureCountThreshold()).thenReturn(new UnboxedIntProperty(42));

    // Act
    (new ServerStats(lbStats)).getResponseTimePercentileTime();

    // Assert
    verify(lbStats).getActiveRequestsCountTimeout();
    verify(lbStats).getCircuitTripMaxTimeoutSeconds();
    verify(lbStats).getCircuitTrippedTimeoutFactor();
    verify(lbStats).getConnectionFailureCountThreshold();
  }

  /**
   * Method under test: {@link ServerStats#getResponseTimePercentileTimeMillis()}
   */
  @Test
  public void testGetResponseTimePercentileTimeMillis() {
    // Arrange, Act and Assert
    assertEquals(0L, (new ServerStats()).getResponseTimePercentileTimeMillis());
  }

  /**
   * Method under test: {@link ServerStats#getResponseTimeAvgRecent()}
   */
  @Test
  public void testGetResponseTimeAvgRecent() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTimeAvgRecent(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTime10thPercentile()}
   */
  @Test
  public void testGetResponseTime10thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTime10thPercentile(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTime25thPercentile()}
   */
  @Test
  public void testGetResponseTime25thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTime25thPercentile(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTime50thPercentile()}
   */
  @Test
  public void testGetResponseTime50thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTime50thPercentile(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTime75thPercentile()}
   */
  @Test
  public void testGetResponseTime75thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTime75thPercentile(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTime90thPercentile()}
   */
  @Test
  public void testGetResponseTime90thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTime90thPercentile(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTime95thPercentile()}
   */
  @Test
  public void testGetResponseTime95thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTime95thPercentile(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTime98thPercentile()}
   */
  @Test
  public void testGetResponseTime98thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTime98thPercentile(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTime99thPercentile()}
   */
  @Test
  public void testGetResponseTime99thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTime99thPercentile(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getResponseTime99point5thPercentile()}
   */
  @Test
  public void testGetResponseTime99point5thPercentile() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new ServerStats()).getResponseTime99point5thPercentile(), 0.0);
  }

  /**
   * Method under test: {@link ServerStats#getTotalRequestsCount()}
   */
  @Test
  public void testGetTotalRequestsCount() {
    // Arrange, Act and Assert
    assertEquals(0L, (new ServerStats()).getTotalRequestsCount());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ServerStats#setBufferSize(int)}
   *   <li>{@link ServerStats#setFailureCountSlidingWindowInterval(long)}
   *   <li>{@link ServerStats#setPublishInterval(int)}
   *   <li>{@link ServerStats#getFailureCountSlidingWindowInterval()}
   *   <li>{@link ServerStats#getServer()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ServerStats serverStats = new ServerStats();

    // Act
    serverStats.setBufferSize(3);
    serverStats.setFailureCountSlidingWindowInterval(1L);
    serverStats.setPublishInterval(42);
    long actualFailureCountSlidingWindowInterval = serverStats.getFailureCountSlidingWindowInterval();
    serverStats.getServer();

    // Assert that nothing has changed
    assertEquals(1L, actualFailureCountSlidingWindowInterval);
  }
}
