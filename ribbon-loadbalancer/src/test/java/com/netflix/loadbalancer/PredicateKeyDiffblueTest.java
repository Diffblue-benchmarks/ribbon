package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PredicateKeyDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code Load Balancer Key}.
   *   <li>Then return {@code Load Balancer Key}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link PredicateKey#PredicateKey(Object, Server)}
   *   <li>{@link PredicateKey#getLoadBalancerKey()}
   *   <li>{@link PredicateKey#getServer()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PredicateKey.<init>(Server)",
    "void PredicateKey.<init>(Object, Server)",
    "Object PredicateKey.getLoadBalancerKey()",
    "Server PredicateKey.getServer()"
  })
  public void testGettersAndSetters_whenLoadBalancerKey_thenReturnLoadBalancerKey() {
    // Arrange
    Server server = new Server("42");

    // Act
    PredicateKey actualPredicateKey = new PredicateKey("Load Balancer Key", server);
    Object actualLoadBalancerKey = actualPredicateKey.getLoadBalancerKey();

    // Assert
    assertEquals("Load Balancer Key", actualLoadBalancerKey);
    assertSame(server, actualPredicateKey.getServer());
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then return LoadBalancerKey is {@code null}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link PredicateKey#PredicateKey(Server)}
   *   <li>{@link PredicateKey#getLoadBalancerKey()}
   *   <li>{@link PredicateKey#getServer()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PredicateKey.<init>(Server)",
    "void PredicateKey.<init>(Object, Server)",
    "Object PredicateKey.getLoadBalancerKey()",
    "Server PredicateKey.getServer()"
  })
  public void testGettersAndSetters_whenServerWithIdIs42_thenReturnLoadBalancerKeyIsNull() {
    // Arrange
    Server server = new Server("42");

    // Act
    PredicateKey actualPredicateKey = new PredicateKey(server);
    Object actualLoadBalancerKey = actualPredicateKey.getLoadBalancerKey();

    // Assert
    assertNull(actualLoadBalancerKey);
    assertSame(server, actualPredicateKey.getServer());
  }
}
