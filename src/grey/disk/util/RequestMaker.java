package grey.disk.util;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 用随机数生成函数产生“磁道号”序列（即磁盘请求的位置），共产生 400 个。
 * 其中50%位于 0～499，25%分布在 500～999，25%分布在 1000～1499。
 */
public class RequestMaker {

    private HashSet<Integer> set = new HashSet<>();
    private int requestNum = 0;

    public RequestMaker(int requestNum) {
        this.requestNum = requestNum;
    }

    public ArrayList<Integer> getRandomRequest(){
        ArrayList<Integer> requestList= new ArrayList<>();

        //50%位于0~499


        //25%位于500~999


        //25%位于1000~1499


        //打乱顺序

        return requestList;
    }

    private void randomsToSet(int begin, int end, int num) {

        if (begin > end || end - begin > num)
            return;

        for (int i = 0; i < num; i++) {

        }

    }


    @Test
    public void test() {

    }

}
