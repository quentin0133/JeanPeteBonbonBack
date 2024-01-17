package fr.dawan.jeankasskouille.bot.exceptions;

public class NoTextChannelException extends Exception {
    public NoTextChannelException() {
        super("Vous devez définir un canal avant d'ajouter des évènements rappels");
    }
}
