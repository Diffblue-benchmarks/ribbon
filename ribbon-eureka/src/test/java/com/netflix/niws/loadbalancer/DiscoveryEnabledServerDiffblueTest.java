package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import java.util.HashMap;
import org.junit.Test;

public class DiscoveryEnabledServerDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DiscoveryEnabledServer#getInstanceInfo()}
   *   <li>{@link DiscoveryEnabledServer#getMetaInfo()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name",
        InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L,
        InstanceInfo.ActionType.ADDED, "Asg Name");

    DiscoveryEnabledServer discoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, true);

    // Act
    InstanceInfo actualInstanceInfo = discoveryEnabledServer.getInstanceInfo();
    discoveryEnabledServer.getMetaInfo();

    // Assert
    assertSame(instanceInfo, actualInstanceInfo);
  }

  /**
   * Method under test:
   * {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean)}
   */
  @Test
  public void testNewDiscoveryEnabledServer() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name",
        InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L,
        InstanceInfo.ActionType.ADDED, "Asg Name");

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
   * Method under test:
   * {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean)}
   */
  @Test
  public void testNewDiscoveryEnabledServer2() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(false, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name",
        InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L,
        InstanceInfo.ActionType.ADDED, "Asg Name");

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
   * Method under test:
   * {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean)}
   */
  @Test
  public void testNewDiscoveryEnabledServer3() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name",
        InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L,
        InstanceInfo.ActionType.ADDED, "Asg Name");

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
   * Method under test:
   * {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}
   */
  @Test
  public void testNewDiscoveryEnabledServer4() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name",
        InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L,
        InstanceInfo.ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, true, true);

    // Assert
    assertEquals("42 Main St", actualDiscoveryEnabledServer.getHost());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getHostPort());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getId());
    assertEquals("UNKNOWN", actualDiscoveryEnabledServer.getZone());
    assertNull(actualDiscoveryEnabledServer.getScheme());
    assertEquals(8080, actualDiscoveryEnabledServer.getPort());
    assertFalse(actualDiscoveryEnabledServer.isAlive());
    assertTrue(actualDiscoveryEnabledServer.isReadyToServe());
    assertSame(instanceInfo, actualDiscoveryEnabledServer.getInstanceInfo());
  }

  /**
   * Method under test:
   * {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}
   */
  @Test
  public void testNewDiscoveryEnabledServer5() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(false, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name",
        InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L,
        InstanceInfo.ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, true, true);

    // Assert
    assertEquals("42 Main St", actualDiscoveryEnabledServer.getHost());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getHostPort());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getId());
    assertEquals("UNKNOWN", actualDiscoveryEnabledServer.getZone());
    assertNull(actualDiscoveryEnabledServer.getScheme());
    assertEquals(8080, actualDiscoveryEnabledServer.getPort());
    assertFalse(actualDiscoveryEnabledServer.isAlive());
    assertTrue(actualDiscoveryEnabledServer.isReadyToServe());
    assertSame(instanceInfo, actualDiscoveryEnabledServer.getInstanceInfo());
  }

  /**
   * Method under test:
   * {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}
   */
  @Test
  public void testNewDiscoveryEnabledServer6() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name",
        InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L,
        InstanceInfo.ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, false, true);

    // Assert
    assertEquals("42 Main St", actualDiscoveryEnabledServer.getHost());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getHostPort());
    assertEquals("42 Main St:8080", actualDiscoveryEnabledServer.getId());
    assertEquals("UNKNOWN", actualDiscoveryEnabledServer.getZone());
    assertNull(actualDiscoveryEnabledServer.getScheme());
    assertEquals(8080, actualDiscoveryEnabledServer.getPort());
    assertFalse(actualDiscoveryEnabledServer.isAlive());
    assertTrue(actualDiscoveryEnabledServer.isReadyToServe());
    assertSame(instanceInfo, actualDiscoveryEnabledServer.getInstanceInfo());
  }

  /**
   * Method under test:
   * {@link DiscoveryEnabledServer#DiscoveryEnabledServer(InstanceInfo, boolean, boolean)}
   */
  @Test
  public void testNewDiscoveryEnabledServer7() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    InstanceInfo instanceInfo = new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
        securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name",
        InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, leaseInfo, true, new HashMap<>(), 1L, 1L,
        InstanceInfo.ActionType.ADDED, "Asg Name");

    // Act
    DiscoveryEnabledServer actualDiscoveryEnabledServer = new DiscoveryEnabledServer(instanceInfo, true, false);

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
