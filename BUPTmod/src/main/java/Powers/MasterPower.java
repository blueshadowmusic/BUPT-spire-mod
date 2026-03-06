package Powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import Modcore.BUPTMod;

public class MasterPower extends AbstractPower {
    public static final String POWER_ID = BUPTMod.MOD_ID + ":MasterPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String FALLBACK_NAME = "大神";
    private static final String[] FALLBACK_DESC = new String[] {"带有“大神”名字的牌获得", "点数值加成。"};
    private static final String FALLBACK_KEYWORD_ZHS = "大神";
    private static final String FALLBACK_KEYWORD_ENG = "Master";
    private static final String KEYSTROKE_MASTER_ID = BUPTMod.MOD_ID + ":KeystrokeMaster";
    private static final String TEX_84 = BUPTMod.MOD_ID + "Resources/images/Powers/MasterPower84.png";
    private static final String TEX_32 = BUPTMod.MOD_ID + "Resources/images/Powers/MasterPower32.png";
    public MasterPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings != null ? powerStrings.NAME : FALLBACK_NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.priority = 50;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(TEX_84), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(TEX_32), 0, 0, 32, 32);
        this.description = getDescriptionText();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        this.description = getDescriptionText();
    }



    private String getDescriptionText() {
        String[] parts = powerStrings != null ? powerStrings.DESCRIPTIONS : FALLBACK_DESC;
        return parts[0] + this.amount + parts[1];
    }

    public static int getBonusForCard(AbstractCard card) {
        if (card == null || AbstractDungeon.player == null) {
            return 0;
        }
        AbstractPower power = AbstractDungeon.player.getPower(POWER_ID);
        if (power == null) {
            return 0;
        }
        return matchesCard(card) ? power.amount : 0;
    }

    private static boolean matchesCard(AbstractCard card) {
        if (card == null) {
            return false;
        }
        if (KEYSTROKE_MASTER_ID.equals(card.cardID)) {
            return true; 
        }
        String[] keywords = getLocalizedKeywords();
        if (keywords.length == 0) {
            return false;
        }
        String name = card.name;
        if (name == null) {
            return false;
        }
        for (String keyword : keywords) {
            if (keyword != null && !keyword.isEmpty() && name.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private static String[] getLocalizedKeywords() {
        if (powerStrings != null && powerStrings.NAME != null && !powerStrings.NAME.isEmpty()) {
            return new String[] { powerStrings.NAME };
        }
        return new String[] { FALLBACK_KEYWORD_ZHS, FALLBACK_KEYWORD_ENG };
    }
}
