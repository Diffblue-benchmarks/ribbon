package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.net.URI;
import java.nio.file.Paths;
import org.junit.Test;

public class ClientRequestDiffblueTest {
  /**
   * Method under test: {@link ClientRequest#isRetriable()}
   */
  @Test
  public void testIsRetriable() {
    // Arrange, Act and Assert
    assertFalse((new ClientRequest()).isRetriable());
  }

  /**
   * Method under test: {@link ClientRequest#isRetriable()}
   */
  @Test
  public void testIsRetriable2() {
    // Arrange
    ClientRequest clientRequest = new ClientRequest();
    clientRequest.setRetriable(true);

    // Act and Assert
    assertTrue(clientRequest.isRetriable());
  }

  /**
   * Method under test: {@link ClientRequest#isRetriable()}
   */
  @Test
  public void testIsRetriable3() {
    // Arrange
    DefaultClientConfigImpl overrideConfig = DefaultClientConfigImpl.getEmptyConfig();
    overrideConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    ClientRequest clientRequest = new ClientRequest();
    clientRequest.setOverrideConfig(overrideConfig);

    // Act and Assert
    assertFalse(clientRequest.isRetriable());
  }

  /**
   * Method under test: {@link ClientRequest#setRetriable(boolean)}
   */
  @Test
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
   * Method under test: {@link ClientRequest#setRetriable(boolean)}
   */
  @Test
  public void testSetRetriable2() {
    // Arrange
    DefaultClientConfigImpl overrideConfig = DefaultClientConfigImpl.getEmptyConfig();
    overrideConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    ClientRequest clientRequest = new ClientRequest();
    clientRequest.setOverrideConfig(overrideConfig);

    // Act
    ClientRequest actualSetRetriableResult = clientRequest.setRetriable(true);

    // Assert
    assertTrue(clientRequest.isRetriable());
    assertTrue(clientRequest.isRetriable);
    assertSame(clientRequest, actualSetRetriableResult);
  }

  /**
   * Method under test: {@link ClientRequest#replaceUri(URI)}
   */
  @Test
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
    String expectedToStringResult = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString();
    URI uri = actualReplaceUriResult.getUri();
    assertEquals(expectedToStringResult, uri.toString());
    assertSame(newURI, uri);
  }

  /**
   * Method under test: {@link ClientRequest#replaceUri(URI)}
   */
  @Test
  public void testReplaceUri2() {
    // Arrange
    DefaultClientConfigImpl overrideConfig = DefaultClientConfigImpl.getEmptyConfig();
    overrideConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    ClientRequest clientRequest = new ClientRequest();
    clientRequest.setOverrideConfig(overrideConfig);
    URI newURI = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    // Act
    ClientRequest actualReplaceUriResult = clientRequest.replaceUri(newURI);

    // Assert
    assertNull(actualReplaceUriResult.isRetriable);
    assertNull(actualReplaceUriResult.getLoadBalancerKey());
    assertFalse(actualReplaceUriResult.isRetriable());
    String expectedToStringResult = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString();
    URI uri = actualReplaceUriResult.getUri();
    assertEquals(expectedToStringResult, uri.toString());
    assertSame(newURI, uri);
    assertSame(overrideConfig, actualReplaceUriResult.getOverrideConfig());
  }

  /**
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
  public void testGettersAndSetters() {
    // Arrange and Act
    ClientRequest actualClientRequest = new ClientRequest();
    ClientRequest actualSetLoadBalancerKeyResult = actualClientRequest.setLoadBalancerKey("Load Balancer Key");
    DefaultClientConfigImpl overrideConfig = DefaultClientConfigImpl.getEmptyConfig();
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
    assertSame(uri, actualUri);
    assertSame(overrideConfig, actualOverrideConfig);
  }

  /**
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
  public void testGettersAndSetters2() {
    // Arrange and Act
    ClientRequest actualClientRequest = new ClientRequest(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
    ClientRequest actualSetLoadBalancerKeyResult = actualClientRequest.setLoadBalancerKey("Load Balancer Key");
    DefaultClientConfigImpl overrideConfig = DefaultClientConfigImpl.getEmptyConfig();
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
    assertSame(uri, actualUri);
    assertSame(overrideConfig, actualOverrideConfig);
  }

  /**
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
  public void testGettersAndSetters3() {
    // Arrange and Act
    ClientRequest actualClientRequest = new ClientRequest(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(), "Load Balancer Key", true);
    ClientRequest actualSetLoadBalancerKeyResult = actualClientRequest.setLoadBalancerKey("Load Balancer Key");
    DefaultClientConfigImpl overrideConfig = DefaultClientConfigImpl.getEmptyConfig();
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
    assertSame(uri, actualUri);
    assertSame(overrideConfig, actualOverrideConfig);
  }

  /**
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
  public void testGettersAndSetters4() {
    // Arrange
    URI uri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    // Act
    ClientRequest actualClientRequest = new ClientRequest(uri, "Load Balancer Key", true,
        DefaultClientConfigImpl.getEmptyConfig());
    ClientRequest actualSetLoadBalancerKeyResult = actualClientRequest.setLoadBalancerKey("Load Balancer Key");
    DefaultClientConfigImpl overrideConfig = DefaultClientConfigImpl.getEmptyConfig();
    ClientRequest actualSetOverrideConfigResult = actualClientRequest.setOverrideConfig(overrideConfig);
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
    assertSame(uri2, actualUri);
    assertSame(overrideConfig, actualOverrideConfig);
  }

  /**
   * Method under test: {@link ClientRequest#ClientRequest(ClientRequest)}
   */
  @Test
  public void testNewClientRequest() {
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
   * Method under test: {@link ClientRequest#ClientRequest(ClientRequest)}
   */
  @Test
  public void testNewClientRequest2() {
    // Arrange
    DefaultClientConfigImpl overrideConfig = DefaultClientConfigImpl.getEmptyConfig();
    overrideConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    ClientRequest request = new ClientRequest();
    request.setOverrideConfig(overrideConfig);

    // Act
    ClientRequest actualClientRequest = new ClientRequest(request);

    // Assert
    assertNull(actualClientRequest.isRetriable);
    assertNull(actualClientRequest.getLoadBalancerKey());
    assertNull(actualClientRequest.getUri());
    assertFalse(actualClientRequest.isRetriable());
    assertSame(overrideConfig, actualClientRequest.getOverrideConfig());
  }
}
