package com.netflix.http4;

import static org.junit.Assert.assertEquals;
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
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ConnectionPoolCleanerDiffblueTest {
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
    scheduler.setRemoveOnCancelPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "java.lang.Integer", new ThreadSafeClientConnManager(), scheduler);
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
  public void testInitTask5() {
    // Arrange
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
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
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
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
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setRemoveOnCancelPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("", new ThreadSafeClientConnManager(), scheduler);
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
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setRemoveOnCancelPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", new BasicClientConnectionManager(), scheduler);
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
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setRemoveOnCancelPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example",
            new BasicClientConnectionManager(new SchemeRegistry()),
            scheduler);
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
  public void testInitTask10() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setThreadFactory(threadFactory);
    scheduler.setRemoveOnCancelPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", new BasicClientConnectionManager(), scheduler);
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
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.scheduleAtFixedRate(mock(Runnable.class), 1L, 1L, TimeUnit.NANOSECONDS);
    scheduler.setThreadFactory(mock(ThreadFactory.class));
    scheduler.setRemoveOnCancelPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("Name", new BasicClientConnectionManager(), scheduler);
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert that nothing has changed
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
  public void testInitTask12() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);
    when(threadFactory.newThread(Mockito.<Runnable>any())).thenReturn(new Thread());

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
    scheduler.setThreadFactory(threadFactory);
    scheduler.setRemoveOnCancelPolicy(true);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("42", new BasicClientConnectionManager(), scheduler);
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
   *   <li>Given {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int)} with one.
   * </ul>
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask_givenScheduledThreadPoolExecutorWithOne() {
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
   * <ul>
   *   <li>Given {@link ScheduledThreadPoolExecutor#ScheduledThreadPoolExecutor(int)} with zero.
   * </ul>
   *
   * <p>Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
  public void testInitTask_givenScheduledThreadPoolExecutorWithZero() {
    // Arrange
    ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();
    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner(
            "https://example.org/example", connMgr, new ScheduledThreadPoolExecutor(0));

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
}
