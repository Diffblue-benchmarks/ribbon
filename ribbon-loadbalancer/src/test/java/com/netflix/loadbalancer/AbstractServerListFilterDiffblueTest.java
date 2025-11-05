package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import org.junit.Test;

public class AbstractServerListFilterDiffblueTest {
  /**
   * Method under test:
   * {@link AbstractServerListFilter#setLoadBalancerStats(LoadBalancerStats)}
   */
  @Test
  public void testSetLoadBalancerStats() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();
    LoadBalancerStats stats = new LoadBalancerStats();

    // Act
    serverListSubsetFilter.setLoadBalancerStats(stats);

    // Assert
    assertSame(stats, serverListSubsetFilter.getLoadBalancerStats());
  }

  /**
   * Method under test:
   * {@link AbstractServerListFilter#setLoadBalancerStats(LoadBalancerStats)}
   */
  @Test
  public void testSetLoadBalancerStats2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>(clientConfig);
    LoadBalancerStats stats = new LoadBalancerStats();

    // Act
    serverListSubsetFilter.setLoadBalancerStats(stats);

    // Assert
    assertSame(stats, serverListSubsetFilter.getLoadBalancerStats());
  }

  /**
   * Method under test: {@link AbstractServerListFilter#getLoadBalancerStats()}
   */
  @Test
  public void testGetLoadBalancerStats() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    // Act and Assert
    assertNull(serverListSubsetFilter.getLoadBalancerStats());
  }

  /**
   * Method under test: {@link AbstractServerListFilter#getLoadBalancerStats()}
   */
  @Test
  public void testGetLoadBalancerStats2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>(clientConfig);

    // Act and Assert
    assertNull(serverListSubsetFilter.getLoadBalancerStats());
  }
}
