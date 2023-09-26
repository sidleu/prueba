package com.reconosersdk.reconosersdk.utils;

import android.util.Log;

import com.reconosersdk.reconosersdk.citizens.MexicanCitizen;

import java.util.Arrays;
import java.util.List;

import static com.reconosersdk.reconosersdk.utils.StringUtils.BACK_LINE_ANVERSO;

public class StringMexicanUtils {

    public static MexicanCitizen getMexicanCitizen(String stringAnverso) {

        MexicanCitizen mexicanCitizen = new MexicanCitizen();
        List<String> data = Arrays.asList(stringAnverso.split(BACK_LINE_ANVERSO));

        Boolean isNamesLabel = false;
        boolean isHomeLabel = false;
        String names = "", home = "";

        for (int i = 0; i < data.size(); i++) {

            String lineText = data.get(i);

            int valueNames = (100 - (StringUtils.computeLevenshteinDistance(lineText, Constants.MEXICAN_NAMES)) * 10);
            int valueHome = (100 - (StringUtils.computeLevenshteinDistance(lineText, Constants.MEXICAN_HOME)) * 10);
            int valueAge = (100 - (StringUtils.computeLevenshteinDistance(lineText.split(" ")[0], Constants.MEXICAN_AGE)) * 10);
            int valueSex = (100 - (StringUtils.computeLevenshteinDistance(lineText.split(" ")[0], Constants.MEXICAN_SEX)) * 10);
            int valueInvoice = (100 - (StringUtils.computeLevenshteinDistance(lineText.split(" ")[0], Constants.MEXICAN_INVOICE)) * 10);
            int valueInvoice2 = (100 - (StringUtils.computeLevenshteinDistance(lineText.split(" ")[0], Constants.MEXICAN_INVOICE_2)) * 10);
            int valueCurp = (100 - (StringUtils.computeLevenshteinDistance(lineText.split(" ")[0], Constants.MEXICAN_CURP)) * 10);
            int valueState = (100 - (StringUtils.computeLevenshteinDistance(lineText.split(" ")[0], Constants.MEXICAN_STATE)) * 10);
            int valueLocation = (100 - (StringUtils.computeLevenshteinDistance(lineText.split(" ")[0], Constants.MEXICAN_LOCATION)) * 10);
            int valueSection = (100 - (StringUtils.computeLevenshteinDistance(lineText.split(" ")[0], Constants.MEXICAN_SECTION)) * 10);
            int valueIssue = (100 - (StringUtils.computeLevenshteinDistance(lineText.split(" ")[0], Constants.MEXICAN_ISSUE)) * 10);
            int valueMunicipality = (100 - (StringUtils.computeLevenshteinDistance(lineText.split(" ")[0], Constants.MEXICAN_MUNICIPALITY)) * 10);

            if (valueNames > 80) {
                isNamesLabel = true;
            } else if (isNamesLabel && valueHome < 80 && valueAge < 80 && valueSex < 80) {
                names = names + lineText + " ";
            }

            if (valueHome > 80) {
                isNamesLabel = false;
                isHomeLabel = true;
            } else if (isHomeLabel && valueInvoice < 80 && valueInvoice2 < 80 && valueAge < 80 && valueSex < 80) {
                home = home + lineText + ", ";
            }

            if (valueInvoice > 80 || valueInvoice2 > 80) {
                isHomeLabel = false;
                mexicanCitizen.setFolio(lineText.split(" ")[1]);
            }

            Log.w("@@@@@@@@@@@@@@@@@@@", lineText + " " + isNamesLabel + " " + valueAge + " " + valueSex);
            String[] dataString = lineText.split(" ");
            if (dataString.length > 1) {
                if (valueCurp > 80) {
                    mexicanCitizen.setCurp(dataString[1]);
                }

                if (valueState > 80) {
                    mexicanCitizen.setEstado(dataString[1]);
                }

                if (valueLocation > 80) {
                    mexicanCitizen.setLocalidad(dataString[1]);
                }

                if (valueSection > 80) {
                    mexicanCitizen.setSeccion(dataString[1]);
                }

                if (valueAge > 80) {
                    mexicanCitizen.setEdad(dataString[1]);
                }

                if (valueSex > 80) {
                    mexicanCitizen.setSexo(dataString[1]);
                }

                if (valueSex > 80) {
                    mexicanCitizen.setSexo(dataString[1]);
                }

                if (valueIssue > 80) {
                    mexicanCitizen.setEmision(dataString[1]);
                }

                if (valueMunicipality > 80) {
                    mexicanCitizen.setMunicipio(dataString[1]);
                }
            }

            if (lineText.contains("CLAVE") || (lineText.contains("ELECTOR") && (!lineText.contains("ELECTORES") || !lineText.contains("ELECTORAL")))) {
                List<String> keyData = Arrays.asList(lineText.split(" "));
                if (keyData.size() > 3) {
                    mexicanCitizen.setClaveElector(keyData.get(keyData.size() - 1));
                }
            }

            if (lineText.contains("ANO") || lineText.contains("AÑO") || lineText.contains("REGISTRO")) {
                List<String> keyData = Arrays.asList(lineText.split(" "));
                if (keyData.size() > 4) {
                    String str1 = keyData.get((keyData.size() - 2));
                    String str2 = keyData.get((keyData.size() - 1));
                    if (StringUtils.isNumeric(str1) && StringUtils.isNumeric(str2)) {
                        mexicanCitizen.setAnoRegistro(str1 + " " + str2);
                    } else if (StringUtils.isNumeric(str2)) {
                        mexicanCitizen.setAnoRegistro(str2);
                    }
                }
            }

            if (lineText.contains("VIGENCIA") || lineText.contains("HASTA")) {
                List<String> keyData = Arrays.asList(lineText.split(" "));
                if (keyData.size() > 3) {
                    mexicanCitizen.setVigencia(keyData.get(keyData.size() - 1));
                }

                if (mexicanCitizen.getEmision() == null && keyData.size() == 4) {
                    mexicanCitizen.setVigencia(keyData.get(0));
                }

                if (mexicanCitizen.getEmision() == null && keyData.size() == 5) {
                    mexicanCitizen.setVigencia(keyData.get(1));
                }
            }
        }

        mexicanCitizen.setNombres(names);
        mexicanCitizen.setDomicilio(home);
        return mexicanCitizen;
    }
}
