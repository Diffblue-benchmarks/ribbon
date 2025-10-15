package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PollingServerListUpdaterDiffblueTest {
  /**
   * Test {@link PollingServerListUpdater#PollingServerListUpdater()}.
   *
   * <p>Method under test: {@link PollingServerListUpdater#PollingServerListUpdater()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link PollingServerListUpdater#PollingServerListUpdater(long, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PollingServerListUpdater.<init>(long, long)"})
  public void testNewPollingServerListUpdater2() {
    // Arrange and Act
    PollingServerListUpdater actualPollingServerListUpdater = new PollingServerListUpdater(1L, 42L);

    // Assert
    assertEquals(0, actualPollingServerListUpdater.getNumberMissedCycles());
    assertEquals(2, actualPollingServerListUpdater.getCoreThreads());
  }

  /**
   * Test {@link PollingServerListUpdater#PollingServerListUpdater(IClientConfig)}.
   *
   * <ul>
   *   <li>Then return NumberMissedCycles is zero.
   * </ul>
   *
   * <p>Method under test: {@link PollingServerListUpdater#PollingServerListUpdater(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PollingServerListUpdater.<init>(IClientConfig)"})
  public void testNewPollingServerListUpdater_thenReturnNumberMissedCyclesIsZero() {
    // Arrange and Act
    PollingServerListUpdater actualPollingServerListUpdater =
        new PollingServerListUpdater(DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertEquals(0, actualPollingServerListUpdater.getNumberMissedCycles());
    assertEquals(2, actualPollingServerListUpdater.getCoreThreads());
  }

  /**
   * Test {@link PollingServerListUpdater#getNumberMissedCycles()}.
   *
   * <p>Method under test: {@link PollingServerListUpdater#getNumberMissedCycles()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int PollingServerListUpdater.getNumberMissedCycles()"})
  public void testGetNumberMissedCycles() {
    // Arrange, Act and Assert
    assertEquals(0, new PollingServerListUpdater().getNumberMissedCycles());
  }

  /**
   * Test {@link PollingServerListUpdater#getCoreThreads()}.
   *
   * <p>Method under test: {@link PollingServerListUpdater#getCoreThreads()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int PollingServerListUpdater.getCoreThreads()"})
  public void testGetCoreThreads() {
    // Arrange, Act and Assert
    assertEquals(2, new PollingServerListUpdater().getCoreThreads());
  }
}
