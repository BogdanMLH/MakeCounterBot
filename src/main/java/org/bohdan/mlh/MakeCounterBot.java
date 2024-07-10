package org.bohdan.mlh;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MakeCounterBot extends TelegramLongPollingBot {

    //Temporary fields
    private Products productsQuanClass = new Products();
    private Products todayMissingProducts = new Products();

    //Main fields
    private boolean isCounting = false;
    private List<Double> productsQuan = new ArrayList<>();
    public int questionIndex = 0;

    //List of questions for user about every required product
    private final String[] questions = {
            "\uD83E\uDDC0Ser mozzarella (karton)",
            "\uD83E\uDDC0Lava cheese (karton)",
            "\uD83C\uDF56Peperoni (kg)",
            "\uD83C\uDF56Szynka (kg)",
            "\uD83C\uDF56Wolowina (kg)",
            "\uD83C\uDF56Wieprzowina (kg)",
            "\uD83C\uDF57Kurczak cezar (kg)",
            "\uD83C\uDF57Chargrill kurczak (kg)",
            "\uD83C\uDF57Kurczak kebab (kg)",
            "\uD83E\uDD53Boczek (kg)",
            "\uD83C\uDF45Sos neapolitanski (worki)",
            "\uD83E\uDDC7Melts (opakowanie)",
            "\uD83E\uDD5ASos bechamel (kg)",
            "\uD83C\uDF75Zupa marchewkowa (kg)",
            "\uD83C\uDF75Zupa batatowa (kg)",
            "\uD83C\uDF3DKukuryza (kg)",
            "\uD83C\uDF4DAnanas (puszka)",
            "\uD83E\uDED2Oliwki (puszka)",
            "\uD83E\uDED1Jalapeno (puszka)"
    };

    //Bot`s username and token
    @Override
    public String getBotUsername() {
        return "make_fc_Bot";
    }
    @Override
    public String getBotToken() {
        return System.getenv("TELEGRAM_BOT_TOKEN");
    }

    //Main method for user`s messages processing
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();

            if ("/start".equals(text) || ("/zakoncz_liczenie".equals(text) && isCounting)) {
                sendStartMessage(message.getChatId());
            } else if (("/zakoncz_liczenie".equals(text) || "/Edytuj_ostatnia_wartosc".equals(text)) && !isCounting) {
                sendNotCountingMessage(message.getChatId());
            } else if ("/rozpocznij_liczenie".equals(text)) {
                startCounting(message.getChatId());
            } else if (isCounting && isNumeric(text)) {
                productsQuan.add(Double.parseDouble(text));
                if (questionIndex < questions.length - 1 && isCounting) {
                    questionIndex++;
                    sendQuestion(message.getChatId());
                } else {
                    sendProductQuan(message.getChatId());
                }
            } else if ("/Edytuj_ostatnia_wartosc".equals(text)) {
                if(questionIndex > 0){
                    productsQuan.remove(questionIndex-1);
                    questionIndex--;
                    sendQuestion(message.getChatId());
                }
            } else {
                sendInvalidOptionMessage(message.getChatId());
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery().getId(), update.getCallbackQuery().getData(), update.getCallbackQuery().getMessage());
        }
    }

    //Main bot work
    //Starting message for user with a little description of bot`s work
    private void sendStartMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("\uD83D\uDC4B<b>Cześć!</b>" +
                "\n\nBot ten został stworzony do szybkiego liczenia produktów w chłodnie.\uD83D\uDCF2" +
                "\n\n<b>Proszę wpisywać wartości ułamkowe oddzielone kropką, przykład: 0.5</b>" +
                "\n\nAby rozpocząć nowe liczenie, kliknij <b>/Rozpocznij_liczenie</b>.");
        message.setParseMode("HTML");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/Rozpocznij_liczenie");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);

        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //Refreshing bot for the next count
    private void startCounting(Long chatId) {
        questionIndex = 0;
        isCounting = true;
        productsQuanClass = new Products();
        todayMissingProducts = new Products();
        productsQuan = new ArrayList<>();
        sendQuestion(chatId);
    }

    //Iterable sending questions from the list
    private void sendQuestion(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(questions[questionIndex]);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //Sending to user a list of missing products for the next day
    private void sendProductQuan(Long chatId) {

        productsQuanClass = Products.fromList(productsQuan);
        todayMissingProducts = ProductsDB.getTodayMissingProducts(productsQuanClass);
        productsQuan = todayMissingProducts.toList();

        if(productsQuan.stream().allMatch(value -> value == 0.0)){
            String response1 = "<b>\uD83E\uDD73W lodówce jest wystarczająco dużo produktów na jutro!\uD83E\uDD73</b>\n" +
                    "\nAby rozpocząć nowe liczenie, kliknij <b>/Rozpocznij_liczenie</b>.";
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText(response1);
            message.setParseMode("HTML");

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            return;
        }

        String response = "<b>Brakujące produkty na jutro:</b>\n";

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (int i = 0; i < productsQuan.size(); i++) {
            if (productsQuan.get(i) > 0) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(questions[i] + ": " + productsQuan.get(i).toString());
                button.setCallbackData("product_" + i);
                List<InlineKeyboardButton> row = new ArrayList<>();
                row.add(button);
                keyboard.add(row);
            }
        }

        markup.setKeyboard(keyboard);
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(response);
        message.setReplyMarkup(markup);
        message.setParseMode("HTML");

        isCounting = false;

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleCallbackQuery(String callbackId, String data, Message message) {
        if (data.startsWith("product_")) {
            int index = Integer.parseInt(data.split("_")[1]);
            productsQuan.set(index, -productsQuan.get(index));
            updateProductList(message);
        }
    }

    private void updateProductList(Message message) {
        boolean allMarked = true;
        StringBuilder response = new StringBuilder("<b>Brakujące produkty na jutro:</b>\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (int i = 0; i < productsQuan.size(); i++) {
            if (Math.abs(productsQuan.get(i)) > 0) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                if (productsQuan.get(i) < 0) {
                    button.setText("✅" + questions[i] + ": " + Math.abs(productsQuan.get(i)) + "✅");
                } else {
                    button.setText(questions[i] + ": " + productsQuan.get(i).toString());
                    allMarked = false;
                }

                button.setCallbackData("product_" + i);
                List<InlineKeyboardButton> row = new ArrayList<>();
                row.add(button);
                keyboard.add(row);
            }
        }

        if (allMarked) {
            sendAllMarkedMessage(message.getChatId());
            deleteMessage(message);
        } else{
            markup.setKeyboard(keyboard);

            EditMessageText newMessage = new EditMessageText();
            newMessage.setChatId(message.getChatId().toString());
            newMessage.setMessageId(message.getMessageId());
            newMessage.setText(response.toString());
            newMessage.setReplyMarkup(markup);
            newMessage.setParseMode("HTML");

            try {
                execute(newMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAllMarkedMessage(Long chatId) {
        StringBuilder response = new StringBuilder("\uD83E\uDD73Brawo!" +
                "\n\n<b>Nie zapomnij umieścić daty na każdym produkcie!</b>\n\n");

        for (int i = 0; i < productsQuan.size() - 3; i++) {
            if (productsQuan.get(i) != 0) {
                String questionWithoutParentheses = questions[i].replaceAll("\\(.*?\\)", "").trim();
                if (i <= 9 || i == 13 || i == 14 || i == 15) {
                    response.append(questionWithoutParentheses).append("  <b><i>(Chłodnia)</i></b>");
                } else if (i == 10 || i == 11) {
                    response.append(questionWithoutParentheses).append("  <b><i>(Zapiekaron)</i></b>");
                } else {
                    response.append(questionWithoutParentheses).append("  <b><i>(Ciasto)</i></b>");
                }
                response.append(" X ").append(Math.abs(productsQuan.get(i))).append("\n");
            }
        }


        response.append("\n\nAby rozpocząć nowe liczenie, kliknij <b>/Rozpocznij_liczenie</b>.");
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(response.toString());
        message.setParseMode("HTML");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //Helpful methods
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void deleteMessage(Message message) {
        try {
            execute(new DeleteMessage(message.getChatId().toString(), message.getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendInvalidOptionMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Nie ma takiej opcji.");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendNotCountingMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("W tej chwili nie ma żadnego liczenia.");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
