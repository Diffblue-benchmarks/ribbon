package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.niws.client.http.HttpClientRequest.Builder;
import com.netflix.niws.client.http.HttpClientRequest.Verb;
import java.net.URI;
import java.nio.file.Paths;
import javax.ws.rs.core.MultivaluedMap;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HttpClientRequestDiffblueTest {
  /**
   * Test Builder {@link Builder#build()}.
   *
   * <p>Method under test: {@link Builder#build()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpClientRequest Builder.build()"})
  public void testBuilderBuild() {
    // Arrange and Act
    HttpClientRequest actualHttpClientRequest = new Builder().build();

    // Assert
    assertNull(actualHttpClientRequest.getOverrideConfig());
    assertNull(actualHttpClientRequest.getLoadBalancerKey());
    assertNull(actualHttpClientRequest.getEntity());
    assertNull(actualHttpClientRequest.getUri());
    assertNull(actualHttpClientRequest.getHeaders());
    assertNull(actualHttpClientRequest.getQueryParams());
    assertEquals(Verb.GET, actualHttpClientRequest.getVerb());
    assertTrue(actualHttpClientRequest.isRetriable());
  }

  /**
   * Test Builder {@link Builder#setEntity(Object)}.
   *
   * <p>Method under test: {@link Builder#setEntity(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.setEntity(Object)"})
  public void testBuilderSetEntity() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act
    Builder actualSetEntityResult = newBuilderResult.setEntity("Entity");

    // Assert
    assertSame(newBuilderResult, actualSetEntityResult);
  }

  /**
   * Test Builder {@link Builder#setHeaders(MultivaluedMap)}.
   *
   * <p>Method under test: {@link Builder#setHeaders(MultivaluedMap)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.setHeaders(MultivaluedMap)"})
  public void testBuilderSetHeaders() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act
    Builder actualSetHeadersResult = newBuilderResult.setHeaders(null);

    // Assert
    assertSame(newBuilderResult, actualSetHeadersResult);
  }

  /**
   * Test Builder {@link Builder#setLoadBalancerKey(Object)}.
   *
   * <p>Method under test: {@link Builder#setLoadBalancerKey(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.setLoadBalancerKey(Object)"})
  public void testBuilderSetLoadBalancerKey() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act
    Builder actualSetLoadBalancerKeyResult =
        newBuilderResult.setLoadBalancerKey("Load Balancer Key");

    // Assert
    assertSame(newBuilderResult, actualSetLoadBalancerKeyResult);
  }

  /**
   * Test Builder {@link Builder#setQueryParams(MultivaluedMap)}.
   *
   * <p>Method under test: {@link Builder#setQueryParams(MultivaluedMap)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.setQueryParams(MultivaluedMap)"})
  public void testBuilderSetQueryParams() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act
    Builder actualSetQueryParamsResult = newBuilderResult.setQueryParams(null);

    // Assert
    assertSame(newBuilderResult, actualSetQueryParamsResult);
  }

  /**
   * Test Builder {@link Builder#setRetriable(boolean)}.
   *
   * <p>Method under test: {@link Builder#setRetriable(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.setRetriable(boolean)"})
  public void testBuilderSetRetriable() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act
    Builder actualSetRetriableResult = newBuilderResult.setRetriable(true);

    // Assert
    assertSame(newBuilderResult, actualSetRetriableResult);
  }

  /**
   * Test Builder {@link Builder#setUri(URI)}.
   *
   * <p>Method under test: {@link Builder#setUri(URI)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.setUri(URI)"})
  public void testBuilderSetUri() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act
    Builder actualSetUriResult =
        newBuilderResult.setUri(
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    assertSame(newBuilderResult, actualSetUriResult);
  }

  /**
   * Test Builder {@link Builder#setVerb(Verb)}.
   *
   * <p>Method under test: {@link Builder#setVerb(Verb)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.setVerb(Verb)"})
  public void testBuilderSetVerb() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act
    Builder actualSetVerbResult = newBuilderResult.setVerb(Verb.GET);

    // Assert
    assertSame(newBuilderResult, actualSetVerbResult);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link HttpClientRequest#newBuilder()}
   *   <li>{@link HttpClientRequest#getEntity()}
   *   <li>{@link HttpClientRequest#getHeaders()}
   *   <li>{@link HttpClientRequest#getQueryParams()}
   *   <li>{@link HttpClientRequest#getVerb()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object HttpClientRequest.getEntity()",
    "MultivaluedMap HttpClientRequest.getHeaders()",
    "MultivaluedMap HttpClientRequest.getQueryParams()",
    "Verb HttpClientRequest.getVerb()",
    "Builder HttpClientRequest.newBuilder()"
  })
  public void testGettersAndSetters() {
    // Arrange
    HttpClientRequest httpClientRequest = HttpClientRequest.newBuilder().build();

    // Act
    httpClientRequest.newBuilder();
    Object actualEntity = httpClientRequest.getEntity();
    MultivaluedMap<String, String> actualHeaders = httpClientRequest.getHeaders();
    MultivaluedMap<String, String> actualQueryParams = httpClientRequest.getQueryParams();

    // Assert
    assertNull(actualEntity);
    assertNull(actualHeaders);
    assertNull(actualQueryParams);
    assertEquals(Verb.GET, httpClientRequest.getVerb());
  }

  /**
   * Test {@link HttpClientRequest#isRetriable()}.
   *
   * <p>Method under test: {@link HttpClientRequest#isRetriable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean HttpClientRequest.isRetriable()"})
  public void testIsRetriable() {
    // Arrange, Act and Assert
    assertTrue(HttpClientRequest.newBuilder().build().isRetriable());
  }

  /**
   * Test {@link HttpClientRequest#replaceUri(URI)}.
   *
   * <p>Method under test: {@link HttpClientRequest#replaceUri(URI)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpClientRequest HttpClientRequest.replaceUri(URI)"})
  public void testReplaceUri() {
    // Arrange
    URI newURI = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    // Act
    HttpClientRequest actualReplaceUriResult =
        HttpClientRequest.newBuilder().build().replaceUri(newURI);

    // Assert
    assertNull(actualReplaceUriResult.getOverrideConfig());
    assertNull(actualReplaceUriResult.getLoadBalancerKey());
    assertNull(actualReplaceUriResult.getEntity());
    assertNull(actualReplaceUriResult.getHeaders());
    assertNull(actualReplaceUriResult.getQueryParams());
    assertEquals(Verb.GET, actualReplaceUriResult.getVerb());
    assertTrue(actualReplaceUriResult.isRetriable());
    assertSame(newURI, actualReplaceUriResult.getUri());
  }

  /**
   * Test Verb {@link Verb#verb()}.
   *
   * <p>Method under test: {@link Verb#verb()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Verb.verb()"})
  public void testVerbVerb() {
    // Arrange, Act and Assert
    assertEquals("GET", Verb.valueOf("GET").verb());
  }
}
