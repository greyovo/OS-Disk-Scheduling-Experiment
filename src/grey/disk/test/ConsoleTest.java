package grey.disk.test;

import grey.disk.util.RequestMaker;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class ConsoleTest {

    private RequestMaker maker;
    private static ArrayList<Integer> request;
    private int firstLoc = -1;
    private int trackNum = -1;
    private int requestNum = -1;

    public ConsoleTest(int firstLoc,int requestNum, int trackNum) {
        maker = new RequestMaker();
        request = new ArrayList<>();
        this.firstLoc = firstLoc;
        this.requestNum = requestNum;
        this.trackNum = trackNum;
    }

    public void generateRequest() {
        maker.setRequestNum(requestNum);
        maker.remakeRandomList();
        updateRequestList(maker.requestList);
    }

    public boolean allPrepared() {

        if (request.isEmpty()) {
            System.err.println("请求序列为空，未生成请求序列");
            return false;
        }
        return true;
    }

    private void updateRequestList(ArrayList<Integer> list) {
        request = list;
//        requestArray = (Integer[]) list.toArray();
    }

    public void drawFCFS() {

        if (!allPrepared())
            return;

        FCFS fcfs = new FCFS(firstLoc, trackNum, request);
        ArrayList<Integer> result = fcfs.getResultList();
        System.out.println(result);
    }

    public void drawSSTF() {

        if (!allPrepared())
            return;

        SSTF sstf = new SSTF(firstLoc, trackNum, request);
        ArrayList<Integer> result = sstf.getResultList();
        System.out.println(result);
    }

    public void drawSCAN() {

        if (!allPrepared())
            return;

        SCAN scan = new SCAN(firstLoc, trackNum, maker.requestList, SCAN.POSITIVE_DIRECTION);
        ArrayList<Integer> result = scan.getResultList();
        System.out.println(result);
    }

    public void drawCSCAN() {

        if (!allPrepared())
            return;

        CSCAN cscan = new CSCAN(firstLoc, trackNum, request, CSCAN.POSITIVE_DIRECTION);
        ArrayList<Integer> result = cscan.getResultList();
        System.out.println(result);
    }
}
