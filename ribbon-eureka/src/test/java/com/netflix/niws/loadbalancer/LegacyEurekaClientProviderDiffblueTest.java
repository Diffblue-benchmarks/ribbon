package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LegacyEurekaClientProviderDiffblueTest {
  /**
   * Test {@link LegacyEurekaClientProvider#get()}.
   *
   * <p>Method under test: {@link LegacyEurekaClientProvider#get()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"com.netflix.discovery.EurekaClient LegacyEurekaClientProvider.get()"})
  public void testGet() {
    // Arrange, Act and Assert
    assertNull(new LegacyEurekaClientProvider().get());
  }

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
