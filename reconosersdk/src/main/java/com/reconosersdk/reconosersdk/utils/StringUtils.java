package com.reconosersdk.reconosersdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.mlkit.vision.text.Text;
import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.citizens.ColombianMinorCitizen;
import com.reconosersdk.reconosersdk.citizens.barcode.ColombianCitizenBarcode;
import com.reconosersdk.reconosersdk.entities.DocumentoAnverso;
import com.reconosersdk.reconosersdk.entities.DocumentoReverso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarCiudadano;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

import static com.reconosersdk.reconosersdk.utils.Constants.COLOMBIAN_DIGITAL_ANOTHER_NUIP;
import static com.reconosersdk.reconosersdk.utils.Constants.COLOMBIAN_DIGITAL_NUIP;
import static com.reconosersdk.reconosersdk.utils.Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT;
import static com.reconosersdk.reconosersdk.utils.Constants.FIRST_FEMALE;
import static com.reconosersdk.reconosersdk.utils.Constants.FIRST_MALE;
import static com.reconosersdk.reconosersdk.utils.Constants.SECOND_MALE;

public class StringUtils {

    public static final String PDF_417 = "PDF417";
    public static final String BACK_LINE_ANVERSO = "\\\\n";
    public static final String BACK_LINE_REVERSO = "\n";
    public static final String ONLY_NUMBERS = "[^0-9]";
    public static final String ERASE_ZEROS = "^0+(?!$)";

    //To get the born date
    public static final String CC_STRONG_FORMAT = "(.*)\\d{2}-\\d{2}-\\d{4}(.*)";
    public static final String CC_WEAK_FORMAT = "\\d{2}-\\d{2}-\\d{4}";
    public static final String ID_STRONG_FORMAT = "(.*)\\d{4}-\\d{2}(.*)";
    public static final String ID_WEAK_FORMAT = "\\d{4}-\\d{2}-\\d{2}";
    public static final String ID_DATE_ELABORATION_FORMAT = "\\d{4}\\d{2}\\d{2}";
    public static final String DD_MM_YYYY_FORMAT = "dd-MM-yyyy";
    public static final String YYYY_MM_DD_FORMAT = "yyyy-MM-dd";
    //Possible strong format to CCD obverse
    public static final String CCD_STRONG_FORMAT = "(.*)\\d{2} \\d{2} \\d{4}(.*)";
    public static final String CCD_WEAK_FORMAT = "\\d{2} \\d{2} \\d{4}";
    public static final String DD_MM_YYYY_FORMAT_CCD = "dd MM yyyy";
    public static final int EIGHTEEN = 180000;
    private static final int FORMAT_DATE_LENGTH = 6;
    //To get the gender through String
    public static final String MALE = "M";
    public static final String FEMALE = "F";
    public static final String MALE_FORMAT = "(.*)\\d{4}-M-\\d{4}(.*)";
    public static final String FEMALE_FORMAT = "(.*)\\d{4}-F-\\d{4}(.*)";
    //To get the percentage to full name
    public static final int TOLERANCE_NAME = 80;
    public static final int TOLERANCE_ID = 90;
    public static final int TOLERANCE_WORD = 78;
    public static final int OBVERSE = 1;
    public static final int REVERSE = 2;

    //To set each document
    public static final int COLOMBIAN_ID_REVERSE = 2;
    public static final int COLOMBIAN_TI_REVERSE = 4;
    public static final int DIGITAL_COLOMBIAN_ID_OBVERSE = 13;

    public static final int MIN_LENGTH_ID = 7;


    //To avoid two or more documents in text
    public static boolean repeatedlyText(String docText, String documentSide, String[] repeatedlyWords) {
        boolean repeatedWord = false;
        boolean containedWord = false;

        //Avoid null string
        if (docText == null || docText.isEmpty()) {
            return true;
        }

        int counterWords = 0;
        //Delete spaces
        docText = docText.trim();
        //Simplify the result
        //docText = docText.toUpperCase();
        //Search if the text contains the keywords
        for (String repeatedlyWord : repeatedlyWords) {
            if (docText.contains(repeatedlyWord)) {
                containedWord = true;
            }
        }

        if (containedWord) {
            String[] stringValues;
            //If the reverse side
            stringValues = docText.split(BACK_LINE_REVERSO);
            if (stringValues.length == 1) {
                //If the obverse side
                stringValues = docText.split(BACK_LINE_ANVERSO);
            }

            for (String repeatedlyWord : repeatedlyWords) {
                //initialize the counter of keywords
                counterWords = 0;
                //To know if the text contains the keywords two or more times
                for (String stringValue : stringValues) {
                    if (stringValue.contains(repeatedlyWord)) {
                        counterWords++;
                    }
                    //The text contains two or more repeatedly keywords
                    if (counterWords > 1) {
                        repeatedWord = true;
                        break;
                    }
                }
                //The text contains two or more repeatedly keywords
                if (repeatedWord) {
                    break;
                }
            }//end loop

        } else {
            return true;
        }

        return repeatedWord;
    }

    public static int getDocumentNumber(String anversoText, int documentType) {
        String[] documentNumber;
        int numberID = 0;
        //Delete spaces
        anversoText = anversoText.trim();
        //Update accuracy
        /*anversoText = anversoText.replace("L", "1");
        anversoText = anversoText.replace("l", "1");
        anversoText = anversoText.replace("O", "0");
        anversoText = anversoText.replace("o", "0");*/
        int documentResult = 0;
        //Avoid Fake positive
        if (documentType != 0) {
            documentResult = documentType;
        } else {
            documentResult = StringUtils.documentData(anversoText);
        }
        //Find the number in these documents
        if (documentResult == Constants.COLOMBIAN_OBVERSE_DOCUMENT || documentResult == Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT || documentResult == Constants.ECUADORIAN_OBVERSE_DOCUMENT || documentResult == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
            if (documentResult == Constants.COLOMBIAN_OBVERSE_DOCUMENT || documentResult == Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT) {
                anversoText = anversoText.replace("I", "1");
                anversoText = anversoText.replace("i", "1");
            }
            String[] splitAnverso = anversoText.split(BACK_LINE_ANVERSO);
            if (splitAnverso.length == 1) {
                splitAnverso = anversoText.split(BACK_LINE_REVERSO);
            }
            //Initialize the array
            documentNumber = new String[splitAnverso.length];
            for (int i = 0; i < splitAnverso.length; i++) {
                //to remove all non-digits
                //And doesn't take the another symbols
                documentNumber[i] = splitAnverso[i].replaceAll(ONLY_NUMBERS, "");
            }
            numberID = findNumberId(documentNumber);
            Arrays.fill(documentNumber, null);
        }
        return numberID;
    }

    public static String getTIDocumentNumber(String anversoText) {
        String[] documentNumber;
        int numberID = 0;
        String numberTI = "";
        //Delete spaces
        anversoText = anversoText.trim();
        int documentResult = StringUtils.documentData(anversoText);
        //Find the number in these documents
        if (documentResult == Constants.COLOMBIAN_TI_OBVERSE_DOCUMENT) {
            String[] splitAnverso = anversoText.split(BACK_LINE_ANVERSO);
            if (splitAnverso.length == 1) {
                splitAnverso = anversoText.split(BACK_LINE_REVERSO);
            }
            //Initialize the array
            documentNumber = new String[splitAnverso.length];
            for (int i = 0; i < splitAnverso.length; i++) {
                //to remove all non-digits
                //And doesn't take the another symbols
                //Log.w("anverso", splitAnverso[i]);
                documentNumber[i] = splitAnverso[i].replaceAll(ONLY_NUMBERS, "");
            }
            numberTI = findTiNumber(documentNumber);
            Arrays.fill(documentNumber, null);
        }
        return numberTI;
    }

    public static String findTiNumber(String[] documentNumber) {
        String TiNumber = "";
        //Avoid nulls
        if (documentNumber != null) {
            for (String s : documentNumber) {
                //Avoid empty String
                if (!s.isEmpty()) {
                    //avoid initial zeros
                    s = s.replaceFirst(ERASE_ZEROS, "");
                    Log.e("number", s);
                    try {
                        if (s.length() == 10 || s.length() == 11) {
                            TiNumber = s;
                        }
                    } catch (Exception e) {
                        Timber.e("The value is bigger %s", Objects.requireNonNull(e.getMessage()));
                        //Avoid errors for multiples documents
                        return "";
                    }
                    /*if (helperInt > numberId) {
                        numberId = helperInt;
                    }*/
                }
            }
        }
        return TiNumber;
    }

