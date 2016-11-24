package io.netlibs.ipaddr;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Helper class for passing an IP address and port together. Similar to Gauva's HostAndPort, except only IP addresses
 * are allowed, not hostnames.
 *
 * @author Theo Zourzouvillys
 *
 */

public class IpAddressAndPort {

  private final IpAddress addr;
  private final int port;

  private IpAddressAndPort(final IpAddress addr, final int port) {
    this.addr = addr;
    this.port = port;
  }

  public IpAddress address() {
    return this.addr;
  }

  public int port() {
    return this.port;
  }

  @Override
  public String toString() {
    if (this.addr instanceof IPv6Address) {
      return String.format("[%s]:%d", this.addr, this.port);
    }
    else {
      return String.format("%s:%d", this.addr, this.port);
    }
  }

  /**
   * Parses in the form x.x.x.x:yyyy, or [xx:xx::]:yyyy.
   *
   * The port must be present and in the valid range.
   *
   * @return
   *
   */

  public static final IpAddressAndPort fromString(final String input) {

    if (input.charAt(0) == '[') {

      final int end = input.indexOf(']');

      if (input.charAt(end + 1) != ':') {
        throw new IllegalArgumentException("Expected ':' after ']'");
      }

      final IPv6Address addr = IPv6Address.fromString(input.substring(1, end));

      return IpAddressAndPort.fromParts(addr, Integer.parseInt(input.substring(end + 2)));

    }
    else {

      final String[] parts = input.split(":");

      if (parts.length != 2) {
        throw new IllegalArgumentException("Expect IP:port");
      }

      return IpAddressAndPort.fromParts(IPv4Address.fromString(parts[0]), Integer.parseInt(parts[1]));

    }

  }

  public static final IpAddressAndPort fromParts(final String addr, final int port) {
    return new IpAddressAndPort(IpAddress.fromString(addr), port);
  }

  public static final IpAddressAndPort fromParts(final IpAddress addr, final int port) {
    return new IpAddressAndPort(addr, port);
  }

  @Override
  public boolean equals(final Object other) {

    if ((other == null) || !(other instanceof IpAddressAndPort)) {
      return false;
    }

    final IpAddressAndPort right = (IpAddressAndPort) other;

    if (this.port != right.port) {
      return false;
    }

    return this.addr.equals(right.addr);

  }

  @Override
  public int hashCode() {
    final StringBuilder builder = new StringBuilder();
    builder.append(this.addr);
    builder.append(':');
    builder.append(this.port);
    return builder.toString().hashCode();
  }

  public SocketAddress toSocketAddress() {
    return new InetSocketAddress(this.address().toInetAddress(), this.port());
  }

  public static IpAddressAndPort from(final SocketAddress addr) {
    final InetSocketAddress sa = (InetSocketAddress) addr;
    return IpAddressAndPort.fromParts(sa.getAddress().getHostAddress(), sa.getPort());
  }

}
