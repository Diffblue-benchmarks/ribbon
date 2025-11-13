package com.netflix.ribbon.hystrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.hystrix.HystrixObservableCommand;
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
   * Test {@link
   * HystrixObservableCommandChain#HystrixObservableCommandChain(HystrixObservableCommand[])}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return LastCommand is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HystrixObservableCommandChain#HystrixObservableCommandChain(HystrixObservableCommand[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HystrixObservableCommandChain.<init>(HystrixObservableCommand[])"})
  public void testNewHystrixObservableCommandChain_whenNull_thenReturnLastCommandIsNull() {
    // Arrange and Act
    HystrixObservableCommandChain<Object> actualHystrixObservableCommandChain =
        new HystrixObservableCommandChain<>((HystrixObservableCommand<Object>) null);

    // Assert
    assertNull(actualHystrixObservableCommandChain.getLastCommand());
    List<HystrixObservableCommand<Object>> commands =
        actualHystrixObservableCommandChain.getCommands();
    assertEquals(1, commands.size());
    assertNull(commands.get(0));
  }

  /**
   * Test {@link HystrixObservableCommandChain#toResultCommandPairObservable()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HystrixObservableCommandChain#toResultCommandPairObservable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"rx.Observable HystrixObservableCommandChain.toResultCommandPairObservable()"})
  public void testToResultCommandPairObservable_thenReturnNull() {
    // Arrange
    HystrixObservableCommandChain<Object> hystrixObservableCommandChain =
        new HystrixObservableCommandChain<>(new ArrayList<>());

    // Act and Assert
    assertNull(hystrixObservableCommandChain.toResultCommandPairObservable());
  }

  /**
   * Test {@link HystrixObservableCommandChain#toObservable()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HystrixObservableCommandChain#toObservable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"rx.Observable HystrixObservableCommandChain.toObservable()"})
  public void testToObservable_thenReturnNull() {
    // Arrange
    HystrixObservableCommandChain<Object> hystrixObservableCommandChain =
        new HystrixObservableCommandChain<>(new ArrayList<>());

    // Act and Assert
    assertNull(hystrixObservableCommandChain.toObservable());
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
