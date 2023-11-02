package com.johnbaccarat.win_kb_fix.core;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class StickyKeysStructure extends Structure {
    public UINT cbSize = new UINT(UINT.SIZE*2);
    public UINT dwFlags;

    @Override
    protected List getFieldOrder() {
        return Arrays.asList("cbSize", "dwFlags");
    }
}
