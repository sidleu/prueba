package com.reconosersdk.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    private static final String SKD_FORMAT_DATE = "yyyy-MM-dd";
    public static final SimpleDateFormat SDF = new SimpleDateFormat(SKD_FORMAT_DATE, Locale.US);
    public static final Calendar MY_CALENDAR = Calendar.getInstance();

    public static String setedCurrentDate(String fechaFinal, String fechaInicial) {
        return fechaFinal.concat(" --- ").concat(fechaInicial);
    }

    public static String getToday() {
        return new SimpleDateFormat(SKD_FORMAT_DATE, Locale.getDefault()).format(new Date());
    }

    public static String deleteMonth(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.MONTH, -1);
        return new SimpleDateFormat(SKD_FORMAT_DATE, Locale.getDefault()).format(c.getTime());
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

        if (s.length() <  t.length() + 1)
            return 0;

        final int n = s.length()-1;
        final int[] sPairs = new int[n];

        for (int i = 0; i <= n; i++)
            if (i == 0)
                sPairs[i] = s.charAt(i) << 16;
            else if (i == n)
                sPairs[i-1] |= s.charAt(i);
            else
                sPairs[i] = (sPairs[i-1] |= s.charAt(i)) << 16;

        // Create the bigrams for string t:
        final int m = t.length()-1;
        final int[] tPairs = new int[m];

        for (int i = 0; i <= m; i++)
            if (i == 0)
                tPairs[i] = t.charAt(i) << 16;
            else if (i == m)
                tPairs[i-1] |= t.charAt(i);
            else
                tPairs[i] = (tPairs[i-1] |= t.charAt(i)) << 16;
        // Sort the bigram lists:

        Arrays.sort(sPairs);

        Arrays.sort(tPairs);

        // Count the matches:
        int matches = 0, i = 0, j = 0;

        while (i < n && j < m)

        {
            if (sPairs[i] == tPairs[j])
            {
                matches += 2;
                i++;
                j++;
            }
            else if (sPairs[i] < tPairs[j])
                i++;
            else
                j++;
        }
        return (double)matches/(n+m);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
