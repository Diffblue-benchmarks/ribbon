package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.ClientException;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RequestSpecificRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.ssl.AbstractSslContextFactory;
import com.netflix.client.ssl.ClientSslSocketFactoryException;
import com.netflix.client.ssl.URLSslContextFactory;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListChangeListener;
import com.netflix.loadbalancer.ServerStatusChangeListener;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.DefaultPublishingPolicy;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.PublishingPolicy;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import com.netflix.servo.tag.BasicTag;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.Tag;
import com.netflix.servo.tag.TagList;
import com.netflix.util.Pair;
import com.sun.jersey.api.client.Client;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RestClientDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test:
   * {@link RestClient#resetSSLSocketFactory(AbstractSslContextFactory)}
   */
  @Test
  public void testResetSSLSocketFactory() throws ClientSslSocketFactoryException, NoSuchAlgorithmException {
    // Arrange
    RestClient restClient = new RestClient();
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URLSslContextFactory abstractContextFactory = mock(URLSslContextFactory.class);
    when(abstractContextFactory.getKeyStore())
        .thenThrow(new IllegalStateException("Unable to configure custom secure socket factory"));
    when(abstractContextFactory.getSSLContext()).thenReturn(SSLContext.getDefault());

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    restClient.resetSSLSocketFactory(abstractContextFactory);
    verify(abstractContextFactory).getKeyStore();
    verify(abstractContextFactory).getSSLContext();
  }

  /**
   * Method under test:
   * {@link RestClient#resetSSLSocketFactory(AbstractSslContextFactory)}
   */
  @Test
  public void testResetSSLSocketFactory2() throws ClientSslSocketFactoryException {
    // Arrange
    RestClient restClient = new RestClient();
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URLSslContextFactory abstractContextFactory = mock(URLSslContextFactory.class);
    when(abstractContextFactory.getSSLContext()).thenReturn(null);

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    restClient.resetSSLSocketFactory(abstractContextFactory);
    verify(abstractContextFactory).getSSLContext();
  }

  /**
   * Method under test:
   * {@link RestClient#resetSSLSocketFactory(AbstractSslContextFactory)}
   */
  @Test
  public void testResetSSLSocketFactory3() throws ClientSslSocketFactoryException {
    // Arrange
    RestClient restClient = new RestClient();
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    URLSslContextFactory abstractContextFactory = mock(URLSslContextFactory.class);
    when(abstractContextFactory.getSSLContext()).thenReturn(null);

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    restClient.resetSSLSocketFactory(abstractContextFactory);
    verify(abstractContextFactory).getSSLContext();
  }

  /**
   * Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  public void testGetResource() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("https://example.org/example");

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("user.dir"), "https").toString(), ":/example.org/example");
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  public void testGetResource2() {
    // Arrange, Act and Assert
    assertNull(RestClient.getResource("%s.nfhttpclient.connIdleEvictTimeMilliSeconds"));
  }

  /**
   * Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  public void testGetResource3() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("https://example.org/examplehttps://example.org/example");

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("user.dir"), "https").toString(),
        ":/example.org/examplehttps:/example.org/example");
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  public void testGetResource4() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("https://example.org/examplecom.netflix.niws.client.http.RestClient");

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("user.dir"), "https").toString(),
        ":/example.org/examplecom.netflix.niws.client.http.RestClient");
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  public void testGetResource5() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("https://example.org/examplecom.sun.jersey.api.client.ClientResponse");

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("user.dir"), "https").toString(),
        ":/example.org/examplecom.sun.jersey.api.client.ClientResponse");
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  public void testGetResource6() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("LoadBalancer:  PingTask executing [{}] servers configuredhttp");

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("user.dir"), "LoadBalancer").toString(),
        ":%20%20PingTask%20executing%20%5B%7B%7D%5D%20servers%20configuredhttp");
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  public void testGetDefaultPortFromScheme() {
    // Arrange, Act and Assert
    assertEquals(80, (new RestClient()).getDefaultPortFromScheme("https://example.org/example"));
    assertEquals(80, (new RestClient()).getDefaultPortFromScheme("http"));
    assertEquals(443, (new RestClient()).getDefaultPortFromScheme("https"));
    assertEquals(80, (new RestClient(new BaseLoadBalancer())).getDefaultPortFromScheme("http"));
  }

  /**
   * Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  public void testGetDefaultPortFromScheme2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertEquals(80,
        (new RestClient(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()))).getDefaultPortFromScheme("http"));
  }

  /**
   * Method under test: {@link RestClient#getDefaultPortFromScheme(String)}
   */
  @Test
  public void testGetDefaultPortFromScheme3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server(""));

    // Act and Assert
    assertEquals(80, (new RestClient(lb)).getDefaultPortFromScheme("http"));
  }

  /**
   * Method under test: {@link RestClient#isRetriableException(Throwable)}
   */
  @Test
  public void testIsRetriableException() {
    // Arrange
    RestClient restClient = new RestClient();

    // Act and Assert
    assertFalse(restClient.isRetriableException(new Throwable()));
  }

  /**
   * Method under test: {@link RestClient#isRetriableException(Throwable)}
   */
  @Test
  public void testIsRetriableException2() {
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
   * Method under test: {@link RestClient#isRetriableException(Throwable)}
   */
  @Test
  public void testIsRetriableException3() {
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
   * Method under test: {@link RestClient#isCircuitBreakerException(Throwable)}
   */
  @Test
  public void testIsCircuitBreakerException() {
    // Arrange
    RestClient restClient = new RestClient();

    // Act and Assert
    assertFalse(restClient.isCircuitBreakerException(new Throwable()));
  }

  /**
   * Method under test: {@link RestClient#isCircuitBreakerException(Throwable)}
   */
  @Test
  public void testIsCircuitBreakerException2() {
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
   * Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  public void testDeriveHostAndPortFromVipAddress() throws ClientException, URISyntaxException {
    // Arrange and Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult = (new RestClient())
        .deriveHostAndPortFromVipAddress("https://example.org/example");

    // Assert
    assertEquals("example.org", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(443, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  public void testDeriveHostAndPortFromVipAddress2() throws ClientException, URISyntaxException {
    // Arrange and Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult = (new RestClient())
        .deriveHostAndPortFromVipAddress("http");

    // Assert
    assertEquals("http", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(80, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  public void testDeriveHostAndPortFromVipAddress3() throws ClientException, URISyntaxException {
    // Arrange and Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult = (new RestClient())
        .deriveHostAndPortFromVipAddress("42");

    // Assert
    assertEquals("42", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(80, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  public void testDeriveHostAndPortFromVipAddress4() throws ClientException, URISyntaxException {
    // Arrange and Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult = (new RestClient(new BaseLoadBalancer()))
        .deriveHostAndPortFromVipAddress("42");

    // Assert
    assertEquals("42", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(80, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  public void testDeriveHostAndPortFromVipAddress5() throws ClientException, URISyntaxException {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult = (new RestClient(
        new BaseLoadBalancer(ping, new AvailabilityFilteringRule()))).deriveHostAndPortFromVipAddress("42");

    // Assert
    assertEquals("42", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(80, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  public void testDeriveHostAndPortFromVipAddress6() throws ClientException, URISyntaxException {
    // Arrange and Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult = (new RestClient(
        new BaseLoadBalancer(mock(IPing.class), null))).deriveHostAndPortFromVipAddress("42");

    // Assert
    assertEquals("42", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(80, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  public void testDeriveHostAndPortFromVipAddress7() throws ClientException, URISyntaxException {
    // Arrange
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult = (new RestClient(lb))
        .deriveHostAndPortFromVipAddress("42");

    // Assert
    assertEquals("42", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(80, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Method under test: {@link RestClient#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  public void testDeriveHostAndPortFromVipAddress8() throws ClientException, URISyntaxException {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServerListChangeListener(mock(ServerListChangeListener.class));
    lb.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    Pair<String, Integer> actualDeriveHostAndPortFromVipAddressResult = (new RestClient(lb))
        .deriveHostAndPortFromVipAddress("42");

    // Assert
    assertEquals("42", actualDeriveHostAndPortFromVipAddressResult.first());
    assertEquals(80, actualDeriveHostAndPortFromVipAddressResult.second().intValue());
  }

  /**
   * Method under test:
   * {@link RestClient#getRequestSpecificRetryHandler(HttpRequest, IClientConfig)}
   */
  @Test
  public void testGetRequestSpecificRetryHandler() {
    // Arrange
    RestClient restClient = new RestClient();
    HttpRequest request = mock(HttpRequest.class);
    when(request.isRetriable()).thenReturn(false);

    // Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler = restClient.getRequestSpecificRetryHandler(request,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(request).isRetriable();
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
  }

  /**
   * Method under test:
   * {@link RestClient#getRequestSpecificRetryHandler(HttpRequest, IClientConfig)}
   */
  @Test
  public void testGetRequestSpecificRetryHandler2() {
    // Arrange
    RestClient restClient = new RestClient();
    HttpRequest request = mock(HttpRequest.class);
    when(request.isRetriable()).thenReturn(false);

    // Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler = restClient.getRequestSpecificRetryHandler(request,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", "Name Space"));

    // Assert
    verify(request).isRetriable();
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
    assertEquals(1, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
  }

  /**
   * Method under test:
   * {@link RestClient#getRequestSpecificRetryHandler(HttpRequest, IClientConfig)}
   */
  @Test
  public void testGetRequestSpecificRetryHandler3() {
    // Arrange
    RestClient restClient = new RestClient();
    HttpRequest request = mock(HttpRequest.class);
    when(request.isRetriable()).thenReturn(false);

    // Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler = restClient.getRequestSpecificRetryHandler(request,
        null);

    // Assert
    verify(request).isRetriable();
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RestClient#setJerseyClient(Client)}
   *   <li>{@link RestClient#getJerseyClient()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    RestClient restClient = new RestClient();
    Client c = new Client();

    // Act
    restClient.setJerseyClient(c);

    // Assert that nothing has changed
    assertSame(c, restClient.getJerseyClient());
  }

  /**
   * Method under test: {@link RestClient#RestClient()}
   */
  @Test
  public void testNewRestClient() {
    // Arrange and Act
    RestClient actualRestClient = new RestClient();

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getLoadBalancer());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient3() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient5() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient6() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient7() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer(new DefaultClientConfigImpl());
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient8() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient9() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient10() {
    // Arrange
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient11() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient12() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.addServerListChangeListener(mock(ServerListChangeListener.class));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient13() {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.addServer(new Server("42"));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link RestClient#RestClient(ILoadBalancer)}
   */
  @Test
  public void testNewRestClient14() {
    // Arrange
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServer(new Server("42"));
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.setPing(mock(IPing.class));

    // Act
    RestClient actualRestClient = new RestClient(lb);

    // Assert
    RetryHandler retryHandler = actualRestClient.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualRestClient.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    MonitorConfig config = executeTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    MonitorConfig config2 = getResult.getConfig();
    TagList tags = config2.getTags();
    Iterator<Tag> iteratorResult = tags.iterator();
    Tag nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BasicTag);
    TagList tags2 = config.getTags();
    assertTrue(tags2 instanceof BasicTagList);
    assertTrue(tags instanceof BasicTagList);
    assertEquals("count", nextResult.getValue());
    assertEquals("default", actualRestClient.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertFalse(actualRestClient.bFollowRedirects);
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualRestClient.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }
}
