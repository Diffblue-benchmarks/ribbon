package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TemplateVarDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TemplateVar#TemplateVar(String)}
   *   <li>{@link TemplateVar#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("Val", (new TemplateVar("Val")).toString());
  }
}
