class Snake extends Animal {
    private double length;

    public Snake(String name, double weight, double length) {
        super(name, weight);
        this.length = length;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Chiều dài: " + length + " mét");
    }
}