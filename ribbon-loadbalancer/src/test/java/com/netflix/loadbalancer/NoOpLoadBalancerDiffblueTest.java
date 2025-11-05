package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;

public class NoOpLoadBalancerDiffblueTest {
  /**
   * Method under test: {@link NoOpLoadBalancer#chooseServer(Object)}
   */
  @Test
  public void testChooseServer() {
    // Arrange, Act and Assert
    assertNull((new NoOpLoadBalancer()).chooseServer("Key"));
  }

  /**
   * Method under test:
   * {@link NoOpLoadBalancer#getServerList(AbstractLoadBalancer.ServerGroup)}
   */
  @Test
  public void testGetServerList() {
    // Arrange, Act and Assert
    assertTrue((new NoOpLoadBalancer()).getServerList(AbstractLoadBalancer.ServerGroup.ALL).isEmpty());
    assertNull((new NoOpLoadBalancer()).getServerList(true));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link NoOpLoadBalancer}
   *   <li>{@link NoOpLoadBalancer#getAllServers()}
   *   <li>{@link NoOpLoadBalancer#getLoadBalancerStats()}
   *   <li>{@link NoOpLoadBalancer#getReachableServers()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    NoOpLoadBalancer actualNoOpLoadBalancer = new NoOpLoadBalancer();
    List<Server> actualAllServers = actualNoOpLoadBalancer.getAllServers();
    LoadBalancerStats actualLoadBalancerStats = actualNoOpLoadBalancer.getLoadBalancerStats();

    // Assert
    assertNull(actualLoadBalancerStats);
    assertNull(actualAllServers);
    assertNull(actualNoOpLoadBalancer.getReachableServers());
  }
}
