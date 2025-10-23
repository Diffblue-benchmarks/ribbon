package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.PrimeConnections.ASyncPrimeConnectionsThreadFactory;
import com.netflix.client.PrimeConnections.PrimeConnectionEndStats;
import com.netflix.client.PrimeConnections.PrimeConnectionListener;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.IPing;
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
   * Test ASyncPrimeConnectionsThreadFactory {@link
   * ASyncPrimeConnectionsThreadFactory#ASyncPrimeConnectionsThreadFactory(String)}.
   *
   * <p>Method under test: {@link
   * ASyncPrimeConnectionsThreadFactory#ASyncPrimeConnectionsThreadFactory(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ASyncPrimeConnectionsThreadFactory.<init>(String)"})
  public void testASyncPrimeConnectionsThreadFactoryNewASyncPrimeConnectionsThreadFactory() {
    // Arrange, Act and Assert
    assertEquals(
        5,
        new ASyncPrimeConnectionsThreadFactory("-thread-")
            .newThread(mock(Runnable.class))
            .getPriority());
  }

  /**
   * Test ASyncPrimeConnectionsThreadFactory {@link
   * ASyncPrimeConnectionsThreadFactory#newThread(Runnable)}.
   *
   * <p>Method under test: {@link ASyncPrimeConnectionsThreadFactory#newThread(Runnable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Thread ASyncPrimeConnectionsThreadFactory.newThread(Runnable)"})
  public void testASyncPrimeConnectionsThreadFactoryNewThread() {
    // Arrange, Act and Assert
    assertEquals(
        5,
        new ASyncPrimeConnectionsThreadFactory("Name")
            .newThread(mock(Runnable.class))
            .getPriority());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link PrimeConnections#PrimeConnections(String, int, long, String, float)}
   *   <li>{@link PrimeConnections#getEndStats()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PrimeConnections.<init>(String, int, long, String, float)",
    "PrimeConnectionEndStats PrimeConnections.getEndStats()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    PrimeConnections actualPrimeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI", 10.0f);

    // Assert
    assertNull(actualPrimeConnections.getEndStats());
  }

  /**
   * Test {@link PrimeConnections#PrimeConnections(String, int, long, String)}.
   *
   * <p>Method under test: {@link PrimeConnections#PrimeConnections(String, int, long, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PrimeConnections.<init>(String, int, long, String)"})
  public void testNewPrimeConnections() {
    // Arrange and Act
    PrimeConnections actualPrimeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

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
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link PrimeConnectionEndStats#PrimeConnectionEndStats(int, int, int, long)}
   *   <li>{@link PrimeConnectionEndStats#toString()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PrimeConnectionEndStats.<init>(int, int, int, long)",
    "String PrimeConnectionEndStats.toString()"
  })
  public void testPrimeConnectionEndStatsGettersAndSetters() {
    // Arrange and Act
    PrimeConnectionEndStats actualPrimeConnectionEndStats =
        new PrimeConnectionEndStats(1, 1, 1, 1L);

    // Assert
    assertEquals(
        "PrimeConnectionEndStats [total=1, success=1, failure=1, totalTime=1]",
        actualPrimeConnectionEndStats.toString());
  }

  /**
   * Test {@link PrimeConnections#primeConnections(List)}.
   *
   * <p>Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

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
   *
   * <p>Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections2() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", Integer.MIN_VALUE, 1L, "Prime Connections URI");

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
   *
   * <p>Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections3() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", Integer.MIN_VALUE, 1L, "Prime Connections URI");

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
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} Alive is {@code true}.
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections_givenServerWithIdIs42AliveIsTrue_thenArrayListSizeIsTwo() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", Integer.MIN_VALUE, 1L, "Prime Connections URI");

    Server server = new Server("42");
    server.setAlive(true);

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(server);

    // Act
    primeConnections.primeConnections(servers);

    // Assert
    Timer timer = primeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    PrimeConnectionEndStats endStats = primeConnections.getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(1L, timer.getValue().longValue());
    assertEquals(1L, endStats.totalTime);
    assertEquals(2, servers.size());
    assertEquals(2, endStats.total);
    assertFalse(servers.get(1).isReadyToServe());
  }

  /**
   * Test {@link PrimeConnections#primeConnections(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections_whenArrayList() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    // Act
    primeConnections.primeConnections(new ArrayList<>());

    // Assert that nothing has changed
    Timer timer = primeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    assertEquals(0L, timer.getValue().longValue());
  }

  /**
   * Test {@link PrimeConnections#primeConnections(List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PrimeConnections.primeConnections(List)"})
  public void testPrimeConnections_whenNull() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    // Act
    primeConnections.primeConnections(null);

    // Assert that nothing has changed
    Timer timer = primeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    assertEquals(0L, timer.getValue().longValue());
  }

  /**
   * Test {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnectionListener)}.
   *
   * <p>Method under test: {@link PrimeConnections#primeConnectionsAsync(List,
   * PrimeConnectionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List PrimeConnections.primeConnectionsAsync(List, PrimeConnectionListener)"})
  public void testPrimeConnectionsAsync() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    // Act
    List<Future<Boolean>> actualPrimeConnectionsAsyncResult =
        primeConnections.primeConnectionsAsync(
            new ArrayList<>(), mock(PrimeConnectionListener.class));

    // Assert
    Counter counter = primeConnections.totalCounter;
    assertTrue(counter instanceof BasicCounter);
    assertEquals(0L, counter.getValue().longValue());
    assertTrue(actualPrimeConnectionsAsyncResult.isEmpty());
  }

  /**
   * Test {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnectionListener)}.
   *
   * <p>Method under test: {@link PrimeConnections#primeConnectionsAsync(List,
   * PrimeConnectionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List PrimeConnections.primeConnectionsAsync(List, PrimeConnectionListener)"})
  public void testPrimeConnectionsAsync2() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    // Act
    List<Future<Boolean>> actualPrimeConnectionsAsyncResult =
        primeConnections.primeConnectionsAsync(null, mock(PrimeConnectionListener.class));

    // Assert
    Counter counter = primeConnections.totalCounter;
    assertTrue(counter instanceof BasicCounter);
    assertEquals(0L, counter.getValue().longValue());
    assertTrue(actualPrimeConnectionsAsyncResult.isEmpty());
  }

  /**
   * Test {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnectionListener)}.
   *
   * <p>Method under test: {@link PrimeConnections#primeConnectionsAsync(List,
   * PrimeConnectionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List PrimeConnections.primeConnectionsAsync(List, PrimeConnectionListener)"})
  public void testPrimeConnectionsAsync3() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    ArrayList<Server> servers = new ArrayList<>();
    IPing ping = mock(IPing.class);
    BaseLoadBalancer listener = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    List<Future<Boolean>> actualPrimeConnectionsAsyncResult =
        primeConnections.primeConnectionsAsync(servers, listener);

    // Assert
    Counter counter = primeConnections.totalCounter;
    assertTrue(counter instanceof BasicCounter);
    assertEquals(0L, counter.getValue().longValue());
    assertTrue(actualPrimeConnectionsAsyncResult.isEmpty());
  }

  /**
   * Test {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnectionListener)}.
   *
   * <p>Method under test: {@link PrimeConnections#primeConnectionsAsync(List,
   * PrimeConnectionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List PrimeConnections.primeConnectionsAsync(List, PrimeConnectionListener)"})
  public void testPrimeConnectionsAsync4() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "_PrimeConnection_TotalCounter");

    // Act
    List<Future<Boolean>> actualPrimeConnectionsAsyncResult =
        primeConnections.primeConnectionsAsync(
            new ArrayList<>(), mock(PrimeConnectionListener.class));

    // Assert
    Counter counter = primeConnections.totalCounter;
    assertTrue(counter instanceof BasicCounter);
    assertEquals(0L, counter.getValue().longValue());
    assertTrue(actualPrimeConnectionsAsyncResult.isEmpty());
  }

  /**
   * Test {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnectionListener)}.
   *
   * <p>Method under test: {@link PrimeConnections#primeConnectionsAsync(List,
   * PrimeConnectionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List PrimeConnections.primeConnectionsAsync(List, PrimeConnectionListener)"})
  public void testPrimeConnectionsAsync5() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "_PrimeConnection_TotalCounter");
    primeConnections.primeConnections(new ArrayList<>());

    // Act
    List<Future<Boolean>> actualPrimeConnectionsAsyncResult =
        primeConnections.primeConnectionsAsync(
            new ArrayList<>(), mock(PrimeConnectionListener.class));

    // Assert
    Counter counter = primeConnections.totalCounter;
    assertTrue(counter instanceof BasicCounter);
    assertEquals(0L, counter.getValue().longValue());
    assertTrue(actualPrimeConnectionsAsyncResult.isEmpty());
  }

  /**
   * Test {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnectionListener)}.
   *
   * <p>Method under test: {@link PrimeConnections#primeConnectionsAsync(List,
   * PrimeConnectionListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List PrimeConnections.primeConnectionsAsync(List, PrimeConnectionListener)"})
  public void testPrimeConnectionsAsync6() {
    // Arrange
    PrimeConnections primeConnections =
        new PrimeConnections("Name", 3, 1L, "_PrimeConnection_TotalCounter");
    primeConnections.primeConnections(new ArrayList<>());

    // Act
    List<Future<Boolean>> actualPrimeConnectionsAsyncResult =
        primeConnections.primeConnectionsAsync(new ArrayList<>(), null);

    // Assert
    Counter counter = primeConnections.totalCounter;
    assertTrue(counter instanceof BasicCounter);
    assertEquals(0L, counter.getValue().longValue());
    assertTrue(actualPrimeConnectionsAsyncResult.isEmpty());
  }
}
