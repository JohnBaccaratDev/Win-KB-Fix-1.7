package com.johnbaccarat.win_kb_fix.core;

import com.sun.jna.IntegerType;

public class UINT extends IntegerType implements Comparable<UINT> {

    /** The Constant SIZE. */
    public static final int SIZE = 4;

    /**
     * Instantiates a new UINT.
     */
    public UINT() {
        this(0);
    }

    /**
     * Instantiates a new UINT.
     *
     * @param value
     *            the value
     */
    public UINT(long value) {
        super(SIZE, value, true);
    }

    @Override
    public int compareTo(UINT other) {
        return compare(this, other);
    }
}
