package com.jive.oss.commons.ip;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Instances of this class represent specific IPv6 addresses.
 * 
 * @author rjs
 *
 */

public class IPv6Address
{
  private final BigInteger value;
  
  public IPv6Address(final BigInteger addr){
    this.value = addr;
  }
  
  public byte[] toByteArray() {
    // BigInteger stores leading byte for sign bit, but
    // we do not want this
    byte[] addr = new byte[16];
    byte[] valByte = this.value.toByteArray();
    if(valByte.length < 17){
      for(int i=0; i<valByte.length; i++)
        addr[i] = valByte[i];
    } else {
      int offset = valByte.length-16;
      for(int i=offset; i < 16+offset; i++)
        addr[i-offset] = valByte[i];
    }
    return addr;
  }
  
  public BigInteger value() {
    return this.value;
  }
  
  public IPv6Address lowerBoundForPrefix(int prefixLength) {
    if ((prefixLength < 0) || (prefixLength > 128)){
      throw new IllegalArgumentException("Invalid prefix length: " + Integer.toString(prefixLength));
    }
    
    BigInteger mask = BigInteger.valueOf(1L);
    mask = mask.shiftLeft(prefixLength);
    mask = BigInteger.valueOf(1L);
    for(int i=0; i<mask.bitLength(); i++)
      mask.flipBit(i);
    return new IPv6Address(this.value.and(mask));
  }
  
  public static BigInteger IPv6AddressStringToBigInteger(String ipv6String)
  {
    int current_hexlet = 0;
    BigInteger value = BigInteger.valueOf(0);
    int hexlet_count = 0;
    
    List<Character> acceptableChars = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd', 'e', 'f');
    
    String inputString = ipv6String;
    ipv6String = ipv6String.trim();
    String pad_string = "";
    int number_hexlets = 0;
  
    // need to expand the string
    if (ipv6String.contains("::")){
      for(int i=0; i<ipv6String.length(); i++){
        char thisChar = ipv6String.charAt(i);
        if (thisChar == ':' || i == ipv6String.length()-1){
          // specifically check for null start of the address (used in some cases -
          // like the default route and v6-mapped-v4 addresses)
          if ((i-1 > 0) && !(ipv6String.charAt(i-1) == ':'))
            number_hexlets++;
        }
      }
      for(int i=number_hexlets+1; i < 8; i++){
        pad_string += "0:";
      }
      // check that we resolve cases with leading zeros correctly
      
      ipv6String = ipv6String.replace("::", ":" + pad_string + "");
      if (ipv6String.startsWith(":"))
        ipv6String = ipv6String.substring(2,ipv6String.length());
    }
    
    for(int i=0; i<ipv6String.length(); i++){
      char thisChar = ipv6String.charAt(i);
      if ((acceptableChars.contains(Character.toLowerCase(thisChar)))){
        int thisVal = Integer.parseInt("" + thisChar, 16);
        current_hexlet = (current_hexlet * 16) + (thisVal);
      } else if (thisChar == ':'){
        hexlet_count += 1;
        value = addHexlet(value, current_hexlet);
        current_hexlet = 0;
      } else {
        throw new IllegalArgumentException(String.format("Invalid IPv6 Address: %s", inputString));
      }
    }
    
    // add last hexlet
    value = addHexlet(value, current_hexlet);
    hexlet_count++;
        
    if(hexlet_count != 8){
     throw new IllegalArgumentException("Invalid number of hexlets, after padding: " + hexlet_count);
    }
    
    return value;
  }
  
  public static IPv6Address fromString(String ipv6String)
  {
    BigInteger value = IPv6AddressStringToBigInteger(ipv6String);
    return of(value);
  }
  
  public static IPv6Address of(BigInteger value){
    if ((value.compareTo(BigInteger.valueOf(0L)) <= 0) || 
        value.compareTo(BigInteger.valueOf(1L).shiftLeft(128).subtract(BigInteger.valueOf(1L))) > 0){
      throw new IllegalArgumentException("Invalid IPv6 address");
    }
    return new IPv6Address(value);
  }
  
  static BigInteger addHexlet(BigInteger value, long hexlet){
    if((hexlet < 0) || (hexlet > 65535)){
      throw new IllegalArgumentException("Invalid hexlet: " + hexlet);
    }
    return value.shiftLeft(16).or(BigInteger.valueOf(hexlet));
  }
}
 
