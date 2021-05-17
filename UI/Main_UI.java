package UI;

import javax.swing.JFrame;

public class Main_UI {
    public JFrame frame = new JFrame("Main_UI");

    public void UI_init(String title) {
        frame.setTitle(title);
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}