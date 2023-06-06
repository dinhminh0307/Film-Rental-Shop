package Middleware;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateMiddleware {
    public static DateTimeFormatter dateParser(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter;
    }

    public static String dateAfterFormat(LocalDate localDate){
        DateTimeFormatter parser = dateParser();
        return parser.format(localDate);
    }



}
