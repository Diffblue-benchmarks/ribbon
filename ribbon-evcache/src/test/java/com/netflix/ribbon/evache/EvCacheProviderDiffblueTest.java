package com.netflix.ribbon.evache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.evcache.EVCacheTranscoder;
import com.netflix.ribbon.proxy.sample.EvCacheClasses;
import com.netflix.ribbon.proxy.sample.EvCacheClasses.SampleEVCacheTranscoder;
import java.util.HashMap;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import rx.Observable;
import rx.internal.util.ScalarSynchronousObservable;

public class EvCacheProviderDiffblueTest {
  /**
   * Test {@link EvCacheProvider#EvCacheProvider(EvCacheOptions)}.
   *
   * <p>Method under test: {@link EvCacheProvider#EvCacheProvider(EvCacheOptions)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EvCacheProvider.<init>(EvCacheOptions)"})
  public void testNewEvCacheProvider() {
    // Arrange
    SampleEVCacheTranscoder transcoder = new SampleEVCacheTranscoder();
    EvCacheOptions options =
        new EvCacheOptions("App Name", null, false, 0, transcoder, "Cache Key Template");

    // Act
    EvCacheProvider<Object> actualEvCacheProvider = new EvCacheProvider<>(options);
    Observable<Object> actualGetResult = actualEvCacheProvider.get("Key", new HashMap<>());

    // Assert
    EVCacheTranscoder<?> transcoder2 = options.getTranscoder();
    assertTrue(transcoder2 instanceof SampleEVCacheTranscoder);
    Observable<Observable<Object>> nestResult = actualGetResult.nest();
    assertTrue(nestResult instanceof ScalarSynchronousObservable);
    assertTrue(nestResult.nest() instanceof ScalarSynchronousObservable);
    assertTrue(actualGetResult.timestamp().nest() instanceof ScalarSynchronousObservable);
    assertEquals("App Name", options.getAppName());
    assertEquals("Cache Key Template", options.getCacheKeyTemplate());
    assertNull(options.getCacheName());
    assertEquals(0, options.getTimeToLive());
    assertEquals(0, transcoder2.getMaxSize());
    assertFalse(options.isEnableZoneFallback());
    assertSame(transcoder, transcoder2);
    assertSame(
        actualGetResult, ((ScalarSynchronousObservable<Observable<Object>>) nestResult).get());
  }

  /**
   * Test {@link EvCacheProvider#EvCacheProvider(EvCacheOptions)}.
   *
   * <p>Method under test: {@link EvCacheProvider#EvCacheProvider(EvCacheOptions)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EvCacheProvider.<init>(EvCacheOptions)"})
  public void testNewEvCacheProvider2() {
    // Arrange
    EvCacheOptions options =
        new EvCacheOptions("App Name", null, false, 0, null, "Cache Key Template");

    // Act
    EvCacheProvider<Object> actualEvCacheProvider = new EvCacheProvider<>(options);
    Observable<Object> actualGetResult = actualEvCacheProvider.get("Key", new HashMap<>());

    // Assert
    Observable<Observable<Object>> nestResult = actualGetResult.nest();
    assertTrue(nestResult instanceof ScalarSynchronousObservable);
    assertTrue(nestResult.nest() instanceof ScalarSynchronousObservable);
    assertTrue(actualGetResult.timestamp().nest() instanceof ScalarSynchronousObservable);
    assertEquals("App Name", options.getAppName());
    assertEquals("Cache Key Template", options.getCacheKeyTemplate());
    assertNull(options.getTranscoder());
    assertNull(options.getCacheName());
    assertEquals(0, options.getTimeToLive());
    assertFalse(options.isEnableZoneFallback());
    assertSame(
        actualGetResult, ((ScalarSynchronousObservable<Observable<Object>>) nestResult).get());
  }
}
