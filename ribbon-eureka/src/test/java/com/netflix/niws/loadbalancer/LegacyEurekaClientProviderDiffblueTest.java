package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertNull;
import org.junit.Test;

public class LegacyEurekaClientProviderDiffblueTest {
  /**
   * Method under test: {@link LegacyEurekaClientProvider#get()}
   */
  @Test
  public void testGet() {
    // Arrange, Act and Assert
    assertNull((new LegacyEurekaClientProvider()).get());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link LegacyEurekaClientProvider}
   */
  @Test
  public void testNewLegacyEurekaClientProvider() {
    // Arrange, Act and Assert
    assertNull((new LegacyEurekaClientProvider()).get());
  }
}
