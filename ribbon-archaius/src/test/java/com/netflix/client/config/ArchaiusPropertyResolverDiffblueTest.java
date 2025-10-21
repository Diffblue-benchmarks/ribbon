package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ArchaiusPropertyResolverDiffblueTest {
  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Boolean}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangBoolean_thenReturnNotPresent() {
    // Arrange
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertFalse(
        ArchaiusPropertyResolver.INSTANCE.get("\"ribbon.MaxAutoRetries\"", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Double}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangDouble_thenReturnNotPresent() {
    // Arrange
    Class<Double> type = Double.class;

    // Act and Assert
    assertFalse(
        ArchaiusPropertyResolver.INSTANCE.get("\"ribbon.MaxAutoRetries\"", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Float}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangFloat_thenReturnNotPresent() {
    // Arrange
    Class<Float> type = Float.class;

    // Act and Assert
    assertFalse(
        ArchaiusPropertyResolver.INSTANCE.get("\"ribbon.MaxAutoRetries\"", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Integer}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangInteger_thenReturnNotPresent() {
    // Arrange
    Class<Integer> type = Integer.class;

    // Act and Assert
    assertFalse(
        ArchaiusPropertyResolver.INSTANCE.get("\"ribbon.MaxAutoRetries\"", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Long}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangLong_thenReturnNotPresent() {
    // Arrange
    Class<Long> type = Long.class;

    // Act and Assert
    assertFalse(
        ArchaiusPropertyResolver.INSTANCE.get("\"ribbon.MaxAutoRetries\"", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangObject_thenReturnNotPresent() {
    // Arrange
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(
        ArchaiusPropertyResolver.INSTANCE.get("\"ribbon.MaxAutoRetries\"", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#getActionCount()}.
   *
   * <p>Method under test: {@link ArchaiusPropertyResolver#getActionCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ArchaiusPropertyResolver.getActionCount()"})
  public void testGetActionCount() {
    // Arrange, Act and Assert
    assertEquals(0, ArchaiusPropertyResolver.INSTANCE.getActionCount());
  }
}
