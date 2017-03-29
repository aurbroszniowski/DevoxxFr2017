package org.ehcache.generator;

import io.rainfall.ObjectGenerator;

/**
 * @author Kedarnath Waikar
 */
public class AddressGenerator implements ObjectGenerator<AddressProto.Address> {
  private Long seed;
  @Override
  public AddressProto.Address generate(Long seed) {
    this.seed=seed;
    AddressProto.Address address1 = AddressProto.Address.newBuilder()
        .setState(seed + "MyState")
        .setCity(seed + "MyCity")
        .setStreet(seed + "MyStreet")
        .build();
    return address1;
  }

  @Override
  public String getDescription() {
    return "Address("+seed+")";
  }
}
