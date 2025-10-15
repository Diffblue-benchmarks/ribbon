package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.ClientException;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.SimpleVipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Timer;
import com.netflix.util.Pair;
import com.netflix.util.concurrent.ShutdownEnabledTimer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class LoadBalancerContextDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer)"})
  public void testNewLoadBalancerContext() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb);

    // Assert
    assertTrue(
        actualLoadBalancerContext.getRetryHandler() instanceof DefaultLoadBalancerRetryHandler);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertTrue(actualLoadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertSame(lb, loadBalancer);
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb, clientConfig);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext3() {
    // Arrange
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " ");

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(new BaseLoadBalancer(), clientConfig);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertNull(clientConfig.getResolver());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb, clientConfig);

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertEquals("42 Main St", actualLoadBalancerContext.vipAddresses);
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext5() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertEquals("42 Main St", actualLoadBalancerContext.vipAddresses);
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer)"})
  public void testNewLoadBalancerContext6() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb);

    // Assert
    assertTrue(
        actualLoadBalancerContext.getRetryHandler() instanceof DefaultLoadBalancerRetryHandler);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    assertTrue(actualLoadBalancerContext.getExecuteTracer() instanceof BasicTimer);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, actualLoadBalancerContext.getMaxAutoRetries());
    assertEquals(1, actualLoadBalancerContext.getMaxAutoRetriesNextServer());
    assertFalse(actualLoadBalancerContext.isOkToRetryOnAllOperations());
    assertSame(lb, loadBalancer);
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext7() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig clientConfig =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb, clientConfig);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext8() {
    // Arrange
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " ");

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(new BaseLoadBalancer(), clientConfig);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertNull(clientConfig.getResolver());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext9() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb, clientConfig);

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertEquals("42 Main St", actualLoadBalancerContext.vipAddresses);
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext10() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues(null, " ");

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertNull(clientConfig.getResolver());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext11() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertEquals("42 Main St", actualLoadBalancerContext.vipAddresses);
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <ul>
   *   <li>Given {@code MILLISECONDS}.
   *   <li>Then return ClientName is {@code MILLISECONDS}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext_givenMilliseconds_thenReturnClientNameIsMilliseconds() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[] {2});

    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");
    clientConfig.setClientName("MILLISECONDS");

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("MILLISECONDS", actualLoadBalancerContext.getClientName());
    assertEquals("MILLISECONDS_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("MILLISECONDS_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertSame(lb.upServerList, ((BaseLoadBalancer) loadBalancer).upServerList);
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext_givenNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[] {2});

    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");
    clientConfig.setClientName(null);

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertSame(lb.upServerList, ((BaseLoadBalancer) loadBalancer).upServerList);
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <ul>
   *   <li>Then LoadBalancer {@link BaseLoadBalancer#lbTimer} return {@link ShutdownEnabledTimer}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext_thenLoadBalancerLbTimerReturnShutdownEnabledTimer() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPing(ping);
    Server newServer = new Server("42");
    lb.addServer(newServer);

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    java.util.Timer timer = ((BaseLoadBalancer) loadBalancer).lbTimer;
    assertTrue(timer instanceof ShutdownEnabledTimer);
    List<Server> allServers = loadBalancer.getAllServers();
    assertEquals(1, allServers.size());
    assertSame(newServer, allServers.get(0));
    assertSame(lb.allServerList, ((BaseLoadBalancer) loadBalancer).allServerList);
    assertSame(lb.lbTimer, timer);
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <ul>
   *   <li>Then return ClientName is {@code Dr Jane Doe}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext_thenReturnClientNameIsDrJaneDoe() {
    // Arrange and Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(
            new BaseLoadBalancer(),
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " "));

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("Dr Jane Doe", actualLoadBalancerContext.getClientName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <ul>
   *   <li>Then return ClientName is {@code Dr Jane Doe}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext_thenReturnClientNameIsDrJaneDoe2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("Dr Jane Doe", actualLoadBalancerContext.getClientName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <ul>
   *   <li>Then return ClientName is {@code Dr Jane Doe}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext_thenReturnClientNameIsDrJaneDoe3() {
    // Arrange and Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(
            new BaseLoadBalancer(),
            DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " "));

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("Dr Jane Doe", actualLoadBalancerContext.getClientName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <ul>
   *   <li>Then return ClientName is {@code Dr Jane Doe}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext_thenReturnClientNameIsDrJaneDoe4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig =
        DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe", " ");

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("Dr Jane Doe", actualLoadBalancerContext.getClientName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("Dr Jane Doe_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <ul>
   *   <li>Then return RetryHandler is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext_thenReturnRetryHandlerIsNull() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPing(ping);
    lb.addServer(new Server("42"));

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    LoadBalancerContext actualLoadBalancerContext = new LoadBalancerContext(lb, clientConfig, null);

    // Assert
    verify(ping).isAlive(isA(Server.class));
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertNull(actualLoadBalancerContext.getRetryHandler());
    assertEquals(1, ((BaseLoadBalancer) loadBalancer).allServerList.size());
    assertEquals(lb.allServerList, loadBalancer.getReachableServers());
    assertSame(lb.upServerList, ((BaseLoadBalancer) loadBalancer).upServerList);
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <ul>
   *   <li>Then return RetryHandler MaxRetriesOnNextServer is zero.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext_thenReturnRetryHandlerMaxRetriesOnNextServerIsZero() {
    // Arrange and Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(new BaseLoadBalancer(), null);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <ul>
   *   <li>Then return RetryHandler MaxRetriesOnNextServer is zero.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext_thenReturnRetryHandlerMaxRetriesOnNextServerIsZero2() {
    // Arrange and Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(new BaseLoadBalancer(), null);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(0, retryHandler.getMaxRetriesOnNextServer());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then EmptyConfig Resolver is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext_whenEmptyConfig_thenEmptyConfigResolverIsNull() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(new BaseLoadBalancer(), clientConfig);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertNull(clientConfig.getResolver());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then EmptyConfig Resolver is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig)"})
  public void testNewLoadBalancerContext_whenEmptyConfig_thenEmptyConfigResolverIsNull2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(new BaseLoadBalancer(), clientConfig);

    // Assert
    RetryHandler retryHandler = actualLoadBalancerContext.getRetryHandler();
    assertTrue(retryHandler instanceof DefaultLoadBalancerRetryHandler);
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertNull(clientConfig.getResolver());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertEquals(1, retryHandler.getMaxRetriesOnNextServer());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then EmptyConfig Resolver is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext_whenEmptyConfig_thenEmptyConfigResolverIsNull3() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertNull(clientConfig.getResolver());
    assertNull(actualLoadBalancerContext.vipAddresses);
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then return ClientName is {@code default}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext_whenEmptyConfig_thenReturnClientNameIsDefault() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, clientConfig, new DefaultLoadBalancerRetryHandler());

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("default_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertTrue(((BaseLoadBalancer) loadBalancer).upServerList.isEmpty());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return ClientName is {@code default}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext_whenNull_thenReturnClientNameIsDefault() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, null, new DefaultLoadBalancerRetryHandler());

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    Monitor<?> getResult = monitors.get(1);
    assertTrue(getResult instanceof StepCounter);
    assertEquals("default", actualLoadBalancerContext.getClientName());
    assertEquals("default_LoadBalancerExecutionTimer", executeTracer.getConfig().getName());
    assertEquals("default_LoadBalancerExecutionTimer", getResult.getConfig().getName());
    assertTrue(((BaseLoadBalancer) loadBalancer).upServerList.isEmpty());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer, IClientConfig,
   * RetryHandler)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return LoadBalancer Ping is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#LoadBalancerContext(ILoadBalancer,
   * IClientConfig, RetryHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoadBalancerContext.<init>(ILoadBalancer, IClientConfig, RetryHandler)"})
  public void testNewLoadBalancerContext_whenNull_thenReturnLoadBalancerPingIsNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    LoadBalancerContext actualLoadBalancerContext =
        new LoadBalancerContext(lb, null, new DefaultLoadBalancerRetryHandler());

    // Assert
    ILoadBalancer loadBalancer = actualLoadBalancerContext.getLoadBalancer();
    assertTrue(loadBalancer instanceof BaseLoadBalancer);
    IRule rule = ((BaseLoadBalancer) loadBalancer).getRule();
    assertTrue(rule instanceof RoundRobinRule);
    Timer executeTracer = actualLoadBalancerContext.getExecuteTracer();
    assertTrue(executeTracer instanceof BasicTimer);
    List<Monitor<?>> monitors = ((BasicTimer) executeTracer).getMonitors();
    assertEquals(4, monitors.size());
    assertTrue(monitors.get(1) instanceof StepCounter);
    assertNull(((BaseLoadBalancer) loadBalancer).getPing());
    assertNull(((BaseLoadBalancer) loadBalancer).lbTimer);
    assertTrue(loadBalancer.getAllServers().isEmpty());
    assertTrue(((BaseLoadBalancer) loadBalancer).allServerList.isEmpty());
    assertSame(loadBalancer, rule.getLoadBalancer());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String LoadBalancerContext.getClientName()",
    "ILoadBalancer LoadBalancerContext.getLoadBalancer()",
    "int LoadBalancerContext.getMaxAutoRetries()",
    "int LoadBalancerContext.getMaxAutoRetriesNextServer()",
    "RetryHandler LoadBalancerContext.getRetryHandler()",
    "boolean LoadBalancerContext.isOkToRetryOnAllOperations()",
    "void LoadBalancerContext.setLoadBalancer(ILoadBalancer)",
    "void LoadBalancerContext.setMaxAutoRetries(int)",
    "void LoadBalancerContext.setMaxAutoRetriesNextServer(int)",
    "void LoadBalancerContext.setOkToRetryOnAllOperations(boolean)",
    "void LoadBalancerContext.setRetryHandler(RetryHandler)"
  })
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
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String LoadBalancerContext.getClientName()",
    "ILoadBalancer LoadBalancerContext.getLoadBalancer()",
    "int LoadBalancerContext.getMaxAutoRetries()",
    "int LoadBalancerContext.getMaxAutoRetriesNextServer()",
    "RetryHandler LoadBalancerContext.getRetryHandler()",
    "boolean LoadBalancerContext.isOkToRetryOnAllOperations()",
    "void LoadBalancerContext.setLoadBalancer(ILoadBalancer)",
    "void LoadBalancerContext.setMaxAutoRetries(int)",
    "void LoadBalancerContext.setMaxAutoRetriesNextServer(int)",
    "void LoadBalancerContext.setOkToRetryOnAllOperations(boolean)",
    "void LoadBalancerContext.setRetryHandler(RetryHandler)"
  })
  public void testGettersAndSetters2() {
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
   * Test {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveSchemeAndPortFromPartialUri(URI)"})
  public void testDeriveSchemeAndPortFromPartialUri() {
    // Arrange and Act
    Pair<String, Integer> actualDeriveSchemeAndPortFromPartialUriResult =
        new LoadBalancerContext(new BaseLoadBalancer())
            .deriveSchemeAndPortFromPartialUri(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    assertEquals("file", actualDeriveSchemeAndPortFromPartialUriResult.first());
    assertEquals(80, actualDeriveSchemeAndPortFromPartialUriResult.second().intValue());
  }

  /**
   * Test {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveSchemeAndPortFromPartialUri(URI)"})
  public void testDeriveSchemeAndPortFromPartialUri2() {
    // Arrange and Act
    Pair<String, Integer> actualDeriveSchemeAndPortFromPartialUriResult =
        new LoadBalancerContext(new BaseLoadBalancer())
            .deriveSchemeAndPortFromPartialUri(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    assertEquals("file", actualDeriveSchemeAndPortFromPartialUriResult.first());
    assertEquals(80, actualDeriveSchemeAndPortFromPartialUriResult.second().intValue());
  }

  /**
   * Test {@link LoadBalancerContext#deriveHostAndPortFromVipAddress(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then throw {@link ClientException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveHostAndPortFromVipAddress(String)"})
  public void testDeriveHostAndPortFromVipAddress_when42_thenThrowClientException()
      throws ClientException, URISyntaxException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    new LoadBalancerContext(new BaseLoadBalancer()).deriveHostAndPortFromVipAddress("42");
  }

  /**
   * Test {@link LoadBalancerContext#deriveHostAndPortFromVipAddress(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then throw {@link ClientException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveHostAndPortFromVipAddress(String)"})
  public void testDeriveHostAndPortFromVipAddress_when42_thenThrowClientException2()
      throws ClientException, URISyntaxException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    new LoadBalancerContext(new BaseLoadBalancer()).deriveHostAndPortFromVipAddress("42");
  }

  /**
   * Test {@link LoadBalancerContext#deriveHostAndPortFromVipAddress(String)}.
   *
   * <ul>
   *   <li>When {@code #}.
   *   <li>Then throw {@link ClientException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveHostAndPortFromVipAddress(String)"})
  public void testDeriveHostAndPortFromVipAddress_whenNumberSign_thenThrowClientException()
      throws ClientException, URISyntaxException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    new LoadBalancerContext(new BaseLoadBalancer()).deriveHostAndPortFromVipAddress("#");
  }

  /**
   * Test {@link LoadBalancerContext#deriveHostAndPortFromVipAddress(String)}.
   *
   * <ul>
   *   <li>When {@code #}.
   *   <li>Then throw {@link ClientException}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#deriveHostAndPortFromVipAddress(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveHostAndPortFromVipAddress(String)"})
  public void testDeriveHostAndPortFromVipAddress_whenNumberSign_thenThrowClientException2()
      throws ClientException, URISyntaxException {
    // Arrange, Act and Assert
    thrown.expect(ClientException.class);
    new LoadBalancerContext(new BaseLoadBalancer()).deriveHostAndPortFromVipAddress("#");
  }
}
