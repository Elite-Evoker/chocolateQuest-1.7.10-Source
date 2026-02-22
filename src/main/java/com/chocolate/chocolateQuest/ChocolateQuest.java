package com.chocolate.chocolateQuest;

import com.chocolate.chocolateQuest.items.ItemSoulBottle;
import com.chocolate.chocolateQuest.items.mobControl.ItemTeamEditor;
import com.chocolate.chocolateQuest.items.mobControl.ItemController;
import com.chocolate.chocolateQuest.items.mobControl.ItemPathMarker;
import com.chocolate.chocolateQuest.items.ItemPotionHeal;
import com.chocolate.chocolateQuest.items.ItemAlchemistBag;
import com.chocolate.chocolateQuest.items.ItemHookShootSpider;
import com.chocolate.chocolateQuest.items.ItemHookShoot;
import com.chocolate.chocolateQuest.items.gun.ItemBubbleCannon;
import com.chocolate.chocolateQuest.items.gun.ItemGolemCannon;
import com.chocolate.chocolateQuest.items.gun.ItemGolemMachineGun;
import com.chocolate.chocolateQuest.items.gun.ItemGolemFramethrower;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import com.chocolate.chocolateQuest.items.gun.ItemAmmoLoader;
import com.chocolate.chocolateQuest.items.gun.ItemGolemHeavy;
import com.chocolate.chocolateQuest.items.gun.ItemGolem;
import com.chocolate.chocolateQuest.items.swords.ItemSpearGun;
import com.chocolate.chocolateQuest.items.gun.ItemPistol;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShield;
import com.chocolate.chocolateQuest.items.ItemMulti;
import com.chocolate.chocolateQuest.magic.SpellBase;
import com.chocolate.chocolateQuest.items.ItemStaffBase;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.items.ItemArmorRobe;
import com.chocolate.chocolateQuest.items.ItemCursedBone;
import com.chocolate.chocolateQuest.items.ItemArmorColored;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import net.minecraft.item.ItemArmor;
import com.chocolate.chocolateQuest.items.ItemArmorHeavy;
import net.minecraft.init.Items;
import com.chocolate.chocolateQuest.items.ItemArmorSpider;
import com.chocolate.chocolateQuest.items.ItemArmorBull;
import com.chocolate.chocolateQuest.items.ItemArmorTurtle;
import com.chocolate.chocolateQuest.items.ItemArmorSlime;
import com.chocolate.chocolateQuest.items.ItemArmorKing;
import com.chocolate.chocolateQuest.items.ItemArmorBootsCloud;
import com.chocolate.chocolateQuest.items.ItemArmorHelmetWitch;
import com.chocolate.chocolateQuest.items.ItemArmorHelmetScouter;
import com.chocolate.chocolateQuest.items.ItemArmorHelmetDragon;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import com.chocolate.chocolateQuest.items.ItemShied;
import com.chocolate.chocolateQuest.items.ItemBanner;
import com.chocolate.chocolateQuest.items.swords.ItemHookSword;
import com.chocolate.chocolateQuest.items.swords.ItemBaseDagger;
import com.chocolate.chocolateQuest.items.swords.ItemBaseBroadSword;
import com.chocolate.chocolateQuest.items.swords.ItemBigSwordArea;
import com.chocolate.chocolateQuest.items.swords.ItemSpearFire;
import com.chocolate.chocolateQuest.items.swords.ItemDaggerEnd;
import com.chocolate.chocolateQuest.items.swords.ItemDaggerNinja;
import com.chocolate.chocolateQuest.items.swords.ItemSwordWalker;
import net.minecraft.potion.Potion;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSwordDefensive;
import com.chocolate.chocolateQuest.items.swords.ItemSwordEffect;
import com.chocolate.chocolateQuest.items.ItemPickaxeMagic;
import com.chocolate.chocolateQuest.items.mobControl.ItemMobToSpawner;
import com.chocolate.chocolateQuest.items.ItemEggBD;
import com.chocolate.chocolateQuest.magic.EnchantmentMagicDefense;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.API.DungeonRegister;
import com.chocolate.chocolateQuest.API.DungeonBase;
import com.chocolate.chocolateQuest.builder.BuilderNether;
import com.chocolate.chocolateQuest.builder.BuilderEmptyCave;
import com.chocolate.chocolateQuest.builder.BuilderCastle;
import com.chocolate.chocolateQuest.builder.BuilderTemplateSurface;
import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.RegisterDungeonBuilder;
import com.chocolate.chocolateQuest.builder.BuilderTemplate;
import com.chocolate.chocolateQuest.entity.mob.registry.MobGremlin;
import com.chocolate.chocolateQuest.entity.mob.registry.MobPirate;
import com.chocolate.chocolateQuest.entity.mob.registry.MobWalker;
import com.chocolate.chocolateQuest.entity.mob.registry.MobMinotaur;
import com.chocolate.chocolateQuest.entity.mob.registry.MobZombiePig;
import com.chocolate.chocolateQuest.entity.mob.registry.MobSpecter;
import com.chocolate.chocolateQuest.entity.mob.registry.MobZombie;
import com.chocolate.chocolateQuest.entity.mob.registry.MobSkeleton;
import com.chocolate.chocolateQuest.entity.mob.registry.MobDefault;
import com.chocolate.chocolateQuest.block.BlockEditorVoid;
import com.chocolate.chocolateQuest.block.BlockDecoration;
import net.minecraft.block.material.Material;
import com.chocolate.chocolateQuest.block.ItemBlockDungeonBrick;
import com.chocolate.chocolateQuest.block.BlockDungeonBrick;
import com.chocolate.chocolateQuest.block.BlockAltar;
import com.chocolate.chocolateQuest.block.BlockBannerStand;
import com.chocolate.chocolateQuest.block.BlockArmorStand;
import net.minecraft.creativetab.CreativeTabs;
import com.chocolate.chocolateQuest.block.BlockEditor;
import com.chocolate.chocolateQuest.block.BlockMobSpawner;
import com.chocolate.chocolateQuest.entity.projectile.EntityProjectileBeam;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.EntityReferee;
import com.chocolate.chocolateQuest.entity.EntitySummonedUndead;
import com.chocolate.chocolateQuest.entity.EntityBaiter;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimePart;
import com.chocolate.chocolateQuest.entity.boss.EntityPartRidable;
import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtlePart;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtle;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantZombie;
import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import com.chocolate.chocolateQuest.entity.boss.EntitySpiderBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantBoxer;
import com.chocolate.chocolateQuest.entity.mob.EntitySpaceWarrior;
import com.chocolate.chocolateQuest.entity.mob.EntityPirateBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPirate;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanGremlin;
import com.chocolate.chocolateQuest.entity.mob.EntityWalkerBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanWalker;
import com.chocolate.chocolateQuest.entity.mob.EntitySpecterBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMinotaur;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPigZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSpecter;
import com.chocolate.chocolateQuest.entity.mob.EntityLich;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityNecromancer;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSkeleton;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMechaHeavy;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import cpw.mods.fml.common.registry.EntityRegistry;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanDummy;
import net.minecraft.command.ICommandManager;
import com.chocolate.chocolateQuest.command.CommandKillCounterDelete;
import com.chocolate.chocolateQuest.command.CommandKillCounter;
import com.chocolate.chocolateQuest.command.CommandReputation;
import com.chocolate.chocolateQuest.command.CommandAwakeEquipement;
import net.minecraft.command.ICommand;
import com.chocolate.chocolateQuest.command.CommandSpawnBoss;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import com.chocolate.chocolateQuest.config.ConfigHelper;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import java.io.InputStream;
import java.util.Enumeration;
import java.net.URL;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.io.FileOutputStream;
import java.io.File;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.net.URLDecoder;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.block.Block;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.misc.DungeonsItemsTab;
import com.chocolate.chocolateQuest.config.Configurations;
import com.chocolate.chocolateQuest.packets.ChannelHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod;

