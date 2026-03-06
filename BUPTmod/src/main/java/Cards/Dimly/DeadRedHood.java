package Cards.Dimly;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import Characters.Dimly.Dimly;
import Modcore.BUPTMod;
import basemod.abstracts.CustomCard;

public class DeadRedHood extends CustomCard {
    public static final String ID = BUPTMod.MOD_ID + ":DeadRedHood";
    private static final String IMG = BUPTMod.MOD_ID + "Resources/images/Cards/Support/Support_BloodBlooming.png";
    private static final int COST = 0;
    private static final int DAMAGE = 20;
    private static final int STRENGTH_GAIN = 1;
    private static final int MAX_HP_LOSS = 1;

    public DeadRedHood() {
        super(ID,
                CardCrawlGame.languagePack.getCardStrings(ID) != null
                        ? CardCrawlGame.languagePack.getCardStrings(ID).NAME
                        : ID,
                IMG,
                COST,
                CardCrawlGame.languagePack.getCardStrings(ID) != null
                        ? CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION
                        : "",
                CardType.ATTACK,
                Dimly.Enums.COLOR_DIMLY,
                CardRarity.SPECIAL,
                CardTarget.ENEMY);
        this.baseDamage = DAMAGE;
        this.exhaust = true;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (m != null && !m.halfDead && !m.isDeadOrEscaped() && (m.isDying || m.currentHealth <= 0)) {
                    addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTH_GAIN), STRENGTH_GAIN));
                    addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            p.decreaseMaxHealth(MAX_HP_LOSS);
                            this.isDone = true;
                        }
                    });
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public AbstractCard makeCopy() {
        return new DeadRedHood();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.initializeDescription();
        }
    }
}