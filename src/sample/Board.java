package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.awt.*;
import java.sql.Ref;

public class Board{

    public static int jede = 0;
    //region Labels
    public Label A1Lab,A2Lab,A3Lab,A4Lab,A5Lab,A6Lab,A7Lab,A8Lab;
    public Label B1Lab,B2Lab,B3Lab,B4Lab,B5Lab,B6Lab,B7Lab,B8Lab;
    public Label C1Lab,C2Lab,C3Lab,C4Lab,C5Lab,C6Lab,C7Lab,C8Lab;
    public Label D1Lab,D2Lab,D3Lab,D4Lab,D5Lab,D6Lab,D7Lab,D8Lab;
    public Label E1Lab,E2Lab,E3Lab,E4Lab,E5Lab,E6Lab,E7Lab,E8Lab;
    public Label F1Lab,F2Lab,F3Lab,F4Lab,F5Lab,F6Lab,F7Lab,F8Lab;
    public Label G1Lab,G2Lab,G3Lab,G4Lab,G5Lab,G6Lab,G7Lab,G8Lab;
    public Label H1Lab,H2Lab,H3Lab,H4Lab,H5Lab,H6Lab,H7Lab,H8Lab;

    //endregion

    String selectedPozicija="";

    private ChessPiece[][] board = new ChessPiece[2][];
    private Label[][]UIboard=new Label[8][];


    public Board() {

        board[0] = new ChessPiece[16];
        board[1] = new ChessPiece[16];

        for(int i=0;i<8;i++)
        UIboard[i]=new Label[8];

        //region Rooks
        board[0][0] = new Rook("a1", ChessPiece.Color.WHITE);
        board[0][7] = new Rook("h1", ChessPiece.Color.WHITE);
        board[1][0] = new Rook("a8", ChessPiece.Color.BLACK);
        board[1][7] = new Rook("h8", ChessPiece.Color.BLACK);
        //endregion

        //region Knight
        board[0][1] = new Knight("b1", ChessPiece.Color.WHITE);
        board[0][6] = new Knight("g1", ChessPiece.Color.WHITE);
        board[1][1] = new Knight("b8", ChessPiece.Color.BLACK);
        board[1][6] = new Knight("g8", ChessPiece.Color.BLACK);
        //endregion

        //region Bishop
        board[0][2] = new Bishop("c1", ChessPiece.Color.WHITE);
        board[0][5] = new Bishop("f1", ChessPiece.Color.WHITE);
        board[1][2] = new Bishop("c8", ChessPiece.Color.BLACK);
        board[1][5] = new Bishop("f8", ChessPiece.Color.BLACK);
        //endregion

        //region Queen
        board[0][3] = new Queen("d1", ChessPiece.Color.WHITE);
        board[1][3] = new Queen("d8", ChessPiece.Color.BLACK);
        //endregion

        //region King
        board[0][4] = new King("e1", ChessPiece.Color.WHITE);
        board[1][4] = new King("e8", ChessPiece.Color.BLACK);
        //endregion

        //region Pawns
        for (int i = 0; i < 8; i++) {
            board[0][8 + i] = new Pawn((char) ('a' + i) + "2", ChessPiece.Color.WHITE);
            board[1][8 + i] = new Pawn((char) ('a' + i) + "7", ChessPiece.Color.BLACK);
        }
        //endregion

        //Region Labels
        //endregion
        //UIboard[0][0].setText("Heroo");
    }


