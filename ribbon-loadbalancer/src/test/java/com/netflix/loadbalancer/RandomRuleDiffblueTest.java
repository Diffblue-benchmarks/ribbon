package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RandomRuleDiffblueTest {
  /**
   * Test {@link RandomRule#choose(Object)} with {@code key}.
   * <ul>
   *   <li>Given {@link RandomRule} (default constructor) LoadBalancer is {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RandomRule#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RandomRule.choose(Object)"})
  public void testChooseWithKey_givenRandomRuleLoadBalancerIsBaseLoadBalancer_thenReturnNull() {
    // Arrange
    RandomRule randomRule = new RandomRule();
    randomRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(randomRule.choose("Key"));
  }

  /**
   * Test {@link RandomRule#choose(Object)} with {@code key}.
   * <ul>
   *   <li>Given {@link RandomRule} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RandomRule#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RandomRule.choose(Object)"})
  public void testChooseWithKey_givenRandomRule_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new RandomRule()).choose("Key"));
  }

  /**
   * Test {@link RandomRule#choose(Object)} with {@code key}.
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RandomRule#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RandomRule.choose(Object)"})
  public void testChooseWithKey_thenReturnServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    RandomRule randomRule = new RandomRule();
    randomRule.setLoadBalancer(lb);

    // Act and Assert
    assertSame(newServer, randomRule.choose("Key"));
  }

  /**
   * Test {@link RandomRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RandomRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RandomRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_thenReturnServerWithIdIs42() {
    // Arrange
    RandomRule randomRule = new RandomRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    // Act and Assert
    assertSame(newServer, randomRule.choose(lb, "Key"));
  }

  /**
   * Test {@link RandomRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RandomRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RandomRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_whenBaseLoadBalancer_thenReturnNull() {
    // Arrange
    RandomRule randomRule = new RandomRule();

    // Act and Assert
    assertNull(randomRule.choose(new BaseLoadBalancer(), "Key"));
  }

  /**
   * Test {@link RandomRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RandomRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RandomRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new RandomRule()).choose(null, "Key"));
  }

  /**
   * Test new {@link RandomRule} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link RandomRule}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RandomRule.<init>()"})
  public void testNewRandomRule() {
    // Arrange, Act and Assert
    assertNull((new RandomRule()).getLoadBalancer());
  }
}
