package com.example.wasifyounas.peerekaamil;

import com.squareup.otto.Bus;

/**
 * Created by ALI IMRAN BANGASH on 7/10/2017.
 */

public class GlobalBus {

    private static Bus sBus;
    public static Bus getBus() {
        if (sBus == null)
            sBus = new Bus();
        return sBus;
    }
}
