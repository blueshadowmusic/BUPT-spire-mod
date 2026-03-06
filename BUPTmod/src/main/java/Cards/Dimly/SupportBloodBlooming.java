package Cards.Dimly;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import Characters.Dimly.Dimly;
import Modcore.BUPTMod;
import basemod.abstracts.CustomCard;

public class SupportBloodBlooming extends CustomCard {
    public static final String ID = BUPTMod.MOD_ID + ":SupportBloodBlooming";
    private static final String IMG = BUPTMod.MOD_ID + "Resources/images/Cards/Support/Support_BloodBlooming.png";
    private static final int COST = 2;

    public SupportBloodBlooming() {
        super(ID,
                CardCrawlGame.languagePack.getCardStrings(ID) != null
                        ? CardCrawlGame.languagePack.getCardStrings(ID).NAME
                        : ID,
                IMG,
                COST,
                CardCrawlGame.languagePack.getCardStrings(ID) != null
                        ? CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION
                        : "",
                CardType.SKILL,
                Dimly.Enums.COLOR_DIMLY,
                CardRarity.RARE,
                CardTarget.SELF);
        this.exhaust = true;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean aboveHalf = p.currentHealth > p.maxHealth / 2;
        AbstractCard generated = aboveHalf ? new EternalMeek() : new DeadRedHood();
        if (this.upgraded) {
            generated.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(generated, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SupportBloodBlooming();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID) != null
                    ? CardCrawlGame.languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION
                    : this.rawDescription;
            this.initializeDescription();
        }
    }
}