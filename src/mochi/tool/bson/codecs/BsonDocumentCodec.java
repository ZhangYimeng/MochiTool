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

package mochi.tool.bson.codecs;

import mochi.tool.bson.BsonDocument;
import mochi.tool.bson.BsonElement;
import mochi.tool.bson.BsonObjectId;
import mochi.tool.bson.BsonReader;
import mochi.tool.bson.BsonType;
import mochi.tool.bson.BsonValue;
import mochi.tool.bson.BsonWriter;
import mochi.tool.bson.codecs.configuration.CodecRegistry;
import mochi.tool.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mochi.tool.bson.codecs.configuration.CodecRegistries.fromProviders;

/**
 * A codec for BsonDocument instances.
 *
 * @since 3.0
 */
public class BsonDocumentCodec implements CollectibleCodec<BsonDocument> {
    private static final String ID_FIELD_NAME = "_id";
    private static final CodecRegistry DEFAULT_REGISTRY = fromProviders(new BsonValueCodecProvider());

    private final CodecRegistry codecRegistry;

    /**
     * Creates a new instance with a default codec registry that uses the {@link BsonValueCodecProvider}.
     */
    public BsonDocumentCodec() {
        codecRegistry = DEFAULT_REGISTRY;
    }

    /**
     * Creates a new instance initialised with the given codec registry.
     *
     * @param codecRegistry the {@code CodecRegistry} to use to look up the codecs for encoding and decoding to/from BSON
     */
    public BsonDocumentCodec(final CodecRegistry codecRegistry) {
        if (codecRegistry == null) {
            throw new IllegalArgumentException("Codec registry can not be null");
        }
        this.codecRegistry = codecRegistry;
    }

    /**
     * Gets the {@code CodecRegistry} for this {@code Codec}.
     *
     * @return the registry
     */
    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    @Override
    public BsonDocument decode(final BsonReader reader, final DecoderContext decoderContext) {
        List<BsonElement> keyValuePairs = new ArrayList<BsonElement>();

        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();
            keyValuePairs.add(new BsonElement(fieldName, readValue(reader, decoderContext)));
        }

        reader.readEndDocument();

        return new BsonDocument(keyValuePairs);
    }

    /**
     * This method may be overridden to change the behavior of reading the current value from the given {@code BsonReader}.  It is required
     * that the value be fully consumed before returning.
     *
     * @param reader         the read to read the value from
     * @param decoderContext the context
     * @return the non-null value read from the reader
     */
    protected BsonValue readValue(final BsonReader reader, final DecoderContext decoderContext) {
        return codecRegistry.get(BsonValueCodecProvider.getClassForBsonType(reader.getCurrentBsonType())).decode(reader, decoderContext);
    }

    @Override
    public void encode(final BsonWriter writer, final BsonDocument value, final EncoderContext encoderContext) {
        writer.writeStartDocument();

        beforeFields(writer, encoderContext, value);
        for (Map.Entry<String, BsonValue> entry : value.entrySet()) {
            if (skipField(encoderContext, entry.getKey())) {
                continue;
            }

            writer.writeName(entry.getKey());
            writeValue(writer, encoderContext, entry.getValue());
        }

        writer.writeEndDocument();
    }

    private void beforeFields(final BsonWriter bsonWriter, final EncoderContext encoderContext, final BsonDocument value) {
        if (encoderContext.isEncodingCollectibleDocument() && value.containsKey(ID_FIELD_NAME)) {
            bsonWriter.writeName(ID_FIELD_NAME);
            writeValue(bsonWriter, encoderContext, value.get(ID_FIELD_NAME));
        }
    }

    private boolean skipField(final EncoderContext encoderContext, final String key) {
        return encoderContext.isEncodingCollectibleDocument() && key.equals(ID_FIELD_NAME);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private void writeValue(final BsonWriter writer, final EncoderContext encoderContext, final BsonValue value) {
        Codec codec = codecRegistry.get(value.getClass());
        encoderContext.encodeWithChildContext(codec, writer, value);
    }

    @Override
    public Class<BsonDocument> getEncoderClass() {
        return BsonDocument.class;
    }

    @Override
    public BsonDocument generateIdIfAbsentFromDocument(final BsonDocument document) {
        if (!documentHasId(document)) {
            document.put(ID_FIELD_NAME, new BsonObjectId(new ObjectId()));
        }
        return document;
    }

    @Override
    public boolean documentHasId(final BsonDocument document) {
        return document.containsKey(ID_FIELD_NAME);
    }

    @Override
    public BsonValue getDocumentId(final BsonDocument document) {
        return document.get(ID_FIELD_NAME);
    }
}
