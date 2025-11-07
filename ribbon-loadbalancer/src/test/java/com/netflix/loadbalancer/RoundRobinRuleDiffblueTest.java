package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RoundRobinRuleDiffblueTest {
  /**
   * Test {@link RoundRobinRule#RoundRobinRule()}.
   * <p>
   * Method under test: {@link RoundRobinRule#RoundRobinRule()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RoundRobinRule.<init>()"})
  public void testNewRoundRobinRule() {
    // Arrange, Act and Assert
    assertNull((new RoundRobinRule()).getLoadBalancer());
  }

  /**
   * Test {@link RoundRobinRule#RoundRobinRule(ILoadBalancer)}.
   * <p>
   * Method under test: {@link RoundRobinRule#RoundRobinRule(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RoundRobinRule.<init>(ILoadBalancer)"})
  public void testNewRoundRobinRule2() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act and Assert
    assertSame(lb, (new RoundRobinRule(lb)).getLoadBalancer());
  }

  /**
   * Test {@link RoundRobinRule#choose(Object)} with {@code key}.
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} EnablePrimingConnections is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RoundRobinRule.choose(Object)"})
  public void testChooseWithKey_givenBaseLoadBalancerEnablePrimingConnectionsIsTrue() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.addServer(new Server("42"));

    RoundRobinRule roundRobinRule = new RoundRobinRule();
    roundRobinRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(roundRobinRule.choose("Key"));
  }

  /**
   * Test {@link RoundRobinRule#choose(Object)} with {@code key}.
   * <ul>
   *   <li>Given {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RoundRobinRule.choose(Object)"})
  public void testChooseWithKey_givenResponseTimeWeightedRule_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new ResponseTimeWeightedRule()).choose("Key"));
  }

  /**
   * Test {@link RoundRobinRule#choose(Object)} with {@code key}.
   * <ul>
   *   <li>Given {@link RoundRobinRule#RoundRobinRule()} LoadBalancer is {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RoundRobinRule.choose(Object)"})
  public void testChooseWithKey_givenRoundRobinRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    RoundRobinRule roundRobinRule = new RoundRobinRule();
    roundRobinRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(roundRobinRule.choose("Key"));
  }

  /**
   * Test {@link RoundRobinRule#choose(Object)} with {@code key}.
   * <ul>
   *   <li>Given {@link RoundRobinRule#RoundRobinRule()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RoundRobinRule.choose(Object)"})
  public void testChooseWithKey_givenRoundRobinRule_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new RoundRobinRule()).choose("Key"));
  }

  /**
   * Test {@link RoundRobinRule#choose(Object)} with {@code key}.
   * <ul>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoundRobinRule#choose(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RoundRobinRule.choose(Object)"})
  public void testChooseWithKey_thenReturnServerWithIdIs42() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    RoundRobinRule roundRobinRule = new RoundRobinRule();
    roundRobinRule.setLoadBalancer(lb);

    // Act and Assert
    assertSame(newServer, roundRobinRule.choose("Key"));
  }

  /**
   * Test {@link RoundRobinRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.</li>
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoundRobinRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RoundRobinRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenServerWithIdIs42_thenReturnServerWithIdIs42() {
    // Arrange
    RoundRobinRule roundRobinRule = new RoundRobinRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    Server newServer = new Server("42");
    lb.addServer(newServer);

    // Act and Assert
    assertSame(newServer, roundRobinRule.choose(lb, "Key"));
  }

  /**
   * Test {@link RoundRobinRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()} EnablePrimingConnections is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoundRobinRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RoundRobinRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_givenTrue_whenBaseLoadBalancerEnablePrimingConnectionsIsTrue() {
    // Arrange
    RoundRobinRule roundRobinRule = new RoundRobinRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setEnablePrimingConnections(true);
    lb.addServer(new Server("42"));

    // Act and Assert
    assertNull(roundRobinRule.choose(lb, "Key"));
  }

  /**
   * Test {@link RoundRobinRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoundRobinRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RoundRobinRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_whenBaseLoadBalancer_thenReturnNull() {
    // Arrange
    RoundRobinRule roundRobinRule = new RoundRobinRule();

    // Act and Assert
    assertNull(roundRobinRule.choose(new BaseLoadBalancer(), "Key"));
  }

  /**
   * Test {@link RoundRobinRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoundRobinRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server RoundRobinRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new RoundRobinRule()).choose(null, "Key"));
  }
}
