import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Map.Entry;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.EntryProcessor;

public class ZmianaWielkosciLiter {

    public static void main( HazelcastInstance client ) throws UnknownHostException {
		//final HazelcastInstance client = HazelcastClient.newHazelcastClient();

		IMap<Long, Zoo> zwierze = client.getMap("zwierze");
		zwierze.executeOnEntries(new HEntryProcessor());

		for (Entry<Long, Zoo> e : zwierze.entrySet()) {
			System.out.println(e.getKey() + " => " + e.getValue());
		}
	}
}

class HEntryProcessor implements EntryProcessor<Long, Zoo, String>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String process(Entry<Long, Zoo> e) {
		Zoo zoo = e.getValue();
		String name = zoo.getZwierze();
		if (name.equals(name.toLowerCase())) {
			name = name.toUpperCase();
			zoo.setZwierze(name);
		} else{
			name = name.toLowerCase();
			zoo.setZwierze(name);
		}
		
		System.out.println("Processing = " + zoo);
		e.setValue(zoo);
		
		return name;
	}
}
