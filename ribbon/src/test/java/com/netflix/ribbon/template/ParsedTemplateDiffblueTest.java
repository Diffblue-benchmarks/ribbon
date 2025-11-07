package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ParsedTemplateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ParsedTemplate#ParsedTemplate(List, String)}
   *   <li>{@link ParsedTemplate#getParsed()}
   *   <li>{@link ParsedTemplate#getTemplate()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ParsedTemplate.<init>(List, String)", "List ParsedTemplate.getParsed()",
      "String ParsedTemplate.getTemplate()"})
  public void testGettersAndSetters() {
    // Arrange
    ArrayList<Object> parsed = new ArrayList<>();

    // Act
    ParsedTemplate actualParsedTemplate = new ParsedTemplate(parsed, "Template");
    List<Object> actualParsed = actualParsedTemplate.getParsed();

    // Assert
    assertEquals("Template", actualParsedTemplate.getTemplate());
    assertTrue(actualParsed.isEmpty());
    assertSame(parsed, actualParsed);
  }

  /**
   * Test {@link ParsedTemplate#create(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return Template is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ParsedTemplate#create(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ParsedTemplate ParsedTemplate.create(String)"})
  public void testCreate_whenEmptyString_thenReturnTemplateIsEmptyString() {
    // Arrange and Act
    ParsedTemplate actualCreateResult = ParsedTemplate.create("");

    // Assert
    assertEquals("", actualCreateResult.getTemplate());
    assertTrue(actualCreateResult.getParsed().isEmpty());
  }

  /**
   * Test {@link ParsedTemplate#create(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Template is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ParsedTemplate#create(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ParsedTemplate ParsedTemplate.create(String)"})
  public void testCreate_whenNull_thenReturnTemplateIsNull() {
    // Arrange and Act
    ParsedTemplate actualCreateResult = ParsedTemplate.create(null);

    // Assert
    assertNull(actualCreateResult.getTemplate());
    assertTrue(actualCreateResult.getParsed().isEmpty());
  }

  /**
   * Test {@link ParsedTemplate#create(String)}.
   * <ul>
   *   <li>When {@code Template}.</li>
   *   <li>Then return {@code Template}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ParsedTemplate#create(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ParsedTemplate ParsedTemplate.create(String)"})
  public void testCreate_whenTemplate_thenReturnTemplate() {
    // Arrange and Act
    ParsedTemplate actualCreateResult = ParsedTemplate.create("Template");

    // Assert
    assertEquals("Template", actualCreateResult.getTemplate());
    List<Object> parsed = actualCreateResult.getParsed();
    assertEquals(1, parsed.size());
    assertEquals("Template", parsed.get(0));
  }
}
