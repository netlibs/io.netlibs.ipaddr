package io.netlibs.ipaddr;

import org.junit.Assert;
import org.junit.Test;

import io.netlibs.ipaddr.CidrV4Address;
import io.netlibs.ipaddr.IPv4Address;

public class CidrV4AddressTest
{

  @Test
  public void test()
  {
    Assert.assertEquals(CidrV4Address.fromString("8/8"), CidrV4Address.fromParts(IPv4Address.fromString("8.0.0.0"), 8));
    Assert.assertTrue(IPv4Address.fromString("8.8.8.8").within(CidrV4Address.fromString("8/8")));
    Assert.assertTrue(IPv4Address.fromString("8.0.0.0").within(CidrV4Address.fromString("8/8")));
    Assert.assertTrue(IPv4Address.fromString("8.255.255.255").within(CidrV4Address.fromString("8/8")));
    Assert.assertFalse(IPv4Address.fromString("8.255.255.255").within(CidrV4Address.fromString("8.0/16")));
    CidrV4Address.fromString("8.0.0/24");
    CidrV4Address.fromString("8.0.0.0/32");
    CidrV4Address.fromString("127.0.0.0/24");
    CidrV4Address.fromString("127.0.0.1/32");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalid1()
  {
    CidrV4Address.fromString("127.0.0.1/24");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalid2()
  {
    CidrV4Address.fromString("127.0.0.1/31");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalid3()
  {
    CidrV4Address.fromString("127.0.0.1/16");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalid4()
  {
    CidrV4Address.fromString("10.0.0.1/0");
  }

}
