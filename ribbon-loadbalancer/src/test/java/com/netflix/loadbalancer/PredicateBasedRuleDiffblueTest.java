package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
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
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    zoneAvoidanceRule.setLoadBalancer(new BaseLoadBalancer(config));

    // Act and Assert
    assertNull(zoneAvoidanceRule.choose("Key"));
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
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenAvailabilityFilteringRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(availabilityFilteringRule.choose(null));
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
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenZoneAvoidanceRuleLoadBalancerIsBaseLoadBalancer_thenReturnNull() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    zoneAvoidanceRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(zoneAvoidanceRule.choose("Key"));
  }

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAwareLoadBalancer#ZoneAwareLoadBalancer()} addServer {@code null}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenZoneAwareLoadBalancerAddServerNull_whenNull_thenReturnNull() {
    // Arrange
    ConfigurationBasedServerList niwsServerList = new ConfigurationBasedServerList();
    niwsServerList.initWithNiwsConfig(
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build());

    ZoneAwareLoadBalancer<Server> lb = new ZoneAwareLoadBalancer<>();
    lb.setServerListImpl(niwsServerList);
    lb.setPing(mock(IPing.class));
    lb.addServer(null);

    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(availabilityFilteringRule.choose(null));
  }
}
