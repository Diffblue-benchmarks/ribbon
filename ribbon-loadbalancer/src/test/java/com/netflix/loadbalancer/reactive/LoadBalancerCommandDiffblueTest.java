package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class LoadBalancerCommandDiffblueTest {
  /**
   * Method under test: {@link LoadBalancerCommand.Builder#withListeners(List)}
   */
  @Test
  public void testBuilderWithListeners() {
    // Arrange
    LoadBalancerCommand.Builder<Object> builderResult = LoadBalancerCommand.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.withListeners(new ArrayList<>()));
  }

  /**
   * Method under test: {@link LoadBalancerCommand.Builder#withListeners(List)}
   */
  @Test
  public void testBuilderWithListeners2() {
    // Arrange
    LoadBalancerCommand.Builder<Object> builderResult = LoadBalancerCommand.builder();

    ArrayList<ExecutionListener<?, Object>> listeners = new ArrayList<>();
    listeners.add(mock(ExecutionListener.class));

    // Act and Assert
    assertSame(builderResult, builderResult.withListeners(listeners));
  }

  /**
   * Method under test: {@link LoadBalancerCommand.Builder#withListeners(List)}
   */
  @Test
  public void testBuilderWithListeners3() {
    // Arrange
    LoadBalancerCommand.Builder<Object> builderResult = LoadBalancerCommand.builder();

    ArrayList<ExecutionListener<?, Object>> listeners = new ArrayList<>();
    listeners.add(mock(ExecutionListener.class));
    listeners.add(mock(ExecutionListener.class));

    // Act and Assert
    assertSame(builderResult, builderResult.withListeners(listeners));
  }
}
