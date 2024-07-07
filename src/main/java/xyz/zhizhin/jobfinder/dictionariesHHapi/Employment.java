package xyz.zhizhin.jobfinder.dictionariesHHapi;

public enum Employment {
    FULL("full", "Полная занятость"),
    PART("part", "Частичная занятость"),
    PROJECT("project", "Проектная работа"),
    VOLUNTEER("volunteer", "Волонтерство"),
    PROBATION("probation", "Стажировка");

    private final String id;
    private final String description;

    Employment(String id, String name) {
        this.id = id;
        this.description = name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
