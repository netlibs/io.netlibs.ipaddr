package com.jive.oss.commons.ip;

/**
 * A v4 prefix and mask, The prefix may be between 1 and 4 octets.
 *
 * @author theo
 *
 */

public class CidrV4Address
{

  private final long prefix;
  private final int mask;

  /**
   *
   * @param prefix
   * @param mask
   */

  private CidrV4Address(final long prefix, final int mask)
  {
    if ((mask < 0) || (mask > 32))
    {
      throw new IllegalArgumentException("invalid mask");
    }
    this.prefix = prefix;
    this.mask = mask;
  }

  public long prefix()
  {
    return this.prefix;
  }

  public int mask()
  {
    return this.mask;
  }

  public static CidrV4Address fromString(final String addr)
  {

    final int idx = addr.indexOf('/');

    if (idx == -1)
    {
      throw new IllegalArgumentException("Invalid CIDR");
    }

    final int mask = Integer.parseInt(addr.substring(idx + 1));

    return fromParts(addr.substring(0, idx), mask);

  }

  /**
   * The address doesn't need to be fully formed, as 8.8/0 is valid here.
   *
   * @param addr
   * @param mask
   * @return
   */

  public static CidrV4Address fromParts(final String prefix, final int mask)
  {
    return new CidrV4Address(parsePrefix(prefix), mask);
  }

  public static long parsePrefix(String ipv4String)
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
        value = IPv4Address.addOctet(value, octet);
        octet = 0;
      }
      else
      {
        throw new IllegalArgumentException(String.format("Invalid IPv4 Address: %s", ipv4String));
      }

    }

    value = IPv4Address.addOctet(value, octet);
    octet++;

    while (octetCount < 4)
    {
      octetCount++;
      value = IPv4Address.addOctet(value, 0);
    }

    return value;

  }

  public static CidrV4Address fromParts(final IPv4Address prefix, final int mask)
  {
    return new CidrV4Address(prefix.value(), mask);
  }

  @Override
  public String toString()
  {
    return String.format("%s/%s", IPv4Address.of(this.prefix), this.mask);
  }

  @Override
  public boolean equals(final Object other)
  {
    if (other instanceof CidrV4Address)
    {
      final CidrV4Address addr = (CidrV4Address) other;
      if (addr.prefix != this.prefix)
      {
        return false;
      }
      if (addr.mask != this.mask)
      {
        return false;
      }
      return true;
    }
    return false;
  }

}
