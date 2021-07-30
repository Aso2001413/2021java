public class Battleship {
    
    private final int SQUARE = 5;
    private final int FRAME = 2;

    public int[] ship_hp = {3,3,4};
    private String[] ship_name = {"Queen Erizabeth","Iowa","Yamato"};
    public int pos_row,pos_column;
    public int[][] position = new int[SQUARE+FRAME][SQUARE+FRAME];

    public String name(int i){
        return this.ship_name[i];
    }
    public int current_HP(int i){
        return this.ship_hp[i];
    }
}
