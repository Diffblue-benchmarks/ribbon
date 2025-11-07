package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PredicateBasedRuleDiffblueTest {
  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   * <ul>
   *   <li>Given {@link ZoneAvoidanceRule} (default constructor) LoadBalancer is {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"com.netflix.loadbalancer.Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenZoneAvoidanceRuleLoadBalancerIsBaseLoadBalancer_thenReturnNull() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    zoneAvoidanceRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(zoneAvoidanceRule.choose("Key"));
  }

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"com.netflix.loadbalancer.Server PredicateBasedRule.choose(Object)"})
  public void testChoose_whenNull_thenReturnNull() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    zoneAvoidanceRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(zoneAvoidanceRule.choose(null));
  }
}
