package com.qishui.commontoolslibrary.exception;

/**
 * Created by zhou on 2018/12/23.
 */

public class ErrorHandle extends RuntimeException {

    public ErrorHandle() {
    }

    public ErrorHandle(String message) {
        super(message);
    }

    public ErrorHandle(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorHandle(Throwable cause) {
        super(cause);
    }
}
