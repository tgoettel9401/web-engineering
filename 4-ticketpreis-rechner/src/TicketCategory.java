public enum TicketCategory {
    INFANT(0, 5, 0.0), CHILD(6, 17, 10.0), ADULT(18, 64, 20.0), SENIOR(65, Integer.MAX_VALUE, 15.0);

    private Integer minAge;
    private Integer maxAge;
    private Double basePrice;

    TicketCategory(Integer minAge, Integer maxAge, Double basePrice) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.basePrice = basePrice;
    }

    public Double getBasePrice() {
        return this.basePrice;
    }

    public static TicketCategory fromAge(Integer age) {
        for (TicketCategory category : values()) {
            if (age >= category.minAge && age <= category.maxAge) {
                return category;
            }
        }
        throw new RuntimeException("Invalid age supplied");
    }

}