package Characters.Dimly;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import Cards.Dimly.DimlyDefend;
import Cards.Dimly.KeystrokeMaster;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;

public class Dimly extends CustomPlayer {
	public static class Enums {
		@SpireEnum
		public static AbstractPlayer.PlayerClass DIMLY_CLASS;

		@SpireEnum(name = "DIMLY_BROWN")
		public static AbstractCard.CardColor COLOR_DIMLY;

		@SpireEnum(name = "DIMLY_BROWN")
		public static CardLibrary.LibraryType LIBRARY_DIMLY;
	}

	private static final String ID = "Dimly";
	private static CharacterStrings strings() {
		if (CardCrawlGame.languagePack == null) {
			return fallbackStrings();
		}
		CharacterStrings cs = CardCrawlGame.languagePack.getCharacterString("buptmod:Dimly");
		return cs != null ? cs : fallbackStrings();
	}

	private static CharacterStrings fallbackStrings() {
		CharacterStrings cs = new CharacterStrings();
		cs.NAMES = new String[] {"Dimly", "Dimly"};
		cs.TEXT = new String[] {"Dimly并非依靠刀剑，而是通过敲击键盘攀登尖塔——一名在语法中做梦、于现实中调试的大一新生。他锻造自己的卡牌，编译自己的命运，以逻辑为盾亦为矛。高塔无尽的循环低语着完美构筑的存在，只要他能修补自己勇气中的漏洞。"
		, "Dimly faces the Heart.", "Vampire?"};
		return cs;
	}
	private static final int ENERGY_PER_TURN = 3;
	private static final int START_HP = 75;
	private static final int START_GOLD = 99;
	private static final int CARD_DRAW = 5;
	private static final int ORB_SLOTS = 0;

	private static String asset(String file) {
		return "buptmodResources/images/" + file;
	}

	private static final String SHOULDER_2 = asset("Dimly/Dimly_shoulder2.png");
	private static final String SHOULDER_1 = asset("Dimly/Dimly_shoulder1.png");
	private static final String CORPSE = asset("Dimly/Dimly_corpse.png");

	private static final String[] ORB_TEXTURES = new String[] {
			asset("UI/layer1.png"),
			asset("UI/layer2.png"),
			asset("UI/layer3.png"),
			asset("UI/layer4.png"),
			asset("UI/layer5.png"),
			asset("UI/layer6.png"),
			asset("UI/layer1d.png"),
			asset("UI/layer2d.png"),
			asset("UI/layer3d.png"),
			asset("UI/layer4d.png"),
			asset("UI/layer5d.png"),
			asset("UI/layer6.png"),
			asset("UI/vfx.png")
	};

	private static final float[] LAYER_SPEED = new float[] { -20.0f, -26.0f, -18.0f, 20.0f, 0.0f, 8.0f, 12.0f, 20.0f, 12.0f, 0.0f, -12.0f, -24.0f, 0.0f };

	public Dimly(String name, PlayerClass playerClass) {
		super(name, playerClass, new CustomEnergyOrb(ORB_TEXTURES, asset("UI/vfx.png"), LAYER_SPEED), null, null);
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);

		this.initializeClass(
			asset("Dimly/Dimly_right.png"),
			SHOULDER_2,
			SHOULDER_1,
			CORPSE,
			createLoadout(),
			0.0F, 0.0F,
			220.0F,220.0F,
			new EnergyManager(ENERGY_PER_TURN)
		);
	}

	private CharSelectInfo createLoadout() {
		return new CharSelectInfo(
				strings().NAMES[0],
				strings().TEXT[0],
				START_HP,
				START_HP,
				ORB_SLOTS,
				START_GOLD,
				CARD_DRAW,
				this,
				getStartingRelics(),
				getStartingDeck(),
				false
		);
	}

	@Override
	public final CharSelectInfo getLoadout() {
		return createLoadout();
	}

	@Override
	public ArrayList<String> getStartingDeck() {
		ArrayList<String> deck = new ArrayList<>();
		deck.add(KeystrokeMaster.ID);
		deck.add(KeystrokeMaster.ID);
		deck.add(KeystrokeMaster.ID);
		deck.add(KeystrokeMaster.ID);
		deck.add(DimlyDefend.ID);
		deck.add(DimlyDefend.ID);
		deck.add(DimlyDefend.ID);
		deck.add(DimlyDefend.ID);
		deck.add("Bash");
		return deck;
	}

	@Override
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> startingRelics = new ArrayList<>();
		startingRelics.add("Burning Blood");
		return startingRelics;
	}

	@Override
	public String getTitle(PlayerClass playerClass) {
		return strings().NAMES[1];
	}

	@Override
	public AbstractCard.CardColor getCardColor() {
		return Enums.COLOR_DIMLY;
	}

	@Override
	public Color getCardTrailColor() {
		return CardHelper.getColor(200, 180, 160);
	}

	@Override
	public String getLeaderboardCharacterName() {
		return ID;
	}

	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontRed;
	}

	@Override
	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playA("ATTACK_DAGGER_2", -0.5f);
	}

	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "ATTACK_DAGGER_2";
	}

	@Override
	public int getAscensionMaxHPLoss() {
		return 5;
	}

	@Override
	public AbstractPlayer newInstance() {
		return new Dimly(ID, Enums.DIMLY_CLASS);
	}

	@Override
	public Color getCardRenderColor() {
		return CardHelper.getColor(174, 122, 81);
	}

	@Override
	public Color getSlashAttackColor() {
		return CardHelper.getColor(174, 122, 81);
	}

	@Override
	public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.BLUNT_LIGHT };
	}

	@Override
	public String getSpireHeartText() {
		return strings().TEXT[1];
	}

	@Override
	public String getVampireText() {
		return strings().TEXT[2];
	}

	@Override
	public AbstractCard getStartCardForEvent() {
		return new Bash();
	}

	@Override
	public String getLocalizedCharacterName() {
		return strings().NAMES[0];
	}

	@Override
	public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
		super.renderOrb(sb, enabled, current_x, current_y);
	}
}

