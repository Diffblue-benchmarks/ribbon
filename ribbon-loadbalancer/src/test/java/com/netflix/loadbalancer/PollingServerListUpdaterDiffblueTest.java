package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import org.junit.Test;
import org.mockito.Mockito;

public class PollingServerListUpdaterDiffblueTest {
  /**
   * Method under test: {@link PollingServerListUpdater#stop()}
   */
  @Test
  public void testStop() {
    // Arrange
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.get(Mockito.<IClientConfigKey<Integer>>any(), Mockito.<Integer>any())).thenReturn(1);

    // Act
    (new PollingServerListUpdater(clientConfig)).stop();

    // Assert
    verify(clientConfig).get(isA(IClientConfigKey.class), eq(30000));
  }

  /**
   * Method under test: {@link PollingServerListUpdater#getNumberMissedCycles()}
   */
  @Test
  public void testGetNumberMissedCycles() {
    // Arrange, Act and Assert
    assertEquals(0, (new PollingServerListUpdater()).getNumberMissedCycles());
  }

  /**
   * Method under test: {@link PollingServerListUpdater#getCoreThreads()}
   */
  @Test
  public void testGetCoreThreads() {
    // Arrange, Act and Assert
    assertEquals(2, (new PollingServerListUpdater()).getCoreThreads());
  }

  /**
   * Method under test:
   * {@link PollingServerListUpdater#PollingServerListUpdater()}
   */
  @Test
  public void testNewPollingServerListUpdater() {
    // Arrange and Act
    PollingServerListUpdater actualPollingServerListUpdater = new PollingServerListUpdater();

    // Assert
    assertEquals(0, actualPollingServerListUpdater.getNumberMissedCycles());
    assertEquals(2, actualPollingServerListUpdater.getCoreThreads());
  }

  /**
   * Method under test:
   * {@link PollingServerListUpdater#PollingServerListUpdater(long, long)}
   */
  @Test
  public void testNewPollingServerListUpdater2() {
    // Arrange and Act
    PollingServerListUpdater actualPollingServerListUpdater = new PollingServerListUpdater(1L, 42L);

    // Assert
    assertEquals(0, actualPollingServerListUpdater.getNumberMissedCycles());
    assertEquals(2, actualPollingServerListUpdater.getCoreThreads());
  }

  /**
   * Method under test:
   * {@link PollingServerListUpdater#PollingServerListUpdater(IClientConfig)}
   */
  @Test
  public void testNewPollingServerListUpdater3() {
    // Arrange and Act
    PollingServerListUpdater actualPollingServerListUpdater = new PollingServerListUpdater(
        DefaultClientConfigImpl.getEmptyConfig());

    // Assert
    assertEquals(0, actualPollingServerListUpdater.getNumberMissedCycles());
    assertEquals(2, actualPollingServerListUpdater.getCoreThreads());
  }

  /**
   * Method under test:
   * {@link PollingServerListUpdater#PollingServerListUpdater(IClientConfig)}
   */
  @Test
  public void testNewPollingServerListUpdater4() {
    // Arrange
    DefaultClientConfigImpl clientConfig = DefaultClientConfigImpl.getEmptyConfig();
    clientConfig.setVipAddressResolver(mock(VipAddressResolver.class));

    // Act
    PollingServerListUpdater actualPollingServerListUpdater = new PollingServerListUpdater(clientConfig);

    // Assert
    assertEquals(0, actualPollingServerListUpdater.getNumberMissedCycles());
    assertEquals(2, actualPollingServerListUpdater.getCoreThreads());
  }
}
