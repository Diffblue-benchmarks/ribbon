package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.DefaultPublishingPolicy;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.util.Pair;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class LoadBalancerContextDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link LoadBalancerContext#getExecuteTracer()}.
   * <ul>
   *   <li>Given {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer)} with lb is {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#getExecuteTracer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Timer LoadBalancerContext.getExecuteTracer()"})
  public void testGetExecuteTracer_givenLoadBalancerContextWithLbIsBaseLoadBalancer() {
    // Arrange and Act
    Timer actualExecuteTracer = (new LoadBalancerContext(new BaseLoadBalancer())).getExecuteTracer();

    // Assert
    assertTrue(actualExecuteTracer instanceof BasicTimer);
    MonitorConfig config = actualExecuteTracer.getConfig();
    assertTrue(config.getPublishingPolicy() instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) actualExecuteTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertTrue(config.getTags() instanceof BasicTagList);
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) actualExecuteTracer).getCount().longValue());
    assertEquals(0L, actualExecuteTracer.getValue().longValue());
    assertEquals(TimeUnit.MILLISECONDS, actualExecuteTracer.getTimeUnit());
  }

  /**
   * Test {@link LoadBalancerContext#getDeepestCause(Throwable)}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()} initCause {@link Throwable#Throwable()}.</li>
   *   <li>Then return Cause is {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#getDeepestCause(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Throwable LoadBalancerContext.getDeepestCause(Throwable)"})
  public void testGetDeepestCause_givenThrowableInitCauseThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    Throwable throwable = new Throwable();
    Throwable throwable2 = new Throwable();
    throwable.initCause(throwable2);

    Throwable throwable3 = new Throwable();
    throwable3.initCause(throwable);

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

    Throwable throwable10 = new Throwable();
    throwable10.initCause(throwable9);

    Throwable throwable11 = new Throwable();
    throwable11.initCause(throwable10);

    Throwable e = new Throwable();
    e.initCause(throwable11);

    // Act and Assert
    assertSame(throwable2, loadBalancerContext.getDeepestCause(e).getCause());
  }

  /**
   * Test {@link LoadBalancerContext#getDeepestCause(Throwable)}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.</li>
   *   <li>Then return {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#getDeepestCause(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Throwable LoadBalancerContext.getDeepestCause(Throwable)"})
  public void testGetDeepestCause_givenThrowable_thenReturnThrowable() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    Throwable e = new Throwable();
    Throwable throwable = new Throwable();
    e.initCause(throwable);

    // Act and Assert
    assertSame(throwable, loadBalancerContext.getDeepestCause(e));
  }

  /**
   * Test {@link LoadBalancerContext#getDeepestCause(Throwable)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#getDeepestCause(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Throwable LoadBalancerContext.getDeepestCause(Throwable)"})
  public void testGetDeepestCause_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new LoadBalancerContext(new BaseLoadBalancer())).getDeepestCause(null));
  }

  /**
   * Test {@link LoadBalancerContext#getDeepestCause(Throwable)}.
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.</li>
   *   <li>Then return {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#getDeepestCause(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Throwable LoadBalancerContext.getDeepestCause(Throwable)"})
  public void testGetDeepestCause_whenThrowable_thenReturnThrowable() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    Throwable e = new Throwable();

    // Act and Assert
    assertSame(e, loadBalancerContext.getDeepestCause(e));
  }

  /**
   * Test {@link LoadBalancerContext#isPresentAsCauseHelper(Throwable, Class)}.
   * <ul>
   *   <li>When {@link IOException#IOException(String)} with {@code foo}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#isPresentAsCauseHelper(Throwable, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Throwable LoadBalancerContext.isPresentAsCauseHelper(Throwable, Class)"})
  public void testIsPresentAsCauseHelper_whenIOExceptionWithFoo_thenReturnNull() {
    // Arrange
    IOException throwableToSearchIn = new IOException("foo");
    Class<Throwable> throwableToSearchFor = Throwable.class;

    // Act and Assert
    assertNull(LoadBalancerContext.isPresentAsCauseHelper(throwableToSearchIn, throwableToSearchFor));
  }

  /**
   * Test {@link LoadBalancerContext#isPresentAsCauseHelper(Throwable, Class)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#isPresentAsCauseHelper(Throwable, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Throwable LoadBalancerContext.isPresentAsCauseHelper(Throwable, Class)"})
  public void testIsPresentAsCauseHelper_whenNull_thenReturnNull() {
    // Arrange
    Class<Throwable> throwableToSearchFor = Throwable.class;

    // Act and Assert
    assertNull(LoadBalancerContext.isPresentAsCauseHelper(null, throwableToSearchFor));
  }

  /**
   * Test {@link LoadBalancerContext#isPresentAsCauseHelper(Throwable, Class)}.
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.</li>
   *   <li>Then return LocalizedMessage is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#isPresentAsCauseHelper(Throwable, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Throwable LoadBalancerContext.isPresentAsCauseHelper(Throwable, Class)"})
  public void testIsPresentAsCauseHelper_whenThrowable_thenReturnLocalizedMessageIsNull() {
    // Arrange
    Throwable throwableToSearchIn = new Throwable();
    Class<Throwable> throwableToSearchFor = Throwable.class;

    // Act
    Throwable actualIsPresentAsCauseHelperResult = LoadBalancerContext.isPresentAsCauseHelper(throwableToSearchIn,
        throwableToSearchFor);

    // Assert
    assertNull(actualIsPresentAsCauseHelperResult.getLocalizedMessage());
    assertNull(actualIsPresentAsCauseHelperResult.getMessage());
    assertNull(actualIsPresentAsCauseHelperResult.getCause());
    assertEquals(0, actualIsPresentAsCauseHelperResult.getSuppressed().length);
  }

  /**
   * Test {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long)} with {@code stats}, {@code response}, {@code e}, {@code responseTime}.
   * <p>
   * Method under test: {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerContext.noteRequestCompletion(ServerStats, Object, Throwable, long)"})
  public void testNoteRequestCompletionWithStatsResponseEResponseTime() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doNothing().when(stats).clearSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());

    // Act
    loadBalancerContext.noteRequestCompletion(stats, "Response", new Throwable(), 1L);

    // Assert
    verify(stats).clearSuccessiveConnectionFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Test {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long)} with {@code stats}, {@code response}, {@code e}, {@code responseTime}.
   * <p>
   * Method under test: {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerContext.noteRequestCompletion(ServerStats, Object, Throwable, long)"})
  public void testNoteRequestCompletionWithStatsResponseEResponseTime2() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doNothing().when(stats).clearSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());

    // Act
    loadBalancerContext.noteRequestCompletion(stats, null, new Throwable(), 1L);

    // Assert
    verify(stats).clearSuccessiveConnectionFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Test {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long)} with {@code stats}, {@code response}, {@code e}, {@code responseTime}.
   * <p>
   * Method under test: {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerContext.noteRequestCompletion(ServerStats, Object, Throwable, long)"})
  public void testNoteRequestCompletionWithStatsResponseEResponseTime3() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doThrow(new RuntimeException("foo")).when(stats).decrementActiveRequestsCount();

    // Act
    loadBalancerContext.noteRequestCompletion(stats, "Response", new Throwable(), 1L);

    // Assert
    verify(stats).decrementActiveRequestsCount();
  }

  /**
   * Test {@link LoadBalancerContext#noteOpenConnection(ServerStats)}.
   * <p>
   * Method under test: {@link LoadBalancerContext#noteOpenConnection(ServerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerContext.noteOpenConnection(ServerStats)"})
  public void testNoteOpenConnection() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats serverStats = mock(DummyServerStats.class);
    doNothing().when(serverStats).incrementActiveRequestsCount();

    // Act
    loadBalancerContext.noteOpenConnection(serverStats);

    // Assert
    verify(serverStats).incrementActiveRequestsCount();
  }

  /**
   * Test {@link LoadBalancerContext#noteOpenConnection(ServerStats)}.
   * <p>
   * Method under test: {@link LoadBalancerContext#noteOpenConnection(ServerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerContext.noteOpenConnection(ServerStats)"})
  public void testNoteOpenConnection2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Error noting stats for client {}");
    lb.addServers(new ArrayList<>());
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(lb);
    DummyServerStats serverStats = mock(DummyServerStats.class);
    doThrow(new RuntimeException("foo")).when(serverStats).incrementActiveRequestsCount();

    // Act
    loadBalancerContext.noteOpenConnection(serverStats);

    // Assert
    verify(serverStats).incrementActiveRequestsCount();
  }

  /**
   * Test {@link LoadBalancerContext#noteOpenConnection(ServerStats)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} chooseServer {@code Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#noteOpenConnection(ServerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerContext.noteOpenConnection(ServerStats)"})
  public void testNoteOpenConnection_givenBaseLoadBalancerChooseServerKey() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer("Key");
    lb.addServers(new ArrayList<>());
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(lb);
    DummyServerStats serverStats = mock(DummyServerStats.class);
    doThrow(new RuntimeException("foo")).when(serverStats).incrementActiveRequestsCount();

    // Act
    loadBalancerContext.noteOpenConnection(serverStats);

    // Assert
    verify(serverStats).incrementActiveRequestsCount();
  }

  /**
   * Test {@link LoadBalancerContext#noteOpenConnection(ServerStats)}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} chooseServer zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#noteOpenConnection(ServerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerContext.noteOpenConnection(ServerStats)"})
  public void testNoteOpenConnection_givenBaseLoadBalancerChooseServerZero() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.chooseServer(0);
    lb.addServers(new ArrayList<>());
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(lb);
    DummyServerStats serverStats = mock(DummyServerStats.class);
    doThrow(new RuntimeException("foo")).when(serverStats).incrementActiveRequestsCount();

    // Act
    loadBalancerContext.noteOpenConnection(serverStats);

    // Assert
    verify(serverStats).incrementActiveRequestsCount();
  }

  /**
   * Test {@link LoadBalancerContext#noteOpenConnection(ServerStats)}.
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException(String)} with {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#noteOpenConnection(ServerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LoadBalancerContext.noteOpenConnection(ServerStats)"})
  public void testNoteOpenConnection_givenRuntimeExceptionWithFoo() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats serverStats = mock(DummyServerStats.class);
    doThrow(new RuntimeException("foo")).when(serverStats).incrementActiveRequestsCount();

    // Act
    loadBalancerContext.noteOpenConnection(serverStats);

    // Assert
    verify(serverStats).incrementActiveRequestsCount();
  }

  /**
   * Test {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}.
   * <p>
   * Method under test: {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveSchemeAndPortFromPartialUri(URI)"})
  public void testDeriveSchemeAndPortFromPartialUri() {
    // Arrange and Act
    Pair<String, Integer> actualDeriveSchemeAndPortFromPartialUriResult = (new LoadBalancerContext(
        new BaseLoadBalancer()))
            .deriveSchemeAndPortFromPartialUri(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    assertEquals("file", actualDeriveSchemeAndPortFromPartialUriResult.first());
    assertEquals(80, actualDeriveSchemeAndPortFromPartialUriResult.second().intValue());
  }

  /**
   * Test {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}.
   * <p>
   * Method under test: {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveSchemeAndPortFromPartialUri(URI)"})
  public void testDeriveSchemeAndPortFromPartialUri2() {
    // Arrange and Act
    Pair<String, Integer> actualDeriveSchemeAndPortFromPartialUriResult = (new LoadBalancerContext(
        new NoOpLoadBalancer()))
            .deriveSchemeAndPortFromPartialUri(Paths.get(System.getProperty("java.io.tmpdir"), "foo").toUri());

    // Assert
    assertEquals("file", actualDeriveSchemeAndPortFromPartialUriResult.first());
    assertEquals(80, actualDeriveSchemeAndPortFromPartialUriResult.second().intValue());
  }

  /**
   * Test {@link LoadBalancerContext#getDefaultPortFromScheme(String)}.
   * <ul>
   *   <li>When {@code http}.</li>
   *   <li>Then return eighty.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerContext.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttp_thenReturnEighty() {
    // Arrange, Act and Assert
    assertEquals(80, (new LoadBalancerContext(new BaseLoadBalancer())).getDefaultPortFromScheme("http"));
  }

  /**
   * Test {@link LoadBalancerContext#getDefaultPortFromScheme(String)}.
   * <ul>
   *   <li>When {@code https}.</li>
   *   <li>Then return four hundred forty-three.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerContext.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenHttps_thenReturnFourHundredFortyThree() {
    // Arrange, Act and Assert
    assertEquals(443, (new LoadBalancerContext(new BaseLoadBalancer())).getDefaultPortFromScheme("https"));
  }

  /**
   * Test {@link LoadBalancerContext#getDefaultPortFromScheme(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerContext.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenNull_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(-1, (new LoadBalancerContext(new BaseLoadBalancer())).getDefaultPortFromScheme(null));
  }

  /**
   * Test {@link LoadBalancerContext#getDefaultPortFromScheme(String)}.
   * <ul>
   *   <li>When {@code Scheme}.</li>
   *   <li>Then return minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#getDefaultPortFromScheme(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int LoadBalancerContext.getDefaultPortFromScheme(String)"})
  public void testGetDefaultPortFromScheme_whenScheme_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(-1, (new LoadBalancerContext(new BaseLoadBalancer())).getDefaultPortFromScheme("Scheme"));
  }

  /**
   * Test {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}.
   * <p>
   * Method under test: {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"URI LoadBalancerContext.reconstructURIWithServer(Server, URI)"})
  public void testReconstructURIWithServer() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act
    URI actualReconstructURIWithServerResult = loadBalancerContext.reconstructURIWithServer(new Server("42"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    String expectedToStringResult = String.join("", "file://42:80",
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toString());
    assertEquals(expectedToStringResult, actualReconstructURIWithServerResult.toString());
  }

  /**
   * Test {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}.
   * <p>
   * Method under test: {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"URI LoadBalancerContext.reconstructURIWithServer(Server, URI)"})
  public void testReconstructURIWithServer2() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act and Assert
    thrown.expect(RuntimeException.class);
    loadBalancerContext.reconstructURIWithServer(new Server("://", "localhost", 8080),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
  }

  /**
   * Test {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}.
   * <p>
   * Method under test: {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"URI LoadBalancerContext.reconstructURIWithServer(Server, URI)"})
  public void testReconstructURIWithServer3() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    loadBalancerContext.setOkToRetryOnAllOperations(true);

    // Act
    URI actualReconstructURIWithServerResult = loadBalancerContext.reconstructURIWithServer(new Server("42"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    String expectedToStringResult = String.join("", "file://42:80",
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toString());
    assertEquals(expectedToStringResult, actualReconstructURIWithServerResult.toString());
  }

  /**
   * Test {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}.
   * <p>
   * Method under test: {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"URI LoadBalancerContext.reconstructURIWithServer(Server, URI)"})
  public void testReconstructURIWithServer4() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act and Assert
    thrown.expect(RuntimeException.class);
    loadBalancerContext.reconstructURIWithServer(new Server("://", "localhost", -1),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
  }

  /**
   * Test {@link LoadBalancerContext#getServerStats(Server)}.
   * <p>
   * Method under test: {@link LoadBalancerContext#getServerStats(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ServerStats LoadBalancerContext.getServerStats(Server)"})
  public void testGetServerStats() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    loadBalancerContext.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(loadBalancerContext.getServerStats(null));
  }

  /**
   * Test {@link LoadBalancerContext#getServerStats(Server)}.
   * <ul>
   *   <li>Given {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer)} with lb is {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#getServerStats(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ServerStats LoadBalancerContext.getServerStats(Server)"})
  public void testGetServerStats_givenLoadBalancerContextWithLbIsNull_thenReturnNull() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(null);

    // Act and Assert
    assertNull(loadBalancerContext.getServerStats(new Server("42")));
  }

  /**
   * Test {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}.
   * <p>
   * Method under test: {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LoadBalancerContext.handleSameServerRetry(Server, int, int, Throwable)"})
  public void testHandleSameServerRetry() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServers(new ArrayList<>());
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(lb);
    Server server = new Server("42");

    // Act and Assert
    assertTrue(loadBalancerContext.handleSameServerRetry(server, 3, 3, new Throwable()));
  }

  /**
   * Test {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}.
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} addServers {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LoadBalancerContext.handleSameServerRetry(Server, int, int, Throwable)"})
  public void testHandleSameServerRetry_givenDynamicServerListLoadBalancerAddServersArrayList() {
    // Arrange
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServers(new ArrayList<>());
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(lb);
    Server server = new Server("42");

    // Act and Assert
    assertTrue(loadBalancerContext.handleSameServerRetry(server, 3, 3, new Throwable()));
  }

  /**
   * Test {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}.
   * <ul>
   *   <li>Given {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer)} with lb is {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LoadBalancerContext.handleSameServerRetry(Server, int, int, Throwable)"})
  public void testHandleSameServerRetry_givenLoadBalancerContextWithLbIsBaseLoadBalancer() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    Server server = new Server("42");

    // Act and Assert
    assertTrue(loadBalancerContext.handleSameServerRetry(server, 3, 3, new Throwable()));
  }

  /**
   * Test {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LoadBalancerContext.handleSameServerRetry(Server, int, int, Throwable)"})
  public void testHandleSameServerRetry_whenTen_thenReturnFalse() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    Server server = new Server("42");

    // Act and Assert
    assertFalse(loadBalancerContext.handleSameServerRetry(server, 10, 3, new Throwable()));
  }
}
