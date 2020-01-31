package Chess;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChessRoom {

    public GridPane boardGridPane;
    public Label opponentLab;
    public Label playerLab;
    public Label colorLab;
    public TextArea errorText;
    public Button previousBtn,nextBtn,lastBtn,firstBtn;
    private Board b;
    private int roomId;
    private PreparedStatement getOpponent,getWhite,getBlack;
    boolean running=true;
    private ChessPiece.Color bojaIgraca= ChessPiece.Color.WHITE;
    private Tab currentTab;
    boolean rematch=false;
    boolean isImport=false;

    public void setCurrentTab(Tab t){
        currentTab=t;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setPlayerLabels(int playerWhite,int playerBlack){

        try {
            getWhite=ConnectionDAO.getConn().prepareStatement("Select username from player where id=?");
            getWhite.setInt(1,playerWhite);

            getBlack=ConnectionDAO.getConn().prepareStatement("Select username from player where id=?");
            getBlack.setInt(1,playerBlack);

            ResultSet rwhite=getWhite.executeQuery();
            rwhite.next();
            ResultSet rblack=getBlack.executeQuery();
            rblack.next();

            if(isImport){
                playerLab.setText(rwhite.getString(1));
                opponentLab.setText(rblack.getString(1));
                return;
            }

            if(b.getCurrentPlayer()== ChessPiece.Color.WHITE){
                playerLab.setText(rwhite.getString(1));
                opponentLab.setText(rblack.getString(1));
            }else{
                opponentLab.setText(rwhite.getString(1));
                playerLab.setText(rblack.getString(1));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void checkForOpponent(){

        if(isImport)return;

        try {
            ResultSet rs=getOpponent.executeQuery();
            if(!rs.next()) System.out.println("Uhm fix here");

            if(rs.getInt(1)==-1 || rs.getInt(1)==-1){
                running=false;
                closeRoom();
                return;
            }

            if(rs.getInt(1)!=0 && rs.getInt(2)!=0){

                System.out.println("Ubacujem u sobu:"+roomId);
                b.setGameReady();

                if(rematch==false) {
                    b.setPlayersIds(rs.getInt(1),rs.getInt(2));
                    setPlayerLabels(rs.getInt(1),rs.getInt(2));
                }
                else {
                    b.setPlayersIds(rs.getInt(2),rs.getInt(1));
                    setPlayerLabels(rs.getInt(2),rs.getInt(1));
                }
                b.setController(this);
                running=false;


            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void draw(ChessPiece.Color bojaIgraca){

        this.bojaIgraca=bojaIgraca;
        b=new Board(boardGridPane, bojaIgraca);
        b.setColorLab(colorLab);
        b.setRoomId(roomId);

        try {
            getOpponent=ConnectionDAO.getConn().prepareStatement("Select white,black from room where id=?");
            getOpponent.setInt(1,roomId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        new Thread(()->{

            while(running){
                try {
                    Platform.runLater(()->checkForOpponent());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }

        }).start();

    }

    public void closeRoom() {


        if(isImport==true){
            currentTab.getTabPane().getTabs().remove(currentTab);
            return;
        }

        running=false;
        Connection conn=ConnectionDAO.getConn();

        try {
            PreparedStatement upit = conn.prepareStatement("Select white,black from room where id=?");
            upit.setInt(1,roomId);
            ResultSet rs=upit.executeQuery();
            rs.next();
            if(rs.getInt(1)==0 || rs.getInt(2)==0){
                System.out.println("Room id je "+roomId);
                upit=conn.prepareStatement("delete from room where id=?");
                upit.setInt(1,roomId);
                upit.executeUpdate();
                System.out.println("Obrisana soba");
            }else{
                if(bojaIgraca== ChessPiece.Color.WHITE){
                    upit=conn.prepareStatement("Update room set white=0 where id=?");
                    upit.setInt(1,roomId);
                    upit.executeUpdate();
                }else{
                    upit=conn.prepareStatement("Update room set black=0 where id=?");
                    upit.setInt(1,roomId);
                    upit.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        currentTab.getTabPane().getTabs().remove(currentTab);

    }

    public void exitClicked(ActionEvent actionEvent) {
        closeRoom();
    }

    public void rematch(){

        rematch=true;
        Connection conn=ConnectionDAO.getConn();
        clearRoom();
        running=true;
        if(bojaIgraca== ChessPiece.Color.WHITE)draw(ChessPiece.Color.BLACK);
        else draw(ChessPiece.Color.WHITE);

    }

    private void clearRoom(){

        if(roomId!=0) {

            Connection conn=ConnectionDAO.getConn();
            try {
                PreparedStatement upit = conn.prepareStatement("Update room set moves=? where id=?");
                upit.setString(1, "");
                upit.setInt(2, roomId);
                upit.executeUpdate();
                System.out.println("Clearing room "+roomId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void leaveRoom(){

        running=false;
        Connection conn=ConnectionDAO.getConn();


        try {
            int white,black;
            PreparedStatement upit = conn.prepareStatement("Select white,black from room where id=?");
            upit.setInt(1,roomId);
            ResultSet rs=upit.executeQuery();
            rs.next();
            white=rs.getInt(1);
            black=rs.getInt(2);
            if(white<=0 || black<=0){
                Alert a=new Alert(Alert.AlertType.CONFIRMATION);
                System.out.println("Room id je "+roomId);
                upit=conn.prepareStatement("delete from room where id=?");
                upit.setInt(1,roomId);
                upit.executeUpdate();
                System.out.println("Obrisana soba");
            }else{
                if(bojaIgraca== ChessPiece.Color.WHITE){
                    upit=conn.prepareStatement("Update room set white=-1 where id=?");
                    upit.setInt(1,roomId);
                    upit.executeUpdate();

                }else{
                    upit=conn.prepareStatement("Update room set black=-1 where id=?");
                    upit.setInt(1,roomId);
                    upit.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        currentTab.getTabPane().getTabs().remove(currentTab);

    }

    public void importGame(String moves,int whiteid,int blackid){

        isImport=true;

        lastBtn.setDisable(false);
        firstBtn.setDisable(false);
        nextBtn.setDisable(false);
        previousBtn.setDisable(false);

        b=new Board(boardGridPane, ChessPiece.Color.WHITE);
        b.setColorLab(colorLab);
        b.setRoomId(0);
        b.setPlayersIds(whiteid,blackid);
        b.setController(this);

            b.importGame(moves);



    }

    public void nextClicked(ActionEvent actionEvent) {

        b.next();

    }

    public void previousClicked(ActionEvent actionEvent) {
        b.previous();
    }

    public void firstClicked(ActionEvent actionEvent) {
        b.first();
    }

    public void lastClicked(ActionEvent actionEvent) {
        b.last();
    }
}
