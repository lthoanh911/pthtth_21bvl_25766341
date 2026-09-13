class Monkey extends Animal {
    private String favoriteFood;

    public Monkey(String name, double weight, String favoriteFood) {
        super(name, weight);
        this.favoriteFood = favoriteFood;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Thức ăn yêu thích: " + favoriteFood);
    }
}