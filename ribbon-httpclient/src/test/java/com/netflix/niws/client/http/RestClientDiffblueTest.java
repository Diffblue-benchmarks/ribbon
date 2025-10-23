package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListChangeListener;
import com.netflix.loadbalancer.ServerListFilter;
import com.netflix.loadbalancer.ServerStatusChangeListener;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import com.sun.jersey.api.client.Client;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class RestClientDiffblueTest {
  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
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
   *
   * <ul>
   *   <li>Given {@link ServerListChangeListener}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenServerListChangeListener() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServerListChangeListener(mock(ServerListChangeListener.class));
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
   *
   * <ul>
   *   <li>Given {@link ServerListFilter}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenServerListFilter() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.setFilter(mock(ServerListFilter.class));
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
   *
   * <ul>
   *   <li>Then LoadBalancer return {@link ZoneAwareLoadBalancer}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_thenLoadBalancerReturnZoneAwareLoadBalancer() {
    // Arrange
    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    Server newServer = new Server("42");
    lb.addServer(newServer);
    lb.addServerStatusChangeListener(listener);
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act and Assert
    ILoadBalancer loadBalancer = new RestClient(lb).getLoadBalancer();
    assertTrue(loadBalancer instanceof ZoneAwareLoadBalancer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertSame(newServer, allServers.get(0));
  }

  /**
   * Test {@link RestClient#RestClient(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Then return LoadBalancer is {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_thenReturnLoadBalancerIsZoneAwareLoadBalancer() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
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
   *
   * <ul>
   *   <li>Then return LoadBalancer is {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_thenReturnLoadBalancerIsZoneAwareLoadBalancer2() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
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
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} Ping is {@link IPing}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return LoadBalancer is {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"URL RestClient.getResource(String)"})
  public void testGetResource() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("https://example.org/example");

    // Assert
    String expectedToStringResult =
        String.join(
            "",
            "file:",
            Paths.get(System.getProperty("user.dir"), "https").toString(),
            ":/example.org/example");
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Test {@link RestClient#getResource(String)}.
   *
   * <ul>
   *   <li>When {@code %s.nfhttpclient.connIdleEvictTimeMilliSeconds}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"URL RestClient.getResource(String)"})
  public void testGetResource_whenSNfhttpclientConnIdleEvictTimeMilliSeconds_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(RestClient.getResource("%s.nfhttpclient.connIdleEvictTimeMilliSeconds"));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link RestClient#setJerseyClient(Client)}
   *   <li>{@link RestClient#getJerseyClient()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Client RestClient.getJerseyClient()",
    "void RestClient.setJerseyClient(Client)"
  })
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
   *
   * <ul>
   *   <li>When {@code http}.
   *   <li>Then return eighty.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int RestClient.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttp_thenReturnEighty() {
    // Arrange, Act and Assert
    assertEquals(80, new RestClient().getDefaultPortFromScheme("http"));
  }

  /**
   * Test {@link RestClient#getDefaultPortFromScheme(String)}.
   *
   * <ul>
   *   <li>When {@code https://example.org/example}.
   *   <li>Then return eighty.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int RestClient.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttpsExampleOrgExample_thenReturnEighty() {
    // Arrange, Act and Assert
    assertEquals(80, new RestClient().getDefaultPortFromScheme("https://example.org/example"));
  }

  /**
   * Test {@link RestClient#getDefaultPortFromScheme(String)}.
   *
   * <ul>
   *   <li>When {@code https}.
   *   <li>Then return four hundred forty-three.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int RestClient.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttps_thenReturnFourHundredFortyThree() {
    // Arrange, Act and Assert
    assertEquals(443, new RestClient().getDefaultPortFromScheme("https"));
  }

  /**
   * Test {@link RestClient#isRetriableException(Throwable)}.
   *
   * <p>Method under test: {@link RestClient#isRetriableException(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RestClient.isRetriableException(Throwable)"})
  public void testIsRetriableException() {
    // Arrange
    RestClient restClient = new RestClient();

    // Act and Assert
    assertFalse(restClient.isRetriableException(new Throwable()));
  }

  /**
   * Test {@link RestClient#isCircuitBreakerException(Throwable)}.
   *
   * <p>Method under test: {@link RestClient#isCircuitBreakerException(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RestClient.isCircuitBreakerException(Throwable)"})
  public void testIsCircuitBreakerException() {
    // Arrange
    RestClient restClient = new RestClient();

    // Act and Assert
    assertFalse(restClient.isCircuitBreakerException(new Throwable()));
  }

  /**
   * Test {@link RestClient#shutdown()}.
   *
   * <p>Method under test: {@link RestClient#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.shutdown()"})
  public void testShutdown() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException());

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalArgumentException())
        .when(listener)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServerStatusChangeListener(listener);
    lb.addServer(new Server("42"));
    lb.setPing(ping);

    // Act
    new RestClient(lb).shutdown();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test {@link RestClient#shutdown()}.
   *
   * <p>Method under test: {@link RestClient#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.shutdown()"})
  public void testShutdown2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException());

    ServerStatusChangeListener listener = mock(ServerStatusChangeListener.class);
    doNothing().when(listener).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener2 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener2).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener3 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener3).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener4 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener4).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener5 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener5).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener6 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener6).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener7 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener7).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener8 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener8).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener9 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener9).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener10 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener10).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener11 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener11).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener12 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener12).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener13 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener13).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener14 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener14).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener15 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener15).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener16 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener16).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener17 = mock(ServerStatusChangeListener.class);
    doNothing().when(listener17).serverStatusChanged(Mockito.<Collection<Server>>any());

    ServerStatusChangeListener listener18 = mock(ServerStatusChangeListener.class);
    doThrow(new IllegalStateException())
        .when(listener18)
        .serverStatusChanged(Mockito.<Collection<Server>>any());

    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServerStatusChangeListener(listener18);
    lb.addServerStatusChangeListener(listener17);
    lb.addServerStatusChangeListener(listener16);
    lb.addServerStatusChangeListener(listener15);
    lb.addServerStatusChangeListener(listener14);
    lb.addServerStatusChangeListener(listener13);
    lb.addServerStatusChangeListener(listener12);
    lb.addServerStatusChangeListener(listener11);
    lb.addServerStatusChangeListener(listener10);
    lb.addServerStatusChangeListener(listener9);
    lb.addServerStatusChangeListener(listener8);
    lb.addServerStatusChangeListener(listener7);
    lb.addServerStatusChangeListener(listener6);
    lb.addServerStatusChangeListener(listener5);
    lb.addServerStatusChangeListener(listener4);
    lb.addServerStatusChangeListener(listener3);
    lb.addServerStatusChangeListener(listener2);
    lb.addServerStatusChangeListener(listener);
    lb.addServer(new Server("42"));
    lb.setPing(ping);

    // Act
    new RestClient(lb).shutdown();

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(listener18).serverStatusChanged(isA(Collection.class));
    verify(listener17).serverStatusChanged(isA(Collection.class));
    verify(listener16).serverStatusChanged(isA(Collection.class));
    verify(listener15).serverStatusChanged(isA(Collection.class));
    verify(listener14).serverStatusChanged(isA(Collection.class));
    verify(listener13).serverStatusChanged(isA(Collection.class));
    verify(listener12).serverStatusChanged(isA(Collection.class));
    verify(listener11).serverStatusChanged(isA(Collection.class));
    verify(listener10).serverStatusChanged(isA(Collection.class));
    verify(listener9).serverStatusChanged(isA(Collection.class));
    verify(listener8).serverStatusChanged(isA(Collection.class));
    verify(listener7).serverStatusChanged(isA(Collection.class));
    verify(listener6).serverStatusChanged(isA(Collection.class));
    verify(listener5).serverStatusChanged(isA(Collection.class));
    verify(listener4).serverStatusChanged(isA(Collection.class));
    verify(listener3).serverStatusChanged(isA(Collection.class));
    verify(listener2).serverStatusChanged(isA(Collection.class));
    verify(listener).serverStatusChanged(isA(Collection.class));
  }

  /**
   * Test {@link RestClient#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} return {@code true}.
   *   <li>Then calls {@link IPing#isAlive(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.shutdown()"})
  public void testShutdown_givenIPingIsAliveReturnTrue_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServer(new Server("42"));
    lb.setPing(ping);

    // Act
    new RestClient(lb).shutdown();

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Test {@link RestClient#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link IPing} {@link IPing#isAlive(Server)} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   *   <li>Then calls {@link IPing#isAlive(Server)}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.shutdown()"})
  public void testShutdown_givenIPingIsAliveThrowIllegalArgumentException_thenCallsIsAlive() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new IllegalArgumentException());

    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServer(new Server("42"));
    lb.setPing(ping);

    // Act
    new RestClient(lb).shutdown();

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }
}
