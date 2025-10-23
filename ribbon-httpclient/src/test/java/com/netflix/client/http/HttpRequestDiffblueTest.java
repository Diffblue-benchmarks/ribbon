package com.netflix.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.netflix.client.http.HttpRequest.Builder;
import com.netflix.client.http.HttpRequest.Verb;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class HttpRequestDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test Builder {@link Builder#build()}.
   *
   * <p>Method under test: {@link Builder#build()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HttpRequest Builder.build()",
    "Builder Builder.headers(CaseInsensitiveMultiMap)",
    "Builder Builder.queryParams(Multimap)",
    "Builder Builder.verb(Verb)"
  })
  public void testBuilderBuild() {
    // Arrange and Act
    HttpRequest actualHttpRequest = new Builder().build();

    // Assert
    HttpHeaders httpHeaders = actualHttpRequest.getHttpHeaders();
    Multimap<String, Entry<String, String>> multimap = ((CaseInsensitiveMultiMap) httpHeaders).map;
    assertTrue(multimap instanceof ArrayListMultimap);
    assertTrue(actualHttpRequest.queryParams instanceof ArrayListMultimap);
    assertTrue(httpHeaders instanceof CaseInsensitiveMultiMap);
    assertNull(actualHttpRequest.getOverrideConfig());
    assertNull(actualHttpRequest.getLoadBalancerKey());
    assertNull(actualHttpRequest.getEntity());
    assertNull(actualHttpRequest.getUri());
    assertEquals(Verb.GET, actualHttpRequest.getVerb());
    assertTrue(actualHttpRequest.isRetriable());
    assertTrue(actualHttpRequest.getHeaders().isEmpty());
    assertTrue(actualHttpRequest.getQueryParams().isEmpty());
    assertEquals(actualHttpRequest.queryParams, multimap);
  }

  /**
   * Test Builder {@link Builder#entity(Object)}.
   *
   * <p>Method under test: {@link Builder#entity(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.entity(Object)"})
  public void testBuilderEntity() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act
    Builder actualEntityResult = newBuilderResult.entity("Entity");

    // Assert
    assertSame(newBuilderResult, actualEntityResult);
  }

  /**
   * Test Builder {@link Builder#header(String, String)}.
   *
   * <p>Method under test: {@link Builder#header(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.header(String, String)"})
  public void testBuilderHeader() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act
    Builder actualHeaderResult =
        newBuilderResult.header("https://example.org/example", "https://example.org/example");

    // Assert
    assertSame(newBuilderResult, actualHeaderResult);
  }

  /**
   * Test Builder {@link Builder#loadBalancerKey(Object)}.
   *
   * <p>Method under test: {@link Builder#loadBalancerKey(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.loadBalancerKey(Object)"})
  public void testBuilderLoadBalancerKey() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act
    Builder actualLoadBalancerKeyResult = newBuilderResult.loadBalancerKey("Load Balancer Key");

    // Assert
    assertSame(newBuilderResult, actualLoadBalancerKeyResult);
  }

  /**
   * Test Builder {@link Builder#Builder()}.
   *
   * <p>Method under test: {@link Builder#Builder()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Builder.<init>()"})
  public void testBuilderNewBuilder() {
    // Arrange, Act and Assert
    HttpRequest httpRequest = new Builder().build();
    assertTrue(httpRequest.queryParams instanceof ArrayListMultimap);
    assertTrue(httpRequest.getHttpHeaders() instanceof CaseInsensitiveMultiMap);
    assertNull(httpRequest.getOverrideConfig());
    assertNull(httpRequest.getLoadBalancerKey());
    assertNull(httpRequest.getEntity());
    assertNull(httpRequest.getUri());
    assertEquals(Verb.GET, httpRequest.getVerb());
    assertTrue(httpRequest.isRetriable());
    assertTrue(httpRequest.getHeaders().isEmpty());
    assertTrue(httpRequest.getQueryParams().isEmpty());
  }

  /**
   * Test Builder {@link Builder#Builder(HttpRequest)}.
   *
   * <p>Method under test: {@link Builder#Builder(HttpRequest)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Builder.<init>(HttpRequest)"})
  public void testBuilderNewBuilder2() {
    // Arrange
    HttpRequest request = new HttpRequest();

    // Act and Assert
    HttpRequest actualHttpRequest = new Builder(request).build();
    assertSame(request, actualHttpRequest);
  }

  /**
   * Test Builder {@link Builder#queryParam(String, String)}.
   *
   * <p>Method under test: {@link Builder#queryParam(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.queryParam(String, String)"})
  public void testBuilderQueryParam() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act
    Builder actualQueryParamResult =
        newBuilderResult.queryParam("https://example.org/example", "https://example.org/example");

    // Assert
    assertSame(newBuilderResult, actualQueryParamResult);
  }

  /**
   * Test Builder {@link Builder#queryParams(String, String)} with {@code name}, {@code value}.
   *
   * <p>Method under test: {@link Builder#queryParams(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.queryParams(String, String)"})
  public void testBuilderQueryParamsWithNameValue() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act
    Builder actualQueryParamsResult =
        newBuilderResult.queryParams("https://example.org/example", "https://example.org/example");

    // Assert
    assertSame(newBuilderResult, actualQueryParamsResult);
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
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act
    Builder actualSetRetriableResult = newBuilderResult.setRetriable(true);

    // Assert
    assertSame(newBuilderResult, actualSetRetriableResult);
  }

  /**
   * Test Builder {@link Builder#uri(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code 42https://example.org/example}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#uri(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.uri(String)"})
  public void testBuilderUriWithString_when42httpsExampleOrgExample_thenThrowRuntimeException() {
    // Arrange, Act and Assert
    thrown.expect(RuntimeException.class);
    HttpRequest.newBuilder().uri("42https://example.org/example");
  }

  /**
   * Test Builder {@link Builder#uri(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code https://example.org/example}.
   *   <li>Then return newBuilder.
   * </ul>
   *
   * <p>Method under test: {@link Builder#uri(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.uri(String)"})
  public void testBuilderUriWithString_whenHttpsExampleOrgExample_thenReturnNewBuilder() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act
    Builder actualUriResult = newBuilderResult.uri("https://example.org/example");

    // Assert
    assertSame(newBuilderResult, actualUriResult);
  }

  /**
   * Test Builder {@link Builder#uri(URI)} with {@code URI}.
   *
   * <p>Method under test: {@link Builder#uri(URI)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.uri(URI)"})
  public void testBuilderUriWithUri() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act
    Builder actualUriResult =
        newBuilderResult.uri(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    assertSame(newBuilderResult, actualUriResult);
  }

  /**
   * Test {@link HttpRequest#HttpRequest()}.
   *
   * <p>Method under test: default or parameterless constructor of {@link HttpRequest}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HttpRequest.<init>()"})
  public void testNewHttpRequest() {
    // Arrange and Act
    HttpRequest actualHttpRequest = new HttpRequest();

    // Assert
    assertTrue(actualHttpRequest.queryParams instanceof ArrayListMultimap);
    assertTrue(actualHttpRequest.getHttpHeaders() instanceof CaseInsensitiveMultiMap);
    assertNull(actualHttpRequest.getOverrideConfig());
    assertNull(actualHttpRequest.getLoadBalancerKey());
    assertNull(actualHttpRequest.getEntity());
    assertNull(actualHttpRequest.getUri());
    assertEquals(Verb.GET, actualHttpRequest.getVerb());
    assertTrue(actualHttpRequest.isRetriable());
    assertTrue(actualHttpRequest.getHeaders().isEmpty());
    assertTrue(actualHttpRequest.getQueryParams().isEmpty());
  }

  /**
   * Test {@link HttpRequest#getQueryParams()}.
   *
   * <p>Method under test: {@link HttpRequest#getQueryParams()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HttpRequest.getQueryParams()"})
  public void testGetQueryParams() {
    // Arrange, Act and Assert
    assertTrue(new HttpRequest().getQueryParams().isEmpty());
  }

  /**
   * Test {@link HttpRequest#getHeaders()}.
   *
   * <p>Method under test: {@link HttpRequest#getHeaders()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HttpRequest.getHeaders()"})
  public void testGetHeaders() {
    // Arrange, Act and Assert
    assertTrue(new HttpRequest().getHeaders().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link HttpRequest#newBuilder()}
   *   <li>{@link HttpRequest#newBuilder(HttpRequest)}
   *   <li>{@link HttpRequest#getEntity()}
   *   <li>{@link HttpRequest#getHttpHeaders()}
   *   <li>{@link HttpRequest#getVerb()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object HttpRequest.getEntity()",
    "HttpHeaders HttpRequest.getHttpHeaders()",
    "Verb HttpRequest.getVerb()",
    "Builder HttpRequest.newBuilder()",
    "Builder HttpRequest.newBuilder(HttpRequest)"
  })
  public void testGettersAndSetters() {
    // Arrange
    HttpRequest httpRequest = HttpRequest.newBuilder().build();

    // Act
    httpRequest.newBuilder();
    httpRequest.newBuilder(HttpRequest.newBuilder().build());
    Object actualEntity = httpRequest.getEntity();
    HttpHeaders actualHttpHeaders = httpRequest.getHttpHeaders();

    // Assert
    Multimap<String, Entry<String, String>> multimap =
        ((CaseInsensitiveMultiMap) actualHttpHeaders).map;
    assertTrue(multimap instanceof ArrayListMultimap);
    assertTrue(actualHttpHeaders instanceof CaseInsensitiveMultiMap);
    assertNull(actualEntity);
    assertEquals(Verb.GET, httpRequest.getVerb());
    assertEquals(httpRequest.queryParams, multimap);
  }

  /**
   * Test {@link HttpRequest#isRetriable()}.
   *
   * <p>Method under test: {@link HttpRequest#isRetriable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean HttpRequest.isRetriable()"})
  public void testIsRetriable() {
    // Arrange, Act and Assert
    assertTrue(new HttpRequest().isRetriable());
  }

  /**
   * Test {@link HttpRequest#replaceUri(URI)}.
   *
   * <p>Method under test: {@link HttpRequest#replaceUri(URI)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpRequest HttpRequest.replaceUri(URI)"})
  public void testReplaceUri() {
    // Arrange
    URI newURI = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    // Act
    HttpRequest actualReplaceUriResult = new HttpRequest().replaceUri(newURI);

    // Assert
    assertTrue(actualReplaceUriResult.queryParams instanceof ArrayListMultimap);
    assertTrue(actualReplaceUriResult.getHttpHeaders() instanceof CaseInsensitiveMultiMap);
    assertNull(actualReplaceUriResult.getOverrideConfig());
    assertNull(actualReplaceUriResult.getLoadBalancerKey());
    assertNull(actualReplaceUriResult.getEntity());
    assertEquals(Verb.GET, actualReplaceUriResult.getVerb());
    assertTrue(actualReplaceUriResult.isRetriable());
    assertTrue(actualReplaceUriResult.getHeaders().isEmpty());
    assertTrue(actualReplaceUriResult.getQueryParams().isEmpty());
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
