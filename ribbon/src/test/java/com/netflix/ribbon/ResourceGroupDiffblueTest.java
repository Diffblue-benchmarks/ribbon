package com.netflix.ribbon;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.config.ClientConfigFactory;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.ribbon.RibbonTransportFactory.DefaultRibbonTransportFactory;
import com.netflix.ribbon.http.HttpResourceGroup;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient.Builder;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import rx.functions.Func1;
import rx.functions.Func2;

public class ResourceGroupDiffblueTest {
  /**
   * Test {@link ResourceGroup#getClientConfig()}.
   *
   * <p>Method under test: {@link ResourceGroup#getClientConfig()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IClientConfig ResourceGroup.getClientConfig()"})
  public void testGetClientConfig() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    when(configFactory.newConfig())
        .thenReturn(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    DefaultRibbonTransportFactory transportFactory = mock(DefaultRibbonTransportFactory.class);

    Builder<ByteBuf, ByteBuf> builderResult = LoadBalancingHttpClient.builder();

    Builder<ByteBuf, ByteBuf> withBackoffStrategyResult =
        builderResult.withBackoffStrategy(mock(Func1.class));

    Builder<ByteBuf, ByteBuf> withClientConfigResult =
        withBackoffStrategyResult.withClientConfig(
            IClientConfig.Builder.newBuilder()
                .ignoreUserTokenInConnectionPoolForSecureClient(true)
                .build());

    Builder<ByteBuf, ByteBuf> withLoadBalancerResult =
        withClientConfigResult.withLoadBalancer(new BaseLoadBalancer());

    Builder<ByteBuf, ByteBuf> withPipelineConfiguratorResult =
        withLoadBalancerResult
            .withExecutorListeners(new ArrayList<>())
            .withPipelineConfigurator(mock(PipelineConfigurator.class));

    Builder<ByteBuf, ByteBuf> withResponseToErrorPolicyResult =
        withPipelineConfiguratorResult
            .withPoolCleanerScheduler(new ScheduledThreadPoolExecutor(1))
            .withResponseToErrorPolicy(mock(Func2.class));
    when(transportFactory.newHttpClient(Mockito.<IClientConfig>any()))
        .thenReturn(
            withResponseToErrorPolicyResult
                .withRetryHandler(new DefaultLoadBalancerRetryHandler())
                .build());

    HttpResourceGroup.Builder newBuilderResult =
        HttpResourceGroup.Builder.newBuilder(
            "https://example.org/example", configFactory, transportFactory);
    HttpResourceGroup httpResourceGroup =
        newBuilderResult.withClientOptions(ClientOptions.create()).build();

    // Act
    IClientConfig actualClientConfig = httpResourceGroup.getClientConfig();

    // Assert
    verify(configFactory).newConfig();
    verify(transportFactory).newHttpClient(isA(IClientConfig.class));
    assertSame(httpResourceGroup.clientConfig, actualClientConfig);
  }
}
