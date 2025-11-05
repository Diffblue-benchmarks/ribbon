package com.netflix.ribbon.testutils;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import rx.functions.Func0;

public class TestUtilsDiffblueTest {
  /**
   * Method under test: {@link TestUtils#waitUntilTrueOrTimeout(int, Func0)}
   */
  @Test
  public void testWaitUntilTrueOrTimeout() {
    // Arrange
    Func0<Boolean> func = mock(Func0.class);
    when(func.call()).thenReturn(true);

    // Act
    TestUtils.waitUntilTrueOrTimeout(10, func);

    // Assert that nothing has changed
    verify(func, atLeast(1)).call();
  }
}
