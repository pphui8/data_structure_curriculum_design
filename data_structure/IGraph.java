package data_structure;

public interface IGraph<E> {
    int getNumOfVertex();//获取顶点的个数
    boolean insertVex(E v);//插入顶点
    boolean deleteVex(E v);//删除顶点
    int indexOfVex(E v);//定位顶点的位置
    E valueOfVex(int v);// 定位指定位置的顶点
    boolean insertEdge(int v1, int v2,int weight);//插入边
    boolean deleteEdge(int v1, int v2);//删除边
    int getEdge(int v1,int v2);//查找边
    MyQueue<Integer> depthFirstSearch(int v);//深度优先搜索遍历
    void to_Adjacency_Matrix();
    boolean topoSort();
    void Floyd();//最短路径
    String Find_road(String a, String b);
    //拓扑排序
}