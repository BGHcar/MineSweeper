package Game.Buscaminas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private Minefield minefield;
    private JButton[][] buttons;
    private int rows;
    private int cols;
    private int totalMines;
    private JLabel timerLabel;
    private volatile boolean running;
    private int timeElapsed;

    public GamePanel(int level) {
        setLayout(new BorderLayout());
        timerLabel = new JLabel("Tiempo: 0", SwingConstants.CENTER);
        add(timerLabel, BorderLayout.NORTH);
        initializeLevel(level);
        createButtons();
        createRestartButton(); // Agregar botón de reinicio
        startTimer();
    }

    private void initializeLevel(int level) {
        switch (level) {
            case 1: // Principiante
                rows = 8;
                cols = 8;
                totalMines = 10;
                break;
            case 2: // Intermedio
                rows = 16;
                cols = 16;
                totalMines = 40;
                break;
            case 3: // Experto
                rows = 16;
                cols = 30;
                totalMines = 99;
                break;
            case 4: // Personalizado
                rows = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de filas:"));
                cols = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de columnas:"));
                totalMines = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de minas:"));
                break;
        }
        minefield = new Minefield(rows, cols, totalMines);
        buttons = new JButton[rows][cols];
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel(new GridLayout(rows, cols));
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                buttons[r][c] = new JButton();
                buttons[r][c].setPreferredSize(new Dimension(50, 50));
                buttons[r][c].addActionListener(new ButtonClickListener(r, c));
                buttonPanel.add(buttons[r][c]);
            }
        }
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void createRestartButton() {
        JButton restartButton = new JButton("Reiniciar");
        restartButton.addActionListener(new RestartButtonListener());
        add(restartButton, BorderLayout.SOUTH); // Agregar el botón al panel
    }

    private void startTimer() {
        running = true;
        timeElapsed = 0;
        new Thread(() -> {
            try {
                while (running) {
                    Thread.sleep(1000);
                    timeElapsed++;
                    timerLabel.setText("Tiempo: " + timeElapsed);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private class ButtonClickListener implements ActionListener {
        private final int row;
        private final int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (minefield.isMine(row, col)) {
                revealMines();
                running = false;
                JOptionPane.showMessageDialog(GamePanel.this, "¡Perdiste! Tiempo: " + timeElapsed + " segundos");
                disableButtons(); // Deshabilitar botones al perder
            } else {
                revealCell(row, col);
            }
        }

        private void revealCell(int r, int c) {
            if (r < 0 || c < 0 || r >= rows || c >= cols) return;
            if (minefield.getCell(r, c).isRevealed()) return;

            minefield.getCell(r, c).reveal();
            buttons[r][c].setText(minefield.getCell(r, c).getAdjacentMines() == 0 ? "" : String.valueOf(minefield.getCell(r, c).getAdjacentMines()));
            buttons[r][c].setBackground(Color.LIGHT_GRAY); // Cambiar color al descubrir

            if (minefield.getCell(r, c).getAdjacentMines() == 0) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        revealCell(r + i, c + j);
                    }
                }
            }
        }

        private void revealMines() {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (minefield.isMine(r, c)) {
                        buttons[r][c].setText("M");
                        buttons[r][c].setBackground(Color.RED); // Cambiar color para resaltar
                    } else {
                        buttons[r][c].setBackground(Color.LIGHT_GRAY); // Cambiar color para resaltar
                    }
                }
            }
            running = false; // Detener el temporizador
        }
    }

    private void disableButtons() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                buttons[r][c].setEnabled(false); // Deshabilitar botones
            }
        }
    }

    private class RestartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reiniciar el juego
            removeAll(); // Limpiar el panel
            revalidate();
            repaint();
            int level = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el nivel (1: Principiante, 2: Intermedio, 3: Experto, 4: Personalizado):"));
            new GamePanel(level); // Crear un nuevo panel de juego
        }
    }
}
