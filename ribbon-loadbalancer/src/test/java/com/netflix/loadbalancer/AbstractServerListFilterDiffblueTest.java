package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractServerListFilterDiffblueTest {
  /**
   * Test {@link AbstractServerListFilter#setLoadBalancerStats(LoadBalancerStats)}.
   * <p>
   * Method under test: {@link AbstractServerListFilter#setLoadBalancerStats(LoadBalancerStats)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractServerListFilter.setLoadBalancerStats(LoadBalancerStats)"})
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
   * Test {@link AbstractServerListFilter#getLoadBalancerStats()}.
   * <p>
   * Method under test: {@link AbstractServerListFilter#getLoadBalancerStats()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LoadBalancerStats AbstractServerListFilter.getLoadBalancerStats()"})
  public void testGetLoadBalancerStats() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    // Act and Assert
    assertNull(serverListSubsetFilter.getLoadBalancerStats());
  }
}
