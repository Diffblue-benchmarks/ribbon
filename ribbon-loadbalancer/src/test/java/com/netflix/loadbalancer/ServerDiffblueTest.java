package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.netflix.util.Pair;
import org.junit.Test;

public class ServerDiffblueTest {
  /**
   * Method under test: {@link Server#setHostPort(String)}
   */
  @Test
  public void testSetHostPort() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHostPort("Host Port");

    // Assert
    assertEquals("Host Port", server.getHost());
    assertEquals("Host Port:80", server.getHostPort());
    assertEquals("Host Port:80", server.getId());
    assertNull(server.getScheme());
    assertEquals(80, server.getPort());
  }

  /**
   * Method under test: {@link Server#setHostPort(String)}
   */
  @Test
  public void testSetHostPort2() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHostPort(null);

    // Assert
    assertEquals("42", server.getHost());
    assertEquals("42:80", server.getHostPort());
    assertNull(server.getId());
    assertNull(server.getScheme());
    assertEquals(80, server.getPort());
  }

  /**
   * Method under test: {@link Server#setHostPort(String)}
   */
  @Test
  public void testSetHostPort3() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHostPort("/");

    // Assert
    assertEquals("", server.getHost());
    assertEquals(":80", server.getHostPort());
    assertEquals(":80", server.getId());
    assertNull(server.getScheme());
    assertEquals(80, server.getPort());
  }

  /**
   * Method under test: {@link Server#setHostPort(String)}
   */
  @Test
  public void testSetHostPort4() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHostPort("http://");

    // Assert
    assertEquals("", server.getHost());
    assertEquals(":80", server.getHostPort());
    assertEquals(":80", server.getId());
    assertEquals("http", server.getScheme());
    assertEquals(80, server.getPort());
  }

  /**
   * Method under test: {@link Server#setHostPort(String)}
   */
  @Test
  public void testSetHostPort5() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHostPort("https://");

    // Assert
    assertEquals("", server.getHost());
    assertEquals(":443", server.getHostPort());
    assertEquals(":443", server.getId());
    assertEquals("https", server.getScheme());
    assertEquals(443, server.getPort());
  }

  /**
   * Method under test: {@link Server#normalizeId(String)}
   */
  @Test
  public void testNormalizeId() {
    // Arrange, Act and Assert
    assertEquals("42:80", Server.normalizeId("42"));
    assertNull(Server.normalizeId(null));
    assertEquals(":80", Server.normalizeId("/"));
    assertEquals(":80", Server.normalizeId("http://"));
    assertEquals(":443", Server.normalizeId("https://"));
    assertEquals("42com.netflix.loadbalancer.Server:80", Server.normalizeId("42com.netflix.loadbalancer.Server"));
    assertEquals(":42", Server.normalizeId(":42"));
  }

  /**
   * Method under test: {@link Server#getHostPort()}
   */
  @Test
  public void testGetHostPort() {
    // Arrange, Act and Assert
    assertEquals("42:80", (new Server("42")).getHostPort());
  }

  /**
   * Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  public void testGetHostPort2() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort("42");

    // Assert
    assertEquals("42", actualHostPort.first());
    assertEquals(80, actualHostPort.second().intValue());
  }

  /**
   * Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  public void testGetHostPort3() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort(null);

    // Assert
    assertNull(actualHostPort);
  }

  /**
   * Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  public void testGetHostPort4() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort("/");

    // Assert
    assertEquals("", actualHostPort.first());
    assertEquals(80, actualHostPort.second().intValue());
  }

  /**
   * Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  public void testGetHostPort5() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort("http://");

    // Assert
    assertEquals("", actualHostPort.first());
    assertEquals(80, actualHostPort.second().intValue());
  }

  /**
   * Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  public void testGetHostPort6() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort("https://");

    // Assert
    assertEquals("", actualHostPort.first());
    assertEquals(443, actualHostPort.second().intValue());
  }

  /**
   * Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  public void testGetHostPort7() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort(":42");

    // Assert
    assertEquals("", actualHostPort.first());
    assertEquals(42, actualHostPort.second().intValue());
  }

  /**
   * Method under test: {@link Server#setId(String)}
   */
  @Test
  public void testSetId() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId("42");

    // Assert
    assertEquals("42", server.getHost());
    assertEquals("42:80", server.getHostPort());
    assertEquals("42:80", server.getId());
    assertNull(server.getScheme());
    assertEquals(80, server.getPort());
  }

  /**
   * Method under test: {@link Server#setId(String)}
   */
  @Test
  public void testSetId2() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId(null);

    // Assert
    assertEquals("42", server.getHost());
    assertEquals("42:80", server.getHostPort());
    assertNull(server.getId());
    assertNull(server.getScheme());
    assertEquals(80, server.getPort());
  }

  /**
   * Method under test: {@link Server#setId(String)}
   */
  @Test
  public void testSetId3() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId("/");

    // Assert
    assertEquals("", server.getHost());
    assertEquals(":80", server.getHostPort());
    assertEquals(":80", server.getId());
    assertNull(server.getScheme());
    assertEquals(80, server.getPort());
  }

  /**
   * Method under test: {@link Server#setId(String)}
   */
  @Test
  public void testSetId4() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId("http://");

    // Assert
    assertEquals("", server.getHost());
    assertEquals(":80", server.getHostPort());
    assertEquals(":80", server.getId());
    assertEquals("http", server.getScheme());
    assertEquals(80, server.getPort());
  }

  /**
   * Method under test: {@link Server#setId(String)}
   */
  @Test
  public void testSetId5() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId("https://");

    // Assert
    assertEquals("", server.getHost());
    assertEquals(":443", server.getHostPort());
    assertEquals(":443", server.getId());
    assertEquals("https", server.getScheme());
    assertEquals(443, server.getPort());
  }

  /**
   * Method under test: {@link Server#setPort(int)}
   */
  @Test
  public void testSetPort() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setPort(8080);

    // Assert
    assertEquals("42:8080", server.getHostPort());
    assertEquals("42:8080", server.getId());
    assertEquals(8080, server.getPort());
  }

  /**
   * Method under test: {@link Server#setPort(int)}
   */
  @Test
  public void testSetPort2() {
    // Arrange
    Server server = new Server(null);

    // Act
    server.setPort(8080);

    // Assert
    assertEquals("null:8080", server.getHostPort());
    assertEquals(8080, server.getPort());
  }

  /**
   * Method under test: {@link Server#setHost(String)}
   */
  @Test
  public void testSetHost() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHost("localhost");

    // Assert
    assertEquals("localhost", server.getHost());
    assertEquals("localhost:80", server.getHostPort());
    assertEquals("localhost:80", server.getId());
  }

  /**
   * Method under test: {@link Server#setHost(String)}
   */
  @Test
  public void testSetHost2() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHost(null);

    // Assert that nothing has changed
    assertEquals("42", server.getHost());
    assertEquals("42:80", server.getHostPort());
    assertEquals("42:80", server.getId());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Server#equals(Object)}
   *   <li>{@link Server#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Server server = new Server("42");
    Server server2 = new Server("42");

    // Act and Assert
    assertEquals(server, server2);
    int expectedHashCodeResult = server.hashCode();
    assertEquals(expectedHashCodeResult, server2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Server#equals(Object)}
   *   <li>{@link Server#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Server server = new Server("42");

    // Act and Assert
    assertEquals(server, server);
    int expectedHashCodeResult = server.hashCode();
    assertEquals(expectedHashCodeResult, server.hashCode());
  }

  /**
   * Method under test: {@link Server#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Server server = new Server("Id");

    // Act and Assert
    assertNotEquals(server, new Server("42"));
  }

  /**
   * Method under test: {@link Server#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Server("42"), null);
  }

  /**
   * Method under test: {@link Server#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Server("42"), "Different type to Server");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Server#setAlive(boolean)}
   *   <li>{@link Server#setReadyToServe(boolean)}
   *   <li>{@link Server#setSchemea(String)}
   *   <li>{@link Server#setZone(String)}
   *   <li>{@link Server#toString()}
   *   <li>{@link Server#getHost()}
   *   <li>{@link Server#getId()}
   *   <li>{@link Server#getMetaInfo()}
   *   <li>{@link Server#getPort()}
   *   <li>{@link Server#getScheme()}
   *   <li>{@link Server#getZone()}
   *   <li>{@link Server#isAlive()}
   *   <li>{@link Server#isReadyToServe()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setAlive(true);
    server.setReadyToServe(true);
    server.setSchemea("Scheme");
    server.setZone("Zone");
    String actualToStringResult = server.toString();
    String actualHost = server.getHost();
    String actualId = server.getId();
    server.getMetaInfo();
    int actualPort = server.getPort();
    String actualScheme = server.getScheme();
    String actualZone = server.getZone();
    boolean actualIsAliveResult = server.isAlive();

    // Assert that nothing has changed
    assertEquals("42", actualHost);
    assertEquals("42:80", actualId);
    assertEquals("42:80", actualToStringResult);
    assertEquals("Scheme", actualScheme);
    assertEquals("Zone", actualZone);
    assertEquals(80, actualPort);
    assertTrue(actualIsAliveResult);
    assertTrue(server.isReadyToServe());
  }

  /**
   * Method under test: {@link Server#Server(String)}
   */
  @Test
  public void testNewServer() {
    // Arrange and Act
    Server actualServer = new Server("42");

    // Assert
    assertEquals("42", actualServer.getHost());
    assertEquals("42:80", actualServer.getHostPort());
    assertEquals("42:80", actualServer.getId());
    assertNull(actualServer.getScheme());
    assertEquals(80, actualServer.getPort());
    assertFalse(actualServer.isAlive());
    assertTrue(actualServer.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualServer.getZone());
  }

  /**
   * Method under test: {@link Server#Server(String)}
   */
  @Test
  public void testNewServer2() {
    // Arrange and Act
    Server actualServer = new Server(null);

    // Assert
    assertEquals("null:80", actualServer.getHostPort());
    assertNull(actualServer.getHost());
    assertNull(actualServer.getId());
    assertNull(actualServer.getScheme());
    assertEquals(80, actualServer.getPort());
    assertFalse(actualServer.isAlive());
    assertTrue(actualServer.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualServer.getZone());
  }

  /**
   * Method under test: {@link Server#Server(String)}
   */
  @Test
  public void testNewServer3() {
    // Arrange and Act
    Server actualServer = new Server("/");

    // Assert
    assertEquals("", actualServer.getHost());
    assertEquals(":80", actualServer.getHostPort());
    assertEquals(":80", actualServer.getId());
    assertNull(actualServer.getScheme());
    assertEquals(80, actualServer.getPort());
    assertFalse(actualServer.isAlive());
    assertTrue(actualServer.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualServer.getZone());
  }

  /**
   * Method under test: {@link Server#Server(String)}
   */
  @Test
  public void testNewServer4() {
    // Arrange and Act
    Server actualServer = new Server("http://");

    // Assert
    assertEquals("", actualServer.getHost());
    assertEquals(":80", actualServer.getHostPort());
    assertEquals(":80", actualServer.getId());
    assertEquals("http", actualServer.getScheme());
    assertEquals(80, actualServer.getPort());
    assertFalse(actualServer.isAlive());
    assertTrue(actualServer.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualServer.getZone());
  }

  /**
   * Method under test: {@link Server#Server(String)}
   */
  @Test
  public void testNewServer5() {
    // Arrange and Act
    Server actualServer = new Server("https://");

    // Assert
    assertEquals("", actualServer.getHost());
    assertEquals(":443", actualServer.getHostPort());
    assertEquals(":443", actualServer.getId());
    assertEquals("https", actualServer.getScheme());
    assertEquals(443, actualServer.getPort());
    assertFalse(actualServer.isAlive());
    assertTrue(actualServer.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualServer.getZone());
  }

  /**
   * Method under test: {@link Server#Server(String, int)}
   */
  @Test
  public void testNewServer6() {
    // Arrange and Act
    Server actualServer = new Server("localhost", 8080);

    // Assert
    assertEquals("localhost", actualServer.getHost());
    assertEquals("localhost:8080", actualServer.getHostPort());
    assertEquals("localhost:8080", actualServer.getId());
    assertNull(actualServer.getScheme());
    assertEquals(8080, actualServer.getPort());
    assertFalse(actualServer.isAlive());
    assertTrue(actualServer.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualServer.getZone());
  }

  /**
   * Method under test: {@link Server#Server(String, String, int)}
   */
  @Test
  public void testNewServer7() {
    // Arrange and Act
    Server actualServer = new Server("Scheme", "localhost", 8080);

    // Assert
    assertEquals("Scheme", actualServer.getScheme());
    assertEquals("localhost", actualServer.getHost());
    assertEquals("localhost:8080", actualServer.getHostPort());
    assertEquals("localhost:8080", actualServer.getId());
    assertEquals(8080, actualServer.getPort());
    assertFalse(actualServer.isAlive());
    assertTrue(actualServer.isReadyToServe());
    assertEquals(Server.UNKNOWN_ZONE, actualServer.getZone());
  }
}
