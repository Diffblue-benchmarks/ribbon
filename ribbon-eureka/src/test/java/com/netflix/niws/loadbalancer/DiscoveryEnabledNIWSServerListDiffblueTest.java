package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}.
   *
   * <p>Method under test: {@link
   * DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(String)"})
  public void testNewDiscoveryEnabledNIWSServerList2() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList =
        new DiscoveryEnabledNIWSServerList(
            "\"test-service-1.netflix.com,test-service-2.netflix.com,test-service-3.netflix.com\"");

    // Assert
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals(
        "\"test-service-1.netflix.com,test-service-2.netflix.com,test-service-3.netflix.com\"",
        actualDiscoveryEnabledNIWSServerList.getVipAddresses());
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
  public void testNewDiscoveryEnabledNIWSServerList_thenReturnVipAddressesIs42MainSt() {
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
    discoveryEnabledNIWSServerList.setVipAddresses(
        "\"test-vip-address-1,test-vip-address-2,test-vip-address-3\"");
    String actualToStringResult = discoveryEnabledNIWSServerList.toString();

    // Assert
    assertEquals(
        "DiscoveryEnabledNIWSServerList:; clientName:null; Effective vipAddresses:\"test-vip-address-1,test-vip"
            + "-address-2,test-vip-address-3\"; isSecure:false; datacenter:null",
        actualToStringResult);
    assertEquals(
        "\"test-vip-address-1,test-vip-address-2,test-vip-address-3\"",
        discoveryEnabledNIWSServerList.getVipAddresses());
  }
}
