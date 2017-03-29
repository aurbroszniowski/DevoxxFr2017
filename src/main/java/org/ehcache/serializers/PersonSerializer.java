package org.ehcache.serializers;

import org.ehcache.generator.Person;
import org.ehcache.spi.serialization.Serializer;
import org.ehcache.spi.serialization.SerializerException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @author Aurelien Broszniowski
 */

public class PersonSerializer implements Serializer<Person> {

  @Override
  public ByteBuffer serialize(final Person person) throws SerializerException {
    ByteBuffer byteBuffer = ByteBuffer.allocate(1536);
    try {
      byteBuffer.put(person.getName().getBytes("UTF-8"));
      byteBuffer.putInt(person.getAge());
      byteBuffer.putFloat(person.getHeight());
      byteBuffer.putDouble(person.getWeight());
      byteBuffer.putLong(person.getId());
      byteBuffer.putChar(person.getIsEnrolled() ? '1' : '0');
      byteBuffer.putChar(person.getGender() == Person.Gender.FEMALE ? '1' : '0');
      byteBuffer.put(person.getAddress().getBytes("UTF8"));
      byteBuffer.put(person.getRawData());
      byteBuffer.putLong(person.getDateOfBirth().getTime());
      byteBuffer.putLong(person.getDateOfJoining().getTime());
    } catch (UnsupportedEncodingException e) {
      throw new SerializerException(e);
    }
    return byteBuffer;
  }

  @Override
  public Person read(final ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {

//TODO implement
    return new Person();
  }

  @Override
  public boolean equals(final Person person, final ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
    return false;
  }
}
