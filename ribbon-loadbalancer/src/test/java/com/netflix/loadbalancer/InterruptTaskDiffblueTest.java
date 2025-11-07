package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InterruptTaskDiffblueTest {
  /**
   * Test {@link InterruptTask#InterruptTask(Thread, long)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link InterruptTask#target} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InterruptTask#InterruptTask(Thread, long)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InterruptTask.<init>(Thread, long)"})
  public void testNewInterruptTask_whenNull_thenReturnTargetIsNull() {
    // Arrange, Act and Assert
    assertNull((new InterruptTask(null, 1L)).target);
  }

  /**
   * Test {@link InterruptTask#cancel()}.
   * <ul>
   *   <li>Given {@link InterruptTask#InterruptTask(long)} with millis is one.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InterruptTask#cancel()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InterruptTask.cancel()"})
  public void testCancel_givenInterruptTaskWithMillisIsOne_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new InterruptTask(1L)).cancel());
  }
}
