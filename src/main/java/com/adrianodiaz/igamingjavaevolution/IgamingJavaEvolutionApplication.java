package com.adrianodiaz.igamingjavaevolution;

import com.adrianodiaz.igamingjavaevolution.shared.Messages;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;


public class IgamingJavaEvolutionApplication {

    private static final Logger log = System.getLogger(IgamingJavaEvolutionApplication.class.getName());

    public static void main(String[] args) {
        log.log(Level.INFO, Messages.APP_STARTED);
    }

}
