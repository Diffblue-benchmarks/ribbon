package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.SimpleVipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import javax.inject.Provider;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DiscoveryEnabledNIWSServerListDiffblueTest {
  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList()}.
   *
   * <p>Method under test: {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>()"})
  public void testNewDiscoveryEnabledNIWSServerList() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList();

    // Assert
    assertNull(actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.clientName);
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(IClientConfig)"})
  public void testNewDiscoveryEnabledNIWSServerList_givenTrue() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withForceClientPortConfiguration(true);
    newBuilderResult.withDeploymentContextBasedVipAddresses("${xx}");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList(clientConfig);

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig,
   * Provider)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig, Provider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(IClientConfig, Provider)"})
  public void testNewDiscoveryEnabledNIWSServerList_givenTrue2() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withForceClientPortConfiguration(true);
    newBuilderResult.withDeploymentContextBasedVipAddresses("${xx}");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList(clientConfig, mock(Provider.class));

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig)}.
   *
   * <ul>
   *   <li>Given {@code ${xx}}.
   *   <li>Then return VipAddresses is {@code ${xx}}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(IClientConfig)"})
  public void testNewDiscoveryEnabledNIWSServerList_givenXx_thenReturnVipAddressesIsXx() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("${xx}");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList(clientConfig);

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig,
   * Provider)}.
   *
   * <ul>
   *   <li>Given {@code ${xx}}.
   *   <li>Then return VipAddresses is {@code ${xx}}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig, Provider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(IClientConfig, Provider)"})
  public void testNewDiscoveryEnabledNIWSServerList_givenXx_thenReturnVipAddressesIsXx2() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("${xx}");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList(clientConfig, mock(Provider.class));

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig)}.
   *
   * <ul>
   *   <li>Then return {@link DiscoveryEnabledNIWSServerList#isSecure}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(IClientConfig)"})
  public void testNewDiscoveryEnabledNIWSServerList_thenReturnIsSecure() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withSecure(true);
    newBuilderResult.withForceClientPortConfiguration(true);
    newBuilderResult.withDeploymentContextBasedVipAddresses("${xx}");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList(clientConfig);

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig,
   * Provider)}.
   *
   * <ul>
   *   <li>Then return {@link DiscoveryEnabledNIWSServerList#isSecure}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig, Provider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(IClientConfig, Provider)"})
  public void testNewDiscoveryEnabledNIWSServerList_thenReturnIsSecure2() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withSecure(true);
    newBuilderResult.withForceClientPortConfiguration(true);
    newBuilderResult.withDeploymentContextBasedVipAddresses("${xx}");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList(clientConfig, mock(Provider.class));

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig)}.
   *
   * <ul>
   *   <li>Then return VipAddresses is {@code 42 Main St}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(IClientConfig)"})
  public void testNewDiscoveryEnabledNIWSServerList_thenReturnVipAddressesIs42MainSt() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList(clientConfig);

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("42 Main St", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig,
   * Provider)}.
   *
   * <ul>
   *   <li>Then return VipAddresses is {@code 42 Main St}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(IClientConfig, Provider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(IClientConfig, Provider)"})
  public void testNewDiscoveryEnabledNIWSServerList_thenReturnVipAddressesIs42MainSt2() {
    // Arrange
    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList(clientConfig, mock(Provider.class));

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("42 Main St", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}.
   *
   * <ul>
   *   <li>Then return VipAddresses is {@code 42 Main St}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(String)"})
  public void testNewDiscoveryEnabledNIWSServerList_thenReturnVipAddressesIs42MainSt3() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList("42 Main St");

    // Assert
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("42 Main St", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String, Provider)}.
   *
   * <ul>
   *   <li>Then return VipAddresses is {@code 42 Main St}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String, Provider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(String, Provider)"})
  public void testNewDiscoveryEnabledNIWSServerList_thenReturnVipAddressesIs42MainSt4() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList("42 Main St", mock(Provider.class));

    // Assert
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("42 Main St", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}.
   *
   * <ul>
   *   <li>When {@code ${xx}}.
   *   <li>Then return VipAddresses is {@code ${xx}}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(String)"})
  public void testNewDiscoveryEnabledNIWSServerList_whenXx_thenReturnVipAddressesIsXx() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList("${xx}");

    // Assert
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String, Provider)}.
   *
   * <ul>
   *   <li>When {@code ${xx}}.
   *   <li>Then return VipAddresses is {@code ${xx}}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String, Provider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(String, Provider)"})
  public void testNewDiscoveryEnabledNIWSServerList_whenXx_thenReturnVipAddressesIsXx2() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList("${xx}", mock(Provider.class));

    // Assert
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
    assertNull(actualDiscoveryEnabledNIWSServerList.datacenter);
    assertNull(actualDiscoveryEnabledNIWSServerList.targetRegion);
    assertEquals(7001, actualDiscoveryEnabledNIWSServerList.overridePort);
    assertFalse(actualDiscoveryEnabledNIWSServerList.isSecure);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseIpAddr);
    assertFalse(actualDiscoveryEnabledNIWSServerList.shouldUseOverridePort);
    assertTrue(actualDiscoveryEnabledNIWSServerList.getInitialListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.getUpdatedListOfServers().isEmpty());
    assertTrue(actualDiscoveryEnabledNIWSServerList.prioritizeVipAddressBasedServers);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#initWithNiwsConfig(IClientConfig)} with {@code
   * clientConfig}.
   *
   * <p>Method under test: {@link DiscoveryEnabledNIWSServerList#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig() {
    // Arrange
    DiscoveryEnabledNIWSServerList discoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("42 Main St");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    discoveryEnabledNIWSServerList.initWithNiwsConfig(clientConfig);

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", discoveryEnabledNIWSServerList.clientName);
    assertEquals("42 Main St", discoveryEnabledNIWSServerList.getVipAddresses());
    assertFalse(discoveryEnabledNIWSServerList.isSecure);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#initWithNiwsConfig(IClientConfig)} with {@code
   * clientConfig}.
   *
   * <p>Method under test: {@link DiscoveryEnabledNIWSServerList#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig2() {
    // Arrange
    DiscoveryEnabledNIWSServerList discoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withDeploymentContextBasedVipAddresses("${xx}");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    discoveryEnabledNIWSServerList.initWithNiwsConfig(clientConfig);

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", discoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", discoveryEnabledNIWSServerList.getVipAddresses());
    assertFalse(discoveryEnabledNIWSServerList.isSecure);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#initWithNiwsConfig(IClientConfig)} with {@code
   * clientConfig}.
   *
   * <p>Method under test: {@link DiscoveryEnabledNIWSServerList#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig3() {
    // Arrange
    DiscoveryEnabledNIWSServerList discoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withSecure(true);
    newBuilderResult.withForceClientPortConfiguration(true);
    newBuilderResult.withDeploymentContextBasedVipAddresses("${xx}");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    discoveryEnabledNIWSServerList.initWithNiwsConfig(clientConfig);

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", discoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", discoveryEnabledNIWSServerList.getVipAddresses());
    assertTrue(discoveryEnabledNIWSServerList.isSecure);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#initWithNiwsConfig(IClientConfig)} with {@code
   * clientConfig}.
   *
   * <ul>
   *   <li>Given {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DiscoveryEnabledNIWSServerList#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig_givenTrue() {
    // Arrange
    DiscoveryEnabledNIWSServerList discoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withForceClientPortConfiguration(true);
    newBuilderResult.withDeploymentContextBasedVipAddresses("${xx}");
    IClientConfig clientConfig =
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    // Act
    discoveryEnabledNIWSServerList.initWithNiwsConfig(clientConfig);

    // Assert
    assertTrue(
        ((DefaultClientConfigImpl) clientConfig).getResolver() instanceof SimpleVipAddressResolver);
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    assertEquals("", discoveryEnabledNIWSServerList.clientName);
    assertEquals("${xx}", discoveryEnabledNIWSServerList.getVipAddresses());
    assertFalse(discoveryEnabledNIWSServerList.isSecure);
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#getInitialListOfServers()}.
   *
   * <p>Method under test: {@link DiscoveryEnabledNIWSServerList#getInitialListOfServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List DiscoveryEnabledNIWSServerList.getInitialListOfServers()"})
  public void testGetInitialListOfServers() {
    // Arrange, Act and Assert
    assertTrue(new DiscoveryEnabledNIWSServerList().getInitialListOfServers().isEmpty());
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#getUpdatedListOfServers()}.
   *
   * <p>Method under test: {@link DiscoveryEnabledNIWSServerList#getUpdatedListOfServers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List DiscoveryEnabledNIWSServerList.getUpdatedListOfServers()"})
  public void testGetUpdatedListOfServers() {
    // Arrange, Act and Assert
    assertTrue(new DiscoveryEnabledNIWSServerList().getUpdatedListOfServers().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link DiscoveryEnabledNIWSServerList#setVipAddresses(String)}
   *   <li>{@link DiscoveryEnabledNIWSServerList#toString()}
   *   <li>{@link DiscoveryEnabledNIWSServerList#getVipAddresses()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String DiscoveryEnabledNIWSServerList.getVipAddresses()",
    "void DiscoveryEnabledNIWSServerList.setVipAddresses(String)",
    "String DiscoveryEnabledNIWSServerList.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange
    DiscoveryEnabledNIWSServerList discoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList();

    // Act
    discoveryEnabledNIWSServerList.setVipAddresses("42 Main St");
    String actualToStringResult = discoveryEnabledNIWSServerList.toString();

    // Assert
    assertEquals("42 Main St", discoveryEnabledNIWSServerList.getVipAddresses());
    assertEquals(
        "DiscoveryEnabledNIWSServerList:; clientName:null; Effective vipAddresses:42 Main St; isSecure:false;"
            + " datacenter:null",
        actualToStringResult);
  }
}
