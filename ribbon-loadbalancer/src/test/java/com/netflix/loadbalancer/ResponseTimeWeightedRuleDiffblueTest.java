package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;

public class ResponseTimeWeightedRuleDiffblueTest {
  /**
   * Method under test:
   * {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();

    // Act and Assert
    assertNull(responseTimeWeightedRule.choose(new BaseLoadBalancer(), "Key"));
  }

  /**
   * Method under test:
   * {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose2() {
    // Arrange, Act and Assert
    assertNull((new ResponseTimeWeightedRule()).choose(null, "Key"));
  }

  /**
   * Method under test:
   * {@link ResponseTimeWeightedRule#choose(ILoadBalancer, Object)}
   */
  @Test
  public void testChoose3() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = new ResponseTimeWeightedRule();
    IPing ping = mock(IPing.class);

    // Act and Assert
    assertNull(responseTimeWeightedRule.choose(new BaseLoadBalancer(ping, new AvailabilityFilteringRule()), "Key"));
  }

  /**
   * Method under test:
   * {@link ResponseTimeWeightedRule.DynamicServerWeightTask#run()}
   */
  @Test
  public void testDynamicServerWeightTaskRun() {
    // Arrange
    ResponseTimeWeightedRule responseTimeWeightedRule = mock(ResponseTimeWeightedRule.class);
    when(responseTimeWeightedRule.getLoadBalancer()).thenReturn(new BaseLoadBalancer());

    // Act
    (responseTimeWeightedRule.new DynamicServerWeightTask()).run();

    // Assert
    verify(responseTimeWeightedRule).getLoadBalancer();
  }

  /**
   * Method under test:
   * {@link ResponseTimeWeightedRule#ResponseTimeWeightedRule()}
   */
  @Test
  public void testNewResponseTimeWeightedRule() {
    // Arrange and Act
    ResponseTimeWeightedRule actualResponseTimeWeightedRule = new ResponseTimeWeightedRule();

    // Assert
    assertEquals("unknown", actualResponseTimeWeightedRule.name);
    assertNull(actualResponseTimeWeightedRule.getLoadBalancer());
    assertNull(actualResponseTimeWeightedRule.serverWeightTimer);
    assertFalse(actualResponseTimeWeightedRule.serverWeightAssignmentInProgress.get());
  }
}
