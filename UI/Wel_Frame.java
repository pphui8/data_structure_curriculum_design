package UI;

import javax.swing.*;
import java.awt.*;

public class Wel_Frame extends Main_UI implements Model{

    private final JButton to_roadBuilder = new JButton("road builder");
    private final JButton to_User = new JButton("User");
    private final JLabel txt = new JLabel("请选择使用项目");
    public void init() {
        UI_init("sight point manager");

        to_roadBuilder.setPreferredSize(new Dimension(300, 170));
        to_User.setPreferredSize(new Dimension(300, 170));

        addListener();
        addItem();
        frame.setVisible(true);
    }

    public void addListener() {
        to_roadBuilder.addActionListener(e -> {
            new RoadBuilder_Frame().init();
            frame.setVisible(false);
        });
        to_User.addActionListener(e -> {
            //new Input.init();
            new Input().init();
            frame.setVisible(false);
        });
    }

    public void addItem() {
        frame.add(to_roadBuilder);
        frame.add(to_User, BorderLayout.SOUTH);
        frame.add(txt, BorderLayout.NORTH);
    }
}