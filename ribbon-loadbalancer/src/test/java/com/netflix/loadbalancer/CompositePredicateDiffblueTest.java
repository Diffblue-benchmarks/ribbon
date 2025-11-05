package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CompositePredicateDiffblueTest {
  /**
   * Method under test:
   * {@link CompositePredicate.Builder#addFallbackPredicate(AbstractServerPredicate)}
   */
  @Test
  public void testBuilderAddFallbackPredicate() {
    // Arrange
    CompositePredicate.Builder withPredicateResult = CompositePredicate.withPredicate(new CompositePredicate());

    // Act and Assert
    assertSame(withPredicateResult, withPredicateResult.addFallbackPredicate(new CompositePredicate()));
  }

  /**
   * Method under test: {@link CompositePredicate.Builder#build()}
   */
  @Test
  public void testBuilderBuild() {
    // Arrange and Act
    CompositePredicate actualBuildResult = (new CompositePredicate.Builder(new CompositePredicate())).build();

    // Assert
    assertNull(actualBuildResult.rule);
    assertNull(actualBuildResult.getLBStats());
  }

  /**
   * Method under test:
   * {@link CompositePredicate.Builder#Builder(AbstractServerPredicate)}
   */
  @Test
  public void testBuilderNewBuilder() {
    // Arrange, Act and Assert
    CompositePredicate buildResult = (new CompositePredicate.Builder(new CompositePredicate())).build();
    assertNull(buildResult.rule);
    assertNull(buildResult.getLBStats());
  }

  /**
   * Method under test:
   * {@link CompositePredicate.Builder#Builder(AbstractServerPredicate)}
   */
  @Test
  public void testBuilderNewBuilder2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    CompositePredicate buildResult = (new CompositePredicate.Builder(
        new AvailabilityPredicate(new AvailabilityFilteringRule(), clientConfig))).build();
    assertNull(buildResult.rule);
    assertNull(buildResult.getLBStats());
  }

  /**
   * Method under test:
   * {@link CompositePredicate.Builder#Builder(AbstractServerPredicate[])}
   */
  @Test
  public void testBuilderNewBuilder3() {
    // Arrange, Act and Assert
    CompositePredicate buildResult = (new CompositePredicate.Builder(new CompositePredicate())).build();
    assertNull(buildResult.rule);
    assertNull(buildResult.getLBStats());
  }

  /**
   * Method under test:
   * {@link CompositePredicate.Builder#Builder(AbstractServerPredicate[])}
   */
  @Test
  public void testBuilderNewBuilder4() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    CompositePredicate buildResult = (new CompositePredicate.Builder(
        new AvailabilityPredicate(new AvailabilityFilteringRule(), clientConfig))).build();
    assertNull(buildResult.rule);
    assertNull(buildResult.getLBStats());
  }

  /**
   * Method under test:
   * {@link CompositePredicate.Builder#setFallbackThresholdAsMinimalFilteredNumberOfServers(int)}
   */
  @Test
  public void testBuilderSetFallbackThresholdAsMinimalFilteredNumberOfServers() {
    // Arrange
    CompositePredicate.Builder withPredicateResult = CompositePredicate.withPredicate(new CompositePredicate());

    // Act and Assert
    assertSame(withPredicateResult, withPredicateResult.setFallbackThresholdAsMinimalFilteredNumberOfServers(10));
  }

  /**
   * Method under test:
   * {@link CompositePredicate.Builder#setFallbackThresholdAsMinimalFilteredNumberOfServers(int)}
   */
  @Test
  public void testBuilderSetFallbackThresholdAsMinimalFilteredNumberOfServers2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    CompositePredicate.Builder withPredicateResult = CompositePredicate
        .withPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule(), clientConfig));

    // Act and Assert
    assertSame(withPredicateResult, withPredicateResult.setFallbackThresholdAsMinimalFilteredNumberOfServers(10));
  }

  /**
   * Method under test:
   * {@link CompositePredicate.Builder#setFallbackThresholdAsMinimalFilteredPercentage(float)}
   */
  @Test
  public void testBuilderSetFallbackThresholdAsMinimalFilteredPercentage() {
    // Arrange
    CompositePredicate.Builder withPredicateResult = CompositePredicate.withPredicate(new CompositePredicate());

    // Act and Assert
    assertSame(withPredicateResult, withPredicateResult.setFallbackThresholdAsMinimalFilteredPercentage(10.0f));
  }

  /**
   * Method under test:
   * {@link CompositePredicate.Builder#setFallbackThresholdAsMinimalFilteredPercentage(float)}
   */
  @Test
  public void testBuilderSetFallbackThresholdAsMinimalFilteredPercentage2() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));
    CompositePredicate.Builder withPredicateResult = CompositePredicate
        .withPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule(), clientConfig));

    // Act and Assert
    assertSame(withPredicateResult, withPredicateResult.setFallbackThresholdAsMinimalFilteredPercentage(10.0f));
  }

  /**
   * Method under test:
   * {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  public void testGetEligibleServers() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();

    // Act and Assert
    assertTrue(compositePredicate.getEligibleServers(new ArrayList<>(), "Load Balancer Key").isEmpty());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link CompositePredicate}
   */
  @Test
  public void testNewCompositePredicate() {
    // Arrange and Act
    CompositePredicate actualCompositePredicate = new CompositePredicate();

    // Assert
    assertNull(actualCompositePredicate.rule);
    assertNull(actualCompositePredicate.getLBStats());
  }
}
