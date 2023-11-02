package com.johnbaccarat.win_kb_fix.wrappers;


import com.johnbaccarat.win_kb_fix.Constants;
import com.johnbaccarat.win_kb_fix.core.McWrapper;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class mc implements McWrapper {

    Minecraft mc;

    Class screenWithKeybindings;

    ByteBuffer keyboardReadBuffer;

    Field keyboardReadBufferField;

    Boolean DisableWinKeyOverride = false;

    public mc(Minecraft m){
        mc = m;

        try{
            screenWithKeybindings = Class.forName("net.minecraft.client.gui.GuiControls");
        }catch (Exception e){
            Constants.LOG.error("Could not find class of GUI with controls.");
            screenWithKeybindings = null;
        }

        try{
            Class c = Class.forName("org.lwjgl.input.Keyboard");
            Field f = c.getDeclaredField("readBuffer");
            f.setAccessible(true);
            keyboardReadBuffer = (ByteBuffer) f.get(null);
        }
        catch (Exception e){
            keyboardReadBuffer = null;
        }
    }

    byte down = 0x1;
    byte up = 0x0;

    public Boolean putInKeyboardBuffer(int key, byte state){
        if(keyboardReadBuffer.hasRemaining()){
            keyboardReadBuffer.position(keyboardReadBuffer.limit());
        }

        if(keyboardReadBuffer.remaining() < (18)){
            int newLimit = keyboardReadBuffer.limit() - keyboardReadBuffer.remaining() + (18);
            if(newLimit > keyboardReadBuffer.capacity()){
                int origPos = keyboardReadBuffer.position();
                keyboardReadBuffer.position(0);
                ByteBuffer nb = ByteBuffer.allocate(newLimit);

                while(keyboardReadBuffer.hasRemaining()){
                    nb.put(keyboardReadBuffer.get());
                }
                nb.position(origPos);

                keyboardReadBuffer = nb;
                try {
                    keyboardReadBufferField.set(null, nb);
                } catch (IllegalAccessException e) {
                    Constants.LOG.error("Could not change readBuffer of keyboard. Windows key will not be usable");
                    DisableWinKeyOverride = true;
                    return false;
                }
            }

            keyboardReadBuffer.limit(newLimit);
        }

        keyboardReadBuffer.putInt(key); // key
        keyboardReadBuffer.put(state); // state - up/down
        keyboardReadBuffer.putInt(0); // character
        keyboardReadBuffer.putLong(0); // nanos
        keyboardReadBuffer.put(up); // repeat

        keyboardReadBuffer.rewind();

        return true;
    }

    @Override
    public Boolean lWinUp() {
        return putInKeyboardBuffer(219, up);
    }

    @Override
    public Boolean lWinDown() {
        return putInKeyboardBuffer(219, down);
    }

    @Override
    public Boolean rWinUp() {
        return putInKeyboardBuffer(220, up);
    }

    @Override
    public Boolean rWinDown() {
        return putInKeyboardBuffer(220, down);
    }

    @Override
    public boolean redirectWinKey() {
        if (Display.isActive()){
            if(mc.currentScreen == null){
                return true;
            }else{
                return screenWithKeybindings != null ? screenWithKeybindings.isInstance(mc.currentScreen) : false;
            }
        }
        return false;
    }

    @Override
    public long getLGFWWindowPointer() {
        try{
            Class c = Class.forName("org.lwjgl.opengl.Display");
            Field f = c.getDeclaredField("display_impl");
            f.setAccessible(true);
            Object displayImplentation = f.get(null);
            c = Class.forName("org.lwjgl.opengl.WindowsDisplay");
            keyboardReadBufferField = c.getDeclaredField("hwnd");
            keyboardReadBufferField.setAccessible(true);
            return (long) keyboardReadBufferField.get(displayImplentation);
        }catch (Exception e2){
            Constants.LOG.error("The instance of the main Window could not be obtained.");
        }

        throw new RuntimeException("Could not get");
    }


    @Override
    public void error(String s) {
        Constants.LOG.error(s);
    }

    @Override
    public void warning(String s) {
        Constants.LOG.warn(s);
    }

    @Override
    public void info(String s) {
        Constants.LOG.info(s);
    }
}
