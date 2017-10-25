package com.wangda.alarm.service.common.util.cache.redisclient.support;

/**
 * @author zhenwei.liu
 * @since 2017-01-18
 */
public class Server {

    private String host;
    private int port;

    public static Server create(String host, int port) {
        Server server = new Server();
        server.setHost(host);
        server.setPort(port);
        return server;
    }

    public String getHost() {
        return host;
    }

    public Server setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Server setPort(int port) {
        this.port = port;
        return this;
    }
}
