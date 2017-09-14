package backup.com.sun.net.httpserver;

import java.net.*;
import java.io.*;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

public class DefaultHttpServerProvider extends HttpServerProvider {
    public HttpServer createHttpServer (InetSocketAddress addr, int backlog) throws IOException {
	return new HttpServerImpl (addr, backlog);
    }

    public HttpsServer createHttpsServer (InetSocketAddress addr, int backlog) throws IOException {
	return new HttpsServerImpl (addr, backlog);
    }
}
