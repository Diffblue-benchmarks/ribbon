package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link TemplateParser#parseTemplate(String)}.
   *
   * <ul>
   *   <li>When a string.
   *   <li>Then return size is five.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#parseTemplate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TemplateParser.parseTemplate(String)"})
  public void testParseTemplate_whenAString_thenReturnSizeIsFive() {
    // Arrange and Act
    List<Object> actualParseTemplateResult =
        TemplateParser.parseTemplate(
            "\"{name: 'John Doe', age: 30, address: {street: '123 Main St', city: 'Springfield', state: 'IL', zip:"
                + " '62701'}, roles: ['admin', 'user']}\"");

    // Assert
    assertEquals(5, actualParseTemplateResult.size());
    Object getResult = actualParseTemplateResult.get(2);
    assertTrue(getResult instanceof PathVar);
    Object getResult2 = actualParseTemplateResult.get(3);
    assertTrue(getResult2 instanceof PathVar);
    assertEquals(", roles: ['admin', 'user']", getResult2.toString());
    assertEquals("\"", actualParseTemplateResult.get(0));
    assertEquals("\"", actualParseTemplateResult.get(4));
    assertEquals("name: 'John Doe', age: 30, address: ", actualParseTemplateResult.get(1));
    assertEquals(
        "street: '123 Main St', city: 'Springfield', state: 'IL', zip: '62701'",
        getResult.toString());
  }

  /**
   * Test {@link TemplateParser#parseTemplate(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#parseTemplate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TemplateParser.parseTemplate(String)"})
  public void testParseTemplate_whenEmptyString_thenReturnEmpty() {
    // Arrange and Act
    List<Object> actualParseTemplateResult = TemplateParser.parseTemplate("");

    // Assert
    assertTrue(actualParseTemplateResult.isEmpty());
  }

  /**
   * Test {@link TemplateParser#parseTemplate(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#parseTemplate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List TemplateParser.parseTemplate(String)"})
  public void testParseTemplate_whenNull_thenReturnEmpty() {
    // Arrange and Act
    List<Object> actualParseTemplateResult = TemplateParser.parseTemplate(null);

    // Assert
    assertTrue(actualParseTemplateResult.isEmpty());
  }

  /**
   * Test {@link TemplateParser#toData(Map, ParsedTemplate)} with {@code variables}, {@code
   * parsedTemplate}.
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, ParsedTemplate)"})
  public void testToDataWithVariablesParsedTemplate() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsed = new ArrayList<>();
    parsed.add(
        new TemplateVar(
            "\"customerID:12345,orderID:67890,productID:111213,quantity:5,price:99.99\""));

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(
        variables,
        new ParsedTemplate(
            parsed, "\"http://localhost:8080/api/{userId}/details?name={name}&age={age}\""));
  }

  /**
   * Test {@link TemplateParser#toData(Map, ParsedTemplate)} with {@code variables}, {@code
   * parsedTemplate}.
   *
   * <ul>
   *   <li>Given {@code "TestKey"}.
   *   <li>When {@link HashMap#HashMap()} {@code "TestKey"} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, ParsedTemplate)"})
  public void testToDataWithVariablesParsedTemplate_givenTestKey_whenHashMapTestKeyIs42()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("\"TestKey\"", "42");

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(
        variables,
        ParsedTemplate.create(
            "\"http://{hostName}:{portNumber}/{contextPath}/{apiVersion}/{resourcePath}?{queryParam1}={value1}&"
                + "{queryParam2}={value2}\""));
  }

  /**
   * Test {@link TemplateParser#toData(Map, ParsedTemplate)} with {@code variables}, {@code
   * parsedTemplate}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, ParsedTemplate)"})
  public void testToDataWithVariablesParsedTemplate_thenReturnEmptyString()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsed = new ArrayList<>();
    parsed.add(new MatrixVar("\"matrixParam1=value1;matrixParam2=value2;matrixParam3=value3\""));

    // Act
    String actualToDataResult =
        TemplateParser.toData(
            variables,
            new ParsedTemplate(
                parsed, "\"http://localhost:8080/api/{userId}/details?name={name}&age={age}\""));

    // Assert
    assertEquals("", actualToDataResult);
  }

  /**
   * Test {@link TemplateParser#toData(Map, ParsedTemplate)} with {@code variables}, {@code
   * parsedTemplate}.
   *
   * <ul>
   *   <li>When create a string.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, ParsedTemplate)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, ParsedTemplate)"})
  public void testToDataWithVariablesParsedTemplate_whenCreateAString()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(
        variables,
        ParsedTemplate.create(
            "\"http://{hostName}:{portNumber}/{contextPath}/{apiVersion}/{resourcePath}?{queryParam1}={value1}&"
                + "{queryParam2}={value2}\""));
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template},
   * {@code parsedList}.
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add(
        new TemplateVar(
            "\"customerID:12345,orderID:67890,productID:111213,quantity:5,price:99.99\""));

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(
        variables, "\"{name:John, age:30, city:New York, occupation:Engineer}\"", parsedList);
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template},
   * {@code parsedList}.
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList2() throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add(
        new MatrixVar("\"matrixParam1=value1;matrixParam2=value2;matrixParam3=value3\""));

    // Act
    String actualToDataResult =
        TemplateParser.toData(
            variables, "\"{name:John, age:30, city:New York, occupation:Engineer}\"", parsedList);

    // Assert
    assertEquals("", actualToDataResult);
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template},
   * {@code parsedList}.
   *
   * <ul>
   *   <li>Given {@link PathVar#PathVar(String)} with val is {@code "userProfile"}.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_givenPathVarWithValIsUserProfile()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add(new PathVar("\"userProfile\""));

    // Act and Assert
    thrown.expect(TemplateParsingException.class);
    TemplateParser.toData(
        variables, "\"{name:John, age:30, city:New York, occupation:Engineer}\"", parsedList);
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template},
   * {@code parsedList}.
   *
   * <ul>
   *   <li>Given {@code "TestKey"}.
   *   <li>When {@link HashMap#HashMap()} {@code "TestKey"} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_givenTestKey_whenHashMapTestKeyIs42()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("\"TestKey\"", "42");

    // Act
    String actualToDataResult =
        TemplateParser.toData(
            variables,
            "\"{name:John, age:30, city:New York, occupation:Engineer}\"",
            new ArrayList<>());

    // Assert
    assertEquals("", actualToDataResult);
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template},
   * {@code parsedList}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_whenArrayListAdd42_thenReturn42()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add("42");

    // Act
    String actualToDataResult =
        TemplateParser.toData(
            variables, "\"{name:John, age:30, city:New York, occupation:Engineer}\"", parsedList);

    // Assert
    assertEquals("42", actualToDataResult);
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template},
   * {@code parsedList}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return {@code 4242}.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_whenArrayListAdd42_thenReturn4242()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    ArrayList<Object> parsedList = new ArrayList<>();
    parsedList.add("42");
    parsedList.add("42");

    // Act
    String actualToDataResult =
        TemplateParser.toData(
            variables, "\"{name:John, age:30, city:New York, occupation:Engineer}\"", parsedList);

    // Assert
    assertEquals("4242", actualToDataResult);
  }

  /**
   * Test {@link TemplateParser#toData(Map, String, List)} with {@code variables}, {@code template},
   * {@code parsedList}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link TemplateParser#toData(Map, String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TemplateParser.toData(Map, String, List)"})
  public void testToDataWithVariablesTemplateParsedList_whenArrayList_thenReturnEmptyString()
      throws TemplateParsingException {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    String actualToDataResult =
        TemplateParser.toData(
            variables,
            "\"{name:John, age:30, city:New York, occupation:Engineer}\"",
            new ArrayList<>());

    // Assert
    assertEquals("", actualToDataResult);
  }
}
