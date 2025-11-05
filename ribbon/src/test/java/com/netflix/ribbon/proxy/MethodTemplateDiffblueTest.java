package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.netflix.ribbon.CacheProvider;
import org.junit.Test;

public class MethodTemplateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MethodTemplate.CacheProviderEntry#CacheProviderEntry(String, CacheProvider)}
   *   <li>{@link MethodTemplate.CacheProviderEntry#getCacheProvider()}
   *   <li>{@link MethodTemplate.CacheProviderEntry#getKey()}
   * </ul>
   */
  @Test
  public void testCacheProviderEntryGettersAndSetters() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);

    // Act
    MethodTemplate.CacheProviderEntry actualCacheProviderEntry = new MethodTemplate.CacheProviderEntry("Key",
        cacheProvider);
    CacheProvider actualCacheProvider = actualCacheProviderEntry.getCacheProvider();

    // Assert
    assertEquals("Key", actualCacheProviderEntry.getKey());
    assertSame(cacheProvider, actualCacheProvider);
  }
}
