import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class App {

    // ----------------------------------------------------------------------
    // Hauptmethode mit for-Schleife, Eingabefluss und Ergebnisliste
    // ----------------------------------------------------------------------

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Willkommen beim Ticketrechner!");

        // 1. Anzahl der Personen abfragen (erster Input)
        System.out.print("Für wie viele Personen soll der Preis berechnet werden? ");

        int numberOfPeople = 0;
        if (scanner.hasNextInt()) {
            numberOfPeople = scanner.nextInt();
        } else {
            System.out.println("Ungültige Eingabe. Bitte gib eine Zahl ein.");
            scanner.close();
            return;
        }

        // Zeilenumbruch konsumieren
        scanner.nextLine();

        // 2. Wochentag für die gesamte Gruppe abfragen (zweiter Input, einmalig)
        System.out.print("An welchem Wochentag ist der Besuch (z.B. Dienstag)? ");
        Weekday weekday = Weekday.fromString(scanner.nextLine());

        System.out
                .println("\n--- Erfassung von " + numberOfPeople + " Tickets am " + weekday.getInputString() + " ---");

        // 3. Schleife zum Einlesen der Personen
        List<Person> people = new ArrayList<>();
        for (int i = 1; i <= numberOfPeople; i++) {

            System.out.printf("Name von Person %d: ", i);
            String name = scanner.nextLine();

            System.out.printf("Alter von Person %d: ", i);
            int alter = scanner.nextInt();

            scanner.nextLine();
            people.add(new Person(name, alter));

        }

        // --- 4. Finale Ausgabe der Liste und des Gesamtpreises ---
        System.out.println("\n=============================================");
        System.out.println("Zusammenfassung der Tickets:");

        people.forEach(person -> {
            System.out.print(getOutputForPerson(person, weekday));
            System.out.println();
        });

        System.out.println("---------------------------------------------");
        Double finalPrice = people.stream()
                .mapToDouble(person -> getTicketprice(weekday, person.getAge()))
                .sum();

        System.out.printf("Gesamtpreis für die Gruppe: %.2f Euro\n", finalPrice);
        System.out.println("=============================================");

        scanner.close();

    }

    private static Double getTicketprice(Weekday weekday, Integer age) {
        return TicketCategory.fromAge(age).getBasePrice() - weekday.getDiscount();
    }

    private static String getOutputForPerson(Person person, Weekday weekday) {
        return new StringBuilder()
                .append("   - ")
                .append("Person " + person.getName() + " ")
                .append("(Alter " + person.getAge() + "): ")
                .append(String.format("%.2f Euro", getTicketprice(weekday, person.getAge())))
                .toString();
    }

}