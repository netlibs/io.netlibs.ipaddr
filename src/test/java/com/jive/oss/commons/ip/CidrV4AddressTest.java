package com.jive.oss.commons.ip;

import org.junit.Assert;
import org.junit.Test;

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
  }

}
