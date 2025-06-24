package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TemplateParsingExceptionDiffblueTest {
  /**
   * Test {@link TemplateParsingException#TemplateParsingException(String)}.
   * <ul>
   *   <li>When {@code Arg0}.</li>
   *   <li>Then return Cause is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParsingException#TemplateParsingException(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TemplateParsingException.<init>(String)",
      "void TemplateParsingException.<init>(String, Throwable)"})
  public void testNewTemplateParsingException_whenArg0_thenReturnCauseIsNull() {
    // Arrange and Act
    TemplateParsingException actualTemplateParsingException = new TemplateParsingException("Arg0");

    // Assert
    assertEquals("Arg0", actualTemplateParsingException.getMessage());
    assertNull(actualTemplateParsingException.getCause());
    assertEquals(0, actualTemplateParsingException.getSuppressed().length);
  }

  /**
   * Test {@link TemplateParsingException#TemplateParsingException(String, Throwable)}.
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.</li>
   *   <li>Then return Cause is {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParsingException#TemplateParsingException(String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TemplateParsingException.<init>(String)",
      "void TemplateParsingException.<init>(String, Throwable)"})
  public void testNewTemplateParsingException_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable arg1 = new Throwable();

    // Act
    TemplateParsingException actualTemplateParsingException = new TemplateParsingException("Arg0", arg1);

    // Assert
    assertEquals("Arg0", actualTemplateParsingException.getMessage());
    assertEquals(0, actualTemplateParsingException.getSuppressed().length);
    assertSame(arg1, actualTemplateParsingException.getCause());
  }
}
