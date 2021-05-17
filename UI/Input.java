package UI;

import data_structure.GraphAdjList;
import plugin.View_Point;
import resource.xml_reader;

import javax.swing.*;
import java.awt.*;

public class Input extends Main_UI implements Model {

    private final JLabel road = new JLabel("请输入各条道路(以节点号命名)及其长度(权重):");
    private final JTextArea road_input = new JTextArea();
    JScrollPane jsp = new JScrollPane(road_input);

    private final JLabel Vex_Name_tip = new JLabel("请按顺序输入节点名称：");
    private final JTextArea Vex_Name = new JTextArea();

    Box vex = Box.createVerticalBox();
    Box road_Box = Box.createVerticalBox();

    Box Vex_Map_Box = Box.createVerticalBox();

    JButton confirm = new JButton("确定");
    JButton auto_Confirm = new JButton("读取已有数据");

    Box confirm_Box = Box.createHorizontalBox();

    public void init() {
        UI_init("Road Input");
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        addListener();
        addItem();
        frame.setVisible(true);
    }

    @Override
    public void addListener() {
        confirm.addActionListener(e -> {
            GraphAdjList<View_Point> map = new GraphAdjList<>(1000);
            String[] Names = Vex_Name.getText().split("\n");
            String[] tmp_Edges = road_input.getText().split("\n");
            for(String i : Names) {
                View_Point tmp = new View_Point(i);
                map.insertVex(tmp);
            }
            for(String i : tmp_Edges) {
                String[] tmp = i.split(" ");
                map.insertEdge(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
            }
            if(map.topoSort()) {
                String loop = map.Find_Loop();
                JOptionPane.showMessageDialog(frame, "查找到回路:" + loop, "wrong!", JOptionPane.WARNING_MESSAGE);
            }
            frame.setVisible(false);
            new User().init(map);
        });

        auto_Confirm.addActionListener(e -> {
            GraphAdjList<View_Point> map = xml_reader.read_xml();
            if(map.topoSort()) {
                String loop = map.Find_Loop();
                System.out.print(loop);
                JOptionPane.showMessageDialog(frame, "查找到回路:" + loop, "wrong!", JOptionPane.WARNING_MESSAGE);
            }
            frame.setVisible(false);
            new User().init(map);
        });
    }

    @Override
    public void addItem() {

        road_Box.add(road);
        road_Box.add(road_input);

        frame.add(vex, BorderLayout.NORTH);
        frame.add(road_Box);

        confirm_Box.add(confirm);
        confirm_Box.add(auto_Confirm);
        frame.add(confirm_Box, BorderLayout.SOUTH);


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