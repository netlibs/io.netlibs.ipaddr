package io.netlibs.ipaddr;

import java.net.InetAddress;

/**
 * Instances of this represent specific IPv4 addresses.
 *
 * @author theo
 *
 */

public final class IPv4Address
{

  private final long value;

  /**
   *
   * @param addr
   */

  private IPv4Address(final long addr)
  {
    this.value = addr;
  }

  /**
   *
   */

  long value()
  {
    return this.value;
  }

  public IPv4Address lowerBoundForPrefix(final int prefixLength)
  {
    if ((prefixLength < 0) || (prefixLength > 32))
    {
      throw new IllegalArgumentException("Invalid Prefix Length: " + Integer.toString(prefixLength));
    }
    final long mask = ~((1L << (32 - prefixLength)) - 1);
    return new IPv4Address(this.value & mask);
  }

  public IPv4Address upperBoundForPrefix(final int prefixLength)
  {
    if ((prefixLength < 0) || (prefixLength > 32))
    {
      throw new IllegalArgumentException("Invalid Prefix Length: " + Integer.toString(prefixLength));
    }
    final long mask = (1L << (32 - prefixLength)) - 1;
    return new IPv4Address(this.value | mask);
  }

  /**
   *
   */

  public static IPv4Address of(final long value)
  {
    if ((value < 0) || (value > ((1L << 32) - 1)))
    {
      throw new IllegalArgumentException("Invalid IP address");
    }
    return new IPv4Address(value);
  }

  /**
   * Parse the given IPv4 address.
   *
   * @param value
   * @return
   *
   */

  public static IPv4Address fromString(String ipv4String)
  {

    ipv4String = ipv4String.trim();

    int octet = 0;
    long value = 0;
    int octetCount = 1;

    for (int i = 0; i < ipv4String.length(); ++i)
    {

      final char ch = ipv4String.charAt(i);

      if (Character.isDigit(ch))
      {
        octet = (octet * 10) + (ch - '0');
      }
      else if (ch == '.')
      {
        octetCount++;
        value = addOctet(value, octet);
        octet = 0;
      }
      else
      {
        throw new IllegalArgumentException(String.format("Invalid IPv4 Address: %s", ipv4String));
      }

    }

    if (octetCount != 4)
    {
      throw new IllegalArgumentException("Invalid number of octets");
    }

    value = addOctet(value, octet);

    return of(value);

  }

  static long addOctet(final long value, final int octet)
  {
    if ((octet < 0) || (octet > 255))
    {
      throw new IllegalArgumentException("Invalid octet: " + octet);
    }
    return ((value) << 8) | octet;
  }

  @Override
  public String toString()
  {
    final int a = (int) (this.value >> 24);
    final int b = (int) (this.value >> 16) & 0xff;
    final int c = (int) (this.value >> 8) & 0xff;
    final int d = (int) (this.value & 0xff);
    return String.format("%d.%d.%d.%d", a, b, c, d);
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o)
    {
      return true;
    }
    else if ((o != null) && (o instanceof IPv4Address))
    {
      final IPv4Address that = (IPv4Address) o;
      return this.value == that.value;
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    return Long.hashCode(this.value);
  }

  /**
   * True if this address is within the given network.
   */

  public boolean within(final CidrV4Address mask)
  {
    return this.within(mask.prefix(), mask.mask());
  }

  public boolean within(final long prefix, final int prefixLength)
  {
    final long mask = ~((1L << (32 - prefixLength)) - 1);
    return (this.value() & mask) == prefix;
  }

  public long longValue()
  {
    return this.value;
  }

  /**
   * Mask all the host bits for the given prefix.
   * 
   * So 8.8.8.8/24 will become 8.8.8.0.
   * 
   */

  public IPv4Address mask(int prefixLength)
  {
    final long mask = ~((1L << (32 - prefixLength)) - 1);
    return IPv4Address.of((this.value() & mask));
  }

  public static IPv4Address fromAddress(InetAddress address)
  {
    return IPv4Address.fromString(address.getHostAddress());
  }

}
