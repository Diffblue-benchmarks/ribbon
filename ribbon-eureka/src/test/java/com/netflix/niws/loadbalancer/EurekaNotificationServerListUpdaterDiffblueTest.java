package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import com.netflix.discovery.EurekaClient;
import javax.inject.Provider;
import org.junit.Test;

public class EurekaNotificationServerListUpdaterDiffblueTest {
  /**
   * Method under test:
   * {@link EurekaNotificationServerListUpdater#getNumberMissedCycles()}
   */
  @Test
  public void testGetNumberMissedCycles() {
    // Arrange, Act and Assert
    assertEquals(0, (new EurekaNotificationServerListUpdater()).getNumberMissedCycles());
  }

  /**
   * Method under test:
   * {@link EurekaNotificationServerListUpdater#EurekaNotificationServerListUpdater()}
   */
  @Test
  public void testNewEurekaNotificationServerListUpdater() {
    // Arrange and Act
    EurekaNotificationServerListUpdater actualEurekaNotificationServerListUpdater = new EurekaNotificationServerListUpdater();

    // Assert
    assertEquals(0, actualEurekaNotificationServerListUpdater.getCoreThreads());
    assertEquals(0, actualEurekaNotificationServerListUpdater.getNumberMissedCycles());
    assertFalse(actualEurekaNotificationServerListUpdater.updateQueued.get());
  }

  /**
   * Method under test:
   * {@link EurekaNotificationServerListUpdater#EurekaNotificationServerListUpdater(Provider)}
   */
  @Test
  public void testNewEurekaNotificationServerListUpdater2() {
    // Arrange and Act
    EurekaNotificationServerListUpdater actualEurekaNotificationServerListUpdater = new EurekaNotificationServerListUpdater(
        mock(Provider.class));

    // Assert
    assertEquals(0, actualEurekaNotificationServerListUpdater.getCoreThreads());
    assertEquals(0, actualEurekaNotificationServerListUpdater.getNumberMissedCycles());
    assertFalse(actualEurekaNotificationServerListUpdater.updateQueued.get());
  }
}
