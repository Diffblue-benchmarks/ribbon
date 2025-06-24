package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.CacheProvider;
import com.netflix.ribbon.proxy.MethodTemplate.CacheProviderEntry;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MethodTemplateDiffblueTest {
  /**
   * Test CacheProviderEntry getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CacheProviderEntry#CacheProviderEntry(String, CacheProvider)}
   *   <li>{@link CacheProviderEntry#getCacheProvider()}
   *   <li>{@link CacheProviderEntry#getKey()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CacheProviderEntry.<init>(String, CacheProvider)",
      "CacheProvider CacheProviderEntry.getCacheProvider()", "String CacheProviderEntry.getKey()"})
  public void testCacheProviderEntryGettersAndSetters() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);

    // Act
    CacheProviderEntry actualCacheProviderEntry = new CacheProviderEntry("Key", cacheProvider);
    CacheProvider actualCacheProvider = actualCacheProviderEntry.getCacheProvider();

    // Assert
    assertEquals("Key", actualCacheProviderEntry.getKey());
    assertSame(cacheProvider, actualCacheProvider);
  }
}
