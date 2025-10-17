package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class NamedConnectionPoolDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator,
   * ConnPerRoute, int)}.
   *
   * <ul>
   *   <li>Then return ConnectionCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(String,
   * ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int)"
  })
  public void testNewNamedConnectionPool_thenReturnConnectionCountIsZero() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool =
        new NamedConnectionPool(
            "https://example.org/example",
            new DefaultClientConnectionOperator(new SchemeRegistry()),
            mock(ConnPerRoute.class),
            3);

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
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator,
   * ConnPerRoute, int, long, TimeUnit)}.
   *
   * <ul>
   *   <li>Then return ConnectionCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(String,
   * ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)"
  })
  public void testNewNamedConnectionPool_thenReturnConnectionCountIsZero2() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool =
        new NamedConnectionPool(
            "https://example.org/example",
            new DefaultClientConnectionOperator(new SchemeRegistry()),
            mock(ConnPerRoute.class),
            3,
            1L,
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
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator,
   * HttpParams)}.
   *
   * <ul>
   *   <li>Then return ConnectionCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(String,
   * ClientConnectionOperator, HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(String, ClientConnectionOperator, HttpParams)"
  })
  public void testNewNamedConnectionPool_thenReturnConnectionCountIsZero3() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act
    NamedConnectionPool actualNamedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());

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
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute,
   * int)}.
   *
   * <ul>
   *   <li>Then return ConnectionCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator,
   * ConnPerRoute, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(ClientConnectionOperator, ConnPerRoute, int)"
  })
  public void testNewNamedConnectionPool_thenReturnConnectionCountIsZero4() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool =
        new NamedConnectionPool(
            new DefaultClientConnectionOperator(new SchemeRegistry()), mock(ConnPerRoute.class), 3);

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(3, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute,
   * int, long, TimeUnit)}.
   *
   * <ul>
   *   <li>Then return ConnectionCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator,
   * ConnPerRoute, int, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)"
  })
  public void testNewNamedConnectionPool_thenReturnConnectionCountIsZero5() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool =
        new NamedConnectionPool(
            new DefaultClientConnectionOperator(new SchemeRegistry()),
            mock(ConnPerRoute.class),
            3,
            1L,
            TimeUnit.NANOSECONDS);

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(3, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, HttpParams)}.
   *
   * <ul>
   *   <li>Then return ConnectionCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator,
   * HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void NamedConnectionPool.<init>(ClientConnectionOperator, HttpParams)"})
  public void testNewNamedConnectionPool_thenReturnConnectionCountIsZero6() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());

    // Act
    NamedConnectionPool actualNamedConnectionPool =
        new NamedConnectionPool(operator, new BasicHttpParams());

    // Assert
    assertEquals(0, actualNamedConnectionPool.getConnectionCount());
    assertEquals(0, actualNamedConnectionPool.getConnectionsInPool());
    assertEquals(20, actualNamedConnectionPool.getMaxTotalConnections());
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator,
   * ConnPerRoute, int)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(String,
   * ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int)"
  })
  public void testNewNamedConnectionPool_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new NamedConnectionPool(
        "https://example.org/example",
        new DefaultClientConnectionOperator(new SchemeRegistry()),
        null,
        3);
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator,
   * ConnPerRoute, int)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(String,
   * ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int)"
  })
  public void testNewNamedConnectionPool_thenThrowIllegalArgumentException2() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new NamedConnectionPool("https://example.org/example", null, mock(ConnPerRoute.class), 3);
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator,
   * ConnPerRoute, int, long, TimeUnit)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(String,
   * ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)"
  })
  public void testNewNamedConnectionPool_thenThrowIllegalArgumentException3() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new NamedConnectionPool(
        "https://example.org/example",
        new DefaultClientConnectionOperator(new SchemeRegistry()),
        null,
        3,
        1L,
        TimeUnit.NANOSECONDS);
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator,
   * ConnPerRoute, int, long, TimeUnit)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(String,
   * ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)"
  })
  public void testNewNamedConnectionPool_thenThrowIllegalArgumentException4() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new NamedConnectionPool(
        "https://example.org/example", null, mock(ConnPerRoute.class), 3, 1L, TimeUnit.NANOSECONDS);
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator,
   * HttpParams)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(String,
   * ClientConnectionOperator, HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(String, ClientConnectionOperator, HttpParams)"
  })
  public void testNewNamedConnectionPool_thenThrowIllegalArgumentException5() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new NamedConnectionPool("https://example.org/example", null, new BasicHttpParams());
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute,
   * int)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator,
   * ConnPerRoute, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(ClientConnectionOperator, ConnPerRoute, int)"
  })
  public void testNewNamedConnectionPool_thenThrowIllegalArgumentException6() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new NamedConnectionPool(new DefaultClientConnectionOperator(new SchemeRegistry()), null, 3);
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute,
   * int, long, TimeUnit)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator,
   * ConnPerRoute, int, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)"
  })
  public void testNewNamedConnectionPool_thenThrowIllegalArgumentException7() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new NamedConnectionPool(
        new DefaultClientConnectionOperator(new SchemeRegistry()),
        null,
        3,
        1L,
        TimeUnit.NANOSECONDS);
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator,
   * ConnPerRoute, int)}.
   *
   * <ul>
   *   <li>When {@code _CreateConnectionTimer}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(String,
   * ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int)"
  })
  public void testNewNamedConnectionPool_whenCreateConnectionTimer() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool =
        new NamedConnectionPool(
            "_CreateConnectionTimer",
            new DefaultClientConnectionOperator(new SchemeRegistry()),
            mock(ConnPerRoute.class),
            3);

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
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute,
   * int)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator,
   * ConnPerRoute, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(ClientConnectionOperator, ConnPerRoute, int)"
  })
  public void testNewNamedConnectionPool_whenNull_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new NamedConnectionPool(null, mock(ConnPerRoute.class), 3);
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, ConnPerRoute,
   * int, long, TimeUnit)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator,
   * ConnPerRoute, int, long, TimeUnit)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(ClientConnectionOperator, ConnPerRoute, int, long, TimeUnit)"
  })
  public void testNewNamedConnectionPool_whenNull_thenThrowIllegalArgumentException2() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new NamedConnectionPool(null, mock(ConnPerRoute.class), 3, 1L, TimeUnit.NANOSECONDS);
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator, HttpParams)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(ClientConnectionOperator,
   * HttpParams)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void NamedConnectionPool.<init>(ClientConnectionOperator, HttpParams)"})
  public void testNewNamedConnectionPool_whenNull_thenThrowIllegalArgumentException3() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    new NamedConnectionPool(null, new BasicHttpParams());
  }

  /**
   * Test {@link NamedConnectionPool#NamedConnectionPool(String, ClientConnectionOperator,
   * ConnPerRoute, int)}.
   *
   * <ul>
   *   <li>When {@code _Reuse}.
   *   <li>Then return ConnectionCount is zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#NamedConnectionPool(String,
   * ClientConnectionOperator, ConnPerRoute, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NamedConnectionPool.<init>(String, ClientConnectionOperator, ConnPerRoute, int)"
  })
  public void testNewNamedConnectionPool_whenReuse_thenReturnConnectionCountIsZero() {
    // Arrange and Act
    NamedConnectionPool actualNamedConnectionPool =
        new NamedConnectionPool(
            "_Reuse",
            new DefaultClientConnectionOperator(new SchemeRegistry()),
            mock(ConnPerRoute.class),
            3);

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
   *
   * <ul>
   *   <li>When {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#initMonitors(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void NamedConnectionPool.initMonitors(String)"})
  public void testInitMonitors_whenHttpsExampleOrgExample() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool(operator, new BasicHttpParams());

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
   * Test {@link NamedConnectionPool#initMonitors(String)}.
   *
   * <ul>
   *   <li>When {@code https://example.org/examplehttps://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#initMonitors(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void NamedConnectionPool.initMonitors(String)"})
  public void testInitMonitors_whenHttpsExampleOrgExamplehttpsExampleOrgExample() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool(operator, new BasicHttpParams());

    // Act
    namedConnectionPool.initMonitors("https://example.org/examplehttps://example.org/example");

    // Assert
    assertEquals(0L, namedConnectionPool.getCreatedEntryCount());
    assertEquals(0L, namedConnectionPool.getDeleteCount());
    assertEquals(0L, namedConnectionPool.getFreeEntryCount());
    assertEquals(0L, namedConnectionPool.getReleaseCount());
    assertEquals(0L, namedConnectionPool.getRequestsCount());
  }

  /**
   * Test {@link NamedConnectionPool#requestPoolEntry(HttpRoute, Object)}.
   *
   * <p>Method under test: {@link NamedConnectionPool#requestPoolEntry(HttpRoute, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.apache.http.impl.conn.tsccm.PoolEntryRequest NamedConnectionPool.requestPoolEntry(HttpRoute, Object)"
  })
  public void testRequestPoolEntry() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());

    // Act
    namedConnectionPool.requestPoolEntry(
        new HttpRoute(new HttpHost("https://example.org/example")), "State");

    // Assert
    assertEquals(1L, namedConnectionPool.getRequestsCount());
  }

  /**
   * Test {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}.
   *
   * <ul>
   *   <li>Given three.
   *   <li>Then calls {@link ConnPerRoute#getMaxForRoute(HttpRoute)}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BasicPoolEntry NamedConnectionPool.getFreeEntry(RouteSpecificPool, Object)"})
  public void testGetFreeEntry_givenThree_thenCallsGetMaxForRoute() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool(operator, new BasicHttpParams());

    ConnPerRoute connPerRoute = mock(ConnPerRoute.class);
    when(connPerRoute.getMaxForRoute(Mockito.<HttpRoute>any())).thenReturn(3);
    HttpRoute route = new HttpRoute(new HttpHost("https://example.org/example"));

    RouteSpecificPool rospl = new RouteSpecificPool(route, connPerRoute);

    // Act
    BasicPoolEntry actualFreeEntry = namedConnectionPool.getFreeEntry(rospl, "State");

    // Assert
    verify(connPerRoute, atLeast(1)).getMaxForRoute(isA(HttpRoute.class));
    assertNull(actualFreeEntry);
  }

  /**
   * Test {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}.
   *
   * <ul>
   *   <li>When {@link RouteSpecificPool#RouteSpecificPool(HttpRoute, int)} with route is {@link
   *       HttpRoute#HttpRoute(HttpHost)} and maxEntries is three.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BasicPoolEntry NamedConnectionPool.getFreeEntry(RouteSpecificPool, Object)"})
  public void testGetFreeEntry_whenRouteSpecificPoolWithRouteIsHttpRouteAndMaxEntriesIsThree() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool(operator, new BasicHttpParams());
    HttpRoute route = new HttpRoute(new HttpHost("https://example.org/example"));

    // Act and Assert
    assertNull(namedConnectionPool.getFreeEntry(new RouteSpecificPool(route, 3), "State"));
  }

  /**
   * Test {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}.
   *
   * <ul>
   *   <li>When {@link RouteSpecificPool#RouteSpecificPool(HttpRoute, int)} with route is {@link
   *       HttpRoute#HttpRoute(HttpHost)} and maxEntries is zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getFreeEntry(RouteSpecificPool, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BasicPoolEntry NamedConnectionPool.getFreeEntry(RouteSpecificPool, Object)"})
  public void testGetFreeEntry_whenRouteSpecificPoolWithRouteIsHttpRouteAndMaxEntriesIsZero() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool(operator, new BasicHttpParams());
    HttpRoute route = new HttpRoute(new HttpHost("https://example.org/example"));

    // Act and Assert
    assertNull(namedConnectionPool.getFreeEntry(new RouteSpecificPool(route, 0), "State"));
  }

  /**
   * Test {@link NamedConnectionPool#createEntry(RouteSpecificPool, ClientConnectionOperator)}.
   *
   * <ul>
   *   <li>Then return State is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#createEntry(RouteSpecificPool,
   * ClientConnectionOperator)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BasicPoolEntry NamedConnectionPool.createEntry(RouteSpecificPool, ClientConnectionOperator)"
  })
  public void testCreateEntry_thenReturnStateIsNull() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());
    HttpRoute route = new HttpRoute(new HttpHost("https://example.org/example"));
    RouteSpecificPool rospl = new RouteSpecificPool(route, 3);

    // Act
    BasicPoolEntry actualCreateEntryResult =
        namedConnectionPool.createEntry(
            rospl, new DefaultClientConnectionOperator(new SchemeRegistry()));

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
   * Test {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long, TimeUnit,
   * WaitingThreadAborter)}.
   *
   * <ul>
   *   <li>Then return State is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long,
   * TimeUnit, WaitingThreadAborter)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BasicPoolEntry NamedConnectionPool.getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)"
  })
  public void testGetEntryBlocking_thenReturnStateIsNull()
      throws InterruptedException, ConnectionPoolTimeoutException {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());
    HttpRoute route = new HttpRoute(new HttpHost("https://example.org/example"));

    // Act
    BasicPoolEntry actualEntryBlocking =
        namedConnectionPool.getEntryBlocking(
            route, "State", 10L, TimeUnit.NANOSECONDS, new WaitingThreadAborter());

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
   * Test {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long, TimeUnit,
   * WaitingThreadAborter)}.
   *
   * <ul>
   *   <li>Then throw {@link ConnectionPoolTimeoutException}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long,
   * TimeUnit, WaitingThreadAborter)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BasicPoolEntry NamedConnectionPool.getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)"
  })
  public void testGetEntryBlocking_thenThrowConnectionPoolTimeoutException()
      throws InterruptedException, ConnectionPoolTimeoutException {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());

    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());
    namedConnectionPool.setMaxTotalConnections(-1);
    HttpRoute route = new HttpRoute(new HttpHost("https://example.org/example"));

    // Act and Assert
    thrown.expect(ConnectionPoolTimeoutException.class);
    namedConnectionPool.getEntryBlocking(
        route, "State", 10L, TimeUnit.NANOSECONDS, new WaitingThreadAborter());
  }

  /**
   * Test {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long, TimeUnit,
   * WaitingThreadAborter)}.
   *
   * <ul>
   *   <li>When minus one.
   *   <li>Then return State is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getEntryBlocking(HttpRoute, Object, long,
   * TimeUnit, WaitingThreadAborter)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BasicPoolEntry NamedConnectionPool.getEntryBlocking(HttpRoute, Object, long, TimeUnit, WaitingThreadAborter)"
  })
  public void testGetEntryBlocking_whenMinusOne_thenReturnStateIsNull()
      throws InterruptedException, ConnectionPoolTimeoutException {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());
    HttpRoute route = new HttpRoute(new HttpHost("https://example.org/example"));

    // Act
    BasicPoolEntry actualEntryBlocking =
        namedConnectionPool.getEntryBlocking(
            route, "State", -1L, TimeUnit.NANOSECONDS, new WaitingThreadAborter());

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
   *
   * <p>Method under test: {@link NamedConnectionPool#deleteEntry(BasicPoolEntry)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void NamedConnectionPool.deleteEntry(BasicPoolEntry)"})
  public void testDeleteEntry() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());
    DefaultClientConnectionOperator op = new DefaultClientConnectionOperator(new SchemeRegistry());
    HttpRoute route = new HttpRoute(new HttpHost("https://example.org/example"));

    BasicPoolEntry entry = new BasicPoolEntry(op, route);

    // Act
    namedConnectionPool.deleteEntry(entry);

    // Assert
    assertEquals(-1, namedConnectionPool.getConnectionCount());
    assertEquals(-1, namedConnectionPool.getConnectionsInPool());
    assertEquals(1L, namedConnectionPool.getDeleteCount());
  }

  /**
   * Test {@link NamedConnectionPool#getFreeEntryCount()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getFreeEntryCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long NamedConnectionPool.getFreeEntryCount()"})
  public void testGetFreeEntryCount_thenReturnZero() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());

    // Act and Assert
    assertEquals(0L, namedConnectionPool.getFreeEntryCount());
  }

  /**
   * Test {@link NamedConnectionPool#getCreatedEntryCount()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getCreatedEntryCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long NamedConnectionPool.getCreatedEntryCount()"})
  public void testGetCreatedEntryCount_thenReturnZero() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());

    // Act and Assert
    assertEquals(0L, namedConnectionPool.getCreatedEntryCount());
  }

  /**
   * Test {@link NamedConnectionPool#getRequestsCount()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getRequestsCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long NamedConnectionPool.getRequestsCount()"})
  public void testGetRequestsCount_thenReturnZero() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());

    // Act and Assert
    assertEquals(0L, namedConnectionPool.getRequestsCount());
  }

  /**
   * Test {@link NamedConnectionPool#getReleaseCount()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getReleaseCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long NamedConnectionPool.getReleaseCount()"})
  public void testGetReleaseCount_thenReturnZero() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());

    // Act and Assert
    assertEquals(0L, namedConnectionPool.getReleaseCount());
  }

  /**
   * Test {@link NamedConnectionPool#getDeleteCount()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link NamedConnectionPool#getDeleteCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long NamedConnectionPool.getDeleteCount()"})
  public void testGetDeleteCount_thenReturnZero() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool("https://example.org/example", operator, new BasicHttpParams());

    // Act and Assert
    assertEquals(0L, namedConnectionPool.getDeleteCount());
  }

  /**
   * Test {@link NamedConnectionPool#getConnectionCount()}.
   *
   * <p>Method under test: {@link NamedConnectionPool#getConnectionCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int NamedConnectionPool.getConnectionCount()"})
  public void testGetConnectionCount() {
    // Arrange
    DefaultClientConnectionOperator operator =
        new DefaultClientConnectionOperator(new SchemeRegistry());
    NamedConnectionPool namedConnectionPool =
        new NamedConnectionPool(operator, new BasicHttpParams());

    // Act and Assert
    assertEquals(0, namedConnectionPool.getConnectionCount());
  }
}
