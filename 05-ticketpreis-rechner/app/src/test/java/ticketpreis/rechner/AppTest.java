package ticketpreis.rechner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Die Klasse muss in einer Datei namens TicketrechnerTest.java liegen
class AppTest {

    private final double DELTA = 0.001; // Toleranz für double-Vergleiche

    // --- 1. Tests für Fehler und Grenzfälle (Standardpreis: 20.00 €) ---

    @Test
    @DisplayName("Test: Ungültiges Alter (< 0) muss -1.0 zurückgeben")
    void testUngueltigesAlter() {
        double expected = -1.0;
        double actual = App.berechneTicketpreis(-5, "montag");
        assertEquals(expected, actual); // assertEquals funktioniert ohne Delta
    }

    @Test
    @DisplayName("Test: Kleinkind (0-5) ist immer kostenlos")
    void testKleinkindFrei() {
        double expected = 0.00;
        double actual = App.berechneTicketpreis(4, "dienstag");
        assertEquals(expected, actual, DELTA); // oder auch mit Delta
    }

    // --- 2. Altersgruppen-Tests (am Wochenende, ohne Rabatt) ---

    @Test
    @DisplayName("Test: Kind (6-17) Basispreis")
    void testKindBasispreis() {
        // Grenzfälle 6 und 17
        assertEquals(10.00, App.berechneTicketpreis(6, "samstag"), DELTA);
        assertEquals(10.00, App.berechneTicketpreis(17, "samstag"), DELTA);
    }

    @Test
    @DisplayName("Test: Erwachsener (18-64) Basispreis")
    void testErwachsenerBasispreis() {
        // Grenzfälle 18 und 64
        assertEquals(20.00, App.berechneTicketpreis(18, "sonntag"), DELTA);
        assertEquals(20.00, App.berechneTicketpreis(64, "sonntag"), DELTA);
    }

    @Test
    @DisplayName("Test: Senior (65+) Basispreis")
    void testSeniorBasispreis() {
        assertEquals(15.00, App.berechneTicketpreis(65, "montag"), DELTA);
        assertEquals(15.00, App.berechneTicketpreis(80, "montag"), DELTA);
    }

    // --- 3. Wochentags-Rabatt-Tests (Dienstag: -2.00 €) ---

    @Test
    @DisplayName("Test: Kind mit Dienstags-Rabatt (10 - 2 = 8)")
    void testKindDienstag() {
        double expected = 8.00;
        double actual = App.berechneTicketpreis(10, "Dienstag"); // Groß-/Kleinschreibung testen
        assertEquals(expected, actual, DELTA);
    }

    @Test
    @DisplayName("Test: Erwachsener mit Dienstags-Rabatt (20 - 2 = 18)")
    void testErwachsenerDienstag() {
        double expected = 18.00;
        double actual = App.berechneTicketpreis(40, "dienstag");
        assertEquals(expected, actual, DELTA);
    }

    @Test
    @DisplayName("Test: Senior mit Dienstags-Rabatt (15 - 2 = 13)")
    void testSeniorDienstag() {
        double expected = 13.00;
        double actual = App.berechneTicketpreis(70, "Dienstag");
        assertEquals(expected, actual, DELTA);
    }

    // --- 4. Tests ohne Rabatt (z.B. Mittwoch) ---

    @Test
    @DisplayName("Test: Kein Rabatt am Mittwoch (Erwachsener)")
    void testMittwochKeinRabatt() {
        double expected = 20.00;
        double actual = App.berechneTicketpreis(40, "mittwoch");
        assertEquals(expected, actual, DELTA);
    }
}