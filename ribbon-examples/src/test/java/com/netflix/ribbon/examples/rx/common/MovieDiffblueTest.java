package com.netflix.ribbon.examples.rx.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.DuplicatedByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class MovieDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test getters and setters.
   * <p>
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Movie.<init>(String, String, String, String, String)", "String Movie.getAgeGroup()",
      "String Movie.getCategory()", "String Movie.getContentURI()", "String Movie.getId()", "String Movie.getName()",
      "String Movie.toString()"})
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

  /**
   * Test {@link Movie#equals(Object)}, and {@link Movie#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
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
   * Test {@link Movie#equals(Object)}, and {@link Movie#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Movie movie = Movie.BREAKING_BAD;

    // Act and Assert
    assertEquals(movie, movie);
    int expectedHashCodeResult = movie.hashCode();
    assertEquals(expectedHashCodeResult, movie.hashCode());
  }

  /**
   * Test {@link Movie#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Movie.HOUSE_OF_CARDS, Movie.BREAKING_BAD);
  }

  /**
   * Test {@link Movie#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(new Movie("42", "Name", "Category", "Age Group", "Not all who wander are lost"),
        Movie.BREAKING_BAD);
  }

  /**
   * Test {@link Movie#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange, Act and Assert
    assertNotEquals(new Movie("42", "Name", "Category", null, "Not all who wander are lost"), Movie.BREAKING_BAD);
  }

  /**
   * Test {@link Movie#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Movie.BREAKING_BAD, null);
  }

  /**
   * Test {@link Movie#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Movie.BREAKING_BAD, "Different type to Movie");
  }

  /**
   * Test {@link Movie#from(ByteBuf)} with {@code byteBuf}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Movie#from(ByteBuf)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Movie Movie.from(ByteBuf)"})
  public void testFromWithByteBuf_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Movie.from(new DuplicatedByteBuf(new EmptyByteBuf(new PooledByteBufAllocator())));
  }

  /**
   * Test {@link Movie#from(String)} with {@code formatted}.
   * <ul>
   *   <li>Then return AgeGroup is {@code U}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Movie#from(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Movie Movie.from(String)"})
  public void testFromWithFormatted_thenReturnAgeGroupIsU() {
    // Arrange and Act
    Movie actualFromResult = Movie.from("{id='U', name='U', category='U', ageGroup='U', contentURI='U'}");

    // Assert
    assertEquals("U", actualFromResult.getAgeGroup());
    assertEquals("U", actualFromResult.getCategory());
    assertEquals("U", actualFromResult.getContentURI());
    assertEquals("U", actualFromResult.getId());
    assertEquals("U", actualFromResult.getName());
  }

  /**
   * Test {@link Movie#from(String)} with {@code formatted}.
   * <ul>
   *   <li>When {@code Formatted}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Movie#from(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Movie Movie.from(String)"})
  public void testFromWithFormatted_whenFormatted_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Movie.from("Formatted");
  }
}
