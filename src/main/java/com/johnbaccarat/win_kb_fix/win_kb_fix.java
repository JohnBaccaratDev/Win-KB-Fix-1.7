package com.johnbaccarat.win_kb_fix;

import com.johnbaccarat.win_kb_fix.core.interop;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.Mod;
import org.apache.commons.lang3.SystemUtils;
import com.johnbaccarat.win_kb_fix.wrappers.mc;
import org.apache.logging.log4j.LogManager;

@Mod(modid = Constants.MOD_ID, version = Constants.VERSION, acceptedMinecraftVersions = "*")
public class win_kb_fix
{
    private static Boolean Inited = false;

    @Mod.EventHandler
    public void post(FMLLoadCompleteEvent event){

        Constants.LOG = LogManager.getLogger(Constants.MOD_ID);

        if(!Inited){
            if(SystemUtils.IS_OS_WINDOWS){
                try{
                    interop.init(new mc(Minecraft.getMinecraft()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                if(SystemUtils.IS_OS_MAC){
                    Constants.LOG.info("Satania_laughing.gif");
                }
            }
            Inited = true;
        }
    }
}
