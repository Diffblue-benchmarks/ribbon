package com.netflix.ribbon.http;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.netflix.ribbon.CacheProvider;
import com.netflix.ribbon.template.ParsedTemplate;
import org.junit.Test;

public class HttpRequestTemplateDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HttpRequestTemplate.CacheProviderWithKeyTemplate#CacheProviderWithKeyTemplate(ParsedTemplate, CacheProvider)}
   *   <li>{@link HttpRequestTemplate.CacheProviderWithKeyTemplate#getKeyTemplate()}
   *   <li>{@link HttpRequestTemplate.CacheProviderWithKeyTemplate#getProvider()}
   * </ul>
   */
  @Test
  public void testCacheProviderWithKeyTemplateGettersAndSetters() {
    // Arrange
    ParsedTemplate keyTemplate = ParsedTemplate.create("Template");
    CacheProvider<Object> provider = mock(CacheProvider.class);

    // Act
    HttpRequestTemplate.CacheProviderWithKeyTemplate<Object> actualCacheProviderWithKeyTemplate = new HttpRequestTemplate.CacheProviderWithKeyTemplate<>(
        keyTemplate, provider);
    ParsedTemplate actualKeyTemplate = actualCacheProviderWithKeyTemplate.getKeyTemplate();

    // Assert
    assertSame(keyTemplate, actualKeyTemplate);
    assertSame(provider, actualCacheProviderWithKeyTemplate.getProvider());
  }
}
