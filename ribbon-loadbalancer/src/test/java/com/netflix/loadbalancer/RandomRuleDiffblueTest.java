package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class RandomRuleDiffblueTest {
  /**
   * Method under test: {@link RandomRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose() {
    // Arrange
    RandomRule randomRule = new RandomRule();

    // Act and Assert
    assertNull(randomRule.choose(new BaseLoadBalancer(), "Key"));
  }

  /**
   * Method under test: {@link RandomRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose2() {
    // Arrange, Act and Assert
    assertNull((new RandomRule()).choose(null, "Key"));
  }

  /**
   * Method under test: {@link RandomRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose3() {
    // Arrange
    RandomRule randomRule = new RandomRule();
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertNull(randomRule.choose(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()), "Key"));
  }

  /**
   * Method under test: {@link RandomRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose4() {
    // Arrange
    RandomRule randomRule = new RandomRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    // Act and Assert
    assertSame(newServer, randomRule.choose(lb, "Key"));
  }

  /**
   * Method under test: {@link RandomRule#choose(Object)}
   */
  @Test
  public void testChoose5() {
    // Arrange, Act and Assert
    assertNull((new RandomRule()).choose("Key"));
  }

  /**
   * Method under test: {@link RandomRule#choose(Object)}
   */
  @Test
  public void testChoose6() {
    // Arrange
    RandomRule randomRule = new RandomRule();
    randomRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(randomRule.choose("Key"));
  }

  /**
   * Method under test: {@link RandomRule#choose(Object)}
   */
  @Test
  public void testChoose7() {
    // Arrange
    RandomRule randomRule = new RandomRule();
    IPing ping = mock(IPing.class);
    randomRule.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act and Assert
    assertNull(randomRule.choose("Key"));
  }

  /**
   * Method under test: {@link RandomRule#choose(Object)}
   */
  @Test
  public void testChoose8() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    RandomRule randomRule = new RandomRule();
    randomRule.setLoadBalancer(lb);

    // Act and Assert
    assertSame(newServer, randomRule.choose("Key"));
  }

  /**
   * Method under test: default or parameterless constructor of {@link RandomRule}
   */
  @Test
  public void testNewRandomRule() {
    // Arrange, Act and Assert
    assertNull((new RandomRule()).getLoadBalancer());
  }
}
