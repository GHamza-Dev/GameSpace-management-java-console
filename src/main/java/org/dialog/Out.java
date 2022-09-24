package org.dialog;

public class Out {

    private final static int defaultDuration = 10;
    private static void softOut(String text, int duration) {
        int len = text.length();
        String output = "";
        System.out.println("");
        try {
            for (int i = 0; i < len; i++) {
                Thread.sleep(duration);
                output = output + text.charAt(i);
                System.out.print(output+"\r");
            }
        }catch (Exception e){
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    public static void soft(String text){
        softOut(text, defaultDuration);
    }
    public static void soft(String text,int duration){
        softOut(text, duration);
    }

}
