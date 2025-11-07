package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LegacyEurekaClientProviderDiffblueTest {
  /**
   * Test {@link LegacyEurekaClientProvider#get()}.
   * <p>
   * Method under test: {@link LegacyEurekaClientProvider#get()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"com.netflix.discovery.EurekaClient LegacyEurekaClientProvider.get()"})
  public void testGet() {
    // Arrange, Act and Assert
    assertNull((new LegacyEurekaClientProvider()).get());
  }

  /**
   * Test new {@link LegacyEurekaClientProvider} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link LegacyEurekaClientProvider}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LegacyEurekaClientProvider.<init>()"})
  public void testNewLegacyEurekaClientProvider() {
    // Arrange, Act and Assert
    assertNull((new LegacyEurekaClientProvider()).get());
  }
}
