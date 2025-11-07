package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectionPoolTimeoutException;
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
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class NamedConnectionPoolDiffblueTest {
  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)}.
   * <ul>
   *   <li>Then return ConnectionCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)"})
  public void testNewNamedConnectionPool_thenReturnConnectionCountIsZero() {
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
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, HttpParams)}.
   * <ul>
   *   <li>Then return ConnectionCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NamedConnectionPool.<init>(String, ClientConnectionOperator, HttpParams)"})
  public void testNewNamedConnectionPool_thenReturnConnectionCountIsZero2() {
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
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, HttpParams)}.
   * <ul>
   *   <li>Then return ConnectionCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, HttpParams)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NamedConnectionPool.<init>(ClientConnectionOperator, HttpParams)"})
  public void testNewNamedConnectionPool_thenReturnConnectionCountIsZero3() {
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
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute, int)}.
   * <ul>
   *   <li>When {@link ConnPerRoute}.</li>
   *   <li>Then return ConnectionCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NamedConnectionPool.<init>(ClientConnectionOperator, ConnPerRoute, int)"})
  public void testNewNamedConnectionPool_whenConnPerRoute_thenReturnConnectionCountIsZero() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool = new NamedConnectionPool(
        new DefaultClientConnectionOperator(new SchemeRegistry()), mock(ConnPerRoute.class), 3);

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(3, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)}.
   * <ul>
   *   <li>When {@link ConnPerRoute}.</li>
   *   <li>Then return ConnectionCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NamedConnectionPool.<init>(ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)"})
  public void testNewNamedConnectionPool_whenConnPerRoute_thenReturnConnectionCountIsZero2() {
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
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int)}.
   * <ul>
   *   <li>When {@code _CreateConnectionTimer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int)"})
  public void testNewNamedConnectionPool_whenCreateConnectionTimer() {
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
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int)}.
   * <ul>
   *   <li>When {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int)"})
  public void testNewNamedConnectionPool_whenHttpsExampleOrgExample() {
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
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int)}.
   * <ul>
   *   <li>When {@code _Reuse}.</li>
   *   <li>Then return ConnectionCount is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int)"})
  public void testNewNamedConnectionPool_whenReuse_thenReturnConnectionCountIsZero() {
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
   * Test {@link NamedConnectionPool#initMonitors(String)}.
   * <p>
   * Method under test: {@link NamedConnectionPool#initMonitors(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NamedConnectionPool.initMonitors(String)"})
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
   * Test {@link NamedConnectionPool#requestPoolEntry(HttpRoute, Object)}.
   * <p>
   * Method under test: {@link NamedConnectionPool#requestPoolEntry(HttpRoute, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.apache.http.impl.conn.tsccm.PoolEntryRequest NamedConnectionPool.requestPoolEntry(HttpRoute, Object)"})
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
   * Test {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}.
   * <ul>
   *   <li>Given three.</li>
   *   <li>Then calls {@link ConnPerRoute#getMaxForRoute(HttpRoute)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BasicPoolEntry NamedConnectionPool.getFreeEntry(RouteSpecificPool, Object)"})
  public void testGetFreeEntry_givenThree_thenCallsGetMaxForRoute() {
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
   * Test {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}.
   * <ul>
   *   <li>When {@link RouteSpecificPool#RouteSpecificPool(HttpRoute, int)} with route is {@link HttpRoute#HttpRoute(HttpHost)} and maxEntries is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BasicPoolEntry NamedConnectionPool.getFreeEntry(RouteSpecificPool, Object)"})
  public void testGetFreeEntry_whenRouteSpecificPoolWithRouteIsHttpRouteAndMaxEntriesIsThree() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool(operator, new BasicHttpParams());

    // Act and Assert
    assertNull(namedConnectionPool
        .getFreeEntry(new RouteSpecificPool(new HttpRoute(new HttpHost("https://example.org/example")), 3), "State"));
  }

  /**
   * Test {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}.
   * <ul>
   *   <li>When {@link RouteSpecificPool#RouteSpecificPool(HttpRoute, int)} with route is {@link HttpRoute#HttpRoute(HttpHost)} and maxEntries is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BasicPoolEntry NamedConnectionPool.getFreeEntry(RouteSpecificPool, Object)"})
  public void testGetFreeEntry_whenRouteSpecificPoolWithRouteIsHttpRouteAndMaxEntriesIsZero() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool = new NamedConnectionPool(operator, new BasicHttpParams());

    // Act and Assert
    assertNull(namedConnectionPool
        .getFreeEntry(new RouteSpecificPool(new HttpRoute(new HttpHost("https://example.org/example")), 0), "State"));
  }

  /**
   * Test {@link NamedConnectionPool#createEntry(RouteSpecificPool, ClientConnectionOperator)}.
   * <ul>
   *   <li>Then return State is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#createEntry(RouteSpecificPool, ClientConnectionOperator)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BasicPoolEntry NamedConnectionPool.createEntry(RouteSpecificPool, ClientConnectionOperator)"})
  public void testCreateEntry_thenReturnStateIsNull() {
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
   * Test {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)}.
   * <ul>
   *   <li>Then return State is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "BasicPoolEntry NamedConnectionPool.getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)"})
  public void testGetEntryBlocking_thenReturnStateIsNull() throws InterruptedException, ConnectionPoolTimeoutException {
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
   * Test {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return State is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "BasicPoolEntry NamedConnectionPool.getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)"})
  public void testGetEntryBlocking_whenMinusOne_thenReturnStateIsNull()
      throws InterruptedException, ConnectionPoolTimeoutException {
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
   * Test {@link NamedConnectionPool#deleteEntry(BasicPoolEntry)}.
   * <p>
   * Method under test: {@link NamedConnectionPool#deleteEntry(BasicPoolEntry)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NamedConnectionPool.deleteEntry(BasicPoolEntry)"})
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
   * Test {@link NamedConnectionPool#getFreeEntryCount()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#getFreeEntryCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long NamedConnectionPool.getFreeEntryCount()"})
  public void testGetFreeEntryCount_thenReturnZero() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0L,
        (new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams())).getFreeEntryCount());
  }

  /**
   * Test {@link NamedConnectionPool#getCreatedEntryCount()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#getCreatedEntryCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long NamedConnectionPool.getCreatedEntryCount()"})
  public void testGetCreatedEntryCount_thenReturnZero() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0L, (new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams()))
        .getCreatedEntryCount());
  }

  /**
   * Test {@link NamedConnectionPool#getRequestsCount()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#getRequestsCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long NamedConnectionPool.getRequestsCount()"})
  public void testGetRequestsCount_thenReturnZero() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0L,
        (new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams())).getRequestsCount());
  }

  /**
   * Test {@link NamedConnectionPool#getReleaseCount()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#getReleaseCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long NamedConnectionPool.getReleaseCount()"})
  public void testGetReleaseCount_thenReturnZero() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0L,
        (new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams())).getReleaseCount());
  }

  /**
   * Test {@link NamedConnectionPool#getDeleteCount()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#getDeleteCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long NamedConnectionPool.getDeleteCount()"})
  public void testGetDeleteCount_thenReturnZero() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0L,
        (new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams())).getDeleteCount());
  }

  /**
   * Test {@link NamedConnectionPool#getConnectionCount()}.
   * <p>
   * Method under test: {@link NamedConnectionPool#getConnectionCount()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int NamedConnectionPool.getConnectionCount()"})
  public void testGetConnectionCount() {
    // Arrange
    DefaultClientConnectionOperator operator = new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act and Assert
    assertEquals(0, (new NamedConnectionPool(operator, new BasicHttpParams())).getConnectionCount());
  }

  /**
   * Test {@link NamedConnectionPool#shutdown()}.
   * <ul>
   *   <li>Then calls {@link ClientConnectionOperator#createConnection()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NamedConnectionPool#shutdown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NamedConnectionPool.shutdown()"})
  public void testShutdown_thenCallsCreateConnection() {
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
}
