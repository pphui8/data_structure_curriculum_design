package UI;

import data_structure.Prim;

import javax.swing.*;
import java.awt.*;

public class RoadBuilder_Frame extends Main_UI implements Model {
    JComboBox<String> numOfVex = new JComboBox<>();
    JLabel numOfVex_tip = new JLabel("请选择节点个数");
    private final JLabel road = new JLabel("请输入邻接表:");
    private final JTextArea road_input = new JTextArea();
    JScrollPane jsp = new JScrollPane(road_input);

    private final JLabel Vex_Name_tip = new JLabel("请按顺序输入节点名称：");
    private final JTextArea Vex_Name = new JTextArea();

    Box vex = Box.createVerticalBox();
    Box road_Box = Box.createVerticalBox();

    Box Vex_Map_Box = Box.createVerticalBox();

    JButton confirm = new JButton("确定");

    public void init() {
        UI_init("Road Builder");
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        addListener();
        addItem();
        frame.setVisible(true);
    }

    @Override
    public void addListener() {
        //此程序依靠52行及其bugs实现，不要乱动
        confirm.addActionListener(e -> {
            int Vex;
            try {
                Vex = Integer.parseInt(numOfVex.getEditor().getItem().toString());
            } catch(Exception Ignored) {
                Vex = 1;
            }
            //System.out.println(Vex);
            String Text1 = road_input.getText();
            String[] Text1s = Text1.split("\n");
            int[][] w = new int[Vex + 1][Vex + 1];
            for(int i = 0; i < Vex; ++i) {
                String[] tmp = Text1s[i].split(" ");
                for(int j = 0; j < Vex; ++j) w[i][j + 1] = Integer.parseInt(tmp[j], 0, tmp[j].length(), 10) - 32;
            }
            String Text2 = Vex_Name.getText();
            String[] Names = Text2.split("\n");
            String result = Prim.prim(Names, Vex, w);
            if(result != null) {
                JOptionPane.showMessageDialog(frame, result, "应修的路线为:", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "查无结果", "结果为:", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    @Override
    public void addItem() {
        for(int i = 1; i < 21; ++i) numOfVex.addItem(Integer.toString(i));
        vex.add(numOfVex_tip);
        vex.add(numOfVex);

        road_Box.add(road);
        road_Box.add(road_input);

        frame.add(vex, BorderLayout.NORTH);
        frame.add(road_Box);

        frame.add(confirm, BorderLayout.SOUTH);

        Vex_Map_Box.add(Vex_Name_tip);
        Vex_Map_Box.add(Vex_Name);
        frame.add(Vex_Map_Box, BorderLayout.WEST);
    }

    public void UI_init(String title) {
        frame.setTitle(title);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}