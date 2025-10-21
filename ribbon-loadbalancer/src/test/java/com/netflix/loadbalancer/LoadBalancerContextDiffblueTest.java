package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LoadBalancerContextDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link LoadBalancerContext#setLoadBalancer(ILoadBalancer)}
   *   <li>{@link LoadBalancerContext#setOkToRetryOnAllOperations(boolean)}
   *   <li>{@link LoadBalancerContext#setRetryHandler(RetryHandler)}
   *   <li>{@link LoadBalancerContext#setMaxAutoRetries(int)}
   *   <li>{@link LoadBalancerContext#setMaxAutoRetriesNextServer(int)}
   *   <li>{@link LoadBalancerContext#getClientName()}
   *   <li>{@link LoadBalancerContext#getLoadBalancer()}
   *   <li>{@link LoadBalancerContext#getMaxAutoRetries()}
   *   <li>{@link LoadBalancerContext#getMaxAutoRetriesNextServer()}
   *   <li>{@link LoadBalancerContext#getRetryHandler()}
   *   <li>{@link LoadBalancerContext#isOkToRetryOnAllOperations()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String LoadBalancerContext.getClientName()",
    "ILoadBalancer LoadBalancerContext.getLoadBalancer()",
    "int LoadBalancerContext.getMaxAutoRetries()",
    "int LoadBalancerContext.getMaxAutoRetriesNextServer()",
    "RetryHandler LoadBalancerContext.getRetryHandler()",
    "boolean LoadBalancerContext.isOkToRetryOnAllOperations()",
    "void LoadBalancerContext.setLoadBalancer(ILoadBalancer)",
    "void LoadBalancerContext.setMaxAutoRetries(int)",
    "void LoadBalancerContext.setMaxAutoRetriesNextServer(int)",
    "void LoadBalancerContext.setOkToRetryOnAllOperations(boolean)",
    "void LoadBalancerContext.setRetryHandler(RetryHandler)"
  })
  public void testGettersAndSetters() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    loadBalancerContext.setLoadBalancer(lb);
    loadBalancerContext.setOkToRetryOnAllOperations(true);
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();
    loadBalancerContext.setRetryHandler(retryHandler);
    loadBalancerContext.setMaxAutoRetries(3);
    loadBalancerContext.setMaxAutoRetriesNextServer(3);
    String actualClientName = loadBalancerContext.getClientName();
    ILoadBalancer actualLoadBalancer = loadBalancerContext.getLoadBalancer();
    int actualMaxAutoRetries = loadBalancerContext.getMaxAutoRetries();
    int actualMaxAutoRetriesNextServer = loadBalancerContext.getMaxAutoRetriesNextServer();
    RetryHandler actualRetryHandler = loadBalancerContext.getRetryHandler();

    // Assert
    assertEquals("default", actualClientName);
    assertEquals(3, actualMaxAutoRetries);
    assertEquals(3, actualMaxAutoRetriesNextServer);
    assertTrue(loadBalancerContext.isOkToRetryOnAllOperations());
    assertSame(retryHandler, actualRetryHandler);
    assertSame(lb, actualLoadBalancer);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link LoadBalancerContext#setLoadBalancer(ILoadBalancer)}
   *   <li>{@link LoadBalancerContext#setOkToRetryOnAllOperations(boolean)}
   *   <li>{@link LoadBalancerContext#setRetryHandler(RetryHandler)}
   *   <li>{@link LoadBalancerContext#setMaxAutoRetries(int)}
   *   <li>{@link LoadBalancerContext#setMaxAutoRetriesNextServer(int)}
   *   <li>{@link LoadBalancerContext#getClientName()}
   *   <li>{@link LoadBalancerContext#getLoadBalancer()}
   *   <li>{@link LoadBalancerContext#getMaxAutoRetries()}
   *   <li>{@link LoadBalancerContext#getMaxAutoRetriesNextServer()}
   *   <li>{@link LoadBalancerContext#getRetryHandler()}
   *   <li>{@link LoadBalancerContext#isOkToRetryOnAllOperations()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String LoadBalancerContext.getClientName()",
    "ILoadBalancer LoadBalancerContext.getLoadBalancer()",
    "int LoadBalancerContext.getMaxAutoRetries()",
    "int LoadBalancerContext.getMaxAutoRetriesNextServer()",
    "RetryHandler LoadBalancerContext.getRetryHandler()",
    "boolean LoadBalancerContext.isOkToRetryOnAllOperations()",
    "void LoadBalancerContext.setLoadBalancer(ILoadBalancer)",
    "void LoadBalancerContext.setMaxAutoRetries(int)",
    "void LoadBalancerContext.setMaxAutoRetriesNextServer(int)",
    "void LoadBalancerContext.setOkToRetryOnAllOperations(boolean)",
    "void LoadBalancerContext.setRetryHandler(RetryHandler)"
  })
  public void testGettersAndSetters2() {
    // Arrange
    LoadBalancerContext loadBalancerContext = new LoadBalancerContext(new BaseLoadBalancer());
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    loadBalancerContext.setLoadBalancer(lb);
    loadBalancerContext.setOkToRetryOnAllOperations(true);
    DefaultLoadBalancerRetryHandler retryHandler = new DefaultLoadBalancerRetryHandler();
    loadBalancerContext.setRetryHandler(retryHandler);
    loadBalancerContext.setMaxAutoRetries(3);
    loadBalancerContext.setMaxAutoRetriesNextServer(3);
    String actualClientName = loadBalancerContext.getClientName();
    ILoadBalancer actualLoadBalancer = loadBalancerContext.getLoadBalancer();
    int actualMaxAutoRetries = loadBalancerContext.getMaxAutoRetries();
    int actualMaxAutoRetriesNextServer = loadBalancerContext.getMaxAutoRetriesNextServer();
    RetryHandler actualRetryHandler = loadBalancerContext.getRetryHandler();

    // Assert
    assertEquals("default", actualClientName);
    assertEquals(3, actualMaxAutoRetries);
    assertEquals(3, actualMaxAutoRetriesNextServer);
    assertTrue(loadBalancerContext.isOkToRetryOnAllOperations());
    assertSame(retryHandler, actualRetryHandler);
    assertSame(lb, actualLoadBalancer);
  }
}
