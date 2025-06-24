package com.netflix.ribbon.examples.rx.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class RecommendationsDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link Recommendations#Recommendations(List)}.
   * <ul>
   *   <li>Given {@link Movie#BREAKING_BAD}.</li>
   *   <li>Then return Movies is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Recommendations#Recommendations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Recommendations.<init>(List)"})
  public void testNewRecommendations_givenBreaking_bad_thenReturnMoviesIsArrayList() {
    // Arrange
    ArrayList<Movie> movies = new ArrayList<>();
    movies.add(Movie.BREAKING_BAD);

    // Act and Assert
    assertEquals(movies, (new Recommendations(movies)).getMovies());
  }

  /**
   * Test {@link Recommendations#Recommendations(List)}.
   * <ul>
   *   <li>Given {@link Movie#BREAKING_BAD}.</li>
   *   <li>Then return Movies is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Recommendations#Recommendations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Recommendations.<init>(List)"})
  public void testNewRecommendations_givenBreaking_bad_thenReturnMoviesIsArrayList2() {
    // Arrange
    ArrayList<Movie> movies = new ArrayList<>();
    movies.add(Movie.BREAKING_BAD);
    movies.add(Movie.BREAKING_BAD);

    // Act and Assert
    assertEquals(movies, (new Recommendations(movies)).getMovies());
  }

  /**
   * Test {@link Recommendations#Recommendations(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Movies Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Recommendations#Recommendations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Recommendations.<init>(List)"})
  public void testNewRecommendations_whenArrayList_thenReturnMoviesEmpty() {
    // Arrange, Act and Assert
    assertTrue((new Recommendations(new ArrayList<>())).getMovies().isEmpty());
  }

  /**
   * Test {@link Recommendations#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Recommendations#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Recommendations.equals(Object)", "int Recommendations.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Recommendations(new ArrayList<>()), "42");
  }

  /**
   * Test {@link Recommendations#from(String)}.
   * <ul>
   *   <li>When {@code Formatted}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Recommendations#from(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Recommendations Recommendations.from(String)"})
  public void testFrom_whenFormatted_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Recommendations.from("Formatted");
  }
}
