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
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient.Builder;
import io.reactivex.netty.client.PoolLimitDeterminationStrategy;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import rx.functions.Func1;
import rx.functions.Func2;

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
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));
    LoadBalancingHttpClient<Object, Object> loadBalancingHttpClient =
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build();

    // Act
    PoolLimitDeterminationStrategy actualPoolStrategy = loadBalancingHttpClient.getPoolStrategy();

    // Assert
    assertSame(loadBalancingHttpClient.globalStrategy, actualPoolStrategy);
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
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act and Assert
    assertEquals(
        30000,
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .getConnectionIdleTimeoutMillis());
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
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act and Assert
    assertTrue(
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .isPoolEnabled());
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
    Builder<Object, Object> builderResult = LoadBalancingHttpClient.builder();

    Builder<Object, Object> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<Object, Object> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<Object, Object> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<Object, Object> withResponseToErrorPolicyResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class))
            .withPoolCleanerScheduler(RibbonTransport.poolCleanerScheduler)
            .withResponseToErrorPolicy(mock(Func2.class));

    // Act and Assert
    assertEquals(
        200,
        withResponseToErrorPolicyResult
            .withRetryHandler(new DefaultLoadBalancerRetryHandler())
            .build()
            .getMaxConcurrentRequests());
  }
}
