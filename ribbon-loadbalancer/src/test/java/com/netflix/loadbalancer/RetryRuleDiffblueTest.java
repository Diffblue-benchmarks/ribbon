package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RetryRuleDiffblueTest {
  /**
   * Test {@link RetryRule#RetryRule()}.
   *
   * <p>Method under test: {@link RetryRule#RetryRule()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.<init>()"})
  public void testNewRetryRule() {
    // Arrange and Act
    RetryRule actualRetryRule = new RetryRule();

    // Assert
    IRule rule = actualRetryRule.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertNull(actualRetryRule.getLoadBalancer());
    assertNull(rule.getLoadBalancer());
    assertEquals(500L, actualRetryRule.getMaxRetryMillis());
  }

  /**
   * Test {@link RetryRule#RetryRule(IRule)}.
   *
   * <ul>
   *   <li>Then return Rule is {@link AvailabilityFilteringRule} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#RetryRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.<init>(IRule)"})
  public void testNewRetryRule_thenReturnRuleIsAvailabilityFilteringRule() {
    // Arrange
    AvailabilityFilteringRule subRule = new AvailabilityFilteringRule();

    // Act and Assert
    assertSame(subRule, new RetryRule(subRule).getRule());
  }

  /**
   * Test {@link RetryRule#RetryRule(IRule, long)}.
   *
   * <ul>
   *   <li>When {@link AvailabilityFilteringRule} (default constructor).
   *   <li>Then return MaxRetryMillis is one.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#RetryRule(IRule, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.<init>(IRule, long)"})
  public void testNewRetryRule_whenAvailabilityFilteringRule_thenReturnMaxRetryMillisIsOne() {
    // Arrange
    AvailabilityFilteringRule subRule = new AvailabilityFilteringRule();

    // Act
    RetryRule actualRetryRule = new RetryRule(subRule, 1L);

    // Assert
    assertEquals(1L, actualRetryRule.getMaxRetryMillis());
    assertSame(subRule, actualRetryRule.getRule());
  }

  /**
   * Test {@link RetryRule#RetryRule(IRule)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#RetryRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.<init>(IRule)"})
  public void testNewRetryRule_whenNull_thenRuleReturnRoundRobinRule() {
    // Arrange and Act
    RetryRule actualRetryRule = new RetryRule(null);

    // Assert
    IRule rule = actualRetryRule.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertNull(actualRetryRule.getLoadBalancer());
    assertNull(rule.getLoadBalancer());
    assertEquals(500L, actualRetryRule.getMaxRetryMillis());
  }

  /**
   * Test {@link RetryRule#RetryRule(IRule, long)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then Rule return {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#RetryRule(IRule, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.<init>(IRule, long)"})
  public void testNewRetryRule_whenNull_thenRuleReturnRoundRobinRule2() {
    // Arrange and Act
    RetryRule actualRetryRule = new RetryRule(null, 0L);

    // Assert
    IRule rule = actualRetryRule.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertNull(actualRetryRule.getLoadBalancer());
    assertNull(rule.getLoadBalancer());
    assertEquals(500L, actualRetryRule.getMaxRetryMillis());
  }

  /**
   * Test {@link RetryRule#setRule(IRule)}.
   *
   * <ul>
   *   <li>Then {@link RetryRule#RetryRule()} Rule is {@link AvailabilityFilteringRule} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.setRule(IRule)"})
  public void testSetRule_thenRetryRuleRuleIsAvailabilityFilteringRule() {
    // Arrange
    RetryRule retryRule = new RetryRule();
    AvailabilityFilteringRule subRule = new AvailabilityFilteringRule();

    // Act
    retryRule.setRule(subRule);

    // Assert
    assertSame(subRule, retryRule.getRule());
  }

  /**
   * Test {@link RetryRule#setRule(IRule)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link RetryRule#RetryRule()} Rule {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#setRule(IRule)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.setRule(IRule)"})
  public void testSetRule_whenNull_thenRetryRuleRuleRoundRobinRule() {
    // Arrange
    RetryRule retryRule = new RetryRule();

    // Act
    retryRule.setRule(null);

    // Assert that nothing has changed
    assertTrue(retryRule.getRule() instanceof RoundRobinRule);
  }

  /**
   * Test {@link RetryRule#setMaxRetryMillis(long)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then {@link RetryRule#RetryRule()} MaxRetryMillis is one.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#setMaxRetryMillis(long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.setMaxRetryMillis(long)"})
  public void testSetMaxRetryMillis_whenOne_thenRetryRuleMaxRetryMillisIsOne() {
    // Arrange
    RetryRule retryRule = new RetryRule();

    // Act
    retryRule.setMaxRetryMillis(1L);

    // Assert
    assertEquals(1L, retryRule.getMaxRetryMillis());
  }

  /**
   * Test {@link RetryRule#setMaxRetryMillis(long)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then {@link RetryRule#RetryRule()} MaxRetryMillis is five hundred.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#setMaxRetryMillis(long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.setMaxRetryMillis(long)"})
  public void testSetMaxRetryMillis_whenZero_thenRetryRuleMaxRetryMillisIsFiveHundred() {
    // Arrange
    RetryRule retryRule = new RetryRule();

    // Act
    retryRule.setMaxRetryMillis(0L);

    // Assert that nothing has changed
    assertEquals(500L, retryRule.getMaxRetryMillis());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link RetryRule#getMaxRetryMillis()}
   *   <li>{@link RetryRule#getRule()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long RetryRule.getMaxRetryMillis()", "IRule RetryRule.getRule()"})
  public void testGettersAndSetters() {
    // Arrange
    RetryRule retryRule = new RetryRule();

    // Act
    long actualMaxRetryMillis = retryRule.getMaxRetryMillis();

    // Assert
    assertTrue(retryRule.getRule() instanceof RoundRobinRule);
    assertEquals(500L, actualMaxRetryMillis);
  }

  /**
   * Test {@link RetryRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <p>Method under test: {@link RetryRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer() {
    // Arrange
    RetryRule retryRule = new RetryRule(new RetryRule(), 1L);
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    retryRule.setLoadBalancer(lb);

    // Assert
    IRule rule = retryRule.getRule();
    assertTrue(rule instanceof RetryRule);
    IRule rule2 = ((RetryRule) rule).getRule();
    assertTrue(rule2 instanceof RoundRobinRule);
    assertSame(lb, retryRule.getLoadBalancer());
    assertSame(lb, rule.getLoadBalancer());
    assertSame(lb, rule2.getLoadBalancer());
  }

  /**
   * Test {@link RetryRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Given {@link RetryRule#RetryRule()}.
   *   <li>Then {@link RetryRule#RetryRule()} Rule {@link RoundRobinRule}.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RetryRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_givenRetryRule_thenRetryRuleRuleRoundRobinRule() {
    // Arrange
    RetryRule retryRule = new RetryRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    retryRule.setLoadBalancer(lb);

    // Assert
    IRule rule = retryRule.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertSame(lb, retryRule.getLoadBalancer());
    assertSame(lb, rule.getLoadBalancer());
  }

  /**
   * Test {@link RetryRule#choose(Object)} with {@code key}.
   *
   * <ul>
   *   <li>Then return Host is {@code New Servers}.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server RetryRule.choose(Object)"})
  public void testChooseWithKey_thenReturnHostIsNewServers() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[] {"New Servers"});
    lb.setEnablePrimingConnections(true);
    lb.addServer(new Server("42"));

    RetryRule retryRule = new RetryRule();
    retryRule.setLoadBalancer(lb);

    // Act
    Server actualChooseResult = retryRule.choose("Key");

    // Assert
    assertEquals("New Servers", actualChooseResult.getHost());
    assertEquals("New Servers:80", actualChooseResult.getHostPort());
    assertEquals("New Servers:80", actualChooseResult.getId());
    assertNull(actualChooseResult.getScheme());
    assertEquals(80, actualChooseResult.getPort());
    assertTrue(actualChooseResult.isAlive());
    assertTrue(actualChooseResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualChooseResult.getZone());
  }

  /**
   * Test {@link RetryRule#choose(Object)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server RetryRule.choose(Object)"})
  public void testChooseWithKey_thenReturnServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    RetryRule retryRule = new RetryRule();
    retryRule.setLoadBalancer(lb);

    // Act and Assert
    assertSame(newServer, retryRule.choose("Key"));
  }

  /**
   * Test {@link RetryRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   *
   * <ul>
   *   <li>Then return Host is {@code New Servers}.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server RetryRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_thenReturnHostIsNewServers() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServers(new Object[] {"New Servers"});
    lb.setEnablePrimingConnections(true);
    lb.addServer(new Server("42"));

    RetryRule retryRule = new RetryRule();
    retryRule.setLoadBalancer(lb);

    // Act
    Server actualChooseResult = retryRule.choose(new BaseLoadBalancer(), "Key");

    // Assert
    assertEquals("New Servers", actualChooseResult.getHost());
    assertEquals("New Servers:80", actualChooseResult.getHostPort());
    assertEquals("New Servers:80", actualChooseResult.getId());
    assertNull(actualChooseResult.getScheme());
    assertEquals(80, actualChooseResult.getPort());
    assertTrue(actualChooseResult.isAlive());
    assertTrue(actualChooseResult.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualChooseResult.getZone());
  }

  /**
   * Test {@link RetryRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   *
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link RetryRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server RetryRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_thenReturnServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    RetryRule retryRule = new RetryRule();
    retryRule.setLoadBalancer(lb);

    // Act and Assert
    assertSame(newServer, retryRule.choose(new BaseLoadBalancer(), "Key"));
  }
}
