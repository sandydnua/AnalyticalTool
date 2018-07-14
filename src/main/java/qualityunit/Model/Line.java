package qualityunit.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract public class Line{
    private String serviceId;
    private String variationId = "";
    private String questionTypeId;
    private String categoryId = "";
    String typeOfAnswer;
    private String subCategoryId = "";
    Date dateFrom;
    private final String patternDate = "dd.MM.yyyy";

    void setService(String service) {
        String[] itemsId = service.split("\\.");
        if (itemsId.length == 2) {
            variationId = itemsId[1];
        }
        serviceId = itemsId[0];
    }

    void setQuestion(String question) {

        String[] itemsId = question.split("\\.");
        if (itemsId.length == 3) {
            subCategoryId = itemsId[2];
        }
        if (itemsId.length > 1 ) {
            categoryId = itemsId[1];
        }
        questionTypeId = itemsId[0];

    }

    Date parseDate(String date) {
        DateFormat format = new SimpleDateFormat(patternDate);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    void setDataFrom(String dataFrom) {
        this.dateFrom = parseDate(dataFrom);
    }
    public String getServiceId() {
        return serviceId;
    }

    public String getVariationId() {
        return variationId;
    }

    public String getQuestionTypeId() {
        return questionTypeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    Date getDateFrom() {
        return dateFrom;
    }
}
