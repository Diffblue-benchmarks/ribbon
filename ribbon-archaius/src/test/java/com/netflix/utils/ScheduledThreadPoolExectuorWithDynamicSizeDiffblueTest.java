package com.netflix.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.config.DynamicIntProperty;
import java.util.concurrent.ThreadFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ScheduledThreadPoolExectuorWithDynamicSizeDiffblueTest {
  /**
   * Test {@link ScheduledThreadPoolExectuorWithDynamicSize#ScheduledThreadPoolExectuorWithDynamicSize(DynamicIntProperty, ThreadFactory)}.
   * <p>
   * Method under test: {@link ScheduledThreadPoolExectuorWithDynamicSize#ScheduledThreadPoolExectuorWithDynamicSize(DynamicIntProperty, ThreadFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ScheduledThreadPoolExectuorWithDynamicSize.<init>(DynamicIntProperty, ThreadFactory)"})
  public void testNewScheduledThreadPoolExectuorWithDynamicSize() {
    // Arrange
    ThreadFactory threadFactory = mock(ThreadFactory.class);

    // Act
    ScheduledThreadPoolExectuorWithDynamicSize actualScheduledThreadPoolExectuorWithDynamicSize = new ScheduledThreadPoolExectuorWithDynamicSize(
        new DynamicIntProperty("Prop Name", 42), threadFactory);

    // Assert
    assertEquals(0, actualScheduledThreadPoolExectuorWithDynamicSize.getActiveCount());
    assertEquals(0, actualScheduledThreadPoolExectuorWithDynamicSize.getLargestPoolSize());
    assertEquals(0, actualScheduledThreadPoolExectuorWithDynamicSize.getPoolSize());
    assertEquals(0L, actualScheduledThreadPoolExectuorWithDynamicSize.getCompletedTaskCount());
    assertEquals(0L, actualScheduledThreadPoolExectuorWithDynamicSize.getTaskCount());
    assertEquals(42, actualScheduledThreadPoolExectuorWithDynamicSize.getCorePoolSize());
    assertFalse(actualScheduledThreadPoolExectuorWithDynamicSize.getContinueExistingPeriodicTasksAfterShutdownPolicy());
    assertFalse(actualScheduledThreadPoolExectuorWithDynamicSize.getRemoveOnCancelPolicy());
    assertTrue(actualScheduledThreadPoolExectuorWithDynamicSize.getQueue().isEmpty());
    assertTrue(actualScheduledThreadPoolExectuorWithDynamicSize.getExecuteExistingDelayedTasksAfterShutdownPolicy());
    assertEquals(Integer.MAX_VALUE, actualScheduledThreadPoolExectuorWithDynamicSize.getMaximumPoolSize());
    assertSame(threadFactory, actualScheduledThreadPoolExectuorWithDynamicSize.getThreadFactory());
  }
}
