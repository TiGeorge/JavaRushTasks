package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args)  {
        int i = ConsoleHelper.readInt();
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(i);
            System.out.println("Сервер запущен.");
            while (true) {
                Socket accept = socket.accept();
                Handler handler = new Handler(accept);
                handler.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void sendBroadcastMessage(Message message) {
        for (Connection connection : connectionMap.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                System.out.println("не смогли отправить сообщение");
            }
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage("Соединение установлено " + socket.getRemoteSocketAddress());
            String userName = null;
            try (Connection connection = new Connection(socket)) {
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                notifyUsers(connection, userName);
                serverMainLoop(connection, userName);
                connectionMap.remove(userName);
                Message message = new Message(MessageType.USER_REMOVED, userName);
                Server.sendBroadcastMessage(message);

            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage(e.getMessage());
            }

            ConsoleHelper.writeMessage("Соединение закрыто");
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            Message request = new Message(MessageType.NAME_REQUEST);
            Message answer = null;
            String name = null;

            while (true) {
                connection.send(request);
                answer = connection.receive();
                name = answer.getData();
                if(answer.getType()==MessageType.USER_NAME && name != null && !name.equals("") && !connectionMap.containsKey(name)) break;
            }

            connectionMap.put(name, connection);
            connection.send(new Message(MessageType.NAME_ACCEPTED));

            return name;
        }

        private void notifyUsers(Connection connection, String userName) throws IOException {

            for (String name : connectionMap.keySet()) {
                Message message = new Message(MessageType.USER_ADDED, name);
                if(!name.equals(userName)) connection.send(message);
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message receive = connection.receive();
                if (receive.getType() == MessageType.TEXT) {
                    String text = userName + ": " + receive.getData();
                    Message message = new Message(MessageType.TEXT, text);
                    sendBroadcastMessage(message);
                } else {
                    ConsoleHelper.writeMessage("Ошибка!");
                }
            }
        }
    }

}
