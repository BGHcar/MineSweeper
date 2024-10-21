package Game;

import javax.swing.*;

import Game.Buscaminas.GamePanel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] options = {"Principiante", "Intermedio", "Experto", "Personalizado"};
            int level = JOptionPane.showOptionDialog(null, "Seleccione el nivel de dificultad:",
                    "Buscaminas", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);
            if (level >= 0) {
                JFrame frame = new JFrame("Buscaminas");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new GamePanel(level + 1)); // Aumentamos en 1 para alinear con el caso
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
