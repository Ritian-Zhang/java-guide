package com.ritian.study.lombok;

import lombok.*;

/**
 * TODO
 *
 * @author ritian
 * @since 2019/9/17 16:50
 **/
@Data
public class DataDemo {

    private String name;
    @NonNull
    private String data;


    public static void main(String[] args) {
        DataDemo demo  = new DataDemo("");
        demo.setData(null);
        System.out.println(demo.toString());
    }
}
