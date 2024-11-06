package com.pmartos;

import lombok.SneakyThrows;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        try {
            new App().dangerousMethod();
        } catch (Throwable e) {
            if (e instanceof IOException) {
                System.out.println("Successfully thrown and caught! Exception message: " + e.getMessage());
            } else {
                throw e;
            }
        }
    }

    @SneakyThrows
    private void dangerousMethod() {
        throw new IOException("This is a test");
    }
}
