package com.netflix.ribbon.hystrix;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class HystrixObservableCommandChainDiffblueTest {
  /**
   * Method under test:
   * {@link HystrixObservableCommandChain#toResultCommandPairObservable()}
   */
  @Test
  public void testToResultCommandPairObservable() {
    // Arrange
    HystrixObservableCommandChain<Object> hystrixObservableCommandChain = new HystrixObservableCommandChain<>(
        new ArrayList<>());

    // Act and Assert
    assertNull(hystrixObservableCommandChain.toResultCommandPairObservable());
  }

  /**
   * Method under test: {@link HystrixObservableCommandChain#toObservable()}
   */
  @Test
  public void testToObservable() {
    // Arrange
    HystrixObservableCommandChain<Object> hystrixObservableCommandChain = new HystrixObservableCommandChain<>(
        new ArrayList<>());

    // Act and Assert
    assertNull(hystrixObservableCommandChain.toObservable());
  }

  /**
   * Method under test: {@link HystrixObservableCommandChain#getCommands()}
   */
  @Test
  public void testGetCommands() {
    // Arrange
    HystrixObservableCommandChain<Object> hystrixObservableCommandChain = new HystrixObservableCommandChain<>(
        new ArrayList<>());

    // Act and Assert
    assertTrue(hystrixObservableCommandChain.getCommands().isEmpty());
  }

  /**
   * Method under test:
   * {@link HystrixObservableCommandChain#HystrixObservableCommandChain(List)}
   */
  @Test
  public void testNewHystrixObservableCommandChain() {
    // Arrange and Act
    HystrixObservableCommandChain<Object> actualHystrixObservableCommandChain = new HystrixObservableCommandChain<>(
        new ArrayList<>());

    // Assert
    assertTrue(actualHystrixObservableCommandChain.getCommands().isEmpty());
  }
}
