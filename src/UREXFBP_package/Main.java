package UREXFBP_package;

import UREXFBP_package.DB;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args){

        String d = new String();

        try(FileReader fr = new FileReader("B:\\IntelliJ IDEA Community Edition 2023.1.4\\Bankomat\\src\\Данные пользователей", StandardCharsets.UTF_8))
        {
            int i;
            while ((i = fr.read()) != -1){
                d += ((char) i);
            }
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        String endstring = d.replaceAll("[' '\r\n]", "");

        //Вытаскивание номера карты
        Pattern num = Pattern.compile("\\d{16}");
        Matcher matcher = num.matcher(endstring);
        String[] CardNumbers = new String[20];
        int i = 0;
        while (matcher.find())
        {
            CardNumbers[i] = matcher.group().toString();
            i++;
        }

        //вытаскивание Пароля

        Pattern rawPass = Pattern.compile("Пароль:\\d{4}");
        matcher.reset();
        matcher = rawPass.matcher(endstring);
        String tempPass = "";
        while (matcher.find()){
            tempPass += matcher.group().toString();
        }

        Pattern pass = Pattern.compile("\\d{4}");
        matcher.reset();
        matcher = pass.matcher(tempPass);
        String[] Passwords = new String[20];
        i = 0;
        while (matcher.find()){
            Passwords[i] = matcher.group().toString();
            i++;
        }

        //Вытаскивание CVV кода
        String rawCVV = "";
        Pattern rawCvv = Pattern.compile("QVCкод:\\d{3}");
        matcher.reset();
        matcher = rawCvv.matcher(endstring);
        while (matcher.find()){
            rawCVV += matcher.group().toString();
        }

        Pattern CVVs = Pattern.compile("(\\d{3}|0*\\d{1,3})");
        matcher.reset();
        matcher = CVVs.matcher(rawCVV);
        String[] CVV = new String[20];
        i = 0;
        while (matcher.find()){
            CVV[i] = matcher.group().toString();
            i++;
        }

        //Вытаскивание СрокаДействия
        String Validity = "";
        Pattern VALIDITY = Pattern.compile("Срокдействиякарты:0*\\d{1,3}/0*\\d{1,3}");
        matcher.reset();
        matcher = VALIDITY.matcher(endstring);
        while (matcher.find()){
            Validity += matcher.group().toString();
        }

        Pattern VAlIDity = Pattern.compile("0*\\d{1,3}/0*\\d{1,3}");
        matcher.reset();
        matcher = VAlIDity.matcher(Validity);
        String[] VALID = new String[20];
        i = 0;
        while (matcher.find()){
            VALID[i] = matcher.group().toString();
            i++;
        }

        String[] Month = new String[20];
        String[] Year = new String[20];

        for (int j = 0; j < VALID.length; j++) {
            Month[j] = VALID[j].substring(0, 2);
            Year[j] = VALID[j].substring(3, 5);
        }

        DB db = new DB();
        Connection connection = null;
        try {
            connection = db.getConnection();
            db.createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int j = 0; j < CardNumbers.length; j++) {
            try {
                db.InsertDataToTable(CardNumbers[j], Passwords[j], Month[j], Year[j], CVV[j]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}