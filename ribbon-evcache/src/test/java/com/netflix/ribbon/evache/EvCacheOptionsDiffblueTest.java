package com.netflix.ribbon.evache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.netflix.evcache.EVCacheTranscoder;
import com.netflix.ribbon.proxy.sample.EvCacheClasses;
import org.junit.Test;

public class EvCacheOptionsDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link EvCacheOptions#EvCacheOptions(String, String, boolean, int, EVCacheTranscoder, String)}
   *   <li>{@link EvCacheOptions#getAppName()}
   *   <li>{@link EvCacheOptions#getCacheKeyTemplate()}
   *   <li>{@link EvCacheOptions#getCacheName()}
   *   <li>{@link EvCacheOptions#getTimeToLive()}
   *   <li>{@link EvCacheOptions#getTranscoder()}
   *   <li>{@link EvCacheOptions#isEnableZoneFallback()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    EvCacheClasses.SampleEVCacheTranscoder transcoder = new EvCacheClasses.SampleEVCacheTranscoder();

    // Act
    EvCacheOptions actualEvCacheOptions = new EvCacheOptions("App Name", "Cache Name", true, 1, transcoder,
        "Cache Key Template");
    String actualAppName = actualEvCacheOptions.getAppName();
    String actualCacheKeyTemplate = actualEvCacheOptions.getCacheKeyTemplate();
    String actualCacheName = actualEvCacheOptions.getCacheName();
    int actualTimeToLive = actualEvCacheOptions.getTimeToLive();
    EVCacheTranscoder<?> actualTranscoder = actualEvCacheOptions.getTranscoder();

    // Assert
    assertTrue(actualTranscoder instanceof EvCacheClasses.SampleEVCacheTranscoder);
    assertEquals("App Name", actualAppName);
    assertEquals("Cache Key Template", actualCacheKeyTemplate);
    assertEquals("Cache Name", actualCacheName);
    assertEquals(1, actualTimeToLive);
    assertTrue(actualEvCacheOptions.isEnableZoneFallback());
    assertSame(transcoder, actualTranscoder);
  }
}
