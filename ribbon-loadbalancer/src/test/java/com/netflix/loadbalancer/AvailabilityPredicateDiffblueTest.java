package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.junit.Test;

public class AvailabilityPredicateDiffblueTest {
  /**
   * Method under test: {@link AvailabilityPredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply() {
    // Arrange
    AvailabilityPredicate availabilityPredicate = new AvailabilityPredicate(new AvailabilityFilteringRule());

    // Act and Assert
    assertTrue(availabilityPredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link AvailabilityPredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply2() {
    // Arrange
    AvailabilityPredicate availabilityPredicate = new AvailabilityPredicate(null);

    // Act and Assert
    assertTrue(availabilityPredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link AvailabilityPredicate#AvailabilityPredicate(IRule)}
   */
  @Test
  public void testNewAvailabilityPredicate() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(rule);

    // Assert
    IRule iRule = actualAvailabilityPredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualAvailabilityPredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Method under test:
   * {@link AvailabilityPredicate#AvailabilityPredicate(IRule, IClientConfig)}
   */
  @Test
  public void testNewAvailabilityPredicate2() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(rule, clientConfig);

    // Assert
    IRule iRule = actualAvailabilityPredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualAvailabilityPredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Method under test:
   * {@link AvailabilityPredicate#AvailabilityPredicate(IRule, IClientConfig)}
   */
  @Test
  public void testNewAvailabilityPredicate3() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(rule, null);

    // Assert
    IRule iRule = actualAvailabilityPredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualAvailabilityPredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Method under test:
   * {@link AvailabilityPredicate#AvailabilityPredicate(IRule, IClientConfig)}
   */
  @Test
  public void testNewAvailabilityPredicate4() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "[{}] get global property '{}' with default '{}'");

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(rule, clientConfig);

    // Assert
    IRule iRule = actualAvailabilityPredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualAvailabilityPredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Method under test:
   * {@link AvailabilityPredicate#AvailabilityPredicate(IRule, IClientConfig)}
   */
  @Test
  public void testNewAvailabilityPredicate5() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(rule, clientConfig);

    // Assert
    IRule iRule = actualAvailabilityPredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualAvailabilityPredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Method under test:
   * {@link AvailabilityPredicate#AvailabilityPredicate(LoadBalancerStats, IClientConfig)}
   */
  @Test
  public void testNewAvailabilityPredicate6() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(lbStats, clientConfig);

    // Assert
    assertNull(actualAvailabilityPredicate.rule);
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(lbStats, actualAvailabilityPredicate.getLBStats());
  }

  /**
   * Method under test:
   * {@link AvailabilityPredicate#AvailabilityPredicate(LoadBalancerStats, IClientConfig)}
   */
  @Test
  public void testNewAvailabilityPredicate7() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(lbStats, null);

    // Assert
    assertNull(actualAvailabilityPredicate.rule);
    assertSame(lbStats, actualAvailabilityPredicate.getLBStats());
  }

  /**
   * Method under test:
   * {@link AvailabilityPredicate#AvailabilityPredicate(LoadBalancerStats, IClientConfig)}
   */
  @Test
  public void testNewAvailabilityPredicate8() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "[{}] get global property '{}' with default '{}'");

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(lbStats, clientConfig);

    // Assert
    assertNull(actualAvailabilityPredicate.rule);
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(lbStats, actualAvailabilityPredicate.getLBStats());
  }

  /**
   * Method under test:
   * {@link AvailabilityPredicate#AvailabilityPredicate(LoadBalancerStats, IClientConfig)}
   */
  @Test
  public void testNewAvailabilityPredicate9() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    AvailabilityPredicate actualAvailabilityPredicate = new AvailabilityPredicate(lbStats, clientConfig);

    // Assert
    assertNull(actualAvailabilityPredicate.rule);
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(lbStats, actualAvailabilityPredicate.getLBStats());
  }
}
