package com.netflix.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.junit.Test;

public class UtilsDiffblueTest {
  /**
   * Method under test: {@link Utils#isPresentAsCause(Throwable, Collection)}
   */
  @Test
  public void testIsPresentAsCause() {
    // Arrange
    Throwable throwableToSearchIn = new Throwable();

    // Act and Assert
    assertFalse(Utils.isPresentAsCause(throwableToSearchIn, new ArrayList<>()));
  }

  /**
   * Method under test: {@link Utils#isPresentAsCause(Throwable, Collection)}
   */
  @Test
  public void testIsPresentAsCause2() {
    // Arrange
    LinkedHashSet<Class<? extends Throwable>> throwableToSearchFor = new LinkedHashSet<>();
    Class<Throwable> forNameResult = Throwable.class;
    throwableToSearchFor.add(forNameResult);

    // Act and Assert
    assertFalse(Utils.isPresentAsCause(null, throwableToSearchFor));
  }

  /**
   * Method under test: {@link Utils#isPresentAsCause(Throwable, Collection)}
   */
  @Test
  public void testIsPresentAsCause3() {
    // Arrange
    Throwable throwableToSearchIn = new Throwable();

    LinkedHashSet<Class<? extends Throwable>> throwableToSearchFor = new LinkedHashSet<>();
    Class<Throwable> forNameResult = Throwable.class;
    throwableToSearchFor.add(forNameResult);

    // Act and Assert
    assertTrue(Utils.isPresentAsCause(throwableToSearchIn, throwableToSearchFor));
  }
}
