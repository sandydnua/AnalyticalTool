package qualityunit.Services;

import qualityunit.Model.LineC;
import qualityunit.Model.LineD;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {

    private final static String tmplDate;
    private final static String tmplLineC;
    private final static String tmplLineD;
    private final static String templateOfValidSting;
    private final static String tmplServiceId;
    private final static String tmplQuestion;


    private final String sourceString;
    private List<LineD> linesD;


    static {
        tmplServiceId = "\\d*(\\.\\d*)?";
        tmplQuestion = "\\d*(\\.\\d*(\\.\\d*)?)?";
        tmplDate = "\\d{1,2}\\.\\d{1,2}\\.\\d{4}";
        tmplLineC = "C " + tmplServiceId + " " + tmplQuestion + " (P|N) " + tmplDate+ " \\d*";
        tmplLineD = "D (" + tmplServiceId + "|\\*) (" + tmplQuestion + "|\\*) (P|N) " + tmplDate+ "(-" + tmplDate+ ")?";
        templateOfValidSting = "^\\d*(( " + tmplLineC +")|( " + tmplLineD + "))*";
    }

    public LineParser(String inputString) {
        sourceString = inputString.replaceAll("(\t|\n)", "")
                .replaceAll(" {2,}+"," ")
                .trim();
        if (!validInputString(sourceString)) {
            throw new IllegalArgumentException("Broken line structure");
        }

        linesD = parseToListD();
        int numberOfLines = getExpectedNumberOfLines(sourceString);
        int actualySize = parseToListC().size() + parseToListD().size();

        if ( numberOfLines != actualySize) {
            throw new IllegalArgumentException("Incorrect number of lines in input string! Expected: "
                                                + numberOfLines + ", actualy: " + actualySize);
        }
    }

    private boolean validInputString(String inputString) {
        return inputString.matches(templateOfValidSting);
    }

    private List<LineC> parseToListC() {
        return parseToListC(tmplLineC);
    }
    public List<LineC> parseToListC(String tmpl) {
        List<LineC> resultList = new ArrayList<>();

        Matcher matcher = Pattern.compile(tmpl)
                                 .matcher(sourceString);
        while (matcher.find()) {
            String itemLine = sourceString.substring(matcher.start(), matcher.end());
            resultList.add(new LineC(itemLine));
        }
        return resultList;
    }
    private List<LineD> parseToListD() {
        List<LineD> resultList = new ArrayList<>();

        Matcher matcher = Pattern.compile(tmplLineD)
                                 .matcher(sourceString);
        while (matcher.find()) {
            String itemLine = sourceString.substring(matcher.start(), matcher.end());
            resultList.add(new LineD(itemLine));
        }
        return resultList;
    }

    private int getExpectedNumberOfLines(String inputString) {
        int indexOfFirstSpace = inputString.indexOf(" ");
        return Integer.parseInt(inputString.substring(0,indexOfFirstSpace));
    }

    public static boolean isLineD(String line) {
        return line.matches(tmplLineD);
    }

    public static boolean isLineC(String line) {
        return line.matches(tmplLineC);
    }

    public static String createTmplForSearchC(LineD lineD) {
        StringBuilder tmpl = new StringBuilder("C ");
        if (lineD.getServiceId().equals("*")) {
            tmpl.append(tmplServiceId).append(" ");
        } else {
            if (lineD.getVariationId().equals("")) {
                tmpl.append(String.format("((%s)|(%s.\\d*)?) ", lineD.getServiceId(), lineD.getServiceId()));
            } else {
                tmpl.append(String.format("%s.%s ",lineD.getServiceId(), lineD.getVariationId()));
            }
        }

        if (lineD.getQuestionTypeId().equals("*")) {
            tmpl.append(tmplQuestion).append(" ");
        } else {
            if (lineD.getCategoryId().equals("") && lineD.getSubCategoryId().equals("")) {
                tmpl.append(String.format("(%s(\\.\\d*(\\.\\d*)?)?) ", lineD.getQuestionTypeId()));
            } else if (lineD.getSubCategoryId().equals("")){
                tmpl.append(String.format("(%s(\\.%s(\\.\\d*)?)?) ", lineD.getQuestionTypeId(), lineD.getCategoryId()));
            } else {
                tmpl.append(String.format("(%s(\\.%s(\\.%s)?)?) ", lineD.getQuestionTypeId(),
                                                                     lineD.getCategoryId(),
                                                                     lineD.getSubCategoryId()));
            }
        }
        return tmpl.append("(P|N) ").append(tmplDate).append(" \\d*").toString();
    }

    public List<LineD> getListLineD() {
        return this.linesD;
    }
}
