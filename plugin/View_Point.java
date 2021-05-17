package plugin;

public class View_Point {
    String name;
    int location;
    public View_Point(String name) {
        this.name = name;
    }

    public View_Point(String name, int x, int y) {
        this.name = name;
        location = x * 10 + y;
    }

    //返回景点名称
    @Override
    public String toString() {
        return name;
    }

    //返回景点的位置
    @Override
    public int hashCode() {return location;}
}

