package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TemplateVarDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TemplateVar#TemplateVar(String)}
   *   <li>{@link TemplateVar#toString()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TemplateVar.<init>(String)", "String TemplateVar.toString()"})
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("Val", (new TemplateVar("Val")).toString());
  }
}
