package com.netflix.ribbon.examples.rx.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Movie.<init>(String, String, String, String, String)",
    "String Movie.getAgeGroup()",
    "String Movie.getCategory()",
    "String Movie.getContentURI()",
    "String Movie.getId()",
    "String Movie.getName()",
    "String Movie.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Movie actualMovie =
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");
    String actualToStringResult = actualMovie.toString();
    String actualAgeGroup = actualMovie.getAgeGroup();
    String actualCategory = actualMovie.getCategory();
    String actualContentURI = actualMovie.getContentURI();
    String actualId = actualMovie.getId();

    // Assert
    assertEquals("\"Action\"", actualCategory);
    assertEquals("\"Adults\"", actualAgeGroup);
    assertEquals("\"The Shawshank Redemption\"", actualMovie.getName());
    assertEquals("\"https://netflix.com/movie/12345\"", actualContentURI);
    assertEquals("\"tt0111161\"", actualId);
    assertEquals(
        "{id='\"tt0111161\"', name='\"The Shawshank Redemption\"', category='\"Action\"', ageGroup='\"Adults\"',"
            + " contentURI='\"https://netflix.com/movie/12345\"'}",
        actualToStringResult);
  }

  /**
   * Test {@link Movie#equals(Object)}, and {@link Movie#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Movie movie = Movie.BREAKING_BAD;
    Movie movie2 = Movie.BREAKING_BAD;

    // Act and Assert
    assertEquals(movie, movie2);
    assertEquals(movie.hashCode(), movie2.hashCode());
  }

  /**
   * Test {@link Movie#equals(Object)}, and {@link Movie#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Movie movie =
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");
    Movie movie2 =
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");

    // Act and Assert
    assertEquals(movie, movie2);
    assertEquals(movie.hashCode(), movie2.hashCode());
  }

  /**
   * Test {@link Movie#equals(Object)}, and {@link Movie#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Movie movie =
        new Movie(
            null,
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");
    Movie movie2 =
        new Movie(
            null,
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");

    // Act and Assert
    assertEquals(movie, movie2);
    assertEquals(movie.hashCode(), movie2.hashCode());
  }

  /**
   * Test {@link Movie#equals(Object)}, and {@link Movie#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    Movie movie =
        new Movie(
            "\"tt0111161\"",
            null,
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");
    Movie movie2 =
        new Movie(
            "\"tt0111161\"",
            null,
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");

    // Act and Assert
    assertEquals(movie, movie2);
    assertEquals(movie.hashCode(), movie2.hashCode());
  }

  /**
   * Test {@link Movie#equals(Object)}, and {@link Movie#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    Movie movie =
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            null,
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");
    Movie movie2 =
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            null,
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");

    // Act and Assert
    assertEquals(movie, movie2);
    assertEquals(movie.hashCode(), movie2.hashCode());
  }

  /**
   * Test {@link Movie#equals(Object)}, and {@link Movie#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual6() {
    // Arrange
    Movie movie =
        new Movie(
            "\"tt0111161\"", "\"The Shawshank Redemption\"", "\"Action\"", "\"Adults\"", null);
    Movie movie2 =
        new Movie(
            "\"tt0111161\"", "\"The Shawshank Redemption\"", "\"Action\"", "\"Adults\"", null);

    // Act and Assert
    assertEquals(movie, movie2);
    assertEquals(movie.hashCode(), movie2.hashCode());
  }

  /**
   * Test {@link Movie#equals(Object)}, and {@link Movie#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Movie#equals(Object)}
   *   <li>{@link Movie#hashCode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Movie.HOUSE_OF_CARDS, Movie.BREAKING_BAD);
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\""),
        Movie.BREAKING_BAD);
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange, Act and Assert
    assertNotEquals(
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            null,
            "\"https://netflix.com/movie/12345\""),
        Movie.BREAKING_BAD);
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Movie movie =
        new Movie(
            "42",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");

    // Act and Assert
    assertNotEquals(
        movie,
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\""));
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Movie movie =
        new Movie(
            null,
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");

    // Act and Assert
    assertNotEquals(
        movie,
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\""));
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Movie movie =
        new Movie(
            "\"tt0111161\"",
            "\"Adults\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");

    // Act and Assert
    assertNotEquals(
        movie,
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\""));
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    Movie movie =
        new Movie(
            "\"tt0111161\"",
            null,
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");

    // Act and Assert
    assertNotEquals(
        movie,
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\""));
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    Movie movie =
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            null,
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\"");

    // Act and Assert
    assertNotEquals(
        movie,
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\""));
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    Movie movie =
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "Not all who wander are lost");

    // Act and Assert
    assertNotEquals(
        movie,
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\""));
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    Movie movie =
        new Movie(
            "\"tt0111161\"", "\"The Shawshank Redemption\"", "\"Action\"", "\"Adults\"", null);

    // Act and Assert
    assertNotEquals(
        movie,
        new Movie(
            "\"tt0111161\"",
            "\"The Shawshank Redemption\"",
            "\"Action\"",
            "\"Adults\"",
            "\"https://netflix.com/movie/12345\""));
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Movie.BREAKING_BAD, null);
  }

  /**
   * Test {@link Movie#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Movie#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Movie.equals(Object)", "int Movie.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Movie.BREAKING_BAD, "Different type to Movie");
  }

  /**
   * Test {@link Movie#from(ByteBuf)} with {@code byteBuf}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link Movie#from(ByteBuf)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Movie Movie.from(ByteBuf)"})
  public void testFromWithByteBuf_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Movie.from(new DuplicatedByteBuf(new EmptyByteBuf(new PooledByteBufAllocator())));
  }

  /**
   * Test {@link Movie#from(String)} with {@code formatted}.
   *
   * <ul>
   *   <li>Then return AgeGroup is {@code UU}.
   * </ul>
   *
   * <p>Method under test: {@link Movie#from(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Movie Movie.from(String)"})
  public void testFromWithFormatted_thenReturnAgeGroupIsUu() {
    // Arrange and Act
    Movie actualFromResult =
        Movie.from("{id='UU', name='UU', category='UU', ageGroup='UU', contentURI='UU'}");

    // Assert
    assertEquals("UU", actualFromResult.getAgeGroup());
    assertEquals("UU", actualFromResult.getCategory());
    assertEquals("UU", actualFromResult.getContentURI());
    assertEquals("UU", actualFromResult.getId());
    assertEquals("UU", actualFromResult.getName());
  }

  /**
   * Test {@link Movie#from(String)} with {@code formatted}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link Movie#from(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Movie Movie.from(String)"})
  public void testFromWithFormatted_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Movie.from("\"Titanic,1997,James Cameron,Leonardo DiCaprio,Kate Winslet,Drama,Romance,194\"");
  }
}
