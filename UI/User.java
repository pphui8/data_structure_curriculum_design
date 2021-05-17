package UI;

import data_structure.GraphAdjList;
import data_structure.MyQueue;
import plugin.View_Point;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class User extends Main_UI implements Model {
    GraphAdjList<View_Point> map;
    //private JPanel map = new JPanel();
    private final JButton Recommend_Route = new JButton("查看推荐路线");
    private final JButton Find_route = new JButton("查找路线");
    private final JPanel Find_road_txt1 = new JPanel();
    private final JPanel Find_road_txt2 = new JPanel();
    private final Box Find_road_txt = Box.createVerticalBox();
    private final JLabel start_txt = new JLabel("始发地");
    private final JLabel end_txt = new JLabel("目的地");
    private final JComboBox<String> start_vex = new JComboBox<>();
    private final JComboBox<String> end_vex = new JComboBox<>();

    @Override
    public void UI_init(String title) {
        frame.setTitle(title);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void init(GraphAdjList<View_Point> map) {
        this.UI_init("旅游导游服务窗口");
        this.map = map;
        init();

        addListener();
        addItem();
        frame.setVisible(true);
    }

        @Override
        public void init() {
            map.Floyd();
            for(int i = 0; i < map.getNumOfVertex(); ++i) {
                start_vex.addItem(map.valueOfVex(i).toString());
                end_vex.addItem(map.valueOfVex(i).toString());
            }
        }

        @Override
        public void addListener() {
            MyQueue<Integer> q = map.depthFirstSearch(0);
            StringBuilder sb = new StringBuilder();
            while(!q.isEmpty()) {
                sb.append(map.valueOfVex(q.pop()).toString()).append("->");
            }
            Recommend_Route.addActionListener(e -> JOptionPane.showMessageDialog(frame, sb.substring(0, sb.length() - 2), "查询结果", JOptionPane.INFORMATION_MESSAGE));
            Find_route.addActionListener(e -> {
                String start = start_vex.getEditor().getItem().toString();
                String end = end_vex.getEditor().getItem().toString();
                if(start == null) start = map.valueOfVex(0).toString();
                if(end == null) end = map.valueOfVex(map.getNumOfVertex()).toString();
                if(Objects.equals(start, end)) JOptionPane.showMessageDialog(frame, "请选择两个不同的地点", "查询结果", JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(frame, map.Find_road(start, end), "查询结果", JOptionPane.INFORMATION_MESSAGE);
            });

        }

        @Override
        public void addItem() {
            Find_road_txt1.add(start_txt);
            Find_road_txt1.add(start_vex);
            Find_road_txt1.add(end_txt);
            Find_road_txt1.add(end_vex);
            Find_road_txt2.add(Find_route);
            Find_road_txt.add(Find_road_txt1);
            Find_road_txt.add(Find_road_txt2);
            frame.add(Find_road_txt, BorderLayout.SOUTH);

            frame.add(Recommend_Route, BorderLayout.CENTER);
        }

}
