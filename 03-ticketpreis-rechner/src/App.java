import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class App {

    /**
     * Berechnet den finalen Ticketpreis basierend auf Alter und Wochentag.
     * 
     * @param alter     Das Alter des Besuchers (in Jahren).
     * @param wochentag Der Wochentag (z.B. "Dienstag").
     * @return Der berechnete Einzelpreis, oder -1.0 bei ungültiger Eingabe.
     */
    public static double berechneTicketpreis(int alter, String wochentag) {

        if (alter < 0) {
            return -1.0;
        }

        double basispreis;

        // --- Regelwerk A: Altersrabatte (if-else if-Kette) ---
        if (alter <= 5) {
            basispreis = 0.00; // Kleinkind
        } else if (alter <= 17) {
            basispreis = 10.00; // Kind
        } else if (alter <= 64) {
            basispreis = 20.00; // Erwachsener
        } else {
            basispreis = 15.00; // Senior
        }

        if (basispreis == 0.00) {
            return basispreis;
        }

        // --- Regelwerk B: Zusätzlicher Wochentags-Rabatt (Switch-Statement) ---
        String tag = wochentag.toLowerCase();

        switch (tag) {
            case "dienstag":
                basispreis -= 2.00;
                break;
            // Alle anderen Tage haben keinen Rabatt
            default:
                break;
        }

        return basispreis;
    }

    // ----------------------------------------------------------------------
    // Hauptmethode mit for-Schleife, Eingabefluss und Ergebnisliste
    // ----------------------------------------------------------------------

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double gesamtpreis = 0.0;
        int anzahlPersonen;

        // Liste zum Speichern der Details für die finale Ausgabe
        List<String> ticketDetails = new ArrayList<>();

        System.out.println("Willkommen beim Ticketrechner!");

        // 1. Anzahl der Personen abfragen (erster Input)
        System.out.print("Für wie viele Personen soll der Preis berechnet werden? ");

        if (scanner.hasNextInt()) {
            anzahlPersonen = scanner.nextInt();
        } else {
            System.out.println("Ungültige Eingabe. Bitte geben Sie eine Zahl ein.");
            scanner.close();
            return;
        }

        // Zeilenumbruch konsumieren
        scanner.nextLine();

        // 2. Wochentag für die gesamte Gruppe abfragen (zweiter Input, einmalig)
        System.out.print("An welchem Wochentag ist der Besuch (z.B. Dienstag)? ");
        String wochentagDerGruppe = scanner.nextLine();

        System.out.println("\n--- Erfassung von " + anzahlPersonen + " Tickets am " + wochentagDerGruppe + " ---");

        // 3. Hauptschleife: for-Schleife für die feste Anzahl an Personen
        for (int i = 1; i <= anzahlPersonen; i++) {
            System.out.printf("Alter von Person %d: ", i);

            // Alter lesen
            if (!scanner.hasNextInt()) {
                System.out.printf("    Fehler bei Person %d: Ungültige Alterseingabe. Ticket ignoriert.\n", i);
                scanner.next(); // Ungültigen Input konsumieren
                scanner.nextLine(); // Zeilenumbruch konsumieren
                continue; // Nächste Iteration
            }
            int alter = scanner.nextInt();

            // Zeilenumbruch konsumieren
            scanner.nextLine();

            // Preis berechnen
            double einzelpreis = berechneTicketpreis(alter, wochentagDerGruppe);

            if (einzelpreis == -1.0) {
                // Fehlerbehandlung für negativen Alterswert
                System.out.printf("    Fehler bei Person %d: Alter (%d) ist negativ. Ticket ignoriert.\n", i, alter);
            } else {
                // 4. Preis zum Gesamtpreis addieren und Detail speichern
                gesamtpreis += einzelpreis;
                String detail = String.format("   - Person %d (Alter %d): %.2f Euro", i, alter, einzelpreis);
                ticketDetails.add(detail);
            }
        }

        // --- 5. Finale Ausgabe der Liste und des Gesamtpreises ---
        System.out.println("\n=============================================");
        System.out.println("Zusammenfassung der Tickets:");

        if (ticketDetails.isEmpty()) {
            System.out.println("Keine gültigen Tickets erfasst.");
        } else {
            // Liste aller Tickets ausgeben
            for (String detail : ticketDetails) {
                System.out.println(detail);
            }
        }

        System.out.println("---------------------------------------------");
        System.out.printf("Gesamtpreis für die Gruppe: %.2f Euro\n", gesamtpreis);
        System.out.println("=============================================");

        scanner.close();
    }
}