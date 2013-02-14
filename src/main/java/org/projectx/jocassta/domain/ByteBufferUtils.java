package org.projectx.jocassta.domain;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

import org.apache.cassandra.utils.ByteBufferUtil;

public class ByteBufferUtils {
  public static String string(final ByteBuffer b) {
    try {
      return ByteBufferUtil.string(b);
    } catch (final CharacterCodingException e) {
      throw new IllegalStateException(e);
    }
  }
}
