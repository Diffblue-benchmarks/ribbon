package com.netflix.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.google.common.collect.ArrayListMultimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class CaseInsensitiveMultiMapDiffblueTest {
  /**
   * Method under test: {@link CaseInsensitiveMultiMap#getFirstValue(String)}
   */
  @Test
  public void testGetFirstValue() {
    // Arrange, Act and Assert
    assertNull((new CaseInsensitiveMultiMap()).getFirstValue("https://example.org/example"));
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#getFirstValue(String)}
   */
  @Test
  public void testGetFirstValue2() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act and Assert
    assertEquals("https://example.org/example", caseInsensitiveMultiMap.getFirstValue("https://example.org/example"));
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#getAllValues(String)}
   */
  @Test
  public void testGetAllValues() {
    // Arrange, Act and Assert
    assertTrue((new CaseInsensitiveMultiMap()).getAllValues("https://example.org/example").isEmpty());
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#getAllValues(String)}
   */
  @Test
  public void testGetAllValues2() {
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
   * Method under test: {@link CaseInsensitiveMultiMap#getAllHeaders()}
   */
  @Test
  public void testGetAllHeaders() {
    // Arrange, Act and Assert
    assertTrue((new CaseInsensitiveMultiMap()).getAllHeaders().isEmpty());
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#containsHeader(String)}
   */
  @Test
  public void testContainsHeader() {
    // Arrange, Act and Assert
    assertFalse((new CaseInsensitiveMultiMap()).containsHeader("https://example.org/example"));
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#containsHeader(String)}
   */
  @Test
  public void testContainsHeader2() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act and Assert
    assertTrue(caseInsensitiveMultiMap.containsHeader("https://example.org/example"));
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#addHeader(String, String)}
   */
  @Test
  public void testAddHeader() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

    // Act
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Assert
    assertEquals(1, caseInsensitiveMultiMap.getAllHeaders().size());
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#addHeader(String, String)}
   */
  @Test
  public void testAddHeader2() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Assert
    assertEquals(1, caseInsensitiveMultiMap.getAllHeaders().size());
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#addHeader(String, String)}
   */
  @Test
  public void testAddHeader3() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "42");

    // Act
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Assert
    assertEquals(2, caseInsensitiveMultiMap.getAllHeaders().size());
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  public void testAsMap() {
    // Arrange, Act and Assert
    assertTrue((new CaseInsensitiveMultiMap()).asMap().isEmpty());
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  public void testAsMap2() {
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
   * Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  public void testAsMap3() {
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
    Collection<String> getResult2 = actualAsMapResult.get("https://example.org/example");
    assertEquals(1, getResult2.size());
    assertTrue(getResult2 instanceof List);
    assertEquals("42", ((List<String>) getResult).get(0));
    assertEquals("https://example.org/example", ((List<String>) getResult2).get(0));
  }

  /**
   * Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  public void testAsMap4() {
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
   * Method under test: default or parameterless constructor of
   * {@link CaseInsensitiveMultiMap}
   */
  @Test
  public void testNewCaseInsensitiveMultiMap() {
    // Arrange and Act
    CaseInsensitiveMultiMap actualCaseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

    // Assert
    assertTrue(actualCaseInsensitiveMultiMap.map instanceof ArrayListMultimap);
    assertTrue(actualCaseInsensitiveMultiMap.getAllHeaders().isEmpty());
  }
}
