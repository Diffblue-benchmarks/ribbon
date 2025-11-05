package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.discovery.EurekaClient;
import javax.inject.Provider;
import org.junit.Test;

public class DiscoveryEnabledNIWSServerListDiffblueTest {
  /**
   * Method under test:
   * {@link DiscoveryEnabledNIWSServerList#getInitialListOfServers()}
   */
  @Test
  public void testGetInitialListOfServers() {
    // Arrange, Act and Assert
    assertTrue((new DiscoveryEnabledNIWSServerList()).getInitialListOfServers().isEmpty());
  }

  /**
   * Method under test:
   * {@link DiscoveryEnabledNIWSServerList#getUpdatedListOfServers()}
   */
  @Test
  public void testGetUpdatedListOfServers() {
    // Arrange, Act and Assert
    assertTrue((new DiscoveryEnabledNIWSServerList()).getUpdatedListOfServers().isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DiscoveryEnabledNIWSServerList#setVipAddresses(String)}
   *   <li>{@link DiscoveryEnabledNIWSServerList#toString()}
   *   <li>{@link DiscoveryEnabledNIWSServerList#getVipAddresses()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    DiscoveryEnabledNIWSServerList discoveryEnabledNIWSServerList = new DiscoveryEnabledNIWSServerList();

    // Act
    discoveryEnabledNIWSServerList.setVipAddresses("42 Main St");
    String actualToStringResult = discoveryEnabledNIWSServerList.toString();

    // Assert that nothing has changed
    assertEquals("42 Main St", discoveryEnabledNIWSServerList.getVipAddresses());
    assertEquals("DiscoveryEnabledNIWSServerList:; clientName:null; Effective vipAddresses:42 Main St; isSecure:false;"
        + " datacenter:null", actualToStringResult);
  }

  /**
   * Method under test:
   * {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList()}
   */
  @Test
  public void testNewDiscoveryEnabledNIWSServerList() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList = new DiscoveryEnabledNIWSServerList();

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
   * Method under test:
   * {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}
   */
  @Test
  public void testNewDiscoveryEnabledNIWSServerList2() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList = new DiscoveryEnabledNIWSServerList(
        "42 Main St");

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
   * Method under test:
   * {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}
   */
  @Test
  public void testNewDiscoveryEnabledNIWSServerList3() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList = new DiscoveryEnabledNIWSServerList(
        "17 High St");

    // Assert
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("17 High St", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
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
   * Method under test:
   * {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}
   */
  @Test
  public void testNewDiscoveryEnabledNIWSServerList4() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList = new DiscoveryEnabledNIWSServerList("${xx}");

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
   * Method under test:
   * {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String, Provider)}
   */
  @Test
  public void testNewDiscoveryEnabledNIWSServerList5() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList = new DiscoveryEnabledNIWSServerList(
        "42 Main St", mock(Provider.class));

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
   * Method under test:
   * {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String, Provider)}
   */
  @Test
  public void testNewDiscoveryEnabledNIWSServerList6() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList = new DiscoveryEnabledNIWSServerList("${xx}",
        mock(Provider.class));

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
}