    public static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static int documentData(String results) {
        //Avoid nulls or empty values
        if (results == null || results.isEmpty()) {
            return 0;
        }
        int citizenDoc = 0;
        double maxIdPercentage = 0.0;
        double idPercentage;
        double[] tolerance;
        double total;
        boolean colombianTI = false;

        //Separate String in a List
        List<String> splitObverse = new ArrayList<>(Arrays.asList(results.toUpperCase().split(BACK_LINE_REVERSO)));
        if (splitObverse.size() == 1) {
            splitObverse = new ArrayList<>(Arrays.asList(results.split(BACK_LINE_ANVERSO)));
        }

        Timber.w("stringResultObverse %s", results);
        Timber.w("splitObverse %s", String.valueOf(splitObverse));


        //TODO to add any document
        ArrayList<List<String>> documentData = new ArrayList<>();
        //Colombian obverse 1
        documentData.add(Arrays.asList(Constants.COLOMBIAN_NATIONALITY, Constants.COLOMBIAN_CC/*, Constants.APELLIDOS, Constants.NUMBER*/));
        //Colombian reverse 2
        documentData.add(Arrays.asList(Constants.COLOMBIAN_BORN_DATE/*, Constants.COLOMBIAN_BORN_PLACE, Constants.COLOMBIAN_EXPEDITION_PLACE*/, Constants.COLOMBIAN_HEIGHT));
        //Colombian ti obverse 3
        documentData.add(Arrays.asList(Constants.COLOMBIAN_TI_NATIONALITY, Constants.COLOMBIAN_TI));
        //Colombian reverse 4
        documentData.add(Arrays.asList(Constants.COLOMBIAN_TI_BORN_DATE, Constants.COLOMBIAN_TI_EXPIRATION_DATE));
        //Ecuadorian obverse 5
        documentData.add(Arrays.asList(Constants.ECUADORIAN_NATIONALITY, Constants.ECUADORIAN_ID_FACILITY, Constants.ECUADORIAN_CC, Constants.ECUADORIAN_NAMES));
        //Ecuadorian reverse 6
        documentData.add(Arrays.asList(Constants.ECUADORIAN_INSTRUCTION, Constants.ECUADORIAN_EXPEDITION_DATE, Constants.ECUADORIAN_EXPIRATION_DATE));
        //Foreigner obverse 7
        documentData.add(Arrays.asList(Constants.FOREIGNER_NATIONALITY, Constants.FOREIGNER_CC, Constants.FOREIGNER_USER_NATIONALITY, Constants.FOREIGNER_USER_VENCE, Constants.FOREIGNER_USER_EXP, Constants.FOREIGNER_USER_DATE));
        //Foreigner reverse 8
        documentData.add(Arrays.asList(Constants.FOREIGNER_MIGRATION, Constants.FOREIGNER_DIRECTOR_MIGRATION, Constants.FOREIGNER_MIGRATION_2));
        //Mexican obverse 9
        documentData.add(Arrays.asList(Constants.MEXICAN_TITLE, Constants.MEXICAN_SUBTITLE, Constants.MEXICAN_CREDENTIAL, Constants.MEXICAN_NAMES, Constants.MEXICAN_HOME, Constants.MEXICAN_AGE));
        //Mexican reverse 10
        documentData.add(Arrays.asList(Constants.MEXICAN_REVERSE, Constants.MEXICAN_REVERSE_2));
        //Mexican obverse national (9) 11
        documentData.add(Arrays.asList(Constants.MEXICAN_NAT_TITTLE, Constants.MEXICAN_NAMES, Constants.MEXICAN_CURP, Constants.MEXICAN_YEAR_REGISTRATION, Constants.MEXICAN_HOME, Constants.MEXICAN_STATE));
        //Mexican reverse national
        documentData.add(Arrays.asList(Constants.FEDERAL_ELECTION, Constants.MEX));
        //Digital Colombian obverse document(13)
        documentData.add(Arrays.asList(Constants.COLOMBIAN_DIGITAL_NATIONALITY, Constants.COLOMBIAN_DIGITAL_NUIP, Constants.COLOMBIAN_DIGITAL_DATE_EXPEDITION, Constants.COLOMBIAN_DIGITAL_DATE_EXPIRATION));


        //Type of document
        for (int i = 0; i < documentData.size(); i++) {
            //Array with tolerance word
            tolerance = new double[documentData.get(i).size()];
            Arrays.fill(tolerance, 0.0);
            //clean the value
            total = 0.0;
            for (int k = 0; k < tolerance.length; k++) {
                total = total + tolerance[k];
                //Timber.w("Now total is: %s", total + " pos: " + k);
            }
            //Type of document
            for (int j = 0; j < documentData.get(i).size(); j++) {
                //Detect document
                for (String splitObverses : splitObverse) {
                    //Percentage of similarity
                    String stringValue = documentData.get(i).get(j).toUpperCase();
                    idPercentage = diceCoefficientOptimized(splitObverses.toUpperCase(), stringValue);

                    if ((i == 1 || i == 3) && !colombianTI && (diceCoefficientOptimized(splitObverses.toUpperCase(), Constants.COLOMBIAN_TI_EXPIRATION_DATE) > 0.9)) {
                        colombianTI = true;
                    }
                    if (idPercentage > maxIdPercentage) {
                        maxIdPercentage = idPercentage;
                    }
                }
                tolerance[j] = maxIdPercentage * Constants.ONE_HUNDRED;
                maxIdPercentage = 0.0;
                //Timber.w("Tolerance in text is: %s", tolerance[j] + "%, " + documentData.get(i).get(j).toUpperCase());
            }
            //Average of tolerance in ever word
            for (int k = 0; k < tolerance.length; k++) {
                total = total + tolerance[k];
                //Timber.w("Now total is: %s", total + " pos: " + k);
            }
            total = total / (tolerance.length);
            Log.w("Tolerance total is: ", total + " ::: " + tolerance.length);
            if (total > TOLERANCE_WORD) {
                if (colombianTI) {
                    citizenDoc = 4;
                } else {
                    citizenDoc = i + 1;
                }

            } else {
                citizenDoc = 0;
            }
            if ((citizenDoc == COLOMBIAN_ID_REVERSE || citizenDoc == COLOMBIAN_TI_REVERSE) && avoidNuipCCD(splitObverse, citizenDoc)) {
                //Avoid confusing documents
                citizenDoc = 0;
            }
            if (citizenDoc == 11) citizenDoc = 9;
            if (citizenDoc == 12) citizenDoc = 10;
            if (citizenDoc == DIGITAL_COLOMBIAN_ID_OBVERSE) citizenDoc = 11;

            //The SDK already found the document type
            Log.e("docType: ", String.valueOf(citizenDoc));
            if (citizenDoc > 0) {
                return citizenDoc;
            }
        }
        //Log.w("citizenDoc", String.valueOf(citizenDoc));
        return citizenDoc;
    }

    private static boolean avoidNuipCCD(List<String> splitObverse, int citizenDoc) {
        boolean nuipFounded = false;
        //Detect document
        for (String splitObverses : splitObverse) {
            if (splitObverses.toUpperCase().contains(COLOMBIAN_DIGITAL_NUIP) || splitObverses.toUpperCase().contains(COLOMBIAN_DIGITAL_ANOTHER_NUIP)) {
                nuipFounded = true;
                //Get out of the loop
                break;
            }
        }
        return nuipFounded;
    }

    public static int documentData(String results, int typeSearch) {

        //Avoid nulls or empty values
        if (results == null || results.isEmpty()) {
            return 0;
        }

        int citizenDoc = 0;
        double maxIdPercentage = 0.0;
        double idPercentage;
        double[] tolerance;
        double total;
        boolean colombianTI = false;

        //Separate String in a List
        List<String> splitObverse = new ArrayList<>(Arrays.asList(results.toUpperCase().split(BACK_LINE_REVERSO)));
        if (splitObverse.size() == 1) {
            splitObverse = new ArrayList<>(Arrays.asList(results.split(BACK_LINE_ANVERSO)));
        }

        Timber.w("splitObverse %s", String.valueOf(splitObverse));
        //TODO to add any document
        ArrayList<List<String>> documentData = new ArrayList<>();
        switch (typeSearch) {
            case 1:
                documentData = getTextDocFront();
                break;
            case 2:
                documentData = getTextDocBack();
                break;
        }

        //Type of document
        for (int i = 0; i < documentData.size(); i++) {
            //Array with tolerance word
            tolerance = new double[documentData.get(i).size()];
            Arrays.fill(tolerance, 0.0);
            //clean the value
            total = 0.0;
            for (int k = 0; k < tolerance.length; k++) {

                total = total + tolerance[k];
                //Timber.w("Now total is: %s", total + " pos: " + k);
            }

            //Type of document
            for (int j = 0; j < documentData.get(i).size(); j++) {

                //Detect document
                for (String splitObverses : splitObverse) {
                    //Percentage of similarity
                    String stringValue = documentData.get(i).get(j).toUpperCase();
                    String currentString = splitObverses.toUpperCase();
                    //When the document is Digital Colombian obverse and contains "NUIP" value
                    if (i == 6 && currentString.contains(Constants.COLOMBIAN_DIGITAL_NUIP)) {
                        //Avoid number
                        currentString = currentString.replaceAll("[0-9]", "");
                        currentString = currentString.replace(".", "");
                        Timber.e("Now current String is: %s", currentString);
                    } else //When the document is Digital Colombian reverse contains ".C"
                        if (i == 6 && currentString.startsWith(Constants.COLOMBIAN_DIGITAL_ALMOST_CO)) {
                            //Avoid number
                            currentString = currentString.replace("0", "O");
                            Timber.e("Now current String is: %s", currentString);
                        }
                    //To easily detector CCD reverse
                    if ((i == 6 && (currentString.startsWith(Constants.COLOMBIAN_DIGITAL_ICCOL) || currentString.startsWith(Constants.COLOMBIAN_DIGITAL_ICC0L)))
                            //To easily detector CCD obverse and get rH easier
                            || (i == 6 && (currentString.contains(Constants.COLOMBIAN_DIGITAL_GS) || currentString.contains(Constants.COLOMBIAN_DIGITAL_GS1) || currentString.contains(Constants.COLOMBIAN_DIGITAL_GS2) || currentString.contains(Constants.COLOMBIAN_DIGITAL_GS3)))) {
                        idPercentage = 1.0;
                        Timber.e("Warning String is: %s", results);
                    } else {
                        idPercentage = diceCoefficientOptimized(currentString, stringValue);
                    }
                    //When the document is Mexican national obverse
                    if (i == 5 && typeSearch == OBVERSE) {
                        if (idPercentage < 0.9 && (splitObverses.toUpperCase().contains(Constants.MEXICAN_CREDENTIAL) || splitObverses.toUpperCase().contains(Constants.MEX) || splitObverses.toUpperCase().contains(Constants.FEDERAL_ELECTION_NUM) || splitObverses.toUpperCase().contains(Constants.FEDERAL_ELECTION_IFE) || splitObverses.toUpperCase().contains(Constants.FEDERAL_ELECTION_IFE_NUM))) {
                            idPercentage = 0.95;
                        }
                    }
                    if (idPercentage > maxIdPercentage) {
                        maxIdPercentage = idPercentage;
                        if (idPercentage > 0.9) {
                            break;
                        }
                    }
                }

                tolerance[j] = maxIdPercentage * Constants.ONE_HUNDRED;
                maxIdPercentage = 0.0;
                //Timber.w("Tolerance in text is: %s", tolerance[j] + "%, " + documentData.get(i).get(j).toUpperCase());
            }

            //Average of tolerance in ever word
            for (int k = 0; k < tolerance.length; k++) {
                total = total + tolerance[k];
                //Timber.w("Now total is: %s", total + " pos: " + k);
            }

            total = total / (tolerance.length);
            Log.w("Tolerance total is: ", total + " ::: " + tolerance.length);
            if (total > TOLERANCE_WORD) {
                switch (typeSearch) {
                    case 1:
                        citizenDoc = getTypeDocF(i);
                        break;
                    case 2:
                        citizenDoc = getTypeDocB(i);
                        break;
                }
            }

            //The SDK already found the document type
            Log.e("docType: ", String.valueOf(citizenDoc));
            if (citizenDoc > 0) {
                return citizenDoc;
            }
        }

        //Log.w("citizenDoc", String.valueOf(citizenDoc));
        return citizenDoc;
    }

