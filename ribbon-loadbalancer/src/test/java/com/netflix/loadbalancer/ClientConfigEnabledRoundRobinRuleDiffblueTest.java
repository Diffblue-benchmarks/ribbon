package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClientConfigEnabledRoundRobinRuleDiffblueTest {
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
