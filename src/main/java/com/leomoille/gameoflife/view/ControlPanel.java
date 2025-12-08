package com.leomoille.gameoflife.view;

import com.leomoille.gameoflife.controller.GameController;
import com.leomoille.gameoflife.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panel containing game controls (Start, Reset, Randomize, Speed).
 * Observes the GameModel to update generation count.
 */
public class ControlPanel extends JPanel implements PropertyChangeListener {
    private final GameController controller;
    private JButton startPauseButton;
    private JLabel generationLabel;

    public ControlPanel(GameController controller) {
        this.controller = controller;
        this.initUI();
    }

    public void setModel(GameModel model) {
        model.addPropertyChangeListener(this);
    }

    private void initUI() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        this.startPauseButton = new JButton("Start");
        this.startPauseButton.addActionListener(e -> {
            this.controller.togglePause();

            // Update button text to reflect next action
            if (this.startPauseButton.getText().equals("Start")) {
                this.startPauseButton.setText("Pause");
            } else {
                this.startPauseButton.setText("Start");
            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            this.controller.resetGame();
            // Reset button state
            this.startPauseButton.setText("Start");
        });

        JButton randomButton = new JButton("Randomize");
        randomButton.addActionListener(e -> this.controller.randomize());

        JSlider speedSlider = new JSlider(10, 500, 100);
        speedSlider.setInverted(true); // Lower delay = faster
        speedSlider.addChangeListener(e -> this.controller.setSpeed(speedSlider.getValue()));

        this.generationLabel = new JLabel("Gen: 0");

        this.add(this.startPauseButton);
        this.add(resetButton);
        this.add(randomButton);
        this.add(new JLabel("Speed:"));
        this.add(speedSlider);
        this.add(this.generationLabel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("generation".equals(evt.getPropertyName())) {
            int gen = (Integer) evt.getNewValue();
            this.generationLabel.setText("Gen: " + gen);
        }
    }
}
