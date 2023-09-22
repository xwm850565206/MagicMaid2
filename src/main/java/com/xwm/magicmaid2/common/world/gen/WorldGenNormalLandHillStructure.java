package com.xwm.magicmaid2.common.world.gen;

import com.google.common.collect.Lists;
import com.xwm.magicmaid.player.skill.ISkill;
import com.xwm.magicmaid.registry.MagicSkillRegistry;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenNormalLandHillStructure extends WorldGenerator
{
    private List<String> structureName;
    private String catalog;
    private String rarity;

    public WorldGenNormalLandHillStructure()
    {
        this.structureName = Lists.newArrayList("evil_skeleton", "evil_hut");
        this.catalog = "perform";
        this.rarity = "secret";
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        generateStructure(worldIn, rand, position);
        return true;
    }

    public void generateStructure(World world, Random random, BlockPos pos)
    {
        WorldServer worldServer = (WorldServer) world;
        TemplateManager manager = worldServer.getStructureTemplateManager();
        String structureName1 = structureName.get(random.nextInt(structureName.size()));
        ResourceLocation location = new ResourceLocation(Reference.MODID , structureName1);
        Template template = manager.get(worldServer.getMinecraftServer(), location);
        if (template != null)
        {
            if (structureName1.equals("evil_hut") )
                pos = pos.up(5);

            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            Rotation rotation = Rotation.values()[random.nextInt(4)];

            PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE).
                    setIgnoreEntities(false).
                    setRotation(rotation).
                    setChunk(world.getChunkFromBlockCoords(pos).getPos());

            List<ISkill> skillList = getSkillList();
            BlockPos sizePos = template.getSize();
            sizePos = Template.transformedBlockPos(settings, sizePos);
            NormalLandHillTemplate hutTemplate = new NormalLandHillTemplate(skillList, template, pos, settings);
            hutTemplate.addComponentParts(world, random, new StructureBoundingBox(pos.getX(), pos.getZ(), pos.getX() + sizePos.getX(), pos.getZ() + sizePos.getZ()));
        }
    }

    private List<ISkill> getSkillList()
    {
        List<ISkill> skillList = new ArrayList<ISkill>() {{
            for (Class<? extends ISkill> skillClazz : MagicSkillRegistry.SKILL_MAP.values())
            {
                try {
                    ISkill skill = skillClazz.newInstance();
                    String[] describeElements = skill.getName().split("\\.");
                    if (describeElements.length < 3)
                        continue;
                    if (describeElements[0].equals(catalog) && describeElements[1].equals(rarity))
                        this.add(skill);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }};

        return skillList;
    }
}
