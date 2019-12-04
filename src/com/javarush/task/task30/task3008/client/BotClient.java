package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BotClient extends Client {

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }


    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        int x = (int) (Math.random() * 100);
        return "date_bot_" + x;
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    public class BotSocketThread extends Client.SocketThread {

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            String text = "Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.";
            BotClient.this.sendTextMessage(text);
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            String[] strings = message.split(": ");
            if (strings.length > 2) {
                String name = strings[0];
                String command = strings[1];
                SimpleDateFormat dateFormat = null;
                switch (command) {
                    case "дата":
                        dateFormat = new SimpleDateFormat("d.MM.YYYY", Locale.ENGLISH);
                        break;
                    case "день":
                        dateFormat = new SimpleDateFormat("d", Locale.ENGLISH);
                        break;
                    case "месяц":
                        dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                        break;
                    case "год":
                        dateFormat = new SimpleDateFormat("YYYY", Locale.ENGLISH);
                        break;
                    case "время":
                        dateFormat = new SimpleDateFormat("H:mm:ss", Locale.ENGLISH);
                        break;
                    case "час":
                        dateFormat = new SimpleDateFormat("H", Locale.ENGLISH);
                        break;
                    case "минуты":
                        dateFormat = new SimpleDateFormat("m", Locale.ENGLISH);
                        break;
                    case "секунды":
                        dateFormat = new SimpleDateFormat("s", Locale.ENGLISH);
                        break;
                }

                if (dateFormat != null) {
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    String answer = String.format("Информация для %s: %s", name, dateFormat.format(date));
                    sendTextMessage(answer);
                }



            }

        }
    }

}
