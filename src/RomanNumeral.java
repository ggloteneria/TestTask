enum RomanNumeral {
    I(1),
    V(5),
    X(10);

    private final int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isNextValueGreater(RomanNumeral next) {
        return this.value < next.value;
    }

    public int doProcessRomanNumber(RomanNumeral next) {
        return next.value - this.value;
    }
}
