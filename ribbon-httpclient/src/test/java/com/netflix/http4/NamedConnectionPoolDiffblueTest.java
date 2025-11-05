package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;
import org.apache.http.impl.conn.tsccm.RouteSpecificPool;
import org.apache.http.impl.conn.tsccm.WaitingThreadAborter;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.junit.Test;
import org.mockito.Mockito;

public class NamedConnectionPoolDiffblueTest {
  /**
   * Method under test: {@link NamedConnectionPool#initMonitors(String)}
   */
  @Test
  public void testInitMonitors() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool(operator, new BasicHttpParams());

    // Act
    namedConnectionPool.initMonitors("https://example.org/example");

    // Assert
    assertEquals(0L, namedConnectionPool.getCreatedEntryCount());
    assertEquals(0L, namedConnectionPool.getDeleteCount());
    assertEquals(0L, namedConnectionPool.getFreeEntryCount());
    assertEquals(0L, namedConnectionPool.getReleaseCount());
    assertEquals(0L, namedConnectionPool.getRequestsCount());
  }

  /**
   * Method under test: {@link NamedConnectionPool#initMonitors(String)}
   */
  @Test
  public void testInitMonitors2() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry(),
        mock(DnsResolver.class));

    NamedConnectionPool namedConnectionPool = new NamedConnectionPool(operator, new BasicHttpParams());

    // Act
    namedConnectionPool.initMonitors("https://example.org/example");

    // Assert
    assertEquals(0L, namedConnectionPool.getCreatedEntryCount());
    assertEquals(0L, namedConnectionPool.getDeleteCount());
    assertEquals(0L, namedConnectionPool.getFreeEntryCount());
    assertEquals(0L, namedConnectionPool.getReleaseCount());
    assertEquals(0L, namedConnectionPool.getRequestsCount());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#requestPoolEntry(HttpRoute, Object)}
   */
  @Test
  public void testRequestPoolEntry() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool("https://example.org/example", operator,
        new BasicHttpParams());

    // Act
    namedConnectionPool.requestPoolEntry(new HttpRoute(new HttpHost("https://example.org/example")), "State");

    // Assert
    assertEquals(1L, namedConnectionPool.getRequestsCount());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}
   */
  @Test
  public void testGetFreeEntry() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool(operator, new BasicHttpParams());

    // Act and Assert
    assertNull(namedConnectionPool
        .getFreeEntry(new RouteSpecificPool(new HttpRoute(new HttpHost("https://example.org/example")), 3), "State"));
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}
   */
  @Test
  public void testGetFreeEntry2() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry(),
        mock(DnsResolver.class));

    NamedConnectionPool namedConnectionPool = new NamedConnectionPool(operator, new BasicHttpParams());

    // Act and Assert
    assertNull(namedConnectionPool
        .getFreeEntry(new RouteSpecificPool(new HttpRoute(new HttpHost("https://example.org/example")), 3), "State"));
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}
   */
  @Test
  public void testGetFreeEntry3() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool(operator, new BasicHttpParams());

    // Act and Assert
    assertNull(namedConnectionPool
        .getFreeEntry(new RouteSpecificPool(new HttpRoute(new HttpHost("https://example.org/example")), 0), "State"));
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}
   */
  @Test
  public void testGetFreeEntry4() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool(operator, new BasicHttpParams());
    ConnPerRoute connPerRoute = mock(ConnPerRoute.class);
    when(connPerRoute.getMaxForRoute(Mockito.<HttpRoute>any())).thenReturn(3);

    // Act
    BasicPoolEntry actualFreeEntry = namedConnectionPool.getFreeEntry(
        new RouteSpecificPool(new HttpRoute(new HttpHost("https://example.org/example")), connPerRoute), "State");

    // Assert
    verify(connPerRoute, atLeast(1)).getMaxForRoute(isA(HttpRoute.class));
    assertNull(actualFreeEntry);
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#createEntry(RouteSpecificPool, ClientConnectionOperator)}
   */
  @Test
  public void testCreateEntry() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool("https://example.org/example", operator,
        new BasicHttpParams());
    RouteSpecificPool rospl = new RouteSpecificPool(new HttpRoute(new HttpHost("https://example.org/example")), 3);

    // Act
    BasicPoolEntry actualCreateEntryResult = namedConnectionPool.createEntry(rospl,
        new DefaultClientConnectionOperator(new SchemeRegistry()));

    // Assert
    assertNull(actualCreateEntryResult.getState());
    assertEquals(0L, actualCreateEntryResult.getUpdated());
    assertEquals(1, namedConnectionPool.getConnectionCount());
    assertEquals(1, namedConnectionPool.getConnectionsInPool());
    assertEquals(1, rospl.getEntryCount());
    assertEquals(1L, namedConnectionPool.getCreatedEntryCount());
    assertEquals(2, rospl.getCapacity());
    assertFalse(rospl.isUnused());
    assertEquals(Long.MAX_VALUE, actualCreateEntryResult.getExpiry());
    assertEquals(Long.MAX_VALUE, actualCreateEntryResult.getValidUntil());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)}
   */
  @Test
  public void testGetEntryBlocking() throws InterruptedException, ConnectionPoolTimeoutException {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool("https://example.org/example", operator,
        new BasicHttpParams());
    HttpRoute route = new HttpRoute(new HttpHost("https://example.org/example"));

    // Act
    BasicPoolEntry actualEntryBlocking = namedConnectionPool.getEntryBlocking(route, "State", 10L, TimeUnit.NANOSECONDS,
        new WaitingThreadAborter());

    // Assert
    assertNull(actualEntryBlocking.getState());
    assertEquals(0L, actualEntryBlocking.getUpdated());
    assertEquals(1, namedConnectionPool.getConnectionCount());
    assertEquals(1, namedConnectionPool.getConnectionsInPool());
    assertEquals(1L, namedConnectionPool.getCreatedEntryCount());
    assertEquals(Long.MAX_VALUE, actualEntryBlocking.getExpiry());
    assertEquals(Long.MAX_VALUE, actualEntryBlocking.getValidUntil());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)}
   */
  @Test
  public void testGetEntryBlocking2() throws InterruptedException, ConnectionPoolTimeoutException {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool("https://example.org/example", operator,
        new BasicHttpParams());
    HttpRoute route = new HttpRoute(new HttpHost("https://example.org/example"));

    // Act
    BasicPoolEntry actualEntryBlocking = namedConnectionPool.getEntryBlocking(route, "State", -1L, TimeUnit.NANOSECONDS,
        new WaitingThreadAborter());

    // Assert
    assertNull(actualEntryBlocking.getState());
    assertEquals(0L, actualEntryBlocking.getUpdated());
    assertEquals(1, namedConnectionPool.getConnectionCount());
    assertEquals(1, namedConnectionPool.getConnectionsInPool());
    assertEquals(1L, namedConnectionPool.getCreatedEntryCount());
    assertEquals(Long.MAX_VALUE, actualEntryBlocking.getExpiry());
    assertEquals(Long.MAX_VALUE, actualEntryBlocking.getValidUntil());
  }

  /**
   * Method under test: {@link NamedConnectionPool#deleteEntry(BasicPoolEntry)}
   */
  @Test
  public void testDeleteEntry() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool("https://example.org/example", operator,
        new BasicHttpParams());
    DefaultClientConnectionOperator op = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act
    namedConnectionPool.deleteEntry(new BasicPoolEntry(op, new HttpRoute(new HttpHost("https://example.org/example"))));

    // Assert
    assertEquals(-1, namedConnectionPool.getConnectionCount());
    assertEquals(-1, namedConnectionPool.getConnectionsInPool());
    assertEquals(1L, namedConnectionPool.getDeleteCount());
  }

  /**
   * Method under test: {@link NamedConnectionPool#getFreeEntryCount()}
   */
  @Test
  public void testGetFreeEntryCount() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0L,
        (new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams())).getFreeEntryCount());
  }

  /**
   * Method under test: {@link NamedConnectionPool#getCreatedEntryCount()}
   */
  @Test
  public void testGetCreatedEntryCount() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0L, (new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams()))
        .getCreatedEntryCount());
  }

  /**
   * Method under test: {@link NamedConnectionPool#getRequestsCount()}
   */
  @Test
  public void testGetRequestsCount() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0L,
        (new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams())).getRequestsCount());
  }

  /**
   * Method under test: {@link NamedConnectionPool#getReleaseCount()}
   */
  @Test
  public void testGetReleaseCount() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0L,
        (new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams())).getReleaseCount());
  }

  /**
   * Method under test: {@link NamedConnectionPool#getDeleteCount()}
   */
  @Test
  public void testGetDeleteCount() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0L,
        (new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams())).getDeleteCount());
  }

  /**
   * Method under test: {@link NamedConnectionPool#getConnectionCount()}
   */
  @Test
  public void testGetConnectionCount() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0, (new NamedConnectionPool(operator, new BasicHttpParams())).getConnectionCount());
  }

  /**
   * Method under test: {@link NamedConnectionPool#getConnectionCount()}
   */
  @Test
  public void testGetConnectionCount2() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry(),
        mock(DnsResolver.class));

    // Act and Assert
    assertEquals(0, (new NamedConnectionPool(operator, new BasicHttpParams())).getConnectionCount());
  }

  /**
   * Method under test: {@link NamedConnectionPool#shutdown()}
   */
  @Test
  public void testShutdown() {
    // Arrange
    ClientConnectionOperator op = mock(ClientConnectionOperator.class);
    when(op.createConnection()).thenReturn(null);
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    NamedConnectionPool namedConnectionPool = new NamedConnectionPool("https://example.org/example", operator,
        new BasicHttpParams());
    namedConnectionPool
        .createEntry(new RouteSpecificPool(new HttpRoute(new HttpHost("https://example.org/example")), 3), op);

    // Act
    namedConnectionPool.shutdown();

    // Assert
    verify(op).createConnection();
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  public void testNewNamedConnectionPool() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool("https://example.org/example",
        new DefaultClientConnectionOperator(new SchemeRegistry()), mock(ConnPerRoute.class), 3);

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(0L, actualNamedConnectionPool.getCreatedEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getDeleteCount());
    assertEquals(0L, actualNamedConnectionPool.getFreeEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getReleaseCount());
    assertEquals(0L, actualNamedConnectionPool.getRequestsCount());
    assertEquals(3, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  public void testNewNamedConnectionPool2() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool("_Reuse",
        new DefaultClientConnectionOperator(new SchemeRegistry()), mock(ConnPerRoute.class), 3);

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(0L, actualNamedConnectionPool.getCreatedEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getDeleteCount());
    assertEquals(0L, actualNamedConnectionPool.getFreeEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getReleaseCount());
    assertEquals(0L, actualNamedConnectionPool.getRequestsCount());
    assertEquals(3, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  public void testNewNamedConnectionPool3() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool("_CreateConnectionTimer",
        new DefaultClientConnectionOperator(new SchemeRegistry()), mock(ConnPerRoute.class), 3);

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(0L, actualNamedConnectionPool.getCreatedEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getDeleteCount());
    assertEquals(0L, actualNamedConnectionPool.getFreeEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getReleaseCount());
    assertEquals(0L, actualNamedConnectionPool.getRequestsCount());
    assertEquals(3, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)}
   */
  @Test
  public void testNewNamedConnectionPool4() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool("https://example.org/example",
        new DefaultClientConnectionOperator(new SchemeRegistry()), mock(ConnPerRoute.class), 3, 1L,
        TimeUnit.NANOSECONDS);

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(0L, actualNamedConnectionPool.getCreatedEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getDeleteCount());
    assertEquals(0L, actualNamedConnectionPool.getFreeEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getReleaseCount());
    assertEquals(0L, actualNamedConnectionPool.getRequestsCount());
    assertEquals(3, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, HttpParams)}
   */
  @Test
  public void testNewNamedConnectionPool5() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool("https://example.org/example", operator,
        new BasicHttpParams());

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(0L, actualNamedConnectionPool.getCreatedEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getDeleteCount());
    assertEquals(0L, actualNamedConnectionPool.getFreeEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getReleaseCount());
    assertEquals(0L, actualNamedConnectionPool.getRequestsCount());
    assertEquals(20, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, HttpParams)}
   */
  @Test
  public void testNewNamedConnectionPool6() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry(),
        mock(DnsResolver.class));

    // Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool("https://example.org/example", operator,
        new BasicHttpParams());

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(0L, actualNamedConnectionPool.getCreatedEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getDeleteCount());
    assertEquals(0L, actualNamedConnectionPool.getFreeEntryCount());
    assertEquals(0L, actualNamedConnectionPool.getReleaseCount());
    assertEquals(0L, actualNamedConnectionPool.getRequestsCount());
    assertEquals(20, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  public void testNewNamedConnectionPool7() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool(
        new DefaultClientConnectionOperator(new SchemeRegistry()), mock(ConnPerRoute.class), 3);

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(3, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)}
   */
  @Test
  public void testNewNamedConnectionPool8() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool(
        new DefaultClientConnectionOperator(new SchemeRegistry()), mock(ConnPerRoute.class), 3, 1L,
        TimeUnit.NANOSECONDS);

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(3, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, HttpParams)}
   */
  @Test
  public void testNewNamedConnectionPool9() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool(operator, new BasicHttpParams());

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(20, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, HttpParams)}
   */
  @Test
  public void testNewNamedConnectionPool10() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry(),
        mock(DnsResolver.class));

    // Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool(operator, new BasicHttpParams());

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(20, actualNamedConnectionPool.getMaxTotalConnections());
  }
}
