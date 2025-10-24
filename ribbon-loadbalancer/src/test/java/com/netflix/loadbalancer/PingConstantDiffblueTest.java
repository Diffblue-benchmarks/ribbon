package com.netflix.loadbalancer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PingConstantDiffblueTest {
  /**
   * Test {@link PingConstant#setConstant(String)} with {@code constantStr}.
   *
   * <ul>
   *   <li>Given {@link PingConstant} (default constructor) Constant is {@code true}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link PingConstant#setConstant(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When {@code "ServerStatus:Alive"}.
   *   <li>Then not {@link PingConstant} (default constructor) Constant.
   * </ul>
   *
   * <p>Method under test: {@link PingConstant#setConstant(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PingConstant.setConstant(String)"})
  public void testSetConstantWithConstantStr_whenServerStatusAlive_thenNotPingConstantConstant() {
    // Arrange
    PingConstant pingConstant = new PingConstant();

    // Act
    pingConstant.setConstant("\"ServerStatus:Alive\"");

    // Assert
    assertFalse(pingConstant.getConstant());
  }

  /**
   * Test {@link PingConstant#setConstant(String)} with {@code constantStr}.
   *
   * <ul>
   *   <li>When {@link Boolean#TRUE} toString.
   *   <li>Then {@link PingConstant} (default constructor) Constant.
   * </ul>
   *
   * <p>Method under test: {@link PingConstant#setConstant(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Given {@link PingConstant} (default constructor) Constant is {@code
   *       "ServerStatus:Alive"}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link PingConstant#isAlive(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean PingConstant.isAlive(Server)"})
  public void testIsAlive_givenPingConstantConstantIsServerStatusAlive_thenReturnFalse() {
    // Arrange
    PingConstant pingConstant = new PingConstant();
    pingConstant.setConstant("\"ServerStatus:Alive\"");

    // Act
    boolean actualIsAliveResult = pingConstant.isAlive(new Server("42"));

    // Assert
    assertFalse(actualIsAliveResult);
  }

  /**
   * Test {@link PingConstant#isAlive(Server)}.
   *
   * <ul>
   *   <li>Given {@link PingConstant} (default constructor).
   *   <li>When {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link PingConstant#isAlive(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean PingConstant.isAlive(Server)"})
  public void testIsAlive_givenPingConstant_whenServerWithIdIs42_thenReturnTrue() {
    // Arrange
    PingConstant pingConstant = new PingConstant();

    // Act
    boolean actualIsAliveResult = pingConstant.isAlive(new Server("42"));

    // Assert
    assertTrue(actualIsAliveResult);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link PingConstant}
   *   <li>{@link PingConstant#setConstant(boolean)}
   *   <li>{@link PingConstant#getConstant()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PingConstant.<init>()",
    "boolean PingConstant.getConstant()",
    "void PingConstant.setConstant(boolean)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    PingConstant actualPingConstant = new PingConstant();
    actualPingConstant.setConstant(true);

    // Assert
    assertTrue(actualPingConstant.getConstant());
  }
}
