package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.ClientException;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractServerListDiffblueTest {
  /**
   * Test {@link AbstractServerList#getFilterImpl(IClientConfig)}.
   *
   * <ul>
   *   <li>When EmptyConfig.
   *   <li>Then return {@link ZoneAffinityServerListFilter}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractServerList#getFilterImpl(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractServerListFilter AbstractServerList.getFilterImpl(IClientConfig)"})
  public void testGetFilterImpl_whenEmptyConfig_thenReturnZoneAffinityServerListFilter()
      throws ClientException {
    // Arrange
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    AbstractServerListFilter<Server> actualFilterImpl =
        new ConfigurationBasedServerList().getFilterImpl(niwsClientConfig);

    // Assert
    assertTrue(actualFilterImpl instanceof ZoneAffinityServerListFilter);
    assertNull(actualFilterImpl.getLoadBalancerStats());
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }
}
