package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.PrimeConnections;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
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
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

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
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    PrimeConnections primeConnections = new PrimeConnections("default", 3, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections = new PrimeConnections("default", 3, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections = new PrimeConnections("Name", 3, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient5() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections = new PrimeConnections("default", 0, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient6() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections =
        new PrimeConnections("default", 3, Long.MAX_VALUE, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient7() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections =
        new PrimeConnections("default", 3, Long.MAX_VALUE, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient8() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections =
        new PrimeConnections("default", 3, 2L, "Prime Connections URI");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient9() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections =
        new PrimeConnections("default", 1, Long.MAX_VALUE, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient10() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections =
        new PrimeConnections("default", 1, Long.MAX_VALUE, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient11() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections = new PrimeConnections("default", 3, 0L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient12() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections =
        new PrimeConnections("default", 1, 2L, "Prime Connections URI");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient13() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setMaxTotalPingTime(3);
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections = new PrimeConnections("", 3, 2L, "Prime Connections URI");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient14() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections = new PrimeConnections("default", 1, -1L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   *   <li>Given forty-two.
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} PingInterval is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenFortyTwo_whenBaseLoadBalancerPingIntervalIsFortyTwo() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPingInterval(42);
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections = new PrimeConnections("default", 0, 2L, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   *   <li>Given {@code Key}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenKey() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.chooseServer("Key");
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections =
        new PrimeConnections("default", 1, Long.MAX_VALUE, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   *   <li>Given three.
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} MaxTotalPingTime is three.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenThree_whenBaseLoadBalancerMaxTotalPingTimeIsThree() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setMaxTotalPingTime(3);
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections =
        new PrimeConnections("default", 3, 2L, "Prime Connections URI");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   *   <li>Given {@code true}.
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} EnablePrimingConnections is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_givenTrue_whenBaseLoadBalancerEnablePrimingConnectionsIsTrue() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.addServers(new ArrayList<>());
    PrimeConnections primeConnections =
        new PrimeConnections("default", 3, Long.MAX_VALUE, "default");
    lb.setPrimeConnections(primeConnections);
    lb.addServers(new ArrayList<>());
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
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} addServers {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>(ILoadBalancer)"})
  public void testNewRestClient_whenBaseLoadBalancerAddServersArrayList() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new ArrayList<>());
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
}
