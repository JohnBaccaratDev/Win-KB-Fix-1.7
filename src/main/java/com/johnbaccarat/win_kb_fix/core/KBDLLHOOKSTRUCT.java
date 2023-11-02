package com.johnbaccarat.win_kb_fix.core;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.BaseTSD;

import java.util.Arrays;
import java.util.List;

public class KBDLLHOOKSTRUCT extends Structure {
    public int vkCode;
    public int scanCode;
    public int flags;
    public int time;
    public BaseTSD.ULONG_PTR dwExtraInfo;
    @Override
    protected List getFieldOrder() {
        return Arrays.asList("vkCode", "scanCode", "flags", "time", "dwExtraInfo");
    }
}