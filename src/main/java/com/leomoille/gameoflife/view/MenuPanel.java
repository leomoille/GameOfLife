package com.leomoille.gameoflife.view;

import com.leomoille.gameoflife.model.ConwayRules;
import com.leomoille.gameoflife.model.HighLifeRules;
import com.leomoille.gameoflife.model.RuleStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

/**
 * The introductory menu panel.
 * Allows the user to select the grid size and game rules before starting.
 */
public class MenuPanel extends JPanel {
    private final MainFrame mainFrame;

    private JComboBox<RuleItem> rulesComboBox;
    private JComboBox<SizeItem> sizeComboBox;

    private JPanel customSizePanel;
    private JTextField widthField;
    private JTextField heightField;

    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.initUI();
    }

    /**
     * Initializes the UI components using GridBagLayout.
     */
    private void initUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Game of Life", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(titleLabel, gbc);

        // Rules Selection
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        this.add(new JLabel("Rules:"), gbc);

        this.rulesComboBox = new JComboBox<>();
        this.rulesComboBox.addItem(new RuleItem(new ConwayRules()));
        this.rulesComboBox.addItem(new RuleItem(new HighLifeRules()));
        gbc.gridx = 1;
        this.add(this.rulesComboBox, gbc);

        // Size Selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Grid Size:"), gbc);

        this.sizeComboBox = new JComboBox<>();
        this.sizeComboBox.addItem(new SizeItem("Small (50x50)", 50, 50));
        this.sizeComboBox.addItem(new SizeItem("Medium (100x100)", 100, 100));
        this.sizeComboBox.addItem(new SizeItem("Large (200x200)", 200, 200));
        this.sizeComboBox.addItem(new SizeItem("Custom...", -1, -1)); // Special item

        this.sizeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                SizeItem item = (SizeItem) e.getItem();
                this.customSizePanel.setVisible(item.width == -1);
                this.revalidate(); // Refresh layout
                this.repaint();
            }
        });

        gbc.gridx = 1;
        this.add(this.sizeComboBox, gbc);

        // Custom Size Fields (Hidden by default)
        this.customSizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        this.widthField = new JTextField("100", 4);
        this.heightField = new JTextField("100", 4);
        this.customSizePanel.add(new JLabel("W:"));
        this.customSizePanel.add(this.widthField);
        this.customSizePanel.add(new JLabel("H:"));
        this.customSizePanel.add(this.heightField);
        this.customSizePanel.setVisible(false);

        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(this.customSizePanel, gbc);

        // Start Button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        startButton.addActionListener(e -> this.startGame());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(startButton, gbc);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 5;
        this.add(exitButton, gbc);
    }

    private void startGame() {
        RuleItem selectedRule = (RuleItem) this.rulesComboBox.getSelectedItem();
        SizeItem selectedSize = (SizeItem) this.sizeComboBox.getSelectedItem();

        if (selectedRule == null || selectedSize == null)
            return;

        int width, height;

        if (selectedSize.width == -1) {
            // Custom Size
            try {
                width = Integer.parseInt(this.widthField.getText().trim());
                height = Integer.parseInt(this.heightField.getText().trim());
                if (width <= 0 || height <= 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid positive integers for dimensions.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            width = selectedSize.width;
            height = selectedSize.height;
        }

        this.mainFrame.showGame(width, height, selectedRule.strategy);
    }

    // Helper classes for ComboBox items
    private static class RuleItem {
        RuleStrategy strategy;

        RuleItem(RuleStrategy strategy) {
            this.strategy = strategy;
        }

        @Override
        public String toString() {
            return this.strategy.getName();
        }
    }

    private static class SizeItem {
        String name;
        int width;
        int height;

        SizeItem(String name, int width, int height) {
            this.name = name;
            this.width = width;
            this.height = height;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
