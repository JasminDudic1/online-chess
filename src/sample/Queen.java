package sample;

import sample.ChessPiece;
import sample.IllegalChessMoveException;

public class Queen extends ChessPiece {

    String pozicija;
    Color boja;
    public char znak='Q';

    Queen(String pozicija,Color boja){
        if(pozicija.length()!=2)throw new IllegalArgumentException("Van ploce");
        char slovo=pozicija.charAt(0);
        slovo=Character.toLowerCase(slovo);
        int broj=pozicija.charAt(1)-48;
        if(slovo<'a' || slovo>'h' || broj<1 || broj>8 || pozicija.length()>2) throw new IllegalArgumentException("Van ploce");
        this.pozicija=pozicija.toLowerCase();
        this.boja=boja;
    }


    @Override
    public String getPosition() {
        return pozicija;
    }

    @Override
    public Color getColor() {
        return boja;
    }

    @Override
    public void move(String position) {
        if(position.length()!=2)throw new IllegalArgumentException("Van ploce");
        char slovo=position.charAt(0);
        slovo=Character.toLowerCase(slovo);
        int broj=position.charAt(1)-48;
        if(slovo<'a' || slovo>'h' || broj<1 || broj>8 || pozicija.length()>2) throw new IllegalArgumentException("Potez nije ispravan");

        int pomakI=Math.abs(this.pozicija.charAt(0)-slovo);
        int pomakJ=Math.abs(this.pozicija.charAt(1)-broj-48);
        if(pomakI!=pomakJ && pomakI*pomakJ!=0) throw new IllegalChessMoveException("Potez nije ispravan");//ako nije ukoso ili u jednu stranu

        this.pozicija=position;

    }

    @Override
    public char getZnak() {
        return znak;
    }

    @Override
    void postaviNa(String pozicija) {
        this.pozicija=pozicija.toLowerCase();
    }
}