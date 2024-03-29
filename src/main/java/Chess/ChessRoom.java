package Chess;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
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
    public Button previousBtn, nextBtn, lastBtn, firstBtn;
    private boolean running = true;
    private boolean rematch = false;
    private boolean isImport = false;
    private boolean isSpectate = false;
    private Board b;
    private int roomId;
    private PreparedStatement getOpponent, getWhiteStatment, getBlackStatment, getWhiteID, getBlackID;
    private ChessPiece.Color bojaIgraca = ChessPiece.Color.WHITE;
    private Tab currentTab;
    private int playerID = 0;

    public Tab getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(Tab t) {
        currentTab = t;
    }

    public void setRoomId(int roomId) {

        this.roomId = roomId;
    }

    public void setPlayerLabels(int playerWhite, int playerBlack) {

        if (bojaIgraca == ChessPiece.Color.WHITE) playerID = playerWhite;
         else playerID = playerBlack;


        try {
            getWhiteStatment = ConnectionDAO.getConn().prepareStatement("Select username from player where id=?");
            getWhiteStatment.setInt(1, playerWhite);

            getBlackStatment = ConnectionDAO.getConn().prepareStatement("Select username from player where id=?");
            getBlackStatment.setInt(1, playerBlack);

            getWhiteID = ConnectionDAO.getConn().prepareStatement("Select white from room where id=?");
            getWhiteID.setInt(1, roomId);

            getBlackID = ConnectionDAO.getConn().prepareStatement("Select black from room where id=?");
            getBlackID.setInt(1, roomId);

            ResultSet rwhite = getWhiteStatment.executeQuery();
            rwhite.next();
            ResultSet rblack = getBlackStatment.executeQuery();
            rblack.next();

            if (isImport) {
                playerLab.setText(rwhite.getString(1));
                opponentLab.setText(rblack.getString(1));
                return;
            }

            if (b.getCurrentPlayer() == ChessPiece.Color.WHITE) {
                playerLab.setText(rwhite.getString(1));
                opponentLab.setText(rblack.getString(1));
            } else {
                opponentLab.setText(rwhite.getString(1));
                playerLab.setText(rblack.getString(1));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void checkForOpponent() {

        if (isImport) return;

        try {
            ResultSet rs = getOpponent.executeQuery();

            if (!rs.next()) new Alert(Alert.AlertType.ERROR,"Someone already deleted the room, shouldnt happen, just press exit").show();


            if (rs.getInt(1) == -1 || rs.getInt(1) == -1) {
                running = false;
                closeRoom();
                return;
            }

            if (rs.getInt(1) > 0 && rs.getInt(2) > 0) {

                clearMoves();
                b.setGameReady();



                if (rematch == false) {
                    setPlayerLabels(rs.getInt(1), rs.getInt(2));
                } else {
                    setPlayerLabels(rs.getInt(2), rs.getInt(1));
                }
                b.setPlayersIds(rs.getInt(1), rs.getInt(2));
                b.setController(this);
                running = false;


            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void draw(ChessPiece.Color bojaIgraca) {

        this.bojaIgraca = bojaIgraca;
        b = new Board(boardGridPane, bojaIgraca);
        b.setColorLab(colorLab);
        b.setRoomId(roomId);
        if (bojaIgraca == null) {
            isSpectate = true;
            b.spectateGame();
        }

        try {
            getOpponent = ConnectionDAO.getConn().prepareStatement("Select white,black from room where id=?");
            getOpponent.setInt(1, roomId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        new Thread(() -> {

            while (running) {
                try {
                    Platform.runLater(() -> checkForOpponent());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }

        }).start();

    }

    public void closeRoom() {


        if (isImport || isSpectate) {
            currentTab.getTabPane().getTabs().remove(currentTab);
            return;
        }

        running = false;
        Connection conn = ConnectionDAO.getConn();

        try {
            PreparedStatement upit = conn.prepareStatement("Select white,black from room where id=?");
            upit.setInt(1, roomId);
            ResultSet rs = upit.executeQuery();
            rs.next();
            if (rs.getInt(1) <= 0 || rs.getInt(2) <= 0) {
                upit = conn.prepareStatement("delete from room where id=?");
                upit.setInt(1, roomId);
                upit.executeUpdate();

            } else {

                if (bojaIgraca == ChessPiece.Color.WHITE) {
                    upit = conn.prepareStatement("Update room set white=0 where id=?");
                    upit.setInt(1, roomId);
                    upit.executeUpdate();
                } else {
                    upit = conn.prepareStatement("Update room set black=0 where id=?");
                    upit.setInt(1, roomId);
                    upit.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        currentTab.getTabPane().getTabs().remove(currentTab);

    }

    public void exitClicked(ActionEvent actionEvent) {
        endChessRoom();
    }

    public void endChessRoom() {
        b.stopGame();
        closeRoom();
    }

    public void rematch() {

        rematch = true;
        running = true;
        clearRoom();
        if (bojaIgraca == ChessPiece.Color.WHITE) {
            bojaIgraca = ChessPiece.Color.BLACK;
            draw(bojaIgraca);
        } else if (bojaIgraca == ChessPiece.Color.BLACK) {
            bojaIgraca = ChessPiece.Color.WHITE;
            draw(bojaIgraca);
        }
    }

    private void clearMoves(){

        try {
            PreparedStatement ps=ConnectionDAO.getConn().prepareStatement("Update room set moves=? where id=?");
            ps.setString(1,"");
            ps.setInt(2,roomId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void clearRoom() {

        System.out.println("Clearing room");

        if (roomId != 0) {

            Connection conn = ConnectionDAO.getConn();
            try {

                PreparedStatement ps = conn.prepareStatement("Update room set moves=?,white=?,black=? where id=?");
                ps.setString(1, "Rematching");

                int whiteID = getWhite();
                int blackID = getBlack();
                if (whiteID == -1 || blackID == -1) {
                    closeRoom();
                    return;
                }

                if (whiteID == 0) {

                    ps.setInt(2, playerID);
                    ps.setInt(3, blackID);//prepisujem istu vrijednost

                } else if (blackID == 0) {

                    ps.setInt(2, whiteID);//prepisujem istu vrijendost
                    ps.setInt(3, playerID);

                } else if (playerID == whiteID) {

                    ps.setInt(2, 0);
                    ps.setInt(3, whiteID);

                } else if (playerID == blackID) {
                    ps.setInt(2, blackID);
                    ps.setInt(3, 0);

                }

                ps.setInt(4, roomId);
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void leaveRoom() {

        running = false;
        Connection conn = ConnectionDAO.getConn();

        try {

            int whiteID = getWhite();
            int blackID = getBlack();
            PreparedStatement upit = conn.prepareStatement("Select white,black from room where id=?");
            upit.setInt(1, roomId);
            ResultSet rs = upit.executeQuery();
            rs.next();
            if (rs.getInt(1) == -1 && rs.getInt(2) == -1) {
                closeRoom();
                return;
            }

            upit = conn.prepareStatement("Update room set white=-1, black=-1 where id=?");
            upit.setInt(1, roomId);
            upit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        currentTab.getTabPane().getTabs().remove(currentTab);

    }

    public void importGame(String moves, int whiteid, int blackid) {

        isImport = true;

        lastBtn.setDisable(false);
        firstBtn.setDisable(false);
        nextBtn.setDisable(false);
        previousBtn.setDisable(false);

        b = new Board(boardGridPane, ChessPiece.Color.WHITE);
        b.setColorLab(colorLab);
        b.setRoomId(0);
        b.setPlayersIds(whiteid, blackid);
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

    private int getWhite() {

        try {
            ResultSet rs = getWhiteID.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    private int getBlack() {
        try {
            ResultSet rs = getBlackID.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
