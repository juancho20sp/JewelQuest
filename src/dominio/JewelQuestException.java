package dominio;

public class JewelQuestException extends Exception{
    public static final String  RANDOM_ERROR = "El método random ha fallado, hable con el programador a cargo";

    public JewelQuestException(String msg) {
        super(msg);
    }
}
