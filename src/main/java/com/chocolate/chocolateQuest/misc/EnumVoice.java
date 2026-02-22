package com.chocolate.chocolateQuest.misc;

public enum EnumVoice
{
    DEFAULT("none", "game.neutral.hurt", "game.neutral.die"), 
    VILLAGER("mob.villager.idle", "mob.villager.hit", "mob.villager.death"), 
    BAT("mob.bat.idle", "mob.bat.hurt", "mob.bat.death"), 
    CHICKEN("mob.chicken.say", "mob.chicken.hurt", "mob.chicken.death"), 
    COW("mob.cow.say", "mob.cow.hurt", "mob.cow.hurt"), 
    PIG("mob.pig.say", "mob.pig.say", "mob.pig.death"), 
    SHEEP("mob.sheep.say", "mob.sheep.say", "mob.sheep.say"), 
    WOLF("mob.wolf.bark", "mob.wolf.hurt", "mob.wolf.death"), 
    BLAZE("mob.blaze.breathe", "mob.blaze.hit", "mob.blaze.death"), 
    ENDERMEN("mob.endermen.idle", "mob.endermen.hit", "mob.endermen.death"), 
    SILVERFISH("mob.silverfish.say", "mob.silverfish.hit", "mob.silverfish.kill"), 
    SKELETON("mob.skeleton.say", "mob.skeleton.hurt", "mob.skeleton.death"), 
    SPIDER("mob.spider.say", "mob.spider.say", "mob.spider.death"), 
    ZOMBIE("mob.zombie.say", "mob.zombie.hurt", "mob.zombie.death"), 
    ZOMBIEPIG("mob.zombiepig.zpig", "mob.zombiepig.zpighurt", "mob.zombiepig.zpigdeath"), 
    PIRATE("chocolatequest:pirate_speak", "chocolatequest:pirate_hurt", "chocolatequest:pirate_death"), 
    MONKEY("chocolatequest:monking_speak", "chocolatequest:monking_hurt", "chocolatequest:monking_death"), 
    GOBLIN("chocolatequest:goblin_speak", "chocolatequest:goblin_hurt", "chocolatequest:goblin_death");
    
    public String say;
    public String hurt;
    public String death;
    
    private EnumVoice(final String say, final String hurt, final String death) {
        this.say = say;
        this.hurt = hurt;
        this.death = death;
    }
    
    public static String[] getNames() {
        final EnumVoice[] states = values();
        final String[] names = new String[states.length];
        for (int i = 0; i < states.length; ++i) {
            names[i] = states[i].toString();
        }
        return names;
    }
    
    public static EnumVoice getVoice(int selected) {
        final EnumVoice[] values = values();
        if (selected >= values.length) {
            selected = 0;
        }
        return values[selected];
    }
}
