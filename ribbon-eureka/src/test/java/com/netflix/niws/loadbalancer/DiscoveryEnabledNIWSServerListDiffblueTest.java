package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DiscoveryEnabledNIWSServerListDiffblueTest {
  /**
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList()}.
   * <p>
   * Method under test: {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>()"})
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
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}.
   * <ul>
   *   <li>Then return VipAddresses is {@code 42 Main St}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(String)"})
  public void testNewDiscoveryEnabledNIWSServerList_thenReturnVipAddressesIs42MainSt() {
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
   * Test {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}.
   * <ul>
   *   <li>When {@code ${UU}}.</li>
   *   <li>Then return VipAddresses is {@code ${UU}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DiscoveryEnabledNIWSServerList#DiscoveryEnabledNIWSServerList(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiscoveryEnabledNIWSServerList.<init>(String)"})
  public void testNewDiscoveryEnabledNIWSServerList_whenUu_thenReturnVipAddressesIsUu() {
    // Arrange and Act
    DiscoveryEnabledNIWSServerList actualDiscoveryEnabledNIWSServerList = new DiscoveryEnabledNIWSServerList("${UU}");

    // Assert
    assertEquals("", actualDiscoveryEnabledNIWSServerList.clientName);
    assertEquals("${UU}", actualDiscoveryEnabledNIWSServerList.getVipAddresses());
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
   * Test {@link DiscoveryEnabledNIWSServerList#getInitialListOfServers()}.
   * <p>
   * Method under test: {@link DiscoveryEnabledNIWSServerList#getInitialListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.List DiscoveryEnabledNIWSServerList.getInitialListOfServers()"})
  public void testGetInitialListOfServers() {
    // Arrange, Act and Assert
    assertTrue((new DiscoveryEnabledNIWSServerList()).getInitialListOfServers().isEmpty());
  }

  /**
   * Test {@link DiscoveryEnabledNIWSServerList#getUpdatedListOfServers()}.
   * <p>
   * Method under test: {@link DiscoveryEnabledNIWSServerList#getUpdatedListOfServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.List DiscoveryEnabledNIWSServerList.getUpdatedListOfServers()"})
  public void testGetUpdatedListOfServers() {
    // Arrange, Act and Assert
    assertTrue((new DiscoveryEnabledNIWSServerList()).getUpdatedListOfServers().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DiscoveryEnabledNIWSServerList#setVipAddresses(String)}
   *   <li>{@link DiscoveryEnabledNIWSServerList#toString()}
   *   <li>{@link DiscoveryEnabledNIWSServerList#getVipAddresses()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DiscoveryEnabledNIWSServerList.getVipAddresses()",
      "void DiscoveryEnabledNIWSServerList.setVipAddresses(String)",
      "String DiscoveryEnabledNIWSServerList.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    DiscoveryEnabledNIWSServerList discoveryEnabledNIWSServerList = new DiscoveryEnabledNIWSServerList();

    // Act
    discoveryEnabledNIWSServerList.setVipAddresses("42 Main St");
    String actualToStringResult = discoveryEnabledNIWSServerList.toString();

    // Assert
    assertEquals("42 Main St", discoveryEnabledNIWSServerList.getVipAddresses());
    assertEquals("DiscoveryEnabledNIWSServerList:; clientName:null; Effective vipAddresses:42 Main St; isSecure:false;"
        + " datacenter:null", actualToStringResult);
  }
}
