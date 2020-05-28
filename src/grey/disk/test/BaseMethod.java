package grey.disk.test;

import java.util.ArrayList;

public abstract class BaseMethod {

    protected ArrayList<Integer> requestList;   //磁道号请求序列
    protected ArrayList<Integer> resultList = new ArrayList<>();    //处理结果序列
    protected int firstLocation = 0;            //初始磁道号
    protected int distance = 0;                 //跨越的磁道数

    public void run() {
    }

    public void setRequestList(ArrayList<Integer> requestList) {
        this.requestList = requestList;
    }

    public void setFirstLocation(int firstLocation) {
        this.firstLocation = firstLocation;
    }

    public ArrayList<Integer> getResultList() {
        return resultList;
    }

    public int getDistance() {
        return distance;
    }

    public int totalValue(ArrayList<Integer> list) {
        int count = 0;
        for (Integer i : list) {
            count += i;
        }
        return count;
    }
}
