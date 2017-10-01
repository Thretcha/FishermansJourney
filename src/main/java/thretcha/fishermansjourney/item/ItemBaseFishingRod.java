package thretcha.fishermansjourney.item;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thretcha.fishermansjourney.FishermansJourneyMod;
import thretcha.fishermansjourney.entity.EntityBaseFishingHook;

public class ItemBaseFishingRod extends ItemFishingRod{

    public ItemBaseFishingRod() {
        super();
        this.setMaxDamage(0);
        this.setUnlocalizedName(FishermansJourneyMod.MODID+".fishingrodbase");
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setRegistryName("fishingrodbase");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this,0,new ModelResourceLocation(this.getRegistryName(),"inventory"));
        /*
        ModelResourceLocation uncast = new ModelResourceLocation(this.getRegistryName(),"inventory");
        ModelResourceLocation cast = new ModelResourceLocation(this.getRegistryName()+"_cast","inventory");
        ModelBakery.registerItemVariants(this, uncast, cast);
        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return cast;
            }
        });*/
    }

    @Override
    public int getItemEnchantability()
    {
        return 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.fishEntity != null)
        {
            int i = playerIn.fishEntity.handleHookRetraction();
            itemstack.damageItem(i, playerIn);
            playerIn.swingArm(handIn);
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        }
        else
        {
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!worldIn.isRemote)
            {
                EntityBaseFishingHook entityfishhook = new EntityBaseFishingHook(worldIn, playerIn);
                //int j = EnchantmentHelper.getFishingSpeedBonus(itemstack);

                //if (j > 0)
               // {
                    entityfishhook.setLureSpeed(0);
                //}

                //int k = EnchantmentHelper.getFishingLuckBonus(itemstack);

               // if (k > 0)
               // {
                    entityfishhook.setLuck(0);
                //}

                worldIn.spawnEntity(entityfishhook);
            }

            playerIn.swingArm(handIn);
            playerIn.addStat(StatList.getObjectUseStats(this));
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
}
