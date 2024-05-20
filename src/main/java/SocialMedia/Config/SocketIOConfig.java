package SocialMedia.Config;

import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

@Configuration
public class SocketIOConfig {
    private static final String SERVER_PATH = "ws://192.168.110.201:1234";
    public static Socket socketServer;

    @Bean
    public void connectToServerChat() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    IO.Options options = new IO.Options();
                    options.reconnection = true; // Enable automatic reconnection
                    options.reconnectionAttempts = Integer.MAX_VALUE; // Unlimited reconnection attempts
                    options.reconnectionDelay = 3000; // Reconnect every 3 seconds

                    socketServer = IO.socket(SERVER_PATH, options);
                    socketServer.connect();

                    // Handle connection event
                    socketServer.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            System.out.println("Connected to server");
                            
                            JSONObject jsonObject = new JSONObject();
                            socketServer.emit("serverConnect", jsonObject);
                        }
                    });

                    // Handle disconnection event
                    socketServer.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            System.out.println("Disconnected from server");
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