    public static ArrayList<List<String>> getTextDocFront() {

        ArrayList<List<String>> documentData = new ArrayList<>();
        //Colombian obverse 1
        documentData.add(Arrays.asList(Constants.COLOMBIAN_NATIONALITY, Constants.COLOMBIAN_CC/*, Constants.APELLIDOS, Constants.NUMBER*/));
        //Colombian ti obverse 3
        documentData.add(Arrays.asList(Constants.COLOMBIAN_TI_NATIONALITY, Constants.COLOMBIAN_TI));
        //Ecuadorian obverse 5
        documentData.add(Arrays.asList(Constants.ECUADORIAN_NATIONALITY, Constants.ECUADORIAN_ID_FACILITY, Constants.ECUADORIAN_CC, Constants.ECUADORIAN_NAMES));
        //Foreigner obverse 7
        documentData.add(Arrays.asList(Constants.FOREIGNER_NATIONALITY, Constants.FOREIGNER_CC, Constants.FOREIGNER_USER_NATIONALITY, Constants.FOREIGNER_USER_VENCE, Constants.FOREIGNER_USER_EXP, Constants.FOREIGNER_USER_DATE));
        //Mexican obverse 9
        documentData.add(Arrays.asList(Constants.MEXICAN_TITLE, Constants.MEXICAN_SUBTITLE, Constants.MEXICAN_CREDENTIAL, Constants.MEXICAN_NAMES, Constants.MEXICAN_HOME, Constants.MEXICAN_AGE));
        //Mexican obverse national (9) 11
        documentData.add(Arrays.asList(Constants.MEXICAN_NAT_TITTLE, Constants.MEXICAN_NAMES, Constants.MEXICAN_CURP, Constants.MEXICAN_YEAR_REGISTRATION, Constants.MEXICAN_HOME, Constants.MEXICAN_STATE));
        //Digital colombian obverse 11
        documentData.add(Arrays.asList(Constants.COLOMBIAN_DIGITAL_NATIONALITY, Constants.COLOMBIAN_DIGITAL_NUIP, Constants.COLOMBIAN_DIGITAL_GS, Constants.COLOMBIAN_DIGITAL_DATE_EXPEDITION, Constants.COLOMBIAN_DIGITAL_DATE_EXPIRATION));
        return documentData;
    }

    public static ArrayList<List<String>> getTextDocBack() {

        ArrayList<List<String>> documentData = new ArrayList<>();
        //Colombian reverse 2
        documentData.add(Arrays.asList(Constants.COLOMBIAN_BORN_DATE/*, Constants.COLOMBIAN_BORN_PLACE, Constants.COLOMBIAN_EXPEDITION_PLACE*/, Constants.COLOMBIAN_HEIGHT));
        //Colombian reverse 4
        documentData.add(Arrays.asList(Constants.COLOMBIAN_TI_BORN_DATE, Constants.COLOMBIAN_TI_EXPIRATION_DATE));
        //Ecuadorian reverse 6
        documentData.add(Arrays.asList(Constants.ECUADORIAN_INSTRUCTION, Constants.ECUADORIAN_EXPEDITION_DATE, Constants.ECUADORIAN_EXPIRATION_DATE));
        //Foreigner reverse 8
        documentData.add(Arrays.asList(Constants.FOREIGNER_MIGRATION, Constants.FOREIGNER_DIRECTOR_MIGRATION, Constants.FOREIGNER_MIGRATION_2));
        //Mexican reverse 10
        documentData.add(Arrays.asList(Constants.MEXICAN_REVERSE, Constants.MEXICAN_REVERSE_2));
        //Mexican reverse national (10) 12
        documentData.add(Arrays.asList(Constants.FEDERAL_ELECTION, Constants.MEX, Constants.FEDERAL_ELECTION_NUM));
        //Digital colombian obverse 12
        documentData.add(Arrays.asList(Constants.COLOMBIAN_DIGITAL_CO, Constants.COLOMBIAN_DIGITAL_REGISTER, Constants.COLOMBIAN_DIGITAL_ICCOL));
        return documentData;
    }

    public static int getTypeDocF(int position) {
        switch (position) {
            case 1:
                return 3;
            case 2:
                return 5;
            case 3:
                return 7;
            case 4:
            case 5:
                return 9;
            //When the document is Digital Colombian obverse and contains "NUIP" value
            case 6:
                return Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT; //11
            default:
                return 1;
        }
    }

    public static int getTypeDocB(int position) {
        switch (position) {
            case 1:
                return 4;
            case 2:
                return 6;
            case 3:
                return 8;
            case 4:
            case 5:
                return 10;
            case 6:
                return Constants.COLOMBIAN_DIGITAL_REVERSE_DOCUMENT;
            default:
                return 2;
        }
    }

    public static String orderLabel(List<Text.TextBlock> results) {
        String result = "";
        ArrayList<Text.Line> lines = new ArrayList<>();

        for (Text.TextBlock textBlock : results) {
            for (Text.Line line : textBlock.getLines()) {
                lines.add(line);
            }
        }

        Text.Line[] array = lines.toArray(new Text.Line[0]);
        for (int i = 0; i < array.length; i++) {
            Log.w("documentResult 1", array[i].getText());
        }

        Arrays.sort(array, (o1, o2) -> {
            Rect a = o1.getBoundingBox();
            Rect b = o2.getBoundingBox();
            return a.top - b.top;
        });

        for (int i = 0; i < array.length; i++) {
            Log.w("documentResult 2", array[i].getText());
            result = result + array[i].getText() + BACK_LINE_ANVERSO;
        }

        return result;
    }

    //Search de max value, it is equals to de number Id of the document
    public static int findNumberId(String[] documentNumber) {
        int numberId = 0;
        int helperInt = 0;
        //Avoid nulls
        if (documentNumber != null) {
            for (String s : documentNumber) {
                //Avoid empty String
                if (!s.isEmpty()) {
                    //avoid initial zeros
                    s = s.replaceFirst(ERASE_ZEROS, "");
                    //if the value is bigger than 2^31-1, Integer.MAX_VALUE
                    try {
                        helperInt = Integer.parseInt(s.trim());
                    } catch (NumberFormatException e) {
                        Log.e("The value is bigger", Objects.requireNonNull(e.getMessage()));
                        //Avoid errors for multiples documents
                        helperInt = 0;
                    }
                    if (helperInt > numberId) {
                        numberId = helperInt;
                        //Get out of loop
                        if (s.trim().length() > MIN_LENGTH_ID) {
                            break;
                        }
                    }
                }
            }
        }
        return numberId;
    }

    public static String getGender(int numberId) {
        if (numberId > 0 && numberId <= FIRST_MALE) {
            return "M";
        } else if (numberId > FIRST_MALE && numberId <= FIRST_FEMALE) {
            return "F";
        } else if (numberId > FIRST_FEMALE && numberId <= SECOND_MALE) {
            return "M";
        } else {
            return "N/A";
        }
    }

