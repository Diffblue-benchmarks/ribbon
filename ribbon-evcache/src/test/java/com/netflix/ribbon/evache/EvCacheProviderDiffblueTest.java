package com.netflix.ribbon.evache;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.evache.EvCacheProvider.FutureObserver;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EvCacheProviderDiffblueTest {
  /**
   * Test FutureObserver new {@link FutureObserver} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link FutureObserver}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FutureObserver.<init>()"})
  public void testFutureObserverNewFutureObserver() {
    // Arrange and Act
    FutureObserver actualFutureObserver = new FutureObserver();

    // Assert
    assertEquals("EvCache-Future-Observer", actualFutureObserver.getName());
    assertEquals(5, actualFutureObserver.getPriority());
  }
}
