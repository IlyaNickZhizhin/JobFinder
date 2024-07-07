package xyz.zhizhin.jobfinder.dictionariesHHapi;

public enum SearchField {
    NAME("name", "в названии вакансии"),
    COMPANY_NAME("company_name", "в названии компании"),
    DESCRIPTION("description", "в описании вакансии");

    private final String id;
    private final String description;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    SearchField(String id, String description) {
        this.id = id;
        this.description = description;
    }
}
