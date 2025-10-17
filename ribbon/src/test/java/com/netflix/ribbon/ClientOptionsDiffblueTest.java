package com.netflix.ribbon;

import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClientOptionsDiffblueTest {
  /**
   * Test {@link ClientOptions#create()}.
   *
   * <p>Method under test: {@link ClientOptions#create()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ClientOptions ClientOptions.create()"})
  public void testCreate() {
    // Arrange, Act and Assert
    assertTrue(ClientOptions.create().getOptions().isEmpty());
  }

  /**
   * Test {@link ClientOptions#getOptions()}.
   *
   * <p>Method under test: {@link ClientOptions#getOptions()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Map ClientOptions.getOptions()"})
  public void testGetOptions() {
    // Arrange, Act and Assert
    assertTrue(ClientOptions.create().getOptions().isEmpty());
  }
}
