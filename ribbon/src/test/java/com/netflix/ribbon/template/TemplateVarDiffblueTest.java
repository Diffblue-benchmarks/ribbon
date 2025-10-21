package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TemplateVarDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TemplateVar#TemplateVar(String)}
   *   <li>{@link TemplateVar#toString()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TemplateVar.<init>(String)", "String TemplateVar.toString()"})
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(
        "\"http://localhost:8080/api/v1/users/{userId}/posts/{postId}\"",
        new TemplateVar("\"http://localhost:8080/api/v1/users/{userId}/posts/{postId}\"")
            .toString());
  }
}
