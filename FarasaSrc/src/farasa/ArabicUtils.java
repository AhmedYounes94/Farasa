/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package farasa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author kareemdarwish
 */
public class ArabicUtils implements java.io.Serializable {
    // ALL Arabic letters \U0621-\U063A\U0641-\U064A
    public static final String AllArabicLetters = "\u0621\u0622\u0623\u0624\u0625\u0626\u0627\u0628\u0629\u062A\u062B\u062C\u062D\u062E\u062F"
            + "\u0630\u0631\u0632\u0633\u0634\u0635\u0636\u0637\u0638\u0639\u063A\u0641\u0642\u0643\u0644\u0645\u0646\u0647\u0648\u0649\u064A";
    // ALL Hindi digits \U0660-\U0669
    public static final String AllHindiDigits = "\u0660\u0661\u0662\u0663\u0664\u0665\u0666\u0667\u0668\u0669";
    // ALL Arabic letters and Hindi digits \U0621-\U063A\U0641-\U064A\U0660-\U0669
    public static final String AllArabicLettersAndHindiDigits = "\u0621\u0622\u0623\u0624\u0625\u0626\u0627\u0628\u0629\u062A\u062B\u062C\u062D\u062E\u062F"
            + "\u0630\u0631\u0632\u0633\u0634\u0635\u0636\u0637\u0638\u0639\u063A\u0641\u0642\u0643\u0644\u0645\u0646\u0647\u0648\u0649\u064A\u0660\u0661\u0662\u0663\u0664\u0665\u0666\u0667\u0668\u0669";
    public static final String AllDigits = "0123456789";
    public static final String ALLDelimiters = "\u0020\u0000-\u002F\u003A-\u0040\u007B-\u00BB\u005B-\u005D\u005F-\u0060\\^\u0600-\u060C\u06D4-\u06ED\ufeff";
    
    public static ArrayList<String> ArabicStopWords = new ArrayList<String>();
    
    public static final char ALEF = '\u0627';
    public static final char ALEF_MADDA = '\u0622';
    public static final char ALEF_HAMZA_ABOVE = '\u0623';
    public static final char ALEF_HAMZA_BELOW = '\u0625';

    public static final char HAMZA = '\u0621';
    public static final char HAMZA_ON_NABRA = '\u0624';
    public static final char HAMZA_ON_WAW = '\u0626';

    public static final char YEH = '\u064A';
    public static final char DOTLESS_YEH = '\u0649';

    public static final char TEH_MARBUTA = '\u0629';
    public static final char HEH = '\u0647';
    
    public static final Pattern emailRegex = Pattern.compile("[a-zA-Z0-9\\-\\._]+@[a-zA-Z0-9\\-\\._]+");
    public static final Pattern pAllDiacritics = Pattern.compile("[\u0640\u064b\u064c\u064d\u064e\u064f\u0650\u0651\u0652\u0670]");
    public static final Pattern pAllNonCharacters = Pattern.compile("[\u0020\u2000-\u200F\u2028-\u202F\u205F-\u206F\uFEFF]+");
    public static final Pattern pAllDelimiters = Pattern.compile("[" + ALLDelimiters + "]+");
    
    public static final String prefixes[] = {
        // "????", "??", "??", "??", "??", "??", "????"
        "\u0627\u0644", "\u0648", "\u0641", "\u0628", "\u0643", "\u0644", "\u0644\u0644", "??"
    };

    public static final String suffixes[] = {
        // "??", "????", "??", "??", "??????", "??????", "????", "????", "????", "????", "????",
        // "??", "????", "????", "????", "????", "????", "??", "??", "??"
        "\u0647", "\u0647\u0627", "\u0643", "\u064a", "\u0647\u0645\u0627", "\u0643\u0645\u0627", "\u0646\u0627", "\u0643\u0645", "\u0647\u0645", "\u0647\u0646", "\u0643\u0646",
        "\u0627", "\u0627\u0646", "\u064a\u0646", "\u0648\u0646", "\u0648\u0627", "\u0627\u062a", "\u062a", "\u0646", "\u0629"
    }; 
   
