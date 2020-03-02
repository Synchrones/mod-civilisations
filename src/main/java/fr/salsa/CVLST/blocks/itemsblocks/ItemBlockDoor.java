package fr.salsa.CVLST.blocks.itemsblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockDoor extends ItemBlock {
    public ItemBlockDoor(Block block) {
        super(block);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(facing != EnumFacing.UP) return EnumActionResult.FAIL;
        else{
            IBlockState bottomDoorState = worldIn.getBlockState(pos);
            Block bottomDoorBlock = bottomDoorState.getBlock();
            if(!block.isReplaceable(worldIn, pos)) pos = pos.offset(facing);
            ItemStack stack = player.getHeldItem(hand);
            if(player.canPlayerEdit(pos, facing, stack) && this.block.canPlaceBlockAt(worldIn, pos)){
                EnumFacing playerFacing = EnumFacing.fromAngle((double)player.rotationYaw);
                int x = playerFacing.getFrontOffsetX();
                int z = playerFacing.getFrontOffsetZ();
                boolean flag = x < 0 && hitZ < 0.5F|| x > 0 && hitZ > 0.5F || z < 0 && hitX < 0.5F|| z > 0 && hitX > 0.5F;
                placeDoor(worldIn, pos, playerFacing, this.block, flag);
                SoundType sound = block.getSoundType(bottomDoorState, worldIn, pos, player);
                worldIn.playSound(player, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1.0F) / 2.0f, sound.getPitch() * 0.8f);
                stack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
            else return  EnumActionResult.FAIL;
        }
    }

    private static void placeDoor(World worldIn, BlockPos bottomDoorPos, EnumFacing playerFacing, Block door, boolean isRightHinge) {
        BlockPos posYClockWise = bottomDoorPos.offset(playerFacing.rotateY());
        BlockPos posYAntiClockWise = bottomDoorPos.offset(playerFacing.rotateYCCW());

        int i = (worldIn.getBlockState(posYAntiClockWise).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(posYAntiClockWise.up()).isNormalCube() ? 1 : 0);
        int i2 = (worldIn.getBlockState(posYClockWise).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(posYClockWise.up()).isNormalCube() ? 1 : 0);

        boolean flag = worldIn.getBlockState(posYAntiClockWise).getBlock() == door || worldIn.getBlockState(posYAntiClockWise.up()).getBlock() == door;
        boolean flag1 = worldIn.getBlockState(posYClockWise).getBlock() == door || worldIn.getBlockState(posYClockWise.up()).getBlock() == door;

        if((!flag || flag1) && i2 <= i){
            if(flag1 && !flag || i2 < i) isRightHinge = false;
        }
        else isRightHinge = true;

        BlockPos topDoorPos = bottomDoorPos.up();
        boolean powered = worldIn.isBlockPowered(bottomDoorPos) || worldIn.isBlockPowered(topDoorPos);
        IBlockState doorstate = door.getDefaultState().withProperty(BlockDoor.FACING, playerFacing).withProperty(BlockDoor.HINGE, isRightHinge ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.POWERED, Boolean.valueOf(powered)).withProperty(BlockDoor.OPEN, Boolean.valueOf(powered));
        worldIn.setBlockState(bottomDoorPos, doorstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER));
        worldIn.setBlockState(topDoorPos, doorstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER));
        worldIn.notifyNeighborsOfStateChange(bottomDoorPos, door, false);
        worldIn.notifyNeighborsOfStateChange(topDoorPos, door, false);
    }
}
