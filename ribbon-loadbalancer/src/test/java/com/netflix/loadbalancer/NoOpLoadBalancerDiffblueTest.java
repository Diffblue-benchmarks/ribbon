package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.AbstractLoadBalancer.ServerGroup;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NoOpLoadBalancerDiffblueTest {
  /**
   * Test {@link NoOpLoadBalancer#chooseServer(Object)} with {@code Object}.
   *
   * <p>Method under test: {@link NoOpLoadBalancer#chooseServer(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server NoOpLoadBalancer.chooseServer(Object)"})
  public void testChooseServerWithObject() {
    // Arrange, Act and Assert
    assertNull(new NoOpLoadBalancer().chooseServer("Key"));
  }

  /**
   * Test {@link NoOpLoadBalancer#getServerList(boolean)} with {@code availableOnly}.
   *
   * <p>Method under test: {@link NoOpLoadBalancer#getServerList(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List NoOpLoadBalancer.getServerList(boolean)"})
  public void testGetServerListWithAvailableOnly() {
    // Arrange, Act and Assert
    assertNull(new NoOpLoadBalancer().getServerList(true));
  }

  /**
   * Test {@link NoOpLoadBalancer#getServerList(ServerGroup)} with {@code serverGroup}.
   *
   * <p>Method under test: {@link NoOpLoadBalancer#getServerList(ServerGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List NoOpLoadBalancer.getServerList(ServerGroup)"})
  public void testGetServerListWithServerGroup() {
    // Arrange, Act and Assert
    assertTrue(new NoOpLoadBalancer().getServerList(ServerGroup.ALL).isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link NoOpLoadBalancer}
   *   <li>{@link NoOpLoadBalancer#getAllServers()}
   *   <li>{@link NoOpLoadBalancer#getLoadBalancerStats()}
   *   <li>{@link NoOpLoadBalancer#getReachableServers()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NoOpLoadBalancer.<init>()",
    "List NoOpLoadBalancer.getAllServers()",
    "LoadBalancerStats NoOpLoadBalancer.getLoadBalancerStats()",
    "List NoOpLoadBalancer.getReachableServers()"
  })
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
