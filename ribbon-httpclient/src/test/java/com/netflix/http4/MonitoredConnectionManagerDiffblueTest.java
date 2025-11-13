package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class MonitoredConnectionManagerDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}.
   *
   * <ul>
   *   <li>Then ConnectionPool return {@link NamedConnectionPool}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String, SchemeRegistry)"})
  public void testNewMonitoredConnectionManager_thenConnectionPoolReturnNamedConnectionPool() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example", schreg);

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
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String, SchemeRegistry)"})
  public void testNewMonitoredConnectionManager_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new MonitoredConnectionManager("https://example.org/example", null);
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code class}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenClass() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("class", schreg, 1L, TimeUnit.NANOSECONDS);

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
   *
   * <ul>
   *   <li>When {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenHttpsExampleOrgExample() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenHttpsExampleOrgExample2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(
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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code id}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenId() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("id", schreg, 1L, TimeUnit.NANOSECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code MICROSECONDS}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenMicroseconds() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", schreg, 1L, TimeUnit.MICROSECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code MILLISECONDS}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenMilliseconds() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", schreg, 1L, TimeUnit.MILLISECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When minus one.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenMinusOne() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(" ", schreg, -1L, TimeUnit.NANOSECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When minus one.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenMinusOne2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", schreg, -1L, TimeUnit.MICROSECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code NamedConnectionPool}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenNamedConnectionPool() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("NamedConnectionPool", schreg, 1L, TimeUnit.NANOSECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenNull_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new MonitoredConnectionManager("https://example.org/example", null, 1L, TimeUnit.NANOSECONDS);
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenNull_thenThrowIllegalArgumentException2() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new MonitoredConnectionManager("https://example.org/example", null, 1L, TimeUnit.MILLISECONDS);
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code _Reuse}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenReuse() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("_Reuse", schreg, 1L, TimeUnit.NANOSECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code _Reuse}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenReuse_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new MonitoredConnectionManager("_Reuse", null, 1L, TimeUnit.NANOSECONDS);
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When sixty-one.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenSixtyOne() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(" ", schreg, 61L, TimeUnit.NANOSECONDS);

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
   *
   * <ul>
   *   <li>When space.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenSpace() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(" ");

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When space.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenSpace2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(" ", schreg, 1L, TimeUnit.NANOSECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When twenty-six.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenTwentySix() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", schreg, 26L, TimeUnit.MILLISECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code value}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenValue() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("value", schreg, 1L, TimeUnit.NANOSECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code value}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenValue2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("value", schreg, 1L, TimeUnit.MILLISECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When zero.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenZero() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(" ", schreg, 0L, TimeUnit.NANOSECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When zero.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenZero2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", schreg, 0L, TimeUnit.MILLISECONDS);

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
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(Long.MIN_VALUE, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit2() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("NamedConnectionPool")
            .createConnectionPool(Long.MIN_VALUE, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit3() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("42")
            .createConnectionPool(Long.MIN_VALUE, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit4() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setMaxTotal(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(3L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit5() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setMaxForRoute(
        new HttpRoute(new HttpHost("https://example.org/example")), 3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(3L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit6() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example", new SchemeRegistry());
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(61L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <ul>
   *   <li>When five.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenFive() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("https://example.org/example")
            .createConnectionPool(5L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <ul>
   *   <li>When five.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenFive2() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(5L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <ul>
   *   <li>When {@code MILLISECONDS}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenMilliseconds() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("https://example.org/example")
            .createConnectionPool(Long.MIN_VALUE, TimeUnit.MILLISECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <ul>
   *   <li>When {@link Long#MIN_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenMin_value() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("https://example.org/example")
            .createConnectionPool(Long.MIN_VALUE, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <ul>
   *   <li>When one.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenOne() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("https://example.org/example")
            .createConnectionPool(1L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <ul>
   *   <li>When sixty-one.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenSixtyOne() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(61L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <ul>
   *   <li>When three.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenThree() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("https://example.org/example")
            .createConnectionPool(3L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)} with {@code
   * connTTL}, {@code connTTLTimeUnit}.
   *
   * <ul>
   *   <li>When twenty-six.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConnPoolByRoute MonitoredConnectionManager.createConnectionPool(long, TimeUnit)"
  })
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenTwentySix() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("https://example.org/example")
            .createConnectionPool(26L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams2() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/exampleName");
    monitoredConnectionManager.setMaxTotal(3);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams3() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("config");
    monitoredConnectionManager.setMaxTotal(3);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams4() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/exampleName");
    monitoredConnectionManager.setMaxTotal(5);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams5() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxTotal(5);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams6() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/exampleName");
    monitoredConnectionManager.setDefaultMaxPerRoute(3);
    monitoredConnectionManager.setMaxTotal(5);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams7() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/exampleName");
    monitoredConnectionManager.setMaxForRoute(
        new HttpRoute(new HttpHost("https://example.org/example")), 3);
    monitoredConnectionManager.setMaxTotal(5);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams8() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setMaxTotal(5);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams9() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxTotal(5);
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack params =
        new ClientParamsStack(aparams, cparams, rparams, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(params);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams10() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(3);
    monitoredConnectionManager.setMaxTotal(5);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams11() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxForRoute(
        new HttpRoute(new HttpHost("https://example.org/example")), 3);
    monitoredConnectionManager.setMaxTotal(5);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new BasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams12() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxTotal(5);
    BasicHttpParams aparams = new BasicHttpParams();
    SyncBasicHttpParams cparams = new SyncBasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack params =
        new ClientParamsStack(aparams, cparams, rparams, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(params);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams13() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxTotal(5);
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack params = new ClientParamsStack(aparams, null, rparams, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(params);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams14() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxTotal(5);
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();

    ClientParamsStack params = new ClientParamsStack(aparams, cparams, null, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(params);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <ul>
   *   <li>When {@link SyncBasicHttpParams} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractConnPool MonitoredConnectionManager.createConnectionPool(HttpParams)"
  })
  public void testCreateConnectionPoolWithParams_whenSyncBasicHttpParams() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxForRoute(
        new HttpRoute(new HttpHost("https://example.org/example")), 3);
    monitoredConnectionManager.setMaxTotal(5);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new SyncBasicHttpParams());

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(
        0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(
        20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#getConnectionPool()}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#getConnectionPool()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ConnPoolByRoute MonitoredConnectionManager.getConnectionPool()"})
  public void testGetConnectionPool() {
    // Arrange, Act and Assert
    assertTrue(
        new MonitoredConnectionManager("https://example.org/example").getConnectionPool()
            instanceof NamedConnectionPool);
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("https://example.org/example")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection2() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example", new SchemeRegistry());

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("https://example.org/example")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection3() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("https://example.org/example")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection4() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("https://example.org/example")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection5() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example", new SchemeRegistry());
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("https://example.org/example")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection6() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(
            new HttpHost("https://example.org/example", 8080, "https://example.org/example")),
        "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection7() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setMaxTotal(3);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(
            new HttpHost("https://example.org/example", 8080, "https://example.org/example")),
        "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection8() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setMaxTotal(0);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(
            new HttpHost("https://example.org/example", 8080, "https://example.org/example")),
        "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection9() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setMaxTotal(7);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(
            new HttpHost("https://example.org/example", 8080, "https://example.org/example")),
        "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection10() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "https://example.org/example", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxTotal(7);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(
            new HttpHost("https://example.org/example", 8080, "https://example.org/example")),
        "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection11() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("42");
    monitoredConnectionManager.setMaxTotal(7);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(
            new HttpHost("https://example.org/example", 8080, "https://example.org/example")),
        "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection12() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("42");
    monitoredConnectionManager.setDefaultMaxPerRoute(3);
    monitoredConnectionManager.setMaxTotal(7);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(
            new HttpHost("https://example.org/example", 8080, "https://example.org/example")),
        "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <ul>
   *   <li>Given {@link MonitoredConnectionManager#MonitoredConnectionManager(String)} with {@code
   *       Name}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection_givenMonitoredConnectionManagerWithName() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("Name");

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("https://example.org/example")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <ul>
   *   <li>Given {@link MonitoredConnectionManager#MonitoredConnectionManager(String)} with {@code
   *       Name} MaxTotal is three.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection_givenMonitoredConnectionManagerWithNameMaxTotalIsThree() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("Name");
    monitoredConnectionManager.setMaxTotal(3);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("https://example.org/example")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Test {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.conn.ClientConnectionRequest MonitoredConnectionManager.requestConnection(HttpRoute, Object)"
  })
  public void testRequestConnection_whenNull() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("https://example.org/example");

    // Act
    monitoredConnectionManager.requestConnection(null, "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }
}
