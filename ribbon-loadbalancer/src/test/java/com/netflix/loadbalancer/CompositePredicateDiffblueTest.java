package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.CompositePredicate.Builder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CompositePredicateDiffblueTest {
  /**
   * Test {@link CompositePredicate#apply(PredicateKey)} with {@code PredicateKey}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link CompositePredicate#apply(PredicateKey)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean CompositePredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_thenReturnTrue() {
    // Arrange
    CompositePredicate primaryPredicate = CompositePredicate.withPredicates().build();
    CompositePredicate compositePredicate =
        CompositePredicate.withPredicate(primaryPredicate).build();

    // Act
    boolean actualApplyResult = compositePredicate.apply(new PredicateKey(new Server("42")));

    // Assert
    assertTrue(actualApplyResult);
  }

  /**
   * Test Builder {@link Builder#setFallbackThresholdAsMinimalFilteredNumberOfServers(int)}.
   *
   * <p>Method under test: {@link Builder#setFallbackThresholdAsMinimalFilteredNumberOfServers(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.setFallbackThresholdAsMinimalFilteredNumberOfServers(int)"})
  public void testBuilderSetFallbackThresholdAsMinimalFilteredNumberOfServers() {
    // Arrange
    CompositePredicate primaryPredicate = CompositePredicate.withPredicates().build();
    Builder withPredicateResult = CompositePredicate.withPredicate(primaryPredicate);

    // Act
    Builder actualSetFallbackThresholdAsMinimalFilteredNumberOfServersResult =
        withPredicateResult.setFallbackThresholdAsMinimalFilteredNumberOfServers(10);

    // Assert
    assertSame(
        withPredicateResult, actualSetFallbackThresholdAsMinimalFilteredNumberOfServersResult);
  }

  /**
   * Test Builder {@link Builder#setFallbackThresholdAsMinimalFilteredPercentage(float)}.
   *
   * <p>Method under test: {@link Builder#setFallbackThresholdAsMinimalFilteredPercentage(float)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.setFallbackThresholdAsMinimalFilteredPercentage(float)"})
  public void testBuilderSetFallbackThresholdAsMinimalFilteredPercentage() {
    // Arrange
    CompositePredicate primaryPredicate = CompositePredicate.withPredicates().build();
    Builder withPredicateResult = CompositePredicate.withPredicate(primaryPredicate);

    // Act
    Builder actualSetFallbackThresholdAsMinimalFilteredPercentageResult =
        withPredicateResult.setFallbackThresholdAsMinimalFilteredPercentage(10.0f);

    // Assert
    assertSame(withPredicateResult, actualSetFallbackThresholdAsMinimalFilteredPercentageResult);
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code
   * loadBalancerKey}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_whenArrayList_thenReturnEmpty() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();

    // Act and Assert
    assertTrue(
        compositePredicate.getEligibleServers(new ArrayList<>(), "Load Balancer Key").isEmpty());
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code
   * loadBalancerKey}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_whenArrayList_thenReturnEmpty2() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();

    // Act and Assert
    assertTrue(compositePredicate.getEligibleServers(new ArrayList<>(), null).isEmpty());
  }

  /**
   * Test new {@link CompositePredicate} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link CompositePredicate}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CompositePredicate.<init>()"})
  public void testNewCompositePredicate() {
    // Arrange and Act
    CompositePredicate actualCompositePredicate = new CompositePredicate();

    // Assert
    assertNull(actualCompositePredicate.rule);
    assertNull(actualCompositePredicate.getLBStats());
  }
}
