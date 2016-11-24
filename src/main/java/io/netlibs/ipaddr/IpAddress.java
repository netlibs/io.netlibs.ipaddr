package io.netlibs.ipaddr;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IP address interface for places where an IPv4 or IPv6 address can be used.
 *
 * @author theo
 *
 */

public interface IpAddress {

  public static IpAddress fromString(final String addr) {

    if (addr.indexOf(':') != -1) {
      return IPv6Address.fromString(addr);
    }

    return IPv4Address.fromString(addr);

  }

  public InetAddress toInetAddress();

  public static boolean isLinkLocal(final String addr) {
    return addr.startsWith("fe80:");
  }

  /**
   * prefers IPv6.
   *
   * @return
   */

  public static IpAddress loopbackAddress() {

    if (true) {
      return IpAddress.fromString("127.0.0.1");
    }

    try {

      for (final NetworkInterface i : Collections.list(NetworkInterface.getNetworkInterfaces())) {

        if (!i.isLoopback()) {
          continue;
        }

        final List<InetAddress> addresses = Collections.list(i.getInetAddresses()).stream()
            .filter(p -> !IpAddress.isLinkLocal(p.getHostAddress()))
            .collect(Collectors.toList());

        for (final InetAddress addr : addresses) {
          if (addr instanceof Inet6Address) {
            return IpAddress.from(addr);
          }
        }

        for (final InetAddress addr : addresses) {
          if (addr instanceof Inet4Address) {
            return IpAddress.from(addr);
          }
        }

      }

    }
    catch (final SocketException ex) {
      throw new RuntimeException(ex);
    }

    throw new IllegalStateException("No loopback interface found");

  }

  public static IpAddress from(final InetAddress addr) {
    return IpAddress.fromString(addr.getHostAddress());
  }

}
