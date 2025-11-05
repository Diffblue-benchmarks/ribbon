package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class PredicateKeyDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PredicateKey#PredicateKey(Server)}
   *   <li>{@link PredicateKey#getLoadBalancerKey()}
   *   <li>{@link PredicateKey#getServer()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Server server = new Server("42");

    // Act
    PredicateKey actualPredicateKey = new PredicateKey(server);
    Object actualLoadBalancerKey = actualPredicateKey.getLoadBalancerKey();

    // Assert
    assertNull(actualLoadBalancerKey);
    assertSame(server, actualPredicateKey.getServer());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PredicateKey#PredicateKey(Object, Server)}
   *   <li>{@link PredicateKey#getLoadBalancerKey()}
   *   <li>{@link PredicateKey#getServer()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters2() {
    // Arrange
    Server server = new Server("42");

    // Act
    PredicateKey actualPredicateKey = new PredicateKey("Load Balancer Key", server);
    Object actualLoadBalancerKey = actualPredicateKey.getLoadBalancerKey();

    // Assert
    assertEquals("Load Balancer Key", actualLoadBalancerKey);
    assertSame(server, actualPredicateKey.getServer());
  }
}