@Mod(modid = "chocolateQuest", version = "1.0", name = "Chocolate Quest")
public class ChocolateQuest
{
    public static final String MODID = "chocolateQuest";
    public static final String VERSION = "1.0";
    @SidedProxy(clientSide = "com.chocolate.chocolateQuest.client.ClientProxy", serverSide = "com.chocolate.chocolateQuest.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance("chocolateQuest")
    public static ChocolateQuest instance;
    public static ChannelHandler channel;
    public static Configurations config;
    public static DungeonsItemsTab tabItems;
    public static DungeonMonstersBase defaultMob;
    public static DungeonMonstersBase skeleton;
    public static DungeonMonstersBase zombie;
    public static DungeonMonstersBase specter;
    public static DungeonMonstersBase gremlin;
    public static DungeonMonstersBase walker;
    public static DungeonMonstersBase pirate;
    public static DungeonMonstersBase pigZombie;
    public static DungeonMonstersBase minotaur;
    public static DungeonMonstersBase spaceWarrior;
    public static Block table;
    public static Block armorStand;
    public static Block bannerStand;
    public static Block dungeonBrick;
    public static Block spawner;
    public static Block exporter;
    public static Block emptyBlock;
    public static Block exporterChest;
    public static final Enchantment enchantmentMagicDefense;
    public static Item egg;
    public static Item mobToSpawner;
    public static Item magicPickaxe;
    public static Item swordMoonLight;
    public static Item swordSunLight;
    public static Item swordTurtle;
    public static Item swordSpider;
    public static Item endSword;
    public static Item tricksterDagger;
    public static Item ninjaDagger;
    public static Item fireSpear;
    public static Item bigSwordBull;
    public static Item monkingSword;
    public static Item monkingDagger;
    public static Item monkingSwordAndShield;
    public static Item hookSword;
    public static Item banner;
    public static Item shield;
    public static Item rustedDagger;
    public static Item rustedSpear;
    public static Item rustedBigSword;
    public static Item rustedSwordAndShied;
    public static Item dragonHelmet;
    public static Item scouter;
    public static Item witchHat;
    public static Item cloudBoots;
    public static Item kingArmor;
    public static Item slimeHelmet;
    public static Item slimePlate;
    public static Item slimePants;
    public static Item slimeBoots;
    public static Item turtleHelmet;
    public static Item turtlePlate;
    public static Item turtlePants;
    public static Item turtleBoots;
    public static Item bullHelmet;
    public static Item bullPlate;
    public static Item bullPants;
    public static Item bullBoots;
    public static Item spiderHelmet;
    public static Item spiderPlate;
    public static Item spiderPants;
    public static Item spiderBoots;
    public static Item diamondHeavyHelmet;
    public static Item diamondHeavyPlate;
    public static Item diamondHeavyPants;
    public static Item diamondHeavyBoots;
    public static Item ironHeavyHelmet;
    public static Item ironHeavyPlate;
    public static Item ironHeavyPants;
    public static Item ironHeavyBoots;
    public static Item inquisitionHelmet;
    public static Item inquisitionPlate;
    public static Item inquisitionPants;
    public static Item inquisitionBoots;
    public static Item diamondHelmet;
    public static Item diamondPlate;
    public static Item diamondPants;
    public static Item diamondBoots;
    public static Item ironHelmet;
    public static Item ironPlate;
    public static Item ironPants;
    public static Item ironBoots;
    public static Item cursedBone;
    public static Item armorMage;
    public static Item staffHeal;
    public static Item staffPhysic;
    public static Item staffMagic;
    public static Item staffBlast;
    public static Item staffFire;
    public static Item staffLight;
    public static Item spell;
    public static Item ironSwordAndShield;
    public static Item diamondSwordAndShield;
    public static Item ironDagger;
    public static Item diamondDagger;
    public static Item ironSpear;
    public static Item diamondSpear;
    public static Item ironBigsword;
    public static Item diamondBigsword;
    public static Item revolver;
    public static Item spearGun;
    public static Item golem;
    public static Item golemHeavy;
    public static Item golemUpgrade;
    public static Item ammoLoader;
    public static Item golemGun;
    public static Item golemFlameThrower;
    public static Item golemRifleGun;
    public static Item golemMachineGun;
    public static Item golemCannon;
    public static Item golemBubbleCannon;
    public static Item hookShoot;
    public static Item longShoot;
    public static Item manualShoot;
    public static Item spiderHook;
    public static Item bullet;
    public static Item material;
    public static Item alchemistBag;
    public static Item potion;
    public static Item pathMarker;
    public static Item controller;
    public static Item controllerTeam;
    public static Item soulBottle;
    public static final int GUI_MOB = 0;
    public static final int GUI_EDITOR = 1;
    public static final int GUI_ARMORSTAND = 2;
    public static final int GUI_GUN = 3;
    public static final int GUI_CHEST = 4;
    public static final int GUI_MERCHANT = 5;
    public static final int GUI_NPC = 6;
    public static final int GUI_AWAKEMENT = 7;
    public static final int GUI_CONTROLLER = 8;
    public static final int GUI_PARTY = 9;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        final Configuration configFile = new Configuration(event.getSuggestedConfigurationFile());
        (ChocolateQuest.config = new Configurations()).load(configFile);
        GameRegistry.registerWorldGenerator((IWorldGenerator)new WorldGeneratorNew(), 100);
        MinecraftForge.EVENT_BUS.register((Object)new EventHandlerCQ());
        this.registerBlocks();
        this.registerItems();
        ChocolateQuest.proxy.register();
        ChocolateQuest.channel = new ChannelHandler();
        ChannelHandler.init();
        this.registerEntities();
        if (ChocolateQuest.config.updateData) {
            this.unpackMod();
        }
    }
    
