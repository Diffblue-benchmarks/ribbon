package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.servo.tag.BasicTag;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.ClientParamsStack;
import org.apache.http.impl.conn.tsccm.AbstractConnPool;
import org.apache.http.impl.conn.tsccm.ConnPoolByRoute;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MonitoredConnectionManagerDiffblueTest {
  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/examplehttps://example.org/example");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}.
   * <ul>
   *   <li>Then ConnectionPool return {@link NamedConnectionPool}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"})
  public void testNewMonitoredConnectionManager_thenConnectionPoolReturnNamedConnectionPool() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", schreg, 1L, TimeUnit.NANOSECONDS);

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
    assertSame(schreg, actualMonitoredConnectionManager.getSchemeRegistry());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <ul>
   *   <li>When {@code cannot be empty}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenCannotBeEmpty() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(" cannot be empty");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}.
   * <ul>
   *   <li>When {@code class}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String, SchemeRegistry)"})
  public void testNewMonitoredConnectionManager_whenClass() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("class", schreg);

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
    assertSame(schreg, actualMonitoredConnectionManager.getSchemeRegistry());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <ul>
   *   <li>When {@code _Delete}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenDelete() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("_Delete");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <ul>
   *   <li>When {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenHttpsExampleOrgExample() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}.
   * <ul>
   *   <li>When {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String, SchemeRegistry)"})
  public void testNewMonitoredConnectionManager_whenHttpsExampleOrgExample2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", schreg);

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
    assertSame(schreg, actualMonitoredConnectionManager.getSchemeRegistry());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <ul>
   *   <li>When {@code key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenKey() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("key");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String, SchemeRegistry)"})
  public void testNewMonitoredConnectionManager_whenName() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("Name", schreg);

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
    assertSame(schreg, actualMonitoredConnectionManager.getSchemeRegistry());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}.
   * <ul>
   *   <li>When {@code parts}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String, SchemeRegistry)"})
  public void testNewMonitoredConnectionManager_whenParts() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("parts", schreg);

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
    assertSame(schreg, actualMonitoredConnectionManager.getSchemeRegistry());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <ul>
   *   <li>When {@code _RequestConnectionTimer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenRequestConnectionTimer() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(
        "_RequestConnectionTimer");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <ul>
   *   <li>When {@code separator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenSeparator() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("separator");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <ul>
   *   <li>When space.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenSpace() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(" ");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <ul>
   *   <li>When {@code , tags=}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenTags() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(", tags=");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}.
   * <ul>
   *   <li>When {@code , tags=}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String, SchemeRegistry)"})
  public void testNewMonitoredConnectionManager_whenTags2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(", tags=", schreg);

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
    assertSame(schreg, actualMonitoredConnectionManager.getSchemeRegistry());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <ul>
   *   <li>When {@code _}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenUnderscore() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("_");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   * <ul>
   *   <li>When {@code value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenValue() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("value");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) connectionPool).getConnectionCount());
    assertEquals(0, connectionPool.getConnectionsInPool());
    assertEquals(0, actualMonitoredConnectionManager.getConnectionsInPool());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getCreatedEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getDeleteCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getFreeEntryCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getReleaseCount());
    assertEquals(0L, ((NamedConnectionPool) connectionPool).getRequestsCount());
    assertEquals(2, actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames().size());
    assertEquals(2, actualMonitoredConnectionManager.getDefaultMaxPerRoute());
    assertEquals(20, connectionPool.getMaxTotalConnections());
    assertEquals(20, actualMonitoredConnectionManager.getMaxTotal());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code connTTL}, {@code connTTLTimeUnit}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"})
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult = (new MonitoredConnectionManager("https://example.org/example"))
        .createConnectionPool(1L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"})
  public void testCreateConnectionPoolWithParams() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "com.netflix.http4.NamedConnectionPool");

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager
        .createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"})
  public void testCreateConnectionPoolWithParams2() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", new SchemeRegistry());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager
        .createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"})
  public void testCreateConnectionPoolWithParams3() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager.createConnectionPool(
        new ClientParamsStack(new ClientParamsStack(aparams, null, rparams, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"})
  public void testCreateConnectionPoolWithParams4() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(" ");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager.createConnectionPool(
        new ClientParamsStack(new ClientParamsStack(aparams, null, rparams, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"})
  public void testCreateConnectionPoolWithParams5() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("tagArray");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager.createConnectionPool(
        new ClientParamsStack(new ClientParamsStack(aparams, null, rparams, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"})
  public void testCreateConnectionPoolWithParams6() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager.createConnectionPool(
        new ClientParamsStack(new ClientParamsStack(aparams, null, rparams, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"})
  public void testCreateConnectionPoolWithParams7() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example");
    SyncBasicHttpParams aparams = new SyncBasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager.createConnectionPool(
        new ClientParamsStack(new ClientParamsStack(aparams, null, rparams, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"})
  public void testCreateConnectionPoolWithParams8() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager.createConnectionPool(
        new ClientParamsStack(new ClientParamsStack(aparams, null, rparams, new SyncBasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   * <ul>
   *   <li>Given {@link MonitoredConnectionManager#MonitoredConnectionManager(String)} with name is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"})
  public void testCreateConnectionPoolWithParams_givenMonitoredConnectionManagerWithNameIs42() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("42");

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager
        .createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   * <ul>
   *   <li>When {@link BasicHttpParams} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"})
  public void testCreateConnectionPoolWithParams_whenBasicHttpParams() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example");

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager
        .createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#getConnectionPool()}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#getConnectionPool()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ConnPoolByRoute MonitoredConnectionManager.getConnectionPool()"})
  public void testGetConnectionPool() {
    // Arrange, Act and Assert
    assertTrue((new MonitoredConnectionManager("https://example.org/example"))
        .getConnectionPool() instanceof NamedConnectionPool);
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example");

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("https://example.org/example")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection2() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "com.netflix.http4.NamedConnectionPool");

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("https://example.org/example")), 20);

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection3() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(" cannot be empty");

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("https://example.org/example")), 20);

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection4() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "com.netflix.http4.NamedConnectionPool");

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("https://example.org/example", 8080)), 20);

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection5() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "com.netflix.http4.NamedConnectionPool");
    HttpHost target = new HttpHost("https://example.org/example");
    InetAddress local = mock(InetAddress.class);

    // Act
    monitoredConnectionManager
        .requestConnection(new HttpRoute(target, local, new HttpHost("https://example.org/example"), true), 20);

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection6() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("42");

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("localhost")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection7() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("localhost")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection8() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("42");

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("https://example.org/example", 8080)),
        "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection9() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("config",
        new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("localhost")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection10() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", new SchemeRegistry(), 2L, TimeUnit.NANOSECONDS);

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("localhost")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection11() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", new SchemeRegistry(), 2L, TimeUnit.NANOSECONDS);

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("https://example.org/example", 8080)),
        "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <ul>
   *   <li>When {@link BasicTag#BasicTag(String, String)} with key is {@code tagArray} and value is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection_whenBasicTagWithKeyIsTagArrayAndValueIs42() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", new SchemeRegistry(), 2L, TimeUnit.NANOSECONDS);
    HttpRoute route = new HttpRoute(new HttpHost("localhost"));

    // Act
    monitoredConnectionManager.requestConnection(route, new BasicTag("tagArray", "42"));

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <ul>
   *   <li>When {@code class}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection_whenClass() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("42");

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("localhost")), "class");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <ul>
   *   <li>When {@code NamedConnectionPool}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection_whenComNetflixHttp4NamedConnectionPool() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", new SchemeRegistry(), 2L, TimeUnit.NANOSECONDS);

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("localhost")),
        "com.netflix.http4.NamedConnectionPool");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <ul>
   *   <li>When four.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection_whenFour() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("42");

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("localhost")), 4);

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <ul>
   *   <li>When {@link HttpHost#HttpHost(String)} with hostname is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection_whenHttpHostWithHostnameIsEmptyString() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "com.netflix.http4.NamedConnectionPool");

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("")), 20);

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   * <ul>
   *   <li>When thirteen.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"})
  public void testRequestConnection_whenThirteen() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("42");

    // Act
    monitoredConnectionManager.requestConnection(new HttpRoute(new HttpHost("localhost")), 13);

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }
}
