package com.jive.oss.commons.ip;

import java.math.BigInteger;
import java.util.stream.IntStream;

import javax.print.attribute.IntegerSyntax;

public class CidrV6Address
{
  
  private BigInteger prefix;
  private int mask;
  
  private CidrV6Address(BigInteger prefix, int mask){
    if ((mask < 0) || (mask > 128))
      throw new IllegalArgumentException("Invalid mask specified: " + mask);
    
    if ((mask == 0) && (prefix.equals(0L)))
      throw new IllegalArgumentException("/0 may not have host bits set");
    
    BigInteger bitmask = BigInteger.valueOf(1L).shiftLeft(128-mask).subtract(BigInteger.valueOf(1L));
    if (bitmask.and(prefix).compareTo(BigInteger.valueOf(0L)) != 0)
      throw new IllegalArgumentException("CIDR may not have host bits set");
    
    this.prefix = prefix;
    this.mask = mask;
  }
  
  public static CidrV6Address fromString(String cidrv6String){
    String[] stringParts = cidrv6String.split("/");
    if (stringParts.length != 2)
      throw new IllegalArgumentException("Invalid CIDR string specified");    
    String prefixString = stringParts[0];
    int mask = Integer.parseInt(stringParts[1]);
    BigInteger prefix = IPv6Address.IPv6AddressStringToBigInteger(prefixString);
    return new CidrV6Address(prefix, mask);
  }
  
  public int mask(){
    return this.mask;
  }
  
  public BigInteger prefix(){
    return this.prefix;
  }
  
  public IPv6Address prefixAsIPv6Address(){
    return new IPv6Address(this.prefix());
  }
  
  public static CidrV6Address fromParts(String prefix, int mask){
    return new CidrV6Address(IPv6Address.IPv6AddressStringToBigInteger(prefix), mask);
  }
  
  public static CidrV6Address fromParts(IPv6Address prefix, int mask){
    return new CidrV6Address(prefix.value(), mask);
  }
}
