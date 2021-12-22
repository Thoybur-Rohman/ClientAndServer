import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class cafeServer {
    private static final int STARTING = 0;
    private static final int NEXTORDER = 1;
    private static final int WAITINGACTION = 2;
    private static final int COLA = 3;
    private static final int SANDWICH = 4;
    private static final int CRISPS = 5;
    private static final int CHOCOLATE = 6;
    private static final int ADDCREDIT = 7;
    private static String theOutput = null;
    private static int state = 0;
    private static double credit = 10.0D;
    private static double quantity = 0.0D;
    private static double charge = 0.0D;
    private static double colaPrice = 1.2D;
    private static double sandwichPrice = 3.2D;
    private static double crispsPrice = 0.5D;
    private static double chocolatePrice = 1.35D;
    private static double addCredit = 0.0D;

    public cafeServer() {
    }

    public static String processInput(String var0) {
        switch(state) {
        case 0:
            theOutput = "cafe server ready and waiting";
            System.out.println("Cafe server ready and waiting...");
            state = 2;
            break;
        case 1:
            theOutput = "Cafe server ready and waiting";
            System.out.println("Cafe server ready and waiting...");
            state = 2;
            break;
        case 2:
            if (var0.equalsIgnoreCase("cola")) {
                theOutput = "send quantity";
                state = 3;
            } else if (var0.equalsIgnoreCase("sandwich")) {
                theOutput = "send quantity";
                state = 4;
            } else if (var0.equalsIgnoreCase("crisps")) {
                theOutput = "send quantity";
                state = 5;
            } else if (var0.equalsIgnoreCase("chocolate")) {
                theOutput = "send quantity";
                state = 6;
            } else if (var0.equalsIgnoreCase("add credit")) {
                theOutput = "send credit value";
                state = 7;
            } else if (var0.equalsIgnoreCase("see credit")) {
                theOutput = String.valueOf(credit);
                state = 1;
            } else {
                theOutput = "message not recognised";
                state = 2;
            }
            break;
        case 3:
            quantity = (double)Integer.parseInt(var0);
            charge = quantity * colaPrice;
            if (credit - charge >= 0.0D) {
                credit -= charge;
                theOutput = "Your new credit is " + String.format("%.2f", credit);
            } else {
                theOutput = "Your don't have enough credit. Add credit and try again.";
            }

            state = 1;
            break;
        case 4:
            quantity = (double)Integer.parseInt(var0);
            charge = quantity * sandwichPrice;
            if (credit - charge >= 0.0D) {
                credit -= charge;
                theOutput = "Your new credit is " + String.format("%.2f", credit);
            } else {
                theOutput = "Your don't have enough credit. Add credit and try again.";
            }

            state = 1;
            break;
        case 5:
            quantity = (double)Integer.parseInt(var0);
            charge = quantity * crispsPrice;
            if (credit - charge >= 0.0D) {
                credit -= charge;
                theOutput = "Your new credit is " + String.format("%.2f", credit);
            } else {
                theOutput = "Your don't have enough credit. Add credit and try again.";
            }

            state = 1;
            break;
        case 6:
            quantity = (double)Integer.parseInt(var0);
            charge = quantity * chocolatePrice;
            if (credit - charge >= 0.0D) {
                credit -= charge;
                theOutput = "Your new credit is " + String.format("%.2f", credit);
            } else {
                theOutput = "Your don't have enough credit. Add credit and try again.";
            }

            state = 1;
            break;
        case 7:
            addCredit = Double.parseDouble(var0);
            credit += addCredit;
            theOutput = "Your new credit is " + String.format("%.2f", credit);
            state = 1;
        }

        return theOutput;
    }

    public static void main(String[] var0) throws IOException {
        ServerSocket var1 = null;

        try {
            var1 = new ServerSocket(7777);
        } catch (IOException var8) {
            System.err.println("server: Could not listen on port: 7777.");
            System.exit(1);
        }

        System.out.println("Cafe server initialised and waiting for client connection on 7777");
        Socket var2 = null;

        try {
            var2 = var1.accept();
        } catch (IOException var7) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter var3 = new PrintWriter(var2.getOutputStream(), true);
        BufferedReader var4 = new BufferedReader(new InputStreamReader(var2.getInputStream()));
        String var6 = processInput((String)null);
        var3.println(var6);

        String var5;
        while((var5 = var4.readLine()) != null) {
            if (var5.equals("endcomms")) {
                System.out.println("server: endcomms and shutdown");
                break;
            }

            var6 = processInput(var5);
            var3.println(var6);
        }

        var3.close();
        var4.close();
        var2.close();
        var1.close();
        System.out.println("Cafe server terminated");
    }
}
