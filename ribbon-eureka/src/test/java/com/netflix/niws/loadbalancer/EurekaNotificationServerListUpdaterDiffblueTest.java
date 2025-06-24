package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EurekaNotificationServerListUpdaterDiffblueTest {
  /**
   * Test {@link EurekaNotificationServerListUpdater#EurekaNotificationServerListUpdater()}.
   * <p>
   * Method under test: {@link EurekaNotificationServerListUpdater#EurekaNotificationServerListUpdater()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EurekaNotificationServerListUpdater.<init>()"})
  public void testNewEurekaNotificationServerListUpdater() {
    // Arrange and Act
    EurekaNotificationServerListUpdater actualEurekaNotificationServerListUpdater = new EurekaNotificationServerListUpdater();

    // Assert
    assertEquals(0, actualEurekaNotificationServerListUpdater.getCoreThreads());
    assertEquals(0, actualEurekaNotificationServerListUpdater.getNumberMissedCycles());
    assertFalse(actualEurekaNotificationServerListUpdater.updateQueued.get());
  }

  /**
   * Test {@link EurekaNotificationServerListUpdater#getNumberMissedCycles()}.
   * <p>
   * Method under test: {@link EurekaNotificationServerListUpdater#getNumberMissedCycles()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int EurekaNotificationServerListUpdater.getNumberMissedCycles()"})
  public void testGetNumberMissedCycles() {
    // Arrange, Act and Assert
    assertEquals(0, (new EurekaNotificationServerListUpdater()).getNumberMissedCycles());
  }
}
