package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.IClientConfigAware;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class LoadBalancerBuilderDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  public void testBuildFixedServerListLoadBalancer() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult = newBuilderResult
        .buildFixedServerListLoadBalancer(new ArrayList<>());

    // Assert
    IClientConfig clientConfig = actualBuildFixedServerListLoadBalancerResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = actualBuildFixedServerListLoadBalancerResult.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBuildFixedServerListLoadBalancerResult.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBuildFixedServerListLoadBalancerResult.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBuildFixedServerListLoadBalancerResult.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBuildFixedServerListLoadBalancerResult.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", clientConfig.getClientName());
    assertEquals("", actualBuildFixedServerListLoadBalancerResult.getName());
    LoadBalancerStats loadBalancerStats = actualBuildFixedServerListLoadBalancerResult.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals("/", ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsUri());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) clientConfig).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) clientConfig).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) clientConfig).getDefaultClientClassname());
    assertEquals("ribbon", clientConfig.getNameSpace());
    assertNull(actualBuildFixedServerListLoadBalancerResult.getPrimeConnections());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(((DefaultClientConfigImpl) clientConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getVersion());
    assertNull(actualBuildFixedServerListLoadBalancerResult.lbTimer);
    assertEquals(0, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetries());
    assertEquals(0, ((AvailabilityFilteringRule) rule).getAvailableServersCount());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(0.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertEquals(2, actualBuildFixedServerListLoadBalancerResult.getMaxTotalPingTime());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionManagerTimeout());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBuildFixedServerListLoadBalancerResult.getPingInterval());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) clientConfig).getDefaultReadTimeout());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(60000, ((DefaultClientConfigImpl) clientConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) clientConfig).getDefaultPort());
    assertEquals(9, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTime());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultUseIpAddressForServer());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isEnablePrimingConnections());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isPingInProgress());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getAllServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getReachableServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.allServerList.isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.upServerList.isEmpty());
    assertTrue(clientConfig.getProperties().isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(Integer.MAX_VALUE, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRequestsAllowedPerWindow());
    assertSame(actualBuildFixedServerListLoadBalancerResult, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBuildFixedServerListLoadBalancerResult,
        ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBuildFixedServerListLoadBalancerResult, rule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  public void testBuildFixedServerListLoadBalancer2() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult = newBuilderResult
        .buildFixedServerListLoadBalancer(servers);

    // Assert
    IClientConfig clientConfig = actualBuildFixedServerListLoadBalancerResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = actualBuildFixedServerListLoadBalancerResult.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBuildFixedServerListLoadBalancerResult.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBuildFixedServerListLoadBalancerResult.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBuildFixedServerListLoadBalancerResult.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBuildFixedServerListLoadBalancerResult.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", clientConfig.getClientName());
    assertEquals("", actualBuildFixedServerListLoadBalancerResult.getName());
    LoadBalancerStats loadBalancerStats = actualBuildFixedServerListLoadBalancerResult.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals("/", ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsUri());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) clientConfig).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) clientConfig).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) clientConfig).getDefaultClientClassname());
    assertEquals("ribbon", clientConfig.getNameSpace());
    assertNull(actualBuildFixedServerListLoadBalancerResult.getPrimeConnections());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(((DefaultClientConfigImpl) clientConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getVersion());
    assertNull(actualBuildFixedServerListLoadBalancerResult.lbTimer);
    assertEquals(0, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetries());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(0.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertEquals(2, actualBuildFixedServerListLoadBalancerResult.getMaxTotalPingTime());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionManagerTimeout());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBuildFixedServerListLoadBalancerResult.getPingInterval());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) clientConfig).getDefaultReadTimeout());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(60000, ((DefaultClientConfigImpl) clientConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) clientConfig).getDefaultPort());
    assertEquals(9, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTime());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultUseIpAddressForServer());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isEnablePrimingConnections());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isPingInProgress());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(clientConfig.getProperties().isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertEquals(servers, actualBuildFixedServerListLoadBalancerResult.getAllServers());
    assertEquals(servers, actualBuildFixedServerListLoadBalancerResult.getReachableServers());
    assertEquals(servers, actualBuildFixedServerListLoadBalancerResult.allServerList);
    assertEquals(servers, actualBuildFixedServerListLoadBalancerResult.upServerList);
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(Integer.MAX_VALUE, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRequestsAllowedPerWindow());
    assertSame(actualBuildFixedServerListLoadBalancerResult, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBuildFixedServerListLoadBalancerResult,
        ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBuildFixedServerListLoadBalancerResult, rule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  public void testBuildFixedServerListLoadBalancer3() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    servers.add(new Server("42"));

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult = newBuilderResult
        .buildFixedServerListLoadBalancer(servers);

    // Assert
    IClientConfig clientConfig = actualBuildFixedServerListLoadBalancerResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IRule rule = actualBuildFixedServerListLoadBalancerResult.getRule();
    assertTrue(rule instanceof AvailabilityFilteringRule);
    AbstractServerPredicate predicate = ((AvailabilityFilteringRule) rule).getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    IPing ping = actualBuildFixedServerListLoadBalancerResult.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBuildFixedServerListLoadBalancerResult.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBuildFixedServerListLoadBalancerResult.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBuildFixedServerListLoadBalancerResult.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", clientConfig.getClientName());
    assertEquals("", actualBuildFixedServerListLoadBalancerResult.getName());
    LoadBalancerStats loadBalancerStats = actualBuildFixedServerListLoadBalancerResult.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals("/", ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsUri());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) clientConfig).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) clientConfig).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) clientConfig).getDefaultClientClassname());
    assertEquals("ribbon", clientConfig.getNameSpace());
    assertNull(actualBuildFixedServerListLoadBalancerResult.getPrimeConnections());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
    assertNull(((DefaultClientConfigImpl) clientConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getVersion());
    assertNull(actualBuildFixedServerListLoadBalancerResult.lbTimer);
    assertEquals(0, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetries());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(0.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(10L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertEquals(2, actualBuildFixedServerListLoadBalancerResult.getMaxTotalPingTime());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionManagerTimeout());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBuildFixedServerListLoadBalancerResult.getPingInterval());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) clientConfig).getDefaultReadTimeout());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(60000, ((DefaultClientConfigImpl) clientConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) clientConfig).getDefaultPort());
    assertEquals(9, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTime());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultUseIpAddressForServer());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isEnablePrimingConnections());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isPingInProgress());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(clientConfig.getProperties().isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertEquals(servers, actualBuildFixedServerListLoadBalancerResult.getAllServers());
    assertEquals(servers, actualBuildFixedServerListLoadBalancerResult.getReachableServers());
    assertEquals(servers, actualBuildFixedServerListLoadBalancerResult.allServerList);
    assertEquals(servers, actualBuildFixedServerListLoadBalancerResult.upServerList);
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(Integer.MAX_VALUE, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRequestsAllowedPerWindow());
    assertSame(actualBuildFixedServerListLoadBalancerResult, ((DummyPing) ping).getLoadBalancer());
    assertSame(actualBuildFixedServerListLoadBalancerResult,
        ((AvailabilityFilteringRule) rule).roundRobinRule.getLoadBalancer());
    assertSame(actualBuildFixedServerListLoadBalancerResult, rule.getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  public void testBuildFixedServerListLoadBalancer4()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    IClientConfigAware.Factory factory = mock(IClientConfigAware.Factory.class);
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any())).thenReturn(availabilityFilteringRule);
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult = newBuilderResult
        .buildFixedServerListLoadBalancer(new ArrayList<>());

    // Assert
    verify(factory).create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
    IClientConfig clientConfig = actualBuildFixedServerListLoadBalancerResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IPing ping = actualBuildFixedServerListLoadBalancerResult.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBuildFixedServerListLoadBalancerResult.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBuildFixedServerListLoadBalancerResult.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBuildFixedServerListLoadBalancerResult.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", clientConfig.getClientName());
    assertEquals("", actualBuildFixedServerListLoadBalancerResult.getName());
    LoadBalancerStats loadBalancerStats = actualBuildFixedServerListLoadBalancerResult.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals("/", ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsUri());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) clientConfig).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) clientConfig).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) clientConfig).getDefaultClientClassname());
    assertEquals("ribbon", clientConfig.getNameSpace());
    assertNull(actualBuildFixedServerListLoadBalancerResult.getPrimeConnections());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(((DefaultClientConfigImpl) clientConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getVersion());
    assertNull(actualBuildFixedServerListLoadBalancerResult.lbTimer);
    assertEquals(0, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetries());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(0.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBuildFixedServerListLoadBalancerResult.getMaxTotalPingTime());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionManagerTimeout());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBuildFixedServerListLoadBalancerResult.getPingInterval());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) clientConfig).getDefaultReadTimeout());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(60000, ((DefaultClientConfigImpl) clientConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) clientConfig).getDefaultPort());
    assertEquals(7L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertEquals(9, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTime());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultUseIpAddressForServer());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isEnablePrimingConnections());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isPingInProgress());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getAllServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getReachableServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.allServerList.isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.upServerList.isEmpty());
    assertTrue(clientConfig.getProperties().isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(Integer.MAX_VALUE, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRequestsAllowedPerWindow());
    assertSame(availabilityFilteringRule, actualBuildFixedServerListLoadBalancerResult.getRule());
    assertSame(actualBuildFixedServerListLoadBalancerResult, ((DummyPing) ping).getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  public void testBuildFixedServerListLoadBalancer5()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    IClientConfigAware.Factory factory = mock(IClientConfigAware.Factory.class);
    BestAvailableRule bestAvailableRule = new BestAvailableRule();
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any())).thenReturn(bestAvailableRule);
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult = newBuilderResult
        .buildFixedServerListLoadBalancer(new ArrayList<>());

    // Assert
    verify(factory).create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
    IClientConfig clientConfig = actualBuildFixedServerListLoadBalancerResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IPing ping = actualBuildFixedServerListLoadBalancerResult.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBuildFixedServerListLoadBalancerResult.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBuildFixedServerListLoadBalancerResult.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBuildFixedServerListLoadBalancerResult.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", clientConfig.getClientName());
    assertEquals("", actualBuildFixedServerListLoadBalancerResult.getName());
    LoadBalancerStats loadBalancerStats = actualBuildFixedServerListLoadBalancerResult.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals("/", ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsUri());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) clientConfig).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) clientConfig).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) clientConfig).getDefaultClientClassname());
    assertEquals("ribbon", clientConfig.getNameSpace());
    assertNull(actualBuildFixedServerListLoadBalancerResult.getPrimeConnections());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(((DefaultClientConfigImpl) clientConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getVersion());
    assertNull(actualBuildFixedServerListLoadBalancerResult.lbTimer);
    assertEquals(0, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetries());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(0.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBuildFixedServerListLoadBalancerResult.getMaxTotalPingTime());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionManagerTimeout());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBuildFixedServerListLoadBalancerResult.getPingInterval());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) clientConfig).getDefaultReadTimeout());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(60000, ((DefaultClientConfigImpl) clientConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) clientConfig).getDefaultPort());
    assertEquals(7L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertEquals(9, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTime());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultUseIpAddressForServer());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isEnablePrimingConnections());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isPingInProgress());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getAllServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getReachableServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.allServerList.isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.upServerList.isEmpty());
    assertTrue(clientConfig.getProperties().isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(Integer.MAX_VALUE, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRequestsAllowedPerWindow());
    assertSame(bestAvailableRule, actualBuildFixedServerListLoadBalancerResult.getRule());
    assertSame(actualBuildFixedServerListLoadBalancerResult, ((DummyPing) ping).getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  public void testBuildFixedServerListLoadBalancer6()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    IClientConfigAware.Factory factory = mock(IClientConfigAware.Factory.class);
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any())).thenReturn(responseTimeWeightedRule);
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult = newBuilderResult
        .buildFixedServerListLoadBalancer(new ArrayList<>());

    // Assert
    verify(factory).create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
    IClientConfig clientConfig = actualBuildFixedServerListLoadBalancerResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IPing ping = actualBuildFixedServerListLoadBalancerResult.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBuildFixedServerListLoadBalancerResult.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBuildFixedServerListLoadBalancerResult.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBuildFixedServerListLoadBalancerResult.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", clientConfig.getClientName());
    assertEquals("", actualBuildFixedServerListLoadBalancerResult.getName());
    LoadBalancerStats loadBalancerStats = actualBuildFixedServerListLoadBalancerResult.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals("/", ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsUri());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) clientConfig).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) clientConfig).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) clientConfig).getDefaultClientClassname());
    assertEquals("ribbon", clientConfig.getNameSpace());
    assertNull(actualBuildFixedServerListLoadBalancerResult.getPrimeConnections());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(((DefaultClientConfigImpl) clientConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getVersion());
    assertNull(actualBuildFixedServerListLoadBalancerResult.lbTimer);
    assertEquals(0, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetries());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(0.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBuildFixedServerListLoadBalancerResult.getMaxTotalPingTime());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionManagerTimeout());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBuildFixedServerListLoadBalancerResult.getPingInterval());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) clientConfig).getDefaultReadTimeout());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(60000, ((DefaultClientConfigImpl) clientConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) clientConfig).getDefaultPort());
    assertEquals(7L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertEquals(9, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTime());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultUseIpAddressForServer());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isEnablePrimingConnections());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isPingInProgress());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getAllServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getReachableServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.allServerList.isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.upServerList.isEmpty());
    assertTrue(clientConfig.getProperties().isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(Integer.MAX_VALUE, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRequestsAllowedPerWindow());
    assertSame(responseTimeWeightedRule, actualBuildFixedServerListLoadBalancerResult.getRule());
    assertSame(actualBuildFixedServerListLoadBalancerResult, ((DummyPing) ping).getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  public void testBuildFixedServerListLoadBalancer7() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    AvailabilityFilteringRule rule = new AvailabilityFilteringRule();
    newBuilderResult.withRule(rule);
    newBuilderResult.withFactory(mock(IClientConfigAware.Factory.class));

    // Act
    BaseLoadBalancer actualBuildFixedServerListLoadBalancerResult = newBuilderResult
        .buildFixedServerListLoadBalancer(new ArrayList<>());

    // Assert
    IClientConfig clientConfig = actualBuildFixedServerListLoadBalancerResult.getClientConfig();
    assertTrue(clientConfig instanceof DefaultClientConfigImpl);
    IPing ping = actualBuildFixedServerListLoadBalancerResult.getPing();
    assertTrue(ping instanceof DummyPing);
    assertTrue(actualBuildFixedServerListLoadBalancerResult.serverComparator instanceof ServerComparator);
    ReadWriteLock readWriteLock = actualBuildFixedServerListLoadBalancerResult.allServerLock;
    assertTrue(readWriteLock instanceof ReentrantReadWriteLock);
    ReadWriteLock readWriteLock2 = actualBuildFixedServerListLoadBalancerResult.upServerLock;
    assertTrue(readWriteLock2 instanceof ReentrantReadWriteLock);
    assertEquals("", clientConfig.getClientName());
    assertEquals("", actualBuildFixedServerListLoadBalancerResult.getName());
    LoadBalancerStats loadBalancerStats = actualBuildFixedServerListLoadBalancerResult.getLoadBalancerStats();
    assertEquals("", loadBalancerStats.getName());
    assertEquals("/", ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsUri());
    assertEquals("com.netflix.client.SimpleVipAddressResolver",
        ((DefaultClientConfigImpl) clientConfig).getDefaultVipaddressResolverClassname());
    assertEquals("com.netflix.loadbalancer.AvailabilityFilteringRule",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerRuleClassname());
    assertEquals("com.netflix.loadbalancer.ConfigurationBasedServerList",
        ((DefaultClientConfigImpl) clientConfig).getDefaultSeverListClass());
    assertEquals("com.netflix.loadbalancer.DummyPing",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerPingClassname());
    assertEquals("com.netflix.loadbalancer.ZoneAwareLoadBalancer",
        ((DefaultClientConfigImpl) clientConfig).getDefaultNfloadbalancerClassname());
    assertEquals("com.netflix.niws.client.http.HttpPrimeConnection",
        ((DefaultClientConfigImpl) clientConfig).getDefaultPrimeConnectionsClass());
    assertEquals("com.netflix.niws.client.http.RestClient",
        ((DefaultClientConfigImpl) clientConfig).getDefaultClientClassname());
    assertEquals("ribbon", clientConfig.getNameSpace());
    assertNull(actualBuildFixedServerListLoadBalancerResult.getPrimeConnections());
    assertNull(((DefaultClientConfigImpl) clientConfig).getResolver());
    assertNull(((DefaultClientConfigImpl) clientConfig).getAppName());
    assertNull(((DefaultClientConfigImpl) clientConfig).getVersion());
    assertNull(actualBuildFixedServerListLoadBalancerResult.lbTimer);
    assertEquals(0, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetries());
    assertEquals(0, loadBalancerStats.getCircuitBreakerTrippedCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getQueueLength());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getReadLockCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock).getWriteHoldCount());
    assertEquals(0, ((ReentrantReadWriteLock) readWriteLock2).getWriteHoldCount());
    assertEquals(0.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultPercentageNiwsEventLogged(), 0.0f);
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxAutoRetriesNextServer());
    assertEquals(1, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMinThreads());
    assertEquals(1.0f, ((DefaultClientConfigImpl) clientConfig).getDefaultMinPrimeConnectionsRatio(), 0.0f);
    assertEquals(10, loadBalancerStats.getCircuitTrippedTimeoutFactor().get());
    assertEquals(2, actualBuildFixedServerListLoadBalancerResult.getMaxTotalPingTime());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalHttpConnections());
    assertEquals(200, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolMaxThreads());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectTimeout());
    assertEquals(2000, ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionManagerTimeout());
    assertEquals(3, loadBalancerStats.getConnectionFailureCountThreshold().get());
    assertEquals(30, loadBalancerStats.getCircuitTripMaxTimeoutSeconds().get());
    assertEquals(30, actualBuildFixedServerListLoadBalancerResult.getPingInterval());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxConnectionsPerHost());
    assertEquals(50, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxHttpConnectionsPerHost());
    assertEquals(5000, ((DefaultClientConfigImpl) clientConfig).getDefaultReadTimeout());
    assertEquals(600, loadBalancerStats.getActiveRequestsCountTimeout().get());
    assertEquals(60000, ((DefaultClientConfigImpl) clientConfig).getDefaultRequestThrottlingWindowInMillis());
    assertEquals(7001, ((DefaultClientConfigImpl) clientConfig).getDefaultPort());
    assertEquals(7L, ((DefaultClientConfigImpl) clientConfig).getRefreshCount());
    assertEquals(9, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRetriesPerServerPrimeConnection());
    assertEquals(900L, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTime());
    assertEquals(TimeUnit.SECONDS, ((DefaultClientConfigImpl) clientConfig).getDefaultPoolKeepAliveTimeUnits());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableGzipContentEncodingFilter());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnablePrimeConnections());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableRequestThrottling());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneAffinity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultEnableZoneExclusivity());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultFollowRedirects());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultIsClientAuthRequired());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultOkToRetryOnAllOperations());
    assertFalse(((DefaultClientConfigImpl) clientConfig).getDefaultUseIpAddressForServer());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isEnablePrimingConnections());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.isPingInProgress());
    assertFalse(actualBuildFixedServerListLoadBalancerResult.pingInProgress.get());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).hasQueuedThreads());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isFair());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLocked());
    assertFalse(((ReentrantReadWriteLock) readWriteLock).isWriteLockedByCurrentThread());
    assertFalse(((ReentrantReadWriteLock) readWriteLock2).isWriteLockedByCurrentThread());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultConnectionPoolCleanerTaskEnabled());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableConnectionPool());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultEnableLoadbalancer());
    assertTrue(((DefaultClientConfigImpl) clientConfig).getDefaultPrioritizeVipAddressBasedServers());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getAllServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.getReachableServers().isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.allServerList.isEmpty());
    assertTrue(actualBuildFixedServerListLoadBalancerResult.upServerList.isEmpty());
    assertTrue(clientConfig.getProperties().isEmpty());
    assertTrue(loadBalancerStats.getServerStats().isEmpty());
    assertTrue(loadBalancerStats.getZoneStats().isEmpty());
    assertTrue(loadBalancerStats.upServerListZoneMap.isEmpty());
    assertTrue(loadBalancerStats.getAvailableZones().isEmpty());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionIdleTimertaskRepeatInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultConnectionidleTimeInMsecs());
    assertEquals(ResponseTimeWeightedRule.DEFAULT_TIMER_INTERVAL,
        ((DefaultClientConfigImpl) clientConfig).getDefaultMaxTotalTimeToPrimeConnections());
    assertEquals(Integer.MAX_VALUE, ((DefaultClientConfigImpl) clientConfig).getDefaultMaxRequestsAllowedPerWindow());
    assertSame(rule, actualBuildFixedServerListLoadBalancerResult.getRule());
    assertSame(actualBuildFixedServerListLoadBalancerResult, ((DummyPing) ping).getLoadBalancer());
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildFixedServerListLoadBalancer(List)}
   */
  @Test
  public void testBuildFixedServerListLoadBalancer8()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    IClientConfigAware.Factory factory = mock(IClientConfigAware.Factory.class);
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any()))
        .thenThrow(new IllegalArgumentException("default"));
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withFactory(factory);

    // Act and Assert
    thrown.expect(RuntimeException.class);
    newBuilderResult.buildFixedServerListLoadBalancer(new ArrayList<>());
    verify(factory).create(eq("com.netflix.loadbalancer.AvailabilityFilteringRule"), isA(IClientConfig.class));
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancer()}
   */
  @Test
  public void testBuildDynamicServerListLoadBalancer() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildDynamicServerListLoadBalancer();
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}
   */
  @Test
  public void testBuildDynamicServerListLoadBalancerWithUpdater() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildDynamicServerListLoadBalancerWithUpdater();
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildDynamicServerListLoadBalancerWithUpdater()}
   */
  @Test
  public void testBuildDynamicServerListLoadBalancerWithUpdater2() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withDynamicServerList(null);
    newBuilderResult.withRule(null);
    newBuilderResult.withServerListUpdater(null);
    newBuilderResult.withServerListFilter(new ServerListSubsetFilter<>());
    newBuilderResult.withPing(new DummyPing());

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildDynamicServerListLoadBalancerWithUpdater();
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}
   */
  @Test
  public void testBuildLoadBalancerFromConfigWithReflection() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    newBuilderResult.buildLoadBalancerFromConfigWithReflection();
  }

  /**
   * Method under test:
   * {@link LoadBalancerBuilder#buildLoadBalancerFromConfigWithReflection()}
   */
  @Test
  public void testBuildLoadBalancerFromConfigWithReflection2()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Arrange
    IClientConfigAware.Factory factory = mock(IClientConfigAware.Factory.class);
    BaseLoadBalancer baseLoadBalancer = new BaseLoadBalancer();
    when(factory.create(Mockito.<String>any(), Mockito.<IClientConfig>any())).thenReturn(baseLoadBalancer);
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();
    newBuilderResult.withClientConfig(DefaultClientConfigImpl.getClientConfigWithDefaultValues("Dr Jane Doe",
        "NFLoadBalancerClassName is not specified in the IClientConfig"));
    newBuilderResult.withFactory(factory);

    // Act
    ILoadBalancer actualBuildLoadBalancerFromConfigWithReflectionResult = newBuilderResult
        .buildLoadBalancerFromConfigWithReflection();

    // Assert
    verify(factory).create(eq("com.netflix.loadbalancer.ZoneAwareLoadBalancer"), isA(IClientConfig.class));
    assertSame(baseLoadBalancer, actualBuildLoadBalancerFromConfigWithReflectionResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link LoadBalancerBuilder#withClientConfig(IClientConfig)}
   *   <li>{@link LoadBalancerBuilder#withDynamicServerList(ServerList)}
   *   <li>{@link LoadBalancerBuilder#withFactory(IClientConfigAware.Factory)}
   *   <li>{@link LoadBalancerBuilder#withPing(IPing)}
   *   <li>{@link LoadBalancerBuilder#withRule(IRule)}
   *   <li>{@link LoadBalancerBuilder#withServerListFilter(ServerListFilter)}
   *   <li>{@link LoadBalancerBuilder#withServerListUpdater(ServerListUpdater)}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    LoadBalancerBuilder<Server> newBuilderResult = LoadBalancerBuilder.newBuilder();

    // Act
    LoadBalancerBuilder<Server> actualWithClientConfigResult = newBuilderResult
        .withClientConfig(DefaultClientConfigImpl.getEmptyConfig());
    LoadBalancerBuilder<Server> actualWithDynamicServerListResult = newBuilderResult
        .withDynamicServerList(new ConfigurationBasedServerList());
    LoadBalancerBuilder<Server> actualWithFactoryResult = newBuilderResult
        .withFactory(mock(IClientConfigAware.Factory.class));
    LoadBalancerBuilder<Server> actualWithPingResult = newBuilderResult.withPing(mock(IPing.class));
    LoadBalancerBuilder<Server> actualWithRuleResult = newBuilderResult.withRule(new AvailabilityFilteringRule());
    LoadBalancerBuilder<Server> actualWithServerListFilterResult = newBuilderResult
        .withServerListFilter(mock(ServerListFilter.class));

    // Assert
    assertSame(newBuilderResult, actualWithClientConfigResult);
    assertSame(newBuilderResult, actualWithDynamicServerListResult);
    assertSame(newBuilderResult, actualWithFactoryResult);
    assertSame(newBuilderResult, actualWithPingResult);
    assertSame(newBuilderResult, actualWithRuleResult);
    assertSame(newBuilderResult, actualWithServerListFilterResult);
    assertSame(newBuilderResult, newBuilderResult.withServerListUpdater(new PollingServerListUpdater()));
  }
}
