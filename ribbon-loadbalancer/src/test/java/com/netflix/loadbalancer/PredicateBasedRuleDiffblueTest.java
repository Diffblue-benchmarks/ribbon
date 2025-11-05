package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class PredicateBasedRuleDiffblueTest {
  /**
   * Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  public void testChoose() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    zoneAvoidanceRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(zoneAvoidanceRule.choose("Key"));
  }

  /**
   * Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  public void testChoose2() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    IPing ping = mock(IPing.class);
    zoneAvoidanceRule.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act and Assert
    assertNull(zoneAvoidanceRule.choose("Key"));
  }

  /**
   * Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  public void testChoose3() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    zoneAvoidanceRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(zoneAvoidanceRule.choose(null));
  }
}
