package com.netflix.ribbon.examples.rx.common;

import com.netflix.ribbon.ServerError;
import com.netflix.ribbon.UnsuccessfulResponseException;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RecommendationServiceResponseValidatorDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test:
   * {@link RecommendationServiceResponseValidator#validate(HttpClientResponse)}
   */
  @Test
  public void testValidate() throws ServerError, UnsuccessfulResponseException {
    // Arrange
    RecommendationServiceResponseValidator recommendationServiceResponseValidator = new RecommendationServiceResponseValidator();
    HttpVersion version = new HttpVersion("https://example.org/example", 1, 1, true);

    // Act and Assert
    thrown.expect(UnsuccessfulResponseException.class);
    recommendationServiceResponseValidator
        .validate(new HttpClientResponse<>(new DefaultFullHttpResponse(version, HttpResponseStatus.valueOf(1)), null));
  }
}
