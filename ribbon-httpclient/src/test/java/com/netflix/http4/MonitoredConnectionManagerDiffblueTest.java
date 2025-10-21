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
        new MonitoredConnectionManager("\"TestConnectionManager\"", schreg);

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
    new MonitoredConnectionManager("\"TestConnectionManager\"", null);
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
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
  public void testNewMonitoredConnectionManager_thenThrowIllegalArgumentException2() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new MonitoredConnectionManager("\"TestConnectionManager\"", null, 1L, TimeUnit.NANOSECONDS);
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code cannot be empty}.
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
  public void testNewMonitoredConnectionManager_whenCannotBeEmpty() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(" cannot be empty", schreg, -1L, TimeUnit.NANOSECONDS);

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
   *   <li>When {@code class}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenClass() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("class");

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
  public void testNewMonitoredConnectionManager_whenClass2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("class", schreg, -1L, TimeUnit.NANOSECONDS);

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
   *   <li>When {@code DefaultPublishingPolicy}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenDefaultPublishingPolicy() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("DefaultPublishingPolicy");

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
   *   <li>When {@code _Delete}.
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
  public void testNewMonitoredConnectionManager_whenDelete() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("_Delete", schreg, -1L, TimeUnit.NANOSECONDS);

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
   *   <li>When eight.
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
  public void testNewMonitoredConnectionManager_whenEight() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(" cannot be empty", schreg, 8L, TimeUnit.NANOSECONDS);

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
   *   <li>When fourteen.
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
  public void testNewMonitoredConnectionManager_whenFourteen() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("class", schreg, 14L, TimeUnit.NANOSECONDS);

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
        new MonitoredConnectionManager("class", schreg, 14L, TimeUnit.MICROSECONDS);

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}.
   * <ul>
   *   <li>When {@code MonitorConfig{name=}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MonitoredConnectionManager.<init>(String, SchemeRegistry, long, TimeUnit)"
  })
  public void testNewMonitoredConnectionManager_whenMonitorConfigName() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("MonitorConfig{name=", schreg, 2L, TimeUnit.NANOSECONDS);

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
   *   <li>When {@code NamedConnectionPool}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenNamedConnectionPool() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("NamedConnectionPool");

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
    new MonitoredConnectionManager("class", null, 2L, TimeUnit.NANOSECONDS);
  }

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code parts}.
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
  public void testNewMonitoredConnectionManager_whenParts() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("parts", schreg, -1L, TimeUnit.NANOSECONDS);

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
   *   <li>When {@code tagArrayNamedConnectionPool}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenTagArraycomNetflixHttp4NamedConnectionPool() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("tagArraycom.netflix.http4.NamedConnectionPool");

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
   *   <li>When {@code , tags=}.
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
  public void testNewMonitoredConnectionManager_whenTags() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(", tags=", schreg, -1L, TimeUnit.NANOSECONDS);

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
   *   <li>When {@code , tags=}.
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
  public void testNewMonitoredConnectionManager_whenTags2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(", tags=", schreg, 1L, TimeUnit.NANOSECONDS);

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
   *   <li>When {@code "TestConnectionManager"}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenTestConnectionManager() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("\"TestConnectionManager\"");

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
   *   <li>When {@code "TestConnectionManager"}.
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
  public void testNewMonitoredConnectionManager_whenTestConnectionManager2() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(
            "\"TestConnectionManager\"", schreg, 1L, TimeUnit.NANOSECONDS);

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
   *   <li>When thirty-one.
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
  public void testNewMonitoredConnectionManager_whenThirtyOne() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(", tags=", schreg, 31L, TimeUnit.NANOSECONDS);

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
   *   <li>When two.
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
  public void testNewMonitoredConnectionManager_whenTwo() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("class", schreg, 2L, TimeUnit.NANOSECONDS);

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
        new MonitoredConnectionManager("value", schreg, -1L, TimeUnit.NANOSECONDS);

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
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("\"TestConnectionManager\"")
            .createConnectionPool(1L, TimeUnit.SECONDS);

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
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("\"TestConnectionManager\"", new SchemeRegistry());

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.SECONDS);

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
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            "\"TestConnectionManager\"", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.SECONDS);

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
        new MonitoredConnectionManager("\"TestConnectionManager\"", new SchemeRegistry());
    monitoredConnectionManager.setMaxTotal(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.SECONDS);

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
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("https://example.org/example")
            .createConnectionPool(1L, TimeUnit.SECONDS);

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
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("config").createConnectionPool(1L, TimeUnit.SECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit7() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("config");
    monitoredConnectionManager.setMaxTotal(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.SECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit8() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("config");
    monitoredConnectionManager.setMaxForRoute(
        new HttpRoute(new HttpHost("\"www.example.com\"")), 3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.SECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit9() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("\"TestConnectionManager\"", new SchemeRegistry());

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(16L, TimeUnit.SECONDS);

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
   *   <li>When {@code NANOSECONDS}.
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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenNanoseconds() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("\"TestConnectionManager\"")
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
   *   <li>When seven.
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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenSeven() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("\"TestConnectionManager\"")
            .createConnectionPool(7L, TimeUnit.SECONDS);

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
   *   <li>When sixteen.
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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit_whenSixteen() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        new MonitoredConnectionManager("\"TestConnectionManager\"")
            .createConnectionPool(16L, TimeUnit.SECONDS);

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
        new MonitoredConnectionManager("\"TestConnectionManager\"")
            .createConnectionPool(3L, TimeUnit.SECONDS);

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
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack cparams2 =
        new ClientParamsStack(aparams2, cparams, rparams, new BasicHttpParams());
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams, cparams2, rparams2, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("\"TestConnectionManager\"", new SchemeRegistry());
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack cparams2 =
        new ClientParamsStack(aparams2, cparams, rparams, new BasicHttpParams());
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams, cparams2, rparams2, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack aparams2 =
        new ClientParamsStack(aparams, cparams, rparams, new BasicHttpParams());
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack cparams3 =
        new ClientParamsStack(aparams3, cparams2, rparams2, new BasicHttpParams());
    BasicHttpParams rparams3 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams2, cparams3, rparams3, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack aparams3 =
        new ClientParamsStack(aparams2, cparams, rparams, new BasicHttpParams());
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack cparams3 =
        new ClientParamsStack(aparams3, cparams2, rparams2, new BasicHttpParams());
    BasicHttpParams rparams3 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams, cparams3, rparams3, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack cparams =
        new ClientParamsStack(aparams2, null, rparams, new BasicHttpParams());
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams, cparams, rparams2, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack rparams2 =
        new ClientParamsStack(aparams3, cparams2, rparams, new BasicHttpParams());

    ClientParamsStack cparams3 =
        new ClientParamsStack(aparams2, cparams, rparams2, new BasicHttpParams());
    BasicHttpParams rparams3 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams, cparams3, rparams3, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();

    ClientParamsStack cparams2 =
        new ClientParamsStack(aparams2, cparams, null, new BasicHttpParams());
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams, cparams2, rparams, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack cparams2 =
        new ClientParamsStack(aparams2, cparams, rparams, new SyncBasicHttpParams());
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams, cparams2, rparams2, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack cparams2 =
        new ClientParamsStack(aparams2, cparams, rparams, new BasicHttpParams());

    ClientParamsStack stack = new ClientParamsStack(aparams, cparams2, new BasicHttpParams(), null);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    monitoredConnectionManager.setDefaultMaxPerRoute(3);
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack cparams2 =
        new ClientParamsStack(aparams2, cparams, rparams, new BasicHttpParams());
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams, cparams2, rparams2, new BasicHttpParams());

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("com.netflix.http4.NamedConnectionPool");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack cparams2 =
        new ClientParamsStack(aparams2, cparams, rparams, new BasicHttpParams());

    ClientParamsStack stack = new ClientParamsStack(aparams, cparams2, new BasicHttpParams(), null);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("com.netflix.http4.NamedConnectionPool");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack cparams2 =
        new ClientParamsStack(aparams3, cparams, rparams, new BasicHttpParams());
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack cparams3 =
        new ClientParamsStack(aparams2, cparams2, rparams2, new BasicHttpParams());

    ClientParamsStack stack = new ClientParamsStack(aparams, cparams3, new BasicHttpParams(), null);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("com.netflix.http4.NamedConnectionPool");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack cparams =
        new ClientParamsStack(aparams2, null, rparams, new BasicHttpParams());

    ClientParamsStack stack = new ClientParamsStack(aparams, cparams, new BasicHttpParams(), null);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
        new MonitoredConnectionManager("com.netflix.http4.NamedConnectionPool");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack rparams2 =
        new ClientParamsStack(aparams3, cparams2, rparams, new BasicHttpParams());

    ClientParamsStack cparams3 =
        new ClientParamsStack(aparams2, cparams, rparams2, new BasicHttpParams());

    ClientParamsStack stack = new ClientParamsStack(aparams, cparams3, new BasicHttpParams(), null);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
  public void testCreateConnectionPoolWithParams15() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("com.netflix.http4.NamedConnectionPool");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack oparams =
        new ClientParamsStack(aparams3, cparams2, rparams2, new BasicHttpParams());

    ClientParamsStack cparams3 = new ClientParamsStack(aparams2, cparams, rparams, oparams);

    ClientParamsStack stack = new ClientParamsStack(aparams, cparams3, new BasicHttpParams(), null);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack));

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
   *   <li>When {@link BasicHttpParams} (default constructor).
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
  public void testCreateConnectionPoolWithParams_whenBasicHttpParams() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("\"TestConnectionManager\"");

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
        new MonitoredConnectionManager("\"TestConnectionManager\"").getConnectionPool()
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
        new MonitoredConnectionManager("\"TestConnectionManager\"");

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("\"www.example.com\"")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }
}
