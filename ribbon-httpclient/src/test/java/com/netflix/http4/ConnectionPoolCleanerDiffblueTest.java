package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.http.conn.ClientConnectionManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ConnectionPoolCleanerDiffblueTest {
  /**
   * Test {@link ConnectionPoolCleaner#ConnectionPoolCleaner(String, ClientConnectionManager, ScheduledExecutorService)}.
   * <p>
   * Method under test: {@link ConnectionPoolCleaner#ConnectionPoolCleaner(String, ClientConnectionManager, ScheduledExecutorService)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ConnectionPoolCleaner.<init>(String, ClientConnectionManager, ScheduledExecutorService)"})
  public void testNewConnectionPoolCleaner() {
    // Arrange
    MonitoredConnectionManager connMgr = new MonitoredConnectionManager("https://example.org/example");

    // Act
    ConnectionPoolCleaner actualConnectionPoolCleaner = new ConnectionPoolCleaner("https://example.org/example",
        connMgr, new ScheduledThreadPoolExecutor(1));

    // Assert
    assertTrue(actualConnectionPoolCleaner.connMgr instanceof MonitoredConnectionManager);
    assertTrue(actualConnectionPoolCleaner.scheduler instanceof ScheduledThreadPoolExecutor);
    assertEquals("https://example.org/example", actualConnectionPoolCleaner.name);
    assertEquals(10L, actualConnectionPoolCleaner.getConnectionCleanerTimerDelay());
    assertEquals(30000L, actualConnectionPoolCleaner.getConnectionCleanerRepeatInterval());
    assertFalse(actualConnectionPoolCleaner.isEnableConnectionPoolCleanerTask());
  }

  /**
   * Test {@link ConnectionPoolCleaner#initTask()}.
   * <p>
   * Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
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
   * Test {@link ConnectionPoolCleaner#initTask()}.
   * <p>
   * Method under test: {@link ConnectionPoolCleaner#initTask()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ConnectionPoolCleaner.initTask()"})
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
}
