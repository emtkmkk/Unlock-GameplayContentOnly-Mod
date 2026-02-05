package mymod;

import java.util.ArrayList;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;

/**
 * @author 彼君不触
 * @author emtk
 * @version 9/23/2022
 * @since 8/26/2018
 */

@SpireInitializer
public class UnlockGameplayContentOnlyMod implements PostInitializeSubscriber {
	
	public static void initialize() {
		BaseMod.subscribe(new UnlockGameplayContentOnlyMod());
	}

	@Override
	public void receivePostInitialize() {
		unlockCharacters();
		unlockFinalAct();
		unlockDaily();
		unlockRelics();
		unlockCards();
	}

	public static void unlockCharacters() {
		UnlockTracker.hardUnlockOverride("The Silent");
		UnlockTracker.hardUnlockOverride("Defect");
		UnlockTracker.hardUnlockOverride("Watcher");
	}
	
	public static void unlockFinalAct() {
		CardCrawlGame.playerPref.putBoolean(PlayerClass.IRONCLAD.name() + "_WIN", true);
		CardCrawlGame.playerPref.putBoolean(PlayerClass.THE_SILENT.name() + "_WIN", true);
		CardCrawlGame.playerPref.putBoolean(PlayerClass.DEFECT.name() + "_WIN", true);
		CardCrawlGame.playerPref.flush();
	}
	
	public static void unlockDaily() {
		ArrayList<Prefs> prefs = new ArrayList<Prefs>();
		prefs.add(SaveHelper.getPrefs("STSDataVagabond"));
		prefs.add(SaveHelper.getPrefs("STSDataTheSilent"));
		prefs.add(SaveHelper.getPrefs("STSDataDefect"));
		prefs.add(SaveHelper.getPrefs("STSDataWatcher"));
		if (prefs.stream().noneMatch(p -> p.getInteger("BOSS_KILL", 0) > 0)) {
			prefs.get(0).putInteger("BOSS_KILL", 1);
			prefs.get(0).flush();
		}
	}
	
	public static void unlockCards() {
		for (String id : new ArrayList<String>(UnlockTracker.lockedCards)) {
			UnlockTracker.unlockPref.putInteger(id, 2);
			UnlockTracker.lockedCards.remove(id);
		}
		UnlockTracker.unlockPref.flush();
	}
	
	public static void unlockRelics() {
		for (String id : new ArrayList<String>(UnlockTracker.lockedRelics)) {
			UnlockTracker.unlockPref.putInteger(id, 2);
			UnlockTracker.lockedRelics.remove(id);
		}
		UnlockTracker.unlockPref.flush();
	}
	

}
