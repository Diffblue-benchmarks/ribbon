package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;

public class PingUrlDiffblueTest {
  /**
   * Method under test: {@link PingUrl#setPingAppendString(String)}
   */
  @Test
  public void testSetPingAppendString() {
    // Arrange
    PingUrl pingUrl = new PingUrl(true, "https://example.org/example");

    // Act
    pingUrl.setPingAppendString("https://example.org/example");

    // Assert
    assertEquals("https://example.org/example", pingUrl.getPingAppendString());
  }

  /**
   * Method under test: {@link PingUrl#setPingAppendString(String)}
   */
  @Test
  public void testSetPingAppendString2() {
    // Arrange
    PingUrl pingUrl = new PingUrl(true, "https://example.org/example");

    // Act
    pingUrl.setPingAppendString(null);

    // Assert
    assertEquals("", pingUrl.getPingAppendString());
  }

  /**
   * Method under test: {@link PingUrl#isAlive(Server)}
   */
  @Test
  public void testIsAlive() {
    // Arrange
    PingUrl pingUrl = new PingUrl(true, "/cs/hostRunning");
    Server server = mock(Server.class);
    when(server.getId()).thenReturn("");

    // Act
    boolean actualIsAliveResult = pingUrl.isAlive(server);

    // Assert
    verify(server).getId();
    assertFalse(actualIsAliveResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PingUrl#PingUrl()}
   *   <li>{@link PingUrl#setExpectedContent(String)}
   *   <li>{@link PingUrl#setSecure(boolean)}
   *   <li>{@link PingUrl#getExpectedContent()}
   *   <li>{@link PingUrl#getPingAppendString()}
   *   <li>{@link PingUrl#isSecure()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    PingUrl actualPingUrl = new PingUrl();
    actualPingUrl.setExpectedContent("https://example.org/example");
    actualPingUrl.setSecure(true);
    String actualExpectedContent = actualPingUrl.getExpectedContent();
    String actualPingAppendString = actualPingUrl.getPingAppendString();

    // Assert that nothing has changed
    assertEquals("", actualPingAppendString);
    assertEquals("https://example.org/example", actualExpectedContent);
    assertTrue(actualPingUrl.isSecure());
  }

  /**
   * Method under test: {@link PingUrl#PingUrl(boolean, String)}
   */
  @Test
  public void testNewPingUrl() {
    // Arrange and Act
    PingUrl actualPingUrl = new PingUrl(true, "https://example.org/example");

    // Assert
    assertEquals("https://example.org/example", actualPingUrl.getPingAppendString());
    assertNull(actualPingUrl.getExpectedContent());
    assertTrue(actualPingUrl.isSecure());
  }

  /**
   * Method under test: {@link PingUrl#PingUrl(boolean, String)}
   */
  @Test
  public void testNewPingUrl2() {
    // Arrange and Act
    PingUrl actualPingUrl = new PingUrl(true, null);

    // Assert
    assertEquals("", actualPingUrl.getPingAppendString());
    assertNull(actualPingUrl.getExpectedContent());
    assertTrue(actualPingUrl.isSecure());
  }
}
