package com.netflix.ribbon.http;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.ribbon.template.TemplateParsingException;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.channel.ContentTransformer;
import org.junit.Test;
import rx.Observable;

public class HttpRequestBuilderDiffblueTest {
  /**
   * Method under test:
   * {@link HttpRequestBuilder#withRequestProperty(String, Object)}
   */
  @Test
  public void testWithRequestProperty() {
    // Arrange
    HttpRequestBuilder<Object> httpRequestBuilder = new HttpRequestBuilder<>(mock(HttpRequestTemplate.class));

    // Act and Assert
    assertSame(httpRequestBuilder, httpRequestBuilder.withRequestProperty("https://example.org/example", "Value"));
  }

  /**
   * Method under test:
   * {@link HttpRequestBuilder#withRawContentSource(Observable, ContentTransformer)}
   */
  @Test
  public void testWithRawContentSource() {
    // Arrange
    HttpRequestBuilder<Object> httpRequestBuilder = new HttpRequestBuilder<>(mock(HttpRequestTemplate.class));
    Observable<Object> raw = Observable.empty();

    // Act and Assert
    assertSame(httpRequestBuilder, httpRequestBuilder.withRawContentSource(raw, mock(ContentTransformer.class)));
  }

  /**
   * Method under test: {@link HttpRequestBuilder#withContent(Observable)}
   */
  @Test
  public void testWithContent() {
    // Arrange
    HttpRequestBuilder<Object> httpRequestBuilder = new HttpRequestBuilder<>(mock(HttpRequestTemplate.class));
    Observable<ByteBuf> content = Observable.empty();

    // Act and Assert
    assertSame(httpRequestBuilder, httpRequestBuilder.withContent(content));
  }

  /**
   * Method under test: {@link HttpRequestBuilder#withHeader(String, String)}
   */
  @Test
  public void testWithHeader() {
    // Arrange
    HttpRequestBuilder<Object> httpRequestBuilder = new HttpRequestBuilder<>(mock(HttpRequestTemplate.class));

    // Act and Assert
    assertSame(httpRequestBuilder,
        httpRequestBuilder.withHeader("https://example.org/example", "https://example.org/example"));
  }

  /**
   * Method under test: {@link HttpRequestBuilder#hystrixCacheKey()}
   */
  @Test
  public void testHystrixCacheKey() throws TemplateParsingException {
    // Arrange
    HttpRequestBuilder<Object> httpRequestBuilder = new HttpRequestBuilder<>(mock(HttpRequestTemplate.class));

    // Act and Assert
    assertNull(httpRequestBuilder.hystrixCacheKey());
  }

  /**
   * Method under test: {@link HttpRequestBuilder#requestProperties()}
   */
  @Test
  public void testRequestProperties() {
    // Arrange
    HttpRequestBuilder<Object> httpRequestBuilder = new HttpRequestBuilder<>(mock(HttpRequestTemplate.class));

    // Act and Assert
    assertTrue(httpRequestBuilder.requestProperties().isEmpty());
  }

  /**
   * Method under test: {@link HttpRequestBuilder#cacheProvider()}
   */
  @Test
  public void testCacheProvider() {
    // Arrange
    HttpRequestBuilder<Object> httpRequestBuilder = new HttpRequestBuilder<>(mock(HttpRequestTemplate.class));

    // Act and Assert
    assertNull(httpRequestBuilder.cacheProvider());
  }

  /**
   * Method under test:
   * {@link HttpRequestBuilder#HttpRequestBuilder(HttpRequestTemplate)}
   */
  @Test
  public void testNewHttpRequestBuilder() {
    // Arrange
    HttpRequestTemplate<Object> requestTemplate = mock(HttpRequestTemplate.class);

    // Act
    HttpRequestBuilder<Object> actualHttpRequestBuilder = new HttpRequestBuilder<>(requestTemplate);

    // Assert
    assertSame(requestTemplate, actualHttpRequestBuilder.template());
  }
}
