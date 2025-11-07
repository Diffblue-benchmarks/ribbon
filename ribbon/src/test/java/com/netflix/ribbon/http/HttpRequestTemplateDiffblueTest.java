package com.netflix.ribbon.http;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.CacheProvider;
import com.netflix.ribbon.http.HttpRequestTemplate.CacheProviderWithKeyTemplate;
import com.netflix.ribbon.template.ParsedTemplate;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HttpRequestTemplateDiffblueTest {
  /**
   * Test CacheProviderWithKeyTemplate getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CacheProviderWithKeyTemplate#CacheProviderWithKeyTemplate(ParsedTemplate, CacheProvider)}
   *   <li>{@link CacheProviderWithKeyTemplate#getKeyTemplate()}
   *   <li>{@link CacheProviderWithKeyTemplate#getProvider()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CacheProviderWithKeyTemplate.<init>(ParsedTemplate, CacheProvider)",
      "ParsedTemplate CacheProviderWithKeyTemplate.getKeyTemplate()",
      "CacheProvider CacheProviderWithKeyTemplate.getProvider()"})
  public void testCacheProviderWithKeyTemplateGettersAndSetters() {
    // Arrange
    ParsedTemplate keyTemplate = ParsedTemplate.create("Template");
    CacheProvider<Object> provider = mock(CacheProvider.class);

    // Act
    CacheProviderWithKeyTemplate<Object> actualCacheProviderWithKeyTemplate = new CacheProviderWithKeyTemplate<>(
        keyTemplate, provider);
    ParsedTemplate actualKeyTemplate = actualCacheProviderWithKeyTemplate.getKeyTemplate();

    // Assert
    assertSame(keyTemplate, actualKeyTemplate);
    assertSame(provider, actualCacheProviderWithKeyTemplate.getProvider());
  }
}
