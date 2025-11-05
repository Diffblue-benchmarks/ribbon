package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.ClientException;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.junit.Test;

public class AbstractServerListDiffblueTest {
  /**
   * Method under test: {@link AbstractServerList#getFilterImpl(IClientConfig)}
   */
  @Test
  public void testGetFilterImpl() throws ClientException {
    // Arrange
    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    AbstractServerListFilter<Server> actualFilterImpl = configurationBasedServerList.getFilterImpl(niwsClientConfig);

    // Assert
    assertTrue(actualFilterImpl instanceof ZoneAffinityServerListFilter);
    assertNull(actualFilterImpl.getLoadBalancerStats());
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }

  /**
   * Method under test: {@link AbstractServerList#getFilterImpl(IClientConfig)}
   */
  @Test
  public void testGetFilterImpl2() throws ClientException {
    // Arrange
    ConfigurationBasedServerList configurationBasedServerList = new ConfigurationBasedServerList();
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getEmptyConfig();
    niwsClientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    AbstractServerListFilter<Server> actualFilterImpl = configurationBasedServerList.getFilterImpl(niwsClientConfig);

    // Assert
    assertTrue(actualFilterImpl instanceof ZoneAffinityServerListFilter);
    assertNull(actualFilterImpl.getLoadBalancerStats());
    assertEquals(4L, niwsClientConfig.getRefreshCount());
  }
}
