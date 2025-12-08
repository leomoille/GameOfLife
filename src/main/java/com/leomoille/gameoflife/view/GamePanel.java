package com.leomoille.gameoflife.view;

import com.leomoille.gameoflife.controller.GameController;
import com.leomoille.gameoflife.model.GameModel;
import com.leomoille.gameoflife.model.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GamePanel extends JPanel implements PropertyChangeListener {
    private GameController controller;
    private Grid grid;

    // Default pixel size per Cell.
    private int cellSize = 20;
    private boolean showGridLines = true;

    // Limits
    private static final int MIN_CELL_SIZE = 2;
    private static final int MAX_CELL_SIZE = 100;

    public GamePanel(GameController controller) {
        this.controller = controller;
        this.setBackground(Color.BLACK);

        // Manage mouse actions
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);

                // Zoom if Control/Meta key is down,
                if (e.isControlDown() || e.isMetaDown()) {
                    int rotation = e.getWheelRotation();
                    if (rotation < 0) {
                        zoomIn();
                    } else {
                        zoomOut();
                    }
                } else {
                    getParent().dispatchEvent(e);
                }
            }
        };

        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        this.addMouseWheelListener(mouseHandler);
    }

    public void zoomIn() {
        if (this.cellSize < MAX_CELL_SIZE) {
            this.cellSize++;
            this.updatePreferredSize();
            this.revalidate();
            this.repaint();
        }
    }

    public void zoomOut() {
        if (this.cellSize > MIN_CELL_SIZE) {
            this.cellSize--;
            this.updatePreferredSize();
            this.revalidate();
            this.repaint();
        }
    }

    public void setModel(GameModel model) {
        this.grid = model.getGrid();
        model.addPropertyChangeListener(this);
        this.updatePreferredSize();
    }

    private void updatePreferredSize() {
        if (grid != null) {
            int width = this.grid.getWidth() * this.cellSize;
            int height = this.grid.getHeight() * this.cellSize;
            this.setPreferredSize(new Dimension(width, height));
        }
    }

    private void handleMouseClick(MouseEvent e) {
        if (grid == null) {
            return;
        }

        int x = e.getX() / this.cellSize;
        int y = e.getY() / this.cellSize;

        if (x >= 0 && x < this.grid.getWidth() && y >= 0 && y < this.grid.getHeight()) {
            this.controller.onCellClicked(x, y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.grid == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        int width = this.grid.getWidth();
        int height = this.grid.getHeight();

        Rectangle clip = g.getClipBounds();

        int startX = 0;
        int startY = 0;
        int endX = width;
        int endY = height;

        if (clip != null) {
            startX = Math.max(0, clip.x / this.cellSize);
            startY = Math.max(0, clip.y / this.cellSize);
            endX = Math.min(width, (clip.x + clip.width) / this.cellSize + 1);
            endY = Math.min(height, (clip.y + clip.height) / this.cellSize + 1);
        }

        g2d.setColor(Color.GREEN);

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                if (this.grid.getCell(x, y).isAlive()) {
                    g2d.fillRect(x * this.cellSize, y * this.cellSize, this.cellSize, this.cellSize);
                }
            }
        }

        if (this.showGridLines && cellSize > 2) {
            g2d.setColor(Color.DARK_GRAY);
            // Draw grid lines only in visible area
            for (int y = startY; y <= endY; y++) {
                g2d.drawLine(startX * this.cellSize, y * this.cellSize, endX * this.cellSize, y * this.cellSize);
            }
            for (int x = startX; x <= endX; x++) {
                g2d.drawLine(x * this.cellSize, startY * this.cellSize, x * this.cellSize, endY * this.cellSize);
            }
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("grid".equals(evt.getPropertyName())) {
            Grid newGrid = (Grid) evt.getNewValue();

            if (newGrid != null) {
                this.grid = newGrid;

                this.updatePreferredSize();
                this.revalidate();
                this.repaint();
            }
        }
    }

    public void setCellSize(int size) {
        this.cellSize = size;
        this.updatePreferredSize();
        this.repaint();
    }
}
