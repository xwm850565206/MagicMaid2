package com.xwm.magicmaid2.core.registry;

import com.xwm.magicmaid.player.skill.ISkill;
import com.xwm.magicmaid.registry.MagicSkillRegistry;
import com.xwm.magicmaid2.common.skill.performskill.secret.SkillJealous;
import com.xwm.magicmaid2.common.skill.performskill.secret.SkillMadeInHeaven;
import com.xwm.magicmaid2.common.skill.performskill.secret.SkillTheWorld;
import com.xwm.magicmaid2.common.skill.performskill.unreachable.SkillTaiJi;
import com.xwm.magicmaid2.common.skill.performskill.unreachable.SkillToForest;
import com.xwm.magicmaid2.common.skill.performskill.unreachable.SkillToNormalLand;

public class SkillRegistry
{
    public static final ISkill PERFORM_MADE_IN_HEAVEN = new SkillMadeInHeaven();
    public static final ISkill PERFORM_THE_WORLD = new SkillTheWorld();
    public static final ISkill PERFORM_JEALOUS = new SkillJealous();
    public static final ISkill PERFORM_TAIJI = new SkillTaiJi();
    public static final ISkill PERFORM_TO_NORMAL_LAND = new SkillToNormalLand();
    public static final ISkill PERFORM_TO_RUIN_FOREST = new SkillToForest();
    public static void registerAll()
    {
        MagicSkillRegistry.register(PERFORM_THE_WORLD.getName(), PERFORM_THE_WORLD.getClass());
        MagicSkillRegistry.register(PERFORM_MADE_IN_HEAVEN.getName(), PERFORM_MADE_IN_HEAVEN.getClass());
        MagicSkillRegistry.register(PERFORM_JEALOUS.getName(), PERFORM_JEALOUS.getClass());
        MagicSkillRegistry.register(PERFORM_TAIJI.getName(), PERFORM_TAIJI.getClass());
        MagicSkillRegistry.register(PERFORM_TO_NORMAL_LAND.getName(), PERFORM_TO_NORMAL_LAND.getClass());
        MagicSkillRegistry.register(PERFORM_TO_RUIN_FOREST.getName(), PERFORM_TO_RUIN_FOREST.getClass());
    }
}
