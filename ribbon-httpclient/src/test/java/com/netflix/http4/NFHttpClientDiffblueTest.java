package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NFHttpClientDiffblueTest {
  /**
   * Test {@link NFHttpClient#getMaxTotalConnnections()}.
   *
   * <p>Method under test: {@link NFHttpClient#getMaxTotalConnnections()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int NFHttpClient.getMaxTotalConnnections()"})
  public void testGetMaxTotalConnnections() {
    // Arrange, Act and Assert
    assertEquals(20, NFHttpClientFactory.getDefaultClient().getMaxTotalConnnections());
  }

  /**
   * Test {@link NFHttpClient#getNumRetries()}.
   *
   * <p>Method under test: {@link NFHttpClient#getNumRetries()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int NFHttpClient.getNumRetries()"})
  public void testGetNumRetries() {
    // Arrange, Act and Assert
    assertEquals(3, NFHttpClientFactory.getDefaultClient().getNumRetries());
  }

  /**
   * Test {@link NFHttpClient#getSleepTimeFactorMs()}.
   *
   * <p>Method under test: {@link NFHttpClient#getSleepTimeFactorMs()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int NFHttpClient.getSleepTimeFactorMs()"})
  public void testGetSleepTimeFactorMs() {
    // Arrange, Act and Assert
    assertEquals(10, NFHttpClientFactory.getDefaultClient().getSleepTimeFactorMs());
  }
}
