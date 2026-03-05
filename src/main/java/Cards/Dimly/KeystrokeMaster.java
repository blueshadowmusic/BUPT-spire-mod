package Cards.Dimly;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import Characters.Dimly.Dimly;
import Modcore.BUPTMod;
import Powers.MasterPower;
import basemod.abstracts.CustomCard;

public class KeystrokeMaster extends CustomCard {
    public static final String ID = BUPTMod.MOD_ID + ":KeystrokeMaster";
    private static final String IMG = BUPTMod.MOD_ID + "Resources/images/Cards/Dimly/Keystroke Master.png"; 
    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int MASTER_STACK = 1;
    private static final int MASTER_STACK_UPGRADE = 1;

    public KeystrokeMaster() {
        super(ID,
            CardCrawlGame.languagePack.getCardStrings(ID) != null ? CardCrawlGame.languagePack.getCardStrings(ID).NAME : ID,
            IMG,
            COST,
            CardCrawlGame.languagePack.getCardStrings(ID) != null ? CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION : "",
            CardType.ATTACK,
            Dimly.Enums.COLOR_DIMLY,
            CardRarity.BASIC,
            CardTarget.ENEMY);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MASTER_STACK;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.STARTER_STRIKE);
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MasterPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void applyPowers() {
        int bonus = MasterPower.getBonusForCard(this);
        int realBaseDamage = this.baseDamage;
        if (bonus != 0) {
            this.baseDamage += bonus;
        }
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int bonus = MasterPower.getBonusForCard(this);
        int realBaseDamage = this.baseDamage;
        if (bonus != 0) {
            this.baseDamage += bonus;
        }
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public AbstractCard makeCopy() {
        return new KeystrokeMaster();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(MASTER_STACK_UPGRADE);
            this.initializeDescription();
        }
    }
}
