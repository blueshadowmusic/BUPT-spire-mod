package Cards.Dimly;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import Characters.Dimly.Dimly;
import Modcore.BUPTMod;
import Powers.MasterPower;
import Powers.MasterDownPower;
import basemod.abstracts.CustomCard;

public class CenterOfUniverse extends CustomCard {
    public static final String ID = BUPTMod.MOD_ID + ":CenterOfUniverse";
    private static final String IMG = BUPTMod.MOD_ID + "Resources/images/Cards/Dimly/CenterOfUniverse.png";
    private static final int COST = 1;
    private static final int MASTER_GAIN = 4;
    private static final int UPGRADE_MASTER_GAIN = 1;
    private static final int DECAY_PER_TURN = 1;

    public CenterOfUniverse() {
        super(ID,
            CardCrawlGame.languagePack.getCardStrings(ID) != null ? CardCrawlGame.languagePack.getCardStrings(ID).NAME : ID,
            IMG,
            COST,
            CardCrawlGame.languagePack.getCardStrings(ID) != null ? CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION : "",
            CardType.POWER,
            Dimly.Enums.COLOR_DIMLY,
            CardRarity.RARE,
            CardTarget.SELF);
        this.baseMagicNumber = MASTER_GAIN;
        this.magicNumber = this.baseMagicNumber;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MasterPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new MasterDownPower(p, DECAY_PER_TURN), DECAY_PER_TURN));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CenterOfUniverse();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MASTER_GAIN);
            this.initializeDescription();
        }
    }
}
