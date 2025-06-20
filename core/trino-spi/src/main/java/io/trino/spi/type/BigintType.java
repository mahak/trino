/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.spi.type;

import io.trino.spi.block.Block;

import java.util.Optional;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class BigintType
        extends AbstractLongType
{
    public static final BigintType BIGINT = new BigintType();

    private BigintType()
    {
        super(new TypeSignature(StandardTypes.BIGINT));
    }

    @Override
    public Object getObjectValue(Block block, int position)
    {
        if (block.isNull(position)) {
            return null;
        }

        return getLong(block, position);
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object other)
    {
        return other == BIGINT;
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }

    @Override
    public Optional<Range> getRange()
    {
        return Optional.of(new Range(Long.MIN_VALUE, Long.MAX_VALUE));
    }

    @Override
    public Optional<Object> getPreviousValue(Object object)
    {
        long value = (long) object;
        if (value == Long.MIN_VALUE) {
            return Optional.empty();
        }
        return Optional.of(value - 1);
    }

    @Override
    public Optional<Object> getNextValue(Object object)
    {
        long value = (long) object;
        if (value == Long.MAX_VALUE) {
            return Optional.empty();
        }
        return Optional.of(value + 1);
    }

    @Override
    public Optional<Stream<?>> getDiscreteValues(Range range)
    {
        return Optional.of(LongStream.rangeClosed((long) range.getMin(), (long) range.getMax()).boxed());
    }
}
