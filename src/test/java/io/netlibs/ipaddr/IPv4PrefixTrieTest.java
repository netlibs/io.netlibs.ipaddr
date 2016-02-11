package io.netlibs.ipaddr;

import static org.junit.Assert.*;

import org.junit.Test;

public class IPv4PrefixTrieTest
{

  @Test
  public void test()
  {

    IPv4PrefixTrie<String> trie = new IPv4PrefixTrie<>();

    trie.put(CidrV4Address.fromString("255.8.0.0/16"), "8.8.0.0");
    trie.put(CidrV4Address.fromString("255.8.8.0/24"), "8.8.8.0");
    trie.put(CidrV4Address.fromString("255.0.0.0/8"), "8.0.0.0");

    System.err.println(trie.get(CidrV4Address.fromString("255.8.8.8/32")));
    System.err.println(trie.longestSearch(CidrV4Address.fromString("255.8.8.8/32")));
    System.err.println(trie.prefixSearch(CidrV4Address.fromString("255.8.8.0/32")));

  }

}
