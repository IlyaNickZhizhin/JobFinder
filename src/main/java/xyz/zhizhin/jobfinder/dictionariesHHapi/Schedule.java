package xyz.zhizhin.jobfinder.dictionariesHHapi;

public enum Schedule {
    FULL_DAY("fullDay", "Полный день", "full_day"),
    SHIFT("shift", "Сменный график", "shift"),
    FLEXIBLE("flexible", "Гибкий график", "flexible"),
    REMOTE("remote", "Удаленная работа", "remote"),
    FLY_IN_FLY_OUT("flyInFlyOut", "Вахтовый метод", "fly_in_fly_out");

    private final String id;
    private final String description;
    private final String uid;

    Schedule(String id, String description, String uid) {
        this.id = id;
        this.description = description;
        this.uid = uid;
    }

    public static Schedule getById(String id) {
        for (Schedule schedule : values()) {
            if ((schedule.id.toUpperCase()).equals(id.toUpperCase())) {
                return schedule;
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

    public String getUid() {
        return uid;
    }
}
