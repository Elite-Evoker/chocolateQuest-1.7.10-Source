package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.client.model.ModelArmorWitchHat;
import com.chocolate.chocolateQuest.client.model.ModelArmorColored;
import com.chocolate.chocolateQuest.client.model.ModelArmorSlime;
import com.chocolate.chocolateQuest.client.model.ModelArmorMinotaur;
import com.chocolate.chocolateQuest.client.model.ModelArmorDragon;
import com.chocolate.chocolateQuest.client.model.ModelArmorTurtle;
import com.chocolate.chocolateQuest.client.model.ModelArmorMageRobe;
import com.chocolate.chocolateQuest.client.model.ModelArmorHeavyPlate;
import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import com.chocolate.chocolateQuest.client.blockRender.RenderBlockEditor;
import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import com.chocolate.chocolateQuest.client.blockRender.RenderBlockBanner;
import com.chocolate.chocolateQuest.block.BlockArmorStandTileEntity;
import com.chocolate.chocolateQuest.client.blockRender.RenderBlockArmorStand;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import com.chocolate.chocolateQuest.block.BlockAltarTileEntity;
import com.chocolate.chocolateQuest.client.blockRender.RenderBlockTable;
import com.chocolate.chocolateQuest.entity.EntityCursor;
import com.chocolate.chocolateQuest.entity.projectile.EntityProjectileBeam;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.EntityBaiter;
import com.chocolate.chocolateQuest.client.model.ModelSkeletonSummoned;
import com.chocolate.chocolateQuest.entity.EntitySummonedUndead;
import com.chocolate.chocolateQuest.entity.EntityReferee;
import com.chocolate.chocolateQuest.client.model.ModelTurtle;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtle;
import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import com.chocolate.chocolateQuest.client.model.ModelGiantZombie;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantZombie;
import com.chocolate.chocolateQuest.client.model.ModelDragonQuadruped;
import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import com.chocolate.chocolateQuest.client.model.ModelSpiderBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySpiderBoss;
import com.chocolate.chocolateQuest.client.model.ModelBull;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import com.chocolate.chocolateQuest.client.model.ModelSlimeBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import com.chocolate.chocolateQuest.client.model.ModelGiantBoxer;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantBoxer;
import com.chocolate.chocolateQuest.client.model.ModelSpecterBoss;
import com.chocolate.chocolateQuest.entity.mob.EntitySpecterBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityPirateBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityWalkerBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPigZombie;
import com.chocolate.chocolateQuest.client.model.ModelMinotaur;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMinotaur;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPirate;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHumanGremlin;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanGremlin;
import com.chocolate.chocolateQuest.client.model.ModelHuman;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanWalker;
import com.chocolate.chocolateQuest.client.model.ModelSpecter;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSpecter;
import com.chocolate.chocolateQuest.client.model.ModelHumanZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSkeleton;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderNPC;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.client.model.ModelGolemMechaHeavy;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMechaHeavy;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHumanMecha;
import com.chocolate.chocolateQuest.client.model.ModelGolemMecha;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import net.minecraft.client.renderer.entity.Render;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHuman;
import net.minecraft.util.ResourceLocation;
import com.chocolate.chocolateQuest.client.model.ModelHumanSkeleton;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import com.chocolate.chocolateQuest.client.blockRender.RenderBlockTableItem;
import cpw.mods.fml.client.registry.RenderingRegistry;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelBubbleCannon;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelCannon;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelMachineGun;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelRailGun;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelFlameThrower;
import net.minecraft.client.model.ModelBase;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemGolemWeapon;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGun;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemPistol;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemShield;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBanner;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemHookSword;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemStaff;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemSwordDefensive;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemTwoHandedSword;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemSpearFire;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemSpear;
import com.chocolate.chocolateQuest.gui.GuiInGameStats;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.model.ModelBiped;
import com.chocolate.chocolateQuest.client.model.ModelArmor;
import com.chocolate.chocolateQuest.CommonProxy;

public class ClientProxy extends CommonProxy
{
    public static ModelArmor defaultArmor;
    public static ModelArmor kingArmor;
    public static ModelArmor heavyArmor;
    public static ModelArmor mageArmor;
    public static ModelArmor turtleArmorModel;
    public static ModelArmor turtleHelmetModel;
    public static ModelBiped dragonHead;
    public static ModelArmor bullHead;
    public static ModelArmor[] slimeArmor;
    public static ModelArmor[] coloredArmor;
    public static ModelArmor witchHat;
    public static int tableRenderID;
    
    @Override
    public void register() {
        super.register();
        this.registerRenderInformation();
        KeyBindings.init();
        FMLCommonHandler.instance().bus().register((Object)new KeyInputHandler());
    }
    
