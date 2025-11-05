package com.netflix.ribbon.examples.rx.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RecommendationsDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test: {@link Recommendations#from(String)}
   */
  @Test
  public void testFrom() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Recommendations.from("Formatted");
  }

  /**
   * Method under test: {@link Recommendations#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Recommendations(new ArrayList<>()), "42");
  }

  /**
   * Method under test: {@link Recommendations#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<Movie> movies = new ArrayList<>();
    movies.add(mock(Movie.class));

    // Act and Assert
    assertNotEquals(new Recommendations(movies), "42");
  }

  /**
   * Method under test: {@link Recommendations#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ArrayList<Movie> movies = new ArrayList<>();
    movies.add(mock(Movie.class));

    // Act and Assert
    assertNotEquals(new Recommendations(movies), null);
  }

  /**
   * Method under test: {@link Recommendations#Recommendations(List)}
   */
  @Test
  public void testNewRecommendations() {
    // Arrange, Act and Assert
    assertTrue((new Recommendations(new ArrayList<>())).getMovies().isEmpty());
  }

  /**
   * Method under test: {@link Recommendations#Recommendations(List)}
   */
  @Test
  public void testNewRecommendations2() {
    // Arrange
    ArrayList<Movie> movies = new ArrayList<>();
    movies.add(Movie.BREAKING_BAD);

    // Act and Assert
    assertEquals(movies, (new Recommendations(movies)).getMovies());
  }

  /**
   * Method under test: {@link Recommendations#Recommendations(List)}
   */
  @Test
  public void testNewRecommendations3() {
    // Arrange
    ArrayList<Movie> movies = new ArrayList<>();
    movies.add(Movie.BREAKING_BAD);
    movies.add(Movie.BREAKING_BAD);

    // Act and Assert
    assertEquals(movies, (new Recommendations(movies)).getMovies());
  }
}