    public ArabicUtils()
    {
        String[] stop = {"??", "????", "????", "????", "????", "??????", "????", "??????", "??????", "????????", "??????????", "????", "????", "??????", "????????", "????", "????", "??????", "??????", "??????"};
        ArabicStopWords = new ArrayList<String>();
        for (String s : stop) {
            ArabicStopWords.add(s);
        }
    }
    
    public static String buck2morph(String input) {
        String buck = "$Y\'|&}*<>&}";
        String morph = "PyAAAAOAAAA";
        input = StringUtils.replaceChars(input, buck, morph);
        // input = input.replace('$', 'P').replace('Y', 'y').replace('\'', 'A').replace('|', 'A').replace('&', 'A').replace('}', 'A').replace('*', 'O');
        // input = input.replace("<", "A").replace(">", "A").replace("&", "A").replace("'", "A").replace("}", "A");
        return input;
    }

    public static String utf82buck(String input) {
        String ar = "\u0627\u0625\u0622\u0623\u0621\u0628\u062a\u062b\u062c\u062d\u062e\u062f\u0630\u0631\u0632\u0633\u0634\u0635\u0636\u0637\u0638\u0639\u063a\u0641\u0642\u0643\u0644\u0645\u0646\u0647\u0648\u064a\u0649\u0629\u0624\u0626\u064e\u064b\u064f\u064c\u0650\u064d\u0652\u0651";
        String buck = "A<|>'btvjHxd*rzs$SDTZEgfqklmnhwyYp&}aFuNiKo~";
        input = StringUtils.replaceChars(input, ar, buck);
        return input;
    }
    
    public static String utf82buckWithoutDiacritics(String input) {
        input = input.replace("\u0627", "A").replace("\u0625", "<").replace("\u0622", "|").replace("\u0623", ">").replace("\u0621", "'");
        input = input.replace("\u0628", "b").replace("\u062a", "t").replace("\u062b", "v").replace("\u062c", "j").replace("\u062d", "H");
        input = input.replace("\u062e", "x").replace("\u062f", "d").replace("\u0630", "*").replace("\u0631", "r").replace("\u0632", "z");
        input = input.replace("\u0633", "s").replace("\u0634", "$").replace("\u0635", "S").replace("\u0636", "D").replace("\u0637", "T");
        input = input.replace("\u0638", "Z").replace("\u0639", "E").replace("\u063a", "g").replace("\u0641", "f").replace("\u0642", "q");
        input = input.replace("\u0643", "k").replace("\u0644", "l").replace("\u0645", "m").replace("\u0646", "n").replace("\u0647", "h");
        input = input.replace("\u0648", "w").replace("\u064a", "y").replace("\u0649", "Y").replace("\u0629", "p").replace("\u0624", "&");
        input = input.replace("\u0626", "}");
        return input;
    }
    
    public static String buck2utf8(String input) {
        String ar = "\u0627\u0625\u0622\u0623\u0621\u0628\u062a\u062b\u062c\u062d\u062e\u062f\u0630\u0631\u0632\u0633\u0634\u0635\u0636\u0637\u0638\u0639\u063a\u0641\u0642\u0643\u0644\u0645\u0646\u0647\u0648\u064a\u0649\u0629\u0624\u0626\u064e\u064b\u064f\u064c\u0650\u064d\u0652\u0651";
        String buck = "A<|>'btvjHxd*rzs$SDTZEgfqklmnhwyYp&}aFuNiKo~";
        input = StringUtils.replaceChars(input, buck, ar);
//        input = input.replace("A", "\u0627").replace("<", "\u0625").replace("|", "\u0622").replace(">", "\u0623").replace("'", "\u0621");
//        input = input.replace("b", "\u0628").replace("t", "\u062a").replace("v", "\u062b").replace("j", "\u062c").replace("H", "\u062d");
//        input = input.replace("x", "\u062e").replace("d", "\u062f").replace("*", "\u0630").replace("r", "\u0631").replace("z", "\u0632");
//        input = input.replace("s", "\u0633").replace("$", "\u0634").replace("S", "\u0635").replace("D", "\u0636").replace("T", "\u0637");
//        input = input.replace("Z", "\u0638").replace("E", "\u0639").replace("g", "\u063a").replace("f", "\u0641").replace("q", "\u0642");
//        input = input.replace("k", "\u0643").replace("l", "\u0644").replace("m", "\u0645").replace("n", "\u0646").replace("h", "\u0647");
//        input = input.replace("w", "\u0648").replace("y", "\u064a").replace("Y", "\u0649").replace("p", "\u0629").replace("&", "\u0624");
//        input = input.replace("}", "\u0626").replace("{", "??");
//        input = input.replace("a", "\u064e").replace("F", "\u064b").replace("u", "\u064f").replace("N", "\u064c");
//        input = input.replace("i", "\u0650").replace("K", "\u064d").replace("o", "\u0652").replace("~", "\u0651");
        return input;
    }
    
