package py.pol.una.ii.pw.util;

import java.util.Map;

/**
 * Created by cesar on 07/10/15.
 */
public class CSVFileReadingException extends Exception{

    private String message;

    public CSVFileReadingException(){
        this.message ="";
    }

    public CSVFileReadingException(String m){
        this.message = m;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
