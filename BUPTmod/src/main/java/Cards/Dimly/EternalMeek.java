package Cards.Dimly;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import Characters.Dimly.Dimly;
import Modcore.BUPTMod;
import basemod.abstracts.CustomCard;

public class EternalMeek extends CustomCard {
    public static final String ID = BUPTMod.MOD_ID + ":EternalMeek";
    private static final String IMG = BUPTMod.MOD_ID + "Resources/images/Cards/Support/Eternal_Meek.png";
    private static final int COST = 0;
    private static final int GOLD = 15;
    private static final int GOLD_UPGRADE = 5;
    private static final int HEAL = 6;
    private static final int HEAL_UPGRADE = 2;
    private static final int MAX_HP = 1;
    private static final int MAX_HP_UPGRADE = 1;

    private int goldGain;
    private int healAmount;
    private int maxHpGain;

    public EternalMeek() {
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
                CardRarity.SPECIAL,
                CardTarget.SELF);
        this.exhaust = true;
        this.goldGain = GOLD;
        this.healAmount = HEAL;
        this.maxHpGain = MAX_HP;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainGoldAction(this.goldGain));
        addToBot(new HealAction(p, p, this.healAmount));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                p.increaseMaxHp(maxHpGain, true);
                this.isDone = true;
            }
        });
    }

    @Override
    public AbstractCard makeCopy() {
        return new EternalMeek();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.goldGain += GOLD_UPGRADE;
            this.healAmount += HEAL_UPGRADE;
            this.maxHpGain += MAX_HP_UPGRADE;
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID) != null
                    ? CardCrawlGame.languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION
                    : this.rawDescription;
            this.initializeDescription();
        }
    }
}