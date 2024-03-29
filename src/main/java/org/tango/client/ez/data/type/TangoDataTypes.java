// +======================================================================
//   $Source$
//
//   Project:   ezTangORB
//
//   Description:  java source code for the simplified TangORB API.
//
//   $Author: Igor Khokhriakov <igor.khokhriakov@hzg.de> $
//
//   Copyright (C) :      2014
//                        Helmholtz-Zentrum Geesthacht
//                        Max-Planck-Strasse, 1, Geesthacht 21502
//                        GERMANY
//                        http://hzg.de
//
//   This file is part of Tango.
//
//   Tango is free software: you can redistribute it and/or modify
//   it under the terms of the GNU Lesser General Public License as published by
//   the Free Software Foundation, either version 3 of the License, or
//   (at your option) any later version.
//
//   Tango is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU Lesser General Public License for more details.
//
//   You should have received a copy of the GNU Lesser General Public License
//   along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
//  $Revision: 25721 $
//
// -======================================================================

package org.tango.client.ez.data.type;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 05.06.12
 */
public final class TangoDataTypes {
    private static final TangoDataType<?>[] TYPES = new TangoDataType<?>[30];
    private static final Map<Class<?>, TangoDataType<?>> CLASSES = new IdentityHashMap<Class<?>, TangoDataType<?>>();
    private static final Map<String, TangoDataType<?>> STRINGS = new HashMap<String, TangoDataType<?>>();
    private static final TangoDataType<?>[] imageTypes = new TangoDataType<?>[30];

    static {
        for (TangoDataType<?> type : ScalarTangoDataTypes.values()) {
            TangoDataTypes.TYPES[type.getAlias()] = type;
        }
        for (TangoDataType<?> type : SpectrumTangoDataTypes.values()) {
            TangoDataTypes.TYPES[type.getAlias()] = type;
        }
    }

    static {
        CLASSES.put(String.class, ScalarTangoDataTypes.STRING);
        CLASSES.put(Boolean.class, ScalarTangoDataTypes.BOOLEAN);
        CLASSES.put(Short.class, ScalarTangoDataTypes.SHORT);
        CLASSES.put(Character.class, ScalarTangoDataTypes.U_CHAR);
        CLASSES.put(Integer.class, ScalarTangoDataTypes.INT);
        CLASSES.put(Long.class, ScalarTangoDataTypes.LONG);
        CLASSES.put(Float.class, ScalarTangoDataTypes.FLOAT);
        CLASSES.put(Double.class, ScalarTangoDataTypes.DOUBLE);
        CLASSES.put(boolean.class, ScalarTangoDataTypes.BOOLEAN);
        CLASSES.put(short.class, ScalarTangoDataTypes.SHORT);
        CLASSES.put(char.class, ScalarTangoDataTypes.U_CHAR);
        CLASSES.put(int.class, ScalarTangoDataTypes.INT);
        CLASSES.put(long.class, ScalarTangoDataTypes.LONG);
        CLASSES.put(float.class, ScalarTangoDataTypes.FLOAT);
        CLASSES.put(double.class, ScalarTangoDataTypes.DOUBLE);
        CLASSES.put(String[].class, SpectrumTangoDataTypes.STRING_ARR);
        CLASSES.put(short[].class, SpectrumTangoDataTypes.SHORT_ARR);
        CLASSES.put(char[].class, SpectrumTangoDataTypes.CHAR_ARR);
        CLASSES.put(int[].class, SpectrumTangoDataTypes.INT_ARR);
        CLASSES.put(long[].class, SpectrumTangoDataTypes.LONG_ARR);
        CLASSES.put(float[].class, SpectrumTangoDataTypes.FLOAT_ARR);
        CLASSES.put(double[].class, SpectrumTangoDataTypes.DOUBLE_ARR);
        CLASSES.put(boolean[].class, SpectrumTangoDataTypes.BOOL_ARR);
        //TODO others
    }

