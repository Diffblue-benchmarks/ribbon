package com.netflix.ribbon.examples.rx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class RxMovieServerDiffblueTest {
  /**
   * Method under test: {@link RxMovieServer#createServer()}
   */
  @Test
  public void testCreateServer() {
    // Arrange, Act and Assert
    assertEquals(RxMovieServer.DEFAULT_PORT,
        (new RxMovieServer(RxMovieServer.DEFAULT_PORT)).createServer().getServerPort());
    assertEquals(1, (new RxMovieServer(1)).createServer().getServerPort());
  }

  /**
   * Method under test: {@link RxMovieServer#RxMovieServer(int)}
   */
  @Test
  public void testNewRxMovieServer() {
    // Arrange and Act
    RxMovieServer actualRxMovieServer = new RxMovieServer(RxMovieServer.DEFAULT_PORT);

    // Assert
    assertTrue(actualRxMovieServer.movies.isEmpty());
    assertTrue(actualRxMovieServer.userRecommendations.isEmpty());
  }
}
