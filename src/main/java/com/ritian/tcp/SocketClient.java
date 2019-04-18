package com.ritian.tcp;

import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.Socket;

/**
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
@Slf4j
public class SocketClient {

    private static String host = "127.0.0.1";
    private static int port = 55550;

    public static void startClient() throws Exception {
        //与服务端建立连接
        Socket socket = new Socket(host, port);
        String message = "你好";
        OutputStream os = socket.getOutputStream();
        os.write(message.getBytes("utf-8"));
        os.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception{
        startClient();
    }
}
