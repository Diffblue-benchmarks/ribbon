package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.junit.Test;

public class AvailabilityFilteringRuleDiffblueTest {
  /**
   * Method under test:
   * {@link AvailabilityFilteringRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    availabilityFilteringRule.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(3L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link AvailabilityFilteringRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig2() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "[{}] get global property '{}' with default '{}'");

    // Act
    availabilityFilteringRule.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(3L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link AvailabilityFilteringRule#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig3() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    availabilityFilteringRule.initWithNiwsConfig(clientConfig);

    // Assert
    assertEquals(3L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link AvailabilityFilteringRule#getAvailableServersCount()}
   */
  @Test
  public void testGetAvailableServersCount() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertEquals(0, availabilityFilteringRule.getAvailableServersCount());
  }

  /**
   * Method under test:
   * {@link AvailabilityFilteringRule#getAvailableServersCount()}
   */
  @Test
  public void testGetAvailableServersCount2() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act and Assert
    assertEquals(0, availabilityFilteringRule.getAvailableServersCount());
  }

  /**
   * Method under test:
   * {@link AvailabilityFilteringRule#getAvailableServersCount()}
   */
  @Test
  public void testGetAvailableServersCount3() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new NoOpLoadBalancer());

    // Act and Assert
    assertEquals(0, availabilityFilteringRule.getAvailableServersCount());
  }

  /**
   * Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  public void testChoose() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(availabilityFilteringRule.choose("Key"));
  }

  /**
   * Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  public void testChoose2() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act and Assert
    assertNull(availabilityFilteringRule.choose("Key"));
  }

  /**
   * Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  public void testChoose3() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.addServer(null);

    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(availabilityFilteringRule.choose(null));
  }

  /**
   * Method under test: {@link AvailabilityFilteringRule#getPredicate()}
   */
  @Test
  public void testGetPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualPredicate = (new AvailabilityFilteringRule()).getPredicate();

    // Assert
    assertTrue(actualPredicate instanceof CompositePredicate);
    assertNull(((CompositePredicate) actualPredicate).rule);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AvailabilityFilteringRule}
   */
  @Test
  public void testNewAvailabilityFilteringRule() {
    // Arrange and Act
    AvailabilityFilteringRule actualAvailabilityFilteringRule = new AvailabilityFilteringRule();

    // Assert
    AbstractServerPredicate predicate = actualAvailabilityFilteringRule.getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    assertNull(actualAvailabilityFilteringRule.getLoadBalancer());
    assertNull(actualAvailabilityFilteringRule.roundRobinRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
  }
}
