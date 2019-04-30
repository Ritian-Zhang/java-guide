package com.ritian.designpattern.structuretype.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元模式
 * 主要用于减少创建对象的数量，以减少内存占用和提高性能。
 * 通常使用工厂类辅助，例子中使用一个HashMap类进行辅助判断，数据池中是否已经有了目标实例，如果有，则直接返回，不需要多次创建重复实例。
 * @author ritian.Zhang
 * @date 2019/04/27
 **/
public class FlyWeightDemo {

    public static void main(String[] args) throws Exception{
       String host  ="192.168.1.100";
        ConnectionFactory factory = new ConnectionFactory();
        factory. getConnection(host);
        Thread.sleep(1000);
        factory.getConnection(host);
    }

}
class Connection{
    private String host;

    public Connection(String host) {
        this.host = host;
    }
}
class ConnectionFactory {
    private Map<String,Connection> map;

    public ConnectionFactory() {
        map = new HashMap<>();
    }

    public  Connection getConnection(String host){
        if(map.containsKey(host)){
            System.out.println("获取已存在实例");
            return map.get(host);
        }
        System.out.println("新建实例");
        Connection connection = new Connection(host);
        map.put(host,connection);
        return connection;
    }

}