package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid2.core.init.ItemInit;

public class ItemSymbol extends ItemBase {
    public ItemSymbol(String name) {
        super(name);
        this.setCreativeTab(null);
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }
}
