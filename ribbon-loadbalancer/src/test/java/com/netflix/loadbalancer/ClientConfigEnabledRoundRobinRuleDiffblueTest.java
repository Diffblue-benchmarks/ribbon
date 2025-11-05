package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class ClientConfigEnabledRoundRobinRuleDiffblueTest {
  /**
   * Method under test:
   * {@link ClientConfigEnabledRoundRobinRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer() {
    // Arrange
    ClientConfigEnabledRoundRobinRule clientConfigEnabledRoundRobinRule = new ClientConfigEnabledRoundRobinRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    clientConfigEnabledRoundRobinRule.setLoadBalancer(lb);

    // Assert
    assertSame(lb, clientConfigEnabledRoundRobinRule.getLoadBalancer());
    assertSame(lb, clientConfigEnabledRoundRobinRule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link ClientConfigEnabledRoundRobinRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer2() {
    // Arrange
    ClientConfigEnabledRoundRobinRule clientConfigEnabledRoundRobinRule = new ClientConfigEnabledRoundRobinRule();
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    clientConfigEnabledRoundRobinRule.setLoadBalancer(lb);

    // Assert
    assertSame(lb, clientConfigEnabledRoundRobinRule.getLoadBalancer());
    assertSame(lb, clientConfigEnabledRoundRobinRule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Method under test: {@link ClientConfigEnabledRoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose() {
    // Arrange, Act and Assert
    assertNull((new ClientConfigEnabledRoundRobinRule()).choose("Key"));
  }

  /**
   * Method under test: {@link ClientConfigEnabledRoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose2() {
    // Arrange
    ClientConfigEnabledRoundRobinRule clientConfigEnabledRoundRobinRule = new ClientConfigEnabledRoundRobinRule();
    clientConfigEnabledRoundRobinRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(clientConfigEnabledRoundRobinRule.choose("Key"));
  }

  /**
   * Method under test: {@link ClientConfigEnabledRoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose3() {
    // Arrange
    ClientConfigEnabledRoundRobinRule clientConfigEnabledRoundRobinRule = new ClientConfigEnabledRoundRobinRule();
    IPing ping = mock(IPing.class);
    clientConfigEnabledRoundRobinRule.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act and Assert
    assertNull(clientConfigEnabledRoundRobinRule.choose("Key"));
  }

  /**
   * Method under test: {@link ClientConfigEnabledRoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose4() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    ClientConfigEnabledRoundRobinRule clientConfigEnabledRoundRobinRule = new ClientConfigEnabledRoundRobinRule();
    clientConfigEnabledRoundRobinRule.setLoadBalancer(lb);

    // Act and Assert
    assertSame(newServer, clientConfigEnabledRoundRobinRule.choose("Key"));
  }

  /**
   * Method under test: {@link ClientConfigEnabledRoundRobinRule#choose(Object)}
   */
  @Test
  public void testChoose5() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.addServer(new Server("42"));

    ClientConfigEnabledRoundRobinRule clientConfigEnabledRoundRobinRule = new ClientConfigEnabledRoundRobinRule();
    clientConfigEnabledRoundRobinRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(clientConfigEnabledRoundRobinRule.choose("Key"));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ClientConfigEnabledRoundRobinRule}
   */
  @Test
  public void testNewClientConfigEnabledRoundRobinRule() {
    // Arrange and Act
    ClientConfigEnabledRoundRobinRule actualClientConfigEnabledRoundRobinRule = new ClientConfigEnabledRoundRobinRule();

    // Assert
    assertNull(actualClientConfigEnabledRoundRobinRule.getLoadBalancer());
    assertNull(actualClientConfigEnabledRoundRobinRule.roundRobinRule.getLoadBalancer());
  }
}
