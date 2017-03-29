package org.ehcache.generator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Person implements Serializable {
  private static final long serialVersionUID = 1L;

  public static final long LONG_DATEOFBIRTH = 100000000;
  public static final long LONG_DATEOFJOINING = 200000000;
  public static final float HEIGHT_FACTOR = 2.5f;
  public static final double WEIGHT_FACTOR = 5.5;
  public static final long ID_FACTOR = 500000000;

  protected String name;
  protected int age;
  protected float height;
  protected double weight;
  protected long id;
  protected boolean isEnrolled;
  protected Gender gender;
  protected Date dateOfBirth;
  protected Date dateOfJoining;
  protected Address address;
  protected byte[] rawData;

  public Person() {}

  public Person(String name, int age, float height, double weight, long id,
                boolean isEnrolled, Gender gender, Date dateOfBirth,
                Date dateOfJoining, String street, String city,
                String state, byte[] raw) {
    this.name = name;
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.id = id;
    this.isEnrolled = isEnrolled;
    this.gender = gender;
    if (street == null && city == null && state == null) {
      this.address = null;
    } else {
      this.address = new Address(street, city, state);
    }
    this.rawData = raw;
    this.dateOfBirth = dateOfBirth;
    this.dateOfJoining = dateOfJoining;
  }

  public int getAge() {
    return age;
  }

  public String getName() {
    return name;
  }

  public Gender getGender() {
    return gender;
  }

  public enum Gender implements Serializable {
    MALE, FEMALE;

    Gender() { }
  }

  public byte[] getRawData() {
    return rawData;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public Date getDateOfJoining() {
    return dateOfJoining;
  }

  public float getHeight() {
    return height;
  }

  public double getWeight() {
    return weight;
  }

  public long getId() {
    return id;
  }

  public boolean getIsEnrolled() {
    return isEnrolled;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(name:" + name + ", age:" + age
           + ", sex:" + (gender == null ? "null" : gender.name().toLowerCase()) + ", DOB: "
           + getDateOfBirth() + ", DOJ: " + getDateOfJoining() + ")";
  }

  public Address getAddress() {
    return address;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Person person = (Person)o;

    if (age != person.age) return false;
    if (Float.compare(person.height, height) != 0) return false;
    if (Double.compare(person.weight, weight) != 0) return false;
    if (id != person.id) return false;
    if (isEnrolled != person.isEnrolled) return false;
    if (name != null ? !name.equals(person.name) : person.name != null) return false;
    if (gender != person.gender) return false;
    if (dateOfBirth != null ? !dateOfBirth.equals(person.dateOfBirth) : person.dateOfBirth != null) return false;
    if (dateOfJoining != null ? !dateOfJoining.equals(person.dateOfJoining) : person.dateOfJoining != null)
      return false;
    if (address != null ? !address.equals(person.address) : person.address != null) return false;
    return Arrays.equals(rawData, person.rawData);

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = name != null ? name.hashCode() : 0;
    result = 31 * result + age;
    result = 31 * result + (height != +0.0f ? Float.floatToIntBits(height) : 0);
    temp = Double.doubleToLongBits(weight);
    result = 31 * result + (int)(temp ^ (temp >>> 32));
    result = 31 * result + (int)(id ^ (id >>> 32));
    result = 31 * result + (isEnrolled ? 1 : 0);
    result = 31 * result + (gender != null ? gender.hashCode() : 0);
    result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
    result = 31 * result + (dateOfJoining != null ? dateOfJoining.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (rawData != null ? Arrays.hashCode(rawData) : 0);
    return result;
  }
}