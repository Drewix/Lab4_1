import java.net.UnknownHostException;
import java.util.Scanner;

import com.hazelcast.core.DistributedObjectEvent;
import com.hazelcast.core.DistributedObjectListener;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
import com.hazelcast.partition.MigrationListener;
import com.hazelcast.partition.MigrationState;
import com.hazelcast.partition.PartitionService;
import com.hazelcast.partition.ReplicaMigrationEvent;
import com.hazelcast.map.listener.EntryAddedListener;

public class Serwer {
    public static void main(String[] args) throws UnknownHostException {

		HazelcastInstance instance = Hazelcast.newHazelcastInstance();

		instance.addDistributedObjectListener(new DistributedObjectListener() {

			@Override
			public void distributedObjectDestroyed(DistributedObjectEvent e) {
				System.out.println(e);
			}

			@Override
			public void distributedObjectCreated(DistributedObjectEvent e) {
				System.out.println(e);
			}
		});

		instance.getCluster().addMembershipListener(new MembershipListener() {

			@Override
			public void memberRemoved(MembershipEvent e) {
				System.out.println(e);
			}

			@Override
			public void memberAdded(MembershipEvent e) {
				System.out.println(e);
			}
		});

		PartitionService partitionService = instance.getPartitionService();
		partitionService.addMigrationListener(new MigrationListener() {
			
			@Override
			public void replicaMigrationFailed(ReplicaMigrationEvent e) {
				System.out.println(e);
			}
			
			@Override
			public void replicaMigrationCompleted(ReplicaMigrationEvent e) {
				System.out.println(e);
			}
			
			@Override
			public void migrationStarted(MigrationState s) {
				System.out.println(s);
			}
			
			@Override
			public void migrationFinished(MigrationState s) {
				System.out.println(s);
			}
		});

		IMap<Long, Zoo> zwierze = instance.getMap("zwierze");

		zwierze.addEntryListener(new EntryAddedListener<Long, Zoo>() {

			@Override
			public void entryAdded(EntryEvent<Long, Zoo> e) {
				System.out.println(e);
			}
		}, true);

		Scanner in = new Scanner(System.in);
		String wybor = "";
		boolean wyjscie = false;
		while (wybor != "0") {

			System.out.println("Proszę wybrac numer operacji:");
			System.out.println("0. Wyjście");
			System.out.println("1. Edycja po stronie serwera");

			wybor = in.nextLine();

			switch (wybor) {
				case "1":
					Edycja.main(instance);
					break;
				case "0":
					wyjscie = true;
					break;
				default:
					System.out.println("Zły znak");
					if (wyjscie)
						break;
			}
		}
	}

}