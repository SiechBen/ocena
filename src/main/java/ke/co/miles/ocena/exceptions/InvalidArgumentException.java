/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.exceptions;

/**
 *
 * @author Ben Siech
 */
public class InvalidArgumentException extends MilesException {

    public InvalidArgumentException(String code) {
        super(code);
    }

    public InvalidArgumentException(String code, String prepend, String append) {
        super(code, prepend, append);
    }

}
