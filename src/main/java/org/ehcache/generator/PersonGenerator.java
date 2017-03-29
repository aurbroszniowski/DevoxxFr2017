package org.ehcache.generator;

import io.rainfall.ObjectGenerator;

import java.util.Random;

/**
 * Generates {@link org.ehcache.generator.Person Person} instances
 * using pseudo-random content.
 */
public class PersonGenerator implements ObjectGenerator<Person> {
  private final Random rnd = new Random();
  private final int maxByteSize;

  /**
   * Construct a {@code PeopleObjectGenerator}.  This generator produces
   * instances of {@link Person Person} with
   * pseudo-random content.
   *
   * @param size the maximum number of random bytes to include in
   *             {@link Person#rawData}
   */
  public PersonGenerator(int size) {
    this.maxByteSize = size;
  }

  @Override
  public Person generate(Long seed) {
    Random rnd = new Random(seed);
    String name = "name (seed=" + seed + ")";
    long id = Person.ID_FACTOR + seed;

    int age;
    if (rnd.nextBoolean()) {
      age = rnd.nextInt(125);
    } else {
      age = 0;
    }

    float height;
    if (rnd.nextBoolean()) {
      height = Person.HEIGHT_FACTOR * seed;
    } else {
      height = Float.NaN;
    }

    double weight;
    if (rnd.nextBoolean()) {
      weight = Person.WEIGHT_FACTOR * seed;
    } else {
      weight = Double.NaN;
    }

    boolean isEnrolled = rnd.nextBoolean();

    Person.Gender gender;
    if (rnd.nextBoolean()) {
      gender = (rnd.nextBoolean()) ? Person.Gender.MALE : Person.Gender.FEMALE;
    } else {
      gender = null;
    }

    java.util.Date dob;
    if (rnd.nextBoolean()) {
      dob = new java.util.Date(Person.LONG_DATEOFBIRTH + seed);
    } else {
      dob = null;
    }

    java.util.Date doj;
    if (rnd.nextBoolean()) {
      doj = new java.util.Date(Person.LONG_DATEOFJOINING + seed);
    } else {
      doj = null;
    }

    String street;
    String city;
    String state;
    if (rnd.nextBoolean()) {
      if (rnd.nextBoolean()) {
        street = seed + " random Street";
      } else {
        street = null;
      }
      if (rnd.nextBoolean()) {
        city = seed + " City is abcdefghijklmnopqrstuvwxyz";
      } else {
        city = null;
      }
      if (rnd.nextBoolean()) {
        state = seed + " Country is abcdefghijklmnopqrstuvwxyz";
      } else {
        state = null;
      }
    } else {
      street = null;
      city = null;
      state = null;
    }

    byte[] rawBytes;
    if (rnd.nextBoolean()) {
      final int byteSize = rnd.nextInt(this.maxByteSize);
      rawBytes = new byte[byteSize];
      rnd.nextBytes(rawBytes);
    } else {
      rawBytes = null;
    }

    return new Person(name, age, height, weight, id, isEnrolled, gender, dob, doj, street, city, state, rawBytes);
  }

  @Override
  public String getDescription() {
    return "Person (maxByteSize = " + maxByteSize + ")";
  }
}