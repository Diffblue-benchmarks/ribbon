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
    Server actualServer = new Server("\"www.netflix.com\"", 8080);

    // Assert
    assertEquals("\"www.netflix.com\"", actualServer.getHost());
    assertEquals("\"www.netflix.com\":8080", actualServer.getHostPort());
    assertEquals("\"www.netflix.com\":8080", actualServer.getId());
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
    Server actualServer = new Server("\"http\"", "\"www.netflix.com\"", 8080);

    // Assert
    assertEquals("\"http\"", actualServer.getScheme());
    assertEquals("\"www.netflix.com\"", actualServer.getHost());
    assertEquals("\"www.netflix.com\":8080", actualServer.getHostPort());
    assertEquals("\"www.netflix.com\":8080", actualServer.getId());
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
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
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
  public void testSetHostPort_givenServerWithIdIs42_whenHttp_thenServerWithIdIs42SchemeIsHttp() {
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
   * Test {@link Server#setHostPort(String)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
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
  public void testSetHostPort_givenServerWithIdIs42_whenNull_thenServerWithIdIs42HostIs42() {
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
    assertEquals(80, server.getPort());
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
   * Test {@link Server#getHostPort()}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then return {@code 42:80}.
   * </ul>
   *
   * <p>Method under test: {@link Server#getHostPort()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Server.getHostPort()"})
  public void testGetHostPort_givenServerWithIdIs42_thenReturn4280() {
    // Arrange, Act and Assert
    assertEquals("42:80", new Server("42").getHostPort());
  }

  /**
   * Test {@link Server#setId(String)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
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
  public void testSetId_givenServerWithIdIs42_whenHttp_thenServerWithIdIs42SchemeIsHttp() {
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
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
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
  public void testSetId_givenServerWithIdIs42_whenHttps_thenServerWithIdIs42HostPortIs443() {
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
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
   *   <li>When {@code null}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Host is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setId(String)"})
  public void testSetId_givenServerWithIdIs42_whenNull_thenServerWithIdIs42HostIs42() {
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
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
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
  public void testSetId_givenServerWithIdIs42_whenSlash_thenServerWithIdIs42HostIsEmptyString() {
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
   * Test {@link Server#setId(String)}.
   *
   * <ul>
   *   <li>When {@code "server-123-us-west-2"}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Host is {@code
   *       "server-123-us-west-2"}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setId(String)"})
  public void testSetId_whenServer123UsWest2_thenServerWithIdIs42HostIsServer123UsWest2() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setId("\"server-123-us-west-2\"");

    // Assert
    assertEquals("\"server-123-us-west-2\"", server.getHost());
    assertEquals("\"server-123-us-west-2\":80", server.getHostPort());
    assertEquals("\"server-123-us-west-2\":80", server.getId());
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
   * Test {@link Server#setHost(String)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then {@link Server#Server(String)} with id is {@code 42} Host is {@code
   *       "www.netflix.com"}.
   * </ul>
   *
   * <p>Method under test: {@link Server#setHost(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Server.setHost(String)"})
  public void testSetHost_givenServerWithIdIs42_thenServerWithIdIs42HostIsWwwNetflixCom() {
    // Arrange
    Server server = new Server("42");

    // Act
    server.setHost("\"www.netflix.com\"");

    // Assert
    assertEquals("\"www.netflix.com\"", server.getHost());
    assertEquals("\"www.netflix.com\":80", server.getHostPort());
    assertEquals("\"www.netflix.com\":80", server.getId());
  }

  /**
   * Test {@link Server#setHost(String)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.
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
  public void testSetHost_givenServerWithIdIs42_whenNull_thenServerWithIdIs42HostIs42() {
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
    Server server = new Server("\"www.netflix.com\"", 8080);

    // Act and Assert
    assertNotEquals(server, new Server("42"));
  }
}
