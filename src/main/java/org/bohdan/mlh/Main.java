package org.bohdan.mlh;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        get("/", (req, res) -> "Hello, I am alive!");
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MakeCounterBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

}

