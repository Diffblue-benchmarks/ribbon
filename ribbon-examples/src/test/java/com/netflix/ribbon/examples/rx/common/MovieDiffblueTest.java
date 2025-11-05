package com.netflix.ribbon.examples.rx.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.DuplicatedByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MovieDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Movie movie = Movie.BREAKING_BAD;
    Movie movie2 = Movie.BREAKING_BAD;

    // Act and Assert
    assertEquals(movie, movie2);
    int expectedHashCodeResult = movie.hashCode();
    assertEquals(expectedHashCodeResult, movie2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Movie movie = Movie.BREAKING_BAD;

    // Act and Assert
    assertEquals(movie, movie);
    int expectedHashCodeResult = movie.hashCode();
    assertEquals(expectedHashCodeResult, movie.hashCode());
  }

  /**
   * Method under test: {@link Movie#from(ByteBuf)}
   */
  @Test
  public void testFrom() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Movie.from(new DuplicatedByteBuf(new EmptyByteBuf(new PooledByteBufAllocator())));
  }

  /**
   * Method under test: {@link Movie#from(String)}
   */
  @Test
  public void testFrom2() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Movie.from("Formatted");
  }

  /**
   * Method under test: {@link Movie#from(String)}
   */
  @Test
  public void testFrom3() {
    // Arrange and Act
    Movie actualFromResult = Movie.from("{id='UU', name='UU', category='UU', ageGroup='UU', contentURI='UU'}");

    // Assert
    assertEquals("UU", actualFromResult.getAgeGroup());
    assertEquals("UU", actualFromResult.getCategory());
    assertEquals("UU", actualFromResult.getContentURI());
    assertEquals("UU", actualFromResult.getId());
    assertEquals("UU", actualFromResult.getName());
  }

  /**
   * Method under test: {@link Movie#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Movie.HOUSE_OF_CARDS, Movie.BREAKING_BAD);
    assertNotEquals(new Movie("42", "Name", "Category", "Age Group", "Not all who wander are lost"),
        Movie.BREAKING_BAD);
    assertNotEquals(new Movie("42", "Name", "Category", null, "Not all who wander are lost"), Movie.BREAKING_BAD);
  }

  /**
   * Method under test: {@link Movie#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Movie.BREAKING_BAD, null);
  }

  /**
   * Method under test: {@link Movie#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Movie.BREAKING_BAD, "Different type to Movie");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Movie#Movie(String, String, String, String, String)}
   *   <li>{@link Movie#toString()}
   *   <li>{@link Movie#getAgeGroup()}
   *   <li>{@link Movie#getCategory()}
   *   <li>{@link Movie#getContentURI()}
   *   <li>{@link Movie#getId()}
   *   <li>{@link Movie#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Movie actualMovie = new Movie("42", "Name", "Category", "Age Group", "Not all who wander are lost");
    String actualToStringResult = actualMovie.toString();
    String actualAgeGroup = actualMovie.getAgeGroup();
    String actualCategory = actualMovie.getCategory();
    String actualContentURI = actualMovie.getContentURI();
    String actualId = actualMovie.getId();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Age Group", actualAgeGroup);
    assertEquals("Category", actualCategory);
    assertEquals("Name", actualMovie.getName());
    assertEquals("Not all who wander are lost", actualContentURI);
    assertEquals("{id='42', name='Name', category='Category', ageGroup='Age Group', contentURI='Not all who wander"
        + " are lost'}", actualToStringResult);
  }
}
