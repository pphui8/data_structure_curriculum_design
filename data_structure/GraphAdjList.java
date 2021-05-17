package data_structure;

import java.lang.reflect.Array;
import java.util.*;

public class GraphAdjList<E> implements IGraph<E> {
    private static final int MAX_VAL = 1000000000;
    // 链表中的节点
    private static class ENode {
        int adjvex; // 邻接顶点序号
        int weight;// 存储边或弧相关的信息，如权值
        ENode nextadj; // 下一个邻接表结点

        public ENode(int adjvex, int weight) {
            this.adjvex = adjvex;
            this.weight = weight;
        }
    }
    // 数组中的节点
    private static class VNode<E> {
        E data; // 顶点信息
        ENode firstadj; // //邻接表的第1个结点
    }

    private VNode<E>[] vexs; // 顶点数组
    private int numOfVexs;// 顶点的实际数量
    private int maxNumOfVexs;// 顶点的最大数量
    private boolean[] visited;// 判断顶点是否被访问过
    public int[][] Adjacency_Matrix;
    public int path[][]; //路径
    public int dist[][]; //距离

    //构造函数
    public GraphAdjList(int maxNumOfVexs) {
        this.maxNumOfVexs = maxNumOfVexs;
        vexs = (VNode<E>[]) Array.newInstance(VNode.class, maxNumOfVexs);
    }

    // 得到顶点的数目
    @Override
    public int getNumOfVertex() {
        return numOfVexs;
    }

    // 插入顶点
    @Override
    public boolean insertVex(E v) {
        if(numOfVexs >= maxNumOfVexs) return false;
        VNode<E> vex = new VNode<>();
        vex.data = v;
        vexs[numOfVexs++] = vex;
        return true;
    }

    @Override
    public void to_Adjacency_Matrix() {
        Adjacency_Matrix = new int[numOfVexs][numOfVexs];
        for(int i = 0; i < numOfVexs; ++i)
            for(int j = 0; j < numOfVexs; ++j) Adjacency_Matrix[i][j] = 0;
        for(int i = 0; i < numOfVexs; ++i) {
            VNode<E> tmp = vexs[i];
            ENode ttmp = tmp.firstadj;
            while(ttmp != null) Adjacency_Matrix[i][ttmp.adjvex] = ttmp.weight;
        }
    }

    // 删除顶点
    @Override
    public boolean deleteVex(E v) {
        for(int i = 0; i < numOfVexs; i++) {
            if(vexs[i].data.equals(v)) {
                if(numOfVexs - 1 - i >= 0) System.arraycopy(vexs, i + 1, vexs, i, numOfVexs - 1 - i);
                vexs[numOfVexs - 1] = null;
                numOfVexs--;
                ENode current;
                ENode previous;
                for(int j = 0; j < numOfVexs; j++) {
                    if(vexs[j].firstadj == null)
                        continue;
                    if(vexs[j].firstadj.adjvex == i) {
                        vexs[j].firstadj = null;
                        continue;
                    }
                    current = vexs[j].firstadj;
                    while(current != null) {
                        previous = current;
                        current = current.nextadj;
                        if (current != null && current.adjvex == i) {
                            previous.nextadj = current.nextadj;
                            break;
                        }
                    }
                }
                for(int j = 0; j < numOfVexs; j++) {
                    current = vexs[j].firstadj;
                    while(current != null) {
                        if(current.adjvex > i) current.adjvex--;
                        current = current.nextadj;
                    }
                }
                return true;
            }
        }
        return false;
    }

    // 定位顶点的位置
    @Override
    public int indexOfVex(E v) {
        for(int i = 0; i < numOfVexs; i++) {
            if(vexs[i].data.equals(v)) {
                return i;
            }
        }
        return -1;
    }

    // 定位指定位置的顶点
    @Override
    public E valueOfVex(int v) {
        if (v < 0 || v >= numOfVexs)
            return null;
        return vexs[v].data;
    }

    // 插入边
    @Override
    public boolean insertEdge(int v1, int v2, int weight) {
        if(v1 < 0 || v2 < 0 || v1 >= numOfVexs || v2 >= numOfVexs) throw new ArrayIndexOutOfBoundsException();
        ENode vex1 = new ENode(v2, weight);

        // 索引为index1的顶点没有邻接顶点
        if(vexs[v1].firstadj != null) {
            vex1.nextadj = vexs[v1].firstadj;
        }
        vexs[v1].firstadj = vex1;

        /*ENode vex2 = new ENode(v1, weight);
        // 索引为index2的顶点没有邻接顶点
        if(vexs[v2].firstadj != null) {
            vex2.nextadj = vexs[v2].firstadj;
        }
        vexs[v2].firstadj = vex2;*/
        return true;
    }

    // 删除边
    @Override
    public boolean deleteEdge(int v1, int v2) {
        if(v1 < 0 || v2 < 0 || v1 >= numOfVexs || v2 >= numOfVexs) throw new ArrayIndexOutOfBoundsException();
        // 删除索引为index1的顶点与索引为index2的顶点之间的边
        ENode current = vexs[v1].firstadj;
        ENode previous = null;
        while(current != null && current.adjvex != v2) {
            previous = current;
            current = current.nextadj;
        }
        if(current != null) {
            previous.nextadj = current.nextadj;
        }
        // 删除索引为index2的顶点与索引为index1的顶点之间的边
        current = vexs[v2].firstadj;
        while(current != null && current.adjvex != v1) {
            previous = current;
            current = current.nextadj;
        }
        if(current != null) {
            assert previous != null;
            previous.nextadj = current.nextadj;
        }
        return true;
    }

