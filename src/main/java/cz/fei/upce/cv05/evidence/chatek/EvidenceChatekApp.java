package cz.fei.upce.cv05.evidence.chatek;

import java.util.Scanner;

public class EvidenceChatekApp {

    private static final int KONEC_PROGRAMU = 0;
    private static final int VYPIS_CHATEK = 1;
    private static final int VYPIS_KONKRETNI_CHATKU = 2;
    private static final int PRIDANI_NAVSTEVNIKU = 3;
    private static final int ODEBRANI_NAVSTEVNIKU = 4;
    private static final int CELKOVA_OBSAZENOST = 5;
    private static final int VYPIS_PRAZDNE_CHATKY = 6;

    private static final int VELIKOST_KEMPU = 10;
    private static final int MAX_VELIKOST_CHATKY = 4;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] chatky = new int[VELIKOST_KEMPU];
        int operace;

        do {
            operace = zobrazMenu(scanner);
            switch (operace) {
                case VYPIS_CHATEK -> vypisChatek(chatky);
                case VYPIS_KONKRETNI_CHATKU -> vypisKonkretniChatku(scanner, chatky);
                case PRIDANI_NAVSTEVNIKU -> pridaniNavstevniku(scanner, chatky);
                case ODEBRANI_NAVSTEVNIKU -> odebraniNavstevniku(scanner, chatky);
                case CELKOVA_OBSAZENOST -> celkovaObsazenost(chatky);
                case VYPIS_PRAZDNE_CHATKY -> vypisPrazdneChatky(chatky);
                case KONEC_PROGRAMU -> System.out.println("Konec programu");
                default -> System.err.println("Neplatna volba");
            }
        } while (operace != KONEC_PROGRAMU);
    }

    private static int zobrazMenu(Scanner scanner) {
        System.out.println("""
                MENU:
                1 - vypsani vsech chatek
                2 - vypsani konkretni chatky
                3 - Pridani navstevniku
                4 - Odebrani navstevniku
                5 - Celkova obsazenost kempu
                6 - Vypis prazdne chatky
                0 - Konec programu
                """);

        System.out.print("Zadej volbu: ");
        return scanner.nextInt();
    }

    private static void vypisChatek(int[] chatky) {
        for (int i = 0; i < chatky.length; i++) {
            System.out.println("Chatka [" + (i + 1) + "] = " + chatky[i]);
        }
    }

    private static void vypisKonkretniChatku(Scanner scanner, int[] chatky) {
        int cisloChatky = ziskatCisloChatky(scanner);
        if (cisloChatky == -1) return; // Neplatné číslo chatky

        System.out.println("Chatka [" + (cisloChatky + 1) + "] = " + chatky[cisloChatky]);
    }

    private static void pridaniNavstevniku(Scanner scanner, int[] chatky) {
        int cisloChatky = ziskatCisloChatky(scanner);
        if (cisloChatky == -1) return; // Neplatné číslo chatky

        System.out.print("Zadej pocet navstevniku: ");
        int pocetNavstevniku = scanner.nextInt();

        if (!overPocetNavstevniku(pocetNavstevniku) || 
            (chatky[cisloChatky] + pocetNavstevniku) > MAX_VELIKOST_CHATKY) {
            System.err.println("Neplatna hodnota pro pocet navstevniku nebo prekrocen max.");
            return;
        }

        chatky[cisloChatky] += pocetNavstevniku;
    }

    private static void odebraniNavstevniku(Scanner scanner, int[] chatky) {
        int cisloChatky = ziskatCisloChatky(scanner);
        if (cisloChatky == -1) return; // Neplatné číslo chatky

        System.out.print("Zadej pocet navstevniku k odebrani: ");
        int pocetNavstevniku = scanner.nextInt();

        if (!overPocetOdebrani(pocetNavstevniku, chatky[cisloChatky])) {
            System.err.println("Neplatna hodnota pro pocet navstevniku");
            return;
        }

        chatky[cisloChatky] -= pocetNavstevniku;
    }

    private static void celkovaObsazenost(int[] chatky) {
        int celkovaObsazenos = 0;
        for (int obsazenost : chatky) {
            celkovaObsazenos += obsazenost;
        }
        System.out.println("Celkova obsazenost kempu: " + celkovaObsazenos);
    }

    private static void vypisPrazdneChatky(int[] chatky) {
        System.out.println("Prazdne chatky:");
        for (int i = 0; i < chatky.length; i++) {
            if (chatky[i] == 0) {
                System.out.println("Chatka [" + (i + 1) + "] je prazdna.");
            }
        }
    }

    // Nová metoda pro získání čísla chatky
    private static int ziskatCisloChatky(Scanner scanner) {
        System.out.print("Zadej cislo chatky: ");
        int cisloChatky = scanner.nextInt() - 1;

        if (cisloChatky < 0 || cisloChatky >= VELIKOST_KEMPU) {
            System.err.println("Tato chatka neexistuje");
            return -1; // Neplatné číslo chatky
        }
        return cisloChatky;
    }

    // Ověření počtu návštěvníků pro přidání
    private static boolean overPocetNavstevniku(int pocetNavstevniku) {
        return pocetNavstevniku > 0 && pocetNavstevniku <= MAX_VELIKOST_CHATKY;
    }

    // Ověření počtu návštěvníků pro odebrání
    private static boolean overPocetOdebrani(int pocetNavstevniku, int aktualniObsazenost) {
        return pocetNavstevniku > 0 && pocetNavstevniku <= aktualniObsazenost;
    }
}
