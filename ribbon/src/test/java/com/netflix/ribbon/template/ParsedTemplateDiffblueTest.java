package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ParsedTemplateDiffblueTest {
  /**
   * Method under test: {@link ParsedTemplate#create(String)}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    ParsedTemplate actualCreateResult = ParsedTemplate.create("Template");

    // Assert
    assertEquals("Template", actualCreateResult.getTemplate());
    List<Object> parsed = actualCreateResult.getParsed();
    assertEquals(1, parsed.size());
    assertEquals("Template", parsed.get(0));
  }

  /**
   * Method under test: {@link ParsedTemplate#create(String)}
   */
  @Test
  public void testCreate2() {
    // Arrange and Act
    ParsedTemplate actualCreateResult = ParsedTemplate.create(null);

    // Assert
    assertNull(actualCreateResult.getTemplate());
    assertTrue(actualCreateResult.getParsed().isEmpty());
  }

  /**
   * Method under test: {@link ParsedTemplate#create(String)}
   */
  @Test
  public void testCreate3() {
    // Arrange and Act
    ParsedTemplate actualCreateResult = ParsedTemplate.create("");

    // Assert
    assertEquals("", actualCreateResult.getTemplate());
    assertTrue(actualCreateResult.getParsed().isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ParsedTemplate#ParsedTemplate(List, String)}
   *   <li>{@link ParsedTemplate#getParsed()}
   *   <li>{@link ParsedTemplate#getTemplate()}
   * </ul>
   */
  @Test
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
}
