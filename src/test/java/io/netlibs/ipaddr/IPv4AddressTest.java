package io.netlibs.ipaddr;

import org.junit.Test;

import io.netlibs.ipaddr.IPv4Address;

public class IPv4AddressTest
{

  @Test
  public void test()
  {
    IPv4Address.fromString("1.2.3.4");
    final IPv4Address addr = IPv4Address.fromString("255.255.255.255");
    System.err.println(addr.value());
  }

  @Test
  public void testMasks()
  {
    final IPv4Address addr = IPv4Address.fromString("255.255.255.255");
    System.err.println(addr.mask(23));
  }

}