    /**
     * Format date is DD-MM-YYYY
     **/
    public static String[] getBornAgeCaseOne(String text, Context context, int documentResult) {
        String[] bornDates = null;
        //To transform the month of year
        String[] monthOfYear = context.getResources().getStringArray(R.array.months);
        String[] monthNumber = context.getResources().getStringArray(R.array.number_months);

        int amountOfDates = 0;
        //Delete spaces
        text = text.trim();
        if (documentResult == 0) {
            documentResult = StringUtils.documentData(text);
        }
        //Find the number in these documents
        if (documentResult == Constants.COLOMBIAN_REVERSE_DOCUMENT || documentResult == Constants.COLOMBIAN_TI_REVERSE_DOCUMENT || documentResult == COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT) {
            //Separate String in a List
            String[] stringValues = text.split(BACK_LINE_REVERSO);
            if (stringValues.length == 1) {
                stringValues = text.split(BACK_LINE_ANVERSO);
            }
            //Assign the maximum the born dates to detect
            bornDates = new String[stringValues.length];
            //Fill the String Array
            Arrays.fill(bornDates, "");

            for (int j = 0; j < stringValues.length; j++) {
                if (documentResult == Constants.COLOMBIAN_REVERSE_DOCUMENT || documentResult == Constants.COLOMBIAN_TI_REVERSE_DOCUMENT) {
                    //Change de "dot" by "one line" to easily the timeData format
                    stringValues[j] = stringValues[j].replace(".", "-");
                } else {
                    //documentResult == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT
                    //Change de "one line" by "space" to easily the timeData format
                    stringValues[j] = stringValues[j].replace("-", " ");
                    try {
                        if (stringValues[j].matches(".*\\d.*")) {
                            stringValues[j] = stringValues[j - 1] + " " + stringValues[j] + " " + stringValues[j + 1];
                            //Avoid duplicate dates, next value will be empty
                            stringValues[j + 1] = "";
                        }
                    } catch (Exception e) {
                        Timber.e("the value is format in: %s", stringValues[j]);
                    }
                }

                for (int i = 0; i < monthOfYear.length; i++) {
                    //To format the months of year by numbers
                    stringValues[j] = stringValues[j].replace(monthOfYear[i], monthNumber[i]);
                    switch (i) {
                        case 0:
                            stringValues[j] = stringValues[j].replace("3N3", monthNumber[i]);
                            break;
                        case 1:
                            stringValues[j] = stringValues[j].replace("F3B", monthNumber[i]);
                            break;
                        case 2:
                            stringValues[j] = stringValues[j].replace("M4R", monthNumber[i]);
                            break;
                        case 3:
                            stringValues[j] = stringValues[j].replace("4BR", monthNumber[i]);
                            break;
                        case 4:
                            stringValues[j] = stringValues[j].replace("M4Y", monthNumber[i]);
                            stringValues[j] = stringValues[j].replace("MAYO", monthNumber[i]);
                            stringValues[j] = stringValues[j].replace("05O", monthNumber[i]);
                            stringValues[j] = stringValues[j].replace("050", monthNumber[i]);
                            stringValues[j] = stringValues[j].replace("M4YO", monthNumber[i]);
                            stringValues[j] = stringValues[j].replace("MAY0", monthNumber[i]);
                            stringValues[j] = stringValues[j].replace("M4Y0", monthNumber[i]);
                            break;
                        case 7:
                            stringValues[j] = stringValues[j].replace("4GO", monthNumber[i]);
                            stringValues[j] = stringValues[j].replace("4G0", monthNumber[i]);
                            stringValues[j] = stringValues[j].replace("AG0", monthNumber[i]);
                            break;
                        case 8:
                            stringValues[j] = stringValues[j].replace("SEPT", monthNumber[i]);
                            stringValues[j] = stringValues[j].replace("S3PT", monthNumber[i]);
                            stringValues[j] = stringValues[j].replace("09T", monthNumber[i]);
                            break;
                        case 9:
                            stringValues[j] = stringValues[j].replace("0CT", monthNumber[i]);
                            break;
                        case 10:
                            stringValues[j] = stringValues[j].replace("N0V", monthNumber[i]);
                            break;
                        case 11:
                            stringValues[j] = stringValues[j].replace("D1C", monthNumber[i]);
                            break;
                    }
                }
                if (documentResult == Constants.COLOMBIAN_REVERSE_DOCUMENT || documentResult == Constants.COLOMBIAN_TI_REVERSE_DOCUMENT) {
                    if (stringValues[j].matches(CC_STRONG_FORMAT)) {
                        //To get the dates on the document
                        bornDates[amountOfDates] = isValidOneCaseCC(stringValues[j]);
                        //Change the format to easily the data process
                        bornDates[amountOfDates] = changeDateFormat(bornDates[amountOfDates]);
                        //Timber.e("The value is format date in: %s", bornDates[amountOfDates]);
                        amountOfDates++;
                    }
                } else {
                    //documentResult == Constants.COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT
                    if (stringValues[j].matches(CCD_STRONG_FORMAT)) {
                        //To get the dates on the document
                        bornDates[amountOfDates] = isValidOneCaseCCD(stringValues[j]);
                        bornDates[amountOfDates] = bornDates[amountOfDates].replace(" ", "-");
                        //Change the format to easily the data process
                        bornDates[amountOfDates] = changeDateFormat(bornDates[amountOfDates]);
                        //Timber.e("The value is format date in: %s", bornDates[amountOfDates]);
                        amountOfDates++;
                    }
                }
            }
        }
        return bornDates;
    }

    /**
     * Format date is YYYY-MM-DD
     **/
    public static String[] getBornAgeCaseTwo(String text, Context context) {
        String[] bornDates = null;
        //To transform the month of year
        String[] monthOfYear = context.getResources().getStringArray(R.array.months);
        String[] monthNumber = context.getResources().getStringArray(R.array.number_months);

        int amountOfDates = 0;
        //Delete spaces
        text = text.trim();
        int documentResult = StringUtils.documentData(text);
        //Find the number in these documents
        if (documentResult == Constants.ECUADORIAN_OBVERSE_DOCUMENT || documentResult == Constants.ECUADORIAN_REVERSE_DOCUMENT) {
            //Separate String in a List
            String[] stringValues = text.split(BACK_LINE_REVERSO);
            if (stringValues.length == 1) {
                stringValues = text.split(BACK_LINE_ANVERSO);
            }
            //Assign the maximum the born dates to detect
            bornDates = new String[stringValues.length];
            //Fill the String Array
            Arrays.fill(bornDates, "");

            for (int j = 0; j < stringValues.length; j++) {
                //Change de "space" by "one line" to easily the timeData format
                stringValues[j] = stringValues[j].replace(".", "-");
                for (int i = 0; i < monthOfYear.length; i++) {
                    //To format the months of year by numbers
                    stringValues[j] = stringValues[j].replace(monthOfYear[i], monthNumber[i]);
                    Log.w("The value is true in ", stringValues[j] + " pos: " + j);
                }


                if (stringValues[j].matches(ID_STRONG_FORMAT)) {
                    //To get the dates on the document
                    bornDates[amountOfDates] = isValidTwoCase(stringValues[j]);
                    Log.w("The value is format in ", bornDates[amountOfDates]);
                    amountOfDates++;
                }
            }
        }
        return bornDates;
    }

    //To get the dates on the  CC document
    public static String isValidOneCaseCC(String text) {
        if (text == null || (!text.matches(CC_STRONG_FORMAT))) {
            text = "";
        }
        Pattern p = Pattern.compile(CC_WEAK_FORMAT);
        Matcher m = p.matcher(text);
        if (m.find()) {
            text = m.group(0);
        }
        //Timber.e("Date founded CC: %s ", text);
        return text;
    }

    //To get the dates on the  CCD document
    public static String isValidOneCaseCCD(String text) {
        if (text == null || (!text.matches(CCD_STRONG_FORMAT))) {
            text = "";
        }
        Pattern p = Pattern.compile(CCD_WEAK_FORMAT);
        Matcher m = p.matcher(text);
        if (m.find()) {
            text = m.group(0);
        }
        //Timber.e("Date founded CCD: %s ", text);
        return text;
    }

    //To get the dates on the document
    public static String isValidTwoCase(String text) {
        if (text == null || (!text.matches(ID_STRONG_FORMAT))) {
            text = "";
        }
        Pattern p = Pattern.compile(ID_WEAK_FORMAT);
        Matcher m = p.matcher(text);
        if (m.find()) {
            text = m.group(0);
        }
        return text;
    }

