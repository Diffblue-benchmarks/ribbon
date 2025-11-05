package com.netflix.ribbon.testutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.appinfo.MyDataCenterInfo;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class MockedDiscoveryServerListTestDiffblueTest {
  /**
   * Method under test:
   * {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  public void testGetDummyInstanceInfo() {
    // Arrange and Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest.getDummyInstanceInfo("App Name",
        new ArrayList<>());

    // Assert
    assertTrue(actualDummyInstanceInfo.isEmpty());
  }

  /**
   * Method under test:
   * {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  public void testGetDummyInstanceInfo2() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));

    // Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest.getDummyInstanceInfo("App Name",
        serverList);

    // Assert
    assertEquals(1, actualDummyInstanceInfo.size());
    InstanceInfo getResult = actualDummyInstanceInfo.get(0);
    DataCenterInfo dataCenterInfo = getResult.getDataCenterInfo();
    assertTrue(dataCenterInfo instanceof MyDataCenterInfo);
    assertEquals("42", getResult.getHostName());
    assertEquals("42", getResult.getId());
    assertEquals("APP NAME", getResult.getAppName());
    assertEquals("na", getResult.getSID());
    assertEquals("unknown", getResult.getVersion());
    assertNull(getResult.getActionType());
    assertNull(getResult.getLeaseInfo());
    assertNull(getResult.isDirtyWithTime());
    assertNull(getResult.getASGName());
    assertNull(getResult.getAppGroupName());
    assertNull(getResult.getHealthCheckUrl());
    assertNull(getResult.getHomePageUrl());
    assertNull(getResult.getIPAddr());
    assertNull(getResult.getInstanceId());
    assertNull(getResult.getSecureHealthCheckUrl());
    assertNull(getResult.getSecureVipAddress());
    assertNull(getResult.getStatusPageUrl());
    assertNull(getResult.getVIPAddress());
    assertEquals(1, getResult.getCountryId());
    assertEquals(7002, getResult.getSecurePort());
    assertEquals(80, getResult.getPort());
    assertEquals(DataCenterInfo.Name.MyOwn, dataCenterInfo.getName());
    assertEquals(InstanceInfo.InstanceStatus.UNKNOWN, getResult.getOverriddenStatus());
    assertEquals(InstanceInfo.InstanceStatus.UP, getResult.getStatus());
    assertFalse(getResult.isCoordinatingDiscoveryServer());
    assertFalse(getResult.isDirty());
    assertTrue(getResult.getMetadata().isEmpty());
    assertTrue(getResult.getHealthCheckUrls().isEmpty());
  }

  /**
   * Method under test:
   * {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  public void testGetDummyInstanceInfo3() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));

    // Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest.getDummyInstanceInfo("App Name",
        serverList);

    // Assert
    assertEquals(2, actualDummyInstanceInfo.size());
    InstanceInfo getResult = actualDummyInstanceInfo.get(0);
    DataCenterInfo dataCenterInfo = getResult.getDataCenterInfo();
    assertTrue(dataCenterInfo instanceof MyDataCenterInfo);
    assertEquals("42", getResult.getHostName());
    assertEquals("42", getResult.getId());
    assertEquals("APP NAME", getResult.getAppName());
    assertEquals("na", getResult.getSID());
    assertEquals("unknown", getResult.getVersion());
    assertNull(getResult.getActionType());
    assertNull(getResult.getLeaseInfo());
    assertNull(getResult.isDirtyWithTime());
    assertNull(getResult.getASGName());
    assertNull(getResult.getAppGroupName());
    assertNull(getResult.getHealthCheckUrl());
    assertNull(getResult.getHomePageUrl());
    assertNull(getResult.getIPAddr());
    assertNull(getResult.getInstanceId());
    assertNull(getResult.getSecureHealthCheckUrl());
    assertNull(getResult.getSecureVipAddress());
    assertNull(getResult.getStatusPageUrl());
    assertNull(getResult.getVIPAddress());
    assertEquals(1, getResult.getCountryId());
    assertEquals(7002, getResult.getSecurePort());
    assertEquals(80, getResult.getPort());
    assertEquals(DataCenterInfo.Name.MyOwn, dataCenterInfo.getName());
    assertEquals(InstanceInfo.InstanceStatus.UNKNOWN, getResult.getOverriddenStatus());
    assertEquals(InstanceInfo.InstanceStatus.UP, getResult.getStatus());
    assertFalse(getResult.isCoordinatingDiscoveryServer());
    assertFalse(getResult.isDirty());
    assertTrue(getResult.getMetadata().isEmpty());
    assertTrue(getResult.getHealthCheckUrls().isEmpty());
    assertEquals(getResult, actualDummyInstanceInfo.get(1));
  }

  /**
   * Method under test:
   * {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  public void testGetDummyInstanceInfo4() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server(""));

    // Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest.getDummyInstanceInfo("App Name",
        serverList);

    // Assert
    assertEquals(1, actualDummyInstanceInfo.size());
    InstanceInfo getResult = actualDummyInstanceInfo.get(0);
    DataCenterInfo dataCenterInfo = getResult.getDataCenterInfo();
    assertTrue(dataCenterInfo instanceof MyDataCenterInfo);
    assertEquals("APP NAME", getResult.getAppName());
    assertEquals("na", getResult.getSID());
    assertEquals("unknown", getResult.getVersion());
    assertNull(getResult.getActionType());
    assertNull(getResult.getLeaseInfo());
    assertNull(getResult.isDirtyWithTime());
    assertNull(getResult.getASGName());
    assertNull(getResult.getAppGroupName());
    assertNull(getResult.getHealthCheckUrl());
    assertNull(getResult.getHomePageUrl());
    assertNull(getResult.getHostName());
    assertNull(getResult.getIPAddr());
    assertNull(getResult.getId());
    assertNull(getResult.getInstanceId());
    assertNull(getResult.getSecureHealthCheckUrl());
    assertNull(getResult.getSecureVipAddress());
    assertNull(getResult.getStatusPageUrl());
    assertNull(getResult.getVIPAddress());
    assertEquals(1, getResult.getCountryId());
    assertEquals(7002, getResult.getSecurePort());
    assertEquals(80, getResult.getPort());
    assertEquals(DataCenterInfo.Name.MyOwn, dataCenterInfo.getName());
    assertEquals(InstanceInfo.InstanceStatus.UNKNOWN, getResult.getOverriddenStatus());
    assertEquals(InstanceInfo.InstanceStatus.UP, getResult.getStatus());
    assertFalse(getResult.isCoordinatingDiscoveryServer());
    assertFalse(getResult.isDirty());
    assertTrue(getResult.getMetadata().isEmpty());
    assertTrue(getResult.getHealthCheckUrls().isEmpty());
  }

  /**
   * Method under test:
   * {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  public void testGetDummyInstanceInfo5() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    serverList.add(new DiscoveryEnabledServer(
        new InstanceInfo("42", "na", "na", "42 Main St", "na", port, securePort, "https://example.org/example",
            "https://example.org/example", "https://example.org/example", "https://example.org/example", "42 Main St",
            "42 Main St", 1, dataCenterInfo, "na", InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP,
            leaseInfo, true, new HashMap<>(), 1L, 1L, InstanceInfo.ActionType.ADDED, "na"),
        true));

    // Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest.getDummyInstanceInfo("App Name",
        serverList);

    // Assert
    assertEquals(1, actualDummyInstanceInfo.size());
    InstanceInfo getResult = actualDummyInstanceInfo.get(0);
    DataCenterInfo dataCenterInfo2 = getResult.getDataCenterInfo();
    assertTrue(dataCenterInfo2 instanceof MyDataCenterInfo);
    assertEquals("APP NAME", getResult.getAppName());
    assertEquals("na", getResult.getHostName());
    assertEquals("na", getResult.getId());
    assertEquals("na", getResult.getSID());
    assertEquals("unknown", getResult.getVersion());
    assertNull(getResult.getActionType());
    assertNull(getResult.getLeaseInfo());
    assertNull(getResult.isDirtyWithTime());
    assertNull(getResult.getASGName());
    assertNull(getResult.getAppGroupName());
    assertNull(getResult.getHealthCheckUrl());
    assertNull(getResult.getHomePageUrl());
    assertNull(getResult.getIPAddr());
    assertNull(getResult.getInstanceId());
    assertNull(getResult.getSecureHealthCheckUrl());
    assertNull(getResult.getSecureVipAddress());
    assertNull(getResult.getStatusPageUrl());
    assertNull(getResult.getVIPAddress());
    assertEquals(1, getResult.getCountryId());
    assertEquals(7002, getResult.getSecurePort());
    assertEquals(8080, getResult.getPort());
    assertEquals(DataCenterInfo.Name.MyOwn, dataCenterInfo2.getName());
    assertEquals(InstanceInfo.InstanceStatus.UNKNOWN, getResult.getOverriddenStatus());
    assertEquals(InstanceInfo.InstanceStatus.UP, getResult.getStatus());
    assertFalse(getResult.isCoordinatingDiscoveryServer());
    assertFalse(getResult.isDirty());
    assertTrue(getResult.getMetadata().isEmpty());
    assertTrue(getResult.getHealthCheckUrls().isEmpty());
  }

  /**
   * Method under test:
   * {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  public void testGetDummyInstanceInfo6() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server(""));

    // Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest
        .getDummyInstanceInfo("Passed in hostname is blank, not setting it", serverList);

    // Assert
    assertEquals(1, actualDummyInstanceInfo.size());
    InstanceInfo getResult = actualDummyInstanceInfo.get(0);
    DataCenterInfo dataCenterInfo = getResult.getDataCenterInfo();
    assertTrue(dataCenterInfo instanceof MyDataCenterInfo);
    assertEquals("PASSED IN HOSTNAME IS BLANK, NOT SETTING IT", getResult.getAppName());
    assertEquals("na", getResult.getSID());
    assertEquals("unknown", getResult.getVersion());
    assertNull(getResult.getActionType());
    assertNull(getResult.getLeaseInfo());
    assertNull(getResult.isDirtyWithTime());
    assertNull(getResult.getASGName());
    assertNull(getResult.getAppGroupName());
    assertNull(getResult.getHealthCheckUrl());
    assertNull(getResult.getHomePageUrl());
    assertNull(getResult.getHostName());
    assertNull(getResult.getIPAddr());
    assertNull(getResult.getId());
    assertNull(getResult.getInstanceId());
    assertNull(getResult.getSecureHealthCheckUrl());
    assertNull(getResult.getSecureVipAddress());
    assertNull(getResult.getStatusPageUrl());
    assertNull(getResult.getVIPAddress());
    assertEquals(1, getResult.getCountryId());
    assertEquals(7002, getResult.getSecurePort());
    assertEquals(80, getResult.getPort());
    assertEquals(DataCenterInfo.Name.MyOwn, dataCenterInfo.getName());
    assertEquals(InstanceInfo.InstanceStatus.UNKNOWN, getResult.getOverriddenStatus());
    assertEquals(InstanceInfo.InstanceStatus.UP, getResult.getStatus());
    assertFalse(getResult.isCoordinatingDiscoveryServer());
    assertFalse(getResult.isDirty());
    assertTrue(getResult.getMetadata().isEmpty());
    assertTrue(getResult.getHealthCheckUrls().isEmpty());
  }
}
