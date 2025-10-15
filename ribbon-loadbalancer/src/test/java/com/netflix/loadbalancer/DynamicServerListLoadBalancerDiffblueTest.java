package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.IClientConfigAware;
import com.netflix.client.IClientConfigAware.Factory;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.loadbalancer.ServerListUpdater.UpdateAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class DynamicServerListLoadBalancerDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.<init>()"})
  public void testNewDynamicServerListLoadBalancer() {
    // Arrange and Act
    DynamicServerListLoadBalancer<Server> actualDynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();

    // Assert
    assertTrue(actualDynamicServerListLoadBalancer.getRule() instanceof RoundRobinRule);
    assertTrue(actualDynamicServerListLoadBalancer.serverComparator instanceof ServerComparator);
    assertTrue(actualDynamicServerListLoadBalancer.allServerLock instanceof ReentrantReadWriteLock);
    assertTrue(actualDynamicServerListLoadBalancer.upServerLock instanceof ReentrantReadWriteLock);
    assertEquals("default", actualDynamicServerListLoadBalancer.getName());
    assertNull(actualDynamicServerListLoadBalancer.getPrimeConnections());
    assertNull(actualDynamicServerListLoadBalancer.getClientConfig());
    assertNull(actualDynamicServerListLoadBalancer.getPing());
    assertNull(actualDynamicServerListLoadBalancer.getServerListImpl());
    assertNull(actualDynamicServerListLoadBalancer.getFilter());
    assertNull(actualDynamicServerListLoadBalancer.getServerListUpdater());
    assertNull(actualDynamicServerListLoadBalancer.lbTimer);
    assertEquals(10, actualDynamicServerListLoadBalancer.getPingInterval());
    assertEquals(5, actualDynamicServerListLoadBalancer.getMaxTotalPingTime());
    assertFalse(actualDynamicServerListLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualDynamicServerListLoadBalancer.isPingInProgress());
    assertFalse(actualDynamicServerListLoadBalancer.isSecure);
    assertFalse(actualDynamicServerListLoadBalancer.useTunnel);
    assertTrue(actualDynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualDynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualDynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(actualDynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer(IClientConfig, IRule,
   * IPing, ServerList, ServerListFilter)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DynamicServerListLoadBalancer#DynamicServerListLoadBalancer(IClientConfig, IRule, IPing,
   * ServerList, ServerListFilter)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicServerListLoadBalancer.<init>(IClientConfig, IRule, IPing, ServerList, ServerListFilter)"
  })
  public void testNewDynamicServerListLoadBalancer_givenNull_thenThrowRuntimeException() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setClientName(null);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    new DynamicServerListLoadBalancer<>(
        clientConfig, rule, ping, new ConfigurationBasedServerList(), mock(ServerListFilter.class));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer(IClientConfig, IRule,
   * IPing, ServerList, ServerListFilter, ServerListUpdater)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DynamicServerListLoadBalancer#DynamicServerListLoadBalancer(IClientConfig, IRule, IPing,
   * ServerList, ServerListFilter, ServerListUpdater)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicServerListLoadBalancer.<init>(IClientConfig, IRule, IPing, ServerList, ServerListFilter, ServerListUpdater)"
  })
  public void testNewDynamicServerListLoadBalancer_givenNull_thenThrowRuntimeException2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setClientName(null);
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    ConfigurationBasedServerList serverList = new ConfigurationBasedServerList();
    ServerListFilter<Server> filter = mock(ServerListFilter.class);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    new DynamicServerListLoadBalancer<>(
        clientConfig, rule, ping, serverList, filter, new PollingServerListUpdater());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When EmptyConfig ClientName is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DynamicServerListLoadBalancer#DynamicServerListLoadBalancer(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.<init>(IClientConfig)"})
  public void testNewDynamicServerListLoadBalancer_givenNull_whenEmptyConfigClientNameIsNull() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setClientName(null);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    new DynamicServerListLoadBalancer<>(clientConfig);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DynamicServerListLoadBalancer#DynamicServerListLoadBalancer(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.<init>(IClientConfig)"})
  public void testNewDynamicServerListLoadBalancer_givenTrue() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withEnablePrimeConnections(true);
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act and Assert
    thrown.expect(RuntimeException.class);
    new DynamicServerListLoadBalancer<>(clientConfig);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#initWithNiwsConfig(IClientConfig, Factory)} with
   * {@code clientConfig}, {@code factory}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#initWithNiwsConfig(IClientConfig,
   * Factory)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicServerListLoadBalancer.initWithNiwsConfig(IClientConfig, Factory)"
  })
  public void testInitWithNiwsConfigWithClientConfigFactory_thenThrowRuntimeException()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    Factory factory = mock(Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new RuntimeException());

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.initWithNiwsConfig(clientConfig, factory);
    verify(factory)
        .create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.restOfInit(IClientConfig)"})
  public void testRestOfInit() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.restOfInit(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} addServer
   *       {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.restOfInit(IClientConfig)"})
  public void testRestOfInit_givenDynamicServerListLoadBalancerAddServerServerWithIdIs42() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.restOfInit(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}
   *       EnablePrimingConnections is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.restOfInit(IClientConfig)"})
  public void testRestOfInit_givenDynamicServerListLoadBalancerEnablePrimingConnectionsIsTrue() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.restOfInit(DefaultClientConfigImpl.getEmptyConfig());

    // Assert that nothing has changed
    verify(serverListUpdater).start(isA(UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} Ping is
   *       {@link IPing}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.restOfInit(IClientConfig)"})
  public void testRestOfInit_givenDynamicServerListLoadBalancerPingIsIPing() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.restOfInit(DefaultClientConfigImpl.getEmptyConfig());

    // Assert that nothing has changed
    verify(serverListUpdater).start(isA(UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} Ping is
   *       {@link IPing}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.restOfInit(IClientConfig)"})
  public void testRestOfInit_givenDynamicServerListLoadBalancerPingIsIPing2() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.restOfInit(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@link ServerListChangeListener} {@link
   *       ServerListChangeListener#serverListChanged(List, List)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.restOfInit(IClientConfig)"})
  public void testRestOfInit_givenServerListChangeListenerServerListChangedDoesNothing() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.restOfInit(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@link ServerListChangeListener} {@link
   *       ServerListChangeListener#serverListChanged(List, List)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.restOfInit(IClientConfig)"})
  public void testRestOfInit_givenServerListChangeListenerServerListChangedDoesNothing2() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.restOfInit(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} AllServers
   *       Empty.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.restOfInit(IClientConfig)"})
  public void testRestOfInit_thenDynamicServerListLoadBalancerAllServersEmpty() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.restOfInit(DefaultClientConfigImpl.getEmptyConfig());

    // Assert that nothing has changed
    verify(serverListUpdater).start(isA(UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, lsrv);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, lsrv);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenDynamicServerListLoadBalancer() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} Ping is
   *       {@link IPing}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenDynamicServerListLoadBalancerPingIsIPing() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.setServersList(new ArrayList<>());

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Given {@link ServerListChangeListener} {@link
   *       ServerListChangeListener#serverListChanged(List, List)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenServerListChangeListenerServerListChangedDoesNothing() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, lsrv);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Given {@link ServerListChangeListener} {@link
   *       ServerListChangeListener#serverListChanged(List, List)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_givenServerListChangeListenerServerListChangedDoesNothing2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, lsrv);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServersList(List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link
   *       DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link
   *       BaseLoadBalancer#upServerList}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServersList(List)"})
  public void testSetServersList_thenArrayListIsDynamicServerListLoadBalancerUpServerList() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, lsrv);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(new HashMap<>());

    // Assert that nothing has changed
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones2() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats =
        dynamicServerListLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("setting server list for zones: {}");
    assertEquals("default:setting server list for zones: {}", getResult.monitorId);
    assertEquals("setting server list for zones: {}", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones3() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
    assertSame(serverList, stringListMap.get("Setting server list for zones: {}"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones4() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    dynamicServerListLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    LoadBalancerStats loadBalancerStats = dynamicServerListLoadBalancer.getLoadBalancerStats();
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
    assertSame(serverList, stringListMap.get("Setting server list for zones: {}"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones5() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("setting server list for zones: {}");
    assertEquals("default:setting server list for zones: {}", getResult.monitorId);
    assertEquals("setting server list for zones: {}", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones6() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new AvailabilityFilteringRule());
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("setting server list for zones: {}");
    assertEquals("default:setting server list for zones: {}", getResult.monitorId);
    assertEquals("setting server list for zones: {}", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenArrayListAddNull() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(null);

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("setting server list for zones: {}");
    assertEquals("default:setting server list for zones: {}", getResult.monitorId);
    assertEquals("setting server list for zones: {}", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>Then {@link HashMap#HashMap()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenArrayListAddServerWithIdIs42_thenHashMapSizeIsOne() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", serverList);

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    assertEquals(1, zoneServersMap.size());
    List<Server> getResult = zoneServersMap.get("Setting server list for zones: {}");
    assertEquals(1, getResult.size());
    LoadBalancerStats loadBalancerStats = zoneAwareLoadBalancer.getLoadBalancerStats();
    Map<String, List<? extends Server>> stringListMap = loadBalancerStats.upServerListZoneMap;
    assertEquals(1, stringListMap.size());
    Set<String> availableZones = loadBalancerStats.getAvailableZones();
    assertEquals(1, availableZones.size());
    assertTrue(getResult.get(0).isAlive());
    assertTrue(availableZones.contains("Setting server list for zones: {}"));
    assertSame(serverList, stringListMap.get("Setting server list for zones: {}"));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Rule is {@link
   *       BestAvailableRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenZoneAwareLoadBalancerRuleIsBestAvailableRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new BestAvailableRule());
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("setting server list for zones: {}");
    assertEquals("default:setting server list for zones: {}", getResult.monitorId);
    assertEquals("setting server list for zones: {}", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} Rule is {@link RandomRule}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#setServerListForZones(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.setServerListForZones(Map)"})
  public void testSetServerListForZones_givenZoneAwareLoadBalancerRuleIsRandomRule() {
    // Arrange
    ZoneAwareLoadBalancer<Server> zoneAwareLoadBalancer = new ZoneAwareLoadBalancer<>();
    zoneAwareLoadBalancer.setRule(new RandomRule());
    zoneAwareLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    HashMap<String, List<Server>> zoneServersMap = new HashMap<>();
    zoneServersMap.put("Setting server list for zones: {}", new ArrayList<>());

    // Act
    zoneAwareLoadBalancer.setServerListForZones(zoneServersMap);

    // Assert
    Map<String, ZoneStats> zoneStats = zoneAwareLoadBalancer.getLoadBalancerStats().getZoneStats();
    assertEquals(1, zoneStats.size());
    ZoneStats getResult = zoneStats.get("setting server list for zones: {}");
    assertEquals("default:setting server list for zones: {}", getResult.monitorId);
    assertEquals("setting server list for zones: {}", getResult.getZone());
    assertEquals(0, getResult.getActiveRequestsCount());
    assertEquals(0, getResult.getCircuitBreakerTrippedCount());
    assertEquals(0, getResult.getInstanceCount());
    assertEquals(0.0d, getResult.getActiveRequestsPerServer(), 0.0);
    assertEquals(0.0d, getResult.getCircuitBreakerTrippedPercentage(), 0.0);
    assertEquals(0L, getResult.getMeasuredZoneHits());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link DynamicServerListLoadBalancer#setFilter(ServerListFilter)}
   *   <li>{@link DynamicServerListLoadBalancer#setServerListImpl(ServerList)}
   *   <li>{@link DynamicServerListLoadBalancer#setServerListUpdater(ServerListUpdater)}
   *   <li>{@link DynamicServerListLoadBalancer#forceQuickPing()}
   *   <li>{@link DynamicServerListLoadBalancer#getFilter()}
   *   <li>{@link DynamicServerListLoadBalancer#getServerListImpl()}
   *   <li>{@link DynamicServerListLoadBalancer#getServerListUpdater()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicServerListLoadBalancer.forceQuickPing()",
    "ServerListFilter DynamicServerListLoadBalancer.getFilter()",
    "ServerList DynamicServerListLoadBalancer.getServerListImpl()",
    "ServerListUpdater DynamicServerListLoadBalancer.getServerListUpdater()",
    "void DynamicServerListLoadBalancer.setFilter(ServerListFilter)",
    "void DynamicServerListLoadBalancer.setServerListImpl(ServerList)",
    "void DynamicServerListLoadBalancer.setServerListUpdater(ServerListUpdater)",
    "String DynamicServerListLoadBalancer.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    ServerListFilter<Server> filter = mock(ServerListFilter.class);

    // Act
    dynamicServerListLoadBalancer.setFilter(filter);
    ConfigurationBasedServerList niwsServerList = new ConfigurationBasedServerList();
    dynamicServerListLoadBalancer.setServerListImpl(niwsServerList);
    PollingServerListUpdater serverListUpdater = new PollingServerListUpdater();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    dynamicServerListLoadBalancer.forceQuickPing();
    ServerListFilter<Server> actualFilter = dynamicServerListLoadBalancer.getFilter();
    ServerList<Server> actualServerListImpl = dynamicServerListLoadBalancer.getServerListImpl();

    // Assert
    assertTrue(actualServerListImpl instanceof ConfigurationBasedServerList);
    assertSame(niwsServerList, actualServerListImpl);
    assertSame(serverListUpdater, dynamicServerListLoadBalancer.getServerListUpdater());
    assertSame(filter, actualFilter);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}.
   *
   * <p>Method under test: {@link
   * DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature()"})
  public void testEnableAndInitLearnNewServersFeature() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature();

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}.
   *
   * <p>Method under test: {@link
   * DynamicServerListLoadBalancer#enableAndInitLearnNewServersFeature()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature()"})
  public void testEnableAndInitLearnNewServersFeature2() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature();

    // Assert
    verify(serverListUpdater).start(isA(UpdateAction.class));
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers_givenDynamicServerListLoadBalancer() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert that nothing has changed
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} addServer
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers_givenDynamicServerListLoadBalancerAddServerNull() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    dynamicServerListLoadBalancer.addServer(null);

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert that nothing has changed
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} Ping is
   *       {@link IPing}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers_givenDynamicServerListLoadBalancerPingIsIPing() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   *
   * <ul>
   *   <li>Then calls {@link ServerListChangeListener#serverListChanged(List, List)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers_thenCallsServerListChanged() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   *
   * <ul>
   *   <li>Then calls {@link ServerListChangeListener#serverListChanged(List, List)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers_thenCallsServerListChanged2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateListOfServers()}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} AllServers
   *       Empty.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateListOfServers()"})
  public void testUpdateListOfServers_thenDynamicServerListLoadBalancerAllServersEmpty() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException())
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   *
   * <ul>
   *   <li>Given {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_givenDynamicServerListLoadBalancer() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link
   *       DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link
   *       BaseLoadBalancer#upServerList}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_thenArrayListIsDynamicServerListLoadBalancerUpServerList() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link
   *       DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link
   *       BaseLoadBalancer#upServerList}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_thenArrayListIsDynamicServerListLoadBalancerUpServerList2() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link
   *       DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link
   *       BaseLoadBalancer#upServerList}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_thenArrayListIsDynamicServerListLoadBalancerUpServerList3() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} is {@link
   *       DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()} {@link
   *       BaseLoadBalancer#upServerList}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_thenArrayListIsDynamicServerListLoadBalancerUpServerList4() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
    assertEquals(dynamicServerListLoadBalancer.upServerList, ls);
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#updateAllServerList(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link ServerListChangeListener#serverListChanged(List, List)}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DynamicServerListLoadBalancer.updateAllServerList(List)"})
  public void testUpdateAllServerList_whenArrayList_thenCallsServerListChanged() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing()
        .when(listener)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.addServer(new Server("42"));

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(new ArrayList<>());

    // Assert
    verify(listener, atLeast(1))
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getNumberMissedCycles()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#getNumberMissedCycles()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DynamicServerListLoadBalancer.getNumberMissedCycles()"})
  public void testGetNumberMissedCycles_thenReturnZero() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(new PollingServerListUpdater());

    // Act and Assert
    assertEquals(0, dynamicServerListLoadBalancer.getNumberMissedCycles());
  }

  /**
   * Test {@link DynamicServerListLoadBalancer#getCoreThreads()}.
   *
   * <ul>
   *   <li>Then {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}
   *       ReachableServers Empty.
   * </ul>
   *
   * <p>Method under test: {@link DynamicServerListLoadBalancer#getCoreThreads()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DynamicServerListLoadBalancer.getCoreThreads()"})
  public void testGetCoreThreads_thenDynamicServerListLoadBalancerReachableServersEmpty() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer =
        new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(new PollingServerListUpdater());

    // Act and Assert
    assertEquals(2, dynamicServerListLoadBalancer.getCoreThreads());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }
}
