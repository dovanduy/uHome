package com.bonree.touchserver.wrappers;

import android.os.IInterface;

public final class DisplayManager {
    private final IInterface manager;

    public DisplayManager(IInterface iInterface) {
        this.manager = iInterface;
    }

    public int[] getDisplayInfo() {
        try {
            Object invoke = this.manager.getClass().getMethod("getDisplayInfo", new Class[]{Integer.TYPE}).invoke(this.manager, new Object[]{Integer.valueOf(0)});
            Class cls = invoke.getClass();
            return new int[]{cls.getDeclaredField("logicalWidth").getInt(invoke), cls.getDeclaredField("logicalHeight").getInt(invoke), cls.getDeclaredField("rotation").getInt(invoke)};
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
