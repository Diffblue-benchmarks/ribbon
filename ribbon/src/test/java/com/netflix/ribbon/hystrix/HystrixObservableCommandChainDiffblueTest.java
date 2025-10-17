package com.netflix.ribbon.hystrix;

import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HystrixObservableCommandChainDiffblueTest {
  /**
   * Test {@link HystrixObservableCommandChain#HystrixObservableCommandChain(List)}.
   *
   * <p>Method under test: {@link HystrixObservableCommandChain#HystrixObservableCommandChain(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HystrixObservableCommandChain.<init>(List)"})
  public void testNewHystrixObservableCommandChain() {
    // Arrange and Act
    HystrixObservableCommandChain<Object> actualHystrixObservableCommandChain =
        new HystrixObservableCommandChain<>(new ArrayList<>());

    // Assert
    assertTrue(actualHystrixObservableCommandChain.getCommands().isEmpty());
  }

  /**
   * Test {@link HystrixObservableCommandChain#getCommands()}.
   *
   * <p>Method under test: {@link HystrixObservableCommandChain#getCommands()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List HystrixObservableCommandChain.getCommands()"})
  public void testGetCommands() {
    // Arrange
    HystrixObservableCommandChain<Object> hystrixObservableCommandChain =
        new HystrixObservableCommandChain<>(new ArrayList<>());

    // Act and Assert
    assertTrue(hystrixObservableCommandChain.getCommands().isEmpty());
  }
}
