package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class RoundRobinRuleDiffblueTest {
  /**
   * Method under test: {@link RoundRobinRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose() {
    // Arrange
    RoundRobinRule roundRobinRule = new RoundRobinRule();

    // Act and Assert
    assertNull(roundRobinRule.choose(new BaseLoadBalancer(), "Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose2() {
    // Arrange, Act and Assert
    assertNull((new RoundRobinRule()).choose(null, "Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose3() {
    // Arrange
    RoundRobinRule roundRobinRule = new RoundRobinRule();
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertNull(roundRobinRule.choose(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()), "Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose4() {
    // Arrange
    RoundRobinRule roundRobinRule = new RoundRobinRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    // Act and Assert
    assertSame(newServer, roundRobinRule.choose(lb, "Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose5() {
    // Arrange
    RoundRobinRule roundRobinRule = new RoundRobinRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.addServer(new Server("42"));

    // Act and Assert
    assertNull(roundRobinRule.choose(lb, "Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose6() {
    // Arrange, Act and Assert
    assertNull((new RoundRobinRule()).choose("Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose7() {
    // Arrange
    RoundRobinRule roundRobinRule = new RoundRobinRule();
    roundRobinRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(roundRobinRule.choose("Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose8() {
    // Arrange, Act and Assert
    assertNull((new ResponseTimeWeightedRule()).choose("Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose9() {
    // Arrange
    RoundRobinRule roundRobinRule = new RoundRobinRule();
    IPing ping = mock(IPing.class);
    roundRobinRule.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act and Assert
    assertNull(roundRobinRule.choose("Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose10() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    RoundRobinRule roundRobinRule = new RoundRobinRule();
    roundRobinRule.setLoadBalancer(lb);

    // Act and Assert
    assertSame(newServer, roundRobinRule.choose("Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose11() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.addServer(new Server("42"));

    RoundRobinRule roundRobinRule = new RoundRobinRule();
    roundRobinRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(roundRobinRule.choose("Key"));
  }

  /**
   * Method under test: {@link RoundRobinRule#RoundRobinRule()}
   */
  @Test
  public void testNewRoundRobinRule() {
    // Arrange, Act and Assert
    assertNull((new RoundRobinRule()).getLoadBalancer());
  }

  /**
   * Method under test: {@link RoundRobinRule#RoundRobinRule(ILoadBalancer)}
   */
  @Test
  public void testNewRoundRobinRule2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act and Assert
    assertSame(lb, (new RoundRobinRule(lb)).getLoadBalancer());
  }

  /**
   * Method under test: {@link RoundRobinRule#RoundRobinRule(ILoadBalancer)}
   */
  @Test
  public void testNewRoundRobinRule3() {
    // Arrange
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act and Assert
    assertSame(lb, (new RoundRobinRule(lb)).getLoadBalancer());
  }
}
