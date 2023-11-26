package WS;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.LoadMessage;
import webSocketMessages.serverMessages.NotificationMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connectionConcurrentHashMap = new ConcurrentHashMap<>();

    public void add(String username, Session session){
        var connection = new Connection(username,session);
        connectionConcurrentHashMap.put(username,connection);
    }
    public void broadcast(String name, NotificationMessage notificationMessage) throws IOException {
        var removes = new ArrayList<Connection>();
        for (Connection connection: connectionConcurrentHashMap.values()){
            if (connection.session.isOpen()){
                if (!connection.username.equals(name)){
                    connection.send(new Gson().toJson(notificationMessage));
                }
            }else {
                removes.add(connection);
            }
        }
        for (var c: removes){
            connectionConcurrentHashMap.remove(c.username);
        }
    }
    public void broadcastBoard(String name, LoadMessage loadMessage) throws IOException {
        var removes = new ArrayList<Connection>();
        for (Connection connection: connectionConcurrentHashMap.values()){
            if (connection.session.isOpen()){
                if (!connection.username.equals(name)){
                    connection.send(new Gson().toJson(loadMessage));
                }
            }else {
                removes.add(connection);
            }
        }
        for (var c: removes){
            connectionConcurrentHashMap.remove(c.username);
        }
    }
}
