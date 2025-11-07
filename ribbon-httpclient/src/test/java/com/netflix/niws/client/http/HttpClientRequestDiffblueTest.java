package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
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
   * <p>
   * Method under test: {@link Builder#build()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HttpClientRequest Builder.build()"})
  public void testBuilderBuild() {
    // Arrange and Act
    HttpClientRequest actualBuildResult = (new Builder()).build();

    // Assert
    assertNull(actualBuildResult.getOverrideConfig());
    assertNull(actualBuildResult.getLoadBalancerKey());
    assertNull(actualBuildResult.getEntity());
    assertNull(actualBuildResult.getUri());
    assertNull(actualBuildResult.getHeaders());
    assertNull(actualBuildResult.getQueryParams());
    assertEquals(Verb.GET, actualBuildResult.getVerb());
    assertTrue(actualBuildResult.isRetriable());
  }

  /**
   * Test Builder new {@link Builder} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link Builder}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Builder.<init>()"})
  public void testBuilderNewBuilder() {
    // Arrange, Act and Assert
    HttpClientRequest buildResult = (new Builder()).build();
    assertNull(buildResult.getOverrideConfig());
    assertNull(buildResult.getLoadBalancerKey());
    assertNull(buildResult.getEntity());
    assertNull(buildResult.getUri());
    assertNull(buildResult.getHeaders());
    assertNull(buildResult.getQueryParams());
    assertEquals(Verb.GET, buildResult.getVerb());
    assertTrue(buildResult.isRetriable());
  }

  /**
   * Test Builder {@link Builder#setEntity(Object)}.
   * <p>
   * Method under test: {@link Builder#setEntity(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.setEntity(Object)"})
  public void testBuilderSetEntity() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setEntity("Entity"));
  }

  /**
   * Test Builder {@link Builder#setHeaders(MultivaluedMap)}.
   * <p>
   * Method under test: {@link Builder#setHeaders(MultivaluedMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.setHeaders(MultivaluedMap)"})
  public void testBuilderSetHeaders() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setHeaders(null));
  }

  /**
   * Test Builder {@link Builder#setLoadBalancerKey(Object)}.
   * <p>
   * Method under test: {@link Builder#setLoadBalancerKey(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.setLoadBalancerKey(Object)"})
  public void testBuilderSetLoadBalancerKey() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setLoadBalancerKey("Load Balancer Key"));
  }

  /**
   * Test Builder {@link Builder#setQueryParams(MultivaluedMap)}.
   * <p>
   * Method under test: {@link Builder#setQueryParams(MultivaluedMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.setQueryParams(MultivaluedMap)"})
  public void testBuilderSetQueryParams() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setQueryParams(null));
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
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setRetriable(true));
  }

  /**
   * Test Builder {@link Builder#setUri(URI)}.
   * <p>
   * Method under test: {@link Builder#setUri(URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.setUri(URI)"})
  public void testBuilderSetUri() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult,
        newBuilderResult.setUri(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri()));
  }

  /**
   * Test Builder {@link Builder#setVerb(Verb)}.
   * <p>
   * Method under test: {@link Builder#setVerb(Verb)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.setVerb(Verb)"})
  public void testBuilderSetVerb() {
    // Arrange
    Builder newBuilderResult = HttpClientRequest.newBuilder();

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.setVerb(Verb.GET));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HttpClientRequest#newBuilder()}
   *   <li>{@link HttpClientRequest#getEntity()}
   *   <li>{@link HttpClientRequest#getHeaders()}
   *   <li>{@link HttpClientRequest#getQueryParams()}
   *   <li>{@link HttpClientRequest#getVerb()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object HttpClientRequest.getEntity()", "MultivaluedMap HttpClientRequest.getHeaders()",
      "MultivaluedMap HttpClientRequest.getQueryParams()", "Verb HttpClientRequest.getVerb()",
      "Builder HttpClientRequest.newBuilder()"})
  public void testGettersAndSetters() {
    // Arrange
    HttpClientRequest buildResult = HttpClientRequest.newBuilder().build();

    // Act
    buildResult.newBuilder();
    Object actualEntity = buildResult.getEntity();
    MultivaluedMap<String, String> actualHeaders = buildResult.getHeaders();
    MultivaluedMap<String, String> actualQueryParams = buildResult.getQueryParams();

    // Assert
    assertNull(actualEntity);
    assertNull(actualHeaders);
    assertNull(actualQueryParams);
    assertEquals(Verb.GET, buildResult.getVerb());
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
