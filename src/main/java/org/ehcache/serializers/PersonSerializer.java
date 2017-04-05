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
   // TODO : Implementer
    throw new UnsupportedOperationException();
  }

  @Override
  public Person read(final ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
    // TODO : Implementer
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(Person person, ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
    // TODO : Implementer
    throw new UnsupportedOperationException();
  }

}
