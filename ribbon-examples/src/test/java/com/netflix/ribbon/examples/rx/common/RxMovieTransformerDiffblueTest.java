package com.netflix.ribbon.examples.rx.common;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.DuplicatedByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RxMovieTransformerDiffblueTest {
  /**
   * Test {@link RxMovieTransformer#call(Movie, ByteBufAllocator)} with {@code Movie}, {@code ByteBufAllocator}.
   * <p>
   * Method under test: {@link RxMovieTransformer#call(Movie, ByteBufAllocator)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ByteBuf RxMovieTransformer.call(Movie, ByteBufAllocator)"})
  public void testCallWithMovieByteBufAllocator() {
    // Arrange
    RxMovieTransformer rxMovieTransformer = new RxMovieTransformer();
    PooledByteBufAllocator byteBufAllocator = mock(PooledByteBufAllocator.class);
    DuplicatedByteBuf duplicatedByteBuf = new DuplicatedByteBuf(Unpooled.compositeBuffer(3));
    when(byteBufAllocator.buffer(anyInt())).thenReturn(duplicatedByteBuf);

    // Act
    ByteBuf actualCallResult = rxMovieTransformer.call(Movie.BREAKING_BAD, byteBufAllocator);

    // Assert
    verify(byteBufAllocator).buffer(eq(121));
    assertSame(duplicatedByteBuf, actualCallResult);
  }
}
