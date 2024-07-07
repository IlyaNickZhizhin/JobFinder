package xyz.zhizhin.jobfinder.dictionariesHHapi;

public enum Experience {
    NO("noExperience", "Нет опыта"),
    JUNIOR("between1And3", "От 1 года до 3 лет"),
    MIDDLE("between3And6", "От 3 до 6 лет"),
    SENIOR("moreThan6", "Более 6 лет");

    private final String id;
    private final String description;

    Experience(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Experience getById(String id) {
        for (Experience experience : values()) {
            if ((experience.id.toLowerCase()).equals(id.toLowerCase())) {
                return experience;
            }
        }
        throw new IllegalArgumentException("No enum constant" + Experience.class.getCanonicalName() + "." + id);
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}
