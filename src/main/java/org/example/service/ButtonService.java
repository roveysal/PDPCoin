package org.example.service;

import org.example.modul.Subject;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ButtonService {
    public ReplyKeyboardMarkup mainMenu(){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowlist = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        KeyboardButton button = new KeyboardButton();
        button.setText("O'qituvchi\uD83E\uDDD1\uD83C\uDFFB\u200D\uD83C\uDFEB");

        KeyboardButton button1 = new KeyboardButton();
        button1.setText("O'quvchi\uD83E\uDDD1\uD83C\uDFFB\u200D\uD83D\uDCBB");

        row1.add(button);
        row2.add(button1);

        rowlist.add(row1);
        rowlist.add(row2);

        markup.setKeyboard(rowlist);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        markup.setIsPersistent(true);
        markup.setSelective(true);

        return markup;
    }


    public ReplyKeyboardMarkup subjects(List<Subject> subjects){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowlist = new ArrayList<>();
        if (subjects.size() % 2 == 1){
            for (int i = 0; i < subjects.size()-1; i+=2) {
                KeyboardRow row = new KeyboardRow();
                KeyboardButton buttonOne = new KeyboardButton();
                buttonOne.setText(subjects.get(i).getName());
                KeyboardButton buttonTwo = new KeyboardButton();
                buttonTwo.setText(subjects.get(i+1).getName());

                row.add(buttonOne);
                row.add(buttonTwo);

                rowlist.add(row);
            }

            KeyboardRow rowOne = new KeyboardRow();
            KeyboardButton button2 = new KeyboardButton();
            button2.setText(subjects.get(subjects.size()-1).getName());
            rowOne.add(button2);
            rowlist.add(rowOne);
        }else{
            for (int i = 0; i < subjects.size()-2; i+=2) {
                KeyboardRow row = new KeyboardRow();
                KeyboardButton buttonOne = new KeyboardButton();
                buttonOne.setText(subjects.get(i).getName());
                KeyboardButton buttonTwo = new KeyboardButton();
                buttonTwo.setText(subjects.get(i+2).getName());

                row.add(buttonOne);
                row.add(buttonTwo);

                rowlist.add(row);
            }
        }

        markup.setKeyboard(rowlist);
        markup.setResizeKeyboard(true);
        markup.setSelective(true);
        markup.setOneTimeKeyboard(true);
        return markup;

    }

    public InlineKeyboardMarkup classes(boolean[] classes){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> rowResult = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        if (classes[0]) {
            button.setText("5-sinf ✅");
            button.setCallbackData("chosen_class_5");
        } else {
            button.setText("5-sinf");
            button.setCallbackData("choose_class_5");
        }

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        if (classes[1]) {
            button2.setText("6-sinf ✅");
            button2.setCallbackData("chosen_class_6");
        } else {
            button2.setText("6-sinf");
            button2.setCallbackData("choose_class_6");
        }

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        if (classes[2]){
            button3.setText("7-sinf ✅");
            button3.setCallbackData("chosen_class_7");
        } else {
            button3.setText("7-sinf");
            button3.setCallbackData("choose_class_7");
        }
        InlineKeyboardButton button4 = new InlineKeyboardButton();
        if (classes[3]){
            button4.setText("8-sinf ✅");
            button4.setCallbackData("chosen_class_8");
        } else {
            button4.setText("8-sinf");
            button4.setCallbackData("choose_class_8");
        }
        InlineKeyboardButton button5 = new InlineKeyboardButton();
        if (classes[4]){
            button5.setText("9-sinf ✅");
            button5.setCallbackData("chosen_class_9");
        } else {
            button5.setText("9-sinf");
            button5.setCallbackData("choose_class_9");
        }

        InlineKeyboardButton button6 = new InlineKeyboardButton();
        if (classes[5]){
            button6.setText("10-sinf ✅");
            button6.setCallbackData("chosen_class_10");
        } else {
            button6.setText("10-sinf");
            button6.setCallbackData("choose_class_10");
        }
        InlineKeyboardButton button7 = new InlineKeyboardButton();
        if (classes[6]){
            button7.setText("11-sinf ✅");
            button7.setCallbackData("chosen_class_11");
        } else {
            button7.setText("11-sinf");
            button7.setCallbackData("choose_class_11");
        }

        InlineKeyboardButton buttonResult = new InlineKeyboardButton();
        buttonResult.setText("Yuborish");
        buttonResult.setCallbackData("class_result");

        row.add(button);
        row.add(button2);
        row1.add(button3);
        row1.add(button4);
        row2.add(button5);
        row2.add(button6);
        row3.add(button7);
        rowResult.add(buttonResult);

        rowList.add(row);
        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);
        rowList.add(rowResult);

        markup.setKeyboard(rowList);

        return markup;
    }

    public  ReplyKeyboardMarkup adminMenu(){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowlist = new ArrayList<>();

        KeyboardButton button = new KeyboardButton();
        button.setText("Sinflarni ko'rish");

        KeyboardButton button1 = new KeyboardButton();
        button1.setText("Fanni o'zgartirish");

        KeyboardButton button2 = new KeyboardButton();
        button2.setText("F.I.O. ni o'zgartirish");

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row.add(button);
        row1.add(button1);
        row2.add(button2);

        rowlist.add(row);
        rowlist.add(row1);
        rowlist.add(row2);

        markup.setKeyboard(rowlist);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        markup.setSelective(true);

        return markup;
    }

    public ReplyKeyboardMarkup studentMenu(){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardButton button = new KeyboardButton();
        button.setText("Uy ishini tekshirishga yuborish");

        KeyboardButton button1 = new KeyboardButton();
        button1.setText("Sinf ishini tekshirishga yuborish");

        KeyboardButton button2 = new KeyboardButton();
        button2.setText("Haftalik nazoratni baholash yuborish");

        KeyboardButton button3 = new KeyboardButton();
        button3.setText("Oylik nazoratni baholash yuborish");

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        row.add(button);
        row1.add(button1);
        row2.add(button2);
        row3.add(button3);

        rowList.add(row);
        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);

        markup.setKeyboard(rowList);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        markup.setSelective(true);

        return markup;
    }
    public ReplyKeyboardMarkup classStudent(){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowlist = new ArrayList<>();

        KeyboardButton button = new KeyboardButton();
        button.setText("5-sinf");

        KeyboardButton button1 = new KeyboardButton();
        button1.setText("6-sinf");

        KeyboardButton button2 = new KeyboardButton();
        button2.setText("7-sinf");

        KeyboardButton button3 = new KeyboardButton();
        button3.setText("8-sinf");

        KeyboardButton button4 = new KeyboardButton();
        button4.setText("9-sinf");

        KeyboardButton button5 = new KeyboardButton();
        button5.setText("10-sinf");

        KeyboardButton button6 = new KeyboardButton();
        button6.setText("11-sinf");

        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();
        KeyboardRow row6 = new KeyboardRow();

        row.add(button);
        row1.add(button1);
        row2.add(button2);
        row3.add(button3);
        row4.add(button4);
        row5.add(button5);
        row6.add(button6);

        rowlist.add(row);
        rowlist.add(row1);
        rowlist.add(row2);
        rowlist.add(row3);
        rowlist.add(row4);
        rowlist.add(row5);
        rowlist.add(row6);
        markup.setKeyboard(rowlist);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        markup.setSelective(true);

        return markup;
    }
}
