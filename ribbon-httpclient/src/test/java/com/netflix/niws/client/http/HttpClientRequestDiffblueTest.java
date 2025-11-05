package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.net.URI;
import java.nio.file.Paths;
import javax.ws.rs.core.MultivaluedMap;
import org.junit.Test;

public class HttpClientRequestDiffblueTest {
  /**
   * Method under test: {@link HttpClientRequest.Builder#build()}
   */
  @Test
  public void testBuilderBuild() {
    // Arrange and Act
    HttpClientRequest actualBuildResult = (new HttpClientRequest.Builder()).build();

    // Assert
    assertNull(actualBuildResult.getOverrideConfig());
    assertNull(actualBuildResult.getLoadBalancerKey());
    assertNull(actualBuildResult.getEntity());
    assertNull(actualBuildResult.getUri());
    assertNull(actualBuildResult.getHeaders());
    assertNull(actualBuildResult.getQueryParams());
    assertEquals(HttpClientRequest.Verb.GET, actualBuildResult.getVerb());
    assertTrue(actualBuildResult.isRetriable());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link HttpClientRequest.Builder}
   */
  @Test
  public void testBuilderNewBuilder() {
    // Arrange, Act and Assert
    HttpClientRequest buildResult = (new HttpClientRequest.Builder()).build();
    assertNull(buildResult.getOverrideConfig());
    assertNull(buildResult.getLoadBalancerKey());
    assertNull(buildResult.getEntity());
    assertNull(buildResult.getUri());
    assertNull(buildResult.getHeaders());
    assertNull(buildResult.getQueryParams());
    assertEquals(HttpClientRequest.Verb.GET, buildResult.getVerb());
    assertTrue(buildResult.isRetriable());
  }

  /**
   * Method under test: {@link HttpClientRequest.Builder#setEntity(Object)}
   */
  @Test
  public void testBuilderSetEntity() {
    // Arrange
    HttpClientRequest.Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setEntity("Entity"));
  }

  /**
   * Method under test:
   * {@link HttpClientRequest.Builder#setHeaders(MultivaluedMap)}
   */
  @Test
  public void testBuilderSetHeaders() {
    // Arrange
    HttpClientRequest.Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setHeaders(null));
  }

  /**
   * Method under test:
   * {@link HttpClientRequest.Builder#setLoadBalancerKey(Object)}
   */
  @Test
  public void testBuilderSetLoadBalancerKey() {
    // Arrange
    HttpClientRequest.Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setLoadBalancerKey("Load Balancer Key"));
  }

  /**
   * Method under test:
   * {@link HttpClientRequest.Builder#setOverrideConfig(IClientConfig)}
   */
  @Test
  public void testBuilderSetOverrideConfig() {
    // Arrange
    HttpClientRequest.Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setOverrideConfig(DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Method under test:
   * {@link HttpClientRequest.Builder#setOverrideConfig(IClientConfig)}
   */
  @Test
  public void testBuilderSetOverrideConfig2() {
    // Arrange
    HttpClientRequest.Builder newBuilderResult = HttpClientRequest.newBuilder();
    DefaultClientConfigImpl config = DefaultClientConfigImpl.getEmptyConfig();
    config.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setOverrideConfig(config));
  }

  /**
   * Method under test:
   * {@link HttpClientRequest.Builder#setQueryParams(MultivaluedMap)}
   */
  @Test
  public void testBuilderSetQueryParams() {
    // Arrange
    HttpClientRequest.Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setQueryParams(null));
  }

  /**
   * Method under test: {@link HttpClientRequest.Builder#setRetriable(boolean)}
   */
  @Test
  public void testBuilderSetRetriable() {
    // Arrange
    HttpClientRequest.Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setRetriable(true));
  }

  /**
   * Method under test: {@link HttpClientRequest.Builder#setUri(URI)}
   */
  @Test
  public void testBuilderSetUri() {
    // Arrange
    HttpClientRequest.Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult,
        newBuilderResult.setUri(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri()));
  }

  /**
   * Method under test:
   * {@link HttpClientRequest.Builder#setVerb(HttpClientRequest.Verb)}
   */
  @Test
  public void testBuilderSetVerb() {
    // Arrange
    HttpClientRequest.Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setVerb(HttpClientRequest.Verb.GET));
  }

  /**
   * Method under test: {@link HttpClientRequest.Verb#verb()}
   */
  @Test
  public void testVerbVerb() {
    // Arrange, Act and Assert
    assertEquals("GET", HttpClientRequest.Verb.valueOf("GET").verb());
  }
}
