package Powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import Modcore.BUPTMod;

public class MasterDownPower extends AbstractPower {
    public static final String POWER_ID = BUPTMod.MOD_ID + ":MasterDownPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String FALLBACK_NAME = "Master Down";
    private static final String[] FALLBACK_DESC = new String[] {"At the end of your turn, lose ", " Master."};
    private static final String TEX_84 = BUPTMod.MOD_ID + "Resources/images/Powers/MasterDownPower84.png";
    private static final String TEX_32 = BUPTMod.MOD_ID + "Resources/images/Powers/MasterDownPower32.png";

    public MasterDownPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings != null ? powerStrings.NAME : FALLBACK_NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
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

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new ReducePowerAction(this.owner, this.owner, MasterPower.POWER_ID, this.amount));
        }
    }


    private String getDescriptionText() {
        String[] parts = powerStrings != null ? powerStrings.DESCRIPTIONS : FALLBACK_DESC;
        return parts[0] + this.amount + parts[1];
    }
}
