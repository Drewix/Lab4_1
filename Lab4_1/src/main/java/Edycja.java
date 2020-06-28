import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Map.Entry;
import java.util.Scanner;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.EntryProcessor;

public class Edycja {

    public static void main( HazelcastInstance client) throws UnknownHostException {

        IMap<Long, Zoo> zwierze = client.getMap("zwierze");

        Scanner scan = new Scanner(System.in);
        System.out.println("Przenieś wszytskie zwierzecia jednego gatunku do jednej klatki");
        System.out.println("Podaj nazwe zwierzecia:");
        String nazwa = "";
        nazwa = scan.nextLine();
        System.out.println("Podaj nowy numer klatki");
        int nr_klatki = 0;
        nr_klatki = scan.nextInt();
        System.out.println("Wszystkie zwierzeta: ");
        zwierze.executeOnEntries(new EdycjaNaSerwerze(nazwa,nr_klatki));
        for (Entry<Long, Zoo> e : zwierze.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }
    }
}

class EdycjaNaSerwerze implements EntryProcessor<Long, Zoo, Integer>, Serializable {
    private static final long serialVersionUID = 1L;

    String nazwa = "";
    int nr_klatki;
    public EdycjaNaSerwerze(String nazwa, int nr_klatki) {
        this.nazwa = nazwa;
        this.nr_klatki =nr_klatki;
    }

    @Override
    public Integer process(Entry<Long, Zoo> e) {
        Zoo zoo = e.getValue();
        String nazwa1 = zoo.getZwierze();


        if (nazwa.equals(nazwa1)) {
            System.out.println("Przed przeniesieniem: " + zoo);
            zoo.setNr_klatki(nr_klatki);
            System.out.println("Po przeniesieniu:" + zoo);
            e.setValue(zoo);
        }
        //System.out.println("Processing = " + zoo);
        //e.setValue(zoo);

        return nr_klatki;
    }
}
