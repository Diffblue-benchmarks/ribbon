package com.netflix.loadbalancer;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.Test;

public class InterruptTaskDiffblueTest {
  /**
   * Method under test: {@link InterruptTask#cancel()}
   */
  @Test
  public void testCancel() {
    // Arrange, Act and Assert
    assertTrue((new InterruptTask(1L)).cancel());
    assertTrue((new InterruptTask(new Thread(mock(Runnable.class), "foo"), 3L)).cancel());
  }
}
