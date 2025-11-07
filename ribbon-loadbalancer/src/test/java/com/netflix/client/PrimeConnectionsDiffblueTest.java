package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.PrimeConnections.PrimeConnectionEndStats;
import com.netflix.client.PrimeConnections.PrimeConnectionListener;
import com.netflix.loadbalancer.Server;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PrimeConnectionsDiffblueTest {
  /**
   * Test {@link PrimeConnections#PrimeConnections(String, int, long, String)}.
   * <p>
   * Method under test: {@link PrimeConnections#PrimeConnections(String, int, long, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PrimeConnections.<init>(String, int, long, String)"})
  public void testNewPrimeConnections() {
    // Arrange and Act
    PrimeConnections actualPrimeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    // Assert
    assertTrue(actualPrimeConnections.successCounter instanceof BasicCounter);
    assertTrue(actualPrimeConnections.totalCounter instanceof BasicCounter);
    assertTrue(actualPrimeConnections.initialPrimeTimer instanceof BasicTimer);
    assertEquals("Prime Connections URI", actualPrimeConnections.primeConnectionsURIPath);
    assertNull(actualPrimeConnections.getEndStats());
    assertEquals(0L, actualPrimeConnections.totalTimeTaken);
    assertEquals(1L, actualPrimeConnections.maxTotalTimeToPrimeConnections);
    assertEquals(3, actualPrimeConnections.maxRetries);
  }

  /**
   * Test {@link PrimeConnections#PrimeConnections(String, int, long, String, float)}.
   * <p>
   * Method under test: {@link PrimeConnections#PrimeConnections(String, int, long, String, float)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PrimeConnections.<init>(String, int, long, String, float)"})
  public void testNewPrimeConnections2() {
    // Arrange and Act
    PrimeConnections actualPrimeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI", 10.0f);

    // Assert
    assertTrue(actualPrimeConnections.successCounter instanceof BasicCounter);
    assertTrue(actualPrimeConnections.totalCounter instanceof BasicCounter);
    assertTrue(actualPrimeConnections.initialPrimeTimer instanceof BasicTimer);
    assertEquals("Prime Connections URI", actualPrimeConnections.primeConnectionsURIPath);
    assertNull(actualPrimeConnections.getEndStats());
    assertEquals(0L, actualPrimeConnections.totalTimeTaken);
    assertEquals(1L, actualPrimeConnections.maxTotalTimeToPrimeConnections);
    assertEquals(3, actualPrimeConnections.maxRetries);
  }

  /**
   * Test PrimeConnectionEndStats getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PrimeConnectionEndStats#PrimeConnectionEndStats(int, int, int, long)}
   *   <li>{@link PrimeConnectionEndStats#toString()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PrimeConnectionEndStats.<init>(int, int, int, long)",
      "String PrimeConnectionEndStats.toString()"})
  public void testPrimeConnectionEndStatsGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("PrimeConnectionEndStats [total=1, success=1, failure=1, totalTime=1]",
        (new PrimeConnectionEndStats(1, 1, 1, 1L)).toString());
  }

  /**
   * Test {@link PrimeConnections#primeConnections(List)}.
   * <p>
   * Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    // Act
    primeConnections.primeConnections(new ArrayList<>());

    // Assert that nothing has changed
    Counter counter = primeConnections.totalCounter;
    assertTrue(counter instanceof BasicCounter);
    Timer timer = primeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    assertEquals(0L, ((BasicTimer) timer).getCount().longValue());
    assertEquals(0L, timer.getValue().longValue());
    assertEquals(0L, counter.getValue().longValue());
  }

  /**
   * Test {@link PrimeConnections#primeConnections(List)}.
   * <p>
   * Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections2() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    primeConnections.primeConnections(servers);

    // Assert
    Timer timer = primeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    PrimeConnectionEndStats endStats = primeConnections.getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(1, endStats.total);
    assertEquals(1L, timer.getValue().longValue());
    assertEquals(1L, endStats.totalTime);
  }

  /**
   * Test {@link PrimeConnections#primeConnections(List)}.
   * <p>
   * Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections3() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));

    // Act
    primeConnections.primeConnections(servers);

    // Assert
    Timer timer = primeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    PrimeConnectionEndStats endStats = primeConnections.getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(1L, timer.getValue().longValue());
    assertEquals(1L, endStats.totalTime);
    assertEquals(2, endStats.total);
  }

  /**
   * Test {@link PrimeConnections#primeConnections(List)}.
   * <p>
   * Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections4() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 30000L, "Prime Connections URI");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    primeConnections.primeConnections(servers);

    // Assert
    assertTrue(primeConnections.initialPrimeTimer instanceof BasicTimer);
    PrimeConnectionEndStats endStats = primeConnections.getEndStats();
    assertEquals(1, endStats.failure);
    assertEquals(1, endStats.total);
  }

  /**
   * Test {@link PrimeConnections#primeConnections(List)}.
   * <p>
   * Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections5() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 30000L, "Prime Connections URI");

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));

    // Act
    primeConnections.primeConnections(servers);

    // Assert
    Counter counter = primeConnections.totalCounter;
    assertTrue(counter instanceof BasicCounter);
    Timer timer = primeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    PrimeConnectionEndStats endStats = primeConnections.getEndStats();
    assertEquals(0, endStats.success);
    assertEquals(1L, ((BasicTimer) timer).getCount().longValue());
    assertEquals(2, servers.size());
    assertEquals(2, endStats.failure);
    assertEquals(2L, counter.getValue().longValue());
    assertFalse(servers.get(0).isReadyToServe());
  }

  /**
   * Test {@link PrimeConnections#getEndStats()}.
   * <p>
   * Method under test: {@link PrimeConnections#getEndStats()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"PrimeConnectionEndStats PrimeConnections.getEndStats()"})
  public void testGetEndStats() {
    // Arrange, Act and Assert
    assertNull((new PrimeConnections("Name", 3, 1L, "Prime Connections URI")).getEndStats());
  }

  /**
   * Test {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnectionListener)}.
   * <p>
   * Method under test: {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnectionListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List PrimeConnections.primeConnectionsAsync(List, PrimeConnectionListener)"})
  public void testPrimeConnectionsAsync() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    // Act
    List<Future<Boolean>> actualPrimeConnectionsAsyncResult = primeConnections.primeConnectionsAsync(new ArrayList<>(),
        mock(PrimeConnectionListener.class));

    // Assert
    Counter counter = primeConnections.totalCounter;
    assertTrue(counter instanceof BasicCounter);
    assertEquals(0L, counter.getValue().longValue());
    assertTrue(actualPrimeConnectionsAsyncResult.isEmpty());
  }
}
