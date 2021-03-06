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

import mochi.tool.bson.BsonReader;
import mochi.tool.bson.BsonWriter;

/**
 * Encodes and decodes {@code Byte} objects.
 *
 * @since 3.0
 */
public class ByteCodec implements Codec<Byte> {
    @Override
    public void encode(final BsonWriter writer, final Byte value, final EncoderContext encoderContext) {
        writer.writeInt32(value);
    }

    @Override
    public Byte decode(final BsonReader reader, final DecoderContext decoderContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<Byte> getEncoderClass() {
        return Byte.class;
    }
}
