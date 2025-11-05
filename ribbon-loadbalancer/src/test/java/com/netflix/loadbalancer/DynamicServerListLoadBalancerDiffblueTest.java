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
import com.netflix.client.IClientConfigAware;
import com.netflix.client.PrimeConnections;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class DynamicServerListLoadBalancerDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#initWithNiwsConfig(IClientConfig, IClientConfigAware.Factory)}
   */
  @Test
  public void testInitWithNiwsConfig() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(false);
    dynamicServerListLoadBalancer.setPrimeConnections(null);
    dynamicServerListLoadBalancer.setServerListImpl(null);
    dynamicServerListLoadBalancer.setFilter(mock(ServerListFilter.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    IClientConfigAware.Factory factory = mock(IClientConfigAware.Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new RuntimeException("LoadBalancer [{}]:  pingIntervalSeconds set to {}"));

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.initWithNiwsConfig(clientConfig, factory);
    verify(factory).create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#initWithNiwsConfig(IClientConfig, IClientConfigAware.Factory)}
   */
  @Test
  public void testInitWithNiwsConfig2() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setLoadBalancerStats(new LoadBalancerStats());
    dynamicServerListLoadBalancer.setEnablePrimingConnections(false);
    dynamicServerListLoadBalancer.setPrimeConnections(new PrimeConnections("Name", 3, 1L, "Prime Connections URI"));
    dynamicServerListLoadBalancer.setServerListImpl(null);
    dynamicServerListLoadBalancer.setFilter(mock(ServerListFilter.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    IClientConfigAware.Factory factory = mock(IClientConfigAware.Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new RuntimeException("LoadBalancer [{}]:  pingIntervalSeconds set to {}"));

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.initWithNiwsConfig(clientConfig, factory);
    verify(factory).create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#initWithNiwsConfig(IClientConfig, IClientConfigAware.Factory)}
   */
  @Test
  public void testInitWithNiwsConfig3() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(false);
    dynamicServerListLoadBalancer.setPrimeConnections(new PrimeConnections("Name", 9, 1L, "Prime Connections URI"));
    dynamicServerListLoadBalancer.setServerListImpl(null);
    dynamicServerListLoadBalancer.setFilter(mock(ServerListFilter.class));
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    IClientConfigAware.Factory factory = mock(IClientConfigAware.Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new RuntimeException("LoadBalancer [{}]:  pingIntervalSeconds set to {}"));

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.initWithNiwsConfig(clientConfig, factory);
    verify(factory).create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.restOfInit(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit2() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.restOfInit(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit3() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("Dr Jane Doe");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit4() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServers(new ArrayList<>());
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("Dr Jane Doe");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit5() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("Dr Jane Doe");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit6() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setEnablePrimingConnections(true);
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("Dr Jane Doe");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit7() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("DynamicServerListLoadBalancer:");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit8() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("DynamicServerListLoadBalancer:");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit9() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("DynamicServerListLoadBalancer:");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit10() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("DynamicServerListLoadBalancer:");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit11() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("Using serverListUpdater {}")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("DynamicServerListLoadBalancer:");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit12() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("Using serverListUpdater {}")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("DynamicServerListLoadBalancer:");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#restOfInit(IClientConfig)}
   */
  @Test
  public void testRestOfInit13() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).start(Mockito.<ServerListUpdater.UpdateAction>any());
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener2 = mock(ServerListChangeListener.class);
    doThrow(new RuntimeException("Using serverListUpdater {}")).when(listener2)
        .serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    ServerListChangeListener listener3 = mock(ServerListChangeListener.class);
    doNothing().when(listener3).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setFilter(mock(ServerListFilter.class));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener3);
    dynamicServerListLoadBalancer.addServerListChangeListener(listener2);
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.addServerListChangeListener(listener);
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getClientName()).thenReturn("DynamicServerListLoadBalancer:");

    // Act
    dynamicServerListLoadBalancer.restOfInit(clientConfig);

    // Assert
    verify(clientConfig).getClientName();
    verify(serverListUpdater).start(isA(ServerListUpdater.UpdateAction.class));
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
    verify(listener3, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    verify(listener2, atLeast(1)).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  public void testSetServersList() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    assertTrue(lsrv.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  public void testSetServersList2() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    assertTrue(lsrv.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#setServersList(List)}
   */
  @Test
  public void testSetServersList3() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Object> lsrv = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.setServersList(lsrv);

    // Assert
    assertTrue(lsrv.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}
   */
  @Test
  public void testStopServerListRefreshing() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.stopServerListRefreshing();

    // Assert
    verify(serverListUpdater).stop();
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}
   */
  @Test
  public void testStopServerListRefreshing2() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doThrow(new RuntimeException("foo")).when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.stopServerListRefreshing();
    verify(serverListUpdater).stop();
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}
   */
  @Test
  public void testStopServerListRefreshing3() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListImpl(new ConfigurationBasedServerList());
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.stopServerListRefreshing();

    // Assert
    verify(serverListUpdater).stop();
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}
   */
  @Test
  public void testStopServerListRefreshing4() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setFilter(mock(ServerListFilter.class));
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.stopServerListRefreshing();

    // Assert
    verify(serverListUpdater).stop();
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}
   */
  @Test
  public void testStopServerListRefreshing5() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setFilter(mock(ServerListFilter.class));
    dynamicServerListLoadBalancer.addServers(new Object[]{});
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.stopServerListRefreshing();

    // Assert
    verify(serverListUpdater).stop();
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#stopServerListRefreshing()}
   */
  @Test
  public void testStopServerListRefreshing6() {
    // Arrange
    PollingServerListUpdater serverListUpdater = mock(PollingServerListUpdater.class);
    doNothing().when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListImpl(new ConfigurationBasedServerList());
    dynamicServerListLoadBalancer.addServer(new Server("http://"));
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.stopServerListRefreshing();

    // Assert
    verify(serverListUpdater).stop();
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  public void testUpdateListOfServers() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  public void testUpdateListOfServers2() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    dynamicServerListLoadBalancer.updateListOfServers();

    // Assert
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#updateListOfServers()}
   */
  @Test
  public void testUpdateListOfServers3() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
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
   * Method under test:
   * {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  public void testUpdateAllServerList() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    assertTrue(ls.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  public void testUpdateAllServerList2() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    assertTrue(ls.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#updateAllServerList(List)}
   */
  @Test
  public void testUpdateAllServerList3() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServer(new Server("42"));
    ArrayList<Server> ls = new ArrayList<>();

    // Act
    dynamicServerListLoadBalancer.updateAllServerList(ls);

    // Assert
    assertTrue(ls.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(dynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(dynamicServerListLoadBalancer.upServerList.isEmpty());
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#shutdown()}
   */
  @Test
  public void testShutdown() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    // Act
    dynamicServerListLoadBalancer.shutdown();

    // Assert
    assertNull(dynamicServerListLoadBalancer.getServerListUpdater());
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#shutdown()}
   */
  @Test
  public void testShutdown2() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    PollingServerListUpdater serverListUpdater = new PollingServerListUpdater();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.shutdown();

    // Assert
    ServerListUpdater serverListUpdater2 = dynamicServerListLoadBalancer.getServerListUpdater();
    assertTrue(serverListUpdater2 instanceof PollingServerListUpdater);
    assertSame(serverListUpdater, serverListUpdater2);
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#shutdown()}
   */
  @Test
  public void testShutdown3() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    dynamicServerListLoadBalancer.shutdown();

    // Assert
    assertNull(dynamicServerListLoadBalancer.getServerListUpdater());
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#shutdown()}
   */
  @Test
  public void testShutdown4() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});
    PollingServerListUpdater serverListUpdater = new PollingServerListUpdater();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.shutdown();

    // Assert
    ServerListUpdater serverListUpdater2 = dynamicServerListLoadBalancer.getServerListUpdater();
    assertTrue(serverListUpdater2 instanceof PollingServerListUpdater);
    assertSame(serverListUpdater, serverListUpdater2);
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#shutdown()}
   */
  @Test
  public void testShutdown5() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPing(mock(IPing.class));
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});
    PollingServerListUpdater serverListUpdater = new PollingServerListUpdater();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.shutdown();

    // Assert
    ServerListUpdater serverListUpdater2 = dynamicServerListLoadBalancer.getServerListUpdater();
    assertTrue(serverListUpdater2 instanceof PollingServerListUpdater);
    assertSame(serverListUpdater, serverListUpdater2);
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#shutdown()}
   */
  @Test
  public void testShutdown6() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setPrimeConnections(new PrimeConnections(" ", 3, 8L, " "));
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});
    PollingServerListUpdater serverListUpdater = new PollingServerListUpdater();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.shutdown();

    // Assert
    ServerListUpdater serverListUpdater2 = dynamicServerListLoadBalancer.getServerListUpdater();
    assertTrue(serverListUpdater2 instanceof PollingServerListUpdater);
    assertSame(serverListUpdater, serverListUpdater2);
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#shutdown()}
   */
  @Test
  public void testShutdown7() {
    // Arrange
    ServerListUpdater serverListUpdater = mock(ServerListUpdater.class);
    doNothing().when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act
    dynamicServerListLoadBalancer.shutdown();

    // Assert
    verify(serverListUpdater).stop();
    assertNull(dynamicServerListLoadBalancer.getLastUpdate());
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#shutdown()}
   */
  @Test
  public void testShutdown8() {
    // Arrange
    ServerListUpdater serverListUpdater = mock(ServerListUpdater.class);
    doThrow(new RuntimeException(" ")).when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.shutdown();
    verify(serverListUpdater).stop();
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#shutdown()}
   */
  @Test
  public void testShutdown9() {
    // Arrange
    ServerListUpdater serverListUpdater = mock(ServerListUpdater.class);
    doThrow(new RuntimeException(" ")).when(serverListUpdater).stop();

    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListImpl(new ConfigurationBasedServerList());
    dynamicServerListLoadBalancer.addServers(new Object[]{"New Servers"});
    dynamicServerListLoadBalancer.setServerListUpdater(serverListUpdater);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    dynamicServerListLoadBalancer.shutdown();
    verify(serverListUpdater).stop();
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#getNumberMissedCycles()}
   */
  @Test
  public void testGetNumberMissedCycles() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(new PollingServerListUpdater());

    // Act and Assert
    assertEquals(0, dynamicServerListLoadBalancer.getNumberMissedCycles());
  }

  /**
   * Method under test: {@link DynamicServerListLoadBalancer#getCoreThreads()}
   */
  @Test
  public void testGetCoreThreads() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
    dynamicServerListLoadBalancer.setServerListUpdater(new PollingServerListUpdater());

    // Act and Assert
    assertEquals(2, dynamicServerListLoadBalancer.getCoreThreads());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DynamicServerListLoadBalancer#setFilter(ServerListFilter)}
   *   <li>{@link DynamicServerListLoadBalancer#setServerListImpl(ServerList)}
   *   <li>
   * {@link DynamicServerListLoadBalancer#setServerListUpdater(ServerListUpdater)}
   *   <li>{@link DynamicServerListLoadBalancer#forceQuickPing()}
   *   <li>{@link DynamicServerListLoadBalancer#getFilter()}
   *   <li>{@link DynamicServerListLoadBalancer#getServerListImpl()}
   *   <li>{@link DynamicServerListLoadBalancer#getServerListUpdater()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    DynamicServerListLoadBalancer<Server> dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();
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

    // Assert that nothing has changed
    assertTrue(actualServerListImpl instanceof ConfigurationBasedServerList);
    assertSame(niwsServerList, actualServerListImpl);
    assertSame(serverListUpdater, dynamicServerListLoadBalancer.getServerListUpdater());
    assertSame(filter, actualFilter);
  }

  /**
   * Method under test:
   * {@link DynamicServerListLoadBalancer#DynamicServerListLoadBalancer()}
   */
  @Test
  public void testNewDynamicServerListLoadBalancer() {
    // Arrange and Act
    DynamicServerListLoadBalancer<Server> actualDynamicServerListLoadBalancer = new DynamicServerListLoadBalancer<>();

    // Assert
    IRule rule = actualDynamicServerListLoadBalancer.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertTrue(actualDynamicServerListLoadBalancer.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualDynamicServerListLoadBalancer.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualDynamicServerListLoadBalancer.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("default", actualDynamicServerListLoadBalancer.getName());
    LoadBalancerStats loadBalancerStats = actualDynamicServerListLoadBalancer.getLoadBalancerStats();
    assertEquals("default", loadBalancerStats.getName());
    assertNull(actualDynamicServerListLoadBalancer.getPrimeConnections());
    assertNull(actualDynamicServerListLoadBalancer.getClientConfig());
    assertNull(actualDynamicServerListLoadBalancer.getPing());
    assertNull(actualDynamicServerListLoadBalancer.getServerListImpl());
    assertNull(actualDynamicServerListLoadBalancer.getFilter());
    assertNull(actualDynamicServerListLoadBalancer.getServerListUpdater());
    assertNull(actualDynamicServerListLoadBalancer.lbTimer);
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10, actualDynamicServerListLoadBalancer.getPingInterval());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(5, actualDynamicServerListLoadBalancer.getMaxTotalPingTime());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertFalse(actualDynamicServerListLoadBalancer.isEnablePrimingConnections());
    assertFalse(actualDynamicServerListLoadBalancer.isPingInProgress());
    assertFalse(actualDynamicServerListLoadBalancer.pingInProgress.get());
    assertFalse(actualDynamicServerListLoadBalancer.serverListUpdateInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertFalse(actualDynamicServerListLoadBalancer.isSecure);
    assertFalse(actualDynamicServerListLoadBalancer.useTunnel);
    assertTrue(actualDynamicServerListLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualDynamicServerListLoadBalancer.getReachableServers().isEmpty());
    assertTrue(actualDynamicServerListLoadBalancer.allServerList.isEmpty());
    assertTrue(actualDynamicServerListLoadBalancer.upServerList.isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertSame(actualDynamicServerListLoadBalancer, rule.getLoadBalancer());
  }
}
