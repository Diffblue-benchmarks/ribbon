package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class ArchaiusPropertyResolverDiffblueTest {
  /**
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  public void testGet() {
    // Arrange
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  public void testGet2() {
    // Arrange
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  public void testGet3() {
    // Arrange
    Class<Double> type = Double.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  public void testGet4() {
    // Arrange
    Class<Float> type = Float.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  public void testGet5() {
    // Arrange
    Class<Integer> type = Integer.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Method under test: {@link ArchaiusPropertyResolver#get(String, Class)}
   */
  @Test
  public void testGet6() {
    // Arrange
    Class<Long> type = Long.class;

    // Act and Assert
    assertFalse(ArchaiusPropertyResolver.INSTANCE.get("Key", type).isPresent());
  }

  /**
   * Method under test: {@link ArchaiusPropertyResolver#getActionCount()}
   */
  @Test
  public void testGetActionCount() {
    // Arrange, Act and Assert
    assertEquals(0, ArchaiusPropertyResolver.INSTANCE.getActionCount());
  }
}
