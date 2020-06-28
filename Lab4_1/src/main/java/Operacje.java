import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;

public class Operacje {

    final private static Random r = new Random(System.currentTimeMillis());

    public static void zapiszDoMapy(HazelcastInstance client) throws UnknownHostException {

        Map<Long, Zoo> zwierze = client.getMap("zwierze");

        Long key1 = (long) Math.abs(r.nextInt());
        short   key2 = (byte) Math.abs(r.nextInt(100)+1);
        Zoo zoo1 = new Zoo("Lew", key2 );
        System.out.println("PUT " + key1 + " => " + zoo1);
        zwierze.put(key1, zoo1);
        key1 = (long) Math.abs(r.nextInt());
        key2 = (byte) Math.abs(r.nextInt(100)+1);
        zoo1 = new Zoo("Hipopotam", key2);
        zwierze.put(key1, zoo1);
        System.out.println("PUT " + key1 + " => " + zoo1);
        key1 = (long) Math.abs(r.nextInt());
        key2 = (byte) Math.abs(r.nextInt(100)+1);
        zoo1 = new Zoo("Małpa", key2);
        zwierze.put(key1, zoo1);
        System.out.println("PUT " + key1 + " => " + zoo1);
        key1 = (long) Math.abs(r.nextInt());
        key2 = (byte) Math.abs(r.nextInt(100)+1);
        zoo1 = new Zoo("Słoń", key2);
        zwierze.put(key1, zoo1);
        System.out.println("PUT " + key1 + " => " + zoo1);
        key1 = (long) Math.abs(r.nextInt());
        key2 = (byte) Math.abs(r.nextInt(100)+1);
        zoo1 = new Zoo("Niedzwiedż", key2);
        zwierze.put(key1, zoo1);
        System.out.println("PUT " + key1 + " => " + zoo1);

    }
    public static void dodawanieZwierzencia(HazelcastInstance client) throws UnknownHostException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj nazwę Zwierzencia");
        String zwierze = "";
        zwierze = scan.nextLine();

        System.out.println("Podaj numer klatki");
        int nr_klatki;
        nr_klatki = scan.nextInt();


        Map<Long, Zoo> atrakcje = client.getMap("atrakcje");
        Long key1 = (long) Math.abs(r.nextInt());
        Zoo zoo1 = new Zoo(zwierze, nr_klatki);
        System.out.println("PUT " + key1 + " => " + zoo1);
        atrakcje.put(key1, zoo1);

    }

    public static void wyswietlWszystko(HazelcastInstance client) throws UnknownHostException {

        IMap<Long, Zoo> zwierze = client.getMap("zwierze");
        System.out.println("Wszystkie zwierzeta: ");
        for (Map.Entry<Long, Zoo> e : zwierze.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }
    }


    public static void wyswietlHipopotamyILwy(HazelcastInstance client) throws UnknownHostException {

        IMap<Long, Zoo> zwierze = client.getMap("zwierze");
        Collection<Zoo> zoo = zwierze.values( Predicates.sql( "(zwierze = Hipopotam OR zwierze = Lew)" ) );
        for (Zoo s : zoo) {
            System.out.println(s);
        }
    }

    public static void wyswietlHipopotamyZKlatek(HazelcastInstance client) throws UnknownHostException {

        IMap<Long, Zoo> zwierze = client.getMap("zwierze");
        Collection<Zoo> zoo = zwierze.values( Predicates.sql( "(zwierze = Hipopotam) AND (nr_klatki BETWEEN 1 AND 50)" ) );
        for (Zoo s : zoo) {
            System.out.println(s);
        }
    }
    public static void wyswietlKonkretneZwierze(HazelcastInstance client) throws UnknownHostException {

        IMap<Long, Zoo> zwierze = client.getMap("zwierze");

        Scanner in = new Scanner(System.in);
        System.out.println("Podaj nazwę zwierzęcia które chcesz wyszukać");
        String wybor = "";
        wybor = in.nextLine();

        Predicate<?,?> zwierzePredicate = Predicates.equal( "zwierze", wybor );

        Collection<Zoo> zoo = zwierze.values(Predicates.and(zwierzePredicate));
        for (Zoo s : zoo) {
            System.out.println(s);
        }
    }



    public static void usunWszystkieDane(HazelcastInstance client ) throws UnknownHostException {
        IMap<Long, Zoo> zwierze = client.getMap( "zwierze" );
        zwierze.evictAll();
    }

    public static void Edycja(HazelcastInstance client ) throws UnknownHostException {

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
        for (Map.Entry<Long, Zoo> e : zwierze.entrySet()) {
            Zoo zoo = e.getValue();
            String nazwa2 = zoo.getZwierze();

            if (nazwa.equals(nazwa2)) {
                System.out.println("Przed przeniesieniem: " + zoo);
                zoo.setNr_klatki(nr_klatki);
                System.out.println("Po przeniesieniu:" + zoo);
                //e.setValue(zoo);

                zwierze.replace(e.getKey(), zoo);

            }
        }
    }

}
