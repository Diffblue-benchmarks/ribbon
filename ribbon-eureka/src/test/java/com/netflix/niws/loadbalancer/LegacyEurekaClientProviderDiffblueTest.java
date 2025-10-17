package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LegacyEurekaClientProviderDiffblueTest {
  /**
   * Test new {@link LegacyEurekaClientProvider} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * LegacyEurekaClientProvider}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LegacyEurekaClientProvider.<init>()"})
  public void testNewLegacyEurekaClientProvider() {
    // Arrange, Act and Assert
    assertNull(new LegacyEurekaClientProvider().get());
  }
}
