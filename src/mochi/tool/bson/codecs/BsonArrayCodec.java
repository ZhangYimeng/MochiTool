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

import mochi.tool.bson.BsonArray;
import mochi.tool.bson.BsonReader;
import mochi.tool.bson.BsonType;
import mochi.tool.bson.BsonValue;
import mochi.tool.bson.BsonWriter;
import mochi.tool.bson.codecs.configuration.CodecRegistry;

import java.util.ArrayList;
import java.util.List;

import static mochi.tool.bson.assertions.Assertions.notNull;

/**
 * A codec for BsonArray instances.
 *
 * @since 3.0
 */
public class BsonArrayCodec implements Codec<BsonArray> {
    private final CodecRegistry codecRegistry;

    /**
     * Construct an instance with the given registry
     *
     * @param codecRegistry the codec registry
     */
    public BsonArrayCodec(final CodecRegistry codecRegistry) {
        this.codecRegistry = notNull("codecRegistry", codecRegistry);
    }

    @Override
    public BsonArray decode(final BsonReader reader, final DecoderContext decoderContext) {
        reader.readStartArray();

        List<BsonValue> list = new ArrayList<BsonValue>();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            list.add(readValue(reader, decoderContext));
        }

        reader.readEndArray();

        return new BsonArray(list);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void encode(final BsonWriter writer, final BsonArray array, final EncoderContext encoderContext) {
        writer.writeStartArray();

        for (BsonValue value : array) {
            Codec codec = codecRegistry.get(value.getClass());
            encoderContext.encodeWithChildContext(codec, writer, value);
        }

        writer.writeEndArray();
    }

    @Override
    public Class<BsonArray> getEncoderClass() {
        return BsonArray.class;
    }

    /**
     * This method may be overridden to change the behavior of reading the current value from the given {@code BsonReader}.  It is required
     * that the value be fully consumed before returning.
     *
     * @param reader the read to read the value from
     * @param decoderContext the decoder context
     * @return the non-null value read from the reader
     */
    protected BsonValue readValue(final BsonReader reader, final DecoderContext decoderContext) {
        return codecRegistry.get(BsonValueCodecProvider.getClassForBsonType(reader.getCurrentBsonType())).decode(reader, decoderContext);
    }

}
