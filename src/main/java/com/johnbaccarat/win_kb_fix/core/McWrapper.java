package com.johnbaccarat.win_kb_fix.core;

public interface McWrapper {
    public Boolean lWinUp();
    public Boolean lWinDown();
    public Boolean rWinUp();
    public Boolean rWinDown();
    public boolean redirectWinKey();
    public long getLGFWWindowPointer();

    public void error(String s);
    public void warning(String s);
    public void info(String s);
}
