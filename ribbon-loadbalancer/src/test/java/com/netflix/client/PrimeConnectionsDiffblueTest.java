package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.DefaultPublishingPolicy;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.PublishingPolicy;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import com.netflix.servo.tag.BasicTag;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.Tag;
import com.netflix.servo.tag.TagList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PrimeConnectionsDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link PrimeConnections.PrimeConnectionEndStats#PrimeConnectionEndStats(int, int, int, long)}
   *   <li>{@link PrimeConnections.PrimeConnectionEndStats#toString()}
   * </ul>
   */
  @Test
  public void testPrimeConnectionEndStatsGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("PrimeConnectionEndStats [total=1, success=1, failure=1, totalTime=1]",
        (new PrimeConnections.PrimeConnectionEndStats(1, 1, 1, 1L)).toString());
  }

  /**
   * Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  public void testPrimeConnections() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    ArrayList<Server> servers = new ArrayList<>();

    // Act
    primeConnections.primeConnections(servers);

    // Assert
    Timer timer = primeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    assertNull(primeConnections.getEndStats());
    assertEquals(0L, ((BasicTimer) timer).getCount().longValue());
    assertEquals(0L, timer.getValue().longValue());
    assertTrue(servers.isEmpty());
  }

  /**
   * Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  public void testPrimeConnections2() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);

    // Act
    primeConnections.primeConnections(servers);

    // Assert
    Timer timer = primeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    PrimeConnections.PrimeConnectionEndStats endStats = primeConnections.getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(1, servers.size());
    assertEquals(1, endStats.total);
    assertEquals(1L, ((BasicTimer) timer).getCount().longValue());
    assertEquals(1L, timer.getValue().longValue());
    assertEquals(1L, endStats.totalTime);
    Server getResult = servers.get(0);
    assertFalse(getResult.isReadyToServe());
    assertSame(server, getResult);
  }

  /**
   * Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  public void testPrimeConnections3() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);
    Server server2 = new Server("42");
    servers.add(server2);

    // Act
    primeConnections.primeConnections(servers);

    // Assert
    Timer timer = primeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    PrimeConnections.PrimeConnectionEndStats endStats = primeConnections.getEndStats();
    assertEquals(0, endStats.failure);
    assertEquals(0, endStats.success);
    assertEquals(1L, ((BasicTimer) timer).getCount().longValue());
    assertEquals(1L, timer.getValue().longValue());
    assertEquals(1L, endStats.totalTime);
    assertEquals(2, servers.size());
    assertEquals(2, endStats.total);
    Server getResult = servers.get(0);
    assertFalse(getResult.isReadyToServe());
    Server getResult2 = servers.get(1);
    assertFalse(getResult2.isReadyToServe());
    assertSame(server, getResult);
    assertSame(server2, getResult2);
  }

  /**
   * Method under test: {@link PrimeConnections#primeConnections(List)}
   */
  @Test
  public void testPrimeConnections4() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 30000L, "Prime Connections URI");

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);

    // Act
    primeConnections.primeConnections(servers);

    // Assert
    assertTrue(primeConnections.initialPrimeTimer instanceof BasicTimer);
    PrimeConnections.PrimeConnectionEndStats endStats = primeConnections.getEndStats();
    assertEquals(0, endStats.success);
    assertEquals(1, servers.size());
    assertEquals(1, endStats.failure);
    assertEquals(1, endStats.total);
    Server getResult = servers.get(0);
    assertFalse(getResult.isReadyToServe());
    assertSame(server, getResult);
  }

  /**
   * Method under test: {@link PrimeConnections#getEndStats()}
   */
  @Test
  public void testGetEndStats() {
    // Arrange, Act and Assert
    assertNull((new PrimeConnections("Name", 3, 1L, "Prime Connections URI")).getEndStats());
  }

  /**
   * Method under test:
   * {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnections.PrimeConnectionListener)}
   */
  @Test
  public void testPrimeConnectionsAsync() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");
    ArrayList<Server> servers = new ArrayList<>();

    // Act
    List<Future<Boolean>> actualPrimeConnectionsAsyncResult = primeConnections.primeConnectionsAsync(servers,
        mock(PrimeConnections.PrimeConnectionListener.class));

    // Assert
    assertTrue(servers.isEmpty());
    assertTrue(actualPrimeConnectionsAsyncResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnections.PrimeConnectionListener)}
   */
  @Test
  public void testPrimeConnectionsAsync2() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);

    // Act
    List<Future<Boolean>> actualPrimeConnectionsAsyncResult = primeConnections.primeConnectionsAsync(servers,
        mock(PrimeConnections.PrimeConnectionListener.class));

    // Assert
    assertEquals(1, servers.size());
    assertEquals(1, actualPrimeConnectionsAsyncResult.size());
    Server getResult = servers.get(0);
    assertFalse(getResult.isReadyToServe());
    assertSame(server, getResult);
  }

  /**
   * Method under test:
   * {@link PrimeConnections#primeConnectionsAsync(List, PrimeConnections.PrimeConnectionListener)}
   */
  @Test
  public void testPrimeConnectionsAsync3() {
    // Arrange
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    ArrayList<Server> servers = new ArrayList<>();
    Server server = new Server("42");
    servers.add(server);
    Server server2 = new Server("42");
    servers.add(server2);

    // Act
    List<Future<Boolean>> actualPrimeConnectionsAsyncResult = primeConnections.primeConnectionsAsync(servers,
        mock(PrimeConnections.PrimeConnectionListener.class));

    // Assert
    assertEquals(2, servers.size());
    assertEquals(2, actualPrimeConnectionsAsyncResult.size());
    Server getResult = servers.get(0);
    assertFalse(getResult.isReadyToServe());
    Server getResult2 = servers.get(1);
    assertFalse(getResult2.isReadyToServe());
    assertSame(server, getResult);
    assertSame(server2, getResult2);
  }

  /**
   * Method under test:
   * {@link PrimeConnections#PrimeConnections(String, int, long, String)}
   */
  @Test
  public void testNewPrimeConnections() {
    // Arrange and Act
    PrimeConnections actualPrimeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    // Assert
    Counter counter = actualPrimeConnections.successCounter;
    MonitorConfig config = counter.getConfig();
    TagList tags = config.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof DataSourceType);
    assertTrue(counter instanceof BasicCounter);
    Counter counter2 = actualPrimeConnections.totalCounter;
    assertTrue(counter2 instanceof BasicCounter);
    Timer timer = actualPrimeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    MonitorConfig config2 = timer.getConfig();
    PublishingPolicy publishingPolicy = config2.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) timer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config3 = getResult.getConfig();
    TagList tags2 = config3.getTags();
    Iterator<Tag> iteratorResult2 = tags2.iterator();
    Tag nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof BasicTag);
    assertTrue(tags2 instanceof BasicTagList);
    TagList tags3 = config2.getTags();
    assertTrue(tags3 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    MonitorConfig config4 = counter2.getConfig();
    TagList tags4 = config4.getTags();
    assertTrue(tags4 instanceof BasicTagList);
    assertEquals("Name_PrimeConnection_SuccessCounter", config.getName());
    assertEquals("Name_PrimeConnection_TotalCounter", config4.getName());
    assertEquals("Name_initialPrimeConnectionsTimer", config3.getName());
    assertEquals("Name_initialPrimeConnectionsTimer", config2.getName());
    assertEquals("Prime Connections URI", actualPrimeConnections.primeConnectionsURIPath);
    assertEquals("count", nextResult2.getValue());
    assertEquals("statistic", nextResult2.getKey());
    assertNull(actualPrimeConnections.getEndStats());
    assertEquals(0, tags3.size());
    assertEquals(0.0d, ((BasicTimer) timer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) timer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) timer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) timer).getCount().longValue());
    assertEquals(0L, timer.getValue().longValue());
    assertEquals(0L, actualPrimeConnections.totalTimeTaken);
    assertEquals(1, tags.size());
    assertEquals(1L, actualPrimeConnections.maxTotalTimeToPrimeConnections);
    assertEquals(3, tags2.size());
    assertEquals(3, actualPrimeConnections.maxRetries);
    assertEquals(DataSourceType.COUNTER, nextResult);
    assertEquals(TimeUnit.MILLISECONDS, timer.getTimeUnit());
    assertFalse(tags2.isEmpty());
    assertFalse(tags.isEmpty());
    assertFalse(tags3.iterator().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(tags3.isEmpty());
    assertTrue(iteratorResult2.hasNext());
    assertEquals(tags, tags4);
    assertSame(publishingPolicy, config3.getPublishingPolicy());
    assertSame(publishingPolicy, config.getPublishingPolicy());
    assertSame(publishingPolicy, config4.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link PrimeConnections#PrimeConnections(String, int, long, String, float)}
   */
  @Test
  public void testNewPrimeConnections2() {
    // Arrange and Act
    PrimeConnections actualPrimeConnections = new PrimeConnections("Name", 3, 1L, "Prime Connections URI", 10.0f);

    // Assert
    Counter counter = actualPrimeConnections.successCounter;
    MonitorConfig config = counter.getConfig();
    TagList tags = config.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof DataSourceType);
    assertTrue(counter instanceof BasicCounter);
    Counter counter2 = actualPrimeConnections.totalCounter;
    assertTrue(counter2 instanceof BasicCounter);
    Timer timer = actualPrimeConnections.initialPrimeTimer;
    assertTrue(timer instanceof BasicTimer);
    MonitorConfig config2 = timer.getConfig();
    PublishingPolicy publishingPolicy = config2.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) timer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config3 = getResult.getConfig();
    TagList tags2 = config3.getTags();
    Iterator<Tag> iteratorResult2 = tags2.iterator();
    Tag nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof BasicTag);
    assertTrue(tags2 instanceof BasicTagList);
    TagList tags3 = config2.getTags();
    assertTrue(tags3 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    MonitorConfig config4 = counter2.getConfig();
    TagList tags4 = config4.getTags();
    assertTrue(tags4 instanceof BasicTagList);
    assertEquals("Name_PrimeConnection_SuccessCounter", config.getName());
    assertEquals("Name_PrimeConnection_TotalCounter", config4.getName());
    assertEquals("Name_initialPrimeConnectionsTimer", config3.getName());
    assertEquals("Name_initialPrimeConnectionsTimer", config2.getName());
    assertEquals("Prime Connections URI", actualPrimeConnections.primeConnectionsURIPath);
    assertEquals("count", nextResult2.getValue());
    assertEquals("statistic", nextResult2.getKey());
    assertNull(actualPrimeConnections.getEndStats());
    assertEquals(0, tags3.size());
    assertEquals(0.0d, ((BasicTimer) timer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) timer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) timer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) timer).getCount().longValue());
    assertEquals(0L, timer.getValue().longValue());
    assertEquals(0L, actualPrimeConnections.totalTimeTaken);
    assertEquals(1, tags.size());
    assertEquals(1L, actualPrimeConnections.maxTotalTimeToPrimeConnections);
    assertEquals(3, tags2.size());
    assertEquals(3, actualPrimeConnections.maxRetries);
    assertEquals(DataSourceType.COUNTER, nextResult);
    assertEquals(TimeUnit.MILLISECONDS, timer.getTimeUnit());
    assertFalse(tags2.isEmpty());
    assertFalse(tags.isEmpty());
    assertFalse(tags3.iterator().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(tags3.isEmpty());
    assertTrue(iteratorResult2.hasNext());
    assertEquals(tags, tags4);
    assertSame(publishingPolicy, config3.getPublishingPolicy());
    assertSame(publishingPolicy, config.getPublishingPolicy());
    assertSame(publishingPolicy, config4.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link PrimeConnections#PrimeConnections(String, IClientConfig)}
   */
  @Test
  public void testNewPrimeConnections3() {
    // Arrange, Act and Assert
    thrown.expect(RuntimeException.class);

    new PrimeConnections("Name", DefaultClientConfigImpl.getEmptyConfig());

  }
}
