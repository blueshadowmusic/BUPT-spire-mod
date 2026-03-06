package Modcore;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import Cards.Dimly.CenterOfUniverse;
import Cards.Dimly.DeadRedHood;
import Cards.Dimly.DimlyDefend;
import Cards.Dimly.EternalMeek;
import Cards.Dimly.KeystrokeMaster;
import Cards.Dimly.SupportBloodBlooming;
import Characters.Dimly.Dimly;
import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;

@SpireInitializer
public class BUPTMod implements PostInitializeSubscriber, EditCharactersSubscriber, EditCardsSubscriber, EditKeywordsSubscriber {
	private static final Logger logger = LogManager.getLogger(BUPTMod.class.getSimpleName());

	public static final String MOD_ID = "buptmod";
	private static final Color DIMLY_COLOR = new Color(0.54f, 0.33f, 0.20f, 1.0f);


	public static void initialize() {
		BUPTMod mod = new BUPTMod();
		mod.register();
	}

	public BUPTMod() {
		BaseMod.addColor(
				Dimly.Enums.COLOR_DIMLY,
				DIMLY_COLOR,
				CardHelper.getColor(174, 122, 81),
				CardHelper.getColor(174, 122, 81),
				CardHelper.getColor(174, 122, 81),
				CardHelper.getColor(200, 180, 160),
				CardHelper.getColor(200, 180, 160),
				CardHelper.getColor(200, 180, 160),
				asset("512/BUPT_bg_attack_512.png"),
				asset("512/BUPT_bg_skill_512.png"),
				asset("512/BUPT_bg_power_512.png"),
				asset("Dimly/Dimly_small_orb.png"),
				asset("1024/BUPT_bg_attack.png"),
				asset("1024/BUPT_bg_skill.png"),
				asset("1024/BUPT_bg_power.png"),
				asset("Dimly/Dimly_cost_orb.png"),
				asset("Dimly/Dimly_card_orb.png")
		);
	}

	private void register() {
		BaseMod.subscribe(this);
	}

	@Override
	public void receivePostInitialize() {
		logger.info("BUPTMod loaded: registering Dimly");
	}

	@Override
	public void receiveEditCharacters() {
		BaseMod.addCharacter(
				new Dimly("Dimly", Dimly.Enums.DIMLY_CLASS),
				asset("Dimly/Dimly_Button.png"),
				asset("Dimly/Dimly_Portrait.png"),
				Dimly.Enums.DIMLY_CLASS
		);
	}

	@Override
	public void receiveEditCards() {
		loadLocalization();
		BaseMod.addCard(new CenterOfUniverse());
		BaseMod.addCard(new SupportBloodBlooming());
		BaseMod.addCard(new EternalMeek());
		BaseMod.addCard(new DeadRedHood());
		BaseMod.addCard(new KeystrokeMaster());
		BaseMod.addCard(new DimlyDefend());
		unlockCards();
	}

	@Override
	public void receiveEditKeywords() {
		loadLocalization();
		registerKeywords();
	}

	private void unlockCards() {
		UnlockTracker.unlockCard(CenterOfUniverse.ID);
		UnlockTracker.markCardAsSeen(CenterOfUniverse.ID);
		UnlockTracker.unlockCard(SupportBloodBlooming.ID);
		UnlockTracker.markCardAsSeen(SupportBloodBlooming.ID);
		UnlockTracker.unlockCard(EternalMeek.ID);
		UnlockTracker.markCardAsSeen(EternalMeek.ID);
		UnlockTracker.unlockCard(DeadRedHood.ID);
		UnlockTracker.markCardAsSeen(DeadRedHood.ID);
		UnlockTracker.unlockCard(KeystrokeMaster.ID);
		UnlockTracker.markCardAsSeen(KeystrokeMaster.ID);
		UnlockTracker.unlockCard(DimlyDefend.ID);
		UnlockTracker.markCardAsSeen(DimlyDefend.ID);
	}

	private void loadLocalization() {
		String folder;
		switch (Settings.language) {
			case ZHS:
				folder = "zhs";
				break;
			default:
				folder = "eng";
		}
		String base = MOD_ID + "Resources/localization/" + folder + "/";
		BaseMod.loadCustomStringsFile(CardStrings.class, base + "buptmod-cards.json");
		BaseMod.loadCustomStringsFile(PowerStrings.class, base + "buptmod-powers.json");
	}

	private void registerKeywords() {
		String lang = "eng";
		if (Settings.language == Settings.GameLanguage.ZHS) {
			lang = "zhs";
		}
		String keywordsPath = MOD_ID + "Resources/localization/" + lang + "/buptmod-keywords.json";
		String json = Gdx.files.internal(keywordsPath).readString(String.valueOf(StandardCharsets.UTF_8));
		Keyword[] keywords = new Gson().fromJson(json, Keyword[].class);
		if (keywords != null) {
			for (Keyword keyword : keywords) {
				String key = keyword.NAMES != null && keyword.NAMES.length > 0 ? keyword.NAMES[0].toLowerCase() : "";
				BaseMod.addKeyword(MOD_ID, key, keyword.NAMES, keyword.DESCRIPTION);
			}
		}
	}

	private static String asset(String fileName) {
		return MOD_ID + "Resources/images/" + fileName;
	}
}

