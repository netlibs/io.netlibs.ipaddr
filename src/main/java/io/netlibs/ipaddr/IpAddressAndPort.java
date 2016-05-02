package io.netlibs.ipaddr;

/**
 * Helper class for passing an IP address and port together. Similar to Gauva's HostAndPort, except only IP addresses are allowed, not
 * hostnames.
 * 
 * @author Theo Zourzouvillys
 *
 */

public class IpAddressAndPort
{

  private final IpAddress addr;
  private final int port;

  private IpAddressAndPort(IpAddress addr, int port)
  {
    this.addr = addr;
    this.port = port;
  }

  public IpAddress address()
  {
    return this.addr;
  }

  public int port()
  {
    return this.port;
  }

  @Override
  public String toString()
  {
    return String.format("%s:%d", this.addr, this.port);
  }

  /**
   * Parses in the form x.x.x.x:yyyy, or [xx:xx::]:yyyy.
   * 
   * The port must be present and in the valid range.
   * 
   * @return
   * 
   */

  public static final IpAddressAndPort fromString(String input)
  {

    if (input.charAt(0) == '[')
    {

      int end = input.indexOf(']');

      if (input.charAt(end + 1) != ':')
      {
        throw new IllegalArgumentException("Expected ':' after ']'");
      }

      IPv6Address addr = IPv6Address.fromString(input.substring(1, end));

      return fromParts(addr, Integer.parseInt(input.substring(end + 2)));

    }
    else
    {

      String[] parts = input.split(":");

      if (parts.length != 2)
      {
        throw new IllegalArgumentException("Expect IP:port");
      }

      return fromParts(IPv4Address.fromString(parts[0]), Integer.parseInt(parts[1]));

    }

  }

  public static final IpAddressAndPort fromParts(String addr, int port)
  {
    return new IpAddressAndPort(IpAddress.fromString(addr), port);
  }

  public static final IpAddressAndPort fromParts(IpAddress addr, int port)
  {
    return new IpAddressAndPort(addr, port);
  }

  @Override
  public boolean equals(Object other)
  {

    if (other == null || !(other instanceof IpAddressAndPort))
    {
      return false;
    }

    IpAddressAndPort right = (IpAddressAndPort) other;

    if (this.port != right.port)
    {
      return false;
    }

    return this.addr.equals(right.addr);

  }

  @Override
  public int hashCode()
  {
    StringBuilder builder = new StringBuilder();
    builder.append(addr);
    builder.append(':');
    builder.append(this.port);
    return builder.toString().hashCode();
  }

}