    public void unpackMod() {
        final String path = "assets/chocolatequest/Chocolate";
        final Class clazz = ChocolateQuest.class;
        URL dirURL = clazz.getResource(path);
        if (dirURL == null) {
            final String me = clazz.getName().replace(".", "/") + ".class";
            dirURL = clazz.getClassLoader().getResource(me);
        }
        if (dirURL.getProtocol().equals("jar")) {
            try {
                final String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
                final JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
                final Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    final JarEntry entry = entries.nextElement();
                    final String name = entry.getName();
                    if (name.startsWith(path)) {
                        final String entryName = name.substring(path.length());
                        final File f = new File(BDHelper.getChocolateDir() + entryName);
                        if (f.exists()) {
                            continue;
                        }
                        if (entry.isDirectory()) {
                            f.mkdirs();
                        }
                        else {
                            final FileOutputStream fileoutputstream = new FileOutputStream(f);
                            final InputStream zipinputstream = jar.getInputStream(entry);
                            final byte[] buf = new byte[1024];
                            int n;
                            while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
                                fileoutputstream.write(buf, 0, n);
                            }
                            fileoutputstream.close();
                            zipinputstream.close();
                        }
                        BDHelper.println("Extracted: " + f);
                    }
                }
            }
            catch (final UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            catch (final IOException e2) {
                e2.printStackTrace();
            }
        }
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        ChocolateQuest.proxy.postInit();
        ConfigHelper.readChests();
    }
    
    @Mod.EventHandler
    public void load(final FMLInitializationEvent evt) {
        NetworkRegistry.INSTANCE.registerGuiHandler((Object)ChocolateQuest.instance, (IGuiHandler)ChocolateQuest.proxy);
        this.registerDungeons();
    }
    
    @Mod.EventHandler
    public void serverStart(final FMLServerStartingEvent event) {
        final MinecraftServer server = MinecraftServer.getServer();
        final ICommandManager command = server.getCommandManager();
        final ServerCommandManager manager = (ServerCommandManager)command;
        manager.registerCommand((ICommand)new CommandSpawnBoss());
        manager.registerCommand((ICommand)new CommandAwakeEquipement());
        manager.registerCommand((ICommand)new CommandReputation());
        manager.registerCommand((ICommand)new CommandKillCounter());
        manager.registerCommand((ICommand)new CommandKillCounterDelete());
    }
    
    public void registerEntities() {
        int id = 0;
        EntityRegistry.registerModEntity((Class)EntityHumanDummy.class, "dummy", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityGolemMecha.class, "mecha", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityGolemMechaHeavy.class, "mechaHeavy", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityHumanSkeleton.class, "armoredSkeleton", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityNecromancer.class, "necromancer", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityHumanZombie.class, "armoredZombie", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityLich.class, "Lich", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityHumanSpecter.class, "specter", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityHumanPigZombie.class, "pigzombie", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityHumanMinotaur.class, "minotaur", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntitySpecterBoss.class, "specterBoss", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityHumanWalker.class, "abyssWalker", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityWalkerBoss.class, "abyssWalkerBoss", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityHumanGremlin.class, "gremlin", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityHumanPirate.class, "pirate", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityPirateBoss.class, "pirateBoss", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntitySpaceWarrior.class, "spaceWarrior", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityGiantBoxer.class, "monking", id++, (Object)this, 100, 3, true);
        EntityRegistry.registerModEntity((Class)EntityBull.class, "bull", id++, (Object)this, 100, 3, true);
        EntityRegistry.registerModEntity((Class)EntitySlimeBoss.class, "slimeBoss", id++, (Object)this, 100, 3, true);
        EntityRegistry.registerModEntity((Class)EntitySpiderBoss.class, "spiderBoss", id++, (Object)this, 100, 3, true);
        EntityRegistry.registerModEntity((Class)EntityWyvern.class, "greenDragon", id++, (Object)this, 100, 3, true);
        EntityRegistry.registerModEntity((Class)EntityGiantZombie.class, "giantZombie", id++, (Object)this, 100, 3, true);
        EntityRegistry.registerModEntity((Class)EntityTurtle.class, "turtleBoss", id++, (Object)this, 100, 3, true);
        EntityRegistry.registerModEntity((Class)EntityTurtlePart.class, "TurtleBossPart", id++, (Object)this, 60, 3, true);
        EntityRegistry.registerModEntity((Class)EntityHumanNPC.class, "CQ_npc", id++, (Object)this, 80, 3, true);
        EntityRegistry.registerModEntity((Class)EntityPart.class, "EntityPart", id++, (Object)this, 100, 3, true);
        EntityRegistry.registerModEntity((Class)EntityPartRidable.class, "EntityPartRidable", id++, (Object)this, 100, 3, true);
        EntityRegistry.registerModEntity((Class)EntitySlimePart.class, "EntityPartSlime", id++, (Object)this, 100, 3, true);
        EntityRegistry.registerModEntity((Class)EntityBaiter.class, "SummonedBait", id++, (Object)this, 50, 3, true);
        EntityRegistry.registerModEntity((Class)EntitySummonedUndead.class, "SummonedUndead", id++, (Object)this, 50, 3, true);
        EntityRegistry.registerModEntity((Class)EntityReferee.class, "Referee", id++, (Object)this, 50, 3, true);
        EntityRegistry.registerModEntity((Class)EntityBaseBall.class, "ChocoProjectile", id++, (Object)this, 64, 3, true);
        EntityRegistry.registerModEntity((Class)EntityHookShoot.class, "Hookshoot", id++, (Object)this, 64, 3, true);
        EntityRegistry.registerModEntity((Class)EntityProjectileBeam.class, "Beam", id++, (Object)this, 64, 3, true);
    }
    
    public void registerBlocks() {
        GameRegistry.registerBlock(ChocolateQuest.spawner = new BlockMobSpawner().setUnlocalizedName("CQSpawner"), "CQSpawner");
        GameRegistry.registerBlock(ChocolateQuest.exporter = new BlockEditor().setUnlocalizedName("exporter").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems), "exporter");
        GameRegistry.registerBlock(ChocolateQuest.armorStand = new BlockArmorStand().setHardness(0.5f).setResistance(0.1f).setUnlocalizedName("armorStand").setTextureName("planks").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems), "armorStand");
        GameRegistry.registerBlock(ChocolateQuest.bannerStand = new BlockBannerStand().setHardness(0.5f).setResistance(0.1f).setUnlocalizedName("bannerStand").setTextureName("planks").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems), "bannerStand");
        GameRegistry.registerBlock(ChocolateQuest.table = new BlockAltar().setHardness(0.5f).setResistance(0.1f).setUnlocalizedName("table").setTextureName("planks").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems), "table");
        GameRegistry.registerBlock(ChocolateQuest.dungeonBrick = new BlockDungeonBrick().setHardness(120.0f).setResistance(80.0f).setUnlocalizedName("dungeonBrick").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems), (Class)ItemBlockDungeonBrick.class, "dungeonBrick");
        final String[] names = { "Treasure Chest", "Food Chest", "Tools Chest", "Ores Chest", "Boss Spawner" };
        GameRegistry.registerBlock(ChocolateQuest.exporterChest = new BlockDecoration(Material.wood, "c", names).setUnlocalizedName("ExporterChest").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems), (Class)ItemBlockDungeonBrick.class, "exporterChest");
        GameRegistry.registerBlock(ChocolateQuest.emptyBlock = new BlockEditorVoid().setUnlocalizedName("none").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems), "none");
    }
    
    public void registerItems() {
        this.registerItem(ChocolateQuest.egg);
        this.registerItem(ChocolateQuest.magicPickaxe);
        this.registerItem(ChocolateQuest.mobToSpawner);
        this.registerItem(ChocolateQuest.rustedDagger);
        this.registerItem(ChocolateQuest.rustedBigSword);
        this.registerItem(ChocolateQuest.rustedSpear);
        this.registerItem(ChocolateQuest.rustedSwordAndShied);
        this.registerItem(ChocolateQuest.hookSword);
        this.registerItem(ChocolateQuest.ironSwordAndShield);
        this.registerItem(ChocolateQuest.diamondSwordAndShield);
        this.registerItem(ChocolateQuest.swordMoonLight);
        this.registerItem(ChocolateQuest.swordSunLight);
        this.registerItem(ChocolateQuest.endSword);
        this.registerItem(ChocolateQuest.swordTurtle);
        this.registerItem(ChocolateQuest.swordSpider);
        this.registerItem(ChocolateQuest.monkingSwordAndShield);
        this.registerItem(ChocolateQuest.ironDagger);
        this.registerItem(ChocolateQuest.diamondDagger);
        this.registerItem(ChocolateQuest.tricksterDagger);
        this.registerItem(ChocolateQuest.ninjaDagger);
        this.registerItem(ChocolateQuest.monkingDagger);
        this.registerItem(ChocolateQuest.ironSpear);
        this.registerItem(ChocolateQuest.diamondSpear);
        this.registerItem(ChocolateQuest.spearGun);
        this.registerItem(ChocolateQuest.fireSpear);
        this.registerItem(ChocolateQuest.ironBigsword);
        this.registerItem(ChocolateQuest.diamondBigsword);
        this.registerItem(ChocolateQuest.bigSwordBull);
        this.registerItem(ChocolateQuest.monkingSword);
        this.registerItem(ChocolateQuest.banner);
        this.registerItem(ChocolateQuest.shield);
        this.registerItem(ChocolateQuest.dragonHelmet);
        this.registerItem(ChocolateQuest.scouter);
        this.registerItem(ChocolateQuest.witchHat);
        this.registerItem(ChocolateQuest.cloudBoots);
        this.registerItem(ChocolateQuest.kingArmor);
        this.registerItem(ChocolateQuest.slimeHelmet);
        this.registerItem(ChocolateQuest.slimePlate);
        this.registerItem(ChocolateQuest.slimePants);
        this.registerItem(ChocolateQuest.slimeBoots);
        this.registerItem(ChocolateQuest.turtleHelmet);
        this.registerItem(ChocolateQuest.turtlePlate);
        this.registerItem(ChocolateQuest.turtlePants);
        this.registerItem(ChocolateQuest.turtleBoots);
        this.registerItem(ChocolateQuest.bullHelmet);
        this.registerItem(ChocolateQuest.bullPlate);
        this.registerItem(ChocolateQuest.bullPants);
        this.registerItem(ChocolateQuest.bullBoots);
        this.registerItem(ChocolateQuest.spiderHelmet);
        this.registerItem(ChocolateQuest.spiderPlate);
        this.registerItem(ChocolateQuest.spiderPants);
        this.registerItem(ChocolateQuest.spiderBoots);
        this.registerItem(ChocolateQuest.diamondHeavyHelmet);
        this.registerItem(ChocolateQuest.diamondHeavyPlate);
        this.registerItem(ChocolateQuest.diamondHeavyPants);
        this.registerItem(ChocolateQuest.diamondHeavyBoots);
        this.registerItem(ChocolateQuest.ironHeavyHelmet);
        this.registerItem(ChocolateQuest.ironHeavyPlate);
        this.registerItem(ChocolateQuest.ironHeavyPants);
        this.registerItem(ChocolateQuest.ironHeavyBoots);
        this.registerItem(ChocolateQuest.inquisitionHelmet);
        this.registerItem(ChocolateQuest.inquisitionPlate);
        this.registerItem(ChocolateQuest.inquisitionPants);
        this.registerItem(ChocolateQuest.inquisitionBoots);
        this.registerItem(ChocolateQuest.diamondHelmet);
        this.registerItem(ChocolateQuest.diamondPlate);
        this.registerItem(ChocolateQuest.diamondPants);
        this.registerItem(ChocolateQuest.diamondBoots);
        this.registerItem(ChocolateQuest.ironHelmet);
        this.registerItem(ChocolateQuest.ironPlate);
        this.registerItem(ChocolateQuest.ironPants);
        this.registerItem(ChocolateQuest.ironBoots);
        this.registerItem(ChocolateQuest.armorMage);
        this.registerItem(ChocolateQuest.staffHeal);
        this.registerItem(ChocolateQuest.staffPhysic);
        this.registerItem(ChocolateQuest.staffMagic);
        this.registerItem(ChocolateQuest.staffFire);
        this.registerItem(ChocolateQuest.staffBlast);
        this.registerItem(ChocolateQuest.staffLight);
        this.registerItem(ChocolateQuest.spell);
        this.registerItem(ChocolateQuest.potion);
        this.registerItem(ChocolateQuest.alchemistBag);
        this.registerItem(ChocolateQuest.revolver);
        this.registerItem(ChocolateQuest.golem);
        this.registerItem(ChocolateQuest.golemHeavy);
        this.registerItem(ChocolateQuest.golemUpgrade);
        this.registerItem(ChocolateQuest.golemGun);
        this.registerItem(ChocolateQuest.golemFlameThrower);
        this.registerItem(ChocolateQuest.golemRifleGun);
        this.registerItem(ChocolateQuest.golemCannon);
        this.registerItem(ChocolateQuest.golemBubbleCannon);
        this.registerItem(ChocolateQuest.golemMachineGun);
        this.registerItem(ChocolateQuest.ammoLoader);
        this.registerItem(ChocolateQuest.bullet);
        this.registerItem(ChocolateQuest.material);
        this.registerItem(ChocolateQuest.cursedBone);
        this.registerItem(ChocolateQuest.hookShoot);
        this.registerItem(ChocolateQuest.longShoot);
        this.registerItem(ChocolateQuest.manualShoot);
        this.registerItem(ChocolateQuest.spiderHook);
        this.registerItem(ChocolateQuest.pathMarker);
        this.registerItem(ChocolateQuest.controller);
        this.registerItem(ChocolateQuest.controllerTeam);
        this.registerItem(ChocolateQuest.soulBottle);
        ChocolateQuest.tabItems.setItemIcon(ChocolateQuest.endSword);
    }
    
    public Item registerItem(final Item item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        return item;
    }
    
    public void registerDungeons() {
        this.addMobToList(ChocolateQuest.defaultMob = new MobDefault());
        this.addMobToList(ChocolateQuest.skeleton = new MobSkeleton());
        this.addMobToList(ChocolateQuest.zombie = new MobZombie());
        this.addMobToList(ChocolateQuest.specter = new MobSpecter());
        this.addMobToList(ChocolateQuest.pigZombie = new MobZombiePig());
        this.addMobToList(ChocolateQuest.minotaur = new MobMinotaur());
        this.addMobToList(ChocolateQuest.walker = new MobWalker());
        this.addMobToList(ChocolateQuest.pirate = new MobPirate());
        this.addMobToList(ChocolateQuest.gremlin = new MobGremlin());
        RegisterDungeonBuilder.addDungeonBuilder(new BuilderTemplate());
        RegisterDungeonBuilder.addDungeonBuilder(new BuilderTemplateSurface());
        RegisterDungeonBuilder.addDungeonBuilder(new BuilderCastle());
        RegisterDungeonBuilder.addDungeonBuilder(new BuilderEmptyCave());
        RegisterDungeonBuilder.addDungeonBuilder(new BuilderNether());
        final File file = new File(BDHelper.getAppDir(), "Chocolate/DungeonConfig/");
        BDHelper.println("## Dungeon register ##");
        this.registerDungeonsFromFolder(file);
        final File[] files = file.listFiles();
        if (files != null) {
            for (final File current : files) {
                if (current.isDirectory()) {
                    this.registerDungeonsFromFolder(current);
                }
            }
        }
    }
    
    public void registerDungeonsFromFolder(final File folder) {
        final File[] files = folder.listFiles();
        if (files != null) {
            for (final File current : files) {
                if (!current.isDirectory()) {
                    DungeonBase dungeon = new DungeonBase();
                    dungeon = dungeon.readData(current);
                    if (dungeon != null) {
                        DungeonRegister.addDungeon(dungeon);
                        BDHelper.println("Registered dungeon: " + current.getName());
                    }
                }
            }
        }
    }
    
    private void addMobToList(final DungeonMonstersBase mob) {
        RegisterDungeonMobs.addMob(mob);
    }
    
    static {
        ChocolateQuest.tabItems = new DungeonsItemsTab("Items");
        enchantmentMagicDefense = (Enchantment)new EnchantmentMagicDefense(52, 1);
        ChocolateQuest.egg = new ItemEggBD().setUnlocalizedName("egg").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.mobToSpawner = new ItemMobToSpawner().setUnlocalizedName("mobToSpawner").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.magicPickaxe = new ItemPickaxeMagic().setUnlocalizedName("pickaxeMagic").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.swordMoonLight = new ItemSwordEffect(Item.ToolMaterial.EMERALD, "swordMoonLight").setShieldId(14).setUnlocalizedName("moonSword").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.swordSunLight = new ItemSwordEffect(Item.ToolMaterial.EMERALD, "swordDefensive").setShieldId(13).setUnlocalizedName("defenseSword").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.swordTurtle = new ItemBaseSwordDefensive(Item.ToolMaterial.EMERALD, "swordTurtle").setShieldId(15).setUnlocalizedName("swordTurtle").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.swordSpider = new ItemSwordEffect(Item.ToolMaterial.IRON, "swordSpider", Potion.poison.id).setShieldId(18).setUnlocalizedName("swordSpider").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.endSword = new ItemSwordWalker().setUnlocalizedName("walkerSword").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.tricksterDagger = new ItemDaggerNinja().setUnlocalizedName("pirateDagger").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ninjaDagger = new ItemDaggerEnd().setUnlocalizedName("ninjaDagger").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.fireSpear = new ItemSpearFire().setUnlocalizedName("dwarfSpear").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.bigSwordBull = new ItemBigSwordArea(Item.ToolMaterial.EMERALD, "bigSwordBull", 6.0f).setUnlocalizedName("bigSwordBull").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.monkingSword = new ItemBaseBroadSword(Item.ToolMaterial.EMERALD, "swordMonking", 8.0f).setUnlocalizedName("swordMonking").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.monkingDagger = new ItemBaseDagger(Item.ToolMaterial.EMERALD, "daggerMonking", 4, 1.2f).setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("daggerMonking");
        ChocolateQuest.monkingSwordAndShield = new ItemBaseSwordDefensive(Item.ToolMaterial.EMERALD, "swordShiedMonking", 5, 1.2f).setShieldId(17).setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("swordShiedMonking").setMaxDurability(1988);
        ChocolateQuest.hookSword = new ItemHookSword(Item.ToolMaterial.EMERALD).setUnlocalizedName("hookSword").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.banner = new ItemBanner().setUnlocalizedName("banner").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.shield = new ItemShied().setUnlocalizedName("shield").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.rustedDagger = new ItemBaseDagger(Item.ToolMaterial.GOLD, "rustedDagger", 1, 3.0f).setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("rustedDagger").setMaxDurability(1988);
        ChocolateQuest.rustedSpear = new ItemBaseSpear(Item.ToolMaterial.GOLD, "rustedSpear", 1, 2.5f).setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("rustedSpear").setMaxDurability(1988);
        ChocolateQuest.rustedBigSword = new ItemBaseBroadSword(Item.ToolMaterial.GOLD, "rustedBigSword", 2.0f, 4.3f).setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("rustedBigSword").setMaxDurability(1988);
        ChocolateQuest.rustedSwordAndShied = new ItemBaseSwordDefensive(Item.ToolMaterial.GOLD, "rustedSword", 1, 3.0f).setShieldId(16).setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("rustedSwordAndShied").setMaxDurability(1988);
        ChocolateQuest.dragonHelmet = new ItemArmorHelmetDragon().setEpic().setUnlocalizedName("dragonHelmet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.scouter = new ItemArmorHelmetScouter().setEpic().setUnlocalizedName("scouter").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.witchHat = new ItemArmorHelmetWitch().setEpic().setUnlocalizedName("witchHat").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.cloudBoots = new ItemArmorBootsCloud().setEpic().setUnlocalizedName("cloudBoots").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.kingArmor = new ItemArmorKing().setEpic().setUnlocalizedName("kingArmor").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.slimeHelmet = new ItemArmorSlime(0, "slimeHelmet").setUnlocalizedName("slimeHelmet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.slimePlate = new ItemArmorSlime(1, "slimePlate").setUnlocalizedName("slimePlate").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.slimePants = new ItemArmorSlime(2, "slimePants").setUnlocalizedName("slimePants").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.slimeBoots = new ItemArmorSlime(3, "slimeBoots").setUnlocalizedName("slimeBoots").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.turtleHelmet = new ItemArmorTurtle(0, "turtleHelmet").setUnlocalizedName("turtleHelmet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.turtlePlate = new ItemArmorTurtle(1, "turtlePlate").setUnlocalizedName("turtlePlate").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.turtlePants = new ItemArmorTurtle(2, "turtlePants").setUnlocalizedName("turtlePants").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.turtleBoots = new ItemArmorTurtle(3, "turtleBoots").setUnlocalizedName("turtleBoots").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.bullHelmet = new ItemArmorBull(0, "bullHelmet").setUnlocalizedName("bullHelmet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.bullPlate = new ItemArmorBull(1, "bullPlate").setUnlocalizedName("bullPlate").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.bullPants = new ItemArmorBull(2, "bullPants").setUnlocalizedName("bullPants").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.bullBoots = new ItemArmorBull(3, "bullBoots").setUnlocalizedName("bullBoots").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.spiderHelmet = new ItemArmorSpider(0, "spiderHelmet").setUnlocalizedName("spiderHelmet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.spiderPlate = new ItemArmorSpider(1, "spiderPlate").setUnlocalizedName("spiderPlate").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.spiderPants = new ItemArmorSpider(2, "spiderPants").setUnlocalizedName("spiderPants").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.spiderBoots = new ItemArmorSpider(3, "spiderBoots").setUnlocalizedName("spiderBoots").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.diamondHeavyHelmet = new ItemArmorHeavy(0, "armorHeavyDiamond", Items.diamond).setUnlocalizedName("diamondHeavyHelmet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.diamondHeavyPlate = new ItemArmorHeavy(1, "armorHeavyDiamond", Items.diamond).setUnlocalizedName("diamondHeavyPlate").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.diamondHeavyPants = new ItemArmorHeavy(2, "armorHeavyDiamond", Items.diamond).setUnlocalizedName("diamondHeavyPants").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.diamondHeavyBoots = new ItemArmorHeavy(3, "armorHeavyDiamond", Items.diamond).setUnlocalizedName("diamondHeavyBoots").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ironHeavyHelmet = new ItemArmorHeavy(0, "armorHeavyIron", Items.iron_ingot, ItemArmor.ArmorMaterial.DIAMOND).setUnlocalizedName("ironHeavyHelmet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ironHeavyPlate = new ItemArmorHeavy(1, "armorHeavyIron", Items.iron_ingot, ItemArmor.ArmorMaterial.DIAMOND).setUnlocalizedName("ironHeavyPlate").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ironHeavyPants = new ItemArmorHeavy(2, "armorHeavyIron", Items.iron_ingot, ItemArmor.ArmorMaterial.DIAMOND).setUnlocalizedName("ironHeavyPants").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ironHeavyBoots = new ItemArmorHeavy(3, "armorHeavyIron", Items.iron_ingot, ItemArmor.ArmorMaterial.DIAMOND).setUnlocalizedName("ironHeavyBoots").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.inquisitionHelmet = new ItemArmorBase(ItemArmor.ArmorMaterial.DIAMOND, 0, "armorInquisition").setUnlocalizedName("inquisitionHelmet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.inquisitionPlate = new ItemArmorBase(ItemArmor.ArmorMaterial.DIAMOND, 1, "armorInquisition").setUnlocalizedName("inquisitionPlate").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.inquisitionPants = new ItemArmorBase(ItemArmor.ArmorMaterial.DIAMOND, 2, "armorInquisition").setUnlocalizedName("inquisitionPants").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.inquisitionBoots = new ItemArmorBase(ItemArmor.ArmorMaterial.DIAMOND, 3, "armorInquisition").setUnlocalizedName("inquisitionBoots").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.diamondHelmet = new ItemArmorColored(ItemArmor.ArmorMaterial.DIAMOND, 0, "diamond", 65535).setUnlocalizedName("diamondColoredHelmet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.diamondPlate = new ItemArmorColored(ItemArmor.ArmorMaterial.DIAMOND, 1, "diamond", 65535).setUnlocalizedName("diamondColoredPlate").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.diamondPants = new ItemArmorColored(ItemArmor.ArmorMaterial.DIAMOND, 2, "diamond", 65535).setUnlocalizedName("diamondColoredPants").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.diamondBoots = new ItemArmorColored(ItemArmor.ArmorMaterial.DIAMOND, 3, "diamond", 65535).setUnlocalizedName("diamondColoredBoots").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ironHelmet = new ItemArmorColored(ItemArmor.ArmorMaterial.IRON, 0, "iron", 13421772).setUnlocalizedName("ironColoredHelmet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ironPlate = new ItemArmorColored(ItemArmor.ArmorMaterial.IRON, 1, "iron", 13421772).setUnlocalizedName("ironColoredPlate").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ironPants = new ItemArmorColored(ItemArmor.ArmorMaterial.IRON, 2, "iron", 13421772).setUnlocalizedName("ironColoredPants").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ironBoots = new ItemArmorColored(ItemArmor.ArmorMaterial.IRON, 3, "iron", 13421772).setUnlocalizedName("ironColoredBoots").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.cursedBone = new ItemCursedBone().setUnlocalizedName("cursedBone").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.armorMage = new ItemArmorRobe().setUnlocalizedName("mageRobe").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.staffHeal = new ItemStaffHeal().setUnlocalizedName("staffLife").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.staffPhysic = new ItemStaffBase(Elements.physic).setUnlocalizedName("staffPhysic").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.staffMagic = new ItemStaffBase(Elements.magic).setUnlocalizedName("staffMagic").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.staffBlast = new ItemStaffBase(Elements.blast).setUnlocalizedName("staffBlast").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.staffFire = new ItemStaffBase(Elements.fire).setUnlocalizedName("staffFire").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.staffLight = new ItemStaffBase(Elements.light).setUnlocalizedName("staffLight").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.spell = new ItemMulti(SpellBase.getNames(), "spell").setUnlocalizedName("spell").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ironSwordAndShield = new ItemSwordAndShield(Item.ToolMaterial.IRON).setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("ironSwordAndShield");
        ChocolateQuest.diamondSwordAndShield = new ItemSwordAndShield(Item.ToolMaterial.EMERALD).setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("diamondSwordAndShield");
        ChocolateQuest.ironDagger = new ItemBaseDagger(Item.ToolMaterial.IRON, "daggerIron").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("daggerIron");
        ChocolateQuest.diamondDagger = new ItemBaseDagger(Item.ToolMaterial.EMERALD, "daggerDiamond").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("daggerDiamond");
        ChocolateQuest.ironSpear = new ItemBaseSpear(Item.ToolMaterial.IRON, "spearIron").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("spearIron");
        ChocolateQuest.diamondSpear = new ItemBaseSpear(Item.ToolMaterial.EMERALD, "spearDiamond").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("spearDiamond");
        ChocolateQuest.ironBigsword = new ItemBaseBroadSword(Item.ToolMaterial.IRON, "bigSwordIron").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("bigswordIron");
        ChocolateQuest.diamondBigsword = new ItemBaseBroadSword(Item.ToolMaterial.EMERALD, "bigSwordDiamond").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setUnlocalizedName("bigswordDiamond");
        ChocolateQuest.revolver = new ItemPistol().setUnlocalizedName("revolver").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.spearGun = new ItemSpearGun().setUnlocalizedName("spearGun").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.golem = new ItemGolem("mechaGolem").setUnlocalizedName("mecha").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.golemHeavy = new ItemGolemHeavy().setUnlocalizedName("mechaHeavy").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.golemUpgrade = new ItemMulti(new String[] { "golemArmor", "golemField", "golemShield", "golemRockets" }, "golemUpgrade").setUnlocalizedName("golemUpgrade").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.ammoLoader = new ItemAmmoLoader().setUnlocalizedName("ammoLoader").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.golemGun = new ItemGolemWeapon(10, 16.0f, 5.0f).setUnlocalizedName("golemGun").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.golemFlameThrower = new ItemGolemFramethrower().setUnlocalizedName("golemFlameThrower").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.golemRifleGun = new ItemGolemWeapon(20, 64.0f, 0.0f).setUnlocalizedName("golemRailGun").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.golemMachineGun = new ItemGolemMachineGun().setUnlocalizedName("golemMachineGun").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.golemCannon = new ItemGolemCannon(30, 25.0f, 3.0f, 3).setUnlocalizedName("golemCannon").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.golemBubbleCannon = new ItemBubbleCannon(30, 16.0f, 3.0f, 3).setUnlocalizedName("golemBubbleCannon").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.hookShoot = new ItemHookShoot(0, "chocolatequest:hookShoot").setUnlocalizedName("hookshoot").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.longShoot = new ItemHookShoot(1, "chocolatequest:hookLong").setUnlocalizedName("longshoot").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.manualShoot = new ItemHookShoot(2, "chocolatequest:hookManual").setUnlocalizedName("manualshoot").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.spiderHook = new ItemHookShootSpider(3).setUnlocalizedName("spiderhook").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.bullet = new ItemMulti(new String[] { "ironBullet", "goldBullet", "magicBullet", "fireBullet", "cannonBullet" }, "bullets").setUnlocalizedName("bullet").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.material = new ItemMulti(new String[] { "hammer", "turtleScale", "bullLeather", "slimeBall", "spiderLeather", "monkingBone" }, "mat").setUnlocalizedName("material").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.alchemistBag = new ItemAlchemistBag().setUnlocalizedName("potionsBag").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.potion = new ItemPotionHeal().setUnlocalizedName("healingPotion").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.pathMarker = new ItemPathMarker().setUnlocalizedName("pathMarker").setTextureName("chocolatequest:pathMap").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.controller = new ItemController().setUnlocalizedName("controller").setTextureName("chocolateQuest:i0").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.controllerTeam = new ItemTeamEditor().setUnlocalizedName("controllerTeam").setTextureName("chocolateQuest:partyEditor").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems);
        ChocolateQuest.soulBottle = new ItemSoulBottle().setUnlocalizedName("soulBottle").setCreativeTab((CreativeTabs)ChocolateQuest.tabItems).setTextureName("chocolateQuest:soulBottle");
    }
}