    //Change the format to easily the data process
    @SuppressLint("SimpleDateFormat")
    public static String changeDateFormat(String bornDate) {
        if (bornDate == null || !bornDate.matches(CC_STRONG_FORMAT) || !bornDate.matches(CC_WEAK_FORMAT)) {
            //Timber.e("Date not founded");
            return "";
        }
        Date date;
        try {
            //Change de "space" by "one line" to easily the timeData format
            bornDate = bornDate.replace(" ", "-");
            date = new SimpleDateFormat(DD_MM_YYYY_FORMAT).parse(bornDate);
            if (date != null) {
                return new SimpleDateFormat(YYYY_MM_DD_FORMAT).format(date);
            } else {
                return "";
            }
        } catch (ParseException e) {
            Timber.e("Date format error : %s", e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    public static boolean eigthteenYearsOld(String[] bornDates, String fechaNacimiento) {
        if (bornDates[0] == null || bornDates[0].isEmpty() || bornDates[1] == null || bornDates[1].isEmpty()
                || fechaNacimiento == null || fechaNacimiento.isEmpty()) {
            //Log.w("Don't have any date", "is empty");
            return false;
        } else {
            try {
                int beginDate = Integer.parseInt(bornDates[0].replace("-", ""));
                int finalDate = Integer.parseInt(bornDates[1].replace("-", ""));
                int beginDateCitizen = Integer.parseInt(fechaNacimiento.replace("-", ""));
                //If the OCR bornDate is not equals to barcode bornDate
                if (beginDate != beginDateCitizen) {
                    return false;
                } else if (beginDate >= finalDate) {
                    return false;
                } else return finalDate - beginDate >= EIGHTEEN;
            }catch(Exception e){
                Timber.e("The error un date is: %s", e.getMessage());
                return false;
            }
        }
    }

    public static String[] getCharGender(String reversoText) {
        String[] genderArray = new String[2];
        //To get the one char to detect the gender, initialize empty
        String genderChar = "";
        //To get the longest String to detect the gender, initialize empty
        String genderString = "";
        //Delete spaces
        reversoText = reversoText.trim();
        if (documentData(reversoText) == Constants.COLOMBIAN_REVERSE_DOCUMENT || documentData(reversoText) == Constants.COLOMBIAN_TI_REVERSE_DOCUMENT) {
            String[] simpleArray = reversoText.split(BACK_LINE_REVERSO);
            if (simpleArray.length == 1) {
                simpleArray = reversoText.split(BACK_LINE_ANVERSO);
            }
            for (String s : simpleArray) {
                //Log.w("Get gender", simpleArray[i]);
                genderChar = getGenderChar(genderChar, s);
                genderString = getGenderString(genderString, s);
            }
        }
        genderArray[0] = genderChar;
        genderArray[1] = genderString;
        /*Log.w("Get simple char", genderArray[0]);
        Log.w("Get simple String", genderArray[1]);*/
        return genderArray;
    }

    //To find the gender and simple char
    public static String getGenderChar(String gender, String findGender) {
        //The gender was found already
        if (gender.equals(MALE) || gender.equals(FEMALE)) {
            return gender;
        }
        if (findGender == null || findGender.isEmpty() ||
                (!findGender.equals(MALE) && !findGender.equals(FEMALE))) {
            return "";
        }
        if (findGender.equals(MALE)) {
            return MALE;
        } else {
            return FEMALE;
        }
    }

    //To find the gender and String
    public static String getGenderString(String gender, String findGender) {
        //The gender was found already
        if (gender.equals(MALE) || gender.equals(FEMALE)) {
            return gender;
        }
        if (findGender == null || findGender.isEmpty() ||
                (!findGender.matches(MALE_FORMAT) && !findGender.matches(FEMALE_FORMAT))) {
            return "";
        }
        if (findGender.matches(FEMALE_FORMAT)) {
            return FEMALE;
        } else if (findGender.matches(MALE_FORMAT)) {
            return MALE;
        } else {
            return "";
        }
    }

    public static boolean validateGenderStrings(String[] gender, String citizenGender) {
        //Avoid nulls or empty values
        if (gender[0] == null || gender[1] == null || citizenGender == null
                || (gender[0].isEmpty() && gender[1].isEmpty() && citizenGender.isEmpty())) {
            //Log.w("Don't have any date", "is empty");
            return false;
            //If the values is the same
        } else
            //Isn't equals
            if (gender[0].equals(MALE) && gender[1].equals(MALE) && citizenGender.equals(MALE)) {
                return true;
            } else
                return gender[0].equals(FEMALE) && gender[1].equals(FEMALE) && citizenGender.equals(FEMALE);
    }

    public static boolean validateOldGenderStrings(String[] gender, String citizenGender, String genderDoc) {
        //Avoid nulls or empty values
        if (gender[0] == null || gender[1] == null || citizenGender == null || genderDoc == null
                || (gender[0].isEmpty() && gender[1].isEmpty() && citizenGender.isEmpty()) && genderDoc.isEmpty()) {
            //Log.w("Don't have any date", "is empty");
            return false;
            //If the values is the same
        } else
            //Isn't equals
            if (gender[0].equals(MALE) && gender[1].equals(MALE) && citizenGender.equals(MALE) && genderDoc.equals(MALE)) {
                return true;
            } else
                return gender[0].equals(FEMALE) && gender[1].equals(FEMALE) && citizenGender.equals(FEMALE) && genderDoc.equals(FEMALE);
    }

    //To find the gender an convert in String
    public static String charGenderString(String gender) {
        //Avoid nulls
        if (gender == null || gender.isEmpty()) {
            return "";
            //If the values is the same
        } else //Isn't equals
            if (gender.equals(MALE)) {
                return "Hombre";
            } else if (gender.equals(FEMALE)) {
                return "Mujer";
            } else return "";
    }

    public static String parseDateElaboration(String reversoText) {
        if (reversoText == null) {
            return "";
        }

        String[] stringValues = reversoText.split(BACK_LINE_REVERSO);
        if (stringValues.length == 1) {
            stringValues = reversoText.split(BACK_LINE_ANVERSO);
        }

        List<String> values = Arrays.asList(stringValues);
        for (int i = 0; i < values.size(); i++) {
            String[] tempStr = values.get(i).split("-");
            Log.w("tempStr", Arrays.toString(tempStr));
            if (tempStr.length > 2) {

                String str = tempStr[tempStr.length - 1];
                if (str.matches(ID_DATE_ELABORATION_FORMAT)) {
                    return str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
                }
            }
        }
        return "";
    }

    public static Boolean validateOCRDateElaboration(@NonNull Date expeditionDate, @NonNull Date elaborationDate) {


        return expeditionDate.before(elaborationDate);
    }

    public static Date parseDateYMD(String dateStr) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

/*    public static ColombianCitizenBarcode parseDataCode(String dataParsing, Context context) {
        //To get the different kinds
        String[] bloodKind = context.getResources().getStringArray(R.array.kind_of_blood);
        ColombianCitizenBarcode citizenDocument = null;
        if (dataParsing != null) {

            String barCode = dataParsing;
            Log.d("PDF417", "Barcode length: " + barCode.length());
            if (barCode.length() < 150) {
                //Verificar sino pudo extraer la info del barcode
                return citizenDocument;
            }

            citizenDocument = new ColombianCitizenBarcode();
            String primerApellido = "";
            String segundoApellido = "";
            String primerNombre = "";
            String segundoNombre = "";
            String cedula = "";
            String rh = "";
            String fechaNacimiento = "";
            String sexo = "";
            String alphaAndDigits = barCode.replaceAll("[^\\p{Alpha}\\p{Digit}\\+\\_]+", " ");
            String[] splitStr = alphaAndDigits.split("\\s+");
            if (!alphaAndDigits.contains("PubDSK")) {
                int corrimiento = 0;

                Pattern pat = Pattern.compile("[A-Z]");
                Matcher match = pat.matcher(splitStr[2 + corrimiento]);
                int lastCapitalIndex = -1;
                if (match.find()) {
                    lastCapitalIndex = match.start();
                    Log.d(PDF_417, "match.start: " + match.start());
                    Log.d(PDF_417, "match.end: " + match.end());
                    Log.d(PDF_417, "splitStr: " + splitStr[2 + corrimiento]);
                    Log.d(PDF_417, "splitStr length: " + splitStr[2 + corrimiento].length());
                    Log.d(PDF_417, "lastCapitalIndex: " + lastCapitalIndex);
                }
                cedula = splitStr[2 + corrimiento].substring(lastCapitalIndex - 10, lastCapitalIndex);
                //Erase initial zeros in ID
                cedula = cedula.replaceFirst(ERASE_ZEROS, "");
                primerApellido = splitStr[2 + corrimiento].substring(lastCapitalIndex);
                segundoApellido = splitStr[3 + corrimiento];
                primerNombre = splitStr[4 + corrimiento];
                *//**
     * Se verifica que contenga segundo nombre
     *//*
                //if the array contains the rH
                int findRH = 6;
                boolean flagRH = false;
                //Verificación si posee más de un nombre
                for (int i = 5 + corrimiento; i < splitStr.length; i++) {
                    for (String bloodKinds : bloodKind) {
                        //Si no contiene el tipo de sangre o comienza con un número, debe ser un nombre
                        if (!splitStr[i].contains(bloodKinds) && !Character.isDigit(splitStr[i].charAt(0))) {
                            flagRH = false;
                        } else {
                            flagRH = true;
                            break;
                        }
                    }
                    if (!flagRH) {
                        segundoNombre = segundoNombre + " " + splitStr[i];
                    } else {
                        findRH = i;
                        //Erase spaces
                        segundoNombre = segundoNombre.trim();
                        break;
                    }
                }
                sexo = splitStr[findRH].contains("M") ? "M" : "F";
                rh = splitStr[findRH].substring(splitStr[findRH].length() - 2);
                rh = fixRH(rh);
                fechaNacimiento = splitStr[findRH].substring(2, 10);
            } else {
                int corrimiento = 0;
                Pattern pat = Pattern.compile("[A-Z]");
                if (splitStr[2 + corrimiento].length() > 7) {
                    corrimiento--;
                }
                boolean colombianTi = false;
                Matcher match = pat.matcher(splitStr[3 + corrimiento]);
                int lastCapitalIndex;
                //To colombian CC
                if (match.find()) {
                    lastCapitalIndex = match.start();
                } else {
                    //To colombian IT
                    colombianTi = true;
                    lastCapitalIndex = 18;
                }

                cedula = splitStr[3 + corrimiento].substring(lastCapitalIndex - 10, lastCapitalIndex);

                //Erase initial zeros in ID
                cedula = cedula.replaceFirst(ERASE_ZEROS, "");
                if (colombianTi) {
                    corrimiento = 0;
                    primerApellido = splitStr[3 + corrimiento];
                } else {
                    primerApellido = splitStr[3 + corrimiento].substring(lastCapitalIndex);
                }
                segundoApellido = splitStr[4 + corrimiento];
                primerNombre = splitStr[5 + corrimiento];
                *//**
     * Se verifica que contenga segundo nombre
     *//*
                //if the array contains the rH
                int findRH = 7;
                boolean flagRH = false;
                //Verificación si posee más de un nombre
                for (int i = 6 + corrimiento; i < splitStr.length; i++) {
                    for (String bloodKinds : bloodKind) {
                        //Si no contiene el tipo de sangre o comienza con un número, debe ser un nombre
                        if (!splitStr[i].contains(bloodKinds) && !Character.isDigit(splitStr[i].charAt(0))) {
                            flagRH = false;
                        } else {
                            flagRH = true;
                            break;
                        }
                    }
                    if (!flagRH) {
                        segundoNombre = segundoNombre + " " + splitStr[i];
                    } else {
                        findRH = i;
                        //Erase spaces
                        segundoNombre = segundoNombre.trim();
                        break;
                    }
                }
                sexo = splitStr[findRH].contains("M") ? "M" : "F";
                rh = splitStr[findRH].substring(splitStr[findRH].length() - 2);
                rh = fixRH(rh);
                fechaNacimiento = splitStr[findRH].substring(2, 10);
            }
            */

    /**
     * Se setea el objeto con los datos
     *//*
            citizenDocument.setPrimerNombre(primerNombre);
            citizenDocument.setSegundoNombre(segundoNombre);
            citizenDocument.setPrimerApellido(primerApellido);
            citizenDocument.setSegundoApellido(segundoApellido);
            citizenDocument.setCedula(cedula);
            citizenDocument.setSexo(sexo);
            citizenDocument.setFechaNacimiento(fechaNacimiento);
            citizenDocument.setRh(rh);

        } else {
            Log.d(PDF_417, "No barcode capturado");
            return citizenDocument;
        }
        Timber.e("STRINGBARCODE: %s",dataParsing);
        return citizenDocument;
    }*/
    public static ColombianCitizenBarcode parseDataCode(String dataParsing, Context context) {
        //To get the different kinds
        String[] bloodKind = context.getResources().getStringArray(R.array.kind_of_blood);
        ColombianCitizenBarcode citizenDocument = new ColombianCitizenBarcode();
        String fullName = "";
        boolean idReaded = false;
        if (dataParsing != null) {
            try{
            String barCode = dataParsing;
            Log.d("PDF417", "Barcode length: " + barCode.length());
            if (barCode.length() < 150) {
                //Verificar sino pudo extraer la info del barcode
                return citizenDocument;
            }
            citizenDocument = new ColombianCitizenBarcode();
            String[] nombres = {"", "", "", ""};
            String cedula = "";
            String rh = "";
            String fechaNacimiento = "";
            String sexo = "";
            String alphaAndDigits = barCode.replaceAll("[^\\p{Alpha}\\p{Digit}\\+\\_\\-\\ ]", "/");
            alphaAndDigits = alphaAndDigits.replaceAll("u0000", "/");
            String[] splitStr;
            if (alphaAndDigits.contains("PubDSK")) {
                splitStr = alphaAndDigits.split("\\/+");
            } else {
                splitStr = alphaAndDigits.split("\\s+");
            }
            if (alphaAndDigits.substring(0, 1).equals("I")) {
                boolean idLastname = false;
                for (String split : splitStr) {
                    if (idReaded) {
                        if ((split.contains("+") || split.contains("-")) && split.length() > 5) {
                            nombres = findNames(fullName);
                            if (split.contains("M")) {
                                sexo = "M";
                            } else if (split.contains("F")) {
                                sexo = "F";
                            } else {
                                sexo = "T";
                            }
                            fechaNacimiento = split.substring(2, 6) + "-" + split.substring(6, 8) + "-" + split.substring(8, 10);
                            rh = split.substring(16);
                            break;
                        } else {
                            if (Character.isDigit(split.charAt(0))) {
                                nombres = findNames(fullName);
                                if (split.contains("M")) {
                                    sexo = "M";
                                } else if (split.contains("F")) {
                                    sexo = "F";
                                } else {
                                    sexo = "T";
                                }
                                fechaNacimiento = split.substring(2, 6) + "-" + split.substring(6, 8) + "-" + split.substring(8, 10);
                            } else if (split.length() == 2) {
                                rh = split;
                                break;
                            } else {
                                if (!split.equals(" ")) {
                                    if (!idLastname) {
                                        fullName = split.trim();
                                        idLastname = true;
                                    } else {
                                        fullName = fullName + " " + split.trim();
                                    }
                                }
                            }
                        }
                    }
                    if (split != splitStr[1] && !idReaded && split != splitStr[0]) {
                        String data = split;
                        if (splitStr[2].length() > 7) {
                            data = split.substring(8);
                            idReaded = true;
                        } else if (splitStr[2].length() < 7 && split.length() > 7) {
                            idReaded = true;
                        }
                        int pos = 0;
                        for (int i = 0; i < data.length(); i++) {
                            if (Character.isLetter(data.charAt(i))) {
                                idLastname = true;
                                break;
                            }
                            pos++;
                        }
                        if (idLastname) {
                            cedula = data.substring(0, pos).replaceFirst(ERASE_ZEROS, "");
                            fullName = data.substring(pos, data.length()).trim();
                        } else {
                            cedula = data;
                        }
                    }
                }
            } else {
                for (String split : splitStr) {
                    if (idReaded) {
                        if ((split.contains("+") || split.contains("-")) && split.length() > 5) {
                            nombres = findNames(fullName);
                            if (split.contains("M")) {
                                sexo = "M";
                            } else if (split.contains("F")) {
                                sexo = "F";
                            } else {
                                sexo = "T";
                            }
                            fechaNacimiento = split.substring(2, 6) + "-" + split.substring(6, 8) + "-" + split.substring(8, 10);
                            rh = split.substring(16);
                            break;
                        } else {
                            if (Character.isDigit(split.charAt(0))) {
                                nombres = findNames(fullName);
                                if (split.contains("M")) {
                                    sexo = "M";
                                } else if (split.contains("F")) {
                                    sexo = "F";
                                } else {
                                    sexo = "T";
                                }
                                //Format YYYY-MM-DD
                                fechaNacimiento = split.substring(2, 6) + "-" + split.substring(6, 8) + "-" + split.substring(8, 10);
                            } else if (split.length() == 2) {
                                rh = split;
                                break;
                            } else {
                                if (!split.equals(" ")) {
                                    fullName = fullName + " " + split.trim();
                                }
                            }
                        }
                    /*if (split.contains("+") || split.contains("-") && split.length() > 2) {
                        //se separa el nombre
                        nombres = findNames(fullName);
                        if (split.contains("M")) {
                            sexo = "M";
                        }else{
                            sexo = "F";
                        }
                        fechaNacimiento = split.substring(2,6)+"-"+split.substring(6,8)+"-"+split.substring(8,10);
                        rh = split.substring(16,split.length());
                        break;
                    }else{
                        if (split.contains("+") || split.contains("-")){
                        }
                        fullName = fullName + " " + split.trim();
                    }*/
                    }
                    if (split.length() > 10 && !split.contains("pubDSK") && !idReaded && !split.equals(splitStr[0]) && !split.contains("PubDSK")) {
                        boolean numberFound = false;
                        for (int x = 0; x < split.length(); x++) {
                            if (Character.isDigit(split.charAt(x))) {
                                numberFound = true;
                            } else if (numberFound && split.length() > x + 2) {
                                cedula = split.substring(x - 10, x).replaceFirst(ERASE_ZEROS, "");
                                fullName = split.substring(x, split.length()).trim();
                                idReaded = true;
                                break;
                            }
                        }
                    }
                }
            }
            citizenDocument.setPrimerNombre(nombres[2]);
            citizenDocument.setSegundoNombre(nombres[3]);
            citizenDocument.setPrimerApellido(nombres[0]);
            citizenDocument.setSegundoApellido(nombres[1]);
            citizenDocument.setCedula(cedula);
            citizenDocument.setSexo(sexo);
            citizenDocument.setFechaNacimiento(fechaNacimiento);
            rh = fixRHBarcode(rh, context);
            citizenDocument.setRh(rh);
        } catch (Exception e){
                Timber.e("Barcode detectado, pero no parseado == %s", e.getMessage());
                return citizenDocument;
            }
        }else {
            Timber.tag(PDF_417).d("No barcode capturado");
            return citizenDocument;
        }
        return citizenDocument;
    }

    public static String fixRHBarcode(String rh, Context context) {
        if (rh == null || rh.isEmpty()) {
            return "";
        } else {
            //To get the different kinds
            String[] bloodKind = context.getResources().getStringArray(R.array.kind_of_blood);
            for (String bloodKinds : bloodKind) {
                //if starsWith or contains
                if (rh.startsWith(bloodKinds) || rh.contains(bloodKinds)) {
                    rh = bloodKinds;
                    break;
                }
            }
            return rh;
        }
    }

    public static String fixCCDRH(String rh, Context context) {
        if (rh == null || rh.isEmpty()) {
            return "";
        } else {
            //To get the different kinds
            String[] bloodKind = context.getResources().getStringArray(R.array.kind_of_blood);
            boolean bloodFounded = false;
            for (String bloodKinds : bloodKind) {
                //if starsWith or contains
                if (rh.startsWith(bloodKinds) || rh.contains(bloodKinds)) {
                    rh = bloodKinds;
                    bloodFounded = true;
                    break;
                }
            }
            if (bloodFounded) {
                return rh;
            } else {
                return "";
            }
        }
    }

    private static String[] findNames(String fullName) {
        String[] nombres = new String[4];
        String[] splitName = fullName.split("\\s+");
        switch (splitName.length) {
            case 3:
                nombres[0] = splitName[0];
                nombres[1] = splitName[1];
                nombres[2] = splitName[2];
                nombres[3] = "";
                break;
            case 4:
                nombres[0] = splitName[0];
                nombres[1] = splitName[1];
                nombres[2] = splitName[2];
                nombres[3] = splitName[3];
                break;
            case 5:
                nombres[0] = splitName[0];
                nombres[1] = splitName[1];
                nombres[2] = splitName[2];
                nombres[3] = splitName[3] + " " + splitName[4];
                break;
            default:
                nombres[0] = fullName;
                break;
        }
        return nombres;
    }

    private static String fixRH(String rh) {
        if (rh.contains("+") || rh.contains("-")) {
            return rh;
        }
        if (!rh.contains("-")) {
            rh = rh.substring(0, rh.length() - 1) + "-";
        }
        return rh;
    }

    public static boolean onGetTotalName(String stringAnverso, ColombianCitizenBarcode citizenDocument) {
        double maxNamePercentage = 0.0;
        double maxLastNamePercentage = 0.0;
        double namePercentage = 0;
        double lastNamePercentage = 0;
        String auxName = "", auxLastName = "";

        List<String> bagString = new ArrayList<>();
        bagString = initBagAnverso(bagString);

        List<String> myList = new ArrayList<>(Arrays.asList(stringAnverso.toUpperCase().split(BACK_LINE_REVERSO)));
        if (myList.size() == 1) {
            myList = new ArrayList<>(Arrays.asList(stringAnverso.toUpperCase().split(BACK_LINE_ANVERSO)));
        }

        myList.removeAll(bagString);
        String citizenNames = citizenDocument.getPrimerNombre() + " " + citizenDocument.getSegundoNombre();
        Timber.w("citizenNames %s", citizenNames);
        String citizenLastNames = citizenDocument.getPrimerApellido() + " " + citizenDocument.getSegundoApellido();
        Timber.w("citizenLastNames %s", citizenLastNames);
        if (!citizenNames.isEmpty() && !citizenLastNames.isEmpty()) {
            for (int i = 0; i < myList.size(); i++) {

                namePercentage = diceCoefficientOptimized(citizenNames.toUpperCase(), myList.get(i).toUpperCase());
                lastNamePercentage = diceCoefficientOptimized(citizenLastNames.toUpperCase(), myList.get(i).toUpperCase());
                Timber.w(" myList.get(i).toUpperCase() %s", myList.get(i).toUpperCase());
                if (namePercentage > maxNamePercentage) {
                    auxName = myList.get(i).toUpperCase();
                    maxNamePercentage = namePercentage;
                }
                if (lastNamePercentage > maxLastNamePercentage) {
                    auxLastName = myList.get(i).toUpperCase();
                    maxLastNamePercentage = lastNamePercentage;
                }
            }
            double total = ((maxNamePercentage + maxLastNamePercentage) / 2) * Constants.ONE_HUNDRED;
            Timber.w("Value of name %s :", total);

            return total > TOLERANCE_NAME;

        } else {
            Timber.w("Value of name %s %", false);
            //If the data of barcode or OCR is empty or null
            return false;
        }
    }

    //Colombia document names
    public static List<String> getOCRNames(String anversoText, ColombianCitizenBarcode citizenDocument, GuardarCiudadano saveResident) {
        String[] stringValues = anversoText.split("\n");
        List<String> values = Arrays.asList(stringValues);
        List<String> bagString = new ArrayList<>();
        bagString = StringUtils.initBagAnverso(bagString);
        Boolean isValidData = true;
        List<String> tempData = new ArrayList<>();
        for (int j = 0; j < values.size(); j++) {
            boolean isBadString = false;
            for (int i = 0; i < bagString.size(); i++) {
                int distance = StringUtils.computeLevenshteinDistance(bagString.get(i).replace(" ", ""), values.get(j).replace(" ", ""));
                int value = 100 - (distance * 10);
                if (value > 60) {
                    isBadString = true;
                    break;
                }
            }
            if (!isBadString) {
                tempData.add(values.get(j));
            }
        }
        List<String> data = new ArrayList<>();
        String names = "", lastNames = "";
        for (int i = 0; i < tempData.size(); i++) {
            if (!StringUtils.isNumeric(tempData.get(i).replace(".", ""))) {
                data.add(tempData.get(i));
                if (saveResident != null) {
                    int distanceNames = StringUtils.computeLevenshteinDistance(tempData.get(i), saveResident.getPrimerNombre() + " " + saveResident.getSegundoNombre());
                    int distanceLastNames = StringUtils.computeLevenshteinDistance(tempData.get(i), saveResident.getPrimerApellido() + " " + saveResident.getSegundoApellido());
                    int valueNames = 100 - (distanceNames * 10);
                    int valueLastNames = 100 - (distanceLastNames * 10);
                    if (valueNames > 80) {
                        names = tempData.get(i);
                    }
                    if (valueLastNames > 80) {
                        lastNames = tempData.get(i);
                    }
                } else if (citizenDocument != null) {
                    int distanceNames = StringUtils.computeLevenshteinDistance(tempData.get(i), citizenDocument.getPrimerNombre() + " " + citizenDocument.getSegundoNombre());
                    int distanceLastNames = StringUtils.computeLevenshteinDistance(tempData.get(i), citizenDocument.getPrimerApellido() + " " + citizenDocument.getSegundoApellido());
                    int valueNames = 100 - (distanceNames * 10);
                    int valueLastNames = 100 - (distanceLastNames * 10);
                    if (valueNames > 80) {
                        names = tempData.get(i);
                    }
                    if (valueLastNames > 80) {
                        lastNames = tempData.get(i);
                    }
                }
            }
        }
        if ((names.isEmpty() || lastNames.isEmpty()) && data.size() > 0) {
            for (int n = 0; n < data.size(); n++) {
                for (char c : data.get(n).toCharArray()) {
                    if (Character.isDigit(c)) {
                        isValidData = false;
                    }
                }
                if (isValidData && lastNames.isEmpty()) {
                    lastNames = data.get(n);
                } else if (isValidData && names.isEmpty()) {
                    names = data.get(n);
                }
                isValidData = true;
            }
        }

        List<String> ocrNames = new ArrayList<>();
        ocrNames.add(names);
        ocrNames.add(lastNames);
        return ocrNames;
    }

    public static String getOCRRh(String reversoText, ColombianCitizenBarcode citizenDocument) {
        String[] stringValues = reversoText.split("\n");
        List<String> values = Arrays.asList(stringValues);
        List<String> bagString = new ArrayList<>();
        bagString = StringUtils.initBagReverso(bagString);
        //Boolean isValidData = true;

        List<String> tempData = new ArrayList<>();
        for (int j = 0; j < values.size(); j++) {
            boolean isBadString = false;
            for (int i = 0; i < bagString.size(); i++) {
                int distance = StringUtils.computeLevenshteinDistance(bagString.get(i), values.get(j));
                int value = 100 - (distance * 10);
                if (value > 60) {
                    isBadString = true;
                    break;
                }
            }

            if (!isBadString) {
                tempData.add(values.get(j));
            }
        }

        List<String> data = new ArrayList<>();
        String rhValue = "";
        for (int i = 0; i < tempData.size(); i++) {
            if (tempData.get(i).length() == 2 && (tempData.get(i).contains("+") || tempData.get(i).contains("-") || tempData.get(i).contains("."))) {
                data.add(tempData.get(i));

                if (citizenDocument != null) {
                    int distanceRh = StringUtils.computeLevenshteinDistance(tempData.get(i), citizenDocument.getRh());
                    int valueRh = 100 - (distanceRh * 10);

                    if (valueRh > 80) {
                        rhValue = tempData.get(i);
                    }
                }
            }
        }

        return rhValue;
    }

    public static boolean onGetTotalId(String stringAnverso, ColombianCitizenBarcode citizenDocument) {
        double maxIdPercentage = 0.0;
        double idPercentage = 0;
        String auxId = "";

        List<String> bagString = new ArrayList<>();
        bagString = initBagAnverso(bagString);

        //remove dots
        stringAnverso = stringAnverso.replace(".", "");
        List<String> myList = new ArrayList<>(Arrays.asList(stringAnverso.toUpperCase().split(BACK_LINE_REVERSO)));
        if (myList.size() == 1) {
            Arrays.asList(stringAnverso.toUpperCase().split(BACK_LINE_ANVERSO));
        }
        myList.removeAll(bagString);
        String idDocument = citizenDocument.getCedula();
        if (!idDocument.isEmpty()) {
            for (int i = 0; i < myList.size(); i++) {

                idPercentage = diceCoefficientOptimized(idDocument.toUpperCase(), myList.get(i).toUpperCase());
                if (idPercentage > maxIdPercentage) {
                    auxId = myList.get(i).toUpperCase();
                    maxIdPercentage = idPercentage;
                }
            }
            double total = maxIdPercentage * Constants.ONE_HUNDRED;
            //Log.w("Value of name", total + "%");

            return total > TOLERANCE_ID;

        } else {
            //If the data of barcode or OCR is empty or null
            return false;
        }
    }

    public static String getOCRBornPlace(String reversoText) {
        String[] stringValues = reversoText.split("\n");
        List<String> values = Arrays.asList(stringValues);
        List<String> bagString = new ArrayList<>();
        bagString = StringUtils.initBagReverso(bagString);
        //Boolean isValidData = true;

        List<String> tempData = new ArrayList<>();
        for (int j = 0; j < values.size(); j++) {
            boolean isBadString = false;
            for (int i = 0; i < bagString.size(); i++) {
                int distance = StringUtils.computeLevenshteinDistance(bagString.get(i), values.get(j));
                int value = 100 - (distance * 10);
                if (value > 60) {
                    isBadString = true;
                    break;
                }
            }

            if (!isBadString) {
                tempData.add(values.get(j));
            }
        }

        List<String> data = new ArrayList<>();
        String bornPlace = "";
        for (int i = 0; i < tempData.size(); i++) {
            if (tempData.get(i).length() == 2 && (tempData.get(i).contains("+") || tempData.get(i).contains("-") || tempData.get(i).contains("."))) {
                data.add(tempData.get(i));
            }
            if (i == 1 || i == 2) {
                if (bornPlace == "") {
                    bornPlace = tempData.get(i);
                } else {
                    bornPlace = bornPlace + " " + tempData.get(i);
                }
            }
        }

        return bornPlace;
    }

    public static ColombianMinorCitizen getColombianMinorCitizen(DocumentoAnverso documentoAnverso, DocumentoReverso documentoReverso, ColombianCitizenBarcode citizenDocument, Context context, int documentResult) {
        ColombianMinorCitizen colombianMinorCitizen = new ColombianMinorCitizen();
        Boolean startString = false;
        if (documentoAnverso != null) {
            String stringAnverso = "";
            String[] tempAnverso = documentoAnverso.getstringAnverso().split("\n");
            for (int i = 0; i < tempAnverso.length; i++) {
                Log.e("Palabra anverso", tempAnverso[i]);
                if (startString) {
                    stringAnverso = stringAnverso + tempAnverso[i] + "\n";
                }
                if (computeLevenshteinDistance(tempAnverso[i], Constants.COLOMBIAN_TI_NATIONALITY) < 3 && !startString) {
                    startString = true;
                    stringAnverso = tempAnverso[i] + "\n";
                }
            }
            Log.e("STRANVERSO", stringAnverso);
            colombianMinorCitizen.setApellidos(getOCRNames(stringAnverso, citizenDocument, null).get(1));
            colombianMinorCitizen.setNombres(getOCRNames(stringAnverso, citizenDocument, null).get(0));
            colombianMinorCitizen.setNumero(getTIDocumentNumber(stringAnverso));
        }
        if (documentoReverso != null) {
            String stringReverso = documentoReverso.getStringReverso();
            String[] bornDates = getBornAgeCaseOne(stringReverso, context, documentResult);
            if (bornDates[0] != null) {
                colombianMinorCitizen.setFechaNacimiento(bornDates[0]);
            }
            if (bornDates[1] != null) {
                colombianMinorCitizen.setFechaVencimiento(bornDates[1]);
            }
            if (bornDates[2] != null) {
                colombianMinorCitizen.setFechaExpedicion(bornDates[2]);
            }
            colombianMinorCitizen.setLugarNacimiento(getOCRBornPlace(stringReverso));
            colombianMinorCitizen.setRh(getOCRRh(stringReverso, citizenDocument));
            String[] gender = getCharGender(stringReverso);
            if ((gender[0] != null) || (!gender[0].equals(""))) {
                colombianMinorCitizen.setSexo(gender[0]);
            } else if ((gender[1] != null) || (!gender[1].equals(""))) {
                colombianMinorCitizen.setSexo(gender[1]);
            }
        }

        return colombianMinorCitizen;
    }


    private static List<String> initBagAnverso(List<String> bagString) {
        bagString.clear();
        bagString.add("REPUBLICA DE COLOMBIA");
        bagString.add("IDENTIFICACION PERSONAL");
        bagString.add("IDENTEICACION PERSONAL");
        bagString.add("CEDULA DE CIUDADANIA");
        bagString.add("TARJETA DE IDENTIDAD");
        bagString.add("NUMERO");
        bagString.add("APELLIDOS");
        bagString.add("NOMBRES");
        bagString.add("FIRMA");
        return bagString;
    }

    public static List<String> initBagReverso(List<String> bagString) {
        bagString.clear();
        bagString.add("FECHA DE NACIMIENTO");
        bagString.add("LUGAR DE NACIMIENTO");
        bagString.add("ESTATURA");
        bagString.add("G.S. RH");
        bagString.add("SEXO");
        bagString.add("FECHA Y LUGAR DE EXPEDICION");
        bagString.add("FECHA DE VENCIMIENTO");
        bagString.add("INDICE DERECHO");
        bagString.add("REGISTRADOR NACIONAL" +
                "");
        return bagString;
    }

    public static boolean findWordInList(String text, String wordReference) {
        String[] stringValues;
        stringValues = text.split(BACK_LINE_REVERSO);
        if (stringValues.length == 1) {
            stringValues = text.split(BACK_LINE_ANVERSO);
        }
        double maxIdPercentage = 0.0;
        double idPercentage;
        for (String stringValue : stringValues) {
            idPercentage = StringUtils.diceCoefficientOptimized(wordReference, stringValue);
            if (idPercentage > maxIdPercentage) {
                maxIdPercentage = idPercentage;
            }
        }
        double total = maxIdPercentage * Constants.ONE_HUNDRED;
        // Log.w("Value of name", total + "%");
        return total > 20.0;
    }

    public static double diceCoefficientOptimized(String s, String t) //Todo mejor para comparar
    {
        // Verifying the input:
        if (s == null || t == null)
            return 0;
        // Quick check to catch identical objects:
        if (s.equals(t))
            return 1;
        // avoid exception for single character searches

        if (s.length() < 2 || t.length() < 2)
            return 0;
        // Create the bigrams for string s:

        if (s.length() + 4 <= t.length())
            return 0;

        final int n = s.length() - 1;
        final int[] sPairs = new int[n];

        for (int i = 0; i <= n; i++)
            if (i == 0)
                sPairs[i] = s.charAt(i) << 16;
            else if (i == n)
                sPairs[i - 1] |= s.charAt(i);
            else
                sPairs[i] = (sPairs[i - 1] |= s.charAt(i)) << 16;

        // Create the bigrams for string t:
        final int m = t.length() - 1;
        final int[] tPairs = new int[m];

        for (int i = 0; i <= m; i++)
            if (i == 0)
                tPairs[i] = t.charAt(i) << 16;
            else if (i == m)
                tPairs[i - 1] |= t.charAt(i);
            else
                tPairs[i] = (tPairs[i - 1] |= t.charAt(i)) << 16;
        // Sort the bigram lists:

        Arrays.sort(sPairs);

        Arrays.sort(tPairs);

        // Count the matches:
        int matches = 0, i = 0, j = 0;

        while (i < n && j < m) {
            if (sPairs[i] == tPairs[j]) {
                matches += 2;
                i++;
                j++;
            } else if (sPairs[i] < tPairs[j])
                i++;
            else
                j++;
        }
        return (double) matches / (n + m);
    }

    /**
     * LevenshteinDistance
     * https://es.wikipedia.org/wiki/Distancia_de_Levenshtein
     */
    private static int minimum(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }


    public static int computeLevenshteinDistance(String str1, String str2) {
        return computeLevenshteinDistance(str1.toCharArray(),
                str2.toCharArray());
    }

    private static int computeLevenshteinDistance(char[] str1, char[] str2) {
        int[][] distance = new int[str1.length + 1][str2.length + 1];

        for (int i = 0; i <= str1.length; i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= str2.length; j++) {
            distance[0][j] = j;
        }
        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                distance[i][j] = minimum(distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] +
                                ((str1[i - 1] == str2[j - 1]) ? 0 : 1));
            }
        }
        return distance[str1.length][str2.length];
    }

