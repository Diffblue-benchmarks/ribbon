package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SimpleVipAddressResolverDiffblueTest {
  /**
   * Test {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}.
   *
   * <ul>
   *   <li>When {@code 42 Main St}.
   *   <li>Then return {@code 42 Main St}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String SimpleVipAddressResolver.resolve(String, IClientConfig)"})
  public void testResolve_when42MainSt_thenReturn42MainSt() {
    // Arrange, Act and Assert
    assertEquals(
        "42 Main St",
        new SimpleVipAddressResolver()
            .resolve("42 Main St", DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Test {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String SimpleVipAddressResolver.resolve(String, IClientConfig)"})
  public void testResolve_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals(
        "", new SimpleVipAddressResolver().resolve("", DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Test {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String SimpleVipAddressResolver.resolve(String, IClientConfig)"})
  public void testResolve_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new SimpleVipAddressResolver().resolve(null, DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Test {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}.
   *
   * <ul>
   *   <li>When {@code ${xx}}.
   *   <li>Then return {@code ${xx}}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String SimpleVipAddressResolver.resolve(String, IClientConfig)"})
  public void testResolve_whenXx_thenReturnXx() {
    // Arrange, Act and Assert
    assertEquals(
        "${xx}",
        new SimpleVipAddressResolver().resolve("${xx}", DefaultClientConfigImpl.getEmptyConfig()));
  }
}
