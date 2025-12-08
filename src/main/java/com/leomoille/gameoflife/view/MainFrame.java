package com.leomoille.gameoflife.view;

import com.leomoille.gameoflife.controller.GameController;
import com.leomoille.gameoflife.model.GameModel;
import com.leomoille.gameoflife.model.RuleStrategy;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements GameView {

    private GameModel model;
    private GameController controller;

    private CardLayout cardLayout;
    private JPanel cardsPanel;

    private MenuPanel menuPanel;
    private JPanel gameContainerPanel; // Holds ScrollPane + Controls

    private static final String MENU_VIEW = "MENU";
    private static final String GAME_VIEW = "GAME";

    public MainFrame(GameController controller, GameModel model) {
        this.controller = controller;
        this.model = model;
        initUI();
    }

    private void initUI() {
        setTitle("Game of Life - Portfolio Showcase");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        // 1. Menu View
        menuPanel = new MenuPanel(this);
        cardsPanel.add(menuPanel, MENU_VIEW);

        // 2. Game View
        gameContainerPanel = createGameView();
        cardsPanel.add(gameContainerPanel, GAME_VIEW);

        this.add(cardsPanel);

        // Start minimal size
        this.setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private JPanel createGameView() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Game Panel
        GamePanel gamePanel = new GamePanel(controller);
        gamePanel.setModel(model);

        // Centering Wrapper: JPanel with GridBagLayout inside ScrollPane
        // "La grille devrait être centrée si elle est plus petite que la largeur de
        // l'écran."
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(Color.DARK_GRAY); // Background for empty space
        wrapperPanel.add(gamePanel); // Centered by default in GridBag

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null); // Optional: clean look

        // Control Panel
        ControlPanel controlPanel = new ControlPanel(controller);
        controlPanel.setModel(model);

        // Add "Back to Menu" button to ControlPanel or Toolbar?
        // Let's add it to ControlPanel or a top bar.
        // User asked "Retourner au menu".
        JButton backButton = new JButton("Menu");
        backButton.addActionListener(e -> showMenu());
        // Hack: adding to ControlPanel isn't clean since ControlPanel is its own class.
        // Let's put a Top Bar or add to Control Panel via getter?
        // Or just re-instantiate ControlPanel?
        // Better: Wrapper for controls.
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(backButton, BorderLayout.WEST);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    public void showGame(int width, int height, RuleStrategy rule) {
        controller.pauseGame(); // Safety
        controller.onResizeGrid(width, height);
        controller.changeRule(rule);

        cardLayout.show(cardsPanel, GAME_VIEW);
    }

    public void showMenu() {
        controller.pauseGame();
        cardLayout.show(cardsPanel, MENU_VIEW);
    }

    @Override
    public void refresh() {
        this.repaint();
    }
}
