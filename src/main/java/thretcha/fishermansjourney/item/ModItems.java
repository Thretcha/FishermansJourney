package thretcha.fishermansjourney.item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thretcha.fishermansjourney.FishermansJourneyMod;

public class ModItems {
    @GameRegistry.ObjectHolder(FishermansJourneyMod.MODID+":fishingrodbase")
    public static ItemBaseFishingRod fishingrodbase;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        fishingrodbase.initModel();
    }
}
