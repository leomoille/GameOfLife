package com.leomoille.gameoflife.view;

import com.leomoille.gameoflife.controller.GameController;
import com.leomoille.gameoflife.model.GameModel;
import com.leomoille.gameoflife.model.RuleStrategy;

import javax.swing.*;
import java.awt.*;

/**
 * The main application window (View).
 * <p>
 * Uses {@link java.awt.CardLayout} to switch between the Main Menu and the Game
 * View.
 * Assembles the {@link MenuPanel}, {@link GamePanel}, and {@link ControlPanel}.
 */
public class MainFrame extends JFrame implements GameView {

    private final GameModel model;
    private final GameController controller;

    private CardLayout cardLayout;
    private JPanel cardsPanel;

    private MenuPanel menuPanel;
    // Holds ScrollPane + Controls
    private JPanel gameContainerPanel;

    private static final String MENU_VIEW = "MENU";
    private static final String GAME_VIEW = "GAME";

    public MainFrame(GameController controller, GameModel model) {
        this.controller = controller;
        this.model = model;
        this.initUI();
    }

    private void initUI() {
        this.setTitle("Game of Life - Portfolio Showcase");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.cardLayout = new CardLayout();
        this.cardsPanel = new JPanel(this.cardLayout);

        // 1. Menu View
        this.menuPanel = new MenuPanel(this);
        this.cardsPanel.add(this.menuPanel, MENU_VIEW);

        // 2. Game View
        this.gameContainerPanel = this.createGameView();
        this.cardsPanel.add(this.gameContainerPanel, GAME_VIEW);

        this.add(this.cardsPanel);

        // Start minimal size
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }

    private JPanel createGameView() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Game Panel
        GamePanel gamePanel = new GamePanel(this.controller);
        gamePanel.setModel(this.model);

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(Color.DARK_GRAY); // Background for empty space
        wrapperPanel.add(gamePanel); // Centered by default in GridBag

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null); // Optional: clean look

        // Control Panel
        ControlPanel controlPanel = new ControlPanel(this.controller);
        controlPanel.setModel(this.model);

        JButton backButton = new JButton("Menu");
        backButton.addActionListener(e -> this.showMenu());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(backButton, BorderLayout.WEST);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    public void showGame(int width, int height, RuleStrategy rule) {
        this.controller.pauseGame(); // Safety
        this.controller.onResizeGrid(width, height);
        this.controller.changeRule(rule);

        this.cardLayout.show(this.cardsPanel, GAME_VIEW);
    }

    public void showMenu() {
        this.controller.pauseGame();
        this.cardLayout.show(this.cardsPanel, MENU_VIEW);
    }

    @Override
    public void refresh() {
        this.repaint();
    }
}
