package com.reconosersdk.reconosersdk.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.mlkit.vision.text.Text;
import com.reconosersdk.reconosersdk.citizens.barcode.ForeignBarcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForeignStringUtils {

    public static final String BACK_LINE_ANVERSO = "\\\\n";
    public static final String BACK_LINE_REVERSO = "\n";

    @SuppressLint("LongLogTag")
    public static String orderLabel(List<Text.TextBlock> results) {

        String result = "";
        ArrayList<String> keys = new ArrayList<>(
                Arrays.asList("APELLIDOS", "NOMBRES", "NACIONALIDAD", "FECHA DE NACIMIENTO", "SEXO", "RH", "EXPEDICION", "EXPEDICIÓN", "VENCE"));
        ArrayList<String> goodString = new ArrayList<>(
                Arrays.asList("COL", "REPUBLICA DE COLOMBIA", "CÉDULA DE EXTRANJERÍA", "MIGRANTE", "RESIDENTE", "NO", "APELLIDOS", "NOMBRES", "NACIONALIDAD", "FECHA DE NACIMIENTO", "SEXO", "RH", "EXPEDICION", "EXPEDICIÓN", "VENCE"));

        ArrayList<Text.Line> lines = new ArrayList<>();
        ArrayList<Text.Line> lines2 = new ArrayList<>();

        for (Text.TextBlock textBlock : results) {
            for (Text.Line line : textBlock.getLines()) {

                //Agrego todas las lineas de cada bloque de texto
                lines.add(line);

                Boolean isGoodStr = false;
                double maxPercentage = 0;

                for (int j = 0; j < goodString.size(); j++) {
                    String lineText = line.getText().toUpperCase();
                    String[] data = lineText.split(" ");
                    if (lineText.contains(goodString.get(j))) {
                        maxPercentage = 100;
                    } else {
                        for (int k = 0; k < data.length; k++) {
                            double percentage = StringUtils.diceCoefficientOptimized(goodString.get(j), data[k]) * Constants.ONE_HUNDRED;
                            if (percentage > maxPercentage) {
                                maxPercentage = percentage;
                            }

                            if (maxPercentage >= 70) {
                                break;
                            }
                        }
                    }

                    //Elimino toda la información que no tiene que ser parseada
                    if (maxPercentage >= 70) {
                        lines2.add(line);
                        isGoodStr = true;
                        maxPercentage = 0;
                        Log.w("SIN PARTNER", line.getText());
                        goodString.remove(j);
                        break;
                    }
                }

                if (!isGoodStr) {
                    Log.w("DESCARTADO", line.getText() + " " + maxPercentage);
                }
            }
        }

        Text.Line[] array = lines.toArray(new Text.Line[0]);
        Text.Line[] array2 = lines2.toArray(new Text.Line[0]);

        //Busco la información que no estaba aparejada con su key y la recupero
        for (int i = 0; i < array2.length; i++) {
            Text.Line line = array2[i];
            Boolean isKey = false;
            for (int j = 0; j < keys.size(); j++) {

                double percentage = StringUtils.diceCoefficientOptimized(keys.get(j), line.getText().toUpperCase()) * Constants.ONE_HUNDRED;
                Log.w("BUSCANDOLE EL PARTNER", percentage + " " + line.getText());
                if (percentage >= 70) {
                    isKey = true;
                    String partner = findPartner(keys.get(j), array, line);
                    if (partner != null) {
                        result = result + line.getText() + " " + partner + BACK_LINE_REVERSO;
                    } else {
                        result = result + line.getText() + BACK_LINE_REVERSO;
                    }
                    keys.remove(j);
                    break;
                }
            }

            if (!isKey) {
                result = result + line.getText() + BACK_LINE_REVERSO;
            }
        }

        Log.w("RESULTADO", result);

        return result;
    }

    public static String findPartner(String key, Text.Line[] array, Text.Line line) {
        String partner = null;
        String text = line.getText().toUpperCase();

        if (text.split(" ").length < 2) {
            Log.w("BUSCANDO", key + " " + line.getText());
            for (int j = 0; j < array.length; j++) {

                if (!text.equals(array[j].getText().toUpperCase()) && (array[j].getBoundingBox().top > (line.getBoundingBox().top - 20)) && (array[j].getBoundingBox().top < (line.getBoundingBox().top + 20)) && array[j].getBoundingBox().left > line.getBoundingBox().left) {
                    partner = array[j].getText();
                    break;
                } else {
                    Log.w("BUSCANDO ---", key + " " + array[j].getText() + " " + array[j].getBoundingBox().top + " " + line.getBoundingBox().top);
                }
            }
        }
        return partner;
    }

    public static String getData(String key, String obverseText) {
        String text = "";
        String[] simpleArray = obverseText.split(BACK_LINE_REVERSO);
        if (simpleArray.length == 1) {
            simpleArray = obverseText.split(BACK_LINE_ANVERSO);
        }

        for (int i = 0; i < simpleArray.length; i++) {
            String lineText = simpleArray[i].toUpperCase();

            if (key.contains("TIPO")) {

                if (lineText.contains("MIGRAN")) {
                    text = "MIGRANTE";
                    break;
                } else if (lineText.contains("RESIDEN")) {
                    text = "RESIDENTE";
                    break;
                }

            } else if (key.contains("NUMERO")) {
                if (lineText.contains("MIGRANTE") || lineText.contains("RESIDENTE") || lineText.contains("NO")) {
                    String[] data = lineText.split(" ");
                    for (int j = 0; j < data.length; j++) {
                        if (isNumeric(data[j])) {
                            text = data[j];
                            break;
                        }
                    }
                }
            } else {

                String[] dataLine = lineText.split(" ");

                Log.e("BUSCANDO","@@@@@@@@@@@@");
                Log.e("KEY/LINEA",key+"/"+lineText);

                double maxPercentage = 0;
                int keyPosition = -1;

                if (lineText.contains(key)) {
                    maxPercentage = 100;
                } else {
                    for (int k = 0; k < dataLine.length; k++) {
                        double percentage = StringUtils.diceCoefficientOptimized(key, dataLine[k]) * Constants.ONE_HUNDRED;
                        if (percentage > maxPercentage) {
                            maxPercentage = percentage;
                        }
                        keyPosition = k;
                        if (maxPercentage >= 70) {
                            break;
                        }
                    }
                }

                if (maxPercentage >= 70) {
                    if (key.contains("NACIMIENTO") || key.contains("EXPEDICION") || key.contains("VENCE")) {
                        String[] data = lineText.split(" ");
                        for (int j = 0; j < data.length; j++) {
                            text = getDate(data[j]);
                            if (!text.isEmpty()) {
                                break;
                            }
                        }

                        if (text.isEmpty() && data.length > 1) {
                            text = data[data.length - 1];
                        }
                        break;
                    } else if (key.contains("SEXO") || key.contains("RH")) {

                        String tempText = lineText.replace("SEXO", "").replace("RH", "").replace(":", "");
                        Log.w("tempText", tempText);
                        if (key.contains("SEXO") && tempText.contains("M")) {
                            text = "M";
                        } else if (key.contains("SEXO") && tempText.contains("F")) {
                            text = "F";
                        } else {
                            text = tempText.replace("M", "").replace("F", "").trim();
                        }
                        break;
                    } else {

                        String tempText = lineText.replace(key, "");
                        if (keyPosition >= 0) {
                            tempText = "";
                            for (int j = keyPosition; j < dataLine.length; j++) {
                                tempText = tempText + " " + dataLine[j];
                            }
                            text = tempText;
                        } else {
                            String[] data = tempText.split(" ");
                            Log.w(key, Arrays.toString(data));
                            for (int j = 0; j < data.length; j++) {
                                text = text + " " + data[j];
                            }
                        }

                        break;
                    }
                }

                if (!text.isEmpty()) {
                    break;
                }
            }
        }
        text = text.replace(":", "").trim();

        /*if (text.isEmpty()) {
            text = "404";
        }*/
        Log.e("TEXT",text);
        if (key.contains("NOMBRES")||key.contains("APELLIDOS")){
            Log.e("NAMES FIND",text);
            String[] resultStr = text.split(" ");
            text = "";
            for (String word: resultStr){
                if (StringUtils.computeLevenshteinDistance(word,key) > 1){
                    if (text.equals("")){
                        text = word;
                    }else{
                        text = text + " " + word;
                    }
                }
            }
        }
        Log.e("TEXTPOST",text);
        return text;
    }

    //To get the dates on the document
    public static String getDate(String text) {
        Log.w("getDate", text);

        if (text == null || (!text.matches("\\d{4}\\/\\d{2}\\/\\d{2}"))) {
            text = "";
        }
        Pattern p = Pattern.compile("\\d{4}\\/\\d{2}\\/\\d{2}");
        Matcher m = p.matcher(text);
        if (m.find()) {
            text = m.group(0);
        }
        return text;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static ForeignBarcode parseForeignBarcode(String barcode) {
        ForeignBarcode foreignBarcode = new ForeignBarcode();
        String alphaAndDigits = barcode.replaceAll("[^\\p{Alpha}\\p{Digit}\\+\\_\\ \\-]+", "/");
        String[] splitStr = alphaAndDigits.split("\\/+");
        Log.e("BARCODE",alphaAndDigits);
        String fullName = "";
        boolean lastItem = false;
        int i = 0;
        for (String splited : splitStr) {
            if (lastItem) {
                foreignBarcode.setNacionalidad(splited);
                break;
            }
            if (splited.contains("+") || splited.contains("-")) {
                String birthDate = splited.substring(0, 8);
                String expeditionDate = splited.substring(9, 17);
                String expirationDate = splited.substring(17, 25);
                String rh = splited.substring(25);
                foreignBarcode.setNombres(fullName);
                foreignBarcode.setFechaNacimiento(birthDate);
                foreignBarcode.setFechaExpedicion(expeditionDate);
                foreignBarcode.setFechaVencimiento(expirationDate);
                foreignBarcode.setRh(rh);

                if (splited.contains("M")) {
                    foreignBarcode.setSexo("M");
                } else {
                    foreignBarcode.setSexo("F");
                }
                lastItem = true;
            }
            if (splited.contains("MIGRANTE")) {
                foreignBarcode.setTipo("MIGRANTE");
            }
            if (splited.contains("RESIDENTE")) {
                foreignBarcode.setTipo("RESIDENTE");
            }
            if (splited.contains("CE") && splited.length() > 18) {
                String cedula = splited.substring(2, 20);
                fullName = splited.substring(20);
                Integer document = Integer.parseInt(cedula);
                foreignBarcode.setNumber(document);

            }
            if (i > 1 && !(splited.contains("+") || splited.contains("-"))) {
                fullName = fullName + " " + splited;
            }
            i++;
        }
        return foreignBarcode;
    }
}
