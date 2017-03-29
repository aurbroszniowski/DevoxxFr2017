package org.ehcache.generator;

import java.io.Serializable;

public class Address implements Serializable {
  private static final long serialVersionUID = 1L;

  private String street;
  private String state;
  private String city;

  public Address() { }

  public Address(String street, String city, String state) {
    this.street = street;
    this.city = city;
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getStreet() {
    return street;
  }

  public String toString() {
    return street + ", " + city + ", " + state;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Address address = (Address)o;

    if (street != null ? !street.equals(address.street) : address.street != null) return false;
    if (state != null ? !state.equals(address.state) : address.state != null) return false;
    return !(city != null ? !city.equals(address.city) : address.city != null);

  }

  @Override
  public int hashCode() {
    int result = street != null ? street.hashCode() : 0;
    result = 31 * result + (state != null ? state.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    return result;
  }
}
