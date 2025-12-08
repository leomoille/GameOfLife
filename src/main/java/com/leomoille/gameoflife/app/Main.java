package com.leomoille.gameoflife.app;

import com.leomoille.gameoflife.controller.GameController;
import com.leomoille.gameoflife.model.GameEngine;
import com.leomoille.gameoflife.model.GameModel;
import com.leomoille.gameoflife.view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set Look and Feel to System default for better integration
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // assembling the object graph
            GameModel model = new GameModel(50, 50);
            GameEngine engine = new GameEngine(model);
            GameController controller = new GameController(model, engine);

            MainFrame frame = new MainFrame(controller, model);

            // Circular dependency: Controller needs View to refresh
            controller.setView(frame);

            frame.setVisible(true);
        });
    }
}
