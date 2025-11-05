package com.netflix.loadbalancer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PingConstantDiffblueTest {
  /**
   * Method under test: {@link PingConstant#setConstant(String)}
   */
  @Test
  public void testSetConstant() {
    // Arrange
    PingConstant pingConstant = new PingConstant();

    // Act
    pingConstant.setConstant("Constant Str");

    // Assert
    assertFalse(pingConstant.getConstant());
  }

  /**
   * Method under test: {@link PingConstant#setConstant(String)}
   */
  @Test
  public void testSetConstant2() {
    // Arrange
    PingConstant pingConstant = new PingConstant();
    pingConstant.setConstant(true);

    // Act
    pingConstant.setConstant(null);

    // Assert
    assertFalse(pingConstant.getConstant());
  }

  /**
   * Method under test: {@link PingConstant#setConstant(String)}
   */
  @Test
  public void testSetConstant3() {
    // Arrange
    PingConstant pingConstant = new PingConstant();

    // Act
    pingConstant.setConstant(Boolean.TRUE.toString());

    // Assert
    assertTrue(pingConstant.getConstant());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link PingConstant}
   *   <li>{@link PingConstant#setConstant(boolean)}
   *   <li>{@link PingConstant#getConstant()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    PingConstant actualPingConstant = new PingConstant();
    actualPingConstant.setConstant(true);

    // Assert that nothing has changed
    assertTrue(actualPingConstant.getConstant());
  }

  /**
   * Method under test: {@link PingConstant#isAlive(Server)}
   */
  @Test
  public void testIsAlive() {
    // Arrange
    PingConstant pingConstant = new PingConstant();

    // Act and Assert
    assertTrue(pingConstant.isAlive(new Server("42")));
  }

  /**
   * Method under test: {@link PingConstant#isAlive(Server)}
   */
  @Test
  public void testIsAlive2() {
    // Arrange
    PingConstant pingConstant = new PingConstant();
    pingConstant.setConstant("Constant Str");

    // Act and Assert
    assertFalse(pingConstant.isAlive(new Server("42")));
  }
}
