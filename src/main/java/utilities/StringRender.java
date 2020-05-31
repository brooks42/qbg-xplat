/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.awt.*;

public class StringRender {

    /** The fonts to draw to the screen */
    public static TrueTypeFont fontB24,  fontB12,  font24,  font12, fontI12;
    /** Boolean flag on whether AntiAliasing is enabled or not */
    private static boolean antiAlias = true;
    private static StringRender instance = null;

    /**
     * Initialise resources
     */
    protected StringRender() {
        // load a default java font
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        fontB24 = new TrueTypeFont(awtFont, antiAlias);
        awtFont = new Font("Times New Roman", Font.BOLD, 12);
        fontB12 = new TrueTypeFont(awtFont, antiAlias);
        awtFont = new Font("Times New Roman", Font.PLAIN, 12);
        font12 = new TrueTypeFont(awtFont, antiAlias);
        awtFont = new Font("Times New Roman", Font.PLAIN, 24);
        font24 = new TrueTypeFont(awtFont, antiAlias);
        awtFont = new Font("Times New Roman", Font.ITALIC, 12);
        fontI12 = new TrueTypeFont(awtFont, antiAlias);
    }
    
    /**
     * Returns a Font with the passed size and style (use Font.STUFF for style)
     * 
     * @param size
     * @param style
     * @return
     */
    public static TrueTypeFont makeFont(int size, int style){
        Font font = new Font("Times New Roman", style, size);
        return new TrueTypeFont(font, antiAlias);
    }

    /**
     * Returns an instance of this RenderHelper.
     * 
     * @return
     */
    public static StringRender getInstance() {
        if (instance == null) {
            instance = new StringRender();
        }
        return instance;
    }

    /**
     * Draw a string.
     */
    public static void drawString(TrueTypeFont font, String msg, int x, int y, Color color) {
        getInstance();
        if (font == null) {
            return;
        }
//        Color.white.bind();
        font.drawString(x, y, msg, color);
//        Color.white.bind();
    }
    
    /**
     * Draw a string.
     */
    public static void drawString(TrueTypeFont font, String msg, int x, int y, Color color, int alpha) {
        getInstance();
        if (font == null) {
            return;
        }
//        Color.white.bind();
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        font.drawString(x, y, msg, color);
//        Color.white.bind();
    }

    // TODO: this needs to be redone...
    static class TrueTypeFont {

        TrueTypeFont(Font font, boolean antialias) {

        }

        void drawString(int x, int y, String msg, Color color) {
            // no-op for now
        }
    }
}
