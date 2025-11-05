package com.netflix.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HttpRequestDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test: {@link HttpRequest.Builder#build()}
   */
  @Test
  public void testBuilderBuild() {
    // Arrange and Act
    HttpRequest actualBuildResult = (new HttpRequest.Builder()).build();

    // Assert
    HttpHeaders httpHeaders = actualBuildResult.getHttpHeaders();
    assertTrue(((CaseInsensitiveMultiMap) httpHeaders).map instanceof ArrayListMultimap);
    assertTrue(actualBuildResult.queryParams instanceof ArrayListMultimap);
    assertTrue(httpHeaders instanceof CaseInsensitiveMultiMap);
    assertNull(actualBuildResult.getOverrideConfig());
    assertNull(actualBuildResult.getLoadBalancerKey());
    assertNull(actualBuildResult.getEntity());
    assertNull(actualBuildResult.getUri());
    assertEquals(HttpRequest.Verb.GET, actualBuildResult.getVerb());
    assertTrue(actualBuildResult.isRetriable());
    assertTrue(actualBuildResult.getHeaders().isEmpty());
    assertTrue(actualBuildResult.getQueryParams().isEmpty());
  }

  /**
   * Method under test: {@link HttpRequest.Builder#entity(Object)}
   */
  @Test
  public void testBuilderEntity() {
    // Arrange
    HttpRequest.Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.entity("Entity"));
  }

  /**
   * Method under test: {@link HttpRequest.Builder#header(String, String)}
   */
  @Test
  public void testBuilderHeader() {
    // Arrange
    HttpRequest.Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.header("https://example.org/example", "https://example.org/example"));
  }

  /**
   * Method under test: {@link HttpRequest.Builder#loadBalancerKey(Object)}
   */
  @Test
  public void testBuilderLoadBalancerKey() {
    // Arrange
    HttpRequest.Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.loadBalancerKey("Load Balancer Key"));
  }

  /**
   * Method under test: {@link HttpRequest.Builder#Builder()}
   */
  @Test
  public void testBuilderNewBuilder() {
    // Arrange, Act and Assert
    HttpRequest buildResult = (new HttpRequest.Builder()).build();
    HttpHeaders httpHeaders = buildResult.getHttpHeaders();
    Multimap<String, Map.Entry<String, String>> multimap = ((CaseInsensitiveMultiMap) httpHeaders).map;
    assertTrue(multimap instanceof ArrayListMultimap);
    Multimap<String, String> multimap2 = buildResult.queryParams;
    assertTrue(multimap2 instanceof ArrayListMultimap);
    assertTrue(httpHeaders instanceof CaseInsensitiveMultiMap);
    assertNull(buildResult.getOverrideConfig());
    assertNull(buildResult.getLoadBalancerKey());
    assertNull(buildResult.getEntity());
    assertNull(buildResult.getUri());
    assertEquals(HttpRequest.Verb.GET, buildResult.getVerb());
    assertTrue(buildResult.isRetriable());
    assertTrue(httpHeaders.getAllHeaders().isEmpty());
    assertTrue(buildResult.getHeaders().isEmpty());
    assertTrue(buildResult.getQueryParams().isEmpty());
    assertEquals(multimap2, multimap);
  }

  /**
   * Method under test: {@link HttpRequest.Builder#Builder(HttpRequest)}
   */
  @Test
  public void testBuilderNewBuilder2() {
    // Arrange
    HttpRequest request = new HttpRequest();

    // Act and Assert
    assertSame(request, (new HttpRequest.Builder(request)).build());
  }

  /**
   * Method under test: {@link HttpRequest.Builder#overrideConfig(IClientConfig)}
   */
  @Test
  public void testBuilderOverrideConfig() {
    // Arrange
    HttpRequest.Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.overrideConfig(DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Method under test: {@link HttpRequest.Builder#overrideConfig(IClientConfig)}
   */
  @Test
  public void testBuilderOverrideConfig2() {
    // Arrange
    HttpRequest.Builder newBuilderResult = HttpRequest.newBuilder();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.overrideConfig(config));
  }

  /**
   * Method under test: {@link HttpRequest.Builder#queryParam(String, String)}
   */
  @Test
  public void testBuilderQueryParam() {
    // Arrange
    HttpRequest.Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult,
        newBuilderResult.queryParam("https://example.org/example", "https://example.org/example"));
  }

  /**
   * Method under test: {@link HttpRequest.Builder#queryParams(String, String)}
   */
  @Test
  public void testBuilderQueryParams() {
    // Arrange
    HttpRequest.Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult,
        newBuilderResult.queryParams("https://example.org/example", "https://example.org/example"));
  }

  /**
   * Method under test: {@link HttpRequest.Builder#setRetriable(boolean)}
   */
  @Test
  public void testBuilderSetRetriable() {
    // Arrange
    HttpRequest.Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setRetriable(true));
  }

  /**
   * Method under test: {@link HttpRequest.Builder#uri(String)}
   */
  @Test
  public void testBuilderUri() {
    // Arrange
    HttpRequest.Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.uri("https://example.org/example"));
  }

  /**
   * Method under test: {@link HttpRequest.Builder#uri(String)}
   */
  @Test
  public void testBuilderUri2() {
    // Arrange, Act and Assert
    thrown.expect(RuntimeException.class);
    HttpRequest.newBuilder().uri("42https://example.org/example");
  }

  /**
   * Method under test: {@link HttpRequest.Builder#uri(URI)}
   */
  @Test
  public void testBuilderUri3() {
    // Arrange
    HttpRequest.Builder newBuilderResult = HttpRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult,
        newBuilderResult.uri(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri()));
  }

  /**
   * Method under test: {@link HttpRequest#getQueryParams()}
   */
  @Test
  public void testGetQueryParams() {
    // Arrange, Act and Assert
    assertTrue((new HttpRequest()).getQueryParams().isEmpty());
  }

  /**
   * Method under test: {@link HttpRequest#getHeaders()}
   */
  @Test
  public void testGetHeaders() {
    // Arrange, Act and Assert
    assertTrue((new HttpRequest()).getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link HttpRequest#isRetriable()}
   */
  @Test
  public void testIsRetriable() {
    // Arrange, Act and Assert
    assertTrue((new HttpRequest()).isRetriable());
  }

  /**
   * Method under test: {@link HttpRequest#replaceUri(URI)}
   */
  @Test
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
    assertEquals(HttpRequest.Verb.GET, actualReplaceUriResult.getVerb());
    assertTrue(actualReplaceUriResult.isRetriable());
    assertTrue(httpHeaders.getAllHeaders().isEmpty());
    assertTrue(actualReplaceUriResult.getHeaders().isEmpty());
    assertTrue(actualReplaceUriResult.getQueryParams().isEmpty());
    String expectedToStringResult = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString();
    URI uri = actualReplaceUriResult.getUri();
    assertEquals(expectedToStringResult, uri.toString());
    assertSame(newURI, uri);
  }

  /**
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
  public void testGettersAndSetters() {
    // Arrange
    HttpRequest httpRequest = new HttpRequest();

    // Act
    httpRequest.newBuilder();
    httpRequest.newBuilder(new HttpRequest());
    Object actualEntity = httpRequest.getEntity();
    HttpHeaders actualHttpHeaders = httpRequest.getHttpHeaders();

    // Assert
    assertTrue(((CaseInsensitiveMultiMap) actualHttpHeaders).map instanceof ArrayListMultimap);
    assertTrue(actualHttpHeaders instanceof CaseInsensitiveMultiMap);
    assertNull(actualEntity);
    assertEquals(HttpRequest.Verb.GET, httpRequest.getVerb());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link HttpRequest}
   */
  @Test
  public void testNewHttpRequest() {
    // Arrange and Act
    HttpRequest actualHttpRequest = new HttpRequest();

    // Assert
    HttpHeaders httpHeaders = actualHttpRequest.getHttpHeaders();
    Multimap<String, Map.Entry<String, String>> multimap = ((CaseInsensitiveMultiMap) httpHeaders).map;
    assertTrue(multimap instanceof ArrayListMultimap);
    assertTrue(actualHttpRequest.queryParams instanceof ArrayListMultimap);
    assertTrue(httpHeaders instanceof CaseInsensitiveMultiMap);
    assertNull(actualHttpRequest.getOverrideConfig());
    assertNull(actualHttpRequest.getLoadBalancerKey());
    assertNull(actualHttpRequest.getEntity());
    assertNull(actualHttpRequest.getUri());
    assertEquals(HttpRequest.Verb.GET, actualHttpRequest.getVerb());
    assertTrue(actualHttpRequest.isRetriable());
    assertTrue(httpHeaders.getAllHeaders().isEmpty());
    assertTrue(actualHttpRequest.getHeaders().isEmpty());
    assertTrue(actualHttpRequest.getQueryParams().isEmpty());
    assertEquals(actualHttpRequest.queryParams, multimap);
  }

  /**
   * Method under test: {@link HttpRequest.Verb#verb()}
   */
  @Test
  public void testVerbVerb() {
    // Arrange, Act and Assert
    assertEquals("GET", HttpRequest.Verb.valueOf("GET").verb());
  }
}
