package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ZoneAffinityServerListFilterDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ZoneAffinityServerListFilter#ZoneAffinityServerListFilter()}
   *   <li>{@link ZoneAffinityServerListFilter#toString()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ZoneAffinityServerListFilter.<init>()",
    "java.lang.String ZoneAffinityServerListFilter.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ZoneAffinityServerListFilter<Server> actualZoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>();

    // Assert
    assertEquals(
        "ZoneAffinityServerListFilter:, zone: null, zoneAffinity:false, zoneExclusivity:false",
        actualZoneAffinityServerListFilter.toString());
    assertNull(actualZoneAffinityServerListFilter.getLoadBalancerStats());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Given {@link ServerListSubsetFilter#ServerListSubsetFilter()}.
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_givenServerListSubsetFilter_whenArrayList() {
    // Arrange
    ServerListSubsetFilter<Server> serverListSubsetFilter = new ServerListSubsetFilter<>();

    // Act and Assert
    assertTrue(serverListSubsetFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAffinityServerListFilter#ZoneAffinityServerListFilter()}.
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ZoneAffinityServerListFilter#getFilteredListOfServers(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ZoneAffinityServerListFilter.getFilteredListOfServers(List)"})
  public void testGetFilteredListOfServers_givenZoneAffinityServerListFilter_whenArrayList() {
    // Arrange
    ZoneAffinityServerListFilter<Server> zoneAffinityServerListFilter =
        new ZoneAffinityServerListFilter<>();

    // Act and Assert
    assertTrue(zoneAffinityServerListFilter.getFilteredListOfServers(new ArrayList<>()).isEmpty());
  }
}
