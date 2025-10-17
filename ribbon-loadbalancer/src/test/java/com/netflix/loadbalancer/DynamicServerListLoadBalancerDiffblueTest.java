package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DynamicServerListLoadBalancerDiffblueTest {
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
    "java.lang.String DynamicServerListLoadBalancer.toString()"
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
}
