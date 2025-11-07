package com.netflix.ribbon.examples.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.examples.server.ServerResources.Person;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ServerResourcesDiffblueTest {
  /**
   * Test {@link ServerResources#getStream()}.
   * <ul>
   *   <li>Then array length is {@code 15890}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServerResources#getStream()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StreamingOutput ServerResources.getStream()"})
  public void testGetStream_thenArrayLengthIs15890() throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualStream = (new ServerResources()).getStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1);
    actualStream.write(byteArrayOutputStream);

    // Assert
    assertEquals(15890, byteArrayOutputStream.toByteArray().length);
  }

  /**
   * Test {@link ServerResources#getPersonStream()}.
   * <ul>
   *   <li>Then array length is {@code 2990}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServerResources#getPersonStream()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StreamingOutput ServerResources.getPersonStream()"})
  public void testGetPersonStream_thenArrayLengthIs2990() throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualPersonStream = (new ServerResources()).getPersonStream();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1);
    actualPersonStream.write(byteArrayOutputStream);

    // Assert
    assertEquals(2990, byteArrayOutputStream.toByteArray().length);
  }

  /**
   * Test {@link ServerResources#getCustomeEvents()}.
   * <ul>
   *   <li>Then array length is {@code 14890}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServerResources#getCustomeEvents()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StreamingOutput ServerResources.getCustomeEvents()"})
  public void testGetCustomeEvents_thenArrayLengthIs14890() throws IOException, WebApplicationException {
    // Arrange and Act
    StreamingOutput actualCustomeEvents = (new ServerResources()).getCustomeEvents();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1);
    actualCustomeEvents.write(byteArrayOutputStream);

    // Assert
    assertEquals(14890, byteArrayOutputStream.toByteArray().length);
  }

  /**
   * Test Person {@link Person#equals(Object)}, and {@link Person#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Person#equals(Object)}
   *   <li>{@link Person#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Person.equals(Object)", "int Person.hashCode()"})
  public void testPersonEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Person person = new Person("Name", 1);
    Person person2 = new Person("Name", 1);

    // Act and Assert
    assertEquals(person, person2);
    int expectedHashCodeResult = person.hashCode();
    assertEquals(expectedHashCodeResult, person2.hashCode());
  }

  /**
   * Test Person {@link Person#equals(Object)}, and {@link Person#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Person#equals(Object)}
   *   <li>{@link Person#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Person.equals(Object)", "int Person.hashCode()"})
  public void testPersonEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Person person = new Person(null, 1);
    Person person2 = new Person(null, 1);

    // Act and Assert
    assertEquals(person, person2);
    int expectedHashCodeResult = person.hashCode();
    assertEquals(expectedHashCodeResult, person2.hashCode());
  }

  /**
   * Test Person {@link Person#equals(Object)}, and {@link Person#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Person#equals(Object)}
   *   <li>{@link Person#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Person.equals(Object)", "int Person.hashCode()"})
  public void testPersonEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Person person = new Person("Name", 1);

    // Act and Assert
    assertEquals(person, person);
    int expectedHashCodeResult = person.hashCode();
    assertEquals(expectedHashCodeResult, person.hashCode());
  }

  /**
   * Test Person {@link Person#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Person#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Person.equals(Object)", "int Person.hashCode()"})
  public void testPersonEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Person person = new Person(null, 1);

    // Act and Assert
    assertNotEquals(person, new Person("Name", 1));
  }

  /**
   * Test Person {@link Person#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Person#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Person.equals(Object)", "int Person.hashCode()"})
  public void testPersonEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Person person = new Person("com.netflix.ribbon.examples.server.ServerResources$Person", 1);

    // Act and Assert
    assertNotEquals(person, new Person("Name", 1));
  }

  /**
   * Test Person {@link Person#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Person#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Person.equals(Object)", "int Person.hashCode()"})
  public void testPersonEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Person person = new Person("Name", 0);

    // Act and Assert
    assertNotEquals(person, new Person("Name", 1));
  }

  /**
   * Test Person {@link Person#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Person#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Person.equals(Object)", "int Person.hashCode()"})
  public void testPersonEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Person("Name", 1), null);
  }

  /**
   * Test Person {@link Person#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Person#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Person.equals(Object)", "int Person.hashCode()"})
  public void testPersonEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Person("Name", 1), "Different type to Person");
  }

  /**
   * Test Person getters and setters.
   * <ul>
   *   <li>Then return toString is {@code Person [name=null, age=0]}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Person#Person()}
   *   <li>{@link Person#toString()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Person.<init>()", "void Person.<init>(String, int)", "String Person.toString()"})
  public void testPersonGettersAndSetters_thenReturnToStringIsPersonNameNullAge0() {
    // Arrange, Act and Assert
    assertEquals("Person [name=null, age=0]", (new Person()).toString());
  }

  /**
   * Test Person getters and setters.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return toString is {@code Person [name=Name, age=1]}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Person#Person(String, int)}
   *   <li>{@link Person#toString()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Person.<init>()", "void Person.<init>(String, int)", "String Person.toString()"})
  public void testPersonGettersAndSetters_whenName_thenReturnToStringIsPersonNameNameAge1() {
    // Arrange, Act and Assert
    assertEquals("Person [name=Name, age=1]", (new Person("Name", 1)).toString());
  }
}
