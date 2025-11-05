package com.netflix.ribbon.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.netflix.ribbon.CacheProvider;
import org.junit.Test;

public class HttpRequestDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HttpRequest.CacheProviderWithKey#CacheProviderWithKey(CacheProvider, String)}
   *   <li>{@link HttpRequest.CacheProviderWithKey#getCacheProvider()}
   *   <li>{@link HttpRequest.CacheProviderWithKey#getKey()}
   * </ul>
   */
  @Test
  public void testCacheProviderWithKeyGettersAndSetters() {
    // Arrange
    CacheProvider<Object> cacheProvider = mock(CacheProvider.class);

    // Act
    HttpRequest.CacheProviderWithKey<Object> actualCacheProviderWithKey = new HttpRequest.CacheProviderWithKey<>(
        cacheProvider, "https://example.org/example");
    CacheProvider<Object> actualCacheProvider = actualCacheProviderWithKey.getCacheProvider();

    // Assert
    assertEquals("https://example.org/example", actualCacheProviderWithKey.getKey());
    assertSame(cacheProvider, actualCacheProvider);
  }
}
