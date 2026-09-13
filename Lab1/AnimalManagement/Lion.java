class Lion extends Animal {
    private double eat;

    public Lion(String name, double weight, double eat) {
        super(name, weight);
        this.eat = eat;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Thức ăn mỗi ngày: " + eat + " kg");
    }
}