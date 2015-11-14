package com.jive.oss.commons.ip;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class IPv6AddressTest
{

  @Test
  public void IPv6Address_fromString_Test() throws UnknownHostException
  {
    //List<String> testCases = Arrays.asList("2001:0000:0000:0000:0000:0001:0001:0001");
    HashMap<String, String> testCases = new HashMap<String,String>();
    // format: input, expected InetAddress output
    testCases.put("2001:0000:0000:0000:0000:0001:0001:0001", "/2001:0:0:0:0:1:1:1");
    testCases.put("2001::1:1:1", "/2001:0:0:0:0:1:1:1");
    testCases.put("2001:db8:14:12:1::2", "/2001:db8:14:12:1:0:0:2");
    testCases.put("fd41:3480:e193:4765:15e1:9893:922e:fc12", "/fd41:3480:e193:4765:15e1:9893:922e:fc12");
    
    for(Map.Entry<String, String> tc : testCases.entrySet()){
      IPv6Address addr = IPv6Address.fromString(tc.getKey());
      byte[] byteAddr = addr.toByteArray();
      
      System.err.println(InetAddress.getByAddress(byteAddr).toString());
      Assert.assertEquals(tc.getValue().toUpperCase(), InetAddress.getByAddress(byteAddr).toString().toUpperCase());
    }
  }

}
