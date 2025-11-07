package com.netflix.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
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
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test Builder {@link Builder#build()}.
   * <p>
   * Method under test: {@link Builder#build()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HttpRequest Builder.build()", "Builder Builder.headers(CaseInsensitiveMultiMap)",
      "Builder Builder.queryParams(Multimap)", "Builder Builder.verb(Verb)"})
  public void testBuilderBuild() {
    // Arrange and Act
    HttpRequest actualBuildResult = (new Builder()).build();

    // Assert
    HttpHeaders httpHeaders = actualBuildResult.getHttpHeaders();
    assertTrue(((CaseInsensitiveMultiMap) httpHeaders).map instanceof ArrayListMultimap);
    assertTrue(actualBuildResult.queryParams instanceof ArrayListMultimap);
    assertTrue(httpHeaders instanceof CaseInsensitiveMultiMap);
    assertNull(actualBuildResult.getOverrideConfig());
    assertNull(actualBuildResult.getLoadBalancerKey());
    assertNull(actualBuildResult.getEntity());
    assertNull(actualBuildResult.getUri());
    assertEquals(Verb.GET, actualBuildResult.getVerb());
    assertTrue(actualBuildResult.isRetriable());
    assertTrue(actualBuildResult.getHeaders().isEmpty());
    assertTrue(actualBuildResult.getQueryParams().isEmpty());
  }

  /**
   * Test Builder {@link Builder#entity(Object)}.
   * <p>
   * Method under test: {@link Builder#entity(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.entity(Object)"})
  public void testBuilderEntity() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.entity("Entity"));
  }

  /**
   * Test Builder {@link Builder#header(String, String)}.
   * <p>
   * Method under test: {@link Builder#header(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.header(String, String)"})
  public void testBuilderHeader() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.header("https://example.org/example", "https://example.org/example"));
  }

  /**
   * Test Builder {@link Builder#loadBalancerKey(Object)}.
   * <p>
   * Method under test: {@link Builder#loadBalancerKey(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.loadBalancerKey(Object)"})
  public void testBuilderLoadBalancerKey() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.loadBalancerKey("Load Balancer Key"));
  }

  /**
   * Test Builder {@link Builder#Builder()}.
   * <p>
   * Method under test: {@link Builder#Builder()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Builder.<init>()"})
  public void testBuilderNewBuilder() {
    // Arrange, Act and Assert
    HttpRequest buildResult = (new Builder()).build();
    HttpHeaders httpHeaders = buildResult.getHttpHeaders();
    Multimap<String, Entry<String, String>> multimap = ((CaseInsensitiveMultiMap) httpHeaders).map;
    assertTrue(multimap instanceof ArrayListMultimap);
    Multimap<String, String> multimap2 = buildResult.queryParams;
    assertTrue(multimap2 instanceof ArrayListMultimap);
    assertTrue(httpHeaders instanceof CaseInsensitiveMultiMap);
    assertNull(buildResult.getOverrideConfig());
    assertNull(buildResult.getLoadBalancerKey());
    assertNull(buildResult.getEntity());
    assertNull(buildResult.getUri());
    assertEquals(Verb.GET, buildResult.getVerb());
    assertTrue(buildResult.isRetriable());
    assertTrue(httpHeaders.getAllHeaders().isEmpty());
    assertTrue(buildResult.getHeaders().isEmpty());
    assertTrue(buildResult.getQueryParams().isEmpty());
    assertEquals(multimap2, multimap);
  }

  /**
   * Test Builder {@link Builder#Builder(HttpRequest)}.
   * <p>
   * Method under test: {@link Builder#Builder(HttpRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Builder.<init>(HttpRequest)"})
  public void testBuilderNewBuilder2() {
    // Arrange
    HttpRequest request = new HttpRequest();

    // Act and Assert
    assertSame(request, (new Builder(request)).build());
  }

  /**
   * Test Builder {@link Builder#queryParam(String, String)}.
   * <p>
   * Method under test: {@link Builder#queryParam(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.queryParam(String, String)"})
  public void testBuilderQueryParam() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult,
        newBuilderResult.queryParam("https://example.org/example", "https://example.org/example"));
  }

  /**
   * Test Builder {@link Builder#queryParams(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link Builder#queryParams(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.queryParams(String, String)"})
  public void testBuilderQueryParamsWithNameValue() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult,
        newBuilderResult.queryParams("https://example.org/example", "https://example.org/example"));
  }

  /**
   * Test Builder {@link Builder#setRetriable(boolean)}.
   * <p>
   * Method under test: {@link Builder#setRetriable(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.setRetriable(boolean)"})
  public void testBuilderSetRetriable() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setRetriable(true));
  }

  /**
   * Test Builder {@link Builder#uri(String)} with {@code String}.
   * <ul>
   *   <li>When {@code 42https://example.org/example}.</li>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#uri(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.uri(String)"})
  public void testBuilderUriWithString_when42httpsExampleOrgExample_thenThrowRuntimeException() {
    // Arrange, Act and Assert
    thrown.expect(RuntimeException.class);
    HttpRequest.newBuilder().uri("42https://example.org/example");
  }

  /**
   * Test Builder {@link Builder#uri(String)} with {@code String}.
   * <ul>
   *   <li>When {@code https://example.org/example}.</li>
   *   <li>Then return newBuilder.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#uri(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.uri(String)"})
  public void testBuilderUriWithString_whenHttpsExampleOrgExample_thenReturnNewBuilder() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.uri("https://example.org/example"));
  }

  /**
   * Test Builder {@link Builder#uri(URI)} with {@code URI}.
   * <p>
   * Method under test: {@link Builder#uri(URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.uri(URI)"})
  public void testBuilderUriWithUri() {
    // Arrange
    Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult,
        newBuilderResult.uri(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri()));
  }

  /**
   * Test {@link HttpRequest#HttpRequest()}.
   * <p>
   * Method under test: default or parameterless constructor of {@link HttpRequest}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HttpRequest.<init>()"})
  public void testNewHttpRequest() {
    // Arrange and Act
    HttpRequest actualHttpRequest = new HttpRequest();

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
    assertTrue(httpHeaders.getAllHeaders().isEmpty());
    assertTrue(actualHttpRequest.getHeaders().isEmpty());
    assertTrue(actualHttpRequest.getQueryParams().isEmpty());
    assertEquals(actualHttpRequest.queryParams, multimap);
  }

  /**
   * Test {@link HttpRequest#getQueryParams()}.
   * <p>
   * Method under test: {@link HttpRequest#getQueryParams()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map HttpRequest.getQueryParams()"})
  public void testGetQueryParams() {
    // Arrange, Act and Assert
    assertTrue((new HttpRequest()).getQueryParams().isEmpty());
  }

  /**
   * Test {@link HttpRequest#getHeaders()}.
   * <p>
   * Method under test: {@link HttpRequest#getHeaders()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map HttpRequest.getHeaders()"})
  public void testGetHeaders() {
    // Arrange, Act and Assert
    assertTrue((new HttpRequest()).getHeaders().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HttpRequest#newBuilder()}
   *   <li>{@link HttpRequest#newBuilder(HttpRequest)}
   *   <li>{@link HttpRequest#getEntity()}
   *   <li>{@link HttpRequest#getHttpHeaders()}
   *   <li>{@link HttpRequest#getVerb()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object HttpRequest.getEntity()", "HttpHeaders HttpRequest.getHttpHeaders()",
      "Verb HttpRequest.getVerb()", "Builder HttpRequest.newBuilder()", "Builder HttpRequest.newBuilder(HttpRequest)"})
  public void testGettersAndSetters() {
    // Arrange
    HttpRequest buildResult = HttpRequest.newBuilder().build();

    // Act
    buildResult.newBuilder();
    HttpRequest toCopy = HttpRequest.newBuilder().build();
    buildResult.newBuilder(toCopy);
    Object actualEntity = buildResult.getEntity();
    HttpHeaders actualHttpHeaders = buildResult.getHttpHeaders();

    // Assert
    assertTrue(((CaseInsensitiveMultiMap) actualHttpHeaders).map instanceof ArrayListMultimap);
    assertTrue(actualHttpHeaders instanceof CaseInsensitiveMultiMap);
    assertNull(actualEntity);
    assertEquals(Verb.GET, buildResult.getVerb());
  }

  /**
   * Test {@link HttpRequest#isRetriable()}.
   * <p>
   * Method under test: {@link HttpRequest#isRetriable()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean HttpRequest.isRetriable()"})
  public void testIsRetriable() {
    // Arrange, Act and Assert
    assertTrue((new HttpRequest()).isRetriable());
  }

  /**
   * Test {@link HttpRequest#replaceUri(URI)}.
   * <p>
   * Method under test: {@link HttpRequest#replaceUri(URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HttpRequest HttpRequest.replaceUri(URI)"})
  public void testReplaceUri() {
    // Arrange
    URI newURI = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    // Act
    HttpRequest actualReplaceUriResult = (new HttpRequest()).replaceUri(newURI);

    // Assert
    HttpHeaders httpHeaders = actualReplaceUriResult.getHttpHeaders();
    assertTrue(((CaseInsensitiveMultiMap) httpHeaders).map instanceof ArrayListMultimap);
    assertTrue(actualReplaceUriResult.queryParams instanceof ArrayListMultimap);
    assertTrue(httpHeaders instanceof CaseInsensitiveMultiMap);
    assertNull(actualReplaceUriResult.getOverrideConfig());
    assertNull(actualReplaceUriResult.getLoadBalancerKey());
    assertNull(actualReplaceUriResult.getEntity());
    assertEquals(Verb.GET, actualReplaceUriResult.getVerb());
    assertTrue(actualReplaceUriResult.isRetriable());
    assertTrue(httpHeaders.getAllHeaders().isEmpty());
    assertTrue(actualReplaceUriResult.getHeaders().isEmpty());
    assertTrue(actualReplaceUriResult.getQueryParams().isEmpty());
    assertSame(newURI, actualReplaceUriResult.getUri());
  }

  /**
   * Test Verb {@link Verb#verb()}.
   * <p>
   * Method under test: {@link Verb#verb()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String Verb.verb()"})
  public void testVerbVerb() {
    // Arrange, Act and Assert
    assertEquals("GET", Verb.valueOf("GET").verb());
  }
}
