package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.junit.Test;

public class SimpleVipAddressResolverDiffblueTest {
  /**
   * Method under test:
   * {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  public void testResolve() {
    // Arrange
    SimpleVipAddressResolver simpleVipAddressResolver = new SimpleVipAddressResolver();

    // Act and Assert
    assertEquals("42 Main St",
        simpleVipAddressResolver.resolve("42 Main St", DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Method under test:
   * {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  public void testResolve2() {
    // Arrange
    SimpleVipAddressResolver simpleVipAddressResolver = new SimpleVipAddressResolver();

    // Act and Assert
    assertNull(simpleVipAddressResolver.resolve(null, DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Method under test:
   * {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  public void testResolve3() {
    // Arrange
    SimpleVipAddressResolver simpleVipAddressResolver = new SimpleVipAddressResolver();

    // Act and Assert
    assertEquals("${xx}", simpleVipAddressResolver.resolve("${xx}", DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Method under test:
   * {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  public void testResolve4() {
    // Arrange
    SimpleVipAddressResolver simpleVipAddressResolver = new SimpleVipAddressResolver();

    // Act and Assert
    assertEquals("", simpleVipAddressResolver.resolve("", DefaultClientConfigImpl.getEmptyConfig()));
  }

  /**
   * Method under test:
   * {@link SimpleVipAddressResolver#resolve(String, IClientConfig)}
   */
  @Test
  public void testResolve5() {
    // Arrange
    SimpleVipAddressResolver simpleVipAddressResolver = new SimpleVipAddressResolver();
    DefaultClientConfigImpl niwsClientConfig = DefaultClientConfigImpl.getEmptyConfig();
    niwsClientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertEquals("42 Main St", simpleVipAddressResolver.resolve("42 Main St", niwsClientConfig));
  }
}
