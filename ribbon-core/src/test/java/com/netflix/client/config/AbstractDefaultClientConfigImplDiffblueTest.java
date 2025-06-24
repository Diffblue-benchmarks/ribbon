package com.netflix.client.config;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.VipAddressResolver;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractDefaultClientConfigImplDiffblueTest {
  /**
   * Test {@link AbstractDefaultClientConfigImpl#setVipAddressResolver(VipAddressResolver)}.
   * <p>
   * Method under test: {@link AbstractDefaultClientConfigImpl#setVipAddressResolver(VipAddressResolver)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractDefaultClientConfigImpl.setVipAddressResolver(VipAddressResolver)"})
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
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractDefaultClientConfigImpl#getResolver()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VipAddressResolver AbstractDefaultClientConfigImpl.getResolver()"})
  public void testGetResolver_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getResolver());
  }

  /**
   * Test {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}.
   * <p>
   * Method under test: {@link AbstractDefaultClientConfigImpl#resolveDeploymentContextbasedVipAddresses()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String AbstractDefaultClientConfigImpl.resolveDeploymentContextbasedVipAddresses()"})
  public void testResolveDeploymentContextbasedVipAddresses() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().resolveDeploymentContextbasedVipAddresses());
  }

  /**
   * Test {@link AbstractDefaultClientConfigImpl#getAppName()}.
   * <p>
   * Method under test: {@link AbstractDefaultClientConfigImpl#getAppName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String AbstractDefaultClientConfigImpl.getAppName()"})
  public void testGetAppName() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getAppName());
  }

  /**
   * Test {@link AbstractDefaultClientConfigImpl#getVersion()}.
   * <p>
   * Method under test: {@link AbstractDefaultClientConfigImpl#getVersion()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String AbstractDefaultClientConfigImpl.getVersion()"})
  public void testGetVersion() {
    // Arrange, Act and Assert
    assertNull(DefaultClientConfigImpl.getEmptyConfig().getVersion());
  }
}
