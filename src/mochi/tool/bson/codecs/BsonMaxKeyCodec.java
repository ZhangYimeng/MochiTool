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

import mochi.tool.bson.BsonMaxKey;
import mochi.tool.bson.BsonReader;
import mochi.tool.bson.BsonWriter;

/**
 * A codec for {@code BsonMaxKey} instances.
 *
 * @since 3.0
 */
public class BsonMaxKeyCodec implements Codec<BsonMaxKey> {
    @Override
    public void encode(final BsonWriter writer, final BsonMaxKey value, final EncoderContext encoderContext) {
        writer.writeMaxKey();
    }

    @Override
    public BsonMaxKey decode(final BsonReader reader, final DecoderContext decoderContext) {
        reader.readMaxKey();
        return new BsonMaxKey();
    }

    @Override
    public Class<BsonMaxKey> getEncoderClass() {
        return BsonMaxKey.class;
    }
}
