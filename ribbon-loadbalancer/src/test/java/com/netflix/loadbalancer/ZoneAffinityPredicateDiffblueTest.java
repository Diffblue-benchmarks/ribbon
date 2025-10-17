package com.netflix.loadbalancer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ZoneAffinityPredicateDiffblueTest {
  /**
   * Test {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)}.
   *
   * <p>Method under test: {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link Server#Server(String)} with id is {@code 42} Zone is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ZoneAffinityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_givenNull_whenServerWithIdIs42ZoneIsNull() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    Server server = new Server("42");
    server.setZone(null);

    // Act
    boolean actualApplyResult = zoneAffinityPredicate.apply(new PredicateKey(server));

    // Assert
    assertFalse(actualApplyResult);
  }

  /**
   * Test {@link ZoneAffinityPredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <ul>
   *   <li>Given {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)} with zone is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ZoneAffinityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_givenZoneAffinityPredicateWithZoneIsNull() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate(null);

    Server server = new Server("42");
    server.setZone("Server");

    // Act
    boolean actualApplyResult = zoneAffinityPredicate.apply(new PredicateKey(server));

    // Assert
    assertFalse(actualApplyResult);
  }

  /**
   * Test {@link ZoneAffinityPredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ZoneAffinityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_thenReturnTrue() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("unknown");

    // Act
    boolean actualApplyResult = zoneAffinityPredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertTrue(actualApplyResult);
  }

  /**
   * Test {@link ZoneAffinityPredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <ul>
   *   <li>When {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityPredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ZoneAffinityPredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_whenServerWithIdIs42_thenReturnFalse() {
    // Arrange
    ZoneAffinityPredicate zoneAffinityPredicate = new ZoneAffinityPredicate("Zone");

    // Act
    boolean actualApplyResult = zoneAffinityPredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertFalse(actualApplyResult);
  }
}
