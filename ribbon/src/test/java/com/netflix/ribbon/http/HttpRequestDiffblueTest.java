package com.netflix.ribbon.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.CacheProvider;
import com.netflix.ribbon.http.HttpRequest.CacheProviderWithKey;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HttpRequestDiffblueTest {
  /**
   * Test CacheProviderWithKey getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CacheProviderWithKey#CacheProviderWithKey(CacheProvider, String)}
   *   <li>{@link CacheProviderWithKey#getCacheProvider()}
   *   <li>{@link CacheProviderWithKey#getKey()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CacheProviderWithKey.<init>(CacheProvider, String)",
      "CacheProvider CacheProviderWithKey.getCacheProvider()", "String CacheProviderWithKey.getKey()"})
  public void testCacheProviderWithKeyGettersAndSetters() {
    // Arrange
    CacheProvider<Object> cacheProvider = mock(CacheProvider.class);

    // Act
    CacheProviderWithKey<Object> actualCacheProviderWithKey = new CacheProviderWithKey<>(cacheProvider,
        "https://example.org/example");
    CacheProvider<Object> actualCacheProvider = actualCacheProviderWithKey.getCacheProvider();

    // Assert
    assertEquals("https://example.org/example", actualCacheProviderWithKey.getKey());
    assertSame(cacheProvider, actualCacheProvider);
  }
}
