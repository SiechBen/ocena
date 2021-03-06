/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.exceptions;

import java.util.ResourceBundle;

/**
 *
 * @author siech
 */
public class MilesException extends Exception {

    {
        bundle = ResourceBundle.getBundle("text");
    }

    public MilesException(String code) {
        this.code = code;
        this.message = bundle.getString(code);
    }

    public MilesException(String code, String prepend, String append) {
        this.code = code;
        this.message = prepend + (prepend == null ? "" : " ") + bundle.getString(code) + (append == null ? "" : " ") + append;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }

    private final String code;
    private final String message;
    private final ResourceBundle bundle;

}
