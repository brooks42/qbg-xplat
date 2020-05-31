/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import utilities.ImageManager;

/**
 * @author User
 */
public class SpriteFactory {

    private final AssetManager assetManager;

    public SpriteFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Node getSprite(Texture2D text, int x, int y, int x2, int y2) {
        return constructNodeForTextureAndBounds(text, x, y, x2, y2);
    }

    private Node constructNodeForTextureAndBounds(Texture2D texture, int x, int y, int x2, int y2) {
        Node node = new Node("test");

        Picture pic = getSpritePicture(texture, x2 - x, y2 - y);

        // TODO: uh oh I don't actually have any of this since I'm not using JME
//        Material picMat = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
//        picMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.AlphaAdditive);
//        node.setMaterial(picMat);

        node.attachChild(pic);
        node.move(x, y, 0);
        return node;
    }

    private Picture getSpritePicture(Texture2D texture, int width, int height) {

        Picture pic = new Picture("test");
        pic.setTexture(assetManager, texture, true);

        pic.setWidth(width);
        pic.setHeight(height);
        pic.move(-width / 2f, -height / 2f, 0);

        return pic;
    }
}
