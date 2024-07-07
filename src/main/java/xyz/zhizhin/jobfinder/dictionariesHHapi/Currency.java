package xyz.zhizhin.jobfinder.dictionariesHHapi;

public enum Currency {
    AZN("AZN", "Манаты"),
    BYR("BYR", "Белорусские рубли"),
    EUR("EUR", "Евро"),
    GEL("GEL", "Грузинский лари"),
    KGS("KGS", "Киргизский сом"),
    KZT("KZT", "Тенге"),
    RUR("RUR", "Рубли"),
    UAH("UAH", "Гривны"),
    USD("USD", "Доллары"),
    UZS("UZS", "Узбекский сум");

    private final String code;
    private final String name;

    Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