    public void initialize(){

        for(int i=0;i<8;i++)
            UIboard[i]=new Label[8];

        //region UIBoard
        UIboard[0][0]=A1Lab;
        UIboard[0][1]=A2Lab;
        UIboard[0][2]=A3Lab;
        UIboard[0][3]=A4Lab;
        UIboard[0][4]=A5Lab;
        UIboard[0][5]=A6Lab;
        UIboard[0][6]=A7Lab;
        UIboard[0][7]=A8Lab;

        UIboard[1][0]=B1Lab;
        UIboard[1][1]=B2Lab;
        UIboard[1][2]=B3Lab;
        UIboard[1][3]=B4Lab;
        UIboard[1][4]=B5Lab;
        UIboard[1][5]=B6Lab;
        UIboard[1][6]=B7Lab;
        UIboard[1][7]=B8Lab;

        UIboard[2][0]=C1Lab;
        UIboard[2][1]=C2Lab;
        UIboard[2][2]=C3Lab;
        UIboard[2][3]=C4Lab;
        UIboard[2][4]=C5Lab;
        UIboard[2][5]=C6Lab;
        UIboard[2][6]=C7Lab;
        UIboard[2][7]=C8Lab;

        UIboard[3][0]=D1Lab;
        UIboard[3][1]=D2Lab;
        UIboard[3][2]=D3Lab;
        UIboard[3][3]=D4Lab;
        UIboard[3][4]=D5Lab;
        UIboard[3][5]=D6Lab;
        UIboard[3][6]=D7Lab;
        UIboard[3][7]=D8Lab;

        UIboard[4][0]=E1Lab;
        UIboard[4][1]=E2Lab;
        UIboard[4][2]=E3Lab;
        UIboard[4][3]=E4Lab;
        UIboard[4][4]=E5Lab;
        UIboard[4][5]=E6Lab;
        UIboard[4][6]=E7Lab;
        UIboard[4][7]=E8Lab;

        UIboard[5][0]=F1Lab;
        UIboard[5][1]=F2Lab;
        UIboard[5][2]=F3Lab;
        UIboard[5][3]=F4Lab;
        UIboard[5][4]=F5Lab;
        UIboard[5][5]=F6Lab;
        UIboard[5][6]=F7Lab;
        UIboard[5][7]=F8Lab;

        UIboard[6][0]=G1Lab;
        UIboard[6][1]=G2Lab;
        UIboard[6][2]=G3Lab;
        UIboard[6][3]=G4Lab;
        UIboard[6][4]=G5Lab;
        UIboard[6][5]=G6Lab;
        UIboard[6][6]=G7Lab;
        UIboard[6][7]=G8Lab;

        UIboard[7][0]=H1Lab;
        UIboard[7][1]=H2Lab;
        UIboard[7][2]=H3Lab;
        UIboard[7][3]=H4Lab;
        UIboard[7][4]=H5Lab;
        UIboard[7][5]=H6Lab;
        UIboard[7][6]=H7Lab;
        UIboard[7][7]=H8Lab;

        //endregion

        for(int i=0;i<8;i++){

            for(int j=0;j<8;j++){
                char pom='A';
                pom+=i;
                String pozicija=""+pom+(j+1);

                ChessPiece naLok=naLokaciji(pozicija);
                if(naLok!=null) {
                    char boja='w';
                    if(naLok.getColor()== ChessPiece.Color.BLACK)boja='b';
                    UIboard[i][j].setText(""+naLok.getZnak()+boja);
                }

                if((i+j)%2==0) {
                    UIboard[i][j].setStyle("-fx-background-color: black;-fx-text-fill: gray;");
                }else  UIboard[i][j].setStyle("-fx-background-color: white;-fx-text-fill: gray;");


            }

        }

    }


    void move(Class type, ChessPiece.Color color, String position) {

        position = position.toLowerCase();
        ChessPiece naLokaciji = naLokaciji(position);

        boolean uSahu=false;

        if (naLokaciji != null) {
            if(naLokaciji.getClass()==King.class) throw new IllegalChessMoveException("Kralj se ne moze pojest");
            if (naLokaciji.getColor() == color) throw new IllegalChessMoveException("Ima ista boja na poziciji");
            if (naLokaciji.getColor() != color) jede = 1;
        }

        int boja = 0;
        if (color == ChessPiece.Color.BLACK) boja = 1;

        figuraLoop:
        for (ChessPiece c : board[boja]) {

            if(c.getClass()!=type)continue;
            if(!praznaPutanja(c.getPosition(), position) && c.getClass()!=Knight.class) continue;
            try{
                String staraPozicija=c.getPosition();

                c.move(position);//pomjeri
                if(jede==1)naLokaciji.postaviNa("X");

                uSahu = isCheck(color);
                if(uSahu==true){
                    c.postaviNa(staraPozicija);
                    naLokaciji.postaviNa(position);
                    continue figuraLoop;
                }

                jede=0;
                return;
            }catch (Exception e){
                continue figuraLoop;
            }
        }
        jede=0;
        throw new IllegalChessMoveException("Nije moguce napraviti taj potez");

    }

