package com.caci.gaia_leave.shared_tools.helper;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AllHelpers {

    public static <T> List<T> listConverter(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                                           .atZone(ZoneId.systemDefault())
                                           .toInstant());
    }

    public static Date convertedStringToDate(String dateToConvert) {
        LocalDate date = LocalDate.parse(dateToConvert);
        return AllHelpers.convertToDateViaInstant(date);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    ;

    public static List<String> getWeekends(int year, String pattern) {
        List<String> weekends = new ArrayList<>();
        LocalDate date = LocalDate.of(year, 1, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);


        while (date.getYear() == year) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                String dateString = date.format(formatter);
                weekends.add(dateString);
            }

            date = date.plusDays(1);
        }
        return weekends;
    }

    /*
     * Ova metoda računa datum pravoslavnog Uskrsa koristeći algoritam koji se zasniva na lunarnim ciklusima
     * i strukturi nedelje. U formuli se koriste sledeći ključni pojmovi:
     *
     * Metonov ciklus:
     * - Period od 19 godina nakon kojeg se približno ponavljaju faze Meseca (lunarne faze).
     * - Varijabla 'a' predstavlja poziciju godine unutar ovog ciklusa.
     *
     * Lunarna faza (Mesec):
     * - Odnosi se na periodične promene u vidljivosti Meseca.
     * - U algoritmu se uzimaju u obzir lunarne karakteristike (lunarni mesec) da bi se pravilno odredio datum Uskrsa.
     *
     * Ostatak varijabli:
     * - 'b' i 'c' su ostaci pri deljenju godine sa 4 i 7, što pomaže u određivanju rasporeda dana u nedelji.
     * - 'd' kombinuje poziciju u Metonovom ciklusu sa dodatnim podešavanjem za lunarne efekte.
     * - 'e' predstavlja dodatno podešavanje vezano za dane u nedelji.
     * - 'f' je zbir 'd' i 'e' i predstavlja ukupni pomak dana do Uskrsa.
     *
     * Na kraju, datum Uskrsa se računa kao 4 + f, što određuje dan u aprilu kada pada Uskrs.
     */
    public static LocalDate getOrthodoxEasterDate(int year) {
        // 'a': Pozicija godine u Metonovom ciklusu (19-godišnji ciklus koji prati ponavljanje lunarnih faza)
        int a = year % 19;

        // 'b': Ostatak pri deljenju godine sa 4, pomaže u određivanju dana u nedelji
        int b = year % 4;

        // 'c': Ostatak pri deljenju godine sa 7, dodatno podešavanje za raspored dana u nedelji
        int c = year % 7;

        // 'd': Kombinuje poziciju u Metonovom ciklusu sa lunarnim podešavanjem
        int d = (19 * a + 16) % 30;

        // 'e': Dodatno podešavanje bazirano na vrednostima 'b', 'c' i 'd' za tačan proračun dana u nedelji
        int e = (2 * b + 4 * c + 6 * d) % 7;

        // 'f': Ukupni pomak dana do Uskrsa (kombinacija lunarne i sedmične komponente)
        int f = d + e;

        // Vraća datum Uskrsa: aprilski datum određen kao (4 + f) dan u mesecu
        return LocalDate.of(year, 4, f + 4);
    }

}
