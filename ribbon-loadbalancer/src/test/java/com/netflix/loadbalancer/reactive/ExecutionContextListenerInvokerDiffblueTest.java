package com.netflix.loadbalancer.reactive;

import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.Server;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExecutionContextListenerInvokerDiffblueTest {
  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionStart()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onExecutionStart()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionContextListenerInvoker.onExecutionStart()"})
  public void testOnExecutionStart_thenDoesNotThrow() {
    // Arrange
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(new ArrayList<>());

    // Act and Assert
    executionContextListenerInvoker.onExecutionStart();
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onExecutionStart()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onExecutionStart()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionContextListenerInvoker.onExecutionStart()"})
  public void testOnExecutionStart_thenDoesNotThrow2() {
    // Arrange
    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(mock(ExecutionListener.class));
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);

    // Act and Assert
    executionContextListenerInvoker.onExecutionStart();
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionInfo)} with {@code
   * info}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionContextListenerInvoker.onStartWithServer(ExecutionInfo)"})
  public void testOnStartWithServerWithInfo_thenDoesNotThrow() {
    // Arrange
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(new ArrayList<>());

    // Act and Assert
    executionContextListenerInvoker.onStartWithServer(
        ExecutionInfo.create(new Server("42"), 10, 10));
  }

  /**
   * Test {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionInfo)} with {@code
   * info}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionContextListenerInvoker#onStartWithServer(ExecutionInfo)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionContextListenerInvoker.onStartWithServer(ExecutionInfo)"})
  public void testOnStartWithServerWithInfo_thenDoesNotThrow2() {
    // Arrange
    ArrayList<ExecutionListener<Object, Object>> listeners = new ArrayList<>();
    listeners.add(mock(ExecutionListener.class));
    ExecutionContextListenerInvoker<Object, Object> executionContextListenerInvoker =
        new ExecutionContextListenerInvoker<>(listeners);

    // Act and Assert
    executionContextListenerInvoker.onStartWithServer(
        ExecutionInfo.create(new Server("42"), 10, 10));
  }
}
