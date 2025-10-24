package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DummyPingDiffblueTest {
  /**
   * Test new {@link DummyPing} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link DummyPing}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DummyPing.<init>()"})
  public void testNewDummyPing() {
    // Arrange, Act and Assert
    assertNull(new DummyPing().getLoadBalancer());
  }
}
