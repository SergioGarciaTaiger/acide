package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.sql.Timestamp;

/**
 * Converts a java.sql.Timestamp to text.
 *
 * @author Joe Walnes
 */

@SuppressWarnings("rawtypes")
public class SqlTimestampConverter extends AbstractSingleValueConverter {

    public boolean canConvert(Class type) {
        return type.equals(Timestamp.class);
    }

    public Object fromString(String str) {
        return Timestamp.valueOf(str);
    }

}
