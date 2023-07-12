package com.xwm.magicmaid2.common.item.piece;


import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid2.core.init.ItemInit;

public class ItemPiece extends ItemBase
{
    public ItemPiece(String name) {
        super(name);
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }
}
