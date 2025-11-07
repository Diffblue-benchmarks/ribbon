package com.netflix.ribbon.testutils;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import rx.functions.Func0;

public class TestUtilsDiffblueTest {
  /**
   * Test {@link TestUtils#waitUntilTrueOrTimeout(int, Func0)}.
   * <p>
   * Method under test: {@link TestUtils#waitUntilTrueOrTimeout(int, Func0)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TestUtils.waitUntilTrueOrTimeout(int, Func0)"})
  public void testWaitUntilTrueOrTimeout() {
    // Arrange
    Func0<Boolean> func = mock(Func0.class);
    when(func.call()).thenReturn(true);

    // Act
    TestUtils.waitUntilTrueOrTimeout(10, func);

    // Assert
    verify(func, atLeast(1)).call();
  }
}