    static {
        STRINGS.put(ScalarTangoDataTypes.DEV_STRING, ScalarTangoDataTypes.STRING);
        STRINGS.put(ScalarTangoDataTypes.DEV_DOUBLE, ScalarTangoDataTypes.DOUBLE);
        STRINGS.put(ScalarTangoDataTypes.DEV_FLOAT, ScalarTangoDataTypes.FLOAT);
        STRINGS.put(ScalarTangoDataTypes.DEV_LONG64, ScalarTangoDataTypes.LONG);
        STRINGS.put(ScalarTangoDataTypes.DEV_LONG, ScalarTangoDataTypes.INT);
        STRINGS.put(ScalarTangoDataTypes.DEV_SHORT, ScalarTangoDataTypes.SHORT);
        STRINGS.put(ScalarTangoDataTypes.DEV_USHORT, ScalarTangoDataTypes.U_SHORT);
        STRINGS.put(ScalarTangoDataTypes.DEV_ULONG, ScalarTangoDataTypes.U_INT);
        STRINGS.put(ScalarTangoDataTypes.DEV_UCHAR, ScalarTangoDataTypes.U_CHAR);
        STRINGS.put(SpectrumTangoDataTypes.DEV_VAR_STRING_ARR, SpectrumTangoDataTypes.STRING_ARR);
        STRINGS.put(SpectrumTangoDataTypes.DEV_VAR_DOUBLE_ARR, SpectrumTangoDataTypes.DOUBLE_ARR);
        STRINGS.put(SpectrumTangoDataTypes.DEV_VAR_FLOAT_ARR, SpectrumTangoDataTypes.FLOAT_ARR);
        STRINGS.put(SpectrumTangoDataTypes.DEV_VAR_LONG64_ARR, SpectrumTangoDataTypes.LONG_ARR);
        STRINGS.put(SpectrumTangoDataTypes.DEV_VAR_LONG_ARR, SpectrumTangoDataTypes.INT_ARR);
        STRINGS.put(SpectrumTangoDataTypes.DEV_VAR_SHORT_ARR, SpectrumTangoDataTypes.SHORT_ARR);
        STRINGS.put(SpectrumTangoDataTypes.DEV_VAR_USHORT_ARR, SpectrumTangoDataTypes.USHORT_ARR);
        STRINGS.put(SpectrumTangoDataTypes.DEV_VAR_ULONG_ARR, SpectrumTangoDataTypes.ULONG64_ARR);
        STRINGS.put(SpectrumTangoDataTypes.DEV_VAR_BOOLEAN_ARR, SpectrumTangoDataTypes.BOOL_ARR);
        //TODO etc
    }

    static {
        for (TangoDataType<?> type : ImageTangoDataTypes.values()) {
            imageTypes[type.getAlias()] = type;
        }
    }

    private TangoDataTypes() {
    }

    public static <T> TangoDataType<T> forTangoDevDataType(int tangoDevDataType) throws UnknownTangoDataType {
        if (tangoDevDataType == SpectrumTangoDataTypes.TANGO_DEV_BOOL_ARR)
            return (TangoDataType<T>) SpectrumTangoDataTypes.BOOL_ARR;
        TangoDataType<T> type = (TangoDataType<T>) TYPES[tangoDevDataType];
        if (type == null)
            throw new UnknownTangoDataType("Can not find TangoDataType for type id = " + tangoDevDataType);
        return type;
    }

    public static <T> TangoDataType<T> forClass(Class<T> clazz) throws UnknownTangoDataType {
        TangoDataType<T> type = (TangoDataType<T>) CLASSES.get(clazz);
        if (type == null)
            throw new UnknownTangoDataType("Can not find TangoDataType for type class = " + clazz.getSimpleName());
        return type;
    }

    public static <T> TangoDataType<T> forString(String devType) throws UnknownTangoDataType {
        TangoDataType<T> type = (TangoDataType<T>) STRINGS.get(devType);
        if (type == null) throw new UnknownTangoDataType("Can not find TangoDataType for type alias = " + devType);
        return type;
    }

    public static <T> TangoDataType<T> imageTypeForDevDataType(int devDataType) throws UnknownTangoDataType {
        TangoDataType<T> type = (TangoDataType<T>) imageTypes[devDataType];
        if (type == null) throw new UnknownTangoDataType("Can not find TangoDataType for type id = " + devDataType);
        return type;
    }
}
