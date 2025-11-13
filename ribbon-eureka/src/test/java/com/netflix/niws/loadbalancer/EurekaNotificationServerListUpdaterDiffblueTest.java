package com.netflix.niws.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.ServerListUpdater;
import com.netflix.loadbalancer.ServerListUpdater.UpdateAction;
import javax.inject.Provider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class EurekaNotificationServerListUpdaterDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link EurekaNotificationServerListUpdater#EurekaNotificationServerListUpdater()}.
   *
   * <p>Method under test: {@link
   * EurekaNotificationServerListUpdater#EurekaNotificationServerListUpdater()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EurekaNotificationServerListUpdater.<init>()"})
  public void testNewEurekaNotificationServerListUpdater() {
    // Arrange and Act
    EurekaNotificationServerListUpdater actualEurekaNotificationServerListUpdater =
        new EurekaNotificationServerListUpdater();

    // Assert
    assertEquals(0, actualEurekaNotificationServerListUpdater.getCoreThreads());
    assertEquals(0, actualEurekaNotificationServerListUpdater.getNumberMissedCycles());
    assertFalse(actualEurekaNotificationServerListUpdater.updateQueued.get());
  }

  /**
   * Test {@link EurekaNotificationServerListUpdater#EurekaNotificationServerListUpdater(Provider)}.
   *
   * <p>Method under test: {@link
   * EurekaNotificationServerListUpdater#EurekaNotificationServerListUpdater(Provider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EurekaNotificationServerListUpdater.<init>(Provider)"})
  public void testNewEurekaNotificationServerListUpdater2() {
    // Arrange and Act
    EurekaNotificationServerListUpdater actualEurekaNotificationServerListUpdater =
        new EurekaNotificationServerListUpdater(mock(Provider.class));

    // Assert
    assertEquals(0, actualEurekaNotificationServerListUpdater.getCoreThreads());
    assertEquals(0, actualEurekaNotificationServerListUpdater.getNumberMissedCycles());
    assertFalse(actualEurekaNotificationServerListUpdater.updateQueued.get());
  }

  /**
   * Test {@link EurekaNotificationServerListUpdater#start(UpdateAction)}.
   *
   * <ul>
   *   <li>Given {@link EurekaNotificationServerListUpdater#EurekaNotificationServerListUpdater()}.
   * </ul>
   *
   * <p>Method under test: {@link EurekaNotificationServerListUpdater#start(UpdateAction)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EurekaNotificationServerListUpdater.start(UpdateAction)"})
  public void testStart_givenEurekaNotificationServerListUpdater() {
    // Arrange, Act and Assert
    thrown.expect(IllegalStateException.class);
    new EurekaNotificationServerListUpdater().start(mock(UpdateAction.class));
  }

  /**
   * Test {@link EurekaNotificationServerListUpdater#start(UpdateAction)}.
   *
   * <ul>
   *   <li>Given {@link Provider} {@link Provider#get()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   *   <li>Then calls {@link Provider#get()}.
   * </ul>
   *
   * <p>Method under test: {@link EurekaNotificationServerListUpdater#start(UpdateAction)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EurekaNotificationServerListUpdater.start(UpdateAction)"})
  public void testStart_givenProviderGetThrowIllegalStateException_thenCallsGet() {
    // Arrange
    Provider<EurekaClient> eurekaClientProvider = mock(Provider.class);
    when(eurekaClientProvider.get()).thenThrow(new IllegalStateException());

    // Act and Assert
    thrown.expect(IllegalStateException.class);
    new EurekaNotificationServerListUpdater(eurekaClientProvider).start(mock(UpdateAction.class));
    verify(eurekaClientProvider).get();
  }

  /**
   * Test {@link EurekaNotificationServerListUpdater#getNumberMissedCycles()}.
   *
   * <p>Method under test: {@link EurekaNotificationServerListUpdater#getNumberMissedCycles()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int EurekaNotificationServerListUpdater.getNumberMissedCycles()"})
  public void testGetNumberMissedCycles() {
    // Arrange, Act and Assert
    assertEquals(0, new EurekaNotificationServerListUpdater().getNumberMissedCycles());
  }

  /**
   * Test {@link EurekaNotificationServerListUpdater#getCoreThreads()}.
   *
   * <p>Method under test: {@link EurekaNotificationServerListUpdater#getCoreThreads()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int EurekaNotificationServerListUpdater.getCoreThreads()"})
  public void testGetCoreThreads() {
    // Arrange, Act and Assert
    assertEquals(0, new EurekaNotificationServerListUpdater().getCoreThreads());
  }
}
