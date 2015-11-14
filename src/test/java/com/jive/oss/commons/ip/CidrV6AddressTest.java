package com.jive.oss.commons.ip;


import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;


public class CidrV6AddressTest
{

  @Test
  public void CidrV6_tc01()
  { 
    CidrV6Address c = CidrV6Address.fromString("::/0");
    Assert.assertEquals(c.prefix(), BigInteger.valueOf(0L));
    Assert.assertEquals(c.mask(), 0);
    
    boolean ethrown = false;
    try {
      CidrV6Address d = CidrV6Address.fromString("::1/0");
    } catch (IllegalArgumentException e) {
      ethrown = true;
    }
    Assert.assertEquals(true, ethrown);
    
    ethrown = false;
    try {
      CidrV6Address e = CidrV6Address.fromString("2001:DB8::/32");
    } catch (IllegalArgumentException e) {
      ethrown = true;
    }
    Assert.assertEquals(false, ethrown);  
    
    ethrown = false;
    try {
      CidrV6Address f = CidrV6Address.fromString("2001:DB8::1/32");
    } catch (IllegalArgumentException e) {
      ethrown = true;
    }
    Assert.assertEquals(true, ethrown);
  } 
}
