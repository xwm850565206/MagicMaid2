package com.xwm.magicmaid2.core.enums;


public enum EnumState
{
    STANDARD, RISEARM, ATTACK, SKILL1, SKILL2;

    public static EnumState valueOf(int state) {
        return EnumState.values()[state];
    }

    public static int toInt(EnumState state){
        for (int i = 0; i < EnumState.values().length; i++) if (state == EnumState.values()[i]) return i;
        return 0;
    }
}
