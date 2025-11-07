package com.netflix.loadbalancer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PingConstantDiffblueTest {
  /**
   * Test {@link PingConstant#setConstant(String)} with {@code constantStr}.
   * <ul>
   *   <li>Given {@link PingConstant} (default constructor) Constant is {@code true}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PingConstant#setConstant(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingConstant.setConstant(String)"})
  public void testSetConstantWithConstantStr_givenPingConstantConstantIsTrue_whenNull() {
    // Arrange
    PingConstant pingConstant = new PingConstant();
    pingConstant.setConstant(true);

    // Act
    pingConstant.setConstant(null);

    // Assert
    assertFalse(pingConstant.getConstant());
  }

  /**
   * Test {@link PingConstant#setConstant(String)} with {@code constantStr}.
   * <ul>
   *   <li>When {@code Constant Str}.</li>
   *   <li>Then not {@link PingConstant} (default constructor) Constant.</li>
   * </ul>
   * <p>
   * Method under test: {@link PingConstant#setConstant(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingConstant.setConstant(String)"})
  public void testSetConstantWithConstantStr_whenConstantStr_thenNotPingConstantConstant() {
    // Arrange
    PingConstant pingConstant = new PingConstant();

    // Act
    pingConstant.setConstant("Constant Str");

    // Assert
    assertFalse(pingConstant.getConstant());
  }

  /**
   * Test {@link PingConstant#setConstant(String)} with {@code constantStr}.
   * <ul>
   *   <li>When {@link Boolean#TRUE} toString.</li>
   *   <li>Then {@link PingConstant} (default constructor) Constant.</li>
   * </ul>
   * <p>
   * Method under test: {@link PingConstant#setConstant(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingConstant.setConstant(String)"})
  public void testSetConstantWithConstantStr_whenTrueToString_thenPingConstantConstant() {
    // Arrange
    PingConstant pingConstant = new PingConstant();

    // Act
    pingConstant.setConstant(Boolean.TRUE.toString());

    // Assert that nothing has changed
    assertTrue(pingConstant.getConstant());
  }

  /**
   * Test {@link PingConstant#isAlive(Server)}.
   * <ul>
   *   <li>Given {@link PingConstant} (default constructor) Constant is {@code Constant Str}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PingConstant#isAlive(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean PingConstant.isAlive(Server)"})
  public void testIsAlive_givenPingConstantConstantIsConstantStr_thenReturnFalse() {
    // Arrange
    PingConstant pingConstant = new PingConstant();
    pingConstant.setConstant("Constant Str");

    // Act and Assert
    assertFalse(pingConstant.isAlive(new Server("42")));
  }

  /**
   * Test {@link PingConstant#isAlive(Server)}.
   * <ul>
   *   <li>Given {@link PingConstant} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PingConstant#isAlive(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean PingConstant.isAlive(Server)"})
  public void testIsAlive_givenPingConstant_thenReturnTrue() {
    // Arrange
    PingConstant pingConstant = new PingConstant();

    // Act and Assert
    assertTrue(pingConstant.isAlive(new Server("42")));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link PingConstant}
   *   <li>{@link PingConstant#setConstant(boolean)}
   *   <li>{@link PingConstant#getConstant()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void PingConstant.<init>()", "boolean PingConstant.getConstant()",
      "void PingConstant.setConstant(boolean)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    PingConstant actualPingConstant = new PingConstant();
    actualPingConstant.setConstant(true);

    // Assert
    assertTrue(actualPingConstant.getConstant());
  }
}
