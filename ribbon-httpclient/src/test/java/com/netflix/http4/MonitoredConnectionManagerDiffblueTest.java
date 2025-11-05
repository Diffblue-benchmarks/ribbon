package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.ClientParamsStack;
import org.apache.http.impl.conn.tsccm.AbstractConnPool;
import org.apache.http.impl.conn.tsccm.ConnPoolByRoute;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.junit.Test;

public class MonitoredConnectionManagerDiffblueTest {
  /**
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  public void testCreateConnectionPool() {
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
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  public void testCreateConnectionPool2() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult = (new MonitoredConnectionManager("Name")).createConnectionPool(1L,
        TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  public void testCreateConnectionPool3() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult = (new MonitoredConnectionManager(
        "com.netflix.http4.NamedConnectionPool")).createConnectionPool(1L, TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(long, TimeUnit)}
   */
  @Test
  public void testCreateConnectionPool4() {
    // Arrange and Act
    ConnPoolByRoute actualCreateConnectionPoolResult = (new MonitoredConnectionManager("=")).createConnectionPool(1L,
        TimeUnit.NANOSECONDS);

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, actualCreateConnectionPoolResult.getConnectionsInPool());
    assertEquals(20, actualCreateConnectionPoolResult.getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool5() {
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
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool6() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(" ");

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
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool7() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("MonitorConfig{name=");

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
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool8() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("separator");

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
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool9() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("=");

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
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool10() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(" ");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();
    ClientParamsStack stack = new ClientParamsStack(aparams2, cparams2, rparams2, new BasicHttpParams());

    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams3 = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager
        .createConnectionPool(new ClientParamsStack(aparams, cparams, rparams,
            new ClientParamsStack(stack, aparams3, cparams3, rparams3, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool11() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("value");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();
    ClientParamsStack stack = new ClientParamsStack(aparams2, cparams2, rparams2, new BasicHttpParams());

    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams3 = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager
        .createConnectionPool(new ClientParamsStack(aparams, cparams, rparams,
            new ClientParamsStack(stack, aparams3, cparams3, rparams3, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool12() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("level");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();
    ClientParamsStack stack = new ClientParamsStack(aparams2, cparams2, rparams2, new BasicHttpParams());

    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams3 = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager
        .createConnectionPool(new ClientParamsStack(aparams, cparams, rparams,
            new ClientParamsStack(stack, aparams3, cparams3, rparams3, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool13() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("Name");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();
    ClientParamsStack stack = new ClientParamsStack(aparams2, cparams2, rparams2, new BasicHttpParams());

    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams3 = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager
        .createConnectionPool(new ClientParamsStack(aparams, cparams, rparams,
            new ClientParamsStack(stack, aparams3, cparams3, rparams3, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool14() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(" ");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    ClientParamsStack aparams2 = new ClientParamsStack(aparams, cparams, rparams, new BasicHttpParams());

    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams3 = new BasicHttpParams();
    BasicHttpParams rparams3 = new BasicHttpParams();
    ClientParamsStack stack = new ClientParamsStack(aparams3, cparams3, rparams3, new BasicHttpParams());

    BasicHttpParams aparams4 = new BasicHttpParams();
    BasicHttpParams cparams4 = new BasicHttpParams();
    BasicHttpParams rparams4 = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager
        .createConnectionPool(new ClientParamsStack(aparams2, cparams2, rparams2,
            new ClientParamsStack(stack, aparams4, cparams4, rparams4, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#createConnectionPool(HttpParams)}
   */
  @Test
  public void testCreateConnectionPool15() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(" ");
    BasicHttpParams aparams = new BasicHttpParams();
    BasicHttpParams cparams = new BasicHttpParams();
    BasicHttpParams rparams = new BasicHttpParams();
    BasicHttpParams aparams2 = new BasicHttpParams();
    BasicHttpParams aparams3 = new BasicHttpParams();
    BasicHttpParams cparams2 = new BasicHttpParams();
    BasicHttpParams rparams2 = new BasicHttpParams();
    ClientParamsStack cparams3 = new ClientParamsStack(aparams3, cparams2, rparams2, new BasicHttpParams());

    BasicHttpParams rparams3 = new BasicHttpParams();
    ClientParamsStack stack = new ClientParamsStack(aparams2, cparams3, rparams3, new BasicHttpParams());

    BasicHttpParams aparams4 = new BasicHttpParams();
    BasicHttpParams cparams4 = new BasicHttpParams();
    BasicHttpParams rparams4 = new BasicHttpParams();

    // Act
    AbstractConnPool actualCreateConnectionPoolResult = monitoredConnectionManager
        .createConnectionPool(new ClientParamsStack(aparams, cparams, rparams,
            new ClientParamsStack(stack, aparams4, cparams4, rparams4, new BasicHttpParams())));

    // Assert
    assertTrue(actualCreateConnectionPoolResult instanceof NamedConnectionPool);
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionCount());
    assertEquals(0, ((NamedConnectionPool) actualCreateConnectionPoolResult).getConnectionsInPool());
    assertEquals(20, ((NamedConnectionPool) actualCreateConnectionPoolResult).getMaxTotalConnections());
  }

  /**
   * Method under test: {@link MonitoredConnectionManager#getConnectionPool()}
   */
  @Test
  public void testGetConnectionPool() {
    // Arrange, Act and Assert
    assertTrue((new MonitoredConnectionManager("https://example.org/example"))
        .getConnectionPool() instanceof NamedConnectionPool);
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
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
   * Method under test:
   * {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  public void testRequestConnection2() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example");

    // Act
    monitoredConnectionManager.requestConnection(
        new HttpRoute(new HttpHost("https://example.org/example"), mock(InetAddress.class), true), "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  public void testRequestConnection3() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("Name");

    // Act
    monitoredConnectionManager.requestConnection(null, "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  public void testRequestConnection4() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager("value");

    // Act
    monitoredConnectionManager.requestConnection(null, "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#requestConnection(HttpRoute, Object)}
   */
  @Test
  public void testRequestConnection5() {
    // Arrange
    MonitoredConnectionManager monitoredConnectionManager = new MonitoredConnectionManager(
        "com.netflix.http4.NamedConnectionPool");

    // Act
    monitoredConnectionManager.requestConnection(null, "State");

    // Assert
    ConnPoolByRoute connectionPool = monitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    assertEquals(1L, ((NamedConnectionPool) connectionPool).getRequestsCount());
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  public void testNewMonitoredConnectionManager() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    List<String> schemeNames = actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames();
    assertEquals(2, schemeNames.size());
    assertEquals("http", schemeNames.get(0));
    assertEquals("https", schemeNames.get(1));
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
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  public void testNewMonitoredConnectionManager2() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(" ");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    List<String> schemeNames = actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames();
    assertEquals(2, schemeNames.size());
    assertEquals("http", schemeNames.get(0));
    assertEquals("https", schemeNames.get(1));
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
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  public void testNewMonitoredConnectionManager3() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(" _Reuse");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    List<String> schemeNames = actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames();
    assertEquals(2, schemeNames.size());
    assertEquals("http", schemeNames.get(0));
    assertEquals("https", schemeNames.get(1));
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
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  public void testNewMonitoredConnectionManager4() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(",");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    List<String> schemeNames = actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames();
    assertEquals(2, schemeNames.size());
    assertEquals("http", schemeNames.get(0));
    assertEquals("https", schemeNames.get(1));
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
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  public void testNewMonitoredConnectionManager5() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("parts");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    List<String> schemeNames = actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames();
    assertEquals(2, schemeNames.size());
    assertEquals("http", schemeNames.get(0));
    assertEquals("https", schemeNames.get(1));
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
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  public void testNewMonitoredConnectionManager6() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(
        "DefaultPublishingPolicy");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    List<String> schemeNames = actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames();
    assertEquals(2, schemeNames.size());
    assertEquals("http", schemeNames.get(0));
    assertEquals("https", schemeNames.get(1));
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
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String)}
   */
  @Test
  public void testNewMonitoredConnectionManager7() {
    // Arrange and Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("statistic");

    // Assert
    ConnPoolByRoute connectionPool = actualMonitoredConnectionManager.getConnectionPool();
    assertTrue(connectionPool instanceof NamedConnectionPool);
    List<String> schemeNames = actualMonitoredConnectionManager.getSchemeRegistry().getSchemeNames();
    assertEquals(2, schemeNames.size());
    assertEquals("http", schemeNames.get(0));
    assertEquals("https", schemeNames.get(1));
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
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry)}
   */
  @Test
  public void testNewMonitoredConnectionManager8() {
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
    SchemeRegistry schemeRegistry = actualMonitoredConnectionManager.getSchemeRegistry();
    assertTrue(schemeRegistry.getSchemeNames().isEmpty());
    assertSame(schreg, schemeRegistry);
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}
   */
  @Test
  public void testNewMonitoredConnectionManager9() {
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
    SchemeRegistry schemeRegistry = actualMonitoredConnectionManager.getSchemeRegistry();
    assertTrue(schemeRegistry.getSchemeNames().isEmpty());
    assertSame(schreg, schemeRegistry);
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}
   */
  @Test
  public void testNewMonitoredConnectionManager10() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("tagArray", schreg, 1L,
        TimeUnit.NANOSECONDS);

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
    SchemeRegistry schemeRegistry = actualMonitoredConnectionManager.getSchemeRegistry();
    assertTrue(schemeRegistry.getSchemeNames().isEmpty());
    assertSame(schreg, schemeRegistry);
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}
   */
  @Test
  public void testNewMonitoredConnectionManager11() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("config", schreg, 1L,
        TimeUnit.NANOSECONDS);

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
    SchemeRegistry schemeRegistry = actualMonitoredConnectionManager.getSchemeRegistry();
    assertTrue(schemeRegistry.getSchemeNames().isEmpty());
    assertSame(schreg, schemeRegistry);
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}
   */
  @Test
  public void testNewMonitoredConnectionManager12() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("_CreateNew", schreg,
        1L, TimeUnit.NANOSECONDS);

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
    SchemeRegistry schemeRegistry = actualMonitoredConnectionManager.getSchemeRegistry();
    assertTrue(schemeRegistry.getSchemeNames().isEmpty());
    assertSame(schreg, schemeRegistry);
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}
   */
  @Test
  public void testNewMonitoredConnectionManager13() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("Name", schreg, 1L,
        TimeUnit.NANOSECONDS);

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
    SchemeRegistry schemeRegistry = actualMonitoredConnectionManager.getSchemeRegistry();
    assertTrue(schemeRegistry.getSchemeNames().isEmpty());
    assertSame(schreg, schemeRegistry);
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}
   */
  @Test
  public void testNewMonitoredConnectionManager14() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager("42", schreg, 1L,
        TimeUnit.NANOSECONDS);

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
    SchemeRegistry schemeRegistry = actualMonitoredConnectionManager.getSchemeRegistry();
    assertTrue(schemeRegistry.getSchemeNames().isEmpty());
    assertSame(schreg, schemeRegistry);
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}
   */
  @Test
  public void testNewMonitoredConnectionManager15() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", schreg, 2L, TimeUnit.NANOSECONDS);

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
    SchemeRegistry schemeRegistry = actualMonitoredConnectionManager.getSchemeRegistry();
    assertTrue(schemeRegistry.getSchemeNames().isEmpty());
    assertSame(schreg, schemeRegistry);
  }

  /**
   * Method under test:
   * {@link MonitoredConnectionManager#MonitoredConnectionManager(String, SchemeRegistry, long, TimeUnit)}
   */
  @Test
  public void testNewMonitoredConnectionManager16() {
    // Arrange
    SchemeRegistry schreg = new SchemeRegistry();

    // Act
    MonitoredConnectionManager actualMonitoredConnectionManager = new MonitoredConnectionManager(
        "https://example.org/example", schreg, Long.MIN_VALUE, TimeUnit.NANOSECONDS);

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
    SchemeRegistry schemeRegistry = actualMonitoredConnectionManager.getSchemeRegistry();
    assertTrue(schemeRegistry.getSchemeNames().isEmpty());
    assertSame(schreg, schemeRegistry);
  }
}
