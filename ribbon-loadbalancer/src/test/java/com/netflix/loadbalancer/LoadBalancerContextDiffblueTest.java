package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.ClientException;
import com.netflix.client.ClientRequest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class LoadBalancerContextDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test:
   * {@link LoadBalancerContext#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act
    loadBalancerContext.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    RetryHandler retryHandler = loadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = loadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("default", loadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("default_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig2() {
    // Arrange
    IPing ping = mock(IPing.class);
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(
        new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act
    loadBalancerContext.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    RetryHandler retryHandler = loadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig3() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act
    loadBalancerContext.initWithNiwsConfig(null);

    // Assert that nothing has changed
    RetryHandler retryHandler = loadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = loadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("default", loadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("default_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig4() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act
    loadBalancerContext
        .initWithNiwsConfig(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " "));

    // Assert
    RetryHandler retryHandler = loadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = loadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("Dr Jane Doe", loadBalancerContext.getClientName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig5() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act
    loadBalancerContext.initWithNiwsConfig(DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " "));

    // Assert
    RetryHandler retryHandler = loadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = loadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("default", loadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("default_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
  }

  /**
   * Method under test: {@link LoadBalancerContext#getExecuteTracer()}
   */
  @Test
  public void testGetExecuteTracer() {
    // Arrange and Act
    Timer actualExecuteTracer = (new LoadBalancerContext(new BaseLoadBalancer())).getExecuteTracer();

    // Assert
    assertTrue(actualExecuteTracer instanceof BasicTimer);
    MonitorConfig config = actualExecuteTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) actualExecuteTracer).getMonitors();
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
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) actualExecuteTracer).getCount().longValue());
    assertEquals(0L, actualExecuteTracer.getValue().longValue());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, actualExecuteTracer.getTimeUnit());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link LoadBalancerContext#getExecuteTracer()}
   */
  @Test
  public void testGetExecuteTracer2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    Timer actualExecuteTracer = (new LoadBalancerContext(new BaseLoadBalancer(ping, new AvailabilityFilteringRule())))
        .getExecuteTracer();

    // Assert
    assertTrue(actualExecuteTracer instanceof BasicTimer);
    MonitorConfig config = actualExecuteTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) actualExecuteTracer).getMonitors();
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
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) actualExecuteTracer).getCount().longValue());
    assertEquals(0L, actualExecuteTracer.getValue().longValue());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, actualExecuteTracer.getTimeUnit());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link LoadBalancerContext#getExecuteTracer()}
   */
  @Test
  public void testGetExecuteTracer3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    Timer actualExecuteTracer = (new LoadBalancerContext(lb, DefaultClientConfigImpl.getEmptyConfig()))
        .getExecuteTracer();

    // Assert
    assertTrue(actualExecuteTracer instanceof BasicTimer);
    MonitorConfig config = actualExecuteTracer.getConfig();
    PublishingPolicy publishingPolicy = config.getPublishingPolicy();
    assertTrue(publishingPolicy instanceof DefaultPublishingPolicy);
    List<Monitor<?>> monitors = ((BasicTimer) actualExecuteTracer).getMonitors();
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
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) actualExecuteTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) actualExecuteTracer).getCount().longValue());
    assertEquals(0L, actualExecuteTracer.getValue().longValue());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, actualExecuteTracer.getTimeUnit());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test: {@link LoadBalancerContext#getDeepestCause(Throwable)}
   */
  @Test
  public void testGetDeepestCause() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    Throwable e = new Throwable();

    // Act and Assert
    assertSame(e, loadBalancerContext.getDeepestCause(e));
  }

  /**
   * Method under test: {@link LoadBalancerContext#getDeepestCause(Throwable)}
   */
  @Test
  public void testGetDeepestCause2() {
    // Arrange, Act and Assert
    assertNull((new LoadBalancerContext(new BaseLoadBalancer())).getDeepestCause(null));
  }

  /**
   * Method under test: {@link LoadBalancerContext#getDeepestCause(Throwable)}
   */
  @Test
  public void testGetDeepestCause3() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    Throwable e = new Throwable();
    Throwable throwable = new Throwable();
    e.initCause(throwable);

    // Act and Assert
    assertSame(throwable, loadBalancerContext.getDeepestCause(e));
  }

  /**
   * Method under test: {@link LoadBalancerContext#getDeepestCause(Throwable)}
   */
  @Test
  public void testGetDeepestCause4() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

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

    Throwable throwable10 = new Throwable();
    throwable10.initCause(throwable9);

    Throwable e = new Throwable();
    e.initCause(throwable10);

    // Act and Assert
    assertSame(throwable, loadBalancerContext.getDeepestCause(e));
  }

  /**
   * Method under test: {@link LoadBalancerContext#getDeepestCause(Throwable)}
   */
  @Test
  public void testGetDeepestCause5() {
    // Arrange
    IPing ping = mock(IPing.class);
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(
        new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));
    Throwable e = new Throwable();

    // Act and Assert
    assertSame(e, loadBalancerContext.getDeepestCause(e));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#isPresentAsCauseHelper(Throwable, Class)}
   */
  @Test
  public void testIsPresentAsCauseHelper() {
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
   * Method under test:
   * {@link LoadBalancerContext#isPresentAsCauseHelper(Throwable, Class)}
   */
  @Test
  public void testIsPresentAsCauseHelper2() {
    // Arrange
    Class<Throwable> throwableToSearchFor = Throwable.class;

    // Act and Assert
    assertNull(LoadBalancerContext.isPresentAsCauseHelper(null, throwableToSearchFor));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#isPresentAsCauseHelper(Throwable, Class)}
   */
  @Test
  public void testIsPresentAsCauseHelper3() {
    // Arrange
    IOException throwableToSearchIn = new IOException("foo");
    Class<Throwable> throwableToSearchFor = Throwable.class;

    // Act and Assert
    assertNull(LoadBalancerContext.isPresentAsCauseHelper(throwableToSearchIn, throwableToSearchFor));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#generateNIWSException(String, Throwable)}
   */
  @Test
  public void testGenerateNIWSException() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    IOException e = new IOException("Read timed out");

    // Act
    ClientException actualGenerateNIWSExceptionResult = loadBalancerContext.generateNIWSException("Uri", e);

    // Assert
    assertEquals("Unable to execute RestClient request for URI:Uri:Read timed out",
        actualGenerateNIWSExceptionResult.getErrorMessage());
    assertEquals("Unable to execute RestClient request for URI:Uri:Read timed out",
        actualGenerateNIWSExceptionResult.getLocalizedMessage());
    assertEquals("Unable to execute RestClient request for URI:Uri:Read timed out",
        actualGenerateNIWSExceptionResult.getMessage());
    assertEquals("{no message: 5}", actualGenerateNIWSExceptionResult.getInternalMessage());
    assertNull(actualGenerateNIWSExceptionResult.getErrorObject());
    assertEquals(0, actualGenerateNIWSExceptionResult.getSuppressed().length);
    assertEquals(5, actualGenerateNIWSExceptionResult.getErrorCode());
    assertEquals(ClientException.ErrorType.READ_TIMEOUT_EXCEPTION, actualGenerateNIWSExceptionResult.getErrorType());
    assertSame(e, actualGenerateNIWSExceptionResult.getCause());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#generateNIWSException(String, Throwable)}
   */
  @Test
  public void testGenerateNIWSException2() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    IllegalStateException e = new IllegalStateException("Read timed out");

    // Act
    ClientException actualGenerateNIWSExceptionResult = loadBalancerContext.generateNIWSException("Uri", e);

    // Assert
    assertEquals("Unable to execute RestClient request for URI:Uri",
        actualGenerateNIWSExceptionResult.getErrorMessage());
    assertEquals("Unable to execute RestClient request for URI:Uri",
        actualGenerateNIWSExceptionResult.getLocalizedMessage());
    assertEquals("Unable to execute RestClient request for URI:Uri", actualGenerateNIWSExceptionResult.getMessage());
    assertEquals("{no message: 0}", actualGenerateNIWSExceptionResult.getInternalMessage());
    assertNull(actualGenerateNIWSExceptionResult.getErrorObject());
    assertEquals(0, actualGenerateNIWSExceptionResult.getErrorCode());
    assertEquals(0, actualGenerateNIWSExceptionResult.getSuppressed().length);
    assertEquals(ClientException.ErrorType.GENERAL, actualGenerateNIWSExceptionResult.getErrorType());
    assertSame(e, actualGenerateNIWSExceptionResult.getCause());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#generateNIWSException(String, Throwable)}
   */
  @Test
  public void testGenerateNIWSException3() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    IOException e = new IOException("Read timed out", new Throwable());

    // Act
    ClientException actualGenerateNIWSExceptionResult = loadBalancerContext.generateNIWSException("Uri", e);

    // Assert
    assertEquals("Unable to execute RestClient request for URI:Uri:null",
        actualGenerateNIWSExceptionResult.getErrorMessage());
    assertEquals("Unable to execute RestClient request for URI:Uri:null",
        actualGenerateNIWSExceptionResult.getLocalizedMessage());
    assertEquals("Unable to execute RestClient request for URI:Uri:null",
        actualGenerateNIWSExceptionResult.getMessage());
    assertEquals("{no message: 5}", actualGenerateNIWSExceptionResult.getInternalMessage());
    assertNull(actualGenerateNIWSExceptionResult.getErrorObject());
    assertEquals(0, actualGenerateNIWSExceptionResult.getSuppressed().length);
    assertEquals(5, actualGenerateNIWSExceptionResult.getErrorCode());
    assertEquals(ClientException.ErrorType.READ_TIMEOUT_EXCEPTION, actualGenerateNIWSExceptionResult.getErrorType());
    assertSame(e, actualGenerateNIWSExceptionResult.getCause());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#generateNIWSException(String, Throwable)}
   */
  @Test
  public void testGenerateNIWSException4() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    IOException e = new IOException("Unable to execute RestClient request for URI:");

    // Act
    ClientException actualGenerateNIWSExceptionResult = loadBalancerContext.generateNIWSException("Uri", e);

    // Assert
    assertEquals("Unable to execute RestClient request for URI:Uri:Unable to execute RestClient request for URI:",
        actualGenerateNIWSExceptionResult.getErrorMessage());
    assertEquals("Unable to execute RestClient request for URI:Uri:Unable to execute RestClient request for URI:",
        actualGenerateNIWSExceptionResult.getLocalizedMessage());
    assertEquals("Unable to execute RestClient request for URI:Uri:Unable to execute RestClient request for URI:",
        actualGenerateNIWSExceptionResult.getMessage());
    assertEquals("{no message: 4}", actualGenerateNIWSExceptionResult.getInternalMessage());
    assertNull(actualGenerateNIWSExceptionResult.getErrorObject());
    assertEquals(0, actualGenerateNIWSExceptionResult.getSuppressed().length);
    assertEquals(4, actualGenerateNIWSExceptionResult.getErrorCode());
    assertEquals(ClientException.ErrorType.SOCKET_TIMEOUT_EXCEPTION, actualGenerateNIWSExceptionResult.getErrorType());
    assertSame(e, actualGenerateNIWSExceptionResult.getCause());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long)}
   */
  @Test
  public void testNoteRequestCompletion() {
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
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long)}
   */
  @Test
  public void testNoteRequestCompletion2() {
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
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long)}
   */
  @Test
  public void testNoteRequestCompletion3() {
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
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long, RetryHandler)}
   */
  @Test
  public void testNoteRequestCompletion4() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doNothing().when(stats).clearSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());
    Throwable e = new Throwable();

    // Act
    loadBalancerContext.noteRequestCompletion(stats, "Response", e, 1L, new DefaultLoadBalancerRetryHandler());

    // Assert
    verify(stats).clearSuccessiveConnectionFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long, RetryHandler)}
   */
  @Test
  public void testNoteRequestCompletion5() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doNothing().when(stats).clearSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());
    Throwable e = new Throwable();

    // Act
    loadBalancerContext.noteRequestCompletion(stats, null, e, 1L, new DefaultLoadBalancerRetryHandler());

    // Assert
    verify(stats).clearSuccessiveConnectionFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long, RetryHandler)}
   */
  @Test
  public void testNoteRequestCompletion6() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doNothing().when(stats).clearSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());
    Throwable e = new Throwable();

    // Act
    loadBalancerContext.noteRequestCompletion(stats, "#", e, 1L, new DefaultLoadBalancerRetryHandler());

    // Assert
    verify(stats).clearSuccessiveConnectionFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long, RetryHandler)}
   */
  @Test
  public void testNoteRequestCompletion7() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doNothing().when(stats).clearSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());
    Throwable e = new Throwable();

    // Act
    loadBalancerContext.noteRequestCompletion(stats, 10, e, 1L, new DefaultLoadBalancerRetryHandler());

    // Assert
    verify(stats).clearSuccessiveConnectionFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long, RetryHandler)}
   */
  @Test
  public void testNoteRequestCompletion8() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doNothing().when(stats).clearSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());

    // Act
    loadBalancerContext.noteRequestCompletion(stats, "Response", null, 1L, new DefaultLoadBalancerRetryHandler());

    // Assert
    verify(stats).clearSuccessiveConnectionFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long, RetryHandler)}
   */
  @Test
  public void testNoteRequestCompletion9() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doThrow(new RuntimeException("foo")).when(stats).decrementActiveRequestsCount();
    Throwable e = new Throwable();

    // Act
    loadBalancerContext.noteRequestCompletion(stats, "Response", e, 1L, new DefaultLoadBalancerRetryHandler());

    // Assert
    verify(stats).decrementActiveRequestsCount();
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long, RetryHandler)}
   */
  @Test
  public void testNoteRequestCompletion10() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doNothing().when(stats).addToFailureCount();
    doNothing().when(stats).incrementSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());
    Throwable e = new Throwable();
    RetryHandler errorHandler = mock(RetryHandler.class);
    when(errorHandler.isCircuitTrippingException(Mockito.<Throwable>any())).thenReturn(true);

    // Act
    loadBalancerContext.noteRequestCompletion(stats, null, e, 1L, errorHandler);

    // Assert
    verify(errorHandler).isCircuitTrippingException(isA(Throwable.class));
    verify(stats).addToFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).incrementSuccessiveConnectionFailureCount();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteRequestCompletion(ServerStats, Object, Throwable, long, RetryHandler)}
   */
  @Test
  public void testNoteRequestCompletion11() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doThrow(new RuntimeException("foo")).when(stats).incrementSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());
    Throwable e = new Throwable();
    RetryHandler errorHandler = mock(RetryHandler.class);
    when(errorHandler.isCircuitTrippingException(Mockito.<Throwable>any())).thenReturn(true);

    // Act
    loadBalancerContext.noteRequestCompletion(stats, null, e, 1L, errorHandler);

    // Assert
    verify(errorHandler).isCircuitTrippingException(isA(Throwable.class));
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).incrementSuccessiveConnectionFailureCount();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteError(ServerStats, ClientRequest, Throwable, long)}
   */
  @Test
  public void testNoteError() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    ServerStats stats = mock(ServerStats.class);
    doNothing().when(stats).clearSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());
    ClientRequest request = new ClientRequest();

    // Act
    loadBalancerContext.noteError(stats, request, new Throwable(), 1L);

    // Assert
    verify(stats).clearSuccessiveConnectionFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteError(ServerStats, ClientRequest, Throwable, long)}
   */
  @Test
  public void testNoteError2() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    ServerStats stats = mock(ServerStats.class);
    doNothing().when(stats).clearSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());
    ClientRequest request = new ClientRequest();

    // Act
    loadBalancerContext.noteError(stats, request, new Throwable("foo"), 1L);

    // Assert
    verify(stats).clearSuccessiveConnectionFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteError(ServerStats, ClientRequest, Throwable, long)}
   */
  @Test
  public void testNoteError3() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    ServerStats stats = mock(ServerStats.class);
    doThrow(new RuntimeException("foo")).when(stats).decrementActiveRequestsCount();
    ClientRequest request = new ClientRequest();

    // Act
    loadBalancerContext.noteError(stats, request, new Throwable(), 1L);

    // Assert
    verify(stats).decrementActiveRequestsCount();
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteResponse(ServerStats, ClientRequest, Object, long)}
   */
  @Test
  public void testNoteResponse() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doNothing().when(stats).clearSuccessiveConnectionFailureCount();
    doNothing().when(stats).decrementActiveRequestsCount();
    doNothing().when(stats).incrementNumRequests();
    doNothing().when(stats).noteResponseTime(anyDouble());

    // Act
    loadBalancerContext.noteResponse(stats, new ClientRequest(), "Response", 1L);

    // Assert
    verify(stats).clearSuccessiveConnectionFailureCount();
    verify(stats).decrementActiveRequestsCount();
    verify(stats).incrementNumRequests();
    verify(stats).noteResponseTime(eq(1.0d));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteResponse(ServerStats, ClientRequest, Object, long)}
   */
  @Test
  public void testNoteResponse2() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    DummyServerStats stats = mock(DummyServerStats.class);
    doThrow(new RuntimeException("foo")).when(stats).decrementActiveRequestsCount();

    // Act
    loadBalancerContext.noteResponse(stats, new ClientRequest(), "Response", 1L);

    // Assert
    verify(stats).decrementActiveRequestsCount();
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#noteOpenConnection(ServerStats)}
   */
  @Test
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
   * Method under test:
   * {@link LoadBalancerContext#noteOpenConnection(ServerStats)}
   */
  @Test
  public void testNoteOpenConnection2() {
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
   * Method under test:
   * {@link LoadBalancerContext#noteOpenConnection(ServerStats)}
   */
  @Test
  public void testNoteOpenConnection3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException("Error noting stats for client {}"));
    IPing ping2 = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping2, new AvailabilityFilteringRule());
    lb.setPing(ping);
    lb.addServer(new Server("42"));
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(lb);
    DummyServerStats serverStats = mock(DummyServerStats.class);
    doThrow(new RuntimeException("foo")).when(serverStats).incrementActiveRequestsCount();

    // Act
    loadBalancerContext.noteOpenConnection(serverStats);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    verify(serverStats).incrementActiveRequestsCount();
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}
   */
  @Test
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
   * Method under test:
   * {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}
   */
  @Test
  public void testDeriveSchemeAndPortFromPartialUri2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act
    Pair<String, Integer> actualDeriveSchemeAndPortFromPartialUriResult = (new LoadBalancerContext(
        new BaseLoadBalancer(ping, new AvailabilityFilteringRule())))
            .deriveSchemeAndPortFromPartialUri(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    assertEquals("file", actualDeriveSchemeAndPortFromPartialUriResult.first());
    assertEquals(80, actualDeriveSchemeAndPortFromPartialUriResult.second().intValue());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getDefaultPortFromScheme(String)}
   */
  @Test
  public void testGetDefaultPortFromScheme() {
    // Arrange, Act and Assert
    assertEquals(-1, (new LoadBalancerContext(new BaseLoadBalancer())).getDefaultPortFromScheme("Scheme"));
    assertEquals(-1, (new LoadBalancerContext(new BaseLoadBalancer())).getDefaultPortFromScheme(null));
    assertEquals(80, (new LoadBalancerContext(new BaseLoadBalancer())).getDefaultPortFromScheme("http"));
    assertEquals(443, (new LoadBalancerContext(new BaseLoadBalancer())).getDefaultPortFromScheme("https"));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getDefaultPortFromScheme(String)}
   */
  @Test
  public void testGetDefaultPortFromScheme2() {
    // Arrange
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertEquals(-1, (new LoadBalancerContext(new BaseLoadBalancer(ping, new AvailabilityFilteringRule())))
        .getDefaultPortFromScheme("Scheme"));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getDefaultPortFromScheme(String)}
   */
  @Test
  public void testGetDefaultPortFromScheme3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));

    // Act and Assert
    assertEquals(-1, (new LoadBalancerContext(lb)).getDefaultPortFromScheme("Scheme"));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  public void testDeriveHostAndPortFromVipAddress() throws ClientException, URISyntaxException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    (new LoadBalancerContext(new BaseLoadBalancer())).deriveHostAndPortFromVipAddress("#");
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  public void testDeriveHostAndPortFromVipAddress2() throws ClientException, URISyntaxException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    (new LoadBalancerContext(new BaseLoadBalancer())).deriveHostAndPortFromVipAddress("42");
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getServerFromLoadBalancer(URI, Object)}
   */
  @Test
  public void testGetServerFromLoadBalancer() throws ClientException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    (new LoadBalancerContext(new BaseLoadBalancer())).getServerFromLoadBalancer(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(), "Load Balancer Key");
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getServerFromLoadBalancer(URI, Object)}
   */
  @Test
  public void testGetServerFromLoadBalancer2() throws ClientException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    (new LoadBalancerContext(null)).getServerFromLoadBalancer(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(), "Load Balancer Key");
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getServerFromLoadBalancer(URI, Object)}
   */
  @Test
  public void testGetServerFromLoadBalancer3() throws ClientException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    (new LoadBalancerContext(new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig()))).getServerFromLoadBalancer(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(), "Load Balancer Key");
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getServerFromLoadBalancer(URI, Object)}
   */
  @Test
  public void testGetServerFromLoadBalancer4() throws ClientException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    (new LoadBalancerContext(new NoOpLoadBalancer())).getServerFromLoadBalancer(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(), "Load Balancer Key");
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getServerFromLoadBalancer(URI, Object)}
   */
  @Test
  public void testGetServerFromLoadBalancer5() throws ClientException {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, null);
    Server newServer = new Server("42");
    lb.addServer(newServer);

    // Act
    Server actualServerFromLoadBalancer = (new LoadBalancerContext(lb)).getServerFromLoadBalancer(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(), "Load Balancer Key");

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertSame(newServer, actualServerFromLoadBalancer);
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getServerFromLoadBalancer(URI, Object)}
   */
  @Test
  public void testGetServerFromLoadBalancer6() throws ClientException {
    // Arrange
    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    // Act and Assert
    assertSame(newServer, (new LoadBalancerContext(lb)).getServerFromLoadBalancer(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(), "Load Balancer Key"));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getServerFromLoadBalancer(URI, Object)}
   */
  @Test
  public void testGetServerFromLoadBalancer7() throws ClientException {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServer(new Server(null));

    // Act and Assert
    thrown.expect(ClientException.class);
    (new LoadBalancerContext(lb)).getServerFromLoadBalancer(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(), "Load Balancer Key");
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}
   */
  @Test
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
   * Method under test:
   * {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}
   */
  @Test
  public void testReconstructURIWithServer2() {
    // Arrange
    IPing ping = mock(IPing.class);
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(
        new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act
    URI actualReconstructURIWithServerResult = loadBalancerContext.reconstructURIWithServer(new Server("42"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    String expectedToStringResult = String.join("", "file://42:80",
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toString());
    assertEquals(expectedToStringResult, actualReconstructURIWithServerResult.toString());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}
   */
  @Test
  public void testReconstructURIWithServer3() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act and Assert
    thrown.expect(RuntimeException.class);
    loadBalancerContext.reconstructURIWithServer(new Server("://", "localhost", 8080),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}
   */
  @Test
  public void testReconstructURIWithServer4() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act and Assert
    thrown.expect(RuntimeException.class);
    loadBalancerContext.reconstructURIWithServer(new Server("://", "localhost", -1),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#reconstructURIWithServer(Server, URI)}
   */
  @Test
  public void testReconstructURIWithServer5() {
    // Arrange
    IPing ping = mock(IPing.class);
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(
        new BaseLoadBalancer(ping, new RoundRobinRule(new BaseLoadBalancer())));

    // Act
    URI actualReconstructURIWithServerResult = loadBalancerContext.reconstructURIWithServer(new Server("42"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    String expectedToStringResult = String.join("", "file://42:80",
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toString());
    assertEquals(expectedToStringResult, actualReconstructURIWithServerResult.toString());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getRetriesNextServer(IClientConfig)}
   */
  @Test
  public void testGetRetriesNextServer() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act and Assert
    assertEquals(1, loadBalancerContext.getRetriesNextServer(DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getRetriesNextServer(IClientConfig)}
   */
  @Test
  public void testGetRetriesNextServer2() {
    // Arrange, Act and Assert
    assertEquals(1, (new LoadBalancerContext(new BaseLoadBalancer())).getRetriesNextServer(null));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getRetriesNextServer(IClientConfig)}
   */
  @Test
  public void testGetRetriesNextServer3() {
    // Arrange
    IPing ping = mock(IPing.class);
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(
        new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act and Assert
    assertEquals(1, loadBalancerContext.getRetriesNextServer(DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Method under test: {@link LoadBalancerContext#getServerStats(Server)}
   */
  @Test
  public void testGetServerStats() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    loadBalancerContext.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(loadBalancerContext.getServerStats(null));
  }

  /**
   * Method under test: {@link LoadBalancerContext#getServerStats(Server)}
   */
  @Test
  public void testGetServerStats2() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(null);

    // Act and Assert
    assertNull(loadBalancerContext.getServerStats(new Server("42")));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getNumberRetriesOnSameServer(IClientConfig)}
   */
  @Test
  public void testGetNumberRetriesOnSameServer() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());

    // Act and Assert
    assertEquals(0, loadBalancerContext.getNumberRetriesOnSameServer(DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getNumberRetriesOnSameServer(IClientConfig)}
   */
  @Test
  public void testGetNumberRetriesOnSameServer2() {
    // Arrange, Act and Assert
    assertEquals(0, (new LoadBalancerContext(new BaseLoadBalancer())).getNumberRetriesOnSameServer(null));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#getNumberRetriesOnSameServer(IClientConfig)}
   */
  @Test
  public void testGetNumberRetriesOnSameServer3() {
    // Arrange
    IPing ping = mock(IPing.class);
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(
        new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act and Assert
    assertEquals(0, loadBalancerContext.getNumberRetriesOnSameServer(DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}
   */
  @Test
  public void testHandleSameServerRetry() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    Server server = new Server("42");

    // Act and Assert
    assertTrue(loadBalancerContext.handleSameServerRetry(server, 3, 3, new Throwable()));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}
   */
  @Test
  public void testHandleSameServerRetry2() {
    // Arrange
    IPing ping = mock(IPing.class);
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(
        new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));
    Server server = new Server("42");

    // Act and Assert
    assertTrue(loadBalancerContext.handleSameServerRetry(server, 3, 3, new Throwable()));
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#handleSameServerRetry(Server, int, int, Throwable)}
   */
  @Test
  public void testHandleSameServerRetry3() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    Server server = new Server("42");

    // Act and Assert
    assertFalse(loadBalancerContext.handleSameServerRetry(server, 10, 3, new Throwable()));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link LoadBalancerContext#setLoadBalancer(ILoadBalancer)}
   *   <li>{@link LoadBalancerContext#setOkToRetryOnAllOperations(boolean)}
   *   <li>{@link LoadBalancerContext#setRetryHandler(RetryHandler)}
   *   <li>{@link LoadBalancerContext#setMaxAutoRetries(int)}
   *   <li>{@link LoadBalancerContext#setMaxAutoRetriesNextServer(int)}
   *   <li>{@link LoadBalancerContext#getClientName()}
   *   <li>{@link LoadBalancerContext#getLoadBalancer()}
   *   <li>{@link LoadBalancerContext#getMaxAutoRetries()}
   *   <li>{@link LoadBalancerContext#getMaxAutoRetriesNextServer()}
   *   <li>{@link LoadBalancerContext#getRetryHandler()}
   *   <li>{@link LoadBalancerContext#isOkToRetryOnAllOperations()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    loadBalancerContext.setLoadBalancer(lb);
    loadBalancerContext.setOkToRetryOnAllOperations(true);
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();
    loadBalancerContext.setRetryHandler(retryHandler);
    loadBalancerContext.setMaxAutoRetries(3);
    loadBalancerContext.setMaxAutoRetriesNextServer(3);
    String actualClientName = loadBalancerContext.getClientName();
    ILoadBalancer actualLoadBalancer = loadBalancerContext.getLoadBalancer();
    int actualMaxAutoRetries = loadBalancerContext.getMaxAutoRetries();
    int actualMaxAutoRetriesNextServer = loadBalancerContext.getMaxAutoRetriesNextServer();
    RetryHandler actualRetryHandler = loadBalancerContext.getRetryHandler();

    // Assert
    assertEquals("default", actualClientName);
    assertEquals(3, actualMaxAutoRetries);
    assertEquals(3, actualMaxAutoRetriesNextServer);
    assertTrue(loadBalancerContext.isOkToRetryOnAllOperations());
    assertSame(retryHandler, actualRetryHandler);
    assertSame(lb, actualLoadBalancer);
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer)}
   */
  @Test
  public void testNewLoadBalancerContext() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer)}
   */
  @Test
  public void testNewLoadBalancerContext2() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}
   */
  @Test
  public void testNewLoadBalancerContext3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}
   */
  @Test
  public void testNewLoadBalancerContext4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb, null);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}
   */
  @Test
  public void testNewLoadBalancerContext5() {
    // Arrange and Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(null,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.getLoadBalancer());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}
   */
  @Test
  public void testNewLoadBalancerContext6() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb,
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}
   */
  @Test
  public void testNewLoadBalancerContext7() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " "));

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("Dr Jane Doe", actualLoadBalancerContext.getClientName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", config.getName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("count", nextResult.getValue());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}
   */
  @Test
  public void testNewLoadBalancerContext8() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " "));

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}
   */
  @Test
  public void testNewLoadBalancerContext9() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb,
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, "value"));

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnSameServer());
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  public void testNewLoadBalancerContext10() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler handler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb, clientConfig, handler);

    // Assert
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(handler, actualLoadBalancerContext.getRetryHandler());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  public void testNewLoadBalancerContext11() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultLoadBalancerRetryHandler handler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb, null, handler);

    // Assert
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(handler, actualLoadBalancerContext.getRetryHandler());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }

  /**
   * Method under test:
   * {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig, RetryHandler)}
   */
  @Test
  public void testNewLoadBalancerContext12() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    DefaultLoadBalancerRetryHandler handler = new DefaultLoadBalancerRetryHandler();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb, clientConfig, handler);

    // Assert
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
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
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", config.getName());
    assertEquals("default_LoadBalancerExecutionTimer", config2.getName());
    assertEquals("statistic", nextResult.getKey());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(0, tags2.size());
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMax().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getMin().doubleValue(), 0.0);
    assertEquals(0.0d, ((BasicTimer) executeTracer).getTotalTime().doubleValue(), 0.0);
    assertEquals(0L, ((BasicTimer) executeTracer).getCount().longValue());
    assertEquals(0L, executeTracer.getValue().longValue());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertEquals(3, tags.size());
    assertEquals(TimeUnit.MILLISECONDS, executeTracer.getTimeUnit());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertFalse(tags.isEmpty());
    assertFalse(tags2.iterator().hasNext());
    assertTrue(tags2.isEmpty());
    assertTrue(iteratorResult.hasNext());
    assertSame(handler, actualLoadBalancerContext.getRetryHandler());
    assertSame(lb, actualLoadBalancerContext.getLoadBalancer());
    assertSame(publishingPolicy, config2.getPublishingPolicy());
  }
}
