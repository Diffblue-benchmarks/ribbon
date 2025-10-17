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
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class MonitoredConnectionManagerDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("42https://example.org/examplehttps://example.org/example");

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long,
   * TimeUnit)}.
   *
   * <ul>
   *   <li>Then ConnectionPool return {@link NamedConnectionPool}.
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
  public void testNewMonitoredConnectionManager_thenConnectionPoolReturnNamedConnectionPool2() {
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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   *
   * <ul>
   *   <li>When {@code _Deleteunit}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenDeleteunit() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("_Deleteunit");

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
   * Test {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}.
   *
   * <ul>
   *   <li>When {@code id_Request}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenIdRequest() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("id_Request");

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
   *
   * <ul>
   *   <li>When {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenName() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager(" Name");

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
   *
   * <ul>
   *   <li>When {@code nameunit}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MonitoredConnectionManager.<init>(String)"})
  public void testNewMonitoredConnectionManager_whenNameunit() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager =
        new MonitoredConnectionManager("nameunit");

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
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MINUTES);

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
        new MonitoredConnectionManager("https://example.org/example");
    monitoredConnectionManager.setDefaultMaxPerRoute(2);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MINUTES);

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
    monitoredConnectionManager.setDefaultMaxPerRoute(5);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MINUTES);

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
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams2, cparams2, rparams2, new BasicHttpParams());
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams3 = new BasicHttpParams();

    ClientParamsStack oparams =
        new ClientParamsStack(stack, aparams3, cparams3, rparams3, new BasicHttpParams());

    ClientParamsStack stack2 = new ClientParamsStack(aparams, cparams, rparams, oparams);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack2));

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
        new MonitoredConnectionManager("https://example.org/example", new SchemeRegistry());
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams2, cparams2, rparams2, new BasicHttpParams());
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams3 = new BasicHttpParams();

    ClientParamsStack oparams =
        new ClientParamsStack(stack, aparams3, cparams3, rparams3, new BasicHttpParams());

    ClientParamsStack stack2 = new ClientParamsStack(aparams, cparams, rparams, oparams);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack2));

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
        new MonitoredConnectionManager("https://example.org/example");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();

    ClientParamsStack aparams2 =
        new ClientParamsStack(aparams, cparams, rparams, new BasicHttpParams());
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams3 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams3, cparams3, rparams3, new BasicHttpParams());
    BasicHttpParams aparams4 = new BasicHttpParams();
    BasicHttpParams cparams4 = new BasicHttpParams();
    BasicHttpParams rparams4 = new BasicHttpParams();

    ClientParamsStack oparams =
        new ClientParamsStack(stack, aparams4, cparams4, rparams4, new BasicHttpParams());

    ClientParamsStack stack2 = new ClientParamsStack(aparams2, cparams2, rparams2, oparams);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack2));

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
        new MonitoredConnectionManager("https://example.org/example");
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams, cparams2, rparams2, new BasicHttpParams());
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams3 = new BasicHttpParams();

    ClientParamsStack oparams =
        new ClientParamsStack(stack, aparams2, cparams3, rparams3, new BasicHttpParams());

    ClientParamsStack stack2 = new ClientParamsStack(null, cparams, rparams, oparams);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack2));

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
        new MonitoredConnectionManager("https://example.org/example", new SchemeRegistry());
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack rparams3 =
        new ClientParamsStack(aparams3, cparams3, rparams2, new BasicHttpParams());

    ClientParamsStack stack =
        new ClientParamsStack(aparams2, cparams2, rparams3, new BasicHttpParams());
    BasicHttpParams aparams4 = new BasicHttpParams();
    BasicHttpParams cparams4 = new BasicHttpParams();
    BasicHttpParams rparams4 = new BasicHttpParams();

    ClientParamsStack oparams =
        new ClientParamsStack(stack, aparams4, cparams4, rparams4, new BasicHttpParams());

    ClientParamsStack stack2 = new ClientParamsStack(aparams, cparams, rparams, oparams);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack2));

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
        new MonitoredConnectionManager("https://example.org/example", new SchemeRegistry());
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();

    ClientParamsStack stack =
        new ClientParamsStack(aparams2, cparams2, null, new BasicHttpParams());
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack oparams =
        new ClientParamsStack(stack, aparams3, cparams3, rparams2, new BasicHttpParams());

    ClientParamsStack stack2 = new ClientParamsStack(aparams, cparams, rparams, oparams);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack2));

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
        new MonitoredConnectionManager("https://example.org/example", new SchemeRegistry());
    SyncBasicHttpParams aparams = new SyncBasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();

    ClientParamsStack rparams3 =
        new ClientParamsStack(aparams3, cparams3, rparams2, new BasicHttpParams());

    ClientParamsStack stack =
        new ClientParamsStack(aparams2, cparams2, rparams3, new BasicHttpParams());
    BasicHttpParams aparams4 = new BasicHttpParams();
    BasicHttpParams cparams4 = new BasicHttpParams();
    BasicHttpParams rparams4 = new BasicHttpParams();

    ClientParamsStack oparams =
        new ClientParamsStack(stack, aparams4, cparams4, rparams4, new BasicHttpParams());

    ClientParamsStack stack2 = new ClientParamsStack(aparams, cparams, rparams, oparams);

    // Act
    AbstractConnPool actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(new ClientParamsStack(stack2));

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
        new MonitoredConnectionManager("https://example.org/exampleName");
    BasicHttpParams local = new BasicHttpParams();
    DefaultedHttpParams params = new DefaultedHttpParams(local, new BasicHttpParams());

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
