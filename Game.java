import java.util.Scanner;
import java.util.Random;

public class Game {
    Scanner sc = new Scanner(System.in);
    Random random = new Random();
    private final int SHIP = 3;
    private final int SQUARE = 5;
    public Battleship[] Ship = new Battleship[SHIP];
    public int[][] sea = new int[SQUARE][SQUARE];
    int row,column,turn = 1;
    boolean coord_flag,end_flag;
    //タイトル
    public void title(){
        System.out.printf("******************************\n");
        System.out.printf("           戦艦ゲーム\n");
        System.out.printf("******************************\n");
    }
    //船の設置
    public void set_Ship(Battleship Ship[],int sea[][]){
        int count = 0;
        while(count < SHIP){
            Ship[count] = new Battleship();
            row = random.nextInt(SQUARE);
            column = random.nextInt(SQUARE);
            //船の重複判定
            if(sea[column+1][row+1] == 0){
                Ship[count].pos_row = row;
                Ship[count].pos_column = column;
                Ship[count].position[column][row] = 1;
                sea[column+1][row+1] = 1;
                count++;            
            }else{
                row = random.nextInt(SQUARE);
                column = random.nextInt(SQUARE);    
            }           
        }
    }
    public void start(Battleship Ship[],int sea[][]){
        while(true){
            turn(turn);
            hp(Ship);
            //debug(Ship,sea);
            row = coord_row();
            column = coord_column();
            coord_flag = check_range(row,column);

            if(coord_flag){
                check_attack(Ship, sea, row, column);
            }else{
                System.out.printf("いずれかの座標が範囲外でした。\n");
                System.out.printf("処理をスキップします。\n");
            }

            end_flag = check_hp(Ship);
            if(end_flag){
                finish();
                break;
            }
            turn++;
        }
    }
    //ターン経過の表示
    public void turn(int turn){
        System.out.printf("---------[ターン%d]---------\n",turn);
    }
    //船の現在の耐久値表示
    public void hp(Battleship Ship[]){
        for(int i = 0; i < SHIP; i++){
            if(Ship[i].current_HP(i) == 0){
                System.out.printf("%s:撃沈\n",Ship[i].name(i));
            }else{
                System.out.printf("%s 耐久値:%d\n",Ship[i].name(i),
                Ship[i].current_HP(i));
            }
        }
    }
    //x座標(row)の取得
    public int coord_row(){
        System.out.printf("爆弾のX座標を入力してください(1-5)\n");
        int row = Integer.parseInt(sc.next());
        return row;
    }
    //y座標(column)の取得
    public int coord_column(){
        System.out.printf("爆弾のY座標を入力してください(1-5)\n");
        int column = Integer.parseInt(sc.next());
        return column;
    }
    //x、y座標が範囲内に指定されているかの確認
    public boolean check_range(int row, int column){
        if(row > SQUARE-SQUARE && row <= SQUARE && column > SQUARE-SQUARE && column <= SQUARE){
            return true;
        }
        return false;
    }
    //攻撃するとき船が命中、または十字の中に存在するかの判定
    public void check_attack(Battleship Ship[], int sea[][], int row, int column){
        for(int i = 0; i < SHIP; i++){
            if(Ship[i].ship_hp[i] != 0){
                if(Ship[i].position[column-1][row-1] != 0){
                    Ship[i].ship_hp[i]--;
                    if(Ship[i].ship_hp[i] == 0){
                        System.out.printf("%s撃沈!!\n", Ship[i].name(i));
                        reset(Ship,sea,i,row,column);
                    }else{
                        System.out.printf("%sニ命中確認!! シカシ、煙幕デ姿ヲ見失ウ\n"
                        ,Ship[i].name(i));
                        respawn(Ship,sea,i,row,column);
                    }
                }else{
                    greenie(Ship,i,row,column);
                }
            }else{
                continue;
            }
        }
    }
    //船が全滅した時、ゲームを終了する判定
    public boolean check_hp(Battleship Ship[]){
        boolean flag = false;
        for(int i = 0; i < SHIP; i++){
            if(Ship[i].current_HP(i)== 0){
                flag = true;
            }else{
                flag = false;
                break;
            }
        }
        return flag;
    }
    //船のリセット
    public void reset(Battleship Ship[], int sea[][], int i, int row, int column){
        Ship[i].position[column-1][row-1] = 0;
        sea[column-1][row-1] = 0;
    }
    //船のリスポーン
    public void respawn(Battleship Ship[], int sea[][], int i, int row, int column){
        int respawn_row,respawn_column,count = 0;
        respawn_row = random.nextInt(SQUARE);
        respawn_column = random.nextInt(SQUARE);
        while(count < 1){
            if(sea[respawn_column][respawn_row] == 0){
                sea[column-1][row-1] = 0;
                Ship[i].position[column-1][row-1] = 0;
                Ship[i].pos_row = respawn_row;
                Ship[i].pos_column = respawn_column;
                Ship[i].position[respawn_column][respawn_row] = 1;
                sea[respawn_column+1][respawn_row+1] = 1;
                count++;
            }else{
                respawn_row = random.nextInt(SQUARE);
                respawn_column = random.nextInt(SQUARE);
            }
        }
    }
    //船が十字にいるのかの判定
    public void greenie(Battleship Ship[], int i, int row, int column){
        if(Ship[i].position[column][row-1] != 0 ||
        Ship[i].position[column][row+1] != 0 ||
        Ship[i].position[column-1][row] != 0 ||
        Ship[i].position[column+1][row] != 0){
            System.out.printf("%sアリ、波高シ!\n",Ship[i].name(i));
        }else{
            System.out.printf("%sハハズレ\n",Ship[i].name(i));
        }
    }
    //ゲームを終了
    public void finish(){
        System.out.printf("THE END\n");
    }
    //デバッグ(確認用)
    public void debug(Battleship Ship[], int sea[][]){
        for(int i = 0; i < SHIP; i++){
            if(Ship[i].current_HP(i) != 0){
                System.out.printf("%s:x座標%d y座標%d\n"
                ,Ship[i].name(i),Ship[i].pos_row+1,Ship[i].pos_column+1);
            }else{
                continue;
            }
        }
    }
}