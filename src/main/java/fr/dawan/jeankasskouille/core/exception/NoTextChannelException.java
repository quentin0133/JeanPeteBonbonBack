package fr.dawan.jeankasskouille.core.exception;

public class NoTextChannelException extends Exception {
    public NoTextChannelException() {
        super("Vous devez définir un canal avant d'ajouter des évènements rappels");
    }
}
