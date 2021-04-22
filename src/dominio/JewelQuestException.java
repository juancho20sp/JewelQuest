package dominio;

public class JewelQuestException extends Exception{
    public static final String  RANDOM_ERROR = "El método random ha fallado, hable con el programador a cargo";
    public static final String NON_ADJACENT_CELLS = "Solo pueden intercambiarse celdas adyacentes";

    public JewelQuestException(String msg) {
        super(msg);
    }
}
