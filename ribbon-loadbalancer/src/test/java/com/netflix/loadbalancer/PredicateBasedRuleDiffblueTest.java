package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PredicateBasedRuleDiffblueTest {
  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"com.netflix.loadbalancer.Server PredicateBasedRule.choose(Object)"})
  public void testChoose() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setEnablePrimingConnections(true);
    lb.setPing(mock(IPing.class));
    lb.addServer(null);

    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(availabilityFilteringRule.choose(null));
  }

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link AvailabilityFilteringRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"com.netflix.loadbalancer.Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenAvailabilityFilteringRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(availabilityFilteringRule.choose("Key"));
  }

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAvoidanceRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"com.netflix.loadbalancer.Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenZoneAvoidanceRuleLoadBalancerIsBaseLoadBalancer_thenReturnNull() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    zoneAvoidanceRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(zoneAvoidanceRule.choose("Key"));
  }
}
