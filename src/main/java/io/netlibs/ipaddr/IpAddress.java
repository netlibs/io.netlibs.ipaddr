package io.netlibs.ipaddr;

/**
 * IP address interface for places where an IPv4 or IPv6 address can be used.
 * 
 * @author theo
 *
 */

public interface IpAddress
{

  public static IpAddress fromString(String addr)
  {

    if (addr.indexOf(':') != -1)
    {
      return IPv6Address.fromString(addr);
    }

    return IPv4Address.fromString(addr);

  }

}
