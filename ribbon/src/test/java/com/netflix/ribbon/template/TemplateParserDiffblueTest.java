package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class TemplateParserDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link TemplateParser#parseTemplate(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#parseTemplate(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List TemplateParser.parseTemplate(String)"})
  public void testParseTemplate_whenEmptyString_thenReturnEmpty() {
    // Arrange and Act
    List<Object> actualParseTemplateResult = TemplateParser.parseTemplate("");

    // Assert
    assertTrue(actualParseTemplateResult.isEmpty());
  }

  /**
   * Test {@link TemplateParser#parseTemplate(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#parseTemplate(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List TemplateParser.parseTemplate(String)"})
  public void testParseTemplate_whenNull_thenReturnEmpty() {
    // Arrange and Act
    List<Object> actualParseTemplateResult = TemplateParser.parseTemplate(null);

    // Assert
    assertTrue(actualParseTemplateResult.isEmpty());
  }

  /**
   * Test {@link TemplateParser#parseTemplate(String)}.
   * <ul>
   *   <li>When {@code Template}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#parseTemplate(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List TemplateParser.parseTemplate(String)"})
  public void testParseTemplate_whenTemplate_thenReturnSizeIsOne() {
    // Arrange and Act
    List<Object> actualParseTemplateResult = TemplateParser.parseTemplate("Template");

    // Assert
    assertEquals(1, actualParseTemplateResult.size());
    assertEquals("Template", actualParseTemplateResult.get(0));
  }

  /**
   * Test {@link TemplateParser#toData(Map, ParsedTemplate)} with {@code variables}, {@code parsedTemplate}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, ParsedTemplate)"})
  public void testToDataWithVariablesParsedTemplate_givenFoo_whenHashMapFooIs42() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act and Assert
    assertEquals("Template", TemplateParser.toData(variables, ParsedTemplate.create("Template")));
  }

  /**
   * Test {@link TemplateParser#toData(Map, ParsedTemplate)} with {@code variables}, {@code parsedTemplate}.
   * <ul>
   *   <li>Given {@link PathVar#PathVar(String)} with {@code Val}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, ParsedTemplate)"})
  public void testToDataWithVariablesParsedTemplate_givenPathVarWithVal() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsed = new ArrayList<>();
    parsed.add(new PathVar("Val"));

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(variables, new ParsedTemplate(parsed, "Template"));
  }

  /**
   * Test {@link TemplateParser#toData(Map, ParsedTemplate)} with {@code variables}, {@code parsedTemplate}.
   * <ul>
   *   <li>Given {@link TemplateVar#TemplateVar(String)} with {@code Val}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, ParsedTemplate)"})
  public void testToDataWithVariablesParsedTemplate_givenTemplateVarWithVal() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsed = new ArrayList<>();
    parsed.add(new TemplateVar("Val"));

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(variables, new ParsedTemplate(parsed, "Template"));
  }

  /**
   * Test {@link TemplateParser#toData(Map, ParsedTemplate)} with {@code variables}, {@code parsedTemplate}.
   * <ul>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, ParsedTemplate)"})
  public void testToDataWithVariablesParsedTemplate_thenReturnEmptyString() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsed = new ArrayList<>();
    parsed.add(new MatrixVar("Val"));

    // Act and Assert
    assertEquals("", TemplateParser.toData(variables, new ParsedTemplate(parsed, "Template")));
  }

  /**
   * Test {@link TemplateParser#toData(Map, ParsedTemplate)} with {@code variables}, {@code parsedTemplate}.
   * <ul>
   *   <li>When create {@code Template}.</li>
   *   <li>Then return {@code Template}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, ParsedTemplate)"})
  public void testToDataWithVariablesParsedTemplate_whenCreateTemplate_thenReturnTemplate()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act and Assert
    assertEquals("Template", TemplateParser.toData(variables, ParsedTemplate.create("Template")));
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template}, {@code parsedList}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_givenFoo_whenHashMapFooIs42() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act and Assert
    assertEquals("", TemplateParser.toData(variables, "Template", new ArrayList<>()));
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template}, {@code parsedList}.
   * <ul>
   *   <li>Given {@link MatrixVar#MatrixVar(String)} with {@code Val}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_givenMatrixVarWithVal() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add(new MatrixVar("Val"));

    // Act and Assert
    assertEquals("", TemplateParser.toData(variables, "Template", parsedList));
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template}, {@code parsedList}.
   * <ul>
   *   <li>Given {@link PathVar#PathVar(String)} with {@code Val}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_givenPathVarWithVal() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add(new PathVar("Val"));

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(variables, "Template", parsedList);
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template}, {@code parsedList}.
   * <ul>
   *   <li>Given {@link TemplateVar#TemplateVar(String)} with {@code Val}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_givenTemplateVarWithVal() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add(new TemplateVar("Val"));

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(variables, "Template", parsedList);
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template}, {@code parsedList}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_whenArrayListAdd42_thenReturn42()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add("42");

    // Act and Assert
    assertEquals("42", TemplateParser.toData(variables, "Template", parsedList));
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template}, {@code parsedList}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code 4242}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_whenArrayListAdd42_thenReturn4242()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add("42");
    parsedList.add("42");

    // Act and Assert
    assertEquals("4242", TemplateParser.toData(variables, "Template", parsedList));
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template}, {@code parsedList}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_whenArrayList_thenReturnEmptyString()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act and Assert
    assertEquals("", TemplateParser.toData(variables, "Template", new ArrayList<>()));
  }
}
