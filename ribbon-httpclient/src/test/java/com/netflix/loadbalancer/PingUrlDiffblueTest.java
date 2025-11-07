package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PingUrlDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingUrl.<init>()", "String PingUrl.getExpectedContent()",
      "String PingUrl.getPingAppendString()", "boolean PingUrl.isSecure()", "void PingUrl.setExpectedContent(String)",
      "void PingUrl.setSecure(boolean)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    PingUrl actualPingUrl = new PingUrl();
    actualPingUrl.setExpectedContent("https://example.org/example");
    actualPingUrl.setSecure(true);
    String actualExpectedContent = actualPingUrl.getExpectedContent();
    String actualPingAppendString = actualPingUrl.getPingAppendString();

    // Assert
    assertEquals("", actualPingAppendString);
    assertEquals("https://example.org/example", actualExpectedContent);
    assertTrue(actualPingUrl.isSecure());
  }

  /**
   * Test {@link PingUrl#PingUrl(boolean, String)}.
   * <ul>
   *   <li>Then return PingAppendString is {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PingUrl#PingUrl(boolean, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingUrl.<init>(boolean, String)"})
  public void testNewPingUrl_thenReturnPingAppendStringIsHttpsExampleOrgExample() {
    // Arrange and Act
    PingUrl actualPingUrl = new PingUrl(true, "https://example.org/example");

    // Assert
    assertEquals("https://example.org/example", actualPingUrl.getPingAppendString());
    assertNull(actualPingUrl.getExpectedContent());
    assertTrue(actualPingUrl.isSecure());
  }

  /**
   * Test {@link PingUrl#PingUrl(boolean, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return PingAppendString is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link PingUrl#PingUrl(boolean, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingUrl.<init>(boolean, String)"})
  public void testNewPingUrl_whenNull_thenReturnPingAppendStringIsEmptyString() {
    // Arrange and Act
    PingUrl actualPingUrl = new PingUrl(true, null);

    // Assert
    assertEquals("", actualPingUrl.getPingAppendString());
    assertNull(actualPingUrl.getExpectedContent());
    assertTrue(actualPingUrl.isSecure());
  }

  /**
   * Test {@link PingUrl#setPingAppendString(String)}.
   * <p>
   * Method under test: {@link PingUrl#setPingAppendString(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingUrl.setPingAppendString(String)"})
  public void testSetPingAppendString() {
    // Arrange
    PingUrl pingUrl = new PingUrl(true, "https://example.org/example");

    // Act
    pingUrl.setPingAppendString("https://example.org/example");

    // Assert that nothing has changed
    assertEquals("https://example.org/example", pingUrl.getPingAppendString());
  }

  /**
   * Test {@link PingUrl#setPingAppendString(String)}.
   * <p>
   * Method under test: {@link PingUrl#setPingAppendString(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingUrl.setPingAppendString(String)"})
  public void testSetPingAppendString2() {
    // Arrange
    PingUrl pingUrl = new PingUrl(true, "https://example.org/example");

    // Act
    pingUrl.setPingAppendString(null);

    // Assert
    assertEquals("", pingUrl.getPingAppendString());
  }

  /**
   * Test {@link PingUrl#isAlive(Server)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PingUrl#isAlive(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean PingUrl.isAlive(Server)"})
  public void testIsAlive_thenReturnFalse() {
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
}
