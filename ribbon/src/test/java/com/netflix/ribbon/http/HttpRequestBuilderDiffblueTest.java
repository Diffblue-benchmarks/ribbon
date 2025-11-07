package com.netflix.ribbon.http;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HttpRequestBuilderDiffblueTest {
  /**
   * Test {@link HttpRequestBuilder#HttpRequestBuilder(HttpRequestTemplate)}.
   * <ul>
   *   <li>Then return template is {@link HttpRequestTemplate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HttpRequestBuilder#HttpRequestBuilder(HttpRequestTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HttpRequestBuilder.<init>(HttpRequestTemplate)"})
  public void testNewHttpRequestBuilder_thenReturnTemplateIsHttpRequestTemplate() {
    // Arrange
    HttpRequestTemplate<Object> requestTemplate = mock(HttpRequestTemplate.class);

    // Act
    HttpRequestBuilder<Object> actualHttpRequestBuilder = new HttpRequestBuilder<>(requestTemplate);

    // Assert
    assertSame(requestTemplate, actualHttpRequestBuilder.template());
  }
}
