package com.netflix.client;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.SocketException;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RequestSpecificRetryHandlerDiffblueTest {
  /**
   * Test {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean)}.
   *
   * <ul>
   *   <li>When {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RequestSpecificRetryHandler.<init>(boolean, boolean)"})
  public void testNewRequestSpecificRetryHandler_whenFalse() {
    // Arrange and Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler =
        new RequestSpecificRetryHandler(false, true);

    // Assert
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> resultClassList =
        actualRequestSpecificRetryHandler.connectionRelated;
    assertEquals(1, resultClassList.size());
    Class<SocketException> expectedGetResult = SocketException.class;
    assertEquals(expectedGetResult, resultClassList.get(0));
  }

  /**
   * Test {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean, boolean)}.
   *
   * <ul>
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RequestSpecificRetryHandler#RequestSpecificRetryHandler(boolean,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RequestSpecificRetryHandler.<init>(boolean, boolean)"})
  public void testNewRequestSpecificRetryHandler_whenTrue() {
    // Arrange and Act
    RequestSpecificRetryHandler actualRequestSpecificRetryHandler =
        new RequestSpecificRetryHandler(true, true);

    // Assert
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnNextServer());
    assertEquals(0, actualRequestSpecificRetryHandler.getMaxRetriesOnSameServer());
    List<Class<? extends Throwable>> resultClassList =
        actualRequestSpecificRetryHandler.connectionRelated;
    assertEquals(1, resultClassList.size());
    Class<SocketException> expectedGetResult = SocketException.class;
    assertEquals(expectedGetResult, resultClassList.get(0));
  }
}
