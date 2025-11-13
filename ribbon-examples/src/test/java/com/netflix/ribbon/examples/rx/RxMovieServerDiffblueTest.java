package com.netflix.ribbon.examples.rx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RxMovieServerDiffblueTest {
  /**
   * Test {@link RxMovieServer#RxMovieServer(int)}.
   *
   * <p>Method under test: {@link RxMovieServer#RxMovieServer(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RxMovieServer.<init>(int)"})
  public void testNewRxMovieServer() {
    // Arrange and Act
    RxMovieServer actualRxMovieServer = new RxMovieServer(RxMovieServer.DEFAULT_PORT);

    // Assert
    assertTrue(actualRxMovieServer.movies.isEmpty());
    assertTrue(actualRxMovieServer.userRecommendations.isEmpty());
  }

  /**
   * Test {@link RxMovieServer#createServer()}.
   *
   * <ul>
   *   <li>Given {@link RxMovieServer#RxMovieServer(int)} with port is one.
   *   <li>Then return ServerPort is one.
   * </ul>
   *
   * <p>Method under test: {@link RxMovieServer#createServer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "io.reactivex.netty.protocol.http.server.HttpServer RxMovieServer.createServer()"
  })
  public void testCreateServer_givenRxMovieServerWithPortIsOne_thenReturnServerPortIsOne() {
    // Arrange, Act and Assert
    assertEquals(1, new RxMovieServer(1).createServer().getServerPort());
  }
}
