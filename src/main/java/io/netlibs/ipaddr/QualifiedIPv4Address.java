package io.netlibs.ipaddr;

/**
 * Instances represent an IP address along with a mask which unlike a CIDR which is distingished by not having any host bits set,
 * {@link QualifiedIPv4Address} includes the whole host address.
 *
 * This is commonly used to describe an interface's IP address along with it's network.
 *
 * @author theo
 *
 */

public class QualifiedIPv4Address
{

  /**
   *
   */

  private final IPv4Address address;

  /**
   *
   */

  private final int mask;

  /**
   *
   * @param address
   * @param mask
   */

  public QualifiedIPv4Address(final IPv4Address address, final int mask)
  {
    this.address = address;
    this.mask = mask;
  }

  public int bits()
  {
    return this.mask;
  }

  public IPv4Address start()
  {
    return this.address.lowerBoundForPrefix(this.mask);
  }

  public IPv4Address end()
  {
    return this.address.upperBoundForPrefix(this.mask);
  }

  /**
   *
   */

  public static QualifiedIPv4Address fromString(final String input)
  {

    final int pos = input.indexOf('/');

    if (pos == -1)
    {
      throw new IllegalArgumentException("Invalid addr/prefix");
    }

    final int prefix = Integer.parseInt(input.substring(pos + 1));

    return fromParts(input.substring(0, pos), prefix);

  }

  /**
   *
   * @param address
   * @param mask
   * @return
   */

  public static QualifiedIPv4Address fromParts(final String address, final int mask)
  {
    return fromParts(IPv4Address.fromString(address), mask);
  }

  /**
   *
   * @param addr
   *          The address.
   * @param mask
   *          The mask. Between 0 and 32.
   * @return
   */

  private static QualifiedIPv4Address fromParts(final IPv4Address addr, final int mask)
  {
    if ((mask < 0) || (mask > 32))
    {
      throw new IllegalArgumentException("Invalid IPv4 Address");
    }
    return new QualifiedIPv4Address(addr, mask);
  }

  @Override
  public String toString()
  {
    return new StringBuilder(this.address.toString()).append('/').append(this.mask).toString();
  }

}