    public static String buck2utf8WithoutDiacritics(String input) {
        input = input.replace("A", "\u0627").replace("<", "\u0625").replace("|", "\u0622").replace(">", "\u0623").replace("'", "\u0621");
        input = input.replace("b", "\u0628").replace("t", "\u062a").replace("v", "\u062b").replace("j", "\u062c").replace("H", "\u062d");
        input = input.replace("x", "\u062e").replace("d", "\u062f").replace("*", "\u0630").replace("r", "\u0631").replace("z", "\u0632");
        input = input.replace("s", "\u0633").replace("$", "\u0634").replace("S", "\u0635").replace("D", "\u0636").replace("T", "\u0637");
        input = input.replace("Z", "\u0638").replace("E", "\u0639").replace("g", "\u063a").replace("f", "\u0641").replace("q", "\u0642");
        input = input.replace("k", "\u0643").replace("l", "\u0644").replace("m", "\u0645").replace("n", "\u0646").replace("h", "\u0647");
        input = input.replace("w", "\u0648").replace("y", "\u064a").replace("Y", "\u0649").replace("p", "\u0629").replace("&", "\u0624");
        input = input.replace("}", "\u0626");
        return input;
    }
    
    public static ArrayList<String> tokenizeText(String input) {

        char[] charInput = input.toCharArray();
        input = "";
        for (int i = 0; i < charInput.length; i++) {
            int c = charInput[i];
            if (c <= 32 || c == 127 || (c >= 194128 && c <= 194160)) {
                input += " ";
            } else {
                input += charInput[i];
            }
        }

        input = input.replaceAll("[\u200B\ufeff]+", " ").replace("    ", "");
        ArrayList<String> output = new ArrayList<String>();
        String[] words = input.split("[\\\u061f \t\n\r,\\-<>\"\\?\\:;\\&]+");
        for (int i = 0; i < words.length; i++) {
            if (words[i].startsWith("#")
                    || words[i].startsWith("@")
                    // || words[i].startsWith(":")
                    || words[i].startsWith(";")
                    || words[i].startsWith("http://")
                    || words[i].matches("[a-zA-Z0-9\\-\\._]+@[a-zA-Z0-9\\-\\._]+")
                    ) {
                if (words[i].endsWith(":") || words[i].endsWith("\'")) {
                    words[i] = words[i].substring(0, words[i].length() - 1);
                }
                output.add(normalize(words[i].trim()));
            } else {
                // String[] tmp = words[i].split("[~<>_\"\\-,\\.??\\!\\#\\$\\%\\?\\^\\&\\*\\(\\)\\[\\]\\{\\}\\/\\|\\\\]+");
                String[] tmp = words[i].split("[" + ArabicUtils.ALLDelimiters + "]+");
                for (int j = 0; j < tmp.length; j++) {
                    while (tmp[j].startsWith("\'")) {
                        tmp[j] = tmp[j].substring(1);
                    }
                    while (tmp[j].endsWith("\'") || tmp[j].endsWith("\"") || tmp[j].endsWith(":")) {
                        tmp[j] = tmp[j].substring(0, tmp[j].length() - 1);
                    }
                    if (!tmp[j].isEmpty() && tmp[j].length() > 0) //
                    {
                        output.add(normalize(tmp[j].trim()));
                    }
                }
            }
        }
        return output;
    }
    
