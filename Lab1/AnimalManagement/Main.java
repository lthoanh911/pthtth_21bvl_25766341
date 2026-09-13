public class Main {
    public static void main(String[] args) {

        Lion lion = new Lion("Leo", 300, 5);
        Snake snake = new Snake("Boa", 50, 5);
        Monkey monkey = new Monkey("George", 150, "chuối");

        lion.displayInfo();
        snake.displayInfo();
        monkey.displayInfo();
    }
}
