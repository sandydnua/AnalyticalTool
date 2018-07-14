package qualityunit.Model;

import qualityunit.Services.LineParser;

public class LineC extends Line {

    private int time;

    public LineC(String line) {
        super();
        line = line.trim();
        if (!LineParser.isLineC(line)) {
            throw new IllegalArgumentException("Incorrect line format!");
        }
        String[] parameters = line.split(" ");

        super.setService(parameters[1]);
        super.setQuestion(parameters[2]);
        super.setDataFrom(parameters[4]);

        typeOfAnswer = parameters[3];
        time = Integer.parseInt(parameters[5]);
    }

    public int getTime() {
        return time;
    }
}
