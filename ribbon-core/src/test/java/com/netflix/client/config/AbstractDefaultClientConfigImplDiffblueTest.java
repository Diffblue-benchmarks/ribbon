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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.SimpleVipAddressResolver;
import com.netflix.client.VipAddressResolver;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class AbstractDefaultClientConfigImplDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link AbstractDefaultClientConfigImpl#setVipAddressResolver(VipAddressResolver)}.
   *
   * <p>Method under test: {@link
   * AbstractDefaultClientConfigImpl#setVipAddressResolver(VipAddressResolver)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractDefaultClientConfigImpl.setVipAddressResolver(VipAddressResolver)"
  })
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
   * Test {@link AbstractDefaultClientConfigImpl#getResolver()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractDefaultClientConfigImpl#getResolver()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"VipAddressResolver AbstractDefaultClientConfigImpl.getResolver()"})
  public void testGetResolver_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getResolver());
  }

  /**
   * Test {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}.
   *
   * <p>Method under test: {@link
   * AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String AbstractDefaultClientConfigImpl.resolveDeploymentContextbasedVipAddresses()"
  })
  public void testResolveDeploymentContextbasedVipAddresses() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.putDefaultIntegerProperty(
        CommonClientConfigKey.DeploymentContextBasedVipAddresses, 42);

    // Act
    String actualResolveDeploymentContextbasedVipAddressesResult =
        emptyConfig.resolveDeploymentContextbasedVipAddresses();

    // Assert
    assertTrue(emptyConfig.getResolver() instanceof SimpleVipAddressResolver);
    assertEquals("42", actualResolveDeploymentContextbasedVipAddressesResult);
  }

  /**
   * Test {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}.
   *
   * <ul>
   *   <li>Then EmptyConfig Resolver is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String AbstractDefaultClientConfigImpl.resolveDeploymentContextbasedVipAddresses()"
  })
  public void testResolveDeploymentContextbasedVipAddresses_thenEmptyConfigResolverIsNull() {
    // Arrange
    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();

    // Act
    String actualResolveDeploymentContextbasedVipAddressesResult =
        emptyConfig.resolveDeploymentContextbasedVipAddresses();

    // Assert
    assertNull(emptyConfig.getResolver());
    assertNull(actualResolveDeploymentContextbasedVipAddressesResult);
  }

  /**
   * Test {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}.
   *
   * <ul>
   *   <li>Then return {@code Resolve}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String AbstractDefaultClientConfigImpl.resolveDeploymentContextbasedVipAddresses()"
  })
  public void testResolveDeploymentContextbasedVipAddresses_thenReturnResolve() {
    // Arrange
    VipAddressResolver resolver = mock(VipAddressResolver.class);
    when(resolver.resolve(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenReturn("Resolve");

    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(resolver);
    emptyConfig.putDefaultIntegerProperty(
        CommonClientConfigKey.DeploymentContextBasedVipAddresses, 42);

    // Act
    String actualResolveDeploymentContextbasedVipAddressesResult =
        emptyConfig.resolveDeploymentContextbasedVipAddresses();

    // Assert
    verify(resolver).resolve(eq("42"), isA(IClientConfig.class));
    assertEquals("Resolve", actualResolveDeploymentContextbasedVipAddressesResult);
  }

  /**
   * Test {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String AbstractDefaultClientConfigImpl.resolveDeploymentContextbasedVipAddresses()"
  })
  public void testResolveDeploymentContextbasedVipAddresses_thenThrowRuntimeException() {
    // Arrange
    VipAddressResolver resolver = mock(VipAddressResolver.class);
    when(resolver.resolve(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new RuntimeException());

    DefaultClientConfigImpl emptyConfig = DefaultClientConfigImpl.getEmptyConfig();
    emptyConfig.setVipAddressResolver(resolver);
    emptyConfig.putDefaultIntegerProperty(
        CommonClientConfigKey.DeploymentContextBasedVipAddresses, 42);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    emptyConfig.resolveDeploymentContextbasedVipAddresses();
    verify(resolver).resolve(eq("42"), isA(IClientConfig.class));
  }

  /**
   * Test {@link AbstractDefaultClientConfigImpl#getAppName()}.
   *
   * <p>Method under test: {@link AbstractDefaultClientConfigImpl#getAppName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String AbstractDefaultClientConfigImpl.getAppName()"})
  public void testGetAppName() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getAppName());
  }

  /**
   * Test {@link AbstractDefaultClientConfigImpl#getVersion()}.
   *
   * <p>Method under test: {@link AbstractDefaultClientConfigImpl#getVersion()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String AbstractDefaultClientConfigImpl.getVersion()"})
  public void testGetVersion() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getVersion());
  }
}