    public static ArrayList<String> tokenize(String s) {
         s = removeNonCharacters(s);
        s = removeDiacritics(s);
        s = s.replaceAll("[\t\n\r]", " ");

        ArrayList<String> output = new ArrayList<String>();

        String[] words = s.split(" ");
        for (int i = 0; i < words.length; i++) {
            if ( words[i].startsWith("#")
                    || words[i].startsWith("@")
                    || words[i].startsWith(":")
                    || words[i].startsWith(";")
                    || words[i].startsWith("http://")
                    // || words[i].matches("[a-zA-Z0-9\\-\\._]+@[a-zA-Z0-9\\-\\._]+")) {
                    || emailRegex.matcher(words[i]).matches()
                    ) {
                // if (words[i].endsWith(":") || words[i].endsWith("\'")) {
                //    words[i] = words[i].substring(0, words[i].length() - 1);
                // }
                output.add(words[i]);
            } else {
                for (String ss : charBasedTonkenizer(words[i]).split(" ")) {
                    if (ss.trim().length() > 0) {
                        if (ss.startsWith("????"))
                            output.add("??????" + ss.substring(2));
                        else 
                        if (ss.trim().length() > 0)
                            output.add(ss);
                    }
                }
            }
        }
        return output;
    }

        public static ArrayList<String> tokenizeWithoutProcessing(String s) {
         s = removeNonCharacters(s);
        // s = removeDiacritics(s);
        s = s.replaceAll("[\t\n\r]", " ");

        ArrayList<String> output = new ArrayList<String>();

        String[] words = s.split(" ");
        for (int i = 0; i < words.length; i++) {
            if ( words[i].startsWith("#")
                    || words[i].startsWith("@")
                    || words[i].startsWith(":")
                    || words[i].startsWith(";")
                    || words[i].startsWith("http://")
                    || emailRegex.matcher(words[i]).matches()) {
                    // words[i].matches("[a-zA-Z0-9\\-\\._]+@[a-zA-Z0-9\\-\\._]+")) {
                // if (words[i].endsWith(":") || words[i].endsWith("\'")) {
                //    words[i] = words[i].substring(0, words[i].length() - 1);
                // }
                output.add(words[i]);
            } else {
                for (String ss : charBasedTonkenizer(words[i]).split(" ")) {
                    if (ss.trim().length() > 0) {
                        if (ss.trim().length() > 0)
                            output.add(ss);
                    }
                }
            }
        }
        return output;
    }
    
