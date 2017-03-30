package org.ehcache.serializers;

import org.ehcache.generator.Person;
import org.ehcache.spi.serialization.Serializer;
import org.ehcache.spi.serialization.SerializerException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * @author Aurelien Broszniowski
 */

public class PersonSerializer implements Serializer<Person> {

  public PersonSerializer(ClassLoader classLoader) {
  }

  @Override
  public ByteBuffer serialize(final Person person) throws SerializerException {
    try {
      ByteBuffer byteBuffer = ByteBuffer.allocate(1536);
      byteBuffer.putLong(person.getId());
      byte[] nameBytes = person.getName().getBytes("UTF-8");
      byteBuffer.putInt(nameBytes.length);
      byteBuffer.put(nameBytes);
      byteBuffer.putInt(person.getAge());
      byteBuffer.putFloat(person.getHeight());
      byteBuffer.putDouble(person.getWeight());
      byteBuffer.put((byte) (person.getIsEnrolled() ? 1 : 0));
      byteBuffer.put((byte) (person.getGender() == Person.Gender.FEMALE ? 1 : 0));
      byte[] addressBytes = person.getAddress().getBytes("UTF8");
      byteBuffer.putInt(addressBytes.length);
      byteBuffer.put(addressBytes);
      if (person.getRawData() != null) {
        byteBuffer.putInt(person.getRawData().length);
        byteBuffer.put(person.getRawData());
      } else {
        byteBuffer.putInt(-1);
      }
      byteBuffer.putLong(person.getDateOfBirth() == null ? 0L : person.getDateOfBirth().getTime());
      byteBuffer.putLong(person.getDateOfJoining() == null ? 0L : person.getDateOfJoining().getTime());
      return byteBuffer;
    } catch (UnsupportedEncodingException e) {
      throw new SerializerException(e);
    }
  }

  @Override
  public Person read(final ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
    try {
      long id = byteBuffer.getLong();
      byte[] nameBytes = new byte[byteBuffer.getInt()];
      byteBuffer.get(nameBytes);
      String name = new String(nameBytes, "UTF-8");
      int age = byteBuffer.getInt();
      float height = byteBuffer.getFloat();
      double weight = byteBuffer.getDouble();
      boolean enrolled = byteBuffer.get() == 0;
      Person.Gender gender = byteBuffer.get() == 1 ? Person.Gender.FEMALE : Person.Gender.MALE;
      byte[] addressBytes = new byte[byteBuffer.getInt()];
      byteBuffer.get(addressBytes);
      String address = new String(addressBytes, "UTF-8");
      byte[] rawData = null;
      int rawDataLength = byteBuffer.getInt();
      if (rawDataLength >= 0) {
        rawData = new byte[rawDataLength];
        byteBuffer.get(rawData);
      }
      long dateOfBirthLong = byteBuffer.getLong();
      Date dateOfBirth = dateOfBirthLong == 0L ? null : new Date(dateOfBirthLong);
      long dateOfJoiningLong = byteBuffer.getLong();
      Date dateOfJoining = dateOfJoiningLong == 0L ? null : new Date(dateOfJoiningLong);

      return new Person(name, age, height, weight, id, enrolled, gender, dateOfBirth, dateOfJoining, address, rawData);
    } catch (UnsupportedEncodingException e) {
      throw new SerializerException(e);
    }
  }

  @Override
  public boolean equals(Person person, ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
    try {
      long id = byteBuffer.getLong();
      if (id != person.getId()) return false;

      byte[] nameBytes = new byte[byteBuffer.getInt()];
      byteBuffer.get(nameBytes);
      String name = new String(nameBytes, "UTF-8");
      if (!name.equals(person.getName())) {
        return false;
      }

      int age = byteBuffer.getInt();
      if (age != person.getAge()) {
        return false;
      }

      float height = byteBuffer.getFloat();
      if (height != person.getHeight()) {
        return false;
      }

      double weight = byteBuffer.getDouble();
      if (weight != person.getWeight()) {
        return false;
      }

      boolean enrolled = byteBuffer.get() == 0;
      if (enrolled != person.getIsEnrolled()) {
        return false;
      }

      Person.Gender gender = byteBuffer.get() == 1 ? Person.Gender.FEMALE : Person.Gender.MALE;
      if (gender != person.getGender()) {
        return false;
      }

      byte[] addressBytes = new byte[byteBuffer.getInt()];
      byteBuffer.get(addressBytes);
      String address = new String(addressBytes, "UTF-8");
      if (!address.equals(person.getAddress())) {
        return false;
      }

      int rawDataLength = byteBuffer.getInt();
      if (rawDataLength >= 0) {
        byte[] rawData = new byte[rawDataLength];
        byteBuffer.get(rawData);
        if (!Arrays.equals(rawData, person.getRawData())) {
          return false;
        }
      } else {
        if (person.getRawData() != null) {
          return false;
        }
      }
      long dateOfBirthLong = byteBuffer.getLong();
      Date dateOfBirth = dateOfBirthLong == 0L ? null : new Date(dateOfBirthLong);
      if (!Objects.equals(dateOfBirth, person.getDateOfBirth())) {
        return false;
      }
      long dateOfJoiningLong = byteBuffer.getLong();
      Date dateOfJoining = dateOfJoiningLong == 0L ? null : new Date(dateOfJoiningLong);
      if (!Objects.equals(dateOfJoining, person.getDateOfJoining())) {
        return false;
      }

      return true;
    } catch (UnsupportedEncodingException e) {
      throw new SerializerException(e);
    }
  }

//  @Override
//  public boolean equals(final Person person, final ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
//    return read(byteBuffer).equals(person);
//  }

}
