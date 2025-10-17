package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ServerListSubsetFilterDiffblueTest {
  /**
   * Test {@link ServerListSubsetFilter#ServerListSubsetFilter()}.
   *
   * <p>Method under test: {@link ServerListSubsetFilter#ServerListSubsetFilter()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServerListSubsetFilter.<init>()"})
  public void testNewServerListSubsetFilter() {
    // Arrange and Act
    ServerListSubsetFilter<Server> actualServerListSubsetFilter = new ServerListSubsetFilter<>();

    // Assert
    assertNull(actualServerListSubsetFilter.getLoadBalancerStats());
  }
}