    public static String formatePercetage(double value) {
        if (value == Constants.ONE_HUNDRED) {
            return String.format(java.util.Locale.US, "%.0f", value);
        }
        return String.format(java.util.Locale.US, "%.2f", value);
    }

    public static int documentState(int state, String docPosition) {
        if (state == 0 && docPosition.equals(Constants.ANVERSO)) {
            state = 1;
        } else if (state == 0 && docPosition.equals(Constants.REVERSO)) {
            state = 2;
        } else if (state == 2 && docPosition.equals(Constants.ANVERSO)) {
            state = 3;
        } else if (state == 1 && docPosition.equals(Constants.REVERSO)) {
            state = 3;
        }
        return state;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static String isNullValues(String myString) {
        if (myString == null) {
            return "";
        } else {
            return myString;
        }
    }

    public static String getSubString(String mainString, String startString, String lastString) {
        String endString = "";
        int startIndex = mainString.indexOf(startString);
        startIndex += startString.length();
        int endIndex = mainString.indexOf(lastString, startIndex);
        endString = mainString.substring(startIndex, endIndex);
        return endString.trim();
    }

    public static String getBornDate(String bornDate, Context context, boolean isBorn) {
        if (bornDate == null || bornDate.isEmpty()) {
            return Constants.COLOMBIAN_DIGITAL_NOT_DATE;
        } else if (bornDate.length() != FORMAT_DATE_LENGTH) {
            return Constants.COLOMBIAN_DIGITAL_NOT_DATE;
        } else {
            String auxBornDate = bornDate;
            //To set the year
            auxBornDate = auxBornDate.substring(0, 2);
            String auxYear = auxBornDate;
            try {
                int formatYear = Integer.parseInt(auxYear);
                if (isBorn) {
                    if (formatYear <= 20) {
                        auxYear = "20" + auxBornDate;
                    } else {
                        auxYear = "19" + auxBornDate;
                    }
                } else {
                    auxYear = "20" + auxBornDate;
                }
            } catch (NumberFormatException e) {
                Timber.e("The value is not a number %s", Objects.requireNonNull(e.getMessage()));
                //Concat automatically
                auxYear = "19" + auxBornDate;
            }
            auxBornDate = bornDate;
            auxBornDate = auxBornDate.substring(2, 4);
            String auxMonth = auxBornDate;
            auxBornDate = bornDate;
            auxBornDate = auxBornDate.substring(4, bornDate.length());
            String auxDay = auxBornDate;
            auxBornDate = auxYear + "-" + auxMonth + "-" + auxDay;
            return auxBornDate;
        }
    }

    public static String getGenderCCD(String myString, Context context) {
        String[] genders = context.getResources().getStringArray(R.array.genders);
        String formatGender = "";
        for (String gender : genders) {
            //find gender
            if (myString.contains(gender)) {
                formatGender = gender;
                break;
            }
        }
        if (formatGender.isEmpty()) {
            formatGender = Constants.COLOMBIAN_DIGITAL_GENDER_404;
        }
        return formatGender;
    }


    //Founded in https://stackoverflow.com/questions/20981506/how-do-you-replace-groups-in-a-regular-expression
    public static String replacePattern(String auxFinder, String findPattern, String replacePattern) {
        Pattern p = Pattern.compile(findPattern);
        Matcher m = p.matcher(auxFinder);
        StringBuffer s = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(s, doReplace(Objects.requireNonNull(m.group(0)), findPattern, replacePattern));
        }
        m.appendTail(s);
        if (s.toString().equals("")) {
            return auxFinder;
        } else {
            return s.toString();
        }
    }

    public static String doReplace(String s, String findPattern, String replacePattern) {
        if (s.equals(findPattern)) {
            return replacePattern;
        }
        return "";
    }

    public static void appendLog(String text, boolean isImage) {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File logFile;
        if(isImage){
            logFile = new File(path, "logImage.txt");
        }else{
            logFile = new File(path, "logServices.txt");
        }
        //File logFile = new File("sdcard/Download/log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Timber.tag("Buffer error ").e(e);
            }
        }
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Timber.tag("Buffer error ").e(e);
        }
    }
}
