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

public class ZoneAvoidancePredicateDiffblueTest {
  /**
   * Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply() {
    // Arrange
    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(new AvailabilityFilteringRule());

    // Act and Assert
    assertTrue(zoneAvoidancePredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply2() {
    // Arrange
    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(null);

    // Act and Assert
    assertTrue(zoneAvoidancePredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply3() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(rule,
        DefaultClientConfigImpl.getEmptyConfig());

    // Act and Assert
    assertTrue(zoneAvoidancePredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply4() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(lbStats,
        DefaultClientConfigImpl.getEmptyConfig());

    // Act and Assert
    assertTrue(zoneAvoidancePredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply5() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    rule.setLoadBalancer(new BaseLoadBalancer());
    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(rule);

    // Act and Assert
    assertTrue(zoneAvoidancePredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply6() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    IPing ping = mock(IPing.class);
    rule.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));
    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(rule);

    // Act and Assert
    assertTrue(zoneAvoidancePredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply7() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer(DefaultClientConfigImpl.getEmptyConfig());
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));

    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    rule.setLoadBalancer(lb);
    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(rule);

    // Act and Assert
    assertTrue(zoneAvoidancePredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link ZoneAvoidancePredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply8() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServerStatusChangeListener(mock(ServerStatusChangeListener.class));
    lb.addServers(new Object[]{"New Servers"});

    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    rule.setLoadBalancer(lb);
    ZoneAvoidancePredicate zoneAvoidancePredicate = new ZoneAvoidancePredicate(rule);

    // Act and Assert
    assertTrue(zoneAvoidancePredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test:
   * {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule)}
   */
  @Test
  public void testNewZoneAvoidancePredicate() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(rule);

    // Assert
    IRule iRule = actualZoneAvoidancePredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualZoneAvoidancePredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Method under test:
   * {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule)}
   */
  @Test
  public void testNewZoneAvoidancePredicate2() {
    // Arrange and Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(null);

    // Assert
    assertNull(actualZoneAvoidancePredicate.rule);
    assertNull(actualZoneAvoidancePredicate.getLBStats());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule, IClientConfig)}
   */
  @Test
  public void testNewZoneAvoidancePredicate3() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(rule, clientConfig);

    // Assert
    IRule iRule = actualZoneAvoidancePredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualZoneAvoidancePredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Method under test:
   * {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule, IClientConfig)}
   */
  @Test
  public void testNewZoneAvoidancePredicate4() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(rule, null);

    // Assert
    IRule iRule = actualZoneAvoidancePredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualZoneAvoidancePredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Method under test:
   * {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule, IClientConfig)}
   */
  @Test
  public void testNewZoneAvoidancePredicate5() {
    // Arrange
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(rule, clientConfig);

    // Assert
    IRule iRule = actualZoneAvoidancePredicate.rule;
    assertTrue(iRule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) iRule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    RoundRobinRule roundRobinRule = ((AvailabilityFilteringRule) iRule).roundRobinRule;
    assertNull(roundRobinRule.getLoadBalancer());
    assertNull(iRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(actualZoneAvoidancePredicate.getLBStats());
    assertNull(predicate.getLBStats());
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(rule.roundRobinRule, roundRobinRule);
  }

  /**
   * Method under test:
   * {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(IRule, IClientConfig)}
   */
  @Test
  public void testNewZoneAvoidancePredicate6() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(null);

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate((IRule) null, clientConfig);

    // Assert
    assertNull(actualZoneAvoidancePredicate.rule);
    assertNull(actualZoneAvoidancePredicate.getLBStats());
    assertEquals(3L, clientConfig.getRefreshCount());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(LoadBalancerStats, IClientConfig)}
   */
  @Test
  public void testNewZoneAvoidancePredicate7() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(lbStats, clientConfig);

    // Assert
    assertNull(actualZoneAvoidancePredicate.rule);
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(lbStats, actualZoneAvoidancePredicate.getLBStats());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(LoadBalancerStats, IClientConfig)}
   */
  @Test
  public void testNewZoneAvoidancePredicate8() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(lbStats, null);

    // Assert
    assertNull(actualZoneAvoidancePredicate.rule);
    assertSame(lbStats, actualZoneAvoidancePredicate.getLBStats());
  }

  /**
   * Method under test:
   * {@link ZoneAvoidancePredicate#ZoneAvoidancePredicate(LoadBalancerStats, IClientConfig)}
   */
  @Test
  public void testNewZoneAvoidancePredicate9() {
    // Arrange
    LoadBalancerStats lbStats = new LoadBalancerStats();
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    ZoneAvoidancePredicate actualZoneAvoidancePredicate = new ZoneAvoidancePredicate(lbStats, clientConfig);

    // Assert
    assertNull(actualZoneAvoidancePredicate.rule);
    assertEquals(3L, clientConfig.getRefreshCount());
    assertSame(lbStats, actualZoneAvoidancePredicate.getLBStats());
  }
}
