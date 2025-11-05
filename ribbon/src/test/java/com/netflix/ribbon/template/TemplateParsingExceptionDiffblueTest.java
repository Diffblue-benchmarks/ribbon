package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class TemplateParsingExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link TemplateParsingException#TemplateParsingException(String)}
   */
  @Test
  public void testNewTemplateParsingException() {
    // Arrange and Act
    TemplateParsingException actualTemplateParsingException = new TemplateParsingException("Arg0");

    // Assert
    assertEquals("Arg0", actualTemplateParsingException.getMessage());
    assertNull(actualTemplateParsingException.getCause());
    assertEquals(0, actualTemplateParsingException.getSuppressed().length);
  }

  /**
   * Method under test:
   * {@link TemplateParsingException#TemplateParsingException(String, Throwable)}
   */
  @Test
  public void testNewTemplateParsingException2() {
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
