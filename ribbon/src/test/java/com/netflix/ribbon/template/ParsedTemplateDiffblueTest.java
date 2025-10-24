package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ParsedTemplateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ParsedTemplate#ParsedTemplate(List, String)}
   *   <li>{@link ParsedTemplate#getParsed()}
   *   <li>{@link ParsedTemplate#getTemplate()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ParsedTemplate.<init>(List, String)",
    "List ParsedTemplate.getParsed()",
    "String ParsedTemplate.getTemplate()"
  })
  public void testGettersAndSetters() {
    // Arrange
    ArrayList<Object> parsed = new ArrayList<>();

    // Act
    ParsedTemplate actualParsedTemplate =
        new ParsedTemplate(
            parsed, "\"http://localhost:8080/api/{userId}/details?name={name}&age={age}\"");
    List<Object> actualParsed = actualParsedTemplate.getParsed();

    // Assert
    assertEquals(
        "\"http://localhost:8080/api/{userId}/details?name={name}&age={age}\"",
        actualParsedTemplate.getTemplate());
    assertTrue(actualParsed.isEmpty());
    assertSame(parsed, actualParsed);
  }

  /**
   * Test {@link ParsedTemplate#create(String)}.
   *
   * <ul>
   *   <li>When a string.
   *   <li>Then return Parsed size is nineteen.
   * </ul>
   *
   * <p>Method under test: {@link ParsedTemplate#create(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ParsedTemplate ParsedTemplate.create(String)"})
  public void testCreate_whenAString_thenReturnParsedSizeIsNineteen() {
    // Arrange and Act
    ParsedTemplate actualCreateResult =
        ParsedTemplate.create(
            "\"http://{hostName}:{portNumber}/{contextPath}/{apiVersion}/{resourcePath}?{queryParam1}={value1}&"
                + "{queryParam2}={value2}\"");

    // Assert
    List<Object> parsed = actualCreateResult.getParsed();
    assertEquals(19, parsed.size());
    Object getResult = parsed.get(1);
    assertTrue(getResult instanceof PathVar);
    Object getResult2 = parsed.get(17);
    assertTrue(getResult2 instanceof PathVar);
    assertEquals(":", parsed.get(2));
    assertEquals("=", parsed.get(Short.SIZE));
    assertEquals("\"", parsed.get(18));
    assertEquals("\"http://", parsed.get(0));
    assertEquals(
        "\"http://{hostName}:{portNumber}/{contextPath}/{apiVersion}/{resourcePath}?{queryParam1}={value1}&"
            + "{queryParam2}={value2}\"",
        actualCreateResult.getTemplate());
    assertEquals("hostName", getResult.toString());
    assertEquals("value2", getResult2.toString());
  }

  /**
   * Test {@link ParsedTemplate#create(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return Template is empty string.
   * </ul>
   *
   * <p>Method under test: {@link ParsedTemplate#create(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Template is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ParsedTemplate#create(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ParsedTemplate ParsedTemplate.create(String)"})
  public void testCreate_whenNull_thenReturnTemplateIsNull() {
    // Arrange and Act
    ParsedTemplate actualCreateResult = ParsedTemplate.create(null);

    // Assert
    assertNull(actualCreateResult.getTemplate());
    assertTrue(actualCreateResult.getParsed().isEmpty());
  }
}
