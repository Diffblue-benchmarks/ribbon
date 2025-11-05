package com.netflix.loadbalancer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ZoneAffinityPredicateDiffblueTest {
  /**
   * Method under test: {@link ZoneAffinityPredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    // Act and Assert
    assertFalse(zoneAffinityPredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link ZoneAffinityPredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply2() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    // Act and Assert
    assertTrue(zoneAffinityPredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test: {@link ZoneAffinityPredicate#apply(PredicateKey)}
   */
  @Test
  public void testApply3() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate(null);

    // Act and Assert
    assertFalse(zoneAffinityPredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Method under test:
   * {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)}
   */
  @Test
  public void testNewZoneAffinityPredicate() {
    // Arrange and Act
    ZoneAffinityPredicate actualZoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    // Assert
    assertNull(actualZoneAffinityPredicate.rule);
    assertNull(actualZoneAffinityPredicate.getLBStats());
  }
}