    void move(String oldPosition, String newPosition) {


        if(oldPosition==newPosition)throw new IllegalChessMoveException("Ista pozicija");

        ChessPiece.Color boja=naLokaciji(oldPosition).getColor();

        oldPosition = oldPosition.toLowerCase();
        newPosition = newPosition.toLowerCase();
        ChessPiece staraLokacijaFigura = naLokaciji(oldPosition);
        ChessPiece novaLokacijaFigura = naLokaciji(newPosition);
        if (staraLokacijaFigura == null) throw new IllegalArgumentException("Nema sta pomjeriti");

        if (novaLokacijaFigura != null) {
            if(naLokaciji(newPosition).getClass()==King.class)  throw new IllegalChessMoveException("Kralj se ne moze pojest");
            if (novaLokacijaFigura.getColor() != staraLokacijaFigura.getColor()) jede = 1;
        }

        if (staraLokacijaFigura.getClass() != Knight.class) {

            if (!praznaPutanja(oldPosition, newPosition))
                throw new IllegalChessMoveException("Nije moguce napraviti taj potez");
        }


        staraLokacijaFigura.move(newPosition);
        int jedeTemp=jede;
        if(isCheck(boja) ){
            staraLokacijaFigura.postaviNa(oldPosition);
            jede=0;
            throw new IllegalChessMoveException("U sahu je ");
        }
        jede=jedeTemp;


        if (jede == 1) novaLokacijaFigura.postaviNa("X");
        jede = 0;
    }

    boolean isCheck(ChessPiece.Color boja) {

        int bojaInt = 0;
        if (boja == ChessPiece.Color.BLACK) bojaInt = 1;

        ChessPiece kralj = board[bojaInt][4];
        String pozicija = kralj.getPosition();

        for (ChessPiece c : board[1 - bojaInt]) {
            jede=1;
            if(!praznaPutanja(c.getPosition(), kralj.getPosition()) && c.getClass()!=Knight.class) continue;//ako nije przna putanja dalje
            String staraPozicija = c.getPosition();
            try {
                c.move(pozicija);//probja pomjerit figuru na poziciju kralja
                c.postaviNa(staraPozicija);//ako moze vrati nazad
                jede=0;//resetuj i zavrsio
                return true;
            } catch (Exception e) {
                continue;
            }
        }
        jede = 0;
        return false;
    }

    public void ispisi() {

        //region GornjiRedSlova
        System.out.print(" |");
        for (char i = 'a'; i <= 'h'; i++)
            System.out.print(i + " |");
        System.out.println();
        //endregion

        //region BrojeviIFigure
        for (int i = 1; i <= 8; i++) {
            System.out.print(9 - i + "|");//lijevi brojevi
            for (char j = 'a'; j <= 'h'; j++) {
                ChessPiece c = naLokaciji("" + j + (9 - i));
                if (c == null) System.out.print("  |");
                else {
                    String boja = "w";
                    if (c.getColor() == ChessPiece.Color.BLACK) boja = "b";
                    System.out.print(c.getZnak() + boja + "|");
                }
            }

            System.out.println(9 - i + "");//desni brojevi
        }
        //endregion

        //region DonjiRedSlova
        System.out.print(" |");
        for (char i = 'a'; i <= 'h'; i++)
            System.out.print(i + " |");
        System.out.println();
        //endregion

    }

    private ChessPiece naLokaciji(String lokacija) {
        if(lokacija.length()!=2)return null;
        lokacija = lokacija.toLowerCase();
        for (int i = 0; i <= 1; i++)
            for (int j = 0; j <= 15; j++) {
                if (board[i][j].getPosition().equals(lokacija)) return board[i][j];
            }

        return null;

    }

    private boolean praznaPutanja(String pocetnaPozicija, String krajnjaPozicija) {
        if(pocetnaPozicija.length()!=2 || krajnjaPozicija.length()!=2)return false;
        char iFigure = pocetnaPozicija.charAt(0);
        char jFigure = pocetnaPozicija.charAt(1);

        char iPozicije = krajnjaPozicija.charAt(0);
        char jPozicije = krajnjaPozicija.charAt(1);

        while (!("" + iFigure + jFigure).equals(krajnjaPozicija)) {

            if (iFigure < iPozicije) iFigure++;//pomjeranje po koodrinatama
            else if (iFigure > iPozicije) iFigure--;//ako je u koso obe uvecava/smanjuje
            if (jFigure < jPozicije) jFigure++;//za konja ne gleda jer ne treba
            else if (jFigure > jPozicije) jFigure--;//za topa ne uvecava jednu (zato nije <=/>= )

            if (("" + iFigure + jFigure).equals(krajnjaPozicija)) break;
            ChessPiece testni = naLokaciji("" + iFigure + jFigure);
            if (testni != null) return false;


        }
        return true;

    }

