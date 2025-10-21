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
    new MonitoredConnectionManager("https://example.org/example", null, 1L, TimeUnit.NANOSECONDS);
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
  public void testNewMonitoredConnectionManager_whenComNetflixHttp4NamedConnectionPool() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(
            "com.netflix.http4.NamedConnectionPool", schreg, 1L, TimeUnit.DAYS);

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
   *   <li>When {@code _Request}.
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
  public void testNewMonitoredConnectionManager_whenRequest() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("_Request", schreg, 1L, TimeUnit.SECONDS);

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
   *   <li>When {@code _Reuse}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String,
   * SchemeRegistry)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String, SchemeRegistry)"})
  public void testNewMonitoredConnectionManager_whenReuse() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("_Reuse", schreg);

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
   *   <li>When twenty.
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
  public void testNewMonitoredConnectionManager_whenTwenty() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(
            "com.netflix.http4.NamedConnectionPool", schreg, 20L, TimeUnit.DAYS);

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
        new MonitoredConnectionManager(
            "com.netflix.http4.NamedConnectionPool", schreg, 2L, TimeUnit.DAYS);

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
   *   <li>When {@code _}.
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
  public void testNewMonitoredConnectionManager_whenUnderscore() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("_", schreg, 20L, TimeUnit.DAYS);

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
        new MonitoredConnectionManager(
            "com.netflix.http4.NamedConnectionPool", schreg, 0L, TimeUnit.DAYS);

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
        new MonitoredConnectionManager("https://example.org/example")
            .createConnectionPool(1L, TimeUnit.NANOSECONDS);

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
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("Name");
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
  public void testCreateConnectionPoolWithParams2() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("Name");
    SyncBasicHttpParams aparams = new SyncBasicHttpParams();
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
  public void testCreateConnectionPoolWithParams3() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("Name");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack cparams2 =
        new ClientParamsStack(aparams2, cparams, rparams, new BasicHttpParams());
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack params =
        new ClientParamsStack(aparams, cparams2, rparams2, new BasicHttpParams());

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
   *   <li>Given {@link MonitoredConnectionManager#MonitoredConnectionManager(String)} with name is
   *       {@code 42}.
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
  public void testCreateConnectionPoolWithParams_givenMonitoredConnectionManagerWithNameIs42() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("42");

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
}
