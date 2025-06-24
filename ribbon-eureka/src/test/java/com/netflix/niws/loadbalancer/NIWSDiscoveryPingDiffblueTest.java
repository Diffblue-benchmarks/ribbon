package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.loadbalancer.BaseLoadBalancer;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NIWSDiscoveryPingDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link NIWSDiscoveryPing}
   *   <li>{@link NIWSDiscoveryPing#setLb(BaseLoadBalancer)}
   *   <li>{@link NIWSDiscoveryPing#initWithNiwsConfig(IClientConfig)}
   *   <li>{@link NIWSDiscoveryPing#getLb()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NIWSDiscoveryPing.<init>()", "BaseLoadBalancer NIWSDiscoveryPing.getLb()",
      "void NIWSDiscoveryPing.initWithNiwsConfig(IClientConfig)", "void NIWSDiscoveryPing.setLb(BaseLoadBalancer)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    NIWSDiscoveryPing actualNiwsDiscoveryPing = new NIWSDiscoveryPing();
    BaseLoadBalancer lb = new BaseLoadBalancer();
    actualNiwsDiscoveryPing.setLb(lb);
    IClientConfig clientConfig = Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();
    actualNiwsDiscoveryPing.initWithNiwsConfig(clientConfig);
    BaseLoadBalancer actualLb = actualNiwsDiscoveryPing.getLb();

    // Assert
    assertNull(actualNiwsDiscoveryPing.getLoadBalancer());
    assertSame(lb, actualLb);
  }
}
