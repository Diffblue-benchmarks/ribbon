package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class RetryRuleDiffblueTest {
  /**
   * Method under test: {@link RetryRule#setRule(IRule)}
   */
  @Test
  public void testSetRule() {
    // Arrange
    RetryRule retryRule = new RetryRule();
    AvailabilityFilteringRule subRule = new AvailabilityFilteringRule();

    // Act
    retryRule.setRule(subRule);

    // Assert
    assertSame(subRule, retryRule.getRule());
  }

  /**
   * Method under test: {@link RetryRule#setRule(IRule)}
   */
  @Test
  public void testSetRule2() {
    // Arrange
    RetryRule retryRule = new RetryRule();

    // Act
    retryRule.setRule(null);

    // Assert
    assertTrue(retryRule.getRule() instanceof RoundRobinRule);
  }

  /**
   * Method under test: {@link RetryRule#setMaxRetryMillis(long)}
   */
  @Test
  public void testSetMaxRetryMillis() {
    // Arrange
    RetryRule retryRule = new RetryRule();

    // Act
    retryRule.setMaxRetryMillis(1L);

    // Assert
    assertEquals(1L, retryRule.getMaxRetryMillis());
  }

  /**
   * Method under test: {@link RetryRule#setMaxRetryMillis(long)}
   */
  @Test
  public void testSetMaxRetryMillis2() {
    // Arrange
    RetryRule retryRule = new RetryRule();

    // Act
    retryRule.setMaxRetryMillis(0L);

    // Assert
    assertEquals(500L, retryRule.getMaxRetryMillis());
  }

  /**
   * Method under test: {@link RetryRule#setMaxRetryMillis(long)}
   */
  @Test
  public void testSetMaxRetryMillis3() {
    // Arrange
    RetryRule retryRule = new RetryRule();
    IPing ping = mock(IPing.class);
    retryRule.setLoadBalancer(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()));

    // Act
    retryRule.setMaxRetryMillis(1L);

    // Assert
    assertEquals(1L, retryRule.getMaxRetryMillis());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RetryRule#getMaxRetryMillis()}
   *   <li>{@link RetryRule#getRule()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link RetryRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer() {
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
   * Method under test: {@link RetryRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  public void testSetLoadBalancer2() {
    // Arrange
    RetryRule retryRule = new RetryRule();
    IPing ping = mock(IPing.class);
    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());

    // Act
    retryRule.setLoadBalancer(lb);

    // Assert
    IRule rule = retryRule.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertSame(lb, retryRule.getLoadBalancer());
    assertSame(lb, rule.getLoadBalancer());
  }

  /**
   * Method under test: {@link RetryRule#RetryRule()}
   */
  @Test
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
   * Method under test: {@link RetryRule#RetryRule(IRule)}
   */
  @Test
  public void testNewRetryRule2() {
    // Arrange
    AvailabilityFilteringRule subRule = new AvailabilityFilteringRule();

    // Act
    RetryRule actualRetryRule = new RetryRule(subRule);

    // Assert
    assertNull(actualRetryRule.getLoadBalancer());
    assertEquals(500L, actualRetryRule.getMaxRetryMillis());
    assertSame(subRule, actualRetryRule.getRule());
  }

  /**
   * Method under test: {@link RetryRule#RetryRule(IRule)}
   */
  @Test
  public void testNewRetryRule3() {
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
   * Method under test: {@link RetryRule#RetryRule(IRule, long)}
   */
  @Test
  public void testNewRetryRule4() {
    // Arrange
    AvailabilityFilteringRule subRule = new AvailabilityFilteringRule();

    // Act
    RetryRule actualRetryRule = new RetryRule(subRule, 1L);

    // Assert
    assertNull(actualRetryRule.getLoadBalancer());
    assertEquals(1L, actualRetryRule.getMaxRetryMillis());
    assertSame(subRule, actualRetryRule.getRule());
  }

  /**
   * Method under test: {@link RetryRule#RetryRule(IRule, long)}
   */
  @Test
  public void testNewRetryRule5() {
    // Arrange and Act
    RetryRule actualRetryRule = new RetryRule(null, 0L);

    // Assert
    IRule rule = actualRetryRule.getRule();
    assertTrue(rule instanceof RoundRobinRule);
    assertNull(actualRetryRule.getLoadBalancer());
    assertNull(rule.getLoadBalancer());
    assertEquals(500L, actualRetryRule.getMaxRetryMillis());
  }
}
