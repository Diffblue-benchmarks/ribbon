package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import java.util.HashMap;
import org.junit.Test;

public class NIWSDiscoveryPingDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link NIWSDiscoveryPing}
   *   <li>{@link NIWSDiscoveryPing#setLb(BaseLoadBalancer)}
   *   <li>{@link NIWSDiscoveryPing#initWithNiwsConfig(IClientConfig)}
   *   <li>{@link NIWSDiscoveryPing#getLb()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    NIWSDiscoveryPing actualNiwsDiscoveryPing = new NIWSDiscoveryPing();
    BaseLoadBalancer lb = new BaseLoadBalancer();
    actualNiwsDiscoveryPing.setLb(lb);
    actualNiwsDiscoveryPing.initWithNiwsConfig(DefaultClientConfigImpl.getEmptyConfig());

    // Assert that nothing has changed
    assertSame(lb, actualNiwsDiscoveryPing.getLb());
  }

  /**
   * Method under test: {@link NIWSDiscoveryPing#isAlive(Server)}
   */
  @Test
  public void testIsAlive() {
    // Arrange
    NIWSDiscoveryPing niwsDiscoveryPing = new NIWSDiscoveryPing();

    // Act and Assert
    assertTrue(niwsDiscoveryPing.isAlive(new Server("42")));
  }

  /**
   * Method under test: {@link NIWSDiscoveryPing#isAlive(Server)}
   */
  @Test
  public void testIsAlive2() {
    // Arrange
    NIWSDiscoveryPing niwsDiscoveryPing = new NIWSDiscoveryPing();
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    // Act and Assert
    assertTrue(niwsDiscoveryPing.isAlive(new DiscoveryEnabledServer(new InstanceInfo("42", "App Name", "App Group Name",
        "42 Main St", "Sid", port, securePort, "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo,
        "Host Name", InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, leaseInfo, true, new HashMap<>(),
        1L, 1L, InstanceInfo.ActionType.ADDED, "Asg Name"), true)));
  }

  /**
   * Method under test: {@link NIWSDiscoveryPing#isAlive(Server)}
   */
  @Test
  public void testIsAlive3() {
    // Arrange, Act and Assert
    assertTrue((new NIWSDiscoveryPing()).isAlive(null));
  }

  /**
   * Method under test: {@link NIWSDiscoveryPing#isAlive(Server)}
   */
  @Test
  public void testIsAlive4() {
    // Arrange
    NIWSDiscoveryPing niwsDiscoveryPing = new NIWSDiscoveryPing();
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    // Act and Assert
    assertTrue(niwsDiscoveryPing.isAlive(new DiscoveryEnabledServer(new InstanceInfo("42", "App Name", "App Group Name",
        "42 Main St", "Sid", port, securePort, "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo,
        "Host Name", null, InstanceInfo.InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L,
        InstanceInfo.ActionType.ADDED, "Asg Name"), true)));
  }

  /**
   * Method under test: {@link NIWSDiscoveryPing#isAlive(Server)}
   */
  @Test
  public void testIsAlive5() {
    // Arrange
    NIWSDiscoveryPing niwsDiscoveryPing = new NIWSDiscoveryPing();
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    // Act and Assert
    assertFalse(niwsDiscoveryPing.isAlive(new DiscoveryEnabledServer(new InstanceInfo("42", "App Name",
        "App Group Name", "42 Main St", "Sid", port, securePort, "https://example.org/example",
        "https://example.org/example", "https://example.org/example", "https://example.org/example", "42 Main St",
        "42 Main St", 1, dataCenterInfo, "Host Name", InstanceInfo.InstanceStatus.DOWN, InstanceInfo.InstanceStatus.UP,
        leaseInfo, true, new HashMap<>(), 1L, 1L, InstanceInfo.ActionType.ADDED, "Asg Name"), true)));
  }
}
