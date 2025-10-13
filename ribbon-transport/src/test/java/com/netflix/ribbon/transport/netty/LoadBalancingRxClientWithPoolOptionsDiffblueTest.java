package com.netflix.ribbon.transport.netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.ribbon.transport.netty.tcp.LoadBalancingTcpClient;
import io.reactivex.netty.client.PoolLimitDeterminationStrategy;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LoadBalancingRxClientWithPoolOptionsDiffblueTest {
  /**
   * Test {@link LoadBalancingRxClientWithPoolOptions#getPoolStrategy()}.
   *
   * <p>Method under test: {@link LoadBalancingRxClientWithPoolOptions#getPoolStrategy()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PoolLimitDeterminationStrategy LoadBalancingRxClientWithPoolOptions.getPoolStrategy()"
  })
  public void testGetPoolStrategy() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingTcpClient<Object, Object> loadBalancingTcpClient =
        new LoadBalancingTcpClient<>(
            lb,
            config,
            new DefaultLoadBalancerRetryHandler(),
            mock(PipelineConfigurator.class),
            RibbonTransport.poolCleanerScheduler);

    // Act
    PoolLimitDeterminationStrategy actualPoolStrategy = loadBalancingTcpClient.getPoolStrategy();

    // Assert
    assertSame(loadBalancingTcpClient.globalStrategy, actualPoolStrategy);
  }

  /**
   * Test {@link LoadBalancingRxClientWithPoolOptions#getConnectionIdleTimeoutMillis()}.
   *
   * <ul>
   *   <li>Then return {@code 30000}.
   * </ul>
   *
   * <p>Method under test: {@link
   * LoadBalancingRxClientWithPoolOptions#getConnectionIdleTimeoutMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancingRxClientWithPoolOptions.getConnectionIdleTimeoutMillis()"})
  public void testGetConnectionIdleTimeoutMillis_thenReturn30000() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingTcpClient<Object, Object> loadBalancingTcpClient =
        new LoadBalancingTcpClient<>(
            lb,
            config,
            new DefaultLoadBalancerRetryHandler(),
            mock(PipelineConfigurator.class),
            RibbonTransport.poolCleanerScheduler);

    // Act and Assert
    assertEquals(30000, loadBalancingTcpClient.getConnectionIdleTimeoutMillis());
  }

  /**
   * Test {@link LoadBalancingRxClientWithPoolOptions#isPoolEnabled()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClientWithPoolOptions#isPoolEnabled()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LoadBalancingRxClientWithPoolOptions.isPoolEnabled()"})
  public void testIsPoolEnabled_thenReturnTrue() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingTcpClient<Object, Object> loadBalancingTcpClient =
        new LoadBalancingTcpClient<>(
            lb,
            config,
            new DefaultLoadBalancerRetryHandler(),
            mock(PipelineConfigurator.class),
            RibbonTransport.poolCleanerScheduler);

    // Act and Assert
    assertTrue(loadBalancingTcpClient.isPoolEnabled());
  }

  /**
   * Test {@link LoadBalancingRxClientWithPoolOptions#getMaxConcurrentRequests()}.
   *
   * <ul>
   *   <li>Then return two hundred.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancingRxClientWithPoolOptions#getMaxConcurrentRequests()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int LoadBalancingRxClientWithPoolOptions.getMaxConcurrentRequests()"})
  public void testGetMaxConcurrentRequests_thenReturnTwoHundred() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    IClientConfig config =
        Builder.newBuilder().ignoreUserTokenInConnectionPoolForSecureClient(true).build();

    LoadBalancingTcpClient<Object, Object> loadBalancingTcpClient =
        new LoadBalancingTcpClient<>(
            lb,
            config,
            new DefaultLoadBalancerRetryHandler(),
            mock(PipelineConfigurator.class),
            RibbonTransport.poolCleanerScheduler);

    // Act and Assert
    assertEquals(200, loadBalancingTcpClient.getMaxConcurrentRequests());
  }
}