    public void Clicked(MouseEvent mouseEvent) {


        Label label = (Label) mouseEvent.getSource();

        String pozicija=label.getId().substring(0,2);

        ChessPiece naLok=naLokaciji(pozicija);


            if(naLok!=null){
            if(selectedPozicija.equals(pozicija)){

                selectedPozicija="";
                refresh();
            }
           else {

               refresh();
               label.setStyle("-fx-background-color: green;-fx-text-fill: gray;");

               selectedPozicija=pozicija;
                check(pozicija);
            }




        }else if(selectedPozicija.length()>0){

                try {
                    testMove(selectedPozicija, pozicija);
                }catch (Exception ex){
                    UIboard[0][0].setText(ex.toString());
                }
                selectedPozicija="";
                refresh();

            }
            else{
                selectedPozicija="";
            refresh();}




    }



    void testMove(String oldPosition, String newPosition) {


        if(oldPosition==newPosition)throw new IllegalChessMoveException("Ista pozicija");

        ChessPiece.Color boja=naLokaciji(oldPosition).getColor();

        oldPosition = oldPosition.toLowerCase();
        newPosition = newPosition.toLowerCase();
        ChessPiece staraLokacijaFigura = naLokaciji(oldPosition);
        ChessPiece novaLokacijaFigura = naLokaciji(newPosition);
        if(novaLokacijaFigura!=null)
        if(staraLokacijaFigura.getColor()==novaLokacijaFigura.getColor())throw new IllegalArgumentException("Ista boja");

        if (staraLokacijaFigura == null) throw new IllegalArgumentException("Nema sta pomjeriti");

        if (novaLokacijaFigura != null) {
            if(naLokaciji(newPosition).getClass()==King.class)  throw new IllegalChessMoveException("Kralj se ne moze pojest");
            if (novaLokacijaFigura.getColor() != staraLokacijaFigura.getColor()) jede = 1;
            else jede=0;
        }

        if (staraLokacijaFigura.getClass() != Knight.class) {

            if (!praznaPutanja(oldPosition, newPosition))
                throw new IllegalChessMoveException("Nije moguce napraviti taj potez");
        }


        staraLokacijaFigura.move(newPosition);
        int jedeTemp=jede;
        if(isCheck(boja) ){
            staraLokacijaFigura.postaviNa(oldPosition);
            jede=0;
            throw new IllegalChessMoveException("U sahu je ");
        }
        jede=jedeTemp;
        jede = 0;
    }

    public void refresh(){

        for(int i=0;i<8;i++){

            for(int j=0;j<8;j++){
                char pom='a';
                pom+=i;
                String pozicija=""+pom+(j+1);

                ChessPiece naLok=naLokaciji(pozicija);

                if(naLok!=null) {
                    char boja='w';
                    if(naLok.getColor()== ChessPiece.Color.BLACK)boja='b';
                    UIboard[i][j].setText(""+naLok.getZnak()+boja);
                }else UIboard[i][j].setText("");

                if((i+j)%2==0) {
                    UIboard[i][j].setStyle("-fx-background-color: black;-fx-text-fill: gray;");
                }else  UIboard[i][j].setStyle("-fx-background-color: white;-fx-text-fill: gray;");



            }

        }

    }

    public void check(String position){

        ChessPiece naLok=naLokaciji(position);

        for(char c='a';c<='h';c++){

                for(int j=1;j<=8;j++){
                String newPosition=""+c+j;

                try{
                    jede=0;
                    testMove(position,newPosition);
                    UIboard[c - 'a'][j - 1].setStyle("-fx-background-color: lightblue;-fx-text-fill: gray;");
                    naLok.postaviNa(position);
                }catch (Exception e){

                }


                }

        }
        jede=0;
    }


}