package java;

import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        System.out.print(uuid);
    }
}
