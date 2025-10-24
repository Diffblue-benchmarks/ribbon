package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
}
