package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;

public class WeightedResponseTimeRuleDiffblueTest {
  /**
   * Method under test:
   * {@link WeightedResponseTimeRule.DynamicServerWeightTask#run()}
   */
  @Test
  public void testDynamicServerWeightTaskRun() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = mock(WeightedResponseTimeRule.class);
    when(weightedResponseTimeRule.getLoadBalancer()).thenReturn(new BaseLoadBalancer());

    // Act
    (weightedResponseTimeRule.new DynamicServerWeightTask()).run();

    // Assert
    verify(weightedResponseTimeRule).getLoadBalancer();
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", weightedResponseTimeRule.name);
    assertSame(lb, weightedResponseTimeRule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer2() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();

    // Act
    weightedResponseTimeRule.setLoadBalancer(null);

    // Assert
    assertEquals("unknown", weightedResponseTimeRule.name);
    assertNull(weightedResponseTimeRule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer3() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", weightedResponseTimeRule.name);
    assertSame(lb, weightedResponseTimeRule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer4() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    NoOpLoadBalancer lb = new NoOpLoadBalancer();

    // Act
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Assert
    assertEquals("unknown", weightedResponseTimeRule.name);
    assertSame(lb, weightedResponseTimeRule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer5() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("42"));

    // Act
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", weightedResponseTimeRule.name);
    assertSame(lb, weightedResponseTimeRule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer6() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.initialize(new BaseLoadBalancer());
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", weightedResponseTimeRule.name);
    assertSame(lb, weightedResponseTimeRule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer7() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServerListChangeListener(mock(ServerListChangeListener.class));

    // Act
    weightedResponseTimeRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", weightedResponseTimeRule.name);
    assertSame(lb, weightedResponseTimeRule.getLoadBalancer());
  }

  /**
   * Method under test: {@link WeightedResponseTimeRule#shutdown()}
   */
  @Test
  public void testShutdown() {
    // Arrange
    ServerListChangeListener listener = mock(ServerListChangeListener.class);
    doNothing().when(listener).serverListChanged(Mockito.<List<Server>>any(), Mockito.<List<Server>>any());

    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();
    lb.addServerListChangeListener(listener);
    lb.addServer(new Server("42"));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);
    IPing ping = mock(IPing.class);
    weightedResponseTimeRule.initialize(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act
    weightedResponseTimeRule.shutdown();

    // Assert
    verify(listener).serverListChanged(isA(List.class), isA(List.class));
  }

  /**
   * Method under test: {@link WeightedResponseTimeRule#getAccumulatedWeights()}
   */
  @Test
  public void testGetAccumulatedWeights() {
    // Arrange, Act and Assert
    assertTrue((new WeightedResponseTimeRule()).getAccumulatedWeights().isEmpty());
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(new BaseLoadBalancer(), "Key"));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose2() {
    // Arrange, Act and Assert
    assertNull((new WeightedResponseTimeRule()).choose(null, "Key"));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose3() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()), "Key"));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose4() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("42"));

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(lb, "Key"));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose5() {
    // Arrange
    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new BaseLoadBalancer());

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("42"));

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(lb, "Key"));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose6() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServer(new Server("42"));

    // Act and Assert
    assertSame(newServer, weightedResponseTimeRule.choose(lb2, "Key"));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose7() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.addServer(new Server("42"));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(lb);

    BaseLoadBalancer lb2 = new BaseLoadBalancer();
    lb2.addServer(new Server("42"));

    // Act and Assert
    assertNull(weightedResponseTimeRule.choose(lb2, "Key"));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new ResponseTimeWeightedRule());
    lb.addServer(new Server("com.netflix.loadbalancer.Server"));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new BaseLoadBalancer());
    weightedResponseTimeRule.initialize(lb);

    // Act
    weightedResponseTimeRule.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig2() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, null);
    lb.addServer(new Server("com.netflix.loadbalancer.Server"));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new BaseLoadBalancer());
    weightedResponseTimeRule.initialize(lb);

    // Act
    weightedResponseTimeRule.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig3() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, null);
    lb.addServer(new Server("com.netflix.loadbalancer.Server"));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new BaseLoadBalancer());
    weightedResponseTimeRule.initialize(lb);

    // Act
    weightedResponseTimeRule.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig4() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServer(new Server("Id"));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new BaseLoadBalancer());
    weightedResponseTimeRule.initialize(lb);

    // Act
    weightedResponseTimeRule.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping).isAlive(isA(Server.class));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig5() {
    // Arrange
    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenReturn(true);
    IPing ping2 = mock(IPing.class);
    when(ping2.isAlive(Mockito.<Server>any())).thenReturn(true);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServers(new ArrayList<>());
    lb.setPing(ping2);
    lb.addServer(new Server("com.netflix.loadbalancer.Server"));

    WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
    weightedResponseTimeRule.setLoadBalancer(new BaseLoadBalancer());
    weightedResponseTimeRule.initialize(lb);

    // Act
    weightedResponseTimeRule.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(ping2).isAlive(isA(Server.class));
  }

  /**
   * Method under test:
   * {@link WeightedResponseTimeRule#WeightedResponseTimeRule()}
   */
  @Test
  public void testNewWeightedResponseTimeRule() {
    // Arrange and Act
    WeightedResponseTimeRule actualWeightedResponseTimeRule = new WeightedResponseTimeRule();

    // Assert
    assertEquals("unknown", actualWeightedResponseTimeRule.name);
    assertNull(actualWeightedResponseTimeRule.getLoadBalancer());
    assertNull(actualWeightedResponseTimeRule.serverWeightTimer);
    assertFalse(actualWeightedResponseTimeRule.serverWeightAssignmentInProgress.get());
    assertTrue(actualWeightedResponseTimeRule.getAccumulatedWeights().isEmpty());
  }
}
