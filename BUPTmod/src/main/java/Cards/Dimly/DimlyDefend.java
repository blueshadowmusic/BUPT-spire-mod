package Cards.Dimly;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import Characters.Dimly.Dimly;
import Modcore.BUPTMod;
import basemod.abstracts.CustomCard;

public class DimlyDefend extends CustomCard {
    public static final String ID = BUPTMod.MOD_ID + ":DimlyDefend";
    private static final String IMG = BUPTMod.MOD_ID + "Resources/images/Cards/Dimly/Defend.png";
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;

    public DimlyDefend() {
        super(ID,
            CardCrawlGame.languagePack.getCardStrings(ID) != null ? CardCrawlGame.languagePack.getCardStrings(ID).NAME : ID,
            IMG,
            COST,
            CardCrawlGame.languagePack.getCardStrings(ID) != null ? CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION : "",
            CardType.SKILL,
            Dimly.Enums.COLOR_DIMLY,
            CardRarity.BASIC,
            CardTarget.SELF);
        this.baseBlock = BLOCK;
        this.tags.add(CardTags.STARTER_DEFEND);
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DimlyDefend();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            this.initializeDescription();
        }
    }
}
