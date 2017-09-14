/*
 * Copyright (c) 2008-2014 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mochi.tool.bson.io;

import mochi.tool.bson.BsonSerializationException;
import mochi.tool.bson.ByteBuf;
import mochi.tool.bson.types.ObjectId;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import static java.lang.String.format;

/**
 * An implementation of {@code BsonInput} that is backed by a {@code ByteBuf}.
 *
 * @since 3.0
 */
public class ByteBufferBsonInput implements BsonInput {
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private ByteBuf buffer;
    private int mark = -1;

    /**
     * Construct an instance with the given byte buffer.  The stream takes over ownership of the buffer and closes it when this instance is
     * closed.
     *
     * @param buffer the byte buffer
     */
    public ByteBufferBsonInput(final ByteBuf buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("buffer can not be null");
        }
        this.buffer = buffer;
        buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    @Override
    public int getPosition() {
        ensureOpen();
        return buffer.position();
    }


    @Override
    public byte readByte() {
        ensureOpen();
        ensureAvailable(1);
        return buffer.get();
    }

    @Override
    public void readBytes(final byte[] bytes) {
        ensureOpen();
        ensureAvailable(bytes.length);
        buffer.get(bytes);
    }

    @Override
    public void readBytes(final byte[] bytes, final int offset, final int length) {
        ensureOpen();
        ensureAvailable(length);
        buffer.get(bytes, offset, length);
    }

    @Override
    public long readInt64() {
        ensureOpen();
        ensureAvailable(8);
        return buffer.getLong();
    }

    @Override
    public double readDouble() {
        ensureOpen();
        ensureAvailable(8);
        return buffer.getDouble();
    }

    @Override
    public int readInt32() {
        ensureOpen();
        ensureAvailable(4);
        return buffer.getInt();
    }

    @Override
    public String readString() {
        ensureOpen();
        int size = readInt32();
        if (size <= 0) {
            throw new BsonSerializationException(format("While decoding a BSON string found a size that is not a positive number: %d",
                                                        size));
        }
        byte[] bytes = new byte[size];
        readBytes(bytes);
        if (bytes[size - 1] != 0) {
            throw new BsonSerializationException("Found a BSON string that is not null-terminated");
        }
        return new String(bytes, 0, size - 1, UTF8_CHARSET);
    }

    @Override
    public ObjectId readObjectId() {
        ensureOpen();
        byte[] bytes = new byte[12];
        readBytes(bytes);
        return new ObjectId(bytes);
    }

    @Override
    public String readCString() {
        ensureOpen();

        // TODO: potentially optimize this
        int mark = buffer.position();
        readUntilNullByte();
        int size = buffer.position() - mark - 1;
        buffer.position(mark);

        byte[] bytes = new byte[size];
        readBytes(bytes);
        readByte();  // read the trailing null byte

        return new String(bytes, UTF8_CHARSET);
    }

    private void readUntilNullByte() {
        //CHECKSTYLE:OFF
        while (readByte() != 0) { //NOPMD
            //do nothing - checkstyle & PMD hate this, not surprisingly
        }
        //CHECKSTYLE:ON
    }

    @Override
    public void skipCString() {
        ensureOpen();
        readUntilNullByte();
    }

    @Override
    public void skip(final int numBytes) {
        ensureOpen();
        buffer.position(buffer.position() + numBytes);
    }

    @Override
    public void mark(final int readLimit) {
        ensureOpen();
        mark = buffer.position();
    }

    @Override
    public void reset() {
        ensureOpen();
        if (mark == -1) {
            throw new IllegalStateException("Mark not set");
        }
        buffer.position(mark);
    }

    @Override
    public boolean hasRemaining() {
        ensureOpen();
        return buffer.hasRemaining();
    }

    @Override
    public void close() {
        buffer.release();
        buffer = null;
    }

    private void ensureOpen() {
        if (buffer == null) {
            throw new IllegalStateException("Stream is closed");
        }
    }
    private void ensureAvailable(final int bytesNeeded) {
        if (buffer.remaining() < bytesNeeded) {
            throw new BsonSerializationException(format("While decoding a BSON document %d bytes were required, "
                                                        + "but only %d remain", bytesNeeded, buffer.remaining()));
        }
    }
}
