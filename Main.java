public class Main {
    public static void main(String[] args) {
        
        final int SHIP = 3;
        final int SQUARE = 5;
        final int FRAME = 2;    
        int[][] sea = new int[SQUARE+FRAME][SQUARE+FRAME];
        Battleship[] Ship = new Battleship[SHIP];

        Game game = new Game();
        game.title();
        game.set_Ship(Ship,sea);
        game.start(Ship, sea);
    }
}