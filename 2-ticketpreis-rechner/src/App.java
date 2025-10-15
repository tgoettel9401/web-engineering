import java.util.Scanner;

public class App {

    /**
     * Berechnet den finalen Ticketpreis basierend auf Alter und Wochentag.
     * @param alter Das Alter des Besuchers (in Jahren).
     * @param wochentag Der Wochentag (z.B. "Montag", "Dienstag").
     * @return Der berechnete Endpreis, oder -1.0 bei ungültiger Eingabe.
     */
    public static double berechneTicketpreis(int alter, String wochentag) {
        
        // Sonderregel: Fehlerhafte Eingabe - später lösen wir das mit Exceptions und verbessern das Verhalten!
        if (alter < 0) {
            return -1.0;
        }

        double basispreis;

        // --- Regelwerk A: Altersrabatte (if-else if-Kette) ---
        
        if (alter <= 5) {
            // Kleinkind: 0-5 Jahre
            basispreis = 0.00;
        } else if (alter <= 17) {
            // Kind: 6-17 Jahre
            basispreis = 10.00;
        } else if (alter <= 64) {
            // Erwachsener: 18-64 Jahre
            basispreis = 20.00;
        } else {
            // Senior: 65+ Jahre
            basispreis = 15.00;
        }

        // Wenn der Basispreis bereits 0 ist, wird kein weiterer Rabatt gewährt.
        if (basispreis == 0.00) {
            return basispreis;
        }

        // --- Regelwerk B: Zusätzlicher Wochentags-Rabatt (Switch-Statement) ---
        
        // Konvertiere den Input-String in Kleinbuchstaben für einen robusten Vergleich
        String tag = wochentag.toLowerCase();

        switch (tag) {
            case "dienstag":
                // 2,00 € Rabatt auf den Basispreis
                basispreis -= 2.00;
                break;
            case "montag":
            case "mittwoch":
            case "donnerstag":
            case "freitag":
            case "samstag":
            case "sonntag":
                // Kein Rabatt/Aufschlag
                break;
            default:
                // Unbekannter Wochentag, kein Rabatt. Die Validierung sparen wir uns an der Stelle!
                break;
        }

        return basispreis;
    }

    // ----------------------------------------------------------------------
    // Hauptmethode mit Nutzereingabe
    // ----------------------------------------------------------------------

    public static void main(String[] args) {
        // Erstelle ein Scanner-Objekt zur Verarbeitung von Nutzereingaben
        Scanner scanner = new Scanner(System.in);

        System.out.println("Willkommen beim Ticketpreisrechner!");
        
        // 1. Alter abfragen
        System.out.print("Bitte gib das Alter des Besuchers ein: ");
        // Nächste Ganzzahl lesen
        int alter = scanner.nextInt();
        
        // 2. Wochentag abfragen
        // nextLine() nach nextInt() verwenden, um den verbleibenden Zeilenumbruch zu konsumieren
        scanner.nextLine(); 
        System.out.print("Bitte gib den Wochentag ein (möglich sind: Montag, Dienstag, Mittwoch, Donnerstag, Freitag, Samstag, Sonntag): ");
        // Nächste Zeile als Wochentag lesen
        String wochentag = scanner.nextLine();
        
        // Berechnung aufrufen
        double endpreis = berechneTicketpreis(alter, wochentag);

        // --- Ausgabe des Ergebnisses ---
        
        if (endpreis == -1.0) {
            System.out.println("\nFehler: Das eingegebene Alter (" + alter + ") ist ungültig.");
        } else {
            System.out.printf("\nErgebnis für %d Jahre am %s:", alter, wochentag);
            System.out.printf("\nDer Ticketpreis beträgt: %.2f Euro\n", endpreis);
        }

        // Scanner schließen, um Ressourcen freizugeben
        scanner.close();
    }
}