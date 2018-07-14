package qualityunit.controller;

import qualityunit.Model.LineC;
import qualityunit.Model.LineD;
import qualityunit.Services.LineParser;

import java.util.List;

class AnalyticalTool {

    public static String toEvaluate(String inputString) {

        LineParser lineParser = new LineParser(inputString);
        StringBuilder result = new StringBuilder("");

        for(LineD lineD : lineParser.getListLineD()){
            List<LineC> listC = lineParser.parseToListC(LineParser.createTmplForSearchC(lineD));
            Integer sum = 0;
            Integer counter = 0;
            for(LineC lineC : listC) {
                if (lineD.isDateIn(lineC)) {
                    sum +=lineC.getTime();
                    counter++;
                }
            }
            result.append( (0 == counter ) ?  "-" : String.valueOf(sum/counter));
            result.append(" ");
        }
        return result.toString();
    }
}
