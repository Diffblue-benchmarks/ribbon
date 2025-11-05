package com.netflix.ribbon.examples.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import org.junit.Test;

public class ServerResourcesDiffblueTest {
  /**
   * Method under test: {@link ServerResources#getStream()}
   */
  @Test
  public void testGetStream() throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualStream = (new ServerResources()).getStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1);
    actualStream.write(byteArrayOutputStream);

    // Assert
    byte[] toByteArrayResult = byteArrayOutputStream.toByteArray();
    assertEquals(15890, toByteArrayResult.length);
    assertEquals(' ', toByteArrayResult[10]);
    assertEquals(' ', toByteArrayResult[15868]);
    assertEquals(' ', toByteArrayResult[15879]);
    assertEquals(' ', toByteArrayResult[15884]);
    assertEquals(' ', toByteArrayResult[19]);
    assertEquals(' ', toByteArrayResult[24]);
    assertEquals(' ', toByteArrayResult[5]);
    assertEquals('0', toByteArrayResult[11]);
    assertEquals('8', toByteArrayResult[15871]);
    assertEquals('9', toByteArrayResult[15869]);
    assertEquals('9', toByteArrayResult[15870]);
    assertEquals('9', toByteArrayResult[15885]);
    assertEquals('9', toByteArrayResult[15886]);
    assertEquals('9', toByteArrayResult[15887]);
    assertEquals(':', toByteArrayResult[15878]);
    assertEquals(':', toByteArrayResult[18]);
    assertEquals(':', toByteArrayResult[4]);
    assertEquals('\n', toByteArrayResult[12]);
    assertEquals('\n', toByteArrayResult[13]);
    assertEquals('\n', toByteArrayResult[15872]);
    assertEquals('\n', toByteArrayResult[15873]);
    assertEquals('\n', toByteArrayResult[15888]);
    assertEquals('\n', toByteArrayResult[15889]);
    assertEquals('a', toByteArrayResult[1]);
    assertEquals('a', toByteArrayResult[15]);
    assertEquals('a', toByteArrayResult[15875]);
    assertEquals('a', toByteArrayResult[15877]);
    assertEquals('a', toByteArrayResult[17]);
    assertEquals('a', toByteArrayResult[3]);
    assertEquals('d', toByteArrayResult[0]);
    assertEquals('d', toByteArrayResult[14]);
    assertEquals('d', toByteArrayResult[15874]);
    assertEquals('e', toByteArrayResult[15867]);
    assertEquals('e', toByteArrayResult[15883]);
    assertEquals('e', toByteArrayResult[23]);
    assertEquals('e', toByteArrayResult[9]);
    assertEquals('i', toByteArrayResult[15865]);
    assertEquals('i', toByteArrayResult[15881]);
    assertEquals('i', toByteArrayResult[21]);
    assertEquals('i', toByteArrayResult[7]);
    assertEquals('l', toByteArrayResult[15880]);
    assertEquals('l', toByteArrayResult[20]);
    assertEquals('l', toByteArrayResult[6]);
    assertEquals('n', toByteArrayResult[15866]);
    assertEquals('n', toByteArrayResult[15882]);
    assertEquals('n', toByteArrayResult[22]);
    assertEquals('n', toByteArrayResult[8]);
    assertEquals('t', toByteArrayResult[15876]);
    assertEquals('t', toByteArrayResult[2]);
    assertEquals('t', toByteArrayResult[Short.SIZE]);
  }

  /**
   * Method under test: {@link ServerResources#getPersonStream()}
   */
  @Test
  public void testGetPersonStream() throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualPersonStream = (new ServerResources()).getPersonStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1);
    actualPersonStream.write(byteArrayOutputStream);

    // Assert
    byte[] toByteArrayResult = byteArrayOutputStream.toByteArray();
    assertEquals(2990, toByteArrayResult.length);
    assertEquals(' ', toByteArrayResult[2965]);
    assertEquals(' ', toByteArrayResult[5]);
    assertEquals(',', toByteArrayResult[17]);
    assertEquals(',', toByteArrayResult[2978]);
    assertEquals('0', toByteArrayResult[15]);
    assertEquals('0', toByteArrayResult[2986]);
    assertEquals('1', toByteArrayResult[24]);
    assertEquals('1', toByteArrayResult[2985]);
    assertEquals('9', toByteArrayResult[2975]);
    assertEquals('9', toByteArrayResult[2976]);
    assertEquals(':', toByteArrayResult[13]);
    assertEquals(':', toByteArrayResult[23]);
    assertEquals(':', toByteArrayResult[2973]);
    assertEquals(':', toByteArrayResult[2984]);
    assertEquals(':', toByteArrayResult[4]);
    assertEquals('"', toByteArrayResult[12]);
    assertEquals('"', toByteArrayResult[14]);
    assertEquals('"', toByteArrayResult[18]);
    assertEquals('"', toByteArrayResult[22]);
    assertEquals('"', toByteArrayResult[2967]);
    assertEquals('"', toByteArrayResult[2972]);
    assertEquals('"', toByteArrayResult[2974]);
    assertEquals('"', toByteArrayResult[2977]);
    assertEquals('"', toByteArrayResult[2979]);
    assertEquals('"', toByteArrayResult[2983]);
    assertEquals('"', toByteArrayResult[7]);
    assertEquals('"', toByteArrayResult[Short.SIZE]);
    assertEquals('\n', toByteArrayResult[2988]);
    assertEquals('\n', toByteArrayResult[2989]);
    assertEquals('a', toByteArrayResult[1]);
    assertEquals('a', toByteArrayResult[19]);
    assertEquals('a', toByteArrayResult[2969]);
    assertEquals('a', toByteArrayResult[2980]);
    assertEquals('a', toByteArrayResult[3]);
    assertEquals('a', toByteArrayResult[9]);
    assertEquals('d', toByteArrayResult[0]);
    assertEquals('e', toByteArrayResult[11]);
    assertEquals('e', toByteArrayResult[21]);
    assertEquals('e', toByteArrayResult[2971]);
    assertEquals('e', toByteArrayResult[2982]);
    assertEquals('g', toByteArrayResult[20]);
    assertEquals('g', toByteArrayResult[2981]);
    assertEquals('m', toByteArrayResult[10]);
    assertEquals('m', toByteArrayResult[2970]);
    assertEquals('n', toByteArrayResult[2968]);
    assertEquals('n', toByteArrayResult[8]);
    assertEquals('t', toByteArrayResult[2]);
    assertEquals('{', toByteArrayResult[2966]);
    assertEquals('{', toByteArrayResult[6]);
    assertEquals('}', toByteArrayResult[2987]);
  }

  /**
   * Method under test: {@link ServerResources#getCustomeEvents()}
   */
  @Test
  public void testGetCustomeEvents() throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualCustomeEvents = (new ServerResources()).getCustomeEvents();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1);
    actualCustomeEvents.write(byteArrayOutputStream);

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
   * Methods under test:
   * <ul>
   *   <li>{@link ServerResources.Person#equals(Object)}
   *   <li>{@link ServerResources.Person#hashCode()}
   * </ul>
   */
  @Test
  public void testPersonEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ServerResources.Person person = new ServerResources.Person("Name", 1);
    ServerResources.Person person2 = new ServerResources.Person("Name", 1);

    // Act and Assert
    assertEquals(person, person2);
    int expectedHashCodeResult = person.hashCode();
    assertEquals(expectedHashCodeResult, person2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ServerResources.Person#equals(Object)}
   *   <li>{@link ServerResources.Person#hashCode()}
   * </ul>
   */
  @Test
  public void testPersonEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ServerResources.Person person = new ServerResources.Person(null, 1);
    ServerResources.Person person2 = new ServerResources.Person(null, 1);

    // Act and Assert
    assertEquals(person, person2);
    int expectedHashCodeResult = person.hashCode();
    assertEquals(expectedHashCodeResult, person2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ServerResources.Person#equals(Object)}
   *   <li>{@link ServerResources.Person#hashCode()}
   * </ul>
   */
  @Test
  public void testPersonEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ServerResources.Person person = new ServerResources.Person("Name", 1);

    // Act and Assert
    assertEquals(person, person);
    int expectedHashCodeResult = person.hashCode();
    assertEquals(expectedHashCodeResult, person.hashCode());
  }

  /**
   * Method under test: {@link ServerResources.Person#equals(Object)}
   */
  @Test
  public void testPersonEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ServerResources.Person person = new ServerResources.Person(null, 1);

    // Act and Assert
    assertNotEquals(person, new ServerResources.Person("Name", 1));
  }

  /**
   * Method under test: {@link ServerResources.Person#equals(Object)}
   */
  @Test
  public void testPersonEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ServerResources.Person person = new ServerResources.Person(
        "com.netflix.ribbon.examples.server.ServerResources$Person", 1);

    // Act and Assert
    assertNotEquals(person, new ServerResources.Person("Name", 1));
  }

  /**
   * Method under test: {@link ServerResources.Person#equals(Object)}
   */
  @Test
  public void testPersonEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ServerResources.Person person = new ServerResources.Person("Name", 0);

    // Act and Assert
    assertNotEquals(person, new ServerResources.Person("Name", 1));
  }

  /**
   * Method under test: {@link ServerResources.Person#equals(Object)}
   */
  @Test
  public void testPersonEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ServerResources.Person("Name", 1), null);
  }

  /**
   * Method under test: {@link ServerResources.Person#equals(Object)}
   */
  @Test
  public void testPersonEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ServerResources.Person("Name", 1), "Different type to Person");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ServerResources.Person#Person()}
   *   <li>{@link ServerResources.Person#toString()}
   * </ul>
   */
  @Test
  public void testPersonGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("Person [name=null, age=0]", (new ServerResources.Person()).toString());
    assertEquals("Person [name=Name, age=1]", (new ServerResources.Person("Name", 1)).toString());
  }
}
