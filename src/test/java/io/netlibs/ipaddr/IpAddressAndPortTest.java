package io.netlibs.ipaddr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IpAddressAndPortTest
{

  @Test
  public void test()
  {
    assertEquals(IpAddressAndPort.fromParts("1.2.3.4", 5555), IpAddressAndPort.fromString("1.2.3.4:5555"));
    assertEquals(IpAddressAndPort.fromParts("::1", 5555), IpAddressAndPort.fromString("[::1]:5555"));
  }

}
