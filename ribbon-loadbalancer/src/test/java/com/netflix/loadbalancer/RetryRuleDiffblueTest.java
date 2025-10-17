package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
}
