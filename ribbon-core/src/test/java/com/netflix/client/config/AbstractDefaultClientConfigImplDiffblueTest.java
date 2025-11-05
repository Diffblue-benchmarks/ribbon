package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.SimpleVipAddressResolver;
import com.netflix.client.VipAddressResolver;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class AbstractDefaultClientConfigImplDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test:
   * {@link AbstractDefaultClientConfigImpl#setVipAddressResolver(VipAddressResolver)}
   */
  @Test
  public void testSetVipAddressResolver() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    VipAddressResolver resolver = mock(VipAddressResolver.class);

    // Act
    emptyConfig.setVipAddressResolver(resolver);

    // Assert
    assertSame(resolver, emptyConfig.getResolver());
  }

  /**
   * Method under test: {@link AbstractDefaultClientConfigImpl#getResolver()}
   */
  @Test
  public void testGetResolver() {
    // Arrange
    VipAddressResolver resolver = mock(VipAddressResolver.class);
    when(resolver.resolve(Mockito.<String>any(), Mockito.<IClientConfig>any())).thenReturn("Resolve");
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(resolver);

    // Act
    VipAddressResolver actualResolver = emptyConfig.getResolver();
    String actualResolveResult = actualResolver.resolve("foo", DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    verify(resolver).resolve(eq("foo"), isA(IClientConfig.class));
    assertEquals("Resolve", actualResolveResult);
  }

  /**
   * Method under test:
   * {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}
   */
  @Test
  public void testResolveDeploymentContextbasedVipAddresses() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    String actualResolveDeploymentContextbasedVipAddressesResult = emptyConfig
        .resolveDeploymentContextbasedVipAddresses();

    // Assert
    assertNull(emptyConfig.getResolver());
    assertNull(actualResolveDeploymentContextbasedVipAddressesResult);
  }

  /**
   * Method under test:
   * {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}
   */
  @Test
  public void testResolveDeploymentContextbasedVipAddresses2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertNull(emptyConfig.resolveDeploymentContextbasedVipAddresses());
  }

  /**
   * Method under test:
   * {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}
   */
  @Test
  public void testResolveDeploymentContextbasedVipAddresses3() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.DeploymentContextBasedVipAddresses, 42);

    // Act
    String actualResolveDeploymentContextbasedVipAddressesResult = emptyConfig
        .resolveDeploymentContextbasedVipAddresses();

    // Assert
    assertTrue(emptyConfig.getResolver() instanceof SimpleVipAddressResolver);
    assertEquals("42", actualResolveDeploymentContextbasedVipAddressesResult);
  }

  /**
   * Method under test:
   * {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}
   */
  @Test
  public void testResolveDeploymentContextbasedVipAddresses4() {
    // Arrange
    VipAddressResolver resolver = mock(VipAddressResolver.class);
    when(resolver.resolve(Mockito.<String>any(), Mockito.<IClientConfig>any())).thenReturn("Resolve");
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(resolver);
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.DeploymentContextBasedVipAddresses, 42);

    // Act
    String actualResolveDeploymentContextbasedVipAddressesResult = emptyConfig
        .resolveDeploymentContextbasedVipAddresses();

    // Assert
    verify(resolver).resolve(eq("42"), isA(IClientConfig.class));
    assertEquals("Resolve", actualResolveDeploymentContextbasedVipAddressesResult);
  }

  /**
   * Method under test:
   * {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}
   */
  @Test
  public void testResolveDeploymentContextbasedVipAddresses5() {
    // Arrange
    VipAddressResolver resolver = mock(VipAddressResolver.class);
    when(resolver.resolve(Mockito.<String>any(), Mockito.<IClientConfig>any())).thenThrow(new RuntimeException("foo"));
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(resolver);
    emptyConfig.putDefaultIntegerProperty(CommonClientConfigKey.DeploymentContextBasedVipAddresses, 42);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    emptyConfig.resolveDeploymentContextbasedVipAddresses();
    verify(resolver).resolve(eq("42"), isA(IClientConfig.class));
  }

  /**
   * Method under test: {@link AbstractDefaultClientConfigImpl#getAppName()}
   */
  @Test
  public void testGetAppName() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getAppName());
  }

  /**
   * Method under test: {@link AbstractDefaultClientConfigImpl#getAppName()}
   */
  @Test
  public void testGetAppName2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertNull(emptyConfig.getAppName());
  }

  /**
   * Method under test: {@link AbstractDefaultClientConfigImpl#getVersion()}
   */
  @Test
  public void testGetVersion() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getVersion());
  }

  /**
   * Method under test: {@link AbstractDefaultClientConfigImpl#getVersion()}
   */
  @Test
  public void testGetVersion2() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act and Assert
    assertNull(emptyConfig.getVersion());
  }
}
