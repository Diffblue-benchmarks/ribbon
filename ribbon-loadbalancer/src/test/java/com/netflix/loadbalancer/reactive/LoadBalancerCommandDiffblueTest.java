package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand.Builder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LoadBalancerCommandDiffblueTest {
  /**
   * Test Builder {@link Builder#withListeners(List)}.
   *
   * <ul>
   *   <li>Given builder withListeners {@link ArrayList#ArrayList()}.
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_givenBuilderWithListenersArrayList_whenArrayList() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();
    builderResult.withListeners(new ArrayList<>());

    // Act
    Builder<Object> actualWithListenersResult = builderResult.withListeners(new ArrayList<>());

    // Assert
    assertSame(builderResult, actualWithListenersResult);
  }

  /**
   * Test Builder {@link Builder#withListeners(List)}.
   *
   * <ul>
   *   <li>Given builder.
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_givenBuilder_whenArrayList() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    // Act
    Builder<Object> actualWithListenersResult = builderResult.withListeners(new ArrayList<>());

    // Assert
    assertSame(builderResult, actualWithListenersResult);
  }

  /**
   * Test Builder {@link Builder#withListeners(List)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionListener}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_givenExecutionListener() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    ArrayList<ExecutionListener<?, Object>> listeners = new ArrayList<>();
    listeners.add(mock(ExecutionListener.class));

    // Act
    Builder<Object> actualWithListenersResult = builderResult.withListeners(listeners);

    // Assert
    assertSame(builderResult, actualWithListenersResult);
  }

  /**
   * Test Builder {@link Builder#withListeners(List)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionListener}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withListeners(List)"})
  public void testBuilderWithListeners_givenExecutionListener2() {
    // Arrange
    Builder<Object> builderResult = LoadBalancerCommand.builder();

    ArrayList<ExecutionListener<?, Object>> listeners = new ArrayList<>();
    listeners.add(mock(ExecutionListener.class));
    listeners.add(mock(ExecutionListener.class));

    // Act
    Builder<Object> actualWithListenersResult = builderResult.withListeners(listeners);

    // Assert
    assertSame(builderResult, actualWithListenersResult);
  }
}
