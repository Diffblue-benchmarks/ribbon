package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.config.FallbackProperty;
import com.netflix.client.config.Property;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ConnPoolByRoute;
import org.apache.http.pool.PoolStats;
import org.junit.Test;

public class ConnectionPoolCleanerDiffblueTest {
  /**
   * Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  public void testInitTask() {
    // Arrange
    MonitoredConnectionManager connMgr = new MonitoredConnectionManager("https://example.org/example");
    ConnectionPoolCleaner connectionPoolCleaner = new ConnectionPoolCleaner("https://example.org/example", connMgr,
        new ScheduledThreadPoolExecutor(1));

    // Act
    connectionPoolCleaner.initTask();

    // Assert that nothing has changed
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  public void testInitTask2() {
    // Arrange
    MonitoredConnectionManager connMgr = new MonitoredConnectionManager("https://example.org/example");

    ConnectionPoolCleaner connectionPoolCleaner = new ConnectionPoolCleaner("https://example.org/example", connMgr,
        new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Method under test: {@link ConnectionPoolCleaner#cleanupConnections()}
   */
  @Test
  public void testCleanupConnections() {
    // Arrange
    Property<Integer> primary = mock(Property.class);
    Optional<Integer> ofResult = Optional.<Integer>of(1);
    when(primary.get()).thenReturn(ofResult);
    FallbackProperty<Integer> connIdleEvictTimeMilliSeconds = new FallbackProperty<>(primary, mock(Property.class));

    MonitoredConnectionManager connMgr = new MonitoredConnectionManager("https://example.org/example");

    ConnectionPoolCleaner connectionPoolCleaner = new ConnectionPoolCleaner("https://example.org/example", connMgr,
        new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setConnIdleEvictTimeMilliSeconds(connIdleEvictTimeMilliSeconds);

    // Act
    connectionPoolCleaner.cleanupConnections();

    // Assert
    verify(primary).get();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ConnectionPoolCleaner#setConnIdleEvictTimeMilliSeconds(Property)}
   *   <li>{@link ConnectionPoolCleaner#setConnectionCleanerRepeatInterval(long)}
   *   <li>{@link ConnectionPoolCleaner#setConnectionCleanerTimerDelay(long)}
   *   <li>{@link ConnectionPoolCleaner#setEnableConnectionPoolCleanerTask(boolean)}
   *   <li>{@link ConnectionPoolCleaner#getConnIdleEvictTimeMilliSeconds()}
   *   <li>{@link ConnectionPoolCleaner#getConnectionCleanerRepeatInterval()}
   *   <li>{@link ConnectionPoolCleaner#getConnectionCleanerTimerDelay()}
   *   <li>{@link ConnectionPoolCleaner#isEnableConnectionPoolCleanerTask()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    MonitoredConnectionManager connMgr = new MonitoredConnectionManager("https://example.org/example");
    ConnectionPoolCleaner connectionPoolCleaner = new ConnectionPoolCleaner("https://example.org/example", connMgr,
        new ScheduledThreadPoolExecutor(1));
    FallbackProperty<Integer> connIdleEvictTimeMilliSeconds = new FallbackProperty<>(null, null);

    // Act
    connectionPoolCleaner.setConnIdleEvictTimeMilliSeconds(connIdleEvictTimeMilliSeconds);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(42L);
    connectionPoolCleaner.setConnectionCleanerTimerDelay(1L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);
    Property<Integer> actualConnIdleEvictTimeMilliSeconds = connectionPoolCleaner.getConnIdleEvictTimeMilliSeconds();
    long actualConnectionCleanerRepeatInterval = connectionPoolCleaner.getConnectionCleanerRepeatInterval();
    long actualConnectionCleanerTimerDelay = connectionPoolCleaner.getConnectionCleanerTimerDelay();

    // Assert that nothing has changed
    assertTrue(actualConnIdleEvictTimeMilliSeconds instanceof FallbackProperty);
    assertEquals(1L, actualConnectionCleanerTimerDelay);
    assertEquals(42L, actualConnectionCleanerRepeatInterval);
    assertTrue(connectionPoolCleaner.isEnableConnectionPoolCleanerTask());
    assertSame(connIdleEvictTimeMilliSeconds, actualConnIdleEvictTimeMilliSeconds);
  }

  /**
   * Method under test:
   * {@link ConnectionPoolCleaner#ConnectionPoolCleaner(String, ClientConnectionManager, ScheduledExecutorService)}
   */
  @Test
  public void testNewConnectionPoolCleaner() {
    // Arrange
    MonitoredConnectionManager connMgr = new MonitoredConnectionManager("https://example.org/example");

    // Act
    ConnectionPoolCleaner actualConnectionPoolCleaner = new ConnectionPoolCleaner("https://example.org/example",
        connMgr, new ScheduledThreadPoolExecutor(1));

    // Assert
    ClientConnectionManager clientConnectionManager = actualConnectionPoolCleaner.connMgr;
    assertTrue(clientConnectionManager instanceof MonitoredConnectionManager);
    ConnPoolByRoute connectionPool = ((MonitoredConnectionManager) clientConnectionManager).getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    ScheduledExecutorService scheduledExecutorService = actualConnectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    List<String> schemeNames = clientConnectionManager.getSchemeRegistry().getSchemeNames();
    assertEquals(2, schemeNames.size());
    assertEquals("http", schemeNames.get(0));
    assertEquals("https", schemeNames.get(1));
    assertEquals("https://example.org/example", actualConnectionPoolCleaner.name);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, ((MonitoredConnectionManager) clientConnectionManager).getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(0L, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getCompletedTaskCount());
    assertEquals(0L, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getTaskCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getCorePoolSize());
    assertEquals(10L, actualConnectionPoolCleaner.getConnectionCleanerTimerDelay());
    assertEquals(2, ((MonitoredConnectionManager) clientConnectionManager).getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, ((MonitoredConnectionManager) clientConnectionManager).getMaxTotal());
    assertEquals(30000L, actualConnectionPoolCleaner.getConnectionCleanerRepeatInterval());
    assertFalse(actualConnectionPoolCleaner.isEnableConnectionPoolCleanerTask());
    assertFalse(
        ((ScheduledThreadPoolExecutor) scheduledExecutorService).getContinueExistingPeriodicTasksAfterShutdownPolicy());
    assertFalse(((ScheduledThreadPoolExecutor) scheduledExecutorService).getRemoveOnCancelPolicy());
    assertTrue(((ScheduledThreadPoolExecutor) scheduledExecutorService).getQueue().isEmpty());
    assertTrue(
        ((ScheduledThreadPoolExecutor) scheduledExecutorService).getExecuteExistingDelayedTasksAfterShutdownPolicy());
    assertEquals(Integer.MAX_VALUE, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getMaximumPoolSize());
  }

  /**
   * Method under test:
   * {@link ConnectionPoolCleaner#ConnectionPoolCleaner(String, ClientConnectionManager, ScheduledExecutorService)}
   */
  @Test
  public void testNewConnectionPoolCleaner2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();
    PoolingClientConnectionManager connMgr = new PoolingClientConnectionManager(schreg, mock(DnsResolver.class));

    // Act
    ConnectionPoolCleaner actualConnectionPoolCleaner = new ConnectionPoolCleaner("https://example.org/example",
        connMgr, new ScheduledThreadPoolExecutor(1));

    // Assert
    ScheduledExecutorService scheduledExecutorService = actualConnectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    ClientConnectionManager clientConnectionManager = actualConnectionPoolCleaner.connMgr;
    assertTrue(clientConnectionManager instanceof PoolingClientConnectionManager);
    assertEquals("https://example.org/example", actualConnectionPoolCleaner.name);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
    PoolStats totalStats = ((PoolingClientConnectionManager) clientConnectionManager).getTotalStats();
    assertEquals(0, totalStats.getAvailable());
    assertEquals(0, totalStats.getLeased());
    assertEquals(0, totalStats.getPending());
    assertEquals(0L, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getCompletedTaskCount());
    assertEquals(0L, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getTaskCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getCorePoolSize());
    assertEquals(10L, actualConnectionPoolCleaner.getConnectionCleanerTimerDelay());
    assertEquals(2, ((PoolingClientConnectionManager) clientConnectionManager).getDefaultMaxPerRoute());
    assertEquals(20, ((PoolingClientConnectionManager) clientConnectionManager).getMaxTotal());
    assertEquals(20, totalStats.getMax());
    assertEquals(30000L, actualConnectionPoolCleaner.getConnectionCleanerRepeatInterval());
    assertFalse(actualConnectionPoolCleaner.isEnableConnectionPoolCleanerTask());
    assertFalse(
        ((ScheduledThreadPoolExecutor) scheduledExecutorService).getContinueExistingPeriodicTasksAfterShutdownPolicy());
    assertFalse(((ScheduledThreadPoolExecutor) scheduledExecutorService).getRemoveOnCancelPolicy());
    assertTrue(((ScheduledThreadPoolExecutor) scheduledExecutorService).getQueue().isEmpty());
    SchemeRegistry schemeRegistry = clientConnectionManager.getSchemeRegistry();
    assertTrue(schemeRegistry.getSchemeNames().isEmpty());
    assertTrue(
        ((ScheduledThreadPoolExecutor) scheduledExecutorService).getExecuteExistingDelayedTasksAfterShutdownPolicy());
    assertEquals(Integer.MAX_VALUE, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getMaximumPoolSize());
    assertSame(schreg, schemeRegistry);
  }
}
