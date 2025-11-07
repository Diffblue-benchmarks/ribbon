package com.netflix.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UtilsDiffblueTest {
  /**
   * Test {@link Utils#isPresentAsCause(Throwable, Collection)}.
   * <ul>
   *   <li>Given {@code Throwable}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Utils#isPresentAsCause(Throwable, Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Utils.isPresentAsCause(Throwable, Collection)"})
  public void testIsPresentAsCause_givenJavaLangThrowable_whenNull_thenReturnFalse() {
    // Arrange
    LinkedHashSet<Class<? extends Throwable>> throwableToSearchFor = new LinkedHashSet<>();
    Class<Throwable> forNameResult = Throwable.class;
    throwableToSearchFor.add(forNameResult);

    // Act and Assert
    assertFalse(Utils.isPresentAsCause(null, throwableToSearchFor));
  }

  /**
   * Test {@link Utils#isPresentAsCause(Throwable, Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Utils#isPresentAsCause(Throwable, Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Utils.isPresentAsCause(Throwable, Collection)"})
  public void testIsPresentAsCause_whenArrayList_thenReturnFalse() {
    // Arrange
    Throwable throwableToSearchIn = new Throwable();

    // Act and Assert
    assertFalse(Utils.isPresentAsCause(throwableToSearchIn, new ArrayList<>()));
  }

  /**
   * Test {@link Utils#isPresentAsCause(Throwable, Collection)}.
   * <ul>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@link Throwable}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Utils#isPresentAsCause(Throwable, Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Utils.isPresentAsCause(Throwable, Collection)"})
  public void testIsPresentAsCause_whenLinkedHashSetAddThrowable_thenReturnTrue() {
    // Arrange
    Throwable throwableToSearchIn = new Throwable();

    LinkedHashSet<Class<? extends Throwable>> throwableToSearchFor = new LinkedHashSet<>();
    Class<Throwable> forNameResult = Throwable.class;
    throwableToSearchFor.add(forNameResult);

    // Act and Assert
    assertTrue(Utils.isPresentAsCause(throwableToSearchIn, throwableToSearchFor));
  }
}
