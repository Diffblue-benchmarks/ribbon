package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import java.net.URI;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClientRequestDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClientRequest#ClientRequest()}
   *   <li>{@link ClientRequest#setLoadBalancerKey(Object)}
   *   <li>{@link ClientRequest#setOverrideConfig(IClientConfig)}
   *   <li>{@link ClientRequest#setUri(URI)}
   *   <li>{@link ClientRequest#getLoadBalancerKey()}
   *   <li>{@link ClientRequest#getOverrideConfig()}
   *   <li>{@link ClientRequest#getUri()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientRequest.<init>()", "void ClientRequest.<init>(URI)",
      "void ClientRequest.<init>(URI, Object, boolean)",
      "void ClientRequest.<init>(URI, Object, boolean, IClientConfig)", "Object ClientRequest.getLoadBalancerKey()",
      "IClientConfig ClientRequest.getOverrideConfig()", "URI ClientRequest.getUri()",
      "ClientRequest ClientRequest.setLoadBalancerKey(Object)",
      "ClientRequest ClientRequest.setOverrideConfig(IClientConfig)", "ClientRequest ClientRequest.setUri(URI)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    ClientRequest actualClientRequest = new ClientRequest();
    ClientRequest actualSetLoadBalancerKeyResult = actualClientRequest.setLoadBalancerKey("Load Balancer Key");
    IClientConfig overrideConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    ClientRequest actualSetOverrideConfigResult = actualClientRequest.setOverrideConfig(overrideConfig);
    URI uri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    ClientRequest actualSetUriResult = actualClientRequest.setUri(uri);
    Object actualLoadBalancerKey = actualClientRequest.getLoadBalancerKey();
    IClientConfig actualOverrideConfig = actualClientRequest.getOverrideConfig();
    URI actualUri = actualClientRequest.getUri();

    // Assert
    assertEquals("Load Balancer Key", actualLoadBalancerKey);
    String expectedToStringResult = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString();
    assertEquals(expectedToStringResult, actualUri.toString());
    assertSame(actualClientRequest, actualSetLoadBalancerKeyResult);
    assertSame(actualClientRequest, actualSetOverrideConfigResult);
    assertSame(actualClientRequest, actualSetUriResult);
    assertSame(overrideConfig, actualOverrideConfig);
    assertSame(uri, actualUri);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClientRequest#ClientRequest(URI, Object, boolean, IClientConfig)}
   *   <li>{@link ClientRequest#setLoadBalancerKey(Object)}
   *   <li>{@link ClientRequest#setOverrideConfig(IClientConfig)}
   *   <li>{@link ClientRequest#setUri(URI)}
   *   <li>{@link ClientRequest#getLoadBalancerKey()}
   *   <li>{@link ClientRequest#getOverrideConfig()}
   *   <li>{@link ClientRequest#getUri()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientRequest.<init>()", "void ClientRequest.<init>(URI)",
      "void ClientRequest.<init>(URI, Object, boolean)",
      "void ClientRequest.<init>(URI, Object, boolean, IClientConfig)", "Object ClientRequest.getLoadBalancerKey()",
      "IClientConfig ClientRequest.getOverrideConfig()", "URI ClientRequest.getUri()",
      "ClientRequest ClientRequest.setLoadBalancerKey(Object)",
      "ClientRequest ClientRequest.setOverrideConfig(IClientConfig)", "ClientRequest ClientRequest.setUri(URI)"})
  public void testGettersAndSetters2() {
    // Arrange
    URI uri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    IClientConfig overrideConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    ClientRequest actualClientRequest = new ClientRequest(uri, "Load Balancer Key", true, overrideConfig);
    ClientRequest actualSetLoadBalancerKeyResult = actualClientRequest.setLoadBalancerKey("Load Balancer Key");
    IClientConfig overrideConfig2 = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    ClientRequest actualSetOverrideConfigResult = actualClientRequest.setOverrideConfig(overrideConfig2);
    URI uri2 = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    ClientRequest actualSetUriResult = actualClientRequest.setUri(uri2);
    Object actualLoadBalancerKey = actualClientRequest.getLoadBalancerKey();
    IClientConfig actualOverrideConfig = actualClientRequest.getOverrideConfig();
    URI actualUri = actualClientRequest.getUri();

    // Assert
    assertEquals("Load Balancer Key", actualLoadBalancerKey);
    String expectedToStringResult = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString();
    assertEquals(expectedToStringResult, actualUri.toString());
    assertSame(actualClientRequest, actualSetLoadBalancerKeyResult);
    assertSame(actualClientRequest, actualSetOverrideConfigResult);
    assertSame(actualClientRequest, actualSetUriResult);
    assertSame(overrideConfig2, actualOverrideConfig);
    assertSame(uri2, actualUri);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Load Balancer Key}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClientRequest#ClientRequest(URI, Object, boolean)}
   *   <li>{@link ClientRequest#setLoadBalancerKey(Object)}
   *   <li>{@link ClientRequest#setOverrideConfig(IClientConfig)}
   *   <li>{@link ClientRequest#setUri(URI)}
   *   <li>{@link ClientRequest#getLoadBalancerKey()}
   *   <li>{@link ClientRequest#getOverrideConfig()}
   *   <li>{@link ClientRequest#getUri()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientRequest.<init>()", "void ClientRequest.<init>(URI)",
      "void ClientRequest.<init>(URI, Object, boolean)",
      "void ClientRequest.<init>(URI, Object, boolean, IClientConfig)", "Object ClientRequest.getLoadBalancerKey()",
      "IClientConfig ClientRequest.getOverrideConfig()", "URI ClientRequest.getUri()",
      "ClientRequest ClientRequest.setLoadBalancerKey(Object)",
      "ClientRequest ClientRequest.setOverrideConfig(IClientConfig)", "ClientRequest ClientRequest.setUri(URI)"})
  public void testGettersAndSetters_whenLoadBalancerKey() {
    // Arrange and Act
    ClientRequest actualClientRequest = new ClientRequest(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(), "Load Balancer Key", true);
    ClientRequest actualSetLoadBalancerKeyResult = actualClientRequest.setLoadBalancerKey("Load Balancer Key");
    IClientConfig overrideConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    ClientRequest actualSetOverrideConfigResult = actualClientRequest.setOverrideConfig(overrideConfig);
    URI uri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    ClientRequest actualSetUriResult = actualClientRequest.setUri(uri);
    Object actualLoadBalancerKey = actualClientRequest.getLoadBalancerKey();
    IClientConfig actualOverrideConfig = actualClientRequest.getOverrideConfig();
    URI actualUri = actualClientRequest.getUri();

    // Assert
    assertEquals("Load Balancer Key", actualLoadBalancerKey);
    String expectedToStringResult = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString();
    assertEquals(expectedToStringResult, actualUri.toString());
    assertSame(actualClientRequest, actualSetLoadBalancerKeyResult);
    assertSame(actualClientRequest, actualSetOverrideConfigResult);
    assertSame(actualClientRequest, actualSetUriResult);
    assertSame(overrideConfig, actualOverrideConfig);
    assertSame(uri, actualUri);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When Property is {@code java.io.tmpdir} is {@code test.txt} toUri.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClientRequest#ClientRequest(URI)}
   *   <li>{@link ClientRequest#setLoadBalancerKey(Object)}
   *   <li>{@link ClientRequest#setOverrideConfig(IClientConfig)}
   *   <li>{@link ClientRequest#setUri(URI)}
   *   <li>{@link ClientRequest#getLoadBalancerKey()}
   *   <li>{@link ClientRequest#getOverrideConfig()}
   *   <li>{@link ClientRequest#getUri()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientRequest.<init>()", "void ClientRequest.<init>(URI)",
      "void ClientRequest.<init>(URI, Object, boolean)",
      "void ClientRequest.<init>(URI, Object, boolean, IClientConfig)", "Object ClientRequest.getLoadBalancerKey()",
      "IClientConfig ClientRequest.getOverrideConfig()", "URI ClientRequest.getUri()",
      "ClientRequest ClientRequest.setLoadBalancerKey(Object)",
      "ClientRequest ClientRequest.setOverrideConfig(IClientConfig)", "ClientRequest ClientRequest.setUri(URI)"})
  public void testGettersAndSetters_whenPropertyIsJavaIoTmpdirIsTestTxtToUri() {
    // Arrange and Act
    ClientRequest actualClientRequest = new ClientRequest(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
    ClientRequest actualSetLoadBalancerKeyResult = actualClientRequest.setLoadBalancerKey("Load Balancer Key");
    IClientConfig overrideConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    ClientRequest actualSetOverrideConfigResult = actualClientRequest.setOverrideConfig(overrideConfig);
    URI uri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    ClientRequest actualSetUriResult = actualClientRequest.setUri(uri);
    Object actualLoadBalancerKey = actualClientRequest.getLoadBalancerKey();
    IClientConfig actualOverrideConfig = actualClientRequest.getOverrideConfig();
    URI actualUri = actualClientRequest.getUri();

    // Assert
    assertEquals("Load Balancer Key", actualLoadBalancerKey);
    String expectedToStringResult = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString();
    assertEquals(expectedToStringResult, actualUri.toString());
    assertSame(actualClientRequest, actualSetLoadBalancerKeyResult);
    assertSame(actualClientRequest, actualSetOverrideConfigResult);
    assertSame(actualClientRequest, actualSetUriResult);
    assertSame(overrideConfig, actualOverrideConfig);
    assertSame(uri, actualUri);
  }

  /**
   * Test {@link ClientRequest#ClientRequest(ClientRequest)}.
   * <ul>
   *   <li>When {@link ClientRequest#ClientRequest()}.</li>
   *   <li>Then return OverrideConfig is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientRequest#ClientRequest(ClientRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClientRequest.<init>(ClientRequest)"})
  public void testNewClientRequest_whenClientRequest_thenReturnOverrideConfigIsNull() {
    // Arrange and Act
    ClientRequest actualClientRequest = new ClientRequest(new ClientRequest());

    // Assert
    assertNull(actualClientRequest.getOverrideConfig());
    assertNull(actualClientRequest.isRetriable);
    assertNull(actualClientRequest.getLoadBalancerKey());
    assertNull(actualClientRequest.getUri());
    assertFalse(actualClientRequest.isRetriable());
  }

  /**
   * Test {@link ClientRequest#isRetriable()}.
   * <ul>
   *   <li>Given {@link ClientRequest#ClientRequest()} Retriable is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientRequest#isRetriable()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ClientRequest.isRetriable()"})
  public void testIsRetriable_givenClientRequestRetriableIsTrue_thenReturnTrue() {
    // Arrange
    ClientRequest clientRequest = new ClientRequest();
    clientRequest.setRetriable(true);

    // Act and Assert
    assertTrue(clientRequest.isRetriable());
  }

  /**
   * Test {@link ClientRequest#isRetriable()}.
   * <ul>
   *   <li>Given {@link ClientRequest#ClientRequest()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientRequest#isRetriable()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ClientRequest.isRetriable()"})
  public void testIsRetriable_givenClientRequest_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ClientRequest()).isRetriable());
  }

  /**
   * Test {@link ClientRequest#setRetriable(boolean)}.
   * <p>
   * Method under test: {@link ClientRequest#setRetriable(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientRequest ClientRequest.setRetriable(boolean)"})
  public void testSetRetriable() {
    // Arrange
    ClientRequest clientRequest = new ClientRequest();

    // Act
    ClientRequest actualSetRetriableResult = clientRequest.setRetriable(true);

    // Assert
    assertTrue(clientRequest.isRetriable());
    assertTrue(clientRequest.isRetriable);
    assertSame(clientRequest, actualSetRetriableResult);
  }

  /**
   * Test {@link ClientRequest#replaceUri(URI)}.
   * <p>
   * Method under test: {@link ClientRequest#replaceUri(URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClientRequest ClientRequest.replaceUri(URI)"})
  public void testReplaceUri() {
    // Arrange
    URI newURI = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    // Act
    ClientRequest actualReplaceUriResult = (new ClientRequest()).replaceUri(newURI);

    // Assert
    assertNull(actualReplaceUriResult.getOverrideConfig());
    assertNull(actualReplaceUriResult.isRetriable);
    assertNull(actualReplaceUriResult.getLoadBalancerKey());
    assertFalse(actualReplaceUriResult.isRetriable());
    assertSame(newURI, actualReplaceUriResult.getUri());
  }
}
