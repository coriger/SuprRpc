package supr.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by lujiangtao on 2016/4/8.
 */
public class Test {

    public static void main(String[] args){

        Integer i = 10066329;
        byte[] b = i.toString().getBytes();
        for(byte bi : b){
            System.out.print(bi+" ");
        }

        System.out.println();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(bos);
            os.writeObject(i);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] c = bos.toByteArray();
        for(byte ci : c){
            System.out.print(ci+" ");
        }
    }

}
