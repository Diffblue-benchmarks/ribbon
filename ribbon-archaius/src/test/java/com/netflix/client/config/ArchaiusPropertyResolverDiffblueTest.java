package com.netflix.client.config;

import static org.junit.Assert.assertFalse;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ArchaiusPropertyResolverDiffblueTest {
  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   * <ul>
   *   <li>When {@code Boolean}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangBoolean_thenReturnNotPresent() {
    // Arrange
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   * <ul>
   *   <li>When {@code Double}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangDouble_thenReturnNotPresent() {
    // Arrange
    Class<Double> type = Double.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   * <ul>
   *   <li>When {@code Float}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangFloat_thenReturnNotPresent() {
    // Arrange
    Class<Float> type = Float.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   * <ul>
   *   <li>When {@code Integer}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangInteger_thenReturnNotPresent() {
    // Arrange
    Class<Integer> type = Integer.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   * <ul>
   *   <li>When {@code Long}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangLong_thenReturnNotPresent() {
    // Arrange
    Class<Long> type = Long.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Test {@link ArchaiusPropertyResolver#get(String, Class)}.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional ArchaiusPropertyResolver.get(String, Class)"})
  public void testGet_whenJavaLangObject_thenReturnNotPresent() {
    // Arrange
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }
}
