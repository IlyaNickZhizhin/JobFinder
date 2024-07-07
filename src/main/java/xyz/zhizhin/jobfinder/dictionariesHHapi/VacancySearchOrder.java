package xyz.zhizhin.jobfinder.dictionariesHHapi;

public enum VacancySearchOrder {
    PUBLICATION_TIME("publication_time", "по дате"),
    SALARY_DESC("salary_desc", "по убыванию дохода"),
    SALARY_ASC("salary_asc", "по возрастанию дохода"),
    RELEVANCE("relevance", "по соответствию"),
    DISTANCE("distance", "по удалённости");

    private final String id;
    private final String description;

    VacancySearchOrder(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}

