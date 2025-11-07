package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PathVarDiffblueTest {
  /**
   * Test {@link PathVar#PathVar(String)}.
   * <p>
   * Method under test: {@link PathVar#PathVar(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PathVar.<init>(String)"})
  public void testNewPathVar() {
    // Arrange, Act and Assert
    assertEquals("Val", (new PathVar("Val")).toString());
  }
}
