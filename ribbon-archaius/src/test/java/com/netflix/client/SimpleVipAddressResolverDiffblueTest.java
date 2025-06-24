package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SimpleVipAddressResolverDiffblueTest {
  /**
   * Test {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}.
   * <ul>
   *   <li>When {@code 42 Main St}.</li>
   *   <li>Then return {@code 42 Main St}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SimpleVipAddressResolver.resolve(String, IClientConfig)"})
  public void testResolve_when42MainSt_thenReturn42MainSt() {
    // Arrange
    SimpleVipAddressResolver simpleVipAddressResolver = new SimpleVipAddressResolver();

    // Act and Assert
    assertEquals("42 Main St",
        simpleVipAddressResolver.resolve("42 Main St", DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Test {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SimpleVipAddressResolver.resolve(String, IClientConfig)"})
  public void testResolve_whenEmptyString_thenReturnEmptyString() {
    // Arrange
    SimpleVipAddressResolver simpleVipAddressResolver = new SimpleVipAddressResolver();

    // Act and Assert
    assertEquals("", simpleVipAddressResolver.resolve("", DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Test {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SimpleVipAddressResolver.resolve(String, IClientConfig)"})
  public void testResolve_whenNull_thenReturnNull() {
    // Arrange
    SimpleVipAddressResolver simpleVipAddressResolver = new SimpleVipAddressResolver();

    // Act and Assert
    assertNull(simpleVipAddressResolver.resolve(null, DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Test {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}.
   * <ul>
   *   <li>When {@code ${UU}}.</li>
   *   <li>Then return {@code ${UU}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SimpleVipAddressResolver.resolve(String, IClientConfig)"})
  public void testResolve_whenUu_thenReturnUu() {
    // Arrange
    SimpleVipAddressResolver simpleVipAddressResolver = new SimpleVipAddressResolver();

    // Act and Assert
    assertEquals("${UU}", simpleVipAddressResolver.resolve("${UU}", DefaultClientConfigImpl.getEmptyConfig()));
  }
}
