package com.netflix.ribbon.examples.rx.common;

import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.ServerError;
import com.netflix.ribbon.UnsuccessfulResponseException;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class RecommendationServiceResponseValidatorDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link RecommendationServiceResponseValidator#validate(HttpClientResponse)} with {@code HttpClientResponse}.
   * <ul>
   *   <li>Then throw {@link UnsuccessfulResponseException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RecommendationServiceResponseValidator#validate(HttpClientResponse)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RecommendationServiceResponseValidator.validate(HttpClientResponse)"})
  public void testValidateWithHttpClientResponse_thenThrowUnsuccessfulResponseException()
      throws ServerError, UnsuccessfulResponseException {
    // Arrange
    RecommendationServiceResponseValidator recommendationServiceResponseValidator = new RecommendationServiceResponseValidator();
    HttpVersion version = new HttpVersion("https://example.org/example", 1, 1, true);

    // Act and Assert
    thrown.expect(UnsuccessfulResponseException.class);
    recommendationServiceResponseValidator
        .validate(new HttpClientResponse<>(new DefaultFullHttpResponse(version, HttpResponseStatus.valueOf(1)), null));
  }
}
