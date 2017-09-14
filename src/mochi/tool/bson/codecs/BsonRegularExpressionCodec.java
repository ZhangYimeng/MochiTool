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
import mochi.tool.bson.BsonRegularExpression;
import mochi.tool.bson.BsonWriter;

/**
 * A codec for BSON regular expressions.
 *
 * @since 3.0
 */
public class BsonRegularExpressionCodec implements Codec<BsonRegularExpression> {
    @Override
    public BsonRegularExpression decode(final BsonReader reader, final DecoderContext decoderContext) {
        return reader.readRegularExpression();
    }

    @Override
    public void encode(final BsonWriter writer, final BsonRegularExpression value, final EncoderContext encoderContext) {
        writer.writeRegularExpression(value);
    }

    @Override
    public Class<BsonRegularExpression> getEncoderClass() {
        return BsonRegularExpression.class;
    }
}
