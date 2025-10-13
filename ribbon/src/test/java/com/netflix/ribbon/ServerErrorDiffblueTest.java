package com.netflix.ribbon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ServerErrorDiffblueTest {
  /**
   * Test {@link ServerError#ServerError(String, Throwable)}.
   *
   * <ul>
   *   <li>Then return Message is {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link ServerError#ServerError(String, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServerError.<init>(String)",
    "void ServerError.<init>(String, Throwable)",
    "void ServerError.<init>(Throwable)"
  })
  public void testNewServerError_thenReturnMessageIsNotAllWhoWanderAreLost() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ServerError actualServerError = new ServerError("Not all who wander are lost", cause);

    // Assert
    assertEquals("Not all who wander are lost", actualServerError.getMessage());
    assertEquals(0, actualServerError.getSuppressed().length);
    assertSame(cause, actualServerError.getCause());
  }

  /**
   * Test {@link ServerError#ServerError(String)}.
   *
   * <ul>
   *   <li>When {@code Not all who wander are lost}.
   *   <li>Then return Cause is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServerError#ServerError(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServerError.<init>(String)",
    "void ServerError.<init>(String, Throwable)",
    "void ServerError.<init>(Throwable)"
  })
  public void testNewServerError_whenNotAllWhoWanderAreLost_thenReturnCauseIsNull() {
    // Arrange and Act
    ServerError actualServerError = new ServerError("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", actualServerError.getMessage());
    assertNull(actualServerError.getCause());
    assertEquals(0, actualServerError.getSuppressed().length);
  }

  /**
   * Test {@link ServerError#ServerError(Throwable)}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   *   <li>Then return Message is {@code Throwable}.
   * </ul>
   *
   * <p>Method under test: {@link ServerError#ServerError(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServerError.<init>(String)",
    "void ServerError.<init>(String, Throwable)",
    "void ServerError.<init>(Throwable)"
  })
  public void testNewServerError_whenThrowable_thenReturnMessageIsJavaLangThrowable() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ServerError actualServerError = new ServerError(cause);

    // Assert
    assertEquals("java.lang.Throwable", actualServerError.getMessage());
    assertEquals(0, actualServerError.getSuppressed().length);
    assertSame(cause, actualServerError.getCause());
  }
}
