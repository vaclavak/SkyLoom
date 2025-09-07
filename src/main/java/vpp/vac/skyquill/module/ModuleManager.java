package vpp.vac.skyquill.module;

import java.util.ArrayList;

import vpp.vac.skyquill.module.impl.dungeons.BloodRoomClear;
import vpp.vac.skyquill.module.impl.dungeons.BloodRoomOpen;
import vpp.vac.skyquill.module.impl.dungeons.FragRunLootDetector;
import vpp.vac.skyquill.module.impl.mining.Chestcounter;
import vpp.vac.skyquill.module.impl.mining.CommisionCounter;
import vpp.vac.skyquill.module.impl.misc.BlackHoleReadyAlert;
import vpp.vac.skyquill.module.impl.settings.DeveloperMode;
import vpp.vac.skyquill.module.impl.trading.AntiOverfluxScam;

public class ModuleManager {

	public static ArrayList<Module> modules = new ArrayList<Module>();

	static {
		modules.add(new Chestcounter());
		modules.add(new DeveloperMode());
		modules.add(new AntiOverfluxScam());
		modules.add(new BloodRoomClear());
		modules.add(new BlackHoleReadyAlert());
		modules.add(new BloodRoomOpen());
		modules.add(new CommisionCounter());
	}

}
