package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TemplateParserDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test: {@link TemplateParser#parseTemplate(String)}
   */
  @Test
  public void testParseTemplate() {
    // Arrange and Act
    List<Object> actualParseTemplateResult = TemplateParser.parseTemplate("Template");

    // Assert
    assertEquals(1, actualParseTemplateResult.size());
    assertEquals("Template", actualParseTemplateResult.get(0));
  }

  /**
   * Method under test: {@link TemplateParser#parseTemplate(String)}
   */
  @Test
  public void testParseTemplate2() {
    // Arrange and Act
    List<Object> actualParseTemplateResult = TemplateParser.parseTemplate(null);

    // Assert
    assertTrue(actualParseTemplateResult.isEmpty());
  }

  /**
   * Method under test: {@link TemplateParser#parseTemplate(String)}
   */
  @Test
  public void testParseTemplate3() {
    // Arrange and Act
    List<Object> actualParseTemplateResult = TemplateParser.parseTemplate("");

    // Assert
    assertTrue(actualParseTemplateResult.isEmpty());
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  public void testToData() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act and Assert
    assertEquals("Template", TemplateParser.toData(variables, ParsedTemplate.create("Template")));
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  public void testToData2() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act and Assert
    assertEquals("Template", TemplateParser.toData(variables, ParsedTemplate.create("Template")));
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  public void testToData3() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));
    variables.put("foo", "42");

    // Act and Assert
    assertEquals("Template", TemplateParser.toData(variables, ParsedTemplate.create("Template")));
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  public void testToData4() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act and Assert
    assertEquals("", TemplateParser.toData(variables, "Template", new ArrayList<>()));
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  public void testToData5() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add(new TemplateVar("Val"));

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(variables, "Template", parsedList);
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  public void testToData6() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add(new MatrixVar("Val"));

    // Act and Assert
    assertEquals("", TemplateParser.toData(variables, "Template", parsedList));
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  public void testToData7() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add(new PathVar("Val"));

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(variables, "Template", parsedList);
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  public void testToData8() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act and Assert
    assertEquals("", TemplateParser.toData(variables, "Template", new ArrayList<>()));
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  public void testToData9() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));
    variables.put("foo", "42");

    // Act and Assert
    assertEquals("", TemplateParser.toData(variables, "Template", new ArrayList<>()));
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  public void testToData10() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add("42");

    // Act and Assert
    assertEquals("42", TemplateParser.toData(variables, "Template", parsedList));
  }

  /**
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  public void testToData11() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add("42");
    parsedList.add("42");

    // Act and Assert
    assertEquals("4242", TemplateParser.toData(variables, "Template", parsedList));
  }
}
