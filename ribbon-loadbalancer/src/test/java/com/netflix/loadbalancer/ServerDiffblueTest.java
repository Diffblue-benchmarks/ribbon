package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.util.Pair;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ServerDiffblueTest {
  /**
   * Test {@link Server#Server(String, int)}.
   *
   * <p>Method under test: {@link Server#Server(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.<init>(String, int)"})
  public void testNewServer() {
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
   * Test {@link Server#Server(String, String, int)}.
   *
   * <p>Method under test: {@link Server#Server(String, String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.<init>(String, String, int)"})
  public void testNewServer2() {
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

  /**
   * Test {@link Server#Server(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return Host is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Server#Server(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.<init>(String)"})
  public void testNewServer_when42_thenReturnHostIs42() {
    // Arrange and Act
    Server actualServer = new Server("42");

    // Assert
    assertEquals("42", actualServer.getHost());
    assertEquals("42:80", actualServer.getHostPort());
    assertEquals("42:80", actualServer.getId());
    assertNull(actualServer.getScheme());
    assertEquals(80, actualServer.getPort());
  }

  /**
   * Test {@link Server#Server(String)}.
   *
   * <ul>
   *   <li>When {@code :42}.
   *   <li>Then return HostPort is {@code :42}.
   * </ul>
   *
   * <p>Method under test: {@link Server#Server(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.<init>(String)"})
  public void testNewServer_when42_thenReturnHostPortIs42() {
    // Arrange and Act
    Server actualServer = new Server(":42");

    // Assert
    assertEquals("", actualServer.getHost());
    assertEquals(":42", actualServer.getHostPort());
    assertEquals(":42", actualServer.getId());
    assertEquals(42, actualServer.getPort());
  }

  /**
   * Test {@link Server#Server(String)}.
   *
   * <ul>
   *   <li>When {@code http://}.
   *   <li>Then return Scheme is {@code http}.
   * </ul>
   *
   * <p>Method under test: {@link Server#Server(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.<init>(String)"})
  public void testNewServer_whenHttp_thenReturnSchemeIsHttp() {
    // Arrange and Act
    Server actualServer = new Server("http://");

    // Assert
    assertEquals("", actualServer.getHost());
    assertEquals(":80", actualServer.getHostPort());
    assertEquals(":80", actualServer.getId());
    assertEquals("http", actualServer.getScheme());
  }

  /**
   * Test {@link Server#Server(String)}.
   *
   * <ul>
   *   <li>When {@code https://}.
   *   <li>Then return HostPort is {@code :443}.
   * </ul>
   *
   * <p>Method under test: {@link Server#Server(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.<init>(String)"})
  public void testNewServer_whenHttps_thenReturnHostPortIs443() {
    // Arrange and Act
    Server actualServer = new Server("https://");

    // Assert
    assertEquals(":443", actualServer.getHostPort());
    assertEquals(":443", actualServer.getId());
    assertEquals("https", actualServer.getScheme());
    assertEquals(443, actualServer.getPort());
  }

  /**
   * Test {@link Server#Server(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return HostPort is {@code null:80}.
   * </ul>
   *
   * <p>Method under test: {@link Server#Server(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.<init>(String)"})
  public void testNewServer_whenNull_thenReturnHostPortIsNull80() {
    // Arrange and Act
    Server actualServer = new Server(null);

    // Assert
    assertEquals("null:80", actualServer.getHostPort());
    assertNull(actualServer.getHost());
    assertNull(actualServer.getId());
    assertNull(actualServer.getScheme());
    assertEquals(80, actualServer.getPort());
  }

  /**
   * Test {@link Server#Server(String)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then return HostPort is {@code :80}.
   * </ul>
   *
   * <p>Method under test: {@link Server#Server(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.<init>(String)"})
  public void testNewServer_whenSlash_thenReturnHostPortIs80() {
    // Arrange and Act
    Server actualServer = new Server("/");

    // Assert
    assertEquals("", actualServer.getHost());
    assertEquals(":80", actualServer.getHostPort());
    assertEquals(":80", actualServer.getId());
    assertNull(actualServer.getScheme());
    assertEquals(80, actualServer.getPort());
  }

  /**
   * Test {@link Server#setHostPort(String)}.
   *
   * <ul>
   *   <li>When {@code Host Port}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Host is {@code Host Port}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setHostPort(String)"})
  public void testSetHostPort_whenHostPort_thenServerWithIdIs42HostIsHostPort() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHostPort("Host Port");

    // Assert
    assertEquals("Host Port", server.getHost());
    assertEquals("Host Port:80", server.getHostPort());
    assertEquals("Host Port:80", server.getId());
    assertNull(server.getScheme());
  }

  /**
   * Test {@link Server#setHostPort(String)}.
   *
   * <ul>
   *   <li>When {@code http://}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Scheme is {@code http}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setHostPort(String)"})
  public void testSetHostPort_whenHttp_thenServerWithIdIs42SchemeIsHttp() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHostPort("http://");

    // Assert
    assertEquals("", server.getHost());
    assertEquals(":80", server.getHostPort());
    assertEquals(":80", server.getId());
    assertEquals("http", server.getScheme());
  }

  /**
   * Test {@link Server#setHostPort(String)}.
   *
   * <ul>
   *   <li>When {@code https://}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} HostPort is {@code :443}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setHostPort(String)"})
  public void testSetHostPort_whenHttps_thenServerWithIdIs42HostPortIs443() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHostPort("https://");

    // Assert
    assertEquals(":443", server.getHostPort());
    assertEquals(":443", server.getId());
    assertEquals("https", server.getScheme());
    assertEquals(443, server.getPort());
  }

  /**
   * Test {@link Server#setHostPort(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Host is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setHostPort(String)"})
  public void testSetHostPort_whenNull_thenServerWithIdIs42HostIs42() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHostPort(null);

    // Assert
    assertEquals("42", server.getHost());
    assertEquals("42:80", server.getHostPort());
    assertNull(server.getId());
    assertNull(server.getScheme());
  }

  /**
   * Test {@link Server#setHostPort(String)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Host is empty string.
   * </ul>
   *
   * <p>Method under test: {@link Server#setHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setHostPort(String)"})
  public void testSetHostPort_whenSlash_thenServerWithIdIs42HostIsEmptyString() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHostPort("/");

    // Assert
    assertEquals("", server.getHost());
    assertEquals(":80", server.getHostPort());
    assertEquals(":80", server.getId());
    assertNull(server.getScheme());
  }

  /**
   * Test {@link Server#normalizeId(String)}.
   *
   * <ul>
   *   <li>When {@code :42}.
   *   <li>Then return {@code :42}.
   * </ul>
   *
   * <p>Method under test: {@link Server#normalizeId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Server.normalizeId(String)"})
  public void testNormalizeId_when42_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals(":42", Server.normalizeId(":42"));
  }

  /**
   * Test {@link Server#normalizeId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code 42:80}.
   * </ul>
   *
   * <p>Method under test: {@link Server#normalizeId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Server.normalizeId(String)"})
  public void testNormalizeId_when42_thenReturn4280() {
    // Arrange, Act and Assert
    assertEquals("42:80", Server.normalizeId("42"));
  }

  /**
   * Test {@link Server#normalizeId(String)}.
   *
   * <ul>
   *   <li>When {@code http://}.
   *   <li>Then return {@code :80}.
   * </ul>
   *
   * <p>Method under test: {@link Server#normalizeId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Server.normalizeId(String)"})
  public void testNormalizeId_whenHttp_thenReturn80() {
    // Arrange, Act and Assert
    assertEquals(":80", Server.normalizeId("http://"));
  }

  /**
   * Test {@link Server#normalizeId(String)}.
   *
   * <ul>
   *   <li>When {@code https://}.
   *   <li>Then return {@code :443}.
   * </ul>
   *
   * <p>Method under test: {@link Server#normalizeId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Server.normalizeId(String)"})
  public void testNormalizeId_whenHttps_thenReturn443() {
    // Arrange, Act and Assert
    assertEquals(":443", Server.normalizeId("https://"));
  }

  /**
   * Test {@link Server#normalizeId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Server#normalizeId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Server.normalizeId(String)"})
  public void testNormalizeId_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(Server.normalizeId(null));
  }

  /**
   * Test {@link Server#normalizeId(String)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then return {@code :80}.
   * </ul>
   *
   * <p>Method under test: {@link Server#normalizeId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Server.normalizeId(String)"})
  public void testNormalizeId_whenSlash_thenReturn80() {
    // Arrange, Act and Assert
    assertEquals(":80", Server.normalizeId("/"));
  }

  /**
   * Test {@link Server#getHostPort()}.
   *
   * <p>Method under test: {@link Server#getHostPort()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Server.getHostPort()"})
  public void testGetHostPort() {
    // Arrange, Act and Assert
    assertEquals("42:80", new Server("42").getHostPort());
  }

  /**
   * Test {@link Server#getHostPort(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return second intValue is four hundred forty-three.
   * </ul>
   *
   * <p>Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair Server.getHostPort(String)"})
  public void testGetHostPortWithString_thenReturnSecondIntValueIsFourHundredFortyThree() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort("https://");

    // Assert
    assertEquals("", actualHostPort.first());
    assertEquals(443, actualHostPort.second().intValue());
  }

  /**
   * Test {@link Server#getHostPort(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return first is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair Server.getHostPort(String)"})
  public void testGetHostPortWithString_when42_thenReturnFirstIs42() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort("42");

    // Assert
    assertEquals("42", actualHostPort.first());
    assertEquals(80, actualHostPort.second().intValue());
  }

  /**
   * Test {@link Server#getHostPort(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code :42}.
   *   <li>Then return second intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair Server.getHostPort(String)"})
  public void testGetHostPortWithString_when42_thenReturnSecondIntValueIsFortyTwo() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort(":42");

    // Assert
    assertEquals("", actualHostPort.first());
    assertEquals(42, actualHostPort.second().intValue());
  }

  /**
   * Test {@link Server#getHostPort(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code http://}.
   *   <li>Then return second intValue is eighty.
   * </ul>
   *
   * <p>Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair Server.getHostPort(String)"})
  public void testGetHostPortWithString_whenHttp_thenReturnSecondIntValueIsEighty() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort("http://");

    // Assert
    assertEquals("", actualHostPort.first());
    assertEquals(80, actualHostPort.second().intValue());
  }

  /**
   * Test {@link Server#getHostPort(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair Server.getHostPort(String)"})
  public void testGetHostPortWithString_whenNull_thenReturnNull() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort(null);

    // Assert
    assertNull(actualHostPort);
  }

  /**
   * Test {@link Server#getHostPort(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then return second intValue is eighty.
   * </ul>
   *
   * <p>Method under test: {@link Server#getHostPort(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair Server.getHostPort(String)"})
  public void testGetHostPortWithString_whenSlash_thenReturnSecondIntValueIsEighty() {
    // Arrange and Act
    Pair<String, Integer> actualHostPort = Server.getHostPort("/");

    // Assert
    assertEquals("", actualHostPort.first());
    assertEquals(80, actualHostPort.second().intValue());
  }

  /**
   * Test {@link Server#setId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Id is {@code 42:80}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setId(String)"})
  public void testSetId_when42_thenServerWithIdIs42IdIs4280() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId("42");

    // Assert that nothing has changed
    assertEquals("42", server.getHost());
    assertEquals("42:80", server.getHostPort());
    assertEquals("42:80", server.getId());
  }

  /**
   * Test {@link Server#setId(String)}.
   *
   * <ul>
   *   <li>When {@code http://}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Scheme is {@code http}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setId(String)"})
  public void testSetId_whenHttp_thenServerWithIdIs42SchemeIsHttp() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId("http://");

    // Assert
    assertEquals("", server.getHost());
    assertEquals(":80", server.getHostPort());
    assertEquals(":80", server.getId());
    assertEquals("http", server.getScheme());
  }

  /**
   * Test {@link Server#setId(String)}.
   *
   * <ul>
   *   <li>When {@code https://}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} HostPort is {@code :443}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setId(String)"})
  public void testSetId_whenHttps_thenServerWithIdIs42HostPortIs443() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId("https://");

    // Assert
    assertEquals(":443", server.getHostPort());
    assertEquals(":443", server.getId());
    assertEquals("https", server.getScheme());
    assertEquals(443, server.getPort());
  }

  /**
   * Test {@link Server#setId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setId(String)"})
  public void testSetId_whenNull_thenServerWithIdIs42IdIsNull() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId(null);

    // Assert
    assertEquals("42", server.getHost());
    assertEquals("42:80", server.getHostPort());
    assertNull(server.getId());
    assertNull(server.getScheme());
  }

  /**
   * Test {@link Server#setId(String)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Host is empty string.
   * </ul>
   *
   * <p>Method under test: {@link Server#setId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setId(String)"})
  public void testSetId_whenSlash_thenServerWithIdIs42HostIsEmptyString() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId("/");

    // Assert
    assertEquals("", server.getHost());
    assertEquals(":80", server.getHostPort());
    assertEquals(":80", server.getId());
    assertNull(server.getScheme());
  }

  /**
   * Test {@link Server#setPort(int)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} HostPort is {@code 42:8080}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setPort(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setPort(int)"})
  public void testSetPort_givenServerWithIdIs42_thenServerWithIdIs42HostPortIs428080() {
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
   * Test {@link Server#setPort(int)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code null}.
   *   <li>Then {@link Server#Server(String)} with id is {@code null} HostPort is {@code null:8080}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setPort(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setPort(int)"})
  public void testSetPort_givenServerWithIdIsNull_thenServerWithIdIsNullHostPortIsNull8080() {
    // Arrange
    Server server = new Server(null);

    // Act
    server.setPort(8080);

    // Assert
    assertEquals("null:8080", server.getHostPort());
    assertNull(server.getId());
    assertEquals(8080, server.getPort());
  }

  /**
   * Test {@link Server#setHost(String)}.
   *
   * <ul>
   *   <li>When {@code localhost}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Host is {@code localhost}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setHost(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setHost(String)"})
  public void testSetHost_whenLocalhost_thenServerWithIdIs42HostIsLocalhost() {
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
   * Test {@link Server#setHost(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Host is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setHost(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setHost(String)"})
  public void testSetHost_whenNull_thenServerWithIdIs42HostIs42() {
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
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String Server.getHost()",
    "String Server.getId()",
    "Server.MetaInfo Server.getMetaInfo()",
    "int Server.getPort()",
    "String Server.getScheme()",
    "String Server.getZone()",
    "boolean Server.isAlive()",
    "boolean Server.isReadyToServe()",
    "void Server.setAlive(boolean)",
    "void Server.setReadyToServe(boolean)",
    "void Server.setSchemea(String)",
    "void Server.setZone(String)",
    "String Server.toString()"
  })
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

    // Assert
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
   * Test {@link Server#equals(Object)}, and {@link Server#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Server#equals(Object)}
   *   <li>{@link Server#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Server.equals(Object)", "int Server.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Server server = new Server("42");
    Server server2 = new Server("42");

    // Act and Assert
    assertEquals(server, server2);
    assertEquals(server.hashCode(), server2.hashCode());
  }

  /**
   * Test {@link Server#equals(Object)}, and {@link Server#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Server#equals(Object)}
   *   <li>{@link Server#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Server.equals(Object)", "int Server.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Server server = new Server("42");

    // Act and Assert
    assertEquals(server, server);
    int expectedHashCodeResult = server.hashCode();
    assertEquals(expectedHashCodeResult, server.hashCode());
  }

  /**
   * Test {@link Server#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Server#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Server.equals(Object)", "int Server.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Server server = new Server("localhost", 8080);

    // Act and Assert
    assertNotEquals(server, new Server("42"));
  }

  /**
   * Test {@link Server#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Server#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Server.equals(Object)", "int Server.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Server("42"), null);
  }

  /**
   * Test {@link Server#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Server#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Server.equals(Object)", "int Server.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Server("42"), "Different type to Server");
  }
}