    @Override
    public void postInit() {
        super.postInit();
        MinecraftForge.EVENT_BUS.register((Object)new GuiInGameStats(Minecraft.getMinecraft()));
    }
    
    @Override
    public void registerRenderInformation() {
        final RenderItemSpear ris = new RenderItemSpear();
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.rustedSpear, (IItemRenderer)ris);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.ironSpear, (IItemRenderer)ris);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.diamondSpear, (IItemRenderer)ris);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.fireSpear, (IItemRenderer)new RenderItemSpearFire());
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.spearGun, (IItemRenderer)new RenderItemSpearFire());
        final RenderItemTwoHandedSword riths = new RenderItemTwoHandedSword();
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.ironBigsword, (IItemRenderer)riths);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.diamondBigsword, (IItemRenderer)riths);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.monkingSword, (IItemRenderer)riths);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.bigSwordBull, (IItemRenderer)riths);
        final RenderItemSwordDefensive risd = new RenderItemSwordDefensive();
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.rustedSwordAndShied, (IItemRenderer)risd);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.endSword, (IItemRenderer)risd);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.swordTurtle, (IItemRenderer)risd);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.swordSpider, (IItemRenderer)risd);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.swordSunLight, (IItemRenderer)risd);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.swordMoonLight, (IItemRenderer)risd);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.monkingSwordAndShield, (IItemRenderer)risd);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.ironSwordAndShield, (IItemRenderer)risd);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.diamondSwordAndShield, (IItemRenderer)risd);
        final RenderItemStaff risf = new RenderItemStaff();
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffHeal, (IItemRenderer)risf);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffPhysic, (IItemRenderer)risf);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffMagic, (IItemRenderer)risf);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffBlast, (IItemRenderer)risf);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffFire, (IItemRenderer)risf);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffLight, (IItemRenderer)risf);
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.hookSword, (IItemRenderer)new RenderItemHookSword());
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.banner, (IItemRenderer)new RenderItemBanner());
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.shield, (IItemRenderer)new RenderItemShield());
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.revolver, (IItemRenderer)new RenderItemPistol());
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemGun, (IItemRenderer)new RenderItemGolemWeapon(new ModelGun()));
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemFlameThrower, (IItemRenderer)new RenderItemGolemWeapon(new ModelFlameThrower()));
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemRifleGun, (IItemRenderer)new RenderItemGolemWeapon(new ModelRailGun()));
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemMachineGun, (IItemRenderer)new RenderItemGolemWeapon(new ModelMachineGun()));
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemCannon, (IItemRenderer)new RenderItemGolemWeapon(new ModelCannon()));
        MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemBubbleCannon, (IItemRenderer)new RenderItemGolemWeapon(new ModelBubbleCannon()));
        RenderingRegistry.registerBlockHandler(ClientProxy.tableRenderID = RenderingRegistry.getNextAvailableRenderId(), (ISimpleBlockRenderingHandler)new RenderBlockTableItem());
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHumanBase.class, (Render)new RenderHuman(new ModelHumanSkeleton(), 0.5f, new ResourceLocation("chocolatequest:textures/entity/biped/woodMan.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGolemMecha.class, (Render)new RenderHumanMecha(new ModelGolemMecha(), 0.5f, new ResourceLocation("chocolatequest:textures/entity/golemMecha.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGolemMechaHeavy.class, (Render)new RenderHumanMecha(new ModelGolemMechaHeavy(), 0.5f, new ResourceLocation("chocolatequest:textures/entity/golemMechaElite.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHumanNPC.class, (Render)new RenderNPC());
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHumanSkeleton.class, (Render)new RenderHuman(new ModelHumanSkeleton(), 0.5f, new ResourceLocation("textures/entity/skeleton/skeleton.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHumanZombie.class, (Render)new RenderHuman(new ModelHumanZombie(), 0.5f, new ResourceLocation("textures/entity/zombie/zombie.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHumanSpecter.class, (Render)new RenderHuman(new ModelSpecter(), 0.5f, new ResourceLocation("chocolatequest:textures/entity/biped/specter.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHumanWalker.class, (Render)new RenderHuman(new ModelHuman(), 0.5f, new ResourceLocation("chocolatequest:textures/entity/biped/shadow.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHumanGremlin.class, (Render)new RenderHumanGremlin(0.5f, new ResourceLocation("chocolatequest:textures/entity/biped/gremlin.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHumanPirate.class, (Render)new RenderHuman(new ModelHuman(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHumanMinotaur.class, (Render)new RenderHuman(new ModelMinotaur(), 0.5f, new ResourceLocation("chocolatequest:textures/entity/biped/minotaurzombie.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHumanPigZombie.class, (Render)new RenderHuman(new ModelHumanZombie(), 0.5f, new ResourceLocation("textures/entity/zombie_pigman.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityWalkerBoss.class, (Render)new RenderHuman(new ModelHuman(), 0.5f, new ResourceLocation("chocolatequest:textures/entity/biped/shadow.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityPirateBoss.class, (Render)new RenderHuman(new ModelHuman(), 0.5f, new ResourceLocation("chocolatequest:textures/entity/biped/pirateBoss.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntitySpecterBoss.class, (Render)new RenderHuman(new ModelSpecterBoss(), 0.5f, new ResourceLocation("chocolatequest:textures/entity/biped/specterBoss.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGiantBoxer.class, (Render)new RenderBossBiped(new ModelGiantBoxer(), 1.0f, new ResourceLocation("chocolatequest:textures/entity/biped/monkey.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntitySlimeBoss.class, (Render)new RenderLivingBossModel((ModelBase)new ModelSlimeBoss(), 1.0f, new ResourceLocation("chocolatequest:textures/entity/slimeBoss.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBull.class, (Render)new RenderLivingBossModel((ModelBase)new ModelBull(), 1.0f, new ResourceLocation("chocolatequest:textures/entity/icebull.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntitySpiderBoss.class, (Render)new RenderLivingBossModel(new ModelSpiderBoss(), 1.0f, new ResourceLocation("chocolatequest:textures/entity/spiderBoss.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityWyvern.class, (Render)new RenderLivingModelFly(new ModelDragonQuadruped(), 1.0f, new ResourceLocation("chocolatequest:textures/entity/dragonbd.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGiantZombie.class, (Render)new RenderBossBiped(new ModelGiantZombie(), 1.0f, new ResourceLocation("textures/entity/zombie/zombie.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityPart.class, (Render)new RenderInvisiblePart());
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTurtle.class, (Render)new RenderLivingBossModel(new ModelTurtle(), 1.0f, new ResourceLocation("chocolatequest:textures/entity/turtle.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityReferee.class, (Render)new RenderBipedCQ(new ModelBiped(), 0.5f, new ResourceLocation("chocolatequest:textures/entity/biped/referee.png")));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntitySummonedUndead.class, (Render)new RenderSummonedUndead(new ModelSkeletonSummoned(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBaiter.class, (Render)new RenderBaiter(new ModelBiped(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBaseBall.class, (Render)new RenderBallProjectile(0.5f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityHookShoot.class, (Render)new RenderHookShoot(0.0f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityProjectileBeam.class, (Render)new RenderBeam(0.0f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCursor.class, (Render)new RenderBanner(0.5f));
    }
    
    @Override
    public void registerTileEntities() {
        final RenderBlockTable rbaltar = new RenderBlockTable();
        ClientRegistry.registerTileEntity((Class)BlockAltarTileEntity.class, "table", (TileEntitySpecialRenderer)rbaltar);
        final RenderBlockArmorStand rbastand = new RenderBlockArmorStand();
        ClientRegistry.registerTileEntity((Class)BlockArmorStandTileEntity.class, "armorStand", (TileEntitySpecialRenderer)rbastand);
        final RenderBlockBanner rbbstand = new RenderBlockBanner();
        ClientRegistry.registerTileEntity((Class)BlockBannerStandTileEntity.class, "bannerStand", (TileEntitySpecialRenderer)rbbstand);
        final RenderBlockEditor rbEditor = new RenderBlockEditor();
        ClientRegistry.registerTileEntity((Class)BlockEditorTileEntity.class, "Exporter", (TileEntitySpecialRenderer)rbEditor);
    }
    
    static {
        ClientProxy.defaultArmor = new ModelArmor(1.0f, 1);
        ClientProxy.kingArmor = new ModelArmorHeavyPlate(1.0f, true);
        ClientProxy.heavyArmor = new ModelArmorHeavyPlate(1.0f);
        ClientProxy.mageArmor = new ModelArmorMageRobe(0.0f);
        ClientProxy.turtleArmorModel = new ModelArmorTurtle(1.0f, 0);
        ClientProxy.turtleHelmetModel = new ModelArmorTurtle(1.0f, 1);
        ClientProxy.dragonHead = new ModelArmorDragon();
        ClientProxy.bullHead = new ModelArmorMinotaur();
        ClientProxy.slimeArmor = new ModelArmor[] { new ModelArmorSlime(0), new ModelArmorSlime(1), new ModelArmorSlime(0.5f, 2), new ModelArmorSlime(3) };
        ClientProxy.coloredArmor = new ModelArmor[] { new ModelArmorColored(0), new ModelArmorColored(1), new ModelArmorColored(0.5f, 2), new ModelArmorColored(3) };
        ClientProxy.witchHat = new ModelArmorWitchHat(1.0f);
        ClientProxy.tableRenderID = 0;
    }
}
