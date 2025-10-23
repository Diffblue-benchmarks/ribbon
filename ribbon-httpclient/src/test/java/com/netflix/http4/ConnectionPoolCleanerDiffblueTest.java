package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.FallbackProperty;
import com.netflix.client.config.Property;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ConnectionPoolCleanerDiffblueTest {
  /**
   * Test {@link ConnectionPoolCleaner#ConnectionPoolCleaner(String, ClientConnectionManager,
   * ScheduledExecutorService)}.
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#ConnectionPoolCleaner(String,
   * ClientConnectionManager, ScheduledExecutorService)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ConnectionPoolCleaner.<init>(String, ClientConnectionManager, ScheduledExecutorService)"
  })
  public void testNewConnectionPoolCleaner() {
    // Arrange
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();

    // Act
    ConnectionPoolCleaner actualConnectionPoolCleaner =
        new ConnectionPoolCleaner(
            "\"IdleConnectionCleaner\"", connMgr, new ScheduledThreadPoolExecutor(1));

    // Assert
    assertTrue(actualConnectionPoolCleaner.scheduler instanceof ScheduledThreadPoolExecutor);
    assertTrue(actualConnectionPoolCleaner.connMgr instanceof ThreadSafeClientConnManager);
    assertEquals("\"IdleConnectionCleaner\"", actualConnectionPoolCleaner.name);
    assertEquals(10L, actualConnectionPoolCleaner.getConnectionCleanerTimerDelay());
    assertEquals(30000L, actualConnectionPoolCleaner.getConnectionCleanerRepeatInterval());
    assertFalse(actualConnectionPoolCleaner.isEnableConnectionPoolCleanerTask());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Property ConnectionPoolCleaner.getConnIdleEvictTimeMilliSeconds()",
    "long ConnectionPoolCleaner.getConnectionCleanerRepeatInterval()",
    "long ConnectionPoolCleaner.getConnectionCleanerTimerDelay()",
    "boolean ConnectionPoolCleaner.isEnableConnectionPoolCleanerTask()",
    "void ConnectionPoolCleaner.setConnIdleEvictTimeMilliSeconds(Property)",
    "void ConnectionPoolCleaner.setConnectionCleanerRepeatInterval(long)",
    "void ConnectionPoolCleaner.setConnectionCleanerTimerDelay(long)",
    "void ConnectionPoolCleaner.setEnableConnectionPoolCleanerTask(boolean)",
    "String ConnectionPoolCleaner.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();
    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "\"IdleConnectionCleaner\"", connMgr, new ScheduledThreadPoolExecutor(1));
    FallbackProperty<Integer> connIdleEvictTimeMilliSeconds = new FallbackProperty<>(null, null);

    // Act
    connectionPoolCleaner.setConnIdleEvictTimeMilliSeconds(connIdleEvictTimeMilliSeconds);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(42L);
    connectionPoolCleaner.setConnectionCleanerTimerDelay(1L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);
    Property<Integer> actualConnIdleEvictTimeMilliSeconds =
        connectionPoolCleaner.getConnIdleEvictTimeMilliSeconds();
    long actualConnectionCleanerRepeatInterval =
        connectionPoolCleaner.getConnectionCleanerRepeatInterval();
    long actualConnectionCleanerTimerDelay = connectionPoolCleaner.getConnectionCleanerTimerDelay();

    // Assert
    assertTrue(actualConnIdleEvictTimeMilliSeconds instanceof FallbackProperty);
    assertEquals(1L, actualConnectionCleanerTimerDelay);
    assertEquals(42L, actualConnectionCleanerRepeatInterval);
    assertTrue(connectionPoolCleaner.isEnableConnectionPoolCleanerTask());
    assertSame(connIdleEvictTimeMilliSeconds, actualConnIdleEvictTimeMilliSeconds);
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask() {
    // Arrange
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();
    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "\"IdleConnectionCleaner\"", connMgr, new ScheduledThreadPoolExecutor(1));

    // Act
    connectionPoolCleaner.initTask();

    // Assert that nothing has changed
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask2() {
    // Arrange
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "\"IdleConnectionCleaner\"", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask3() {
    // Arrange
    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "\"IdleConnectionCleaner\"", null, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask4() {
    // Arrange
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("\"IdleConnectionCleaner\"", null, scheduler);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask5() {
    // Arrange
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setRemoveOnCancelPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", new ThreadSafeClientConnManager(), scheduler);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask6() {
    // Arrange
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setRemoveOnCancelPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", new ThreadSafeClientConnManager(), scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(42L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask7() {
    // Arrange
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setRemoveOnCancelPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", new ThreadSafeClientConnManager(), scheduler);
    connectionPoolCleaner.setConnectionCleanerTimerDelay(1L);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(42L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask8() {
    // Arrange
    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("default", null, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask9() {
    // Arrange
    PoolingClientConnectionManager connMgr =
        new PoolingClientConnectionManager(new SchemeRegistry());

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "com.netflix.http4.ConnectionPoolCleaner", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <ul>
   *   <li>Given {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int)} with one
   *       MaximumPoolSize is three.
   * </ul>
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask_givenScheduledThreadPoolExecutorWithOneMaximumPoolSizeIsThree() {
    // Arrange
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setMaximumPoolSize(3);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("default", null, scheduler);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <ul>
   *   <li>Given {@link ThreadSafeClientConnManager#ThreadSafeClientConnManager()}
   *       DefaultMaxPerRoute is three.
   * </ul>
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask_givenThreadSafeClientConnManagerDefaultMaxPerRouteIsThree() {
    // Arrange
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();
    connMgr.setDefaultMaxPerRoute(3);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "\"IdleConnectionCleaner\"", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#cleanupConnections()}.
   *
   * <ul>
   *   <li>Given {@link Property} {@link Property#get()} return of one.
   *   <li>Then calls {@link Property#get()}.
   * </ul>
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#cleanupConnections()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.cleanupConnections()"})
  public void testCleanupConnections_givenPropertyGetReturnOfOne_thenCallsGet() {
    // Arrange
    Property<Integer> primary = mock(Property.class);
    Optional<Integer> ofResult = Optional.of(1);
    when(primary.get()).thenReturn(ofResult);
    FallbackProperty<Integer> connIdleEvictTimeMilliSeconds =
        new FallbackProperty<>(primary, mock(Property.class));
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "\"IdleConnectionCleaner\"", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setConnIdleEvictTimeMilliSeconds(connIdleEvictTimeMilliSeconds);

    // Act
    connectionPoolCleaner.cleanupConnections();

    // Assert
    verify(primary).get();
  }
}
