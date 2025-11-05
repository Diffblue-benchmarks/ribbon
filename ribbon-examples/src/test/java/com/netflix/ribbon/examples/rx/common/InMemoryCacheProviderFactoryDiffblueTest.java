package com.netflix.ribbon.examples.rx.common;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.ribbon.CacheProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;
import rx.Observable;

public class InMemoryCacheProviderFactoryDiffblueTest {
  /**
   * Method under test: {@link InMemoryCacheProviderFactory#createCacheProvider()}
   */
  @Test
  public void testCreateCacheProvider() {
    // Arrange and Act
    CacheProvider<Movie> actualCreateCacheProviderResult = (new InMemoryCacheProviderFactory()).createCacheProvider();
    Observable<Movie> actualGetResult = actualCreateCacheProviderResult.get("foo", new HashMap<>());

    // Assert
    assertTrue(actualCreateCacheProviderResult instanceof InMemoryCacheProviderFactory.InMemoryCacheProvider);
    assertNull(actualCreateCacheProviderResult.get("Key", null));
    assertNull(actualGetResult);
  }

  /**
   * Method under test: {@link InMemoryCacheProviderFactory#createCacheProvider()}
   */
  @Test
  public void testCreateCacheProvider2() {
    // Arrange and Act
    CacheProvider<Movie> actualCreateCacheProviderResult = (new InMemoryCacheProviderFactory()).createCacheProvider();
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.computeIfPresent("foo", mock(BiFunction.class));
    Observable<Movie> actualGetResult = actualCreateCacheProviderResult.get("foo", stringObjectMap);

    // Assert
    assertTrue(actualCreateCacheProviderResult instanceof InMemoryCacheProviderFactory.InMemoryCacheProvider);
    assertNull(actualCreateCacheProviderResult.get("Key", null));
    assertNull(actualGetResult);
  }

  /**
   * Method under test:
   * {@link InMemoryCacheProviderFactory.InMemoryCacheProvider#get(String, Map)}
   */
  @Test
  public void testInMemoryCacheProviderGet() {
    // Arrange
    InMemoryCacheProviderFactory.InMemoryCacheProvider inMemoryCacheProvider = new InMemoryCacheProviderFactory.InMemoryCacheProvider();

    // Act and Assert
    assertNull(inMemoryCacheProvider.get("Key", new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link InMemoryCacheProviderFactory.InMemoryCacheProvider#get(String, Map)}
   */
  @Test
  public void testInMemoryCacheProviderGet2() {
    // Arrange
    InMemoryCacheProviderFactory.InMemoryCacheProvider inMemoryCacheProvider = new InMemoryCacheProviderFactory.InMemoryCacheProvider();

    HashMap<String, Object> requestProperties = new HashMap<>();
    requestProperties.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertNull(inMemoryCacheProvider.get("Key", requestProperties));
  }
}
