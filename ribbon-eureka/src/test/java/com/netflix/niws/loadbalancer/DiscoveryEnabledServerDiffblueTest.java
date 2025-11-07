package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.ActionType;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.appinfo.InstanceInfo.PortWrapper;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.appinfo.LeaseInfo.Builder;
import java.util.HashMap;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DiscoveryEnabledServerDiffblueTest {
  /**
   * Test {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}.
   * <ul>
   *   <li>Then return Host is {@code 42 Main St}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiscoveryEnabledServer.<init>(InstanceInfo, boolean, boolean)"})
  public void testNewDiscoveryEnabledServer_thenReturnHostIs42MainSt() {
    // Arrange
    PortWrapper port = new PortWrapper(true, 8080);

    PortWrapper securePort = new PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = Builder.newBuilder().build();
    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name", InstanceStatus.UP,
        InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L, ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, true, true);

    // Assert
    assertEquals("42 Main St", actualDiscoveryEnabledServer.getHost());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getHostPort());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getId());
    assertSame(instanceInfo, actualDiscoveryEnabledServer.getInstanceInfo());
  }

  /**
   * Test {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiscoveryEnabledServer.<init>(InstanceInfo, boolean)"})
  public void testNewDiscoveryEnabledServer_whenFalse() {
    // Arrange
    PortWrapper port = new PortWrapper(true, 8080);

    PortWrapper securePort = new PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = Builder.newBuilder().build();
    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name", InstanceStatus.UP,
        InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L, ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, false);

    // Assert
    assertEquals("Host Name", actualDiscoveryEnabledServer.getHost());
    assertEquals("Host Name:8080", actualDiscoveryEnabledServer.getHostPort());
    assertEquals("Host Name:8080", actualDiscoveryEnabledServer.getId());
    assertEquals("UNKNOWN", actualDiscoveryEnabledServer.getZone());
    assertNull(actualDiscoveryEnabledServer.getScheme());
    assertEquals(8080, actualDiscoveryEnabledServer.getPort());
    assertFalse(actualDiscoveryEnabledServer.isAlive());
    assertTrue(actualDiscoveryEnabledServer.isReadyToServe());
    assertSame(instanceInfo, actualDiscoveryEnabledServer.getInstanceInfo());
  }

  /**
   * Test {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return Host is {@code 42 Main St}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiscoveryEnabledServer.<init>(InstanceInfo, boolean, boolean)"})
  public void testNewDiscoveryEnabledServer_whenFalse_thenReturnHostIs42MainSt() {
    // Arrange
    PortWrapper port = new PortWrapper(true, 8080);

    PortWrapper securePort = new PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = Builder.newBuilder().build();
    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name", InstanceStatus.UP,
        InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L, ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, false, true);

    // Assert
    assertEquals("42 Main St", actualDiscoveryEnabledServer.getHost());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getHostPort());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getId());
    assertSame(instanceInfo, actualDiscoveryEnabledServer.getInstanceInfo());
  }

  /**
   * Test {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return Host is {@code Host Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiscoveryEnabledServer.<init>(InstanceInfo, boolean, boolean)"})
  public void testNewDiscoveryEnabledServer_whenFalse_thenReturnHostIsHostName() {
    // Arrange
    PortWrapper port = new PortWrapper(true, 8080);

    PortWrapper securePort = new PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = Builder.newBuilder().build();
    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name", InstanceStatus.UP,
        InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L, ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, true, false);

    // Assert
    assertEquals("Host Name", actualDiscoveryEnabledServer.getHost());
    assertEquals("Host Name:8080", actualDiscoveryEnabledServer.getHostPort());
    assertEquals("Host Name:8080", actualDiscoveryEnabledServer.getId());
    assertSame(instanceInfo, actualDiscoveryEnabledServer.getInstanceInfo());
  }

  /**
   * Test {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean)}.
   * <ul>
   *   <li>When {@link InstanceInfo.PortWrapper#PortWrapper(boolean, int)} with enabled is {@code false} and port is {@code 8080}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiscoveryEnabledServer.<init>(InstanceInfo, boolean)"})
  public void testNewDiscoveryEnabledServer_whenPortWrapperWithEnabledIsFalseAndPortIs8080() {
    // Arrange
    PortWrapper port = new PortWrapper(true, 8080);

    PortWrapper securePort = new PortWrapper(false, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = Builder.newBuilder().build();
    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name", InstanceStatus.UP,
        InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L, ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, true);

    // Assert
    assertEquals("Host Name", actualDiscoveryEnabledServer.getHost());
    assertEquals("Host Name:8080", actualDiscoveryEnabledServer.getHostPort());
    assertEquals("Host Name:8080", actualDiscoveryEnabledServer.getId());
    assertEquals("UNKNOWN", actualDiscoveryEnabledServer.getZone());
    assertNull(actualDiscoveryEnabledServer.getScheme());
    assertEquals(8080, actualDiscoveryEnabledServer.getPort());
    assertFalse(actualDiscoveryEnabledServer.isAlive());
    assertTrue(actualDiscoveryEnabledServer.isReadyToServe());
    assertSame(instanceInfo, actualDiscoveryEnabledServer.getInstanceInfo());
  }

  /**
   * Test {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}.
   * <ul>
   *   <li>When {@link InstanceInfo.PortWrapper#PortWrapper(boolean, int)} with enabled is {@code false} and port is {@code 8080}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiscoveryEnabledServer.<init>(InstanceInfo, boolean, boolean)"})
  public void testNewDiscoveryEnabledServer_whenPortWrapperWithEnabledIsFalseAndPortIs80802() {
    // Arrange
    PortWrapper port = new PortWrapper(true, 8080);

    PortWrapper securePort = new PortWrapper(false, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = Builder.newBuilder().build();
    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name", InstanceStatus.UP,
        InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L, ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, true, true);

    // Assert
    assertEquals("42 Main St", actualDiscoveryEnabledServer.getHost());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getHostPort());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getId());
    assertSame(instanceInfo, actualDiscoveryEnabledServer.getInstanceInfo());
  }

  /**
   * Test {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean)}.
   * <ul>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiscoveryEnabledServer.<init>(InstanceInfo, boolean)"})
  public void testNewDiscoveryEnabledServer_whenTrue() {
    // Arrange
    PortWrapper port = new PortWrapper(true, 8080);

    PortWrapper securePort = new PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = Builder.newBuilder().build();
    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name", InstanceStatus.UP,
        InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L, ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, true);

    // Assert
    assertEquals("Host Name", actualDiscoveryEnabledServer.getHost());
    assertEquals("Host Name:8080", actualDiscoveryEnabledServer.getHostPort());
    assertEquals("Host Name:8080", actualDiscoveryEnabledServer.getId());
    assertEquals("UNKNOWN", actualDiscoveryEnabledServer.getZone());
    assertNull(actualDiscoveryEnabledServer.getScheme());
    assertEquals(8080, actualDiscoveryEnabledServer.getPort());
    assertFalse(actualDiscoveryEnabledServer.isAlive());
    assertTrue(actualDiscoveryEnabledServer.isReadyToServe());
    assertSame(instanceInfo, actualDiscoveryEnabledServer.getInstanceInfo());
  }
}
