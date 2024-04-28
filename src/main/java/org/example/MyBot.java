package org.example;

import org.example.modul.Subject;
import org.example.modul.User;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.ButtonService;
import org.example.service.SubjectServiceImpl;
import org.example.service.UserServiceImpl;
import org.example.states.BotState;
import org.example.states.Role;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

public class MyBot extends TelegramLongPollingBot{


    private final UserServiceImpl userService = new UserServiceImpl();
    private final ButtonService buttonService = new ButtonService();
    private final UserRepository userRepository = new UserRepository();
    private final SubjectRepository subjectRepository = new SubjectRepository();
    public MyBot(String botToken){
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (userRepository.getUserById(update.getMessage().getChatId()) == null) {
                userRepository.createUser(update.getMessage().getChatId(), BotState.START);
            }
            User user = userRepository.getUserById(update.getMessage().getChatId());

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            System.out.println(user.toString());
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (text.equals("/start")){
                if (user.getRole()==Role.ADMIN){
                    message.setText("Admin paneliga xush kelibsiz!");
                    message.setReplyMarkup(buttonService.adminMenu());
                    userRepository.updateBotState(chatId, BotState.MENU_ADMIN);
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }else if (user.getRole()==Role.TEACHER){
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("O'qituvchi paneliga xush kelibsiz");
                    sendMessage.setChatId(chatId);
                    userRepository.updateBotState(chatId, BotState.TEACHER_PAGE);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } else if (user.getRole()==Role.STUDENT) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Quyidagi menyulardan birini tanlang.");
                    sendMessage.setChatId(chatId);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    message.setText("Botdan to'liq foydalanish uchun ro'yxatdan o'ting.");
                    message.setReplyMarkup(buttonService.mainMenu());
                    userRepository.updateBotState(chatId,BotState.START);
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }else {
                switch (user.getBotState()){
                    case START -> {
                        if (text.equals("O'qituvchi\uD83E\uDDD1\uD83C\uDFFB\u200D\uD83C\uDFEB")){
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setText("Ism familyangizni kiriting");
                            sendMessage.setChatId(chatId);
                            userRepository.updateBotState(chatId, BotState.REGISTER_TEACHER);
                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (text.equals("O'quvchi\uD83E\uDDD1\uD83C\uDFFB\u200D\uD83D\uDCBB")) {
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setText("Ism familyangizni kiriting");
                            sendMessage.setChatId(chatId);
                            userRepository.updateBotState(chatId, BotState.REGISTER_STUDENT);

                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    case REGISTER_TEACHER -> {
                        if (text.contains(" ")&&(text.endsWith("ov")||text.endsWith("ev")||text.endsWith("ova")||text.endsWith("eva"))){
                            SendMessage message1 = new SendMessage();
                            message1.setText("Qaysi fandan dars berasiz?");
                            message1.setChatId(chatId);
                            message1.setReplyMarkup(buttonService.subjects(subjectRepository.getSubjectsNames()));
                            userRepository.updateBotState(chatId, BotState.REG_TEACH_CLASS);
                            try {
                                execute(message1);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }else {
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setText("Ism familiya xato kiritilgan");
                            sendMessage.setChatId(chatId);
                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    case REG_TEACH_CLASS -> {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Sinfni tanlang");
                        sendMessage.setChatId(chatId);
                        sendMessage.setReplyMarkup(buttonService.classes(new boolean[6]));
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case REGISTER_STUDENT -> {
                        if (text.equals(text.contains(" ")&&(text.endsWith("ov")||text.endsWith("ev")||text.endsWith("ova")||text.endsWith("eva")))){
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setText("Sinfingizni tanlang");
                            sendMessage.setReplyMarkup(buttonService.classes(new boolean[6]));
                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    case UPDATE -> {
                        if (text.equals("Sinflarni ko'rish")) {
//                            List<String> subjectNames = SubjectRepository.findSubjectName();
//                            StringBuilder response = new StringBuilder("Mavjud bo'lgan fanlar: \n");
//                            for (String name : subjectNames) {
//                                response.append(name).append("\n");
//                            }
//                            message.setText(response.toString());

                        } else if (text.equals("Fanni o'zgartirish")) {
                            user.setBotState(BotState.UPDATE);
                            message.setText("Ismni o'zgartirmoqchi bo'lgan fanni kiriting:");
                        } else if (text.equals("F.I.O. ni o'zgartirish")) {
                            user.setBotState(BotState.UPDATE);
                            message.setText("Yangi F.I.O. ni kiriting:");
                        } else {

                            message.setText("Noto'g'ri so'rov. Iltimos, tanlangan variantni tanlang.");
                        }
                        message.setReplyMarkup(buttonService.adminMenu());
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case CONFIRM -> {

                    }
                    case MENU_ADMIN -> {
                        if (text.equals("Sinflarni ko'rish")){

                        }

                    }
                    case MENU_STUDENT -> {

                    }
                    case CHOOSE_TYPE -> {

                    }
                    case SEND_CWORK -> {

                    }
                    case SEND_HOMEWORK -> {

                    }
                    case SEND_REQ_WEEKLY -> {

                    }
                    case SEND_REQ_MONTHLY -> {

                    }
                    case TEACHER_PAGE -> {

                    }

                }

            }

        } else if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String data = update.getCallbackQuery().getData();
            if (data.contains("choose_class_")){
                int number = Integer.parseInt(data.substring(13));
                EditMessageReplyMarkup markup = new EditMessageReplyMarkup();
                markup.setChatId(chatId);
                markup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
//                InlineKeyboardMarkup replyMarkup = update.getMessage().getReplyMarkup();

                boolean ans[] = new boolean[6];
                ans[number - 5] = true;
                markup.setReplyMarkup(buttonService.classes(ans));

                try {
                    execute(markup);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (data.contains("class_result")) {
                EditMessageReplyMarkup markup = new EditMessageReplyMarkup();
                markup.setChatId(chatId);
                markup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                InlineKeyboardMarkup replyMarkup = update.getCallbackQuery().getMessage().getReplyMarkup();

                int[] chosenClasses = new int[6];
                int i = 0;

                for (List<InlineKeyboardButton> inlineKeyboardButtons : replyMarkup.getKeyboard()) {
                    for (InlineKeyboardButton inlineKeyboardButton : inlineKeyboardButtons) {
                        if (inlineKeyboardButton.getCallbackData().contains("chosen_class_")){
                            chosenClasses[i] = Integer.parseInt(inlineKeyboardButton.getCallbackData().substring(13));
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "roveys_quiz_bot";
    }
}
