package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.ActionType;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.appinfo.InstanceInfo.PortWrapper;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.appinfo.LeaseInfo.Builder;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import java.util.HashMap;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NIWSDiscoveryPingDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link NIWSDiscoveryPing}
   *   <li>{@link NIWSDiscoveryPing#setLb(BaseLoadBalancer)}
   *   <li>{@link NIWSDiscoveryPing#initWithNiwsConfig(IClientConfig)}
   *   <li>{@link NIWSDiscoveryPing#getLb()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void NIWSDiscoveryPing.<init>()",
    "BaseLoadBalancer NIWSDiscoveryPing.getLb()",
    "void NIWSDiscoveryPing.initWithNiwsConfig(IClientConfig)",
    "void NIWSDiscoveryPing.setLb(BaseLoadBalancer)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    NIWSDiscoveryPing actualNiwsDiscoveryPing = new NIWSDiscoveryPing();
    BaseLoadBalancer lb = new BaseLoadBalancer();
    actualNiwsDiscoveryPing.setLb(lb);
    actualNiwsDiscoveryPing.initWithNiwsConfig(
        IClientConfig.Builder.newBuilder()
            .ignoreUserTokenInConnectionPoolForSecureClient(true)
            .build());
    BaseLoadBalancer actualLb = actualNiwsDiscoveryPing.getLb();

    // Assert
    assertNull(actualNiwsDiscoveryPing.getLoadBalancer());
    assertSame(lb, actualLb);
  }

  /**
   * Test {@link NIWSDiscoveryPing#isAlive(Server)}.
   *
   * <ul>
   *   <li>Given {@code DOWN}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link NIWSDiscoveryPing#isAlive(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean NIWSDiscoveryPing.isAlive(Server)"})
  public void testIsAlive_givenDown_thenReturnFalse() {
    // Arrange
    NIWSDiscoveryPing niwsDiscoveryPing = new NIWSDiscoveryPing();
    PortWrapper port = new PortWrapper(true, 8080);
    PortWrapper securePort = new PortWrapper(true, 8080);
    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = Builder.newBuilder().build();

    InstanceInfo ii =
        new InstanceInfo(
            "42",
            "App Name",
            "App Group Name",
            "42 Main St",
            "Sid",
            port,
            securePort,
            "https://example.org/example",
            "https://example.org/example",
            "https://example.org/example",
            "https://example.org/example",
            "42 Main St",
            "42 Main St",
            1,
            dataCenterInfo,
            "Host Name",
            InstanceStatus.UP,
            InstanceStatus.UP,
            leaseInfo,
            true,
            new HashMap<>(),
            1L,
            1L,
            ActionType.ADDED,
            "Asg Name");

    InstanceInfo instanceInfo = new InstanceInfo(ii);
    instanceInfo.setStatus(InstanceStatus.DOWN);

    // Act
    boolean actualIsAliveResult =
        niwsDiscoveryPing.isAlive(new DiscoveryEnabledServer(instanceInfo, true));

    // Assert
    assertFalse(actualIsAliveResult);
  }

  /**
   * Test {@link NIWSDiscoveryPing#isAlive(Server)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link InstanceInfo#InstanceInfo(InstanceInfo)} with ii is {@link
   *       InstanceInfo#InstanceInfo(String, String, String, String, String, PortWrapper,
   *       PortWrapper, String, String, String, String, String, String, int, DataCenterInfo, String,
   *       InstanceStatus, InstanceStatus, LeaseInfo, Boolean, HashMap, Long, Long, ActionType,
   *       String)} Status is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NIWSDiscoveryPing#isAlive(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean NIWSDiscoveryPing.isAlive(Server)"})
  public void testIsAlive_givenNull_whenInstanceInfoWithIiIsInstanceInfoStatusIsNull() {
    // Arrange
    NIWSDiscoveryPing niwsDiscoveryPing = new NIWSDiscoveryPing();
    PortWrapper port = new PortWrapper(true, 8080);
    PortWrapper securePort = new PortWrapper(true, 8080);
    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = Builder.newBuilder().build();

    InstanceInfo ii =
        new InstanceInfo(
            "42",
            "App Name",
            "App Group Name",
            "42 Main St",
            "Sid",
            port,
            securePort,
            "https://example.org/example",
            "https://example.org/example",
            "https://example.org/example",
            "https://example.org/example",
            "42 Main St",
            "42 Main St",
            1,
            dataCenterInfo,
            "Host Name",
            InstanceStatus.UP,
            InstanceStatus.UP,
            leaseInfo,
            true,
            new HashMap<>(),
            1L,
            1L,
            ActionType.ADDED,
            "Asg Name");

    InstanceInfo instanceInfo = new InstanceInfo(ii);
    instanceInfo.setStatus(null);

    // Act
    boolean actualIsAliveResult =
        niwsDiscoveryPing.isAlive(new DiscoveryEnabledServer(instanceInfo, true));

    // Assert
    assertTrue(actualIsAliveResult);
  }

  /**
   * Test {@link NIWSDiscoveryPing#isAlive(Server)}.
   *
   * <ul>
   *   <li>Given {@code UP}.
   *   <li>When {@link InstanceInfo#InstanceInfo(InstanceInfo)} with ii is {@link
   *       InstanceInfo#InstanceInfo(String, String, String, String, String, PortWrapper,
   *       PortWrapper, String, String, String, String, String, String, int, DataCenterInfo, String,
   *       InstanceStatus, InstanceStatus, LeaseInfo, Boolean, HashMap, Long, Long, ActionType,
   *       String)} Status is {@code UP}.
   * </ul>
   *
   * <p>Method under test: {@link NIWSDiscoveryPing#isAlive(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean NIWSDiscoveryPing.isAlive(Server)"})
  public void testIsAlive_givenUp_whenInstanceInfoWithIiIsInstanceInfoStatusIsUp() {
    // Arrange
    NIWSDiscoveryPing niwsDiscoveryPing = new NIWSDiscoveryPing();
    PortWrapper port = new PortWrapper(true, 8080);
    PortWrapper securePort = new PortWrapper(true, 8080);
    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = Builder.newBuilder().build();

    InstanceInfo ii =
        new InstanceInfo(
            "42",
            "App Name",
            "App Group Name",
            "42 Main St",
            "Sid",
            port,
            securePort,
            "https://example.org/example",
            "https://example.org/example",
            "https://example.org/example",
            "https://example.org/example",
            "42 Main St",
            "42 Main St",
            1,
            dataCenterInfo,
            "Host Name",
            InstanceStatus.UP,
            InstanceStatus.UP,
            leaseInfo,
            true,
            new HashMap<>(),
            1L,
            1L,
            ActionType.ADDED,
            "Asg Name");

    InstanceInfo instanceInfo = new InstanceInfo(ii);
    instanceInfo.setStatus(InstanceStatus.UP);

    // Act
    boolean actualIsAliveResult =
        niwsDiscoveryPing.isAlive(new DiscoveryEnabledServer(instanceInfo, true));

    // Assert
    assertTrue(actualIsAliveResult);
  }

  /**
   * Test {@link NIWSDiscoveryPing#isAlive(Server)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link NIWSDiscoveryPing#isAlive(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean NIWSDiscoveryPing.isAlive(Server)"})
  public void testIsAlive_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new NIWSDiscoveryPing().isAlive(null));
  }

  /**
   * Test {@link NIWSDiscoveryPing#isAlive(Server)}.
   *
   * <ul>
   *   <li>When {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link NIWSDiscoveryPing#isAlive(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean NIWSDiscoveryPing.isAlive(Server)"})
  public void testIsAlive_whenServerWithIdIs42_thenReturnTrue() {
    // Arrange
    NIWSDiscoveryPing niwsDiscoveryPing = new NIWSDiscoveryPing();

    // Act
    boolean actualIsAliveResult = niwsDiscoveryPing.isAlive(new Server("42"));

    // Assert
    assertTrue(actualIsAliveResult);
  }
}
