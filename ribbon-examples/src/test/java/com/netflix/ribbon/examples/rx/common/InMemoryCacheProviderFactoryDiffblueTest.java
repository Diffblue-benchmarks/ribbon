package com.netflix.ribbon.examples.rx.common;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.CacheProvider;
import com.netflix.ribbon.examples.rx.common.InMemoryCacheProviderFactory.InMemoryCacheProvider;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import rx.Observable;

public class InMemoryCacheProviderFactoryDiffblueTest {
  /**
   * Test {@link InMemoryCacheProviderFactory#createCacheProvider()}.
   * <p>
   * Method under test: {@link InMemoryCacheProviderFactory#createCacheProvider()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CacheProvider InMemoryCacheProviderFactory.createCacheProvider()"})
  public void testCreateCacheProvider() {
    // Arrange and Act
    CacheProvider<Movie> actualCreateCacheProviderResult = (new InMemoryCacheProviderFactory()).createCacheProvider();
    Observable<Movie> actualGetResult = actualCreateCacheProviderResult.get("foo", new HashMap<>());

    // Assert
    assertTrue(actualCreateCacheProviderResult instanceof InMemoryCacheProvider);
    assertNull(actualCreateCacheProviderResult.get("Key", null));
    assertNull(actualGetResult);
  }

  /**
   * Test InMemoryCacheProvider {@link InMemoryCacheProvider#get(String, Map)}.
   * <p>
   * Method under test: {@link InMemoryCacheProvider#get(String, Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Observable InMemoryCacheProvider.get(String, Map)"})
  public void testInMemoryCacheProviderGet() {
    // Arrange
    InMemoryCacheProvider inMemoryCacheProvider = new InMemoryCacheProvider();

    // Act and Assert
    assertNull(inMemoryCacheProvider.get("Key", new HashMap<>()));
  }
}
