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
import org.apache.http.impl.conn.tsccm.AbstractConnPool;
import org.apache.http.impl.conn.tsccm.ConnPoolByRoute;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
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
        new MonitoredConnectionManager(
            "\"TestConnectionManager\"", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
    monitoredConnectionManager.setMaxForRoute(
        new HttpRoute(new HttpHost("\"www.example.com\"")), 3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
        new MonitoredConnectionManager(" ", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
        new MonitoredConnectionManager(":", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
        new MonitoredConnectionManager("Name", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
        new MonitoredConnectionManager(",", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
        new MonitoredConnectionManager(" ", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
        new MonitoredConnectionManager(" ", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxForRoute(
        new HttpRoute(new HttpHost("\"www.example.com\"")), 3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit10() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(":", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit11() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("Name", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxTotal(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit12() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(",", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit13() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(" ", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(7);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit14() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(" ", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(12);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit15() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(":", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(4);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit16() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(":", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(13);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit17() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(":", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(2);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit18() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(":", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(6);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit19() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    monitoredConnectionManager.setDefaultMaxPerRoute(7);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit20() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("\"TestConnectionManager\"");
    monitoredConnectionManager.setDefaultMaxPerRoute(12);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit21() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager("\"TestConnectionManager\"", new SchemeRegistry());
    monitoredConnectionManager.setDefaultMaxPerRoute(12);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit22() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(" ", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(6);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit23() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(",", new SchemeRegistry(), 2L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(3);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit24() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(",", new SchemeRegistry(), 2L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(1);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit25() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(" ", new SchemeRegistry(), 0L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(7);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

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
  public void testCreateConnectionPoolWithConnTTLConnTTLTimeUnit26() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager =
        new MonitoredConnectionManager(
            " cannot be empty", new SchemeRegistry(), 20L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setDefaultMaxPerRoute(12);

    // Act
    ConnPoolByRoute actualCreateConnectionPoolResult =
        monitoredConnectionManager.createConnectionPool(1L, TimeUnit.MICROSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Test {@link MonitoredConnectionManager#createConnectionPool(HttpParams)} with {@code params}.
   *
   * <ul>
   *   <li>Then return {@link NamedConnectionPool}.
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
  public void testCreateConnectionPoolWithParams_thenReturnNamedConnectionPool() {
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
        new MonitoredConnectionManager(
            "\"TestConnectionManager\"", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxForRoute(
        new HttpRoute(new HttpHost("\"www.example.com\"")), 3);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("\"www.example.com\"")), "State");

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
        new MonitoredConnectionManager("42", new SchemeRegistry(), 1L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxForRoute(
        new HttpRoute(new HttpHost("\"www.example.com\"")), 3);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("\"www.example.com\"")), "State");

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
        new MonitoredConnectionManager(
            "\"TestConnectionManager\"", new SchemeRegistry(), 0L, TimeUnit.NANOSECONDS);
    monitoredConnectionManager.setMaxForRoute(
        new HttpRoute(new HttpHost("\"www.example.com\"")), 3);

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("\"www.example.com\"")), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }
}
