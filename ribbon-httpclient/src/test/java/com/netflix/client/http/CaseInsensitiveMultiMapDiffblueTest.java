package com.netflix.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.google.common.collect.ArrayListMultimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CaseInsensitiveMultiMapDiffblueTest {
  /**
   * Test {@link CaseInsensitiveMultiMap#getFirstValue(String)}.
   * <ul>
   *   <li>Given {@link CaseInsensitiveMultiMap} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#getFirstValue(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CaseInsensitiveMultiMap.getFirstValue(String)"})
  public void testGetFirstValue_givenCaseInsensitiveMultiMap_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new CaseInsensitiveMultiMap()).getFirstValue("https://example.org/example"));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#getFirstValue(String)}.
   * <ul>
   *   <li>Then return {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#getFirstValue(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String CaseInsensitiveMultiMap.getFirstValue(String)"})
  public void testGetFirstValue_thenReturnHttpsExampleOrgExample() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act and Assert
    assertEquals("https://example.org/example", caseInsensitiveMultiMap.getFirstValue("https://example.org/example"));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#getAllValues(String)}.
   * <ul>
   *   <li>Given {@link CaseInsensitiveMultiMap} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#getAllValues(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CaseInsensitiveMultiMap.getAllValues(String)"})
  public void testGetAllValues_givenCaseInsensitiveMultiMap_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new CaseInsensitiveMultiMap()).getAllValues("https://example.org/example").isEmpty());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#getAllValues(String)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#getAllValues(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CaseInsensitiveMultiMap.getAllValues(String)"})
  public void testGetAllValues_thenReturnSizeIsOne() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act
    List<String> actualAllValues = caseInsensitiveMultiMap.getAllValues("https://example.org/example");

    // Assert
    assertEquals(1, actualAllValues.size());
    assertEquals("https://example.org/example", actualAllValues.get(0));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#getAllHeaders()}.
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#getAllHeaders()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CaseInsensitiveMultiMap.getAllHeaders()"})
  public void testGetAllHeaders() {
    // Arrange, Act and Assert
    assertTrue((new CaseInsensitiveMultiMap()).getAllHeaders().isEmpty());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#containsHeader(String)}.
   * <ul>
   *   <li>Given {@link CaseInsensitiveMultiMap} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#containsHeader(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CaseInsensitiveMultiMap.containsHeader(String)"})
  public void testContainsHeader_givenCaseInsensitiveMultiMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new CaseInsensitiveMultiMap()).containsHeader("https://example.org/example"));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#containsHeader(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#containsHeader(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CaseInsensitiveMultiMap.containsHeader(String)"})
  public void testContainsHeader_thenReturnTrue() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act and Assert
    assertTrue(caseInsensitiveMultiMap.containsHeader("https://example.org/example"));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#addHeader(String, String)}.
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#addHeader(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CaseInsensitiveMultiMap.addHeader(String, String)"})
  public void testAddHeader() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Assert that nothing has changed
    assertEquals(1, caseInsensitiveMultiMap.getAllHeaders().size());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#addHeader(String, String)}.
   * <ul>
   *   <li>Then {@link CaseInsensitiveMultiMap} (default constructor) AllHeaders size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#addHeader(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CaseInsensitiveMultiMap.addHeader(String, String)"})
  public void testAddHeader_thenCaseInsensitiveMultiMapAllHeadersSizeIsOne() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

    // Act
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Assert
    assertEquals(1, caseInsensitiveMultiMap.getAllHeaders().size());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#addHeader(String, String)}.
   * <ul>
   *   <li>Then {@link CaseInsensitiveMultiMap} (default constructor) AllHeaders size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#addHeader(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CaseInsensitiveMultiMap.addHeader(String, String)"})
  public void testAddHeader_thenCaseInsensitiveMultiMapAllHeadersSizeIsTwo() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "42");

    // Act
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Assert
    assertEquals(2, caseInsensitiveMultiMap.getAllHeaders().size());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#asMap()}.
   * <ul>
   *   <li>Given {@link CaseInsensitiveMultiMap} (default constructor) addHeader {@code Name} and {@code 42}.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map CaseInsensitiveMultiMap.asMap()"})
  public void testAsMap_givenCaseInsensitiveMultiMapAddHeaderNameAnd42_thenReturnSizeIsTwo() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("Name", "42");
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act
    Map<String, Collection<String>> actualAsMapResult = caseInsensitiveMultiMap.asMap();

    // Assert
    assertEquals(2, actualAsMapResult.size());
    Collection<String> getResult = actualAsMapResult.get("Name");
    assertEquals(1, getResult.size());
    assertTrue(getResult instanceof List);
    assertEquals("42", ((List<String>) getResult).get(0));
    assertTrue(actualAsMapResult.containsKey("https://example.org/example"));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#asMap()}.
   * <ul>
   *   <li>Given {@link CaseInsensitiveMultiMap} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map CaseInsensitiveMultiMap.asMap()"})
  public void testAsMap_givenCaseInsensitiveMultiMap_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new CaseInsensitiveMultiMap()).asMap().isEmpty());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#asMap()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map CaseInsensitiveMultiMap.asMap()"})
  public void testAsMap_thenReturnHttpsExampleOrgExampleSizeIsOne() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act
    Map<String, Collection<String>> actualAsMapResult = caseInsensitiveMultiMap.asMap();

    // Assert
    assertEquals(1, actualAsMapResult.size());
    Collection<String> getResult = actualAsMapResult.get("https://example.org/example");
    assertEquals(1, getResult.size());
    assertTrue(getResult instanceof List);
    assertEquals("https://example.org/example", ((List<String>) getResult).get(0));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#asMap()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map CaseInsensitiveMultiMap.asMap()"})
  public void testAsMap_thenReturnHttpsExampleOrgExampleSizeIsTwo() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "42");
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act
    Map<String, Collection<String>> actualAsMapResult = caseInsensitiveMultiMap.asMap();

    // Assert
    assertEquals(1, actualAsMapResult.size());
    Collection<String> getResult = actualAsMapResult.get("https://example.org/example");
    assertEquals(2, getResult.size());
    assertTrue(getResult instanceof List);
    assertEquals("42", ((List<String>) getResult).get(0));
    assertEquals("https://example.org/example", ((List<String>) getResult).get(1));
  }

  /**
   * Test new {@link CaseInsensitiveMultiMap} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CaseInsensitiveMultiMap}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CaseInsensitiveMultiMap.<init>()"})
  public void testNewCaseInsensitiveMultiMap() {
    // Arrange and Act
    CaseInsensitiveMultiMap actualCaseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

    // Assert
    assertTrue(actualCaseInsensitiveMultiMap.map instanceof ArrayListMultimap);
    assertTrue(actualCaseInsensitiveMultiMap.getAllHeaders().isEmpty());
  }
}
