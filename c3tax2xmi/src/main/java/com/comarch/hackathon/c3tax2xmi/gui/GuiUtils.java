package com.comarch.hackathon.c3tax2xmi.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

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
    
}
