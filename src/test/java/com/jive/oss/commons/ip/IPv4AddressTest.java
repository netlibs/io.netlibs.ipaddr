package com.jive.oss.commons.ip;

import org.junit.Test;

public class IPv4AddressTest
{

  @Test
  public void test()
  {
    IPv4Address.fromString("1.2.3.4");
    final IPv4Address addr = IPv4Address.fromString("255.255.255.255");
    System.err.println(addr.value());
  }

}
