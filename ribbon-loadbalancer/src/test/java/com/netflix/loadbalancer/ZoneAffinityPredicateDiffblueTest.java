package com.netflix.loadbalancer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ZoneAffinityPredicateDiffblueTest {
  /**
   * Test {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)}.
   * <p>
   * Method under test: {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ZoneAffinityPredicate.<init>(String)"})
  public void testNewZoneAffinityPredicate() {
    // Arrange and Act
    ZoneAffinityPredicate actualZoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    // Assert
    assertNull(actualZoneAffinityPredicate.rule);
    assertNull(actualZoneAffinityPredicate.getLBStats());
  }

  /**
   * Test {@link ZoneAffinityPredicate#apply(PredicateKey)} with {@code PredicateKey}.
   * <ul>
   *   <li>Given {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)} with zone is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAffinityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ZoneAffinityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_givenZoneAffinityPredicateWithZoneIsNull() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate(null);

    // Act and Assert
    assertFalse(zoneAffinityPredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Test {@link ZoneAffinityPredicate#apply(PredicateKey)} with {@code PredicateKey}.
   * <ul>
   *   <li>Given {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)} with {@code Zone}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAffinityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ZoneAffinityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_givenZoneAffinityPredicateWithZone_thenReturnFalse() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    // Act and Assert
    assertFalse(zoneAffinityPredicate.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Test {@link ZoneAffinityPredicate#apply(PredicateKey)} with {@code PredicateKey}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ZoneAffinityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ZoneAffinityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_thenReturnTrue() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    // Act and Assert
    assertTrue(zoneAffinityPredicate.apply(new PredicateKey(new Server("42"))));
  }
}
