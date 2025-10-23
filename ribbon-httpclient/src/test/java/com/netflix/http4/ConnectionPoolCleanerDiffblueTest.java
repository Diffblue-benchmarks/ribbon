package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionManager;
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
            "https://example.org/example", connMgr, new ScheduledThreadPoolExecutor(1));
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
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setKeepAliveTime(1L, TimeUnit.NANOSECONDS);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("default", new ThreadSafeClientConnManager(), scheduler);
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
    scheduler.setKeepAliveTime(1L, TimeUnit.NANOSECONDS);

    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("default", new ThreadSafeClientConnManager(), scheduler);
    connectionPoolCleaner.setConnectionCleanerTimerDelay(1L);
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
    ConnectionPoolCleaner connectionPoolCleaner =
        new ConnectionPoolCleaner("42", null, new ScheduledThreadPoolExecutor(1));
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
    scheduler.setKeepAliveTime(1L, TimeUnit.NANOSECONDS);

    ConnectionPoolCleaner connectionPoolCleaner = new ConnectionPoolCleaner("42", null, scheduler);
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
            "https://example.org/example", connMgr, new ScheduledThreadPoolExecutor(1));
    connectionPoolCleaner.setEnableConnectionPoolCleanerTask(true);

    // Act
    connectionPoolCleaner.initTask();

    // Assert
    ScheduledExecutorService scheduledExecutorService = connectionPoolCleaner.scheduler;
    assertTrue(scheduledExecutorService instanceof ScheduledThreadPoolExecutor);
    assertEquals(1, ((ScheduledThreadPoolExecutor) scheduledExecutorService).getLargestPoolSize());
  }
}
