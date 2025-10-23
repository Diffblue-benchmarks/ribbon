package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AvailabilityFilteringRuleDiffblueTest {
  /**
   * Test new {@link AvailabilityFilteringRule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link AvailabilityFilteringRule}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AvailabilityFilteringRule.<init>()"})
  public void testNewAvailabilityFilteringRule() {
    // Arrange and Act
    AvailabilityFilteringRule actualAvailabilityFilteringRule = new AvailabilityFilteringRule();

    // Assert
    AbstractServerPredicate predicate = actualAvailabilityFilteringRule.getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    assertNull(actualAvailabilityFilteringRule.getLoadBalancer());
    assertNull(actualAvailabilityFilteringRule.roundRobinRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
  }

  /**
   * Test {@link AvailabilityFilteringRule#getAvailableServersCount()}.
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#getAvailableServersCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int AvailabilityFilteringRule.getAvailableServersCount()"})
  public void testGetAvailableServersCount() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new NoOpLoadBalancer());

    // Act and Assert
    assertEquals(0, availabilityFilteringRule.getAvailableServersCount());
  }

  /**
   * Test {@link AvailabilityFilteringRule#getAvailableServersCount()}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#getAvailableServersCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int AvailabilityFilteringRule.getAvailableServersCount()"})
  public void testGetAvailableServersCount_thenReturnOne() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setLoadBalancerStats(null);
    lb.addServer(new Server("42"));

    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(lb);

    // Act and Assert
    assertEquals(1, availabilityFilteringRule.getAvailableServersCount());
  }

  /**
   * Test {@link AvailabilityFilteringRule#getAvailableServersCount()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#getAvailableServersCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int AvailabilityFilteringRule.getAvailableServersCount()"})
  public void testGetAvailableServersCount_thenReturnZero() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertEquals(0, availabilityFilteringRule.getAvailableServersCount());
  }

  /**
   * Test {@link AvailabilityFilteringRule#choose(Object)}.
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server AvailabilityFilteringRule.choose(Object)"})
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
   * Test {@link AvailabilityFilteringRule#choose(Object)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server AvailabilityFilteringRule.choose(Object)"})
  public void testChoose_thenReturnNull() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(availabilityFilteringRule.choose("Key"));
  }

  /**
   * Test {@link AvailabilityFilteringRule#getPredicate()}.
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#getPredicate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractServerPredicate AvailabilityFilteringRule.getPredicate()"})
  public void testGetPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualPredicate = new AvailabilityFilteringRule().getPredicate();

    // Assert
    assertTrue(actualPredicate instanceof CompositePredicate);
    assertNull(((CompositePredicate) actualPredicate).rule);
  }
}
