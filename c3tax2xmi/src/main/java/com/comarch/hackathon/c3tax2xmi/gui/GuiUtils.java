package com.comarch.hackathon.c3tax2xmi.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Szlachtap
 */
public class GuiUtils {
    
    public static void centreOnScreen(Component componentToMove) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        componentToMove.setLocation((screenSize.width - componentToMove.getWidth()) / 2,
                (screenSize.height - componentToMove.getHeight()) / 2);
    }
    
    public static Image loadImage(String iconName) {
        URL resource = GuiUtils.class.getResource(iconName);
        if (resource != null) {
            ImageIcon imageIcon = new ImageIcon(resource);
            if (imageIcon.getImage() != null && imageIcon.getIconHeight() > 0) {
                return imageIcon.getImage();
            }
        }
        return null;
    }
    
}
