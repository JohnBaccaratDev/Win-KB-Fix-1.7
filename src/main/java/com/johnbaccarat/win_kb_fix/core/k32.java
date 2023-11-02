package com.johnbaccarat.win_kb_fix.core;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT;

public class k32 {

    public static final k32 INSTANCE;

    static {
        INSTANCE = new k32();
        Native.register("kernel32");
    }

    public native WinNT.HANDLE CreateMutexW(WinBase.SECURITY_ATTRIBUTES lpMutexAttributes,
                                            boolean bInitialOwner,
                                            String lpName);

    public native boolean ReleaseMutex(WinNT.HANDLE handle);

}
