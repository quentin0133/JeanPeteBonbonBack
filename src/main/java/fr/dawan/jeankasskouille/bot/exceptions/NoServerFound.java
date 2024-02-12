package fr.dawan.jeankasskouille.bot.exceptions;

public class NoServerFound extends RuntimeException {
    public NoServerFound() {
        super("Le serveur n'est pas un serveur enregistré");
    }
}
