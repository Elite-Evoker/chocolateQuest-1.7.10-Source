package com.chocolate.chocolateQuest.gui.guinpc;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import com.chocolate.chocolateQuest.packets.PacketEditNPC;
import net.minecraft.entity.Entity;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import com.chocolate.chocolateQuest.gui.GuiHumanBase;
import net.minecraft.client.gui.Gui;
import java.util.Enumeration;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.jar.JarEntry;
import java.util.ArrayList;
import java.util.jar.JarFile;
import java.net.URLDecoder;
import com.chocolate.chocolateQuest.ChocolateQuest;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.entity.SharedMonsterAttributes;
import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import com.chocolate.chocolateQuest.misc.EnumRace;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.gui.GuiButtonDisplayString;
import java.util.Random;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBoxInteger;
import com.chocolate.chocolateQuest.gui.GuiButtonSlider;
import com.chocolate.chocolateQuest.gui.GuiButtonMultiOptions;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;

public class GuiEditNpc extends GuiLinked
{
    EntityHumanNPC npc;
    GuiTextField name;
    GuiTextField displayName;
    GuiTextField texture;
    GuiButton textureFile;
    GuiScrollOptions textureFileSelector;
    GuiButtonMultiOptions model;
    GuiButtonSlider size;
    GuiButtonSlider colorRed;
    GuiButtonSlider colorGreen;
    GuiButtonSlider colorBlue;
    GuiButtonTextBoxInteger buttonReputationDeath;
    GuiButtonTextBoxInteger buttonReputationToAttack;
    GuiTextField faction;
    GuiButtonMultiOptions gender;
    GuiButtonMultiOptions usePlayerTexture;
    GuiButtonMultiOptions blocksMovement;
    GuiButtonMultiOptions visibleName;
    GuiButtonMultiOptions canPickLoot;
    GuiButtonMultiOptions shouldTargetMonsters;
    GuiButtonMultiOptions canTeleport;
    GuiButtonMultiOptions voice;
    GuiButtonTextBoxInteger buttonHealth;
    GuiButtonTextBoxInteger buttonSpeed;
    GuiButtonTextBoxInteger buttonHomeX;
    GuiButtonTextBoxInteger buttonHomeY;
    GuiButtonTextBoxInteger buttonHomeZ;
    GuiButtonTextBoxInteger buttonHomeDistance;
    int playSound;
    Random rand;
    