    // 查找边
    @Override
    public int getEdge(int v1, int v2) {
        if(v1 < 0 || v2 < 0 || v1 >= numOfVexs || v2 >= numOfVexs) throw new ArrayIndexOutOfBoundsException();
        ENode current = vexs[v1].firstadj;
        while(current != null) {
            if(current.adjvex == v2) {
                return current.weight;
            }
            current = current.nextadj;
        }
        return 0;
    }

    // 深度优先搜索遍历
    @Override
    public MyQueue<Integer> depthFirstSearch(int v) {
        if (v < 0 || v >= numOfVexs) throw new ArrayIndexOutOfBoundsException();
        visited = new boolean[numOfVexs];
        MyQueue<Integer> q = new MyQueue<>();
        MyQueue<Integer> stack = new MyQueue<Integer>();
        stack.push(v);
        visited[v] = true;
        ENode current;
        while(!stack.isEmpty()) {
            v = stack.pop();
            q.push(v);
            current = vexs[v].firstadj;
            while(current != null) {
                if(!visited[current.adjvex]) {
                    stack.push(current.adjvex);
                    visited[current.adjvex] = true;
                }
                current = current.nextadj;
            }
        }
        return q;
    }

    //先统计所有节点的入度，对于入度为0的节点就可以分离出来，然后把这个节点指向的节点的入度减一，如果最后输出的点数量小于节点数，则有环
    public boolean topoSort() { //true: has loop, false: has no loop
       if(numOfVexs == 0) throw new NullPointerException();
       MyQueue<Integer> q = new MyQueue<>();
       int[] in_degree = new int[numOfVexs];
       for(int i = 0; i < numOfVexs; ++i) in_degree[i] = 0;
       //初始化入度
       for(int i = 0; i < numOfVexs; ++i) {
           VNode<E> tmp = vexs[i];
           ENode vex = tmp.firstadj;
           while(vex != null) {
               in_degree[vex.adjvex]++;
               vex = vex.nextadj;
           }
       }
        for(int i = 0; i < numOfVexs; ++i) {
            if(in_degree[i]==0) {
                q.push(i);
            }
        }
        int count = 0;
        while(!q.isEmpty()) {
            int v = q.pop();
            count++;
            ENode tmp = vexs[v].firstadj;
            while(tmp != null)      {//出队后，每个邻接点入度减1
                if((--in_degree[tmp.adjvex]) == 0) q.push(tmp.adjvex);//入度为0的顶点入队
                tmp = tmp.nextadj;

            }
        }
        if(count < numOfVexs) return true;
        return false;
    }

    //进行深度优先遍历的时候，当考察的点的下一个邻接点是已经被遍历的点，并且是自己之前的父亲节点的时候，我们就找到了一条逆向边，因此可以判断该无向图中存在环路。
    public String Find_Loop() {
        visited = new boolean[numOfVexs];
        int[] trace = new int[maxNumOfVexs];
        StringBuilder sb = new StringBuilder();
        Arrays.fill(trace, 0);
        int counter = 0;
        MyStack<Integer> stack = new MyStack<>();
        stack.push(0);
        visited[0] = true;
        ENode current;
        int v;
        trace[counter] = 0;
        counter++;
        while(!stack.isEmpty()) {
            v = stack.pop();
            current = vexs[v].firstadj;
            while(current != null) {
                if(visited[current.adjvex]) {
                    for(int i = 0; i < counter; ++i) {
                        if(i == current.adjvex) {   //loop have been found
                            for(int j = i; j < counter; ++j) {
                                sb.append(vexs[j].data.toString() + "->");
                            }
                            break;
                        }
                    }
                }
                if(!visited[current.adjvex]) {
                    trace[counter] = current.adjvex;
                    counter++;
                    stack.push(current.adjvex);
                    visited[current.adjvex] = true;
                }
                current = current.nextadj;
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.substring(0, sb.length() - 1);
    }

    //存放邻接矩阵的类
    public void Graph_Floyd() {
        path = new int[numOfVexs + 1][numOfVexs + 1];
        dist = new int[numOfVexs + 1][numOfVexs + 1];
        for(int i = 0; i < numOfVexs; ++i) {
            for(int j = 0; j < numOfVexs; ++j) {
                dist[i][j] = MAX_VAL;
                path[i][j] = j;
            }
        }
    }

    @Override
    public void Floyd() {
        Graph_Floyd();
        // Update
        for(int i = 0; i < numOfVexs; ++i) {
            VNode<E> node = vexs[i];
            ENode tmp = node.firstadj;
            while(tmp != null) {
                dist[i][tmp.adjvex] = tmp.weight;
                dist[tmp.adjvex][i] = tmp.weight;
                tmp = tmp.nextadj;
            }
        }
        // Floyd
        for(int k = 0; k < numOfVexs; k++) {
            for(int i = 0; i < numOfVexs; i++) {
                for(int j = 0; j < numOfVexs; j++) {
                    if(dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = path[i][k];
                    }
                }
            }
        }
    }

    @Override
    public String Find_road(String a, String b) {
        int start = 0, end = numOfVexs;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < numOfVexs; ++i) {
            if(vexs[i].data.toString().equals(a)) start = i;
            if(vexs[i].data.toString().equals(b)) end = i;
        }
        int f = start;
        while(f != end) {
            sb.append(vexs[f].data.toString() + "->");
            f = path[f][end];
        }
        sb.append(b);
        return sb.length() > 0 ? sb.substring(0, sb.length()) : null;
    }
}