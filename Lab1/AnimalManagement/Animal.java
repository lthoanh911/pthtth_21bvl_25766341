class Animal {
    protected String name;
    protected double weight;

    public Animal(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public void displayInfo() {
        System.out.println("Tên: " + name);
        System.out.println("Cân nặng: " + weight + " kg");
    }
}