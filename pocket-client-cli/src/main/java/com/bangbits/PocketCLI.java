package com.bangbits;

import com.bangbits.pocket.PocketException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Simple Command Line Interface for accessing encrypted data stored in the Android
 * PIM application 'Pocket' [https://market.android.com/details?id=com.citc.wallet].
 * Simply run application with path to mounted DropBox folder (i.e. ./Dropbox/SecureWallet/)
 * 
 * Pocket is (c) Tim Clark and under a closed source license with no public API or
 * specification. This client thus exist only due to carefully inspecting Pockets
 * behavior, the wallet SQLite database and related research. The greatest 'leak' 
 * of implementation details by Tim is probably this post on stackoverflow:
 * [http://stackoverflow.com/questions/4573392/aes-cipher-not-picking-up-iv]
 * 
 * It goes without saying, this application may fail at any moment as Tim changes
 * file formats or encryption algorithms. It was tested against Pocket 2.3.
 * 
 * 
 * java -classpath /tmp/maven-targets/PocketCLI/PocketCLI-1.0-SNAPSHOT.jar:/home/casper/.m2/repository/bouncycastle/bcprov-jdk16/140/bcprov-jdk16-140.jar:/home/casper/.m2/repository/org/xerial/sqlite-jdbc/3.7.2/sqlite-jdbc-3.7.2.jar com.bangbits.PocketCLI /home/casper/Dropbox/SecureWallet
 * 
 * When support JAR's are in same dir as Pocket:
 * 
 * java -classpath /tmp/maven-targets/PocketCLI/PocketCLI-1.0-SNAPSHOT.jar com.bangbits.PocketCLI /home/casper/Dropbox/SecureWallet
 *
 * ...or...
 * 
 * java -jar /tmp/maven-targets/PocketCLI/jars/PocketCLI-1.0-SNAPSHOT.jar /home/casper/Dropbox/SecureWallet
 *
 * java -jar /tmp/maven-targets/pocket-client-cli/jars/pocket-client-cli-1.0-SNAPSHOT.jar /home/casper/Dropbox/SecureWallet
 */

public final class PocketCLI implements PocketController{

    public static void main(String... args) {
        Logger  logger = Logger.getLogger("com");
        logger.setLevel(Level.OFF);
        
        PocketCLI pocketCli = new PocketCLI(args);
    }

    public PocketCLI(String... args) {
        Package jar = PocketCLI.class.getPackage();
        
        info("* " + jar.getSpecificationTitle() + " " 
                + jar.getImplementationVersion() 
                + " by " + jar.getImplementationVendor() + " *");
        
        try{
            new LocalPocketModel( this, args.length == 0 ? "": args[0] + ( args[0].endsWith("/") ? "":"/") );
        }
        catch(PocketException pe){
            info(pe.getMessage());
        }
    }

    public String getPassword() {
        return new PasswordPrompt().readPassword("Please enter password to use for decryption: ");
    }

    public void info(String msg) {
        System.out.println(msg);
    }

    class InputStreamCleanerThread implements Runnable {

        private boolean keepRunning = true;

        public InputStreamCleanerThread(String prompt) {
            System.out.print(prompt);
        }

        public void run() {

            do {
                System.out.print("\010 ");
                
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ie) {
                    // Ignore
                }
                
            } while (keepRunning);
            
            System.out.print("\010");
            
        }

        public void halt() {
            this.keepRunning = false;
        }
    }

    class PasswordPrompt {

        String readPassword(String prompt) {
            final InputStreamCleanerThread cleanerThread = new InputStreamCleanerThread(prompt);
            final Thread mask = new Thread(cleanerThread);
            mask.start();

            final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String password = "";

            try {
                password = in.readLine();
            } catch (IOException ioe) {
                // Ignore
            }finally{
                cleanerThread.halt();
            }
            
            return password;
        }
    }
}