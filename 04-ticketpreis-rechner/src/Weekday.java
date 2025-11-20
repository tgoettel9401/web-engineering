public enum Weekday {
    MONDAY("Montag", 0.0), TUESDAY("Dienstag", 2.0), WEDNESDAY("Mittwoch", 0.0), THURSDAY("Donnerstag", 0.0),
    FRIDAY("Freitag", 0.0), SATURDAY("Samstag", 0.0), SUNDAY("Sonntag", 0.0);

    Weekday(String inputString, Double discount) {
        this.inputString = inputString;
        this.discount = discount;
    }

    private String inputString;
    private Double discount;

    public String getInputString() {
        return this.inputString;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public static Weekday fromString(String inputString) {
        for (Weekday weekday : values()) {
            if (weekday.inputString.equals(inputString)) {
                return weekday;
            }
        }
        throw new RuntimeException("Ungültiger Wochentag " + inputString + " übergeben!");
    }
}
