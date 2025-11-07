package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.ConfigurationBasedServerList;
import com.netflix.loadbalancer.DummyPing;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.PollingServerListUpdater;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAffinityServerListFilter;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClientFactoryDiffblueTest {
  /**
   * Test {@link ClientFactory#getNamedLoadBalancer(String)} with {@code name}.
   * <ul>
   *   <li>Then ClientConfig return {@link DefaultClientConfigImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientFactory#getNamedLoadBalancer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ILoadBalancer ClientFactory.getNamedLoadBalancer(String)"})
  public void testGetNamedLoadBalancerWithName_thenClientConfigReturnDefaultClientConfigImpl() {
    // Arrange and Act
    ILoadBalancer actualNamedLoadBalancer = ClientFactory.getNamedLoadBalancer("Name");

    // Assert
    assertTrue(
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getClientConfig() instanceof DefaultClientConfigImpl);
    assertTrue(
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getRule() instanceof AvailabilityFilteringRule);
    assertTrue(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer)
        .getServerListImpl() instanceof ConfigurationBasedServerList);
    assertTrue(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPing() instanceof DummyPing);
    assertTrue(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer)
        .getServerListUpdater() instanceof PollingServerListUpdater);
    assertTrue(
        ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getFilter() instanceof ZoneAffinityServerListFilter);
    assertTrue(actualNamedLoadBalancer instanceof ZoneAwareLoadBalancer);
    assertEquals("Name", ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getName());
    assertNull(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPrimeConnections());
    assertEquals(2, ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getMaxTotalPingTime());
    assertEquals(2, ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getCoreThreads());
    assertEquals(30, ((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).getPingInterval());
    assertFalse(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).isEnablePrimingConnections());
    assertFalse(((ZoneAwareLoadBalancer<Server>) actualNamedLoadBalancer).isPingInProgress());
    assertTrue(actualNamedLoadBalancer.getAllServers().isEmpty());
    assertTrue(actualNamedLoadBalancer.getReachableServers().isEmpty());
  }
}
