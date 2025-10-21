package com.netflix.ribbon.evache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.evcache.EVCacheTranscoder;
import com.netflix.ribbon.proxy.sample.EvCacheClasses;
import com.netflix.ribbon.proxy.sample.EvCacheClasses.SampleEVCacheTranscoder;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EvCacheOptionsDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link EvCacheOptions#EvCacheOptions(String, String, boolean, int, EVCacheTranscoder,
   *       String)}
   *   <li>{@link EvCacheOptions#getAppName()}
   *   <li>{@link EvCacheOptions#getCacheKeyTemplate()}
   *   <li>{@link EvCacheOptions#getCacheName()}
   *   <li>{@link EvCacheOptions#getTimeToLive()}
   *   <li>{@link EvCacheOptions#getTranscoder()}
   *   <li>{@link EvCacheOptions#isEnableZoneFallback()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EvCacheOptions.<init>(String, String, boolean, int, EVCacheTranscoder, String)",
    "String EvCacheOptions.getAppName()",
    "String EvCacheOptions.getCacheKeyTemplate()",
    "String EvCacheOptions.getCacheName()",
    "int EvCacheOptions.getTimeToLive()",
    "EVCacheTranscoder EvCacheOptions.getTranscoder()",
    "boolean EvCacheOptions.isEnableZoneFallback()"
  })
  public void testGettersAndSetters() {
    // Arrange
    SampleEVCacheTranscoder transcoder = new SampleEVCacheTranscoder();

    // Act
    EvCacheOptions actualEvCacheOptions =
        new EvCacheOptions(
            "\"TestNetflixApp\"",
            "\"UserSessionCache\"",
            true,
            1,
            transcoder,
            "\"User::Profile::{userId}\"");
    String actualAppName = actualEvCacheOptions.getAppName();
    String actualCacheKeyTemplate = actualEvCacheOptions.getCacheKeyTemplate();
    String actualCacheName = actualEvCacheOptions.getCacheName();
    int actualTimeToLive = actualEvCacheOptions.getTimeToLive();
    EVCacheTranscoder<?> actualTranscoder = actualEvCacheOptions.getTranscoder();

    // Assert
    assertTrue(actualTranscoder instanceof SampleEVCacheTranscoder);
    assertEquals("\"TestNetflixApp\"", actualAppName);
    assertEquals("\"User::Profile::{userId}\"", actualCacheKeyTemplate);
    assertEquals("\"UserSessionCache\"", actualCacheName);
    assertEquals(1, actualTimeToLive);
    assertTrue(actualEvCacheOptions.isEnableZoneFallback());
    assertSame(transcoder, actualTranscoder);
  }
}
