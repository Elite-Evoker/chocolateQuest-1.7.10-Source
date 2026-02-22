package com.chocolate.chocolateQuest.client;

import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import net.minecraft.client.renderer.texture.TextureUtil;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.File;
import com.chocolate.chocolateQuest.utils.BDHelper;
import org.lwjgl.opengl.GL11;
import java.io.IOException;
import java.util.Map;

public class TextureExternal
{
    int glTextureId;
    static Map<String, TextureExternal> map;
    
    public TextureExternal() {
        this.glTextureId = -1;
    }
    
    public static void bindTexture(final String name) {
        TextureExternal texture = TextureExternal.map.get(name);
        if (texture == null) {
            texture = new TextureExternal();
            try {
                texture.loadTexture(name);
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }
        bindTexture(texture);
    }
    
    static void bindTexture(final TextureExternal texture) {
        GL11.glBindTexture(3553, texture.getGlTextureId());
    }
    
    public void loadTexture(final String name) throws IOException {
        InputStream inputstream = null;
        try {
            final File file = new File(BDHelper.getChocolateDir() + name);
            if (file.exists()) {
                inputstream = new FileInputStream(file);
                final BufferedImage bufferedimage = ImageIO.read(inputstream);
                final boolean blur = false;
                final boolean clamp = false;
                TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedimage, blur, clamp);
            }
            else {
                this.glTextureId = TextureUtil.missingTexture.getGlTextureId();
            }
        }
        finally {
            if (inputstream != null) {
                inputstream.close();
            }
        }
    }
    
    public int getGlTextureId() {
        if (this.glTextureId == -1) {
            this.glTextureId = TextureUtil.glGenTextures();
        }
        return this.glTextureId;
    }
    
    static {
        TextureExternal.map = new HashMap<String, TextureExternal>();
    }
}
