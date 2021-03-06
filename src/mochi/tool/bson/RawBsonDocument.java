/*
 * Copyright (c) 2008-2015 MongoDB, Inc.
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

package mochi.tool.bson;

import mochi.tool.bson.codecs.BsonDocumentCodec;
import mochi.tool.bson.codecs.BsonValueCodecProvider;
import mochi.tool.bson.codecs.Codec;
import mochi.tool.bson.codecs.DecoderContext;
import mochi.tool.bson.codecs.EncoderContext;
import mochi.tool.bson.codecs.RawBsonDocumentCodec;
import mochi.tool.bson.codecs.configuration.CodecRegistry;
import mochi.tool.bson.io.BasicOutputBuffer;
import mochi.tool.bson.io.ByteBufferBsonInput;
import mochi.tool.bson.json.JsonWriter;
import mochi.tool.bson.json.JsonWriterSettings;

import java.io.StringWriter;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static mochi.tool.bson.codecs.BsonValueCodecProvider.getClassForBsonType;
import static mochi.tool.bson.codecs.configuration.CodecRegistries.fromProviders;

/**
 * An immutable BSON document that is represented using only the raw bytes.
 *
 * @since 3.0
 */
public final class RawBsonDocument extends BsonDocument {
    private static final long serialVersionUID = 1L;

    private static final CodecRegistry REGISTRY = fromProviders(new BsonValueCodecProvider());

    private final byte[] bytes;

    /**
     * Constructs a new instance with the given byte array.  Note that it does not make a copy of the array, so do not modify it after
     * passing it to this constructor.
     *
     * @param bytes the bytes representing a BSON document.  Note that the byte array is NOT copied, so care must be taken not to modify it
     *              after passing it to this construction, unless of course that is your intention.
     */
    public RawBsonDocument(final byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes can not be null");
        }
        this.bytes = bytes;
    }

    /**
     * Construct a new instance from the given document and codec for the document type.
     *
     * @param document the document to transform
     * @param codec    the codec to facilitate the transformation
     * @param <T>      the BSON type that the codec encodes/decodes
     */
    public <T> RawBsonDocument(final T document, final Codec<T> codec) {
        BasicOutputBuffer buffer = new BasicOutputBuffer();
        BsonBinaryWriter writer = new BsonBinaryWriter(buffer);
        try {
            codec.encode(writer, document, EncoderContext.builder().build());
            this.bytes = buffer.toByteArray();
        } finally {
            writer.close();
        }
    }

    /**
     * Returns a {@code ByteBuf} that wraps the byte array, with the proper byte order.  Any changes made to the returned will be reflected
     * in the underlying byte array owned by this instance.
     *
     * @return a byte buffer that wraps the byte array owned by this instance.
     */
    public ByteBuf getByteBuffer() {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return new ByteBufNIO(buffer);
    }

    /**
     * Decode this into a document.
     *
     * @param codec the codec to facilitate the transformation
     * @param <T>   the BSON type that the codec encodes/decodes
     * @return the decoded document
     */
    public <T> T decode(final Codec<T> codec) {
        BsonBinaryReader reader = createReader();
        try {
            return codec.decode(reader, DecoderContext.builder().build());
        } finally {
            reader.close();
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
    }

    @Override
    public BsonValue put(final String key, final BsonValue value) {
        throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
    }

    @Override
    public BsonDocument append(final String key, final BsonValue value) {
        throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
    }

    @Override
    public void putAll(final Map<? extends String, ? extends BsonValue> m) {
        throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
    }

    @Override
    public BsonValue remove(final Object key) {
        throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
    }

    @Override
    public boolean isEmpty() {
        BsonBinaryReader bsonReader = createReader();
        try {
            bsonReader.readStartDocument();
            if (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                return false;
            }
            bsonReader.readEndDocument();
        } finally {
            bsonReader.close();
        }

        return true;
    }

    @Override
    public int size() {
        int size = 0;
        BsonBinaryReader bsonReader = createReader();
        try {
            bsonReader.readStartDocument();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                size++;
                bsonReader.readName();
                bsonReader.skipValue();
            }
            bsonReader.readEndDocument();
        } finally {
            bsonReader.close();
        }

        return size;
    }

    @Override
    public Set<Entry<String, BsonValue>> entrySet() {
        return toBsonDocument().entrySet();
    }

    @Override
    public Collection<BsonValue> values() {
        return toBsonDocument().values();
    }

    @Override
    public Set<String> keySet() {
        return toBsonDocument().keySet();
    }

    @Override
    public boolean containsKey(final Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key can not be null");
        }

        BsonBinaryReader bsonReader = createReader();
        try {
            bsonReader.readStartDocument();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                if (bsonReader.readName().equals(key)) {
                    return true;
                }
                bsonReader.skipValue();
            }
            bsonReader.readEndDocument();
        } finally {
            bsonReader.close();
        }

        return false;
    }

    @Override
    public boolean containsValue(final Object value) {
        BsonBinaryReader bsonReader = createReader();
        try {
            bsonReader.readStartDocument();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                bsonReader.skipName();
                if (deserializeBsonValue(bsonReader).equals(value)) {
                    return true;
                }
            }
            bsonReader.readEndDocument();
        } finally {
            bsonReader.close();
        }

        return false;
    }

    @Override
    public BsonValue get(final Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key can not be null");
        }

        BsonBinaryReader bsonReader = createReader();
        try {
            bsonReader.readStartDocument();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                if (bsonReader.readName().equals(key)) {
                    return deserializeBsonValue(bsonReader);
                }
                bsonReader.skipValue();
            }
            bsonReader.readEndDocument();
        } finally {
            bsonReader.close();
        }

        return null;
    }

    @Override
    public String toJson() {
        return toJson(new JsonWriterSettings());
    }

    @Override
    public String toJson(final JsonWriterSettings settings) {
        StringWriter writer = new StringWriter();
        new RawBsonDocumentCodec().encode(new JsonWriter(writer, settings), this, EncoderContext.builder().build());
        return writer.toString();
    }

    @Override
    public boolean equals(final Object o) {
        return toBsonDocument().equals(o);
    }

    @Override
    public int hashCode() {
        return toBsonDocument().hashCode();
    }

    @Override
    public BsonDocument clone() {
        return new RawBsonDocument(bytes.clone());
    }

    private BsonValue deserializeBsonValue(final BsonBinaryReader bsonReader) {
        return REGISTRY.get(getClassForBsonType(bsonReader.getCurrentBsonType())).decode(bsonReader, DecoderContext.builder().build());
    }

    private BsonBinaryReader createReader() {
        return new BsonBinaryReader(new ByteBufferBsonInput(getByteBuffer()));
    }

    private BsonDocument toBsonDocument() {
        BsonBinaryReader bsonReader = createReader();
        try {
            return new BsonDocumentCodec().decode(bsonReader, DecoderContext.builder().build());
        } finally {
            bsonReader.close();
        }
    }

    // see https://docs.oracle.com/javase/6/docs/platform/serialization/spec/output.html
    private Object writeReplace() {
        return new SerializationProxy(this.bytes);
    }

    // see https://docs.oracle.com/javase/6/docs/platform/serialization/spec/input.html
    private void readObject(final ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 1L;

        private final byte[] bytes;

        public SerializationProxy(final byte[] bytes) {
            this.bytes = bytes;
        }

        private Object readResolve() {
            return new RawBsonDocument(bytes);
        }
    }
}
