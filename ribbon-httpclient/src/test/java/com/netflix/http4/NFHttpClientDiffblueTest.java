package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NFHttpClientDiffblueTest {
  /**
   * Test {@link NFHttpClient#getMaxTotalConnnections()}.
   * <p>
   * Method under test: {@link NFHttpClient#getMaxTotalConnnections()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int NFHttpClient.getMaxTotalConnnections()"})
  public void testGetMaxTotalConnnections() {
    // Arrange, Act and Assert
    assertEquals(20, NFHttpClientFactory.getDefaultClient().getMaxTotalConnnections());
  }

  /**
   * Test {@link NFHttpClient#getMaxConnectionsPerHost()}.
   * <ul>
   *   <li>Given DefaultClient.</li>
   * </ul>
   * <p>
   * Method under test: {@link NFHttpClient#getMaxConnectionsPerHost()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int NFHttpClient.getMaxConnectionsPerHost()"})
  public void testGetMaxConnectionsPerHost_givenDefaultClient() {
    // Arrange, Act and Assert
    assertEquals(2, NFHttpClientFactory.getDefaultClient().getMaxConnectionsPerHost());
  }

  /**
   * Test {@link NFHttpClient#getMaxConnectionsPerHost()}.
   * <ul>
   *   <li>Given NFHttpClient {@code https://example.org/example} is {@code 8080}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NFHttpClient#getMaxConnectionsPerHost()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int NFHttpClient.getMaxConnectionsPerHost()"})
  public void testGetMaxConnectionsPerHost_givenNFHttpClientHttpsExampleOrgExampleIs8080() {
    // Arrange, Act and Assert
    assertEquals(2,
        NFHttpClientFactory.getNFHttpClient("https://example.org/example", 8080).getMaxConnectionsPerHost());
  }

  /**
   * Test {@link NFHttpClient#getNumRetries()}.
   * <p>
   * Method under test: {@link NFHttpClient#getNumRetries()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int NFHttpClient.getNumRetries()"})
  public void testGetNumRetries() {
    // Arrange, Act and Assert
    assertEquals(3, NFHttpClientFactory.getDefaultClient().getNumRetries());
  }

  /**
   * Test {@link NFHttpClient#getSleepTimeFactorMs()}.
   * <p>
   * Method under test: {@link NFHttpClient#getSleepTimeFactorMs()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int NFHttpClient.getSleepTimeFactorMs()"})
  public void testGetSleepTimeFactorMs() {
    // Arrange, Act and Assert
    assertEquals(10, NFHttpClientFactory.getDefaultClient().getSleepTimeFactorMs());
  }
}
