package com.jive.oss.commons.ip;

import org.junit.Assert;
import org.junit.Test;

public class QualifiedIPv4AddressTest
{

  @Test
  public void test()
  {

    final QualifiedIPv4Address addr = QualifiedIPv4Address.fromParts("10.0.0.1", 24);

    Assert.assertEquals("10.0.0.0", addr.start().toString());
    Assert.assertEquals("10.0.0.255", addr.end().toString());

    Assert.assertEquals("10.1.0.0", QualifiedIPv4Address.fromString("10.1.15.1/16").start().toString());
    Assert.assertEquals("10.1.255.255", QualifiedIPv4Address.fromString("10.1.1.1/16").end().toString());

    Assert.assertEquals(1, QualifiedIPv4Address.fromString("0.0.0.0/1").bits());
    Assert.assertEquals(32, QualifiedIPv4Address.fromString("0.0.0.0/32").bits());

  }

}
