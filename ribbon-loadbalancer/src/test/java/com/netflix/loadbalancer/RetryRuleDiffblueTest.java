package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RetryRuleDiffblueTest {
  /**
   * Test {@link RetryRule#RetryRule()}.
   * <p>
   * Method under test: {@link RetryRule#RetryRule()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Then return Rule is {@link AvailabilityFilteringRule} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link RetryRule#RetryRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RetryRule.<init>(IRule)"})
  public void testNewRetryRule_thenReturnRuleIsAvailabilityFilteringRule() {
    // Arrange
    AvailabilityFilteringRule subRule = new AvailabilityFilteringRule();

    // Act and Assert
    assertSame(subRule, (new RetryRule(subRule)).getRule());
  }

  /**
   * Test {@link RetryRule#RetryRule(IRule, long)}.
   * <ul>
   *   <li>When {@link AvailabilityFilteringRule} (default constructor).</li>
   *   <li>Then return MaxRetryMillis is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link RetryRule#RetryRule(IRule, long)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RetryRule#RetryRule(IRule)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then Rule return {@link RoundRobinRule}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RetryRule#RetryRule(IRule, long)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RetryRule#getMaxRetryMillis()}
   *   <li>{@link RetryRule#getRule()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
}
