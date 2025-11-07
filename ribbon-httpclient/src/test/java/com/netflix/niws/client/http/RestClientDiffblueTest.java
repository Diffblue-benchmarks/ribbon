package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.PrimeConnections;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.ServerStatusChangeListener;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import com.sun.jersey.api.client.Client;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class RestClientDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   * <p>
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} Ping is {@link IPing}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_whenBaseLoadBalancerPingIsIPing() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then return LoadBalancer is {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_whenBaseLoadBalancer_thenReturnLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb, actualRestClient.getLoadBalancer());
  }

  /**
   * Test {@link RestClient#getResource(String)}.
   * <p>
   * Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"URL RestClient.getResource(String)"})
  public void testGetResource() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("https://example.org/example");

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("user.dir"), "https").toString(), ":/example.org/example");
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Test {@link RestClient#getResource(String)}.
   * <p>
   * Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"URL RestClient.getResource(String)"})
  public void testGetResource2() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("com.netflix.client.ClientException");

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("user.dir"), "com.netflix.client.ClientException").toString());
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Test {@link RestClient#getResource(String)}.
   * <p>
   * Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"URL RestClient.getResource(String)"})
  public void testGetResource3() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("LoadBalancer:  PingTask executing [{}] servers configureddefault");

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("user.dir"), "LoadBalancer").toString(),
        ":%20%20PingTask%20executing%20%5B%7B%7D%5D%20servers%20configureddefault");
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Test {@link RestClient#getResource(String)}.
   * <ul>
   *   <li>When {@code %s.nfhttpclient.connIdleEvictTimeMilliSeconds}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"URL RestClient.getResource(String)"})
  public void testGetResource_whenSNfhttpclientConnIdleEvictTimeMilliSeconds_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(RestClient.getResource("%s.nfhttpclient.connIdleEvictTimeMilliSeconds"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RestClient#setJerseyClient(Client)}
   *   <li>{@link RestClient#getJerseyClient()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Client RestClient.getJerseyClient()", "void RestClient.setJerseyClient(Client)"})
  public void testGettersAndSetters() {
    // Arrange
    RestClient restClient = new RestClient();
    Client c = new Client();

    // Act
    restClient.setJerseyClient(c);

    // Assert
    assertSame(c, restClient.getJerseyClient());
  }

  /**
   * Test {@link RestClient#getDefaultPortFromScheme(String)}.
   * <ul>
   *   <li>When {@code http}.</li>
   *   <li>Then return eighty.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int RestClient.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttp_thenReturnEighty() {
    // Arrange, Act and Assert
    assertEquals(80, (new RestClient()).getDefaultPortFromScheme("http"));
  }

  /**
   * Test {@link RestClient#getDefaultPortFromScheme(String)}.
   * <ul>
   *   <li>When {@code https://example.org/example}.</li>
   *   <li>Then return eighty.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int RestClient.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttpsExampleOrgExample_thenReturnEighty() {
    // Arrange, Act and Assert
    assertEquals(80, (new RestClient()).getDefaultPortFromScheme("https://example.org/example"));
  }

  /**
   * Test {@link RestClient#getDefaultPortFromScheme(String)}.
   * <ul>
   *   <li>When {@code https}.</li>
   *   <li>Then return four hundred forty-three.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int RestClient.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttps_thenReturnFourHundredFortyThree() {
    // Arrange, Act and Assert
    assertEquals(443, (new RestClient()).getDefaultPortFromScheme("https"));
  }

  /**
   * Test {@link RestClient#getDefaultPortFromScheme(String)}.
   * <ul>
   *   <li>When {@code org.apache.http.conn.ssl.SSLSocketFactory}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int RestClient.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenOrgApacheHttpConnSslSSLSocketFactory() {
    // Arrange, Act and Assert
    assertEquals(80, (new RestClient()).getDefaultPortFromScheme("org.apache.http.conn.ssl.SSLSocketFactory"));
  }

  /**
   * Test {@link RestClient#isRetriableException(Throwable)}.
   * <ul>
   *   <li>Given {@link RestClient#RestClient()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#isRetriableException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RestClient.isRetriableException(Throwable)"})
  public void testIsRetriableException_givenRestClient_thenReturnFalse() {
    // Arrange
    RestClient restClient = new RestClient();

    Throwable throwable = new Throwable();
    throwable.initCause(new Throwable());

    Throwable throwable2 = new Throwable();
    throwable2.initCause(throwable);

    Throwable throwable3 = new Throwable();
    throwable3.initCause(throwable2);

    Throwable throwable4 = new Throwable();
    throwable4.initCause(throwable3);

    Throwable throwable5 = new Throwable();
    throwable5.initCause(throwable4);

    Throwable throwable6 = new Throwable();
    throwable6.initCause(throwable5);

    Throwable throwable7 = new Throwable();
    throwable7.initCause(throwable6);

    Throwable throwable8 = new Throwable();
    throwable8.initCause(throwable7);

    Throwable throwable9 = new Throwable();
    throwable9.initCause(throwable8);

    Throwable e = new Throwable();
    e.initCause(throwable9);

    // Act and Assert
    assertFalse(restClient.isRetriableException(e));
  }

  /**
   * Test {@link RestClient#isRetriableException(Throwable)}.
   * <ul>
   *   <li>Given {@link RestClient#RestClient()}.</li>
   *   <li>When {@link Throwable#Throwable()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#isRetriableException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RestClient.isRetriableException(Throwable)"})
  public void testIsRetriableException_givenRestClient_whenThrowable_thenReturnFalse() {
    // Arrange
    RestClient restClient = new RestClient();

    // Act and Assert
    assertFalse(restClient.isRetriableException(new Throwable()));
  }

  /**
   * Test {@link RestClient#isRetriableException(Throwable)}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()} addSuppressed {@link Throwable#Throwable()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#isRetriableException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RestClient.isRetriableException(Throwable)"})
  public void testIsRetriableException_givenThrowableAddSuppressedThrowable_thenReturnFalse() {
    // Arrange
    RestClient restClient = new RestClient();

    Throwable throwable = new Throwable();
    throwable.initCause(new Throwable());

    Throwable throwable2 = new Throwable();
    throwable2.initCause(throwable);

    Throwable throwable3 = new Throwable();
    throwable3.initCause(throwable2);

    Throwable throwable4 = new Throwable();
    throwable4.initCause(throwable3);

    Throwable throwable5 = new Throwable();
    throwable5.initCause(throwable4);

    Throwable throwable6 = new Throwable();
    throwable6.addSuppressed(new Throwable());
    throwable6.initCause(throwable5);

    Throwable throwable7 = new Throwable();
    throwable7.initCause(throwable6);

    Throwable throwable8 = new Throwable();
    throwable8.initCause(throwable7);

    Throwable throwable9 = new Throwable();
    throwable9.initCause(throwable8);

    Throwable e = new Throwable();
    e.initCause(throwable9);

    // Act and Assert
    assertFalse(restClient.isRetriableException(e));
  }

  /**
   * Test {@link RestClient#isCircuitBreakerException(Throwable)}.
   * <p>
   * Method under test: {@link RestClient#isCircuitBreakerException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RestClient.isCircuitBreakerException(Throwable)"})
  public void testIsCircuitBreakerException() {
    // Arrange
    RestClient restClient = new RestClient();

    Throwable throwable = new Throwable();
    throwable.initCause(null);

    Throwable throwable2 = new Throwable();
    throwable2.initCause(throwable);

    Throwable throwable3 = new Throwable();
    throwable3.initCause(throwable2);

    IllegalStateException illegalStateException = new IllegalStateException("throwable");
    illegalStateException.initCause(throwable3);

    Throwable throwable4 = new Throwable();
    throwable4.initCause(illegalStateException);

    Throwable e = new Throwable();
    e.initCause(throwable4);

    // Act and Assert
    assertFalse(restClient.isCircuitBreakerException(e));
  }

  /**
   * Test {@link RestClient#isCircuitBreakerException(Throwable)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link Throwable#Throwable()} initCause {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#isCircuitBreakerException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RestClient.isCircuitBreakerException(Throwable)"})
  public void testIsCircuitBreakerException_givenNull_whenThrowableInitCauseNull() {
    // Arrange
    RestClient restClient = new RestClient();

    Throwable e = new Throwable();
    e.initCause(null);

    // Act and Assert
    assertFalse(restClient.isCircuitBreakerException(e));
  }

  /**
   * Test {@link RestClient#isCircuitBreakerException(Throwable)}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()} addSuppressed {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#isCircuitBreakerException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RestClient.isCircuitBreakerException(Throwable)"})
  public void testIsCircuitBreakerException_givenThrowableAddSuppressedThrowable() {
    // Arrange
    RestClient restClient = new RestClient();

    Throwable throwable = new Throwable();
    throwable.initCause(new Throwable());

    Throwable throwable2 = new Throwable();
    throwable2.initCause(throwable);

    Throwable throwable3 = new Throwable();
    throwable3.initCause(throwable2);

    Throwable throwable4 = new Throwable();
    throwable4.initCause(throwable3);

    Throwable throwable5 = new Throwable();
    throwable5.initCause(throwable4);

    Throwable throwable6 = new Throwable();
    throwable6.initCause(throwable5);

    Throwable throwable7 = new Throwable();
    throwable7.initCause(throwable6);

    Throwable throwable8 = new Throwable();
    throwable8.initCause(throwable7);

    Throwable throwable9 = new Throwable();
    throwable9.addSuppressed(new Throwable());
    throwable9.initCause(throwable8);

    Throwable e = new Throwable();
    e.initCause(throwable9);

    // Act and Assert
    assertFalse(restClient.isCircuitBreakerException(e));
  }

  /**
   * Test {@link RestClient#isCircuitBreakerException(Throwable)}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()} initCause {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#isCircuitBreakerException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RestClient.isCircuitBreakerException(Throwable)"})
  public void testIsCircuitBreakerException_givenThrowableInitCauseNull() {
    // Arrange
    RestClient restClient = new RestClient();

    Throwable throwable = new Throwable();
    throwable.initCause(null);

    Throwable throwable2 = new Throwable();
    throwable2.initCause(throwable);

    Throwable throwable3 = new Throwable();
    throwable3.initCause(throwable2);

    Throwable throwable4 = new Throwable();
    throwable4.initCause(throwable3);

    Throwable throwable5 = new Throwable();
    throwable5.initCause(throwable4);

    Throwable throwable6 = new Throwable();
    throwable6.initCause(throwable5);

    Throwable throwable7 = new Throwable();
    throwable7.initCause(throwable6);

    Throwable throwable8 = new Throwable();
    throwable8.initCause(throwable7);

    Throwable throwable9 = new Throwable();
    throwable9.initCause(throwable8);

    Throwable e = new Throwable();
    e.initCause(throwable9);

    // Act and Assert
    assertFalse(restClient.isCircuitBreakerException(e));
  }

  /**
   * Test {@link RestClient#isCircuitBreakerException(Throwable)}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()} initCause {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#isCircuitBreakerException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RestClient.isCircuitBreakerException(Throwable)"})
  public void testIsCircuitBreakerException_givenThrowableInitCauseThrowable() {
    // Arrange
    RestClient restClient = new RestClient();

    Throwable throwable = new Throwable();
    throwable.initCause(new Throwable());

    Throwable throwable2 = new Throwable();
    throwable2.initCause(throwable);

    Throwable throwable3 = new Throwable();
    throwable3.initCause(throwable2);

    Throwable throwable4 = new Throwable();
    throwable4.initCause(throwable3);

    Throwable throwable5 = new Throwable();
    throwable5.initCause(throwable4);

    Throwable throwable6 = new Throwable();
    throwable6.initCause(throwable5);

    Throwable throwable7 = new Throwable();
    throwable7.initCause(throwable6);

    Throwable throwable8 = new Throwable();
    throwable8.initCause(throwable7);

    Throwable throwable9 = new Throwable();
    throwable9.initCause(throwable8);

    Throwable e = new Throwable();
    e.initCause(throwable9);

    // Act and Assert
    assertFalse(restClient.isCircuitBreakerException(e));
  }

  /**
   * Test {@link RestClient#isCircuitBreakerException(Throwable)}.
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#isCircuitBreakerException(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean RestClient.isCircuitBreakerException(Throwable)"})
  public void testIsCircuitBreakerException_whenThrowable() {
    // Arrange
    RestClient restClient = new RestClient();

    // Act and Assert
    assertFalse(restClient.isCircuitBreakerException(new Throwable()));
  }

  /**
   * Test {@link RestClient#shutdown()}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer} {@link BaseLoadBalancer#shutdown()} throw {@link IllegalArgumentException#IllegalArgumentException(String)} with {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#shutdown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RestClient.shutdown()"})
  public void testShutdown_givenBaseLoadBalancerShutdownThrowIllegalArgumentExceptionWithFoo() {
    // Arrange
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    doThrow(new IllegalArgumentException("foo")).when(lb).shutdown();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    (new RestClient(lb)).shutdown();
    verify(lb).shutdown();
  }

  /**
   * Test {@link RestClient#shutdown()}.
   * <ul>
   *   <li>Given {@link PrimeConnections} {@link PrimeConnections#shutdown()} does nothing.</li>
   *   <li>Then calls {@link PrimeConnections#shutdown()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#shutdown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RestClient.shutdown()"})
  public void testShutdown_givenPrimeConnectionsShutdownDoesNothing_thenCallsShutdown() {
    // Arrange
    PrimeConnections primeConnections = mock(PrimeConnections.class);
    doNothing().when(primeConnections).shutdown();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPrimeConnections(primeConnections);

    RestClient restClient = new RestClient(mock(BaseLoadBalancer.class));
    restClient.setLoadBalancer(lb);

    // Act
    restClient.shutdown();

    // Assert
    verify(primeConnections).shutdown();
  }

  /**
   * Test {@link RestClient#shutdown()}.
   * <ul>
   *   <li>Given {@link PrimeConnections} {@link PrimeConnections#shutdown()} throw {@link IllegalArgumentException#IllegalArgumentException(String)} with space.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#shutdown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RestClient.shutdown()"})
  public void testShutdown_givenPrimeConnectionsShutdownThrowIllegalArgumentExceptionWithSpace() {
    // Arrange
    PrimeConnections primeConnections = mock(PrimeConnections.class);
    doThrow(new IllegalArgumentException(" ")).when(primeConnections).shutdown();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPrimeConnections(primeConnections);

    RestClient restClient = new RestClient(mock(BaseLoadBalancer.class));
    restClient.setLoadBalancer(lb);

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    restClient.shutdown();
    verify(primeConnections).shutdown();
  }

  /**
   * Test {@link RestClient#shutdown()}.
   * <ul>
   *   <li>Given {@link RestClient#RestClient(ILoadBalancer)} with lb is {@link BaseLoadBalancer}.</li>
   *   <li>Then calls {@link BaseLoadBalancer#shutdown()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RestClient#shutdown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RestClient.shutdown()"})
  public void testShutdown_givenRestClientWithLbIsBaseLoadBalancer_thenCallsShutdown() {
    // Arrange
    BaseLoadBalancer lb = mock(BaseLoadBalancer.class);
    doNothing().when(lb).shutdown();

    // Act
    (new RestClient(lb)).shutdown();

    // Assert
    verify(lb).shutdown();
  }
}
