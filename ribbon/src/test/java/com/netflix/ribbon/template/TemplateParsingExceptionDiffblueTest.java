package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TemplateParsingExceptionDiffblueTest {
  /**
   * Test {@link TemplateParsingException#TemplateParsingException(String)}.
   *
   * <ul>
   *   <li>When a string.
   *   <li>Then return Cause is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParsingException#TemplateParsingException(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TemplateParsingException.<init>(String)",
    "void TemplateParsingException.<init>(String, Throwable)"
  })
  public void testNewTemplateParsingException_whenAString_thenReturnCauseIsNull() {
    // Arrange and Act
    TemplateParsingException actualTemplateParsingException =
        new TemplateParsingException(
            "\"Failed to parse the template due to invalid syntax at line 10, column 5. Please check the template"
                + " structure.\"");

    // Assert
    assertEquals(
        "\"Failed to parse the template due to invalid syntax at line 10, column 5. Please check the template"
            + " structure.\"",
        actualTemplateParsingException.getMessage());
    assertNull(actualTemplateParsingException.getCause());
    assertEquals(0, actualTemplateParsingException.getSuppressed().length);
  }

  /**
   * Test {@link TemplateParsingException#TemplateParsingException(String, Throwable)}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   *   <li>Then return Cause is {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParsingException#TemplateParsingException(String,
   * Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TemplateParsingException.<init>(String)",
    "void TemplateParsingException.<init>(String, Throwable)"
  })
  public void testNewTemplateParsingException_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable arg1 = new Throwable();

    // Act
    TemplateParsingException actualTemplateParsingException =
        new TemplateParsingException(
            "\"Failed to parse the provided template due to unexpected syntax at line 10, column 5. Please check the"
                + " template for errors.\"",
            arg1);

    // Assert
    assertEquals(
        "\"Failed to parse the provided template due to unexpected syntax at line 10, column 5. Please check the"
            + " template for errors.\"",
        actualTemplateParsingException.getMessage());
    assertEquals(0, actualTemplateParsingException.getSuppressed().length);
    assertSame(arg1, actualTemplateParsingException.getCause());
  }
}
