package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.FallbackProperty;
import com.netflix.client.config.Property;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

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
            "https://example.org/example", connMgr, new ScheduledThreadPoolExecutor(1));

    // Assert
    assertTrue(actualConnectionPoolCleaner.scheduler instanceof ScheduledThreadPoolExecutor);
    assertTrue(actualConnectionPoolCleaner.connMgr instanceof ThreadSafeClientConnManager);
    assertEquals("https://example.org/example", actualConnectionPoolCleaner.name);
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
            "https://example.org/example", connMgr, new ScheduledThreadPoolExecutor(1));
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
            "https://example.org/example", connMgr, new ScheduledThreadPoolExecutor(1));

    // Act
    connectionPoolCleaner.initTask();

    // Assert that nothing has changed
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
    assertEquals(0L, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getTaskCount());
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
            "https://example.org/example", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
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
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("java.lang.Integer", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
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
        new ConnectionPoolCleaner(
            "java.lang.Integer", new ThreadSafeClientConnManager(), scheduler);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
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
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("java.lang.Integer", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setConnectionCleanerTimerDelay(1L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
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
    BasicClientConnectionManager connMgr = new BasicClientConnectionManager();

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("java.lang.Integer", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
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
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(42L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
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
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(0, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
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
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", new ThreadSafeClientConnManager(), scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask10() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("default", new ThreadSafeClientConnManager(), scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask11() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example",
            new ThreadSafeClientConnManager(new SchemeRegistry()),
            scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask12() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("Name", new ThreadSafeClientConnManager(), scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask13() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler =
        new ScheduledThreadPoolExecutor(1, mock(ThreadFactory.class));
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("Name", new ThreadSafeClientConnManager(), scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask14() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler =
        new ScheduledThreadPoolExecutor(1, mock(ThreadFactory.class));
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "Connection pool clean up started for client {}",
            new ThreadSafeClientConnManager(),
            scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask15() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler =
        new ScheduledThreadPoolExecutor(1, mock(ThreadFactory.class));
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "Initializing ConnectionPoolCleaner for NFHttpClient:",
            new ThreadSafeClientConnManager(),
            scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask16() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler =
        new ScheduledThreadPoolExecutor(1, mock(ThreadFactory.class));
    scheduler.setCorePoolSize(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "Connection pool clean up started for client {}",
            new ThreadSafeClientConnManager(),
            scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask17() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler =
        new ScheduledThreadPoolExecutor(1, mock(ThreadFactory.class));
    scheduler.setMaximumPoolSize(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "Connection pool clean up started for client {}",
            new ThreadSafeClientConnManager(),
            scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask18() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler =
        new ScheduledThreadPoolExecutor(1, mock(ThreadFactory.class));
    scheduler.setKeepAliveTime(1L, TimeUnit.NANOSECONDS);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "Connection pool clean up started for client {}",
            new ThreadSafeClientConnManager(),
            scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask19() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler =
        new ScheduledThreadPoolExecutor(1, mock(ThreadFactory.class));
    scheduler.setMaximumPoolSize(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "Connection pool clean up started for client {}",
            new ThreadSafeClientConnManager(),
            scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(Long.MAX_VALUE);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
  public void testInitTask20() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler =
        new ScheduledThreadPoolExecutor(1, mock(ThreadFactory.class));
    scheduler.setRejectedExecutionHandler(mock(RejectedExecutionHandler.class));
    scheduler.setMaximumPoolSize(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "Connection pool clean up started for client {}",
            new ThreadSafeClientConnManager(),
            scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(Long.MAX_VALUE);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   *
   * <ul>
   *   <li>Given {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int)} with one
   *       CorePoolSize is one.
   * </ul>
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask_givenScheduledThreadPoolExecutorWithOneCorePoolSizeIsOne() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setCorePoolSize(1);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example",
            new ThreadSafeClientConnManager(new SchemeRegistry()),
            scheduler);
    connectionPoolCleaner.setConnectionCleanerRepeatInterval(10L);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    verify(threadFactory).newThread(isA(Runnable.class));
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getActiveCount());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getPoolSize());
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
            "https://example.org/example", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setConnIdleEvictTimeMilliSeconds(connIdleEvictTimeMilliSeconds);

    // Act
    connectionPoolCleaner.cleanupConnections();

    // Assert
    verify(primary).get();
  }
}
