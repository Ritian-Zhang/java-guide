package com.ritian.tcp;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ritian.Zhang
 * @date 2019/04/16
 **/
@Slf4j
public class SocketServer {

    private static final int port = 55550;

    public static void startSocket() throws Exception{
        ServerSocket server = new ServerSocket(port);

        //建立好连接,从socket中获取输入流
        Socket socket = server.accept();
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
        while ((len = inputStream.read(bytes)) != -1) {
            // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            sb.append(new String(bytes, 0, len, "UTF-8"));
        }
        log.info("get message from client: {}",sb);
        inputStream.close();
        socket.close();
        server.close();
    }

    public static void main(String[] args) throws Exception{
        startSocket();
    }

}