    private static String charBasedTonkenizer(String s) {
//        String[] seperator = {"~", "??", "??", "??", "<", ">", "_", "\"", "-", "??", "!", "#", "?", "^", "&", "*", "(", ")", "[", "]", "{", "}", "|", "\\", "-", "<", ">", "\"", "?", "??", "??", ";", ":"};
//        ArrayList<String> seperatorList = new ArrayList<String>();
//        for (String ss : seperator) {
//            seperatorList.add(ss);
//        }

        String sFinal = "";
       
        for (int i = 0; i < s.length(); i++) {
            // if (seperatorList.contains(s.substring(i, i + 1))) {
            //if (s.substring(i, i + 1).matches("[" + ArabicUtils.ALLDelimiters + "]")) {
            // if (ArabicUtils.ALLDelimiters.contains(s.substring(i, i + 1))) {    
            if (pAllDelimiters.matcher(s.substring(i, i + 1)).matches())
            {
                sFinal += " " + s.substring(i, i + 1) + " ";
            } else if (s.substring(i, i + 1) == "." || s.substring(i, i + 1) == "," || s.substring(i, i + 1) == ".") {
                if (i == 0) {
                    sFinal += s.substring(i, i + 1) + " ";
                } else if (i == s.length() - 1) {
                    sFinal += " " + s.substring(i, i + 1);
                // } else if (s.substring(i - 1, i).matches("[0-9]") && s.substring(i + 1, i + 2).matches("[0-9]")) {
                } else if (AllDigits.contains(s.substring(i - 1, i)) && AllDigits.contains(s.substring(i + 1, i + 2))) {
                    sFinal += s.substring(i, i + 1);
                } else {
                    sFinal += " " + s.substring(i, i + 1) + " ";
                }
            // } else if (!s.substring(i, i + 1).matches("[" + ArabicUtils.AllArabicLettersAndHindiDigits + "\u0640\u064b\u064c\u064d\u064e\u064f\u0650\u0651\u0652\u0670" + "a-zA-Z0-9]")) {
            } else if (!(ArabicUtils.AllArabicLettersAndHindiDigits + "\u0640\u064b\u064c\u064d\u064e\u064f\u0650\u0651\u0652\u0670" 
                    + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????").contains(s.substring(i, i + 1))) {
                sFinal += " " + s.substring(i, i + 1) + " ";
                // sFinal += s.substring(i, i + 1);
            } else {
                if (i == 0) {
                    sFinal += s.substring(i, i + 1);
                } else {
                    // if ((s.substring(i, i + 1).matches("[0-9]") && s.substring(i - 1, i).matches("[" + ArabicUtils.AllArabicLetters + "]"))
                    //        || (s.substring(i - 1, i).matches("[0-9]") && s.substring(i, i + 1).matches("[" + ArabicUtils.AllArabicLetters + "]"))) {
                    if (
                            (
                            AllDigits.contains(s.substring(i, i + 1)) 
                            && AllArabicLetters.contains(s.substring(i - 1, i))
                            )
                            || 
                            (
                            AllDigits.contains(s.substring(i - 1, i)) 
                            && 
                            AllArabicLetters.contains(s.substring(i, i + 1)))
                            ) {
                        sFinal += " " + s.substring(i, i + 1);
                    } else {
                        sFinal += s.substring(i, i + 1);
                    }

                }
            }
        }
        return sFinal;
    }
    
    public static String normalize(String s) {
        // IF Starts with lam-lam
        if (s.startsWith("\u0644\u0644")) //
        {
            // need to insert an ALEF into the word
            s = "\u0644\u0627\u0644" + s.substring(2);
        }
        // If starts with waw-lam-lam
        if (s.startsWith("\u0648\u0644\u0644")) //
        {
            // need to insert an ALEF into the word
            s = "\u0648\u0644\u0627\u0644" + s.substring(3);
        }
		// If starts with fa-lam-lam
		/* // Until fix the CRFPP training model
         if (s.startsWith("\u0641\u0644\u0644")) //
         {
         // need to insert an ALEF into the word
         s = "\u0641\u0644\u0627\u0644" + s.substring(3);
         }
         */
        /* skip normalization of hamza, ta marbouta and alef maqsoura
         s = s.replace(ALEF_MADDA, ALEF).replace(ALEF_HAMZA_ABOVE, ALEF).replace(ALEF_HAMZA_BELOW, ALEF);
         s = s.replace(DOTLESS_YEH, YEH);
         s = s.replace(HAMZA_ON_NABRA, HAMZA).replace(HAMZA_ON_WAW, HAMZA);
         s = s.replace(TEH_MARBUTA, HEH);
         */
        s = pAllDiacritics.matcher(s).replaceAll("");
        // s = s.replaceAll("[\u0640\u064b\u064c\u064d\u064e\u064f\u0650~\u0651\u0652\u0670]+", ""); // .replace(KASRATAN, EMPTY).replace(DAMMATAN, EMPTY).replace(FATHATAN, EMPTY).replace(FATHA, EMPTY).replace(DAMMA, EMPTY).replace(KASRA, EMPTY).replace(SHADDA, EMPTY).replace(SUKUN, EMPTY);
        return s;
    }
    
    public static String normalizeFull(String s) {
        // IF Starts with lam-lam
        if (s.startsWith("\u0644\u0644")) //
        {
            // need to insert an ALEF into the word
            s = "\u0644\u0627\u0644" + s.substring(2);
        }
        // If starts with waw-lam-lam
        if (s.startsWith("\u0648\u0644\u0644")) //
        {
            // need to insert an ALEF into the word
            s = "\u0648\u0644\u0627\u0644" + s.substring(3);
        }
		// If starts with fa-lam-lam
		/* // Until fix the CRFPP training model
         if (s.startsWith("\u0641\u0644\u0644")) //
         {
         // need to insert an ALEF into the word
         s = "\u0641\u0644\u0627\u0644" + s.substring(3);
         }
         */
        
         s = s.replace(ALEF_MADDA, ALEF).replace(ALEF_HAMZA_ABOVE, ALEF).replace(ALEF_HAMZA_BELOW, ALEF);
         s = s.replace(DOTLESS_YEH, YEH);
         s = s.replace(HAMZA_ON_NABRA, HAMZA).replace(HAMZA_ON_WAW, HAMZA);
         s = s.replace(TEH_MARBUTA, HEH);
         
         s = pAllDiacritics.matcher(s).replaceAll("");
        // s = s.replaceAll("[\u0640\u064b\u064c\u064d\u064e\u064f\u0650~\u0651\u0652\u0670]+", ""); // .replace(KASRATAN, EMPTY).replace(DAMMATAN, EMPTY).replace(FATHATAN, EMPTY).replace(FATHA, EMPTY).replace(DAMMA, EMPTY).replace(KASRA, EMPTY).replace(SHADDA, EMPTY).replace(SUKUN, EMPTY);
        return s;
    }

    public static String removeDiacritics(String s) {
        s = pAllDiacritics.matcher(s).replaceAll("");
        // s = s.replaceAll("[\u0640\u064b\u064c\u064d\u064e\u064f\u0650\u0651\u0652\u0670]", "");
        return s;
    }

    private static String removeNonCharacters(String s) {
        s = pAllNonCharacters.matcher(s).replaceAll(" ");
        // s = s.replaceAll("[\u0020\u2000-\u200F\u2028-\u202F\u205F-\u206F\uFEFF]+", " ");
        return s;
    }
    
    public static BufferedReader openFileForReading(String filename) throws FileNotFoundException {
        BufferedReader sr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
        return sr;
    }

    public static BufferedWriter openFileForWriting(String filename) throws FileNotFoundException {
        BufferedWriter sw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filename))));
        return sw;
    }   
    
    public static String standardizeDiacritics(String word) {
        String diacrtics = "[\u064e\u064b\u064f\u064c\u0650\u064d\u0652\u0651]";
        String sokun = "\u0652";
        String fatha = "\u064e";
        word = word.replaceFirst("^" + diacrtics + "+", "");
        word = word.replace("????", "??").replace("????", "??").replace("????", "??").replace(".", "").replace("????", "??");
        int pos = word.indexOf("??");
        while (pos > 0 && pos < word.length() - 1) {
            if (!word.substring(pos - 1, pos).matches(diacrtics) && word.substring(pos + 1, pos + 2).equals(sokun)) {
                word = word.substring(0, pos + 1) + word.substring(pos + 2);
            }
            pos = word.indexOf("??", pos + 1);
        }
        pos = word.indexOf("??");
        while (pos > 0 && pos < word.length() - 1) {
            if (!word.substring(pos - 1, pos).matches(diacrtics) && word.substring(pos + 1, pos + 2).equals(sokun)) {
                word = word.substring(0, pos + 1) + word.substring(pos + 2);
            }
            pos = word.indexOf("??", pos + 1);
        }
        pos = word.indexOf("??");
        while (pos > 0 && pos < word.length() - 1) {
            if (!word.substring(pos - 1, pos).matches(diacrtics)
                    && (word.substring(pos + 1, pos + 2).equals(sokun) || word.substring(pos + 1, pos + 2).equals(fatha))) {
                word = word.substring(0, pos + 1) + word.substring(pos + 2);
            }
            pos = word.indexOf("??", pos + 1);
        }
        if (word.startsWith("??????")) {
            word = word.replaceFirst("??????", "????");
        }
        // word = word.replaceFirst(diacrtics + "+$", "");
        return word;
    }
    
    public static String transferDiacriticsFromWordToSegmentedVersion(String diacritizedWord, String stemmedWord)
    {
        boolean startsWithLamLam = false;
        boolean startsWithWaLamLam = false;
        boolean startsWithFaLamLam = false;
        if (
                (stemmedWord.startsWith("??+????") && // !ArabicUtils.removeDiacritics(diacritizedWord).equals(stemmedWord.replace("+", "").replace(";", "")))
                ArabicUtils.removeDiacritics(diacritizedWord).startsWith("????"))
                ||
                (stemmedWord.startsWith("??+??+????") && // !ArabicUtils.removeDiacritics(diacritizedWord).equals(stemmedWord.replace("+", "").replace(";", "")))
                ArabicUtils.removeDiacritics(diacritizedWord).startsWith("??????"))
                ||
                (stemmedWord.startsWith("??+??+????") && // !ArabicUtils.removeDiacritics(diacritizedWord).equals(stemmedWord.replace("+", "").replace(";", "")))
                ArabicUtils.removeDiacritics(diacritizedWord).startsWith("??????"))
                )
        {
            int posFirstLam = diacritizedWord.indexOf("??", 0);
            int posSecondLam = diacritizedWord.indexOf("??", posFirstLam + 1);
            diacritizedWord = diacritizedWord.substring(0, posSecondLam) + "??" + diacritizedWord.substring(posSecondLam);
        }
        
        String output = "";
        stemmedWord = stemmedWord.replace(" ", "");
        stemmedWord = stemmedWord.replaceFirst("\\+$", "");
        if (diacritizedWord.equals(stemmedWord) || !stemmedWord.contains("+"))
            return diacritizedWord;
        
        int pos = 0;
        for (int i = 0; i < stemmedWord.length(); i++)
        {
            if (stemmedWord.substring(i, i+1).equals("+") || stemmedWord.substring(i, i+1).equals(";"))
            {
                {
                    output += stemmedWord.substring(i, i+1);
                }
            }
            else
            {
                int loc = diacritizedWord.indexOf(stemmedWord.substring(i, i+1), pos);
                if (loc >= 0)
                {
                    String diacritics = diacritizedWord.substring(pos, loc);
                    output += diacritics + stemmedWord.substring(i, i+1);
                    // add trailing diacritics
                    loc++;
                    while (loc < diacritizedWord.length() && diacritizedWord.substring(loc, loc + 1).matches("[" + 
                           ArabicUtils.buck2utf8("aiouNKF~") + "]"))
                    {
                        output += diacritizedWord.substring(loc, loc + 1);
                        loc++;
                    }
                    pos = loc;
                }
                else
                {
                    // System.err.println(diacritizedWord + "\t" + stemmedWord);
                }
            }
        }
        return output;
    }
    
    public static String getDiacritizedStem(String stemmed, String diacritized) 
    {
        String output = "";
        String[] parts = (" " + stemmed + " ").split(";");
        if (parts.length != 3)
            return diacritized;
        String suffixes = parts[2].trim();
        String stem = parts[1].trim();
        
        int i = diacritized.length();
        while (i > 0 && !ArabicUtils.removeDiacritics(diacritized.substring(i, diacritized.length())).equals(suffixes))
        {
            i--;
        }
        // head would be the word without the suffix
        String head = diacritized.substring(0, i);
        String tail = diacritized.substring(i);
        // next get the stem
        i = head.length();
        while (i > 0 && !ArabicUtils.removeDiacritics(head.substring(i, head.length())).equals(stem))
        {
            i--;
        }
        String diacritizedStem = head.substring(i);
        
        return diacritizedStem;
    }
    
}