    public GuiEditNpc(final EntityHumanNPC npc) {
        this.playSound = 99;
        this.rand = new Random();
        this.npc = npc;
        this.maxScrollAmmount = 110;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int x = 25;
        int y = 10;
        final int buttonWidth = 200;
        final int buttonHeight = 20;
        final int buttonSeparation = 5;
        final int buttonsGridWidth = buttonWidth / 2 + buttonSeparation;
        GuiButton button = new GuiButtonDisplayString(0, x, y, buttonWidth, buttonHeight, "Name (Identifier)");
        this.buttonList.add(button);
        y += button.height;
        (this.name = new GuiTextField(this.fontRendererObj, x, y, buttonWidth, buttonHeight)).setText(this.npc.name);
        this.textFieldList.add(this.name);
        final int colorSeparation = 70;
        button = new GuiButtonDisplayString(0, x + buttonWidth + buttonSeparation + colorSeparation, y - button.height, 108, buttonHeight, "Color");
        this.buttonList.add(button);
        this.colorRed = new GuiButtonSlider(0, x + buttonWidth + buttonSeparation + colorSeparation, y, "Red", BDHelper.getColorRed(this.npc.color), 255, true);
        this.buttonList.add(this.colorRed);
        this.colorGreen = new GuiButtonSlider(0, x + buttonWidth + buttonSeparation + colorSeparation, y + buttonHeight, "Green", BDHelper.getColorGreen(this.npc.color), 255, true);
        this.buttonList.add(this.colorGreen);
        this.colorBlue = new GuiButtonSlider(0, x + buttonWidth + buttonSeparation + colorSeparation, y + buttonHeight * 2, "Blue", BDHelper.getColorBlue(this.npc.color), 255, true);
        this.buttonList.add(this.colorBlue);
        y += this.name.height + buttonSeparation;
        button = new GuiButtonDisplayString(0, x, y, buttonWidth, buttonHeight, "DisplayName");
        this.buttonList.add(button);
        y += button.height - 5;
        (this.displayName = new GuiTextField(this.fontRendererObj, x, y, buttonWidth, buttonHeight)).setText(this.npc.displayName);
        this.textFieldList.add(this.displayName);
        y += this.displayName.height + buttonSeparation;
        button = new GuiButtonDisplayString(0, x, y, buttonWidth, buttonHeight, "Texture");
        this.buttonList.add(button);
        y += button.height - 5;
        (this.texture = new GuiTextField(this.fontRendererObj, x, y, buttonWidth, buttonHeight)).setMaxStringLength(50);
        this.texture.setText(this.npc.texture);
        this.textFieldList.add(this.texture);
        this.textureFile = new GuiButton(0, x + buttonWidth + 1, y, buttonHeight, buttonHeight, "+");
        this.buttonList.add(this.textureFile);
        button = new GuiButtonDisplayString(0, x + buttonWidth + buttonSeparation + buttonHeight, y - button.height, 50, buttonHeight, "Texture type");
        this.buttonList.add(button);
        this.usePlayerTexture = new GuiButtonMultiOptions(0, x + buttonWidth + buttonSeparation + buttonHeight, y, 50, buttonHeight, new String[] { "Player", "Entity" }, this.npc.hasPlayerTexture ? 0 : 1);
        this.buttonList.add(this.usePlayerTexture);
        button = new GuiButtonDisplayString(0, x + buttonWidth + buttonSeparation * 2 + buttonHeight + 80, y - button.height, 50, buttonHeight, "Gender");
        this.buttonList.add(button);
        this.gender = new GuiButtonMultiOptions(0, x + buttonWidth + buttonSeparation * 2 + buttonHeight + 80, y, 50, buttonHeight, new String[] { "Male", "Female" }, this.npc.isMale ? 0 : 1);
        this.buttonList.add(this.gender);
        y += this.texture.height + buttonSeparation;
        final String[] fileNames = this.getTextureNames();
        this.textureFileSelector = new GuiScrollOptions(this.STATIC_ID, 30, 30, this.width - 60, this.height - 60, fileNames, this.fontRendererObj, 0);
        button = new GuiButtonDisplayString(0, x, y, buttonWidth, buttonHeight, "Race");
        this.buttonList.add(button);
        y += button.height - 5;
        this.model = new GuiButtonMultiOptions(0, x, y, buttonWidth, buttonHeight, EnumRace.getNames(), this.npc.modelType);
        this.buttonList.add(this.model);
        this.size = new GuiButtonSlider(0, x + buttonWidth + buttonSeparation, y, "Size", this.npc.size);
        this.size.width = buttonWidth;
        this.buttonList.add(this.size);
        y += this.model.height + buttonSeparation;
        button = new GuiButtonDisplayString(0, x, y, buttonWidth, buttonHeight, "Faction");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 2, y, buttonWidth / 2, buttonHeight, "Reputation friendly");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 3, y, buttonWidth / 2, buttonHeight, "Reputation on kill");
        this.buttonList.add(button);
        y += button.height - 5;
        (this.faction = new GuiTextField(this.fontRendererObj, x, y, buttonWidth, buttonHeight)).setText(this.npc.entityTeam);
        this.textFieldList.add(this.faction);
        this.buttonReputationToAttack = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 2, y, buttonWidth / 2, buttonHeight, this.fontRendererObj, this.npc.repToAttack);
        this.buttonList.add(this.buttonReputationToAttack);
        this.textFieldList.add(this.buttonReputationToAttack.textbox);
        this.buttonReputationDeath = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 3, y, buttonWidth / 2, buttonHeight, this.fontRendererObj, this.npc.repOnDeath);
        this.buttonList.add(this.buttonReputationDeath);
        this.textFieldList.add(this.buttonReputationDeath.textbox);
        y += this.model.height + buttonSeparation;
        button = new GuiButtonDisplayString(0, x, y, buttonWidth / 2, buttonHeight, "Is invincible");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonWidth / 2 + buttonSeparation, y, buttonWidth / 2, buttonHeight, "Visible name");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 2, y, buttonWidth / 2, buttonHeight, "Can pick loot");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 3, y, buttonWidth / 2, buttonHeight, "Target monsters");
        this.buttonList.add(button);
        y += button.height;
        final String[] names = { "Yes", "No" };
        this.blocksMovement = new GuiButtonMultiOptions(0, x, y, buttonWidth / 2, buttonHeight, names, this.npc.isInvincible ? 0 : 1);
        this.buttonList.add(this.blocksMovement);
        this.visibleName = new GuiButtonMultiOptions(0, x + buttonWidth / 2 + buttonSeparation, y, buttonWidth / 2, buttonHeight, names, this.npc.getAlwaysRenderNameTag() ? 0 : 1);
        this.buttonList.add(this.visibleName);
        this.canPickLoot = new GuiButtonMultiOptions(0, x + buttonsGridWidth * 2, y, buttonWidth / 2, buttonHeight, names, this.npc.canPickUpLoot() ? 0 : 1);
        this.buttonList.add(this.canPickLoot);
        this.shouldTargetMonsters = new GuiButtonMultiOptions(0, x + buttonsGridWidth * 3, y, buttonWidth / 2, buttonHeight, names, this.npc.targetMobs ? 0 : 1);
        this.buttonList.add(this.shouldTargetMonsters);
        y += this.model.height + buttonSeparation;
        button = new GuiButtonDisplayString(0, x, y, buttonWidth / 2, buttonHeight, "Voice");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonsGridWidth, y, buttonWidth / 2, buttonHeight, "Can teleport");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 2, y, buttonWidth / 2, buttonHeight, "Health");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 3, y, buttonWidth / 2, buttonHeight, "Speed");
        this.buttonList.add(button);
        y += button.height;
        this.voice = new GuiButtonMultiOptions(0, x, y, buttonWidth / 2 - 13, buttonHeight, EnumVoice.getNames(), this.npc.voice);
        this.buttonList.add(this.voice);
        final GuiButton play = new GuiButtonIcon(this.playSound, x + 88, y + 2, 13.0f, 5.0f, 1.0f, 1.0f, "");
        this.buttonList.add(play);
        this.canTeleport = new GuiButtonMultiOptions(0, x + buttonsGridWidth, y, buttonWidth / 2, buttonHeight, names, this.npc.canTeleport ? 0 : 1);
        this.buttonList.add(this.canTeleport);
        final int health = (int)this.npc.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
        this.buttonHealth = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 2, y, buttonWidth / 2, buttonHeight, this.fontRendererObj, health);
        this.buttonList.add(this.buttonHealth);
        this.textFieldList.add(this.buttonHealth.textbox);
        final int speed = (int)(this.npc.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() * 1000.0);
        this.buttonSpeed = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 3, y, buttonWidth / 2, buttonHeight, this.fontRendererObj, speed);
        this.buttonList.add(this.buttonSpeed);
        this.textFieldList.add(this.buttonSpeed.textbox);
        y += this.model.height + buttonSeparation;
        button = new GuiButtonDisplayString(0, x, y, buttonWidth / 2, buttonHeight, "Home X");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonsGridWidth, y, buttonWidth / 2, buttonHeight, "Home Y");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 2, y, buttonWidth / 2, buttonHeight, "Home Z");
        this.buttonList.add(button);
        button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 3, y, buttonWidth / 2, buttonHeight, "Home radio");
        this.buttonList.add(button);
        y += this.model.height + buttonSeparation;
        final ChunkCoordinates coords = this.npc.getHomePosition();
        this.buttonHomeX = new GuiButtonTextBoxInteger(0, x, y, buttonWidth / 2, buttonHeight, this.fontRendererObj, coords.posX);
        this.buttonList.add(this.buttonHomeX);
        this.textFieldList.add(this.buttonHomeX.textbox);
        this.buttonHomeY = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 1, y, buttonWidth / 2, buttonHeight, this.fontRendererObj, coords.posY);
        this.buttonList.add(this.buttonHomeY);
        this.textFieldList.add(this.buttonHomeY.textbox);
        this.buttonHomeZ = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 2, y, buttonWidth / 2, buttonHeight, this.fontRendererObj, coords.posZ);
        this.buttonList.add(this.buttonHomeZ);
        this.textFieldList.add(this.buttonHomeZ.textbox);
        this.buttonHomeDistance = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 3, y, buttonWidth / 2, buttonHeight, this.fontRendererObj, (int)this.npc.getHomeDistance());
        this.buttonList.add(this.buttonHomeDistance);
        this.textFieldList.add(this.buttonHomeDistance.textbox);
    }
    
    public String[] getTextureNames() {
        String[] fileNames = { "pirate.png" };
        final Class clazz = ChocolateQuest.class;
        final String path = "assets/chocolatequest/textures/entity/biped";
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
                final ArrayList<String> list = new ArrayList<String>();
                while (entries.hasMoreElements()) {
                    final JarEntry entry = entries.nextElement();
                    if (!entry.isDirectory()) {
                        final String name = entry.getName();
                        if (!name.startsWith(path)) {
                            continue;
                        }
                        final String entryName = name.substring(path.length() + 1);
                        list.add(entryName);
                    }
                }
                final int size = list.size();
                if (list.size() > 0) {
                    fileNames = new String[size];
                    for (int i = 0; i < size; ++i) {
                        fileNames[i] = list.get(i);
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
        else {
            final File file = new File(dirURL.getFile());
            final File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                fileNames = new String[files.length];
                for (int j = 0; j < files.length; ++j) {
                    fileNames[j] = files[j].getName();
                }
            }
        }
        return fileNames;
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float fl) {
        super.drawScreen(x, y, fl);
        if (this.frontButton == null) {
            final int posX = 230;
            final int posY = -this.scrollAmmount + 20;
            Gui.drawRect(posX, posY + 3, posX + 58, posY + 76, -16777216);
            GuiHumanBase.drawEntity((EntityLivingBase)this.npc, posX + 28, posY + 60);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        super.actionPerformed(button);
        if (button == this.textureFileSelector) {
            final String s = ((GuiScrollOptions)button).getSelected();
            if (s != null) {
                this.texture.setText(s);
            }
            this.setFrontButton(null);
        }
        if (button == this.textureFile) {
            this.setFrontButton(this.textureFileSelector);
        }
        if (button.id == this.playSound) {
            final int voiceID = this.rand.nextInt(3);
            String voice = null;
            switch (voiceID) {
                case 1: {
                    voice = EnumVoice.getVoice(this.npc.voice).hurt;
                    break;
                }
                case 2: {
                    voice = EnumVoice.getVoice(this.npc.voice).death;
                    break;
                }
                default: {
                    voice = EnumVoice.getVoice(this.npc.voice).say;
                    break;
                }
            }
            this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(voice), 1.0f));
            this.mc.theWorld.playSoundAtEntity((Entity)this.mc.thePlayer, "chocolateQuest:handgun", 1.2f, 1.0f);
        }
        this.updateNPC();
    }
    
    public void onGuiClosed() {
        this.updateNPC();
        final IMessage packet = (IMessage)new PacketEditNPC(this.npc);
        ChocolateQuest.channel.sendPaquetToServer(packet);
        super.onGuiClosed();
    }
    
    public void updateNPC() {
        this.npc.name = this.name.getText();
        this.npc.displayName = this.displayName.getText();
        this.npc.texture = this.texture.getText();
        this.npc.modelType = this.model.value;
        this.npc.size = this.size.sliderValue;
        this.npc.isMale = (this.gender.value == 0);
        final float red = this.colorRed.sliderValue;
        final float green = this.colorGreen.sliderValue;
        final float blue = this.colorBlue.sliderValue;
        final int color = ((int)Math.min(red * 256.0f, 255.0f) & 0xFFFFFF) << 16 | (int)Math.min(green * 256.0f, 255.0f) << 8 | (int)Math.min(blue * 256.0f, 255.0f) << 0;
        this.npc.color = color;
        this.npc.entityTeam = this.faction.getText();
        this.npc.repToAttack = BDHelper.getIntegerFromString(this.buttonReputationToAttack.textbox.getText());
        this.npc.repOnDeath = BDHelper.getIntegerFromString(this.buttonReputationDeath.textbox.getText());
        this.npc.hasPlayerTexture = (this.usePlayerTexture.value == 0);
        this.npc.textureLocationPlayer = null;
        this.npc.resize();
        this.npc.isInvincible = (this.blocksMovement.value == 0);
        this.npc.setCanPickUpLoot(this.canPickLoot.value == 0);
        this.npc.setAlwaysRenderNameTag(this.visibleName.value == 0);
        this.npc.targetMobs = (this.shouldTargetMonsters.value == 0);
        this.npc.canTeleport = (this.canTeleport.value == 0);
        this.npc.voice = this.voice.value;
        final String health = this.buttonHealth.textbox.getText();
        this.npc.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)BDHelper.getIntegerFromString(health));
        final String speed = this.buttonSpeed.textbox.getText();
        this.npc.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(Math.min(0.5, BDHelper.getIntegerFromString(speed) * 0.001));
        final int x = BDHelper.getIntegerFromString(this.buttonHomeX.textbox.getText());
        final int y = BDHelper.getIntegerFromString(this.buttonHomeY.textbox.getText());
        final int z = BDHelper.getIntegerFromString(this.buttonHomeZ.textbox.getText());
        final int dist = BDHelper.getIntegerFromString(this.buttonHomeDistance.textbox.getText());
        this.npc.setHomeArea(x, y, z, dist);
    }
}
