package com.netflix.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CaseInsensitiveMultiMapDiffblueTest {
  /**
   * Test {@link CaseInsensitiveMultiMap#getFirstValue(String)}.
   *
   * <ul>
   *   <li>Given {@link CaseInsensitiveMultiMap} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#getFirstValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CaseInsensitiveMultiMap.getFirstValue(String)"})
  public void testGetFirstValue_givenCaseInsensitiveMultiMap_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new CaseInsensitiveMultiMap().getFirstValue("https://example.org/example"));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#getFirstValue(String)}.
   *
   * <ul>
   *   <li>Then return {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#getFirstValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CaseInsensitiveMultiMap.getFirstValue(String)"})
  public void testGetFirstValue_thenReturnHttpsExampleOrgExample() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act and Assert
    assertEquals(
        "https://example.org/example",
        caseInsensitiveMultiMap.getFirstValue("https://example.org/example"));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#getAllValues(String)}.
   *
   * <ul>
   *   <li>Given {@link CaseInsensitiveMultiMap} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#getAllValues(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List CaseInsensitiveMultiMap.getAllValues(String)"})
  public void testGetAllValues_givenCaseInsensitiveMultiMap_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new CaseInsensitiveMultiMap().getAllValues("https://example.org/example").isEmpty());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#getAllValues(String)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#getAllValues(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List CaseInsensitiveMultiMap.getAllValues(String)"})
  public void testGetAllValues_thenReturnSizeIsOne() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act
    List<String> actualAllValues =
        caseInsensitiveMultiMap.getAllValues("https://example.org/example");

    // Assert
    assertEquals(1, actualAllValues.size());
    assertEquals("https://example.org/example", actualAllValues.get(0));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#getAllHeaders()}.
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#getAllHeaders()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List CaseInsensitiveMultiMap.getAllHeaders()"})
  public void testGetAllHeaders() {
    // Arrange, Act and Assert
    assertTrue(new CaseInsensitiveMultiMap().getAllHeaders().isEmpty());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#containsHeader(String)}.
   *
   * <ul>
   *   <li>Given {@link CaseInsensitiveMultiMap} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#containsHeader(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean CaseInsensitiveMultiMap.containsHeader(String)"})
  public void testContainsHeader_givenCaseInsensitiveMultiMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new CaseInsensitiveMultiMap().containsHeader("https://example.org/example"));
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#containsHeader(String)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#containsHeader(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#addHeader(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CaseInsensitiveMultiMap.addHeader(String, String)"})
  public void testAddHeader() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Act
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Assert that nothing has changed
    Multimap<String, Entry<String, String>> multimap = caseInsensitiveMultiMap.map;
    assertTrue(multimap instanceof ArrayListMultimap);
    assertEquals(1, multimap.size());
    assertEquals(1, multimap.entries().size());
    assertEquals(1, caseInsensitiveMultiMap.getAllHeaders().size());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#addHeader(String, String)}.
   *
   * <ul>
   *   <li>Then {@link CaseInsensitiveMultiMap} (default constructor) {@link
   *       CaseInsensitiveMultiMap#map} size is one.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#addHeader(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CaseInsensitiveMultiMap.addHeader(String, String)"})
  public void testAddHeader_thenCaseInsensitiveMultiMapMapSizeIsOne() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

    // Act
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Assert
    Multimap<String, Entry<String, String>> multimap = caseInsensitiveMultiMap.map;
    assertTrue(multimap instanceof ArrayListMultimap);
    assertEquals(1, multimap.size());
    assertEquals(1, multimap.entries().size());
    assertEquals(1, caseInsensitiveMultiMap.getAllHeaders().size());
    assertFalse(multimap.isEmpty());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#addHeader(String, String)}.
   *
   * <ul>
   *   <li>Then {@link CaseInsensitiveMultiMap} (default constructor) {@link
   *       CaseInsensitiveMultiMap#map} size is two.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#addHeader(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CaseInsensitiveMultiMap.addHeader(String, String)"})
  public void testAddHeader_thenCaseInsensitiveMultiMapMapSizeIsTwo() {
    // Arrange
    CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "42");

    // Act
    caseInsensitiveMultiMap.addHeader("https://example.org/example", "https://example.org/example");

    // Assert
    Multimap<String, Entry<String, String>> multimap = caseInsensitiveMultiMap.map;
    assertTrue(multimap instanceof ArrayListMultimap);
    assertEquals(2, multimap.size());
    assertEquals(2, multimap.entries().size());
    assertEquals(2, caseInsensitiveMultiMap.getAllHeaders().size());
    assertFalse(multimap.isEmpty());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#asMap()}.
   *
   * <ul>
   *   <li>Given {@link CaseInsensitiveMultiMap} (default constructor) addHeader {@code Name} and
   *       {@code 42}.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Given {@link CaseInsensitiveMultiMap} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map CaseInsensitiveMultiMap.asMap()"})
  public void testAsMap_givenCaseInsensitiveMultiMap_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new CaseInsensitiveMultiMap().asMap().isEmpty());
  }

  /**
   * Test {@link CaseInsensitiveMultiMap#asMap()}.
   *
   * <ul>
   *   <li>Then return {@code https://example.org/example} size is one.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Then return {@code https://example.org/example} size is two.
   * </ul>
   *
   * <p>Method under test: {@link CaseInsensitiveMultiMap#asMap()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <p>Method under test: default or parameterless constructor of {@link CaseInsensitiveMultiMap}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CaseInsensitiveMultiMap.<init>()"})
  public void testNewCaseInsensitiveMultiMap() {
    // Arrange and Act
    CaseInsensitiveMultiMap actualCaseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

    // Assert
    Multimap<String, Entry<String, String>> multimap = actualCaseInsensitiveMultiMap.map;
    assertTrue(multimap instanceof ArrayListMultimap);
    assertEquals(0, multimap.size());
    assertTrue(multimap.isEmpty());
    assertTrue(multimap.entries().isEmpty());
    assertTrue(actualCaseInsensitiveMultiMap.getAllHeaders().isEmpty());
  }
}
