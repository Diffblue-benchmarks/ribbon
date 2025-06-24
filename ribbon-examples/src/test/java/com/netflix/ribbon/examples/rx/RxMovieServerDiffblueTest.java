package com.netflix.ribbon.examples.rx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RxMovieServerDiffblueTest {
  /**
   * Test {@link RxMovieServer#RxMovieServer(int)}.
   * <p>
   * Method under test: {@link RxMovieServer#RxMovieServer(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Given {@link RxMovieServer#RxMovieServer(int)} with port is one.</li>
   *   <li>Then return ServerPort is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link RxMovieServer#createServer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"io.reactivex.netty.protocol.http.server.HttpServer RxMovieServer.createServer()"})
  public void testCreateServer_givenRxMovieServerWithPortIsOne_thenReturnServerPortIsOne() {
    // Arrange, Act and Assert
    assertEquals(1, (new RxMovieServer(1)).createServer().getServerPort());
  }

  /**
   * Test {@link RxMovieServer#createServer()}.
   * <ul>
   *   <li>Then return ServerPort is {@link RxMovieServer#DEFAULT_PORT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RxMovieServer#createServer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"io.reactivex.netty.protocol.http.server.HttpServer RxMovieServer.createServer()"})
  public void testCreateServer_thenReturnServerPortIsDefault_port() {
    // Arrange, Act and Assert
    assertEquals(RxMovieServer.DEFAULT_PORT,
        (new RxMovieServer(RxMovieServer.DEFAULT_PORT)).createServer().getServerPort());
  }
}
