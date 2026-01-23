package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import org.apache.http.conn.ClientConnectionManager;
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
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", new ThreadSafeClientConnManager(), scheduler);
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
  public void testInitTask4() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", new ThreadSafeClientConnManager(), scheduler);
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
}
