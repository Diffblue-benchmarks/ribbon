package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClientConfigEnabledRoundRobinRuleDiffblueTest {
  /**
   * Test {@link ClientConfigEnabledRoundRobinRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <p>Method under test: {@link ClientConfigEnabledRoundRobinRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClientConfigEnabledRoundRobinRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer() {
    // Arrange
    ClientConfigEnabledRoundRobinRule clientConfigEnabledRoundRobinRule =
        new ClientConfigEnabledRoundRobinRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    clientConfigEnabledRoundRobinRule.setLoadBalancer(lb);

    // Assert
    assertSame(lb, clientConfigEnabledRoundRobinRule.getLoadBalancer());
    assertSame(lb, clientConfigEnabledRoundRobinRule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link ClientConfigEnabledRoundRobinRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link AvailabilityFilteringRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link ClientConfigEnabledRoundRobinRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ClientConfigEnabledRoundRobinRule.choose(Object)"})
  public void testChoose_givenAvailabilityFilteringRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(availabilityFilteringRule.choose("Key"));
  }

  /**
   * Test {@link ClientConfigEnabledRoundRobinRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} EnablePrimingConnections is {@code
   *       true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ClientConfigEnabledRoundRobinRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ClientConfigEnabledRoundRobinRule.choose(Object)"})
  public void testChoose_givenBaseLoadBalancerEnablePrimingConnectionsIsTrue_thenReturnNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.addServer(new Server("42"));

    ClientConfigEnabledRoundRobinRule clientConfigEnabledRoundRobinRule =
        new ClientConfigEnabledRoundRobinRule();
    clientConfigEnabledRoundRobinRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(clientConfigEnabledRoundRobinRule.choose("Key"));
  }

  /**
   * Test {@link ClientConfigEnabledRoundRobinRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ClientConfigEnabledRoundRobinRule} (default constructor) LoadBalancer is
   *       {@link BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link ClientConfigEnabledRoundRobinRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ClientConfigEnabledRoundRobinRule.choose(Object)"})
  public void testChoose_givenClientConfigEnabledRoundRobinRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    ClientConfigEnabledRoundRobinRule clientConfigEnabledRoundRobinRule =
        new ClientConfigEnabledRoundRobinRule();
    clientConfigEnabledRoundRobinRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(clientConfigEnabledRoundRobinRule.choose("Key"));
  }

  /**
   * Test {@link ClientConfigEnabledRoundRobinRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ClientConfigEnabledRoundRobinRule} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ClientConfigEnabledRoundRobinRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ClientConfigEnabledRoundRobinRule.choose(Object)"})
  public void testChoose_givenClientConfigEnabledRoundRobinRule_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new ClientConfigEnabledRoundRobinRule().choose("Key"));
  }

  /**
   * Test {@link ClientConfigEnabledRoundRobinRule#choose(Object)}.
   *
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ClientConfigEnabledRoundRobinRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server ClientConfigEnabledRoundRobinRule.choose(Object)"})
  public void testChoose_thenReturnServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    ClientConfigEnabledRoundRobinRule clientConfigEnabledRoundRobinRule =
        new ClientConfigEnabledRoundRobinRule();
    clientConfigEnabledRoundRobinRule.setLoadBalancer(lb);

    // Act and Assert
    assertSame(newServer, clientConfigEnabledRoundRobinRule.choose("Key"));
  }

  /**
   * Test new {@link ClientConfigEnabledRoundRobinRule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * ClientConfigEnabledRoundRobinRule}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClientConfigEnabledRoundRobinRule.<init>()"})
  public void testNewClientConfigEnabledRoundRobinRule() {
    // Arrange and Act
    ClientConfigEnabledRoundRobinRule actualClientConfigEnabledRoundRobinRule =
        new ClientConfigEnabledRoundRobinRule();

    // Assert
    assertNull(actualClientConfigEnabledRoundRobinRule.getLoadBalancer());
    assertNull(actualClientConfigEnabledRoundRobinRule.roundRobinRule.getLoadBalancer());
  }
}
