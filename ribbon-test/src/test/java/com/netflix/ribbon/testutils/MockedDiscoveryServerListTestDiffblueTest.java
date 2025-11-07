package com.netflix.ribbon.testutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.Server;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MockedDiscoveryServerListTestDiffblueTest {
  /**
   * Test {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}.
   * <p>
   * Method under test: {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List MockedDiscoveryServerListTest.getDummyInstanceInfo(String, List)"})
  public void testGetDummyInstanceInfo() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server(""));

    // Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest
        .getDummyInstanceInfo("Passed in hostname is blank, not setting it", serverList);

    // Assert
    assertEquals(1, actualDummyInstanceInfo.size());
    InstanceInfo getResult = actualDummyInstanceInfo.get(0);
    assertEquals("PASSED IN HOSTNAME IS BLANK, NOT SETTING IT", getResult.getAppName());
    assertNull(getResult.getHostName());
    assertNull(getResult.getId());
  }

  /**
   * Test {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}.
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.</li>
   *   <li>Then return first HostName is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List MockedDiscoveryServerListTest.getDummyInstanceInfo(String, List)"})
  public void testGetDummyInstanceInfo_givenServerWithIdIs42_thenReturnFirstHostNameIs42() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));

    // Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest.getDummyInstanceInfo("App Name",
        serverList);

    // Assert
    assertEquals(1, actualDummyInstanceInfo.size());
    InstanceInfo getResult = actualDummyInstanceInfo.get(0);
    assertEquals("42", getResult.getHostName());
    assertEquals("42", getResult.getId());
    assertEquals("APP NAME", getResult.getAppName());
  }

  /**
   * Test {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}.
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List MockedDiscoveryServerListTest.getDummyInstanceInfo(String, List)"})
  public void testGetDummyInstanceInfo_givenServerWithIdIs42_thenReturnSizeIsTwo() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));

    // Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest.getDummyInstanceInfo("App Name",
        serverList);

    // Assert
    assertEquals(2, actualDummyInstanceInfo.size());
    assertEquals(actualDummyInstanceInfo.get(0), actualDummyInstanceInfo.get(1));
  }

  /**
   * Test {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}.
   * <ul>
   *   <li>Then return first AppName is {@code APP NAME}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List MockedDiscoveryServerListTest.getDummyInstanceInfo(String, List)"})
  public void testGetDummyInstanceInfo_thenReturnFirstAppNameIsAppName() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server(""));

    // Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest.getDummyInstanceInfo("App Name",
        serverList);

    // Assert
    assertEquals(1, actualDummyInstanceInfo.size());
    InstanceInfo getResult = actualDummyInstanceInfo.get(0);
    assertEquals("APP NAME", getResult.getAppName());
    assertNull(getResult.getHostName());
    assertNull(getResult.getId());
  }

  /**
   * Test {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MockedDiscoveryServerListTest#getDummyInstanceInfo(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List MockedDiscoveryServerListTest.getDummyInstanceInfo(String, List)"})
  public void testGetDummyInstanceInfo_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    List<InstanceInfo> actualDummyInstanceInfo = MockedDiscoveryServerListTest.getDummyInstanceInfo("App Name",
        new ArrayList<>());

    // Assert
    assertTrue(actualDummyInstanceInfo.isEmpty());
  }
}
