package com.netflix.ribbon.test.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import org.junit.Test;

public class EmbeddedResourcesDiffblueTest {
  /**
   * Method under test: {@link EmbeddedResources#getStream()}
   */
  @Test
  public void testGetStream() throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualStream = (new EmbeddedResources()).getStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1);
    actualStream.write(byteArrayOutputStream);

    // Assert
    byte[] toByteArrayResult = byteArrayOutputStream.toByteArray();
    assertEquals(14890, toByteArrayResult.length);
    assertEquals(' ', toByteArrayResult[10]);
    assertEquals(' ', toByteArrayResult[14865]);
    assertEquals(' ', toByteArrayResult[14870]);
    assertEquals(' ', toByteArrayResult[14880]);
    assertEquals(' ', toByteArrayResult[14885]);
    assertEquals(' ', toByteArrayResult[18]);
    assertEquals(' ', toByteArrayResult[23]);
    assertEquals(' ', toByteArrayResult[5]);
    assertEquals('0', toByteArrayResult[11]);
    assertEquals('1', toByteArrayResult[24]);
    assertEquals('8', toByteArrayResult[14873]);
    assertEquals('9', toByteArrayResult[14871]);
    assertEquals('9', toByteArrayResult[14872]);
    assertEquals('9', toByteArrayResult[14886]);
    assertEquals('9', toByteArrayResult[14887]);
    assertEquals('9', toByteArrayResult[14888]);
    assertEquals(':', toByteArrayResult[14879]);
    assertEquals(':', toByteArrayResult[17]);
    assertEquals(':', toByteArrayResult[4]);
    assertEquals('\n', toByteArrayResult[12]);
    assertEquals('\n', toByteArrayResult[14874]);
    assertEquals('\n', toByteArrayResult[14889]);
    assertEquals('a', toByteArrayResult[1]);
    assertEquals('a', toByteArrayResult[14]);
    assertEquals('a', toByteArrayResult[14876]);
    assertEquals('a', toByteArrayResult[14878]);
    assertEquals('a', toByteArrayResult[3]);
    assertEquals('a', toByteArrayResult[Short.SIZE]);
    assertEquals('d', toByteArrayResult[0]);
    assertEquals('d', toByteArrayResult[13]);
    assertEquals('d', toByteArrayResult[14875]);
    assertEquals('e', toByteArrayResult[14869]);
    assertEquals('e', toByteArrayResult[14884]);
    assertEquals('e', toByteArrayResult[22]);
    assertEquals('e', toByteArrayResult[9]);
    assertEquals('i', toByteArrayResult[14867]);
    assertEquals('i', toByteArrayResult[14882]);
    assertEquals('i', toByteArrayResult[20]);
    assertEquals('i', toByteArrayResult[7]);
    assertEquals('l', toByteArrayResult[14866]);
    assertEquals('l', toByteArrayResult[14881]);
    assertEquals('l', toByteArrayResult[19]);
    assertEquals('l', toByteArrayResult[6]);
    assertEquals('n', toByteArrayResult[14868]);
    assertEquals('n', toByteArrayResult[14883]);
    assertEquals('n', toByteArrayResult[21]);
    assertEquals('n', toByteArrayResult[8]);
    assertEquals('t', toByteArrayResult[14877]);
    assertEquals('t', toByteArrayResult[15]);
    assertEquals('t', toByteArrayResult[2]);
  }

  /**
   * Method under test: {@link EmbeddedResources#getEntityStream()}
   */
  @Test
  public void testGetEntityStream() throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualEntityStream = (new EmbeddedResources()).getEntityStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1);
    actualEntityStream.write(byteArrayOutputStream);

    // Assert
    byte[] toByteArrayResult = byteArrayOutputStream.toByteArray();
    assertEquals(34890, toByteArrayResult.length);
    assertEquals(' ', toByteArrayResult[5]);
    assertEquals(',', toByteArrayResult[22]);
    assertEquals(',', toByteArrayResult[34877]);
    assertEquals('9', toByteArrayResult[34884]);
    assertEquals('9', toByteArrayResult[34885]);
    assertEquals('9', toByteArrayResult[34886]);
    assertEquals(':', toByteArrayResult[13]);
    assertEquals(':', toByteArrayResult[34868]);
    assertEquals(':', toByteArrayResult[34883]);
    assertEquals(':', toByteArrayResult[4]);
    assertEquals('"', toByteArrayResult[12]);
    assertEquals('"', toByteArrayResult[14]);
    assertEquals('"', toByteArrayResult[21]);
    assertEquals('"', toByteArrayResult[23]);
    assertEquals('"', toByteArrayResult[34867]);
    assertEquals('"', toByteArrayResult[34869]);
    assertEquals('"', toByteArrayResult[34876]);
    assertEquals('"', toByteArrayResult[34878]);
    assertEquals('"', toByteArrayResult[34882]);
    assertEquals('"', toByteArrayResult[7]);
    assertEquals('\n', toByteArrayResult[34888]);
    assertEquals('\n', toByteArrayResult[34889]);
    assertEquals('a', toByteArrayResult[1]);
    assertEquals('a', toByteArrayResult[24]);
    assertEquals('a', toByteArrayResult[3]);
    assertEquals('a', toByteArrayResult[34879]);
    assertEquals('a', toByteArrayResult[9]);
    assertEquals('b', toByteArrayResult[17]);
    assertEquals('b', toByteArrayResult[18]);
    assertEquals('b', toByteArrayResult[34872]);
    assertEquals('b', toByteArrayResult[34873]);
    assertEquals('d', toByteArrayResult[0]);
    assertEquals('e', toByteArrayResult[11]);
    assertEquals('e', toByteArrayResult[34866]);
    assertEquals('e', toByteArrayResult[34881]);
    assertEquals('g', toByteArrayResult[34880]);
    assertEquals('i', toByteArrayResult[34871]);
    assertEquals('i', toByteArrayResult[Short.SIZE]);
    assertEquals('m', toByteArrayResult[10]);
    assertEquals('m', toByteArrayResult[34865]);
    assertEquals('n', toByteArrayResult[20]);
    assertEquals('n', toByteArrayResult[34875]);
    assertEquals('n', toByteArrayResult[8]);
    assertEquals('o', toByteArrayResult[19]);
    assertEquals('o', toByteArrayResult[34874]);
    assertEquals('r', toByteArrayResult[15]);
    assertEquals('r', toByteArrayResult[34870]);
    assertEquals('t', toByteArrayResult[2]);
    assertEquals('{', toByteArrayResult[6]);
    assertEquals('}', toByteArrayResult[34887]);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EmbeddedResources.Person#equals(Object)}
   *   <li>{@link EmbeddedResources.Person#hashCode()}
   * </ul>
   */
  @Test
  public void testPersonEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    EmbeddedResources.Person person = new EmbeddedResources.Person("Name", 1);
    EmbeddedResources.Person person2 = new EmbeddedResources.Person("Name", 1);

    // Act and Assert
    assertEquals(person, person2);
    int expectedHashCodeResult = person.hashCode();
    assertEquals(expectedHashCodeResult, person2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EmbeddedResources.Person#equals(Object)}
   *   <li>{@link EmbeddedResources.Person#hashCode()}
   * </ul>
   */
  @Test
  public void testPersonEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    EmbeddedResources.Person person = new EmbeddedResources.Person(null, 1);
    EmbeddedResources.Person person2 = new EmbeddedResources.Person(null, 1);

    // Act and Assert
    assertEquals(person, person2);
    int expectedHashCodeResult = person.hashCode();
    assertEquals(expectedHashCodeResult, person2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EmbeddedResources.Person#equals(Object)}
   *   <li>{@link EmbeddedResources.Person#hashCode()}
   * </ul>
   */
  @Test
  public void testPersonEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    EmbeddedResources.Person person = new EmbeddedResources.Person("Name", 1);

    // Act and Assert
    assertEquals(person, person);
    int expectedHashCodeResult = person.hashCode();
    assertEquals(expectedHashCodeResult, person.hashCode());
  }

  /**
   * Method under test: {@link EmbeddedResources.Person#equals(Object)}
   */
  @Test
  public void testPersonEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    EmbeddedResources.Person person = new EmbeddedResources.Person(null, 1);

    // Act and Assert
    assertNotEquals(person, new EmbeddedResources.Person("Name", 1));
  }

  /**
   * Method under test: {@link EmbeddedResources.Person#equals(Object)}
   */
  @Test
  public void testPersonEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    EmbeddedResources.Person person = new EmbeddedResources.Person(
        "com.netflix.ribbon.test.resources.EmbeddedResources$Person", 1);

    // Act and Assert
    assertNotEquals(person, new EmbeddedResources.Person("Name", 1));
  }

  /**
   * Method under test: {@link EmbeddedResources.Person#equals(Object)}
   */
  @Test
  public void testPersonEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    EmbeddedResources.Person person = new EmbeddedResources.Person("Name", 0);

    // Act and Assert
    assertNotEquals(person, new EmbeddedResources.Person("Name", 1));
  }

  /**
   * Method under test: {@link EmbeddedResources.Person#equals(Object)}
   */
  @Test
  public void testPersonEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new EmbeddedResources.Person("Name", 1), null);
  }

  /**
   * Method under test: {@link EmbeddedResources.Person#equals(Object)}
   */
  @Test
  public void testPersonEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new EmbeddedResources.Person("Name", 1), "Different type to Person");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EmbeddedResources.Person#Person()}
   *   <li>{@link EmbeddedResources.Person#toString()}
   * </ul>
   */
  @Test
  public void testPersonGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("Person [name=null, age=0]", (new EmbeddedResources.Person()).toString());
    assertEquals("Person [name=Name, age=1]", (new EmbeddedResources.Person("Name", 1)).toString());
  }
}
