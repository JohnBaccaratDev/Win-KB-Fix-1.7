package com.johnbaccarat.win_kb_fix.core;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;

public class u32 {

    public static final u32 INSTANCE;
    static {
        INSTANCE = new u32();
        Native.register("user32");
    }

    public static Integer VK_LWIN = 0x5B;

    public static Integer VK_RWIN = 0x5C;

    // Sticky Keys
    public static UINT SPI_GETSTICKYKEYS = new UINT(0x003A);
    public static UINT SPI_SETSTICKYKEYS = new UINT(0x3B);
    public native boolean SystemParametersInfoW(UINT uiAction, UINT uiParam, Structure pvParam, UINT fWinIni) throws LastErrorException;

    // Keyboard Hook

    public native WinUser.HHOOK SetWindowsHookExW(int idHook, WinUser.HOOKPROC lpfn, WinDef.HINSTANCE hmod, WinDef.DWORD dwThreadId);
    public static int GWLP_HINSTANCE = -6;
    public native WinDef.HINSTANCE GetWindowLongPtrW(WinDef.HWND hWnd, int index);
    public static Integer SKF_HOTKEYACTIVE = 0x00000004;
    public final static WinDef.LRESULT reject = new WinDef.LRESULT(1);

    public static WinNT.HANDLE getMutex(){
        return k32.INSTANCE.CreateMutexW(null, false, interop.mutexName );
    }
}
