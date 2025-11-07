package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PollingServerListUpdaterDiffblueTest {
  /**
   * Test {@link PollingServerListUpdater#PollingServerListUpdater()}.
   * <p>
   * Method under test: {@link PollingServerListUpdater#PollingServerListUpdater()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PollingServerListUpdater.<init>()"})
  public void testNewPollingServerListUpdater() {
    // Arrange and Act
    PollingServerListUpdater actualPollingServerListUpdater = new PollingServerListUpdater();

    // Assert
    assertEquals(0, actualPollingServerListUpdater.getNumberMissedCycles());
    assertEquals(2, actualPollingServerListUpdater.getCoreThreads());
  }

  /**
   * Test {@link PollingServerListUpdater#PollingServerListUpdater(long, long)}.
   * <p>
   * Method under test: {@link PollingServerListUpdater#PollingServerListUpdater(long, long)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PollingServerListUpdater.<init>(long, long)"})
  public void testNewPollingServerListUpdater2() {
    // Arrange and Act
    PollingServerListUpdater actualPollingServerListUpdater = new PollingServerListUpdater(1L, 42L);

    // Assert
    assertEquals(0, actualPollingServerListUpdater.getNumberMissedCycles());
    assertEquals(2, actualPollingServerListUpdater.getCoreThreads());
  }

  /**
   * Test {@link PollingServerListUpdater#getNumberMissedCycles()}.
   * <p>
   * Method under test: {@link PollingServerListUpdater#getNumberMissedCycles()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int PollingServerListUpdater.getNumberMissedCycles()"})
  public void testGetNumberMissedCycles() {
    // Arrange, Act and Assert
    assertEquals(0, (new PollingServerListUpdater()).getNumberMissedCycles());
  }

  /**
   * Test {@link PollingServerListUpdater#getCoreThreads()}.
   * <p>
   * Method under test: {@link PollingServerListUpdater#getCoreThreads()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int PollingServerListUpdater.getCoreThreads()"})
  public void testGetCoreThreads() {
    // Arrange, Act and Assert
    assertEquals(2, (new PollingServerListUpdater()).getCoreThreads());
  }
}
