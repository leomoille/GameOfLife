package com.leomoille.gameoflife.view;

import com.leomoille.gameoflife.model.ConwayRules;
import com.leomoille.gameoflife.model.HighLifeRules;
import com.leomoille.gameoflife.model.RuleStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

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
        add(titleLabel, gbc);

        // Rules Selection
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(new JLabel("Rules:"), gbc);

        rulesComboBox = new JComboBox<>();
        rulesComboBox.addItem(new RuleItem(new ConwayRules()));
        rulesComboBox.addItem(new RuleItem(new HighLifeRules()));
        gbc.gridx = 1;
        add(rulesComboBox, gbc);

        // Size Selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Grid Size:"), gbc);

        sizeComboBox = new JComboBox<>();
        sizeComboBox.addItem(new SizeItem("Small (50x50)", 50, 50));
        sizeComboBox.addItem(new SizeItem("Medium (100x100)", 100, 100));
        sizeComboBox.addItem(new SizeItem("Large (200x200)", 200, 200));
        sizeComboBox.addItem(new SizeItem("Custom...", -1, -1)); // Special item

        sizeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                SizeItem item = (SizeItem) e.getItem();
                customSizePanel.setVisible(item.width == -1);
                revalidate(); // Refresh layout
                repaint();
            }
        });

        gbc.gridx = 1;
        add(sizeComboBox, gbc);

        // Custom Size Fields (Hidden by default)
        customSizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        widthField = new JTextField("100", 4);
        heightField = new JTextField("100", 4);
        customSizePanel.add(new JLabel("W:"));
        customSizePanel.add(widthField);
        customSizePanel.add(new JLabel("H:"));
        customSizePanel.add(heightField);
        customSizePanel.setVisible(false);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(customSizePanel, gbc);

        // Start Button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        startButton.addActionListener(e -> startGame());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(startButton, gbc);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 5;
        add(exitButton, gbc);
    }

    private void startGame() {
        RuleItem selectedRule = (RuleItem) rulesComboBox.getSelectedItem();
        SizeItem selectedSize = (SizeItem) sizeComboBox.getSelectedItem();

        if (selectedRule == null || selectedSize == null)
            return;

        int width, height;

        if (selectedSize.width == -1) {
            // Custom Size
            try {
                width = Integer.parseInt(widthField.getText().trim());
                height = Integer.parseInt(heightField.getText().trim());
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

        mainFrame.showGame(width, height, selectedRule.strategy);
    }

    // Helper classes for ComboBox items
    private static class RuleItem {
        RuleStrategy strategy;

        RuleItem(RuleStrategy strategy) {
            this.strategy = strategy;
        }

        @Override
        public String toString() {
            return strategy.getName();
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
            return name;
        }
    }
}
