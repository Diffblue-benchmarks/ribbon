package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.ResponseTimeWeightedRule.DynamicServerWeightTask;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ResponseTimeWeightedRuleDiffblueTest {
  /**
   * Test DynamicServerWeightTask {@link DynamicServerWeightTask#run()}.
   * <ul>
   *   <li>Then calls {@link AbstractLoadBalancerRule#getLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DynamicServerWeightTask#run()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DynamicServerWeightTask.run()"})
  public void testDynamicServerWeightTaskRun_thenCallsGetLoadBalancer() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = mock(ResponseTimeWeightedRule.class);
    when(responseTimeWeightedRule.getLoadBalancer()).thenReturn(new BaseLoadBalancer());

    // Act
    (responseTimeWeightedRule.new DynamicServerWeightTask()).run();

    // Assert
    verify(responseTimeWeightedRule).getLoadBalancer();
  }

  /**
   * Test {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}.
   * <p>
   * Method under test: {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ResponseTimeWeightedRule.<init>()"})
  public void testNewResponseTimeWeightedRule() {
    // Arrange and Act
    ResponseTimeWeightedRule actualResponseTimeWeightedRule = new ResponseTimeWeightedRule();

    // Assert
    assertEquals("unknown", actualResponseTimeWeightedRule.name);
    assertNull(actualResponseTimeWeightedRule.getLoadBalancer());
    assertNull(actualResponseTimeWeightedRule.serverWeightTimer);
    assertFalse(actualResponseTimeWeightedRule.serverWeightAssignmentInProgress.get());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   * <p>
   * Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<>();

    // Act
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", responseTimeWeightedRule.name);
    assertSame(lb, responseTimeWeightedRule.getLoadBalancer());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   * <ul>
   *   <li>Given {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} initialize {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_givenResponseTimeWeightedRuleInitializeBaseLoadBalancer() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    responseTimeWeightedRule.initialize(new BaseLoadBalancer());
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", responseTimeWeightedRule.name);
    assertSame(lb, responseTimeWeightedRule.getLoadBalancer());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_givenServerWithIdIs42() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.addServer(new Server("42"));

    // Act
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", responseTimeWeightedRule.name);
    assertSame(lb, responseTimeWeightedRule.getLoadBalancer());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   * <ul>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} LoadBalancer is {@link NoOpLoadBalancer} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_thenResponseTimeWeightedRuleLoadBalancerIsNoOpLoadBalancer() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    NoOpLoadBalancer lb = new NoOpLoadBalancer();

    // Act
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Assert
    assertEquals("unknown", responseTimeWeightedRule.name);
    assertSame(lb, responseTimeWeightedRule.getLoadBalancer());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_whenBaseLoadBalancer() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    responseTimeWeightedRule.setLoadBalancer(lb);

    // Assert
    assertEquals("default", responseTimeWeightedRule.name);
    assertSame(lb, responseTimeWeightedRule.getLoadBalancer());
  }

  /**
   * Test {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()} {@link ResponseTimeWeightedRule#name} is {@code unknown}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResponseTimeWeightedRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ResponseTimeWeightedRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_whenNull_thenResponseTimeWeightedRuleNameIsUnknown() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();

    // Act
    responseTimeWeightedRule.setLoadBalancer(null);

    // Assert that nothing has changed
    assertEquals("unknown", responseTimeWeightedRule.name);
  }

  /**
   * Test {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>When {@link BaseLoadBalancer#BaseLoadBalancer()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server ResponseTimeWeightedRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_whenBaseLoadBalancer_thenReturnNull() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();

    // Act and Assert
    assertNull(responseTimeWeightedRule.choose(new BaseLoadBalancer(), "Key"));
  }

  /**
   * Test {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)} with {@code lb}, {@code key}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Server ResponseTimeWeightedRule.choose(ILoadBalancer, Object)"})
  public void testChooseWithLbKey_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new ResponseTimeWeightedRule()).choose(null, "Key"));
  }
}
