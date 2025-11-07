package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand.Builder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LoadBalancerCommandDiffblueTest {
  /**
   * Test Builder {@link Builder#withListeners(List)}.
   * <ul>
   *   <li>Given {@link ExecutionListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_givenExecutionListener() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    ArrayList<ExecutionListener<?, Object>> listeners = new ArrayList<>();
    listeners.add(mock(ExecutionListener.class));

    // Act and Assert
    assertSame(builderResult, builderResult.withListeners(listeners));
  }

  /**
   * Test Builder {@link Builder#withListeners(List)}.
   * <ul>
   *   <li>Given {@link ExecutionListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_givenExecutionListener2() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    ArrayList<ExecutionListener<?, Object>> listeners = new ArrayList<>();
    listeners.add(mock(ExecutionListener.class));
    listeners.add(mock(ExecutionListener.class));

    // Act and Assert
    assertSame(builderResult, builderResult.withListeners(listeners));
  }

  /**
   * Test Builder {@link Builder#withListeners(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_whenArrayList() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.withListeners(new ArrayList<>()));
  }
}
