package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ConfigurationBasedServerListDiffblueTest {
  /**
   * Test {@link ConfigurationBasedServerList#derive(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return first Host is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ConfigurationBasedServerList.derive(String)"})
  public void testDerive_when42_thenReturnFirstHostIs42() {
    // Arrange and Act
    List<Server> actualDeriveResult = new ConfigurationBasedServerList().derive("42");

    // Assert
    assertEquals(1, actualDeriveResult.size());
    Server getResult = actualDeriveResult.get(0);
    assertEquals("42", getResult.getHost());
    assertEquals("42:80", getResult.getHostPort());
    assertEquals("42:80", getResult.getId());
    assertNull(getResult.getScheme());
    assertEquals(80, getResult.getPort());
  }

  /**
   * Test {@link ConfigurationBasedServerList#derive(String)}.
   *
   * <ul>
   *   <li>When {@code :42}.
   *   <li>Then return first HostPort is {@code :42}.
   * </ul>
   *
   * <p>Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ConfigurationBasedServerList.derive(String)"})
  public void testDerive_when42_thenReturnFirstHostPortIs42() {
    // Arrange and Act
    List<Server> actualDeriveResult = new ConfigurationBasedServerList().derive(":42");

    // Assert
    assertEquals(1, actualDeriveResult.size());
    Server getResult = actualDeriveResult.get(0);
    assertEquals(":42", getResult.getHostPort());
    assertEquals(":42", getResult.getId());
    assertNull(getResult.getScheme());
    assertEquals(42, getResult.getPort());
  }

  /**
   * Test {@link ConfigurationBasedServerList#derive(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ConfigurationBasedServerList.derive(String)"})
  public void testDerive_whenEmptyString_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new ConfigurationBasedServerList().derive("").isEmpty());
  }

  /**
   * Test {@link ConfigurationBasedServerList#derive(String)}.
   *
   * <ul>
   *   <li>When {@code http://}.
   *   <li>Then return first Scheme is {@code http}.
   * </ul>
   *
   * <p>Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ConfigurationBasedServerList.derive(String)"})
  public void testDerive_whenHttp_thenReturnFirstSchemeIsHttp() {
    // Arrange and Act
    List<Server> actualDeriveResult = new ConfigurationBasedServerList().derive("http://");

    // Assert
    assertEquals(1, actualDeriveResult.size());
    Server getResult = actualDeriveResult.get(0);
    assertEquals(":80", getResult.getHostPort());
    assertEquals(":80", getResult.getId());
    assertEquals("http", getResult.getScheme());
    assertEquals(80, getResult.getPort());
  }

  /**
   * Test {@link ConfigurationBasedServerList#derive(String)}.
   *
   * <ul>
   *   <li>When {@code https://}.
   *   <li>Then return first HostPort is {@code :443}.
   * </ul>
   *
   * <p>Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ConfigurationBasedServerList.derive(String)"})
  public void testDerive_whenHttps_thenReturnFirstHostPortIs443() {
    // Arrange and Act
    List<Server> actualDeriveResult = new ConfigurationBasedServerList().derive("https://");

    // Assert
    assertEquals(1, actualDeriveResult.size());
    Server getResult = actualDeriveResult.get(0);
    assertEquals(":443", getResult.getHostPort());
    assertEquals(":443", getResult.getId());
    assertEquals("https", getResult.getScheme());
    assertEquals(443, getResult.getPort());
  }

  /**
   * Test {@link ConfigurationBasedServerList#derive(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ConfigurationBasedServerList.derive(String)"})
  public void testDerive_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new ConfigurationBasedServerList().derive(null).isEmpty());
  }

  /**
   * Test {@link ConfigurationBasedServerList#derive(String)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then return first HostPort is {@code :80}.
   * </ul>
   *
   * <p>Method under test: {@link ConfigurationBasedServerList#derive(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ConfigurationBasedServerList.derive(String)"})
  public void testDerive_whenSlash_thenReturnFirstHostPortIs80() {
    // Arrange and Act
    List<Server> actualDeriveResult = new ConfigurationBasedServerList().derive("/");

    // Assert
    assertEquals(1, actualDeriveResult.size());
    Server getResult = actualDeriveResult.get(0);
    assertEquals(":80", getResult.getHostPort());
    assertEquals(":80", getResult.getId());
    assertNull(getResult.getScheme());
    assertEquals(80, getResult.getPort());
  }
}
